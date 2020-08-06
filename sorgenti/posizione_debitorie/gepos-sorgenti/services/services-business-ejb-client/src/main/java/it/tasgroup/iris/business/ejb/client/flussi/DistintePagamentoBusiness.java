package it.tasgroup.iris.business.ejb.client.flussi;

import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.pagamenti.nodopagamentispc.*;
import it.nch.profile.IProfileManager;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.creditore.StatistichePagamento;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.domain.*;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.flussi.RevocaPagamentoDTO;
import it.tasgroup.iris.dto.rest.filters.StatistichePagamentoFilter;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumTypeExportMassivo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface DistintePagamentoBusiness {

	public List<GestioneFlussi> getGestioneFlussiAll(ContainerDTO inputDTO);

	public List<GestioneFlussi> getDistintaByCodTransazione(String codTransazione);

	public GestioneFlussi getDistintaById(Long idDistinta);

	public List<Pagamenti> getPagamenti(ContainerDTO inputDTO);

	public List<Pagamenti> getPagamentiFull(ContainerDTO inputDTO);

	public void esportaPagamentiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale) throws Exception;
	
	public void esportaCondizioniCreditoreFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale) throws Exception;

	public void esportaPagamentiCSVStandard(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, EnumExportSTDFormat stdFormat, Locale locale) throws Exception;
	
	public void esportaCondizioniCSVStandard(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, EnumExportSTDFormat stdFormat, Locale locale) throws Exception;

	public void esportaAvvisiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale) throws Exception;

	public void esportaRiaccreditiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, String cfOperatore, Locale locale) throws Exception;

	public void esportaQuietanzeGestioneFlussiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, String cfOperatore, EnumTypeExportMassivo typo) throws Exception;

	public Long contaPagamentiQuietanzati(ContainerDTO inputDTO);
	
	public Long contaRicevuteTelematiche(ContainerDTO inputDTO);

	public Esportazioni getEsportazioni(Long idPren);

	public DistintaCartaCreditoVO preparaPagamento(IProfileManager profilo,DistintaCartaCreditoVO distinta);

	public GestioneFlussi aggiornaEsito(long idFlusso, StatiPagamentiIris stato, String tranId, String deOperazione,String tipoIdentifAttestante, String identifAttestante, String descrizAttestante );

	public GestioneFlussi aggiornaEsitoDaRT(DatiRicevutaPagamentoVO datiRicevutaPagamento) throws DomainNotFoundException, RptNotFoundException, DuplicatedRptException;

    public List<Object[]> pagamentiInScadenzaHP(IProfileManager profilo, String catTributo);

    public BigDecimal importoTotalePagatoByPendenza(String idPendenza);

    public List<PagamentoPosizioneDebitoriaForHomePageVO> ultimiPagamentiEffettuatiHP(IProfileManager profilo);

    public ContainerDTO storicoPagamenti(IProfileManager profilo, ContainerDTO containerDTO);

    public GestioneFlussi getDistintaPagamentoForRicevuta(String codPagamento, String idFlusso, String codPagante) throws BusinessConstraintException;

    // Funzioni per HP BO e Ente

    public List<Object[]> riepilogoIncassi(String idEnte);
    
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento);
    
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk);

    public List<Object[]> riepilogoPagamenti(String idEnte);
    
    public List<Object[]> riepilogoPagamenti(String idEnte, String annoRiferimento);
    
    public List<Object[]> riepilogoPagamenti(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk);

    public List<Object[]> riepilogoPosizioniAttese(String idEnte, Integer nrGiorni);

    public List<Object[]> riepilogoPosizioniNonAttese(String idEnte, Integer nrGiorni);

    public List<TributoEnte> listTributoEntePDB(String lapl);

    public List<String> getDebitorePendenza(String idPendenza);

    public void esportaAvvisiCSVStandard(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale);

	public List<GestioneFlussi> getDistinteNDP(ContainerDTO containerDTO);

	public CfgGatewayPagamento getCfgGatewayPagamentoBySystemIdAndApplicationId(String systemId, String applicationId);

	public List<GestioneFlussi> getDistinteByFilterParameters(ContainerDTO containerDTO);

	public List<GestioneFlussi> getDistintaPagamento(ContainerDTO dtoIn);

	public List<GestioneFlussi> getDistinteByIdCondizionePagamento(String idCondizionePagamento);

	public List<GestioneFlussi> getByCodPagamentoCodiceFiscale(String codPagamento, String codFiscale);

	public List<GestioneFlussi> getDistinteByCodPagamento(String codPagamento);

	public Pagamenti getPagamentoById(Long idPagamento);

	public List<Pagamenti> getPagamentiByIdDistinta(Long idDistinta);

	public List<GestioneFlussi> getDistinteStessoGruppo(Long idDistinta);

	List<Pagamento> getPagamentiCreditore(ContainerDTO inputDTO);

	public List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> getCondizioniCreditore(ContainerDTO inputDTO);
	
	public List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> getCondizioniCreditoreStorico(ContainerDTO inputDTO);
			
	String getUrlRicevuta(Pagamenti pagamento, String codPagamento, String codiceFiscale, String idFlusso);

	StatistichePagamento getStatisticheCreditore(StatistichePagamentoFilter inputDTO);

	Long aggiornaInformazioniTransazioneIncasso(Long idFisico, String iuvPagamento, String codiceContestoPagamento, Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso);

	List<Pagamento> getPagamentiEseguiti(ContainerDTO inputDTO, String flagInCorso, String idCondizione);

	List<Pagamenti> getPagamentibyCodFisc(ContainerDTO inputDTO);

	String getUrlQuietanza(Pagamenti pagamento, String codPagamento, String codiceFiscale, String idFlusso);

	List<Pagamento> getPagamentoEseguito(String idCondizione);
	
	public boolean annullaOperatoreByIdDistinta(Long idDistinta);

	public DocumentoRepositoryDTO getRicevuteTelematicheContent(String iuv,String codiceTransazionePSP, String idFiscaleCreditore);

	public List<Pagamenti> getPagamentiFullRicevuteTelematiche(ContainerDTO inputDTO);
	
    public void manageRichiestaRevoca(DataRichiestaRevocaPagamentoVO richiestaRevoca) throws SemanticException, RTNotFoundException;
	
	public RevochePagamento updateRevocaPagamento(RevocaPagamentoDTO revocaDTO);

	public List<RevochePagamento> getRevocaPagamentoByIdPagamento(Long idPagamento);

	List<GestioneFlussi> getDistinteByIdCondizionePagamentoStorico(String idCondizionePagamento);

	GestioneFlussi getDistintaByIdStorico(Long idDistinta);

    public EsitoNDP notificaPagamento(RichiestaNotificaPagamento request);

}

