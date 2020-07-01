package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.Client;

public class FileLoader {

	public ArrayList<Client> loadClientsFromFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./res/" + fileName));
			String line = reader.readLine();
			ArrayList<Client> loadedClients = new ArrayList<Client>();
			while(line!=null) {
				loadedClients.add(parseDataFile(line));
				line = reader.readLine();
			}
			
			return loadedClients;
		} catch (Exception e) {
			System.out.println("\nError: could not load clients from database file");
			return null;
		}
	}
	
	private Client parseDataFile(String data) {
		String[] parsedData = data.split(" ");
		return new Client(parsedData[0],parsedData[1],Double.parseDouble(parsedData[2]));
	}
	
	public void writeToFile(ArrayList<Client> clients, String fileName, boolean writeOver) {
		File writeFile = new File("./res/" + fileName);
		try {
			PrintWriter output = new PrintWriter(writeFile);
			if(writeOver) {
				for(int i = 0; i < clients.size(); i++) {
					Client c = clients.get(i);
					output.println(c.getFirstName() + " " + c.getLastName() + " " + c.getBalance());
				}
				output.close();
				System.out.println("\n Done writing to save file");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: could not write to file");
		}
	}
}
