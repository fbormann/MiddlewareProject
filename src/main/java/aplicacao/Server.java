package main.java.aplicacao;

import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.EstoqueProxy;
import main.java.distribuicao.Invoker;
import main.java.naming.NamingProxy;


public class Server {
	public static void main(String[] args) {
		System.out.println("Running the server app");
		try {
			NamingProxy namingProxy = new NamingProxy("localhost", 2224);
			Invoker inv = new Invoker();
			ClientProxy proxy = new EstoqueProxy("localhost", 2000, 1234);
			namingProxy.bind("Estoque", proxy);
			inv.invoke(proxy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
