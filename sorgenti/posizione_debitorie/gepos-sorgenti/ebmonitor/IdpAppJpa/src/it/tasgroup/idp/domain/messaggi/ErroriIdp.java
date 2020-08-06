package it.tasgroup.idp.domain.messaggi;

import it.tasgroup.idp.domain.BaseEntity;

import java.io.Serializable;
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

@Entity
@Table(name="ERRORI_IDP")
public class ErroriIdp extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private ErroriIdpPK pk;	
	private String receiverid;	
	private String receiversys;
	private byte[] esitoXml;
	private String serviceName;	
	private int prVersione;	
	private String descrizioneStato;	
	private String trtSenderId;
	private String trtSenderSys;		
	
	/*** Persistent Associations***/
	private PendenzeCart pendenzeCart;

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@EmbeddedId
	public ErroriIdpPK getPk() {
		return this.pk;
	}

	public void setPk(ErroriIdpPK pk) {
		this.pk = pk;
	}
	
	@Column(name="RECEIVERID")
	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name="RECEIVERSYS")
	public String getReceiversys() {
		return this.receiversys;
	}

	public void setReceiversys(String receiversys) {
		this.receiversys = receiversys;
	}

	@Column(name="ESITO_XML")
	@Lob
	public byte[] getEsitoXml() {
		return this.esitoXml;
	}

	public void setEsitoXml(byte[] esitoXml) {
		this.esitoXml = esitoXml;
	}

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}


 	@ManyToOne(fetch=FetchType.LAZY)
 	@JoinColumns({
 		@JoinColumn(name="E2EMSGID", referencedColumnName="E2EMSGID",insertable = false, updatable = false),
 		@JoinColumn(name="SENDERID", referencedColumnName="SENDERID",insertable = false, updatable = false),
 		@JoinColumn(name="SENDERSYS", referencedColumnName="SENDERSYS",insertable = false, updatable = false)
 	})
 	public PendenzeCart getPendenzeCart() {
 		return this.pendenzeCart;
 	}
 
 	public void setPendenzeCart(PendenzeCart pendenzeCart) {
 		this.pendenzeCart = pendenzeCart;
 	}

	@Column(name="DESCRIZIONE_STATO")
	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	
	@Column(name="SERVICENAME")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErroriIdp [pk=");
		builder.append(pk);
		builder.append(", receiverid=");
		builder.append(receiverid);
		builder.append(", receiversys=");
		builder.append(receiversys);
		builder.append(", esitoXml=");
		builder.append(Arrays.toString(esitoXml));
		builder.append(", serviceName=");
		builder.append(serviceName);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", descrizioneStato=");
		builder.append(descrizioneStato);
		//builder.append(", pendenzeCart=");
		//builder.append(pendenzeCart);
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
