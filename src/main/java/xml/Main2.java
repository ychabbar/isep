package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Main2 {
	private static String downloadDirectory = "C:\\Users\\Damien\\imageJ2ee";
	private static String topArtistUrl = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&autocorrect=1&api_key=3feba82e23f73071739389aa3fde518f&limit=30";
	private static String artistInfo = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&autocorrect=1&lang=fr&api_key=3feba82e23f73071739389aa3fde518f&artist=";
	private static String albumInfo = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=3feba82e23f73071739389aa3fde518f&autocorrect=1"; // &artist=toto&album=tata
	private static String topAlbum = "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&api_key=3feba82e23f73071739389aa3fde518f&limit=10&autocorrect=1&artist=";
	private static List<String> artistList = new ArrayList<String>();

	private static Boolean downloadImage = true;

	public Main2() throws IOException {
		List<Artist> artlistList = new ArrayList<Artist>();

		// Fill the artist list by top artist in lastfm
		// False = do not download image
		System.out.println("Getting artists list from lastfm...");
		artistList = returnTopArtistList(downloadImage);

		// Get basic infos for the artist
		for (int artistListCounter = 0; artistListCounter < artistList.size(); artistListCounter++) {
			List<Album> albumList = new ArrayList<Album>();
			String currentArtistName = artistList.get(artistListCounter);

			Artist currentArtist = new Artist();
			System.out.println("Getting information for " + currentArtistName);
			currentArtist = getArtistInfos(currentArtistName);

			artlistList.add(currentArtist);
			/*
			 * FOR DEBUG System.out.println("name=" + currentArtist.getName());
			 * System.out.println("description=" +
			 * currentArtist.getDescription());
			 */

			System.out.println("Getting top albums for " + currentArtistName);
			currentArtist.setAlbumList(getArtistTopAlbumList(currentArtistName,
					downloadImage));

			// Get basic infos for album
			for (int albumListCounter = 0; albumListCounter < currentArtist
					.getAlbumList().size(); albumListCounter++) {
				String currentAlbumName = currentArtist.getAlbumList().get(
						albumListCounter);
				Album currentAlbum = new Album();

				System.out.println("Getting information for "
						+ currentAlbumName + " (" + currentArtistName + ")");

				currentAlbum = getAlbumInfos(currentArtistName,
						currentAlbumName);
				albumList.add(currentAlbum);
			}

			// Ajoute la liste complÃ¨te et remplie d'albums dans la liste
			// d'album d'un artiste
			currentArtist.setAlbumObjectList(albumList);
		}

		// OK everything is added

		generateSQL(artlistList);

	}

	public Artist getArtistInfos(String artistName) throws IOException {
		Artist artist = new Artist();
		artistName = artistName.replaceAll(" ", "%20");
		String finalURL = artistInfo + artistName.replaceAll(" ", "%20");

		try {
			URL url = new URL(finalURL);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream("artistInfo.xml");
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("artistInfo.xml");

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List artistList = rootNode.getChildren("artist");

			Iterator i = artistList.iterator();
			while (i.hasNext()) {

				// Setting Name for Artist
				Element artistNode = (Element) i.next();
				artist.setName(artistNode.getChildText("name").trim());
				// .replaceAll("'", "''"));

				// Setting Description for Artist
				List bioList = artistNode.getChildren("bio");
				for (int j = 0; j < bioList.size(); j++) {
					Element bioNode = (Element) bioList.get(j);

					artist.setDescription(bioNode.getChildText("content")
							.trim());
					// .trim().replaceAll("'", "''"));

				}
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		File fileToDelete = new File("artistInfo.xml");
		fileToDelete.delete();

		return artist;
	}

	public List<String> returnTopArtistList(Boolean downloadImage)
			throws IOException {

		List<String> artistList = new ArrayList<String>();

		try {
			URL url2 = new URL(topArtistUrl);
			InputStream is = url2.openStream();
			OutputStream os = new FileOutputStream("topArtist.xml");
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("topArtist.xml");

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List artists = rootNode.getChildren("artists");

			Iterator i = artists.iterator();
			while (i.hasNext()) {

				Element artiststNode = (Element) i.next();
				List listArtist = artiststNode.getChildren("artist");

				for (int j = 0; j < listArtist.size(); j++) {
					Element node = (Element) listArtist.get(j);
					System.out.println("Adding " + node.getChildText("name")
							+ " to the list.");
					artistList.add(node.getChildText("name"));

					// Download Image
					if (downloadImage) {
						List imageNode = node.getChildren("image");

						for (int k = 0; k < imageNode.size(); k++) {
							Element imageElement = (Element) imageNode.get(k);

							System.out.println("Downloading "
									+ node.getChildText("name")
									+ " image size("
									+ imageElement.getAttributeValue("size")
									+ ") " + imageElement.getValue());
							URL url3 = new URL(imageElement.getValue());
							InputStream is2 = url3.openStream();

							File dir = new File(downloadDirectory
									+ node.getChildText("name"));
							dir.mkdirs();

							OutputStream os2 = new FileOutputStream(dir + "//"
									+ imageElement.getAttributeValue("size")
									+ ".png");

							byte[] b2 = new byte[2048];
							int length2;

							while ((length2 = is2.read(b2)) != -1) {
								os2.write(b2, 0, length2);
							}

							is2.close();
							os2.close();
						}
					}

				}
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		File fileToDelete = new File("topArtist.xml");
		fileToDelete.delete();

		return artistList;
	}

	public List<String> getArtistTopAlbumList(String artistName,
			Boolean downloadImage) throws IOException {
		List<String> album = new ArrayList<String>();

		try {
			URL url2 = new URL(topAlbum + artistName.replaceAll(" ", "%20"));
			InputStream istream = url2.openStream();
			OutputStream ostream = new FileOutputStream("topAlbumList.xml");
			byte[] b = new byte[2048];
			int length;

			while ((length = istream.read(b)) != -1) {
				ostream.write(b, 0, length);

			}
			istream.close();
			ostream.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("topAlbumList.xml");

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List artists = rootNode.getChildren("topalbums");

			Iterator i = artists.iterator();
			while (i.hasNext()) {
				Element artiststNode = (Element) i.next();

				List listArtist = artiststNode.getChildren("album");
				for (int j = 0; j < listArtist.size(); j++) {
					Element node = (Element) listArtist.get(j);

					album.add(node.getChildText("name"));

					// Image section
					List imageNode = node.getChildren("image");
					if (downloadImage) {
						for (int k = 0; k < imageNode.size(); k++) {

							Element imageElement = (Element) imageNode.get(k);

							System.out.println("Downloading "
									+ node.getChildText("name")
									+ " image size("
									+ imageElement.getAttributeValue("size")
									+ ") " + imageElement.getValue());
							URL url3 = new URL(imageElement.getValue());
							InputStream is2 = url3.openStream();

							File dir = new File(downloadDirectory
									+ (artistName)
									+ "//"
									+ (node.getChildText("name").replaceAll(
											"\\W", "")));
							dir.mkdirs();

							OutputStream os2 = new FileOutputStream(dir + "//"
									+ imageElement.getAttributeValue("size")
									+ ".png");

							byte[] b2 = new byte[2048];
							int length2;

							while ((length2 = is2.read(b2)) != -1) {
								os2.write(b2, 0, length2);
							}

							is2.close();
							os2.close();
						}
					}
				}
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		File fileToDelete = new File("topAlbumList.xml");
		fileToDelete.delete();

		return album;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public Album getAlbumInfos(String artistName, String albumName)
			throws IOException {
		List<Track> trackListObject = new ArrayList<Track>();
		List<String> tagListString = new ArrayList<String>();

		Album album = new Album();
		artistName = artistName.replaceAll(" ", "%20");
		albumName = albumName.replaceAll(" ", "%20");
		String finalURL = albumInfo + "&artist=" + artistName + "&album="
				+ albumName;

		try {
			URL url2 = new URL(finalURL);

			InputStream is = url2.openStream();
			OutputStream os = new FileOutputStream("albumInfo.xml");
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("albumInfo.xml");

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List albumList = rootNode.getChildren("album");
			Iterator i = albumList.iterator();

			while (i.hasNext()) {
				Element albumNode = (Element) i.next();

				album.setName(albumNode.getChildText("name").trim());
				// .replaceAll("'", "''"));

				// Gestion de la date rÃ©cupÃ©rer
				if (!albumNode.getChildText("releasedate").trim().isEmpty()) {
					// System.out.println(dateConverter(albumNode.getChildText(
					// "releasedate").trim()));
					album.setReleaseDate(new Date(albumNode.getChildText(
							"releasedate").trim()));
				} else {
					album.setReleaseDate(new Date("01/01/2000"));
				}

				// Track maganing
				@SuppressWarnings("rawtypes")
				List tracksList = albumNode.getChildren("tracks");
				for (int j = 0; j < tracksList.size(); j++) {
					Element tracksNode = (Element) tracksList.get(j);

					List trackList = tracksNode.getChildren("track");
					for (int k = 0; k < trackList.size(); k++) {
						Element trackNode = (Element) trackList.get(k);
						Track track = new Track();
						track.setTitleName(trackNode.getChildText("name")
								.trim().replaceAll("'", "''"));
						track.setDuration(trackNode.getChildText("duration"));

						System.out.println("Track name: "
								+ trackNode.getChildText("name") + "("
								+ trackNode.getChildText("duration") + ")");

						// track.setTitleName(trackNode.getChildText("name"));
						// track.setDuration(trackNode.getChildText("duration"));

						trackListObject.add(track);

					}

				}

				// Tags part
				List toptagsList = albumNode.getChildren("toptags");
				for (int k = 0; k < toptagsList.size(); k++) {
					Element toptagsNode = (Element) toptagsList.get(k);

					List tagList = toptagsNode.getChildren("tag");
					for (int m = 0; m < tagList.size(); m++) {
						Element tagNode = (Element) tagList.get(m);
						// System.out.println("Tag "
						// + tagNode.getChildText("name"));
						tagListString.add(tagNode.getChildText("name"));
					}
				}

				// Wiki part
				List wikiList = albumNode.getChildren("wiki");
				if (wikiList.size() != 0) {

					for (int j = 0; j < wikiList.size(); j++) {
						Element bioNode = (Element) wikiList.get(j);
						if (bioNode.getChildText("content").trim()
								.replaceAll("'", "''") == "null") {
							album.setDescription("Aucune description pour "
									+ albumNode.getChildText("name"));
						} else {
							album.setDescription(bioNode
									.getChildText("content").trim());
							// .replaceAll("'", "''"));
						}
					}
				} else {
					album.setDescription("Aucune description pour "
							+ albumNode.getChildText("name"));
				}
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		File fileToDelete = new File("albumInfo.xml");
		fileToDelete.delete();

		album.setTagList(tagListString);
		album.setTrackList(trackListObject);
		return album;
	}

	public void dateConverter(String dateStr) {
		/*
		 * SimpleDateFormat formater = null; formater = new
		 * SimpleDateFormat("dd/MM/yyyy"); //
		 * System.out.println(formater.format(new Date(dateStr)));
		 * formater.format(new Date(dateStr)).
		 * 
		 * return formater.format(new Date(dateStr));
		 */
	}

	public void generateSQL(List<Artist> artistList) throws IOException {
		List<String> tagList = new ArrayList<String>();
		String data = "";
		File file = new File(downloadDirectory + "generatedSQL.sql");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWritter = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

		Artist currentArtist = new Artist();

		for (int i = 0; i < artistList.size(); i++) {
			currentArtist = artistList.get(i);

			// Output for Artist
			System.out.println(currentArtist.getName());
			System.out.println(currentArtist.getDescription());

			data += "INSERT INTO ARTISTS (ARTIST_NAME, DESCRIPTION) VALUES ('"
					+ currentArtist.getName().replaceAll("'", "''") + "', '"
					+ currentArtist.getDescription().replaceAll("'", "''")
					+ "');\n";

			for (int j = 0; j < currentArtist.getAlbumObjectList().size(); j++) {
				Album currentAlbum = new Album();
				currentAlbum = currentArtist.getAlbumObjectList().get(j);

				List<String> currentAlbumTags = new ArrayList<String>();
				currentAlbumTags = currentAlbum.getTagList();

				// Output for album
				System.out.println(currentAlbum.getName());
				System.out.println(currentAlbum.getDescription());
				System.out.println(currentAlbum.getReleaseDate());

				// Get a proper date format for the db
				SimpleDateFormat formater = null;
				formater = new SimpleDateFormat("yyyy-MM-dd");

				data += "INSERT INTO ALBUMS (ALBUM_NAME, DESCRIPTION, RELEASE_DATE) VALUES ('"
						+ currentAlbum.getName().replaceAll("'", "''")
						+ "', '"
						+ currentAlbum.getDescription().replaceAll("'", "''")
						+ "', '"
						+ formater.format((currentAlbum.getReleaseDate()))
						+ "');\n";

				data += "INSERT INTO ALBUMS_ARTISTS (ID_ARTIST, ID_ALBUM) VALUES ((SELECT MAX(ID) FROM ARTISTS WHERE ARTIST_NAME = '"
						+ currentArtist.getName().replaceAll("'", "''")
						+ "' AND DESCRIPTION = '"
						+ currentArtist.getDescription().replaceAll("'", "''")
						+ "'), (SELECT MAX(ID) FROM ALBUMS WHERE ALBUM_NAME = '"
						+ currentAlbum.getName().replaceAll("'", "''")
						+ "' AND DESCRIPTION = '"
						+ currentAlbum.getDescription().replaceAll("'", "''")
						+ "'));\n";

				for (int m = 0; m < currentAlbumTags.size(); m++) {
					System.out.println("Tag: " + currentAlbumTags.get(m));

					// If a tag already exist INSERT
					if (!tagList.contains(currentAlbumTags.get(m))) {
						System.out.println("this genre does not exist in list"
								+ currentAlbumTags.get(m));
						data += "INSERT INTO GENRES (MUSICAL_GENRE) VALUES ('"
								+ currentAlbumTags.get(m).trim()
										.replaceAll("'", "''") + "'); \n";
						tagList.add(currentAlbumTags.get(m));
					}

					data += "INSERT INTO ALBUMS_GENRES (ID_ALBUM, ID_GENRE) VALUES ((SELECT MAX(ID) FROM ALBUMS WHERE ALBUM_NAME = '"
							+ currentAlbum.getName().trim()
									.replaceAll("'", "''")
							+ "' AND DESCRIPTION = '"
							+ currentAlbum.getDescription().trim()
									.replaceAll("'", "''")
							+ "'), (SELECT MAX(ID) FROM GENRES WHERE MUSICAL_GENRE = '"
							+ currentAlbumTags.get(m).trim()
									.replaceAll("'", "''") + "')); \n";

				}

				for (int k = 0; k < currentAlbum.getTrackList().size(); k++) {
					Track currentTrack = new Track();
					currentTrack = currentAlbum.getTrackList().get(k);

					System.out.println(currentTrack.getTitleName() + " ("
							+ currentTrack.getDuration() + ")");

					data += "INSERT INTO SONGS (SONG, DURATION) VALUES ('"
							+ currentTrack.getTitleName().trim()
									.replaceAll("'", "''") + "', '"
							+ currentTrack.getDuration() + "') ;\n";

					data += "INSERT INTO ALBUMS_SONGS (ID_SONG, ID_ALBUM) VALUES ((SELECT MAX(ID) FROM SONGS WHERE SONG = '"
							+ currentTrack.getTitleName().trim()
									.replaceAll("'", "''")
							+ "' AND DURATION = '"
							+ currentTrack.getDuration()
							+ "'), (SELECT MAX(ID) FROM ALBUMS WHERE ALBUM_NAME = '"
							+ currentAlbum.getName().trim()
									.replaceAll("'", "''")
							+ "' AND DESCRIPTION = '"
							+ currentAlbum.getDescription().trim()
									.replaceAll("'", "''") + "')) ;\n";

				}
			}
		}

		bufferWritter.write(data);
		bufferWritter.close();
		System.out.println("Done");
	}
}
