package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the STAT_POSIZIONI_MESE database table.
 * 
 */
@Entity
@Table(name="STAT_POSIZIONI_MESE")

public class StatistichePosizioniMese extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private StatistichePosizioniMesePK id;
	private Integer numeroPosizioni; 
	private BigDecimal importo;
	private String opInserimento;
	private Timestamp tsInserimento;

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
	public Integer getNumeroPosizioni() {
		return numeroPosizioni;
	}

	public void setNumeroPosizioni(Integer numeroPosizioni) {
		this.numeroPosizioni = numeroPosizioni;
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