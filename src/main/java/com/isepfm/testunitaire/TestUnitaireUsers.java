package com.isepfm.testunitaire;

import static org.junit.Assert.*;
import junit.framework.TestCase;



import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;










import com.isepfm.beans.Users;
import com.isepfm.dao.DAOFactory;
import com.isepfm.service.IUsersDao;
import com.isepfm.service.UsersImp;

public class TestUnitaireUsers extends TestCase {

	  private IUsersDao usersImp;

	   public TestUnitaireUsers(String arg0) {
	         super(arg0);
	   }
	   public static void main(String[] args) {
	        
	   }

	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
        usersImp = new UsersImp(null);
	}

	@After
	public void tearDown() throws Exception {
		  super.tearDown();
	         usersImp = null;
	}

	
	 private static final String ALGO_CHIFFREMENT = "SHA-256";
	@Test
	public void testCreateUser() {
		
		Users utilisateur = new Users();
		utilisateur.setNom("yass");
		utilisateur.setMotDePasse("piol");
		utilisateur.setEmail("yass.chabbar@gmail.com");
			
		DAOFactory daoFactory = new DAOFactory("","","");
		daoFactory = DAOFactory.getInstance();
		
		
		usersImp = daoFactory.getUserDao();
	
		
		String nom = utilisateur.getNom();
        if(usersImp.ifUserExist(nom))
        {
        	usersImp.deleteUser(nom);
        }
        
             
        assertFalse(usersImp.ifUserExist(nom));
		usersImp.createUser(utilisateur);
        assertTrue(usersImp.ifUserExist(nom));
        assertEquals(usersImp.getUser(nom).getNom(), utilisateur.getNom());
        assertEquals(usersImp.getUser(nom).getEmail(), utilisateur.getEmail());
        assertEquals(usersImp.getUser(nom).getMotDePasse(), utilisateur.getMotDePasse());       
   
		
		assertTrue(usersImp.ifUserExist(utilisateur.getNom()));
		usersImp.deleteUser(utilisateur.getNom());
		assertFalse(usersImp.ifUserExist(utilisateur.getNom()));
        
        
	}

	

	
}
