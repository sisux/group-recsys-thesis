package edu.ub.tfc.recommender.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;

import edu.ub.tfc.recommender.bean.GroupEvaluation;
import edu.ub.tfc.recommender.bean.UserGroup;
import edu.ub.tfc.recommender.dao.UserGroupDAO;
import edu.ub.tfc.recommender.groups.ElicitationStrategy;
import edu.ub.tfc.recommender.groups.GroupMetric;
import edu.ub.tfc.recommender.services.RecommenderService;

public class GroupRecommenderService extends AbstractRecommenderServiceImpl {

	/* ****************************
			ATTRIBUTES
	* *************************** */
	
	private RecommenderService _baseRecommenderService;
	private ElicitationStrategy _strategy;
	private List<GroupMetric> _groupMetricList;
	
	/* ****************************
			ACCESSORS
	* *************************** */	
	
	/**
	 * Return the group metric list
	 * @return
	 */
	public List<GroupMetric> getGroupMetricList() {
		return _groupMetricList;
	}
	
	/**
	 * 
	 * @param theGroupMetricList
	 */
	public void setGroupMetricList(List<GroupMetric> theGroupMetricList) {
		_groupMetricList = theGroupMetricList;
	}

	/**
	 * @return the _recommender
	 */
	public RecommenderService getBaseRecommenderService() {
		return _baseRecommenderService;
	}

	/**
	 * @return the _strategy
	 */
	public ElicitationStrategy getStrategy() {
		return _strategy;
	}

	/**
	 * @param _baseRecommenderService the _recommender to set
	 */
	public void setBaseRecommenderService(RecommenderService theRecommenderService) {
		this._baseRecommenderService = theRecommenderService;
	}

	/**
	 * @param _strategy the _strategy to set
	 */
	public void setStrategy(ElicitationStrategy theStrategy) {
		this._strategy = theStrategy;
	}

	/* ****************************
			PRIVATE METHODS
	* *************************** */

	private void setBaselineGroupInfo(UserGroup theUserGroup, int theItemsNumToRecommend) {
		this._metricResults.put(GroupEvaluation.GROUP_ID, theUserGroup.get_id().toString());
		this._metricResults.put(GroupEvaluation.GROUP_DESCRIPTION, theUserGroup.get_description());
		this._metricResults.put(GroupEvaluation.ELICITATION_STRATEGY, this._strategy.getElicitationStrategyName());
		this._metricResults.put(GroupEvaluation.NUM_OF_ITEMS_TO_RECOMMEND, String.valueOf(theItemsNumToRecommend));
	}

	/**
	 * All metrics are evaluated and the result is 
	 * stored in GroupMetricsResults
	 */
	private void evaluateAllGroupMetrics(Map<Long, Float> theGroupEstimation, Map<Long, Map<Long, Float>> theUsersEstimations ) {
		Map<String, String> tmpMetricResult;
		for(GroupMetric tmpGroupMetric : this._groupMetricList) {
			tmpMetricResult = tmpGroupMetric.calculate(theGroupEstimation, theUsersEstimations);
			this._metricResults.putAll(tmpMetricResult);
		}
	}
	
	/**
	 * For each user, get their estimations
	 * @param theUserGroup
	 * @param theItemsID
	 * @return
	 * @throws TasteException
	 */
	private Map<Long, Map<Long, Float>> getAllUserEstimations(
			UserGroup theUserGroup, List<Long> theItemsID) throws TasteException {

		Map<Long, Map<Long, Float>> tmpUsersEstimations = new HashMap<Long, Map<Long, Float>>();
		for(Long tmpUserId : theUserGroup.get_userIds()) {
			tmpUsersEstimations.put(tmpUserId, _baseRecommenderService.evaluate(tmpUserId, theItemsID));
		}
		return tmpUsersEstimations;
	}

	/* ****************************
			PROTECTED METHODS
	 * *************************** */
	
	/**
	 * Calcula las estimaciones de valoraciones para una lista de items y un grupo de usuarios
	 * @param userID ID del grupo de usuarios
	 * @param itemsID Lista de IDs de items a evaluar
	 * @return Estimaciones
	 * @throws TasteException Fallo del recomendador
	 */
	@Override
	protected Map<Long, Float> evaluateRecommender(Long userID,
			List<Long> itemsID) throws TasteException {
		UserGroupDAO tmpUserGroupDAO = new UserGroupDAO();
		
		// 1. Obtenir el llistat d'usuaris a partir del groupId
		UserGroup tmpUserGroup = tmpUserGroupDAO.findGroupById(userID);
		
		//   1.1. Add basic info to results
		this.setBaselineGroupInfo(tmpUserGroup, itemsID.size());
		
		// 2. Per each single user, perform its recommendation
		Map<Long, Map<Long, Float>> tmpUsersEstimations = getAllUserEstimations(tmpUserGroup, itemsID);

		// 3. En funció de la ELICITATION_STRATEGY, obtenir el llistat de recomenacions grupals
		Map<Long, Float> tmpResult = _strategy.evaluate(tmpUsersEstimations);
		super.setRecommenderUnits(tmpUserGroup.get_userIds().size() * itemsID.size());
		
		// 4. Executar totes les metriques associades
		evaluateAllGroupMetrics(tmpResult, tmpUsersEstimations); 
		 
	return tmpResult;
	}
}
