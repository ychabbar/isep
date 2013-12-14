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
import org.springframework.web.bind.annotation.RequestParam;

import com.isepfm.beans.Album;
import com.isepfm.beans.Track;
import com.isepfm.dao.DAOFactory;
import com.isepfm.forms.SearchHandler;
import com.isepfm.manager.AlbumServiceManager;
import com.isepfm.manager.ArtistServiceManager;
import com.isepfm.manager.ServiceManager;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ITrackDao;


@Controller
public class ListAlbumController {
	
	
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
		
	@RequestMapping(value ="albumsG/{recherche}" ,method = RequestMethod.GET)
	public String searchAlbumsGenre(@PathVariable String recherche, ModelMap model) {		
		
		SearchHandler album = new SearchHandler(albumDao);       
	   
		//Recupère les albums par genre
		ArrayList<Album> albumGenre = album.collectAlbumByGenre(recherche, 100);
		
		//On recupére la liste des artistes associées aux albums
		ServiceManager artistLists = new ServiceManager(artistDao);
		ArrayList<String> artistListGenre = artistLists.collectListArtistByAlbums(albumGenre);
		
		ArrayList<String> albumFolderGenre = artistLists.collectCheminImage(albumGenre);
		
		ArrayList<String> nbTrackByAlbumGenre = artistLists.nbTrackByAlbum(albumGenre);		

		ArrayList<String> dateSortieAlbumGenre = artistLists.dateSortieByAlbum(albumGenre);
		
		//On recupère ce que l'utilisateur a tapé
		model.addAttribute("recherche", recherche);
		
		//On recupère les éléments des albums recherchés par genre 
		model.addAttribute("album", albumGenre);
		model.addAttribute("artistList", artistListGenre);
		model.addAttribute("albumFolder", albumFolderGenre);
				
		//On recupère le nombre de titre des albums
		model.addAttribute("nbTrackByAlbum", nbTrackByAlbumGenre);		

		//On recupère la date de sortie des albums
		model.addAttribute("dateSortieAlbum", dateSortieAlbumGenre);
		
		
		return "albums";
	}
	
	@RequestMapping(value ="albumsA/{recherche}" ,method = RequestMethod.GET)
	public String searchAlbumsTrack(@PathVariable String recherche, ModelMap model) {		
		
		SearchHandler album = new SearchHandler(albumDao);       
	   
		//Recupère les albums par genre		
		ArrayList<Album> albumArtist = album.collectAlbumByArtist(recherche, 100);
		
		//On recupére la liste des artistes associées aux albums
		ServiceManager artistLists = new ServiceManager(artistDao);
		ArrayList<String> artistListArtist = artistLists.collectListArtistByAlbums(albumArtist);
		
		ArrayList<String> albumFolderArtist = artistLists.collectCheminImage(albumArtist);
		
		ArrayList<String> nbTrackByAlbumArtist = artistLists.nbTrackByAlbum(albumArtist);		

		ArrayList<String> dateSortieAlbumArtist = artistLists.dateSortieByAlbum(albumArtist);
		
		//On recupère ce que l'utilisateur a tapé
		model.addAttribute("recherche", recherche);
				
		//On recupère les éléments des albums recherchés par artiste 
		model.addAttribute("album", albumArtist);
		model.addAttribute("artistList", artistListArtist);
		model.addAttribute("albumFolder", albumFolderArtist);
				
		//On recupère le nombre de titre des albums
		model.addAttribute("nbTrackByAlbum", nbTrackByAlbumArtist);		

		//On recupère la date de sortie des albums
		model.addAttribute("dateSortieAlbum", dateSortieAlbumArtist);
		
		
		return "albums";
	}	
	
	@RequestMapping(value ="albumsT/{recherche}" ,method = RequestMethod.GET)
	public String search(@PathVariable String recherche, ModelMap model) {		
		
		SearchHandler album = new SearchHandler(albumDao);       
	   
		//Recupère les albums par genre
		ArrayList<Album> albumTrack = album.collectAlbumByTrack(recherche, 100);
		
		//On recupére la liste des artistes associées aux albums
		ServiceManager artistLists = new ServiceManager(artistDao);
		ArrayList<String> artistListTrack = artistLists.collectListArtistByAlbums(albumTrack);
		
		ArrayList<String> albumFolderTrack = artistLists.collectCheminImage(albumTrack);
		
		ArrayList<String> nbTrackByAlbumTrack = artistLists.nbTrackByAlbum(albumTrack);
		
		ArrayList<String> dateSortieAlbumTrack = artistLists.dateSortieByAlbum(albumTrack);
		
		//On recupère ce que l'utilisateur a tapé
		model.addAttribute("recherche", recherche);
		

		//On recupère les éléments des albums recherchés par titre 
		model.addAttribute("album", albumTrack);
		model.addAttribute("artistList", artistListTrack);
		model.addAttribute("albumFolder", albumFolderTrack);
		
		model.addAttribute("nbTrackByAlbum", nbTrackByAlbumTrack);
		
		model.addAttribute("dateSortieAlbum", dateSortieAlbumTrack);
		
		
		return "albums";
	}	


}
