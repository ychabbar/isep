package com.isepfm.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.isepfm.service.AlbumImp;
import com.isepfm.service.ArtistImp;
import com.isepfm.service.CommentsImp;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ICommentsDao;
import com.isepfm.service.IOtherDao;
import com.isepfm.service.ITrackDao;
import com.isepfm.service.IUsersDao;
import com.isepfm.service.OtherImp;
import com.isepfm.service.TrackImp;
import com.isepfm.service.UsersImp;

public class DAOFactory {

	private static final String FICHIER_PROPERTIES = "app/com/isepfm/dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
	private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

	private String url;
	private String username;
	private String password;

	public DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/*
	 * Méthode chargée de récupérer les informations de connexion à la base de
	 * données, charger le driver JDBC et retourner une instance de la Factory
	 */
	public static DAOFactory getInstance() throws DAOConfigurationException {

		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;
		System.out.println("Loading dao properties");
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("java.class.path"));

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream fichierProperties = classLoader
				.getResourceAsStream(FICHIER_PROPERTIES);
		/*
		 * if (fichierProperties == null) { throw new
		 * DAOConfigurationException("Le fichier properties " +
		 * FICHIER_PROPERTIES + " est introuvable."); }
		 * 
		 * try { properties.load(fichierProperties); url =
		 * properties.getProperty(PROPERTY_URL); driver =
		 * properties.getProperty(PROPERTY_DRIVER); nomUtilisateur =
		 * properties.getProperty(PROPERTY_NOM_UTILISATEUR); motDePasse =
		 * properties.getProperty(PROPERTY_MOT_DE_PASSE);
		 * 
		 * } catch (IOException e) {
		 * 
		 * throw new DAOConfigurationException(
		 * "Impossible de charger le fichier properties " + FICHIER_PROPERTIES,
		 * e); }
		 */

		url = "jdbc:mysql://localhost:3306/bdd_isepfm?zeroDateTimeBehavior=convertToNull";
		driver = "com.mysql.jdbc.Driver";

		nomUtilisateur = "root";
		motDePasse = "";

		try {

			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException(
					"Le driver est introuvable dans le classpath.", e);
		}

		System.out.println(url + " " + driver);
		DAOFactory instance = new DAOFactory(url, nomUtilisateur, motDePasse);
		return instance;
	}

	/* Méthode chargée de fournir une connexion à la base de données */
	/* package */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public IUsersDao getUserDao() {
		System.out.println("getUserDao function");
		return new UsersImp(this);
	}

	public IArtistDao getArtistDao() {
		System.out.println("getArtistDao function");
		return new ArtistImp(this);
	}

	public IAlbumDao getAlbumDao() {
		System.out.println("getAlbumDao function");
		return new AlbumImp(this);
	}

	public ITrackDao getTrackDao() {
		System.out.println("getTrack function");
		return new TrackImp(this);
	}

	public IOtherDao getOtherDao() {
		System.out.println("getTrack function");
		return new OtherImp(this);
	}

	public ICommentsDao getCommentsDao() {
		return new CommentsImp(this);
	}

}