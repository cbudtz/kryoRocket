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
}