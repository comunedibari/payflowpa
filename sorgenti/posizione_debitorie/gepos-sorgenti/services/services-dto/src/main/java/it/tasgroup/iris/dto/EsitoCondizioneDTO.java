/**
 * 
 */
package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.io.Serializable;

/**
 * @author pazzik
 *
 */
public class EsitoCondizioneDTO implements Serializable {
	
	private EnumBusinessReturnCodes returnCode = EnumBusinessReturnCodes.OK;	
	
	private CondizionePagamentoDTO condizione;

	public CondizionePagamentoDTO getCondizione() {
		return condizione;
	}

	public void setCondizione(CondizionePagamentoDTO condizione) {
		this.condizione = condizione;
	}
	
	public EnumBusinessReturnCodes getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(EnumBusinessReturnCodes returnCode) {
		this.returnCode = returnCode;
	}


}
