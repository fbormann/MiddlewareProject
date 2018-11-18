package infraEstrutura.tcp;

import infraEstrutura.Estoque;
import infraEstrutura.IServer;


public class ServerTcp extends IServer {
		
	static TcpServerRequestHandler tcpServerRequestHandler;
	
	public ServerTcp(int port) throws Exception{
		tcpServerRequestHandler = new TcpServerRequestHandler(port);
		estoque = new Estoque();    	
	}
	
    public static void main(String[] args) throws Exception {
    	String out;
    	int port = 2005;
    	ServerTcp server = new ServerTcp(port);
    	while (true){
        	String[] command = tcpServerRequestHandler.receiveAsStringArray();
        	if (command[0].equals("add")) {
        		out = server.add(command[1]);
        	} else if(command[0].equals("remove")){
        		out = server.remove(command[1]);
        	} else if(command[0].equals("list")){
        		out = server.getAll();
        	} else {
        		out = "Invalid command. Use 'add', 'remove' or 'list' \n";
        	}
        	tcpServerRequestHandler.send(out.getBytes());
    	}
    }
}
   
