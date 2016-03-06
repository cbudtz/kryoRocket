package view;
import dto.GameState;
import network.GameStateListener;
import network.KeyPressListener;

public interface IGUI extends GameStateListener {
	void drawGameState(GameState state);
	void registerKeyPressListener(KeyPressListener listener);
}
