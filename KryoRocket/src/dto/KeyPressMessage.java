package dto;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class KeyPressMessage {
	public String userHash;
	public Set<GameKeys> keysDown = new ConcurrentSkipListSet<>();
	public GameKeys released;
	public GameKeys pressed;
	

}
