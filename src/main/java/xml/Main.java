package xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

class Main {
	public static void main(String[] args) throws IOException {

		Main2 main2 = new Main2();

	}

	public static void GenerateSQL() throws IOException {

		String data = "";
		File file = new File("downloadDirectory" + "generatedSQL.sql");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fileWritter = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

		bufferWritter.write(data);
		bufferWritter.close();
		System.out.println("Done");
	}

}
