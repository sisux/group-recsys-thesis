package edu.ub.tfc.recommender.bean;

import java.util.Map;

public class GroupEvaluation extends Evaluacion {

	/* ****************************
			CONSTANTS
	* *************************** */	
	
	public static final String GROUP_ID = "GROUP ID";
	public static final String GROUP_DESCRIPTION = "GROUP DESCRIPTION";
	
	public static final String RECOMMENDATION_SERVICE = "REC SERVICE";
	public static final String ELICITATION_STRATEGY = "ELICITATION STRATEGY";
	public static final String NUM_OF_ITEMS_TO_RECOMMEND = "NUM OF ITEMS TO RECOMMEND";
	
	public static final String MAE_METRIC_NAME = "MAE";
	public static final String RMSE_METRIC_NAME = "RMSE";
	
	
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
		tmpResult += ";" + getItemValue(MAE_METRIC_NAME);
		tmpResult += ";" + getItemValue(RMSE_METRIC_NAME);

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
