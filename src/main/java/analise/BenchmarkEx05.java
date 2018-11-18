package analise;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import aplicacao.AppClient;
import infraEstrutura.IClient;
import infraEstrutura.middleware.Client;
import infraEstrutura.tcp.ClientTcp;
import infraEstrutura.udp.ClientUdp;

public class BenchmarkEx05 {
		
		public static void test(BufferedWriter writer, IClient client, int exec) throws Exception {
			int[] executionAmount = new int[] {exec};
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
			
	        
	        Path middleware_rmi_50000 = Paths.get("middleware_rmi_50000.txt");
	        Path middleware_rmi_100000 = Paths.get("middleware_rmi_100000.txt");
	        Path middleware_rmi_10000 = Paths.get("middleware_rmi_10000.txt");
	        Path remote_patterns_50000 = Paths.get("remote_patterns_50000.txt");
	        Path remote_patterns_100000 = Paths.get("remote_patterns_100000.txt");
	        Path remote_patterns_10000 = Paths.get("remote_patterns_10000.txt");

	        // we assume that both server applications are running on the side.
	        try {
	        	int port = 12345;
//	        
//	        	System.out.println("Middleware rcp test 100000");
//	            BufferedWriter writer = Files.newBufferedWriter(remote_patterns_100000);
//	            AppClient appClient = new AppClient(); 
//	            test(writer, appClient, 100000);	
//				System.out.println("Middleware rcp test ended");
				
//				System.out.println("Middleware rcp test 50000");
//	            BufferedWriter writer = Files.newBufferedWriter(remote_patterns_50000);
//	            AppClient appClient = new AppClient(); 
//	            test(writer, appClient, 50000);	
//				System.out.println("Middleware rcp test ended");
				
//				System.out.println("Middleware rcp test 10000");
//	            BufferedWriter writer = Files.newBufferedWriter(remote_patterns_10000);
//	            AppClient appClient = new AppClient(); 
//	            test(writer, appClient, 10000);	
//				System.out.println("Middleware rcp test ended");
				
//	        	System.out.println("Middleware rmi test 100000");
//	            BufferedWriter writer = Files.newBufferedWriter(middleware_rmi_100000);
//	    		String host = String.format("//127.0.0.1:%1$d/Estoque", port);
//				Client clientRmi = new Client(host);
//				test(writer, clientRmi, 100000);
//	            System.out.println("Middleware rmi test ended");
//				
//	        	System.out.println("Middleware rmi test 50000");
//	            BufferedWriter writer = Files.newBufferedWriter(middleware_rmi_50000);
//	    		String host = String.format("//127.0.0.1:%1$d/Estoque", port);
//				Client clientRmi = new Client(host);
//				test(writer, clientRmi, 50000);
//	            System.out.println("Middleware rmi test ended");
				
	        	System.out.println("Middleware rmi test 10000");
	            BufferedWriter writer = Files.newBufferedWriter(middleware_rmi_10000);
	    		String host = String.format("//127.0.0.1:%1$d/Estoque", port);
				Client clientRmi = new Client(host);
				test(writer, clientRmi, 10000);
	            System.out.println("Middleware rmi test ended");
	            
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	     
}
