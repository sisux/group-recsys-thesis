package edu.ub.tfc.recommender.servlet;

import static edu.ub.tfc.recommender.utils.Utils.escribirCsv;
import static edu.ub.tfc.recommender.utils.Utils.escribirLog;
import static edu.ub.tfc.recommender.utils.Utils.streamBinaryData;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.GenericJDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.ub.tfc.recommender.bean.Evaluacion;
import edu.ub.tfc.recommender.bean.Resultado;
import edu.ub.tfc.recommender.distance.RootMeanSquaredError;
import edu.ub.tfc.recommender.services.RecommenderService;

/**
 * Servlet
 * @author David Gil De Arce
 */
public class RecommenderServlet extends HttpServlet {

	private static final String ITEM_COSINE = "itemCosine";
	private static final String ITEM_PEARSON = "itemPearson";
	private static final String USER_COSINE = "userCosine";
	private static final String USER_PEARSON = "userPearson";
	private static final String USER_EUCLIDEAN = "userEuclidean";
	private static final String ITEM_EUCLIDEAN = "itemEuclidean";
	private static final String ITEM_SPEARMAN	= "itemSpearman";
	private static final String USER_SPEARMAN = "userSpearman";

	private static final String ITEM_COSINE_SERVICE = "itemCosineService";
	private static final String ITEM_PEARSON_SERVICE = "itemPearsonService";
	private static final String USER_COSINE_SERVICE = "userCosineService";
	private static final String USER_PEARSON_SERVICE = "userPearsonService";
	private static final String USER_EUCLIDEAN_SERVICE = "userEuclideanService";
	private static final String ITEM_EUCLIDEAN_SERVICE = "itemEuclideanService";
	private static final String ITEM_SPEARMAN_SERVICE= "itemSpearmanService";
	private static final String USER_SPEARMAN_SERVICE = "userSpearmanService";

	private static final String CONFIGURACION_JSP = "/WEB-INF/views/configuracion.jsp";
	private static final String RESULTADOS_JSP = "/WEB-INF/views/resultados.jsp";
	private static final long serialVersionUID = 1L;

