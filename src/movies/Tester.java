package movies;

import movie.relations.application.MainApplication;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

public class Tester extends ServerResource {  
  
    public static void main(String[] args) throws Exception {  
    	Component component = new Component();
    	component.getServers().add(Protocol.HTTP, 8182);
    	//component.getServers().add(Protocol.FILE);
    	component.getDefaultHost().attach("/app", new MainApplication());
    	component.start();
    }  
}  