package edu.ub.tfc.recommender.domain;

/**
 * Clase para objetos User
 * @author David Gil De Arce
 */
public class User {

	private Integer userId;
	private String name;

	public User() {
		super();
	}

	/**
	 * @param userId
	 * @param name
	 */
	public User(final Integer userId, final String name) {
		super();
		this.userId = userId;
		this.name = name;
	}

	/**
	 * Obtiene el ID de un usuario
	 * @return ID de un usuario
	 */
	public Integer getUserId() {
		return this.userId;
	}

	/**
	 * Guarda el ID de un usuario
	 * @param userId ID del usuario
	 */
	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	/**
	 * Obtiene el nombre de un usuario
	 * @return El nombre del usuario
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Guarda el nombre de un usuario
	 * @param name El nombre del usuario
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
