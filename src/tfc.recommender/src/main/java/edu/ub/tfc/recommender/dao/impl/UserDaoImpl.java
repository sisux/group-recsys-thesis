package edu.ub.tfc.recommender.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import edu.ub.tfc.recommender.dao.UserDao;
import edu.ub.tfc.recommender.domain.User;


/**
 * Clase que implementa la interfaz UserDao.
 * @author David Gil
 * 
 */
public class UserDaoImpl implements UserDao {

	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.UserDao#insertUser(edu.ub.tfc.recommender.domain.User)
	 */
	@Override
	public Integer insertUser(final User user) {
		final String sql = "INSERT INTO users (user_id, name) values (?, ?)";
		final Object[] params = new Object[] {user.getUserId(), user.getName()};
		final int[] types = new int[] {Types.INTEGER, Types.VARCHAR};
		return this.jdbcTemplate.update(sql, params, types);
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.UserDao#updateUser(edu.ub.tfc.recommender.domain.User)
	 */
	@Override
	public Integer updateUser(final User user) {
		final String sql = "UPDATE users SET name = ? where user_id = ?";
		final Object[] params = new Object[] {user.getName(), user.getUserId()};
		final int[] types = new int[] {Types.VARCHAR, Types.INTEGER};
		return this.jdbcTemplate.update(sql, params, types);
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.UserDao#deleteUser(java.lang.Integer)
	 */
	@Override
	public void deleteUser(final Integer userId) {
		final String sql = "DELETE FROM users WHERE user_id = ? ";
		final Object[] params = new Object[] {userId};
		this.jdbcTemplate.update(sql, params);
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.UserDao#selectUser(java.lang.Integer)
	 */
	@Override
	public User selectUser(final Integer userId) {
		final String sql = "SELECT user_id, name FROM users WHERE user_id = ?";
		final Object[] params = new Object[] {userId};
		final User user = new User();
		// Process query results
		this.jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow (final ResultSet rs) throws SQLException {
				user.setUserId(new Integer(rs.getInt("user_id")));
				user.setName(rs.getString("name"));
			}
		});
		return user;
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.UserDao#selectAllUsers()
	 */
	@Override
	public List<User> selectAllUsers() {
		final String sql = "SELECT user_id, name FROM users";
		final Object[] params = new Object[] {};
		final List<User> users = new ArrayList<User>();

		this.jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow (final ResultSet rs) throws SQLException {
				final User user = new User();
				user.setUserId(new Integer(rs.getInt("user_id")));
				user.setName(rs.getString("name"));
				users.add(user);
			}
		});
		return users;
	}

}
