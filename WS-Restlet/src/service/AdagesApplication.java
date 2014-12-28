package service;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.routing.Router;

import domain.Adage;

public class AdagesApplication extends Application{

	public synchronized Restlet createInboundRoot(){
		Restlet janitor = new Restlet(getContext()){
			public void handle(Request request, Response response){
				String msg = null;
				String sid = (String) request.getAttributes().get("id");
				if (sid == null){
					msg = badRequest("No ID given.\n");
				}
				Integer id = null;
				try{
					id = Integer.parseInt(sid.trim());
				}
				catch(Exception e){
					msg = badRequest("Ill-formed ID.\n");
				}
				Adage adage = Adages.find(id);
				if (adage == null){
					msg = badRequest("No adage with ID " + id + "\n");
				}
				else{
					Adages.getList().remove(adage);
					msg = "Adage " + id + " removed.\n";
				}
				response.setEntity(msg, MediaType.TEXT_PLAIN);
			}
		};
		
		Router router = new Router(getContext());
		router.attach("/", PlainResource.class);
		router.attach("/xml", XmlAllResource.class);
		router.attach("/json", JsonResource.class);
		router.attach("/create", CreateResource.class);
		router.attach("/delete/{id}", janitor);
		return router;
	}
	
	private String badRequest(String msg){
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return error.toString();
	}
}
