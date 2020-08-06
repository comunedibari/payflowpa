package it.tasgroup.iris.persistence.dao.interfaces;
 
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.services.dao.ejb.Dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

@Local
public interface CondizioniPagamentoDao extends Dao<CondizionePagamento> {

    public CondizionePagamento getSingleCondizioneById(String idCondizione);


    public List<CondizionePagamento> getCondizioniByIdList(List<String> idCondizioni);

    /**
     * Metodo per l'estrazione dei pagamenti in scadenza da visualizzare in home page cittadino
     * result[0]: condizione di pagamento
     * result[1]: la pendenza a cui appartiene la condizione
     * result[2]: l'ID di un documento di pagamento, valorizzato solo se la condizione associata ad un documento emesso non annullato (altrimenti e null)
     * result[3]: flag con valore '1' se la condizione ha transazioni in errore, '0' altrimenti
     * result[4]: flag con valore '1' se il pagamento ï¿½ in delega, '0' altrimenti
     *
     * @param codiceFiscale
     * @return
     */
    public List<Object[]> pagamentiInScadenzaHP(String codiceFiscale, String catTributo);

    /**
     * Metodo per l'estrazione dell'importo totale giï¿½ pagato per la pendenza il cui id ï¿½ passato come argomento
     * @param idPendenza
     * @return
     */
    public BigDecimal importoTotalePagatoByPendenza(String idPendenza);

    // Funzioni per la HP di BO e Ente

    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_INCASSI
     * result[2] IMPORTO
     * @param idEnte
     * @return
     */
    public List<Object[]> riepilogoIncassi(String idEnte);
    
    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_INCASSI
     * result[2] IMPORTO
     * @param idEnte
     * @param idEnte
     * @return
     */
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento);

    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_INCASSI
     * result[2] IMPORTO
     * @param idEnte
     * @param idEnte
     * @return
     */
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk);

    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_PAGAMENTI
     * result[2] IMPORTO
     * @param idEnte
     * @return
     */
    public List<Object[]> riepilogoPagamenti(String idEnte);
    
    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_PAGAMENTI
     * result[2] IMPORTO
     * @param idEnte
     * @param dataRiferimento
     * @return
     */
    public List<Object[]> riepilogoPagamenti(String idEnte, String dataRiferimento, Set<String> cdTrbEntePk);

    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_PAGAMENTI
     * result[2] IMPORTO
     * @param idEnte
     * @param dataRiferimento
     * @return
     */
    public List<Object[]> riepilogoPagamenti(String idEnte, String dataRiferimento);

    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_PENDENZE
     * result[2] IMPORTO_TOTALE
     * @param idEnte
     * @param nrGiorni
     * @return
     */
    public List<Object[]> riepilogoPosizioniAttese(String idEnte, Integer nrGiorni);

    /**
     * Ordine dei risultati:
     * result[0] TIPO_DEBITO
     * result[1] NUM_PENDENZE
     * result[2] IMPORTO_TOTALE
     * @param idEnte
     * @param nrGiorni
     * @return
     */
    public List<Object[]> riepilogoPosizioniNonAttese(String idEnte, Integer nrGiorni);

    /** 
 	 * Ritorna una lista di condizioni con st_riga 'V' o 'H' ordinata dalla più recente alla meno recente 
 	 */
    public List<CondizionePagamento> getListCondizioniByIdPagamento(String idPagamento, String codiceTributoEnte, String idEnte);

	public List<CondizionePagamento> getListCondizioniByCIPCode(String codiceRicercaPagamento, String codiceTributoEnte, String idEnte);

	public List<String> getDebitoriPendenza(String idPendenza) ;

	public CondizionePagamento getCondizioneByCFCreditoreIDEnteImporto(String codFiscaleCreditore, String idPagamentoEnte, BigDecimal importo);
    
    public CondizionePagamento getCondizioneByCFCreditoreIdPagamento(String codFiscaleCreditore, String idPagamento);
}


