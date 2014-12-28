package service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class JsonResource extends ServerResource{
	public JsonResource(){}
	@Get
	public Representation toJson(){
		JSONObject json = new JSONObject();
		JSONArray jr = new JSONArray(Adages.getList());
		try {
			json.put("adages", jr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonRepresentation(json);
	}
}
