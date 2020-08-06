package it.tasgroup.idp.ndp.rs.endpoints;

import static it.tasgroup.idp.ndp.utils.NdpRestApiConst.CODICE_CONTESTO_PAGAMENTO;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.gov.agenziaentrate.x2014.marcaDaBollo.TipoMarcaDaBollo;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.nodopagamentispc.DatiRicevutaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiSingoloPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DomainNotFoundException;
import it.nch.pagamenti.nodopagamentispc.DuplicatedRptException;
import it.nch.pagamenti.nodopagamentispc.RptNotFoundException;
import it.tasgroup.idp.ndp.rt.CtDatiSingoloPagamentoRT;
import it.tasgroup.idp.ndp.rt.CtDatiVersamentoRT;
import it.tasgroup.idp.ndp.rt.CtRicevutaTelematica;
import it.tasgroup.idp.ndp.utils.GiornaleEventiNdpDtoBuilder;
import it.tasgroup.idp.ndp.utils.JAXBMarshalling;
import it.tasgroup.idp.ndp.utils.PSPDTOBuilder;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNdpKo;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNdpOk;
import it.tasgroup.idp.rs.model.pagamenti.NdpAttivaVerificaRisposta;
import it.tasgroup.idp.rs.model.pagamenti.NdpAttivaVerificaRispostaOK;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaAnnullaNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaAttivaNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaInviaRT;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AnnullamentoPagamentoServiceLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneLocal;
import it.tasgroup.iris.business.ejb.client.pendenze.PendenzeBusinessLocal;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeLocal;
import it.tasgroup.iris.facade.ejb.restapi.PaymentAPIFacadeBeanLocal;
/**
 * Condizioni di Pagamento.
 */
@Path("/")
public class NdpEndpoint {


    @EJB
    PaymentAPIFacadeBeanLocal paymentAPI;

    @EJB
    DistintePagamentoFacadeLocal distintePagamentoFacade;
    
    @EJB(name = "AutorizzazioneBusiness")
	private AutorizzazioneLocal autorizzazioneBean;
	
	@EJB(name = "GiornaleEventiBusiness")
	private GiornaleEventiBusinessLocal giornaleEventiBean;
	
	@EJB(name = "DistintePagamentoFacade")
	private DistintePagamentoBusinessLocal distPagFacade;
	
	@EJB(name = "AnnullamentoBusiness")
	private AnnullamentoPagamentoServiceLocal annullamentoPagService;
	
	@EJB(name = "AnagraficaEntiFacade")
	private  AnagraficaEntiFacadeLocal anagraficaEntiService; 
	
	@EJB(name = "PendenzeBusiness")
	private  PendenzeBusinessLocal pendenzeBusinessService;
	
    
    private static final Logger LOG = LogManager.getLogger(NdpEndpoint.class.getName());
	
   
    
    @GET
    @Produces("application/json")
    @Path("apa_verifica")
    public Response verifica(@QueryParam("identificativoDominio") String identificativoDominio,
                                              @QueryParam("identificativoUnivocoVersamento") String identificativoUnivocoVersamento) {
    	
    	LOG.info("NdpEndpoint - verifica, richiesta: ");
		
    	NdpAttivaVerificaRisposta risposta = new NdpAttivaVerificaRisposta();
        
		GiornaleEventiExtDTO g = GiornaleEventiNdpDtoBuilder.createGiornaleEvRichiestaVerificaDTO(identificativoDominio, identificativoUnivocoVersamento,CODICE_CONTESTO_PAGAMENTO);
		
		giornaleEventiBean.save(g);
		
		RichiestaAutorizzazioneDTO autorizzazioneDto = PSPDTOBuilder.fillRichiestaAutorizzazioneDTO(identificativoDominio, identificativoUnivocoVersamento, null, null);

		EsitoOperazionePagamentoDTO esitoOpDto = autorizzazioneBean.verificaPagamentoPSP(autorizzazioneDto);				
		
		risposta = PSPDTOBuilder.buildEsitoAttivaVerifica(esitoOpDto);
		
		g = GiornaleEventiNdpDtoBuilder.createGiornaleEvRispostaVerificaAttivaDTO(identificativoDominio,
				identificativoUnivocoVersamento,
                risposta, true);


		giornaleEventiBean.save(g);
		
		
		
		LOG.info("NdpEndpoint - verifica, risposta: "+ risposta);		
		
		
		if ("OK".equals(risposta.getEsito())) {
		     return Response.ok(new NdpAttivaVerificaRispostaOK(risposta)).build();
		} else {
			 return Response.ok(new EsitoNdpKo(risposta)).build();
		}	
			
    }
    
