package it.tasgroup.cbill.domain;

import it.tasgroup.iris2.pagamenti.Rendicontazioni;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ESITI_CBILL database table.
 * 
 */
@Entity
@Table(name="ESITI_CBILL")
@NamedQueries(
{
@NamedQuery(name="EsitiCbill.findAll", query="SELECT e FROM EsitiCbill e"),
@NamedQuery(name="EsitiCbill.findAllNonRiconciliatiByIdCasellarioInfo", query="SELECT e FROM EsitiCbill e where flagRiconciliazione<>1 AND e.rendicontazioni.casellarioInfo.id = :idCasellarioInfo ")
})
public class EsitiCbill extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String atmcab;

	private String atmid;

	private String atmtransactionnum;

	private BigDecimal bankcommission;

	private String billaccountid;

	private BigDecimal billamount;

	private BigDecimal billercommission;

	private String billerid;

	private String billid;

	private String channel;

	private String creditbankid;
	
	private short flagRiconciliazione;

	private String iban;
	
	private int idBozzeBonificiRiaccredito;
	
	private String idRiconciliazione;

	private String orderingbank;

	private Timestamp paymentdttm;

	private BigDecimal totalcharge;

	private String transactionid;

	//bi-directional many-to-one association to Rendicontazioni
	/*** Persistent Associations ***/
	private Rendicontazioni rendicontazioni;

	public EsitiCbill() {
	}
	
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="esiti_cbill_pk_gen")	
	@SequenceGenerator(
	    name="esiti_cbill_pk_gen",
	    sequenceName="ESITI_CBILL_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	

	public String getAtmcab() {
		return this.atmcab;
	}

	public void setAtmcab(String atmcab) {
		this.atmcab = atmcab;
	}

	public String getAtmid() {
		return this.atmid;
	}

	public void setAtmid(String atmid) {
		this.atmid = atmid;
	}

	public String getAtmtransactionnum() {
		return this.atmtransactionnum;
	}

	public void setAtmtransactionnum(String atmtransactionnum) {
		this.atmtransactionnum = atmtransactionnum;
	}

	public BigDecimal getBankcommission() {
		return this.bankcommission;
	}

	public void setBankcommission(BigDecimal bankcommission) {
		this.bankcommission = bankcommission;
	}

	public String getBillaccountid() {
		return this.billaccountid;
	}

	public void setBillaccountid(String billaccountid) {
		this.billaccountid = billaccountid;
	}

	public BigDecimal getBillamount() {
		return this.billamount;
	}

	public void setBillamount(BigDecimal billamount) {
		this.billamount = billamount;
	}

	public BigDecimal getBillercommission() {
		return this.billercommission;
	}

	public void setBillercommission(BigDecimal billercommission) {
		this.billercommission = billercommission;
	}

	public String getBillerid() {
		return this.billerid;
	}

	public void setBillerid(String billerid) {
		this.billerid = billerid;
	}

	public String getBillid() {
		return this.billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCreditbankid() {
		return this.creditbankid;
	}

	public void setCreditbankid(String creditbankid) {
		this.creditbankid = creditbankid;
	}

	@Column(name="FLAG_RICONCILIAZIONE")
	public short getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(short flagRiconciliazione) {
		this.flagRiconciliazione = flagRiconciliazione;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
	public int getIdBozzeBonificiRiaccredito() {
		return this.idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(int idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}

	@Column(name="ID_RICONCILIAZIONE")
	public String getIdRiconciliazione() {
		return this.idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}

	public String getOrderingbank() {
		return this.orderingbank;
	}

	public void setOrderingbank(String orderingbank) {
		this.orderingbank = orderingbank;
	}

	public Timestamp getPaymentdttm() {
		return this.paymentdttm;
	}

	public void setPaymentdttm(Timestamp paymentdttm) {
		this.paymentdttm = paymentdttm;
	}

	public BigDecimal getTotalcharge() {
		return this.totalcharge;
	}

	public void setTotalcharge(BigDecimal totalcharge) {
		this.totalcharge = totalcharge;
	}

	public String getTransactionid() {
		return this.transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	@ManyToOne
	@JoinColumn(name="ID_RENDICONTAZIONE")	
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}

}