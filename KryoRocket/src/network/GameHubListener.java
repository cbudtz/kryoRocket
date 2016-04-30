package network;

import dto.CreateGameResponse;
import dto.GameState;
import dto.JoinGameResponse;
import dto.JoinResponse;

public interface GameHubListener {
	void onJoinResponse(JoinResponse joinResponse);// joined network
	void onCreateGameResponse(CreateGameResponse createGameResponse);//Made new game
	void onJoinGameResponse(JoinGameResponse joinGameResponse);// //Joined Games
	void onGameStateChanged(GameState gameState);//New gamestate

}
