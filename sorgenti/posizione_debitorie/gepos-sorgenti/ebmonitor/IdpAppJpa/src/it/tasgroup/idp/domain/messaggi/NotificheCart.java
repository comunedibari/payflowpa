package it.tasgroup.idp.domain.messaggi;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Arrays;


/**
 * The persistent class for the NOTIFICHE_CART database table.
 * 
 */ 
@Entity 
@Table(name="NOTIFICHE_CART")
public class NotificheCart extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private NotificheCartPK id;
	private byte[] notificaXml;
	private int prVersione;
	private String senderid;
	private String sendersys;
	private String stato;
	private String tipoNotifica;
	private Timestamp timestampInvio;
	private String trtReceiverId;
	private String trtReceiverSys;
	private String idEnte;
	private String cdTrbEnte;
	private int tentativi;

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

	@Column(name="TIPO_NOTIFICA")
	public String getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}	

	
	
	public void setTrtReceiverId(String trtReceiverId) {
		this.trtReceiverId = trtReceiverId;
	}

	public void setTrtReceiverSys(String trtReceiverSys) {
		this.trtReceiverSys = trtReceiverSys;
	}

	@Column(name="TRT_RECEIVERID")
	public String getTrtReceiverId() {
		return this.trtReceiverId;
	}	

	@Column(name="TRT_RECEIVERSYS")
	public String getTrtReceiverSys() {
		return this.trtReceiverSys;
	}	
	
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	
	@Column(name="CD_TRB_ENTE")
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
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
		builder.append(", idEnte =");
		builder.append(idEnte);
		builder.append(", cdTrbEnte =");
		builder.append(cdTrbEnte);
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
		builder.append(", tentativi=");
		builder.append(tentativi);
		builder.append("]");
		return builder.toString();
	}


}