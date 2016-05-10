package network;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import dto.CreateGameMessage;
import dto.CreateGameResponse;
import dto.DataTransferObjects;
import dto.GameSettings;
import dto.GameState;
import dto.GetPublicGamesMessage;
import dto.GetPublicGamesResponse;
import dto.JoinGameMessage;
import dto.JoinGameResponse;
import dto.JoinMessage;
import dto.JoinResponse;
import dto.KeyPressMessage;
import dto.PlayerData;
import gameEngine.GameEngineDummyImpl;
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
	private Map<String, Connection> clientConnections = new ConcurrentHashMap<>(); //userHash, Connection
	//For translation of GameState userUUID's to userNames;
	private Map<String, String> userHashes = new ConcurrentHashMap<>(); //userHash, username
	
	private Map<String, IGameEngine> games = new ConcurrentHashMap<>();//gameUUID, gameInstance
	//To identify which gameEngine should receive a given keyPressMessage/SelectionMesage 
	private Map<String, String> activePlayers = new ConcurrentHashMap<>();//userUUID, gameUUID;
	//To identify which users (ids) should receive a given GameState
	private Map<String, Set<String>> spectators = new ConcurrentHashMap<>();//gameUUID, userUUID

//OuterClass - just starting server and handling callbacks from GameEngines.
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
		server.addListener(new TurboServerListener(this));
		server.start(); //Start server in own thread;
	}

	@Override
	public void receiveGameState(GameState state, String gameId) {
		
		//Parse GameState - Hide IDs for Users
		ConcurrentHashMap<String, PlayerData> players = state.players;
		for (PlayerData player : players.values()) {
			String userId = player.playerid;
			String playerName = userHashes.get(userId);
			player.username=playerName;
			player.playerid="";
		}		
		//Look up spectators
		Set<String> gameSpectators = spectators.get(gameId);
		
		//Look up connections
		for (String spectatorID : gameSpectators) {
			Connection spectatorConnection = clientConnections.get(spectatorID);
			spectatorConnection.sendTCP(state);
		}
		

	}
	
	public class TurboConnection extends Connection{
		//Subclassing for future implementation details...
	}
	//InnerClass handling most control
	public class TurboServerListener extends Listener {
		
		EngineListener engineListener = null;
		private static final String SHA_256 = "SHA-256";

		public TurboServerListener(KryoServer kryoServer) {
			this.engineListener =kryoServer;
		}

		@Override
		public void received(Connection connection, Object object) {
			super.received(connection, object);
			if (object instanceof JoinMessage){ 
			//User joining network
				JoinMessage joinMessage = (JoinMessage)object;
				String digest = digest(joinMessage.name, joinMessage.pass);
				clientConnections.put(digest, connection); //Keep track of connections
				userHashes.put(digest, joinMessage.name); //Keep track of userHashes
				connection.sendTCP(new JoinResponse(digest)); //Tell user his hash...
				System.out.println(this.getClass() + ": Player Joined Network: " + joinMessage.name + ", Hash: " + digest); //Debug info
			} else if (object instanceof CreateGameMessage){
			//User creating game
				CreateGameMessage createGameMessage = (CreateGameMessage)object;
				//generate random uuid for game
				String gameUuid = UUID.randomUUID().toString();
				String userHash = createGameMessage.settings.gameOwner;
				//Instantiate new gameEngine
				GameEngineDummyImpl newGame = new GameEngineDummyImpl(); //TODO change to real implementation
				//Start engine
				GameState initialGamestate = newGame.initializegame(new GameSettings(userHash), gameUuid, engineListener); //Setup game and start it!
				//Keep track of it
				games.put(gameUuid, newGame);
				//Keep track of active players
				activePlayers.put(userHash, gameUuid);
				//Keep track of spectators
				spectators.put(gameUuid, new ConcurrentSkipListSet<String>());
				spectators.get(gameUuid).add(userHash);
				//send id to owner.
				connection.sendTCP(new CreateGameResponse(gameUuid, initialGamestate));
				System.out.println(this.getClass()+": User creating game: " + gameUuid);
			} else if (object instanceof GetPublicGamesMessage){
				//Convert hasmap to set
				ConcurrentSkipListSet<String> returnGameIds = new ConcurrentSkipListSet<String>();				
				returnGameIds.addAll(games.keySet());
				//return list of active games
				connection.sendTCP(new GetPublicGamesResponse(returnGameIds));
				System.out.println(this.getClass() + ": user requesting all games: " + games.keySet());
			} else if (object instanceof JoinGameMessage){
				JoinGameMessage joinGameMessage = (JoinGameMessage) object;
				String gameID = joinGameMessage.gameuuid;
				String playerID = joinGameMessage.playeruuid;
				IGameEngine game = games.get(gameID);
				if (game == null) {connection.sendTCP(new JoinGameResponse("fail"));}
				else {
					//Tell gameEngine to add player
					game.joinGame(playerID);
					//Show him the works
					spectators.get(gameID).add(playerID);
					//Let him send gameKeys
					activePlayers.put(playerID, gameID);
					//Tell him everything went well (lie?)
					connection.sendTCP(new JoinGameResponse("Ok"));
					System.out.println("User" + playerID +" Joined Game:" + gameID);
				}
			} else if (object instanceof KeyPressMessage){
				KeyPressMessage keyPress = (KeyPressMessage) object;
				String playerID = keyPress.userHash;
				String gameID = activePlayers.get(playerID);
				IGameEngine game = games.get(gameID);
				game.onKeyPressMes(keyPress);
				
			} else {
				System.out.println(this.getClass() + ": dto not implemented!");
			}
		}
		
		private String digest(String user, String pass){
			MessageDigest md = getSHA256();
			String keystring = user + pass;
			byte[] keyBytes = keystring.getBytes();
			byte[] digest = md.digest(keyBytes);
			return new String(digest);
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




}
