package fmi.corejava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class RequestSending {
	private RequestInfo requestInfo;
	private int port;

	public RequestSending(int port, File requestFile) throws FileNotFoundException, IOException {
		this.requestInfo = new RequestInfo(requestFile);
		this.port = port;
	}
	
	public void sendingRequest() {
		try {
			Socket s = new Socket(requestInfo.getHostName(), this.port);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw.println(requestInfo.getQueryMethod());
			pw.println(requestInfo.getQueryHost());
			pw.println(" ");
			pw.flush();
			
			String line = br.readLine();
			System.out.println(line);
			if (!(line.equals(requestInfo.getExpectedResponse()))) {
				throw new UnexpectedResponse("Unexpected Response");
			}
		} 
		catch (IOException e) {
		}
		catch (UnexpectedResponse e) {
			
		}
	}
	
	public RequestInfo getRequestInfo() {
		return this.requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
