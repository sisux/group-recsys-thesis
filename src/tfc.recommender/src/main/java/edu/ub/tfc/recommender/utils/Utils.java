package edu.ub.tfc.recommender.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Clase de utilidades
 * @author David Gil De Arce
 */
public class Utils {
	public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Escribe una traza de log
	 * @param texto Texto que se quiere escribir
	 */
	public static void escribirLog(final String texto) {

		System.out.println("[" + SDF.format(Calendar.getInstance().getTime()) + "] - " +  texto);
	}

}
