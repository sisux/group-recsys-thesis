package edu.ub.tfc.recommender.groups;

import java.util.Map;

public interface ElicitationStrategy {

	/**
	 * Return the group estimations from a set of individual user estimations
	 * @param theUsersEstimations (idUser - {idItem, itemEstimation})
	 * 
	 */
	Map<Long, Float> evaluate(Map<Long, Map<Long, Float>> theUsersEstimations);
}
