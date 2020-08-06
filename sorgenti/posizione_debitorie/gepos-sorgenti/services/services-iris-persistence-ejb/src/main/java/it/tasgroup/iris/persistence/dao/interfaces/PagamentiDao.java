package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.creditore.StatistichePagamento;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.rest.filters.StatistichePagamentoFilter;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface PagamentiDao extends Dao<Pagamenti> {

	public Pagamenti salvaPagamento(String op,String idPendenzaEnte,SessionShoppingCartItemDTO pvo,Long idDistintaPagamento);

    /**
     * Metodo per l'estrazione degi ultimi pagamenti effettuati dall'utente loggato, indipendentemente dal fatto che questi
     * siano stati effettuati per conto suo o di altri
     * result
     * @param codiceFiscale
     * @return
     */
    public List<PagamentoPosizioneDebitoriaForHomePageVO> ultimiPagamentiEffettuatiHP(String codiceFiscale);

    /**
     * Metodo per l'estrazione dello storico pagamenti l'output è lo stesso del metodo ultimiPagamentiEffettuatiHP
     * @param profile
     * @param containerDTO
     * @return
     */
    public List<PagamentoPosizioneDebitoriaForHomePageVO> storicoPagamenti(IProfileManager profile, ContainerDTO containerDTO);

    public List<Pagamenti> getPagamenti(ContainerDTO inputDTO);
    public List<Pagamenti> getPagamentiFull(ContainerDTO inputDTO);
	
    public List<CondizionePagamento>  getCondizioniCreditoreFull(ContainerDTO inputDTO);
	
    public Long contaPagamentiQuietanzati(ContainerDTO inputDTO);
    
    public Long contaRicevuteTelematiche(ContainerDTO inputDTO);
    
    public <T extends TributoStrutturato> List<Pagamenti> getExistingPayments(T tributo, String cdTrbEnte);

    public Pagamenti updatePagamentoIdRepository(Long idPagamento, Long repoId,String idDocumentoExt);

	public List<Pagamenti> getPagamentiByIdDistinta(Long idDistinta);

    public List<Pagamenti> getPagamentiCreditore(ContainerDTO containerDTO);

    StatistichePagamento getStatisticheCreditore(StatistichePagamentoFilter statistichePagamentoFilters);

    Long aggiornaInformazioniTransazioneIncasso(Long idFisico, String iuvPagamento, String codiceContestoPagamento, Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso);

	public List<Pagamenti> getPagamentiByCodFiscale(ContainerDTO inputDTO);

	public List<Pagamenti> getPagamentiEseguiti(ContainerDTO inputDTO, String flagInCorso, String idCondizione);

	public List<Pagamenti> getPagamentoEseguito(String idCondizione);
		
	public List<CondizionePagamento> getCondizioniCreditore(ContainerDTO containerDTO);
	
	public List<Pagamenti> getPagamentiFullRT(ContainerDTO inputDTO);
	
	public EsitoNDP notificaPagamento(RichiestaNotificaPagamento request);
	}
