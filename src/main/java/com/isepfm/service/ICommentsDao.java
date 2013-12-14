package com.isepfm.service;

import java.util.List;

import com.isepfm.beans.Comments;
import com.isepfm.dao.DAOException;

public interface ICommentsDao {

	void insertComment(Comments comments) throws DAOException;

	List<Comments> getCommentsByAlbumId(Long albumId) throws DAOException;
}