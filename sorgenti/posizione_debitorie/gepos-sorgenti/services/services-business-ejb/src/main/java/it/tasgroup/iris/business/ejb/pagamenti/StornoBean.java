package it.tasgroup.iris.business.ejb.pagamenti;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.StornoLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.StornoRemote;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.PagamentiOnline.MaxDataComparator;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.dto.StornoDTO;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "StornoBusiness")
public class StornoBean implements StornoLocal, StornoRemote {

	private static final Logger LOGGER = LogManager.getLogger(StornoBean.class);

	@EJB(name = "GestioneFlussiDaoService")
	private GestioneFlussiDao distintaDao;
	
	@EJB(name = "CommonPaymentBusiness")
	private CommonPaymentBusinessLocal commonPaymentBusinessBean;
	
	protected static final String DEFAULT_OPERATOR = "IRIS";

	@Resource
	private SessionContext context;

	@Override
	public EsitoOperazionePagamentoDTO storna(StornoDTO dto) {

		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();

		try {
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,dto.getTestata(),null,null);
			
			if (dtoOut.getReturnCode().isError())
												return dtoOut;
			
			// /////////////////////
			// Verifica transazione
			// /////////////////////
			
			GestioneFlussi gf = distintaDao.getDistintaByMsgId(dto.getCodiceTransazione());
			if (gf == null) {

				LOGGER.error("Codice Transazione Non Trovato : " + dto.getCodiceTransazione());

				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000010);
				
				return dtoOut;
			}
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info("Transazione Trovata : " + dto.getCodiceTransazione() + " - stato : " + gf.getStato());
			// /////////////////////
			// Verifica codice sessione
			// /////////////////////
			if (gf.getPagamentiOnline() != null && gf.getPagamentiOnline().size()>0) {
				Set<PagamentiOnline> pgo = gf.getPagamentiOnline();
				PagamentiOnline maxPagamento = Collections.max(pgo, new MaxDataComparator());

				SessionIdDTO newSession = new SessionIdDTO(maxPagamento.getSessionIdSistema(), maxPagamento.getSessionIdTerminale(), maxPagamento.getSessionIdToken(),
						maxPagamento.getSessionIdTimbro());
				if (!newSession.equals(dto.getTestata().getSession())) {
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000014);
					
					dtoOut.setStatoDocumento(gf.getStato());
					return dtoOut;
				}
			}
			else {
				LOGGER.error("Pagamenti Online non trovati per la trasazione: "+dto.getCodiceTransazione());
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000014);
				
				dtoOut.setStatoDocumento(gf.getStato());
				return dtoOut;
			}

			///////////////////////
			// aggiorna transazione e pagamenti
			///////////////////////
			//la transazione puo essere annullata solo si trova in stato in corso
			if (gf.getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping())
					|| gf.getStato().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())){
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
				
				Timestamp now = new Timestamp(new Date().getTime());
				///////////////////////
				//aggiornamento gestione flussi
				///////////////////////				
				gf.setStato(StatiPagamentiIris.ANNULLATO.getFludMapping());
				gf.setTsUpdate(now);
				
				///////////////////////				
				//aggiornamento pagamenti
				///////////////////////
				
				String causaleStorno = dto.getCausaleStorno();
				
				if (gf.getPagamenti() != null){
					
					for (Pagamenti pagamento : gf.getPagamenti()) {
						
						pagamento.setStPagamento(StatiPagamentiIris.ANNULLATO.getPagaMapping());
						
						pagamento.setTsAggiornamento(now);
						
						pagamento.setTsStorno(dto.getTsStorno());
						
						if (!StringUtils.isEmpty(causaleStorno))
							
							pagamento.setNotePagamento(causaleStorno);
						
					}
				}
				
				///////////////////////
				//inserimento pagamenti_online
				///////////////////////
				PagamentiOnline pagaOnline = PaymentEntityBuilder.popolaPagamentoOnline(dto.getTestata(), gf.getCodTransazione(), gf, null, EnumOperazioniPagamento.STORNO, dtoOut.getReturnCode(), "OPERATORE");

				if(gf.getPagamentiOnline() == null)
					gf.setPagamentiOnline(new HashSet<PagamentiOnline>());
					
				gf.getPagamentiOnline().add(pagaOnline);
				
				//save gestione flussi
				distintaDao.save(gf);

				
				if (LOGGER.isInfoEnabled())
					LOGGER.info("Transazione : " + dto.getCodiceTransazione() + " STORNATA");

				dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
				
			}
			else if (gf.getStato().equals(StatiPagamentiIris.IN_CORSO.getFludMapping())) 
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000017);
			
			else if (gf.getStato().equals(StatiPagamentiIris.ANNULLATO.getFludMapping())
					|| gf.getStato().equals(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping()))
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000011);
			
			else if (gf.getStato().equals(StatiPagamentiIris.IN_ERRORE.getFludMapping()))
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000013);
	
			else  
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000018);
		
		} catch (DAORuntimeException dex) {
			context.setRollbackOnly();

			LOGGER.error("StornoBean - storno dao exception", dex);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);

		} catch (Exception e) {
			context.setRollbackOnly();

			LOGGER.error("StornoBean - storno exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);

		} finally{
			
			EnumOperazioniPagamento statoOperazione = EnumOperazioniPagamento.STORNO;
			
			commonPaymentBusinessBean.manageTermination(dto.getTestata(), dtoOut, null, statoOperazione, DEFAULT_OPERATOR, dto.getCodiceTransazione());	
			
		}
		return dtoOut;
	}

}
