package dto;

public class GameSettings {

	public String gameOwner;
	
	public GameSettings(){
		
	}

	public GameSettings(String useruuid) {
		this.gameOwner = useruuid;
	}

}
