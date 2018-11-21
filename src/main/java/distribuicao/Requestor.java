package main.java.distribuicao;

import main.java.distribuicao.criptografia.Cripto;
import main.java.distribuicao.criptografia.ICripto;
import main.java.distribuicao.criptografia.Marshaller;
import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.IRequestHandler;
import main.java.infraEstrutura.ClientRequestHandler;

import java.io.IOException;


public class Requestor {
	public Termination invoke(Invocation inv) throws Exception{
//		System.out.println("Message being sent to: " + "host: "+ inv.getHost() + "port: " + inv.getPort());
		IRequestHandler crh = new ClientRequestHandler(inv.getHost(), inv.getPort());
        Termination termination = new Termination(null);
        boolean msgSentSuccessfully = false;
		long timeout = 6*1000;
		long timeElapsed = 0;
		long timeStarted = System.currentTimeMillis();
		while(timeElapsed < timeout && !msgSentSuccessfully) {
            timeElapsed = System.currentTimeMillis() - timeStarted;
//            System.out.println("timeElapsed: "+timeElapsed);
            try {
                crh.create();

                Marshaller marshaller = new Marshaller();
                ICripto cripto = new Cripto();

                Message msgToBeMarshalled = new Message(0, inv.getOperation(), inv.getParameters(), null);
                byte[] msgToBeCripted = marshaller.marshall(msgToBeMarshalled);
                byte[] cripted = cripto.encript(msgToBeCripted);
                crh.send(cripted);
                byte[] response = crh.receive();
                if (response == null) {
//                    System.out.println("Server lost connection. Retrying");
                    Thread.sleep(2*1000);
                } else {
                    termination.setResult(marshaller.unmarshall(response));
                    msgSentSuccessfully = true;
                }
                crh.closeConnection();
            } catch (IOException e) {
//                System.out.println("Client lost connection, message may not been sent succefully");
                Thread.sleep(2*1000);
            }
        }
        return termination;
	}
}
