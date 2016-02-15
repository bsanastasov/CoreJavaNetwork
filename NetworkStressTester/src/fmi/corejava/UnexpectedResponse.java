package fmi.corejava;

public class UnexpectedResponse extends Exception {
	
	private static final long serialVersionUID = 1L;
	public UnexpectedResponse(String message) {
		super(message);
	}
}
