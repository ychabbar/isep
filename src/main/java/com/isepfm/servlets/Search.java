/*package com.isepfm.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isepfm.beans.Album;
import com.isepfm.dao.DAOFactory;
import com.isepfm.forms.SearchHandler;
import com.isepfm.service.IAlbumDao;

public class Search extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
	private static final String CHAMP = "recherche";
	public static final String ATT_ALBUM_GENRE = "Album_Genre";
	public static final String ATT_ALBUM_ARTIST = "Album_Artist";
	public static final String ATT_ALBUM_TRACK = "Album_Track";
	public static final String VUE = "/WEB-INF/views/search.jsp";

	private IAlbumDao albumDao;

	public void init() throws ServletException {
		
		this.albumDao = ((DAOFactory) getServletContext().getAttribute(
				CONF_DAO_FACTORY)).getAlbumDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String rechercheActuelle = request.getParameter(CHAMP);
	
		
		SearchHandler album = new SearchHandler(albumDao);

		
		ArrayList<Album> albumGenre = album.collectAlbumByGenre(rechercheActuelle);
		ArrayList<Album> albumArtist = album.collectAlbumByArtist(rechercheActuelle);
		ArrayList<Album> albumTrack = album.collectAlbumByTrack(rechercheActuelle);
		
		
		request.setAttribute(CHAMP, rechercheActuelle);
		request.setAttribute(ATT_ALBUM_GENRE, albumGenre);
		request.setAttribute(ATT_ALBUM_ARTIST, albumArtist);
		request.setAttribute(ATT_ALBUM_TRACK, albumTrack);

		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}
}*/