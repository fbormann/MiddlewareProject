package aplicacao;

import java.util.Scanner;
import distribuicao.EstoqueProxy;
import infraEstrutura.IClient;

public class AppClient implements IClient {
	static EstoqueProxy proxy;
	
	public AppClient(){
		proxy = new EstoqueProxy("localhost", 2000, 1234);
	}
	
	public static void main(String[] args) {
		
		try {
			AppClient client = new AppClient();
			Scanner scanner = new Scanner(System.in);
			while(true){
				String msg = scanner.nextLine();
				String[] msgArray = msg.split(" ");
				switch (msgArray[0]) {
				case "add":
					client.add(msgArray[1]);
					break;
				case "remove":
					client.remove(msgArray[1]);
					break;
				case "list":
					client.list();
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void add(String item) throws Exception {
		System.out.println(proxy.add(item));
	}

	@Override
	public void remove(String item) throws Exception {
		System.out.println(proxy.remove(item));		
	}

	@Override
	public void list() throws Exception {
		System.out.println(proxy.getAll());		
	}
}
