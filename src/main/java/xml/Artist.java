package xml;

import java.util.List;

public class Artist {

	private long id;
	private String name;
	private String description;
	public List<String> albumList;
	public List<Album> albumObjectList;

	public List<Album> getAlbumObjectList() {
		return albumObjectList;
	}

	public void setAlbumObjectList(List<Album> albumObjectList) {
		this.albumObjectList = albumObjectList;
	}

	public Artist() {

	}

	public List<String> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<String> albumList) {
		this.albumList = albumList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}