package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GoogleBooksApiClient {
	private static final String API_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
	private static final String API_KEY = "AIzaSyCf-AXkwFk0I2dNalYr5QQRxmODdpXfDeQ";

	public List<Book> fetchBooksData(String query) 
	{
		List<Book> bookData = new ArrayList<>();
		try {
			String apiUrl = API_BASE_URL + "?q=" + URLEncoder.encode(query, "UTF-8") + "&key=" + API_KEY + "&startIndex=0&maxResults=20";
			URL url = new URL(apiUrl);
			HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
				Gson gson = new Gson();
				JsonObject responseJson = gson.fromJson(reader, JsonObject.class);
				JsonArray items = responseJson.getAsJsonArray("items");
				
				for(JsonElement item : items)
				{
				    JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
				    String title = volumeInfo.get("title").getAsString();

				    JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
				    String author = (authorsArray != null && authorsArray.size() > 0) ? authorsArray.get(0).getAsString() : "Unknown Author";

				    JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
				    String coverUrl = (imageLinks != null) ? imageLinks.get("thumbnail").getAsString() : "No Image";

				    Book book = new Book(title, author, coverUrl);
				    bookData.add(book);
				}
			}catch (IOException e) {
				e.printStackTrace();	
			}
			
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookData;
	}
}
