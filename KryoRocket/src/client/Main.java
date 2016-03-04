package client;

import network.KryoClient;
import view.IGUI;
import view.impl.GUI;

public class Main {
	public static void main(String[] args) {
		IGUI gui = new GUI();
		KryoClient kryoClient = new KryoClient();
		kryoClient.listener = gui;
		kryoClient.run();
	}
}
