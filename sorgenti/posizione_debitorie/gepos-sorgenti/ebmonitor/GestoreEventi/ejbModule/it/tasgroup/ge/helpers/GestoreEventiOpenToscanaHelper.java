package it.tasgroup.ge.helpers;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.Eventi;
import it.tasgroup.ge.enums.EnumStatoEventi;
import it.tasgroup.ge.enums.EnumTipiEventi;
import it.tasgroup.ge.opentoscana.Client;
import it.tasgroup.ge.opentoscana.Client.Method;
import it.tasgroup.ge.opentoscana.RTMessage;
import it.tasgroup.ge.opentoscana.TokenRetriever;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.idp.util.IrisProperties;

public abstract class GestoreEventiOpenToscanaHelper implements GestoreEventiHelper {

	protected static final Log logger = LogFactory.getLog(GestoreEventiOpenToscanaHelper.class);
	
	protected static final String ST_RIGA_VALIDA = "V";
	protected static final String ST_RIGA_ANNULLATA = "A";
	
	protected EntityManager em = null;
	
	protected String messageId = null;
	protected Method method = null;
	
	public abstract RTMessage getRTMessage(CommunicationEvent e, CfgEventi cfgEvento);

	private static String rtMessengerEndpoint;
	
	// inizializzazione
	static {
		rtMessengerEndpoint = IrisProperties.getProperty("rt.messenger.endpoint.opentoscana");
		logger.info("Init GestoreEventiOpenToscanaHelper");
		logger.info("rt.messenger.endpoint.opentoscana = " + rtMessengerEndpoint);
	}
	
	@Override
	public String fireCommunicationEvt(CommunicationEvent communicationEvent, CfgEventi cfgEvento) throws Exception {

		// creazione rtmessage
		RTMessage rtMessage = getRTMessage(communicationEvent, cfgEvento);
		/*
		 *  il codice evento puo' essere cambiato nella costruzione del messaggio (110 -> 103)
		 *  non facciamo quindi "cfgEvento.getCodiceEvento()" ma "rtMessage.getCodiceEvento()"
		 */
		String codiceEvento = rtMessage.getCodiceEvento();
		String codiceEventoLog = codiceEvento.equals(cfgEvento.getCodiceEvento()) ? codiceEvento : codiceEvento + " (ex " + cfgEvento.getCodiceEvento() + ")";
		logger.info("OpenToscana - rtMessage " + codiceEventoLog + " [" + rtMessage.toJSONString() + "]");
		
		// spedizione rtmessage
		logger.info("OpenToscana - send [" + this.method.getValore() + "] url [" + rtMessengerEndpoint + getPathInfo() + "]");
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Authorization", "Bearer " + TokenRetriever.getToken());
		Client client = new Client();
    	byte[] response = client.send(rtMessengerEndpoint + getPathInfo(), this.method, Client.Content.JSON, header, rtMessage.toJSONString().getBytes("UTF-8"));
		
    	// interpretazione response
    	if (EnumTipiEventi.AVVISATURA_PUSH_INSERT.getEventoCode().equals(codiceEvento) || EnumTipiEventi.AVVISO_DI_PAGAMENTO_ESEGUITO.getEventoCode().equals(codiceEvento)) {
    		// la response corretta e' un json che contenga la chiave messageId con un valore numerico
    		String responseString = null;
    		try {
    			responseString = new String(response, "UTF-8");
	    		JSONObject jsonObject = new JSONObject(responseString);
				jsonObject.getNumber("messageId"); // Throws JSONException if the key is not found or if the value is not a Number object and cannot be converted to a number.
			} catch (Exception e) {
				throw new RuntimeException("Errore response OT [" + responseString + "]", e);
			}
    	} else if (EnumTipiEventi.AVVISATURA_PUSH_UPDATE.getEventoCode().equals(codiceEvento) || EnumTipiEventi.AVVISATURA_PUSH_DELETE.getEventoCode().equals(codiceEvento)) {
    		// la response corretta e' stringa vuota
			String responseString = null;
			try {
				responseString = new String(response, "UTF-8");
			} catch (Exception e) {
				throw new RuntimeException("Errore response OT [" + responseString + "]", e);
			}
			if (!responseString.trim().isEmpty()) {
				throw new RuntimeException("Errore response OT [" + responseString + "]");
			}
    	}
    	
    	String msg = this.messageId == null ? "" : "rif. messageId: " + this.messageId;
    	if (response != null && response.length > 0) {
    		msg = new String(response, "UTF-8");
    	} 
    	
    	logger.info("OpenToscana - response [" + msg + "]");
    	
		return msg;
	}

	protected String getMessageId(String datiEvento, List tipiEventi) {
		
		String messageId = null;
		Query q = em.createNamedQuery("EventiByCodiciEventoAndDatiEventoAndStato", Eventi.class);
		q.setParameter("codiciEvento", tipiEventi);
		q.setParameter("datiEvento", datiEvento);
		q.setParameter("stato", EnumStatoEventi.NOTIFICATO.getChiave());
		List<Eventi> listaEventi = (List<Eventi>) q.getResultList();
		if (!listaEventi.isEmpty()) {
			String descrStato = listaEventi.get(0).getDescrStato();
			try {
				JSONObject jsonObject = new JSONObject(descrStato);
				messageId = String.valueOf(jsonObject.getNumber("messageId"));
			} catch (Exception e) {
				// do nothing
			}
		}
		return messageId;

	}

	private String getPathInfo() {
		return this.messageId == null ? "" : "/" + this.messageId;
	}

}
