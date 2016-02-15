package fmi.corejava;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public class StressingNetwork implements Runnable {
	
	private RequestSending requestSending;
	private CyclicBarrier bar;
	public static AtomicBoolean isBroken = new AtomicBoolean(false);
	private static AtomicLong maxTimeForResponse = new AtomicLong(0L);
	
	public StressingNetwork(RequestSending requestSending, CyclicBarrier bar) {
		this.requestSending = requestSending;
		this.bar = bar;
	}
	
	public void sendingRequest() throws UnexpectedResponse, IOException {
		
		Long startTime = System.currentTimeMillis();
		requestSending.sendingRequest();
		Long timeForResponse = System.currentTimeMillis() - startTime;
		
		if (timeForResponse > maxTimeForResponse.longValue()) {
			maxTimeForResponse.set(timeForResponse);
		}
	}

	public static long getMaxTimeForResponse() {
		return maxTimeForResponse.longValue();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			bar.await();
			try {
				this.sendingRequest();
			} catch (UnexpectedResponse | IOException e) {
				// TODO Auto-generated catch block
				System.out.println("The request could not be sent.");
				System.out.println(e.getMessage());
				isBroken.compareAndSet(false, true);
				e.printStackTrace();
			}
		}
		catch (BrokenBarrierException | InterruptedException e) {
				e.printStackTrace();
		}
	}
	

}
