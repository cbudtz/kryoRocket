package dto;

public class CreateGameMessage {
	public String userhash;
	public GameSettings settings;
	
	public CreateGameMessage() {
	
	}
	public CreateGameMessage(String userHash, GameSettings settings){
		this.userhash=userHash;
		this.settings= settings;
	}

}
