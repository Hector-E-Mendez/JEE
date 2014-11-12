package adages;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/resourcesA")
public class RestfulAdage extends Application{

	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(Adages.class);
		return set;
	}
}
