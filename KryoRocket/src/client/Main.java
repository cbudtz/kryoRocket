package client;

import network.KryoClient;
import view.IGUI;
import view.impl.GUI;

public class Main {
	public static void main(String[] args) {
		//Initialize GUI and NetworkClient
		IGUI gui = new GUI();
		KryoClient kryoClient = new KryoClient();
		//Glue them together
		kryoClient.listener = gui; //TODO some method instead
		kryoClient.run();
		
		gui.registerKeyPressListener(kryoClient);
	}
}
