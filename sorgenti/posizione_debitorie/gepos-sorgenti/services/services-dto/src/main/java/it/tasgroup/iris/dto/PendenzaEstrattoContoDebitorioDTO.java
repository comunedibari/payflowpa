package it.tasgroup.iris.dto;

import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;

import java.math.BigDecimal;
import java.util.List;

public class PendenzaEstrattoContoDebitorioDTO implements IPendenzaDTO{
	
	private String idPendenza;
	private String statoDocumento;
	private String idPendenzaEnte;
	private String idEnte;
	private String cdTrbEnte;
	private BigDecimal importo;
	private BigDecimal importoPagato;
	private String descrizioneEnte;
	private String tipoTributo;
	private String descrizioneTributo;
	private String causale;
	private String note;
	private Long idTributoStrutturato;
	private DistintePagamentoDTOLight pagamentoEseguito;
	private Object tributoStrutturato;
	
	private String codFiscaleCreditore;
	
	private String codFiscaleDebitore;

	// TODO PAZZIK TRASFORMARE IN DTO
	private List condizioni;
	
	public String getStatoDocumento() {
		return statoDocumento;
	}
	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}
	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}
	public String getTipoTributo() {
		return tipoTributo;
	}
	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}
	public String getDescrizioneTributo() {
		return descrizioneTributo;
	}
	public void setDescrizioneTributo(String descrizioneTributo) {
		this.descrizioneTributo = descrizioneTributo;
	}
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public List getCondizioni() {
		return condizioni;
	}
	public void setCondizioni(List condizioni) {
		this.condizioni = condizioni;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public BigDecimal getImportoPagato() {
		return importoPagato;
	}
	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	public Long getIdTributoStrutturato() {
		return idTributoStrutturato;
	}
	public void setIdTributoStrutturato(Long idTributoStrutturato) {
		this.idTributoStrutturato = idTributoStrutturato;
	}
	public Object getTributoStrutturato() {
		return tributoStrutturato;
	}
	public void setTributoStrutturato(Object tributoStrutturato) {
		this.tributoStrutturato = tributoStrutturato;
	}
	
	public DistintePagamentoDTOLight getPagamentoEseguito() {
		return pagamentoEseguito;
	}
	
	public void setPagamentoEseguito(DistintePagamentoDTOLight pagamentoEseguito) {
		this.pagamentoEseguito = pagamentoEseguito;
	}
	
	public String getCodFiscaleCreditore() {
		return codFiscaleCreditore;
	}
	public void setCodFiscaleCreditore(String codFiscaleCreditore) {
		this.codFiscaleCreditore = codFiscaleCreditore;
	}
	
	public String getCodFiscaleDebitore() {
		return codFiscaleDebitore;
	}
	
	public void setCodFiscaleDebitore(String codFiscaleDebitore) {
		this.codFiscaleDebitore = codFiscaleDebitore;
	}
}	
