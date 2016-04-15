package gameEngine;

import java.util.UUID;

import dto.GameKeys;
import dto.GameState;
import dto.KeyPressMessage;
import dto.PlayerData;
import network.EngineListener;

public class GameEngineDummyImpl implements IGameEngine {
	GameState gameState = new GameState();
	private EngineListener engineListener;



	@Override
	public String joinGame(String gameName) {
		String playerID = UUID.randomUUID().toString();
		gameState.players.put(playerID, new PlayerData(gameName));
		return playerID;
	}

	@Override
	public void onKeyPressMes(KeyPressMessage keyMsg) {
		PlayerData player = gameState.players.get(keyMsg.ShipUUID);
		if (player!=null){
		gameState.players.get(keyMsg.ShipUUID).keysDown.clear();
		gameState.players.get(keyMsg.ShipUUID).keysDown.addAll(keyMsg.keysDown);
		} else {
			System.out.println(this.getClass() + ": no such playerID!");
		}

	}

	@Override
	public void run() {
		while (true){
			for (PlayerData p : gameState.players.values()){

				for (GameKeys key : p.keysDown) {
					switch (key) {
					case UP:
						p.yPos--;
						break;
					case DOWN:
						p.yPos++;
						break;
					case LEFT:
						p.xPos--;
						break;
					case RIGHT:
						p.xPos++;
					case FIRE1:
					case FIRE2:
					default:
						break;
					}
				}
			}
			if (engineListener!= null){
				engineListener.receiveGameState(gameState);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void registerGameStateListener(EngineListener listener) {
		this.engineListener = listener;
		
	}

}
