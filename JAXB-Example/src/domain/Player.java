package domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Player {

	private String name;
	private Team team;
	
	public Player(){}

	public Player(String name, Team team){
		this.name = name;
		this.team = team;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public String toString(){
		return "Player name : " + this.name + ", Player's Team : " + this.team.getName();
	}
}
