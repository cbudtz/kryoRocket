package dto;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {
	public ConcurrentHashMap<String,PlayerData> players = new ConcurrentHashMap<>(); //UUID, playerData
	
	@Override
	public String toString(){
		
		return players.toString();
		
	}

}
