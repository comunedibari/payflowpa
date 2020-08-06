package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.tasgroup.services.util.enumeration.EnumModalitaAnonima;
import it.tasgroup.services.util.enumeration.EnumStatoIncasso;
import it.tasgroup.services.util.enumeration.EnumTipoPredeterminato;

public class DistintePagamentoDTOLight implements Serializable {

    

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928206669755123806L;

	private Integer id;

    private String modalitaPagamento;
    private String fornitorePagamento;
    private Timestamp dataCreazione;
    private BigDecimal importo;
    private String stato;
    private String utenteCreatore;
    private BigDecimal importoCommissione;
    private String codTransazione;
    private String iuv;
    private String codPagamento;
    private Integer numDisposizioni;
    private Timestamp dataSpedizione;
    private Timestamp dataEsecuzione;
    private String idPendenza;
    private String idPagamento;
    private String flagIncasso;
    private Long idDocumentoRepository;
    private BigDecimal imPagato;
    private String statoPagamento;
    private String codFiscDebitore;
    private String luogoNascitaDeb;
    private String indirizzoDeb;
    private Timestamp dataNascitaDeb;
    private Timestamp dataRegolamento;
    private String TRN;
    private String idFlusso;
    private String denomEnte;
    private String desTributo;
    private Long idQuietanza;
    private String quietanzaMsg;
    private String opInserimento;
    private String modalitaAnonima;
    private String tipoSpontaneo;
    private String incasso;
    private String coPagante;
    
    private String causalePagamento;
    private String causalePagamentoFormattata = null;
    private String causalePagDescr;
    private String note;
    private Integer annoRif;
    
	private String codTransazionePSP;
	private String psp;
	private String nomePSP;
	
	private String subPSP;
	private String emailVersante;
	private String noteVersante;
	private String denominazioneDebitore;
	
	private String codFiscVersante;
	private boolean associatedDocAvailable=true;
	
	private String identificativoFiscaleCreditore;

	private String idRiscossionePSP;
	
	private Locale locale;
	
    private Long idCfgGatewayPagamento;	
    private Long idCfgFornitoreGatewayPagamento;
    private Long idDistintaPagamento;	
    private BigDecimal importoDovuto;
    private String causaleVersamento;
    private String vociImporto;
    //NUOVI
    private String soluzioneDiPagamento;
    private String statoPosizione;
    private Timestamp dataScadenzaPagamento;
    private String canalePagamento;
    private String notePagamento;  
    private String autenticazioneVersante;
    private String tipoIncasso;
    private String idMittente;
    private String deMittente;
	private String coRiscossore;
    private String riferimento;
    private Date dataRegistrazionePagamento;
    private BigDecimal importoDebito;

	    
    private String identificativoAttestante;
    private String tipoIdentificativoAttestante;
    private String descrIstitutoAttestante;
    
    private String circuito;

	private String attestante;
	
	private boolean RTAvailable;
    private boolean RPTAvailable;
    
	private boolean docRRAvailable;
	private boolean docERAvailable;
	
	private List<RevocaPagamentoDTO> revochePagamentoDTO = new ArrayList<RevocaPagamentoDTO>(); 
	
	private Integer numeroRevoche;
	private Integer numeroRevocheDaValutare;
	   
