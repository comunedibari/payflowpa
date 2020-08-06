package it.nch.is.fo.gestionePagamenti;

import it.nch.fwk.fo.common.CommonBusinessObject;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public interface GatewayCommon extends CommonBusinessObject {

	public final static String GATEWAY_ATTIVO = "ATTIVO";
	public final static String GATEWAY_DISATTIVO = "DISATTIVO";
	public final static String GATEWAY_DA_ATTIVARE = "DA_ATTIVARE";
	public final static String TESTO_ATTIVO = "testonormale";
	public final static String TESTO_DISATTIVO = "testodisattivo";
	public final static String HIDE_BUTTONS = "HideButtonGatewayDisattivo";
	public final static String HIDE_BUTTONS_OK = "HideButtonGatewayDisattivoOK";
	public final static String IDX_SEPARATOR = "###$$$###";
	public final static String IDX_SEPARATOR_REGEX = "###\\$\\$\\$###";
	
	public abstract Collection getConfPagamenti();
	public abstract void setConfPagamenti(Set confPagamenti);
	public abstract String getGateway();

	public abstract void setGateway(String gateway);
	
	public abstract String getDeGateway();

	public abstract void setDeGateway(String deGateway);
	
	public abstract String getFornitore();

	public abstract void setFornitore(String fornitore);

	public String getDtAttivazioneCommon();

	public void setDtAttivazioneCommon(String dtAttivazione);
	
	public Timestamp getDtAttivazione();

	public void setDtAttivazione(Timestamp dtAttivazione);
	
	public String getFlStato();

	public void setFlStato(String flStato);

	public String getCommissione();

	public void setCommissione(String commissione);

	public String getStRiga();

	public void setStRiga(String stRiga);
	
	public Integer getPrVersione();
	
	public void setPrVersione(Integer prVersione);
	
	public String getOpInserimento();
	
	public void setOpInserimento(String opInserimento);
	
	public String getTsInserimentoCommon();

	public void setTsInserimentoCommon(String tsInserimento);
	
	public String getOpAggiornamento();
	
	public void setOpAggiornamento(String opAggiornamento);
	
	public String getTsAggiornamentoCommon();

	public void setTsAggiornamentoCommon(String tsAggiornamento);
	




}