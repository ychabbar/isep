package com.isepfm.service;

import com.isepfm.beans.Users;
import com.isepfm.dao.DAOException;

public interface IUsersDao {

	void createUser(Users utilisateur) throws DAOException;

	Users getUser(String name) throws DAOException;

	Users getUserByMail(String mail) throws DAOException;

	/**
	 * Récupère un utilisateur à partir de son ID
	 * 
	 * @param userId
	 *            id de l'utilisateur
	 * @return retourne un objet utilisateur
	 * @throws DAOException
	 */
	Users getUserById(long userId) throws DAOException;

	void updateUser(Users utilisateur) throws DAOException;

	void deleteUser(String name) throws DAOException;

	boolean ifUserExist(String name) throws DAOException;

}