package com.isepfm.service;

import java.util.ArrayList;
import java.util.List;

import com.isepfm.beans.Track;
import com.isepfm.dao.DAOException;

public interface ITrackDao {

	void createTrack(Track track) throws DAOException;

	Track getTrackByName(String track) throws DAOException;

	Track getTrackById(int id) throws DAOException;
	
	ArrayList<Track> getAllTrackByName(String name) throws DAOException;

	List<Track> getTrackListByArtistName(String artistName) throws DAOException;
	
	void updateTrack(Track track) throws DAOException;

	void deleteTrack(String SONG) throws DAOException;
	
	List<Track> getTracksByAlbum(String albumName) throws DAOException;
	
	boolean ifTrackExist(String SONG) throws DAOException;

}