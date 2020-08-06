package it.tasgroup.iris.business.ejb.pagamenti;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.pagamenti.Multe;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.business.ejb.client.pagamenti.AnnullamentoPagamentoServiceLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AnnullamentoPagamentoServiceRemote;
import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessLocal;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pagamenti.MaxPagamentiComparator;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.PagamentiOnline.MaxDataComparator;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAnnullamentoDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.MulteDao;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiOnLineDao;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "AnnullamentoBusiness")
public class AnnullamentoPagamentoBean implements AnnullamentoPagamentoServiceLocal, AnnullamentoPagamentoServiceRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(AnnullamentoPagamentoBean.class);

	private final static String OPERATORE = "IRIS";
	
	@EJB(name="GestioneFlussiDaoService")
	private GestioneFlussiDao distintaDao;

	@EJB(name="PagamentiOnLineDaoService")
	private PagamentiOnLineDao pagOnlineDao;

	@EJB(name="PagamentiDaoService")
	private PagamentiDao pagDao;

	@EJB(name="MulteDaoService")
	private MulteDao multeDao;
	
	@EJB(name="CondizioniPagamentoDaoService")
	private CondizioniPagamentoDao condizioniDao;
	
	@EJB(name = "CommonPaymentBusiness")
	private CommonPaymentBusinessLocal commonPaymentBusinessBean;
	
	protected static final String DEFAULT_OPERATOR = "IRIS";

	@Override 
	public EsitoOperazionePagamentoDTO annullamentoPagamento(RichiestaAnnullamentoDTO richiestaAnnullamento) {
		
		EsitoOperazionePagamentoDTO esito = new EsitoOperazionePagamentoDTO();
		
		esito.setLogMessage("AnnullamentoPagamentoBean - annullamentoPagamento per codTransazione="+richiestaAnnullamento.getCodiceTransazione());
		
		String codTransazione = null;
		
		if (LOGGER.isInfoEnabled())
			LOGGER.info("AnnullamentoPagamentoBean - annullamentoPagamento - Inizio Annullamento Transazione" + new Date());
		
		try {
			
			esito = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(esito, richiestaAnnullamento.getTestata(),null,null);
			
			if (esito.getReturnCode().isError())
											return esito;
			
			///////////////////////
			// Verifica transazione
			///////////////////////
			
			
			if (!StringUtils.isEmpty(richiestaAnnullamento.getCodiceTransazione()))
				
				codTransazione  = richiestaAnnullamento.getCodiceTransazione();
			
			else if (!StringUtils.isEmpty(richiestaAnnullamento.getCodAutorizzazione())){
				
				//recupero cod transazione da COD AUTORIZZAZIONE
				if (LOGGER.isInfoEnabled())
					LOGGER.info("Codice autorizzazione : " + richiestaAnnullamento.getCodAutorizzazione());
	
					PagamentiOnline  pagOnline = pagOnlineDao.getPagamentoOnLineByCodAutorizzazione(richiestaAnnullamento.getCodAutorizzazione());
					
					if (pagOnline != null)
						codTransazione = pagOnline.getFlussoDistintaOnline().getCodTransazione();
					
					else{
						//transazione non trovata
						
						LOGGER.error("Transazione non trovata per il codice autorizzazione:"+richiestaAnnullamento.getCodAutorizzazione());
						
						esito.setReturnCode(EnumBusinessReturnCodes.A0000010);
						
						return esito; 
					}
					
			} else if (richiestaAnnullamento.getMulta() != null){

				//recupero cod transazione da MULTA
				Multe multa = multeDao.getMultaByTargaAndNumVerbale(richiestaAnnullamento.getMulta().getTarga(), richiestaAnnullamento.getMulta().getNumVerbale());
				if (multa == null){
					//multa non trovata
					
					LOGGER.error("Multa non trovata : " + richiestaAnnullamento.getMulta().toString());

					esito.setReturnCode(EnumBusinessReturnCodes.B0000001);
					
					return esito; 
				}
				else{
					if (multa.getPagamento() == null){
						//nessun pagamento associato alla multa
						
						LOGGER.error("Nessuna transazione associata alla multa : " + richiestaAnnullamento.getMulta().toString());

						esito.setReturnCode(EnumBusinessReturnCodes.A0000010);
						
						return esito; 
					}
					
					codTransazione = multa.getPagamento().getFlussoDistinta().getCodTransazione();
				}
			}
			else if (richiestaAnnullamento.getCondizioniDiPagamento() != null 
					&& richiestaAnnullamento.getCondizioniDiPagamento().size() >  0){

				//recupero cod transazione da CONDIZIONI
				String idcondizione = richiestaAnnullamento.getCondizioniDiPagamento().iterator().next();
				idcondizione = StringUtils.rightPad(idcondizione, 20, " ");
				
				CondizionePagamento condPagamento = condizioniDao.getSingleCondizioneById(idcondizione);
				Pagamenti maxPaga = Collections.max(condPagamento.getPagamenti(), new MaxPagamentiComparator());
				codTransazione = maxPaga.getFlussoDistinta().getCodTransazione();
			}
			else {
				//errore dati di input nessun codice di ricerca
				esito.setReturnCode(EnumBusinessReturnCodes.A0000023);
				
				return esito; 
			}
				
			
			GestioneFlussi gf = distintaDao.getDistintaByMsgId(codTransazione);
			
			if (gf == null){
								
				LOGGER.error("Codice Transazione Non Trovato : " + codTransazione);
				
				esito.setReturnCode(EnumBusinessReturnCodes.A0000010);
				
				return esito; 
			}
			
			richiestaAnnullamento.getTestata().setSenderSys(gf.getCfgGatewayPagamento().getSystemId());
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info("Transazione Trovata : " + codTransazione + " - stato : " + gf.getStato());


			///////////////////////////
			//Verifica codice sessione
			///////////////////////////
			if (gf.getPagamentiOnline() != null && gf.getPagamentiOnline().size() > 0){
				Set<PagamentiOnline> pgo = gf.getPagamentiOnline();
				
				//mi interessano solo le AUP
				//creo una nuovo set di AUP
				Set<PagamentiOnline> aupPgo = new HashSet<PagamentiOnline>();
				
				for (PagamentiOnline p : gf.getPagamentiOnline()) {
					
					if ((EnumOperazioniPagamento.AUTORIZZAZIONE.getChiave()).equalsIgnoreCase(p.getIdOperazione())) {
						aupPgo.add(p);
					}
				}
				
				//cerco la più recente solo tra le  autorizzazioni
				PagamentiOnline maxPagamento = Collections.max(aupPgo, new MaxDataComparator());
				
				
				SessionIdDTO currSession = new SessionIdDTO(maxPagamento.getSessionIdSistema(),maxPagamento.getSessionIdTerminale(),maxPagamento.getSessionIdToken(),maxPagamento.getSessionIdTimbro());
				
				if (!currSession.equals(richiestaAnnullamento.getTestata().getSession())){

					LOGGER.error("Id Sessione errato -  REQUEST : " + richiestaAnnullamento.getTestata().getSession().toString() + " DB :  " + currSession.toString());

					esito.setReturnCode(EnumBusinessReturnCodes.A0000014);
					
					return esito; 
				}
			}

				
			////////////////////////////////////
			// aggiorna transazione e pagamenti
			////////////////////////////////////
			//la transazione puo essere annullata solo si trova in stato in corso
			if (gf.getStato().equals(StatiPagamentiIris.IN_CORSO.getFludMapping())){
				esito.setReturnCode(EnumBusinessReturnCodes.OK);
				
				Timestamp now = new Timestamp(new Date().getTime());
				///////////////////////////////
				//aggiornamento gestione flussi
				////////////////////////////////		
				gf.setStato(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping());
				gf.setTsUpdate(now);
				
				/////////////////////////			
				//aggiornamento pagamenti
				/////////////////////////
			
				if (gf.getPagamenti() != null && !gf.getPagamenti().isEmpty()){
					for (Pagamenti pagamento : gf.getPagamenti()) {
						pagamento.setStPagamento(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getPagaMapping());
						pagamento.setTsAggiornamento(now);
						
						//cancello la multa se e solo se il pagamento è una multa
						Multe multa = pagamento.getMulta();
						if (multa != null){
							pagamento.setMulta(null);
							multeDao.delete(multa);
						}
					}
				}
				
				///////////////////////////////
				//inserimento pagamenti_online
				///////////////////////////////
				String descOperatore = OPERATORE;
				if (gf.getPagamenti() != null && !gf.getPagamenti().isEmpty()){
					Set<DestinatariPendenza> destinatari = gf.getPagamenti().iterator().next().getCondPagamento().getPendenza().getDestinatari();

					if (!destinatari.isEmpty()){
						descOperatore = destinatari.iterator().next().getCoDestinatario();
					}
				}
				
				PagamentiOnline pagaOnline = PaymentEntityBuilder.popolaPagamentoOnline(richiestaAnnullamento.getTestata(), richiestaAnnullamento.getCodAutorizzazione(), gf, null, EnumOperazioniPagamento.ANNULLAMENTO, esito.getReturnCode(), descOperatore);
				
				if(gf.getPagamentiOnline() == null)
					gf.setPagamentiOnline(new HashSet<PagamentiOnline>());
					
				gf.getPagamentiOnline().add(pagaOnline);
				//save gestione flussi
				distintaDao.save(gf);
				
				if (LOGGER.isInfoEnabled())
					LOGGER.info("Transazione : " + codTransazione + " ANNULLATA");

				esito.setReturnCode(EnumBusinessReturnCodes.OK);
				
			}
			else if (gf.getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping())) 
				esito.setReturnCode(EnumBusinessReturnCodes.A0000012);
				
			else if (gf.getStato().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())) 
				esito.setReturnCode(EnumBusinessReturnCodes.A0000016);
				
			else if (gf.getStato().equals(StatiPagamentiIris.ANNULLATO.getFludMapping())
					|| gf.getStato().equals(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping()))
				esito.setReturnCode(EnumBusinessReturnCodes.A0000011);	
			
			else if (gf.getStato().equals(StatiPagamentiIris.IN_ERRORE.getFludMapping()))
				esito.setReturnCode(EnumBusinessReturnCodes.A0000013);			
			
			else  
				esito.setReturnCode(EnumBusinessReturnCodes.A0000018);
				

		} catch(EJBTransactionRolledbackException dex){
			
			Throwable cause = dex.getCause();
				
			LOGGER.error("AnnullamentoPagamentoBean - annullamentoPagamento dao EJBTransactionRolledbackException", dex);
								
			if (cause instanceof DAORuntimeException)
							
				esito.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			else 	
				esito.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
		} catch (Exception e) {
						
			LOGGER.error("AnnullamentoPagamentoBean - annullamentoPagamento Exception", e);
			
			esito.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
					
		}	finally {
			
			commonPaymentBusinessBean.manageTermination(richiestaAnnullamento.getTestata(), esito, null, EnumOperazioniPagamento.ANNULLAMENTO, DEFAULT_OPERATOR, richiestaAnnullamento.getCodAutorizzazione());
		
		}

		LOGGER.info("Fine Annullamento Transazione" + new Date());
		
		return esito;
	}
	
	@Override
    public void annullaPagamentoNDP(String idCondizione, StatiPagamentiIris newState){
		
		
    	List<GestioneFlussi> glist= distintaDao.getDistinteByIdCondizionePagamento(idCondizione);
    	if (glist==null || glist.size()==0)
    		return;
    	
		GestioneFlussi gf = glist.get(0);
		try {
			if (gf == null) {
				LOGGER.error("Codice Transazione Non Trovato : " + idCondizione);
				return;
			}

			if (LOGGER.isInfoEnabled())
				LOGGER.info("Transazione Trovata : " + idCondizione + " - stato : " + gf.getStato());

			// /////////////////////////
			// Verifica codice sessione
			// /////////////////////////
			if (gf.getPagamentiOnline() != null && gf.getPagamentiOnline().size() > 0) {
				Set<PagamentiOnline> pgo = gf.getPagamentiOnline();
				PagamentiOnline maxPagamento = Collections.max(pgo,	new MaxDataComparator());

			}

			// //////////////////////////////////
			// aggiorna transazione e pagamenti
			// //////////////////////////////////
			// la transazione puo essere annullata solo si trova in stato in
			// corso
			if (gf.getStato().equals(
					StatiPagamentiIris.IN_CORSO.getFludMapping())) {

				Timestamp now = new Timestamp(new Date().getTime());
				// /////////////////////////////
				// aggiornamento gestione flussi
				// //////////////////////////////
				gf.setStato(newState.getFludMapping());
				gf.setTsUpdate(now);
				// ///////////////////////
				// aggiornamento pagamenti
				// ///////////////////////

				if (gf.getPagamenti() != null && !gf.getPagamenti().isEmpty()) {
					for (Pagamenti pagamento : gf.getPagamenti()) {
						pagamento.setStPagamento(newState.getPagaMapping());
						pagamento.setTsAggiornamento(now);

						// cancello la multa se e solo se il pagamento è una
						// multa
						Multe multa = pagamento.getMulta();
						if (multa != null) {
							pagamento.setMulta(null);
							multeDao.delete(multa);
						}
					}
				}

				// /////////////////////////////
				// inserimento pagamenti_online
				// /////////////////////////////
				String descOperatore = OPERATORE;
				if (gf.getPagamenti() != null && !gf.getPagamenti().isEmpty()) {
					Set<DestinatariPendenza> destinatari = gf.getPagamenti().iterator().next().getCondPagamento().getPendenza().getDestinatari();

					if (!destinatari.isEmpty()) {
						descOperatore = destinatari.iterator().next().getCoDestinatario();
					}
				}

				
				String codiceAutorizzazione = "-";
				String sessionIdToken = "-"; // testataIn.getSession().getToken();
				
				PagamentiOnline pol = new PagamentiOnline();
				if (gf != null) {						
					pol.setFlussoDistintaOnline(gf);
					codiceAutorizzazione = gf.getIuv();
				} 
				
				pol.setSessionIdSistema("-"); // testataIn.getSession().getIdSistema()
				pol.setSessionIdTerminale("-"); // testataIn.getSession().getIdTerminale()
				pol.setSessionIdToken(sessionIdToken);
				
				String timbroTroncato = "-"; //testataIn.getSession().getDataOraAccesso();
				
				if (timbroTroncato.length()>20)
					timbroTroncato=timbroTroncato.substring(0, 19);
				
				Timestamp now1 = new Timestamp(System.currentTimeMillis());
				
				pol.setSessionIdTimbro(timbroTroncato);
				pol.setTsOperazione(now1);
				pol.setTiOperazione(EnumOperazioniPagamento.ANNULLAMENTO.getChiave());
				pol.setIdOperazione(EnumOperazioniPagamento.ANNULLAMENTO.getChiave());
				pol.setDeOperazione(EnumOperazioniPagamento.ANNULLAMENTO.getDescrizione());
				
				pol.setTsInserimento(now);
				pol.setCodAutorizzazione(codiceAutorizzazione);
				
				String esito = "OK";
				
				pol.setEsito(esito);
				pol.setCodErrore(esito);
				pol.setApplicationId("IRIS"); // testataIn.getSenderSil()
				pol.setSystemId("IRIS"); // testataIn.getSenderSys()
				pol.setOpInserimento(descOperatore);
				
				//--------------------------------------------------
				if (gf.getPagamentiOnline() == null)
					gf.setPagamentiOnline(new HashSet<PagamentiOnline>());

				gf.getPagamentiOnline().add(pol);
				// save gestione flussi
				distintaDao.save(gf);

				if (LOGGER.isInfoEnabled())
					LOGGER.info("Transazione : " + idCondizione
							+ " ANNULLATA");

			}

		} catch (EJBTransactionRolledbackException dex) {
			Throwable cause = dex.getCause();
			LOGGER.error(
					"AnnullamentoPagamentoBean - annullamentoPagamento dao EJBTransactionRolledbackException",
					dex);
		} catch (Exception e) {
			LOGGER.error(
					"AnnullamentoPagamentoBean - annullamentoPagamento Exception",
					e);
		} finally {

		}
		LOGGER.info("Fine Annullamento Transazione" + new Date());
		return;

	}

	
	
	@Override
	public void annullaPagamentoPrecedente(Long idDistinta) {
	
		GestioneFlussi distinta = null;
		
		try {
			distinta = distintaDao.getById(GestioneFlussi.class, idDistinta);
		} catch (Exception e) {
			LOGGER.error ("AnnullamentoBean impossibile recuperare la distinta da annullare",e);
			throw new RuntimeException (e);
		}
		
		if (distinta.getStato().equals(StatiPagamentiIris.IN_CORSO.getFludMapping())){
			LOGGER.info("AnnullamentoBean::annullaPagamentoPrecedente() pagamento in corso.... lo annullo...");		
			Timestamp now = new Timestamp(new Date().getTime());
		
			///////////////////////////////
			//aggiornamento gestione flussi
			////////////////////////////////		
			
			distinta.setStato(StatiPagamentiIris.ANNULLATO.getFludMapping());
			distinta.setTsUpdate(now);
			
			/////////////////////////			
			//aggiornamento pagamenti
			/////////////////////////
		
			if (distinta.getPagamenti() != null && !distinta.getPagamenti().isEmpty()){
				for (Pagamenti pagamento : distinta.getPagamenti()) {
					pagamento.setStPagamento(StatiPagamentiIris.ANNULLATO.getPagaMapping());
					pagamento.setTsAggiornamento(now);
				}
			}
			LOGGER.info("AnnullamentoBean::annullaPagamentoPrecedente() pagamento in corso.... annullato ...");
		} 	
	}

	@Override
	public boolean checkPagamentoInCorso(String idCondizione) {
		List<GestioneFlussi> glist= distintaDao.getDistinteByIdCondizionePagamento(idCondizione);
    	if (glist==null || glist.size()==0)
    		return false;
    	
		GestioneFlussi gf = glist.get(0);
		// //////////////////////////////////
		// aggiorna transazione e pagamenti
		// //////////////////////////////////
		// la transazione puo essere annullata solo si trova in stato in
		// corso
		if (gf.getStato().equals(
							StatiPagamentiIris.IN_CORSO.getFludMapping())) {
			return true;
		} else {
		  return false;
		}
	}
	
}


