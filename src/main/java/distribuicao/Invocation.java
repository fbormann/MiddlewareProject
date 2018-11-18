package distribuicao;

import java.util.ArrayList;

public class Invocation {
	private int objectId;
	private String host;
	private int port;
	private String operation;
	private ArrayList<Object> parameters;
	
	public Invocation(int objectId, String host, int port, String operation, ArrayList<Object> parameters) {
		super();
		this.objectId = objectId;
		this.host = host;
		this.port = port;
		this.operation = operation;
		this.parameters = parameters;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public ArrayList<Object> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}
}
