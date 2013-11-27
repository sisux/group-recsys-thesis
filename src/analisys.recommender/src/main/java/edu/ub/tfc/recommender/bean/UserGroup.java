package edu.ub.tfc.recommender.bean;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {

	private Long _id;
	private List<Long> _userIds;
	private String _centroid;
	private String _description;

	public UserGroup() {
		_userIds = new ArrayList<Long>();
	}
	
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public List<Long> get_userIds() {
		return _userIds;
	}
	public void set_userIds(List<Long> _userIds) {
		this._userIds = _userIds;
	}
	public String get_centroid() {
		return _centroid;
	}
	public void set_centroid(String _centroid) {
		this._centroid = _centroid;
	}

	public String get_description() {
		return _description;
	}

	public void set_description(String _groupDescription) {
		this._description = _groupDescription;
	}
	
}
