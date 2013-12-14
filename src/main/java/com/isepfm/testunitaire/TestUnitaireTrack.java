package com.isepfm.testunitaire;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;









import com.isepfm.beans.Artist;
import com.isepfm.beans.Track;
import com.isepfm.dao.DAOFactory;
import com.isepfm.service.ArtistImp;
import com.isepfm.service.ITrackDao;
import com.isepfm.service.TrackImp;

public class TestUnitaireTrack extends TestCase {
	
	private ITrackDao trackImp;
	
	public TestUnitaireTrack(String arg0) {
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
        trackImp = new TrackImp (null);
	}

	@After
	public void tearDown() throws Exception {
		 super.tearDown();
         trackImp = null;
	}

	@Test
	public void testCreateTrack() {
		
		Track track = new Track();
		
		track.setTrackName("yass");
		
		track.setDuration(198);
		
		DAOFactory daoFactory = new DAOFactory("","","");
		daoFactory = DAOFactory.getInstance();
		
		trackImp = daoFactory.getTrackDao();
		String nom = track.getTrackName();
		
		 if(trackImp.ifTrackExist(nom))
	        {
	        	trackImp.deleteTrack(nom);
	        }
	        
		
		
		 
		 assertFalse(trackImp.ifTrackExist(nom));
		 
         trackImp.createTrack(track);
         
        
         assertTrue(trackImp.ifTrackExist(nom));
         
       
         assertEquals(trackImp.getTrackByName(nom).getTrackName(), track.getTrackName());
	     assertEquals(trackImp.getTrackByName(nom).getDuration(), track.getDuration());
	     
	     assertTrue(trackImp.ifTrackExist(track.getTrackName()));
	     trackImp.deleteTrack(track.getTrackName());
			assertFalse(trackImp.ifTrackExist(track.getTrackName()));
		
		
	}

}
