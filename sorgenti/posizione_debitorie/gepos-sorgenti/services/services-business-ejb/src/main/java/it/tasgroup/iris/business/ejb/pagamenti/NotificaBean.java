package it.tasgroup.iris.business.ejb.pagamenti;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.stati.pagamenti.CheckRiconciliazioneCompleta;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.util.ZipUnzip;
import it.tasgroup.iris.business.ejb.client.ddp.DDPBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.NotificaLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.NotificaRemote;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.PagamentiOnline.MaxDataComparator;
import it.tasgroup.iris.dto.AllegatiPendenzaDTO;
import it.tasgroup.iris.dto.DettaglioNotificaDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.NotificaPagamentoDTO;
import it.tasgroup.iris.dto.SessionIdDTO;
import it.tasgroup.iris.persistence.dao.interfaces.DDPDAO;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.report.utility.PdfUtils;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumModRiversamento;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;
import it.tasgroup.services.util.enumeration.EnumTipoNotifica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "NotificaPagamentoBusiness")
public class NotificaBean implements NotificaLocal, NotificaRemote {

	private static final Logger LOGGER = LogManager.getLogger(NotificaBean.class);

	@EJB(name = "DocumentoDiPagamentoDao")
	private DDPDAO ddpDAO;

	@EJB(name = "GestioneFlussiDaoService")
	private GestioneFlussiDao distintaDao;
	
	@EJB(name = "CommonPaymentBusiness")
	private CommonPaymentBusinessLocal commonPaymentBusinessBean;
	
	@EJB
	private DDPBusinessLocal ddpBusinessBean;
	
	@Resource 
	private EJBContext context; 
	
	protected static final String DEFAULT_OPERATOR = "IRIS";

	@Override
	public EsitoOperazionePagamentoDTO notifica(NotificaPagamentoDTO dto) {
		
		Pagamenti pagamentoCoerente = null;
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		
		dtoOut.setLogMessage("NotificaBean - notifica - notifica pagamento per la transazione: " + dto.getCodiceTransazione());
		
		try {
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut, dto.getTestata(),null,null);
			
			if (dtoOut.getReturnCode().isError())
											return dtoOut;
			
			// //////////////////////////////
			// Verifica transazione esistente
			// //////////////////////////////
			GestioneFlussi gf = distintaDao.getDistintaByMsgId(dto.getCodiceTransazione());
			
			if (gf == null) {
								
				LOGGER.error("NotificaBean - notifica - Codice Transazione Non Trovato : " + dto.getCodiceTransazione());

				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000010);
				
				return dtoOut;
			}
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info("NotificaBean - notifica - Transazione Trovata : " + dto.getCodiceTransazione() + " - stato : " + gf.getStato());
			
