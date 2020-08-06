package it.tasgroup.iris.business.ejb.pagamenti;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.CfgTributoEntePlugin;
import it.nch.is.fo.util.plugin.PluginReceiptUtil;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.EstrattoContoDebitorioLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.EstrattoContoDebitorioRemote;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.dto.EstrattoContoDebitorioDTO;
import it.tasgroup.iris.dto.EstrattoContoDebitorioOutputDTO;
import it.tasgroup.iris.dto.PendenzaEstrattoContoDebitorioDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "EstrattoContoDebitorioBusiness")
public class EstrattoContoDebitorioBean implements EstrattoContoDebitorioLocal, EstrattoContoDebitorioRemote  {


	@EJB(name = "PendenzaDaoService")
	private PendenzaDao pendDao;

	@EJB(name = "EntiDaoService")
	private EntiDao entiDao;
	
	@EJB(name = "CommonPaymentBusiness")
	private CommonPaymentBusinessLocal commonPaymentBusinessBean;
	
	public final static String CBILL = "CBILL";
	
	public final static String PUNTOSI = "PUNTOSI";
	
	protected static final String DEFAULT_OPERATOR = "IRIS";

	private static final Logger LOGGER = LogManager.getLogger(EstrattoContoDebitorioBean.class);
	
	@Override
	public EstrattoContoDebitorioOutputDTO estrattoContoDebitorio(EstrattoContoDebitorioDTO richECDDTO) {
		
		EstrattoContoDebitorioOutputDTO dtoOut = new EstrattoContoDebitorioOutputDTO();
		
		dtoOut.setLogMessage("EstrattoContoDebitorioBean - estrattoContoDebitorio" + richECDDTO);
		
		dtoOut.setExtractInProgressPayments(richECDDTO.isExtractInProgressPayments());
		
		/**
		 * Check condizioni di input
		 */
		
		try{
			
			dtoOut = (EstrattoContoDebitorioOutputDTO) commonPaymentBusinessBean.controlliTestata(dtoOut, richECDDTO.getTestata(),null,null);
			
			if (dtoOut.getReturnCode().isError())
											return dtoOut;
							
			boolean includePagate = false;
			
			boolean includeDaPagare = false;
			
			List<String> listaStatiPendenze = richECDDTO.getLsStato();
			
			if (listaStatiPendenze != null) {
				
				for(String statoRichiesto : listaStatiPendenze) {
					
					if ("DA PAGARE".equals(statoRichiesto))
						includeDaPagare = true;
					
					else if ("PAGATA".equals(statoRichiesto))
						includePagate = true;
					
					else if ("CHIUSA".equals(statoRichiesto) || 
						"PARZIALMENTE PAGATA".equals(statoRichiesto) ||
						(!richECDDTO.isCBILL() && "NON PAGABILE".equals(statoRichiesto)) ||
						"PAGAMENTO IN CORSO".equals(statoRichiesto)) {
							dtoOut.setReturnCode(EnumBusinessReturnCodes.W0000003);
							dtoOut.setMessageDescription("STATO '"+statoRichiesto+"' NON SUPPORTATO");
							return dtoOut;
					}	
				}
			}
			
			
			//riabilitata ricerca per date
//			if (!isSearchByDateAllowed(richECDDTO) && (richECDDTO.getDataIni()!=null || richECDDTO.getDataFin()!=null)) {				
//				dtoOut.setReturnCode(EnumBusinessReturnCodes.W0000003);
//				dtoOut.setMessageDescription("RICERCA PER DATE NON SUPPORTATA");
//				return dtoOut;						
//			}
			
			List<Pendenza> listaPendenzeDaPagare = new ArrayList<Pendenza>();
			List<Pendenza> listaPendenzePagate = new ArrayList<Pendenza>();
			
			String siaCreditore = richECDDTO.getCodiceSIACreditore();
			String cfCreditore = richECDDTO.getCodiceFiscaleCreditore();
			
			String idCreditore = null;
			
			if (!StringUtils.isEmpty(siaCreditore) || !StringUtils.isEmpty(cfCreditore))
				idCreditore = entiDao.getIdEnte(siaCreditore, cfCreditore);
			
				
			/**
			 * Estrazione dei dati
			 */
			
			String codFiscale = richECDDTO.getCodiceFiscaleDebitore();
			
			boolean extractInProgressPayments = richECDDTO.isExtractInProgressPayments();
			
			Date dateFrom = richECDDTO.getDataIni();
					
			Date dateTo = richECDDTO.getDataFin();
			
			Integer annoRiferimento = richECDDTO.getAnnoRiferimento();
			
			String codCategoriaDebito = richECDDTO.getCodiceCategoriaDebito();
			
			String codDebitoCreditore = richECDDTO.getCodiceDebitoCreditore();
			
			if (includeDaPagare) 
				listaPendenzeDaPagare = pendDao.listaPendenzeDaPagareEstrattoContoDebitorio(codFiscale, dateFrom, dateTo, idCreditore,  annoRiferimento, codCategoriaDebito, codDebitoCreditore, extractInProgressPayments);
			
			if (includePagate)
				listaPendenzePagate = pendDao.listaPendenzePagateEstrattoContoDebitorio(codFiscale, dateFrom, dateTo, idCreditore,  annoRiferimento, codCategoriaDebito, codDebitoCreditore, extractInProgressPayments);
				
			
			/**
			 * Post filtering su pendenze da pagare
			 */
			for (Pendenza pend : listaPendenzeDaPagare) {
				
				PendenzaEstrattoContoDebitorioDTO dto = new PendenzaEstrattoContoDebitorioDTO();
				
				Set<CondizionePagamento> condizioni = pend.getCondizioni();
				
				List<CondizionePagamento> cp = new ArrayList<CondizionePagamento>();
				
				for (CondizionePagamento condizionePagamento: condizioni){				
										
					condizionePagamento.updateStatoPagamentoCalcolato();
					//to avoid lazyInit
					condizionePagamento.getVociPagamento();
					
					boolean isCBILLCompatible = condizionePagamento.isDaPagareOInCorso() && condizionePagamento.isCBILLIdsCompatible();
					
					// TODO PAZZIK VERIFICARE
					if (!richECDDTO.isCBILL() || isCBILLCompatible)
							cp.add(condizionePagamento);
					
				}
				
				//Nota: Se qui si vuole filtrare solo quelle "PAGABILI" bisogna ciclare su cp e tenere solo le condizioni che sono 
				//negli stati che ci interessano. Al momento non si toglie niente.
				
				dto.setCondizioni(cp);
		
				completeDTO(dto,pend,"DA PAGARE");
							
				dtoOut.addPendenza(dto);
				
			}
			
			/**
			 * Post filtering su pendenze pagate
			 * Includo tutte le pendenze pagate, e per queste solo le condizioni che effettivamente sono state pagate.
			 */
			for (Pendenza pend : listaPendenzePagate) {
				
				PendenzaEstrattoContoDebitorioDTO dto = new PendenzaEstrattoContoDebitorioDTO();
				
				Set<CondizionePagamento> condizioni = pend.getCondizioni();
				
				List<CondizionePagamento> cp = new ArrayList<CondizionePagamento>();
				
				for (CondizionePagamento condizionePagamento : condizioni) {
					
					Pagamenti mainPayment = BillItemInspector.getMainPayment(condizionePagamento);
					
					//to avoid lazyInit
					condizionePagamento.getVociPagamento();					
					
					// PAZZIK 20-12-2013
					// per gestire il caso di pendenze inserite in IRIS, ma pagate al di fuori di IRIS
					// cio� tali che risultano in stato PAGATO ma senza avere pagamenti
					if (mainPayment != null) {
					
						GestioneFlussi mainFlow = mainPayment.getFlussoDistinta();
						
						condizionePagamento.setCodPagamentoFlusso(mainFlow.getCodPagamento());
						
						condizionePagamento.setCodPaganteFlusso(mainFlow.getUtentecreatore());
						
						condizionePagamento.setIdPagamentoFlusso(mainFlow.getId().toString());										
					}
					
					condizionePagamento.updateStatoPagamentoCalcolato();
					
					if (EnumStatoPagamentoCondizione.PAGATA.equals(condizionePagamento.getStatoPagamentoCalcolato()) ||
						
						EnumStatoPagamentoCondizione.IN_CORSO.equals(condizionePagamento.getStatoPagamentoCalcolato())	) {
											
						// TODO PAZZIK VERIFICARE
						if (!richECDDTO.isCBILL() || condizionePagamento.isCBILLIdsCompatible())
							
											cp.add(condizionePagamento);
						
					}
				}	
				
				dto.setCondizioni(cp);
				
				completeDTO(dto,pend,"PAGATA");
				
				dtoOut.addPendenza(dto);
				
			}
			
			if (richECDDTO.isCBILL())
				postFilteringCBILL(dtoOut);
			
			if (richECDDTO.isPAI())
				postFilteringPAI(richECDDTO, dtoOut);
			
		
		} catch(EJBTransactionRolledbackException dex){
			
			Throwable cause = dex.getCause();
			
			
			LOGGER.error("EstrattoContoDebitorioBean - estrattoContoDebitorio dao exception", dex);
			
			
			if (cause instanceof DAORuntimeException)
							
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			
			else 
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
		} catch (Exception e) {
						
			
			LOGGER.error("EstrattoContoDebitorioBean - estrattoContoDebitorio exception", e);

			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
						
		} finally{
			
			commonPaymentBusinessBean.manageTermination(richECDDTO.getTestata(), dtoOut, richECDDTO.getDeOperazione(), EnumOperazioniPagamento.ESTRATTOCONTO, DEFAULT_OPERATOR, richECDDTO.getCodiceFiscaleDebitore());
		}
			
		return dtoOut;
	}
	
