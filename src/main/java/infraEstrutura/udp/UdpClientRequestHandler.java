package main.java.infraEstrutura.udp;

import main.java.infraEstrutura.IRequestHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClientRequestHandler implements IRequestHandler {

	int port;
    String host;
    DatagramSocket socket;
    InetAddress ipAddress;
    byte[] receiveData = new byte[1024];
	
	public UdpClientRequestHandler(String host, int port){
		try {
            this.host = host;
            this.port = port;
            socket = new DatagramSocket();
            ipAddress = InetAddress.getByName(host);
        } catch (Exception e) {
            System.out.println("Connection Failed");
        }
	}
	
	public void create(){
		
	}
	
	@Override
	public void send(byte[] data) throws Exception {
		DatagramPacket sendPacket = new DatagramPacket(data,
                data.length, ipAddress, port);
        socket.send(sendPacket);
	}

	@Override
	public byte[] receive() throws Exception {
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
                receiveData.length);
		socket.receive(receivePacket);
		return receivePacket.getData();
	}

	@Override
	public void closeConnection() throws IOException {}

}
