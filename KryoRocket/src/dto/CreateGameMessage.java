package dto;

public class CreateGameMessage {
	public GameSettings settings;
	
	public CreateGameMessage() {
	}
	public CreateGameMessage(GameSettings settings){
		this.settings= settings;
	}
	public CreateGameMessage(String userhash){
		settings= new GameSettings(userhash);
	}

}
