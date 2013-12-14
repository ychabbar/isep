package com.isepfm.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.isepfm.beans.Users;
import com.isepfm.dao.DAOException;
import com.isepfm.service.IUsersDao;

public final class InscriptionForm {
    private static final String CHAMP_EMAIL      = "mail";
    private static final String CHAMP_PASS       = "password";
    private static final String CHAMP_CONF       = "confirmation";
    private static final String CHAMP_NOM        = "name";

    private static final String ALGO_CHIFFREMENT = "SHA-256";

    private String              resultat;
    private Map<String, String> erreurs          = new HashMap<String, String>();
    private IUsersDao      utilisateurDao;

    public InscriptionForm( IUsersDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Users inscrireUtilisateur( HttpServletRequest request ) {
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );
        String nom = getValeurChamp( request, CHAMP_NOM );

        Users utilisateur = new Users();
        try {
            traiterEmail( email, utilisateur );
            traiterMotsDePasse( motDePasse, confirmation, utilisateur );
            traiterNom( nom, utilisateur );

            if ( erreurs.isEmpty() ) {
                utilisateurDao.createUser(utilisateur);
                resultat = "Succès de l'inscription.";
            } else {
                resultat = "Echec de l'inscription.";
            }
        } catch ( DAOException e ) {
            resultat = "Ã‰chec de l'inscription : une erreur imprÃ©vue est survenue, merci de rÃ©essayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
    }

    /*
     * Appel Ã  la validation de l'adresse email reÃ§ue et initialisation de la
     * propriÃ©tÃ© email du bean
     */
    private void traiterEmail( String email, Users utilisateur ) {
        try {
            validationEmail( email );
            } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
    }
    
  

    /*
     * Appel Ã  la validation des mots de passe reÃ§us, chiffrement du mot de
     * passe et initialisation de la propriÃ©tÃ© motDePasse du bean
     */
    private void traiterMotsDePasse( String motDePasse, String confirmation, Users utilisateur ) {
        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
            setErreur( CHAMP_CONF, null );
        }

        /*
         * Utilisation de la bibliothÃ¨que Jasypt pour chiffrer le mot de passe
         * efficacement.
         * 
         * L'algorithme SHA-256 est ici utilisÃ©, avec par dÃ©faut un salage
         * alÃ©atoire et un grand nombre d'itÃ©rations de la fonction de hashage.
         * 
         * La String retournÃ©e est de longueur 56 et contient le hash en Base64.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );

        utilisateur.setMotDePasse( motDePasseChiffre );
    }

    /*
     * Appel Ã  la validation du nom reÃ§u et initialisation de la propriÃ©tÃ© nom
     * du bean
     */
    private void traiterNom( String nom, Users utilisateur ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );
    }

    
    /* Validation de l'adresse email */
    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            } else if ( utilisateurDao.getUserByMail( email ) != null ) {
                throw new FormValidationException( "Cette adresse email est dÃ©jÃ  utilisÃ©e." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir une adresse mail." );
        }
    }

    /* Validation des mots de passe */
    private void validationMotsDePasse( String motDePasse, String confirmation ) throws FormValidationException {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new FormValidationException( "Les mots de passe entrÃ©s sont diffÃ©rents, merci de les saisir Ã  nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new FormValidationException( "Les mots de passe doivent contenir au moins 3 caractÃ¨res." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
        }
    }

    /* Validation du nom */
    private void validationNom( String nom ) throws FormValidationException {
        if ( nom != null && nom.length() < 3 ) {
            throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 3 caractÃ¨res." );
        }
        
        if(utilisateurDao.ifUserExist(nom))
        {
        	throw new FormValidationException("Ce nom d'utilisateur est deja utilisé.");
        }
        
    }

    /*
     * Ajoute un message correspondant au champ spÃ©cifiÃ© Ã  la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * MÃ©thode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}