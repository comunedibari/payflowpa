package it.nch.pagamenti.nodopagamentispc;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatiRicevutaPagamentoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codiceContestoPagamento;
	private String riferimentoMessaggioRichiesta;
	private Date riferimentoDataRichiesta;
	private String codiceEsitoPagamento;
	private String identificativoUnivocoVersamento;
	private String identificativoFiscaleCreditore;
	private BigDecimal importoTotale;
	private String codiceIdentificativoUnivocoPSP; 
	private String tipoIdentificativoPSP;
	private String descrizionePSP;

	
	private List<DatiSingoloPagamentoVO> datiPagamento;

	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}

	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}

	public String getRiferimentoMessaggioRichiesta() {
		return riferimentoMessaggioRichiesta;
	}

	public void setRiferimentoMessaggioRichiesta(String riferimentoMessaggioRichiesta) {
		this.riferimentoMessaggioRichiesta = riferimentoMessaggioRichiesta;
	}

	public Date getRiferimentoDataRichiesta() {
		return riferimentoDataRichiesta;
	}

	public void setRiferimentoDataRichiesta(Date riferimentoDataRichiesta) {
		this.riferimentoDataRichiesta = riferimentoDataRichiesta;
	}

	public String getCodiceEsitoPagamento() {
		return codiceEsitoPagamento;
	}

	public void setCodiceEsitoPagamento(String codiceEsitoPagamento) {
		this.codiceEsitoPagamento = codiceEsitoPagamento;
	}

	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}

	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}

	public List<DatiSingoloPagamentoVO> getDatiPagamento() {
		if(datiPagamento == null) 
			datiPagamento = new ArrayList<DatiSingoloPagamentoVO>();
		return datiPagamento;
	}

	public void setDatiVersamento(List<DatiSingoloPagamentoVO> datiPagamento) {
		this.datiPagamento = datiPagamento;
	}
	
	public void addDatiSingoloVersamento(DatiSingoloPagamentoVO datiSingoloPagamentoVO) {
		getDatiPagamento().add(datiSingoloPagamentoVO);
	}
	
	public StatiPagamentiIris getStatoPagamentoIris() {

		/*
		0 = PAGAMENTO ESEGUITO				ESEGUITO
		1 = PAGAMENTO NON ESEGUITO			NON ESEGUITO
		2 = PAGAMENTO PARZIALMENTE ESEGUITO PARZ. ESEGUITO 
		3 = DECORRENZA TERMINI              ANNULLATO
		4 = DECORRENZA TERMINI PARZIALE     PARZ. ESEGUITO
		
		*/			
		
		if("0".equals(codiceEsitoPagamento)) {
			return StatiPagamentiIris.ESEGUITO;
			
		} else if("1".equals(codiceEsitoPagamento)) {
			return StatiPagamentiIris.NON_ESEGUITO;
			
		} else if("2".equals(codiceEsitoPagamento)) {
			return StatiPagamentiIris.PARZIALMENTE_ESEGUITO;
			
		} else if("3".equals(codiceEsitoPagamento)) {
			return StatiPagamentiIris.ANNULLATO;
			
		} else if("4".equals(codiceEsitoPagamento)) {
			return StatiPagamentiIris.PARZIALMENTE_ESEGUITO;
			
		} else {
			return null;
			
		}
	}

	public String getIdentificativoFiscaleCreditore() {
		return identificativoFiscaleCreditore;
	}

	public void setIdentificativoFiscaleCreditore(
			String identificativoFiscaleCreditore) {
		this.identificativoFiscaleCreditore = identificativoFiscaleCreditore;
	}

	public BigDecimal getImportoTotale() {
		return importoTotale;
	}

	public void setImportoTotale(BigDecimal importoTotale) {
		this.importoTotale = importoTotale;
	}

	public String getCodiceIdentificativoUnivocoPSP() {
		return codiceIdentificativoUnivocoPSP;
	}

	public void setCodiceIdentificativoUnivocoPSP(String codiceIdentificativoUnivocoPSP) {
		this.codiceIdentificativoUnivocoPSP = codiceIdentificativoUnivocoPSP;
	}

	public String getTipoIdentificativoPSP() {
		return tipoIdentificativoPSP;
	}

	public void setTipoIdentificativoPSP(String tipoIdentificativoPSP) {
		this.tipoIdentificativoPSP = tipoIdentificativoPSP;
	}

	public String getDescrizionePSP() {
		return descrizionePSP;
	}

	public void setDescrizionePSP(String descrizionePSP) {
		this.descrizionePSP = descrizionePSP;
	}
	
}
