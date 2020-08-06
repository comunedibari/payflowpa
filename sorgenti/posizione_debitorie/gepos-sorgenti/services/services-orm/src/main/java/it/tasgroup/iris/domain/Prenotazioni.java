package it.tasgroup.iris.domain;

import it.nch.is.fo.profilo.Intestatari;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the JLTPAGA database table.
 * 
 */
@Entity
@Table(name="PRENOTAZIONI")
public class Prenotazioni extends BaseIdEntity {

	private Intestatari intestatario;
	private String operatoreRich;
	private String codRich;
	private String tipoServizio;
	private String tipoEsportazione;
	private Timestamp tsBegin;
	private Timestamp tsEnd;
	private String stato;
	private String opInserimento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;
	
	private Set<Esportazioni> esportazioni;
	private Set<InfoPrenotazioni> infoPrenotazioni;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="prenotazioni_pk_gen")	
	@SequenceGenerator(
		    name="prenotazioni_pk_gen",
		    sequenceName="PRENOTAZIONI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 			

	@OneToMany(targetEntity = Esportazioni.class, mappedBy = "prenotazione",fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	public Set<Esportazioni> getEsportazioni() {
		return this.esportazioni;
	}
	public void setEsportazioni(Set<Esportazioni> esportazioni) {
		this.esportazioni = esportazioni;
	}

	@ManyToOne
	@JoinColumn(name="INTESTATARIO")
	public Intestatari getIntestatario() {
		return this.intestatario;
	}
	public void setIntestatario(Intestatari intestatari) {
		this.intestatario = intestatari;
	}

	@Column(name="OPERATORE_RICH")
	public String getOperatoreRich() {
		return operatoreRich;
	}
	public void setOperatoreRich(String operatoreRich) {
		this.operatoreRich = operatoreRich;
	}
	@Column(name="COD_RICH")
	public String getCodRich() {
		return codRich;
	}
	public void setCodRich(String codRich) {
		this.codRich = codRich;
	}
	@Column(name="TIPOSERVIZIO")
	public String getTipoServizio() {
		return tipoServizio;
	}
	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}
	@Column(name="TIPO_ESPORTAZIONE")
	public String getTipoEsportazione() {
		return tipoEsportazione;
	}
	public void setTipoEsportazione(String tipoEsportazione) {
		this.tipoEsportazione = tipoEsportazione;
	}

	@Column(name="TIMESTAMP_BEGIN")
	public Timestamp getTsBegin() {
		return tsBegin;
	}
	public void setTsBegin(Timestamp tsBegin) {
		this.tsBegin = tsBegin;
	}
	@Column(name="TIMESTAMP_END")
	public Timestamp getTsEnd() {
		return tsEnd;
	}
	public void setTsEnd(Timestamp tsEnd) {
		this.tsEnd = tsEnd;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
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
	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	
	@OneToMany(targetEntity = InfoPrenotazioni.class, mappedBy = "prenotazione", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public Set<InfoPrenotazioni> getInfoPrenotazioni() {
		return infoPrenotazioni;
	}

	public void setInfoPrenotazioni(Set<InfoPrenotazioni> infoPrenotazioni) {
		this.infoPrenotazioni = infoPrenotazioni;
	}	
	
	
}