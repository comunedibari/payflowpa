package it.tasgroup.iris2.pagamenti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseIdEntity;


/**
 * The persistent class for the JLTPAGA database table.
 * 
 */

@NamedQueries({
@NamedQuery(
	name="EsportazioniByPrenotazione", 
	query = "SELECT Esportazioni FROM Esportazioni esportazioni " +
			" WHERE esportazioni.prenotazione = :prenotazione")
})

@Entity
@Table(name=" ESPORTAZIONI")
public class Esportazioni extends BaseIdEntity {

	private static final long serialVersionUID = 1L;
	
	private Prenotazioni prenotazione;
	private String nomeFile;
	private String formato;
	private String compressione;
	private Long lenDati;
	
	private byte[] dati;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="esportazioni_pk_gen")	
	@SequenceGenerator(
		    name="esportazioni_pk_gen",
		    sequenceName="ESPORTAZIONI_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 			
	
//	@OneToOne
	@ManyToOne(targetEntity=Prenotazioni.class)
	@JoinColumn(name="ID_PRENOTAZIONE")
	public Prenotazioni getPrenotazione() {
		return this.prenotazione;
	}
	public void setPrenotazione(Prenotazioni prenotazione) {
		this.prenotazione = prenotazione;
	}

	@Column(name="NOME_FILE")
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	@Column(name="FORMATO")
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	@Column(name="COMPRESSIONE")
	public String getCompressione() {
		return compressione;
	}
	public void setCompressione(String compressione) {
		this.compressione = compressione;
	}
	@Column(name="LEN_DATI")
	public Long getLenDati() {
		return lenDati;
	}
	public void setLenDati(Long lenDati) {
		this.lenDati = lenDati;
	}
	
	@Lob()
	public byte[] getDati() {
		return dati;
	}
	public void setDati(byte[] dati) {
		this.dati = dati;
	}


	
}