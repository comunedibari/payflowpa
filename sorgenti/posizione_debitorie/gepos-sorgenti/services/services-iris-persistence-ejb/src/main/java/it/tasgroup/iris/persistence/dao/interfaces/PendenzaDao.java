package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.TributoEnte;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.dto.SearchTributiRequest;
import it.tasgroup.iris.dto.anonymous.payment.AnonymousAnagraficaDTO;
import it.tasgroup.iris.dto.anonymous.payment.CondizioneDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.services.dao.ejb.Dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.persistence.Tuple;

@Local
public interface PendenzaDao extends Dao<Pendenza>{


	/**
	 * Opzioni per il servizio di inquiry posizione debitoria
	 * @author RepettiS
	 *
	 */
	public static class PosizioneDebitoriaOptions {
		/**
		 * Esclude dalla lista i tributi nascosti
		 * ovvero quelli che hanno flNascostoFe=true su TributoEnte
		 */
		public boolean escludiTributiNascosti=false;
		/**
		 * Nel valutare lo stato di una pendenza
		 * considera i pagamenti "in corso" come pagati. Se posto a true
		 * le query sulle posizioni "da pagare" non ritornano pendenze con pagamenti "in corso".
		 */
		public boolean pagamentiInCorsoComePagati=false;
		/**
		 * Esclude le pendenze che hanno tutte le condizioni con
		 * termini di pagamento scaduti. TODO: predisposto per future ottimizzazioni WS ma non ancora implementato.
		 */
		public boolean escludiPendenzeTerminiPagamentoScadute=false;
	}


	public Pendenza getPendenzaById(String id);

    public Pendenza getPendenzaByChiaveSemantica(String cdTrbEnte, String idEnte, String idPendenzaEnte);

	public List<Tuple> getAnonymousPaymentConditionByIdDistinta(Long idDistinta);

	public <T extends TributoStrutturato> Set<CondizionePagamento> createNewPendenza(T tributoStrutturato, boolean isNDPCompliant);

	public <T extends TributoStrutturato> Set<CondizionePagamento> createNewPendenza(T tributoStrutturato, boolean isNDPCompliant, AnonymousAnagraficaDTO anagrafica);

	public <T extends TributoStrutturato> List<T> findTributi(SearchTributiRequest<T> searchWrapper);

	public List<Pendenza> listaPendenzeDaPagareEstrattoContoDebitorio(String codiceFiscale, Date dateFrom, Date dateTo,  String idEnte, Integer annoRiferimento, String codCategoriaDebito, String codDebitoCreditore,  boolean ignoraPagamentiInCorso);

	public List<Pendenza> listaPendenzePagateEstrattoContoDebitorio(String codiceFiscale, Date dateFrom, Date dateTo, String idEnte, Integer annoRiferimento, String codCategoriaDebito, String codDebitoCreditore,  boolean ignoraPagamentiInCorso);

	public TributoStrutturato getTributoStrutturatoByIdPendenza(String id);

	public void updateStRiga(CondizionePagamento cond);

	public List<Pendenza> getMultaByBusinessKeys(String targa, Date data_verbale, String numero_verbale, String serie_verbale);

	public List<Pendenza> listPendenzeByFilterParameters(PosizioneDebitoriaInputVO filterParameters, PagingData pagingData, PagingCriteria pagingCriteria, PosizioneDebitoriaOptions options );

	public List<TributoEnte> listTributoEnteForUserPosizioneDebitoria(String utente);

	public CondizionePagamento getCondizioneByIUV(String iuv, String idEnte, String cdTrbEnte, String codiceFiscale);

    public CondizionePagamento getCondizioneByEnteIUV(String iuv, String idEnte, String codiceFiscale);

	public CategoriaTributo getCategoriaTributo(String idTributo);

	public CondizionePagamento getCondizioneByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException;

	public List<CondizionePagamento> getCondizioniByIUV(String iuv, String idTributo, String codiceFiscale) throws BusinessConstraintException;

	public Long countPendenzeTributo(String idEnte, String cdTrbEnte);

	public Set<CondizionePagamento> createNewPendenza(CondizioneDTO condizione, AnonymousAnagraficaDTO anagrafica);

	public String replacePosizione(String idCondizione, BigDecimal importo, String noteVersante, String codiceFiscaleOperatore);
	
	public CondizionePagamento getCondizioneByIUV(String iuv, String codiceFiscale) throws BusinessConstraintException;
	

}