package infraEstrutura;

import java.io.IOException;

public interface IRequestHandler {

	public void send(byte[] data) throws Exception;
	public byte[] receive() throws Exception;
	public void closeConnection() throws IOException ;
	public void create() throws IOException;
}
