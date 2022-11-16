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

			inputLine = inputLine.replaceAll("\"","'");

			if (inputLine.contains("BEGIN:VEVENT")) {
				jsonStringI = jsonStringI + "{\n"; isInsideEvent = true; 
				continue;}

			if (!isInsideEvent) {continue;}

			if (inputLine.contains("END:VEVENT")) { 
				jsonStringI = jsonStringI + "},\n"; 
				isInsideEvent = false; 
				continue;
			}


			if (inputLine.contains("DTSTAMP:") || inputLine.contains("DTSTART:") || inputLine.contains("DTEND:") ||

					inputLine.contains("SUMMARY:") || inputLine.contains("UID:") || inputLine.contains("DESCRIPTION:") ||

					inputLine.contains("LOCATION:"))            

				jsonStringI =  jsonStringI + "\"" + inputLine.replaceFirst(":","\":\"") + "\",\n";

			else jsonStringI = jsonStringI.substring(0, jsonStringI.length() - 3) + inputLine.substring(1, inputLine.length()) + "\",\n";

		}

		if (isInsideEvent) jsonStringI += "}]"; else jsonStringI += "]";

		writer.write(jsonStringI);
		br.close();
		writer.close();
		
		while ((inputLineHugo = br2.readLine()) != null) {

			inputLineHugo = inputLineHugo.replaceAll("\"","'");

			if (inputLineHugo.contains("BEGIN:VEVENT")) {
				jsonStringH = jsonStringH + "{\n"; isInsideEvent2 = true; 
				continue;}

			if (!isInsideEvent2) {continue;}

			if (inputLineHugo.contains("END:VEVENT")) { 
				jsonStringH = jsonStringH + "},\n"; 
				isInsideEvent2 = false; 
				continue;
			}


			if (inputLineHugo.contains("DTSTAMP:") || inputLineHugo.contains("DTSTART:") || inputLineHugo.contains("DTEND:") ||

					inputLineHugo.contains("SUMMARY:") || inputLineHugo.contains("UID:") || inputLineHugo.contains("DESCRIPTION:") ||

					inputLineHugo.contains("LOCATION:"))            

				jsonStringH =  jsonStringH + "\"" + inputLineHugo.replaceFirst(":","\":\"") + "\",\n";

			else jsonStringH = jsonStringH.substring(0, jsonStringH.length() - 3) + inputLineHugo.substring(1, inputLineHugo.length()) + "\",\n";

		}

		if (isInsideEvent2) jsonStringH += "}]"; else jsonStringH += "]";
	
				writerHugo.write(jsonStringH);
				br2.close();
				writerHugo.close();

	}

}
