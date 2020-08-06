package it.tasgroup.iris2.pagamenti;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;


/**
 * The persistent class for the STAT_POSIZIONI_MESE database table.
 * 
 */
@Entity
@Table(name="STAT_POSIZIONI_MESE")

public class StatistichePosizioniMese extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private StatistichePosizioniMesePK id;
	private BigDecimal numeroPosizioni; 
	private BigDecimal importo;

	public StatistichePosizioniMese() {
	}

	@EmbeddedId
	public StatistichePosizioniMesePK getId() {
		return this.id;
	}

	public void setId(StatistichePosizioniMesePK id) {
		this.id = id;
	}
	
	@Column(name="NUMERO_POSIZIONI")
	public BigDecimal getNumeroPosizioni() {
		return numeroPosizioni;
	}

	public void setNumeroPosizioni(BigDecimal numeroPosizioni) {
		this.numeroPosizioni = numeroPosizioni;
	}

	@Column(name="IMPORTO")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	@Override
	public String toString() {
		return 
			" StatistichePosizioniMese "
			+ "["
			+ this.getId().toString()
			+ " numeroPosizioni: " 	+ numeroPosizioni
			+ " - importo: " 		+ NumberFormat.getNumberInstance(java.util.Locale.ITALY).format(importo)
			+ " - opInserimento: " 	+ getOpInserimento()
			+ " - tsInserimento: " + getTsInserimento()
			+ "]";
	}

}