package edu.ub.tfc.recommender.groups.strategies.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.ub.tfc.recommender.groups.strategies.ElicitationStrategy;

public class MultiplicativeElicitationStrategy implements ElicitationStrategy {

	/* ****************************
		CLASS ATTRIBUTES
	* *************************** */

	private static Logger logger = Logger.getLogger(MultiplicativeElicitationStrategy.class);
	
	/* ****************************
			CONSTANTS
	* *************************** */
	
	private final static double MAX_RATING_VALUE = 5;
	private final static float NON_PENALIZING_RATING_VALUE = 0.2f; //(1/5)
	private final static String ELICITATION_STRATEGY_NAME = "Multiplicative ES";
	
	/* ****************************
		PUBLIC METHODS
	* *************************** */
	
	@Override
	public Map<Long, Float> evaluate(Map<Long, Map<Long, Float>> theUsersEstimations) {
	Map<Long, Float> tmpResult = null;
	Boolean isFirst = true;
	
	for(Map<Long, Float> tmpItemEstimation : theUsersEstimations.values()) {
		if(isFirst) {
			tmpResult = new HashMap<Long, Float>(tmpItemEstimation);
			isFirst = false;
		} else {
			multItemsEstimations(tmpResult, tmpItemEstimation);	
		}
	}
	logger.info("Users Estimations:" + theUsersEstimations.toString());
	logger.info("Group Estimation Before Fit:" + tmpResult.toString());
	
	fitIntoRatingRange(tmpResult, theUsersEstimations.size());
	
	logger.info("Group Estimation After Fit:" + tmpResult.toString());
	
	return tmpResult;
	}
	
	
	@Override
	public String getElicitationStrategyName() {
		return ELICITATION_STRATEGY_NAME;
	}
	
	/* ****************************
		PRIVATE METHODS
	* *************************** */
	
	/**
	* 
	* @param theBase
	* @param theEstimationToAdd
	*/
	private void multItemsEstimations(Map<Long, Float> theBase,
		Map<Long, Float> theEstimationToAdd) {
	
	Float tmpCurrentValue;
		for(Long tmpCurrentItemId : theBase.keySet()) {
			tmpCurrentValue = getNonPenalizingZero(theBase.get(tmpCurrentItemId));
			tmpCurrentValue *= getNonPenalizingZero(theEstimationToAdd.get(tmpCurrentItemId));
			theBase.put(tmpCurrentItemId, tmpCurrentValue);
		}
	}
	
	/**
	 * 
	 * @param theValue
	 * @return
	 */
	private Float getNonPenalizingZero(Float theValue) {
		if(theValue==null || theValue == 0)
			return NON_PENALIZING_RATING_VALUE;
	return theValue;
	}
	
	/**
	* 
	* @param theBase
	*/
	private void fitIntoRatingRange(Map<Long, Float> theBase, int theNumOfUsersInGroup) {
	Float tmpCurrentValue;
	Float tmpMaxScale = (float) Math.pow(MAX_RATING_VALUE, theNumOfUsersInGroup - 1);
	
		for(Long tmpCurrentItemId : theBase.keySet()) {
			tmpCurrentValue = theBase.get(tmpCurrentItemId);
			tmpCurrentValue = (float) (tmpCurrentValue / tmpMaxScale);
			theBase.put(tmpCurrentItemId, tmpCurrentValue);
		}
	}
}