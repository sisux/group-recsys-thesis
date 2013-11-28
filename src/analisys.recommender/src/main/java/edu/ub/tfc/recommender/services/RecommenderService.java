package edu.ub.tfc.recommender.services;

import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;

/**
 * Interfaz del servicio de recommendador
 * @author David Gil De Arce
 */
public interface RecommenderService {

	/**
	 * Calcula las estimaciones de valoraciones para una lista de items y un usuario
	 * @param userID ID del usuario
	 * @param itemsID Lista de IDs de ’tems
	 * @return Estimaciones
	 * @throws TasteException Fallo del recomendador
	 */
	Map<Long, Float> evaluate(final Long userID, final List<Long> itemsID) throws TasteException;
	
	/**
	 * Return internal metrics of the recommender service
	 * @return
	 */
	Map<String,String> getMetricResults();
}
