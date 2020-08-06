package it.tasgroup.idp.domain.messaggi;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
	
@Entity
@Table(name="PENDENZE_CART")
public class PendenzeCart implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*** Persistent Values***/
	private PendenzeCartPK pk;
	private String receiverid;
	private String receiversys;
	private byte[] messaggioXml;
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
	private String trt_senderid;
	private String trt_sendersys;

	
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

	@Column(name="MESSAGGIO_XML")
	@Lob
	@Basic(fetch=FetchType.LAZY)
	public byte[] getMessaggioXml() {
		return this.messaggioXml;
	}

	public void setMessaggioXml(byte[] messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

	@Column(name="TIMESTAMP_RICEZIONE")
	public Timestamp getTimestampRicezione() {
		return this.timestampRicezione;
	}

	public void setTimestampRicezione(Timestamp timestampRicezione) {
		this.timestampRicezione = timestampRicezione;
	}

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

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PendenzeCart [pk=");
		builder.append(pk);
		builder.append(", receiverid=");
		builder.append(receiverid);
		builder.append(", receiversys=");
		builder.append(receiversys);
		builder.append(", messaggioXml=");
		builder.append(Arrays.toString(messaggioXml));
		builder.append(", timestampRicezione=");
		builder.append(timestampRicezione);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", prVersione=");
		builder.append(prVersione);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append(", numPendenze=");
		builder.append(numPendenze);
		builder.append(", numPendDeleted=");
		builder.append(numPendDeleted);
		builder.append(", tipoMessaggio=");
		builder.append(tipoMessaggio);
		builder.append(", tipoTributo=");
		builder.append(tipoTributo);
		builder.append("]");
		return builder.toString();
	}

	@Column(name="TRT_SENDERID")
	public String getTrt_senderid() {
		return trt_senderid;
	}

	public void setTrt_senderid(String trt_senderid) {
		this.trt_senderid = trt_senderid;
	}

	@Column(name="TRT_SENDERSYS")
	public String getTrt_sendersys() {
		return trt_sendersys;
	}

	public void setTrt_sendersys(String trt_sendersys) {
		this.trt_sendersys = trt_sendersys;
	}
	
	
}
