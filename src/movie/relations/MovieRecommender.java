package movie.relations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import movie.imdb.utils.MovieUtils;
/**
 * This class holds all the movie attribute to movie mapping and also computes the relationship
 * @author prathik (prathik.puthran@gmail.com)
 * @return
 */
public class MovieRecommender {
	
	
	Map<String, Movie> titleToMovieMap = new HashMap<String, Movie>();
	Map<String, ArrayList<Movie>> castToMovieMap = new HashMap<String, ArrayList<Movie>>();
	Map<String, ArrayList<Movie>> directorToMovieMap = new HashMap<String, ArrayList<Movie>>();
	Map<String, ArrayList<Movie>> keywordsToMovieMap = new HashMap<String, ArrayList<Movie>>();
	Map<String, ArrayList<Movie>> filmLocationsToMovieMap = new HashMap<String, ArrayList<Movie>>();
	Map<String, ArrayList<Movie>> writerToMovieMap = new HashMap<String, ArrayList<Movie>>();
	Map<String, ArrayList<Movie>> genreToMovieMap = new HashMap<String, ArrayList<Movie>>();
	Map<String, ArrayList<Movie>> producerToMovieMap = new HashMap<String, ArrayList<Movie>>();
	public boolean indexBuild = false;
	
	private MovieRecommender() {
		
	}
	
	public static MovieRecommender getInstance() {
		return InstanceHolder.recommender;
	}
	
	private static class InstanceHolder {
		public static MovieRecommender recommender = new MovieRecommender();
	}
	
	public List<MovieRelation> getRecommendedMovies(String movieName) {
		if(movieName == null) {
			throw new IllegalArgumentException("Invalid input");
		} else {
			movieName = MovieUtils.getNormalizedString(movieName);
			Movie m = titleToMovieMap.get(movieName);
			if(m == null) {
				return new ArrayList<MovieRelation>();
			}
			ArrayList<Movie> relatedByActors = getRelatedMovies(m, MovieAttributes.ACTOR);      //5
			ArrayList<Movie> relatedByDirector = getRelatedMovies(m, MovieAttributes.DIRECTOR);  //5
			//ArrayList<Movie> relatedByCast = getRelatedMovies(m, MovieAttributes.CAST);
			ArrayList<Movie> relatedByGenre = getRelatedMovies(m, MovieAttributes.GENRE);       //4
			ArrayList<Movie> relatedByKeyword = getRelatedMovies(m, MovieAttributes.KEYWORD);   //1
			ArrayList<Movie> relatedByProducer = getRelatedMovies(m, MovieAttributes.PRODUCER);  //3
			ArrayList<Movie> relatedByWriter = getRelatedMovies(m, MovieAttributes.WRITER);   //4
			ArrayList<Movie> relatedByLoc = getRelatedMovies(m, MovieAttributes.FILMLOCATION);  //1
			
			Map<String, MovieRelation> relatedMovies = new HashMap<String, MovieRelation>();
			addToRelatedMovies(relatedMovies, relatedByActors, MovieAttributes.ACTOR, movieName);
			addToRelatedMovies(relatedMovies, relatedByDirector, MovieAttributes.DIRECTOR, movieName);
			addToRelatedMovies(relatedMovies, relatedByGenre, MovieAttributes.GENRE, movieName);
			addToRelatedMovies(relatedMovies, relatedByKeyword, MovieAttributes.KEYWORD, movieName);
			addToRelatedMovies(relatedMovies, relatedByProducer, MovieAttributes.PRODUCER, movieName);
			addToRelatedMovies(relatedMovies, relatedByWriter, MovieAttributes.WRITER, movieName);
			addToRelatedMovies(relatedMovies, relatedByLoc, MovieAttributes.FILMLOCATION, movieName);
			List<MovieRelation> relations = new ArrayList<MovieRelation>();
			for(Entry<String, MovieRelation> r : relatedMovies.entrySet()) {
				relations.add(r.getValue());
			}
			Collections.sort(relations);
			return relations;
		}
	}
	
	public void addToRelatedMovies(Map<String, MovieRelation> relatedMovies, ArrayList<Movie> movies, MovieAttributes attribute, String movieName) {
		int i=0;
		int size = movies.size();
		while(i<size) {
			Movie curMovie = movies.get(i);
			String curTitle = MovieUtils.getNormalizedString(curMovie.title);
			if(curTitle.equals(movieName)) {
				i++;
				continue;
			}
			MovieRelation rel = relatedMovies.get(curMovie.uri);
			if(rel == null) {
				rel = new MovieRelation(curMovie);
			}
			updateCount(rel, attribute);
			i++;
			relatedMovies.put(curMovie.uri, rel);
		}
	}
	
