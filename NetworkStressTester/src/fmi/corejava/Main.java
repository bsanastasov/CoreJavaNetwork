package fmi.corejava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	
	private static int request = 1;
	
	public static void start() throws IOException, FileNotFoundException, InterruptedException {
		
		RequestSending requestSending = new RequestSending(80, new File("/home/blagolaj/pCloudDrive/My Documents/FMI/2course"
				+ "/Съвременни Java технологии/Courseworks/3. Network stress tester/example_request.txt"));
		
		while (true) {
			
			ExecutorService executor = Executors.newFixedThreadPool(request);
			CyclicBarrier bar = new CyclicBarrier(request);
			StressingNetwork stress = new StressingNetwork(requestSending, bar);
			
			System.out.println("Sending " + request + " requests to the server");
			
			for (int i = 0; i < request; ++i) {
				executor.execute(stress);
			}
			
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			
			if (StressingNetwork.isBroken.get()) {
				break;
			}
			
			System.out.println(request + " requests were send successfully!");
			request ++;
		}
		
			System.out.println("Max requests, which can be send: " + (request - 1) + ".");
			System.out.println("Longest time for response is: " + StressingNetwork.getMaxTimeForResponse());
			
			
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		start();
		
	}

}
