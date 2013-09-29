package movie.relations.application;

import movie.relations.IndexBuild;
import movie.relations.RelationHandler;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
/**
 * @author prathik (prathik.puthran@gmail.com)
 */
public class MainApplication extends Application {
	public MainApplication() {
		
	}
	
    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/relations", RelationHandler.class);
        router.attach("/buildindex", IndexBuild.class);
       // router.attach("/getPage", Page.class);
        return router;
    }

}
