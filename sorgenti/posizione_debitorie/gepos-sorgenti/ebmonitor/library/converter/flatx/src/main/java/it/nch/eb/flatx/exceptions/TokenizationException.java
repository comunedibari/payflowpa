/**
 * Created on 07/ago/07
 */
package it.nch.eb.flatx.exceptions;

/**
 * @author gdefacci
 */
public class TokenizationException extends ParserException {

//	public TokenizationException() {
//		super();
//	}

	private static final long	serialVersionUID	= -4801433571911308435L;

	public TokenizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenizationException(String message) {
		super(message);
	}

	public TokenizationException(Throwable cause) {
		super(cause);
	}
	
	private String 	token;
	private int		startOfToken = -1;
	private String 	fullLine;
	private int		lineNumber = -1;
	private int 	expecetedSize = -1;
	private String 	tokenName;
	private String 	tokenValue;
	
	public TokenizationException(String token, int startOfToken, int expecetedSize, String fullLine) {
		super("tokenization error");
		this.token = token;
		this.startOfToken = startOfToken;
		this.fullLine = fullLine;
		this.expecetedSize = expecetedSize;
	}
	
	public TokenizationException(String msg, String token, int startOfToken, int expecetedSize, String fullLine) {
		super("tokenization error " + msg);
		this.token = token;
		this.startOfToken = startOfToken;
		this.fullLine = fullLine;
		this.expecetedSize = expecetedSize;
	}
	
	public TokenizationException(TokenizationException ex) {
		super(ex);
		this.token = ex.token;
		this.startOfToken = ex.startOfToken;
		this.fullLine = ex.fullLine;
		this.lineNumber = ex.lineNumber;
		this.expecetedSize = ex.expecetedSize;
	}

	public TokenizationException(Exception e, int lineNumber2, String content) {
		super("Exception happened on line number [" + lineNumber2 + "] for line [" + content + "]" , e);
		this.lineNumber = lineNumber2;
		this.fullLine = content;
	}

	public String getToken() {
		return token;
	}

	public int getStartOfToken() {
		return startOfToken;
	}

	public String getFullLine() {
		return fullLine;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getExpecetedSize() {
		return expecetedSize;
	}
	
	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	
	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public void setFullLine(String fullLine) {
		this.fullLine = fullLine;
	}

	public String getMessage() {
		String msg = super.getMessage() + 
			(token!=null?"\ninvalid token	[" + token + "]" :"")           
			+ (startOfToken>-1?"\nstartOfToken [" + startOfToken+"]":"")  
			+ (expecetedSize>-1?"\nexpecetedSize [" + expecetedSize + "]" 
			+ (token!=null?"\nreal token size[" + token.length() + "]":"" ):"") 
			+ (tokenName!=null?"\ntokenName [" + tokenName+ "]":"") 
			+ (tokenValue!=null?"\ntokenValue [" + tokenValue+ "]":"")
			+ "\nfullLine \n[" + fullLine + "]";
		return msg;
	}

	
}
