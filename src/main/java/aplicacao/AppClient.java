package main.java.aplicacao;

import main.java.distribuicao.EstoqueProxy;
import main.java.infraEstrutura.IClient;
import main.java.naming.NamingProxy;

import java.util.Scanner;

public class AppClient implements IClient {
	static EstoqueProxy proxy;
    static int state = MenuStateMachine.MENU_STATE;
    static String login, password, type = null;
    static boolean signin, loggedin;

	public AppClient() throws Exception {
		NamingProxy namingProxy = new NamingProxy("localhost", 2224);
		// Add Naming-Server as a docker service (Don't know how to call it)
//        NamingProxy namingProxy = new NamingProxy("Naming-Server", 2224);
		proxy = (EstoqueProxy) namingProxy.lookUp("Estoque");
	}

	public static void main(String[] args) {
		while(true){
            Authentication auth = new Authentication();
            Scanner sc = new Scanner(System.in);
            while(state<3) {
                try {
                    switch (state) {
                        case MenuStateMachine.SIGNIN_STATE:
                            System.out.println("Username: ");
                            login = sc.next();
                            System.out.println("Password: ");
                            password = sc.next();
                            boolean invalid_type = true;
                            while(invalid_type) {
                                System.out.println("type: ");
                                type = sc.next();
                                if(type.equalsIgnoreCase("Admin") ||
                                        type.equalsIgnoreCase("Seller") ||
                                        type.equalsIgnoreCase("Manager")) {
                                    invalid_type = false;
                                } else {
                                    System.out.println("Invalid type");
                                    System.out.println("Possible types are: Admin, Seller, Manager");
                                }
                            }
                            signin = auth.signup(login, password, type);
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
                            type = auth.signin(login, password);
                            loggedin = type!=null;
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
                } catch (java.sql.SQLException e) {
                    System.out.print("Database exception.");
                    if(state == MenuStateMachine.SIGNIN_STATE) {
                        System.out.println("Try a different username");
                    }
                }
            }
            try {
                System.out.println();
                AppClient client = new AppClient();
                Scanner scanner = new Scanner(System.in);
//                System.out.println("Running the client app");
                while(loggedin){
                    String msg = scanner.nextLine();
//                    System.out.println("msg: " + msg);
                    String[] msgArray = msg.split(" ");
                    switch (msgArray[0]) {
                        case "add":
                            client.add(msgArray[1], type);
                            break;
                        case "remove":
                            client.remove(msgArray[1], type);
                            break;
                        case "list":
                            client.list(type);
                            break;
                        case "logout":
                            loggedin = false;
                            state = MenuStateMachine.MENU_STATE;
                            break;
                        default:
//                            System.out.println("Invalid command. Try 'add {item}', 'remove {item}', 'list' or 'logout'");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	@Override
	public void add(String item, String usertype) throws Exception {
		System.out.println(proxy.add(item, usertype));
	}

	@Override
	public void remove(String item, String usertype) throws Exception {
		System.out.println(proxy.remove(item, usertype));
	}

	@Override
	public void list(String usertype) throws Exception {
		System.out.println(proxy.getAll(usertype));
	}
}
