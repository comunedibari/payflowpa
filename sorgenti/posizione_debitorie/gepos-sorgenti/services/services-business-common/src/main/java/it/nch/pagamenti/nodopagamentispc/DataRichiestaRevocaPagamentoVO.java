/**
 * 
 */
package it.nch.pagamenti.nodopagamentispc;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 *
 */
public class DataRichiestaRevocaPagamentoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String      identificativoDominio; 
	private String      identificativoUnivocoVersamento;
	private String      codiceContestoPagamento;
	private String      identificativoMessaggioRevoca;
	private Timestamp   dataOraRichiestaRevoca;
	private String      tipoRevoca;
	private List<DatiSingolaRevocaVO> datiRevoca;
	
	
	public String getIdentificativoDominio() {
		return identificativoDominio;
	}
	public void setIdentificativoDominio(String identificativoDominio) {
		this.identificativoDominio = identificativoDominio;
	}
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}
	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	public String getIdentificativoMessaggioRevoca() {
		return identificativoMessaggioRevoca;
	}
	public void setIdentificativoMessaggioRevoca(String identificativoMessaggioRevoca) {
		this.identificativoMessaggioRevoca = identificativoMessaggioRevoca;
	}
	public Timestamp getDataOraRichiestaRevoca() {
		return dataOraRichiestaRevoca;
	}
	public void setDataOraRichiestaRevoca(Timestamp dataOraRichiestaRevoca) {
		this.dataOraRichiestaRevoca = dataOraRichiestaRevoca;
	}
	public String getTipoRevoca() {
		return tipoRevoca;
	}
	public void setTipoRevoca(String tipoRevoca) {
		this.tipoRevoca = tipoRevoca;
	}
	public List<DatiSingolaRevocaVO> getDatiRevoca() {
		if(datiRevoca == null) 
			datiRevoca = new ArrayList<DatiSingolaRevocaVO>();
		return datiRevoca;
	}
	public void setDatiRevoca(List<DatiSingolaRevocaVO> datiRevoca) {
		this.datiRevoca = datiRevoca;
	}

	
}
