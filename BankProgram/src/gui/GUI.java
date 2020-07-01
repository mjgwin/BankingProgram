package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.Client;
import data.ClientManager;


public class GUI extends JFrame{
	
	private Container pane;
	private JTextArea textArea;
	private JButton addClient, displayClient, enterCommand, saveClients;
	private JScrollPane scrollPane;
	private JTextField inputField;
	private JPanel buttonPanel, inputPanel;
	
	private ClientManager manager;
	
	public GUI(ClientManager manager) {
		setupComponents();
		this.manager = manager;
		textArea.append("Loaded " + manager.getClients().size() + " clients from database file");
	}
	
	private void setupComponents() {
		pane = getContentPane();
		textArea = new JTextArea(20,20);
		inputField = new JTextField(20);
		addClient = new JButton("Add Client");
		displayClient = new JButton("Display Clients");
		enterCommand = new JButton("Send");
		saveClients = new JButton("Save");
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        setLayout(new BorderLayout());
        addClient.setPreferredSize(new Dimension(120, 30));
        displayClient.setPreferredSize(new Dimension(120, 30));
        enterCommand.setPreferredSize(new Dimension(120, 30));
        saveClients.setPreferredSize(new Dimension(120, 30));
        scrollPane.setPreferredSize(new Dimension(400, 300));
        buttonPanel.add(addClient);
        buttonPanel.add(displayClient);
        buttonPanel.add(saveClients);
        inputPanel.add(inputField);
        inputPanel.add(enterCommand);
        pane.add(scrollPane, BorderLayout.WEST);
        pane.add(inputPanel, BorderLayout.SOUTH);
        pane.add(buttonPanel, BorderLayout.CENTER);
		setTitle("Bank Program");
		setSize(600,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		addListeners();
	}
	
	private void addListeners() {
		addClient.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				String input = JOptionPane.showInputDialog("ADD CLIENT (<FIRSNAME> <LASTNAME> <BALANCE>)");
				try {
					String[] splitInput = input.split(" ");
					manager.addClient(splitInput[0],splitInput[1],Double.parseDouble(splitInput[2]));
					Client c = manager.getClientByName(new String(splitInput[0] + " " + splitInput[1]));
					textArea.append("\nAdding new client: " + c.toString() + " with balance " + c.getBalance());
				}catch(Exception s) {
					displayMessage("\nError adding client: check formatting (<Firstname> <Lastname> <Balance>)");
				}
			}  
			});
		
		displayClient.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				displayClients(manager.sortAlphabetical());
			}  
			});
		
		saveClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.saveClients();
				textArea.append("\nDone writing to save file");
			}
			
		});
	}
	
	private void displayClients(ArrayList<Client> clients) {
		if(clients.size() > 0) {
			textArea.append("\nClients sorted by name: ");
			for(int i = 0; i < clients.size(); i++) {
				textArea.append("\n" +clients.get(i).toString() + " " + clients.get(i).getBalance());
			}
			manager.displayDatabaseAsString(clients);
		}else {
			displayMessage("\nNo clients in system!");
		}
		
	}
	
	private void displayMessage(String message) {
		textArea.append(message);
		System.out.println(message);
	}
	
}
