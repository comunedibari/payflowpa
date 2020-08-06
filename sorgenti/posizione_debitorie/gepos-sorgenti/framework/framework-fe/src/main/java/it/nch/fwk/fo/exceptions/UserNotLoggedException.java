package it.nch.fwk.fo.exceptions;

public class UserNotLoggedException extends FrontEndException{

	private static final long	serialVersionUID	= 3406931075765229663L;

	public UserNotLoggedException(String strErrorCode){
		super(strErrorCode);
	}
	public UserNotLoggedException(String strErrorCode, String strErrorMessage,String strModule){
		super(strErrorCode, strErrorMessage, strModule);
	}

}
