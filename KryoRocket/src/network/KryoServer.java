package network;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import dto.DataTransferObjects;
import dto.GameState;
import dto.JoinMessage;
import dto.KeyPressMessage;
import gameEngine.IGameEngine;

/**
 * GameHub Implementation 
 * Listens to KryonetMessages and relays messages to the right GameEngines
 * Listens to GameEngines and relays messages to the right clients
 */
//TODO refactor - extract KryoServer and decouple Connection technology using an interface and observerpattern
public class KryoServer implements EngineListener {
	private static final int PORT = 5151;
	
	public Server server;
	//For translation of userUUID to right connection
	private Map<String, Connection> clientConnections = new ConcurrentHashMap<>(); //userUUID, Connection
	//For translation of GameState userUUID's to userNames;
	private Map<String, String> useruuids = new ConcurrentHashMap<>(); //userUUID, username
	
	private Map<String, IGameEngine> games = new ConcurrentHashMap<>();//gameUUID, gameInstance
	//To identify which gameEngine should receive a given keyPressMessage/SelectionMesage 
	private Map<String, String> activePlayers = new ConcurrentHashMap<>();//userUUID, gameUUID;
	//To identify which users (ids) should receive a given GameState
	private Map<String, String> spectators = new ConcurrentHashMap<>();//gameUUID, userUUID


	public KryoServer(){
		server = new Server(){
			protected Connection newConnection(){
				return new TurboConnection(); //On new client connected
			}
		};
		DataTransferObjects.register(server); //Set up Kryo serialization
		try {
			server.bind(PORT); //Register port
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Port probably allready used.
		}
		server.addListener(new TurboServerListener());
		server.start(); //Start server in own thread;
	}

	public class TurboConnection extends Connection{
		//Subclassing for future implementation details...
	}

	public class TurboServerListener extends Listener {


		private static final String SHA_256 = "SHA-256";

		@Override
		public void received(Connection connection, Object object) {
			super.received(connection, object);
			if (object instanceof JoinMessage){
				JoinMessage joinMessage = (JoinMessage)object;
				MessageDigest md = getSHA256();
				
			} else if (object instanceof JoinMessage){
				
			}
		}

		private MessageDigest getSHA256() {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance(SHA_256);
			} catch (NoSuchAlgorithmException e) {
				System.err.println(this.getClass() + ": Wrong algorithm:" + SHA_256);
				e.printStackTrace();
			}			
			return md;
		}

	}

	@Override
	public void receiveGameState(GameState state, String gameId) {
		//Look up spectators
		
		//Look up connections
		
		//Send to all spectators
		if (server!= null) server.sendToAllTCP(state);

	}


}
