package com.isepfm.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isepfm.beans.Users;
import com.isepfm.dao.DAOFactory;
import com.isepfm.service.IUsersDao;


public class Signup extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/views/signup.jsp";

    private IUsersDao userDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.userDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        //InscriptionForm form = new InscriptionForm( userDao );

        /* Traitement de la requête et récupération du bean en résultant */
        //User utilisateur = form.inscrireUtilisateur( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        //request.setAttribute( ATT_FORM, form );
        //request.setAttribute( ATT_USER, utilisateur );

        //this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}