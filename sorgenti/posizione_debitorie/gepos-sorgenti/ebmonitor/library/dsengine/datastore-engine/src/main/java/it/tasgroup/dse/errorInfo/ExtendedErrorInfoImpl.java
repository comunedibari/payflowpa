package it.tasgroup.dse.errorInfo;

import it.nch.eb.common.utils.StringUtils;
import it.nch.fwk.checks.errors.ErrorInfo;
import it.nch.fwk.checks.errors.ErrorInfoImpl;
import it.nch.fwk.checks.errors.ParserErrorInfo;
import it.nch.fwk.checks.errors.extras.ExtendedErrorInfo;
import it.nch.fwk.xml.validation.SchemaElement;

/**
 * @author FG@2007
 */
public class ExtendedErrorInfoImpl extends ErrorInfoImpl implements ExtendedErrorInfo {

	private static final long	serialVersionUID	= -365833471012680013L;
	protected String    	errorDetail;    // validation specific
	private String			namespaceUri;
	private String			path;
	private String			value;
	private String			name;
	
	public ExtendedErrorInfoImpl(String errorId, 
			int errorType, 
			String severity, 
			String elementNamespace, 
			String elementName, 
			String elementPath, 
			String elementValue, 
			String expectedValue,
			String internalError) { 
			
		super(errorId, errorType, expectedValue, severity);
		this.errorDetail = internalError;
		this.name = elementName;
		this.path = elementPath;
		this.value = elementValue;
		this.namespaceUri = elementNamespace;
	}
	
	public ExtendedErrorInfoImpl(ParserErrorInfo parserErrorInfo, SchemaElement currentElement2) {
		super(parserErrorInfo.getErrorId(), parserErrorInfo.getErrorType(), ErrorInfo.NO_EXPECETED_VALUE, parserErrorInfo.getSeverity());

		this.namespaceUri = (currentElement2==null) ? "NULL" : currentElement2.getUri();
		this.errorDetail = parserErrorInfo.getOriginalErrorMessage();
		this.value = (currentElement2==null) ? "NULL" : currentElement2.getValue();
		this.path = (currentElement2==null) ? "NULL" : currentElement2.getXPath();
		this.name = (currentElement2==null) ? "NULL" : currentElement2.getQualifiedName();
		this.message = parserErrorInfo.getErrorMessage();
	}
	
	public String getErrorDetail() {
		return this.errorDetail;
	}
	
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public String getName() {
		return name;
	}

	public String getNameSpaceURI() {
		return namespaceUri;
	}

	public String getPath() {
		return path;
	}

	public String getValue() {
		return value;
	}
	
	protected void describeField(StringBuffer sb, String label, Object value) {
		if (value != null) {
			sb.append(label);
			sb.append(value.toString());
		}
			
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		describeField(sb, "ErrorId: ", getErrorId());
		describeField(sb, "\nErrorType: ", String.valueOf(getErrorType()));
		describeField(sb, "\nErrorSeverity: ", getSeverity());
		describeField(sb, "\nErrorMessage: ", getMessage());
		describeField(sb, "\nErrorDetail: ", getErrorDetail());
		describeField(sb, "\nElementURI: ", getNameSpaceURI());
		describeField(sb, "\nElementPath: ", getPath());
		describeField(sb, "\nElementName: ", getName());
		describeField(sb, "\nElementValue: ", getValue());
		describeField(sb, "\nExpectedValue: ", getExpectedValue());			
		return sb.toString();
	}
	
	public String toXml(){
		return
			StringUtils.xmlWrap("ErrorId",this.getErrorId())+
			StringUtils.xmlWrap("ErrorType",String.valueOf(this.getErrorType()))+
			StringUtils.xmlWrap("ErrorSeverity",this.getSeverity())+
			StringUtils.xmlWrap("ErrorMessage",this.getMessage())+
			StringUtils.xmlWrap("ErrorDetail",this.getErrorDetail())+
			StringUtils.xmlWrap("ElementURI",this.getNameSpaceURI())+
			StringUtils.xmlWrap("ElementPath",this.getPath())+
			StringUtils.xmlWrap("ElementName",this.getName())+
			StringUtils.xmlWrap("ElementValue",this.getValue())+
			StringUtils.xmlWrap("ExpectedValue",this.getExpectedValue());			
	}
}
