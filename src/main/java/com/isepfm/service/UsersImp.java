package com.isepfm.service;

import static com.isepfm.dao.DAOUtilities.fermeturesSilencieuses;
import static com.isepfm.dao.DAOUtilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.isepfm.beans.Users;
import com.isepfm.dao.DAOException;
import com.isepfm.dao.DAOFactory;

public class UsersImp implements IUsersDao {

	private DAOFactory daoFactory;

	public UsersImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static final String SQL_SELECT_PAR_NAME = "SELECT id, mail, password, name FROM USERS WHERE name = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.isepfm.service.IUsersDao#getUser(java.lang.String)
	 */
	@Override
	public Users getUser(String name) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Users user = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_NAME, false, name);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				user = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return user;
	}

	private static final String SQL_SELECT_BY_MAIL = "SELECT id, mail, password, name FROM USERS WHERE mail = ?";

	public Users getUserByMail(String mail) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Users user = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_BY_MAIL, false, mail);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				user = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return user;
	}

	/*
	 * Implémentation de la méthode creer() définie dans l'interface
	 * UtilisateurDao
	 */

	private static final String SQL_INSERT = "INSERT INTO USERS (mail, password, name) VALUES (?, ?, ?)";

	@Override
	public void createUser(Users utilisateur) throws IllegalArgumentException,
			DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, utilisateur.getEmail(),
					utilisateur.getMotDePasse(), utilisateur.getNom());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propriété id du bean Utilisateur
				 * avec sa valeur
				 */
				utilisateur.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException(
						"Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	@Override
	public void updateUser(Users utilisateur) throws IllegalArgumentException,
			DAOException {
	}

	private static final String SQL_DELETE_USER = "DELETE FROM USERS WHERE NAME = ?";

	@Override
	public void deleteUser(String name) throws IllegalArgumentException,DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int status;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_USER, false, name);
			status = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

	}

	private static final String SQL_IFUSEREXIST = "SELECT id FROM USERS WHERE name = ?";

	@Override
	public boolean ifUserExist(String name) throws IllegalArgumentException,
			DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean userExist = false;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_IFUSEREXIST, false, name);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			if (resultSet.next()) {
				userExist = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return userExist;

	}

	private static final String SQL_SELECT_PAR_ID = "SELECT id, mail, password, name FROM USERS WHERE id = ?";

	@Override
	public Users getUserById(long userId) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Users user = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_ID, false, userId);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				user = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return user;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Users map(ResultSet resultSet) throws SQLException {
		Users userDB = new Users();
		userDB.setId(resultSet.getLong("id"));
		userDB.setEmail(resultSet.getString("mail"));
		userDB.setMotDePasse(resultSet.getString("password"));
		userDB.setNom(resultSet.getString("name"));
		return userDB;
	}
}
