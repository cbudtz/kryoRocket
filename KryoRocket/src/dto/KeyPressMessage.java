package dto;

import java.util.ArrayList;

public class KeyPressMessage {
	public String ShipUUID;
	public ArrayList<GameKeys> keysDown = new ArrayList<>();
	public GameKeys released;
	public GameKeys pressed;
	

}
