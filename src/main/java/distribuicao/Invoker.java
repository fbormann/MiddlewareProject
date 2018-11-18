package main.java.distribuicao;

import main.java.distribuicao.criptografia.Cripto;
import main.java.distribuicao.criptografia.ICripto;
import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.EstoqueSQLite;
import main.java.infraEstrutura.IRequestHandler;
import main.java.infraEstrutura.tcp.TcpServerRequestHandler;

public class Invoker {
	public void invoke(ClientProxy client) throws Exception {
		System.out.println("Running...");

		Marshaller marshaller = new Marshaller();
		ICripto cripto = new Cripto();
		
		Termination termination = new Termination(null);
		EstoqueSQLite estoque = new EstoqueSQLite();
		Message msgUncripted = null;
		IRequestHandler srh = new TcpServerRequestHandler(client.getPort());

		while(true) {
			srh.create();
			System.out.println("Running loop...");
			byte[] msgToBeUncripted = srh.receive();
			byte[] msgToBeUnmarshalled = cripto.decript(msgToBeUncripted);
			msgUncripted = (Message) marshaller.unmarshall(msgToBeUnmarshalled);
			String item, result = null;
			Message reply = null;
			switch(msgUncripted.getOperation()) {
				case "add":
					item = (String) msgUncripted.getParameters().get(0);
					result = estoque.add(item);
					termination.setResult(result);
					reply = new Message(0, null, null, termination.getResult());
					srh.send(marshaller.marshall(reply));
					System.out.println("sent response");
					break;
				case "remove":
					item = (String) msgUncripted.getParameters().get(0);
					result = estoque.remove(item);
					termination.setResult(result);
					reply = new Message(0, null, null, termination.getResult());
					srh.send(marshaller.marshall(reply));
					System.out.println("sent response");
					break;
				case "list":
					result = estoque.getAll();
					termination.setResult(result);
					reply = new Message(0, null, null, termination.getResult());
					srh.send(marshaller.marshall(reply));
					System.out.println("sent response");
					break;
			}
			srh.closeConnection();
		}
	}
}
