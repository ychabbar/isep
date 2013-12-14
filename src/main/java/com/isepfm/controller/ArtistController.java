package com.isepfm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.beans.Track;
import com.isepfm.dao.DAOFactory;
import com.isepfm.manager.ArtistServiceManager;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ITrackDao;

/**
 * @author richard
 * 
 */
/**
 * @author fixe-richard
 * 
 */
@Controller
@RequestMapping("/artist")
public class ArtistController {
	public static final String CONF_DAO_FACTORY = "daofactory";

	@Autowired
	private ServletContext context;
	private IArtistDao artistDao;
	private IAlbumDao albumDao;
	private ITrackDao trackDao;
	private ArtistServiceManager artistServiceManager;

	@PostConstruct
	public void init() {
		System.out.println("INIT OF ARTIST CONTROLLER");
		this.artistDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getArtistDao();

		this.albumDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getAlbumDao();

		this.trackDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getTrackDao();

		artistServiceManager = new ArtistServiceManager(albumDao, artistDao,
				trackDao);
	}

	/**
	 * Controleur d'affichage d'un artiste
	 * 
	 * @param name
	 *            nom de l'artiste à afficher
	 * @param model
	 * @return retourne la page d'affichage d'un artiste si un résultat est
	 *         trouvé
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getArtistByName(@PathVariable String name, ModelMap model) {
		/*
		 * ArtistServiceManager artistServiceManager = new ArtistServiceManager(
		 * albumDao, artistDao, trackDao);
		 */
		Artist artistFromDB = artistServiceManager.getArtistByName(name);

		// If artist found IN DB -> show description
		if (artistFromDB != null) {
			System.out.println(name + " was found in db, object is not null");
			model.addAttribute("artist", artistFromDB);

		} else {
			// If no artist is found -> return an error page
			System.out.println(name + " not found on DB, the object is null");
			return "/search";
		}
		// Récupération List album/artist and tracks
		List<Album> albumList = artistServiceManager
				.getAlbumByArtist(artistFromDB.getName(), 100);

		List<Track> trackList = artistServiceManager
				.getTrackListByAlbumName(artistFromDB.getName());

		List<String> trackDuration = artistServiceManager
				.convertIntToTime(trackList);

		System.out.println(trackDuration.size());
		model.addAttribute("trackList", trackList);
		model.addAttribute("trackDuration", trackDuration);

		// Récupération TOP 3 albums
		List<Album> topAlbum = artistServiceManager
				.getTopAlbumByArtist(artistFromDB.getName());

		List<String> albumPath = artistServiceManager
				.getAlbumFolderPath(topAlbum);

		model.addAttribute("albumList", topAlbum);
		model.addAttribute("albumPath", albumPath);

		return "artist";
	}

	@RequestMapping(value = "/id={id}", method = RequestMethod.GET)
	public String getArtistById(@PathVariable int id, ModelMap model) {

		Artist artistFromDB = this.artistDao.getArtistById(id);

		// If artist found IN DB -> show description
		if (artistFromDB != null) {
			System.out.println(id
					+ " id of artist was found in db, object is not null");
			model.addAttribute("artistName", artistFromDB.getName());
			model.addAttribute("description", artistFromDB.getDescription());
			return "artist";
		} else {
			// If no artist is found -> return an error page
			System.out.println(id
					+ "id of artist not found on DB, the object is null");
			return "/search";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void addArtist() {

	}

	/**
	 * WebService renvoyant un artiste par son nom
	 * 
	 * @param artistName
	 * @return
	 */
	@RequestMapping(value = "/api/&artistname={artistName}", method = RequestMethod.GET)
	public @ResponseBody
	Artist getArtist(@PathVariable String artistName) {
		System.out.println("Creating state object");
		Artist artist = artistServiceManager.getArtistByName(artistName);

		return artist;
	}
}
