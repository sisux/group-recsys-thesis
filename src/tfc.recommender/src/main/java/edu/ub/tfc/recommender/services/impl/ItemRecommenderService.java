/**
 * 
 */
package edu.ub.tfc.recommender.services.impl;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import edu.ub.tfc.recommender.services.RecommenderInterface;

/**
 * Controlador que conecta el servlet con el recomendador item-based.
 * @author David Gil
 */
public class ItemRecommenderService extends RecommenderAbstract implements RecommenderInterface {

	private Long userID;
	private Integer howMany;

	public ItemRecommenderService() {

	}

	/**
	 * @see edu.ub.tfc.recommender.services.impl.RecommenderAbstract#recommend()
	 */
	@Override
	public List<RecommendedItem> recommend() throws TasteException {
		return recommender.recommend(userID, howMany);
	}

	/**
	 * @param userID ID del usuario
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * @param howMany Nœmero de ’tems
	 */
	public void setHowMany(Integer howMany) {
		this.howMany = howMany;
	}

}
