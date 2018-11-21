package main.java.naming;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.EstoqueProxy;
import main.java.naming.INaming;

public class NamingImpl implements INaming {

	private Map<String, LinkedList<ClientProxy>> lookUpTable;

	public NamingImpl() {
		this.lookUpTable = new HashMap<String, LinkedList<ClientProxy>>();
	}

	@Override
	public void bind(String service, ClientProxy proxy) throws ClassNotFoundException, IOException {
		System.out.println("bind on Impl (bind): " + proxy.getHost());
		if (this.lookUpTable.containsKey(service)) {
			this.lookUpTable.get(service).addLast(proxy);
		} else {
			LinkedList<ClientProxy> newService = new LinkedList<ClientProxy>();
			newService.add(proxy);
			this.lookUpTable.put(service, newService);
		}
	}

	@Override
	public ClientProxy lookUp(String service) throws Exception {
		ClientProxy proxy = this.lookUpTable.get(service).getFirst();
		System.out.println("lookup on Impl (lookup): " + proxy.getHost());
		this.lookUpTable.get(service).removeFirst();
		this.lookUpTable.get(service).addLast(proxy);
		return proxy;
	}

}
