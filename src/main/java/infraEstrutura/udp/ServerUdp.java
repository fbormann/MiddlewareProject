package main.java.infraEstrutura.udp;

import main.java.infraEstrutura.Estoque;
import main.java.infraEstrutura.IServer;

public class ServerUdp extends IServer {
	
	static UdpServerRequestHandler udpServerRequestHandler;
	public ServerUdp(int port) throws Exception{
		estoque = new Estoque();
        udpServerRequestHandler = new UdpServerRequestHandler(port);
	}
	
    public static void main(String[] args) throws Exception {
		ServerUdp server = new ServerUdp(2004);
    	String out;
		while (true){
    		byte[] data = udpServerRequestHandler.receive();
    		String[] command = new String(data).split(" ");
    		command[1] = command[1].replace(System.getProperty("line.separator"), "");
        	if (command[0].equals("add")) {
        		out = server.add(command[1]);
        	} else if(command[0].equals("remove")){
        		out = server.remove(command[1]);
        	} else if(command[0].equals("list")){
        		out = server.getAll();
        	} else {
        		out = "Invalid command. Use 'add', 'remove' or 'list' \n";
        	}
        	udpServerRequestHandler.send(out.getBytes());
        }
    }
}
   
