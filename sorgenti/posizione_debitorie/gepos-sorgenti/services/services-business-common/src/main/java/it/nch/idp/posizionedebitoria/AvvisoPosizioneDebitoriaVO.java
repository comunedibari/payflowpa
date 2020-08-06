package it.nch.idp.posizionedebitoria;

import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.services.util.enumeration.EnumTipoPagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AvvisoPosizioneDebitoriaVO implements Serializable
{ 
	private static final long serialVersionUID = 1L;
	
	private String idPendenza;
	private String coDestinatario;
	private String codEnte;                   // getCodiceEnte
	private Date dataEmissione;
	private Date dataScadenza;
	private Date dataFineValidita;
	private String ente;                       // getDenominazione
	private String tributo;                    // getDeTrb
	private String tipoTributo;
	private String idTributo;
	private String cod_tributo;                // getCdTrbEnte
	
	private String idEnte;
	private String cdPlugin;
	
	private Integer tributo_strutturato;
	private String causale;
	private String codiceFiscale;
	private List<DebitorePosizioneDebitoriaVO> debitori;
	private BigDecimal importo;
	private String modalita;
	private BigDecimal importoPagato;
	private BigDecimal importoRiscosso;
	private String stato;
	private String codicePendenza;
	private BigDecimal importoSbf;
	private BigDecimal importoNonPagabile;
	private Integer condizioniPagabili;
	private Integer condizioniNonPagabili;
	private Integer annoRif;
	private String note;
	private String urlUpdateService;
	private String descrizioneMittente;
	private String riscossore;
	private String riferimento;
	private Date dataCreazione;
	private String idPendenzaEnte;
	
	private List<CondizionePagamentoVO> condizioni;

	private Object tributoStrutturato;
	
	public String getFullRiscossore() {
		if (riscossore == null && riferimento == null)
			return "";
		else
			return (riscossore != null ? riscossore : " - ") + "/" + 
						(riferimento != null ? riferimento : " - ");
	}
	

	public String getCodicePendenza() {
		return codicePendenza;
	}

	public void setCodicePendenza(String codicePendenza) {
		this.codicePendenza = codicePendenza;
	}

	public AvvisoPosizioneDebitoriaVO() {super();}
	
	public AvvisoPosizioneDebitoriaVO(Date dataEmissione,Date dataScadenza,String ente,String tributo, String idTipoTributo,
			String causale,String codiceFiscale,BigDecimal importo,String modalita,String stato)
	{
		this.setDataEmissione(dataEmissione);
		this.setDataScadenza(dataScadenza);
		this.setEnte(ente);
		this.setImporto(importo);
		this.setTributo(tributo);
		this.setTipoTributo(idTipoTributo);
		this.setCausale(causale);
		this.setCodiceFiscale(codiceFiscale);
		this.setModalita(modalita);
		this.setStato(stato);
	}
	
	public AvvisoPosizioneDebitoriaVO(Date dataEmissione,Date dataScadenza,String ente,String tributo, String idTipoTributo,
				String causale,String codiceFiscale,BigDecimal importo,BigDecimal importoPagato,BigDecimal importoRiscosso,String modalita,String stato)
	{
		this(dataEmissione,dataScadenza,ente,tributo,idTipoTributo,causale,codiceFiscale,importo,modalita,stato);
		this.setImportoPagato(importoPagato);
		this.setImportoRiscosso(importoRiscosso);
	}

	public String getCausale() {
		return causale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}
	
	public Date getDataScadenza() {
		return dataScadenza;
	}
	
	public Date getDataScadenzaVideo() {
		if (dataScadenza.compareTo(SharedConstants.NO_EXPIRE) == 0)
			return null;
		else
			return dataScadenza;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getEnte() {
		return ente;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public String getModalitaDesc() {
		
		if(getModalita() == null || getModalita().equals(""))
    		return "";

    	EnumTipoPagamenti enu = EnumTipoPagamenti.getByKey(getModalita());
    	
    	if (enu==null) return "";
    	return enu.getDescrizione();
		
	}
	
	public String getModalita() {
		return modalita;
	}

	public String getStato() {
		return stato;
	}

	public void setCausale(String string) {
		causale = string;
	}

	public void setCodiceFiscale(String string) {
		codiceFiscale = string;
	}

	public void setDataEmissione(Date date) {
		dataEmissione = date;
	}
	
	public void setDataScadenza(Date date) {
		dataScadenza = date;
	}

	public void setEnte(String string) {
		ente = string;
	}

	public void setImporto(BigDecimal decimal1) {
		importo = decimal1;
	}

	public void setModalita(String string) {
		modalita = string;
	}

	public void setStato(String string) {
		stato = string;
	}

	public String getTipoTributo() {
		return tipoTributo;
	}

	public void setTipoTributo(String string) {
		tipoTributo = string;
	}
	
	public String getIdTributo() {
		return idTributo;
	}

	public void setIdTributo(String string) {
		idTributo = string;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String string) {
		tributo = string;
	}

	public List<DebitorePosizioneDebitoriaVO> getDebitori() {
		return debitori;
	}

	public void setDebitori(List<DebitorePosizioneDebitoriaVO> list) {
		debitori = list;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String string) {
		idPendenza = string;
	}

	public String getCoDestinatario() {
		return coDestinatario;
	}

	public void setCoDestinatario(String string) {
		coDestinatario = string;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public BigDecimal getImportoRiscosso() {
		return importoRiscosso;
	}

	public void setImportoPagato(BigDecimal decimal) {
		importoPagato = decimal;
	}

	public void setImportoRiscosso(BigDecimal decimal) {
		importoRiscosso = decimal;
	}

	public BigDecimal getImportoSbf() {
		return importoSbf;
	}

	public void setImportoSbf(BigDecimal importoSbf) {
		this.importoSbf = importoSbf;
	}

	public BigDecimal getImportoNonPagabile() {
		return importoNonPagabile;
	}

	public void setImportoNonPagabile(BigDecimal importoNonPagabile) {
		this.importoNonPagabile = importoNonPagabile;
	}

	public Integer getCondizioniPagabili() {
		return condizioniPagabili;
	}

	public void setCondizioniPagabili(Integer condizioniPagabili) {
		this.condizioniPagabili = condizioniPagabili;
	}

	public Integer getCondizioniNonPagabili() {
		return condizioniNonPagabili;
	}

	public void setCondizioniNonPagabili(Integer condizioniNonPagabili) {
		this.condizioniNonPagabili = condizioniNonPagabili;
	}

	public String getCod_tributo() {
		return cod_tributo;
	}

	public void setCod_tributo(String cod_tributo) {
		this.cod_tributo = cod_tributo;
	}

	public Integer getTributo_strutturato() {
		return tributo_strutturato;
	}

	public void setTributo_strutturato(Integer tributo_strutturato) {
		this.tributo_strutturato = tributo_strutturato;
	}

	public Object getTributoStrutturato() {
		return tributoStrutturato;
	}

	public void setTributoStrutturato(Object tributoStrutturato) {
		this.tributoStrutturato = tributoStrutturato;
	}

	public Integer getAnnoRif() {
		return annoRif;
	}

	public void setAnnoRif(Integer annoRif) {
		this.annoRif = annoRif;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUrlUpdateService() {
		return urlUpdateService;
	}

	public void setUrlUpdateService(String urlUpdateService) {
		this.urlUpdateService = urlUpdateService;
	}
	
	public List<CondizionePagamentoVO> getCondizioni() {
		return condizioni;
	}

	public void setCondizioni(List<CondizionePagamentoVO> condizioni) {
		this.condizioni = condizioni;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public String getDescrizioneMittente() {
		return descrizioneMittente;
	}

	public void setDescrizioneMittente(String descrizioneMittente) {
		this.descrizioneMittente = descrizioneMittente;
	}

	public String getRiscossore() {
		return riscossore;
	}

	public void setRiscossore(String riscossore) {
		this.riscossore = riscossore;
	}
	
	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
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

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}
	
	
}
