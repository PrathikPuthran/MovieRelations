package movie.imdb.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import movie.relations.Movie;

import com.google.gson.Gson;
/**
 * This class drives the scraping the movie details from IMDB
 * It picks the links of alltimegross movies from the html file stored locally
 * @author prathik (prathik.puthran@gmail.com)
 * @return
 */
public class MoviePageScraper {
	public static void main(String[] args) throws IOException {
		Map<String, String> movieToUriMap = ScraperUtils.getMovieToDetailPageMap(new File("htmlpage/hits.html"));
		Movie[] movies = new Movie[movieToUriMap.size()];
		int i=0;
		for(Entry<String, String> entry : movieToUriMap.entrySet()) {
			System.out.println((i+1) + " Processing movie: " + entry.getKey() + " BaseUri: " + entry.getValue());
			movies[i++] = ScraperUtils.getMovieDetails(entry.getKey(), entry.getValue());
		}
		Gson gson = new Gson();
		String json = gson.toJson(movies);
		PrintWriter optStream = new PrintWriter(new FileOutputStream(new File("movies_latest.json")));
		optStream.write(json);
		optStream.flush();
		optStream.close(); 
/*		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader("movies_new.json"));
		Movie[] movies = gson.fromJson(reader, Movie[].class);
		for(Movie m : movies) {
			m.title = movieToUriMap.get(m.uri);
		}
		String json = gson.toJson(movies);
		PrintWriter optStream = new PrintWriter(new FileOutputStream(new File("movies_latest.json")));
		optStream.write(json);
		optStream.flush();
		optStream.close(); */ 
	}	
}
