package com.isepfm.manager;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ITrackDao;

public class AlbumServiceManager {

	private IAlbumDao albumDao;
	private IArtistDao artistDao;
	private ITrackDao trackDao;

	/**
	 * Instancie la classe avec les objets du DAO
	 * 
	 * @param albumDao
	 *            objet venant du controller
	 * @param artistDao
	 *            objet venant du controller
	 */
	public AlbumServiceManager(IAlbumDao albumDao, IArtistDao artistDao,
			ITrackDao trackDao) {
		this.albumDao = albumDao;
		this.artistDao = artistDao;
		this.trackDao = trackDao;
	}

	/**
	 * Fonction permettant d'appeler le service Dao qui récupère un album par le
	 * nom
	 * 
	 * @param artistName
	 *            Nom de l'album à récuperer
	 * @return Retourne un objet Album
	 */
	public Album getAlbumByName(String albumName) {
		return this.albumDao.getAlbumByName(albumName);
	}
	
	

}
