package it.tasgroup.iris2.pagamenti;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseIdEntity;

/**
 * The persistent class for the JLTPAGA database table.
 * 
 */
@Entity
@Table(name="LOG_ELABORAZIONE_BATCH")

public class LogElaborazioneBatch extends BaseIdEntity {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String tipoElaborazione;
	private String esito;
	private Timestamp tsElaborazione;

	public LogElaborazioneBatch() {}
	
	public LogElaborazioneBatch(String tipo) {
		tipoElaborazione = tipo;
	}
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="logElaborazioneBatch_pk_gen")	
	@SequenceGenerator(
	    name="logElaborazioneBatch_pk_gen",
	    sequenceName="LOG_ELABORAZIONE_BATCH_ID_SEQ",
	    allocationSize=1
	)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	}
	@Column(name="TIPO_ELABORAZIONE")
	public String getTipoElaborazione() {
		return tipoElaborazione;
	}
	public void setTipoElaborazione(String tipoElaborazione) {
		this.tipoElaborazione = tipoElaborazione;
	}
	@Column(name="ESITO")
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	@Column(name="TS_ELABORAZIONE")
	public Timestamp getTsElaborazione() {
		return tsElaborazione;
	}
	public void setTsElaborazione(Timestamp tsElaborazione) {
		this.tsElaborazione = tsElaborazione;
	} 	
	
	@Override
	public String toString() {
		return 
			"LogElaborazioneBatch "
			+ "["
			+ "id: " 				+ id
			+ " - tipoElaborazione: " 	+ tipoElaborazione
			+ " - esito: " 			+ esito
			+ " - tsElaborazione: " + tsElaborazione
			+ "]";
	}
	

}
