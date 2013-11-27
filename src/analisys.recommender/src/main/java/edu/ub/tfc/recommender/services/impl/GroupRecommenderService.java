package edu.ub.tfc.recommender.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;

import edu.ub.tfc.recommender.groups.ElicitationStrategy;
import edu.ub.tfc.recommender.services.RecommenderService;

public class GroupRecommenderService implements RecommenderService {

	private RecommenderService _baseRecommenderService;
	private ElicitationStrategy _strategy;
	
	/**
	 * @return the _recommender
	 */
	public RecommenderService getRecommenderService() {
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
	public void setRecommenderService(RecommenderService theRecommenderService) {
		this._baseRecommenderService = theRecommenderService;
	}

	/**
	 * @param _strategy the _strategy to set
	 */
	public void set_strategy(ElicitationStrategy theStrategy) {
		this._strategy = theStrategy;
	}

	/**
	 * Calcula las estimaciones de valoraciones para una lista de items y un grupo de usuarios
	 * @param userID ID del grupo de usuarios
	 * @param itemsID Lista de IDs de ’tems
	 * @return Estimaciones
	 * @throws TasteException Fallo del recomendador
	 */
	@Override
	public Map<Long, Float> evaluate(Long userID, List<Long> itemsID)
			throws TasteException {
		Map<Long, Map<Long, Float>> tmpUsersEstimations = new HashMap<Long, Map<Long, Float>>();
		
		// 1. Obtenir el llistat d'usuaris a partir del groupId
		List<Long> tmpGroupUsers = null;
		
		// 2. Per cada usuari, obtenir-ne les estimacions
		for(Long tmpUserId : tmpGroupUsers) {
			tmpUsersEstimations.put(tmpUserId, _baseRecommenderService.evaluate(tmpUserId, itemsID));
		}
		
		// 3. En funció de la ELICITATION_STRATEGY, obtenir el llistat de recomenacions
		return _strategy.evaluate(tmpUsersEstimations);
	}

	
}
