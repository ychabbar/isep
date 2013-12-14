package com.isepfm.controller;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isepfm.beans.Track;
import com.isepfm.dao.DAOFactory;
import com.isepfm.service.ITrackDao;

@Controller
@RequestMapping("/track")
public class TrackController {
	public static final String CONF_DAO_FACTORY = "daofactory";
	@Autowired
	private ServletContext context;
	private ITrackDao trackDao;

	@PostConstruct
	public void init() {
		this.trackDao = ((DAOFactory) context.getAttribute(CONF_DAO_FACTORY))
				.getTrackDao();
		System.out.println("INIT OF TrackDao CONTROLLER");
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public void addArtist() {
		Track track = new Track();
		track.setTrackName("Chanson 1");
		track.setDuration(423);

		this.trackDao.createTrack(track);
	}

}
