package dto;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class PlayerData{
	//InputData
	public volatile Set<GameKeys> keysDown = new ConcurrentSkipListSet<>();
	
	//Ship data - position
	public double xPos;
	public double yPos;
	public double xSpeed;
	public double ySpeed;
	public double xAcc;
	public double yAcc;
	public double angle;
	// stats
	public double health;
	public int lives;

	// 
	public String username = "Player"; //Playername translated from hub
	public String playerid = ""; //ID from gameEngine
	
	public PlayerData(){
		//Default constructor
	}
	
	//For testing purposes
	public PlayerData(String playerid){
		this(100, 100, 90, playerid);
		
	}
	
	public PlayerData(double xPos, double yPos, double angle, String playerid) {
		this.angle=angle;
		this.xPos = xPos;
		this.yPos = yPos;
		this.playerid = playerid;
	}

	@Override
	public String toString() {
		return "PlayerData [keysDown=" + keysDown + ", xPos=" + xPos + ", yPos=" + yPos + ", xSpeed=" + xSpeed
				+ ", ySpeed=" + ySpeed + ", xAcc=" + xAcc + ", yAcc=" + yAcc + ", angle=" + angle + ", health=" + health
				+ ", lives=" + lives + ", username=" + username + ", playerid=" + playerid + "]";
	}
	
}