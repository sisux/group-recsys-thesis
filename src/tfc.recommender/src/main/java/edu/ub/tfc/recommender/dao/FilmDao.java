package edu.ub.tfc.recommender.dao;

import java.util.List;

import edu.ub.tfc.recommender.domain.Film;

/**
 * Interfaz para consultar, modificar y borrar pel’culas de la base de datos.
 * @author David Gil De Arce
 * 
 */
public interface FilmDao {
	/**
	 * Inserta una pel’cula
	 * @param record Pel’cula
	 * @return ID de la pel’cula insertada
	 */
	Integer insertFilm(Film record);

	/**
	 * Modifica la informaci—n de una pel’cula
	 * @param record Pel’cula
	 * @return ID de la pel’cula modificada
	 */
	Integer updateFilm(Film record);

	/**
	 * Borra una pel’cula
	 * @param FilmId ID de la pel’cula que se borrar‡
	 */
	void deleteFilm(Integer FilmId);

	/**
	 * Devuelve una pel’cula por ID
	 * @param FilmId ID de la pel’cula que se busca
	 * @return Pel’cula
	 */
	Film selectFilmById(Integer FilmId);

	/**
	 * Devuelve todas las pel’culas de la base de datos
	 * @return Lista de pel’culas
	 */
	List<Film> selectAllFilms();

	/**
	 * Devuelve una pel’cula por nombre
	 * El motor de bœsqueda es sensible a mayœsculas
	 * @param name Nombre de la pel’cula
	 * @return Pel’cula o lista de pel’culas que contengan ese nombre
	 */
	List<Film> selectFilmByName(String name);

}
