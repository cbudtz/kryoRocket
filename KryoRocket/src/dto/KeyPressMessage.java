package dto;

import java.util.ArrayList;

public class KeyPressMessage {
	public long ShipUUID;
	public ArrayList<GameKeys> keysDown = new ArrayList<>();
	public GameKeys released;
	public GameKeys pressed;
	

}
