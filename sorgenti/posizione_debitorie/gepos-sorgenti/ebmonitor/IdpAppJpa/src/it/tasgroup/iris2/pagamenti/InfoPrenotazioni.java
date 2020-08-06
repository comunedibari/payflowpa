package it.tasgroup.iris2.pagamenti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseIdEntity;

@NamedQueries({
@NamedQuery(
	name="InfoPrenotazioniByIdProcessoIdRichiedenteTipoProcesso", 
	query = "SELECT infoPrenotazioni FROM InfoPrenotazioni infoPrenotazioni " +
			" WHERE infoPrenotazioni.idProcesso = :idProcesso" +
			" AND infoPrenotazioni.idRichiedente = :idRichiedente" +
			" AND infoPrenotazioni.tipoProcesso = :tipoProcesso")
})

@Entity
@Table(name=" INFO_PRENOTAZIONI")
public class InfoPrenotazioni extends BaseIdEntity {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idOrigineDati;
	private Prenotazioni prenotazione;
	private String tipoProcesso;
	private String idProcesso;
	private String idRichiedente;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="info_prenotazioni_pk_gen")	
	@SequenceGenerator(
		    name="info_prenotazioni_pk_gen",
		    sequenceName="INFO_PRENOTAZIONI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 			
	
	@Column(name="ID_ORIGINE_DATI")
	public Long getIdOrigineDati() {
		return idOrigineDati;
	}
	public void setIdOrigineDati(Long idOrigineDati) {
		this.idOrigineDati = idOrigineDati;
	}

	@ManyToOne(targetEntity=Prenotazioni.class)
	@JoinColumn(name="ID_PRENOTAZIONE")
	public Prenotazioni getPrenotazione() {
		return this.prenotazione;
	}
	public void setPrenotazione(Prenotazioni prenotazione) {
		this.prenotazione = prenotazione;
	}

	@Column(name="TIPO_PROCESSO")
	public String getTipoProcesso() {
		return tipoProcesso;
	}
	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}
	
	@Column(name="ID_PROCESSO")
	public String getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(String idProcesso) {
		this.idProcesso = idProcesso;
	}
	
	@Column(name="ID_RICHIEDENTE")
	public String getIdRichiedente() {
		return idRichiedente;
	}
	public void setIdRichiedente(String idRichiedente) {
		this.idRichiedente = idRichiedente;
	}
	
}