package it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio;

import java.math.BigDecimal;

public class BilancioFlussoExportDto {

	private String codIud;
	private String codCapitolo;
	private String codUfficio;
	private String codAccertamento;
	private BigDecimal numImporto;

	public BilancioFlussoExportDto() {
		super();
	}

	public BilancioFlussoExportDto(String codIud, String codCapitolo, String codUfficio, String codAccertamento,
			BigDecimal numImporto) {
		super();
		this.codIud = codIud;
		this.codCapitolo = codCapitolo;
		this.codUfficio = codUfficio;
		this.codAccertamento = codAccertamento;
		this.numImporto = numImporto;
	}

	public String getCodIud() {
		return codIud;
	}

	public void setCodIud(String codIud) {
		this.codIud = codIud;
	}

	public String getCodCapitolo() {
		return codCapitolo;
	}

	public void setCodCapitolo(String codCapitolo) {
		this.codCapitolo = codCapitolo;
	}

	public String getCodUfficio() {
		return codUfficio;
	}

	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}

	public String getCodAccertamento() {
		return codAccertamento;
	}

	public void setCodAccertamento(String codAccertamento) {
		this.codAccertamento = codAccertamento;
	}

	public BigDecimal getNumImporto() {
		return numImporto;
	}

	public void setNumImporto(BigDecimal numImporto) {
		this.numImporto = numImporto;
	}

}
