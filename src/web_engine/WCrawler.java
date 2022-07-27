package web_engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import web_engine.HTMLtoTextConvertor;
import web_engine.WCrawler;

public class WCrawler implements Runnable {
	private Thread thread;
	private static Map<String, String> visit = new HashMap<String, String>();
	private String linktosearch;
	private static int depth = 3;
	private static int total = 0;
	private static int totalf = 0;
	private static int count = 0;
	public static String regexpglobal = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	public WCrawler(String link, int maxlevel, int totalLink) {
		depth = maxlevel;
		totalf = totalLink;
		linktosearch = link;
		thread = new Thread(this);

		thread.start();
	}

	@Override
	public void run() {
		crawl(0, linktosearch);
	}

	private static void crawl(int level, String url) {
		if (total < totalf && level <= depth) {
			Document doc = request(url);
			if (doc != null) {
				{
					total++;
					for (Element link : doc.select("a[href]")) {
						String nextlink = link.absUrl("href");

						if (isURL(nextlink)) {

							if (!visit.containsKey(nextlink)) {
								int nxtLvl = level++;

								crawl(nxtLvl, nextlink);
							}
						}
					}
				}
			}

		}

	}

	private static boolean isURL(String url) {
		String regexplocal = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

		Pattern p = Pattern.compile(regexplocal);
		Matcher m = p.matcher(url);

		if (m.matches()) {
			return true;
		}
		return false;

	}

	private static Document request(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document document = connection.get();

			if (connection.response().statusCode() == 200) {
				System.out.println(url);
				String title = document.title();
				visit.put(url, title);
				writeFile(title, document);

				HTMLtoTextConvertor.convertingToTextFile(url);
				
				return document;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public static void writeFile(String title, Document doc) {
		count++;
		BufferedWriter writeText;
		try {
			writeText = new BufferedWriter(new FileWriter("src/saved/" + title + "" + count + ".html"));
			writeText.write(doc.outerHtml());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getLocalizedMessage();
		}
	}

	public static void main(String[] args) {
		WCrawler wc = new WCrawler("https://uwindsor.ca/", 3, 11);
		

	}

}
