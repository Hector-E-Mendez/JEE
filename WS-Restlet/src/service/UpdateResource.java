package service;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import domain.Adage;

public class UpdateResource extends ServerResource{
	public UpdateResource(){}
	@Post
	public Representation update(Representation data){
		String msg = null;
		Status status = null;
		String adageId = null;
		int id = 0;
		
		Form form = new Form(data);
		adageId = form.getFirstValue("id");
		String words = form.getFirstValue("words");
		
		if (adageId == null){
			msg = "No id was given for the adage.\n";
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		}
		else{
			id = Integer.parseInt(adageId);
		}
		
		if (words == null){
			msg = "No words were given for the adage.\n";
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		}
		else{
			Adage adage = Adages.find(id);
			String oldWords = adage.getWords();
			adage.setWords(words);
			msg = "The adage " + oldWords + " has been updated.\n to " + words + "\n";
			status = Status.SUCCESS_OK;
		}
		
		setStatus(status);
		return new StringRepresentation(msg, MediaType.TEXT_PLAIN);
	}
}
