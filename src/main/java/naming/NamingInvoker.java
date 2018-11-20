package main.java.naming;


import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.criptografia.Marshaller;
import main.java.distribuicao.message.Message;
import main.java.infraEstrutura.ServerRequestHandler;

public class NamingInvoker {

	public void invoke(int port) throws Exception {
		ServerRequestHandler srh = new ServerRequestHandler(port);
		NamingImpl impl = new NamingImpl();

		while (true) {
			srh.create();
			byte[] messageToBeUnmarshalled = srh.receive();
			Marshaller marshaller = new Marshaller();
			Message messageUnmarshalled = (Message) marshaller.unmarshall(messageToBeUnmarshalled);
			String operation = messageUnmarshalled.getOperation();
			String serviceName = null;
			ClientProxy clientProxy = null;

			switch (operation) {
				case "bind":
					serviceName = (String) messageUnmarshalled.getParameters().get(0);
					clientProxy = (ClientProxy) messageUnmarshalled.getParameters().get(1);
					impl.bind(serviceName, clientProxy);
					break;
				case "lookUp":
					serviceName = (String) messageUnmarshalled.getParameters().get(0);
					clientProxy = impl.lookUp(serviceName);
					messageUnmarshalled.setOperationResult(clientProxy);
					byte[] messageMarshalled = marshaller.marshall(messageUnmarshalled);
					srh.send(messageMarshalled);
					break;
				default:
					System.out.println("Case not expected in NamingInvoker");
					break;
			}
		}
	}
}