	private List<Long> usersIds = new ArrayList<Long>();
	private GenericJDBCDataModel testModel = null;
	private GenericJDBCDataModel trainModel = null;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getParameter("action");
		if (action != null) {
			final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			final String nombreFicheroCsv = (String) springContext.getBean("nombreFicheroCsv");
			final String pathFicheroCsv = (String) springContext.getBean("nombreFicheroCsv");
			if (action.equals("test")) {
				escribirLog("========================================");
				escribirLog("INICIANDO TEST");
				LongPrimitiveIterator userIDs = null;

				try {
					this.trainModel = (GenericJDBCDataModel) springContext.getBean("trainModel");
					this.testModel = (GenericJDBCDataModel) springContext.getBean("testModel");

					userIDs = this.trainModel.getUserIDs();
				} catch (final TasteException e1) {
					e1.printStackTrace();
				}
				this.usersIds = new ArrayList<Long>();
				while (userIDs.hasNext()) {
					this.usersIds.add(userIDs.next());
				}
				escribirLog("TEST PARA " + this.usersIds.size() + " USUARIOS");

				Map<Long, Resultado> testCase = new HashMap<Long, Resultado>();
				final List<String> services = new ArrayList<String>();
				if (request.getParameter(USER_PEARSON) != null) {
					escribirLog("EVALUACIÓN USER-PEARSON");
					services.add(USER_PEARSON_SERVICE);
				}
				if (request.getParameter(USER_COSINE) != null) {
					escribirLog("EVALUACIÓN USER-COSINE");
					services.add(USER_COSINE_SERVICE);
				}
				if (request.getParameter(ITEM_PEARSON) != null) {
					escribirLog("EVALUACIÓN ITEM-PEARSON");
					services.add(ITEM_PEARSON_SERVICE);
				}
				if (request.getParameter(ITEM_COSINE) != null) {
					escribirLog("EVALUACIÓN ITEM-COSINE");
					services.add(ITEM_COSINE_SERVICE);
				}

				if (request.getParameter(ITEM_EUCLIDEAN) != null) {
					escribirLog("EVALUACIÓN ITEM-EUCLIDEAN");
					services.add(ITEM_EUCLIDEAN_SERVICE);
				}
				if (request.getParameter(USER_EUCLIDEAN) != null) {
					escribirLog("EVALUACIÓN USER-EUCLIDEAN");
					services.add(USER_EUCLIDEAN_SERVICE);
				}
				if (request.getParameter(ITEM_SPEARMAN) != null) {
					escribirLog("EVALUACIÓN ITEM-SPEARMAN");
					services.add(ITEM_SPEARMAN_SERVICE);
				}
				if (request.getParameter(USER_SPEARMAN) != null) {
					escribirLog("EVALUACIÓN USER-SPEARMAN");
					services.add(USER_SPEARMAN_SERVICE);
				}

				try {
					final Integer totalIterations = Integer.valueOf(request.getParameter("totalIterations"));
					final Integer itemsEstimados = Integer.valueOf(request.getParameter("itemsEstimados"));					
					escribirLog("TEST PARA "+ totalIterations + " ITERACIONES");
					testCase = this.evaluate(services, totalIterations, itemsEstimados);
					escribirLog("ESCRIBIENDO FICHERO DE RESULTADO");
					escribirCsv(testCase, nombreFicheroCsv, pathFicheroCsv);
				} catch (final TasteException e) {
					escribirLog("ERROR");
				}
				escribirLog("TEST FINALIZADO");
				escribirLog("========================================");
				request.setAttribute("testCase", testCase);
				final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(RESULTADOS_JSP);
				dispatcher.forward(request, response);
			} else if (action.equals("download")) {
				final ServletOutputStream sOutStream = response.getOutputStream();
				streamBinaryData(pathFicheroCsv, nombreFicheroCsv, sOutStream, response);
			}
		} else {
			final RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(CONFIGURACION_JSP);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Realiza las evaluaciones
	 * @param services Servicios a evaluar
	 * @param totalIterations Total de iteraciones
	 * @param itemsEstimados Número de ítems a estimar
	 * @return Conjunto de evaluaciones
	 * @throws TasteException Fallo del recomendador
	 */
	private Map<Long, Resultado> evaluate(final List<String> services, final Integer totalIterations, final Integer itemsEstimados) throws TasteException {
		// mapa que se devolverá
		final Map<Long, Resultado> iterations = new HashMap<Long, Resultado>();
		// contexto de spring
		final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

		escribirLog("CARGANDO SERVICIOS");
		final Map<String, RecommenderService> recommenderServices = new HashMap<String, RecommenderService>();
		for (final String service : services) {
			final RecommenderService recommenderService = (RecommenderService) springContext.getBean(service);
			recommenderServices.put(service, recommenderService);
		}

		// random aleatorio
		final Random generator = new Random(19580427);

		int iteracion = 1;
		// por cada usuario (iteración)
		for (final Long userID : this.usersIds) {
			if (iteracion > totalIterations) {
				break;
			}
			escribirLog("----------------------------------------");
			escribirLog("EVALUANDO USUARIO " + userID);
			final PreferenceArray arrayReal = this.trainModel.getPreferencesFromUser(userID);
			final int totalItems = arrayReal.length();

			escribirLog("CARGANDO ITEMS VALORADOS PARA EL USUARIO");
			final FastIDSet itemIDs = this.trainModel.getItemIDsFromUser(userID);
			final Iterator<Long> iterator = itemIDs.iterator();
			final List<Long> itemsIds = new ArrayList<Long>();
			while (iterator.hasNext()) {
				itemsIds.add(iterator.next());
			}

			escribirLog("BORRANDO "+ itemsEstimados +" VALORACIONES ALEATORIAS");
			final List<Long> items = new ArrayList<Long>();
			final Map<Long, Float> backup = new HashMap<Long, Float>();
			for (int i = 0; i < itemsEstimados; i++) {
				final int index = generator.nextInt(itemsIds.size());
				final Long itemID = itemsIds.get(index);
				final Float preferenceValue = this.testModel.getPreferenceValue(userID, itemID);
				if (preferenceValue != null) {
					backup.put(itemID, preferenceValue);
					items.add(itemID);
					this.testModel.removePreference(userID, itemID);
				} else {
					i--;
				}
			}

			final Map<String, Evaluacion> evaluaciones = new HashMap<String, Evaluacion>();
			for (final String key : recommenderServices.keySet()) {
				escribirLog("EVALUANDO SERVICIO " + key);
				final RecommenderService recommenderService = recommenderServices.get(key);
				final long timeStart = System.currentTimeMillis();
				final Map<Long, Float> evaluacion = recommenderService.evaluate(userID, items);
				final long timeFinish = System.currentTimeMillis();
				final long time = (timeFinish - timeStart) / items.size();
				final Evaluacion evalItem = new Evaluacion(time, evaluacion);
				evaluaciones.put(key, evalItem);
			}

			escribirLog("CALCULANDO DISTANCIAS");
			final Resultado evaluation = new Resultado();
			for (final String key : recommenderServices.keySet()) {
				final Evaluacion evalItem = evaluaciones.get(key);
				final Map<Long, Float> evaluate = evalItem.getEvaluacion();
				// calculo de la distancia
				final double[] d1 = new double[totalItems];
				final double[] d2 = new double[totalItems];
				for (int i = 0; i < totalItems; i++) {
					final long itemID = arrayReal.get(i).getItemID();

					d1[i] = arrayReal.get(i).getValue();
					final Float preferenceValue = this.testModel.getPreferenceValue(userID, itemID);

					if (preferenceValue != null) {
						d2[i] = preferenceValue;
					} else {
						d2[i] = evaluate.get(itemID);
					}
				}
				final double distance = ManhattanDistanceMeasure.distance(d1, d2);
				double mae = distance / totalItems;

				String val = mae + "";
				BigDecimal big = new BigDecimal(val);
				mae = big.setScale(5, RoundingMode.HALF_UP).doubleValue();

				final RootMeanSquaredError calculo = new RootMeanSquaredError();
				double rmse = calculo.distance(d1, d2) / Math.sqrt(totalItems);

				val = rmse + "";
				big = new BigDecimal(val);
				rmse = big.setScale(5, RoundingMode.HALF_UP).doubleValue();

				if (key.equals(USER_PEARSON_SERVICE)) {
					evaluation.setUserPearsonMae(mae);
					evaluation.setUserPearsonRmse(rmse);
					evaluation.setUserPearsonTime(evalItem.getTime());
				} else if (key.equals(USER_COSINE_SERVICE)) {
					evaluation.setUserCosineMae(mae);
					evaluation.setUserCosineRmse(rmse);
					evaluation.setUserCosineTime(evalItem.getTime());
				} else if (key.equals(ITEM_PEARSON_SERVICE)) {
					evaluation.setItemPearsonMae(mae);
					evaluation.setItemPearsonRmse(rmse);
					evaluation.setItemPearsonTime(evalItem.getTime());
				} else if (key.equals(ITEM_COSINE_SERVICE)) {
					evaluation.setItemCosineMae(mae);
					evaluation.setItemCosineRmse(rmse);
					evaluation.setItemCosineTime(evalItem.getTime());
				} else if (key.equals(USER_EUCLIDEAN_SERVICE)) {
					evaluation.setUserEuclideanMae(mae);
					evaluation.setUserEuclideanRmse(rmse);
					evaluation.setUserEuclideanTime(evalItem.getTime());
				} else if (key.equals(USER_SPEARMAN_SERVICE)) {
					evaluation.setUserSpearmanMae(mae);
					evaluation.setUserSpearmanRmse(rmse);
					evaluation.setUserSpearmanTime(evalItem.getTime());
				} else if (key.equals(ITEM_EUCLIDEAN_SERVICE)) {
					evaluation.setItemEuclideanMae(mae);
					evaluation.setItemEuclideanRmse(rmse);
					evaluation.setItemEuclideanTime(evalItem.getTime());
				} else if (key.equals(ITEM_SPEARMAN_SERVICE)) {
					evaluation.setItemSpearmanMae(mae);
					evaluation.setItemSpearmanRmse(rmse);
					evaluation.setItemSpearmanTime(evalItem.getTime());
				}
			}

			escribirLog("RESTAURANDO VALORACIONES ELIMINADAS");
			// Restauración del backup de valores para las siguientes pruebas
			for (final Long itemID : backup.keySet()) {
				this.testModel.setPreference(userID, itemID, backup.get(itemID));
			}
			
			iterations.put(userID, evaluation);
			iteracion++;
		}
		return iterations;
	}
}
