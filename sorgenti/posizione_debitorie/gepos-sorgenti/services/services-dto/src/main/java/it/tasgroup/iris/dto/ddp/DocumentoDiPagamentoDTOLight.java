package it.tasgroup.iris.dto.ddp;

import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DocumentoDiPagamentoDTOLight implements Serializable {

	private Date dataEmissione;
	private String id;
	private EnumTipoDDP tipoDDP;
	private EnumStatoDDP statoDDP;
	private Date dataAnnullamento;
	private Integer numCondizioni;
	private String opInserimento;
	private String intestatario;
	private String email;
	private BigDecimal importo;
	private BigDecimal importoNetto;
	private String codPagante;
	
	private BigDecimal importoCommissioni;
	
	private List<CondizioneDDPDTO> carrello;
	
	private DistintePagamentoDTOLight distinta;
	
	public EnumStatoDDP getStatoDDP() {
		return statoDDP;
	}
	
	public void setStatoDDP(EnumStatoDDP statoDDP) {
		this.statoDDP = statoDDP;
	}
	
	public Date getDataEmissione() {
		return dataEmissione;
	}
	
	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	
	public EnumTipoDDP getTipoDDP() {
		return tipoDDP;
	}
	
	public void setTipoDDP(EnumTipoDDP tipoDDP) {
		this.tipoDDP = tipoDDP;
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

	public List<CondizioneDDPDTO> getCarrello() {
		return carrello;
	}

	public void setCarrello(List<CondizioneDDPDTO> carrello) {
		this.carrello = carrello;
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

	public DistintePagamentoDTOLight getDistinta() {
		return distinta;
	}

	public void setDistinta(DistintePagamentoDTOLight distinta) {
		this.distinta = distinta;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodPagante() {
		return codPagante;
	}

	public void setCodPagante(String codPagante) {
		this.codPagante = codPagante;
	}

	public BigDecimal getImportoNetto() {
		return importoNetto;
	}

	public void setImportoNetto(BigDecimal importoNetto) {
		this.importoNetto = importoNetto;
	}
	
}
