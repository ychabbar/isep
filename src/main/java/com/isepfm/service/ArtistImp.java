package com.isepfm.service;

import static com.isepfm.dao.DAOUtilities.fermeturesSilencieuses;
import static com.isepfm.dao.DAOUtilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Artist;
import com.isepfm.dao.DAOException;
import com.isepfm.dao.DAOFactory;

public class ArtistImp implements IArtistDao {

	private DAOFactory daoFactory;

	public ArtistImp(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static final String SQL_SELECT_PAR_NOM = "SELECT ID, ARTIST_NAME, DESCRIPTION FROM ARTISTS WHERE ARTIST_NAME = ?";

	@Override
	public Artist getArtistByName(String name) throws DAOException {
		System.out.println("Trying to get Artist from data base, artiste name:"
				+ name);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Artist artist = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_NOM, false, name);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				artist = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return artist;

	}

	private static final String SQL_SELECT_PAR_ID = "SELECT ID, ARTIST_NAME, DESCRIPTION FROM ARTISTS WHERE ID = ?";

	@Override
	public Artist getArtistById(int id) throws DAOException {
		System.out.println("Trying to get Artist from data base, artiste id:"
				+ id);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Artist artist = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_PAR_ID, false, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				artist = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return artist;

	}

	private static final String SQL_SELECT_ALL_ARTIST_BY_NAME = "SELECT ID, ARTIST_NAME, DESCRIPTION FROM ARTISTS WHERE NAME like %?%";

	@Override
	public ArrayList<Artist> getListArtistByName(String name)
			throws DAOException {
		System.out.println("Trying to get Artist from data base, artiste id:"
				+ name);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList listArtist = new ArrayList();
		Artist artist = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ALL_ARTIST_BY_NAME, false, name);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				artist = map(resultSet);
				listArtist.add(artist);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return listArtist;

	}

	private static final String SQL_INSERT = "INSERT INTO ARTISTS (ARTIST_NAME, DESCRIPTION) VALUES (?, ?)";

	@Override
	public void createArtist(Artist artist) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, artist.getName(), artist.getDescription());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la création de l'artiste, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propriété id du bean Utilisateur
				 * avec sa valeur
				 */
				artist.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException(
						"Échec de la création de l'artiste en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}
	}

	private static final String SQL_UPDATE = "UPDATE ARTISTS SET ARTIST_NAME =  ?, DESCRIPTION = ? WHERE ID = ?;";

	@Override
	public void updateArtist(Artist artist) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_INSERT, true, artist.getName(),
					artist.getDescription(), artist.getId());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException(
						"Échec de la modification de l'artiste, aucune ligne modifiée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connexion);
		}

	}
	
	private static final String SQL_DELETE_ARTIST = "DELETE FROM ARTISTS WHERE  ARTIST_NAME = ?";

	@Override
	public void deleteArtist(String ARTIST_NAME) throws IllegalArgumentException,DAOException {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int status;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_ARTIST, false, ARTIST_NAME);
			status = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

	}

	private static final String SQL_IFARTISTEXIST = "SELECT id FROM ARTISTS WHERE ARTIST_NAME = ?";
	
	@Override
	public boolean ifArtistExist(String ARTIST_NAME) throws IllegalArgumentException, DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean userExist = false;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_IFARTISTEXIST, false, ARTIST_NAME);
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

	
	

	private static Artist map(ResultSet resultSet) throws SQLException {
		Artist artistDB = new Artist();
		artistDB.setId(resultSet.getLong("ID"));
		artistDB.setName(resultSet.getString("ARTIST_NAME"));
		artistDB.setDescription(resultSet.getString("DESCRIPTION"));
		return artistDB;
	}

	private static final String SQL_SELECT_RANDOM = "SELECT ID, ARTIST_NAME, DESCRIPTION FROM ARTISTS ORDER BY RAND() LIMIT ?";

	@Override
	public List<Artist> getRandomArtist(int numberOfReturn) throws DAOException {
		List<Artist> artistList = new ArrayList<Artist>();
		System.out
				.println("Trying to get Artist from data base, number of returns:"
						+ numberOfReturn);
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Artist artist = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_RANDOM, false, numberOfReturn);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				artist = new Artist();
				artist = map(resultSet);
				artistList.add(artist);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return artistList;
	}

	private static final String SQL_SELECT_ARTIST_BY_ALBUM_ID = "SELECT ARTIST_NAME FROM ARTISTS WHERE ID = (SELECT ID_ARTIST FROM ALBUMS_ARTISTS WHERE ID_ALBUM = ?)";

	@Override
	public String getArtistNameByAlbumId(long id) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String artistName = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ARTIST_BY_ALBUM_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				artistName = resultSet.getString("ARTIST_NAME");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return artistName;
	}
	
	private static final String SQL_SELECT_ARTIST_BY_ALBUM_NAME = "SELECT art.ARTIST_NAME FROM ARTISTS art "
			+"INNER JOIN ALBUMS_ARTISTS albArt ON albArt.id_artist = art.id "
			+"INNER JOIN ALBUMS alb ON alb.id = albArt.id_album "
			+"WHERE alb.album_name = ? ";

	@Override
	public String getArtistNameByAlbumName(String albumName) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String artistName = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ARTIST_BY_ALBUM_NAME, false, albumName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				artistName = resultSet.getString("ARTIST_NAME");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return artistName;
	}
	
	
	
	private static final String SQL_SELECT_NB_TRACK_BY_ALBUM_ID = "SELECT COUNT( * ) as nb FROM ALBUMS alb INNER JOIN ALBUMS_SONGS albS ON albS.id_album = alb.id INNER JOIN SONGS s ON s.id = albS.id_song WHERE alb.id = ?";
	@Override
	public String getNbTrackByAlbumId(long id) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String nbTrack = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_NB_TRACK_BY_ALBUM_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				nbTrack = resultSet.getString("nb");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return nbTrack;
	}
	
	
	private static final String SQL_SELECT_DATE_SORTIE_BY_ALBUM_ID = "SELECT release_date FROM ALBUMS WHERE id = ?";
	
	@Override
	public String getDateSortieByAlbumId(long id) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String dateSortie = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_DATE_SORTIE_BY_ALBUM_ID, false, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				dateSortie = resultSet.getString("release_date");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return dateSortie;
	}
	
	
}
