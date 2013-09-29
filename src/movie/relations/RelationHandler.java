package movie.relations;

import java.util.List;

import movie.relations.MovieRecommender.MovieRelation;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
/**
 * Handler for relations request.
 * @author prathik (prathik.puthran@gmail.com)
 *
 */
public class RelationHandler extends ServerResource {

	@Get("json")
	public String getRelatedMovies() {
		try {
			String query = getQuery().getValues("q");
			if(query == null || "".equals(query)) {
				return "";
			} else {
				IndexBuild bd = new IndexBuild();
				bd.buildIndex();
				MovieRecommender recommender = MovieRecommender.getInstance();
				
				List<MovieRelation> relations = recommender.getRecommendedMovies(query);
				System.out.println(relations);
				Result[] res = new Result[relations.size()];
				int i=0;
				String result = "";
				for(MovieRelation r : relations) {
					Result r1 = new Result();
					r1.title = r.m.title;
					result += (i+1) + " " +r.m.title + "\n";
					//r1.uri = r.m.uri;
					res[i++] = r1;
				}
				Gson gson = new Gson();
				gson.toJson(res);
				return result;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "No movie";
		}
	}
	
	public static class Result {
		String title;
		//String uri;
	}
}
