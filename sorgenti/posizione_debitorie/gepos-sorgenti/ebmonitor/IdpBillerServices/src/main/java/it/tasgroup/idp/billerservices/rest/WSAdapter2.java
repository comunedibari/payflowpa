package it.tasgroup.idp.billerservices.rest;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.rest.Commons.DataValidationException;
import it.tasgroup.idp.billerservices.rest.Commons.EnteSil;
import it.tasgroup.idp.billerservices.rest.Commons.TrasmissioneNotFoundException;
import it.tasgroup.idp.billerservices.rest.result.GetStatoResult;
import it.tasgroup.idp.billerservices.rest.result.GetStatoResultList;
import it.tasgroup.idp.billerservices.rest.result.InformativaPagamentiResult;
import it.tasgroup.idp.billerservices.rest.result.InformativaPagamentiResultList;
import it.tasgroup.idp.billerservices.rest.result.SendResult;
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


public class WSAdapter2 {

	
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

	public static SendResult build(AllineamentoPendenzeResponse response) throws Exception {
		if (StatoEsitoType.ELABORATO_OK == response.getResult()) {
			SendResult ret = new SendResult();
			ret.setIdTrasmissione(response.getMsgId());
			return ret;
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
	
	public static GetStatoResultList  build(String idTrasmissione, GetStatoResponse response) throws Exception {
		/*
		 *  non viene mai settato il fault quindi controlliamo l'esito 
		 *  ma non ne sappiamo distinguere la vera causa, tra problema applicativo o di sistema
		 */
		
		if (response.getStatoEsito() == StatoEsitoType.SCONOSCIUTO) {
			throw new Exception();
		}	
		
		GetStatoResultList retList = new GetStatoResultList();
		
		GetStatoResult ret= new GetStatoResult();
		
		long idTrasmissioneLong = 0;
		try {
			idTrasmissioneLong = Long.parseLong(idTrasmissione); // idTrasmisisone e' proprio dello SmartProxy
		} catch (NumberFormatException nfe) { /* se va in errore e' solo per dati vecchi pre generatore di id likeLong */ }
		ret.setIdTrasmissione(idTrasmissioneLong);
		ret.setStato(response.getStatoEsito().value());
		
		retList.getStatoTrasmissioni().add(ret);
		
		return retList;
	}
	
	public static ListaTrasmissioniRequest buildListaTrasmissioniRequest(List<EnteSil> listaEnteSil) {
		ListaTrasmissioniRequest ret = new ListaTrasmissioniRequest();
		ret.setSenderId(listaEnteSil.get(0).getEnte());
		ret.setSenderSys(getConcatenazioneSil(listaEnteSil));
		return ret;
	}
	
	// TODO il seguente metodo torna una lista non paginata, come da implementazione SmartProxy
	// si potrebbe pensare quantomeno di limitare il numero delle "trasmissioni" tornate
	public static GetStatoResultList build(ListaTrasmissioniResponse response) throws Exception {
		/* 
		 *  non viene mai settato il fault quindi controlliamo l'esito 
		 *  ma non ne sappiamo distinguere la vera causa, tra problema applicativo o di sistema
		 */
		if (response.getResult() == StatoEsitoType.ELABORATO_KO) {
			throw new Exception();
		}
		GetStatoResultList retList = new  GetStatoResultList();
		
		for (TrasmissioneType trasmissione : response.getTrasmissioni()) {
			GetStatoResult ret= new GetStatoResult();
			long idTrasmissione = 0;
			try {
				idTrasmissione = Long.parseLong(trasmissione.getMsgId()); // idTrasmisisone e' proprio dello SmartProxy
			} catch (NumberFormatException nfe) { /* se va in errore e' solo per dati vecchi pre generatore di id likeLong */ }
			ret.setIdTrasmissione(idTrasmissione);
			ret.setStato(trasmissione.getStatoEsito().value());
			retList.getStatoTrasmissioni().add(ret);
		}	
		return retList;
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
	
	public static InformativaPagamentiResultList build(ListaNotificheResponse response) throws Exception {
		if (response.getResult().equals("KO")) {
			throw new Exception("ListaNotificheResponse.result == KO");
		}
		InformativaPagamentiResultList informativeList = new InformativaPagamentiResultList();			
		for (NotificaType notifica : response.getNotifiche()) {
			// mapping FlussoDTO@SmartProxy vs NotificaType@Loader
			long idTrasmissione = 0;
			try {
				idTrasmissione = Long.parseLong(notifica.getMsgId()); // idTrasmisisone e' proprio dello SmartProxy
			} catch (NumberFormatException nfe) { /* se va in errore e' solo per dati vecchi pre generatore di id likeLong */ }
			String soggetto = notifica.getSenderId();
			String sil = notifica.getSenderSys();
			String tipoNotifica = notifica.getTipoNotifica().value();
			
			Date dataCreazione = notifica.getTimestampCreazione().toGregorianCalendar().getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dataFromatted= format.format(dataCreazione);
			
			
			InformativaPagamentiResult informativa = new InformativaPagamentiResult();
			informativa.setIdTrasmissione(idTrasmissione);
			informativa.setSystemId(soggetto);
			informativa.setSystemSys(sil);
			informativa.setTipoNotifica(tipoNotifica);
			informativa.setDataCreazione(dataFromatted);
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


	private static String getConcatenazioneSil(List<EnteSil> listaEnteSil) {

		StringBuffer concatenazioneSil = new StringBuffer();
		for (EnteSil enteSil : listaEnteSil) {
			concatenazioneSil.append(enteSil.getSil()).append(";");
		}
		return concatenazioneSil.substring(0, concatenazioneSil.length() - 1).toString();
		
	}
		
}

