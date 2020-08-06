package it.tasgroup.iris.dto.anonymous.ddp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DDPAnonymousDTOLight implements Serializable {

	private Date dataEmissione;
	
	private String id;
	
	private Date dataAnnullamento;
	
	private String opAnnullamento;
	
	private String opInserimento;
	
	private String intestatario;
	
	private Integer numCondizioni;
	
	private BigDecimal importo;
	
	private BigDecimal importoCommissioni;
	
	public Date getDataEmissione() {
		return dataEmissione;
	}
	
	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}
	
	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

	public Integer getNumCondizioni() {
		return numCondizioni;
	}

	public void setNumCondizioni(Integer numCondizioni) {
		this.numCondizioni = numCondizioni;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}

	public String getOpAnnullamento() {
		return opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	
}
