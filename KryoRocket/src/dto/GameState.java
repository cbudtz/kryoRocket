package dto;
import java.util.ArrayList;

public class GameState {
	public ArrayList<PlayerData> players = new ArrayList<>();
	
	@Override
	public String toString(){
		
		return players.toString();
		
	}

}
