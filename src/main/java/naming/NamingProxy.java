package main.java.naming;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.criptografia.Marshaller;
import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.ClientRequestHandler;

public class NamingProxy implements INaming {
	
	private String host;
	private int port;
	private ClientRequestHandler clientRequestHandler;

	public NamingProxy(String host, int port) throws Exception {
		this.host = host;
		this.port = port;
		this.clientRequestHandler = new ClientRequestHandler(this.host, this.port);
	}

	@Override
	public void bind(String serviceName, ClientProxy clientProxy) throws Exception {
		System.out.println("trying to create connection");
		this.clientRequestHandler.create();
		System.out.println("connection created");
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		List<Object> parameters = new ArrayList<>();
		parameters.add(serviceName);
		parameters.add(clientProxy);

		Message message = new Message(methodName, parameters);
		Marshaller marshaller = new Marshaller();
		byte[] messageMarshalled = marshaller.marshall(message);
		this.clientRequestHandler.send(messageMarshalled);
		System.out.println("Send bind requisition for service: "+ serviceName);
		this.clientRequestHandler.closeConnection();
	}

	@Override
	public ClientProxy lookUp(String serviceName) throws Exception {
		this.clientRequestHandler.create();
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		List<Object> parameters = new ArrayList<>();
		parameters.add(serviceName);

		Message message = new Message(methodName, parameters);
		Marshaller marshaller = new Marshaller();
		byte[] messageMarshalled = marshaller.marshall(message);
		this.clientRequestHandler.send(messageMarshalled);
		messageMarshalled = this.clientRequestHandler.receive();
		this.clientRequestHandler.closeConnection();
		message = (Message)marshaller.unmarshall(messageMarshalled);
		return (ClientProxy) message.getOperationResult();
	}

}