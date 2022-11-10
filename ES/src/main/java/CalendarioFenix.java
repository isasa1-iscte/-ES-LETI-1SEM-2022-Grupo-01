import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CalendarioFenix {

	//	Class that fetches Fenix Calendar and converts into json

	public static void main(String[] args) throws IOException{
		String httpsURL = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=isasa1@iscte.pt&password=zLCpZLCYvhVBeyUsaEsd4ZNeFKdfHPgYzim2ITS5INtWNQzHAKsk1gfaBLNnhnCLcc5WGxYDKqmBz9OAbulLqWAn2NDiHQn7kfyqZ4M8WQwcuiZXvAWb9Oz55wXjyeRc";
		URL myURL = new URL(httpsURL);
		HttpsURLConnection urlConnection = (HttpsURLConnection)myURL.openConnection();
		InputStream is = urlConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String httpsURLH = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=hmsea@iscte.pt&password=S98lQuMJLj5SbJOigxwvkhW6NxnbFjvrqtznetteK3nR04ozgL7BgMqEOPKFzgLMcaiXi5Ygjral29BCRkBmF9V3FTdkrne6yYxETDRIEapjhD8tOiGR5pB811UH8Pti";
		URL hURL = new URL(httpsURLH);
		HttpsURLConnection urlConnectionH = (HttpsURLConnection)hURL.openConnection();
		InputStream is2 = urlConnectionH.getInputStream();
		InputStreamReader isr2 = new InputStreamReader(is2);
		BufferedReader br2 = new BufferedReader(isr2);

		String inputLine;
		File jsonInes = new File("jsonInes.txt");
		FileWriter writer = new FileWriter(jsonInes);
		String jsonStringI = "[\n";
		boolean isInsideEvent = false;
		String inputLineHugo;
		File jsonHugo = new File("jsonHugo.txt");
		FileWriter writerHugo = new FileWriter(jsonHugo);
		String jsonStringH = "[\n";
		boolean isInsideEvent2 = false;

		while ((inputLine = br.readLine()) != null) {
			if(inputLine.compareTo("BEGIN:VEVENT")==0) {
				jsonStringI = jsonStringI + "\"BEGIN\":\"VEVENT\",\n";
				isInsideEvent = true;
			}
			if (inputLine.contains("DTSTART:")) {
				inputLine = inputLine.replace("DTSTART:", "");
				jsonStringI = jsonStringI + "\"DTSTART\":\"" + inputLine +"\",\n" ;
			}
			if (inputLine.contains("DTEND:")) {
				inputLine = inputLine.replace("DTEND:", "");
				jsonStringI = jsonStringI + "\"DTEND\":\"" + inputLine +"\",\n" ;
			}
			if (inputLine.contains("SUMMARY:")) {
				inputLine = inputLine.replace("SUMMARY:", "");
				jsonStringI = jsonStringI + "\"SUMMARY\":\"" + inputLine +"\",\n" ;
			}
			if (inputLine.contains("LOCATION:")) {
				inputLine = inputLine.replace("LOCATION:", "");
				jsonStringI = jsonStringI + "\"LOCATION\":\"" + inputLine +"\",\n" ;
			}
			else if(inputLine.compareTo("END:VEVENT")==0 && isInsideEvent) {
				jsonStringI = jsonStringI + "\"END\":\"VEVENT\",\n}\n";
			}
		}
		jsonStringI += "]";
		writer.write(jsonStringI);

		br.close();
		writer.close();
	
		while ((inputLineHugo = br2.readLine()) != null) {
			if(inputLineHugo.compareTo("BEGIN:VEVENT")==0) {
				jsonStringH = jsonStringH +"\"BEGIN\":\"VEVENT\",\n" ;
				isInsideEvent2 = true;
			}
			else if (inputLineHugo.contains("DTSTART:")) {
				inputLineHugo = inputLineHugo.replace("DTSTART:", "");
				jsonStringH = jsonStringH + "\"DTSTART\":\"" + inputLineHugo + "\",\n" ;
			}
			else if (inputLineHugo.contains("DTEND:")) {
				inputLineHugo = inputLineHugo.replace("DTEND:", "");
				jsonStringH = jsonStringH + "\"DTEND\":\"" + inputLineHugo + "\",\n" ;
			}
			else if (inputLineHugo.contains("SUMMARY:")) {
				inputLineHugo = inputLineHugo.replace("SUMMARY:", "");
				jsonStringH = jsonStringH + "\"SUMMARY\":\"" + inputLineHugo + "\",\n" ;
			}
			else if (inputLineHugo.contains("LOCATION:")) {
				inputLineHugo = inputLineHugo.replace("LOCATION:", "");
				jsonStringH = jsonStringH + "\"LOCATION\":\"" + inputLineHugo + "\",\n" ;
			}
			else if(inputLineHugo.compareTo("END:VEVENT")==0 && isInsideEvent2) {
				jsonStringH = jsonStringH + "\"END\":\"VEVENT\",\n}\n";
			}
		}

		jsonStringH += "]";
		writerHugo.write(jsonStringH);
			System.out.println(jsonStringH);
		br2.close();
		writerHugo.close();
		
	}

}