	private void postFilteringPAI(EstrattoContoDebitorioDTO richECDDTO, EstrattoContoDebitorioOutputDTO dtoOut) {
		
		List<PendenzaEstrattoContoDebitorioDTO> pendenze = dtoOut.getPendenze();
		
		List<PendenzaEstrattoContoDebitorioDTO> filteredPendenze = new ArrayList<PendenzaEstrattoContoDebitorioDTO>();
		
		for (PendenzaEstrattoContoDebitorioDTO pend : pendenze) {
			
			if (pend.getCondizioni().size() == 1) {
			
				CondizionePagamento condizione = (CondizionePagamento) pend.getCondizioni().get(0);
				
				// TODO PAZZIK solo temporaneamente per evitare che getEsitoPagamento sbombi quando si trova ad eseguire la getVoci senza una transazione aperta
				// sistemare in futuro con CondizionePagamentoDTO
				System.out.println(condizione.getVociPagamento());
			
				condizione.updateStatoPagamentoCalcolato();			
			
				if (!richECDDTO.isSoloPosizioniPagabili() || condizione.getStatoPagamentoCalcolato().equals(EnumStatoPagamentoCondizione.DA_PAGARE))
						
						filteredPendenze.add(pend);	
							
			}
										
		}
					
		dtoOut.setPendenze(filteredPendenze);
	}

