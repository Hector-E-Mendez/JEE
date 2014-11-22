package main;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import service.Sports;

@ApplicationPath("/RestfulSports")
public class RestfulSports extends Application{

	public Set<Class<?>> getClasses(){
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(Sports.class);
		return set;
	}
}
