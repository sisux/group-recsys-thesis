package edu.ub.tfc.recommender.groups;

import static edu.ub.tfc.recommender.utils.Utils.escribirCsv;
import static edu.ub.tfc.recommender.utils.Utils.escribirLog;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.GenericJDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;

import edu.ub.tfc.recommender.bean.Evaluacion;
import edu.ub.tfc.recommender.bean.Resultado;
import edu.ub.tfc.recommender.bean.UserGroup;
import edu.ub.tfc.recommender.dao.UserGroupDAO;
import edu.ub.tfc.recommender.distance.RootMeanSquaredError;
import edu.ub.tfc.recommender.services.RecommenderService;
import edu.ub.tfc.recommender.services.impl.RecommenderServiceType;

public class GRSAnalyser {

	/* ****************************
			CONSTANTS
	 * *************************** */
	
	private static final Integer MAX_RANDOM_ATTEMPTS = 2000;
	
	/* ****************************
    		ATTRIBUTES
	 * *************************** */
	
	private GenericJDBCDataModel _testModel;
	private GenericJDBCDataModel _trainModel;
	private List<Long> _groupIds;
	private String _nombreFicheroCsv;
	private String _pathFicheroCsv;
	private UserGroupDAO _userGroupDAO;
	
	/* ****************************
              CONSTRUCTORS
	 **************************** */
	
	public GRSAnalyser(GenericJDBCDataModel theTestModel, GenericJDBCDataModel theTrainModel) throws TasteException {
		_testModel = theTestModel;
		_trainModel = theTrainModel;

		_userGroupDAO = new UserGroupDAO();
		_groupIds = _userGroupDAO.getAllUserGroupIds();
	}
	
	/* ****************************
              ACCESSORS
	 **************************** */
	
	/**
	 * @return the nombreFicheroCsv
	 */
	public String getNombreFicheroCsv() {
		return _nombreFicheroCsv;
	}

	/**
	 * @return the pathFicheroCsv
	 */
	public String getPathFicheroCsv() {
		return _pathFicheroCsv;
	}

	/**
	 * @param nombreFicheroCsv the nombreFicheroCsv to set
	 */
	public void setNombreFicheroCsv(String nombreFicheroCsv) {
		this._nombreFicheroCsv = nombreFicheroCsv;
	}

	/**
	 * @param pathFicheroCsv the pathFicheroCsv to set
	 */
	public void setPathFicheroCsv(String pathFicheroCsv) {
		this._pathFicheroCsv = pathFicheroCsv;
	}
	
	/* ****************************
    		PUBLIC METHODS
	 * *************************** */
	
	public Map<Long, Resultado> performAnalisys(final Map<String, RecommenderService> recommenderServices, final Integer totalIterations, final Integer itemsEstimados) throws IOException {
		escribirLog("========================================");
		escribirLog("INICIANDO TEST");

		Map<Long, Resultado> tmpAnalysisResult = null;
		try {
			escribirLog("TEST PARA "+ totalIterations + " GRUPOS");
			tmpAnalysisResult = this.evaluate(recommenderServices, totalIterations, itemsEstimados);
		
			escribirLog("ESCRIBIENDO FICHERO DE RESULTADO");
			escribirCsv(tmpAnalysisResult, _nombreFicheroCsv, _pathFicheroCsv);
		} catch (final TasteException e) {
			escribirLog("ERROR");
		}
		escribirLog("TEST FINALIZADO");
		escribirLog("========================================");
		return tmpAnalysisResult;
	}
	
	/* ****************************
			PRIVATE METHODS
	 * *************************** */
	
	/**
	 * Realiza las evaluaciones
	 * @param services Servicios a evaluar
	 * @param totalIterations Total de iteraciones
	 * @param itemsEstimados Nœmero de ’tems a estimar
	 * @return Conjunto de evaluaciones
	 * @throws TasteException Fallo del recomendador
	 */
	private Map<Long, Resultado> evaluate(final Map<String, RecommenderService> recommenderServices, final Integer totalIterations, final Integer itemsEstimados) throws TasteException {
		// mapa que se devolvera
		final Map<Long, Resultado> tmpResult = new HashMap<Long, Resultado>();
		
		Resultado tmpResultado;
		PreferenceArray arrayReal;
		int totalItems;
		List<Long> items;
		Map<Long, Float> backup;
		Evaluacion evaluacion;
		UserGroup tmpCurrentGroup;
		
		int iteracion = 1;
		// por cada usuario (iteraci—n)
		for (final Long tmpGroupId : this._groupIds) {
			if (iteracion > totalIterations) {
				break;
			}
			
			escribirLog("----------------------------------------");
			escribirLog("EVALUANDO GRUPO " + tmpGroupId);
			tmpCurrentGroup = _userGroupDAO.findGroupById(tmpGroupId);
			
			//TODO: change this
//			arrayReal = this._trainModel.getPreferencesFromUser(tmpGroupId);
//			totalItems = arrayReal.length();
//
			items = retrieveNonRatedItems(tmpCurrentGroup.get_userIds(), itemsEstimados);
			
//			items = new ArrayList<Long>();
//			backup = new HashMap<Long, Float>();
//			removeAndBackupValuations(items, backup, tmpGroupId, itemsEstimados);
			
			//for all services to evaluate
			RecommenderService recommenderService;
			tmpResultado = new Resultado();
			for (String key : recommenderServices.keySet()) {
				escribirLog("EVALUANDO SERVICIO " + key);
				recommenderService = recommenderServices.get(key);
				
				//Ejecuta el servicio de recomendacion y obtiene las correspondientes estimaciones
				evaluacion = evaluateService(recommenderService, items, tmpGroupId);
				
				//TODO: change this
//				//Calcula la diferencia entre lo recomendado y lo real
//				escribirLog("CALCULANDO DISTANCIAS");
//				updateDistances(tmpResultado, key, evaluacion, totalItems, arrayReal, tmpGroupId);
			}
			tmpResult.put(tmpGroupId, tmpResultado);
			iteracion++;

			//TODO: change this
//			//Restablece el estado inicial para dicho userId
//			restoreRemovedValuations(tmpGroupId, backup);
		}
		return tmpResult;
	}
	
