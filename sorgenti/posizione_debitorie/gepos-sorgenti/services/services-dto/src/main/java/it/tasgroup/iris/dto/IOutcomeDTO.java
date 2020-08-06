/**
 * 
 */
package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumModRiversamento;

import java.util.List;

/**
 * @author pazzik
 *
 */
public interface IOutcomeDTO {
	
	public EnumBusinessReturnCodes getReturnCode();

	public void setReturnCode(EnumBusinessReturnCodes returrCode);
	
	public String getMessageCode();

	public String getMessageDescription();
	
	public List<? extends IPendenzaDTO> getIPendenzeDTO();

	public String getLogMessage();
	
	public EnumModRiversamento getEnumModRiversamento();
	
	public void setEnumModRiversamento(EnumModRiversamento flag);
	
	public String getFlagRendRiversamento();

	public void setFlagRendRiversamento(String flagRendRiversamento);
	
	public String getImportoCommissioni();
	
	public void setImportoCommissioni(String importoCommissioni);

}
