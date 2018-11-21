package main.java.analise;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.java.aplicacao.AppClient;
import main.java.infraEstrutura.IClient;

public class Benchmark {
	public static void test(BufferedWriter writer, IClient client) throws Exception {
		int[] executionAmount = new int[] {100000};
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
					writer.append(duration + "\n");
				} else {
					j--; //so the counter doesn't move and we try again
				}
			}
		}
		writer.close();
	}

	public static void main(String[] args) {
		int amount = 100000;
		String filename = "middleware_" + amount + ".txt";
		Path testPath = Paths.get(filename);
		
		// we assume that both server applications are running on the side.
		try {
			System.out.println("Middleware test");
			
			BufferedWriter writer = Files.newBufferedWriter(testPath);
			AppClient client = new AppClient(); 
			System.out.println("AppClient started");
			
			test(writer, client);
			System.out.println("Middleware test ended");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}