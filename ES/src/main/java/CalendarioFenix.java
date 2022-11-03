import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CalendarioFenix {

//	Class that fetches Fenix Calendar and converts into json
	
	public static void main(String[] args) throws IOException{
		String httpsURL = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=isasa1@iscte.pt&password=zLCpZLCYvhVBeyUsaEsd4ZNeFKdfHPgYzim2ITS5INtWNQzHAKsk1gfaBLNnhnCLcc5WGxYDKqmBz9OAbulLqWAn2NDiHQn7kfyqZ4M8WQwcuiZXvAWb9Oz55wXjyeRc";
		URL myURL = new URL(httpsURL);
		HttpsURLConnection con = (HttpsURLConnection)myURL.openConnection();
		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String inputLine;
		String jsonString = "[";
		boolean isInsideEvent = false;
		while ((inputLine = br.readLine()) != null) {
			if(inputLine.compareTo("BEGIN:VEVENT")==0) {
				jsonString = jsonString +"{";
				isInsideEvent = true;
			}
			else if(inputLine.compareTo("END:VEVENT")==0 && isInsideEvent) {
				jsonString = jsonString + inputLine + "}";
			}
		}
		jsonString += "]";
		System.out.println(jsonString);
		br.close();
	}
}