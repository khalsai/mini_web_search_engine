package web_engine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLtoTextConvertor {

	public static void main(String args[]) {
		try {
			convertingToTextFile("https://wikihow.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Reference:: https://www.programiz.com/java-programming/printwriter
	 * 
	 * @param url
	 */
	public static void convertingToTextFile(String htmlURL) throws IOException {

		File webPagesInTextDirectory = new File("src/WebPagesInText");
		if (!webPagesInTextDirectory.exists()) {
			webPagesInTextDirectory.mkdir();
		}

		Connection jsoupConnect = Jsoup.connect(htmlURL);
		Document doc = jsoupConnect.get();
		

		String fileName = "";
	    
		long millisec = System.currentTimeMillis();
	    
		String date_time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    
		String randmchars = RandomStringUtils.randomAlphanumeric(16);
	    
		fileName = randmchars + "_" + date_time + "_" + millisec;


		File f = new File("src/WebPagesInText/" + fileName + ".txt");

		if (!f.exists()) {
			f.createNewFile();
		}
		Cache_Manage.Add_Cache(htmlURL + " " + fileName + ".txt");

		String text = doc.text();
		// initialize the print writer object
		PrintWriter out = new PrintWriter("src/WebPagesInText/" + fileName + ".txt");
		// write all the text to the file
		out.println(text);
		// close the file
		out.close();
		
	}
	
	
}
