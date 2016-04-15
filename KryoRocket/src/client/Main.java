package client;

import java.util.Scanner;

import network.KryoClient;
import view.IGUI;
import view.impl.GUI;

public class Main {
	public static void main(String[] args) {
		//Initialize GUI and NetworkClient
		IGUI gui = new GUI();
		System.out.println("Select playerID (1,2,3 or 4)");
		Scanner scan = new Scanner(System.in);
		String playerID = scan.next();
		((GUI) gui).playerID=playerID;
		KryoClient kryoClient = new KryoClient();
		//Glue them together
		kryoClient.listener = gui; //TODO some method instead
		kryoClient.run();
		
		gui.registerInputListener(kryoClient);
		scan.close();
		
	}
}
