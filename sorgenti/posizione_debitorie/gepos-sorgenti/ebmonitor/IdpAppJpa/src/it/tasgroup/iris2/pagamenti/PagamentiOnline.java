package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the PAGAMENTI_ONLINE database table.
 * 
 */
@Entity
@Table(name="PAGAMENTI_ONLINE")
public class PagamentiOnline extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String codAutorizzazione;
	private String deOperazione;
	private String idOperazione;
	private String numOperazione;
	private String sessionIdSistema;
	private String sessionIdTerminale;
	private String sessionIdTimbro;
	private String sessionIdToken;
	private String tiOperazione;
	private Timestamp tsOperazione;

	/*** Persistent Associations ***/
	private DistintePagamento distintePagamento;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="pagamenti_online_pk_gen")	
	@SequenceGenerator(
	    name="pagamenti_online_pk_gen",
	    sequenceName="PAGAMENTI_ONLINE_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="COD_AUTORIZZAZIONE")
	public String getCodAutorizzazione() {
		return this.codAutorizzazione;
	}

	public void setCodAutorizzazione(String codAutorizzazione) {
		this.codAutorizzazione = codAutorizzazione;
	}


	@Column(name="DE_OPERAZIONE")
	public String getDeOperazione() {
		return this.deOperazione;
	}

	public void setDeOperazione(String deOperazione) {
		this.deOperazione = deOperazione;
	}


	@Column(name="ID_OPERAZIONE")
	public String getIdOperazione() {
		return this.idOperazione;
	}

	public void setIdOperazione(String idOperazione) {
		this.idOperazione = idOperazione;
	}


	@Column(name="NUM_OPERAZIONE")
	public String getNumOperazione() {
		return this.numOperazione;
	}

	public void setNumOperazione(String numOperazione) {
		this.numOperazione = numOperazione;
	}


	@Column(name="SESSION_ID_SISTEMA")
	public String getSessionIdSistema() {
		return this.sessionIdSistema;
	}

	public void setSessionIdSistema(String sessionIdSistema) {
		this.sessionIdSistema = sessionIdSistema;
	}


	@Column(name="SESSION_ID_TERMINALE")
	public String getSessionIdTerminale() {
		return this.sessionIdTerminale;
	}

	public void setSessionIdTerminale(String sessionIdTerminale) {
		this.sessionIdTerminale = sessionIdTerminale;
	}


	@Column(name="SESSION_ID_TIMBRO")
	public String getSessionIdTimbro() {
		return this.sessionIdTimbro;
	}

	public void setSessionIdTimbro(String sessionIdTimbro) {
		this.sessionIdTimbro = sessionIdTimbro;
	}


	@Column(name="SESSION_ID_TOKEN")
	public String getSessionIdToken() {
		return this.sessionIdToken;
	}

	public void setSessionIdToken(String sessionIdToken) {
		this.sessionIdToken = sessionIdToken;
	}


	@Column(name="TI_OPERAZIONE")
	public String getTiOperazione() {
		return this.tiOperazione;
	}

	public void setTiOperazione(String tiOperazione) {
		this.tiOperazione = tiOperazione;
	}


	@Column(name="TS_OPERAZIONE")
	public Timestamp getTsOperazione() {
		return this.tsOperazione;
	}

	public void setTsOperazione(Timestamp tsOperazione) {
		this.tsOperazione = tsOperazione;
	}


	//bi-directional many-to-one association to DistintePagamento
    @ManyToOne
	@JoinColumn(name="ID_DISTINTE_PAGAMENTO")
	public DistintePagamento getDistintePagamento() {
		return this.distintePagamento;
	}

	public void setDistintePagamento(DistintePagamento distintePagamento) {
		this.distintePagamento = distintePagamento;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagamentiOnline [codAutorizzazione=");
		builder.append(codAutorizzazione);
		builder.append(", deOperazione=");
		builder.append(deOperazione);
		builder.append(", idOperazione=");
		builder.append(idOperazione);
		builder.append(", numOperazione=");
		builder.append(numOperazione);
		builder.append(", sessionIdSistema=");
		builder.append(sessionIdSistema);
		builder.append(", sessionIdTerminale=");
		builder.append(sessionIdTerminale);
		builder.append(", sessionIdTimbro=");
		builder.append(sessionIdTimbro);
		builder.append(", sessionIdToken=");
		builder.append(sessionIdToken);
		builder.append(", tiOperazione=");
		builder.append(tiOperazione);
		builder.append(", tsOperazione=");
		builder.append(tsOperazione);
		//builder.append(", distintePagamento=");
		//builder.append(distintePagamento);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
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