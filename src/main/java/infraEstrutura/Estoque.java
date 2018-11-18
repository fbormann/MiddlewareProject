package infraEstrutura;

import java.lang.StringBuilder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Estoque extends UnicastRemoteObject implements IEstoque {

	public Estoque() throws RemoteException {
		super();
	}

	HashMap<String, Integer> estoque = new HashMap<String, Integer>();

    @Override
    public String add(String item) throws RemoteException {
    	item = item.trim();
    	int qtd = modify(item, true);
    	StringBuilder sb = new StringBuilder();
    	sb.append("Added ")
    		.append(item).append(" with success.")
    		.append(" Now we have ").append(qtd)
    		.append(" ").append(item).append("(s)");
    	String msg = sb.toString();
    	return msg;
    }
    
    @Override
    public String remove(String item) throws RemoteException {
    	int qtd = modify(item, false);
    	return String.format("Removed %1$s with sucess. Now we have %2$d %1$s(s)\n",
    			item, qtd);
    }
    
    @Override
    public String getAll() throws RemoteException {
    	StringBuilder sb = new StringBuilder();
    	for(String item : estoque.keySet()){
    		sb    			
    			.append(item)
    			.append(" ")
    			.append(estoque.get(item))
    			.append(" ");
    	}
    	sb.append("\n");
    	return sb.toString();
    }
    
    private int modify(String item, boolean positive) {
    	int qtd = estoque.getOrDefault(item, 0);
    	if(positive)
    		qtd++;
    	else
    		qtd--;
    	if(qtd<=0){
    		estoque.remove(item);
    		return 0;
    	} else {
        	estoque.put(item, qtd);
        	return qtd;
    	}
    }
    
}
