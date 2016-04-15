package network;

import dto.GameState;
import dto.JoinResponse;

public interface EngineListener {
	void receiveGameState(GameState state);

	void receiveJoinResponse(JoinResponse object);

}
