package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;


/**
 * The persistent class for the CONFERME_CART database table.
 * 
 */
@Entity
@Table(name="CONFERME_CART")
public class ConfermeCart implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private ConfermeCartPK id;
	private byte[] messaggioXml;
	private String opInserimento;
	private String receiverid;
	private String receiversys;
	private String stato;
	private Timestamp timestampRicezione;
	private Timestamp tsInserimento;


	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@EmbeddedId
	public ConfermeCartPK getId() {
		return this.id;
	}
	public void setId(ConfermeCartPK id) {
		this.id = id;
	}
	
    @Lob()
	@Column(name="MESSAGGIO_XML")
	public byte[] getMessaggioXml() {
		return this.messaggioXml;
	}
	public void setMessaggioXml(byte[] messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

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

	public String getStato() {
		return this.stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name="TIMESTAMP_RICEZIONE")
	public Timestamp getTimestampRicezione() {
		return this.timestampRicezione;
	}
	public void setTimestampRicezione(Timestamp timestampRicezione) {
		this.timestampRicezione = timestampRicezione;
	}

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfermeCart [id=");
		builder.append(id);
		builder.append(", messaggioXml=");
		builder.append(Arrays.toString(messaggioXml));
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", receiverid=");
		builder.append(receiverid);
		builder.append(", receiversys=");
		builder.append(receiversys);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", timestampRicezione=");
		builder.append(timestampRicezione);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append("]");
		return builder.toString();
	}

	
}