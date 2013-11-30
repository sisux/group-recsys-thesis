package edu.ub.tfc.recommender.groups.strategies;

import java.util.Map;

public interface ElicitationStrategy {

	/**
	 * Return the group estimations from a set of individual user estimations
	 * @param theUsersEstimations (idUser - {idItem, itemEstimation})
	 * 
	 */
	Map<Long, Float> evaluate(Map<Long, Map<Long, Float>> theUsersEstimations);

	/**
	 * Return the Name of the Elicitation Strategy
	 * @return
	 */
	String getElicitationStrategyName();
}
