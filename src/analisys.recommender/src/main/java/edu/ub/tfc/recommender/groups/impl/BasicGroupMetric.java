package edu.ub.tfc.recommender.groups.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;

import edu.ub.tfc.recommender.distance.RootMeanSquaredError;
import edu.ub.tfc.recommender.groups.GroupMetric;

public class BasicGroupMetric implements GroupMetric {

	/* ****************************
				CONSTANTS
	* *************************** */	
	
	private static final String MAE_METRIC_NAME = "MAE";
	private static final String RMSE_METRIC_NAME = "RMSE";

	/* ****************************
			PUBLIC METHODS
	* *************************** */
	 
	//Calcula las métricas entre la valoración real del usuario y la estimación obtenida por el recomendador
	@Override
	public Map<String, String> calculate(Map<Long, Float> theGroupEstimation, Map<Long, Map<Long, Float>> theUsersEstimations) {
		Double tmpGroupMAE = (double) 0;
		Double tmpGroupRMSE = (double) 0;
		
		Double tmpUserMAE;
		Double tmpUserRMSE;
		double[] realUserRatingVector;
		double[] predictedUserRatingVector;
		
		Map<String, String> tmpResult = new HashMap<String,String>();
		int totalItems = theGroupEstimation.size();
		
		try {
			//per each user of the group....
			for(Long tmpUserId : theUsersEstimations.keySet()) {
				// calculo de la distancia
				realUserRatingVector = new double[totalItems];
				predictedUserRatingVector = new double[totalItems];
	
				fillRatingVectors(realUserRatingVector, predictedUserRatingVector, theGroupEstimation, theUsersEstimations.get(tmpUserId));
				
				tmpUserMAE = calculateMAE(totalItems, realUserRatingVector, predictedUserRatingVector);
				tmpUserRMSE = calculateRMSE(totalItems, realUserRatingVector, predictedUserRatingVector);
	
				tmpGroupMAE += tmpUserMAE;
				tmpGroupRMSE += tmpUserRMSE;
			}
			
			tmpGroupMAE = tmpGroupMAE / theUsersEstimations.size();
			tmpGroupRMSE = tmpGroupRMSE / theUsersEstimations.size();
			
			tmpResult.put(MAE_METRIC_NAME, tmpGroupMAE.toString());
			tmpResult.put(RMSE_METRIC_NAME, tmpGroupRMSE.toString());
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tmpResult;
	}
	
	/* ****************************
			PRIVATE METHODS
	* *************************** */
	
	/**
	 * Obtiene las todas valoraciones reales y estimadas del usuario
	 * @param realUserRatingVector Todas las valoraciones reales
	 * @param predictedUserRatingVector Todas las valoraciones estimadas
	 * @param evaluate
	 * @param arrayReal
	 * @param userID
	 * @throws TasteException
	 */
	private void fillRatingVectors(final double[] realUserRatingVector, final double[] predictedUserRatingVector, Map<Long, Float> theGroupEstimation, Map<Long, Float> theUsersEstimation) throws TasteException {
		int i = 0;
		for (Long tmpItemId : theGroupEstimation.keySet()) {
			realUserRatingVector[i] = theUsersEstimation.get(tmpItemId);
			predictedUserRatingVector[i] = theGroupEstimation.get(tmpItemId);
			i++;
		}
	}

	/**
	 * Realiza el cálculo del Root Mean Squared Error
	 * @param totalItems
	 * @param realUserRatingVector
	 * @param predictedUserRatingVector
	 * @return
	 */
	private double calculateRMSE(final int totalItems, final double[] realUserRatingVector, final double[] predictedUserRatingVector) {
		final RootMeanSquaredError calculo = new RootMeanSquaredError();
		double rmse = calculo.distance(realUserRatingVector, predictedUserRatingVector) / Math.sqrt(totalItems);

		String val = rmse + "";
		BigDecimal big = new BigDecimal(val);
		rmse = big.setScale(5, RoundingMode.HALF_UP).doubleValue();
		return rmse;
	}

	/**
	 * Realiza el cálculo del Mean Absolute Error
	 * @param totalItems
	 * @param realUserRatingVector
	 * @param predictedUserRatingVector
	 * @return
	 */
	private double calculateMAE(final int totalItems, final double[] realUserRatingVector, final double[] predictedUserRatingVector) {
		final double distance = ManhattanDistanceMeasure.distance(realUserRatingVector, predictedUserRatingVector);
		double mae = distance / totalItems;

		String val = mae + "";
		BigDecimal big = new BigDecimal(val);
		mae = big.setScale(5, RoundingMode.HALF_UP).doubleValue();
		return mae;
	}
}
