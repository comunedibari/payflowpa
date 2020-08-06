package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class BonificiRiaccreditoDTO implements Serializable{
	
	private Long id;
	private Timestamp dataEsecuzione;
	private String ibanBeneficiario;
	private String ragioneSocialeBeneficiario;
	private BigDecimal importo;
	private String stato;
	private String codiceUnivoco;
	private Integer numBozze;
	
	private List<BozzeBonificiRiaccreditoDTO> bozzeBonificiRiaccredito;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getDataEsecuzione() {
		return dataEsecuzione;
	}
	public void setDataEsecuzione(Timestamp dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}
	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}
	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}
	public String getRagioneSocialeBeneficiario() {
		return ragioneSocialeBeneficiario;
	}
	public void setRagioneSocialeBeneficiario(String ragioneSocialeBeneficiario) {
		this.ragioneSocialeBeneficiario = ragioneSocialeBeneficiario;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getCodiceUnivoco() {
		return codiceUnivoco;
	}
	public void setCodiceUnivoco(String codiceUnivoco) {
		this.codiceUnivoco = codiceUnivoco;
	}
	
	public List<BozzeBonificiRiaccreditoDTO> getBozzeBonificiRiaccredito() {
		return bozzeBonificiRiaccredito;
	}
	public void setBozzeBonificiRiaccredito(
			List<BozzeBonificiRiaccreditoDTO> bozzeBonificiRiaccredito) {
		this.bozzeBonificiRiaccredito = bozzeBonificiRiaccredito;
	}
	
	public Integer getNumBozze() {
		return numBozze;
	}
	public void setNumBozze(Integer numBozze) {
		this.numBozze = numBozze;
	}
}