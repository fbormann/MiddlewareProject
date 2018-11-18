package main.java.infraEstrutura.tcp;

import main.java.infraEstrutura.IRequestHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClientRequestHandler implements IRequestHandler {

	private Socket socket;
	private DataOutputStream socketOut;
	private BufferedReader socketIn;
	private String host;
	private int port;
	public TcpClientRequestHandler(String host, int port) throws Exception{
		this.host = host;
		this.port = port;
		
	}

	public void create() throws IOException {
		boolean create = false;
		while(!create){
			try {
				socket = new Socket(host,port);
				create = true;
			} catch (Exception e){}
		}
		socketOut = new DataOutputStream(
				socket.getOutputStream());
		socketIn = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

	}
	
	@Override
	public void send(byte[] data) throws Exception {
		socketOut.write(data);
		socketOut.flush();
		System.out.println("sent");
	}

	@Override
	public byte[] receive() throws Exception {
		System.out.println("trying to receive client");
		byte[] buffer = new byte[100*1024];
		socket.getInputStream().read(buffer);
		System.out.println("read");
		return buffer;
	}
	
	public void closeConnection() throws IOException{
		System.out.println("cliente fechou");
		socket.close();
		socketIn.close();
		socketOut.close();
	}


}
