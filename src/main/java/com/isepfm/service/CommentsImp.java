package com.isepfm.service;

import static com.isepfm.dao.DAOUtilities.fermeturesSilencieuses;
import static com.isepfm.dao.DAOUtilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Comments;
import com.isepfm.dao.DAOException;
import com.isepfm.dao.DAOFactory;

/**
 * @author fixe-richard
 * 
 */
public class CommentsImp implements ICommentsDao {

	private DAOFactory daoFactory;

	public CommentsImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static final String SQL_INSERT_COMMENT = "INSERT INTO COMMENTARY (DATE, CONTENT, ID_ALBUM, ID_USER) VALUES (?, ?, ?, ?)";

	@Override
	public void insertComment(Comments comments) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT_COMMENT, true, comments.getDate(),
					comments.getContent(), comments.getId_album(),
					comments.getIdUser());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de l'ajout d'un commentaire, aucune ligne ajoutée dans la table.");
			}

			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {

				comments.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException(
						"Échec de l'ajout d'un commentaire, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}

	}

	/**
	 * Permet de mapper un objet Commentaire et les attributs dans la base de
	 * données
	 * 
	 * @param resultSet
	 * @return Retourne un commentaire de la base de donnée
	 * @throws SQLException
	 */
	private static Comments map(ResultSet resultSet) throws SQLException {
		Comments comments = new Comments();
		comments.setId(resultSet.getLong("ID"));
		comments.setContent(resultSet.getString("CONTENT"));
		comments.setDate(resultSet.getDate("DATE"));
		comments.setIdUser(resultSet.getLong("ID_USER"));
		comments.setId_album(resultSet.getLong("ID_ALBUM"));
		return comments;
	}

	private static final String SQL_SELECT_LIST_PAR_NOM = "SELECT ID, DATE, CONTENT, ID_ALBUM, ID_USER FROM COMMENTARY WHERE ID_ALBUM = ?;";

	@Override
	public List<Comments> getCommentsByAlbumId(Long albumId)
			throws DAOException {

		System.out
				.println("Trying to get Album comments from data base, album id:"
						+ albumId);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Comments> commentsList = new ArrayList<Comments>();
		Comments comments = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_LIST_PAR_NOM, false, albumId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				comments = map(resultSet);
				commentsList.add(comments);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return commentsList;
	}

}
