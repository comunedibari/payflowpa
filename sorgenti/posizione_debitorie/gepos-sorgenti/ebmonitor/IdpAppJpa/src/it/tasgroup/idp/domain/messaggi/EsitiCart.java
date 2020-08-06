package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;

@Entity
@Table(name="ESITI_CART")
public class EsitiCart extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private EsitiCartPK pk;
	private byte[] esitoXml;
	private String stato;
	private Timestamp timestampInvio;	
	private int prVersione;
	private String trtSenderId;
	private String trtSenderSys;	
	private int tentativi;
	
	/*** Persistent Associations***/
	private PendenzeCart pendenzeCart;
	

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@EmbeddedId
	public EsitiCartPK getPk() {
		return this.pk;
	}

	public void setPk(EsitiCartPK pk) {
		this.pk = pk;
	}

	@Column(name="ESITO_XML")
	@Lob
	public byte[] getEsitoXml() {
		return this.esitoXml;
	}

	public void setEsitoXml(byte[] esitoXml) {
		this.esitoXml = esitoXml;
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

	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}
	
	@ManyToOne
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
		builder.append("EsitiCart [pk=");
		builder.append(pk);
		builder.append(", esitoXml=");
		builder.append(Arrays.toString(esitoXml));
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", timestampInvio=");
		builder.append(timestampInvio);
		builder.append(", prVersione=");
		builder.append(prVersione);
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
		builder.append(", tentativi=");
		builder.append(tentativi);
		builder.append("]");
		return builder.toString();
	}

	
}
