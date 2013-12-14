package com.isepfm.manager;

import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.beans.Track;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.IArtistDao;
import com.isepfm.service.ITrackDao;

public class ArtistServiceManager {

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
	public ArtistServiceManager(IAlbumDao albumDao, IArtistDao artistDao,
			ITrackDao trackDao) {
		this.albumDao = albumDao;
		this.artistDao = artistDao;
		this.trackDao = trackDao;
	}

	/**
	 * Fonction permettant d'appeler le service Dao qui récupère un artiste par
	 * le nom
	 * 
	 * @param artistName
	 *            Nom de l'artiste à récuperer
	 * @return Retourne un objet Artist
	 */
	public Artist getArtistByName(String artistName) {
		return this.artistDao.getArtistByName(artistName);
	}
	
	
	/**
	 * Permet de récuprer la liste des albums d'un artiste
	 * 
	 * @param artistName
	 * @return liste d'albums d'un artiste
	 */
	public List<Album> getAlbumByArtist(String artistName, int limitAlbum) {
		return this.albumDao.getListAlbumByArtist(artistName, limitAlbum);
	}

	/**
	 * Permet de récupérer la liste des trois meilleurs albums d'un artiste
	 * 
	 * @param artistName
	 * @return Retourne la liste des 3 meilleurs albums d'un artiste
	 */
	public List<Album> getTopAlbumByArtist(String artistName) {
		return this.albumDao.getTopAlbumByArtist(artistName);
	}

	/**
	 * Permet de récuperer la liste des chanson d'un artiste
	 * 
	 * @param artistName
	 * @return liste contenant les chansons
	 */
	public List<Track> getTrackListByAlbumName(String artistName) {
		return trackDao.getTrackListByArtistName(artistName);
	}
	
	public List<Track> getTracksByAlbum(String albumName) {
		return trackDao.getTracksByAlbum(albumName);
	}

	/**
	 * Permet de retourner une liste de durée format hh:mm:ss
	 * 
	 * @param trackList
	 *            Liste contenant des objet Track
	 * @return Retourne une liste de string avec un format de durée(de chanson)
	 *         valide
	 */
	public List<String> convertIntToTime(List<Track> trackList) {
		List<String> convertedTime = new ArrayList<String>();
		int hours;
		int minutes;
		int seconds;
		int totalSecs;

		for (int i = 0; i < trackList.size(); i++) {
			totalSecs = trackList.get(i).getDuration();
			hours = totalSecs / 3600;
			minutes = (totalSecs % 3600) / 60;
			seconds = totalSecs % 60;

			convertedTime.add(// twoDigitString(hours) + ":"
					(minutes) + ":" + twoDigitString(seconds));
		}

		return convertedTime;
	}

	/**
	 * Permet de retourner un nombre a 2 chiffres
	 * 
	 * @param number
	 * @return
	 */
	private String twoDigitString(int number) {

		if (number == 0) {
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public List<String> getAlbumFolderPath(List<Album> albumList) {
		List<String> albumFolderPath = new ArrayList<String>();
		for (int i = 0; i < albumList.size(); i++) {
			
			String path = albumList.get(i).getName()
					.replaceAll("\\W", "");
			
			albumFolderPath.add(path);
		}
		return albumFolderPath;
	}
	
	
	public String getArtistNameByAlbumId(long artistName) {
		return this.artistDao.getArtistNameByAlbumId(artistName);
	}
	
	public String getArtistNameByAlbumName(String albumName) {
		return this.artistDao.getArtistNameByAlbumName(albumName);
	}
	
	
}
