package edu.ub.tfc.recommender.bean;

import java.util.Map;

public class GroupEvaluation extends Evaluacion {

	/* ****************************
			CONSTANTS
	* *************************** */	
	
	public static final String GROUP_ID = "id";
	public static final String GROUP_DESCRIPTION = "kind";
	
	public static final String RECOMMENDATION_SERVICE = "service";
	public static final String ELICITATION_STRATEGY = "strategy";
	public static final String NUM_OF_ITEMS_TO_RECOMMEND = "itemstorec";
	
	public static final String MAE_METRIC_NAME = "mae";
	public static final String RMSE_METRIC_NAME = "rmse";
	
	public static final String AVG_METRIC_NAME = "avg";
	public static final String STDEV_METRIC_NAME = "cstdev";
	
	/* ****************************
			ATTRIBUTES
	* *************************** */
	
	private Map<String,String> metrics;

	/* ****************************
			ACCESSORS
	* *************************** */
	
	/**
	 * @return the metrics
	 */
	public Map<String, String> getMetrics() {
		return metrics;
	}

	/**
	 * @param metrics the metrics to set
	 */
	public void setMetrics(Map<String, String> metrics) {
		this.metrics = metrics;
	}
	
	/* ****************************
			CONSTRUCTORS
	* *************************** */	
	
	public GroupEvaluation(long time, Map<Long, Float> evaluacion) {
		super(time, evaluacion);
	}
	
	
	/* ****************************
	  	 PUBLIC STATIC METHODS
	* *************************** */		
	
	public static String toStringHeader() {
		String tmpResult = GROUP_ID;
		tmpResult += ";" + GROUP_DESCRIPTION;
		tmpResult += ";" + RECOMMENDATION_SERVICE;
		tmpResult += ";" + ELICITATION_STRATEGY;
		tmpResult += ";" + NUM_OF_ITEMS_TO_RECOMMEND;
		tmpResult += ";" + MAE_METRIC_NAME;
		tmpResult += ";" + RMSE_METRIC_NAME;
		tmpResult += ";" + AVG_METRIC_NAME;
		tmpResult += ";" + STDEV_METRIC_NAME;
		
		return tmpResult;
	}
	
	
	/* ****************************
			PUBLIC METHODS
	* *************************** */	
	
	public void addNewMetric(String theKey, String theValue) {
		this.metrics.put(theKey, theValue);
	}

	@Override
	public String toString() {
		
		String tmpResult = getItemValue(GROUP_ID);
		tmpResult += ";" + getItemValue(GROUP_DESCRIPTION);
		tmpResult += ";" + getItemValue(RECOMMENDATION_SERVICE);
		tmpResult += ";" + getItemValue(ELICITATION_STRATEGY);
		tmpResult += ";" + getItemValue(NUM_OF_ITEMS_TO_RECOMMEND);
		tmpResult += ";" + getItemValue(MAE_METRIC_NAME);
		tmpResult += ";" + getItemValue(RMSE_METRIC_NAME);
		tmpResult += ";" + getItemValue(AVG_METRIC_NAME);
		tmpResult += ";" + getItemValue(STDEV_METRIC_NAME);		
		
		return tmpResult;
	}

	
	/**
	 * 
	 * @param theKey
	 * @return
	 */
	public String getItemValue(String theKey) {
		String tmpValue = this.metrics.get(theKey);
		return (tmpValue == null)? "" : tmpValue;
	}
}
