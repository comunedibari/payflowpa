package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="ESITI_PENDENZA")

@NamedQueries(
{
@NamedQuery(name="listaCountEsitiPendenza", 
	query = "SELECT count(esiti)  " +
			" FROM EsitiPendenza esiti " +
			" where " + 
			"  esiti.pendenzeCart = :cart " + 
			" and esiti.stato = :stato "),	
@NamedQuery(name="listaEsitiPendenza", 
	query="SELECT cond FROM EsitiPendenza esitiPendenze" +
			" , CondizioniPagamento cond  " +
			" WHERE esitiPendenze.stato = :stato " +
			" AND cond.pendenze.idPendenza = esitiPendenze.idPendenza " +
			" AND esitiPendenze.pendenzeCart = :cart "),
@NamedQuery(name="listaEsitiPendenzaRFC127", 
	query="SELECT esiti FROM EsitiPendenza esiti " +
			" WHERE esiti.pendenzeCart.pk.e2emsgid = :e2emsgid" +
			" AND esiti.pendenzeCart.pk.senderid = :senderId " + 
			" AND esiti.pendenzeCart.pk.sendersys = :senderSys "),
@NamedQuery(name="listaEsitiPendenzaRFC127AndStato", 
	query="SELECT esiti FROM EsitiPendenza esiti " +
			" WHERE esiti.pendenzeCart.pk.e2emsgid = :e2emsgid" +
			" AND esiti.pendenzeCart.pk.senderid = :senderId " + 
			" AND esiti.pendenzeCart.pk.sendersys = :senderSys " +
			" AND esiti.stato = :stato"),					
@NamedQuery(name="listaEsitiPendenzaPerStato", 
	query="SELECT esitiPendenze FROM EsitiPendenza esitiPendenze " +
			" WHERE esitiPendenze.stato = :stato " +
			" AND esitiPendenze.pendenzeCart = :cart ")
					
})


public class EsitiPendenza implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private String idEsitoPendenza;
	private String esitoPendenza;
	private String descrizioneEsito;
	private String stato;
	private String idPendenza;
	private String idPendenzaEnte;
	private int prVersione;
	private String opInserimento;
	private Timestamp tsInserimento;
	
	//per casini gestione header di tratta
	private String trtSenderId;
	private String trtSenderSys;
	
	//campi transient di utilita nella gestione degli esiti
	private String idPagamento;
	private String iuv;
	
	/*** Persistent Associations***/
	private PendenzeCart pendenzeCart;
	private Set<ErroriEsitiPendenza> erroriEsitiPendenzaCollection;


	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Id
	@Column(name="ID_ESITO_PENDENZA")
	public String getIdEsitoPendenza() {
		return this.idEsitoPendenza;
	}
	public void setIdEsitoPendenza(String idEsitoPendenza) {
		this.idEsitoPendenza = idEsitoPendenza;
	}

	@Column(name="ESITO_PENDENZA")
	public String getEsitoPendenza() {
		return this.esitoPendenza;
	}
	public void setEsitoPendenza(String esitoPendenza) {
		this.esitoPendenza = esitoPendenza;
	}

	@Column(name="DESCRIZIONE_ESITO")
	public String getDescrizioneEsito() {
		return this.descrizioneEsito;
	}
	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}

	public String getStato() {
		return this.stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="ID_PENDENZA")
	public String getIdPendenza() {
		return this.idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name="ID_PENDENZA_ENTE")
	public String getIdPendenzaEnte() {
		return this.idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
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

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="E2EMSGID", referencedColumnName="E2EMSGID"),
		@JoinColumn(name="SENDERID", referencedColumnName="SENDERID"),
		@JoinColumn(name="SENDERSYS", referencedColumnName="SENDERSYS")
	})
	public PendenzeCart getPendenzeCart() {
		return this.pendenzeCart;
	}
	public void setPendenzeCart(PendenzeCart pendenzeCart) {
		this.pendenzeCart = pendenzeCart;
	}

	@OneToMany(mappedBy="idEsitoPendenza")
	public Set<ErroriEsitiPendenza> getErroriEsitiPendenzaCollection() {
		return this.erroriEsitiPendenzaCollection;
	}
	public void setErroriEsitiPendenzaCollection(Set<ErroriEsitiPendenza> erroriEsitiPendenzaCollection) {
		this.erroriEsitiPendenzaCollection = erroriEsitiPendenzaCollection;
	}
	
	
	
	@Transient
	public String getTrtSenderId() {
		return trtSenderId;
	}
	public void setTrtSenderId(String trtSenderId) {
		this.trtSenderId = trtSenderId;
	}
	@Transient
	public String getTrtSenderSys() {
		return trtSenderSys;
	}
	public void setTrtSenderSys(String trtSenderSys) {
		this.trtSenderSys = trtSenderSys;
	}
	@Transient
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	@Transient
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EsitiPendenza [idEsitoPendenza=");
		builder.append(idEsitoPendenza);
		builder.append(", esitoPendenza=");
		builder.append(esitoPendenza);
		builder.append(", descrizioneEsito=");
		builder.append(descrizioneEsito);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", idPendenza=");
		builder.append(idPendenza);
		builder.append(", idPendenzaEnte=");
		builder.append(idPendenzaEnte);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idEsitoPendenza == null) ? 0 : idEsitoPendenza.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EsitiPendenza other = (EsitiPendenza) obj;
		if (idEsitoPendenza == null) {
			if (other.idEsitoPendenza != null) {
				return false;
			}
		} else if (!idEsitoPendenza.equals(other.idEsitoPendenza)) {
			return false;
		}
		return true;
	}

	
}
