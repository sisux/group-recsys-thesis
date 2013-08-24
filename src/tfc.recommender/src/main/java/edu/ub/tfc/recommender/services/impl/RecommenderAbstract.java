package edu.ub.tfc.recommender.services.impl;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import edu.ub.tfc.recommender.services.RecommenderInterface;

/**
 * Clase abstracta de los recomendadores
 * @author David Gil De Arce
 */
public abstract class RecommenderAbstract implements RecommenderInterface {

	protected Recommender recommender;

	/**
	 * @see edu.ub.tfc.recommender.services.RecommenderInterface#recommend()
	 */
	@Override
	public abstract List<RecommendedItem> recommend() throws TasteException;

	/**
	 * Guarda un recomendador
	 * @param recommender Recomendador
	 */
	public void setRecommender(final Recommender recommender) {
		this.recommender = recommender;
	}

	/**
	 * @see edu.ub.tfc.recommender.services.RecommenderInterface#getRecommender()
	 */
	@Override
	public Recommender getRecommender() {
		return this.recommender;
	}



}
