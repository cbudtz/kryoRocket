import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import dto.DataTransferObjects;

public class KryoClient implements Runnable{

	

	private Client client;

	@Override
	public void run() {
		client = new Client();
		client.start();
		DataTransferObjects.register(client);
		
		client.addListener(new TurboClientListener());
		try {
			client.connect(5000, "localhost", DataTransferObjects.TCP_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private final class TurboClientListener extends Listener {
		@Override
		public void connected(Connection connection) {
			// TODO Auto-generated method stub
			super.connected(connection);
		}
		
		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof String){
				System.out.println(object);
			} else {
				System.out.println("TurboClientListener - got:" + object.getClass());
			}
		}
	}

}