			dto.getTestata().setSenderSys(gf.getCfgGatewayPagamento().getSystemId());
			// /////////////////////////
			// Verifica codice sessione
			// /////////////////////////
			if (gf.getPagamentiOnline() != null) {
				
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

				SessionIdDTO newSession = new SessionIdDTO(maxPagamento.getSessionIdSistema(), maxPagamento.getSessionIdTerminale(), maxPagamento.getSessionIdToken(),
						maxPagamento.getSessionIdTimbro());
				
				if (!newSession.equals(dto.getTestata().getSession())) {
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000014);
					
					dtoOut.setStatoDocumento(gf.getStato());

					return dtoOut;
				}
			}
			
			// //////////////////////////
			// Verifica idPagamentoEnte
			// //////////////////////////
			if(!StringUtils.isEmpty(dto.getIdPagamentoEnte())){
				
				for (Pagamenti pagam : gf.getPagamenti()) {
					
					if (pagam.getCondPagamento().getIdPagamento().equals(dto.getIdPagamentoEnte())) {
						
						pagamentoCoerente = pagam;
						
						break;
						
					}
							
				}
								
				if (pagamentoCoerente == null) {
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.N0000001);
					dtoOut.setDescrizione("Richiesta con dati non coerenti");
					//dtoOut.setDescrizione("Nessun pagamento di ID "+ dto.getIdPagamentoEnte() + " nella distinta di codTrasazione: " +dto.getCodiceTransazione());
					//dtoOut.setStatoDocumento(gf.getStato());

					return dtoOut;
				}
			}
			
			// //////////////////////////////
			// CALCOLO DELLO STATO INCASSO
			// //////////////////////////////
			
			EnumModRiversamento enumModRiversamento = dtoOut.getEnumModRiversamento();
			
			String flagRendRiversamento = dtoOut.getFlagRendRiversamento();
			
			if (EnumModRiversamento.FOUR_CORNER_MODE.equals(enumModRiversamento) && "0".equals(flagRendRiversamento))
				dto.setFlagIncasso("N");
			else 
				dto.setFlagIncasso("0");

			// //////////////////////////////
			// AGGIORNAMENTO STATO PAGAMENTO
			// //////////////////////////////
			// TODO terminare aggiornamento
			if (dto.getStatoNotifica().equals(EnumTipoNotifica.ESEGUITO.getChiave())) {
				// SE richiesta di ESEGUITO check stato IN CORSO oppure ESEGUITO
				// SBF
				
				boolean isAggiornaDistinteAnnullate = (dto.isAggiornaDistinteAnnullate() && (gf.getStato().equals(StatiPagamentiIris.ANNULLATO.getFludMapping()) || gf.getStato().equals(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping())));
				
				if (isAggiornaDistinteAnnullate
						|| gf.getStato().equals(StatiPagamentiIris.IN_CORSO.getFludMapping()) 
						|| gf.getStato().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())
						) {
					
					dtoOut = inserimentoInDB(gf, dto);
					
					return dtoOut;
					
				} 
				
				if (!dto.isAggiornaDistinteAnnullate() && (gf.getStato().equals(StatiPagamentiIris.ANNULLATO.getFludMapping()) || gf.getStato().equals(StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getFludMapping()))){
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000011);
					
					return dtoOut;
					
				}
				
				if (gf.getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping()) && gf.getCfgGatewayPagamento().isCBILL()){
				
					if (dto.isIgnoraDistintaPagataStessoCanale()){
						
						dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
						
						return dtoOut;
						
					} else {
							
						dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000002);
							
						return dtoOut;
					}
				}
				
			} else {
				// SE richiesta di ESEGUITO SBF check stato IN CORSO
				if (gf.getStato().equals(StatiPagamentiIris.IN_CORSO.getFludMapping())){
					
					dtoOut = inserimentoInDB(gf, dto);
					
					return dtoOut;
				}
					
				else {
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000003);
										
					LOGGER.error("NotificaBean - notifica - Pendenza non pagabile");
					
					LOGGER.error("NotificaBean - notifica - richiesto: " + dto.getStatoNotifica() + "stato: " + gf.getStato());
										
					return dtoOut;
				}
			}
		
		} catch(EJBTransactionRolledbackException dex){
			
			Throwable cause = dex.getCause();
			
			LOGGER.error("NotificaBean - notifica EJBTransactionRolledbackException", dex);
			
			if (cause instanceof DAORuntimeException)	
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			
			else 				
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
		} catch (Exception e) {
						
			LOGGER.error("NotificaBean - notifica exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
					
		}	finally {
			
			EnumOperazioniPagamento statoOperazione = EnumOperazioniPagamento.getOperationByStatusDescription(dto.getStatoNotifica());
			
			commonPaymentBusinessBean.manageTermination(dto.getTestata(), dtoOut, null, statoOperazione, DEFAULT_OPERATOR, dto.getIdPagamentoEnte());	
			
		}
		
		return dtoOut;
	}

	/**
	 * 
	 * @param gf
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	private EsitoOperazionePagamentoDTO inserimentoInDB(GestioneFlussi gf, NotificaPagamentoDTO dto) throws Exception {
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		
		dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
		
		// //////////////////////////
		// Verifica importo distinta
		// //////////////////////////
		if (dto.getImporto() != null) {
		
			if (dto.getImporto().compareTo(gf.getTotimportipositivi()) != 0){
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000020);
				
				return dtoOut;
			}
				
		}
		
		Timestamp now = new Timestamp(new Date().getTime());
		
		String tipostatoFlusso = dto.getStatoNotifica().equals(EnumTipoNotifica.ESEGUITO.getChiave()) ? StatiPagamentiIris.ESEGUITO.getFludMapping() : StatiPagamentiIris.ESEGUITO_SBF.getFludMapping();
		
		String tipostatoPaga = dto.getStatoNotifica().equals(EnumTipoNotifica.ESEGUITO.getChiave()) ? StatiPagamentiIris.ESEGUITO.getPagaMapping() : StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping();			

		////////////////////////////////
		//Aggiornamento ddp se presente
		///////////////////////////////
		
		DocumentoDiPagamento ddp = gf.getDocPagamento();
		
		String ddpKey=null;
		
		if (ddp!=null) {
			
			ddp.setStato(EnumStatoDDP.PAGATO.getChiave());
			
			ddpDAO.save(ddp);
			
			ddpKey=ddp.getId();
			
			gf.setEmailVersante(ddp.getEmailVersante());
			
		} else 
			ddpKey=dto.getIdPagamentoEnte();
		
		Set<PagamentiOnline> pagamentiOnline = gf.getPagamentiOnline();
		
//		String ddpKey = null;
//		Set<PagamentiOnline> pagamentiOnline = gf.getPagamentiOnline();
//		if (dto.getStatoNotifica().equals(EnumTipoNotifica.ESEGUITO.getChiave())) {
//			if (!pagamentiOnline.isEmpty()){
//				for (Iterator iterator = pagamentiOnline.iterator(); iterator.hasNext() && ddpKey == null;) {
//					PagamentiOnline pagamento = (PagamentiOnline) iterator.next();
//					ddpKey = pagamento.getCodAutorizzazione();
//				}
//				if (!StringUtils.isEmpty(ddpKey)){
//					
//					DocumentoDiPagamento ddp;
//				
//					ddp = ddpDAO.getById(DocumentoDiPagamento.class, ddpKey);
//					ddp.setStato(EnumStatoDDP.PAGATO.getChiave());
//					ddpDAO.save(ddp);
//				
//				}
//			}
//		}		
		
		// /////////////////////////////
		// aggiornamento gestione flussi
		// /////////////////////////////
		gf.setStato(tipostatoFlusso);
		gf.setTsUpdate(now);
		
		BigDecimal importoCommissioni = dto.getImportoCommissioni();
		
		if (importoCommissioni != null) {
			gf.setImportoCommissioni(importoCommissioni);
			gf.setTotimportipositivi(importoCommissioni.add(gf.getTotimportipositivi()).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		String codiceFiscalePagante = dto.getCodiceFiscalePagante();
		
		if (!StringUtils.isEmpty(codiceFiscalePagante))
			gf.setUtentecreatore(codiceFiscalePagante);
		
		String codiceContestoPagamento = dto.getCodiceContestoPagamento();
		
		if (!StringUtils.isEmpty(codiceContestoPagamento))
			gf.setCodTransazionePSP(codiceContestoPagamento);
		else
			gf.setCodTransazionePSP("0");
		
		//se il pagamento è eseguito aggiorno i dati dell'istituto attestante
		String tipoIdentifAttestante = null;
		String identifAttestante =null;
		String descrizAttestante =null;
		if (StatiPagamentiIris.ESEGUITO.getFludMapping().equals(tipostatoFlusso)) {
			
			CfgGatewayPagamento gw = gf.getCfgGatewayPagamento();
			
			//8 = Punto si
			//9 = CUp 2.0
			//10 = Punti Gialli
			if (gw.getCfgFornitoreGateway().getId() == 8 || gw.getCfgFornitoreGateway().getId() == 9 || gw.getCfgFornitoreGateway().getId() == 10) {
				tipoIdentifAttestante= "ASL";
			}
			//1 = BT
			//3 = CBILL
			//7 = Poste
			else if (gw.getCfgFornitoreGateway().getId() == 1 || gw.getCfgFornitoreGateway().getId()== 3 || gw.getCfgFornitoreGateway().getId()== 7 ) {
				tipoIdentifAttestante= "A";
			}
			//caso "non gestito"
			else {
				tipoIdentifAttestante= "-";
			}
			identifAttestante=gw.getSystemId();
			descrizAttestante=gw.getSystemName().toUpperCase();
		}

		gf.setDescrizioneAttestante(descrizAttestante);
		gf.setIdentificativoAttestante(identifAttestante);
		gf.setTipoIdentificativoAttestante(tipoIdentifAttestante);
		
		// ////////////////////////
		// aggiornamento pagamenti
		// ////////////////////////
		if (gf.getPagamenti() != null) {
			
			for (Pagamenti pagamento : gf.getPagamenti()) {
			
				pagamento.setStPagamento(tipostatoPaga);				
				pagamento.setIdDocumentoExt(dto.isNascondiDocumentoPagamentoIris()?"#ND":null);
				
				if ("E_BOLLO".equals(pagamento.getCdTrbEnte()) || (gf.getCfgGatewayPagamento().getFlagRendRiversamento().equals("0") && 
						CheckRiconciliazioneCompleta.isEnteRiconciliazioneCompleta(gf.getIdentificativoFiscaleCreditore()))) {
				   pagamento.setFlagIncasso("N");	
				} else {
				   pagamento.setFlagIncasso(dto.getFlagIncasso());
				}
							
				pagamento.setTsAggiornamento(now);
				
				Date dataPagamento = dto.getDataPagamento();
				
				if (dataPagamento!=null)
					
					pagamento.setTsDecorrenza(new Timestamp(dataPagamento.getTime()));
				
				if (!StringUtils.isEmpty(codiceFiscalePagante))
					
					pagamento.setCoPagante(codiceFiscalePagante);
			
			}
			
			gf.setNumeroDisposizioni(gf.getPagamenti().size());
			
			dtoOut = updatePaymentsByDetails(dtoOut, gf.getPagamenti(), dto.getDettagli(),dto.isNascondiDocumentoPagamentoIris()?"#ND":null);
			
			if (dtoOut.getReturnCode().isError()){
				
				context.setRollbackOnly();
				
				return dtoOut;
			}
		}

		// ////////////////////////////
		// inserimento pagamenti_online
		// ////////////////////////////
				
		EnumOperazioniPagamento statoOperazione = EnumOperazioniPagamento.getOperationByStatusDescription(dto.getStatoNotifica());
		
		PagamentiOnline pagaOnline = PaymentEntityBuilder.popolaPagamentoOnline(dto.getTestata(), ddpKey, gf, null, statoOperazione, dtoOut.getReturnCode(), "OPERATORE");
		
		if (gf.getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping())) {
			
			if (dto.getCodiceContestoPagamento()!=null) {
				gf.setCodTransazionePSP(dto.getCodiceContestoPagamento());
			} else {
				gf.setCodTransazionePSP("0");
			}
			
			pagaOnline.setIdOperazione(dto.getCodiceContestoPagamento());
			
		}
		
		if (pagamentiOnline == null)
			
			gf.setPagamentiOnline(new HashSet<PagamentiOnline>());

		pagamentiOnline.add(pagaOnline);

		// save gestione flussi
			
		gf = distintaDao.save(gf);
		
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Transazione : " + dto.getCodiceTransazione() + "messa in stato: " + dto.getStatoNotifica());
	
		dtoOut.setMsgId(gf.getCodPagamento());
		
		dtoOut.setCodPagamentoFlusso(gf.getCodPagamento());
		
		dtoOut.setDataOrdine(gf.getDataSpedizione());
		
		dtoOut.setCodiceFiscaleDebitore(gf.getOpInserimento());
		
		dtoOut.setIdFlusso(gf.getId());
		
		return dtoOut;
	}
	
	private EsitoOperazionePagamentoDTO updatePaymentsByDetails(EsitoOperazionePagamentoDTO dtoOut, Set<Pagamenti> pagamentiSet, List<DettaglioNotificaDTO> detailList,String idExtDocument){
		
		for(DettaglioNotificaDTO dett : detailList){
			
			Pagamenti correspondingPagamento = null;
			
			for (Pagamenti pagamento : pagamentiSet) {
				
				if (pagamento.getCondPagamento().getIdPagamento().equals(dett.getIdPagamentoCreditore())){
					
					correspondingPagamento = pagamento;
					
					break;
					
				}					
				
			}
			
			if (correspondingPagamento == null) {
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000027);
				
				return dtoOut;
			}
				
			correspondingPagamento.setNotePagamento(dett.getNotePagamento());
			
			correspondingPagamento.setIdRiscossionePSP(dett.getIdRiscossione());
			
			AllegatiPendenzaDTO allegatoDTO = dett.getAllegato();
					
			if (allegatoDTO != null) {
				
				if (!allegatoDTO.getTiAllegato().equals(EnumTipoAllegato.RICEVUTA)) {
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000025);
					
					return dtoOut;
				}
				
				dtoOut = createDocumentiRepository(dtoOut, allegatoDTO, correspondingPagamento,idExtDocument);
				
				if (dtoOut.getReturnCode().isError())
					
					return dtoOut;
			}
				
		}
		
		return dtoOut;
		
	}

	private EsitoOperazionePagamentoDTO createDocumentiRepository(EsitoOperazionePagamentoDTO dtoOut, AllegatiPendenzaDTO allegatoDTO, Pagamenti pagamento,String idExtDocument) {
		
		// la stringa ricevuta nella request è già stata decodificata da CXF
		// al momento della ricezione, quindi non serve decodificare ulteriormente in questa fase
		byte[] decodedContent = allegatoDTO.getDatiBody();
				
		byte[] unzippedContent = allegatoDTO.isCompressione() ? ZipUnzip.unzip(decodedContent) : decodedContent;
			
		dtoOut = isPDFDocument(dtoOut, unzippedContent);
		
		if (dtoOut.getReturnCode().isError())
			return dtoOut;
			
		DocumentiRepository docRepo = fillDocumentiRepository(unzippedContent, allegatoDTO.getTitolo(), pagamento.getFlussoDistinta().getUtentecreatore());
		
		DocumentiRepository docRepoNew = ddpBusinessBean.createDocumentiRepository(docRepo, pagamento.getId(), null,idExtDocument);
			
		pagamento.setIdDocumentoRepository(docRepoNew.getId());
		
		return dtoOut;
	}

	private EsitoOperazionePagamentoDTO isPDFDocument(EsitoOperazionePagamentoDTO dtoOut, byte[] unzippedDecodedContent) {
		
		boolean isPDFDocument =	PdfUtils.isPDFDocument(unzippedDecodedContent);
		
		if(!isPDFDocument)
			dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000026);
		
		return dtoOut;
	}

	public static DocumentiRepository fillDocumentiRepository(byte[] reportFlow, String downloadFileName,  String opInserimento) {
		
		DocumentiRepository docRepo = new DocumentiRepository();
		
		docRepo.setDocumento(reportFlow);
		
		docRepo.setDimensione(reportFlow.length);
		
		docRepo.setNomeFile(downloadFileName);
		
		docRepo.setOpInserimento(opInserimento);
		
		docRepo.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		
		return docRepo;		
	}


}
