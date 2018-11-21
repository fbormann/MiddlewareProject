package main.java.aplicacao;

import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.EstoqueProxy;
import main.java.distribuicao.Invoker;
import main.java.naming.NamingProxy;


public class Server {
	public static void main(String[] args) {
//		System.out.println("Running the server app");
		try {
			NamingProxy namingProxy = new NamingProxy("naming_server", 2224);
			System.out.println("Found client app on naming server");
			Invoker inv = new Invoker();
			ClientProxy proxy = new EstoqueProxy("server", 2000, 1234);
			System.out.println("Client proxy created, host: " + proxy.getHost());
			namingProxy.bind("Estoque", proxy);
			inv.invoke(proxy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
