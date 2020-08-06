package it.tasgroup.idp.billerservices.controller;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.iris.domain.SessioneGateway;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenzeMultiOTFElementType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esiti;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpBodyType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpMultiEsitoOTF;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpMultiEsitoOTFElement;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpOTFType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTFEsito;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ComunicazionePosizioniDebitorieControllerEsitoBuilder {
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	/**
	 * Costruisce l'esito KO con errori relativi all'intero messaggio.
	 * @param allineamentoPendenzeOTF
	 * @param errorsMap
	 */
	public IdpAllineamentoPendenzeMultiEnteOTFEsito buildEsitoKOInteroMessaggio(IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF, EnumReturnValues errore) {
	
		IdpAllineamentoPendenzeMultiEnteOTFEsito  esito = new IdpAllineamentoPendenzeMultiEnteOTFEsito();
		IdpMultiEsitoOTF esitoInner = new IdpMultiEsitoOTF();
		esito.setIdpMultiEsitoOTF(esitoInner);
		esitoInner.setVersione(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getVersione());
		IdpMultiEsitoOTFElement eMsg= new IdpMultiEsitoOTFElement();
		IdpBodyType eBody = new IdpBodyType();
		
		InfoMessaggio eInfoMsg = buildInfoMessaggio(errore,null);
	    	
	    eBody.setInfoMessaggio(eInfoMsg);
	    eMsg.setIdpBody(eBody);
	    eMsg.setE2EMsgId("ALL_MESSAGE");
	    esitoInner.getIdpEsitoOTF().add(eMsg);
	    
	    return esito;
	}
	
	public IdpAllineamentoPendenzeMultiEnteOTFEsito buildEsitoKO(
			IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF,
			HashMap<String, EnumReturnValues> errorsMap, HashMap<String, ValidationException> validationExceptionMap ) {
		
			IdpAllineamentoPendenzeMultiEnteOTFEsito  esito = new IdpAllineamentoPendenzeMultiEnteOTFEsito();
			IdpMultiEsitoOTF esitoInner = new IdpMultiEsitoOTF();
			esito.setIdpMultiEsitoOTF(esitoInner);
			esitoInner.setVersione(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getVersione());
						
			// Scorro i messaggi in ingresso e ritorno nell'esito un messaggio relativo solo a quelli andati male.
			for (IdpAllineamentoPendenzeMultiOTFElementType inMsg : allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getIdpAllineamentoPendenzeOTF()) {
				String e2emsgid=inMsg.getE2EMsgId();
				if (errorsMap.containsKey(e2emsgid) || validationExceptionMap.containsKey(e2emsgid)) {
					IdpMultiEsitoOTFElement eMsg= new IdpMultiEsitoOTFElement();
					IdpBodyType eBody = new IdpBodyType();
					
					InfoMessaggio eInfoMsg = buildInfoMessaggio(errorsMap.get(e2emsgid), validationExceptionMap.get(e2emsgid));
					
//					    InfoMessaggio eInfoMsg = new InfoMessaggio();
//					      eInfoMsg.setStato(StatoMessaggio.ELABORATO_CON_ERRORI);
//					      Esiti eInfoMsgEsiti = new Esiti();
//					       Esito eInfoMsgEsito = new Esito();
//	
//				        	String errorBundleKey="";
//					        String errorCode="";
//					        String errorDescription="";
//	
//					        // Decodifico errore:
//					        if (errorsMap.get(e2emsgid)!=null) {
//					        	// Errori anagrafici, etc.. 
//						       
//					        	errorBundleKey = errorsMap.get(e2emsgid).getBundleKey();
//					        	
//					        	if (errorBundleKey!=null && !"".equals(errorBundleKey.trim())) {
//							        errorCode  = mapErrorCodeFromErrorKey(errorBundleKey);
//							        errorDescription=mapErrorDescriptionFromErrorKCode(errorCode);
//					        	} else {
//					        		errorCode = errorsMap.get(e2emsgid).getKey();
//					        		errorDescription=errorCode;
//					        	}
//						        
//					        	
//					        	
//					        	
//					        } else if (validationExceptionMap.get(e2emsgid)!=null) {
//					        	// Errori di validazione semantica delle pendenze
//					        						        				        
//						        if (EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA==validationExceptionMap.get(e2emsgid).getErrorCode()) {
//						        	errorCode="V0000001";
//						        	errorDescription = validationExceptionMap.get(e2emsgid).getDescription();
//						        } else {
//						        	
//						        	errorBundleKey = validationExceptionMap.get(e2emsgid).getErrorCode().getBundleKey();
//
//						        	if (errorBundleKey!=null && !"".equals(errorBundleKey.trim())) {
//								        errorCode  = mapErrorCodeFromErrorKey(errorBundleKey);
//								        errorDescription = mapErrorDescriptionFromErrorKCode(errorCode);
//						        	} else {
//						        		errorCode = validationExceptionMap.get(e2emsgid).getErrorCode().getKey();
//						        		errorDescription = validationExceptionMap.get(e2emsgid).getDescription();
//						        	}     	
//						        }
//	
//					        }
//					  
//					        eInfoMsgEsito.setCodice(errorCode);
//					        eInfoMsgEsito.setDescrizione(errorDescription); 
//					       eInfoMsgEsiti.getEsito().add(eInfoMsgEsito);
//					      eInfoMsg.setEsiti(eInfoMsgEsiti);
					      
					      
						eBody.setInfoMessaggio(eInfoMsg);
					eMsg.setE2EMsgId(e2emsgid);	
					eMsg.setIdpBody(eBody);
					esitoInner.getIdpEsitoOTF().add(eMsg);
				}
			}
			
		return esito;
	}
	
	
	public IdpAllineamentoPendenzeMultiEnteOTFEsito buildEsitoOK(
			IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF,
			SessioneGateway sessioneGateway) {
			
		IdpAllineamentoPendenzeMultiEnteOTFEsito  esito = new IdpAllineamentoPendenzeMultiEnteOTFEsito();
		IdpMultiEsitoOTF esitoInner = new IdpMultiEsitoOTF();
		esito.setIdpMultiEsitoOTF(esitoInner);
		esitoInner.setVersione(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getVersione());
		
		IdpOTFType idpOtfEsito = new IdpOTFType();
		idpOtfEsito.setIdSessioneGW(sessioneGateway.getToken());
		String urlGw = IrisProperties.getProperty("GATEWAY_URL");
		idpOtfEsito.setUrlGW(urlGw + sessioneGateway.getToken());
		idpOtfEsito.setVersione(allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getVersione());
		esitoInner.setIdpOTF(idpOtfEsito);
		return esito;
		
	}

	
	
	private InfoMessaggio  buildInfoMessaggio(EnumReturnValues errorCodeEnum, ValidationException validationException) {
		
	    InfoMessaggio eInfoMsg = new InfoMessaggio();
	      eInfoMsg.setStato(StatoMessaggio.ELABORATO_CON_ERRORI);
	      Esiti eInfoMsgEsiti = new Esiti();
	       Esito eInfoMsgEsito = new Esito();

      	String errorBundleKey="";
	        String errorCode="";
	        String errorDescription="";

	        // Decodifico errore:
	        if (errorCodeEnum!=null) {
	        	// Errori anagrafici, etc.. 
		       
	        	errorBundleKey = errorCodeEnum.getBundleKey();
	        	
	        	if (errorBundleKey!=null && !"".equals(errorBundleKey.trim())) {
			        errorCode  = mapErrorCodeFromErrorKey(errorBundleKey);
			        errorDescription=mapErrorDescriptionFromErrorKCode(errorCode);
	        	} else {
	        		errorCode = errorCodeEnum.getKey();
	        		errorDescription=errorCode;
	        	}
		        
	        } else if (validationException!=null) {
	        	// Errori di validazione semantica delle pendenze
	        						        				        
		        if (EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA==validationException.getErrorCode()) {
		        	errorCode="V0000001";
		        	errorDescription = validationException.getDescription();
		        } else {
		        	
		        	errorBundleKey = validationException.getErrorCode().getBundleKey();

		        	if (errorBundleKey!=null && !"".equals(errorBundleKey.trim())) {
				        errorCode  = mapErrorCodeFromErrorKey(errorBundleKey);
				        errorDescription = mapErrorDescriptionFromErrorKCode(errorCode);
		        	} else {
		        		errorCode = validationException.getErrorCode().getKey();
		        		errorDescription = validationException.getDescription();
		        	}     	
		        }

	        }
	  
	        eInfoMsgEsito.setCodice(errorCode);
	        eInfoMsgEsito.setDescrizione(errorDescription); 
	       eInfoMsgEsiti.getEsito().add(eInfoMsgEsito);
	      eInfoMsg.setEsiti(eInfoMsgEsiti);
	      
		return eInfoMsg;
	}
	

	private String mapErrorCodeFromErrorKey(String errorkey) {
		String localMessage = null;
		try {
			localMessage = ResourceBundle.getBundle("error").getString(errorkey);
		} catch (Exception e) {
			return errorkey;
		}
		return localMessage;
	}
	
	private String mapErrorDescriptionFromErrorKCode(String errorCode) {
		String localMessage = null;
		try {
			localMessage = ResourceBundle.getBundle("error").getString(errorCode);

		} catch (Exception e) {
			return errorCode;
		}
		return localMessage;
	}

	

}
