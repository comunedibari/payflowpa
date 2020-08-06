package it.nch.is.fo.configurazionePagamento;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.is.fo.gestionePagamenti.IGatewayForm;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;



public class GatewayFormImpl extends BaseForm implements  IGatewayForm{

	private String gateway;
	private String deGateway;
	private String fornitore;
	private Timestamp dtAttivazione;
	private String flStato;
	private String commissione;
	private String stRiga;
	private Integer prVersione;
	private String opInserimento;
	private String tsInserimentoCommon;
	private String opAggiornamento;
	private String tsAggiornamentoCommon;
	private String [] selectedItems;
	
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getDeGateway() {
		return deGateway;
	}
	public void setDeGateway(String deGateway) {
		this.deGateway = deGateway;
	}
	public String getFornitore() {
		return fornitore;
	}
	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}
	public String getFlStato() {
		return flStato;
	}
	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}
	public String getCommissione() {
		return commissione;
	}
	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	public Integer getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(Integer prVersione) {
		this.prVersione = prVersione;
	}
	public String getOpInserimento() {
		return opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	public String[] getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	public Timestamp getDtAttivazione() {
		return dtAttivazione;
	}
	public void setDtAttivazione(Timestamp dtAttivazione) {
		this.dtAttivazione = dtAttivazione;
	}
	public String getTsInserimentoCommon() {
		return tsInserimentoCommon;
	}
	public void setTsInserimentoCommon(String tsInserimentoCommon) {
		this.tsInserimentoCommon = tsInserimentoCommon;
	}
	public String getTsAggiornamentoCommon() {
		return tsAggiornamentoCommon;
	}
	public void setTsAggiornamentoCommon(String tsAggiornamentoCommon) {
		this.tsAggiornamentoCommon = tsAggiornamentoCommon;
	}
	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Collection getConfPagamenti() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setConfPagamenti(Set confPagamenti) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public String getDtAttivazioneCommon() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDtAttivazioneCommon(String dtAttivazione) {
		// TODO Auto-generated method stub
		
	}
	

}