	public Integer getId() { 
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModalitaPagamento() {
        return modalitaPagamento;
    }

    public String getFornitorePagamento() {
		return fornitorePagamento;
	}

	public void setFornitorePagamento(String fornitorePagamento) {
		this.fornitorePagamento = fornitorePagamento;
	}

	public String getUtenteCreatore() {
        return utenteCreatore;
    }

    public void setUtenteCreatore(String utenteCreatore) {
        this.utenteCreatore = utenteCreatore;
    }

    public BigDecimal getImportoCommissione() {
        return importoCommissione;
    }

    public void setImportoCommissione(BigDecimal importoCommissione) {
        this.importoCommissione = importoCommissione;
    }

    public String getCodTransazione() {
        return codTransazione;
    }

    public void setCodTransazione(String codTransazione) {
        this.codTransazione = codTransazione;
    }

    public void setModalitaPagamento(String modalitaPagamento) {
        this.modalitaPagamento = modalitaPagamento;
    }

    public Timestamp getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Integer getNumDisposizioni() {
        return numDisposizioni;
    }

    public void setNumDisposizioni(Integer numDisposizioni) {
        this.numDisposizioni = numDisposizioni;
    }

    public Timestamp getDataSpedizione() {
        return dataSpedizione;
    }

    public void setDataSpedizione(Timestamp dataSpedizione) {
        this.dataSpedizione = dataSpedizione;
    }

    public String getIdPendenza() {
        return idPendenza;
    }

    public void setIdPendenza(String idPendenza) {
        this.idPendenza = idPendenza;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(String idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getFlagIncasso() {
        return flagIncasso;
    }

    public void setFlagIncasso(String flagIncasso) {
        this.flagIncasso = flagIncasso;
    }
    
    public String getIncasso(){
        if(this.incasso == null && getFlagIncasso() != null){
            EnumStatoIncasso incasso = EnumStatoIncasso.getByKey(getFlagIncasso());
            setIncasso(incasso.getDescrizione());
        } 
        
        return this.incasso;
    }
    
    public void setIncasso(String incasso){
        this.incasso = incasso;
    }
    
    

    public String getCausalePagamento() {
        return causalePagamento;
    }

    public void setCausalePagamento(String causalePagamento) {
        this.causalePagamento = causalePagamento;
    }

	public String getCausalePagDescr() {
		return causalePagDescr;
	}

	public void setCausalePagDescr(String causalePagDescr) {
		this.causalePagDescr = causalePagDescr;
	}

	public Long getIdDocumentoRepository() {
        return idDocumentoRepository;
    }

    public void setIdDocumentoRepository(Long idDocumentoRepository) {
        this.idDocumentoRepository = idDocumentoRepository;
    }

    public BigDecimal getImPagato() {
        return imPagato;
    }

    public void setImPagato(BigDecimal imPagato) {
        this.imPagato = imPagato;
    }

    public String getStatoPagamento() {
        return statoPagamento;
    }

    public void setStatoPagamento(String statoPagamento) {
        this.statoPagamento = statoPagamento;
    }

    public String getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }

    public String getCodFiscDebitore() {
        return codFiscDebitore;
    }

    public void setCodFiscDebitore(String codFiscDebitore) {
        this.codFiscDebitore = codFiscDebitore;
    }

    public String getDenominazioneDebitore() {
		return denominazioneDebitore;
	}

	public void setDenominazioneDebitore(String denominazioneDebitore) {
		this.denominazioneDebitore = denominazioneDebitore;
	}

	public String getDenomEnte() {
        return denomEnte;
    }

    public void setDenomEnte(String denomEnte) {
        this.denomEnte = denomEnte;
    }

    public String getDesTributo() {
        return desTributo;
    }

    public void setDesTributo(String desTributo) {
        this.desTributo = desTributo;
    }

    public Long getIdQuietanza() {
        return idQuietanza;
    }

    public void setIdQuietanza(Long idQuietanza) {
        this.idQuietanza = idQuietanza;
    }

    public String getQuietanzaMsg() {
		return quietanzaMsg;
	}

	public void setQuietanzaMsg(String quietanzaMsg) {
		this.quietanzaMsg = quietanzaMsg;
	}

	public String getOpInserimento() {
        return opInserimento;
    }

    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }

    public String getCoPagante() {
		return coPagante;
	}

	public void setCoPagante(String coPagante) {
		this.coPagante = coPagante;
	}

	public String getModalitaAnonima() {
        if(getOpInserimento() != null && EnumModalitaAnonima.ANONIMO.getChiave().equalsIgnoreCase(getOpInserimento().trim())){
            setModalitaAnonima(EnumModalitaAnonima.ANONIMO.getDescrizione());
        } else {
            setModalitaAnonima(EnumModalitaAnonima.CNS.getDescrizione());
        }
            
        return modalitaAnonima;
    }

    public void setModalitaAnonima(String modalitaAnonima) {
        this.modalitaAnonima = modalitaAnonima;
    }

    public String getTipoSpontaneo() {
    	
        if(tipoSpontaneo != null && EnumTipoPredeterminato.NON_PREDETERMINATO.getChiave().equalsIgnoreCase(tipoSpontaneo.trim()))          
        	setTipoSpontaneo(EnumTipoPredeterminato.NON_PREDETERMINATO.getDescrizione());       	
        else        	
            setTipoSpontaneo(EnumTipoPredeterminato.PREDEDERMINATO.getDescrizione());
        
        return tipoSpontaneo;
    }

    public void setTipoSpontaneo(String tipoSpontaneo) {
        this.tipoSpontaneo = tipoSpontaneo;
    }

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getAnnoRif() {
		return annoRif;
	}

	public void setAnnoRif(Integer annoRif) {
		this.annoRif = annoRif;
	}

	public String getCodTransazionePSP() {
		return codTransazionePSP;
	}

	public void setCodTransazionePSP(String codTransazionePSP) {
		this.codTransazionePSP = codTransazionePSP;
	}

	public String getVociImporto() {
		return vociImporto;
	}

	public void setVociImporto(String vociImporto) {
		this.vociImporto = vociImporto;
	}

	public String getNomePSP() {
		return nomePSP;
	}

	public void setNomePSP(String nomePSP) {
		this.nomePSP = nomePSP;
	}
	
	public String getSubPSP() {
		return subPSP;
	}

	public String getCausalePagamentoFormattata() {
		return causalePagamentoFormattata;
	}

	public void setCausalePagamentoFormattata(String causalePagamentoFormattata) {
		this.causalePagamentoFormattata = causalePagamentoFormattata;
	}

	public void setSubPSP(String subPSP) {
		this.subPSP = subPSP;
	}

	public String getPsp() {
		return psp;
	}

	public void setPsp(String psp) {
		this.psp = psp;
	}

	public String getEmailVersante() {
		return emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}

	public BigDecimal getImportoDovuto() {
		return importoDovuto;
	}

	public void setImportoDovuto(BigDecimal importoDovuto) {
		this.importoDovuto = importoDovuto;
	}

	public String getNoteVersante() {
		return noteVersante;
	}

	public void setNoteVersante(String noteVersante) {
		this.noteVersante = noteVersante;
	}

	public String getCodFiscVersante() {
		return codFiscVersante;
	}

	public void setCodFiscVersante(String codFiscVersante) {
		this.codFiscVersante = codFiscVersante;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	
	public String getIuvToDisplay() {
		if (idCfgFornitoreGatewayPagamento.equals(Long.valueOf(4)))
			return iuv;
		else 
			return "-";
	}

	public String getLuogoNascitaDeb() {
		return luogoNascitaDeb;
	}

	public void setLuogoNascitaDeb(String luogoNascitaDeb) {
		this.luogoNascitaDeb = luogoNascitaDeb;
	}

	public String getIndirizzoDeb() {
		return indirizzoDeb;
	}

	public void setIndirizzoDeb(String indirizzoDeb) {
		this.indirizzoDeb = indirizzoDeb;
	}

	public Timestamp getDataNascitaDeb() {
		return dataNascitaDeb;
	}

	public void setDataNascitaDeb(Timestamp dataNascitaDeb) {
		this.dataNascitaDeb = dataNascitaDeb;
	}


	public boolean isAssociatedDocAvailable() {
		return associatedDocAvailable;
	}

	public void setAssociatedDocAvailable(boolean associatedDocAvailable) {
		this.associatedDocAvailable = associatedDocAvailable;
	}


	public Timestamp getDataRegolamento() {
		return dataRegolamento;
	}

	public void setDataRegolamento(Timestamp dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}

	public String getTRN() {
		return TRN;
	}

	public void setTRN(String tRN) {
		TRN = tRN;
	}

	public Long getIdCfgFornitoreGatewayPagamento() {
		return idCfgFornitoreGatewayPagamento;
	}

	public void setIdCfgFornitoreGatewayPagamento(Long idCfgFornitoreGatewayPagamento) {
		this.idCfgFornitoreGatewayPagamento = idCfgFornitoreGatewayPagamento;
	}

	public String getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}

	public Timestamp getDataEsecuzione() {
		return dataEsecuzione;
	}

	public void setDataEsecuzione(Timestamp dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	public Long getIdCfgGatewayPagamento() {
		return idCfgGatewayPagamento;
	}

	public void setIdCfgGatewayPagamento(Long idCfgGatewayPagamento) {
		this.idCfgGatewayPagamento = idCfgGatewayPagamento;
	}
	
	public Long getIdDistintaPagamento() {
		return idDistintaPagamento;
	}

	public void setIdDistintaPagamento(Long idDistintaPagamento) {
		this.idDistintaPagamento = idDistintaPagamento;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String getIdRiscossionePSP() {
		return idRiscossionePSP;
	}

	public void setIdRiscossionePSP(String idRiscossionePSP) {
		this.idRiscossionePSP = idRiscossionePSP;
	}

	public String getIdentificativoFiscaleCreditore() {
		return identificativoFiscaleCreditore;
	}

	public void setIdentificativoFiscaleCreditore(String identificativoFiscaleCreditore) {
		this.identificativoFiscaleCreditore = identificativoFiscaleCreditore;
	}

	public String getCausaleVersamento() {
		return causaleVersamento;
	}

	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
	}

	public String getSoluzioneDiPagamento() {
		return soluzioneDiPagamento;
	}

	public void setSoluzioneDiPagamento(String soluzioneDiPagamento) {
		this.soluzioneDiPagamento = soluzioneDiPagamento;
	}

	public String getStatoPosizione() {
		return statoPosizione;
	}

	public void setStatoPosizione(String statoPosizione) {
		this.statoPosizione = statoPosizione;
	}

	public Timestamp getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}

	public void setDataScadenzaPagamento(Timestamp dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}

	public String getCanalePagamento() {
		return canalePagamento;
	}

	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}