    @POST
    @Produces("application/json")
    @Path("apa_attiva")
    public Response attiva(RichiestaAttivaNDP richiesta) {
    	
    	NdpAttivaVerificaRisposta risposta = new NdpAttivaVerificaRisposta();
        
    	LOG.info("NdpEndpoint - attiva, richiesta: ");
		    	
		GiornaleEventiExtDTO g = GiornaleEventiNdpDtoBuilder.createGiornaleEvRichiestaVerificaDTO(richiesta.getIdentificativoDominio(), richiesta.getIdentificativoUnivocoVersamento(),richiesta.getCodiceContestoPagamento());
		
		giornaleEventiBean.save(g);
			
		RichiestaAutorizzazioneDTO autorizzazioneDto = PSPDTOBuilder.fillRichiestaAutorizzazioneDTO(richiesta.getIdentificativoDominio(),
				                                                                                    richiesta.getIdentificativoUnivocoVersamento(),
				                                                                                    richiesta.getImportoSingoloVersamento(), 
				                                                                                    richiesta.getCodiceContestoPagamento());

		EsitoOperazionePagamentoDTO esitoOpDto = autorizzazioneBean.attivaPagamentoPSP(autorizzazioneDto);		
		
		risposta = PSPDTOBuilder.buildEsitoAttivaVerifica(esitoOpDto);
		
		g = GiornaleEventiNdpDtoBuilder.createGiornaleEvRispostaVerificaAttivaDTO(richiesta.getIdentificativoDominio(),
				                                                                  richiesta.getIdentificativoUnivocoVersamento(),
				                                                                  risposta, false);
		
		giornaleEventiBean.save(g);		
		
		LOG.info("NdpEndpoint - attiva - END ");
		if ("OK".equals(risposta.getEsito())) {
		     return Response.ok(new NdpAttivaVerificaRispostaOK(risposta)).build();
		} else {
			 return Response.ok(new EsitoNdpKo(risposta)).build();
		}
		
    }
    
    
    @POST
    @Produces("application/json")
    @Path("apa_annulla")
    public Response annulla(RichiestaAnnullaNDP richiesta){
    	EsitoNdpKo esito = new EsitoNdpKo();
    	// controllo esistenza dell'ente
    	try {
    	   anagraficaEntiService.getEnteBasicDataByCodiceFiscale(richiesta.getIdentificativoDominio());
    	} catch (Exception e) {
    		esito.setEsito("KO");
    		esito.setFaultCode("00000");
    		esito.setFaultDescription("Ente non censito");
    		esito.setFaultString("Ente non censito");
    		return Response.ok(esito).build();
    	}
    	// controllo esistenza della posizione
    	CondizionePagamento cond = null;
    	try {
    	   cond = pendenzeBusinessService.getCondizioniByCfDebitoreAndIuv(richiesta.getIdentificativoDominio(),richiesta.getIdentificativoUnivocoVersamento());
    	} catch (Throwable t) {
    		
    	}
    	if (cond==null) {
    		esito.setEsito("KO");
    		esito.setFaultCode("00001");
    		esito.setFaultDescription("Posizione non presente");
    		esito.setFaultString("Posizione non presente");
    		return Response.ok(esito).build();
    	}
    	// controllo esistenza di pagamento in corso
    	String idCondizione=cond.getIdCondizione();
    	if (!annullamentoPagService.checkPagamentoInCorso(idCondizione)) {
    		esito.setEsito("KO");
    		esito.setFaultCode("00002");
    		esito.setFaultDescription("Non esistono pagamenti in corso");
    		esito.setFaultString("Non esistono pagamenti in corso");
    		return Response.ok(esito).build();
    	}
    	
    	annullamentoPagService.annullaPagamentoNDP(idCondizione, StatiPagamentiIris.ANNULLATO);
    	esito.setEsito("OK");	
    	if ("OK".equals(esito.getEsito())) {
		     return Response.ok(new EsitoNdpOk()).build();
		} else {
			 return Response.ok(new EsitoNdpKo()).build();
		}
		
    }
    
    
    