	/**
	 * Calcula las métricas entre la valoración real del usuario y la estimación obtenida por el recomendador
	 * @param theResultado
	 * @param key
	 * @param evalItem
	 * @param totalItems
	 * @param arrayReal
	 * @param userID
	 * @throws TasteException
	 */
	private void updateDistances(Resultado theResultado, String key, Evaluacion evalItem, int totalItems, PreferenceArray arrayReal, Long userID) throws TasteException {
//		Map<Long, Float> evaluate = evalItem.getEvaluacion();
//		
//		// calculo de la distancia
//		final double[] d1 = new double[totalItems];
//		final double[] d2 = new double[totalItems];
//		calculateDistances(d1, d2, evaluate, arrayReal, userID);
//		
//		double mae = calculateMAE(totalItems, d1, d2);
//		double rmse = calculateRMSE(totalItems, d1, d2);

		updateResultado(key, theResultado, mae, rmse, evalItem.getTime());
	}
	
	/**
	 * Actualiza el resultado del analisis en el objeto general
	 * @param key
	 * @param tmpResultado
	 * @param mae
	 * @param rmse
	 * @param evalItemTime
	 */
	private void updateResultado(String key, Resultado tmpResultado, double mae, double rmse, long evalItemTime) {
	
		if (RecommenderServiceType.GROUP_EUCLIDEAN_SERVICE.equalsName(key)) {
			tmpResultado.setUserPearsonMae(mae);
			tmpResultado.setUserPearsonRmse(rmse);
			tmpResultado.setUserPearsonTime(evalItemTime);
		} else if (RecommenderServiceType.GROUP_AVERAGE_SERVICE.equalsName(key)) {
			tmpResultado.setUserCosineMae(mae);
			tmpResultado.setUserCosineRmse(rmse);
			tmpResultado.setUserCosineTime(evalItemTime);
		}
	}

	/**
	 * Evalua el tiempo de ejecución del servicio de recomendacion
	 * @param recommenderService
	 * @param items
	 * @param userID
	 * @return
	 * @throws TasteException
	 */
	private Evaluacion evaluateService(RecommenderService recommenderService, List<Long> items, Long userID) throws TasteException {
		
		long timeStart = System.currentTimeMillis();
		Map<Long, Float> evaluacion = recommenderService.evaluate(userID, items);
		long timeFinish = System.currentTimeMillis();
		long time = (timeFinish - timeStart) / items.size();
		Evaluacion evalItem = new Evaluacion(time, evaluacion);

		return evalItem;
	}
	
	/**
	 * Elimina valoraciones reales del usuario indicado en el TEST MODEL
	 * @param items
	 * @param backup
	 * @param userID
	 * @param itemsEstimados
	 * @throws TasteException
	 */
	private void removeAndBackupValuations(List<Long> items, Map<Long, Float> backup, Long userID, Integer itemsEstimados) throws TasteException {
		Random generator = new Random(19580427);// random aleatorio
		
		escribirLog("CARGANDO ITEMS VALORADOS PARA EL USUARIO");
		FastIDSet itemIDs = this._trainModel.getItemIDsFromUser(userID);
		List<Long> tmpItemsIds = convertToList(itemIDs);

		escribirLog("BORRANDO "+ itemsEstimados +" VALORACIONES ALEATORIAS");
		for (int i = 0; i < itemsEstimados; i++) {
			int index = generator.nextInt(tmpItemsIds.size());
			Long itemID = tmpItemsIds.get(index);
			Float preferenceValue = this._testModel.getPreferenceValue(userID, itemID);
			if (preferenceValue != null) {
				backup.put(itemID, preferenceValue);
				items.add(itemID);
				this._testModel.removePreference(userID, itemID);
			} else {
				i--;
			}
		}
	}
	
