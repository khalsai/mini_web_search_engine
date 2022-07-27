package web_engine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import library.Trie;
import library.TrieNode;
public class WordSpellCorrect {
	private static final String DIR_PATH = "src/WebPagesInText";
	Trie trie = new Trie();
	Map<String, Integer> CountWord = new HashMap<>(); // creating a hash map - CountWord
	public void loadSpell_Correct() { // https://stackabuse.com/java-list-files-in-a-directory/
		File file = new File(DIR_PATH);
		File[] files_list = file.listFiles(); // using a File class as an array
		
		for (int i = 0; i < files_list.length; i++) { // iterate through each file
			if (files_list[i].isFile()) { // check if specified file is present or not
				try {
					storeDictionary(files_list[i]);
				} catch (IOException e) {
					System.out.println("in exception block1");
					e.printStackTrace();
				}
			}
		}
	}
	public void storeDictionary(File fileNme) throws IOException {
		try {
			FileReader fileReader = new FileReader(fileNme); // // reading text file into array
			BufferedReader buffer_Reader = new BufferedReader(fileReader);
			String line = ""; // new line with value empty string
			while ((line = buffer_Reader.readLine()) != null) { // read file line by line only if it is not null
				String words = line.toLowerCase(); // converting to lower case
				// System.out.println(words);
				if (!line.contains(" ")) {
					words = words.toLowerCase(); // converting to lower case
					if (isAlpha(words)) {
						CountWord.put(words, CountWord.getOrDefault(words, 0) + 1); // increase count if word is present
						trie.addWord(words); // add words to trie
					}
				} else {
					String[] strgWords = line.split("\\s");
					for (String sWords : strgWords) {
						sWords = sWords.toLowerCase();
						if (isAlpha(sWords)) {
							CountWord.put(sWords, CountWord.getOrDefault(sWords, 0) + 1);
							trie.addWord(sWords); // add word to trie
						}
					}
				}
			}
			fileReader.close();
			buffer_Reader.close();
		} catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();
			System.out.println(e);
		}
	}
	public static boolean isAlpha(String sWords) {
		return ((sWords != null) && (!sWords.equals("")) && (sWords.matches("^[a-zA-Z]*$"))); // check if it contains all alphabets																							
	}
	public String findSimilarWords(String inputWord) { // input from the user
		String rslt = "";
		if (inputWord.length() == 0 || inputWord == null) { // if input is empty, return result
			return rslt;
		}
		String sLowerInputWord = inputWord.toLowerCase(); // converting input to lower case
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();
		TrieNode node = trie.search(sLowerInputWord); // search input word in trie
		if (node == null) {
			for (String word : CountWord.keySet()) {
				int dist = library.Sequences.editDistance(word, sLowerInputWord); // calculate min distance between string and input word
				TreeMap<Integer, TreeSet<String>> similar_Words = map.getOrDefault(dist, new TreeMap<>());
				int freq = CountWord.get(word);
				TreeSet<String> set = similar_Words.getOrDefault(freq, new TreeSet<>());
				set.add(word);
				similar_Words.put(freq, set);
				map.put(dist, similar_Words);
			}
			rslt = map.firstEntry().getValue().lastEntry().getValue().first();
		} else if (node != null) {
			rslt = sLowerInputWord;
		}
		return rslt;
	}
	public ArrayList<String> autocomplete(String inputWord) {
		ArrayList<String> strg = new ArrayList<String>();
		if (inputWord.length() == 0 || inputWord == null) {
			return strg;
		}
		String sLowerInputWord = inputWord.toLowerCase();
		TrieNode node = trie.search(sLowerInputWord);
		if (node == null) {
			for (String words : CountWord.keySet()) {
				if (!words.isEmpty()) {
					if (words.startsWith(sLowerInputWord)) {
						strg.add(words);
					}
				}
			}
		}
		return strg;
	}
}
