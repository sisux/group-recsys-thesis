package edu.ub.tfc.recommender.groups.metrics.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import edu.ub.tfc.recommender.bean.GroupEvaluation;
import edu.ub.tfc.recommender.groups.metrics.GroupMetric;
import edu.ub.tfc.recommender.utils.Utils;

/**
 * Discounted Cumulative Gain
 * @author sisux
 *
 */
public class DcgGroupMetric implements GroupMetric {

	/* ****************************
			CLASS ATTRIBUTES
	 * ************************** */

	private static Logger logger = Logger.getLogger(DcgGroupMetric.class);	

	/* ****************************
			PUBLIC METHODS
	* *************************** */
	 
	//Calcula las métricas entre la valoración real del usuario y la estimación obtenida por el recomendador
	@Override
	public Map<String, String> calculate(Map<Long, Float> theGroupEstimation, Map<Long, Map<Long, Float>> theUsersEstimations) {
		float tmpIdealDCG;
		float tmpWorstDCG;
		float tmpDCGRangeLength;
		float tmpCurrentDCG;
		float tmpCurrentSatisfaction;
		List<Float> tmpUserSatisfaction = new ArrayList<Float>();
        Map<Long,Float> tmpSortedUserEstimation;

        Map<Long, Float> tmpSortedGroupEstimation = sortMapByValue(theGroupEstimation);
		//per each user estimation
		for(Map<Long, Float> tmpUserEstimation : theUsersEstimations.values()) {
			tmpSortedUserEstimation = sortMapByValue(tmpUserEstimation);
	        
			tmpIdealDCG = getIdealDCG(tmpSortedUserEstimation);
			tmpWorstDCG= getWorstDCG(tmpSortedUserEstimation);
			tmpDCGRangeLength = tmpIdealDCG - tmpWorstDCG;
			tmpCurrentDCG = getDCG(tmpSortedUserEstimation, tmpSortedGroupEstimation.keySet());
			tmpCurrentSatisfaction = (tmpCurrentDCG - tmpWorstDCG) / tmpDCGRangeLength;
			tmpUserSatisfaction.add(tmpCurrentSatisfaction);
			
			logger.info("Current user satisfaction: " + tmpCurrentSatisfaction);
		}
		
		return calculateSatisfactionMetrics(tmpUserSatisfaction);
	}

	/* ****************************
			PRIVATE METHODS
	* *************************** */

	/**
	 * 
	 * @param theUserSatisfaction
	 */
	private Map<String, String> calculateSatisfactionMetrics(List<Float> theUserSatisfaction) {
		Map<String, String> tmpResult = new HashMap<String,String>();
		
		Float[] tmpSatisfactionsObj = theUserSatisfaction.toArray(new Float[theUserSatisfaction.size()]);
		float[] tmpSatisfactionPrim = ArrayUtils.toPrimitive(tmpSatisfactionsObj, Float.NaN);
		
		//calculates Average
		float tmpAverage = Utils.computeAverage(tmpSatisfactionPrim);
		
		//calculates STDEV
		float tmpStdev = Utils.correctedSampleStdDev(tmpSatisfactionPrim, tmpAverage);

		logger.info("Group avg satisfaction: " + tmpAverage);
		logger.info("Group stdev satisfaction: " + tmpStdev);
		
		tmpResult.put(GroupEvaluation.AVG_METRIC_NAME, String.valueOf(tmpAverage));
		tmpResult.put(GroupEvaluation.STDEV_METRIC_NAME, String.valueOf(tmpStdev));

		return tmpResult;
	}

	/**
	 * Calculates the DGC of theSortedItemList using the values
	 *  in theSortedEstimation
	 * @param theSortedEstimation
	 * @param theSortedItemList
	 * @return
	 */
	private float getDCG(Map<Long, Float> theSortedEstimation,
			Set<Long> theSortedItemList) {
		float tmpResult = 0f;
		Float tmpItemValue;
		int i = 1;
		for(Long tmpCurrentItem : theSortedItemList) {
			tmpItemValue = theSortedEstimation.get(tmpCurrentItem);
			if(i == 1) {
				tmpResult += tmpItemValue.floatValue();
			} else {
				tmpResult += (tmpItemValue.floatValue() / Utils.log(i, 2));
			}
			i++;
		}
		
		return tmpResult;
	}

	/**
	 * 
	 * @param theSortedUserEstimation
	 * @return
	 */
	private float getWorstDCG(Map<Long, Float> theSortedEstimation) {
		float tmpResult = 0f;
		Float tmpItemValue;
		int i = theSortedEstimation.size();
		for(Map.Entry<Long, Float> tmpCurrentItemEntry : theSortedEstimation.entrySet()) {
			tmpItemValue = tmpCurrentItemEntry.getValue();
			if(i == 1) {
				tmpResult += tmpItemValue.floatValue();
			} else {
				tmpResult += (tmpItemValue.floatValue() / Utils.log(i, 2));
			}
			i--;
		}
		return tmpResult;
	}

	/**
	 * 
	 * @param tmpSortedUserEstimation
	 * @return
	 */
	private float getIdealDCG(Map<Long, Float> theSortedEstimation) {
		float tmpResult = 0f;
		Float tmpItemValue;
		int i = 1;
		for(Map.Entry<Long, Float> tmpCurrentItemEntry : theSortedEstimation.entrySet()) {
			tmpItemValue = tmpCurrentItemEntry.getValue();
			if(i == 1) {
				tmpResult += tmpItemValue.floatValue();
			} else {
				tmpResult += (tmpItemValue.floatValue() / Utils.log(i, 2));
			}
			i++;
		}
		return tmpResult;
	}
	
	/**
	 * Sort Map by its values
	 * @param map
	 * @return
	 */
    private static Map<Long, Float> sortMapByValue(Map<Long, Float> map) {
        List<Map.Entry<Long,Float>> list = new LinkedList<Entry<Long, Float>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Long,Float>>() {

			@Override
			public int compare(Map.Entry<Long, Float> o1, Map.Entry<Long, Float> o2) {
				
				if (o1.getValue() <= o2.getValue()) {
			        return 1;
			    } else {
			        return -1;
			    }
			}
        });

        Map<Long,Float> result = new LinkedHashMap<Long,Float>();
        for (Iterator<Entry<Long, Float>> it = list.iterator(); it.hasNext();) {
            Map.Entry<Long, Float> entry = it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
