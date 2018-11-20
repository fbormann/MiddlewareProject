package main.java.infraEstrutura.tcp;

import main.java.infraEstrutura.IRequestHandler;
import java.io.*;
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
		System.out.println("data sent through request handler");
		socketOut.write(data);
		socketOut.flush();
	}

	@Override
	public byte[] receive() throws Exception {
		System.out.println("trying to receive client");
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
	
	public void closeConnection() throws IOException{
		System.out.println("cliente fechou");
		socket.close();
		socketOut.close();
	}


}
