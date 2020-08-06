package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class DettaglioEsitiDTOLight implements Serializable {
    
    private Long id;
    private Integer idBozzeBonificiRiaccredito;
    private Object idRiconciliazione;
    private Long idBonificiRiaccredito;
    
    // Only EsitiBb
    private String abiEsattrice;
    private String cabEsattrice;
    private String codeline;
    
    // Only Rid
    private String causale;
    private Integer dataScadenzaOrig;
    private String siaCreditore;
    private String codDebitore;
    private String tipoCodIndividuale;
    private String abiBancaAssuntrice;
    private String cabBancaAssuntrice;
    private String ibanOrdinante;
    private String codRiferimento;
    private String causaleStorno;
    private String flagStorno;
    
    // Only Aea
    private Timestamp dataCreazioneOrig;
    private String siaCreditore_aea;
    private String codIndividuale;
    private String tipoCodIndividuale_aea;
    private String abiAddebito;
    private String cabAddebito;
    private String numeroCcAddebito;
    private String codPaeseAddebito;
    private String checkDigitAddebito;
    private String cinAddebito;
    private String codRiferimento_aea;
    
    // Only EsitiBonificiRiaccredito
    private Timestamp dataEsecuzione;
    private Timestamp dataContabileAddebito;
    private Timestamp dataValutaBeneficiario;
    
    // Only EsitiCbill
    private String canale;
    private String codiceBiller;
    private String idVersamento;
    private String idDebito;
    private BigDecimal importoDebito;
    private BigDecimal importoTotale;
    private BigDecimal commissioniBiller;
    private BigDecimal commissioniBanca;
    private String ibanAccredito;
    private String bancaOrdinante;
    private String bancaBeneficiario;
    private String codTransazionePSP;
    private String dettagliAtm;
    private Integer flagRiconciliazione;
    
    //Only EsitiNdp
    public String importoConSegno;
    private String idRiscossione;
    private String esitoPagamento;
    
    public String getImportoConSegno() {
        return importoConSegno;
    }
    
    public void setImportoConSegno(String importoConSegno) {
        this.importoConSegno = importoConSegno;
    }
    
    public String getIdRiscossione() {
        return idRiscossione;
    }
    
    public void setIdRiscossione(String idRiscossione) {
        this.idRiscossione = idRiscossione;
    }
    
    public String getEsitoPagamento() {
        return esitoPagamento;
    }
    
    public void setEsitoPagamento(String esitoPagamento) {
        this.esitoPagamento = esitoPagamento;
    }
    
    public Timestamp getDataEsecuzione() {
        return dataEsecuzione;
    }
    public void setDataEsecuzione(Timestamp dataEsecuzione) {
        this.dataEsecuzione = dataEsecuzione;
    }
    public Timestamp getDataContabileAddebito() {
        return dataContabileAddebito;
    }
    public void setDataContabileAddebito(Timestamp dataContabileAddebito) {
        this.dataContabileAddebito = dataContabileAddebito;
    }
    public Timestamp getDataValutaBeneficiario() {
        return dataValutaBeneficiario;
    }
    public void setDataValutaBeneficiario(Timestamp dataValutaBeneficiario) {
        this.dataValutaBeneficiario = dataValutaBeneficiario;
    }
    public String getTipoCodIndividuale_aea() {
        return tipoCodIndividuale_aea;
    }
    public void setTipoCodIndividuale_aea(String tipoCodIndividuale_aea) {
        this.tipoCodIndividuale_aea = tipoCodIndividuale_aea;
    }
    public String getCodRiferimento_aea() {
        return codRiferimento_aea;
    }
    public void setCodRiferimento_aea(String codRiferimento_aea) {
        this.codRiferimento_aea = codRiferimento_aea;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAbiEsattrice() {
        return abiEsattrice;
    }
    public void setAbiEsattrice(String abiEsattrice) {
        this.abiEsattrice = abiEsattrice;
    }
    public String getCabEsattrice() {
        return cabEsattrice;
    }
    public void setCabEsattrice(String cabEsattrice) {
        this.cabEsattrice = cabEsattrice;
    }
    public String getCodeline() {
        return codeline;
    }
    public void setCodeline(String codeline) {
        this.codeline = codeline;
    }
    public Integer getIdBozzeBonificiRiaccredito() {
        return idBozzeBonificiRiaccredito;
    }
    public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
        this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
    }
    public Object getIdRiconciliazione() {
        return idRiconciliazione;
    }
    public void setIdRiconciliazione(Object idRiconciliazione) {
        this.idRiconciliazione = idRiconciliazione;
    }
    public Integer getDataScadenzaOrig() {
        return dataScadenzaOrig;
    }
    public void setDataScadenzaOrig(Integer dataScadenzaOrig) {
        this.dataScadenzaOrig = dataScadenzaOrig;
    }
    public Timestamp getDataCreazioneOrig() {
        return dataCreazioneOrig;
    }
    public void setDataCreazioneOrig(Timestamp dataCreazioneOrig) {
        this.dataCreazioneOrig = dataCreazioneOrig;
    }
    public String getSiaCreditore() {
        return siaCreditore;
    }
    public void setSiaCreditore(String siaCreditore) {
        this.siaCreditore = siaCreditore;
    }
    public String getCodDebitore() {
        return codDebitore;
    }
    public void setCodDebitore(String codDebitore) {
        this.codDebitore = codDebitore;
    }
    public String getTipoCodIndividuale() {
        return tipoCodIndividuale;
    }
    public void setTipoCodIndividuale(String tipoCodIndividuale) {
        this.tipoCodIndividuale = tipoCodIndividuale;
    }
    public String getAbiBancaAssuntrice() {
        return abiBancaAssuntrice;
    }
    public void setAbiBancaAssuntrice(String abiBancaAssuntrice) {
        this.abiBancaAssuntrice = abiBancaAssuntrice;
    }
    public String getCabBancaAssuntrice() {
        return cabBancaAssuntrice;
    }
    public void setCabBancaAssuntrice(String cabBancaAssuntrice) {
        this.cabBancaAssuntrice = cabBancaAssuntrice;
    }
    public String getIbanOrdinante() {
        return ibanOrdinante;
    }
    public void setIbanOrdinante(String ibanOrdinante) {
        this.ibanOrdinante = ibanOrdinante;
    }
    public String getCodRiferimento() {
        return codRiferimento;
    }
    public void setCodRiferimento(String codRiferimento) {
        this.codRiferimento = codRiferimento;
    }
    public String getCausaleStorno() {
        return causaleStorno;
    }
    public void setCausaleStorno(String causaleStorno) {
        this.causaleStorno = causaleStorno;
    }
    public void setFlagStorno(String flagStorno) {
        this.flagStorno = flagStorno;
    }
    public String getFlagStorno() {
        return flagStorno;
    }
    public void setCausale(String causale) {
        this.causale = causale;
    }
    public String getCausale() {
        return causale;
    }
    public String getCodIndividuale() {
        return codIndividuale;
    }
    public void setCodIndividuale(String codIndividuale) {
        this.codIndividuale = codIndividuale;
    }
    public String getAbiAddebito() {
        return abiAddebito;
    }
    public void setAbiAddebito(String abiAddebito) {
        this.abiAddebito = abiAddebito;
    }
    public String getCabAddebito() {
        return cabAddebito;
    }
    public void setCabAddebito(String cabAddebito) {
        this.cabAddebito = cabAddebito;
    }
    public String getNumeroCcAddebito() {
        return numeroCcAddebito;
    }
    public void setNumeroCcAddebito(String numeroCcAddebito) {
        this.numeroCcAddebito = numeroCcAddebito;
    }
    public String getCodPaeseAddebito() {
        return codPaeseAddebito;
    }
    public void setCodPaeseAddebito(String codPaeseAddebito) {
        this.codPaeseAddebito = codPaeseAddebito;
    }
    public String getCheckDigitAddebito() {
        return checkDigitAddebito;
    }
    public void setCheckDigitAddebito(String checkDigitAddebito) {
        this.checkDigitAddebito = checkDigitAddebito;
    }
    public String getCinAddebito() {
        return cinAddebito;
    }
    public void setCinAddebito(String cinAddebito) {
        this.cinAddebito = cinAddebito;
    }
    public String getSiaCreditore_aea() {
        return siaCreditore_aea;
    }
    public void setSiaCreditore_aea(String siaCreditore_aea) {
        this.siaCreditore_aea = siaCreditore_aea;
    }
    public Long getIdBonificiRiaccredito() {
        return idBonificiRiaccredito;
    }
    public void setIdBonificiRiaccredito(Long idBonificiRiaccredito) {
        this.idBonificiRiaccredito = idBonificiRiaccredito;
    }
    
    public BigDecimal getImportoDebito() {
        return importoDebito;
    }
    
    public void setImportoDebito(BigDecimal importoDebito) {
        this.importoDebito = importoDebito;
    }
    
    public String getCanale() {
        return canale;
    }
    
    public void setCanale(String canale) {
        this.canale = canale;
    }
    
    public String getCodiceBiller() {
        return codiceBiller;
    }
    
    public void setCodiceBiller(String codiceBiller) {
        this.codiceBiller = codiceBiller;
    }
    
    public String getIdVersamento() {
        return idVersamento;
    }
    
    public void setIdVersamento(String idVersamento) {
        this.idVersamento = idVersamento;
    }
    
    public String getIdDebito() {
        return idDebito;
    }
    
    public void setIdDebito(String idDebito) {
        this.idDebito = idDebito;
    }
    
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }
    
    public void setImportoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }
    
    public BigDecimal getCommissioniBiller() {
        return commissioniBiller;
    }
    
    public void setCommissioniBiller(BigDecimal commissioniBiller) {
        this.commissioniBiller = commissioniBiller;
    }
    
    public BigDecimal getCommissioniBanca() {
        return commissioniBanca;
    }
    
    public void setCommissioniBanca(BigDecimal commissioniBanca) {
        this.commissioniBanca = commissioniBanca;
    }
    
    public String getIbanAccredito() {
        return ibanAccredito;
    }
    
    public void setIbanAccredito(String ibanAccredito) {
        this.ibanAccredito = ibanAccredito;
    }
    
    public String getBancaOrdinante() {
        return bancaOrdinante;
    }
    
    public void setBancaOrdinante(String bancaOrdinante) {
        this.bancaOrdinante = bancaOrdinante;
    }
    
    public String getBancaBeneficiario() {
        return bancaBeneficiario;
    }
    
    public void setBancaBeneficiario(String bancaBeneficiario) {
        this.bancaBeneficiario = bancaBeneficiario;
    }
    
    public String getCodTransazionePSP() {
        return codTransazionePSP;
    }
    
    public void setCodTransazionePSP(String codTransazionePSP) {
        this.codTransazionePSP = codTransazionePSP;
    }
    
    public String getDettagliAtm() {
        return dettagliAtm;
    }
    
    public void setDettagliAtm(String dettagliAtm) {
        this.dettagliAtm = dettagliAtm;
    }
    
    public Integer getFlagRiconciliazione() {
        return flagRiconciliazione;
    }
    
    public void setFlagRiconciliazione(Integer flagRiconciliazione) {
        this.flagRiconciliazione = flagRiconciliazione;
    }
    
    public String getFlagRiconciliazioneHTML() {
        
        String flagRiconciliazioneHTML = "";
        try {
            if (this.flagRiconciliazione == null) {
                flagRiconciliazioneHTML = "";
            } else {
                flagRiconciliazioneHTML  = EnumFlagRiconciliazioneEsiti.getEnumFlagRiconciliazioneEsitiByChiave(this.flagRiconciliazione).getDescrizione();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return flagRiconciliazioneHTML;
    }
}
