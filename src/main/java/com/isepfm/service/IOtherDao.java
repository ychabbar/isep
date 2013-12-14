package com.isepfm.service;

import java.util.List;

import com.isepfm.dao.DAOException;

public interface IOtherDao {
	
	boolean ifLikeAlbumOfUserExist(String userName, String albumName) throws IllegalArgumentException, DAOException;

	void addLikeAlbumUser(long idAlbum, long idUser) throws IllegalArgumentException, DAOException;
	
	boolean ifNoteAlbumOfUserExist(String userName, String albumName) throws IllegalArgumentException, DAOException;
	
	void addNoteAlbumUser(int note ,long idAlbum, long idUser) throws IllegalArgumentException, DAOException;
	
	double moyenneNoteAlbumOfUsers(String albumName) throws IllegalArgumentException, DAOException;
	
	int nbNoteAlbumOfUsers(String albumName) throws IllegalArgumentException, DAOException;
	
	int nbAllNoteArtistAlbums(String artistName) throws IllegalArgumentException, DAOException;		
	
	List<Object> topLikeAlbums() throws IllegalArgumentException, DAOException;
		
}
