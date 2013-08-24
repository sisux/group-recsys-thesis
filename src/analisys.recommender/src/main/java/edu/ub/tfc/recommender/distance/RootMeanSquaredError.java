package edu.ub.tfc.recommender.distance;

import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 * Cálculo de la métrica Root Mean Squared Error (RMSE)
 * @author David Gil De Arce
 */
public class RootMeanSquaredError extends EuclideanDistanceMeasure {

	/**
	 * Devuelve la distancia cuadrática entre vectores
	 * @param l1 Primer vector
	 * @param l2 Segundo vector
	 * @return Distancia cuadrática
	 */
	public double distance(final double[] l1, final double[] l2) {
		final Vector v1 = new DenseVector(l1);
		final Vector v2 = new DenseVector(l2);

		return super.distance(v1, v2);
	}

}
