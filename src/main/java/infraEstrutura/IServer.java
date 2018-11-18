package infraEstrutura;

public abstract class IServer {
	
	public static int port;
	public static String host;
	public static IEstoque estoque;

	public String add(String item) throws Exception {
		return estoque.add(item);
	}

	public String remove(String item) throws Exception {
		return estoque.remove(item);
	}

	public String getAll() throws Exception {
		return estoque.getAll();
	}
	
}
