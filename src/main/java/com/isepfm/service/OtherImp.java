package com.isepfm.service;

import static com.isepfm.dao.DAOUtilities.fermeturesSilencieuses;
import static com.isepfm.dao.DAOUtilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Album;
import com.isepfm.beans.Track;
import com.isepfm.dao.DAOException;
import com.isepfm.dao.DAOFactory;

public class OtherImp implements IOtherDao {

	private DAOFactory daoFactory;

	public OtherImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static final String SQL_IFLIKEALBUMOFUSEREXIST = "SELECT u.id "
			+ "FROM USERS u " + "INNER JOIN `LIKE` l on l.id_user = u.id "
			+ "INNER JOIN ALBUMS alb on alb.id = l.id_album "
			+ "WHERE u.name = ? " + "AND alb.album_name = ? ";

	@Override
	public boolean ifLikeAlbumOfUserExist(String userName, String albumName)
			throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean likeExist = false;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_IFLIKEALBUMOFUSEREXIST, false, userName, albumName);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			if (resultSet.next()) {
				likeExist = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return likeExist;

	}

	private static final String SQL_ADD_LIKE_ALBUM_USER = "INSERT INTO `LIKE` (date_like, id_album, id_user) VALUES (NOW(), ?, ?)";

	@Override
	public void addLikeAlbumUser(long idAlbum, long idUser)
			throws IllegalArgumentException, DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_ADD_LIKE_ALBUM_USER, true, idAlbum, idUser);
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la modification du titre, aucune ligne modifiée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	private static final String SQL_IFNOTEALBUMOFUSEREXIST = "SELECT u.id "
			+ "FROM USERS u " + "INNER JOIN NOTE n on n.id_user = u.id "
			+ "INNER JOIN ALBUMS alb on alb.id = n.id_album "
			+ "WHERE u.name = ? " + "AND alb.album_name = ? ";

	@Override
	public boolean ifNoteAlbumOfUserExist(String userName, String albumName)
			throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean noteExist = false;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_IFNOTEALBUMOFUSEREXIST, false, userName, albumName);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			if (resultSet.next()) {
				noteExist = true;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return noteExist;

	}

	private static final String SQL_ADD_NOTE_ALBUM_USER = "INSERT INTO NOTE (note, date, id_album, id_user) VALUES (?, NOW(), ?, ?)";

	@Override
	public void addNoteAlbumUser(int note, long idAlbum, long idUser)
			throws IllegalArgumentException, DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_ADD_NOTE_ALBUM_USER, true, note, idAlbum, idUser);
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la modification du titre, aucune ligne modifiée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	private static final String SQL_MOY_ALBUM_NOTE = "SELECT AVG( n.note ) as moy "
			+ "FROM NOTE n "
			+ "INNER JOIN ALBUMS alb ON alb.id = n.id_album "
			+ "WHERE alb.album_name = ? ";

	@Override
	public double moyenneNoteAlbumOfUsers(String albumName)
			throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		double moyenne = 0;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_MOY_ALBUM_NOTE, false, albumName);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			if (resultSet.next()) {
				moyenne = resultSet.getDouble("moy");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return moyenne;

	}

	private static final String SQL_NB_ALBUM_NOTE = "SELECT count( n.note ) as nb "
			+ "FROM NOTE n "
			+ "INNER JOIN ALBUMS alb ON alb.id = n.id_album "
			+ "WHERE alb.album_name = ? ";

	@Override
	public int nbNoteAlbumOfUsers(String albumName)
			throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int nbNote = 0;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_NB_ALBUM_NOTE, false, albumName);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			if (resultSet.next()) {
				nbNote = resultSet.getInt("nb");
				;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return nbNote;

	}

	private static final String SQL_NB_ARTIST_NOTE = "SELECT COUNT( n.note ) AS nb "
			+ "FROM NOTE n "
			+ "INNER JOIN ALBUMS alb ON alb.id = n.id_album "
			+ "INNER JOIN ALBUMS_ARTISTS albArt ON albArt.id_album = alb.id "
			+ "INNER JOIN ARTISTS art ON art.id = albArt.id_artist "
			+ "WHERE art.artist_name = ? ";

	@Override
	public int nbAllNoteArtistAlbums(String artistName) throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int nbNote = 0;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_NB_ARTIST_NOTE, false, artistName);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			if (resultSet.next()) {
				nbNote = resultSet.getInt("nb");
				
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return nbNote;

	}
	

	
	private static final String SQL_TOP3_LIKE_ALBUM = "SELECT count(l.id_album) AS lik, alb.album_name as albname "
			+"FROM albums alb "
			+"INNER JOIN  `like` l ON l.id_album = alb.id "
			+"GROUP BY l.id_album DESC "
			+"LIMIT 3 ";

	@Override
	public List<Object> topLikeAlbums() throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Object> topAlb = new ArrayList<Object>();
		String alb = null;
		int nbLike = 0;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_TOP3_LIKE_ALBUM, false);
			resultSet = preparedStatement.executeQuery();

			// Retourne true s'il trouve quelque chose et false sinon
			while (resultSet.next()) {
				alb = resultSet.getString("albname");
				nbLike = resultSet.getInt("lik");
				String nbLike2 = Integer.toString(nbLike);
				
				topAlb.add(alb);
				topAlb.add(nbLike2);
				
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return topAlb;

	}
	
	
}