	public void updateCount(MovieRelation relation, MovieAttributes attribute) {
		if(MovieAttributes.ACTOR.equals(attribute)) {
			relation.commonActorCount++;
		} else if(MovieAttributes.DIRECTOR.equals(attribute)) {
			relation.commonDirCount++;
		} else if(MovieAttributes.KEYWORD.equals(attribute)) {
			relation.commonKeyCount++;
		} else if(MovieAttributes.GENRE.equals(attribute)) {
			relation.commonGenreCount++;
		} else if(MovieAttributes.PRODUCER.equals(attribute)) {
			relation.commonProdCount++;
		} else if(MovieAttributes.WRITER.equals(attribute)) {
			relation.commonWriterCount++;
		} else if(MovieAttributes.FILMLOCATION.equals(attribute)) {
			relation.commonLocationCount++;
		}
	}
	
	public ArrayList<Movie> getRelatedMovies(Movie m, MovieAttributes attribute) {
		ArrayList<Movie> result = new ArrayList<Movie>();
		if(MovieAttributes.ACTOR.equals(attribute)) {
			List<String> actors = m.actors;
			for(String a : actors) {
				a = MovieUtils.getNormalizedString(a);
				result.addAll(castToMovieMap.get(a));
			}
		} else if(MovieAttributes.DIRECTOR.equals(attribute)) {
			List<String> dirs = m.directors;
			for(String d : dirs) {
				d = MovieUtils.getNormalizedString(d);
				result.addAll(directorToMovieMap.get(d));
			}
		} else if(MovieAttributes.KEYWORD.equals(attribute)) {
			List<String> keys = m.keywords;
			for(String k : keys) {
				k = MovieUtils.getNormalizedString(k);
				result.addAll(keywordsToMovieMap.get(k));
			}
		} else if(MovieAttributes.FILMLOCATION.equals(attribute)) {
			List<String> locs = m.filmLocations;
			for(String loc : locs) {
				loc = MovieUtils.getNormalizedString(loc);
				result.addAll(filmLocationsToMovieMap.get(loc));
			}
		} else if(MovieAttributes.GENRE.equals(attribute)) {
			List<String> gns = m.genres;
			for(String g : gns) {
				g = MovieUtils.getNormalizedString(g);
				result.addAll(genreToMovieMap.get(g));
			}
		} else if(MovieAttributes.PRODUCER.equals(attribute)) {
			List<String> prods = m.producer;
			for(String p : prods) {
				p = MovieUtils.getNormalizedString(p);
				result.addAll(producerToMovieMap.get(p));
			}
		} else if(MovieAttributes.WRITER.equals(attribute)) {
			List<String> wrts = m.writers;
			for(String w : wrts) {
				w = MovieUtils.getNormalizedString(w);
				result.addAll(writerToMovieMap.get(w));
			}
		}
		//Collections.sort(result);
		return result;
	}
	
	public void buildMovieIndex(Movie[] movies) {
		if(movies == null) {
			throw new IllegalArgumentException("Invalid input. movies [null]");
		} else {
			for(Movie m : movies) {
				String tit = MovieUtils.getNormalizedString(m.title);
				titleToMovieMap.put(tit, m);
				addCastToMap(m);
				addDirectorToMovieMap(m);
				addToKeywordMap(m);
				addFilmLocMap(m);
				addGenreMap(m);
				addProducerToMap(m);
				addWriterToMap(m);
			}
			indexBuild = true;
			//sortCollections();
		}
	}
	
	public void sortCollections() {
		for(ArrayList<Movie> movies : castToMovieMap.values()) {
			Collections.sort(movies);
		}
		for(ArrayList<Movie> movies: directorToMovieMap.values()) {
			Collections.sort(movies);
		}
		for(ArrayList<Movie> movies : keywordsToMovieMap.values()) {
			Collections.sort(movies);
		}
		for(ArrayList<Movie> movies: filmLocationsToMovieMap.values()) {
			Collections.sort(movies);
		}
		for(ArrayList<Movie> movies : writerToMovieMap.values()) {
			Collections.sort(movies);
		}
		for(ArrayList<Movie> movies : genreToMovieMap.values()) {
			Collections.sort(movies);
		}
		for(ArrayList<Movie> movies : producerToMovieMap.values()) {
			Collections.sort(movies);
		}
	}
	
	public void addCastToMap(Movie m) {
		for(String actor : m.actors) {
			String act = MovieUtils.getNormalizedString(actor);
			if(castToMovieMap.containsKey(act)) {
				ArrayList<Movie> res = castToMovieMap.get(act);
				if(!res.contains(m))
					res.add(m);
				castToMovieMap.put(act, res);
			} else {
				ArrayList<Movie> res = new ArrayList<Movie>();
				res.add(m);
				castToMovieMap.put(act, res);
			}
		}
	}
	
