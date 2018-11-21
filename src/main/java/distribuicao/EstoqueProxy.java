package main.java.distribuicao;

import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.IEstoque;

import java.rmi.RemoteException;
import java.util.ArrayList;



public class EstoqueProxy extends ClientProxy implements IEstoque {
	private static final long serialVersionUID = 1L;

	public EstoqueProxy(String host, int port, int objectId) {
		super(host, port, objectId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String add(String item, String usertype) throws RemoteException {
		return sendCommand("add", item, usertype);
	}

	@Override
	public String remove(String item, String usertype) throws RemoteException {
		return sendCommand("remove", item, usertype);
	}

	@Override
	public String getAll(String usertype) throws RemoteException {
		return sendCommand("list", null, usertype);
	}
	
	public String sendCommand(String command, String item, String usertype) {
	    if(!checkAcessControl(command, usertype)){
	        return "Invalid action for "+usertype+"-user";
        }
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(item);
		System.out.println("Invocation sent");
		Invocation inv = new Invocation(super.getObjectId(), super.getHost(), super.getPort(), command, parameters);
		Requestor req = new Requestor();
		Termination result = null;
		try {
			result = req.invoke(inv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Message responseMessage = ((Message) result.getResult());
		if(responseMessage == null || responseMessage.getOperationResult() == null) {
			return "Mensagem vazia";
		} else {
			return responseMessage.getOperationResult().toString();
		}
	}

	private boolean checkAcessControl(String command, String usertype) {
        if(usertype.equalsIgnoreCase("manager") &&
				command.equalsIgnoreCase("remove")) {
        	return false;
		} else if(usertype.equalsIgnoreCase("seller") &&
				command.equalsIgnoreCase("add")){
            return false;
		} else {
			return true;
		}
	}
}
