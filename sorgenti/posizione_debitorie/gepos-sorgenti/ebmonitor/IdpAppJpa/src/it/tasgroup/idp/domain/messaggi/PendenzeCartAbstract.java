package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
	
@MappedSuperclass
public class PendenzeCartAbstract implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private PendenzeCartPK pk;
	private String receiverid;
	private String receiversys;
	private Timestamp timestampRicezione;
	private String stato;
	private int prVersione;
	private String opInserimento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;	
	private int numPendenze;
	private int numPendDeleted;
	private String tipoMessaggio;
	private String tipoOperazione;
	private String tipoTributo;
	private String trtSenderId;
	private String trtSenderSys;	
	
	//collection
	private Set<EsitiPendenza> esitiPendenzaCollection;

	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@EmbeddedId
	public PendenzeCartPK getPk() {
		return this.pk;
	}

	public void setPk(PendenzeCartPK pk) {
		this.pk = pk;
	}

	@Column(name="RECEIVERID")
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public String getReceiversys() {
		return this.receiversys;
	}

	public void setReceiversys(String receiversys) {
		this.receiversys = receiversys;
	}
 
	@Column(name="TIMESTAMP_RICEZIONE")
	public Timestamp getTimestampRicezione() {
		return this.timestampRicezione;
	}

	public void setTimestampRicezione(Timestamp timestampRicezione) {
		this.timestampRicezione = timestampRicezione;
	}

	@Column(name="STATO")
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	
	
	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}	

	@Column(name="NUM_PENDENZE")
	public int getNumPendenze() {
		return this.numPendenze;
	}

	public void setNumPendenze(int numPendenze) {
		this.numPendenze = numPendenze;
	}

	@Column(name="TIPO_MESSAGGIO")
	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}
	
	@Column(name="TIPO_OPERAZIONE")
	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}	


	@Column(name="TIPO_TRIBUTO")
	public String getTipoTributo() {
		return tipoTributo;
	}

	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}

	@Column(name="NUM_PEND_DELETED")
	public int getNumPendDeleted() {
		return numPendDeleted;
	}

	public void setNumPendDeleted(int numPendDeleted) {
		this.numPendDeleted = numPendDeleted;
	}
	
	
	
	public void setTrtSenderId(String trtSenderId) {
		this.trtSenderId = trtSenderId;
	}

	public void setTrtSenderSys(String trtSenderSys) {
		this.trtSenderSys = trtSenderSys;
	}

	@Column(name="TRT_SENDERID")
	public String getTrtSenderId() {
		return this.trtSenderId;
	}	

	@Column(name="TRT_SENDERSYS")
	public String getTrtSenderSys() {
		return this.trtSenderSys;
	}		
	
	


	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="E2EMSGID", referencedColumnName="E2EMSGID"),
		@JoinColumn(name="SENDERID", referencedColumnName="SENDERID"),
		@JoinColumn(name="SENDERSYS", referencedColumnName="SENDERSYS")
	})	
	public Set<EsitiPendenza> getEsitiPendenzaCollection() {
		return this.esitiPendenzaCollection;
	}
	public void setEsitiPendenzaCollection(Set<EsitiPendenza> esitiPendenzaCollection) {
		this.esitiPendenzaCollection = esitiPendenzaCollection;
	}
	
	
}
