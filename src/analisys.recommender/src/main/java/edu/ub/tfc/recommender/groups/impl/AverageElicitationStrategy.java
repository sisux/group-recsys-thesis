package edu.ub.tfc.recommender.groups.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;

import edu.ub.tfc.recommender.bean.Evaluacion;
import edu.ub.tfc.recommender.bean.Resultado;
import edu.ub.tfc.recommender.distance.RootMeanSquaredError;
import edu.ub.tfc.recommender.groups.ElicitationStrategy;

public class AverageElicitationStrategy implements ElicitationStrategy {

	/* ****************************
			ATTRIBUTES
	* *************************** */
	
	private Float _mae;
	private Float _rmse;

	/* ****************************
			PUBLIC METHODS
	* *************************** */
	
	@Override
	public Map<Long, Float> evaluate(Map<Long, Map<Long, Float>> theUsersEstimations) {
		Map<Long, Float> tmpResult = null;
		Boolean isFirst = true;
		
		resetMetrics();
		
		for(Map<Long, Float> tmpItemEstimation : theUsersEstimations.values()) {
			if(isFirst) {
				tmpResult = new HashMap<Long, Float>(tmpItemEstimation);
				isFirst = false;
			} else {
				sumItemsEstimations(tmpResult, tmpItemEstimation);	
			}
		}
		avgItemsEstimations(tmpResult, theUsersEstimations.size());
		return tmpResult;
	}
	
	/* ****************************
			ACCESSORS
	* *************************** */
	
	public Float getMAE() {
		return _mae;
	}
	
	public Float getRMSE() {
		return _rmse;
	}

	/* ****************************
			PRIVATE METHODS
	* *************************** */
	
	/**
	 * 
	 */
	private void resetMetrics() {
		this._mae = Float.NaN;
		this._rmse = Float.NaN;
	}
	
	/**
	 * 
	 * @param theBase
	 * @param theEstimationToAdd
	 */
	private void sumItemsEstimations(Map<Long, Float> theBase,
			Map<Long, Float> theEstimationToAdd) {
	
		Float tmpCurrentValue;
		for(Long tmpCurrentItemId : theBase.keySet()) {
			tmpCurrentValue = theBase.get(tmpCurrentItemId);
			tmpCurrentValue += theEstimationToAdd.get(tmpCurrentItemId);
			theBase.put(tmpCurrentItemId, tmpCurrentValue);
		}
	}

	/**
	 * 
	 * @param theBase
	 * @param theNumOfEstimationsAdded
	 */
	private void avgItemsEstimations(Map<Long, Float> theBase, int theNumOfEstimationsAdded) {
		Float tmpCurrentValue;
		for(Long tmpCurrentItemId : theBase.keySet()) {
			tmpCurrentValue = theBase.get(tmpCurrentItemId);
			tmpCurrentValue = (tmpCurrentValue / theNumOfEstimationsAdded);
			theBase.put(tmpCurrentItemId, tmpCurrentValue);
		}
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
		Map<Long, Float> evaluate = evalItem.getEvaluacion();
		
		// calculo de la distancia
		final double[] d1 = new double[totalItems];
		final double[] d2 = new double[totalItems];
		calculateDistances(d1, d2, evaluate, arrayReal, userID);
		
		double mae = calculateMAE(totalItems, d1, d2);
		double rmse = calculateRMSE(totalItems, d1, d2);
	}
	
	/**
	 * Obtiene las todas valoraciones reales y estimadas del usuario
	 * @param d1 Todas las valoraciones reales
	 * @param d2 Todas las valoraciones estimadas
	 * @param evaluate
	 * @param arrayReal
	 * @param userID
	 * @throws TasteException
	 */
	private void calculateDistances(final double[] d1, final double[] d2, Map<Long, Float> evaluate, PreferenceArray arrayReal, Long userID) throws TasteException {
		for (int i = 0; i < d1.length; i++) {
			final long itemID = arrayReal.get(i).getItemID();

			d1[i] = arrayReal.get(i).getValue();
			final Float preferenceValue = this._testModel.getPreferenceValue(userID, itemID);

			if (preferenceValue != null) {
				d2[i] = preferenceValue;
			} else {
				d2[i] = evaluate.get(itemID);
			}
		}
	}
	
	/**
	 * Realiza el cálculo del Root Mean Squared Error
	 * @param totalItems
	 * @param d1
	 * @param d2
	 * @return
	 */
	private double calculateRMSE(final int totalItems, final double[] d1, final double[] d2) {
		final RootMeanSquaredError calculo = new RootMeanSquaredError();
		double rmse = calculo.distance(d1, d2) / Math.sqrt(totalItems);

		String val = rmse + "";
		BigDecimal big = new BigDecimal(val);
		rmse = big.setScale(5, RoundingMode.HALF_UP).doubleValue();
		return rmse;
	}

	/**
	 * Realiza el cálculo del Mean Absolute Error
	 * @param totalItems
	 * @param d1
	 * @param d2
	 * @return
	 */
	private double calculateMAE(final int totalItems, final double[] d1, final double[] d2) {
		final double distance = ManhattanDistanceMeasure.distance(d1, d2);
		double mae = distance / totalItems;

		String val = mae + "";
		BigDecimal big = new BigDecimal(val);
		mae = big.setScale(5, RoundingMode.HALF_UP).doubleValue();
		return mae;
	}

}
