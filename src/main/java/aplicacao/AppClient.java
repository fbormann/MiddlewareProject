package main.java.aplicacao;

import main.java.distribuicao.EstoqueProxy;
import main.java.infraEstrutura.IClient;
import main.java.naming.NamingProxy;

import java.util.Scanner;

public class AppClient implements IClient {
	static EstoqueProxy proxy;
	
	public AppClient() throws Exception {
		NamingProxy namingProxy = new NamingProxy("localhost", 2224);
		proxy = (EstoqueProxy) namingProxy.lookUp("Estoque");
	}
	
	public static void main(String[] args) {
		try {


			Authentication auth = new Authentication();
			int state = MenuStateMachine.MENU_STATE;
			String login, password;
			boolean signin, loggedin;
			Scanner sc = new Scanner(System.in);
			while(state<3) {
				switch (state) {
					case MenuStateMachine.SIGNIN_STATE:
						System.out.println("Username: ");
						login = sc.next();
						System.out.println("Password: ");
						password = sc.next();
						signin = auth.signup(login, password);
						if (!signin) {
							System.out.println("Failed to signup");
							System.out.println("0. Go back to menu");
							System.out.println("1. try again");
							int choice = sc.nextInt();
							if (choice != 1) {
								state = 0;
							}
						} else {
							System.out.println("Now login with this username and password");
							state = MenuStateMachine.LOGIN_STATE;
						}
						break;
					case MenuStateMachine.LOGIN_STATE:
						System.out.println("User: ");
						login = sc.next();
						System.out.println("Password: ");
						password = sc.next();
						loggedin = auth.signin(login, password);
						if(!loggedin) {
							System.out.println("User or password is wrong");
							System.out.println("0. Go back to menu");
							System.out.println("1. try again");
							int choice = sc.nextInt();
							if (choice != 1) {
								state = 0;
							}
						} else {
							state = MenuStateMachine.EXIT_STATE;
						}
						break;
					case MenuStateMachine.MENU_STATE:
					default:
						System.out.println("Choose what you want to do");
						System.out.println("0. sign up");
						System.out.println("1. login");
						int choice = sc.nextInt();
						if (choice == MenuStateMachine.LOGIN_STATE ||
								choice == MenuStateMachine.SIGNIN_STATE) {
							state = choice;
						}
						break;
				}
			}
			System.out.println();
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
