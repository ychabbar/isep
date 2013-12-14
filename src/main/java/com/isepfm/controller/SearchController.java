package com.isepfm.controller;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.isepfm.beans.Album;
import com.isepfm.dao.DAOFactory;
import com.isepfm.forms.SearchHandler;
import com.isepfm.manager.ServiceManager;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;


/**
 * @author Damien
 *
 */
/**
 * @author Damien
 *
 */
@Controller
public class SearchController {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_ALBUM_GENRE = "Album_Genre";
	public static final String ATT_ALBUM_ARTIST = "Album_Artist";
	public static final String ATT_ALBUM_TRACK = "Album_Track";
	
	@Autowired
	private ServletContext context;
	private IAlbumDao albumDao;
	private IArtistDao artistDao;

	@PostConstruct
	public void init() {
	
		this.albumDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY)).getAlbumDao();
		this.artistDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY)).getArtistDao();
		
	}
	

	
	@RequestMapping(value ="/search" ,method = RequestMethod.GET)
	public String search(@RequestParam String recherche, ModelMap model) {		
		
		SearchHandler album = new SearchHandler(albumDao);       
	   
		//Recupère les albums par genre
		ArrayList<Album> albumGenre = album.collectAlbumByGenre(recherche, 4);		
		ArrayList<Album> albumArtist = album.collectAlbumByArtist(recherche, 4);	
		ArrayList<Album> albumTrack = album.collectAlbumByTrack(recherche, 4);
		
		//On recupére la liste des artistes associées aux albums
		ServiceManager artistLists = new ServiceManager(artistDao);
		ArrayList<String> artistListGenre = artistLists.collectListArtistByAlbums(albumGenre);
		ArrayList<String> artistListArtist = artistLists.collectListArtistByAlbums(albumArtist);
		ArrayList<String> artistListTrack = artistLists.collectListArtistByAlbums(albumTrack);
		
		ArrayList<String> albumFolderGenre = artistLists.collectCheminImage(albumGenre);
		ArrayList<String> albumFolderArtist = artistLists.collectCheminImage(albumArtist);
		ArrayList<String> albumFolderTrack = artistLists.collectCheminImage(albumTrack);
		
		ArrayList<String> nbTrackByAlbumGenre = artistLists.nbTrackByAlbum(albumGenre);
		ArrayList<String> nbTrackByAlbumArtist = artistLists.nbTrackByAlbum(albumArtist);
		ArrayList<String> nbTrackByAlbumTrack = artistLists.nbTrackByAlbum(albumTrack);
		

		ArrayList<String> dateSortieAlbumGenre = artistLists.dateSortieByAlbum(albumGenre);
		ArrayList<String> dateSortieAlbumArtist = artistLists.dateSortieByAlbum(albumArtist);
		ArrayList<String> dateSortieAlbumTrack = artistLists.dateSortieByAlbum(albumTrack);
		
		//On recupère ce que l'utilisateur a tapé
		model.addAttribute("recherche", recherche);
		
		//On recupère les éléments des albums recherchés par genre 
		model.addAttribute("albumGenre", albumGenre);
		model.addAttribute("artistListGenre", artistListGenre);
		model.addAttribute("albumFolderGenre", albumFolderGenre);
		
		//On recupère les éléments des albums recherchés par artiste 
		model.addAttribute("albumArtist", albumArtist);
		model.addAttribute("artistListArtist", artistListArtist);
		model.addAttribute("albumFolderArtist", albumFolderArtist);
				
		//On recupère les éléments des albums recherchés par titre 
		model.addAttribute("albumTrack", albumTrack);
		model.addAttribute("artistListTrack", artistListTrack);
		model.addAttribute("albumFolderTrack", albumFolderTrack);
		
		//On recupère le nombre de titre des albums
		model.addAttribute("nbTrackByAlbumGenre", nbTrackByAlbumGenre);
		model.addAttribute("nbTrackByAlbumArtist", nbTrackByAlbumArtist);
		model.addAttribute("nbTrackByAlbumTrack", nbTrackByAlbumTrack);
		

		//On recupère la date de sortie des albums
		model.addAttribute("dateSortieAlbumGenre", dateSortieAlbumGenre);
		model.addAttribute("dateSortieAlbumArtist", dateSortieAlbumArtist);
		model.addAttribute("dateSortieAlbumTrack", dateSortieAlbumTrack);
		
		
		return "search";
	}	

}