    @POST
    @Produces("application/json")
    @Path("invia_ricevuta_telematica")
    public Response inviaRicevutaRT(RichiestaInviaRT richiesta) {
    	
    	LOG.info("NdpEndpoint - inviaRicevutaRT BEGIN");
         
    	EsitoNDP esito = new EsitoNDP();
 		
        GiornaleEventiExtDTO g = GiornaleEventiNdpDtoBuilder.createGiornaleEvRichiestaRicevutaDTO( 
        		richiesta.getIdentificativoDominio(),
        		richiesta.getIdentificativoUnivocoVersamento(),
        		richiesta.getCodiceContestoPagamento(),
        		richiesta.getRicevutaTelematica());
        
 		giornaleEventiBean.save(g);
 		
 		
 		try {
 			String rtXmlString = new String(richiesta.getRicevutaTelematica());			
 			LOG.debug("NdpEndpoint - inviaRicevutaRT RT ricevuta");
 			LOG.debug(rtXmlString);
 			
 			
 			//
 			// unmarshall di RT 
 			//
 			JAXBElement<CtRicevutaTelematica> rtJaxb = (JAXBElement<CtRicevutaTelematica>) JAXBMarshalling.getObject("it.tasgroup.idp.ndp.rt", rtXmlString);
 			CtRicevutaTelematica rt = rtJaxb.getValue();
 			CtDatiVersamentoRT datiVersamento = rt.getDatiPagamento();
 			
 			//
 			// creazione VO per richiesta di aggiornamento
 			//
 			DatiRicevutaPagamentoVO datiRicevuta = new DatiRicevutaPagamentoVO();
 			datiRicevuta.setRiferimentoMessaggioRichiesta(rt.getRiferimentoMessaggioRichiesta());
 			datiRicevuta.setRiferimentoDataRichiesta(rt.getRiferimentoDataRichiesta().toGregorianCalendar().getTime());
 			datiRicevuta.setCodiceEsitoPagamento(datiVersamento.getCodiceEsitoPagamento());
 			datiRicevuta.setIdentificativoUnivocoVersamento(datiVersamento.getIdentificativoUnivocoVersamento());
 			datiRicevuta.setCodiceContestoPagamento(datiVersamento.getCodiceContestoPagamento());
 			datiRicevuta.setIdentificativoFiscaleCreditore(rt.getDominio().getIdentificativoDominio());
 			
 			String codiceIdentificativoUnivocoPSP = rt.getIstitutoAttestante().getIdentificativoUnivocoAttestante().getCodiceIdentificativoUnivoco();
 			String tipoIdentificativoPSP = rt.getIstitutoAttestante().getIdentificativoUnivocoAttestante().getTipoIdentificativoUnivoco().name();
 			String descrizionePSPattestante = rt.getIstitutoAttestante().getDenominazioneAttestante();
 			
 			LOG.debug("NdpEndpoint - inviaRicevutaRT RT ricevuta codiceIdentificativoUnivocoPSP = '"+ codiceIdentificativoUnivocoPSP+"' , tipoIdentificativoPSP = '"+tipoIdentificativoPSP+"' "); 
 			
 			datiRicevuta.setCodiceIdentificativoUnivocoPSP(codiceIdentificativoUnivocoPSP);
 			datiRicevuta.setTipoIdentificativoPSP(tipoIdentificativoPSP);
 			datiRicevuta.setDescrizionePSP(descrizionePSPattestante.toUpperCase());
 			
 			for (CtDatiSingoloPagamentoRT ctDatiSingoloPagamentoRT : datiVersamento.getDatiSingoloPagamento()) {
 								
 				DatiSingoloPagamentoVO datiSingoloPagamento = new DatiSingoloPagamentoVO();
 				datiSingoloPagamento.setIdentificativoUnivocoRiscossione(ctDatiSingoloPagamentoRT.getIdentificativoUnivocoRiscossione());
 				datiSingoloPagamento.setEsitoSingoloPagamento(ctDatiSingoloPagamentoRT.getEsitoSingoloPagamento());
 				datiSingoloPagamento.setSingoloImportoPagato(ctDatiSingoloPagamentoRT.getSingoloImportoPagato());
 				datiSingoloPagamento.setDataEsitoSingoloPagamento(ctDatiSingoloPagamentoRT.getDataEsitoSingoloPagamento().toGregorianCalendar().getTime());
 				datiSingoloPagamento.setCommissioniApplicatePSP(ctDatiSingoloPagamentoRT.getCommissioniApplicatePSP());
 				if (ctDatiSingoloPagamentoRT.getAllegatoRicevuta()!=null) {
 				  datiSingoloPagamento.setTipoAllegatoRicevuta(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTipoAllegatoRicevuta().toString());
 				  datiSingoloPagamento.setAllegatoRicevuta(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTestoAllegato());
 			      if ("BD".equals(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTipoAllegatoRicevuta().toString())) {
 			    	  String alleg = new String(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTestoAllegato());
 			    	  datiSingoloPagamento.setDatiricevutaMBD(parseAllegatoMBD(alleg));
 			    	  
 			      }
 				}
 				datiRicevuta.addDatiSingoloVersamento(datiSingoloPagamento);
 			}
 			
 			//
 			// aggiornamento dati distinta e pagamenti
 			//
 			
 			distPagFacade.aggiornaEsitoDaRT(datiRicevuta);
 			LOG.debug("NdpEndpoint - inviaRicevutaRT Aggiornamento dati terminato con successo");
 		    esito.setEsito("OK");
         } catch (DuplicatedRptException d) {	
 			
 			esito.setEsito("KO"); 			
 			esito.setFaultCode("PAA_RT_DUPLICATA");
 			esito.setFaultDescription("Dominio non censito");
 			LOG.error("[PagamentiTelematiciRTImpl::paaInviaRT] PAA_RT_DUPLICATA ");    
 	        
 		} catch (DomainNotFoundException d) {	
 			
 			esito.setEsito("KO"); 			
 			esito.setFaultCode("PAA_ID_DOMINIO_ERRATO");
 			esito.setFaultDescription("Dominio non censito");
 			LOG.error("[PagamentiTelematiciRTImpl::paaInviaRT] PAA_ID_DOMINIO_ERRATO");
 			
 		} catch (RptNotFoundException r) {
 			
 			esito.setEsito("KO"); 			
 			esito.setFaultCode("PAA_RPT_SCONOSCIUTA");
 			esito.setFaultDescription("RPT non trovata");
 			LOG.error("[PagamentiTelematiciRTImpl::paaInviaRT] PAA_RPT_SCONOSCIUTA");
 			
 		}
 		catch (Throwable e) {
 			
 			esito.setEsito("KO"); 			
 			esito.setFaultCode("PAA_SYSTEM_ERROR");
 			esito.setFaultDescription("Errore generico");
 			LOG.error("[PagamentiTelematiciRTImpl::paaInviaRT] PAA_SYSTEM_ERROR", e);
 			
 		}
 				
 		
 		g = GiornaleEventiNdpDtoBuilder.createGiornaleEvRispostaRTDTO(richiesta.getIdentificativoDominio(),
 				richiesta.getIdentificativoUnivocoVersamento(),
 				richiesta.getCodiceContestoPagamento(),esito);
 		giornaleEventiBean.save(g);
 		
 		LOG.info("NdpEndpoint - inviaRicevutaRT operation  terminata");
         
 		if ("OK".equals(esito.getEsito())) {
		     return Response.ok(new EsitoNdpOk()).build();
		} else {
			 return Response.ok(esito).build();
		}
    }
    /**
     * Ritorna la lista dei Pagamenti effettuati sulla piattaforma da parte di un debitore utilizzando il suo codice fiscale.
     *
     * @param codiceFiscaleDebitore           [Obbligatorio] il codice fiscale del debitore
     * @param catTributo                      [Opzionale] codice categoria del tributo, per filtrare le condizioni (e.g. "Categoria013", si ottengono solo le prestazioni sanitarie )
     * @param flagInCorso          			  [Opzionale] Se valorizzato (String, != null), restituisce anche i pagamenti in corso. Se null vengono esclusi i pagamenti in corso
     * 
     * @return Array di <a href="el_ns0_pagamento.html">Pagamento</a>
     * @throws Exception
     */
    @POST    
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/notifica_riconciliazione")
    public Response notificaRiconciliazione(RichiestaNotificaPagamento request) throws Exception {
        EsitoNDP outputDTO = distintePagamentoFacade.notificaPagamento(request);
        if ("OK".equals(outputDTO.getEsito())) {
		     return Response.ok(new EsitoNdpOk()).build();
		} else {
			 return Response.ok(outputDTO).build();
		}
    }

    
    /**
	 * @param allegatotestoMBD
	 * @return
	 * @throws Exception
	 */
	private  String  parseAllegatoMBD(String allegatotestoMBD) throws Exception {
		
		//
		// unmarshall di RT
		//
		JAXBElement<TipoMarcaDaBollo> rtJaxb = (JAXBElement<TipoMarcaDaBollo>) JAXBMarshalling.getObject("it.tasgroup.idp.marca_bollo_digitale", allegatotestoMBD);
		TipoMarcaDaBollo rt = rtJaxb.getValue();
		
		

		String out =
		"IUBD = " +rt.getIUBD() +";"+
    	"DATA_ORA_ACQUISTO = " +rt.getOraAcquisto()+";"+
		"PSP_EMITTENTE = " +rt.getPSP().getDenominazione()+
		"(" +rt.getPSP().getCodiceFiscale()+")";
		return out;
		
	}
   
}
