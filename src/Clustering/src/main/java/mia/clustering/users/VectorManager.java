package mia.clustering.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mia.clustering.users.dao.PostgreSQLAccessor;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.Vector;

public class VectorManager {

	private PostgreSQLAccessor _dbAccessor;
	
	public VectorManager() {
		try {
		_dbAccessor = PostgreSQLAccessor.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Vector> getUserVectors() {
		List<Vector> tmpResult = null;
		
		try {
			ResultSet tmpUserVectors = _dbAccessor.getUserVectors();
			tmpResult = new ArrayList<Vector>();

			while (tmpUserVectors.next()) {
				tmpResult.add(getUserVector(tmpUserVectors));
			}
			tmpUserVectors.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpResult;
	}
	
	private Vector getUserVector(ResultSet theRow) throws SQLException {
		NamedVector tmpResult;

		DenseVector tmpDenseVector = new DenseVector(new double[] {
	    		theRow.getDouble("UnkownFreq"),
	    		theRow.getDouble("ActionFreq"),
	    		theRow.getDouble("AdventureFreq"),
	    		theRow.getDouble("AnimationFreq"),
	    		theRow.getDouble("ChildrensFreq"),
	    		theRow.getDouble("ComedyFreq"),
	    		theRow.getDouble("CrimeFreq"),
	    		theRow.getDouble("DocumentaryFreq"),
	    		theRow.getDouble("DramaFreq"),
	    		theRow.getDouble("FantasyFreq"),
	    		theRow.getDouble("FilmNoirFreq"),
	    		theRow.getDouble("HorrorFreq"),
	    		theRow.getDouble("MusicalFreq"),
	    		theRow.getDouble("MysteryFreq"),
	    		theRow.getDouble("RomanceFreq"),
	    		theRow.getDouble("SciFiFreq"),
	    		theRow.getDouble("ThrillerFreq"),
	    		theRow.getDouble("WarFreq"),
	    		theRow.getDouble("WesternFreq")
	    });
	    tmpResult = new NamedVector(tmpDenseVector, Integer.toString(theRow.getInt("userId")));
	    return tmpResult;
	}
}
