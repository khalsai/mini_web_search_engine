package web_engine;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

import library.In;


public class Cache_Manage {
	

		
		public static void Delete_Cache() throws IOException {

			// Getting the cache file
			File Cache_File = new File("src/Cache.txt");

			// Clearing the cache file
			if (Cache_File.exists()) {
				// File output stream
				FileOutputStream o_FileOutputStream= new FileOutputStream(Cache_File);

				// Initializing the print writer with object
				PrintWriter o_PrintWriter = new PrintWriter(o_FileOutputStream);

				// writing all the text data to the file
				o_PrintWriter.write("");
				o_PrintWriter.close();
				System.out.println("File deletion successful! ");
			} else {
				System.out.println("This file does not exists");
			}

			// Deleting the directory
			File o_dire_text = new File("src/WebPagesInText/");

			// checking if the directory exists if exists then deleting it
			if (o_dire_text.exists()) {
				FileUtils.cleanDirectory(o_dire_text);
				o_dire_text.delete();
			}
			System.out.println("Cache clearing successful");
		}

		
		public static void Add_Cache(String cache) throws IOException {

			// Get the cache file
			File Cache_File = new File("src/Cache.txt");

			// If cache file doesn't exist then create the file
			if (!Cache_File.exists()) {
				Cache_File.createNewFile();
			}

			// File output stream object creation
			FileOutputStream o_FileOutputStream = new FileOutputStream(Cache_File, true );

			// Initializing the print writer object 
			PrintWriter o_PrintWriter = new PrintWriter(o_FileOutputStream);

			// writing all the text to  file
			o_PrintWriter.append(cache + "\n");

			// closing the file
			o_PrintWriter.close();
		}

		
		public static Boolean Is_URL_Available(String uRL_passed) throws IOException {
			
			In Cache_File = new In("src/Cache.txt");
			
			// If the file is empty then returning false
			if(Cache_File.isEmpty()) {
				return false;
			}
			
			// Reading line by line from the cache file until it gets empty
			while (!Cache_File.isEmpty()) {
				String o_line = Cache_File.readLine();
				// Getting the URL from the string
				String url_in_Cache = o_line.split(" ")[0];
				// Comparing the URL with the input URL, if found then returning true
				if (url_in_Cache.equals(uRL_passed)) {
					return true;
				}
			}

			return false;
		}

	}


