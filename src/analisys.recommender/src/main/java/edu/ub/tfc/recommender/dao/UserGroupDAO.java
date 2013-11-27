package edu.ub.tfc.recommender.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.ub.tfc.recommender.bean.UserGroup;

public class UserGroupDAO {

	/* ****************************
			ATTRIBUTES
	* *************************** */

	private Connection _connection = PostgreSQLAccessor.getInstance().get_connection();

	/* ****************************
			PUBLIC METHODS
	* *************************** */
	
	/**
	 * Remove all existing groups (included the initial clusters)
	 */
	public void cleanExistinGroups() {
		PreparedStatement statement = null;
   
	    try {
	    	String tmpQuery = "DELETE FROM \"groupUsers\"";
	    	statement = this._connection.prepareStatement(tmpQuery);
	        int affectedRows = statement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Deleting groupUsers information, no rows affected.");
	        }

	        tmpQuery = "DELETE FROM \"groups\"";
	        statement.close();
	        statement = this._connection.prepareStatement(tmpQuery);
	        affectedRows = statement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Deleting groups information, no rows affected.");
	        }
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	       if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	}
	
	/**
	 * Insert a list of groups
	 * @param theUserGroups
	 */
	public void insertGroups(Map<Long, UserGroup> theUserGroups) {
		Collection<UserGroup> tmpGroups = theUserGroups.values();
		for (UserGroup tmpGroup : tmpGroups)  {
			insertGroup(tmpGroup);
		}
	}
	
	/**
	 * Return all cluster groups
	 * @return
	 */
	public List<Long> getAllClusters() {
		return getAllGroupIds(true);
	}

	/**
	 * Return all cluster groups
	 * @return
	 */
	public List<Long> getAllUserGroupIds() {
		return getAllGroupIds(false);
	}
	
	/**
	 * Return the UserGroup from its own ID
	 * @param theUserGroupId
	 * @return
	 */
	public UserGroup findGroupById(Long theUserGroupId) {
		PreparedStatement statement = null;
	    ResultSet tmpGroupResultSet = null;
	    UserGroup tmpResult = null;
	    
	    try {
	    	String tmpQuery = "SELECT g.\"Id\", g.centroid, gu.\"userId\" FROM groups as g, \"groupUsers\" as gu WHERE gu.\"groupId\" = g.\"Id\" AND g.\"Id\"=?";
	    	statement = this._connection.prepareStatement(tmpQuery);
	    	statement.setLong(1, theUserGroupId);
	        tmpGroupResultSet = statement.executeQuery();
	        tmpResult = parseFromSQL(tmpGroupResultSet);
	        statement.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	    return tmpResult;
	}
	
	/* ****************************
			PRIVATE METHODS
	* *************************** */
	
	/**
	 * Return all cluster/non cluster groups
	 * @return
	 */
	private List<Long> getAllGroupIds(Boolean areCluster) {
		PreparedStatement statement = null;
	    ResultSet tmpGroupResultSet = null;
	    List<Long> tmpGroupIds = new ArrayList<Long>();
	    
	    try {
	    	String tmpCluster = (areCluster) ? " NOT " : " ";
	    	String tmpQuery = "SELECT g.\"Id\" FROM groups as g WHERE g.centroid IS" + tmpCluster+ "NULL";
	    	statement = this._connection.prepareStatement(tmpQuery);
	        tmpGroupResultSet = statement.executeQuery();
	        while(tmpGroupResultSet.next()) {
	        	tmpGroupIds.add(tmpGroupResultSet.getLong("Id"));
	        }
	        statement.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	    return tmpGroupIds;
	}
	
	/**
	 * Create a UserGroup from the SQL Resultset
	 * @param theUserGroupData
	 * @return
	 * @throws SQLException
	 */
	private UserGroup parseFromSQL(ResultSet theUserGroupData) throws SQLException {
		 UserGroup tmpResult = null;
		 if(theUserGroupData!=null) {
			 List<Long> tmpUserIds = new ArrayList<Long>();
			 tmpResult = new UserGroup();
	 		 tmpResult.set_userIds(tmpUserIds);
	 		 
		 	 while(theUserGroupData.next()) {
				 if(theUserGroupData.isFirst()) {
					 tmpResult.set_id(theUserGroupData.getLong("Id"));
					 tmpResult.set_centroid(theUserGroupData.getString("centroid"));
				 }
				 tmpUserIds.add(theUserGroupData.getLong("userId"));
		 	 }
		 }
		 return tmpResult;
	}
	
	/**
	 * Insert a single UserGroup into the DB
	 * @param theUserGroup
	 */
	private void insertGroup(UserGroup theUserGroup) {
	    PreparedStatement statement = null;
	    ResultSet generatedKeys = null;
	    int tmpGeneratedGroupId;
	    
	    try {
	    	String tmpQuery = "INSERT INTO \"groups\"(\"centroid\",\"description\") VALUES (?,?)";
	    	statement = this._connection.prepareStatement(tmpQuery, Statement.RETURN_GENERATED_KEYS);
	    	statement.setString(1, theUserGroup.get_centroid());
	    	statement.setString(2, theUserGroup.get_description());
	        int affectedRows = statement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }

	        generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	tmpGeneratedGroupId = generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
	        statement.close();
	        
	        tmpQuery = "INSERT INTO \"groupUsers\"(\"groupId\", \"userId\") VALUES (" + tmpGeneratedGroupId+",?)";
	        statement = this._connection.prepareStatement(tmpQuery);
	        for(Long tmpUser : theUserGroup.get_userIds()) {
	        	statement.setLong(1, tmpUser);
	        	affectedRows = statement.executeUpdate();
	        }
	        statement.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	}
}
