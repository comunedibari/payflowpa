package it.nch.idp.posizionedebitoria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PosizioneDebitoriaInputVO implements Serializable {

	private Date dataEmissione_da;
	private Date dataEmissione_a;
	private Date dataPagamento_da;
	private Date dataPagamento_a;
	private Date dataScadenza_da;
	private Date dataScadenza_a;
	private String tipoTributo;
	private ArrayList<String> tipoTributoList = new ArrayList<String>();
	private String idPendenza;
	private String tributo;
	private String causale;
	private String destinatario;
	private BigDecimal importototale_da;
	private BigDecimal importototale_a;
	private String modoPagamento;
	private String filtroEnte;
	private String idEnte;
	private String intestatario;
	private String lapl;
	private String filtroStato;
	private String filtroTipoTributo;
	private String filtroEnteTributo;
	private String filtroModPagamento;
	private String ordinamento;
	private String ordinamentoTipo;
	private String tracciato;
	private String idFlusso;
	private String idCondizionePagamento;
	private String codicePendenza;
	private Integer annoRiferimento;

	private String codPagamento;
	private String filtroCodPagamento;
	private Date filtroTsInserimento_a;
	
	private Boolean filtroIncludiPagamentiInErrore;
	private Integer filtroFornitore;
	private Integer filtroEscludiFornitore;
	
	private boolean isDebitoriRich;

	private List idAvvisi;
	private List idAllegati;
	private String idDistinta;
	private List idPagamenti;
    private String iuv;
    
    private String riscossore;
    private String riferimento;
	
	public String getFiltroModPagamento() {
		return filtroModPagamento;
	}

	public void setFiltroModPagamento(String filtroModPagamento) {
		this.filtroModPagamento = filtroModPagamento;
	}

	public String getFiltroEnte() {
		return filtroEnte;
	}

	public String getFiltroStato() {
		return filtroStato;
	}

	public String getLapl() {
		return lapl;
	}

	public void setFiltroEnte(String string) {
		filtroEnte = string;
	}

	public void setFiltroStato(String string) {
		filtroStato = string;
	}

	public void setLapl(String string) {
		lapl = string;
	}

	public List getIdAvvisi() {
		return idAvvisi;
	}

	public void setIdAvvisi(List list) {
		idAvvisi = list;
	}

	public String getCausale() {
		return causale;
	}

	public Date getDataEmissione_a() {
		return dataEmissione_a;
	}

	public Date getDataEmissione_da() {
		return dataEmissione_da;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public BigDecimal getImportototale_a() {
		return importototale_a;
	}

	public BigDecimal getImportototale_da() {
		return importototale_da;
	}

	public String getModoPagamento() {
		return modoPagamento;
	}

	public String getTipoTributo() {
		return tipoTributo;
	}

	public void setCausale(String string) {
		causale = string;
	}

	public void setDataEmissione_a(Date date) {
		dataEmissione_a = date;
	}

	public void setDataEmissione_da(Date date) {
		dataEmissione_da = date;
	}

	public void setDestinatario(String string) {
		destinatario = string;
	}

	public void setImportototale_a(BigDecimal decimal1) {
		importototale_a = decimal1;
	}

	public void setImportototale_da(BigDecimal decimal1) {
		importototale_da = decimal1;
	}

	public void setModoPagamento(String string) {
		modoPagamento = string;
	}

	public void setTipoTributo(String string) {
		tipoTributo = string;
	}

	public ArrayList<String> getTipoTributoList() {
		return tipoTributoList;
	}
	
	public void addTipoTributoToList(String tipoTributo) {
		tipoTributoList.add(tipoTributo);
	}

	public void setTipoTributoList(ArrayList<String> tipoTributoList) {
		this.tipoTributoList = tipoTributoList;
	}

	public String getOrdinamento() {
		return ordinamento;
	}

	public String getOrdinamentoTipo() {
		return ordinamentoTipo;
	}

	public void setOrdinamento(String string) {
		ordinamento = string;
	}

	public void setOrdinamentoTipo(String string) {
		ordinamentoTipo = string;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String string) {
		idEnte = string;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String string) {
		tributo = string;
	}

	public List getIdAllegati() {
		return idAllegati;
	}

	public void setIdAllegati(List list) {
		idAllegati = list;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String string) {
		idPendenza = string;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(String string) {
		intestatario = string;
	}

	public String getFiltroTipoTributo() {
		return filtroTipoTributo;
	}

	public void setFiltroTipoTributo(String string) {
		filtroTipoTributo = string;
	}

	public Date getDataPagamento_a() {
		return dataPagamento_a;
	}

	public Date getDataPagamento_da() {
		return dataPagamento_da;
	}

	public void setDataPagamento_a(Date date) {
		dataPagamento_a = date;
	}

	public void setDataPagamento_da(Date date) {
		dataPagamento_da = date;
	}

	public String getTracciato() {
		return tracciato;
	}

	public void setTracciato(String string) {
		tracciato = string;
	}


	public String getIdDistinta() {
		return idDistinta;
	}

	public void setIdDistinta(String idDistinta) {
		this.idDistinta = idDistinta;
	}

	public String getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(String string) {
		idFlusso = string;
	}

	/**
	 * @return
	 */
	public String getIdCondizionePagamento() {
		return idCondizionePagamento;
	}

	/**
	 * @param string
	 */
	public void setIdCondizionePagamento(String string) {
		idCondizionePagamento = string;
	}

	/**
	 * @return
	 */
	public List getIdPagamenti() {
		return idPagamenti;
	}

	/**
	 * @param list
	 */
	public void setIdPagamenti(List list) {
		idPagamenti = list;
	}

	public String getCodicePendenza() {
		return codicePendenza;
	}

	public void setCodicePendenza(String idPendenzaEnte) {
		this.codicePendenza = idPendenzaEnte;
	}

	public String getFiltroCodPagamento() {
		return filtroCodPagamento;
	}

	public void setFiltroCodPagamento(String filtroCodPagamento) {
		this.filtroCodPagamento = filtroCodPagamento;
	}

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	public Date getDataScadenza_da() {
		return dataScadenza_da;
	}

	public void setDataScadenza_da(Date dataScadenza_da) {
		this.dataScadenza_da = dataScadenza_da;
	}

	public Date getDataScadenza_a() {
		return dataScadenza_a;
	}

	public void setDataScadenza_a(Date dataScadenza_a) {
		this.dataScadenza_a = dataScadenza_a;
	}

	public Date getFiltroTsInserimento_a() {
		return filtroTsInserimento_a;
	}

	public void setFiltroTsInserimento_a(Date filtroTsInserimento_a) {
		this.filtroTsInserimento_a = filtroTsInserimento_a;
	}

	public Boolean getFiltroIncludiPagamentiInErrore() {
		return filtroIncludiPagamentiInErrore;
	}

	public void setFiltroIncludiPagamentiInErrore(
			Boolean filtroIncludiPagamentiInErrore) {
		this.filtroIncludiPagamentiInErrore = filtroIncludiPagamentiInErrore;
	}

	public String getFiltroEnteTributo() {
		return filtroEnteTributo;
	}

	public void setFiltroEnteTributo(String filtroEnteTributo) {
		this.filtroEnteTributo = filtroEnteTributo;
	}

	public Integer getFiltroFornitore() {
		return filtroFornitore;
	}

	public void setFiltroFornitore(Integer filtroFornitore) {
		this.filtroFornitore = filtroFornitore;
	}

	public Integer getFiltroEscludiFornitore() {
		return filtroEscludiFornitore;
	}

	public void setFiltroEscludiFornitore(Integer filtroEscludiFornitore) {
		this.filtroEscludiFornitore = filtroEscludiFornitore;
	}


	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento=annoRiferimento;
	}

	public boolean isDebitoriRich() {
		return isDebitoriRich;
	}

	public void setDebitoriRich(boolean isDebitoriRich) {
		this.isDebitoriRich = isDebitoriRich;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	
}
