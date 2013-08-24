package edu.ub.tfc.recommender.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import edu.ub.tfc.recommender.dao.FilmDao;
import edu.ub.tfc.recommender.domain.Film;

/**
 * Clase que implementa la interfaz FilmDao
 * @author David Gil
 *
 */
public class FilmDaoImpl implements FilmDao {

	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.FilmDao#insertFilm(edu.ub.tfc.recommender.domain.Film)
	 */
	@Override
	public Integer insertFilm(final Film record) {
		final String sql = "INSERT INTO films (film_id, name, film_genre) values (?, ?, ?)";
		final Object[] params = new Object[] {record.getFilmId(), record.getName(), record.getFilmGenre()};
		final int[] types = new int[] {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
		return this.jdbcTemplate.update(sql, params, types);
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.FilmDao#updateFilm(edu.ub.tfc.recommender.domain.Film)
	 */
	@Override
	public Integer updateFilm(final Film record) {
		final String sql = "UPDATE films SET name = ?, film_genre = ? where film_id = ?";
		final Object[] params = new Object[] {record.getName(), record.getFilmGenre(), record.getFilmId()};
		final int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
		return this.jdbcTemplate.update(sql, params, types);
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.FilmDao#deleteFilm(java.lang.Integer)
	 */
	@Override
	public void deleteFilm(final Integer filmId) {
		final String sql = "DELETE FROM films WHERE film_id = ? ";
		final Object[] params = new Object[] {filmId};
		this.jdbcTemplate.update(sql, params);
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.FilmDao#selectFilmById(java.lang.Integer)
	 */
	@Override
	public Film selectFilmById(final Integer filmId) {
		final String sql = "SELECT film_id, name, film_genre FROM films WHERE film_id = ?";
		final Object[] params = new Object[] {filmId};
		final Film film = new Film();
		// Process query results
		this.jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow (final ResultSet rs) throws SQLException {
				film.setFilmId(new Integer(rs.getInt("film_id")));
				film.setName(rs.getString("name"));
				film.setFilmGenre(rs.getString("film_genre"));
			}
		});
		return film;
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.FilmDao#selectAllFilms()
	 */
	@Override
	public List<Film> selectAllFilms() {
		final String sql = "SELECT film_id, name, film_genre FROM films";
		final Object[] params = new Object[] {};
		final List<Film> films = new ArrayList<Film>();

		this.jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow (final ResultSet rs) throws SQLException {
				final Film film = new Film();
				film.setFilmId(new Integer(rs.getInt("film_id")));
				film.setName(rs.getString("name"));
				film.setFilmGenre(rs.getString("film_genre"));
				films.add(film);
			}
		});
		return films;
	}

	/**
	 * @see edu.ub.tfc.recommender.dao.FilmDao#selectFilmByName(java.lang.String)
	 */
	@Override
	public List<Film> selectFilmByName(final String name) {
		final String sql = "SELECT film_id, name, film_genre FROM films WHERE name like ?";
		final Object[] params = new Object[] {"%"+name+"%"};
		final List<Film> films = new ArrayList<Film>();
		// Process query results
		this.jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			@Override
			public void processRow (final ResultSet rs) throws SQLException {
				final Film film = new Film();
				film.setFilmId(new Integer(rs.getInt("film_id")));
				film.setName(rs.getString("name"));
				film.setFilmGenre(rs.getString("film_genre"));
				films.add(film);
			}
		});
		return films;
	}



}
