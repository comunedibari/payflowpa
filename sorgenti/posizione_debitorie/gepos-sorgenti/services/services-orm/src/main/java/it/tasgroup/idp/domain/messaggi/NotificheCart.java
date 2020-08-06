package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.iris.domain.GestioneFlussi;


/**
 * The persistent class for the NOTIFICHE_CART database table.
 * 
 */ 
@Entity 
@Table(name="NOTIFICHE_CART")
public class NotificheCart  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private NotificheCartPK id;
	private byte[] notificaXml;
	private int prVersione;
	private String senderid;
	private String sendersys;
	private String stato;
	private String tipoNotifica;
	private String cdTrbEnte;
	private Timestamp timestampInvio;
	private String opInserimento;
	private String opAggiornamento;
	private Timestamp tsInserimento;
	private Timestamp tsAggiornamento;
	private int tentativi;
	
	/*** Persistent References ***/
	private TributoEnte tributoente;

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@EmbeddedId
	public NotificheCartPK getId() {
		return this.id;
	}

	public void setId(NotificheCartPK id) {
		this.id = id;
	}
	
    @Lob()
	@Column(name="NOTIFICA_XML")
	public byte[] getNotificaXml() {
		return this.notificaXml;
	}

	public void setNotificaXml(byte[] notificaXml) {
		this.notificaXml = notificaXml;
	}
	
	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}

	public String getSenderid() {
		return this.senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getSendersys() {
		return this.sendersys;
	}

	public void setSendersys(String sendersys) {
		this.sendersys = sendersys;
	}	

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="TIMESTAMP_INVIO")
	public Timestamp getTimestampInvio() {
		return this.timestampInvio;
	}

	public void setTimestampInvio(Timestamp timestampInvio) {
		this.timestampInvio = timestampInvio;
	}
	
	@ManyToOne(targetEntity=TributoEnte.class)
	@JoinColumns({
        @JoinColumn(name="ID_ENTE", referencedColumnName="ID_ENTE",insertable =  false, updatable = false),
        @JoinColumn(name="CD_TRB_ENTE", referencedColumnName="CD_TRB_ENTE",insertable =  false, updatable = false)
    })
	
	public TributoEnte getTributoente() {
		return tributoente;
	}
	public void setTributoente(TributoEnte tributoente) {
		this.tributoente = tributoente;
	}


	@Column(name="TIPO_NOTIFICA")
	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}	

	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
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
	
	@Column(name="TENTATIVI")
	public int getTentativi() {
		return tentativi;
	}

	public void setTentativi(int tentativi) {
		this.tentativi = tentativi;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificheCart [id=");
		builder.append(id);
		builder.append(", notificaXml=");
		builder.append(Arrays.toString(notificaXml));
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", senderid=");
		builder.append(senderid);
		builder.append(", sendersys=");
		builder.append(sendersys);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", timestampInvio=");
		builder.append(timestampInvio);
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append("]");
		return builder.toString();
	}


}