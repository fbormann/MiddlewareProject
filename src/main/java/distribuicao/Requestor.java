package main.java.distribuicao;

import main.java.distribuicao.criptografia.Cripto;
import main.java.distribuicao.criptografia.ICripto;
import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.IRequestHandler;
import main.java.infraEstrutura.tcp.TcpClientRequestHandler;


public class Requestor {
	public Termination invoke(Invocation inv) throws Exception{
		System.out.println("Message being sent to: " + "host: "+ inv.getHost() + "port: " + inv.getPort());
		IRequestHandler crh = new TcpClientRequestHandler(inv.getHost(), inv.getPort());
		crh.create();

		Marshaller marshaller = new Marshaller();
		ICripto cripto = new Cripto();
		
		Termination termination = new Termination(null);
		Message msgToBeMarshalled = new Message(0, inv.getOperation(), inv.getParameters(), null);
		byte[] msgToBeCripted = marshaller.marshall(msgToBeMarshalled);
		byte[] cripted = cripto.encript(msgToBeCripted);
		crh.send(cripted);
		byte[] response = crh.receive();

		termination.setResult(marshaller.unmarshall(response));
		crh.closeConnection();
		return termination;
	}
}
