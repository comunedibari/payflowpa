package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

/**
 * Bean che mappa la form della statistica: Dettaglio Accertamenti.
 * 
 */
public class CruscottoIncassiAccertamentiDettaglioDto extends CruscottoIncassiAccertamentiInputDto {
	
	private String codAccertamento; 
	

	public String getCodAccertamento() {
		return codAccertamento;
	}

	public void setCodAccertamento(String codAccertamento) {
		this.codAccertamento = codAccertamento;
	}

}