package com.isepfm.beans;

public class Track {
	private long id;
	private String trackName;
	private String label;
	private int duration;

	public long getId() {
		return id;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public Track() {

	}

	public String getLabel() {
		return label;
	}

	public int getDuration() {
		return duration;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
