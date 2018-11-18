package main.java.infraEstrutura.middleware;

import main.java.infraEstrutura.Estoque;
import main.java.infraEstrutura.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server extends IServer {

	public Server() throws RemoteException, MalformedURLException {
		port = 12345;
		host = String.format("//127.0.0.1:%1$d/Estoque", port);
		estoque = new Estoque();
		System.out.println("rmi:"+host);
		LocateRegistry.createRegistry(port);
		Naming.rebind("rmi:"+host, estoque);
	}
	
    public static void main(String[] args) throws
        RemoteException, MalformedURLException {
    	new Server();
    }

}
