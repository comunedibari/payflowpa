package it.tasgroup.idp.billerservices.rest;

import java.nio.charset.Charset;
import java.util.List;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.rest.Commons.DataValidationException;
import it.tasgroup.idp.billerservices.rest.Commons.EnteSil;
import it.tasgroup.idp.billerservices.rest.Commons.TrasmissioneNotFoundException;
import it.tasgroup.idp.loader.AllineamentoPendenzeRequest;
import it.tasgroup.idp.loader.AllineamentoPendenzeResponse;
import it.tasgroup.idp.loader.FaultType;
import it.tasgroup.idp.loader.FileType;
import it.tasgroup.idp.loader.GetEsitoRequest;
import it.tasgroup.idp.loader.GetEsitoResponse;
import it.tasgroup.idp.loader.GetStatoRequest;
import it.tasgroup.idp.loader.GetStatoResponse;
import it.tasgroup.idp.loader.ListaTrasmissioniRequest;
import it.tasgroup.idp.loader.ListaTrasmissioniResponse;
import it.tasgroup.idp.loader.ProcessSpecificationType;
import it.tasgroup.idp.loader.StatoEsitoType;
import it.tasgroup.idp.loader.TrasmissioneType;
import it.tasgroup.idp.notification.AckFileRequest;
import it.tasgroup.idp.notification.DownloadReportResultType;
import it.tasgroup.idp.notification.GetFileNotificaRequest;
import it.tasgroup.idp.notification.GetFileNotificaResponse;
import it.tasgroup.idp.notification.ListaNotificheRequest;
import it.tasgroup.idp.notification.ListaNotificheResponse;
import it.tasgroup.idp.notification.NotificaType;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.toscana.rete.cart.servizi.iris.smartproxy.services.rest.xsd.InformativaPagamentiList;

public class WSAdapter {

	private static String SMART_PROXY_LIKE_PLUGIN = "CSV_BASIC_V2";
	
	/*
	 * creazione request e response per AllineamentoPendenze
	 */
	
	public static AllineamentoPendenzeRequest buildAllineamentoPendenzeRequest(String textFile, List<EnteSil> listaEnteSil) throws Exception {
		AllineamentoPendenzeRequest ret = new AllineamentoPendenzeRequest();
		
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		ret.setMsgId(GeneratoreIdUnivoci.longLike()); // per retrocompatibilita' con lo SmartProxy vogliamo e2eMsgId che siano stringhe simil long, quale era l'idTrasmissione
		
		ProcessSpecificationType processSpecificationType = new  ProcessSpecificationType();
		processSpecificationType.setFileType(SMART_PROXY_LIKE_PLUGIN);
		processSpecificationType.setGeneratedIdPagamento(true); // voglio venga usato il msgId che ho settato qui sopra
		processSpecificationType.setSmartReplace(true); // lo smart proxy gia' si comportava cosi'
		ret.setProcessSpecification(processSpecificationType);

		FileType fileType = new FileType();
		fileType.setTextFile(textFile);
		ret.setFile(fileType);
		
		return ret;
	}

	public static String build(AllineamentoPendenzeResponse response) throws Exception {
		if (StatoEsitoType.ELABORATO_OK == response.getResult()) {
			return response.getMsgId();
		} else {
			FaultType faultType = response.getFault();
			String exceptionMsg = faultType.getFaultCode() + " - " + faultType.getFaultDescription() + " - " + faultType.getFaultString();
			Exception e = new DataValidationException(faultType.getFaultDescription());
			if (EnumReturnValues.ERRORE_GENERICO.getKey().equals(faultType.getFaultCode())) {
				e = new Exception(exceptionMsg);
			}
			throw e;
		}
	}
	
	public static GetStatoRequest buildGetStatoRequest(String idTrasmissione, List<EnteSil> listaEnteSil) {
		GetStatoRequest ret = new GetStatoRequest();
		ret.setMsgId(idTrasmissione);
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		return ret;
	}
	
	public static String build(GetStatoResponse response) throws Exception {
		/*
		 *  non viene mai settato il fault quindi controlliamo l'esito 
		 *  ma non ne sappiamo distinguere la vera causa, tra problema applicativo o di sistema
		 */
		if (response.getStatoEsito() == StatoEsitoType.SCONOSCIUTO) {
			throw new Exception();
		}
		return statoEsitoTrasmissione2Stato(response.getStatoEsito());
	}
	
	public static ListaTrasmissioniRequest buildListaTrasmissioniRequest(List<EnteSil> listaEnteSil) {
		ListaTrasmissioniRequest ret = new ListaTrasmissioniRequest();
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		return ret;
	}
	
	// TODO il seguente metodo torna una lista non paginata, come da implementazione SmartProxy
	// si potrebbe pensare quantomeno di limitare il numero delle "trasmissioni" tornate
	public static String build(ListaTrasmissioniResponse response) throws Exception {
		/*
		 *  non viene mai settato il fault quindi controlliamo l'esito 
		 *  ma non ne sappiamo distinguere la vera causa, tra problema applicativo o di sistema
		 */
		if (response.getResult() == StatoEsitoType.ELABORATO_KO) {
			throw new Exception();
		}
		String inizioHtml = "<html><body><h1>Stato delle Pendenze elaborate</h1><table border=\"1\"><tr><td>Id Trasmissione</td><td>Stato</td></tr>";
		StringBuffer html = new StringBuffer(inizioHtml);
		for (TrasmissioneType trasmissione : response.getTrasmissioni()) {
			html.append("<tr>");
			html.append("<td>" + trasmissione.getMsgId() + "</td>");
			html.append("<td>" + statoEsitoTrasmissione2Stato(trasmissione.getStatoEsito()) + ".</td>");
			html.append("</tr>");
		}
		String fineHtml = "</table></body></html>";
		html.append(fineHtml);
		return html.toString();
	}
	
