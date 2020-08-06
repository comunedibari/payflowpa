package it.nch.idp.backoffice.statistiche;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RiepilogoCruscottoVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mese;
	private String meseTxt;
	private Integer posizioniInserite;
	private BigDecimal importoPosizioniInserite;
	private Integer pagamentiEseguiti;
	private BigDecimal importoPagamentiEseguiti;
	private Integer pagamentiSpontaneiEseguiti;
	private BigDecimal importoPagamentiSpontaneiEseguiti;
	
	public int getMese() {
		return mese;
	}
	public void setMese(int mese) {
		this.mese = mese;
	}
	public Integer getPosizioniInserite() {
		return posizioniInserite;
	}
	public void setPosizioniInserite(Integer posizioniInserite) {
		this.posizioniInserite = posizioniInserite;
	}
	public BigDecimal getImportoPosizioniInserite() {
		return importoPosizioniInserite;
	}
	public void setImportoPosizioniInserite(BigDecimal importoPosizioniInserite) {
		this.importoPosizioniInserite = importoPosizioniInserite;
	}
	public Integer getPagamentiEseguiti() {
		return pagamentiEseguiti;
	}
	public void setPagamentiEseguiti(Integer pagamentiEseguiti) {
		this.pagamentiEseguiti = pagamentiEseguiti;
	}
	public BigDecimal getImportoPagamentiEseguiti() {
		return importoPagamentiEseguiti;
	}
	public void setImportoPagamentiEseguiti(BigDecimal importoPagamentiEseguiti) {
		this.importoPagamentiEseguiti = importoPagamentiEseguiti;
	}
	public Integer getPagamentiSpontaneiEseguiti() {
		return pagamentiSpontaneiEseguiti;
	}
	public void setPagamentiSpontaneiEseguiti(Integer pagamentiSpontaneiEseguiti) {
		this.pagamentiSpontaneiEseguiti = pagamentiSpontaneiEseguiti;
	}
	public BigDecimal getImportoPagamentiSpontaneiEseguiti() {
		return importoPagamentiSpontaneiEseguiti;
	}
	public void setImportoPagamentiSpontaneiEseguiti(BigDecimal importoPagamentiSpontaneiEseguiti) {
		this.importoPagamentiSpontaneiEseguiti = importoPagamentiSpontaneiEseguiti;
	}

	public String getMeseTxt() {
		return meseTxt;
	}
	public void setMeseTxt(String meseTxt) {
		this.meseTxt = meseTxt;
	}

	public static RiepilogoCruscottoVO build(CruscottoVO posizione, CruscottoVO pagamento, CruscottoVO pagamentoSpontaneo) {
		RiepilogoCruscottoVO res = new RiepilogoCruscottoVO();
		res.mese = posizione.getMese();
		res.posizioniInserite = posizione.getOccorrenze();
		res.importoPosizioniInserite = posizione.getImporto() != null ? posizione.getImporto().setScale(2, RoundingMode.UNNECESSARY) : null;
		res.pagamentiEseguiti = pagamento.getOccorrenze();
		res.importoPagamentiEseguiti = pagamento.getImporto() != null ? pagamento.getImporto().setScale(2, RoundingMode.UNNECESSARY) : null; 
		res.pagamentiSpontaneiEseguiti = pagamentoSpontaneo.getOccorrenze();
		res.importoPagamentiSpontaneiEseguiti = pagamentoSpontaneo.getImporto() != null ? pagamentoSpontaneo.getImporto().setScale(2, RoundingMode.UNNECESSARY) : null; 
		return res;
	}
	
}
