package edu.ub.tfc.recommender.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;

import edu.ub.tfc.recommender.bean.Evaluacion;
import edu.ub.tfc.recommender.services.RecommenderService;

public abstract class AbstractRecommenderServiceImpl implements RecommenderService {
			
		/* ****************************
				ATTRIBUTES
		* *************************** */
		
		protected Map<String,String> _metricResults = new HashMap<String,String>();
		private long _recommenderUnits;
		
		/* ****************************
				ACCESSORS
		* *************************** */	
		
		/**
		 * Return the current metric results
		 * @return
		 */
		public Map<String,String> getMetricResults() {
			return _metricResults;
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
			
			this._metricResults.clear();
			long timeStart = System.currentTimeMillis();

			Map<Long, Float> tmpResult = evaluateRecommender(userID, itemsID);
			
			long timeFinish = System.currentTimeMillis();
			long time = (timeFinish - timeStart) / getRecommenderUnits();
			this._metricResults.put(Evaluacion.UNITARY_RECOMMENDATION_TIME, String.valueOf(time));
			
			return tmpResult;
		}

		/* ****************************
				PROTECTED METHODS
		* *************************** */
		
		protected abstract Map<Long, Float> evaluateRecommender(Long userID, List<Long> itemsID) throws TasteException;
		
		protected void setRecommenderUnits(long theRecommendedUnits) {
			this._recommenderUnits = theRecommendedUnits;
		}

		protected long getRecommenderUnits() {
			return this._recommenderUnits;
		}

	}
