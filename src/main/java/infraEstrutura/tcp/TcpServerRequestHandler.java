package main.java.infraEstrutura.tcp;

import main.java.infraEstrutura.IRequestHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerRequestHandler implements IRequestHandler {

	BufferedReader socketIn;
	DataOutputStream socketOut;
	Socket socket;
	ServerSocket welcomeSocket;
	
	public TcpServerRequestHandler(int port) throws Exception{
		welcomeSocket = new ServerSocket(port);
	}
	
	public void create() throws IOException{
		socket = welcomeSocket.accept();
    	System.out.println("Accepted.");
    	socketOut = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void send(byte[] data) throws Exception {
		socketOut.write(data);
		socketOut.flush();
	}

	@Override
	public byte[] receive() throws Exception {
		socketIn = new BufferedReader(new InputStreamReader(
    			socket.getInputStream()));
		System.out.println("trying to receive");
		while (!socketIn.ready());
		byte[] buffer = new byte[100*1024];
		socket.getInputStream().read(buffer);
		return buffer;
	}
	
	public String[] receiveAsStringArray() throws Exception{
		byte[] data = receive();
		String fullCommand = new String(data);
		return fullCommand.split(" ");
	}
	
	public void closeConnection() throws IOException {
		System.out.println("server fechou");
		socket.close();
//		welcomeSocket.close();
		socketIn.close();
		socketOut.close();
	}

}
