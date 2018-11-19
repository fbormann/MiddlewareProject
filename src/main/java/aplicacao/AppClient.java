package main.java.aplicacao;

import main.java.distribuicao.EstoqueProxy;
import main.java.infraEstrutura.IClient;
import java.util.Scanner;

public class AppClient implements IClient {
	static EstoqueProxy proxy;
	
	public AppClient(){
		proxy = new EstoqueProxy("localhost", 2000, 1234);
	}
	
	public static void main(String[] args) {
		try {
			Authentication auth = new Authentication();
			boolean loggedin = false;
			while(!loggedin) {
				Scanner sc = new Scanner(System.in);
				System.out.print("User: ");
				String login = sc.nextLine();
				System.out.print("Password: ");
				String password = sc.nextLine();
				
				loggedin = auth.signin(login, password);
				
				if(!loggedin) System.out.println("User or password is wrong");
				
				System.out.println();
			}
			
			AppClient client = new AppClient();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Running the client app");
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
