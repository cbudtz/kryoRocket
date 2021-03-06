package network;

import java.util.concurrent.ConcurrentHashMap;

import dto.CreateGameMessage;
import dto.JoinGameMessage;
import dto.JoinMessage;
import dto.KeyPressMessage;

public interface InputListener {
	//Pregame
	void receiveJoinMessage(JoinMessage joinMessage); //Join Network 
	void receiveCreateGameMessage(CreateGameMessage createGameMessage);//Create new game
	void receiveJoinGameMessage(JoinGameMessage joinGameMessage); //Join Game
	//Ingame
	void receiveKeyPress(KeyPressMessage keyMessage); //In game real-time keys
	void receivePlayerSelection(ConcurrentHashMap<String, String> selections); //Make in game selection
	
	
	

}