	public static GetEsitoRequest buildGetEsitoRequest(String idTrasmissione, List<EnteSil> listaEnteSil) {
		GetEsitoRequest ret = new GetEsitoRequest();
		ret.setMsgId(idTrasmissione);
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		return ret;
	}

	public static String build(GetEsitoResponse response) throws TrasmissioneNotFoundException {
		/*
		 *  non viene mai settato il fault quindi controlliamo l'esito 
		 *  ma non ne sappiamo distinguere la vera causa, tra problema applicativo o di sistema
		 */
		if (response.getStatoEsito() == StatoEsitoType.SCONOSCIUTO) {
			throw new TrasmissioneNotFoundException();
		}
		return new String(response.getEsito(), Charset.forName("UTF-8"));
	}
	
	/*
	 * creazione request e response per InformativaPagamenti
	 */
	
	public static ListaNotificheRequest buildGetListaNotificheRequest(List<EnteSil> listaEnteSil) {
		ListaNotificheRequest ret = new ListaNotificheRequest();
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		return ret;
	}
	
	public static InformativaPagamentiList build(ListaNotificheResponse response) throws Exception {
		if (response.getResult().equals("KO")) {
			throw new Exception("ListaNotificheResponse.result == KO");
		}
		InformativaPagamentiList informativeList = new InformativaPagamentiList();			
		for (NotificaType notifica : response.getNotifiche()) {
			// mapping FlussoDTO@SmartProxy vs NotificaType@Loader
			long idTrasmissione = 0;
			try {
				idTrasmissione = Long.parseLong(notifica.getMsgId()); // idTrasmisisone e' proprio dello SmartProxy
			} catch (NumberFormatException nfe) { /* se va in errore e' solo per dati vecchi pre generatore di id likeLong */ }
			String msgId = notifica.getMsgId();
			String soggetto = notifica.getSenderId();
			String sil = notifica.getSenderSys();
			boolean isEsitato = true; // sempre a true perche' comunque nella get mandiamo un ack che ci togliera' i record gia' scaricati dalla list
			
			it.toscana.rete.cart.servizi.iris.smartproxy.services.rest.xsd.InformativaPagamenti informativa = new it.toscana.rete.cart.servizi.iris.smartproxy.services.rest.xsd.InformativaPagamenti();
			informativa.setIdTrasmissione(idTrasmissione);
			informativa.setMsgId(msgId);
			informativa.setSystemId(soggetto);
			informativa.setSystemSys(sil);
			informativa.setEsitato(isEsitato);

			informativeList.getInformativaPagamenti().add(informativa);
		}
		return informativeList;
	}
	
	
	public static GetFileNotificaRequest buildGetFileNotificaRequest(String idTrasmissione, List<EnteSil> listaEnteSil) {
		GetFileNotificaRequest ret = new GetFileNotificaRequest();
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		ret.setMsgId(idTrasmissione);
		return ret;
	}
	
	public static String build(GetFileNotificaResponse response) throws Exception {
		if (DownloadReportResultType.DISPONIBILE == response.getResult()) {
			return new String(response.getFile(), Charset.forName("UTF-8"));
		} else {
			it.tasgroup.idp.notification.FaultType faultType = response.getFault();
			String exceptionMsg = faultType.getFaultCode() + " - " + faultType.getFaultDescription() + " - " + faultType.getFaultString();
			if (EnumReturnValues.NOTIFICA_NON_PRESENTE.getKey().equals(faultType.getFaultCode())) {
				throw new TrasmissioneNotFoundException();
			} else if (!EnumReturnValues.ERRORE_GENERICO.getKey().equals(faultType.getFaultCode())) {
				throw new DataValidationException(faultType.getFaultDescription());
			} else {
				throw new Exception(exceptionMsg);
			}
		}
	}
	
	
	public static AckFileRequest buildAckFileRequest(String idTrasmissione, List<EnteSil> listaEnteSil) {
		AckFileRequest ret = new AckFileRequest();
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		ret.setMsgId(idTrasmissione);
		return ret;
	}


	/*
	 * metodi di utilita'
	 */
	
	private static String statoEsitoTrasmissione2Stato(StatoEsitoType statoEsitoTrasmissione) {
		// mappo quanto torna il loader a quanto si aspetta chi usava lo SmartProxy
		String stato = "Disposizione dei pagamenti in flusso in corso.";
		switch (statoEsitoTrasmissione) {
		case DA_ELABORARE:
			stato = "Tracciato in fase di acquisizione";
			break;
		case VALIDATO:
			stato = "Disposizione dei pagamenti in flusso in corso.";
			break;
		case VALIDATO_KO:
			stato = "Trasmissione elaborata da IRIS con esito ElaboratoConErrori.";
			break;
		case ELABORATO_OK:
			stato = "Trasmissione elaborata da IRIS con esito ElaboratoCorrettamente.";
			break;
		case ELABORATO_KO:
			stato = "Trasmissione elaborata da IRIS con esito ElaboratoConErrori.";
			break;
		case ELABORATO_OK_PARZIALE:
			stato = "Trasmissione elaborata da IRIS con esito ElaboratoConErrori.";
			break;
		case SCONOSCIUTO:
			stato = "Disposizione dei pagamenti in flusso in corso.";
			break;
		}
		
		return stato;
	}
	
	private static String getConcatenazioneSil(List<EnteSil> listaEnteSil) {

		StringBuffer concatenazioneSil = new StringBuffer();
		for (EnteSil enteSil : listaEnteSil) {
			concatenazioneSil.append(enteSil.getSil()).append(";");
		}
		return concatenazioneSil.substring(0, concatenazioneSil.length() - 1).toString();
		
	}
	
}

