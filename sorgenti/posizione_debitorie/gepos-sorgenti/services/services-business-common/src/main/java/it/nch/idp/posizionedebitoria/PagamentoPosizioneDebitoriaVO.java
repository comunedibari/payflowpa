/*
 * Created on 5-giu-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.idp.posizionedebitoria;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PagamentoPosizioneDebitoriaVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4238374954190458226L;
	final static public String TIPO_SPONTANEO_MULTE = "Multa";
	final static public String TIPO_ATTESO = "PEND";
	
	private Integer idPagamento;
	private Date decorrenza;
	private Date tsInserimento;
	private String flusso;
	private String idPendenza;
	private String idCondizione;
	private String idTributo;
	private String idEnte;
	private String tiDebito;
	private String idTributoEnte;
	private String cdPlugin;
	private String laplPagante;
	private Date scadenza;
	private String tipoPagamento;
	private String stato;
	private BigDecimal importo;
	private List<?> dettaglioImporto;
	
	private String ente;
	private String tributo;
	private String tipoTributo;
	private String causale;
	private boolean isSpontaneo;
	private String tipoSpontaneo;
	private Object spontaneoVO;
	private Boolean flagIncasso;
	private String flagConsegnaRicevutaIdp;
	
	private String note;

	private Date dataValutaAccredito;
	
	private String idPendenzaEnte;
	private String codAziendaSanitariaEnte;
	private boolean associatedDocAvailable=true;
	
	private PagamentoDettaglioIncassoVO dettaglioIncasso;
	
	
	/**@deprecated 	spostato in DistintaCarcaCreditoVO*/
	private String mezzoDiPagamento;
	

	public Date getDataValutaAccredito() {
		return dataValutaAccredito;
	}

	public void setDataValutaAccredito(Date dataValutaAccredito) {
		this.dataValutaAccredito = dataValutaAccredito;
	}
    	
	public String getTiDebito() {
		return tiDebito;
	}

	public void setTiDebito(String tiDebito) {
		this.tiDebito = tiDebito;
	}

	public boolean isSpontaneo() {
		return isSpontaneo;
	}

	public void setSpontaneo(boolean isSpontaneo) {
		this.isSpontaneo = isSpontaneo;
	}

	/**
	 * @return
	 */
	public Date getDecorrenza() {
		return decorrenza;
	}

	/**
	 * @return
	 */
	public String getFlusso() {
		return flusso;
	}

	/**
	 * @return
	 */
	public String getIdCondizione() {
		return idCondizione;
	}

	/**
	 * @return
	 */
	public String getIdEnte() {
		return idEnte;
	}

	/**
	 * @return
	 */
	public Integer getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @return
	 */
	public String getIdPendenza() {
		return idPendenza;
	}

	/**
	 * @return
	 */
	public String getIdTributo() {
		return idTributo;
	}

	/**
	 * @return
	 */
	public BigDecimal getImporto() {
		return importo;
	}

	/**
	 * @return
	 */
	public String getLaplPagante() {
		return laplPagante;
	}

	/**
	 * @return
	 */
	public String getTipoPagamento() {
		return tipoPagamento;
	}

	/**
	 * @return
	 */
	public Date getScadenza() {
		return scadenza;
	}

	/**
	 * @return
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param date
	 */
	public void setDecorrenza(Date date) {
		decorrenza = date;
	}

	/**
	 * @param string
	 */
	public void setFlusso(String string) {
		flusso = string;
	}

	/**
	 * @param string
	 */
	public void setIdCondizione(String string) {
		idCondizione = string;
	}

	/**
	 * @param string
	 */
	public void setIdEnte(String string) {
		idEnte = string;
	}

	/**
	 * @param string
	 */
	public void setIdPagamento(Integer id) {
		idPagamento = id;
	}

	/**
	 * @param string
	 */
	public void setIdPendenza(String string) {
		idPendenza = string;
	}

	/**
	 * @param string
	 */
	public void setIdTributo(String string) {
		idTributo = string;
	}

	/**
	 * @param decimal
	 */
	public void setImporto(BigDecimal decimal) {
		importo = decimal;
	}

	/**
	 * @param string
	 */
	public void setLaplPagante(String string) {
		laplPagante = string;
	}

	/**
	 * @param string
	 */
	public void setTipoPagamento(String string) {
		tipoPagamento = string;
	}

	/**
	 * @param date
	 */
	public void setScadenza(Date date) {
		scadenza = date;
	}

	/**
	 * @param string
	 */
	public void setStato(String string) {
		stato = string;
	}

	/**
	 * @return
	 */
	public List<?> getDettaglioImporto() {
		return dettaglioImporto;
	}

	/**
	 * @param list
	 */
	public void setDettaglioImporto(List<?> list) {
		dettaglioImporto = list;
	}

	/**
	 * @return
	 */
	public String getCausale() {
		return causale;
	}

	/**
	 * @return
	 */
	public String getEnte() {
		return ente;
	}

	/**
	 * @return
	 */
	public String getIdTributoEnte() {
		return idTributoEnte;
	}

	/**
	 * @return
	 */
	public String getTipoTributo() {
		return tipoTributo;
	}

	/**
	 * @return
	 */
	public String getTributo() {
		return tributo;
	}

	/**
	 * @param string
	 */
	public void setCausale(String string) {
		causale = string;
	}

	/**
	 * @param string
	 */
	public void setEnte(String string) {
		ente = string;
	}

	/**
	 * @param string
	 */
	public void setIdTributoEnte(String string) {
		idTributoEnte = string;
	}

	/**
	 * @param string
	 */
	public void setTipoTributo(String string) {
		tipoTributo = string;
	}

	/**
	 * @param string
	 */
	public void setTributo(String string) {
		tributo = string;
	}

	/**
	 * @return
	 * @deprecated Spostato nella DistintaCartaCreditoVO
	 */
	public String getMezzoDiPagamento() {
		return mezzoDiPagamento;
	}

	/**
	 * @param string
	 * @deprecated Spostato nella DistintaCartaCreditoVO
	 */
	public void setMezzoDiPagamento(String string) {
		mezzoDiPagamento = string;
	}
	public String getTipoSpontaneo() {
		return tipoSpontaneo;
	}

	public void setTipoSpontaneo(String tipoSpontaneo) {
		this.tipoSpontaneo = tipoSpontaneo;
	}

	public Object getSpontaneoVO() {
		return spontaneoVO;
	}

	public void setSpontaneoVO(Object spontaneoVO) {
		this.spontaneoVO = spontaneoVO;
	}
    
    public Boolean getFlagIncasso() {
        return flagIncasso;
    }

    public void setFlagIncasso(Boolean flagIncasso) {
        this.flagIncasso = flagIncasso;
    }
    
    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    public String toString() {
		
		   StringBuffer sb = new StringBuffer();
		   sb.append("\n"+this.getClass()+"\n");
	       sb.append("[");
	       sb.append("Ente="+this.getEnte());
	       sb.append(", Tributo="+this.getTributo());
	       sb.append(", Importo="+this.getImporto());
	       sb.append(", scadenza="+this.getScadenza());	       	       
	       sb.append("]\n");
	       return sb.toString();
	    }

	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

	public String getCodAziendaSanitariaEnte() {
		return codAziendaSanitariaEnte;
	}

	public void setCodAziendaSanitariaEnte(String codAziendaSanitariaEnte) {
		this.codAziendaSanitariaEnte = codAziendaSanitariaEnte;
	}

	public boolean isAssociatedDocAvailable() {
		return associatedDocAvailable;
	}

	public void setAssociatedDocAvailable(boolean associatedDocAvailable) {
		this.associatedDocAvailable = associatedDocAvailable;
	}

	public Date getTsInserimento() {
		return tsInserimento;
	}

	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}

	public String getFlagConsegnaRicevutaIdp() {
		return flagConsegnaRicevutaIdp;
	}

	public void setFlagConsegnaRicevutaIdp(String flagConsegnaRicevutaIdp) {
		this.flagConsegnaRicevutaIdp = flagConsegnaRicevutaIdp;
	}
	
	public PagamentoDettaglioIncassoVO getDettaglioIncasso() {
		return dettaglioIncasso;
	}

	public void setDettaglioIncasso(PagamentoDettaglioIncassoVO dettaglioIncasso) {
		this.dettaglioIncasso = dettaglioIncasso;
	}
}
