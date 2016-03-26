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
		Object resobj = GetJson.get("http://api.urbandictionary.com/v0/random");
		if (! (resobj instanceof JSONObject)) {
			System.err.println("Unexpected response: " + resobj.toString());
			return results;
		}
		JSONObject obj = (JSONObject)resobj;
		JSONArray list = (JSONArray)obj.get("list");
		for(Object item : list) {
			JSONObject defintion = (JSONObject)item;
			Object result = defintion.get("example");
			if(result != null) {
				String sResult = result.toString();						sResult = sResult.replaceAll("\r\n", "\n");
				sResult = sResult.replaceAll("\t", " ");
				sResult = sResult.replaceAll("[”“]", "\"");
				sResult = sResult.replaceAll("\"([^\"]*)\"", "$1");

//				sResult = sResult.replaceAll("[a-zA-Z0-9]\n", ".\n");
//				sResult = sResult.replaceAll("[a-zA-Z0-9]$", ".\n");
				sResult = sResult.replaceAll("\\[([^\\]]*)\\]", "$1");
				sResult = sResult.replaceAll("(^|\n)([ ]*)([a-z0-9]+\\.)([^\n]*)", "$4\n");
				sResult = sResult.replaceAll("(^|\n)([ ]*)([a-zA-Z0-9]+\\))([^\n]*)", "$4\n");
				sResult = sResult.replaceAll("(^|\n)([ ]*)([a-zA-Z0-9_# -]+\\:)([^\n]*)", "$4\n");


				// nuke fake bold
				sResult = sResult.replaceAll("\\*([^\\*]*)\\*", "$1");
				// nuke () comments just makes the chain confused
				sResult = sResult.replaceAll("\\(([^\\)]*)\\)", "");


				//System.err.println(sResult);
				//System.err.println("***End of Result****");

				results.add(sResult);
			}
		}
		return results;
	}



	public static void main(String args[]) {
		while(true) {
			for (int i = 0; i < 30; i++) {
				List<String> results = getStrings();
				for (String res : results) {
					if(res != null) {
						Markov.addWords(res);
					}
				}
				try{Thread.sleep(300);}catch(Exception e) {}
			}
			for (int i = 0; i < 1; i++) {
				String result = Markov.generateSentence();
				System.err.println(result);
			}
		}
	}
}


