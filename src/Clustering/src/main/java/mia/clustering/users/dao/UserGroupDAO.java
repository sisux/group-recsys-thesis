package mia.clustering.users.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import mia.clustering.users.model.UserGroup;

public class UserGroupDAO {

	private Connection _connection = PostgreSQLAccessor.getInstance().get_connection();
	
	public void cleanExistingroups() {
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
	
	public void insertGroups(Map<Integer, UserGroup> theUserGroups) {
		Collection<UserGroup> tmpGroups = theUserGroups.values();
		for (UserGroup tmpGroup : tmpGroups)  {
			insertGroup(tmpGroup);
		}
	}
	
	public List<Integer> getAllClusters() {
		PreparedStatement statement = null;
	    ResultSet tmpGroupResultSet = null;
	    List<Integer> tmpGroupIds = new ArrayList<Integer>();
	    
	    try {
	    	String tmpQuery = "SELECT g.\"Id\" FROM groups as g WHERE g.centroid IS NOT NULL";
	    	statement = this._connection.prepareStatement(tmpQuery);
	        tmpGroupResultSet = statement.executeQuery();
	        while(tmpGroupResultSet.next()) {
	        	tmpGroupIds.add(tmpGroupResultSet.getInt("Id"));
	        }
	        statement.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
	    }
	    return tmpGroupIds;
	}
	
	public UserGroup findGroupById(Integer theUserGroupId) {
		PreparedStatement statement = null;
	    ResultSet tmpGroupResultSet = null;
	    UserGroup tmpResult = null;
	    
	    try {
	    	String tmpQuery = "SELECT g.\"Id\", g.centroid, gu.\"userId\" FROM groups as g, \"groupUsers\" as gu WHERE gu.\"groupId\" = g.\"Id\" AND g.\"Id\"=?";
	    	statement = this._connection.prepareStatement(tmpQuery);
	    	statement.setInt(1, theUserGroupId);
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
	
	private UserGroup parseFromSQL(ResultSet theUserGroupData) throws SQLException {
		 UserGroup tmpResult = null;
		 if(theUserGroupData!=null) {
			 List<Integer> tmpUserIds = new ArrayList<Integer>();
			 tmpResult = new UserGroup();
	 		 tmpResult.set_userIds(tmpUserIds);
	 		 
		 	 while(theUserGroupData.next()) {
				 if(theUserGroupData.isFirst()) {
					 tmpResult.set_id(theUserGroupData.getInt("Id"));
					 tmpResult.set_centroid(theUserGroupData.getString("centroid"));
				 }
				 tmpUserIds.add(theUserGroupData.getInt("userId"));
		 	 }
		 }
		 return tmpResult;
	}
	
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
	        
	        tmpQuery = "INSERT INTO \"groupUsers\"(\"groupId\", \"userId\") VALUES (" + tmpGeneratedGroupId+",?)";
	        statement = this._connection.prepareStatement(tmpQuery);
	        for(Integer tmpUser : theUserGroup.get_userIds()) {
	        	statement.setInt(1, tmpUser);
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
