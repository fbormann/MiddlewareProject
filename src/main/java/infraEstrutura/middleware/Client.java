package main.java.infraEstrutura.middleware;

import main.java.infraEstrutura.IClient;
import main.java.infraEstrutura.IEstoque;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client implements IClient {
	
	private IEstoque server;
	
	public Client(String host) throws
						MalformedURLException,
						RemoteException,
						NotBoundException {
		System.out.println("host: "+host);
		server = (IEstoque) Naming.lookup(host);
	}
	
	
	public void add(String item) throws RemoteException {
		String message = server.add(item);
		System.out.println(message);
		
	}
	
	public void remove(String item) throws RemoteException {
		String message = server.remove(item);
		System.out.println(message);
	}
	
	public void list() throws RemoteException {
		String message = server.getAll();
		System.out.println(message);
	}
	
	public static void main(String[] args)
			throws RemoteException, MalformedURLException, NotBoundException  {
		System.out.println("the client is run too");
		int port = 12345;
		String host = String.format("//127.0.0.1:%1$d/Estoque", port);
		Client client = new Client(host);
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        while(true) {
    		String fullLine = in.nextLine();
    		String[] command = fullLine.split(" ");
    		System.out.println(command.toString());
    		if (command[0].equals("add")) {
        		client.add(command[1]);
        	} else if(command[0].equals("remove")){
        		client.remove(command[1]);
        	} else if(command[0].equals("list")){
        		client.list();
        	} else {
        		System.out.println(
        				"Invalid command. Use 'add', 'remove' or 'list' \n");
        	}
    	}
    } 
	
}
