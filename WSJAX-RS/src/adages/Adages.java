package adages;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;

import org.codehaus.jackson.map.ObjectMapper;

@Path("/")
public class Adages {

	private String[] aphorisms = {"What can be shown cannot be said.",
								  "If a lion could talk, we could not understand him."};
	
	public Adages(){}
	
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	@Path("/plain")
	public String getPlain(){
		return createAdage().toString() + "\n";
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/json")
	public String getJson(){
		return this.toJson(createAdage());
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML})
	public JAXBElement<Adage> getXml(){
		return this.toXml(this.createAdage());
	}
	
	private Adage createAdage(){
		Adage adage = new Adage();
		adage.setWords(this.aphorisms[new Random().nextInt(this.aphorisms.length)]);
		return adage;
	}
	
	@XmlElementDecl(namespace = "http://aphormism.adage", name="adage")
	private JAXBElement<Adage> toXml(Adage adage){
		return new JAXBElement<Adage>(new QName("adage"), Adage.class, adage);
	}
	
	private String toJson(Adage adage){
		String json = "no json";
		try{
			json = new ObjectMapper().writeValueAsString(adage);
		}
		catch (Exception e){}
		
		return json;
	}
}
