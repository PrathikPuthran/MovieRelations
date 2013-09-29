package movie.imdb.utils;
/**
 * Utils for normalizing the string
 * @author prathik (prathik.puthran@gmail.com)
 */
public class MovieUtils {
	public static String getNormalizedString(String s) {
		if(s != null) {
			s = s.toLowerCase();
			s = coalesceSpaces(s);
			s = removePunctuatations(s);
			s = truncate(s);
		}
		return s;
	}
		
	public static String truncate(String key) {
		return key.replaceAll("^\\s*|\\s*$", "");
	}
		
	public static String coalesceSpaces(String key) {
		return key.replaceAll("\\s\\s+", " ");
	}
		
	public static String removePunctuatations(String key) {
		return key.replaceAll("[^a-zA-Z0-9 ]", "");
	}
}
