package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumModRiversamento;

import java.util.ArrayList;
import java.util.List;

public class EstrattoContoDebitorioOutputDTO implements IOutcomeDTO{
	
	private EnumBusinessReturnCodes returnCode = EnumBusinessReturnCodes.OK;
	
	private String logMessage = null;
	
	private String messageDescription = null;
	
	private boolean extractInProgressPayments = false;
	
	private List<PendenzaEstrattoContoDebitorioDTO> pendenze=new ArrayList<PendenzaEstrattoContoDebitorioDTO>();
	
	private EnumModRiversamento enumModRiversamento;
	
	private String flagRendRiversamento;
	
	public String getMessageCode() {
		return returnCode.getChiave();
	}
	
	public String getMessageDescription() {
		
		if (messageDescription!= null)
			return messageDescription;
		
		return returnCode.getDescrizione();
	}
	
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	
	public List<PendenzaEstrattoContoDebitorioDTO> getPendenze() {
		return pendenze;
	}
	public void setPendenze(List<PendenzaEstrattoContoDebitorioDTO> pendenze) {
		this.pendenze = pendenze;
	}
	public void addPendenza(PendenzaEstrattoContoDebitorioDTO pend) {
		pendenze.add(pend);
	}

	public EnumBusinessReturnCodes getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(EnumBusinessReturnCodes returnCode) {
		this.returnCode = returnCode;
	}

	@Override
	public List<? extends IPendenzaDTO> getIPendenzeDTO() {
		
		return pendenze;
	}

	@Override
	public String getLogMessage() {
		
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public boolean isExtractInProgressPayments() {
		return extractInProgressPayments;
	}

	public void setExtractInProgressPayments(boolean extractInProgressPayments) {
		this.extractInProgressPayments = extractInProgressPayments;
	}

	@Override
	public EnumModRiversamento getEnumModRiversamento() {
		
		return enumModRiversamento;
	}

	@Override
	public void setEnumModRiversamento(EnumModRiversamento flag) {
		
		this.enumModRiversamento = flag;
		
	}

	@Override
	public String getFlagRendRiversamento() {
		return flagRendRiversamento;
	}
	@Override
	public void setFlagRendRiversamento(String flagRendRiversamento) {
		this.flagRendRiversamento = flagRendRiversamento;
	}

	@Override
	public String getImportoCommissioni() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setImportoCommissioni(String importoCommissioni) {
		// TODO Auto-generated method stub
		
	}
		
}
