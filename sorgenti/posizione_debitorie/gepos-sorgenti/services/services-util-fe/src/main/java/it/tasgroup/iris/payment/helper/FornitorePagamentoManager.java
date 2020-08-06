package it.tasgroup.iris.payment.helper;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaInputVO;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.util.enumeration.EnumFornitorePagamento;
import it.tasgroup.services.util.enumeration.EnumStatoDistintePagamento;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pazzik
 *
 */
public class FornitorePagamentoManager {
	
	private FornitorePagamentoManager() {

	}

	/**
	 * Aggiorna una distinta in corso a partire dal suo dettaglio (DistintaPosizioneDebitoriaDettaglioVO)
	 * e restituisce true se dopo la verifica posso continuare con il pagamento della distinta
	 * @param dettDistinta dettaglio della distinta
	 * @return true se dopo la verifica posso continuare con il pagamento della distinta
	 * @throws Exception se la distinta non è in corso??
	 */
	public static boolean aggiornaStatoDistintaInCorso(DistintaPosizioneDebitoriaDettaglioVO dettDistinta, IProfileManager profileManager) throws Exception {
		FornitorePagamento fornitore = FornitorePagamentoFactory.getNewInstance(dettDistinta);
		fornitore.profileManager = profileManager;
		
		return fornitore.aggiornaStatoDistintaInCorso(dettDistinta);
	}

	/**
	 * Aggiorna una distinta in corso a partire dall'id
	 * e restituisce true se dopo la verifica posso continuare con il pagamento della distinta
	 * @param idDistintaInCorso
	 * @return true se dopo la verifica posso continuare con il pagamento della distinta
	 * @throws Exception se la distinta non è in corso??
	 */
	public static boolean aggiornaStatoDistintaInCorso(String idDistintaInCorso, IProfileManager profileManager) throws Exception {
		List<DistintaPosizioneDebitoriaDettaglioVO> distinte = recuperaDettagliDistinte(idDistintaInCorso,  EnumStatoDistintePagamento.INCORSO, null, null, null, null);
		
		if (distinte != null) {
			DistintaPosizioneDebitoriaDettaglioVO distinta = distinte.get(0); // mi fido del fatto che ci sia una sola distinta ...
			FornitorePagamento fornitore = FornitorePagamentoFactory.getNewInstance(distinta);
			fornitore.profileManager = profileManager;
			
			return fornitore.aggiornaStatoDistintaInCorso(distinta);
		} else {
			throw new Exception("DISTINTA NON TROVATA. La distinta con id: " + idDistintaInCorso + " non esiste oppure non è in stato: "
					+ EnumStatoDistintePagamento.INCORSO);
		}		
	}
	
	/**
	 * Recupera tutte le distinte in corso 
	 * (tra quelle inserite con un ritardo pari a RITARDO_INSERIMENTO_MINUTI = 15minuti)
	 * e tenta l'aggiornamento dello stato
	 * @param numMax limita il numero di distinte da elaborare. Se numMax = null vengono recuperate TUTTE le distinte in corso.
	 * @throws Exception
	 */
	public static void aggiornaStatoDistinteInCorso(Integer numMax, Integer minutiRitardo, EnumFornitorePagamento fornitorePagamento, EnumFornitorePagamento escludiFornitorePagamento) throws Exception {
		
		List<DistintaPosizioneDebitoriaDettaglioVO> distinte = recuperaDettagliDistinte(null, EnumStatoDistintePagamento.INCORSO, fornitorePagamento, escludiFornitorePagamento, numMax, minutiRitardo);

		if (distinte != null) {
			
			for (DistintaPosizioneDebitoriaDettaglioVO distinta : distinte) {
				
				try {
					
					FornitorePagamentoFactory.getNewInstance(distinta).aggiornaStatoDistintaInCorso(distinta);
				
				} catch (Exception e) {
					
					Tracer.error(FornitorePagamentoManager.class.getName(), "aggiornaStatoDistinteInCorso", 
							"Errore durante l'aggiornamento dello stato della distinta " + distinta.getDistinta() + " - continuo", e);
				}
			}
		}
	}
	
