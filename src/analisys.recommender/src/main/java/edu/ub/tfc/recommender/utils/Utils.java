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
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
			
			pw.println(GroupEvaluation.toStringHeader());
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
	
	/**
	 * Calculate the values log_{base}
	 * @param x
	 * @param base
	 * @return
	 */
	public static double log(double x, double base)
	{
		return (Math.log(x) / Math.log(base));
	}
	
	/*
	 * Return the mean of a set of values
	 */
	public static float computeAverage(float[] theDataSet) {
		float tmpMean = 0L;
		int n = theDataSet.length;
		
		if(theDataSet != null && n > 0) {
			for(int i = 0; i < n; i++)
				tmpMean += theDataSet[i];
			
			tmpMean = tmpMean / n;
		}
		return tmpMean;
	}
	
	/**
	 * Calculates the Standard Deviation of a data set
	 * @param a
	 * @return
	 */
	public static float stdDev(float[] a, float theAverage)
	{
		float total = 0;
		int n = a.length;
		double all;
		for(int i = 0; i < n; i++) 	{
			all = Math.pow(a[i]-theAverage, 2);
			total += all;
		}
		total = total/n;
		total = (float) Math.sqrt(total);
		return total;
	}
	
	/**
	 * Corrected sample standard deviation
	 * @param a
	 * @param theAverage
	 * @return
	 */
	public static float correctedSampleStdDev(float[] a, float theAverage)
	{
		float total = 0;
		int n = a.length;
		double all;
		for(int i = 0; i < n; i++) 	{
			all = Math.pow(a[i]-theAverage, 2);
			total += all;
		}
		total = total/(n-1);
		total = (float) Math.sqrt(total);
		return total;
	}
	
	/**
	 * Computes the average (mean) value of a data set
	 * @param a
	 * @return
	 */
	public static double computeAverage(double[] a)
	{
		double average = 0;
		int n = a.length;
		for(int i = 0; i < n; i++)
			average = average + a[i];
		if(n > 0) {
			return (average/n);
		} else {
			System.out.println("ERROR: Can't average 0 numbers.");
			return 0;
		}
	}
	
	/**
	 * Sort Map by its values
	 * @param map
	 * @return
	 */
    public static Map<Long, Float> sortMapByValue(Map<Long, Float> map) {
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
