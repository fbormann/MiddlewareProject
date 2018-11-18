package aplicacao;

import distribuicao.ClientProxy;
import distribuicao.EstoqueProxy;
import distribuicao.Invoker;

public class Server {
	public static void main(String[] args) {
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
