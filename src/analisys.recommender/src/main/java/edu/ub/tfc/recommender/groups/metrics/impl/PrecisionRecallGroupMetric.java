package edu.ub.tfc.recommender.groups.metrics.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import edu.ub.tfc.recommender.bean.GroupEvaluation;
import edu.ub.tfc.recommender.groups.metrics.GroupMetric;
import edu.ub.tfc.recommender.utils.Utils;

/**
 * Each user has their own ratings (reality) and the group presents the retrieved items (prediction)
 * @author sisux
 *
 */
public class PrecisionRecallGroupMetric implements GroupMetric {

	/* ****************************
				CONSTANTS
	 * ************************** */
	
	private static final float GOOD_RECOMMENDATION_THRESHOLD = (5/2);
	
	private static final int REALITY_IDX_ACTUALLY_GOOD = 0;
	private static final int REALITY_IDX_ACTUALLY_BAD = 1;
	
	private static final int PREDICTION_IDX_RATED_GOOD = 0;
	private static final int PREDICTION_IDX_RATED_BAD = 1;
	
	/* ****************************
			CLASS ATTRIBUTES
	 * ************************** */

	private static Logger logger = Logger.getLogger(PrecisionRecallGroupMetric.class);	

	/* ****************************
			PUBLIC METHODS
	* *************************** */
	 
	//Calcula las métricas entre la valoración real del usuario y la estimación obtenida por el recomendador
	@Override
	public Map<String, String> calculate(Map<Long, Float> theGroupEstimation, Map<Long, Map<Long, Float>> theUsersEstimations) {
		List<PrecisionAndRecall> tmpUsersPrecisionAndRecall = new ArrayList<PrecisionAndRecall>();
        Map<Long,Float> tmpSortedUserEstimation;

        Map<Long, Float> tmpSortedGroupEstimation = Utils.sortMapByValue(theGroupEstimation);
		//per each user estimation
		for(Map<Long, Float> tmpUserEstimation : theUsersEstimations.values()) {
			tmpSortedUserEstimation = Utils.sortMapByValue(tmpUserEstimation);
	        tmpUsersPrecisionAndRecall.add(calculateUserPrecisionAndRecall(tmpSortedUserEstimation, tmpSortedGroupEstimation));
		}
		return calculateGroupPrecisionAndRecallMetrics(tmpUsersPrecisionAndRecall);
	}

	/* ****************************
			PRIVATE METHODS
	* *************************** */

	/**
	 * Calculates the group metrics as the average of the user ones
	 * @param theUsersPrecisionAndRecall
	 * @return
	 */
	private Map<String, String> calculateGroupPrecisionAndRecallMetrics(
			List<PrecisionAndRecall> theUsersPrecisionAndRecall) {
		
		Map<String, String> tmpResult = new HashMap<String,String>();
		
		float[] tmpUsersPrecision = new float[theUsersPrecisionAndRecall.size()];
		float[] tmpUsersRecall = new float[theUsersPrecisionAndRecall.size()];
		
		int i = 0;
		for(PrecisionAndRecall tmpUserPrecisionAndRecall : theUsersPrecisionAndRecall)  {
			tmpUsersPrecision[i] = tmpUserPrecisionAndRecall._precision;
			tmpUsersRecall[i] = tmpUserPrecisionAndRecall._recall;
			i++;
		}
		
		//calculates average for both
		float tmpGroupPrecision = Utils.computeAverage(tmpUsersPrecision);
		float tmpGroupRecall = Utils.computeAverage(tmpUsersRecall);

		logger.info("Group precision: " + tmpGroupPrecision);
		logger.info("Group recall: " + tmpGroupRecall);
		
		tmpResult.put(GroupEvaluation.PRECISION_METRIC_NAME, String.valueOf(tmpGroupPrecision));
		tmpResult.put(GroupEvaluation.RECALL_METRIC_NAME, String.valueOf(tmpGroupRecall));

		return tmpResult;
	}
	
	/**
	 * Calculates both metrics <precision, recall>
	 * @param theReality
	 * @param thePrediction
	 * @return
	 */
	private PrecisionAndRecall calculateUserPrecisionAndRecall(
			Map<Long, Float> theReality,
			Map<Long, Float> thePrediction) {
		
		int[][] tmpClassificationMatrix = new int[2][2];
		int tmpPredictionIndex;
		int tmpRealityIndex;
		
		for(Entry<Long, Float> tmpEntry : thePrediction.entrySet()) {
			tmpRealityIndex = (theReality.get(tmpEntry.getKey())>= GOOD_RECOMMENDATION_THRESHOLD)? REALITY_IDX_ACTUALLY_GOOD : REALITY_IDX_ACTUALLY_BAD;
			tmpPredictionIndex = (tmpEntry.getValue() >= GOOD_RECOMMENDATION_THRESHOLD)? PREDICTION_IDX_RATED_GOOD : PREDICTION_IDX_RATED_BAD;
			tmpClassificationMatrix[tmpRealityIndex][tmpPredictionIndex]++;
		}
		
		return new PrecisionAndRecall(tmpClassificationMatrix);
	}

	/**
	 * 
	 * @author sisux
	 *
	 */
	private class PrecisionAndRecall {
		float _precision;
		float _recall;
		
		/**
		 * True_Positive  {Reality = Actually Good, Prediction = Rated Good}
		 * False_Positive {Reality = Actually Bad, Prediction = Rated Good}
		 * True_Negative  {Reality = Actually Bad, Prediction = Rated Bad}
		 * False_Negative {Reality = Actually Good, Prediction = Rated Bad}
		 * @param theClassificationMatrix
		 */
		PrecisionAndRecall(int[][] theClassificationMatrix) {
			int tmpTruePositive = theClassificationMatrix[REALITY_IDX_ACTUALLY_GOOD][PREDICTION_IDX_RATED_GOOD];
			int tmpFalsePositive = theClassificationMatrix[REALITY_IDX_ACTUALLY_BAD][PREDICTION_IDX_RATED_GOOD];
			int tmpFalseNegative = theClassificationMatrix[REALITY_IDX_ACTUALLY_GOOD][PREDICTION_IDX_RATED_BAD];
			
			setPrecision(tmpTruePositive, tmpFalsePositive);
			setRecall(tmpTruePositive, tmpFalseNegative);
		}
		
		private void setPrecision(float theTruePositive, float theFalsePositive) {
			_precision = (theTruePositive / (theTruePositive + theFalsePositive)); 
		}
		
		private void setRecall(float theTruePositive, float theFalseNegative) {
			_recall = (theTruePositive / (theTruePositive + theFalseNegative));
		}
	}

}
