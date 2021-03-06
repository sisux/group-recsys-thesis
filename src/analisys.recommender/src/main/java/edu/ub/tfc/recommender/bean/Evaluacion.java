package edu.ub.tfc.recommender.bean;

import java.util.Map;

/**
 * Clase de objetos Evaluacion
 * @author David Gil De Arce
 */
public class Evaluacion {


	/* ****************************
			CONSTANTS
	* *************************** */

	public static final String UNITARY_RECOMMENDATION_TIME = "URTime";

	/* ****************************
			ATTRIBUTES
	* *************************** */
	
	private long time;
	private Map<Long, Float> evaluacion;

	/**
	 * Constructor
	 * @param time Promedio de tiempo en generar las predicciones
	 * @param evaluacion Lista de evaluaciones
	 */
	public Evaluacion(final long time, final Map<Long, Float> evaluacion) {
		this.time = time;
		this.evaluacion = evaluacion;
	}

	/**
	 * Obtiene el promedio de tiempo invertido en las predicciones
	 * @return Promedio de tiempo
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * Guarda el promedio de tiempo invertido en las predicciones
	 * @param time Promedio de tiempo
	 */
	public void setTime(final long time) {
		this.time = time;
	}

	/**
	 * @return the evaluacion
	 */
	public Map<Long, Float> getEvaluacion() {
		return this.evaluacion;
	}

	/**
	 * @param evaluacion the evaluacion to set
	 */
	public void setEvaluacion(final Map<Long, Float> evaluacion) {
		this.evaluacion = evaluacion;
	}
}
