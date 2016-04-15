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

	// 
	public String name = "Player";
	
	public PlayerData(String name){
		this(100, 100, 90, name);
		
	}
	
	public PlayerData(double xPos, double yPos, double angle, String name) {
		this.angle=angle;
		this.xPos = xPos;
		this.yPos = yPos;
	}
}