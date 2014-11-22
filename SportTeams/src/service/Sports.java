package service;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import domain.Player;
import domain.Team;

@Path("/")
public class Sports{
	@Context
	private ServletContext ctx;
	
	@GET
	@Path("team")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTeam(@QueryParam("id") Long id){
		String json = "no json";
		SessionFactory sessionFactory = (SessionFactory) ctx.getAttribute("hibFactory");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query queryResults = session.getNamedQuery("team.findById");
		queryResults.setLong("id", id);
		Team team = (Team) queryResults.uniqueResult();
		session.getTransaction().commit();
		
		try{
			json = new ObjectMapper().writeValueAsString(team);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return json;
	}
	
	@GET
	@Path("player")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPalyer(@QueryParam("id") Long id){
		String json = "no json";
		SessionFactory sessionFactory = (SessionFactory) ctx.getAttribute("hibFactory");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query queryResults = session.getNamedQuery("player.findById");
		queryResults.setLong("id", id);
		Player player = (Player) queryResults.uniqueResult();
		session.getTransaction().commit();
		
		try{
			json = new ObjectMapper().writeValueAsString(player);
		}
		catch (Exception e){
			System.out.println("Exception : " + e.getMessage());
		}
		
		return json;
	}
}
