package com.isepfm.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Lastfm {

	private String topArtistURL = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=3feba82e23f73071739389aa3fde518f&limit=1500";

	public String getTopArtistURL() {
		return topArtistURL;
	}

	public Lastfm() {

	}

	public void getTopArtistDownloadImage(String url, String directory)
			throws IOException {
		// Download xml file first

		URL url2;
		try {
			url2 = new URL(url);
			InputStream is = url2.openStream();
			OutputStream os = new FileOutputStream("download.xml");
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
		File xmlFile = new File("download.xml");

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List artists = rootNode.getChildren("artists");

			Iterator i = artists.iterator();
			while (i.hasNext()) {

				Element artiststNode = (Element) i.next();
				System.out.println(artiststNode);
				List listArtist = artiststNode.getChildren("artist");

				for (int j = 0; j < listArtist.size(); j++) {
					Element node = (Element) listArtist.get(j);

					List imageNode = node.getChildren("image");
					for (int k = 0; k < imageNode.size(); k++) {

						Element imageElement = (Element) imageNode.get(k);

						System.out.println("Artist name : "
								+ node.getChildText("name"));

						System.out.println("downloading image size:"
								+ imageElement.getAttributeValue("size") + " "
								+ imageElement.getValue());
						URL url3 = new URL(imageElement.getValue());
						InputStream is2 = url3.openStream();

						File dir = new File(directory
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

						final String dire = System.getProperty("user.dir");
						System.out.println("current dir = " + dire);

						is2.close();
						os2.close();
					}
				}
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		File fileToDelete = new File("download.xml");
		fileToDelete.delete();
	}

}
