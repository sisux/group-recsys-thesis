/**
 * 
 */
package edu.ub.tfc.recommender.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.Recommender;

import edu.ub.tfc.recommender.services.RecommenderService;

/**
 * Implementacion del servicio de recomendacion
 * @author David Gil
 */
public class RecommenderServiceImpl extends AbstractRecommenderServiceImpl {
	
	protected Recommender recommender;

	/**
	 * Guarda un recomendador
	 * @param recommender Recomendador a guardar
	 */
	public void setRecommender(final Recommender recommender) {
		this.recommender = recommender;
	}

	/**
	 * @see edu.ub.tfc.recommender.services.RecommenderInterface#getRecommender()
	 */
	public Recommender getRecommender() {
		return this.recommender;
	}

	@Override
	protected Map<Long, Float> evaluateRecommender(Long userID,
			List<Long> itemsID) throws TasteException {
		final Map<Long, Float> result = new HashMap<Long, Float>();

		this.setRecommenderUnits(itemsID.size());
		
		for (final Long itemID : itemsID) {
			try {
				final Float estimacion = this.recommender.estimatePreference(userID, itemID);
				if (estimacion == null || estimacion.isNaN()) {
					result.put(itemID, 0.f);
				} else{
					result.put(itemID, estimacion);
				}
			} catch (final TasteException te) {
				result.put(itemID, 0.f);
			}
		}
		return result;
	}

}
