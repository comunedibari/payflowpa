/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumErrorMessage;
import it.tasgroup.iris.shared.util.enumeration.EnumLayer;
import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;


/**
 * @author PazziK
 *
 */
public class I18NException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	protected Object[] i18NMessageParameters = null;
	
	private EnumLayer layer = null;
	
	protected EnumErrorMessage errorCode = null;
	
	protected I18NException(){
		
	}
	
	
	public I18NException(EnumErrorMessage errorCode, Object[] parameters, EnumLayer layer, String message, Throwable cause){
		
		super(message, cause);
		
		this.i18NMessageParameters = parameters;
		
		this.layer = layer;
		
		this.errorCode = errorCode;
				
	}
	
	
	public EnumSeverityLevel getSeverityLevel() {
		
		return errorCode.getSeverityLevel();
		
	}

	public String getI18NMessageKey() {
		return errorCode.getChiaveBundle();
	}
	
	public Object[] getI18NMessageParameters() {
		return i18NMessageParameters;
	}
	
	public EnumLayer getLayer() {
		return layer;
	}

	public void setLayer(EnumLayer layer) {
		this.layer = layer;
	}
	
	public EnumErrorMessage getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(EnumErrorMessage errorCode) {
		this.errorCode = errorCode;
	}


}
