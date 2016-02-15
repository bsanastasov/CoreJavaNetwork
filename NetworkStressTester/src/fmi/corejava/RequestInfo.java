package fmi.corejava;

	import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;

	public class RequestInfo {
		private String queryMethod;
		private String queryHost;
		private String hostName;
		private static final String expectedResponse = "HTTP/1.1 200 OK";

		public RequestInfo(File requestFile) {

			try  {
				
				BufferedReader requestReader = new BufferedReader(new FileReader(requestFile));
				this.queryMethod = requestReader.readLine();
				this.queryHost = requestReader.readLine();
				String[] splitted = queryHost.split("\\s+");
				this.hostName = splitted[1];

			} 
			catch (FileNotFoundException e) {
				
			}
			catch (IOException e) {
				
			}
		}

		public String getQueryMethod() {
			return queryMethod;
		}

		public void setQueryMethod(String queryMethod) {
			this.queryMethod = queryMethod;
		}

		public String getQueryHost() {
			return queryHost;
		}

		public void setQueryHost(String queryHost) {
			this.queryHost = queryHost;
		}

		public String getExpectedResponse() {
			return expectedResponse;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

}
