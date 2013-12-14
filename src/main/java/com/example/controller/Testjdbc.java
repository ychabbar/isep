package com.example.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isepfm.service.IUsersDao;
import com.isepfm.api.*;

@Controller
@RequestMapping("/testjdbc")
public class Testjdbc {
	@Autowired
	private ServletContext servletContext;
	private IUsersDao utilisateurDao;

	public void init() {
		System.out.println("Testjdbc controller init !");
	}

	@RequestMapping(method = RequestMethod.GET)
	public void printWelcome() throws IOException {
		// this.utilisateurDao = ((DAOFactory) context.getAttribute(
		// "daofactory" )).getUserDao();
		System.out.println("Downloading top artist..... e!");

		//Lastfm lastFM = new Lastfm();
		//lastFM.getTopArtist(lastFM.getTopArtistURL(),
		//		"/app/src/main/webapp/WEB-INF/");		
	}

	//affiche tous les fichiers et les repertoires
	public static void walkin(File dir) {

		File listFile[] = dir.listFiles();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {
					System.out.println("Directory:" + listFile[i].toString());
					System.out.println("|\t\t");
					walkin(listFile[i]);
				} else {

					System.out.println("+---"
							+ listFile[i].getName().toString());

				}
			}
		}
	}
}
