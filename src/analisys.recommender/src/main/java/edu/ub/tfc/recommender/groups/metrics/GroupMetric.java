package edu.ub.tfc.recommender.groups.metrics;

import java.util.Map;

public interface GroupMetric {

	/**
	 * Return a Map<MetricName, MetricValue>
	 * @return
	 */
	Map<String, String> calculate(Map<Long, Float> theGroupEstimation, Map<Long, Map<Long, Float>> theUsersEstimations);
}
