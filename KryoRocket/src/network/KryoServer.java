package network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import dto.DataTransferObjects;
import dto.GameState;
import dto.PlayerData;

public class KryoServer {

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
		server.start();
	}

	public class TurboConnection extends Connection{

	}

	public static void main(String[] args) {
		KryoServer k = new KryoServer();
		int i = 1;
		while (true){
			i++;
			GameState g = new GameState();
			g.players.add(new PlayerData(10, i, 0, "Brian"));
			k.server.sendToAllTCP(g);
			System.out.println("KryoServer: sent gamestate!");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i>600)i=0;
		}
	}
}
