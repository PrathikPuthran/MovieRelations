package movie.relations.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import movie.relations.Movie;
import movie.relations.MovieRecommender;
import movie.relations.MovieRecommender.MovieRelation;

import com.google.gson.Gson;

public class IndexBuilder {
	public static void main(String[] args) throws FileNotFoundException {
		String file = "movies_latest.json";
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Movie[] movies = gson.fromJson(reader, Movie[].class);
		MovieRecommender reco = MovieRecommender.getInstance();
		reco.buildMovieIndex(movies);
		List<MovieRelation> related = reco.getRecommendedMovies("The Dark Knight Rises");
		//System.out.println(related.get(0));
		System.out.println(related.toString());
	}
}
