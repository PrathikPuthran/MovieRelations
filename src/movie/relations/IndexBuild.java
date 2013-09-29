package movie.relations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

/**
	This class is handler for buildindex request.
	@author prathik (prathik.puthran@gmail.com)
 */
public class IndexBuild extends ServerResource {
	@Get("json")
	public String buildIndex() {
		try {
			MovieRecommender reco = MovieRecommender.getInstance();
			if(reco.indexBuild) {
				return "SUCCESS";
			}
			String file = "movies_latest.json";
			Gson gson = new Gson();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			Movie[] movies = gson.fromJson(reader, Movie[].class);
			reco.buildMovieIndex(movies);
			return "Success";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}
	}
}
