package com.isepfm.service;

import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.dao.DAOException;

public interface IArtistDao {

	void createArtist(Artist artist) throws DAOException;

	Artist getArtistByName(String name) throws DAOException;

	Artist getArtistById(int id) throws DAOException;
	
	ArrayList<Artist> getListArtistByName(String name) throws DAOException;

	String getArtistNameByAlbumId(long id);
	
	List<Artist> getRandomArtist(int numberOfReturn) throws DAOException;
    
	String getNbTrackByAlbumId(long id) throws DAOException;
    
	String getDateSortieByAlbumId(long id) throws DAOException;

	void updateArtist(Artist artist) throws DAOException;

	void deleteArtist(String ARTIST_NAME) throws DAOException;
	
	String getArtistNameByAlbumName(String albumName) throws DAOException;
	
	boolean ifArtistExist(String ARTIST_NAME) throws DAOException;

}