	private void postFilteringCBILL(EstrattoContoDebitorioOutputDTO dtoOut) {
		
		List<PendenzaEstrattoContoDebitorioDTO> pendenze = dtoOut.getPendenze();
		
		List<PendenzaEstrattoContoDebitorioDTO> filteredPendenze = new ArrayList<PendenzaEstrattoContoDebitorioDTO>();
		
		for (PendenzaEstrattoContoDebitorioDTO pend : pendenze) {
			
			// TODO PAZZIK VERIFICARE: BASTA TOGLIERE LA PENDENZA CON PIU CONDIZIONI DAL RISULTATO 
			// O DEVO LANCIARE UNA ILLEGALSTATEEXCEPTION ?
			
			if (pend.getCondizioni().size() == 1 ) {
			
				CondizionePagamento condizione = (CondizionePagamento) pend.getCondizioni().get(0);
				
				BillItemInspector.preLoadPagamenti(condizione);
								
				Pagamenti mainPayment = BillItemInspector.getMainPayment(condizione);
				
				String statoPagamento = null;
				
				GestioneFlussi mainPaymentFlow = null;
				
				CfgGatewayPagamento canale = null;
				
				if (mainPayment != null) {
					
					statoPagamento = mainPayment != null ? mainPayment.getStPagamento() : null;
				
					mainPaymentFlow = mainPayment.getFlussoDistinta();				
				
					canale = mainPaymentFlow.getCfgGatewayPagamento();
				}
				
				if (mainPayment == null || !StatiPagamentiIris.IN_CORSO.getPagaMapping().equals(statoPagamento) || (dtoOut.isExtractInProgressPayments() && StatiPagamentiIris.IN_CORSO.getPagaMapping().equals(statoPagamento) && canale.isCBILL())){
						
						filteredPendenze.add(pend);	
						
				}
										
			}
		}
		
		DataScadenzaComparator comparator = new DataScadenzaComparator();
		
		Collections.sort(filteredPendenze, comparator);
		
		// PAZZIK 08-05-2014 come da nuove specifiche
		//if (filteredPendenze.size() > 5)
			
				//filteredPendenze = filteredPendenze.subList(0, 5);
					
		dtoOut.setPendenze(filteredPendenze);
	}

