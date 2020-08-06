package it.tasgroup.iris2.pagamenti;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.tasgroup.idp.domain.BaseEntity;

@Entity
@Table(name="STAT_PAGAMENTI_MESE")

/**
 * The persistent class for the STAT_PAGAMENTI_MESE database table.
 * 
 */

public class StatistichePagamentiMese extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private StatistichePagamentiMesePK id;
	private BigDecimal numeroPagamenti; 
	private BigDecimal importo;
	
	public StatistichePagamentiMese() {
	}

	@EmbeddedId
	public StatistichePagamentiMesePK getId() {
		return this.id;
	}

	public void setId(StatistichePagamentiMesePK id) {
		this.id = id;
	}
	
	@Column(name="NUMERO_PAGAMENTI")
	public BigDecimal getNumeroPagamenti() {
		return numeroPagamenti;
	}

	public void setNumeroPagamenti(BigDecimal numeroPagamenti) {
		this.numeroPagamenti = numeroPagamenti;
	}

	@Column(name="IMPORTO")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String toString() {
		return 
			" StatistichePagamentiMese "
			+ "["
			+ this.getId().toString()
			+ " numeroPagamenti: " 	+ String.valueOf(numeroPagamenti.doubleValue())
			+ " - importo: " 		+ NumberFormat.getNumberInstance(java.util.Locale.ITALY).format(importo)
			+ " - opInserimento: " 	+ getOpInserimento()
			+ " - tsInserimento: " + getTsInserimento()
			+ "]";
	}

}