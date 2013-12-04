package ee.ut.domain;

@SuppressWarnings("serial")
public class EmailNotSetException extends Exception {
	public EmailNotSetException(String message) {
		super(message);
	}
}
