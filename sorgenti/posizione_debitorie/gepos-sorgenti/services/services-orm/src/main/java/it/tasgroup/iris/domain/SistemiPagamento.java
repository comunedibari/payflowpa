package it.tasgroup.iris.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the SISTEMI_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="SISTEMI_PAGAMENTO")
@NamedQueries(
{
@NamedQuery(  
		name="getSystemById",
		query="select sispag from SistemiPagamento sispag where sispag.systemId=:idSistema"),
@NamedQuery(
		name="getSilById",
		query="select sispag from SistemiPagamento sispag where sispag.applicationId=:idApplicazione")		
})

public class SistemiPagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String applicationId;
	private String applicationIp;
	private short documentoPagamento;
	private String flStato;
	private String molteplicitaPagamento;
	private String opAggiornamento;
	private String opInserimento;
	private int prVersione;
	private String stRiga;
	private String systemId;
	private int timeoutAutorizzazione;
	private int timeoutNotifica;
	private String tipoStrumento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;


	@Id
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(name="APPLICATION_ID")
	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	@Column(name="APPLICATION_IP")
	public String getApplicationIp() {
		return this.applicationIp;
	}

	public void setApplicationIp(String applicationIp) {
		this.applicationIp = applicationIp;
	}


	@Column(name="DOCUMENTO_PAGAMENTO")
	public short getDocumentoPagamento() {
		return this.documentoPagamento;
	}

	public void setDocumentoPagamento(short documentoPagamento) {
		this.documentoPagamento = documentoPagamento;
	}


	@Column(name="FL_STATO")
	public String getFlStato() {
		return this.flStato;
	}

	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}


	@Column(name="MOLTEPLICITA_PAGAMENTO")
	public String getMolteplicitaPagamento() {
		return this.molteplicitaPagamento;
	}

	public void setMolteplicitaPagamento(String molteplicitaPagamento) {
		this.molteplicitaPagamento = molteplicitaPagamento;
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


	@Column(name="PR_VERSIONE")
	public int getPrVersione() {
		return this.prVersione;
	}

	public void setPrVersione(int prVersione) {
		this.prVersione = prVersione;
	}


	@Column(name="ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(name="SYSTEM_ID")
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


	@Column(name="TIMEOUT_AUTORIZZAZIONE")
	public int getTimeoutAutorizzazione() {
		return this.timeoutAutorizzazione;
	}

	public void setTimeoutAutorizzazione(int timeoutAutorizzazione) {
		this.timeoutAutorizzazione = timeoutAutorizzazione;
	}


	@Column(name="TIMEOUT_NOTIFICA")
	public int getTimeoutNotifica() {
		return this.timeoutNotifica;
	}

	public void setTimeoutNotifica(int timeoutNotifica) {
		this.timeoutNotifica = timeoutNotifica;
	}


	@Column(name="TIPO_STRUMENTO")
	public String getTipoStrumento() {
		return this.tipoStrumento;
	}

	public void setTipoStrumento(String tipoStrumento) {
		this.tipoStrumento = tipoStrumento;
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



}