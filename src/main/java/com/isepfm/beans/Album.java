package com.isepfm.beans;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import xml.DateAdapter;

@XmlRootElement(name = "Album")
@XmlAccessorType(XmlAccessType.NONE)
public class Album {
	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "duration")
	private String duration;
	@XmlElement(name = "summary")
	private String summary;
	@XmlElement(name = "category")
	private String category;
	@XmlElement(name = "releaseDate", required = true) 
    @XmlJavaTypeAdapter(DateAdapter.class)
	private Date releaseDate;

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDuration() {
		return duration;
	}

	public String getSummary() {
		return summary;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
