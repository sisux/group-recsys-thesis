package edu.ub.tfc.recommender.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import edu.ub.tfc.recommender.bean.GRSAnalyserResult;
import edu.ub.tfc.recommender.bean.GroupEvaluation;
import edu.ub.tfc.recommender.bean.Resultado;

/**
 * Clase de utilidades
 * @author David Gil De Arce
 */
public class Utils {
	
	// Constantes
	private static final char DECIMAL_COMA = ',';
	private static final char DECIMAL_POINT = '.';
	private static final String SEPARATOR = ";";
	public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Genera la salida a partir de un fichero
	 * @param pathFicheroCsv Ruta del fichero CSV
	 * @param nombreFicheroCsv Nombre del fichero CSV
	 * @param outstr Output stream
	 * @param resp Response de la petici—n
	 * @throws IOException Si hay algœn fallo con el sistema de ficheros
	 */
	public static void streamBinaryData(final String pathFicheroCsv, final String nombreFicheroCsv, final ServletOutputStream outstr, final HttpServletResponse resp) throws IOException {
		String ErrorStr = null;

		try {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			final String inFile = pathFicheroCsv + nombreFicheroCsv;
			final int tam = (int) new File(inFile).length();
			bis = new BufferedInputStream(new FileInputStream(inFile));

			try {
				// Asignamos el tipo de fichero CSV
				resp.setContentType("text/csv"); // Cualquier mime type
				// Seteamos el tama–o de la respuesta
				resp.setContentLength(tam);
				resp.setHeader("Content-Disposition", "attachment;filename=\"" + nombreFicheroCsv + "\"");

				bos = new BufferedOutputStream(outstr);

				// Bucle para leer de un fichero y escribir en el otro.
				final byte[] array = new byte[1000];
				int leidos = bis.read(array);
				while (leidos > 0) {
					bos.write(array, 0, leidos);
					leidos = bis.read(array);
				}

			} catch (final Exception e) {
				e.printStackTrace();
				ErrorStr = "Error Streaming the Data";
				outstr.print(ErrorStr);
			} finally {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (outstr != null) {
					outstr.flush();
					outstr.close();
				}
			}

		} catch (final Exception e) {
			escribirLog("FICHERO NO ENCONTRADO");
		}
	}

	/**
	 * Escribe una traza de log
	 * @param texto Texto que se quiere escribir
	 */
	public static void escribirLog(final String texto) {

		System.out.println("[" + SDF.format(Calendar.getInstance().getTime()) + "] - " +  texto);
	}

	/**
	 * Genera el fichero CSV
	 * @param testCase Resultados de la evaluaci—n
	 * @param nombreFicheroCsv Nombre del fichero CSV
	 * @param pathFicheroCsv Ruta del fichero CSV
	 */
	public static void escribirCsv(final Map<Long, Resultado> testCase, final String nombreFicheroCsv, final String pathFicheroCsv) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(pathFicheroCsv + nombreFicheroCsv);
			pw = new PrintWriter(fichero);
			final List<Long> keys = new ArrayList<Long>(testCase.keySet());
			Collections.sort(keys);
			for (final Long key : keys) {
				final Resultado evaluation = testCase.get(key);
				final String linea = key + SEPARATOR + evaluation.toString();
				pw.println(linea.replace(DECIMAL_POINT, DECIMAL_COMA));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero) {
					fichero.close();
				}
			} catch (final Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * Genera el fichero CSV
	 * @param testCase Resultados de la evaluaci—n
	 * @param nombreFicheroCsv Nombre del fichero CSV
	 * @param pathFicheroCsv Ruta del fichero CSV
	 */
	public static void escribirCsv(final GRSAnalyserResult testCases, final String nombreFicheroCsv, final String pathFicheroCsv) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		
		try {
			fichero = new FileWriter(pathFicheroCsv + nombreFicheroCsv);
			pw = new PrintWriter(fichero);
			
			final List<Long> tmpGroupIds = testCases.getAllGroupsId();
			Collections.sort(tmpGroupIds);
			
			for (final Long theGroupId : tmpGroupIds) {
				List<GroupEvaluation> tmpGroupEvaluations = testCases.getAllGroupEvaluationsByGroupId(theGroupId);
				
				for(final GroupEvaluation tmpGroupEvaluation : tmpGroupEvaluations) {
					pw.println(tmpGroupEvaluation.toString().replace(DECIMAL_POINT, DECIMAL_COMA));
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero) {
					fichero.close();
				}
			} catch (final Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
