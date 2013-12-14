package com.isepfm.testunitaire;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.isepfm.beans.Album;

import com.isepfm.dao.DAOFactory;
import com.isepfm.service.AlbumImp;
import com.isepfm.service.IAlbumDao;



public class TestUnitaireAlbum extends TestCase{

	private IAlbumDao albumImp ;
	
	public TestUnitaireAlbum(String arg0) {
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
        albumImp = new AlbumImp(null);
	}

	@After
	public void tearDown() throws Exception {
		 super.tearDown();
         albumImp = null;
	}

	@Test
	public void testAlbumImp() {
		Album album = new Album();
	
		album.setName("Brians");
		album.setDescription("description");
		album.setReleaseDate(null);
			
		DAOFactory daoFactory = new DAOFactory("","","");
		daoFactory = DAOFactory.getInstance();
		
		
		albumImp = daoFactory.getAlbumDao();
		String nom = album.getName();
       
		
		 if(albumImp.ifAlbumExist(nom))
	        {
	        	albumImp.deleteAlbum(nom);
	        }
	        
        
        
        
        assertFalse(albumImp.ifAlbumExist(nom));
        
        
		albumImp.createAlbum(album);
		
		 assertTrue(albumImp.ifAlbumExist(nom));
		
        assertEquals(albumImp.getAlbumByName(nom).getName(),  album.getName());
        assertEquals(albumImp.getAlbumByName(nom).getDescription(), album.getDescription());
        assertEquals(albumImp.getAlbumByName(nom).getReleaseDate(), album.getReleaseDate());
        
        assertTrue(albumImp.ifAlbumExist(album.getName()));
		albumImp.deleteAlbum(album.getName());
		assertFalse(albumImp.ifAlbumExist(album.getName()));
             
	}

	

}
