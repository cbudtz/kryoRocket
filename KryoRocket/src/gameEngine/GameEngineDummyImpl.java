package gameEngine;

import java.util.Map;

import dto.GameKeys;
import dto.GameSettings;
import dto.GameState;
import dto.KeyPressMessage;
import dto.PlayerData;
import network.EngineListener;

public class GameEngineDummyImpl implements IGameEngine, Runnable {
	GameState gameState = new GameState();
	private EngineListener engineListener;
	private String gameId;


	@Override
	public void onKeyPressMes(KeyPressMessage keyMsg) {
		PlayerData player = gameState.players.get(keyMsg.userHash);
		if (player!=null){
		gameState.players.get(keyMsg.userHash).keysDown.clear();
		gameState.players.get(keyMsg.userHash).keysDown.addAll(keyMsg.keysDown);
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
				engineListener.receiveGameState(gameState, gameId);
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

	@Override
	public GameState initializegame(GameSettings settings,String gameId, EngineListener engineListener) {
		this.gameId=gameId;
		//Put first player somewhere...
		this.gameState.players.put(settings.gameOwner, new PlayerData(settings.gameOwner));
		//Dirty, dirty
		new Thread(this).start();
		//Should have some setup of gameState
		return gameState;
	}

	@Override
	public void joinGame(String playeruuid) {
		System.out.println(this.getClass() + "player joined:" + playeruuid);
		gameState.players.put(playeruuid, new PlayerData(playeruuid));
	}

	@Override
	public void onPlayerSelection(Map<String, String> selections) {
		// TODO Auto-generated method stub
		
	}

}
