package mia.clustering.users.model;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {

	private Integer _id;
	private List<Integer> _userIds;
	private String _centroid;
	private String _description;

	public UserGroup() {
		_userIds = new ArrayList<Integer>();
	}
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public List<Integer> get_userIds() {
		return _userIds;
	}
	public void set_userIds(List<Integer> _userIds) {
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
