package service;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class PlainResource extends ServerResource{
	public PlainResource(){}
	@Get
	public Representation toText(){
		return new StringRepresentation(Adages.toPlain(), MediaType.TEXT_PLAIN);
	}
}
