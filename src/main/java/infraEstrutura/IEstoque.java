package main.java.infraEstrutura;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IEstoque extends Remote {
	
	String add(String item, String usertype) throws RemoteException;
	
	String remove(String item, String usertype) throws RemoteException;

	String getAll(String usertype) throws RemoteException;
	
}
