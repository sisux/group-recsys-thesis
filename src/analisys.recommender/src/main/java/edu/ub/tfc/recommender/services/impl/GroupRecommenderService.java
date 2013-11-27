package edu.ub.tfc.recommender.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;

import edu.ub.tfc.recommender.bean.UserGroup;
import edu.ub.tfc.recommender.dao.UserGroupDAO;
import edu.ub.tfc.recommender.groups.ElicitationStrategy;
import edu.ub.tfc.recommender.services.RecommenderService;

public class GroupRecommenderService implements RecommenderService {


	/* ****************************
			ATTRIBUTES
	* *************************** */
	
	private RecommenderService _baseRecommenderService;
	private ElicitationStrategy _strategy;

	/* ****************************
			ACCESSORS
	* *************************** */	
	
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
			PUBLIC METHODS
	* *************************** */
	
	/**
	 * Calcula las estimaciones de valoraciones para una lista de items y un grupo de usuarios
	 * @param userID ID del grupo de usuarios
	 * @param itemsID Lista de IDs de items a evaluar
	 * @return Estimaciones
	 * @throws TasteException Fallo del recomendador
	 */
	@Override
	public Map<Long, Float> evaluate(Long userID, List<Long> itemsID)
			throws TasteException {

		UserGroupDAO tmpUserGroupDAO = new UserGroupDAO();
		
		// 1. Obtenir el llistat d'usuaris a partir del groupId
		UserGroup tmpUserGroup = tmpUserGroupDAO.findGroupById(userID);
		
		// 2. Per each single user, perform its recommendation
		Map<Long, Map<Long, Float>> tmpUsersEstimations = getAllUserEstimations(tmpUserGroup, itemsID);
				
		// 3. En funció de la ELICITATION_STRATEGY, obtenir el llistat de recomenacions
		return _strategy.evaluate(tmpUsersEstimations);
	}


	/* ****************************
			PRIVATE METHODS
	* *************************** */
	
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
}
