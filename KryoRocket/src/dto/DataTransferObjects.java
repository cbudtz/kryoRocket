package dto;

import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;


public class DataTransferObjects {

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(String.class);
		kryo.register(GameState.class);
		kryo.register(ArrayList.class);
		kryo.register(PlayerData.class);
		kryo.register(KeyPressMessage.class);
		kryo.register(Keys.class);
				
		// TODO Auto-generated method stub
		
	}

	public static final int TCP_PORT = 5151;

}
