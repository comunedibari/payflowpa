/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

/**
 * @author PazziK
 *
 */
public class DDPMultiplicityViolationException extends BusinessConstraintException {
	

	public DDPMultiplicityViolationException(EnumTipoDDP tipoDDP, Integer numCondizioniSelezionate, Integer molteplicitaConfigurata) {
		
		Object[] i18nMessageParameters = {tipoDDP.getDescrizione(), molteplicitaConfigurata, numCondizioniSelezionate};
		
		this.errorCode = EnumBusinessErrorCodes.APPEXC_MOLTDDP;
		this.i18NMessageParameters = i18nMessageParameters;
	}
	
	public String getMessage(){
		
		return "Impossibile creare un documento di tipo "+ this.i18NMessageParameters[0] +" con "+ this.i18NMessageParameters[2]+" condizioni: molteplicita' massima consentita " + this.i18NMessageParameters[1];
	}

}
