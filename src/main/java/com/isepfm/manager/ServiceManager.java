package com.isepfm.manager;

import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Album;
import com.isepfm.beans.Track;
import com.isepfm.service.IArtistDao;

public class ServiceManager {
	
	private IArtistDao artistDao;
	
	
	public ServiceManager(IArtistDao artistDao) {
		this.artistDao = artistDao;
	}
	
	public ArrayList<String> collectListArtistByAlbums(ArrayList<Album> listAlbum) {
		
		ArrayList<String> artistList = new ArrayList<String>();
					
		for (int i = 0; i < listAlbum.size(); i++) {
			String artistName = artistDao.getArtistNameByAlbumId(listAlbum.get(i).getId());
			artistList.add(artistName);			
		}
		
		return artistList;
	}
	
	
	public ArrayList<String> collectCheminImage(ArrayList<Album> listAlbum) {
		
		ArrayList<String> albumFolder = new ArrayList<String>();
					
		for (int i = 0; i < listAlbum.size(); i++) 
		{
			albumFolder.add(listAlbum.get(i).getName().replaceAll("\\W", ""));		
		}
		
		return albumFolder;
	}
	
	public ArrayList<String> nbTrackByAlbum(ArrayList<Album> listAlbum) {
		
		String nbTrack = null;
		ArrayList<String> listNbTrack = new ArrayList<String>();
		
		
		for (int i = 0; i < listAlbum.size(); i++) 
		{
			nbTrack = artistDao.getNbTrackByAlbumId(listAlbum.get(i).getId());
			listNbTrack.add(nbTrack);
		}
		
		return listNbTrack;
	}
	
	
	public ArrayList<String> dateSortieByAlbum(ArrayList<Album> listAlbum) {
		
		String dateSortie = null;
		ArrayList<String> listDateSortie = new ArrayList<String>();		
		
		for (int i = 0; i < listAlbum.size(); i++) 
		{
			dateSortie = artistDao.getDateSortieByAlbumId(listAlbum.get(i).getId());
			listDateSortie.add(dateSortie);
		}
		
		return listDateSortie;
	}
	
	public String getArtistNameByAlbumId(Album album) {
		return artistDao.getArtistNameByAlbumId(album.getId());
	}	
		
}
