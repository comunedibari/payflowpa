package it.tasgroup.iris.facade.ejb.client.flussi;

import it.nch.fwk.fo.dto.DTO;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.pagamenti.nodopagamentispc.DataRichiestaRevocaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiRicevutaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DomainNotFoundException;
import it.nch.pagamenti.nodopagamentispc.DuplicatedRptException;
import it.nch.pagamenti.nodopagamentispc.RTNotFoundException;
import it.nch.pagamenti.nodopagamentispc.RptNotFoundException;
import it.nch.pagamenti.nodopagamentispc.SemanticException;
import it.nch.pagamenti.nodopagamentispc.WrongRptException;
import it.nch.profile.IProfileManager;
import it.tasgroup.idp.rs.model.CondizionePagamento;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.creditore.StatistichePagamento;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.CruscottoHomePageDTO;
import it.tasgroup.iris.dto.PagamentiInScadenzaDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.dto.flussi.PagamentoDTOLightForReceipt;
import it.tasgroup.iris.dto.flussi.RevocaPagamentoDTO;
import it.tasgroup.iris.dto.rest.filters.StatistichePagamentoFilter;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumTipoExport;
import it.tasgroup.services.util.enumeration.EnumTypeExportMassivo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

public interface DistintePagamentoFacade {

	public ContainerDTO readGestioneFlussiListLight(ContainerDTO inputDTO);

	// public DistintePagamentoDTO readGestioneFlussiDettaglio(Long id);

	public List<DistintePagamentoDTOLight> readGestioneFlussiListLightByCodTransazione(String codTransazione);

//	public ContainerDTO getPagamentiCreditore(ContainerDTO inputDTO);

	public ContainerDTO getPagamentiCreditore(ContainerDTO inputDTO, Map<String, String> mapEnti, HashMap<String, HashMap<String, String>> mapTipiTributo);

//	public ContainerDTO getPagamentiFull(ContainerDTO inputDTO);

	public ContainerDTO getQuietanzePagamenti(ContainerDTO inputDTO);