	/**
	 * Completa il DTO in output con i dati della pendenza.
	 *  
	 */
	private void completeDTO(PendenzaEstrattoContoDebitorioDTO dto, Pendenza pend, String statoPend) {


		CfgTributoEntePlugin cfgPlugin = pend.getTributoEnte().getCfgTributoEntePlugin();

		if (cfgPlugin != null && PluginReceiptUtil.exists_plugin(cfgPlugin.getCdPlugin())) {

			TributoStrutturato tributo = (TributoStrutturato) pendDao.getTributoStrutturatoByIdPendenza(pend.getIdPendenza());
			if (tributo != null) {
				dto.setIdTributoStrutturato(tributo.getId());
				dto.setTributoStrutturato(((TributoStrutturato) tributo));

				Map<String, String> map = PluginReceiptUtil.getDetails(tributo, pend.getTributoEnte().getIdEnte(), pend.getTributoEnte().getCdTrbEnte(), Locale.ITALY);
				if (map != null) {
					dto.setCausale(map.toString().replace("=", ": ").replace("}", "").replace("{", ""));
				}
			}

		} else {
			dto.setCausale(PluginReceiptUtil.getCausaleFormatted(pend.getTributoEnte().getCdTrbEnte(), pend.getDeCausale()));
		}
		
		dto.setIdPendenza(pend.getIdPendenza());
		dto.setIdPendenzaEnte(pend.getIdPendenzaente());
		dto.setCodFiscaleCreditore(pend.getTributoEnte().getEntiobj().getIntestatarioobj().getLaplIForm());
		dto.setCodFiscaleDebitore(pend.getDestinatari().iterator().next().getCoDestinatario());
		dto.setCdTrbEnte(pend.getTributoEnte().getCdTrbEnte());
		dto.setDescrizioneTributo(pend.getTributoEnte().getDeTrb());
		dto.setDescrizioneEnte(pend.getTributoEnte().getEntiobj().getDenominazione());
		dto.setStatoDocumento(statoPend);
		dto.setIdEnte(pend.getTributoEnte().getIdEnte());
		dto.setTipoTributo(pend.getTributoEnte().getCdTrbEnte());

		dto.setImporto(pend.getImTotale());
		dto.setNote(pend.getNote());
	}
	
	public class DataScadenzaComparator implements Comparator<PendenzaEstrattoContoDebitorioDTO>{

		@Override
		public int compare(PendenzaEstrattoContoDebitorioDTO pend0, PendenzaEstrattoContoDebitorioDTO pend1) {
			
			List<CondizionePagamento> condizioni0 = pend0.getCondizioni();
			
			List<CondizionePagamento> condizioni1 = pend1.getCondizioni();
			
			if (condizioni0.size() > 1)
				throw new IllegalStateException("Le ricerche CBILL non possono restituire pendenze con pi\u00F9 di una condizione di pagamento. Pend: " + pend0.getIdPendenza());
			
			if (condizioni1.size() > 1)
				throw new IllegalStateException("Le ricerche CBILL non possono restituire pendenze con pi\u00F9 di una condizione di pagamento. Pend: " + pend1.getIdPendenza());
						
			CondizionePagamento condizione0 = condizioni0.get(0);
			
			CondizionePagamento condizione1 = condizioni1.get(0);	
					
			return new CondizionePagamento.DtScadenzaComparator().compare(condizione0,condizione1);
		}
		
	}
	
	private boolean isSearchByDateAllowed(EstrattoContoDebitorioDTO richECDDTO){
		
		return richECDDTO.isCBILL() || richECDDTO.isPAI();
	
	}

}
