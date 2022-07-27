package web_engine;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

import library.TST;

public class FindingWord {

	private static final String DIRECTORY_PATH = "src/WebPagesInText/";

	/*
	 * public static void main(String[] args) {
	 * 
	 * readAllFiles(); }
	 */

	/**
	 * Methods are used to read all text files from any directory
	 * 
	 * @throws IOException
	 *
	 */
	public static void readAllFiles() throws IOException {
		// create instance of directory
		File fileDirctry = new File(DIRECTORY_PATH);
		Scanner scannr_Input = new Scanner(System.in);
		WordFrequency wrdfrq = new WordFrequency();

		String Restart_arr;

		// Get list of all the files in form of String Array
		String[] namingtheFiles = fileDirctry.list();
		
		// Map used to store Name of Text file mapped to the occurrence of word
		Map<String, Integer> hashMapping = new HashMap<String, Integer>();

		do {
			System.out.println("Input Search Word: ");
			String searchKeyword = scannr_Input.nextLine(); // This read user input
			
			

			// loop for reading the contents of all the files
			for (String files : Objects.requireNonNull(namingtheFiles)) {

				String namedtheFile = DIRECTORY_PATH + files;
				File currfile = new File(namedtheFile);
				if (currfile.exists() && currfile.isFile() && currfile.canRead()) {
					Path file_Path = Paths.get(namedtheFile);
					hashMapping.put(file_Path.getFileName().toString(), new Integer(wrdfrq.findFrequencyInAFile(file_Path, searchKeyword)));

				}
			}

			Map<String, Integer> MappingAfterSorting = sortingByValue(hashMapping);

			// Ranking method called to rank the HTML files from most occurred to least
			// occurred
			RankingFiles(MappingAfterSorting);						
					
			System.out.println("Searching For Another Word? Yes/No");
			Restart_arr = scannr_Input.nextLine();
		} while (Restart_arr.equals("yes") || Restart_arr.equals("Yes"));

		if (Restart_arr.equals("No") || Restart_arr.equals(""))
			System.out.println("Thanks for our Services. Help us get Good Grade");

	}
	
	
	
	
	 // Method for sorting the files on the basis of occurrence of the word
	  private static Map<String, Integer> sortingByValue(Map<String, Integer> unsortMap) {

	       
	        List<Map.Entry<String, Integer>> mappedList =
	                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

	        
	        Collections.sort(mappedList, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2) {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });

	        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
	        Map<String, Integer> map_AfterSort = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> entry : mappedList) {
	        	map_AfterSort.put(entry.getKey(), entry.getValue());
	        }

	        /*
	        //classic iterator example
	        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
	            Map.Entry<String, Integer> entry = it.next();
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }*/


	        return map_AfterSort;
	    }
	
	
	
	 
	  
	  
	  public static <K, V> void RankingFiles(Map<K, V> map) throws IOException {
	  ArrayList keyWrdLst = new ArrayList(map.keySet());
		BufferedReader reader_Buffer = new BufferedReader(new FileReader("src/Cache.txt"));
		ArrayList<String> line_arrList = new ArrayList<>();
		Map<String, String> hmtMap = new HashMap<String, String>();
		String individualLine = reader_Buffer.readLine(); 
	  System.out.println("Ranking of the files");
	  int rank=1;
		for (int i = keyWrdLst.size() - 1; i >= 0; i--) {
			
			
			while (individualLine != null) { 
				line_arrList.add(individualLine); individualLine = reader_Buffer.readLine(); 
				} 
		
			
			for(String strng: line_arrList)
			{
				String[] tmp=strng.split(" ");
				hmtMap.put(tmp[1],tmp[0]);
				
			}
			
			
			// File
			String key = (String) keyWrdLst.get(i);
			
			
			System.out.println(rank+". "+" |||       Occurrence of Word: "+map.get(key) +"   |||       URL:"+hmtMap.get(key));
			
		   
			//Occurrences of the value
			//int value =(int) map.get(key);
			//System.out.println("Value :: " + value);
			rank++;
		}
		
		reader_Buffer.close();
	  
	  }
	  
	
}

