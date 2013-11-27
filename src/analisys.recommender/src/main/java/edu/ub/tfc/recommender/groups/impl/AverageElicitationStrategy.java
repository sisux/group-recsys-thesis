package edu.ub.tfc.recommender.groups.impl;

import java.util.Map;

import edu.ub.tfc.recommender.groups.ElicitationStrategy;

public class AverageElicitationStrategy implements ElicitationStrategy {

	@Override
	public Map<Long, Float> evaluate(
			Map<Long, Map<Long, Float>> theUsersEstimations) {
		return null;
	}

}
