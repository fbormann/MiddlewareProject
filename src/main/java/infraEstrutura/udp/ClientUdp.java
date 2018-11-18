package main.java.infraEstrutura.udp;

import main.java.infraEstrutura.IClient;

import java.util.Scanner;

public class ClientUdp implements IClient {

	UdpClientRequestHandler clientRequestHandler;
	
    public ClientUdp(String host, int port) {
    	clientRequestHandler = new UdpClientRequestHandler(host, port);
    }

    private void sendInfo(String item) throws Exception {
        clientRequestHandler.send(item.getBytes());
		System.out.println("Message sent. Waiting for server response.");
		String response = new String(clientRequestHandler.receive());
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
        sendInfo(command+" \n");
    }

    public static void main(String[] args) throws Exception {
    	ClientUdp client = new ClientUdp("127.0.0.1", 2004);
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
    	while(true) {
    		String command = in.nextLine();
    		client.custom(command);
    	}
    }
}
