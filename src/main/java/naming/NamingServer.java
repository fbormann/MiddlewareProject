package main.java.naming;

public class NamingServer {

	private static final int NAMINGPORT = 2224;

	public static void main(String[] args) throws Exception {

		NamingInvoker invoker = new NamingInvoker();
		invoker.invoke(NAMINGPORT);
	}
}
