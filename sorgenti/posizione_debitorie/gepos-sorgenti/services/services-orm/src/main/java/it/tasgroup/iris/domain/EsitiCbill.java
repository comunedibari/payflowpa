package it.tasgroup.iris.domain;

import it.tasgroup.services.util.enumeration.EnumCanaleCbill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the ESITI_CBILL database table.
 *
 */
@Entity
@Table(name="ESITI_CBILL")
@NamedQueries(
{
	@NamedQuery(
		name="getEsitiCbillById",
		query="select esiti from EsitiCbill esiti "+
			  "where esiti.id =:id"),
	@NamedQuery(
		name="listEsitiCbillByIdRendicontazione",
		query="select esiti from EsitiCbill esiti " +
				"where esiti.rendicontazioni.id=:idRendicontazione ")
		}
)
public class EsitiCbill extends BaseIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idRiconciliazione;
    private Integer flagRiconciliazione;
    private String codiceBiller;
    private String idVersamento;
    private String idDebito;
    private String ibanAccredito;
    private String codTransazionePSP;
    private Timestamp dataPagamento;
    private String bancaOrdinante;
    private String bancaBeneficiario;
    private BigDecimal importoDebito;
    private BigDecimal commissioniBiller;
    private BigDecimal commissioniBanca;
    private BigDecimal importoTotale;
    private String canale;
    private String atmCab;
    private String atmId;
    private String atmTransactionNum;
    private Integer idBozzeBonificiRiaccredito;
    private String opAggiornamento;
    private String opInserimento;
    private Timestamp tsAggiornamento;
    private Timestamp tsInserimento;
    private Rendicontazioni rendicontazioni;

    public EsitiCbill() {
    }

    public EsitiCbill(String idRiconciliazione, Integer flagRiconciliazione, 
            String codiceBiller, String idVersamento, String idDebito, String ibanAccredito, String codTransazionePSP, 
            Timestamp dataPagamento, String bancaOrdinante, String bancaBeneficiario, BigDecimal importoDebito, 
            BigDecimal commissioniBiller, BigDecimal commissioniBanca, BigDecimal importoTotale, String canale,
            String atmCab, String atmId, String atmTransactionNum, Integer idBozzeBonificiRiaccredito, 
            String opAggiornamento, String opInserimento, Timestamp tsAggiornamento, Timestamp tsInserimento, 
            Rendicontazioni rendicontazioni) {
        this.idRiconciliazione = idRiconciliazione;
        this.flagRiconciliazione = flagRiconciliazione;
        this.codiceBiller = codiceBiller;
        this.idVersamento = idVersamento;
        this.idDebito = idDebito;
        this.ibanAccredito = ibanAccredito;
        this.codTransazionePSP = codTransazionePSP;
        this.dataPagamento = dataPagamento;
        this.bancaOrdinante = bancaOrdinante;
        this.bancaBeneficiario = bancaBeneficiario;
        this.importoDebito = importoDebito;
        this.commissioniBiller = commissioniBiller;
        this.commissioniBanca = commissioniBanca;
        this.importoTotale = importoTotale;
        this.canale = canale;
        this.atmCab = atmCab;
        this.atmId = atmId;
        this.atmTransactionNum = atmTransactionNum;
        this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
        this.opAggiornamento = opAggiornamento;
        this.opInserimento = opInserimento;
        this.tsAggiornamento = tsAggiornamento;
        this.tsInserimento = tsInserimento;
        this.rendicontazioni = rendicontazioni;
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="esiti_cbill_pk_gen")	
	@SequenceGenerator(
		    name="esiti_cbill_pk_gen",
		    sequenceName="ESITI_CBILL_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		    
    
    @Column(name="FLAG_RICONCILIAZIONE")
    public Integer getFlagRiconciliazione() {
        return this.flagRiconciliazione;
    }
    
    public void setFlagRiconciliazione(Integer flagRiconciliazione) {
        this.flagRiconciliazione = flagRiconciliazione;
    }    
    
    @Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
    public Integer getIdBozzeBonificiRiaccredito() {
        return this.idBozzeBonificiRiaccredito;
    }
    
    public void setIdBozzeBonificiRiaccredito(Integer idBozzeBonificiRiaccredito) {
        this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
    }
    
    
    @Column(name="ID_RICONCILIAZIONE")
    public String getIdRiconciliazione() {
        return this.idRiconciliazione;
    }
    
    public void setIdRiconciliazione(String idRiconciliazione) {
        this.idRiconciliazione = idRiconciliazione;
    }
    
    @Column(name="OP_AGGIORNAMENTO")
    public String getOpAggiornamento() {
        return this.opAggiornamento;
    }
    
    public void setOpAggiornamento(String opAggiornamento) {
        this.opAggiornamento = opAggiornamento;
    }
    
    
    @Column(name="OP_INSERIMENTO")
    public String getOpInserimento() {
        return this.opInserimento;
    }
    
    public void setOpInserimento(String opInserimento) {
        this.opInserimento = opInserimento;
    }
        
    @Column(name="TS_AGGIORNAMENTO")
    public Timestamp getTsAggiornamento() {
        return this.tsAggiornamento;
    }
    
    public void setTsAggiornamento(Timestamp tsAggiornamento) {
        this.tsAggiornamento = tsAggiornamento;
    }
    
    
    @Column(name="TS_INSERIMENTO")
    public Timestamp getTsInserimento() {
        return this.tsInserimento;
    }
    
    public void setTsInserimento(Timestamp tsInserimento) {
        this.tsInserimento = tsInserimento;
    }
    
    //bi-directional many-to-one association to Rendicontazioni
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_RENDICONTAZIONE")
    public Rendicontazioni getRendicontazioni() {
        return this.rendicontazioni;
    }
    
    public void setRendicontazioni(Rendicontazioni rendicontazioni) {
        this.rendicontazioni = rendicontazioni;
    }

    @Column(name="BILLERID")
    public String getCodiceBiller() {
        return codiceBiller;
    }

    public void setCodiceBiller(String codiceBiller) {
        this.codiceBiller = codiceBiller;
    }

    @Column(name="BILLACCOUNTID")
    public String getIdVersamento() {
        return idVersamento;
    }

    public void setIdVersamento(String idVersamento) {
        this.idVersamento = idVersamento;
    }

    @Column(name="BILLID")
    public String getIdDebito() {
        return idDebito;
    }

    public void setIdDebito(String idDebito) {
        this.idDebito = idDebito;
    }

    @Column(name="IBAN")
    public String getIbanAccredito() {
        return ibanAccredito;
    }

    public void setIbanAccredito(String ibanAccredito) {
        this.ibanAccredito = ibanAccredito;
    }

    @Column(name="TRANSACTIONID")
    public String getCodTransazionePSP() {
        return codTransazionePSP;
    }

    public void setCodTransazionePSP(String codTransazionePSP) {
        this.codTransazionePSP = codTransazionePSP;
    }

    @Column(name="PAYMENTDTTM")
    public Timestamp getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Timestamp dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Column(name="ORDERINGBANK")
    public String getBancaOrdinante() {
        return bancaOrdinante;
    }

    public void setBancaOrdinante(String bancaOrdinante) {
        this.bancaOrdinante = bancaOrdinante;
    }

    @Column(name="CREDITBANKID")
    public String getBancaBeneficiario() {
        return bancaBeneficiario;
    }

    public void setBancaBeneficiario(String bancaBeneficiario) {
        this.bancaBeneficiario = bancaBeneficiario;
    }

    @Column(name="BILLAMOUNT")
    public BigDecimal getImportoDebito() {
        return importoDebito;
    }

    public void setImportoDebito(BigDecimal importoDebito) {
        this.importoDebito = importoDebito;
    }

    @Column(name="BILLERCOMMISSION")
    public BigDecimal getCommissioniBiller() {
        return commissioniBiller;
    }

    public void setCommissioniBiller(BigDecimal commissioniBiller) {
        this.commissioniBiller = commissioniBiller;
    }

    @Column(name="BANKCOMMISSION")
    public BigDecimal getCommissioniBanca() {
        return commissioniBanca;
    }

    public void setCommissioniBanca(BigDecimal commissioniBanca) {
        this.commissioniBanca = commissioniBanca;
    }

    @Column(name="TOTALCHARGE")
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }

    @Column(name="CHANNEL")
    public String getCanale() {
        return canale;
    }

    public void setCanale(String canale) {
        this.canale = canale;
    }

    @Column(name="ATMCAB")
    public String getAtmCab() {
        return atmCab;
    }

    public void setAtmCab(String atmCab) {
        this.atmCab = atmCab;
    }

    @Column(name="ATMID")
    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    @Column(name="ATMTRANSACTIONNUM")
    public String getAtmTransactionNum() {
        return atmTransactionNum;
    }

    public void setAtmTransactionNum(String atmTransactionNum) {
        this.atmTransactionNum = atmTransactionNum;
    }    
    
    @Transient
    public String getDettagliAtm(){
        if(!EnumCanaleCbill.ATM.getChiave().equalsIgnoreCase(getCanale())){
            return null;
        }
        
        StringBuilder atm = new StringBuilder();
        atm.append(getAtmCab()).append(":")
           .append(getAtmId()).append(":")
           .append(getAtmTransactionNum()).toString();
        
        return atm.toString();
    }
    
    @Transient
    public BigDecimal getImportoTotaleInEuro(){
        return getImportoTotale() == null && BigDecimal.ZERO.compareTo(getImportoTotale()) == 0? getImportoTotale() : getImportoTotale().divide(new BigDecimal("100"));
    }
    
    @Transient
    public BigDecimal getImportoDebitoInEuro(){
        return getImportoDebito() == null && BigDecimal.ZERO.compareTo(getImportoDebito()) == 0? getImportoDebito() : getImportoDebito().divide(new BigDecimal("100"));
    }
}