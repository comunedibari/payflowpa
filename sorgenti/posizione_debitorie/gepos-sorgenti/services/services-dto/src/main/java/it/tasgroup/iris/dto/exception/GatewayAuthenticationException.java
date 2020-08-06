/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author pazzik
 *
 */
public class GatewayAuthenticationException extends I18NException{
	
	public GatewayAuthenticationException(String token, String totalAmount) {
		
		Object[] i18nMessageParameters = {token, totalAmount};
		
		this.i18NMessageParameters = i18nMessageParameters;
		
		this.errorCode = EnumBusinessErrorCodes.GTW_AUTH_KO;
	}
	
	public String getMessage(){
		
		return "Token non autenticato";
	}
}
