package edu.ub.tfc.recommender.services;

import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.Recommender;

/**
 * Interfaz del servicio de recommendador
 * @author David Gil De Arce
 */
public interface RecommenderService {
	/**
	 * Devuelve el recomendador
	 * @return Recomendador
	 */
	Recommender getRecommender();

	/**
	 * Calcula las estimaciones de valoraciones para una lista de items y un usuario
	 * @param userID ID del usuario
	 * @param itemsID Lista de IDs de ’tems
	 * @return Estimaciones
	 * @throws TasteException Fallo del recomendador
	 */
	Map<Long, Float> evaluate(final Long userID, final List<Long> itemsID) throws TasteException;
}
