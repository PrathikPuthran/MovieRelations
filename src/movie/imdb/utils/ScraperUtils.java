package movie.imdb.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movie.relations.Movie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * This class has utils for scraping the movie attributes from IMDB page
 * @author prathik (prathik.puthran@gmail.com)
 * @return
 */
public class ScraperUtils {
	public static Map<String, String> getMovieToDetailPageMap(File file) throws IOException {
		Document doc = Jsoup.parse(file, "UTF-8");
		//Document doc = Jsoup.connect("http://www.imdb.com/title/tt0499549/").get();
		Element mainDiv = doc.getElementById("main");
		Elements tables = mainDiv.getElementsByTag("table");
		Element movieTable = tables.first();
		Elements movies = movieTable.select("a[href]");
		Map<String, String> movieToUrlMap = new HashMap<String, String>();
		for(Element ele : movies) {
			movieToUrlMap.put(ele.text(), ele.attr("href"));
			//movieToUrlMap.put(ele.attr("href"), ele.text());
		}
		return movieToUrlMap;
	}
	
	public static Movie getMovieDetails(String title, String baseUri) throws IOException {
		Movie m = new Movie();
		Document doc = Jsoup.connect(baseUri).get();
		m.title = title;
		m.actors = getCast(doc, title);
		m.directors = getDirectors(doc, title);
		m.writers = getWriters(doc, title);
		m.genres = getGenres(doc, title);
		String r = getReleaseYear(doc, title);
		m.releaseYear = "".equals(r) ? null : Integer.parseInt(r);
		String rat = getRating(doc, title);
		m.rating = "".equals(rat) ? null : Double.parseDouble(rat);
		m.keywords = getKeywords(baseUri, title);
		m.filmLocations = getFilmLocations(baseUri, title);
		m.producer = getProducers(doc, title);
		m.uri = baseUri;
		return m;
	}
	
	
	public static List<String> getCast(Document doc, String title) {
		if(doc == null) {
			return null;
		} else {
			List<String> cast = new ArrayList<String>();
			Element castTable = doc.select("table.cast_list").first();
			if(castTable != null) {
				Elements actorSpans = castTable.select("span.itemprop[itemprop=name]");
				if(actorSpans != null) {
					for(Element e : actorSpans) {
						cast.add(e.text());
					}
					return cast;
				}
			}
			System.out.println("Cast parsing failed for title : "+ title);
			return cast;
		}
	}

	public static List<String> getDirectors(Document doc, String title) {
		if(doc == null) {
			return null;
		} else {
			List<String> directors = new ArrayList<String>();
			Elements dirDiv = doc.select("div[itemprop=director]");
			Element div = dirDiv.first();
			Elements links = div.select("a[itemprop=url]");
			for(Element e : links) {
				Elements span = e.select("span.itemprop");
				directors.add(span.text());
			}
			return directors;
		}
	}
	
	public static List<String> getWriters(Document doc, String title) {
		if(doc == null) {
			throw new IllegalArgumentException("Invalid document");
		} else {
			List<String> writers = new ArrayList<String>();
			Elements wrtDiv = doc.select("div[itemprop=creator]");
			Element div = wrtDiv.first();
			Elements links = div.select("a[itemprop=url]");
			for(Element e : links) {
				Elements span = e.select("span.itemprop");
				writers.add(span.first().text());
			}
			return writers;
		}
	}
	
	public static List<String> getGenres(Document doc, String title) {
		if(doc == null) {
			throw new IllegalArgumentException("Invalid input");
		} else {
			List<String> genres = new ArrayList<String>();
			Elements genreDiv = doc.select("div[itemprop=genre]");
			Element div = genreDiv.first();
			Elements links = div.select("a[href]");
			for(Element link : links) {
				genres.add(link.text());
			}
			return genres;
		}
	}
	
	public static String getReleaseYear(Document doc, String title) {
		if(doc == null) {
			throw new IllegalArgumentException("Invalid input");
		} else {
			Elements header = doc.select("h1.header");
			if(header == null) {
				System.out.println("Not valid html page " + title);
				return "";
			}
			Element head = header.first();
			Element link = head.select("a[href]").first();
			if(link == null) {
				return "";
			}
			return link.text();
		}
	}
	
	public static String getRating(Document doc, String title) {
		if(doc == null) {
			throw new IllegalArgumentException("Invalid input");
		} else {
			Element rating = doc.select("span[itemprop=ratingValue]").first();
			if(rating != null) {
				return rating.text();
			} else {
				System.out.println("Rating not found for " + title);
				return "";
			}
		}
	}
	
	public static List<String> getFilmLocations(String baseUri, String title) throws IOException {
		if(baseUri == null) {
			throw new IllegalArgumentException("Invalid input");
		} else {
			String uri = baseUri + "locations";
			Document doc = Jsoup.connect(uri).get();
			List<String> result = new ArrayList<String>();
			Element location = doc.getElementById("filming_locations_content");
			Elements locations = location.select("a[itemprop=url]");
			if(locations == null) {
				System.out.println("Film locations not found for " + title);
				return result;
			}
			for(Element e : locations) {
				result.add(e.text());
			}
			return result;
		}
	}
	
	public static List<String> getKeywords(String baseUri, String title) throws IOException {
		if(baseUri == null) {
			throw new IllegalArgumentException("Invalid input");
		} else {
			String uri = baseUri + "keywords";	
			Document doc = Jsoup.connect(uri).get();
			List<String> keywords = new ArrayList<String>();
			Element resList = doc.getElementById("keywords_content");
			Elements keyData = resList.select("a[href]");
			for(Element e : keyData) {
				keywords.add(e.text());
			}
			return keywords;
		}
	} 
	
	public static List<String> getProducers(Document doc, String title) {
		if(doc == null) {
			throw new IllegalArgumentException("");
		} else {
			Elements producers = doc.select("span[itemtype=http://schema.org/Organization]");
			List<String> prodComps = new ArrayList<String>();
			for(Element e : producers) {
				Element data = e.select("span.itemprop[itemprop=name]").first();
				prodComps.add(data.text());
			}
			return prodComps;
		}
	}
	
}
