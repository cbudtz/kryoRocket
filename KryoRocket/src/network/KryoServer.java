package network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import dto.DataTransferObjects;
import dto.GameState;
import dto.JoinMessage;
import dto.JoinResponse;
import dto.KeyPressMessage;
import gameEngine.IGameEngine;

public class KryoServer implements EngineListener {
	//TODO refactor - move to gameEngineImplementation
	public Server server;
	public IGameEngine gameEngine;


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
		//Subclassing for future implementation details...
	}

	public class TurboServerListener extends Listener {


		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof KeyPressMessage){
				KeyPressMessage keyMsg = (KeyPressMessage) object;
				System.out.println();
				System.out.println("KryoServer: Received:" + keyMsg.keysDown);
				if (gameEngine!=null) gameEngine.onKeyPressMes(keyMsg);
			} else if (object instanceof JoinMessage){
				if (gameEngine!=null){
					JoinMessage message = (JoinMessage) object;
					String playerID = gameEngine.joinGame(message.name);
					connection.sendTCP(new JoinResponse(playerID));
				} else {System.out.println(this.getClass() + ": GameEngine not initialized!");
				}

				super.received(connection, object);
			}
		}
	}

	@Override
	public void receiveGameState(GameState state) {
		if (server!= null) server.sendToAllTCP(state);

	}

	@Override
	public void receiveJoinResponse(JoinResponse JoinResponse) {
		if (server!=null) server.sendToAllTCP(JoinResponse);

	}
}
