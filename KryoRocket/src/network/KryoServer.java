package network;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import dto.DataTransferObjects;
import dto.GameState;
import dto.KeyPressMessage;
import dto.PlayerData;
import dto.GameKeys;

public class KryoServer {
	//TODO refactor - move to gameEngineImplementation
	//private volatile Set<GameKeys> keysPressed = new HashSet<>();
	private GameState gameState = new GameState();


	Server server;
	public KryoServer(){
		server = new Server(){
			protected Connection newConnection(){
				return new TurboConnection();
			}
		};
		DataTransferObjects.register(server);
		try {
			server.bind(5151);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.addListener(new TurboServerListener());
		server.start();
	}

	public class TurboConnection extends Connection{

	}

	public static void main(String[] args) {
		KryoServer k = new KryoServer();
		k.gameState.players.put("1", new PlayerData(10,10,0,"Brian"));
		k.gameState.players.put("2", new PlayerData(20,20,0,"Børge"));
		k.gameState.players.put("3", new PlayerData(30,20,0,"Benny"));
		k.gameState.players.put("4", new PlayerData(40,20,0,"Bongo"));
		while (true){
			for (PlayerData p : k.gameState.players.values()){
				
				for (GameKeys key : p.keysPressed) {
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
			
			
			k.server.sendToAllTCP(k.gameState);
		//	System.out.println("KryoServer: sent gamestate!");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class TurboServerListener extends Listener {


		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof KeyPressMessage){
				KeyPressMessage keyMsg = (KeyPressMessage) object;
				System.out.println();
				System.out.println("KryoServer: Received:" + keyMsg.keysDown);
				gameState.players.get(keyMsg.ShipUUID).keysPressed.clear();
				for (GameKeys gameKey : keyMsg.keysDown) {
					gameState.players.get(keyMsg.ShipUUID).keysPressed.add(gameKey);
				}
			}

			super.received(connection, object);
		}

	}
}
