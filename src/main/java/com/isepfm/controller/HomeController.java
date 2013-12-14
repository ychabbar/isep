package com.isepfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.dao.DAOFactory;
import com.isepfm.manager.AlbumServiceManager;
import com.isepfm.manager.ArtistServiceManager;
import com.isepfm.manager.HomeServiceManager;
import com.isepfm.manager.OtherServiceManager;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ICommentsDao;
import com.isepfm.service.IOtherDao;
import com.isepfm.service.ITrackDao;
import com.isepfm.service.IUsersDao;

@Controller
public class HomeController {
	public static final String CONF_DAO_FACTORY = "daofactory";
	@Autowired
	private ServletContext context;
	private IArtistDao artistDao;
	private IAlbumDao albumDao;
	private IOtherDao otherDao;
	private ITrackDao trackDao;
	private ICommentsDao commentsDao;
	private IUsersDao usersDao;
	private HomeServiceManager homeServiceManager;
	private OtherServiceManager otherServiceManager;
	private ArtistServiceManager artistServiceManager;

	@PostConstruct
	public void init() {
		this.artistDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getArtistDao();

		this.albumDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getAlbumDao();
		
		this.otherDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getOtherDao();

		this.commentsDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getCommentsDao();
		
		this.usersDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getUserDao();
		
		homeServiceManager = new HomeServiceManager(albumDao, artistDao);

		otherServiceManager = new OtherServiceManager(otherDao, albumDao,
				usersDao, commentsDao);
		
		artistServiceManager = new ArtistServiceManager(albumDao, artistDao,
				trackDao);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap model, Map<Object, Object> map) {

		
		List<Artist> artistList = homeServiceManager.getRandomArtist(6);
		model.addAttribute("artistList", artistList);

		List<Album> albumList = homeServiceManager.getRandomAlbum(6);
		
		
		List<String> artistRandomList = new ArrayList<String>();
		List<String> albumFolder = new ArrayList<String>();

		for (int i = 0; i < albumList.size(); i++) 
		{
			String artistName = artistServiceManager.getArtistNameByAlbumId(albumList.get(i).getId());
			artistRandomList.add(artistName);
			albumFolder.add(albumList.get(i).getName().replaceAll("\\W", ""));
		}
		
		map.put("album", albumList);
		map.put("albumArtist", artistRandomList);
		map.put("albumFolder", albumFolder);
		
		
		//On recupère le nombre de vote d'une album
		List<Integer> nbNotes = new ArrayList<Integer>();
		
		for(int i=0; i<albumList.size(); i++)
		{				
			int nbNote = otherServiceManager.nbAllNoteArtistAlbums(albumList.get(i).getName());
			nbNotes.add(nbNote);		
		}		
	
		map.put("nbNotes", nbNotes);
		
		
		//On va gerer la partie Top Like des albums 
		//On recupere le lien des images pour le top like album
		List<Object> topLikeAlbumAndLike = otherServiceManager.topLikeAlbums();
		List<String> topLikeAlbum = new ArrayList<String>();
		List<String> topNbLike = new ArrayList<String>();
		
		List<String> albumFolder2 = new ArrayList<String>();
		List<String> artistList2 = new ArrayList<String>();
		
		for(int i=0; i<topLikeAlbumAndLike.size(); i++)
		{	
			//Si le nombre est pair on a un album sinon on a son nombre de like
			if(i%2!=1 || i == 0)
			{
				String topAlb = (String)topLikeAlbumAndLike.get(i);		
				
				System.out.println("ALBUM " + topAlb);
				
				String artistName2 = artistServiceManager.getArtistNameByAlbumName(topAlb);
				
				System.out.println("ARTISTE " + artistName2);
				artistList2.add(artistName2);

				topLikeAlbum.add(topAlb);
				albumFolder2.add(topAlb.replaceAll("\\W", ""));
				
				
			}
			else
			{
				String topLike = (String)topLikeAlbumAndLike.get(i);
				topNbLike.add(topLike);
			}
					
		}
				
		map.put("topLikeAlbum", topLikeAlbum);
		map.put("topNbLike", topNbLike);
		map.put("albumFolder2", albumFolder2);
		map.put("artistList2", artistList2);

		return "index";
	}
}
