package main;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import domain.Player;
import domain.Team;

public class App {
	private static final String fileName = "player.xml";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new App().runApp();
	}
	
	private void runApp(){
		try{
			JAXBContext ctx = JAXBContext.newInstance(Player.class);
			Marshaller m = ctx.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			Player player = this.createPlayer();
			
			m.marshal(player, System.out);
			
			FileOutputStream out = new FileOutputStream(fileName);
			m.marshal(player, out);
			out.close();
			
			Unmarshaller u = ctx.createUnmarshaller();
			System.out.println("Printing Object from unmarshalled XML file");
			Player playerClone = (Player) u.unmarshal(new File(fileName));
			System.out.println (playerClone.toString());
		}
		catch (JAXBException e){
			System.err.println(e);
		}
		catch (Exception e){
			System.err.println(e);
		}
	}
	
	private Player createPlayer(){
		Team team = new Team("Boston Red Sox");
		Player player = new Player("David Ortiz", team);
		return player;
	}

}
