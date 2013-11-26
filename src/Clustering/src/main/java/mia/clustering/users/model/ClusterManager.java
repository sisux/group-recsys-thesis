package mia.clustering.users.model;

import java.util.HashMap;
import java.util.Map;

import mia.clustering.users.dao.PostgreSQLAccessor;
import mia.clustering.users.dao.UserGroupDAO;

public class ClusterManager {

	private PostgreSQLAccessor _dbAccessor;
	
	private Map<Integer, UserGroup> _userGroups;
	private int _maxGroups;
	private int _maxUsersPerGroup;
	private double _targetSimilarity;
	
	public Map<Integer, UserGroup> get_userGroups() {
		return _userGroups;
	}
	
	public int get_maxGroups() {
		return _maxGroups;
	}

	public void set_maxGroups(int _maxGroups) {
		this._maxGroups = _maxGroups;
	}

	public int get_maxUsersPerGroup() {
		return _maxUsersPerGroup;
	}

	public void set_maxUsersPerGroup(int _maxUsersPerGroup) {
		this._maxUsersPerGroup = _maxUsersPerGroup;
	}

	public double get_targetSimilarity() {
		return _targetSimilarity;
	}

	public void set_targetSimilarity(double _targetSimilarity) {
		this._targetSimilarity = _targetSimilarity;
	}

	public ClusterManager() {
		_dbAccessor = PostgreSQLAccessor.getInstance();
		_userGroups = new HashMap<Integer, UserGroup>();
	}
	
	public ClusterManager(int theGroupNum, int theGroupSize, double theTargetSimilarity) {
		this();
		_maxGroups = theGroupNum;
		_maxUsersPerGroup = theGroupSize;
		_targetSimilarity = theTargetSimilarity;
	}
	
	public void saveGroups() {
		UserGroupDAO tmpUserGroupDAO = new UserGroupDAO();
		tmpUserGroupDAO.cleanExistingroups();
		tmpUserGroupDAO.insertGroups(this._userGroups);
	}
}
