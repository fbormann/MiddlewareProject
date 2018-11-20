package main.java.naming;

import java.io.IOException;

import main.java.distribuicao.ClientProxy;
import main.java.distribuicao.EstoqueProxy;

public interface INaming {
	public void bind(String service, ClientProxy clientProxy) throws Exception;

	public ClientProxy lookUp(String service) throws Exception;

}
