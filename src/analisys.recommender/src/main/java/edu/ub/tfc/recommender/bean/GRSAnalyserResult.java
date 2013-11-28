package edu.ub.tfc.recommender.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GRSAnalyserResult {
	
	private Map<Long, List<GroupEvaluation>> _result;
	
	public GRSAnalyserResult() {
		_result = new HashMap<Long, List<GroupEvaluation>>();
	}

	/**
	 * 
	 * @param theGroupId
	 * @param theGroupEvaluationResult
	 */
	public void addResult(Long theGroupId, GroupEvaluation theGroupEvaluationResult) {
		List<GroupEvaluation> tmpListOfResults = _result.get(theGroupId);
		if(tmpListOfResults==null) {
			tmpListOfResults = new ArrayList<GroupEvaluation>();
			tmpListOfResults.add(theGroupEvaluationResult);
			_result.put(theGroupId, tmpListOfResults);
		} else {
			tmpListOfResults.add(theGroupEvaluationResult);
		}
	}
	
	/**
	 * 
	 * @param theGroupId
	 * @return
	 */
	public List<GroupEvaluation> getAllGroupEvaluationsByGroupId(Long theGroupId) {
		return _result.get(theGroupId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Long> getAllGroupsId() {
		return new ArrayList<Long>(_result.keySet());
	}
	
}