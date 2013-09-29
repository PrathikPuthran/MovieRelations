package movie.relations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Class representing movie
 * @author prathik (prathik.puthran@gmail.com)
 */
public class Movie implements Comparable<Movie>{
	public String title;
	public List<String> actors = new ArrayList<String>();
	public List<String> directors = new ArrayList<String>();
	public List<String> writers = new ArrayList<String>();
	public List<String> genres = new ArrayList<String>();
	public Integer releaseYear;
	public Double rating;
	public List<String> filmLocations = new ArrayList<String>();
	public List<String> keywords = new ArrayList<String>();
	public List<String> producer = new ArrayList<String>();
	public String uri;
	public int movieWeight;
	public Movie() {
		
	}
	@Override
 	public int compareTo(Movie o) {
		if(o == null) {
			return 1;
		} else if(this.title == null && o.title == null) {
			return 0;
		} else if(this.title == null) {
			return 1;
		} else {
			return this.title.compareTo(o.title);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Movie [title=" + title + /*", actors=" + actors + ", directors="
				+ directors + ", writers=" + writers + ", genres=" + genres
				+ ", releaseYear=" + releaseYear + ", rating=" + rating
				+ ", filmLocations=" + filmLocations + ", keywords=" + keywords
				+ ", producer=" + producer + ", uri=" + uri + */"]";
	}
	
	
	
}
