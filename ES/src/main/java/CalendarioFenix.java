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
		String httpsURLI = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=isasa1@iscte.pt&password=zLCpZLCYvhVBeyUsaEsd4ZNeFKdfHPgYzim2ITS5INtWNQzHAKsk1gfaBLNnhnCLcc5WGxYDKqmBz9OAbulLqWAn2NDiHQn7kfyqZ4M8WQwcuiZXvAWb9Oz55wXjyeRc";
		URL iURL = new URL(httpsURLI);
		HttpsURLConnection urlConnectionI = (HttpsURLConnection)iURL.openConnection();
		InputStream is = urlConnectionI.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String httpsURLH = "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=hmsea@iscte.pt&password=S98lQuMJLj5SbJOigxwvkhW6NxnbFjvrqtznetteK3nR04ozgL7BgMqEOPKFzgLMcaiXi5Ygjral29BCRkBmF9V3FTdkrne6yYxETDRIEapjhD8tOiGR5pB811UH8Pti";
		URL hURL = new URL(httpsURLH);
		HttpsURLConnection urlConnectionH = (HttpsURLConnection)hURL.openConnection();
		InputStream is2 = urlConnectionH.getInputStream();
		InputStreamReader isr2 = new InputStreamReader(is2);
		BufferedReader br2 = new BufferedReader(isr2);
		

		String inputLineInes;
		File jsonInes = new File("jsonInes.txt");
		FileWriter writerInes = new FileWriter(jsonInes);
		String jsonStringI = "[";
		boolean isInsideEvent = false;

		while ((inputLineInes = br.readLine()) != null) {
			if(inputLineInes.compareTo("BEGIN:VEVENT")==0) {
				jsonStringI = jsonStringI +"\n{\n"  + inputLineInes + ",\n" ;
				isInsideEvent = true;
			}
			else if (inputLineInes.contains("DTSTART:")) {
				jsonStringI = jsonStringI + inputLineInes + ",\n" ;
			}
			else if (inputLineInes.contains("DTEND:")) {
				jsonStringI = jsonStringI + inputLineInes + ",\n" ;
			}
			else if (inputLineInes.contains("SUMMARY:")) {
				jsonStringI = jsonStringI + inputLineInes + ",\n" ;
			}
			else if (inputLineInes.contains("LOCATION:")) {
				jsonStringI = jsonStringI + inputLineInes + ",\n" ;
			}
			else if(inputLineInes.compareTo("END:VEVENT")==0 && isInsideEvent) {
				jsonStringI = jsonStringI + inputLineInes + "\n}";
			}
		}
		jsonStringI += "\n]";
		writerInes.write(jsonStringI);
//		System.out.println(jsonStringI);
		br.close();
		writerInes.close();
		
		
		
		String inputLineHugo;
		File jsonHugo = new File("jsonHugo.txt");
		FileWriter writerHugo = new FileWriter(jsonHugo);
		String jsonStringH = "[";
		boolean isInsideEvent2 = false;
		
		while ((inputLineHugo = br2.readLine()) != null) {
			if(inputLineHugo.compareTo("BEGIN:VEVENT")==0) {
				jsonStringH = jsonStringH +"\n{\n"  + inputLineHugo + ",\n" ;
				isInsideEvent2 = true;
			}
			else if (inputLineHugo.contains("DTSTART:")) {
				jsonStringH = jsonStringH + inputLineHugo + ",\n" ;
			}
			else if (inputLineHugo.contains("DTEND:")) {
				jsonStringH = jsonStringH + inputLineHugo + ",\n" ;
			}
			else if (inputLineHugo.contains("SUMMARY:")) {
				jsonStringH = jsonStringH + inputLineHugo + ",\n" ;
			}
			else if (inputLineHugo.contains("LOCATION:")) {
				jsonStringH = jsonStringH + inputLineHugo + ",\n" ;
			}
			else if(inputLineHugo.compareTo("END:VEVENT")==0 && isInsideEvent2) {
				jsonStringH = jsonStringH + inputLineHugo + "\n}";
			}
		}
		jsonStringH += "\n]";
		writerHugo.write(jsonStringH);
//		System.out.println(jsonStringH);
		br2.close();
		writerHugo.close();
	}
}
