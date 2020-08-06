package it.tasgroup.idp.billerservices.controller.richiestapagamento;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMap;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.xmlbillerservices.pendenze.CondizionePagamento;
import it.tasgroup.idp.xmlbillerservices.pendenze.Dettaglio;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpAllineamentoPendenzeMultiOTFElementType;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpMultiEsitoOTF;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpMultiEsitoOTFElement;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpOTFType;
import it.tasgroup.idp.xmlbillerservices.pendenze.InfoDettaglio;
import it.tasgroup.idp.xmlbillerservices.pendenze.InfoMessaggio;
import it.tasgroup.idp.xmlbillerservices.pendenze.Pendenza;
import it.tasgroup.idp.xmlbillerservices.pendenze.StatoDettaglio;
import it.tasgroup.idp.xmlbillerservices.pendenze.StatoMessaggio;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamentoResponse;
import it.tasgroup.iris.domain.SessioneGateway;

import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RichiestaPagamentoControllerEsitoBuilder {
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	/**
	 * Costruisce l'esito KO con errori relativi all'intero messaggio.
	 * @param allineamentoPendenzeOTF
	 * @param errorsMap 
	 */
	public RichiestaPagamentoResponse buildEsitoKOInteroMessaggio(RichiestaPagamento allineamentoPendenzeOTF, EnumReturnValues errore) {
	
		RichiestaPagamentoResponse  esito = new RichiestaPagamentoResponse();
		IdpMultiEsitoOTF esitoInner = new IdpMultiEsitoOTF();
		esito.setIdpMultiEsitoOTF(esitoInner);
		//esitoInner.setVersione(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getVersione());
		IdpMultiEsitoOTFElement eMsg= new IdpMultiEsitoOTFElement();
				
		InfoMessaggio eInfoMsg = buildInfoMessaggio(errore,null);
		eMsg.setInfoMessaggio(eInfoMsg);
		eMsg.setMsgId(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getMsgId());
		
	    esitoInner.setIdpEsitoOTF(eMsg);
	    
	    return esito;
	}
	
	public RichiestaPagamentoResponse buildEsitoKO(
			RichiestaPagamento allineamentoPendenzeOTF,
			HashMap<String, EnumReturnValues> errorsMap, HashMap<String, ValidationException> validationExceptionMap ) {
		
			RichiestaPagamentoResponse  esito = new RichiestaPagamentoResponse();
			IdpMultiEsitoOTF esitoInner = new IdpMultiEsitoOTF();

			InfoDettaglio infoDettaglio = new InfoDettaglio();
			
			// Scorro i messaggi in ingresso e ritorno nell'esito un messaggio relativo solo a quelli andati male.
			for (IdpAllineamentoPendenzeMultiOTFElementType inMsg : allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getIdpAllineamentoPendenzeOTF()) {
				String e2emsgid=inMsg.getE2EMsgId();
				if (errorsMap.containsKey(e2emsgid) || validationExceptionMap.containsKey(e2emsgid)) {
					
					InfoMessaggio eInfoMsg = buildInfoMessaggio(errorsMap.get(e2emsgid), validationExceptionMap.get(e2emsgid));

					Dettaglio dettaglioErrore = new Dettaglio();
					dettaglioErrore.setE2EMsgId(e2emsgid);
					dettaglioErrore.setCodice(eInfoMsg.getCodice());
					dettaglioErrore.setDescrizione(eInfoMsg.getDescrizione());
					
					if (validationExceptionMap.containsKey(e2emsgid)) {
						ValidationException exp=validationExceptionMap.get(e2emsgid);
						dettaglioErrore.setIdDebito(exp.getIdDebito());
						dettaglioErrore.setIdPagamento(exp.getIdPagamento());
					}
					


					dettaglioErrore.setStato(StatoDettaglio.KO);  // Per ora gestiamo solo questo
					dettaglioErrore.setNote(null); // Per ora non si mettono.. 
					
									

					infoDettaglio.getDettaglio().add(dettaglioErrore);
			
				}
			}
			
			IdpMultiEsitoOTFElement esitoElement = new IdpMultiEsitoOTFElement();
			esitoInner.setIdpEsitoOTF(esitoElement);
			
			InfoMessaggio infoMessaggioComplessivo = new InfoMessaggio();
			 
			infoMessaggioComplessivo.setStato(StatoMessaggio.ELABORATO_KO);
			infoMessaggioComplessivo.setCodice(EnumReturnValues.KO.getKey());
			infoMessaggioComplessivo.setDescrizione("Vedi InfoDettaglio");
			
			esitoElement.setInfoMessaggio(infoMessaggioComplessivo);
			esitoElement.setInfoDettaglio(infoDettaglio);
			esitoElement.setMsgId(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getMsgId());

			esito.setIdpMultiEsitoOTF(esitoInner);
			
		return esito;
	}
	
	
	public RichiestaPagamentoResponse buildEsitoOK(
			RichiestaPagamento allineamentoPendenzeOTF,
			SessioneGateway sessioneGateway, EntityManager em) {
			
		RichiestaPagamentoResponse  esito = new RichiestaPagamentoResponse();
		IdpMultiEsitoOTF esitoInner = new IdpMultiEsitoOTF();
		esito.setIdpMultiEsitoOTF(esitoInner);
		
		IdpOTFType idpOtfEsito = new IdpOTFType();
		idpOtfEsito.setIdSessioneGW(sessioneGateway.getToken());
		String urlGw = IrisProperties.getProperty("GATEWAY_URL");
		idpOtfEsito.setUrlGW(urlGw + sessioneGateway.getToken());
		esitoInner.setIdpOTF(idpOtfEsito);
		
		IdpMultiEsitoOTFElement esitoElement = new IdpMultiEsitoOTFElement();
		esitoElement.setMsgId(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getMsgId());
		InfoMessaggio infoMessaggio = new InfoMessaggio();
		infoMessaggio.setCodice(EnumReturnValues.OK.getKey());
		infoMessaggio.setStato(StatoMessaggio.ELABORATO_OK);
		infoMessaggio.setDescrizione("Elaborato correttamente");
		
		esitoElement.setInfoMessaggio(infoMessaggio);
		esitoInner.setIdpEsitoOTF(esitoElement);
		
		
		String includeSuccess =IrisProperties.getProperty("includes.result.ok.esiti","false");
		boolean includeIUV = IrisProperties.getProperty("includes.iuv.esiti","false").equals("true");
		if (includeSuccess != null && !includeSuccess.equals("false")) {
			// infodettaglio
			InfoDettaglio infoDett = new InfoDettaglio();
			esitoElement.setInfoDettaglio(infoDett);
			// ****** inserisco l esito dell evento
			for (IdpAllineamentoPendenzeMultiOTFElementType inMsg : allineamentoPendenzeOTF
					.getIdpAllineamentoPendenzeMultiOTF()
					.getIdpAllineamentoPendenzeOTF()) {
				String e2emsgid = inMsg.getE2EMsgId();

				
				for (Pendenza p : inMsg.getIdpBody().getPendenza()) {

					Enti ente = null;
					try {
						ente = GestoreVerificheAnagrafiche.getEnteByCodEnte(
								inMsg.getE2ESender().getE2ESndrId(), em);
					} catch (Exception l) {
						logger.error(l);
					}
					TributiEnti tributo = null;
					try {
						tributo = GestoreVerificheAnagrafiche
								.getTributoEnteByIdEnteCodTributo(
										ente.getIdEnte(), inMsg.getIdpBody()
												.getPendenza().get(0)
												.getTipoDebito(), em);
					} catch (Exception l) {
						logger.error(l);
					}
					for (CondizionePagamento c : p.getInfoPagamento()
							.getCondizionePagamento()) {
						Dettaglio dett = new Dettaglio();
						dett.setE2EMsgId(e2emsgid);
						dett.setStato(StatoDettaglio.OK);
						dett.setIdDebito(p.getIdDebito());
						dett.setIdPagamento(c.getIdPagamento()); // //in questo
																	// caso
																	// stiamo
																	// analizzando
																	// quanto
																	// fornito
																	// in input
																	// da ws
						// quindi id-pagamento e sempre contenuto nel messaggio
						// originale
						// fornito nella invocazione
						if (includeIUV) {
							if (!tributo.getFlNdpIuvGenerato().equals("0")) {
								Query q1 = em
										.createNamedQuery("IUVPosizEnteMapByIdEnteAndIdPagamento");
								q1.setParameter("idPagamento",
										c.getIdPagamento());
								q1.setParameter("idEnte", tributo.getId()
										.getIdEnte());
								List<IUVPosizEnteMap> lIuvMapList = (List<IUVPosizEnteMap>) q1
										.getResultList();
								if (lIuvMapList.size() > 0) {
									dett.setIdentificativoUnivocoVersamento(lIuvMapList
											.get(0).getId().getIuv());
								} else {
									//
								}

							} else {
								dett.setIdentificativoUnivocoVersamento(c
										.getIdPagamento());
							}
						}
						infoDett.getDettaglio().add(dett);
					}
				}
			}
		}
		return esito;
	}
	
	
	private InfoMessaggio  buildInfoMessaggio(EnumReturnValues errorCodeEnum, ValidationException validationException) {
		
	    InfoMessaggio eInfoMsg = new InfoMessaggio();
	    eInfoMsg.setStato(StatoMessaggio.ELABORATO_KO);

	    String errorCode="";
	    String errorDescription="";

	        
	        // Decodifico errore:
	        if (errorCodeEnum!=null) {
	        	// Errori anagrafici, etc.. 
	        	errorCode = errorCodeEnum.getKey();
	        	errorDescription= errorCodeEnum.getDescription();
		       		        
	        } else if (validationException!=null) {
	        	errorCode = validationException.getErrorCode().getKey();
	        	errorDescription = validationException.getDescription();
	        }

	       
	        eInfoMsg.setCodice(errorCode);
	        eInfoMsg.setDescrizione(errorDescription);
	      
		return eInfoMsg;
	}
		

}
