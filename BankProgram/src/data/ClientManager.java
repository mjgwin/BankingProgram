package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import io.FileLoader;

public class ClientManager {

	private ArrayList<Client> clients;
	private int currNumClients;
	private int maxClients;
	private boolean running;
	private FileLoader loader;
	private String fileName;
	
	public ClientManager(int maxClients) {
		this.maxClients = maxClients;
	}
	
	public void loadClientsFromFile() {
		if(fileName == null) {
			throw new IllegalStateException("Error: must declare filename to load from file");
		}
		loader = new FileLoader();
		clients = loader.loadClientsFromFile(fileName);
		currNumClients = clients.size();
		System.out.println("Loaded " + currNumClients + " clients from database file");
	}
	
	public void loadEmpty() {
		clients = new ArrayList<Client>();
		currNumClients = 0;
	}
	
	public void addClient(String firstName, String lastName, double initialBalance) {
		try {
			if(currNumClients < maxClients) {
				clients.add(new Client(firstName, lastName, initialBalance)); 
				System.out.println("\nAdding client: " + firstName + " " + lastName + " with balance " + initialBalance);
				currNumClients++;
				System.out.println("\nCurrent number of clients: "  + currNumClients);
				
			}else {
				System.out.println("\nToo many clients in database!");
			}
		}catch(Exception e) {
			System.out.println("\nError creating client and adding to database");
		}
		
	}
	
	public void processRequests() {
		Scanner userInput = new Scanner(System.in);
		running = true;
		while(running) {
			System.out.println("\nClientDatabase> ");
			String input = userInput.nextLine();
			if(input.contains("/add")) {
				try {
					String[] splitInput = input.split(" ");
					addClient(splitInput[1],splitInput[2],Double.parseDouble(splitInput[3]));
				}catch(Exception e) {
					System.out.println("\nError adding client: check formatting (<Firstname> <Lastname> <Balance>)");
				}
			}else if(input.contains("/display")) {
				if(input.equals("/display sortedname")) {
					System.out.println("\nClients sorted by name: ");
					displayDatabaseAsString(sortAlphabetical());
				}else if(input.equals("/display")) {
					System.out.println("\nClients: ");
					displayDatabaseAsString(clients);
				}else {
					System.out.println("\nCommand error: bad syntax, should be </display> or </display sortedname>"); 
				}
			}else if(input.equals("/exit")) {
				System.out.println("\nExiting...");
				running = false;
				System.exit(1);
			}else if(input.contains("/getbalance")) {
				String[] splitInput = input.split(" ");
				Client c = null;
				try {
					String parseInput = new String(splitInput[1] + " " + splitInput[2]);
					if(getClientByName(parseInput)!=null) {
						c = getClientByName(parseInput);
						System.out.println("\nThe balance of: " + c.getFirstName() + " " + c.getLastName() + " is " + c.getBalance());
					}else {
						System.out.println("\nError: no client with entered name");
					}
				}catch(Exception e) {
					System.out.println("\nError getting client: (Possible bad args, use format <Firstname> <Lastname>)");
				}	
			}else if(input.contains("/delete")) {
				String[] splitInput = input.split(" ");
				try {
					String parseInput = new String(splitInput[1] + " " + splitInput[2]);
					if(getClientByName(parseInput)!=null) {
						deleteClientByName(parseInput);
					}else {
						System.out.println("\nError: no client with entered name");
					}
				}catch(Exception e) {
					System.out.println("\nError deleting client: (Possible bad args, use format <Firstname> <Lastname>)");
				}
			}else if(input.equals("/save")) {
				loader.writeToFile(clients, fileName, true);
			}else if(input.contains("/setbalance")) {
				try {
					String[] parseInput = input.split(" ");
					String clientName = new String(parseInput[1] + " " + parseInput[2]); 
					if(getClientByName(clientName)!=null) {
						editBalanceByName(clientName,Double.parseDouble(parseInput[3]));
					}else {
						System.out.println("\nError: no client with entered name");
					}
				}catch(Exception e){
					System.out.println("\nError setting balance: (Possible bad args, use format <Firstname> <Lastname> <New Balance>)");
				}
			}else {
				System.out.println("\nCommand not recognized, please try again");
			}
		}
	}
	
	public void displayDatabaseAsString(ArrayList<Client> arr) {
		for(int i = 0; i < currNumClients; i++) {
			System.out.println(arr.get(i).toString() + " " + arr.get(i).getBalance());
		}
	}
	
	public ArrayList<Client> sortAlphabetical() {
		String[] names = new String[currNumClients];
		for(int i = 0; i < currNumClients; i++) {
			names[i] = clients.get(i).toString();
		}
		Arrays.sort(names);
		ArrayList<Client> sortedClients = new ArrayList<Client>();
		for(int i = 0; i < currNumClients; i++) {	
			sortedClients.add(getClientByName(names[i])); 
		}
		return sortedClients;
	}
	
	public Client getClientByName(String name) {
		for(int i = 0; i < currNumClients; i++) {
			if(clients.get(i).toString().equals(name)) {
				return clients.get(i);
			}
		}
		return null;
	}
	
	public void deleteClientByName(String name) {
		for(int i = 0; i < currNumClients; i++) {
			if(clients.get(i).toString().equals(name)) {
				clients.remove(i);
				currNumClients--;
				System.out.println("\nDeleting client with name: "+ name);
			}
		}
	}
	
	public void editBalanceByName(String name, double newAmount) {
		Client c = getClientByName(name);
		c.setBalance(newAmount);
		System.out.println("\nSetting balance of client: " + c.getFirstName() + " " + c.getLastName() +" to " + c.getBalance());
	}
	
	public void setStorageFile(String fileName) {
		this.fileName = fileName;
	}
	
	public ArrayList<Client> getClients(){
		return clients;
	}
	
	public void saveClients() {
		loader.writeToFile(clients, fileName, true);
	}
}
