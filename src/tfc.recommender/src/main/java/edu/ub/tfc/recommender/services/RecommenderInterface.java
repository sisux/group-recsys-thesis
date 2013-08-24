package edu.ub.tfc.recommender.services;

import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

/**
 * Interfaz para los recomendadores
 * @author David Gil De Arce
 */
public interface RecommenderInterface {
	
	/**
	 * Devuelve el resultado de la evaluaci—n de la recomendaci—n
	 * @return Lista de los ’tems recomendados
	 * @throws TasteException Lanza una excepci—n cuando falla el recomendador
	 */
	List<RecommendedItem> recommend() throws TasteException;

	/**
	 * Establece el ID de un usuario
	 * @param userID ID del usuario
	 */
	void setUserID(Long userID);

	/**
	 * Establece el nœmero de ’tems que se recomendar‡n
	 * @param userID Nœmero de ’tems
	 */
	void setHowMany(Integer howMany);

	/**
	 * Devuelve un recomendador
	 * @returns Un recomendador
	 */
	Recommender getRecommender();
}
