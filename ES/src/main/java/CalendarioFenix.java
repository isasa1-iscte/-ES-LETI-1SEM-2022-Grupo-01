import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
//import org.json.JSONArray;
//import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalendarioFenix {

	//	Class that fetches Fenix Calendar and converts into json

	public static void main(String[] args) throws IOException{
		String httpsURL = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=isasa1@iscte.pt&password=zLCpZLCYvhVBeyUsaEsd4ZNeFKdfHPgYzim2ITS5INtWNQzHAKsk1gfaBLNnhnCLcc5WGxYDKqmBz9OAbulLqWAn2NDiHQn7kfyqZ4M8WQwcuiZXvAWb9Oz55wXjyeRc";
		URL myURL = new URL(httpsURL);
		HttpsURLConnection urlConnection = (HttpsURLConnection)myURL.openConnection();
		InputStream is = urlConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String inputLine;
		File jsonInes = new File("jsonInes.txt");
		FileWriter writer = new FileWriter(jsonInes);
		String jsonStringI = "[\n";
		boolean isInsideEvent = false;

		while ((inputLine = br.readLine()) != null) {
			if(inputLine.compareTo("BEGIN:VEVENT")==0) {
				jsonStringI = jsonStringI +"{\n"  + inputLine + ",\n" ;
				isInsideEvent = true;
			}
			else if (inputLine.contains("DTSTART:")) {
				jsonStringI = jsonStringI + inputLine + ",\n" ;
			}
			else if (inputLine.contains("DTEND:")) {
				jsonStringI = jsonStringI + inputLine + ",\n" ;
			}
			else if (inputLine.contains("SUMMARY:")) {
				jsonStringI = jsonStringI + inputLine + ",\n" ;
			}
			else if (inputLine.contains("LOCATION:")) {
				jsonStringI = jsonStringI + inputLine + ",\n" ;
			}
			else if(inputLine.compareTo("END:VEVENT")==0 && isInsideEvent) {
				jsonStringI = jsonStringI + inputLine + ",\n}\n";
			}
		}
		jsonStringI += " ]";
		writer.write(jsonStringI);
		
		br.close();
		writer.close();
//
//		ArrayList<String> eventos = new ArrayList<String>();
//		Scanner scanner=new Scanner(jsonInes);
//		while(scanner.hasNextLine()){
//			eventos.add(scanner.nextLine());
//		}
//		scanner.close();

		System.out.println();

	}
}