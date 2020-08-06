package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the STAT_PAGAMENTI_MESE database table.
 * 
 */
@Entity
@Table(name="STAT_PAGAMENTI_MESE")

public class StatistichePagamentiMese extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private StatistichePagamentiMesePK id;
	private Integer numeroPagamenti; 
	private BigDecimal importo;
	private String opInserimento;
	private Timestamp tsInserimento;
	
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
	public Integer getNumeroPagamenti() {
		return numeroPagamenti;
	}

	public void setNumeroPagamenti(Integer numeroPagamenti) {
		this.numeroPagamenti = numeroPagamenti;
	}

	@Column(name="IMPORTO")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

}