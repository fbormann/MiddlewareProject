package distribuicao;

import java.io.Serializable;

public class ClientProxy implements Serializable {
	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	private int objectId;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ClientProxy(String host, int port, int objectId){
		this.host = host;
		this.port = port;
		this.objectId = objectId;
	}
}
