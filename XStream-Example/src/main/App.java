package main;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import domain.Player;
import domain.Team;

public class App {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new App().runApp();
	}
	
	private void runApp(){
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("player", Player.class);
		String output = xstream.toXML(createPlayer());
		System.out.println(output);
		
		Player player = (Player) xstream.fromXML(output);
		System.out.println("Unmarshalling XML");
		System.out.println(player);
		
		XStream xstreamJson = new XStream(new JsonHierarchicalStreamDriver());
		String json = xstreamJson.toXML(player);
		System.out.println(json);
	}
	
	private Player createPlayer(){
		Team team = new Team("Boston Red Sox");
		Player player = new Player("David Ortiz", team);
		return player;
	}

}
