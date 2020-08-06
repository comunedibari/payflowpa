package it.regioneveneto.mygov.payment.mypivot.domain.dto.bilancio;

public class BilancioDto {

	private String codUfficio;
	private String codTipoDovuto;
	private String codCapitolo;
	private String codAccertamento;
	private String importo;

	public String getCodUfficio() {
		return codUfficio;
	}

	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}

	public String getCodTipoDovuto() {
		return codTipoDovuto;
	}

	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}

	public String getCodCapitolo() {
		return codCapitolo;
	}

	public void setCodCapitolo(String codCapitolo) {
		this.codCapitolo = codCapitolo;
	}

	public String getCodAccertamento() {
		return codAccertamento;
	}

	public void setCodAccertamento(String codAccertamento) {
		this.codAccertamento = codAccertamento;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

}