	/**
	 * Recupera tutte le distinte eseguite salvo buon fine 
	 * (tra quelle inserite con un ritardo pari a RITARDO_INSERIMENTO_MINUTI = 15minuti)
	 * e tenta l'aggiornamento dello stato
	 * @param numMax
	 * @throws Exception
	 */
	public static void aggiornaStatoDistinteEseguiteSbf(Integer numMax, Integer minutiRitardo, EnumFornitorePagamento fornitorePagamento, EnumFornitorePagamento escludiFornitorePagamento) throws Exception {
		List<DistintaPosizioneDebitoriaDettaglioVO> distinte = recuperaDettagliDistinte(null,  EnumStatoDistintePagamento.ESEGUITOSBF, fornitorePagamento, escludiFornitorePagamento, numMax, minutiRitardo);

		if (distinte != null) { 
			for (DistintaPosizioneDebitoriaDettaglioVO distinta : distinte) {
				try {
					FornitorePagamentoFactory.getNewInstance(distinta).aggiornaStatoDistintaEseguitoSBF(distinta);
				} catch (Exception e) {
					Tracer.error(FornitorePagamentoManager.class.getName(), "aggiornaStatoDistinteEseguiteSbf", 
							"Errore durante l'aggiornamento dello stato della distinta " + distinta.getDistinta() + " - continuo", e);
				}
			}
		}
	}

	
	/**
	 * Recupera i dettagli delle distinte di pagamento utilizzando i parametri in input come filtri di ricerca.
	 * 
	 * @param idDistinta
	 * @param statoDistinta
	 * @param idFornitorePagamento
	 * @param numMax
	 * @param ritardoInserimento in minuti
	 * @return
	 * @throws Exception
	 */
	private static List<DistintaPosizioneDebitoriaDettaglioVO> recuperaDettagliDistinte(String idDistinta, EnumStatoDistintePagamento statoDistinta, 
			EnumFornitorePagamento fornitorePagamento, EnumFornitorePagamento escludiFornitorePagamento,
			Integer numMax, Integer minutiRitardoInserimento) throws ServiceLocatorException {
		
		Tracer.debug(FornitorePagamentoManager.class.getName(), "recuperaDettagliDistinte", "Filtro la ricerca per "
				+ " idDistinta: " + idDistinta 
				+ " statoDistinta: " + statoDistinta 
				+ " fornitorePagamento: " + fornitorePagamento 
				+ " escludiFornitorePagamento: " + escludiFornitorePagamento 
				+ " numMax: " + numMax 
				+ " minutiRitardoInserimento: " + minutiRitardoInserimento);
		
		ContainerDTO dtoIn = new ContainerDTO();
		
		// filtro numero massimo di record estratti
		if (numMax != null) {
			PagingCriteria pagingCriteria = new PagingCriteria();
			pagingCriteria.setResultsPerPage(numMax);
			dtoIn.setPagingCriteria(pagingCriteria);
		}
		
		PosizioneDebitoriaInputVO inVo = new PosizioneDebitoriaInputVO();
		
		// filtro per idDistinta
		if (idDistinta != null)
			inVo.setIdDistinta(idDistinta);
		
		// filtro per stato
		if(statoDistinta != null)
			inVo.setFiltroStato(statoDistinta.getChiave());

		// filtro per idFornitorePagamento (=)
		if(fornitorePagamento != null)
			inVo.setFiltroFornitore(fornitorePagamento.getId());

		// filtro per idFornitorePagamento (!=)
		if(escludiFornitorePagamento != null)
			inVo.setFiltroEscludiFornitore(escludiFornitorePagamento.getId());

		// filtro per ritardo inserimento (tutte le distinte inserite prima di ...)
		if(minutiRitardoInserimento != null) 
			inVo.setFiltroTsInserimento_a(new Date(System.currentTimeMillis() - minutiRitardoInserimento * 60 * 1000));
		
		dtoIn.setInputDTO(inVo);

		DistintePagamentoFacade facadeBean = (DistintePagamentoFacade) ServiceLocator.getSLSBProxy("distintePagamentoFacadeBean");
        
		List<DistintaPosizioneDebitoriaDettaglioVO> distinte = facadeBean.getDistinteByFilterParameters(dtoIn);
        
		if(distinte != null && !distinte.isEmpty())
			Tracer.debug(FornitorePagamentoManager.class.getName(), "recuperaDettagliDistinte", "Nr. distinte Trovate: " + distinte.size());
		else
			Tracer.debug(FornitorePagamentoManager.class.getName(), "recuperaDettagliDistinte", "NESSUNA Distinta Trovata");
		
		return distinte;
	}
	
	
	public static String preparaRichiestaPagamento(HttpServletRequest request,
			HttpServletResponse response,
			List<SessionShoppingCartItemDTO> pagamentiWIP,
			CfgGatewayPagamentoDTO cfgDto,
			IProfileManager profileManager, OperatoriPojo operatorePojo, boolean isAnonymous) throws PagamentoException, IncompleteRegistrationException {
		
		Integer idFornitore = cfgDto.getCfgFornitoreGateway().getId().intValue();
		
		String modPag = cfgDto.getCfgModalitaPagamento().getBundleKey();
		
		if (idFornitore == null)
			throw new RuntimeException("ID Fornitore NULLO");
		
		FornitorePagamento fornitore = FornitorePagamentoFactory.getNewInstance(idFornitore, modPag);

		// inizializzazione
		fornitore.cfgPagamento = cfgDto;
		fornitore.profileManager = profileManager;
		fornitore.setAnonymous(isAnonymous);
		fornitore.pagamentiWIP = pagamentiWIP;
		fornitore.operatorePojo = operatorePojo;
		fornitore.inizializzaImporti();
			
		return fornitore.preparaRichiesta(request, response);
	}

}
