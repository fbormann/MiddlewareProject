package main.java.aplicacao;

import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.EstoqueProxy;
import main.java.distribuicao.Invoker;

public class Server {
	public static void main(String[] args) {
		System.out.println("Running the server app");

		ClientProxy proxy = new EstoqueProxy("localhost", 2000, 1234);
		
		Invoker inv = new Invoker();
		try {
			inv.invoke(proxy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
