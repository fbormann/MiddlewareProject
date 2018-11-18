package infraEstrutura;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IEstoque extends Remote {
	
	String add(String item) throws RemoteException;
	
	String remove(String item) throws RemoteException;

	String getAll() throws RemoteException;
	
}
