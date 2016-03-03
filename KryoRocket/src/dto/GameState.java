package dto;
import java.util.ArrayList;

public class GameState {
	public ArrayList<playerData> players = new ArrayList<>(); 


	public class playerData{
		public playerData(double xPos, double yPos, double angle, String name) {
			this.angle=angle;
			this.xPos = xPos;
			this.yPos = yPos;
		}

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
	}

}
