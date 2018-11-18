package distribuicao.message;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int requestId;
	private String operation;
	private ArrayList<Object> parameters;
	private Object operationResult;
	
	public Message(int requestId, String operation, ArrayList<Object> parameters, Object operationResult) {
		super();
		this.requestId = requestId;
		this.operation = operation;
		this.parameters = parameters;
		this.operationResult = operationResult;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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

	public Object getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(Object operationResult) {
		this.operationResult = operationResult;
	}
	
	@Override
	public String toString() {
		if(parameters != null && parameters.size()>0 && parameters.get(0)!=null) {
			return operation+" "+parameters.get(0).toString()+". isResponse= "+(operationResult!=null);
		} else {
			return operation;
		}
	}
	
}
