package dto;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class GameState {
	public ConcurrentHashMap<String,PlayerData> players = new ConcurrentHashMap<>(); //UUID, playerData
	public Set<ShotData> shots = new ConcurrentSkipListSet<>();
	@Override
	public String toString() {
		return "GameState [players=" + players + ", shots=" + shots + "]";
	}
	
	

}
