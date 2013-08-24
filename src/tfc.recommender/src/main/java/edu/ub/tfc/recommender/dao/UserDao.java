package edu.ub.tfc.recommender.dao;

import java.util.List;
import edu.ub.tfc.recommender.domain.User;

/**
 * Interfaz para consultar, modificar y borrar usuarios de la base de datos.
 * @author David Gil De Arce
 * 
 */
public interface UserDao {
	/**
	 * Inserta un usuario
	 * @param record Usuario
	 * @return ID del usuario insertado
	 */
	Integer insertUser(User record);

	/**
	 * Modifica la informaci—n de un usuario
	 * @param record Usuario
	 * @return ID del usuario modificado
	 */
	Integer updateUser(User record);

	/**
	 * Borra un usuario
	 * @param userId ID del usuario que se borrar‡
	 */
	void deleteUser(Integer userId);

	/**
	 * Busca un usuario por ID
	 * @param userId ID del usuario
	 * @return Usuario
	 */
	User selectUser(Integer userId);

	/**
	 * Busca todos los usuarios de la base de datos
	 * @return Lista de todos los usuarios
	 */
	List<User> selectAllUsers();
}
