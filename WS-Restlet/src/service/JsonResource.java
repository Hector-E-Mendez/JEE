package service;

import org.json.JSONArray;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class JsonResource extends ServerResource{
	public JsonResource(){}
	@Get
	public Representation toJson(){
		JSONArray json = new JSONArray(Adages.getList());
		return new JsonRepresentation(json);
	}
}
