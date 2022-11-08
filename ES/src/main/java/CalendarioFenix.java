import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.File;
import javax.net.ssl.HttpsURLConnection;
import java.io.FileWriter;

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
		String jsonStringI = "[";
		boolean isInsideEvent = false;

		while ((inputLine = br.readLine()) != null) {
			if(inputLine.compareTo("BEGIN:VEVENT")==0) {
				jsonStringI = jsonStringI +"{"  + inputLine + "," ;
				isInsideEvent = true;
				writer.write(jsonStringI);
			}
			else if (inputLine.contains("DTSTART:")) {
				jsonStringI = jsonStringI + inputLine + "," ;
				writer.write(jsonStringI);
			}
			else if (inputLine.contains("DTEND:")) {
				jsonStringI = jsonStringI + inputLine + "," ;
				writer.write(jsonStringI);
			}
			else if (inputLine.contains("SUMMARY:")) {
				jsonStringI = jsonStringI + inputLine + "," ;
				writer.write(jsonStringI);
			}
			else if (inputLine.contains("LOCATION:")) {
				jsonStringI = jsonStringI + inputLine + "," ;
				writer.write(jsonStringI);
			}
			else if(inputLine.compareTo("END:VEVENT")==0 && isInsideEvent) {
				jsonStringI = jsonStringI + inputLine + "}";
				writer.write(jsonStringI);
			}
		}
		jsonStringI += "]";
		writer.write("]");
//		System.out.println(jsonStringI);
		br.close();
		writer.close();
	}
}
