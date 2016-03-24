package com.sipstacks.redneck;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import org.json.simple.*;



public class GetJson{
	
	public static Object get(String myUrl) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";

		try {
			url = new URL(myUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (IOException e) {
			return e.getMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		return JSONValue.parse(result);
	}
}
