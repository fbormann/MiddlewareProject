package main.java.infraEstrutura;

public interface IClient {
	void add(String item, String usertype) throws Exception;
	void remove(String item, String usertype) throws Exception;;
	void list(String usertype) throws Exception;
}
