package com.sipstacks.redneck;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.lang.StringBuffer;


public class Markov {

	// Hashmap
	public static Hashtable<String, Vector<String>> markovChain = new Hashtable<String, Vector<String>>();
	static {
		markovChain.put("_start", new Vector<String>());
		markovChain.put("_end", new Vector<String>());
	}
	static Random rnd = new Random();
	
	
	/*
	 * Main constructor
	 */
	/*
	public static void main(String[] args) throws IOException {
		
		// Create the first two entries (k:_start, k:_end)
		markovChain.put("_start", new Vector<String>());
		markovChain.put("_end", new Vector<String>());
		
		while(true) {
		// Get some words 
		System.out.print("Enter your phrase > ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String sInput = in.readLine() + ".";
		
		// Add the words to the hash table
		addWords(sInput);
		generateSentence();
		}
		
	}
	*/

	/**
	  Finds the next element, skipping over empty strings
	  Returns null if there isn't a next
	 */
	private static String getNext(String [] list, int last) {
		for (int i=last+1; i<list.length; i++) {
			if(list[i].length() == 0) continue;
			
			return list[i];
		}
		return null;
	}
	
	/*
	 * Add words
	 */
	public static void addWords(String phrase) {
		String[] lines = phrase.split("[\n]");
		for (String line : lines) {
			// put each word into an array
			String[] words = line.split("[\\s]+");

			if (words.length < 2) continue;
					
			// Loop through each word, check if it's already added
			// if its added, then get the suffix vector and add the word
			// if it hasn't been added then add the word to the list
			// if its the first or last word then select the _start / _end key

			boolean start = true;
			
			for (int i=0; i<words.length; i++) {

				if(words[i].length() == 0) continue;

				String nextWord = getNext(words, i);
							
				// Add the start and end words to their own
				if (start && nextWord != null) {
					start = false;
					Vector<String> startWords = markovChain.get("_start");
					startWords.add(words[i]);
				}
					
					
				if (nextWord != null) {	
					Vector<String> suffix = markovChain.get(words[i]);
					if (suffix == null) {
						suffix = new Vector<String>();
						suffix.add(nextWord);
						markovChain.put(words[i], suffix);
					} else {
						suffix.add(nextWord);
						markovChain.put(words[i], suffix);
					}
				} else {
					Vector<String> suffix = markovChain.get(words[i]);
					if (suffix == null) {
						suffix = new Vector<String>();
						suffix.add("_end");
						markovChain.put(words[i], suffix);
					} else {
						suffix.add("_end");
						markovChain.put(words[i], suffix);
					}
				}

			}		
		}
	}
	
	
	/*
	 * Generate a markov phrase
	 */
	public static String generateSentence() {
		
		StringBuffer  sb = new StringBuffer();
		// Vector to hold the phrase
		Vector<String> newPhrase = new Vector<String>();
		
		// String for the next word
		String nextWord = "";
				
		// Select the first word
		Vector<String> startWords = markovChain.get("_start");
		int startWordsLen = startWords.size();
		nextWord = startWords.get(rnd.nextInt(startWordsLen));
		
		// Keep looping through the words until we've reached the end
		while ( !nextWord.equals("_end") ) {
			newPhrase.add(nextWord);
			Vector<String> wordSelection = markovChain.get(nextWord);
			if (wordSelection == null) break;
			int wordSelectionLen = wordSelection.size();
			nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
		}

		for (String word : newPhrase) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(word);
		}
		return sb.toString();
	}
}
