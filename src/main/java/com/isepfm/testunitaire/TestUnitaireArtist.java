package com.isepfm.testunitaire;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.isepfm.beans.Artist;
import com.isepfm.dao.DAOFactory;
import com.isepfm.service.ArtistImp;
import com.isepfm.service.IArtistDao;




public class TestUnitaireArtist extends TestCase {
	
	 private IArtistDao artistImp;

	  public  TestUnitaireArtist(String arg0) {
	         super(arg0);
	   }
	   public static void main(String[] args) {
	        
	   }
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
        artistImp = new ArtistImp (null);
	}

	@After
	public void tearDown() throws Exception {
		 super.tearDown();
         artistImp = null;
	}

	@Test
	public void testCreateArtist() {
		Artist artist = new Artist();
		artist.setName("yasser");
		artist.setDescription("description");
		
		DAOFactory daoFactory = new DAOFactory("","","");
		daoFactory = DAOFactory.getInstance();
		
		artistImp = daoFactory.getArtistDao();
		String nom = artist.getName();
		
		 if(artistImp.ifArtistExist(nom))
	        {
	        	artistImp.deleteArtist(nom);
	        }
	        
		
		 assertEquals("yasser",artist.getName() );
		 
		 assertFalse(artistImp.ifArtistExist(nom));
		 artistImp.createArtist(artist);
		 
		 assertTrue(artistImp.ifArtistExist(nom));
		 
		 assertEquals(artistImp.getArtistByName(nom).getName(),  artist.getName());
	     assertEquals(artistImp.getArtistByName(nom).getDescription(), artist.getDescription());
	     
	     assertTrue(artistImp.ifArtistExist(artist.getName()));
	     artistImp.deleteArtist(artist.getName());
			assertFalse(artistImp.ifArtistExist(artist.getName()));
	     
		
	}

}
