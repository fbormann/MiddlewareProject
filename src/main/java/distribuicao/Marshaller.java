package main.java.distribuicao;

import main.java.distribuicao.message.Message;

import java.io.IOException;
import java.util.ArrayList;

public class Marshaller {
	
	private final static String SPECIAL_CHAR = "QQWWW";
	
	public byte[] marshall(Message msgToBeMarshalled) throws IOException {
		String msgString = messageToString(msgToBeMarshalled);
		byte[] msg = msgString.getBytes();
		return msg;
	}

	public Message unmarshall(byte[] msgToBeUnmarshalled) throws IOException, ClassNotFoundException {
		String receivedMsg = (new String(msgToBeUnmarshalled)).trim();
		if(receivedMsg.contains(SPECIAL_CHAR)){
			receivedMsg = receivedMsg.substring(0, receivedMsg.length()-5);
			return new Message(0, null, null, receivedMsg);
		} else {
			String[] msgArray = receivedMsg.split(" ");
			ArrayList<Object> parameters = new ArrayList<Object>();
			parameters.add(msgArray[1]);
			return new Message(0, msgArray[0], parameters, null);
		}
	
	}
	
	public String messageToString(Message msg){
		StringBuilder sb = new StringBuilder();
		if (msg.getOperationResult() != null) {
			sb.append((String) msg.getOperationResult());
			// Special char if msg is a response msg
			sb.append(SPECIAL_CHAR);
		} else {
			sb.append(msg.getOperation()).append(" ");
			for(Object p : msg.getParameters()){
				sb.append(p.toString());
			}
		}
		return sb.toString();
	}
}
