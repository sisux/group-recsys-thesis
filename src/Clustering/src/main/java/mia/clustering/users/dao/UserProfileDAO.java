package mia.clustering.users.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.Vector;

public class UserProfileDAO {

	private Connection _connection = PostgreSQLAccessor.getInstance().get_connection();
	
	/**
	 * 
	 * @return
	 */
	public List<Vector> getUserVectors() {
		List<Vector> tmpResult = new ArrayList<Vector>();
		
		try {
			Statement stmt = this._connection.createStatement();
			String tmpQuery = "SELECT \"userId\", \"UnkownFreq\", \"ActionFreq\", \"AdventureFreq\", \"AnimationFreq\","
							  + "\"ChildrensFreq\", \"ComedyFreq\", \"CrimeFreq\", \"DocumentaryFreq\"," 
							  + "\"DramaFreq\", \"FantasyFreq\", \"FilmNoirFreq\", \"HorrorFreq\", \"MusicalFreq\"," 
							  + "\"MysteryFreq\", \"RomanceFreq\", \"SciFiFreq\", \"ThrillerFreq\", \"WarFreq\"," 
							  + "\"WesternFreq\""
							  + " FROM \"userVectors\"";
	 
			ResultSet tmpUserVectors = stmt.executeQuery(tmpQuery);
			while (tmpUserVectors.next()) {
				tmpResult.add(parseAsUserVector(tmpUserVectors));
			}
			tmpUserVectors.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpResult;
	}
	
	/**
	 * 
	 * @param theRow
	 * @return
	 * @throws SQLException
	 */
	private Vector parseAsUserVector(ResultSet theRow) throws SQLException {
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
