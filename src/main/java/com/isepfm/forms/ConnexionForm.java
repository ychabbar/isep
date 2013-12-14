package com.isepfm.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.isepfm.beans.Users;
import com.isepfm.dao.DAOException;
import com.isepfm.service.IUsersDao;

public final class ConnexionForm {

	private static final String CHAMP_NAME = "nom";
	private static final String CHAMP_PASS = "motdepasse";
	private static final String CHAMPS_CONNEXION = "champsConnexion";
	private static final String ALGO_CHIFFREMENT = "SHA-256";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	private IUsersDao utilisateurDao;

	public ConnexionForm(IUsersDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Users connecterUtilisateur(HttpServletRequest request) {
		/* Récupération des champs du formulaire */
		String name = getValeurChamp(request, CHAMP_NAME);
		String password = getValeurChamp(request, CHAMP_PASS);

		Users utilisateur = new Users();

		try {

			try {
				validateUser(name);
			} catch (FormValidationException e) {
				setErreur(CHAMP_NAME, e.getMessage());
			}

			if (erreurs.isEmpty()) {
				try {
					authOK(name, password);
					utilisateur.setNom(name);
				} catch (FormValidationException e) {
					setErreur(CHAMP_PASS, e.getMessage());
				}
			}

			if (erreurs.isEmpty()) {
				resultat = "Succès de la connexion.";
			} else {
				resultat = "Échec de la connexion.";
				new Exception("Échec de la connexion.");
			}

		} catch (DAOException e) {
			resultat = "Ã‰chec de la connexion : une erreur imprÃ©vue est survenue, merci de rÃ©essayer dans quelques instants.";
			e.printStackTrace();
			utilisateur.setNom("0");
			return utilisateur;
		}

		return utilisateur;
	}

	// Méthode qui va verifier si l'utilisateur existe.
	public void validateUser(String name) throws FormValidationException {

		// On verifie que l'utilisateur existe
		if (!utilisateurDao.ifUserExist(name)) {
			throw new FormValidationException("Cet utilisateur n'existe pas.");
		}

	}

	public void authOK(String name, String motDePasse)
			throws FormValidationException {

		Users userGet = utilisateurDao.getUser(name);

		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
		passwordEncryptor.setPlainDigest(false);

		boolean passIsCorrect = passwordEncryptor.checkPassword(motDePasse,
				userGet.getMotDePasse());

		if (!passIsCorrect) {
			throw new FormValidationException("Le mot de passe est incorrect.");
		}
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp(HttpServletRequest request,
			String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
}