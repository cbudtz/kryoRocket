package server;

import gameEngine.GameEngineDummyImpl;
import gameEngine.IGameEngine;
import network.KryoServer;

public class Main {

	public static void main(String[] args) {
		
		KryoServer kryoServer= new KryoServer();
		IGameEngine gameEngine = new GameEngineDummyImpl();
		gameEngine.registerGameStateListener(kryoServer);
//		kryoServer.gameEngine = gameEngine;
		//Thread engineThread = new Thread(gameEngine);
//		engineThread.start();
		
	}
}
