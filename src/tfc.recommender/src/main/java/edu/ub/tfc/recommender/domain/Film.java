package edu.ub.tfc.recommender.domain;

/**
 * Clase para objetos Film
 * @author David Gil De Arce
 */
public class Film {
	
	private int filmId;
	private String name;
	private String filmGenre;

	public Film() {
		super();
	}
	/**
	 * @param filmId
	 * @param name
	 * @param filmGenre
	 */
	public Film(final int filmId, final String name, final String filmGenre) {
		super();
		this.filmId = filmId;
		this.name = name;
		this.filmGenre = filmGenre;
	}
	/**
	 * Obtiene el ID de una película
	 * @return ID de la película
	 */
	public int getFilmId() {
		return this.filmId;
	}
	/**
	 * Guarda el ID de una película
	 * @param filmId ID de la película
	 */
	public void setFilmId(final int filmId) {
		this.filmId = filmId;
	}
	/**
	 * Obtiene el nombre de una película
	 * @return El nombre de la película
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Guarda el nombre de una película
	 * @param name Nombre de la película
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * Obtiene el género de una película
	 * @return El género de la película
	 */
	public String getFilmGenre() {
		return this.filmGenre;
	}
	/**
	 * Guarda el género de una película
	 * @param filmGenre El género de la película
	 */
	public void setFilmGenre(final String filmGenre) {
		this.filmGenre = filmGenre;
	}


}
