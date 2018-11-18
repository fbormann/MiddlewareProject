package infraEstrutura.tcp;
import java.util.Scanner;

import infraEstrutura.IClient;

public class ClientTcp implements IClient {
	
	TcpClientRequestHandler tcpClientRequestHandler;
	
	public ClientTcp(String host, int port) throws Exception{
		tcpClientRequestHandler = new TcpClientRequestHandler(host, port);
	}
	
	private void sendInfo(String in) throws Exception {
		tcpClientRequestHandler.send(in.getBytes());
		System.out.println("Message sent. Waiting for server response.");
		String response = new String(tcpClientRequestHandler.receive());
		System.out.println(response);
	}
	
	public void add(String item) throws Exception {
		sendInfo("add "+item+"\n");
	}
	
	public void remove(String item) throws Exception {
		sendInfo("remove "+item+"\n");
	}
	
	public void list() throws Exception {
		sendInfo("list "+"\n");
	}
	
	public void custom(String command) throws Exception {
		sendInfo(command+"\n");
	}
	
    public static void main(String[] args) throws Exception {
    	ClientTcp client = new ClientTcp("127.0.0.1", 2005);
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
    	while(true) {
    		String command = in.nextLine();
    		client.custom(command);
    	}
    }

}
