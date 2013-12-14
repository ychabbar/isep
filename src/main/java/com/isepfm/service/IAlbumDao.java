package com.isepfm.service;

import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Album;
import com.isepfm.beans.Artist;
import com.isepfm.dao.DAOException;

public interface IAlbumDao {

	void createAlbum(Album album) throws DAOException;

	Album getAlbumByName(String name) throws DAOException;

	Album getAlbumById(int id) throws DAOException;
	
	List<Album> getRandomAlbum(int numberOfReturn) throws DAOException;
	
	List<Album> getTopAlbumByArtist(String artistName) throws DAOException;
	
	ArrayList<Album> getListAlbumByNameApprox(String name) throws DAOException;
	
    ArrayList<Album> getListAlbumByGenre(String genre, int limitAlbum) throws DAOException;
    
    ArrayList<Album> getListAlbumByArtist(String artist, int limitAlbum) throws DAOException;
    
    ArrayList<Album> getListAlbumByTrack(String track, int limitAlbum) throws DAOException;
	
	void updateAlbum(Album album) throws DAOException;

	void deleteAlbum(String ALBUM_NAME) throws DAOException;

	boolean ifAlbumExist(String ALBUM_NAME) throws DAOException;
	

}