	public void esportaPagamenti(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti, Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception;
	
	public void esportaCondizioni(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti, Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception;

	public void esportaPagamentiCSVStandard(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, EnumExportSTDFormat enumExportSTDFormat, Locale locale) throws Exception;
	
	
	public void esportaCondizioniCSVStandard(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
											Map<String, String> mapTipiTiributo, String cfOperatore, EnumExportSTDFormat enumExportSTDFormat, Locale locale) throws Exception;
	
	public void esporta(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, Map<String, String> mapEnti, Map<String, String> mapTipiTiributo, String cfOperatore, EnumTipoExport exportType, EnumDynaReportFormat dynaFormat) throws Exception;

	public void esportaAvvisi(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, Map<String, String> mapEnti, Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception;

	public ContainerDTO creaPrenotazioneEsporta(IProfileManager profileManager, ContainerDTO inputDTO, String cfOperatore) throws Exception;

	public DTO listaPreferenze(IProfileManager profilo, DTO dto);

	public DTO aggiornaPreferenze(IProfileManager profilo, DTO dto);

	public void esportaQuietanzePagamenti(Locale locale, IProfileManager profileManager, ContainerDTO inputDTO, String cfOperatore, EnumTypeExportMassivo typo) throws Exception;

	public ContainerDTO listaPrenotazioni(ContainerDTO inputDTO);

	public ContainerDTO contaQuietanzePagamenti(ContainerDTO inputDTO);
	
	public ContainerDTO contaRicevuteTelematiche(ContainerDTO inputDTO);
	
	public ContainerDTO getDocEsportazionePreferenza(IProfileManager profileManager, ContainerDTO inputDTO) throws Exception;

	public DistintaCartaCreditoVO preparaPagamento(IProfileManager profilo, DistintaCartaCreditoVO distinta);

	public void aggiornaEsito(long idFlusso, StatiPagamentiIris stato, String tranId, String deOperazione, String tipoIdentifAttestante, String identifAttestante, String descrizAttestante );

	public String aggiornaEsitoDaRT(DatiRicevutaPagamentoVO datiRicevutaPagamento) throws DomainNotFoundException, RptNotFoundException, DuplicatedRptException;
	
	public void manageRichiestaRevoca(DataRichiestaRevocaPagamentoVO richiestaRevoca) throws SemanticException, RTNotFoundException;
	
	public void updateRevocaPagamento(RevocaPagamentoDTO revocaDTO);


	/**
     * Visualizza un elenco delle posizioni debitorie per le quali è atteso un pagamento entro la data di scadenza mostrata
     *
     * @param profilo
     * @param carrello
     * @return
     */
    public PagamentiInScadenzaDTO pagamentiInScadenzaHP(IProfileManager profilo, List<String> carrello);


	public List<CondizionePagamento> pagamentiInScadenzaHP(IProfileManager profilo, String catTributo );

    /**
     * Mostra gli ultimi pagamenti effettuati dall'utente loggato, indipendentemente dal fatto che questi siano
     * fatti per conto proprio o di altri
     *
     * @param profilo
     * @return
     */
    public List<PagamentoPosizioneDebitoriaForHomePageVO> ultimiPagamentieffettuatiHP(IProfileManager profilo);


	/**
	 * Storico Pagamenti.. by query.
	 * @param profilo
	 * @param inputDTO
	 * @return
	 * @throws BusinessConstraintException
     */
    public ContainerDTO storicoPagamenti(IProfileManager profilo, ContainerDTO inputDTO) throws BusinessConstraintException;

    /**
     * Cancella una prenotazione di export a partire dal suo id
     * @param idPrenotazione
     * @return <code>true</code> se l'operazione va a buon fine, <code>false</code> altrimenti
     * @throws Exception
     */
    public boolean cancellaPrenotazione(Long idPrenotazione) throws Exception;

    // Metodi per la nuova HP BO e Ente

    public List<CruscottoHomePageDTO> riepilogoIncassi(String idEnte);

    public List<CruscottoHomePageDTO> riepilogoPagamenti(String idEnte);
    
    public List<CruscottoHomePageDTO> riepilogoIncassi(String idEnte, String annoRiferimento);

    public List<CruscottoHomePageDTO> riepilogoPagamenti(String idEnte, String annoRiferimento);
    
    public List<CruscottoHomePageDTO> riepilogoIncassi(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk);

    public List<CruscottoHomePageDTO> riepilogoPagamenti(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk);

    public List<CruscottoHomePageDTO> riepilogoPosizioniAttese(String idEnte, Integer nrGiorni);

    public List<CruscottoHomePageDTO> riepilogoPosizioniNonAttese(String idEnte, Integer nrGiorni);

    public void esportaRiaccrediti(IProfileManager profileManager,
			ContainerDTO inputDTO, Map<String, String> mapListaCampi,
			String cfOperatore, Locale locale) throws Exception;

	public List<TributoEnteDTO> listTributoEntePDB(String lapl);
	
	public List<TributoEnteDTO> listTributoEntePDB(String lapl, boolean isBackOffice);

	public List<String> getDebitorePendenza(String idPendenza);

	public void esportaAvvisiCSVStandard(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception;

	public void esportaBollettiniAvvisiZipPdf(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception;

	public ContainerDTO readDistinteNDP(ContainerDTO inputDTO);

	public ZipOutputStream esportaBollettiniPdfChunck(Iterator<String> iteratorCondizioni, ZipOutputStream zos, IProfileManager pfm, Long idCfgGatewayPagamento, Locale locale) throws Exception;

	public DistintePagamentoDTOLight retrieveFlowById(IProfileManager profileManager, Long flowId);

	public List<DistintePagamentoDTOLight> getDistinteByCodPagamento(String codPagamento);

	public CondizionePagamentoDTO readCondizionePagamento(String idCondizione);

	public List<DistintaPosizioneDebitoriaDettaglioVO> getDistinteByFilterParameters(ContainerDTO inputDTO);

	public List<DistintaPosizioneDebitoriaDettaglioVO> getDistintaPagamento(ContainerDTO dto);

	public List<DistintaPosizioneDebitoriaDettaglioVO> getDistinteByIdCondizionePagamento(String idCondizionePagamento);

	public DistintaPosizioneDebitoriaDettaglioVO getDistintaById(Long idDistinta);


	public DistintaCartaCreditoVO getDistintaCreditCardByCodTransazione(String codTransazione);

	public PagamentoDTOLightForReceipt getPagamentoById(Long idPagamento);

	public List<PagamentoDTOLightForReceipt> getPagamentiByIdDistinta(Long idDistinta);

	public ContainerDTO getPagamentiCreditore(ContainerDTO inputDTO);
		
	public ContainerDTO getCondizioniCreditore(ContainerDTO inputDTO);
		
	StatistichePagamento getStatisticheCreditore(StatistichePagamentoFilter statistichePagamentoFilter);

	Long aggiornaInformazioniTransazioneIncasso(Long idFisico, String iuvPagamento, String codiceContestoPagamento, Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso);
	
	public ContainerDTO getPagamentiEseguiti(ContainerDTO inputDTO, String flagInCorso, String idCondizione);

	ContainerDTO getPagamentoEseguito(String idCondizione);
	
	public void annullaOperatoreByDistinta(DistintePagamentoDTOLight d);

	public ContainerDTO listaPrenotazioniStorico(ContainerDTO inputDTO);

	List<DistintaPosizioneDebitoriaDettaglioVO> getDistinteByIdCondizionePagamentoStorico(String idCondizionePagamento);

	DistintaPosizioneDebitoriaDettaglioVO getDistintaByIdStorico(Long idDistinta);

	EsitoNDP notificaPagamento(RichiestaNotificaPagamento request);

//	public void esportaQuietanzePagamenti(Locale locale, IProfileManager profileManager, ContainerDTO inputDTOExport,
//			String cfOperatore, EnumTypeExportMassivo typo);
	
	

}


