package com.isepfm.beans;

import java.sql.Timestamp;
import java.util.Date;

public class Comments {

	private Long id;
	private Date date;
	private String content;
	private Long id_album;
	private Long id_user;

	public Long getId_album() {
		return id_album;
	}

	public void setId_album(Long id_album) {
		this.id_album = id_album;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setIdUser(Long userId) {
		this.id_user = userId;
	}

	public Long getIdUser() {
		return this.id_user;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}