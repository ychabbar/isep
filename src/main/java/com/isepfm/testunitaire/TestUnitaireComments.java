package com.isepfm.testunitaire;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.isepfm.beans.Comments;
import com.isepfm.dao.DAOFactory;
import com.isepfm.service.CommentsImp;
import com.isepfm.service.ICommentsDao;



public class TestUnitaireComments extends TestCase  {
	
	private ICommentsDao commentsImp;
	
	public TestUnitaireComments(String arg0) {
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
        commentsImp = new CommentsImp (null);
	}

	@After
	public void tearDown() throws Exception {
		 super.tearDown();
         commentsImp = null;
	}

	@Test
	public void testInsertComment() {
		
		Comments comments = new Comments();
		
		comments.setContent("yasser");
		comments.setDate(null);
		

		DAOFactory daoFactory = new DAOFactory("","","");
		daoFactory = DAOFactory.getInstance();
		
		commentsImp = daoFactory.getCommentsDao();
		
		 commentsImp.insertComment(comments);
		 
		
		
		
	}

}
