package it.tasgroup.idp.sftp;

public class SftpClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public SftpClientException() {
		super();
	}

	public SftpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public SftpClientException(String message) {
		super(message);
	}

	public SftpClientException(Throwable cause) {
		super(cause);
	}
}
