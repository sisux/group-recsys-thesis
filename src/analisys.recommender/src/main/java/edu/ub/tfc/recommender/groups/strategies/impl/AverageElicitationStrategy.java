package edu.ub.tfc.recommender.groups.strategies.impl;

import java.util.HashMap;
import java.util.Map;

import edu.ub.tfc.recommender.groups.strategies.ElicitationStrategy;

public class AverageElicitationStrategy implements ElicitationStrategy {

	/* ****************************
			CONSTANTS
	* *************************** */
	
	private final static String ELICITATION_STRATEGY_NAME = "Average ES";
	
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
				sumItemsEstimations(tmpResult, tmpItemEstimation);	
			}
		}
		avgItemsEstimations(tmpResult, theUsersEstimations.size());

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
}
