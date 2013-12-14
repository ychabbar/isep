package com.isepfm.controller;

import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Person;
import com.isepfm.beans.Album;
import com.isepfm.beans.Comments;
import com.isepfm.beans.Track;
import com.isepfm.dao.DAOFactory;
import com.isepfm.manager.AlbumServiceManager;
import com.isepfm.manager.ArtistServiceManager;
import com.isepfm.manager.OtherServiceManager;
import com.isepfm.manager.ServiceManager;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ICommentsDao;
import com.isepfm.service.IOtherDao;
import com.isepfm.service.ITrackDao;
import com.isepfm.service.IUsersDao;

@Controller
@RequestMapping("/album")
public class AlbumController {
	public static final String CONF_DAO_FACTORY = "daofactory";
	private AlbumServiceManager albumServiceManager;

	@Autowired
	private ServletContext context;
	private IAlbumDao albumDao;
	private IArtistDao artistDao;
	private ITrackDao trackDao;
	private ArtistServiceManager artistServiceManager;
	private IOtherDao otherDAO;
	private IUsersDao userDAO;
	private OtherServiceManager otherServiceManager;
	private ICommentsDao commentsDao;

	@PostConstruct
	public void init() {
		this.artistDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getArtistDao();

		this.albumDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getAlbumDao();

		this.otherDAO = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getOtherDao();

		this.trackDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getTrackDao();

		this.userDAO = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getUserDao();

		this.commentsDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getCommentsDao();

		artistServiceManager = new ArtistServiceManager(albumDao, artistDao,
				trackDao);

		albumServiceManager = new AlbumServiceManager(albumDao, artistDao,
				trackDao);

		otherServiceManager = new OtherServiceManager(otherDAO, albumDao,
				userDAO, commentsDao);
	}

	/*
	 * @RequestMapping(value = "name={name}", method = RequestMethod.GET) public
	 * String getArtistByName(@PathVariable String name, ModelMap model) {
	 * 
	 * Album albumFromDB = this.albumDao.getAlbumByName(name);
	 * 
	 * }
	 * 
	 * /*
	 * 
	 * @RequestMapping(value = "name={name}", method = RequestMethod.GET) public
	 * String getArtistByName(@PathVariable String name, ModelMap model) {
	 * 
	 * Album albumFromDB = this.albumDao.getAlbumByName(name);
	 * 
	 * // If album found IN DB -> show description if (albumFromDB != null) {
	 * System.out.println(name + " was found in db, object is not null");
	 * model.addAttribute("albumName", albumFromDB.getName());
	 * model.addAttribute("description", albumFromDB.getDescription());
	 * model.addAttribute("releaseDate", albumFromDB.getReleaseDate());
	 * model.addAttribute("category", albumFromDB.getCategory());
	 * 
	 * return "album"; } else { // If no artist is found -> return an error page
	 * System.out.println(name + " not found on DB, the object is null"); return
	 * "/search"; } }
	 * 
	 * @RequestMapping(value = "/id={id}", method = RequestMethod.GET) public
	 * String getArtistById(@PathVariable int id, ModelMap model) {
	 * 
	 * Album albumFromDB = this.albumDao.getAlbumById(id);
	 * 
	 * // If artist found IN DB -> show description if (albumFromDB != null) {
	 * System.out.println(id +
	 * " id of alum was found in db, object is not null");
	 * model.addAttribute("albumName", albumFromDB.getName());
	 * model.addAttribute("albumDescription", albumFromDB.getDescription());
	 * return "artist"; } else { // If no artist is found -> return an error
	 * page System.out.println(id +
	 * "id of artist not found on DB, the object is null"); return "/search"; }
	 * }
	 */

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String lienVersAlbum(@PathVariable String name, ModelMap model,
			HttpSession session) {

		Album albumFromDB = this.albumDao.getAlbumByName(name);

		// If album found IN DB -> show description
		if (albumFromDB != null) {

			boolean isLike = true;
			boolean isNote = true;

			model.addAttribute("albumFromDB", albumFromDB);

			ServiceManager artistName = new ServiceManager(artistDao);
			String artist = artistName.getArtistNameByAlbumId(albumFromDB);

			List<Track> trackList = artistServiceManager
					.getTracksByAlbum(albumFromDB.getName());
			List<String> trackDuration = artistServiceManager
					.convertIntToTime(trackList);

			String albumFolder = albumFromDB.getName().replaceAll("\\W", "");

			// On récupere le nom de l'utilisateur de la personne connectée

			String userName = (String) session
					.getAttribute("sessionUtilisateurName");

			System.out.println(name + " was found in db, object is not null");
			model.addAttribute("albumName", albumFromDB.getName());
			model.addAttribute("description", albumFromDB.getDescription());
			model.addAttribute("releaseDate", albumFromDB.getReleaseDate());
			model.addAttribute("category", albumFromDB.getCategory());

			model.addAttribute("artist", artist);

			model.addAttribute("trackList", trackList);
			model.addAttribute("trackDuration", trackDuration);

			model.addAttribute("albumFolder", albumFolder);

			// Récupération des commentaires
			model.addAttribute("Comments", new Comments());
			List<Comments> commentsList = otherServiceManager
					.getAlbumCommentsByAlbumId(albumFromDB.getId());

			List<String> userNameList = otherServiceManager
					.returnUserNameByCommentId(commentsList);

			model.addAttribute("commentsList", commentsList);
			model.addAttribute("userNameList", userNameList);

			// On va verifier si l'utilisateur a deja liké l'album
			if (userName != null) {
				isLike = otherDAO.ifLikeAlbumOfUserExist(userName,
						albumFromDB.getName());
			}

			model.addAttribute("isLike", isLike);

			// On va verifier si l'utilisateur a deja noté l'album
			if (userName != null) {
				isNote = otherDAO.ifNoteAlbumOfUserExist(userName,
						albumFromDB.getName());
			}

			model.addAttribute("isNote", isNote);

			// Et recupère egalement le nombre de note sur l'album ainsi que la
			// moyenne des notes
			int nbVote;
			double moyVote;

			nbVote = otherDAO.nbNoteAlbumOfUsers(albumFromDB.getName());
			moyVote = otherDAO.moyenneNoteAlbumOfUsers(albumFromDB.getName());

			model.addAttribute("nbVote", nbVote);
			model.addAttribute("moyVote", moyVote);

			return "album";
		} else {
			// If no artist is found -> return an error page
			System.out.println(name + " not found on DB, the object is null");
			return "/search";
		}
	}

