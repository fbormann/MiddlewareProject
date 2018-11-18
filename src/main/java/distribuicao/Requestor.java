package distribuicao;

import java.util.Arrays;

import distribuicao.criptografia.Cripto;
import distribuicao.criptografia.ICripto;
import distribuicao.criptografia.Marshaller;
import distribuicao.message.Message;
import infraEstrutura.IRequestHandler;
import infraEstrutura.tcp.TcpClientRequestHandler;

public class Requestor {
	public Termination invoke(Invocation inv) throws Exception{
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
