package it.tasgroup.idp.domain.riversamenti;

import it.tasgroup.idp.domain.BaseIdEntity;
import it.tasgroup.iris2.pagamenti.IncassiBonificiRh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the RIVERSAMENTI database table.
 * 
 */
@Entity
@Table(name="RIVERSAMENTI")
@NamedQueries({
@NamedQuery(
	name="Riversamenti.findAll", 
	query=
	"SELECT r FROM Riversamenti r"),
//@NamedQuery(
//		name="FindIncassiDaRiversare", 
//		query=
//		"SELECT RH.ID, RH.ID_RICONCILIAZIONE, RH.ID_RENDICONTAZIONE, RIV.ID"
//		+ " FROM RIVERSAMENTI RIV,"
//		+ " 	 INCASSI_BONIFICI_RH  RH, " 
//		+ " 	 RENDICONTAZIONI REND " 
//		+ " WHERE "
//		+ " RH.ID = RIV.ID_INCASSI "
//		+ " AND RH.ID_RENDICONTAZIONE = REND.ID "
//		+ " AND RIV.STATO='IN_CORSO'")
	@NamedQuery(
			name="FindIncassiDaRiversare", 
			query=
			"SELECT RIV "
			+ " FROM Riversamenti RIV 	"
			+ " 	 join fetch RIV.incassiBonificiRh  " 
			+ " WHERE "
			+ " RIV.stato = :stato"),
	@NamedQuery(
			name="FindLast", 
			query=
			"SELECT RIV "
			+ " FROM Riversamenti RIV 	"
			+ " 	 WHERE RIV.incassiBonificiRh is not null "					
			+ " 	 order by RIV.tsInserimento desc   " ),
	@NamedQuery(
			name="Last25Riv", 
			query=
			"SELECT RIV " +
			" FROM Riversamenti as RIV "
			+ " 	 WHERE RIV.incassiBonificiRh is not null "	
			+ " 	 ORDER BY RIV.tsInserimento desc ")				
})

public class Riversamenti extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private short flagChiusura;
	private String note;
	private String stato;
	
	private String iban;
	
	private IncassiBonificiRh incassiBonificiRh;

	public Riversamenti() {
	}

	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="riversamenti_pk_gen")	
	@SequenceGenerator(
	    name="riversamenti_pk_gen",
	    sequenceName="RIVERSAMENTI_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		


	@Column(name="FLAG_CHIUSURA")
	public short getFlagChiusura() {
		return this.flagChiusura;
	}

	public void setFlagChiusura(short flagChiusura) {
		this.flagChiusura = flagChiusura;
	}


	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToOne
	@JoinColumn(name="ID_INCASSI_BONIFICI_RH")
	public IncassiBonificiRh getIncassiBonificiRh() {
		return this.incassiBonificiRh;
	}

	public void setIncassiBonificiRh(IncassiBonificiRh incassiBonificiRh) {
		this.incassiBonificiRh = incassiBonificiRh;
	}




	public String getIban() {
		return iban;
	}




	public void setIban(String iban) {
		this.iban = iban;
	}




//	@Column(name="ANOMALIA")
//	public String getAnomalia() {
//		return anomalia;
//	}
//	@Column(name="ID_RICONCILIAZIONE")
//	public String getIdRiconciliazione() {
//		return this.idRiconciliazione;
//	}
//	public void setAnomalia(String anomalia) {
//		this.anomalia = anomalia;
//	}
//	public void setIdRiconciliazione(String idRiconciliazione) {
//		this.idRiconciliazione = idRiconciliazione;
//	}

	
	
}