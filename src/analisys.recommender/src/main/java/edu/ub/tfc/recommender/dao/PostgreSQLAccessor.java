package edu.ub.tfc.recommender.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLAccessor {

	private static PostgreSQLAccessor __postgreSQLAccessor;
	private Connection _connection;
	
	public Connection get_connection() {
		return _connection;
	}

	public static PostgreSQLAccessor getInstance() {
		if (__postgreSQLAccessor==null) {
			__postgreSQLAccessor = new PostgreSQLAccessor();
		}
		return __postgreSQLAccessor;
	}
	
	private PostgreSQLAccessor() {
		try {
			connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void connect() throws SQLException, ClassNotFoundException {
		String url = "jdbc:postgresql://localhost/movielens";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","root");
		Class.forName("org.postgresql.Driver");
		_connection = DriverManager.getConnection(url, props);
	}
	
	private void disconnect() throws SQLException {
		this._connection.close();
	}
}
