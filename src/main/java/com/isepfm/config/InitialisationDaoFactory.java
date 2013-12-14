package com.isepfm.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.isepfm.dao.DAOFactory;

@WebListener
public class InitialisationDaoFactory implements ServletContextListener {
	private static final String ATT_DAO_FACTORY = "daofactory";

	private DAOFactory daoFactory;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("dao factory initialization");
		/* Récupération du ServletContext lors du chargement de l'application */
		// Le servletContext est un objet implicite
		ServletContext servletContext = event.getServletContext();
		/* Instanciation de notre DAOFactory */
		this.daoFactory = DAOFactory.getInstance();
		/* Enregistrement dans un attribut ayant pour portée toute l'application */
		servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		/* Rien à réaliser lors de la fermeture de l'application... */
	}
}