	/*
	 * @RequestMapping(value = "{name}/{note}", method = RequestMethod.POST)
	 * public String addPerson(@ModelAttribute("person") Person person,
	 * BindingResult result) {
	 * 
	 * personService.addPerson(person);
	 * 
	 * return "redirect:/people/"; }
	 */

	/*
	 * @RequestMapping(value = "/add", method = RequestMethod.GET) public void
	 * addAlbum() {
	 * 
	 * /*
	 * 
	 * @RequestMapping(value = "/add", method = RequestMethod.GET) public void
	 * addAlbum() {
	 * 
	 * Album album = new Album(); album.setDescription("DEscription 1");
	 * album.setName("Artist toto nom"); album.setCategory("Rock");
	 * album.setReleaseDate(new Date(13, 11, 30));
	 * this.albumDao.createAlbum(album);
	 * 
	 * }
	 */

	@RequestMapping(value = "/api/&albumname={albumName}", method = RequestMethod.GET)
	public @ResponseBody
	Album getAlbum(@PathVariable String albumName) {
		System.out.println("Creating state object");
		Album album = albumServiceManager.getAlbumByName(albumName);

		return album;
	}

	/**
	 * Permet d'ajouter un like à un album
	 * 
	 * @param albumName
	 *            nom de l'album
	 * @param model
	 *            nom du model qui sera envoyé a la page JSP
	 * @param session
	 *            récupère la session de l'utilisateur
	 * @return
	 */
	@RequestMapping(value = "/{albumName}/like", method = RequestMethod.GET)
	public String addLike(@PathVariable String albumName, ModelMap model,
			HttpSession session) {

		String userName = (String) session
				.getAttribute("sessionUtilisateurName");

		if (userName != null) {
			otherServiceManager.addLikeAlbumUser(userName, albumName);

		}

		return lienVersAlbum(albumName, model, session);
	}

	@RequestMapping(value = "{albumName}/addNote", method = RequestMethod.POST)
	public String addPerson(@PathVariable String albumName,
			@ModelAttribute("rating") String note, BindingResult result,
			HttpSession session, ModelMap model) {

		String userName = (String) session
				.getAttribute("sessionUtilisateurName");

		if (userName != null & note != null) {
			int note2 = Integer.parseInt(note);
			otherServiceManager.addNoteAlbumUser(note2, userName, albumName);
		}

		return lienVersAlbum(albumName, model, session);
	}

	@RequestMapping(value = "Recovery/addComment", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("toto") String toto,
			@ModelAttribute("userName") String userName,
			@ModelAttribute("comment") Comments currentComment) {

		// System.out.println(currentComment.getContent());
		Comments comment = new Comments();
		comment.setContent(currentComment.getContent());
		comment.setId_album(currentComment.getId_album());
		// comment.setContent(tata);
		// comment.setDate(new Date(2013, 10, 10));
		// comment.setId_album(new Long(1921));fF
		// comment.setIdUser(new Long(1));
		// System.out.println(comment.getDate());
		this.otherServiceManager.insertCommentWithUserName(comment, userName);

		// System.out.println(tata + "____" + toto);

		return "albums";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person person,
			BindingResult result) {

		// personService.addPerson(person);

		return "redirect:/people/";
	}
}
