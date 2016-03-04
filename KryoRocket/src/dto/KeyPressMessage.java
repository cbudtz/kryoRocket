package dto;

import java.util.ArrayList;

public class KeyPressMessage {
	public long ShipUUID;
	public ArrayList<Keys> keysDown = new ArrayList<>();
	public Keys released;
	public Keys pressed;
	

}
