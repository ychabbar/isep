package com.isepfm.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.beans.Track;
import com.isepfm.beans.Users;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ITrackDao;

public class SearchHandler {

	private IAlbumDao albumDao;
	
	public SearchHandler(IAlbumDao albumDao) {
		this.albumDao = albumDao;
	}

	// Méthode qui récupère tous les albums en fonction de la catégorie tapée
	public ArrayList<Album> collectAlbumByGenre(String champsRecherche, int limitAlbum) {
		
		// On récupère les albums
		ArrayList<Album> listAlb = albumDao.getListAlbumByGenre(champsRecherche, limitAlbum);

		return listAlb;
	}

	// Méthode qui récupère tous les albums en fonction de l'artist tapée
	public ArrayList<Album> collectAlbumByArtist(String champsRecherche, int limitAlbum) {

		// On récupère les albums
		ArrayList<Album> listAlb = albumDao.getListAlbumByArtist(champsRecherche, limitAlbum);

		return listAlb;
	}
	
	// Méthode qui récupère tous les albums en fonction de la chanson tapée
	public ArrayList<Album> collectAlbumByTrack(String champsRecherche, int limitAlbum) {

		// On récupère les albums
		ArrayList<Album> listAlb = albumDao.getListAlbumByTrack(champsRecherche, limitAlbum);

		return listAlb;
	}
	


}
