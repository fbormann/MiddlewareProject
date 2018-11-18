package analise;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import infraEstrutura.IClient;
import infraEstrutura.IEstoque;
import infraEstrutura.middleware.Client;
import infraEstrutura.tcp.ClientTcp;
import infraEstrutura.udp.ClientUdp;

public class benchmark {
	
	public static void test(BufferedWriter writer, IClient client) throws Exception {
		int[] executionAmount = new int[] {50000};
		long start;
        long end;
        long duration;
        for (int i = 0; i < executionAmount.length; i++) {
			for (int j = 0; j < executionAmount[i];j++) {
                start = System.nanoTime();
                client.add("new_item");
                end = System.nanoTime();
                duration = end - start;
                if (duration > 0L) {
                    writer.write(duration + "\n");
                } else {
                    j--; //so the counter doesn't move and we try again
                }
			}
		}
	}
	
	public static void main(String[] args) {
		
        
        Path middlePath = Paths.get("middleware.txt");
        Path tcpPath = Paths.get("tcp.txt");
        Path udpPath = Paths.get("udp.txt");

        // we assume that both server applications are running on the side.
        try {
            System.out.println("Middleware test");
            int port = 12345;
    		String host = String.format("//127.0.0.1:%1$d/Estoque", port);
            BufferedWriter writer = Files.newBufferedWriter(middlePath);
            Client client = new Client(host); 
            test(writer, client);
			System.out.println("Middleware test ended");
			
			System.out.println("TCP test");
            BufferedWriter tcpWriter = Files.newBufferedWriter(tcpPath);
			ClientTcp clientTcp = new ClientTcp("127.0.0.1", 2005);
			test(tcpWriter, clientTcp);
			System.out.println("TCP test ended");
			
			BufferedWriter udpWriter = Files.newBufferedWriter(udpPath);
            ClientUdp udpClient = new ClientUdp("127.0.0.1",2004);
            System.out.println("Udp test");
            test(udpWriter, udpClient);
            
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
}