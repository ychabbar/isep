package com.isepfm.manager;

import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;

public class HomeServiceManager {

	private IAlbumDao albumDao;
	private IArtistDao artistDao;

	/**
	 * Instancie la classe avec les objets du DAO
	 * 
	 * @param albumDao
	 *            objet venant du controller
	 * @param artistDao
	 *            objet venant du controller
	 */
	public HomeServiceManager(IAlbumDao albumDao, IArtistDao artistDao) {
		this.albumDao = albumDao;
		this.artistDao = artistDao;
	}

	/**
	 * Permet de récupérer une liste d'artiste
	 * 
	 * @param numbeOfReturn
	 *            Nombre d'artistes à retourner dans la liste
	 * 
	 * @return retourne une liste contenant des artistes
	 */
	public List<Artist> getRandomArtist(int numbeOfReturn) {
		List<Artist> artistList = new ArrayList<Artist>();
		artistList = this.artistDao.getRandomArtist(6);
		return artistList;
	}

	public List<Album> getRandomAlbum(int numerOfReturn) {
		List<Album> albumList = new ArrayList<Album>();
		albumList = this.albumDao.getRandomAlbum(numerOfReturn);
		return albumList;
	}
	
	
	
}