	public void addDirectorToMovieMap(Movie m) {
		if(m != null) {
			for(String dir : m.directors) {
				dir = MovieUtils.getNormalizedString(dir);
				ArrayList<Movie> mov = directorToMovieMap.get(dir);
				if(mov == null) {
					mov = new ArrayList<Movie>();
				}
				if(!mov.contains(m)) {
					mov.add(m);
				}
				directorToMovieMap.put(dir, mov);
			}
		}
	}
	
	public void addToKeywordMap(Movie m) {
		if(m.keywords != null) {
			for(String keyword : m.keywords) {
				keyword = MovieUtils.getNormalizedString(keyword);
				ArrayList<Movie> mov = keywordsToMovieMap.get(keyword);
				if(mov == null) {
					mov = new ArrayList<Movie>();
					mov.add(m);
					keywordsToMovieMap.put(keyword, mov);
				} else {
					if(!mov.contains(m)) {
						mov.add(m);
					}
					keywordsToMovieMap.put(keyword, mov);
				}
			}
		}
	}
	
	public void addFilmLocMap(Movie m) {
		if(m.filmLocations != null) {
			for(String loc : m.filmLocations) {
				loc = MovieUtils.getNormalizedString(loc);
				ArrayList<Movie> mov = filmLocationsToMovieMap.get(loc);
				if(mov == null) {
					mov = new ArrayList<Movie>();					
				}
				if(!mov.contains(m)) 
					mov.add(m);
				filmLocationsToMovieMap.put(loc, mov);
			}
		}
	}
	
	public void addGenreMap(Movie m) {
		if(m.genres != null) {
			for(String gen : m.genres) {
				gen = MovieUtils.getNormalizedString(gen);
				ArrayList<Movie> mov = genreToMovieMap.get(gen);
				if(mov == null) {
					mov = new ArrayList<Movie>();
				}
				if(!mov.contains(m)) {
					mov.add(m);
				}
				genreToMovieMap.put(gen, mov);
			}
		}
	}
	
	public void addProducerToMap(Movie m) {
		if(m.producer != null) {
			for(String prod : m.producer) {
				prod = MovieUtils.getNormalizedString(prod);
				ArrayList<Movie> mov = producerToMovieMap.get(prod);
				if(mov == null) {
					mov = new ArrayList<Movie>();
				}
				if(!mov.contains(m)) {
					mov.add(m);
				}
				producerToMovieMap.put(prod, mov);
			}
		}
	}
	
	public void addWriterToMap(Movie m ) {
		if(m.writers != null) {
			for(String w : m.writers) {
				w = MovieUtils.getNormalizedString(w);
				ArrayList<Movie> mov = writerToMovieMap.get(w);
				if(mov == null) {
					mov = new ArrayList<Movie>();
				}
				if(!mov.contains(m)) {
					mov.add(m);
				}
				writerToMovieMap.put(w, mov);
			}
		}
	}
	
	public static class MovieRelation implements Comparable<MovieRelation>{
		public Movie m;
		//public MovieAttributes relatedBy;
		public int relationWeight;
		public int commonActorCount = 0;
		public int commonDirCount = 0;
		public int commonGenreCount = 0;
		public int commonKeyCount = 0;
		public int commonProdCount = 0;
		public int commonWriterCount = 0;
		public int commonLocationCount = 0;
		public MovieRelation(Movie m) {
			this.m  = m;
		}
		@Override
		public int compareTo(MovieRelation o) {
			int tW = getWeight();
			int oW = o.getWeight();
			if(tW == oW) {
				if(this.m.rating.equals(o.m.rating)) {
					return 0;
				} else if(this.m.rating < o.m.rating) {
					return 1;
				} else {
					return -1;
				}
			}
			return oW - tW;
		}
		
		public int getWeight() {
			return commonActorCount*40 + commonDirCount*50 + commonGenreCount*4 + commonKeyCount*1 + commonProdCount*2 + commonWriterCount*10 + commonLocationCount*1; 
		}
		@Override
		public String toString() {
			return "MovieRelation [m=" + m.title + ", commonActorCount="
					+ commonActorCount + ", commonDirCount=" + commonDirCount
					+ ", commonGenreCount=" + commonGenreCount
					+ ", commonKeyCount=" + commonKeyCount
					+ ", commonProdCount=" + commonProdCount
					+ ", commonWriterCount=" + commonWriterCount
					+ ", commonLocationCount=" + commonLocationCount + "]"; 
		}
		
		
	}
}
