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
import com.isepfm.beans.Artist;
import com.isepfm.dao.DAOException;
import com.isepfm.dao.DAOFactory;

public class AlbumImp implements IAlbumDao {

	private DAOFactory daoFactory;

	public AlbumImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static final String SQL_SELECT_PAR_NOM = "SELECT ID, ALBUM_NAME, DESCRIPTION, RELEASE_DATE FROM ALBUMS WHERE ALBUM_NAME = ?";

	@Override
	public Album getAlbumByName(String name) throws DAOException {
		System.out.println("Trying to get Album from data base, album name:"
				+ name);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Album album = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_NOM, false, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				album = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return album;

	}

	private static final String SQL_SELECT_ALL_ALBUM_BY_APPROX_NAME = "SELECT ID, ALBUM_NAME, DESCRIPTION, RELEASE_DATE FROM ALBUMS WHERE ALBUM_NAME like %?%";

	@Override
	public ArrayList<Album> getListAlbumByNameApprox(String name)
			throws DAOException {
		System.out.println("Trying to get Album from data base, album name:"
				+ name);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList listAlb = new ArrayList();
		Album album = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ALL_ALBUM_BY_APPROX_NAME, false, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				album = map(resultSet);
				listAlb.add(album);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listAlb;

	}

	private static final String SQL_SELECT_ALBUM_BY_GENRE = "SELECT a.ID, a.ALBUM_NAME, a.DESCRIPTION, a.RELEASE_DATE "
			+ "FROM ALBUMS a "
			+ "INNER JOIN ALBUMS_GENRES ag ON a.ID = ag.ID_ALBUM "
			+ "INNER JOIN GENRES g on g.ID = ag.ID_GENRE "
			+ "WHERE g.MUSICAL_GENRE like ? "
			+ "LIMIT ? ";

