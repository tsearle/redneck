package com.sipstacks.redneck;

import com.sipstacks.redneck.GetJson;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.*;
import com.sipstacks.redneck.Markov;


public class Redneck 
{
	public static List<String> getStrings() {
		List<String> results = new ArrayList<String>();
		JSONObject obj = (JSONObject)GetJson.get("http://api.urbandictionary.com/v0/random");
		JSONArray list = (JSONArray)obj.get("list");
		for(Object item : list) {
			JSONObject defintion = (JSONObject)item;
			Object result = defintion.get("example");
			if(result != null) {
				String sResult = result.toString().replaceAll("^\"(.*)\"$", "$1");
				results.add(sResult);
			}
		}
		return results;
	}



	public static void main(String args[]) {
		for (int i = 0; i < 30; i++) {
			List<String> results = getStrings();
			for (String res : results) {
				if(res != null) {
					Markov.addWords(res);
				}
			}
			try{Thread.sleep(300);}catch(Exception e) {}
		}
		for (int i = 0; i < 10; i++) {
			String result = Markov.generateSentence();
			System.err.println(result);
		}
	}
}


