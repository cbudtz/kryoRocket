package dto;

public class CreateGameResponse {
	public String gameId;
	public GameState initialGameState;
	
	public CreateGameResponse(){
		//Default constructor
	}
	public CreateGameResponse(String uuid, GameState initialGamestate) {
		this.gameId=uuid;
		this.initialGameState = initialGamestate;
	}


}
