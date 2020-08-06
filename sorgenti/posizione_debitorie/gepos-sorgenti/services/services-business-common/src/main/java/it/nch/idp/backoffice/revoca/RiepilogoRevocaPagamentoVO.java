package it.nch.idp.backoffice.revoca;

import java.math.BigDecimal;

import it.tasgroup.services.util.enumeration.EnumTipoRichiestaRevoca;

public class RiepilogoRevocaPagamentoVO {
	String iuv;
	String descrizioneDebito; 
	String identificativoDebito;
	BigDecimal importo;
	String causaleDebito;
	String versante;
	String idRevoca;
	String flagIncasso;
	
	public RiepilogoRevocaPagamentoVO(){}
	
	public RiepilogoRevocaPagamentoVO(
						String iuv,
						String descrizioneDebito, 
						String identificativoDebito,
						BigDecimal importo,
						String causaleDebito,
						String versante,
						String flagIncasso) {
		this.setIuv(iuv);
		this.setDescrizioneDebito(descrizioneDebito); 
		this.setIdentificativoDebito(identificativoDebito);
		this.setImporto(importo);
		this.setCausaleDebito(causaleDebito);
		this.setVersante(versante);
		this.setFlagIncasso(flagIncasso);
	}
	
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getDescrizioneDebito() {
		return descrizioneDebito;
	}
	public void setDescrizioneDebito(String descrizioneDebito) {
		this.descrizioneDebito = descrizioneDebito;
	}
	public String getIdentificativoDebito() {
		return identificativoDebito;
	}
	public void setIdentificativoDebito(String identificativoDebito) {
		this.identificativoDebito = identificativoDebito;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getCausaleDebito() {
		return causaleDebito;
	}
	public void setCausaleDebito(String causaleDebito) {
		this.causaleDebito = causaleDebito;
	}
	public String getVersante() {
		return versante;
	}
	public void setVersante(String versante) {
		this.versante = versante;
	}
	
	public String getIdRevoca() {
		return idRevoca;
	}

	public void setIdRevoca(String idRevoca) {
		this.idRevoca = idRevoca;
	}

	public String getFlagIncasso() {
		return flagIncasso;
	}

	public void setFlagIncasso(String flagIncasso) {
		this.flagIncasso = flagIncasso;
	}
	
	public String getFlagIncassoBoundle() {
		return EnumTipoRichiestaRevoca.getByKey(flagIncasso).getChiaveBundle();
	}




}
