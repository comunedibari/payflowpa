package it.tasgroup.idp.configurazioneente.controller;

import java.util.ResourceBundle;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esiti;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpBodyType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpEsito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoDettaglio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoMessaggio;

public class ConfigurazioneEnteControllerEsitoBuilder {
	
	
	public it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito buildEsitoOK(it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte idpConfigurazioneEnte) {
		it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito esito = new it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito();
		IdpEsito esitoInner = new IdpEsito();
		esitoInner.setIdpBody(new IdpBodyType());
		esito.setIdpEsito(esitoInner);
		
		InfoMessaggio eInfoMsg = new InfoMessaggio();
		esitoInner.getIdpBody().setInfoMessaggio(eInfoMsg);
		
		esitoInner.setIdpHeader(idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpHeader());
		esitoInner.setVersione(idpConfigurazioneEnte.getIdpConfigurazioneEnte().getVersione());
		
		esitoInner.getIdpBody().setInfoDettaglio(new InfoDettaglio());
	    
	    eInfoMsg.setStato(StatoMessaggio.ELABORATO_CORRETTAMENTE);
		return esito;
	}

	public it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito buildEsitoKO(it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnte idpConfigurazioneEnte, EnumReturnValues errore) {
		it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito esito = new it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpconfigurazione.IdpConfigurazioneEnteEsito();
		IdpEsito esitoInner = new IdpEsito();
		esitoInner.setIdpBody(new IdpBodyType());
		esito.setIdpEsito(esitoInner);
		
		InfoMessaggio eInfoMsg = new InfoMessaggio();
		esitoInner.getIdpBody().setInfoMessaggio(eInfoMsg);
		
		esitoInner.setIdpHeader(idpConfigurazioneEnte.getIdpConfigurazioneEnte().getIdpHeader());
		esitoInner.setVersione(idpConfigurazioneEnte.getIdpConfigurazioneEnte().getVersione());
	
		
		esitoInner.getIdpBody().setInfoDettaglio(new InfoDettaglio());
		
		
		Esiti eInfoMsgEsiti = new Esiti();
	    Esito eInfoMsgEsito = new Esito();
	    
	    eInfoMsg.setStato(StatoMessaggio.ELABORATO_CON_ERRORI);
		
		
		if (errore != null) {
			String errorBundleKey = errore.getBundleKey();
			String errorCode = "";
			String errorDescription = "";
			if (errorBundleKey != null && !"".equals(errorBundleKey.trim())) {
		        errorCode  = mapErrorCodeFromErrorKey(errorBundleKey);
		        errorDescription = mapErrorCodeFromErrorKey(errorCode);
        	} else {
        		errorCode = errore.getKey();
        		errorDescription = errore.getDescription();
        	}
			eInfoMsgEsito.setCodice(errorCode);
	        eInfoMsgEsito.setDescrizione(errorDescription); 
	        eInfoMsgEsiti.getEsito().add(eInfoMsgEsito);
	        eInfoMsg.setEsiti(eInfoMsgEsiti);
		}
		return esito;
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

}