	public String getNotePagamento() {
		return notePagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	public String getAutenticazioneVersante() {
		return autenticazioneVersante;
	}

	public void setAutenticazioneVersante(String autenticazioneVersante) {
		this.autenticazioneVersante = autenticazioneVersante;
	}

	public String getTipoIncasso() {
		return tipoIncasso;
	}

	public void setTipoIncasso(String tipoIncasso) {
		this.tipoIncasso = tipoIncasso;
	}

    public String getIdMittente() {
		return idMittente;
	}

	public void setIdMittente(String idMittente) {
		this.idMittente = idMittente;
	}

	public String getDeMittente() {
		return deMittente;
	}

	public void setDeMittente(String deMittente) {
		this.deMittente = deMittente;
	}

	public String getCoRiscossore() {
		return coRiscossore;
	}

	public void setCoRiscossore(String coRiscossore) {
		this.coRiscossore = coRiscossore;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
    public Date getDataRegistrazionePagamento() {
        return dataRegistrazionePagamento;
    }

    public void setDataRegistrazionePagamento(Date dataRegistrazionePagamento) {
        this.dataRegistrazionePagamento = dataRegistrazionePagamento;
    }

	public BigDecimal getImportoDebito() {
		return importoDebito;
	}

	public void setImportoDebito(BigDecimal importoDebito) {
		this.importoDebito = importoDebito;
	}

	public String getDescrIstitutoAttestante() {
		return descrIstitutoAttestante;
	}

	public void setDescrIstitutoAttestante(String descrIstitutoAttestante) {
		this.descrIstitutoAttestante = descrIstitutoAttestante;
	}

	public String getIdentificativoAttestante() {
		return identificativoAttestante;
	}

	public void setIdentificativoAttestante(String identificativoAttestante) {
		this.identificativoAttestante = identificativoAttestante;
	}

	public String getTipoIdentificativoAttestante() {
		return tipoIdentificativoAttestante;
	}

	public void setTipoIdentificativoAttestante(String tipoIdentificativoAttestante) {
		this.tipoIdentificativoAttestante = tipoIdentificativoAttestante;
	}
	
	public String getAttestante() {
		  String tipoId=tipoIdentificativoAttestante;
		    if (tipoIdentificativoAttestante!=null){ 
		    	if ("G".equals(tipoIdentificativoAttestante)) {
		    		tipoId = "C.F.";
		    	}
		    	if ("A".equals(tipoIdentificativoAttestante)) {
		    		tipoId = "ABI";
		    	}
		    	if ("B".equals(tipoIdentificativoAttestante)) {
		    		tipoId = "BIC";
		    	}
		    }
		String res = "";
		
		if (descrIstitutoAttestante != null && !descrIstitutoAttestante.isEmpty())
			res = descrIstitutoAttestante + " (" + 
					tipoId + " " + 
				identificativoAttestante + ")";
		return res;
	}

	public void setAttestante(String identificativoAttestante) {
		//do nothing
	}
	
    
    public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	
    public List<RevocaPagamentoDTO> getRevochePagamentoDTO() {
		return revochePagamentoDTO;
	}

	public void setRevochePagamentoDTO(List<RevocaPagamentoDTO> revochePagamentoDTO) {
		this.revochePagamentoDTO = revochePagamentoDTO;
	}
	
	public void addRevocaPagamentoDTO(RevocaPagamentoDTO revocaPagamentoDTO) {
		revochePagamentoDTO.add(revocaPagamentoDTO);
	}

	public Integer getNumeroRevoche() {
		return numeroRevoche;
	}

	public void setNumeroRevoche(Integer numeroRevoche) {
		this.numeroRevoche = numeroRevoche;
	}

	public Integer getNumeroRevocheDaValutare() {
		return numeroRevocheDaValutare;
	}

	public void setNumeroRevocheDaValutare(Integer numeroRevocheDaValutare) {
		this.numeroRevocheDaValutare = numeroRevocheDaValutare;
	}

	public boolean isDocRRAvailable() {
		return docRRAvailable;
	}

	public void setDocRRAvailable(boolean docRRAvailable) {
		this.docRRAvailable = docRRAvailable;
	}

	public boolean isDocERAvailable() {
		return docERAvailable;
	}

	public void setDocERAvailable(boolean docERAvailable) {
		this.docERAvailable = docERAvailable;
	}

	public boolean isRTAvailable() {
		return RTAvailable;
	}

	public void setRTAvailable(boolean rTAvailable) {
		RTAvailable = rTAvailable;
	}

	public boolean isRPTAvailable() {
		return RPTAvailable;
	}

	public void setRPTAvailable(boolean rPTAvailable) {
		RPTAvailable = rPTAvailable;
	}
	
	
}