	/**
	 * 
	 * @param userIDs
	 * @return
	 */
	private List<Long> convertToList(LongPrimitiveIterator userIDs) {
		List<Long> tmpItemsIds = new ArrayList<Long>();
		while (userIDs.hasNext()) {
			tmpItemsIds.add(userIDs.next());
		}
		return tmpItemsIds;
	}
	
	
	/**
	 * Convierte un tipo de datos a otro
	 * @param theItemIDs
	 * @return
	 */
	private List<Long> convertToList(FastIDSet theItemIDs) {
		final Iterator<Long> iterator = theItemIDs.iterator();
		List<Long> tmpItemsIds = new ArrayList<Long>();
		while (iterator.hasNext()) {
			tmpItemsIds.add(iterator.next());
		}
		return tmpItemsIds;
	}
	
	/**
	 * Reestablece las valoraciones reales del usuario en el MODEL TEST
	 * @param userID
	 * @param backup
	 * @throws TasteException
	 */
	private void restoreRemovedValuations(final Long userID, final Map<Long, Float> backup) throws TasteException {
		escribirLog("RESTAURANDO VALORACIONES ELIMINADAS");
		// Restauraci—n del backup de valores para las siguientes pruebas
		for (final Long itemID : backup.keySet()) {
			this._testModel.setPreference(userID, itemID, backup.get(itemID));
		}
	}

//	/**
//	 * Realiza el cálculo del Root Mean Squared Error
//	 * @param totalItems
//	 * @param d1
//	 * @param d2
//	 * @return
//	 */
//	private double calculateRMSE(final int totalItems, final double[] d1, final double[] d2) {
//		final RootMeanSquaredError calculo = new RootMeanSquaredError();
//		double rmse = calculo.distance(d1, d2) / Math.sqrt(totalItems);
//
//		String val = rmse + "";
//		BigDecimal big = new BigDecimal(val);
//		rmse = big.setScale(5, RoundingMode.HALF_UP).doubleValue();
//		return rmse;
//	}
//
//	/**
//	 * Realiza el cálculo del Mean Absolute Error
//	 * @param totalItems
//	 * @param d1
//	 * @param d2
//	 * @return
//	 */
//	private double calculateMAE(final int totalItems, final double[] d1, final double[] d2) {
//		final double distance = ManhattanDistanceMeasure.distance(d1, d2);
//		double mae = distance / totalItems;
//
//		String val = mae + "";
//		BigDecimal big = new BigDecimal(val);
//		mae = big.setScale(5, RoundingMode.HALF_UP).doubleValue();
//		return mae;
//	}
	
//	/**
//	 * Obtiene las todas valoraciones reales y estimadas del usuario
//	 * @param d1 Todas las valoraciones reales
//	 * @param d2 Todas las valoraciones estimadas
//	 * @param evaluate
//	 * @param arrayReal
//	 * @param userID
//	 * @throws TasteException
//	 */
//	private void calculateDistances(final double[] d1, final double[] d2, Map<Long, Float> evaluate, PreferenceArray arrayReal, Long userID) throws TasteException {
//		for (int i = 0; i < d1.length; i++) {
//			final long itemID = arrayReal.get(i).getItemID();
//
//			d1[i] = arrayReal.get(i).getValue();
//			final Float preferenceValue = this._testModel.getPreferenceValue(userID, itemID);
//
//			if (preferenceValue != null) {
//				d2[i] = preferenceValue;
//			} else {
//				d2[i] = evaluate.get(itemID);
//			}
//		}
//	}
	
	/**
	 * Returns N non rated items by any user
	 * @param theUserList
	 * @return
	 * @throws TasteException 
	 */
	private List<Long> retrieveNonRatedItems(List<Long> theUserList, Integer theItemsToRetrieve) throws TasteException {
		Set<Long> tmpRatedItemsByAnyUser = getAllRatedItems(theUserList);
		List<Long> tmpResult = new ArrayList<Long>();
		boolean isEnd = false;
		Long tmpRandomItemId;
		Integer tmpAttempts = 0;
		Random generator = new Random(19580427);// random aleatorio
		
		int range = this._trainModel.getNumItems() - 1 + 1;

		while(!isEnd) {
			//get the ith item of the model
			tmpRandomItemId = new Long(generator.nextInt(range) + 1);

			if(!tmpRatedItemsByAnyUser.contains(tmpRandomItemId)) {
				tmpResult.add(tmpRandomItemId);
			}
			tmpAttempts++;
			isEnd = (tmpResult.size() == theItemsToRetrieve) || (tmpAttempts > MAX_RANDOM_ATTEMPTS);
		}
		return tmpResult;
	}
	
	/**
	 * Gets a set with all the items rated by a user
	 * @param theUserList
	 * @return
	 * @throws TasteException 
	 */
	private Set<Long> getAllRatedItems(List<Long> theUserList) throws TasteException {
		Set<Long> tmpResult = new HashSet<Long>();
		FastIDSet tmpItemsFromUser;
		for(Long tmpUserId : theUserList) {
			tmpItemsFromUser = this._trainModel.getItemIDsFromUser(tmpUserId);
			tmpResult.addAll(convertToList(tmpItemsFromUser));
		}
		return tmpResult;
	}
	
}
