package main.java.distribuicao;

import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.IRequestHandler;
import main.java.infraEstrutura.tcp.TcpClientRequestHandler;

public class Requestor {
	public Termination invoke(Invocation inv) throws Exception{
		IRequestHandler crh = new TcpClientRequestHandler(inv.getHost(), inv.getPort());
		crh.create();
		Marshaller marshaller = new Marshaller();
		Termination termination = new Termination(null);
		Message msgToBeMarshalled = new Message(0, inv.getOperation(), inv.getParameters(), null);
		System.out.println("send msg: "+msgToBeMarshalled.toString());
		crh.send(marshaller.marshall(msgToBeMarshalled));
		System.out.println("sent");
		byte[] response = crh.receive();
		System.out.println("received");

		termination.setResult(marshaller.unmarshall(response));
		crh.closeConnection();
		return termination;
	}
}
