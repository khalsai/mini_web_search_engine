package web_engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WebSearchApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		WordSpellCorrect o_corrector;

		while (true) {
			System.out.println("Please choose one of the options from the given list");
			System.out.println("Enter 1 if : Searching a URL (Web Crawling)");
			System.out.println("Enter 2 if : Deleting Cache");
			System.out.println("Enter 3 if:  Ranking web pages according to the occurence of a partiuclar word");
			System.out.println("Enter 4 if : Spell-Correct ");
			System.out.println("Enter 5 if : Auto-Complete (Words Suggestions)");
			System.out.println("Enter 6 if:  Word Computation Frequency");
			System.out.println("Enter 7 if : Program EXIT");

			Scanner o_sc = new Scanner(System.in);
			System.out.println("Please enter the option you want to run ->");
			int c_choice = o_sc.nextInt();
			o_sc = new Scanner(System.in);
			if (c_choice == 7) {
				System.out.println("Program is terminating now!!!!!");
				break;
			}

			switch (c_choice) {
			case 0:
				System.out.println("Select a valid number please");
				break;

			case 1:
				
				System.out.println("***************************************************************************");
				System.out.println("Kindly enter the url that you want to search");
				System.out.println("***************************************************************************");
				
				String url = o_sc.nextLine();
				    System.out.println("***************************************************************************");
					System.out.println("Enter the depth please");
					System.out.println("***************************************************************************");
				int depth = o_sc.nextInt();
				    System.out.println("***************************************************************************");
					System.out.println("Enter the total number of links please");
					System.out.println("***************************************************************************");
				int maximum = o_sc.nextInt();
				if (isValidUrl(url)) {
					if (!Cache_Manage.Is_URL_Available(url)) {
						WCrawler o_wc = new WCrawler(url, depth, maximum);

					} else {
						System.out.println("This particular URL has been crawled already.");
					}

				} else {
					System.out.println("Enter a valid URL PLEASE");
				}

				break;

			case 2:
				Cache_Manage.Delete_Cache();
				break;

			case 3:
				System.out.println("Kindly enter a word that you want to search.");
				FindingWord.readAllFiles();
				break;

			case 4:
				System.out.println("Kindly enter a word");

				String s_Search = o_sc.nextLine();
				o_corrector = new WordSpellCorrect();

				o_corrector.loadSpell_Correct();
				String o_suggestion = o_corrector.findSimilarWords(s_Search);
				if (o_suggestion.length() == 0)
					System.out.println("There are no such words present. Kindly enter a valid word to search");
				else
					System.out.println("Suggestions: " + o_suggestion);

				break;

			case 5:
				System.out.println("Kindly enter a word which you want to Autocomplete");
				String o_Search = o_sc.nextLine();
				o_corrector = new WordSpellCorrect();

				o_corrector.loadSpell_Correct();
				ArrayList<String> o_suggestion1 = o_corrector.autocomplete(o_Search);
				System.out.println(o_suggestion1.toString());

				break;
				
			case 6:
				System.out.println("Enter a word whose frequency you want to Compute::");
				String Work_f_Frequency = o_sc.nextLine();
				Frequency_word.Find_frequency(Work_f_Frequency);
				break;

			default:
				System.out.println("Kindly select a valid number from above given options");
				break;
			
		}

	}
}		

	private static boolean isValidUrl(String url) {
		if (Pattern.matches(WCrawler.regexpglobal, url))
			return true;
		return false;
	}

}
