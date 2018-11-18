package main.java.infraEstrutura.udp;

import main.java.infraEstrutura.IRequestHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServerRequestHandler implements IRequestHandler {

	int port;
    DatagramSocket serverSocket;
    DatagramPacket receivePacket;
    byte[] receiveData = new byte[1024];
	
	public UdpServerRequestHandler(int port){
		try {
            this.port = port;
            serverSocket = new DatagramSocket(port);
        } catch (Exception e) {
            System.out.println("Connection Failed");
        }
	}
	
	public void create(){
		
	}
	
	
	@Override
	public void send(byte[] data) throws Exception {
    	InetAddress IPAddress = receivePacket.getAddress();
		int portReceive = receivePacket.getPort();
    	DatagramPacket sendPacket = new DatagramPacket(data,
    			data.length, IPAddress, portReceive);
		serverSocket.send(sendPacket);
	}

	@Override
	public byte[] receive() throws Exception {
		receivePacket = new DatagramPacket(receiveData,
                receiveData.length);
		serverSocket.receive(receivePacket);
		return receivePacket.getData();
	}

	@Override
	public void closeConnection() throws IOException {}
}
