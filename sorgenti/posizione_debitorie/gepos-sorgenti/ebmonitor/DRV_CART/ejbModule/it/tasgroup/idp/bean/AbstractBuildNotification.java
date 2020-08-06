package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder.EnumVersioniNotificaRFC145;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.StatoEnum;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractBuildNotification implements IBuildNotification {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void build(List<NotifichePagamenti> listaNotifichePagamenti, String statoNotificaPagamento,DataStorageInterface dataTx, EntityManager em) throws Exception {
		

		//BIZ LOGIC
		Iterator<NotifichePagamenti> iterListaNotifichePagamenti = listaNotifichePagamenti.iterator();
		String idEnte = "first"; String cdTrbEnte = "first";
		String cdEnte = "first"; String silEnte = "first";
		String trtIdEnte = "first"; String trtSilEnte = "first";
		String tipoSpontaneo = "first"; String tipoNotifica = "first";
		//creo una lista contenente i pagamenti raggruppati
		List<PagamentoModel> listaPagamentiModel = new ArrayList<PagamentoModel>();
		//********** RECUPERO IL MASSIMO NUMERO ELEMENTI CONTENUTI NEL FLUSSO IN BASE ALLO STATO NOTIFICA
		int MAX_NUM_ELEM_IN_FLOW = 1000;
		try {
			MAX_NUM_ELEM_IN_FLOW=Integer.parseInt(IrisProperties.getProperty("maxnum.notification." + statoNotificaPagamento));
		} catch (Throwable t){
			logger.warn(" errore nel recupero di maxnum.notification." + statoNotificaPagamento );			
		}		
		//**********
        int numOfElement = 0;
		while (iterListaNotifichePagamenti.hasNext()) {
			NotifichePagamenti notificaPagamento = (NotifichePagamenti) iterListaNotifichePagamenti.next();
			//creo il model
			NotifichePagamentoModel notificaPagamentoModel = new NotifichePagamentoModel();

			//Creo un pagamento model
			PagamentoModel pagamentoModel = creoPagamentoModel(notificaPagamento, statoNotificaPagamento, em);
			//set schema version					
			
			//salvo il tipo notifica per gestire l'ultimo ciclo
			tipoNotifica = notificaPagamento.getStatoPagamento();

			//devo decidere se aggiungere alla lista attuale oppure ad una nuova
			if ((idEnte.equals(notificaPagamento.getIdEnte()) && 
				 cdTrbEnte.equals(notificaPagamento.getCdTrbEnte()) && 
				 silEnte.equals(notificaPagamento.getIdSystem())	&& 
				 tipoSpontaneo.equals(notificaPagamento.getTipospontaneo())
				 && numOfElement < MAX_NUM_ELEM_IN_FLOW)
					|| (idEnte.equals("first"))
				) {
				//aggiungo alla lista corrente perchè il pagModel attuale è dello stesso ente/tributo/sil(?)
				listaPagamentiModel.add(pagamentoModel);
				//memorizzo il codice attuale
				idEnte = notificaPagamento.getIdEnte();
				cdTrbEnte = notificaPagamento.getCdTrbEnte();
				cdEnte = notificaPagamento.getCdEnte();
				silEnte = notificaPagamento.getIdSystem();
				trtIdEnte = notificaPagamento.getTrtReceiverId();
				trtSilEnte = notificaPagamento.getTrtReceiverSys();
				tipoSpontaneo = notificaPagamento.getTipospontaneo();
				numOfElement++;
				logger.info("PAG MODEL NELLA LISTA = " + listaPagamentiModel.size() + " ENTE " + idEnte + " TRB " + cdTrbEnte + " SIL " + silEnte + " TIPOSPONTANEO " + tipoSpontaneo);
			} else {
				//aggiungo al notificaModel principale e creo il messaggio Xml
				notificaPagamentoModel.setListaPagamenti(listaPagamentiModel);
				logger.info("=============================================================================");
				logger.info("CREO MESSAGGIO CON N° PAG MODEL = " + listaPagamentiModel.size() + " ENTE " + idEnte + " TRB " + cdTrbEnte + " SIL " + silEnte + " TIPOSPONTANEO " + tipoSpontaneo);

				//ad ogni salto codice creo un nuovo id per la memorizzazione del messaggio XML
				String id = GeneratoreIdUnivoci.GetCurrent().generaId(null);
				logger.info("NOTIFICHE CART NEXT ID = " + id);
				//generare con sequence
				setHeaderData(cdEnte, silEnte, notificaPagamentoModel, id, trtIdEnte, trtSilEnte);

				String notifica = buildXmlNotifica(tipoSpontaneo, notificaPagamentoModel, em);

				//idem anche il salvataggio va fatto ad ogni salto codice !!!
				//salvo in esiti cart per spedire
				//salvo l'esito da spedire (StatoEnum.DA_SPEDIRE) in tabella
				NotificheCart notificheCart = new NotificheCart();
				fillNotificheCart(cdEnte,
						silEnte, notificaPagamentoModel, notifica, tipoNotifica, notificheCart, trtIdEnte, trtSilEnte,
						notificaPagamento.getIdEnte(), notificaPagamento.getCdTrbEnte());
						//aIdEnte, aCdTrbEnte);

				//creo notifiche cart e aggiorno lo stato delle notifiche appena create in una unica transazione 
				// la chiamata è in requires_new ed effettua insert ed update
				try {
				   dataTx.persistNotificheCartAndUpdateStatoNotPagamenti(notificheCart, listaPagamentiModel, notificaPagamentoModel);
				} catch (Throwable t) {
					logger.error("AbstractBuildNotification persistNotificheCartAndUpdateStatoNotPagamenti() rollback...");
					logger.info("AbstractBuildNotification go ahead...");
				}
				//inoltre creo una nuova lista e ci memorizzo l'elemento corrente
				listaPagamentiModel = new ArrayList<PagamentoModel>();
				listaPagamentiModel.add(pagamentoModel);
				//inoltre creo anche un nuovo not pag model
				notificaPagamentoModel = new NotifichePagamentoModel();
				//devo rimemorizzare anche il NUOVO codice attuale
				idEnte = notificaPagamento.getIdEnte();
				cdTrbEnte = notificaPagamento.getCdTrbEnte();
				cdEnte = notificaPagamento.getCdEnte();
				silEnte = notificaPagamento.getIdSystem();
				tipoSpontaneo = notificaPagamento.getTipospontaneo();
				numOfElement = 1;
			}

		}

		logger.info("=============================================================================");
		logger.info("CREO MESSAGGIO CON N° PAG MODEL = " + listaPagamentiModel.size() + " ENTE " + idEnte + " TRB " + cdTrbEnte + " SIL " + silEnte + " TIPOSPONTANEO " + tipoSpontaneo);

		//ripetere per l'ultimo elemento
		//sistemare questo porcaio di query
		String id = GeneratoreIdUnivoci.GetCurrent().generaId(null);
		logger.debug("NOTIFICHE CART NEXT ID = " + id);


		NotifichePagamentoModel model = new NotifichePagamentoModel();
		setHeaderData(cdEnte, silEnte, model, id, trtIdEnte, trtSilEnte);
		//aggiungo la lista che era rimasta in sospeso
		model.setListaPagamenti(listaPagamentiModel);

		String notifica = buildXmlNotifica(tipoSpontaneo, model, em);

		//idem anche il salvataggio va fatto ad ogni salto codice !!!
		//salvo in esiti cart per spedire
		//salvo l'esito da spedire (StatoEnum.DA_SPEDIRE) in tabella
		NotificheCart notificheCart = new NotificheCart();
		fillNotificheCart(cdEnte, silEnte,
				model, notifica, tipoNotifica, notificheCart, trtIdEnte, trtSilEnte,
				idEnte,cdTrbEnte);
				//aIdEnte, aCdTrbEnte);
		
		
		//creo notifiche cart e aggiorno lo stato delle notifiche appena create in una unica transazione 
		try {
		   dataTx.persistNotificheCartAndUpdateStatoNotPagamenti(notificheCart, listaPagamentiModel, model);
		} catch (Throwable t) {
			logger.error("AbstractBuildNotification persistNotificheCartAndUpdateStatoNotPagamenti() rollback...");
			logger.info("AbstractBuildNotification go ahead...");
		}	

		logger.info(" creazione/spedizione notifica pagamento terminata " );
	
	}
    
	/**
	 *
	 * @param cdEnte
	 * @param silEnte
	 * @param notificaPagamentoModel
	 * @param id
	 * @param trtSilEnte 
	 * @param trtIdEnte 
	 */
	private void setHeaderData(String cdEnte, String silEnte,
			NotifichePagamentoModel notificaPagamentoModel, String id, String trtIdEnte, String trtSilEnte) {
		notificaPagamentoModel.setE2emsgid(id);
		notificaPagamentoModel.setSenderId("RTIRIS");
		notificaPagamentoModel.setSenderSys("SIL_IRIS_ITR");
		notificaPagamentoModel.setReceiverId(cdEnte);
		notificaPagamentoModel.setReceiverSys(silEnte);
		notificaPagamentoModel.setTrtReceiverId(trtIdEnte);
		notificaPagamentoModel.setTrtReceiverSys(trtSilEnte);
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
		String timeIso = "+01:00";
		if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
			//ora legale attiva
			timeIso = "+02:00";
		} 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
		String tmsp = format.format(new Date(System.currentTimeMillis()));
		notificaPagamentoModel.setDataOraCreazioneEsito(tmsp);
	}
	
	
	/**
	 *
	 * @param cdEnte
	 * @param silEnte
	 * @param model
	 * @param notifica
	 * @param notificheCart
	 * @param trtSilEnte 
	 * @param trtIdEnte 
	 * @return
	 */
	private NotificheCartPK fillNotificheCart(String cdEnte, String silEnte,
			NotifichePagamentoModel model, String notifica, String tipoNotifica,
			NotificheCart notificheCart, String trtIdEnte, String trtSilEnte,
			String idEnte, String cdTrbEnte) {
		notificheCart.setNotificaXml(notifica.getBytes(Charset.forName("US-ASCII")));
		NotificheCartPK notCartPK = new NotificheCartPK();
		notCartPK.setE2emsgid(model.getE2emsgid());
		notCartPK.setReceiverid(cdEnte);
		notCartPK.setReceiversys(silEnte);
		notificheCart.setId(notCartPK);
		notificheCart.setSenderid("RTIRIS");
		notificheCart.setSendersys("SIL_IRIS_ITR");
		notificheCart.setTipoNotifica(tipoNotifica);

		notificheCart.setStato(StatoEnum.DA_SPEDIRE);
		notificheCart.setOpInserimento("NOTIFIC_PAGAMENTO-TIMER");
		notificheCart.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
		notificheCart.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		
		notificheCart.setTrtReceiverId(trtIdEnte);
		notificheCart.setTrtReceiverSys(trtSilEnte);
		
		notificheCart.setIdEnte(idEnte);
		notificheCart.setCdTrbEnte(cdTrbEnte);
		return notCartPK;
	}

	
	/**
	 * Da definire a cura degli implementor 
	 * @param notPagamento
	 * @return
	 */
	protected abstract PagamentoModel creoPagamentoModel(NotifichePagamenti notPagamento, String statoNotificaPagamento, EntityManager em);

	/**
	 * Da definire a cura degli implementor 
	 * @param notPagamento
	 * @return
	 */	
	protected abstract String buildXmlNotifica(String tipoSpontaneo, NotifichePagamentoModel model, EntityManager em) throws Exception;


	public String fixupWithEscapes(String s) {
		StringBuffer sb = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			switch (c) {
			
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
}
