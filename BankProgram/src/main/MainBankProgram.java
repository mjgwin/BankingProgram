package main;

import data.ClientManager;
import gui.GUI;

public class MainBankProgram {

	private static ClientManager clientManager;
	private static GUI gui;
	private static boolean withGUI = true;
	
	public static void main(String[] args) {
		if(withGUI) {
			clientManager = new ClientManager(10);
			clientManager.setStorageFile("testData.txt");
			clientManager.loadClientsFromFile();
			gui = new GUI(clientManager);
		}else {
			clientManager = new ClientManager(10);
			clientManager.setStorageFile("testData.txt");
			clientManager.loadClientsFromFile();
			clientManager.processRequests();
		}
	}

}
