package main.java.infraEstrutura;

import main.java.infraEstrutura.IRequestHandler;
import java.io.*;
import java.net.Socket;


public class ClientRequestHandler implements IRequestHandler {
	private Socket socket;
	private DataOutputStream socketOut;
	private String host;
	private int port;

	public ClientRequestHandler(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void create() throws IOException {
		boolean create = false;
		while(!create){
            socket = new Socket(host,port);
            create = true;
		}
		socketOut = new DataOutputStream(
				socket.getOutputStream());
	}
	
	@Override
	public void send(byte[] data) throws IOException {
//		System.out.println("data sent through request handler");
		socketOut.write(data);
		socketOut.flush();
	}

	@Override
	public byte[] receive() throws IOException {
//		System.out.println("trying to receive client");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bufferMaxSize = 1024;
		byte[] content = new byte[ bufferMaxSize ];  
		int bytesRead = bufferMaxSize;
		while(bytesRead == bufferMaxSize ) {
			bytesRead = socket.getInputStream().read(content);
			if(emptyArray(content)){
			    return null;
            }
            baos.write( content, 0, bytesRead );
		} // while 
		return baos.toByteArray();
	}

	private boolean emptyArray(byte[] array){
	    for(byte b : array){
	        if(b!=0){
	            return false;
            }
        }
        return true;
    }

	public void closeConnection() throws IOException{
//		System.out.println("cliente fechou");
		socket.close();
		socketOut.close();
	}


}
