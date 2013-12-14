package com.isepfm.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.isepfm.beans.Comments;
import com.isepfm.beans.Users;
import com.isepfm.service.IAlbumDao;
import com.isepfm.service.ICommentsDao;
import com.isepfm.service.IOtherDao;
import com.isepfm.service.IUsersDao;

public class OtherServiceManager {

	private IOtherDao otherDao;
	private IAlbumDao albumDao;
	private IUsersDao userDao;
	private ICommentsDao commentsDao;

	public OtherServiceManager(IOtherDao otherDao, IAlbumDao albumDao,
			IUsersDao userDao, ICommentsDao commentsDao) {
		this.otherDao = otherDao;
		this.albumDao = albumDao;
		this.userDao = userDao;
		this.commentsDao = commentsDao;
	}

	public boolean ifLikeAlbumUserExist(String userName, String albumName) {

		boolean isLike = otherDao.ifLikeAlbumOfUserExist(userName, albumName);

		return isLike;
	}

	public void addLikeAlbumUser(String userName, String albumName) {

		long idAlbum = albumDao.getAlbumByName(albumName).getId();
		long idUser = userDao.getUser(userName).getId();

		otherDao.addLikeAlbumUser(idAlbum, idUser);
	}

	public boolean ifNoteAlbumOfUserExist(String userName, String albumName) {

		boolean isNote = otherDao.ifNoteAlbumOfUserExist(userName, albumName);

		return isNote;
	}

	public void addNoteAlbumUser(int note, String userName, String albumName) {

		long idAlbum = albumDao.getAlbumByName(albumName).getId();
		long idUser = userDao.getUser(userName).getId();

		otherDao.addNoteAlbumUser(note, idAlbum, idUser);
	}

	public double moyenneNoteAlbumOfUsers(String albumName) {

		double moy = otherDao.moyenneNoteAlbumOfUsers(albumName);

		return moy;
	}

	public int nbNoteAlbumOfUsers(String albumName) {

		return otherDao.nbNoteAlbumOfUsers(albumName);
	}

	public List<Comments> getAlbumCommentsByAlbumId(Long albumId) {
		return commentsDao.getCommentsByAlbumId(albumId);
	}

	public Users getUserNameById(long userId) {

		return this.userDao.getUserById(userId);
	}

	public List<String> returnUserNameByCommentId(List<Comments> commentsList) {
		List<String> userNameList = new ArrayList<String>();
		for (int commentListIndex = 0; commentListIndex < commentsList.size(); commentListIndex++) {
			userNameList.add(this.userDao.getUserById(
					commentsList.get(commentListIndex).getIdUser()).getNom());
		}
		return userNameList;
	}


	public void insertCommentWithUserName(Comments comment, String userName) {

		Users currentUser = this.userDao.getUser(userName);
		System.out.println(userName);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date todayDate = new Date(cal.getTime().getYear(), cal.getTime()
				.getMonth(), cal.getTime().getDay());

		comment.setIdUser(currentUser.getId());
		comment.setDate(todayDate);
		this.commentsDao.insertComment(comment);
	}
	
	public int nbAllNoteArtistAlbums(String artistName) {	
		
		return otherDao.nbAllNoteArtistAlbums(artistName);
	}
	
	public List<Object> topLikeAlbums() {	
		
		return otherDao.topLikeAlbums();

	}
}
