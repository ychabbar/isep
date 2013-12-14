package com.isepfm.service;

import static com.isepfm.dao.DAOUtilities.fermeturesSilencieuses;
import static com.isepfm.dao.DAOUtilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Track;
import com.isepfm.dao.DAOException;
import com.isepfm.dao.DAOFactory;

public class TrackImp implements ITrackDao {

	private DAOFactory daoFactory;

	public TrackImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static final String SQL_SELECT_PAR_NOM = "SELECT ID, SONG, DURATION FROM SONGS WHERE SONG = ?";

	@Override
	public Track getTrackByName(String name) throws DAOException {
		System.out.println("Trying to get Track from data base, track name:"
				+ name);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Track track = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_NOM, false, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				track = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return track;

	}

	private static final String SQL_SELECT_PAR_ID = "SELECT ID, ALBUM_NAME, CATEGORY, DESCRIPTION, RELEASE_DATE FROM SONGS WHERE ID = ?";

	@Override
	public Track getTrackById(int id) throws DAOException {
		System.out
				.println("Trying to get track from data base, track id:" + id);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Track track = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_ID, false, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				track = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return track;

	}

	private static final String SQL_SELECT_ALL_TRACK_BY_NAME = "SELECT ID, ALBUM_NAME, CATEGORY, DESCRIPTION, RELEASE_DATE FROM SONGS WHERE NAME LIKE %?%";

	@Override
	public ArrayList<Track> getAllTrackByName(String name) throws DAOException {
		System.out.println("Trying to get track from data base, track id:"
				+ name);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList listTrack = new ArrayList();
		Track track = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ALL_TRACK_BY_NAME, false, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				track = map(resultSet);
				listTrack.add(track);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listTrack;

	}

	private static final String SQL_INSERT = "INSERT INTO SONGS (SONG, DURATION) VALUES (?, ?)";

	@Override
	public void createTrack(Track track) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, track.getTrackName(), track.getDuration());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création du titre, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propriété id du bean Utilisateur
				 * avec sa valeur
				 */
				track.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException(
						"Échec de la création d'un titre en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	private static final String SQL_UPDATE = "UPDATE SONGS SET SONG =  ?, DURATION = ? WHERE ID = ?;";

	@Override
	public void updateTrack(Track track) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, track.getTrackName(), track.getDuration());
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

	private static final String SQL_DELETE_TRACK = "DELETE FROM SONGS WHERE  SONG = ?";
	@Override
	public void deleteTrack(String SONG) throws IllegalArgumentException,DAOException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int status;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_TRACK, false, SONG);
			status = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

	}

	private static final String SQL_IFTRACKEXIST = "SELECT id FROM SONGS WHERE SONG = ?";
	
	@Override
	public boolean ifTrackExist(String SONG) throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean userExist = false;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_IFTRACKEXIST, false, SONG);
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

	
		
		
		

	

	/**
	 * Permet de mapper un Objet avec les résulats trouvés dans la base de
	 * donnée
	 * 
	 * @param resultSet
	 * @return Retourne un objet complété avec les résultats de la base de
	 *         données
	 * @throws SQLException
	 */
	private static Track map(ResultSet resultSet) throws SQLException {
		Track trackDB = new Track();
		trackDB.setId(resultSet.getLong("ID"));
		trackDB.setTrackName(resultSet.getString("SONG"));
		trackDB.setDuration(resultSet.getInt("DURATION"));

		return trackDB;
	}

	private static final String SQL_SELECT_TRACKS_BY_ARTIST_NAME = "SELECT ID, SONG, DURATION FROM SONGS WHERE ID IN (SELECT ID_SONG FROM ALBUMS_SONGS WHERE ID_ALBUM IN (SELECT ID_ALBUM FROM ALBUMS_ARTISTS WHERE ID_ARTIST = (SELECT ID FROM ARTISTS WHERE ARTIST_NAME = ?)));";

	@Override
	public List<Track> getTrackListByArtistName(String artistName)
			throws DAOException {

		System.out
				.println("Trying to get track by artist name from data base, artist name :"
						+ artistName);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Track> listTrack = new ArrayList<Track>();
		Track track = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_TRACKS_BY_ARTIST_NAME, false, artistName);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
			
				track = map(resultSet);
				listTrack.add(track);
				
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listTrack;
	}
	
	
	private static final String SQL_SELECT_TRACKS_BY_ALBUM_NAME = "SELECT s.ID, s.SONG, s.DURATION FROM SONGS s "
			+ "INNER JOIN ALBUMS_SONGS albSon ON albSon.id_song = s.id "
			+ "INNER JOIN ALBUMS alb ON alb.id = albSon.id_album "
			+ "WHERE ALBUM_NAME = ? ";

	@Override
	public List<Track> getTracksByAlbum(String albumName)
			throws DAOException {

		System.out
				.println("Trying to get track by artist name from data base, artist name :"
						+ albumName);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Track> listTrack = new ArrayList<Track>();
		Track track = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_TRACKS_BY_ALBUM_NAME, false, albumName);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
			
				track = map(resultSet);
				listTrack.add(track);
				
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listTrack;
	}

}
