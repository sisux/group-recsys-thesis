package edu.ub.tfc.recommender.model;

/**
 * Clase para objetos Pelicula
 * @author David Gil De Arce
 */
public class Pelicula {
	
	private int id;
	private String titulo;
	private String genero;
	private String url;
	private int valoracion;

	/**
	 * @param id
	 * @param titulo
	 * @param genero
	 * @param url
	 */
	public Pelicula(final int id, final String titulo, final String genero, final String url) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.genero = genero;
		this.url = url;
	}
	/**
	 * Obtiene el ID de una película
	 * @return ID de la película
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * Guarda el ID de una película
	 * @param id ID de la película
	 */
	public void setId(final int id) {
		this.id = id;
	}
	/**
	 * Obtiene el título de una película
	 * @return Título de la película
	 */
	public String getTitulo() {
		return this.titulo;
	}
	/**
	 * Guarda el título de una película
	 * @param titulo Títula de la película
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Obtiene el género de una película
	 * @return Género de la película
	 */
	public String getGenero() {
		return this.genero.replace("|",", ");
	}
	/**
	 * Guarda el género de una película
	 * @param genero Género de la película
	 */
	public void setGenero(final String genero) {
		this.genero = genero;
	}
	/**
	 * Obtiene la URL de una película
	 * @return URL de la película
	 */
	public String getUrl() {
		return this.url;
	}
	/**
	 * Guarda la URL de una película
	 * @param url URL de la película
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
	/**
	 * Obtiene la valoración de una película
	 * @return Valoración de la película
	 */
	public int getValoracion() {
		return this.valoracion;
	}
	/**
	 * Guarda la valoración de una película
	 * @param valoracion Valoración de la película
	 */
	public void setValoracion(final int valoracion) {
		this.valoracion = valoracion;
	}



}
