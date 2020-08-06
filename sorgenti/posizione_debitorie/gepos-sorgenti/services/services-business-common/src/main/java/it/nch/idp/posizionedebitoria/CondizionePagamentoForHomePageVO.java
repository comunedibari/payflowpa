package it.nch.idp.posizionedebitoria;

import java.util.Date;
import java.util.List;

public class CondizionePagamentoForHomePageVO extends CondizionePagamentoVO implements Comparable<CondizionePagamentoForHomePageVO>{
    
	private static final long serialVersionUID = 1L;

	// Property aggiunte per la visualizzazione in HP
    private String idDocumento;
    private Boolean flagErrore; 
    private Boolean flagInCarrello;
    private Integer flagPagamentoInDelega;
    private Boolean flagIsSpontaneo;
    private boolean cartellaPagamento;
    private String cfDestinatario;
    private String urlUpdService;
    private	String intestatarioDDP;
    private	String cfPaganteDDP;
    private		String cdPlugin;
    private String riscossore;
    private String riferimento;

    private String idDebitoEnte;
    private String idPagamentoEnte;
    private String note;
    private Date dataEmissione;
    private Date dataPrescrizione;
    private Integer annoRiferimento;
    private List<String> listaCfDestinatari;
    
//      pend.setCodiceCARTEnte("RegioneToscana");
//      pend.setCodiceSILEnte("SIL_RTOSCANA_ITR");

    
    
    
    
    public String getIdDocumento() {
        return idDocumento;
    }
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }
    public Boolean getFlagErrore() {
        return flagErrore;
    }
    public void setFlagErrore(Boolean flagErrore) {
        this.flagErrore = flagErrore;
    }
    public Boolean getFlagInCarrello() {
        return flagInCarrello;
    }
    public void setFlagInCarrello(Boolean flagInCarrello) {
        this.flagInCarrello = flagInCarrello;
    }
    public Integer getFlagPagamentoInDelega() {
        return flagPagamentoInDelega;
    }
    public void setFlagPagamentoInDelega(Integer flagPagamentoInDelega) {
        this.flagPagamentoInDelega = flagPagamentoInDelega;
    }
    
    public Boolean getFlagIsSpontaneo() {
        return flagIsSpontaneo;
    }
    public void setFlagIsSpontaneo(Boolean flagIsSpontaneo) {
        this.flagIsSpontaneo = flagIsSpontaneo;
    }
    
    public boolean isCartellaPagamento() {
		return cartellaPagamento;
	}
	public void setCartellaPagamento(boolean cartellaPagamento) {
		this.cartellaPagamento = cartellaPagamento;
	}
	
	@Override
    public int compareTo(CondizionePagamentoForHomePageVO o) {
		
        if((o.getDataScandenza() == null && this.getDataScandenza() == null) || this.getDataScandenza().equals(o.getDataScandenza()))
            return 0;
            
        if(this.getDataScandenza().after(o.getDataScandenza()))
            return 1;
        
         return -1;
        
    }
	public String getCfDestinatario() {
		
		return cfDestinatario;
		
	}
	public void setCfDestinatario(String cfDestinatario) {
		
		this.cfDestinatario = cfDestinatario;
		
	}
	
	public String getUrlUpdService() {
		return urlUpdService;
	}
	
	public void setUrlUpdService(String urlUpdService) {
		this.urlUpdService = urlUpdService;
	}
	
	public String getIntestatarioDDP() {
		return intestatarioDDP;
	}
	
	public void setIntestatarioDDP(String intestatarioDDP) {
		this.intestatarioDDP = intestatarioDDP;
	}
	public String getCfPaganteDDP() {
		return cfPaganteDDP;
	}
	public void setCfPaganteDDP(String cfPaganteDDP) {
		this.cfPaganteDDP = cfPaganteDDP;
	}
	public String getCdPlugin() {
		return cdPlugin;
	}
	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}
	public String getRiscossore() {
		return riscossore;
	}
	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}
	
	public String getRiferimento() {
		return riferimento;
	}
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	public String getIdDebitoEnte() {
		return idDebitoEnte;
	}
	public void setIdDebitoEnte(String idDebitoEnte) {
		this.idDebitoEnte = idDebitoEnte;
	}
	public String getIdPagamentoEnte() {
		return idPagamentoEnte;
	}
	public void setIdPagamentoEnte(String idPagamentoEnte) {
		this.idPagamentoEnte = idPagamentoEnte;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public Date getDataPrescrizione() {
		return dataPrescrizione;
	}
	public void setDataPrescrizione(Date dataPrescrizione) {
		this.dataPrescrizione = dataPrescrizione;
	}
	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}
	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	public List<String> getListaCfDestinatari() {
		return listaCfDestinatari;
	}
	public void setListaCfDestinatari(List<String> listaCfDestinatari) {
		this.listaCfDestinatari = listaCfDestinatari;
	}
	
}
