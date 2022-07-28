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


public class Frequency_word {

	static void Find_frequency(String Word_Search) {
		int o_TN;
		File[] o_files = new File("src/WebPagesInText/").listFiles(); // this gets the path of file
		for (File fILE : o_files) { // Iterates the folder and get all the html files in it
			String File_Name = "src/WebPagesInText/"+fILE.getName();
			
					
			int o_Freq = Finding_Frequency_In_A_File(Paths.get(File_Name),Word_Search);

			// prints total number of occurrences
			System.out.println("In File" + "\"" + File_Name + "\"" + " Frequency is : " + o_Freq);
		}
	}

		static int Finding_Frequency_In_A_File(Path o_path, String Word_Search) {
			StringBuilder o_builder = new StringBuilder();
			try (BufferedReader o_buffer = new BufferedReader(
					new FileReader(o_path.toString()))) {
				String o_str;
				// Appends the text from file into a String o_builder
				while ((o_str = o_buffer.readLine()) != null) {

					o_builder.append(o_str).append("\n");
				}
			} catch (IOException o_e) {
				o_e.printStackTrace();
			}
			// Converts the string o_builder to a string
			String o_text = o_builder.toString();
			HashMap<String, Integer> o_map = new HashMap<>();

			String[] o_list = o_text.split(" ");
			int initial = 1;
			for (String o_st : o_list) {
				String o_s = o_st.toLowerCase();

				if (!o_s.equals("")) {
					if (o_map.containsKey(o_s)) {
						int o_c = o_map.get(o_s);
						o_map.put(o_s, o_c + 1);
					} else {
						o_map.put(o_s, initial);
					}

				}
			}

			int Frequency = 0;
			if (o_map.containsKey(Word_Search)) {
				Frequency = o_map.get(Word_Search);
			}
			return Frequency;
		}
		
		

	}

