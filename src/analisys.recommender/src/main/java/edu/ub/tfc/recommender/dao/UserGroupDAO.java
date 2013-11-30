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

import org.apache.log4j.Logger;

import edu.ub.tfc.recommender.bean.UserGroup;

public class UserGroupDAO {

	/* ****************************
			CONSTANTS
	* *************************** */

	private static final int NO_LIMIT = -1;

	/* ****************************
			CLASS ATTRIBUTES
	* *************************** */

	private static Logger logger = Logger.getLogger(UserGroupDAO.class);		
	
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
	 * Return all items rated by a user group
	 * @param theUserGroupId
	 * @return
	 */
	public List<Long> getAllRatedItems(Long theUserGroupId) {
		return getRatedItemsByPopularity(theUserGroupId, NO_LIMIT);
	}
	
	/**
	 * Return the N most popular items rated by a user group
	 * @param theUserGroupId
	 * @param theMaxItemsToRetrieve
	 * @return
	 */
	public List<Long> getNMostPopularRatedItems(Long theUserGroupId, int theMaxItemsToRetrieve) {
	    return getRatedItemsByPopularity(theUserGroupId, theMaxItemsToRetrieve);
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
	    	String tmpQuery = "SELECT g.\"Id\", g.centroid, g.\"description\", gu.\"userId\" FROM groups as g, \"groupUsers\" as gu WHERE gu.\"groupId\" = g.\"Id\" AND g.\"Id\"=?";
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
	 * Return the N most popular items rated by a user group
	 * @param theUserGroupId
	 * @param theMaxItemsToRetrieve
	 * @return
	 */
	private List<Long> getRatedItemsByPopularity(Long theUserGroupId, int theMaxItemsToRetrieve) {
		PreparedStatement statement = null;
	    ResultSet tmpRankedItemsResultSet = null;
	    List<Long> tmpItemIds = null;
	    Boolean hasLimit = (theMaxItemsToRetrieve != NO_LIMIT);
	    try {

	    	String tmpQuery = "SELECT r.\"itemId\" as \"Id\", COUNT(gU.\"userId\") as \"popularity\""
	    			+ " FROM \"groupUsers\" gU, \"ratings\" r"
	    			+ " WHERE gU.\"groupId\"=? AND r.\"userId\"=gU.\"userId\""
	    			+ "	GROUP BY r.\"itemId\" ORDER BY \"popularity\" DESC";
	    	tmpQuery += (hasLimit) ? " LIMIT ?" : "";
	    	
	    	statement = this._connection.prepareStatement(tmpQuery);
	    	statement.setLong(1, theUserGroupId);
	    	if(hasLimit) {
	    		statement.setInt(2, theMaxItemsToRetrieve);
	    	}
	        tmpRankedItemsResultSet = statement.executeQuery();
	        tmpItemIds = this.getIdsFromSQL(tmpRankedItemsResultSet);
	        statement.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	    return tmpItemIds;
	}
	
	/**
	 * Return all cluster/non cluster groups
	 * @return
	 */
	private List<Long> getAllGroupIds(Boolean areCluster) {
		PreparedStatement statement = null;
	    ResultSet tmpGroupResultSet = null;
	    List<Long> tmpGroupIds = null;
	    
	    try {
	    	String tmpCluster = (areCluster) ? " NOT " : " ";
	    	String tmpQuery = "SELECT g.\"Id\" FROM groups as g WHERE g.centroid IS" + tmpCluster+ "NULL";
	    	statement = this._connection.prepareStatement(tmpQuery);
	        tmpGroupResultSet = statement.executeQuery();
	        tmpGroupIds = this.getIdsFromSQL(tmpGroupResultSet);
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
					 tmpResult.set_description(theUserGroupData.getString("description"));
				 }
				 tmpUserIds.add(theUserGroupData.getLong("userId"));
		 	 }
		 }
		 return tmpResult;
	}
	
	/**
	 * Return the List of Long Id columns
	 * @param theIdSQLSet
	 * @return
	 * @throws SQLException 
	 */
	private List<Long> getIdsFromSQL(ResultSet theIdSQLSet) throws SQLException {
		List<Long> tmpIdList = new ArrayList<Long>();
	    
		while(theIdSQLSet.next()) {
			tmpIdList.add(theIdSQLSet.getLong("Id"));
        }
		return tmpIdList;
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

	/**
	 * Returns the Group Ids with the description indicated
	 * @param theGroupDescription
	 * @param theMaxGroups
	 * @return
	 */
	public List<Long> getTopGroupIdsByDescription(String theGroupDescription, Integer theMaxGroups) {
		PreparedStatement statement = null;
	    ResultSet tmpGroupResultSet = null;
	    List<Long> tmpGroupIds = null;
	    
	    try {
	    	String tmpQuery = "SELECT g.\"Id\" FROM groups as g WHERE g.description LIKE '" + theGroupDescription+"' LIMIT " + theMaxGroups;
	    	statement = this._connection.prepareStatement(tmpQuery);
	        tmpGroupResultSet = statement.executeQuery();
	        tmpGroupIds = this.getIdsFromSQL(tmpGroupResultSet);
	        statement.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	return tmpGroupIds;
	}
}