	@Override
	public ArrayList<Album> getListAlbumByGenre(String genre, int limitAlbum)
			throws DAOException {
		System.out
				.println("Trying to get Album from data base, Genre:" + genre);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList listAlb = new ArrayList();
		Album album = null;

		// On va faire un like dans la requete donc on prepare notre parametre
		// Afin que l'on fasse la recherche de manière approximative
		genre = "%" + genre + "%";

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ALBUM_BY_GENRE, false, genre, limitAlbum);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				album = map(resultSet);
				listAlb.add(album);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listAlb;

	}

	private static final String SQL_SELECT_ALBUM_BY_ARTIST = "SELECT a.ID, a.ALBUM_NAME, a.DESCRIPTION, a.RELEASE_DATE "
			+ "FROM ALBUMS a "
			+ "INNER JOIN ALBUMS_ARTISTS aa ON a.ID = aa.ID_ALBUM "
			+ "INNER JOIN ARTISTS art on art.ID = aa.ID_ARTIST "
			+ "WHERE art.ARTIST_NAME like ?"
			+ "LIMIT ? ";

	@Override
	public ArrayList<Album> getListAlbumByArtist(String artist , int limitAlbum)
			throws DAOException {
		System.out.println("Trying to get Album from data base, artist:"
				+ artist);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList listAlb = new ArrayList();
		Album album = null;

		// On va faire un like dans la requete donc on prepare notre parametre
		// Afin que l'on fasse la recherche de manière approximative
		artist = "%" + artist + "%";

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ALBUM_BY_ARTIST, false, artist, limitAlbum);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				album = map(resultSet);
				listAlb.add(album);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listAlb;

	}

	private static final String SQL_SELECT_ALBUM_BY_TRACK = "SELECT a.ID, a.ALBUM_NAME, a.DESCRIPTION, a.RELEASE_DATE "
			+ "FROM ALBUMS a "
			+ "INNER JOIN ALBUMS_SONGS albson ON a.ID = albson.ID_ALBUM "
			+ "INNER JOIN SONGS s on s.ID = albson.ID_SONG "
			+ "WHERE s.SONG like ? "
			+ "LIMIT ? ";

	@Override
	public ArrayList<Album> getListAlbumByTrack(String track , int limitAlbum)
			throws DAOException {
		System.out
				.println("Trying to get Album from data base, track:" + track);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList listAlb = new ArrayList();
		Album album = null;
		// On va faire un like dans la requete donc on prepare notre parametre
		// Afin que l'on fasse la recherche de manière approximative
		track = "%" + track + "%";

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ALBUM_BY_TRACK, false, track, limitAlbum);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				album = map(resultSet);
				listAlb.add(album);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listAlb;

	}

	private static final String SQL_SELECT_PAR_ID = "SELECT ID, ALBUM_NAME, CATEGORY, DESCRIPTION, RELEASE_DATE FROM ALBUMS WHERE ID = ?";

	@Override
	public Album getAlbumById(int id) throws DAOException {
		System.out
				.println("Trying to get Album from data base, album id:" + id);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Album album = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_ID, false, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				album = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return album;

	}

	private static final String SQL_INSERT = "INSERT INTO ALBUMS (ALBUM_NAME, DESCRIPTION, RELEASE_DATE) VALUES (?, ?, ?)";

	@Override
	public void createAlbum(Album album) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, album.getName(),
					album.getDescription(), album.getReleaseDate());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création de l'album, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propriété id du bean Utilisateur
				 * avec sa valeur
				 */
				album.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException(
						"Échec de la création de l'album en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	private static final String SQL_UPDATE = "UPDATE ALBUMS SET ALBUM_NAME =  ?, DESCRIPTION = ?, RELEASE_DATE = ? WHERE ID = ?;";

	@Override
	public void updateAlbum(Album album) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, album.getName(), album.getCategory(),
					album.getDescription(), album.getReleaseDate(),
					album.getId());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la modification de l'album, aucune ligne modifiée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	private static Album map(ResultSet resultSet) throws SQLException {
		Album albumDB = new Album();
		albumDB.setId(resultSet.getLong("ID"));
		albumDB.setName(resultSet.getString("ALBUM_NAME"));
		albumDB.setReleaseDate(resultSet.getDate("RELEASE_DATE"));
		albumDB.setDescription(resultSet.getString("DESCRIPTION"));
		return albumDB;
	}

	private static final String SQL_DELETE_ALBUM = "DELETE FROM ALBUMS WHERE ALBUM_NAME = ?";
	@Override
	public void deleteAlbum(String ALBUM_NAME) throws IllegalArgumentException,DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int status;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_ALBUM, false, ALBUM_NAME);
			status = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

	}

	private static final String SQL_IFALBUMEXIST = "SELECT id FROM ALBUMS WHERE ALBUM_NAME = ?";
	
	@Override
	public boolean ifAlbumExist(String ALBUM_NAME) throws IllegalArgumentException,
			DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean userExist = false;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_IFALBUMEXIST, false, ALBUM_NAME);
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

	private static final String SQL_SELECT_RANDOM = "SELECT ID, ALBUM_NAME, DESCRIPTION, RELEASE_DATE FROM ALBUMS ORDER BY RAND() LIMIT ?";

	@Override
	public List<Album> getRandomAlbum(int numberOfReturn) throws DAOException {
		List<Album> albumList = new ArrayList<Album>();
		System.out.println("Trying to get Artist from data base, number of returns:"+ numberOfReturn);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Album album = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_RANDOM, false, numberOfReturn);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				album = new Album();
				album = map(resultSet);
				albumList.add(album);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return albumList;
	}

	private static final String SQL_SELECT_TOP_ALBUM_BY_ARTIST = "SELECT ID, ALBUM_NAME, DESCRIPTION, RELEASE_DATE FROM ALBUMS WHERE ID IN (SELECT ID FROM (SELECT ID_ALBUM AS ID, AVG(NOTE) FROM (SELECT ID_ALBUM, NOTE FROM NOTE WHERE ID_ALBUM IN (SELECT a.ID FROM ALBUMS a INNER JOIN ALBUMS_ARTISTS aa ON a.ID = aa.ID_ALBUM INNER JOIN ARTISTS art ON art.ID = aa.ID_ARTIST WHERE art.ARTIST_NAME = ?)) AS TEMP GROUP BY ID_ALBUM DESC LIMIT 3) AS TEMP3)";

	@Override
	public List<Album> getTopAlbumByArtist(String artistName)
			throws DAOException {
		System.out.println("Trying to get top Album from data base, artist:"
				+ artistName);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Album> listAlb = new ArrayList<Album>();
		Album album = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_TOP_ALBUM_BY_ARTIST, false, artistName);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				album = map(resultSet);

				listAlb.add(album);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listAlb;
	}

}
