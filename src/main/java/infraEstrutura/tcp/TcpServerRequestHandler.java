package main.java.infraEstrutura.tcp;

import main.java.infraEstrutura.IRequestHandler;

import java.io.*;
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
		System.out.println("trying to receive");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		int bufferMaxSize = 1024;
		byte[] content = new byte[ bufferMaxSize ];  
		int bytesRead = bufferMaxSize;  
		while(bytesRead == bufferMaxSize ) {  
			bytesRead = socket.getInputStream().read(content);
			baos.write( content, 0, bytesRead ); 
		} // while 
		return baos.toByteArray();
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
		socketOut.close();
	}

}
