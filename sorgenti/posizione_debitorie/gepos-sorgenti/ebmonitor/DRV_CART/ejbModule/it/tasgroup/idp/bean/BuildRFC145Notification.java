package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.StatoEnum;

import java.math.BigDecimal;
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

public class BuildRFC145Notification implements IBuildNotification {

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	
	@Override
	public void build(List<NotifichePagamenti> listaNotifichePagamenti, String statoNotificaPagamento,DataStorageInterface dataTx, EntityManager em) throws Exception {
		
		logger.info(" creazione/spedizione notifiche terminata, "
				+ " N. PAGAMENTI = " + listaNotifichePagamenti.size() 
				+ " , STATONOTIFICA = " + statoNotificaPagamento);
		//BIZ LOGIC
		Iterator<NotifichePagamenti> iterLista = listaNotifichePagamenti.iterator();
		String idEnte = "first"; String cdTrbEnte = "first";
		String cdEnte = "first"; String silEnte = "first";
		String trtIdEnte = "first"; String trtSilEnte = "first";
		String tipoSpontaneo = "first"; String tipoNotifica = "first";
		//creo una lista contenente i pagamenti raggruppati
		List<PagamentoModel> listaPagamentiModel = new ArrayList<PagamentoModel>();

		while (iterLista.hasNext()) {
			NotifichePagamenti notPagamento = (NotifichePagamenti) iterLista.next();
			//creo il model
			NotifichePagamentoModel model = new NotifichePagamentoModel();

			//Creo un pagamento model
			PagamentoModel pagModel = creoPagamentoModel(notPagamento,statoNotificaPagamento);
			//set schema version					
			
			//salvo il tipo notifica per gestire l'ultimo ciclo
			tipoNotifica = notPagamento.getStatoPagamento();

			//devo decidere se aggiungere alla lista attuale oppure ad una nuova
			if ((idEnte.equals(notPagamento.getIdEnte()) && 
				 cdTrbEnte.equals(notPagamento.getCdTrbEnte()) && 
				 silEnte.equals(notPagamento.getIdSystem())	&& 
				 tipoSpontaneo.equals(notPagamento.getTipospontaneo()))
					|| (idEnte.equals("first"))
				) {
				//aggiungo alla lista corrente perchè il pagModel attuale è dello stesso ente/tributo/sil(?)
				listaPagamentiModel.add(pagModel);
				//memorizzo il codice attuale
				idEnte = notPagamento.getIdEnte();
				cdTrbEnte = notPagamento.getCdTrbEnte();
				cdEnte = notPagamento.getCdEnte();
				silEnte = notPagamento.getIdSystem();
				trtIdEnte = notPagamento.getTrtReceiverId();
				trtSilEnte = notPagamento.getTrtReceiverSys();
				tipoSpontaneo = notPagamento.getTipospontaneo();
				logger.info("PAG MODEL NELLA LISTA = " + listaPagamentiModel.size() + " ENTE " + idEnte + "TRB " + cdTrbEnte + "SIL " + silEnte + " TIPOSPONTANEO" + tipoSpontaneo);
			} else {
				//aggiungo al notificaModel principale e creo il messaggio Xml
				model.setListaPagamenti(listaPagamentiModel);
				logger.info("=============================================================================");
				logger.info("CREO MESSAGGIO CON N° PAG MODEL = " + listaPagamentiModel.size() + " ENTE " + idEnte + "TRB " + cdTrbEnte + "SIL " + silEnte + " TIPOSPONTANEO" + tipoSpontaneo);

				//ad ogni salto codice creo un nuovo id per la memorizzazione del messaggio XML
				String id = GeneratoreIdUnivoci.GetCurrent().generaId(null);
				logger.debug("NOTIFICHE CART NEXT ID = " + id);
				//generare con sequence
				setHeaderData(cdEnte, silEnte, model, id, trtIdEnte, trtSilEnte);

				//la creazione del messaggio xml va fatta ad ogni salto codice !!!
				//creo il messaggio di notifica
				//questo va creato a 'saldo di codice' ID_ENTE (sicuro) e CD_TRB_ENTE (?)
				NotifichePagamentoBuilder build = new NotifichePagamentoBuilder();
				// TODO INSERIRE QUI LA FACTORY CHE RECUPERA
				String notifica = buildXmlNotifica(tipoSpontaneo,model, build);

				//idem anche il salvataggio va fatto ad ogni salto codice !!!
				//salvo in esiti cart per spedire
				//salvo l'esito da spedire (StatoEnum.DA_SPEDIRE) in tabella
				NotificheCart notificheCart = new NotificheCart();
				fillNotificheCart(cdEnte,
						silEnte, model, notifica, tipoNotifica, notificheCart, trtIdEnte, trtSilEnte,
						notPagamento.getIdEnte(), notPagamento.getCdTrbEnte());
						//aIdEnte, aCdTrbEnte);

				// la chiamata è in requires_new ed effettua insert ed update 				
				try {
				  dataTx.persistNotificheCartAndUpdateStatoNotPagamenti(notificheCart,listaPagamentiModel, model); 
				} catch (Throwable t) {
					logger.error("BuildRFC145Notification persistNotificheCartAndUpdateStatoNotPagamenti() rollback...");
					logger.info("BuildRFC145Notification go ahead...");
				}
				//inoltre creo una nuova lista e ci memorizzo l'elemento corrente
				listaPagamentiModel = new ArrayList<PagamentoModel>();
				listaPagamentiModel.add(pagModel);
				//inoltre creo anche un nuovo not pag model
				model = new NotifichePagamentoModel();
				//devo rimemorizzare anche il NUOVO codice attuale
				idEnte = notPagamento.getIdEnte();
				cdTrbEnte = notPagamento.getCdTrbEnte();
				cdEnte = notPagamento.getCdEnte();
				silEnte = notPagamento.getIdSystem();
				tipoSpontaneo = notPagamento.getTipospontaneo();
			}
		}

		logger.info("=============================================================================");
		logger.info("CREO MESSAGGIO CON N° PAG MODEL = " + listaPagamentiModel.size() + " ENTE " + idEnte + "TRB" + cdTrbEnte + "SIL " + silEnte + " TIPOSPONTANEO" + tipoSpontaneo);

		//ripetere per l'ultimo elemento
		//sistemare questo porcaio di query
		String id = GeneratoreIdUnivoci.GetCurrent().generaId(null);
		logger.debug("NOTIFICHE CART NEXT ID = " + id);

		NotifichePagamentoModel model = new NotifichePagamentoModel();
		setHeaderData(cdEnte, silEnte, model, id, trtIdEnte, trtSilEnte);
		//aggiungo la lista che era rimasta in sospeso
		model.setListaPagamenti(listaPagamentiModel);

		//la creazione del messaggio xml va fatta ad ogni salto codice !!!
		//creo il messaggio di notifica
		//questo va creato a 'saldo di codice' ID_ENTE (sicuro) e CD_TRB_ENTE (?)
		NotifichePagamentoBuilder build = new NotifichePagamentoBuilder();
		
		String notifica = buildXmlNotifica(tipoSpontaneo, model, build);

		//idem anche il salvataggio va fatto ad ogni salto codice !!!
		//salvo in esiti cart per spedire
		//salvo l'esito da spedire (StatoEnum.DA_SPEDIRE) in tabella
		NotificheCart notificheCart = new NotificheCart();
		fillNotificheCart(cdEnte, silEnte,
				model, notifica, tipoNotifica, notificheCart, trtIdEnte, trtSilEnte,
				idEnte,cdTrbEnte);
				//aIdEnte, aCdTrbEnte);
		
		// la chiamata è in requires_new ed effettua insert ed update 		
		try {
		   dataTx.persistNotificheCartAndUpdateStatoNotPagamenti(notificheCart,listaPagamentiModel, model); 
	    } catch (Throwable t) {
		   logger.error("BuildRFC145Notification persistNotificheCartAndUpdateStatoNotPagamenti() rollback...");
		   logger.info("BuildRFC145Notification go ahead...");
	    }

		logger.info(" creazione/spedizione notifiche pagamento terminata " );	
	
	}
    
	/**
	 *
	 * @param cdEnte
	 * @param silEnte
	 * @param model
	 * @param id
	 * @param trtSilEnte 
	 * @param trtIdEnte 
	 */
	private void setHeaderData(String cdEnte, String silEnte,
			NotifichePagamentoModel model, String id, String trtIdEnte, String trtSilEnte) {
		model.setE2emsgid(id);
		model.setSenderId("RTIRIS");
		model.setSenderSys("SIL_IRIS_ITR");
		model.setReceiverId(cdEnte);
		model.setReceiverSys(silEnte);
		model.setTrtReceiverId(trtIdEnte);
		model.setTrtReceiverSys(trtSilEnte);
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
		String timeIso = "+01:00";
		if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
			//ora legale attiva
			timeIso = "+02:00";
		} 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
		String tmsp = format.format(new Date(System.currentTimeMillis()));
		model.setDataOraCreazioneEsito(tmsp);
	}

	/**
	 *
	 * @param tipoSpontaneo
	 * @param model
	 * @param build
	 * @return
	 * @throws Exception
	 */
	private String buildXmlNotifica(String tipoSpontaneo,
			NotifichePagamentoModel model, NotifichePagamentoBuilder build)
			throws Exception {
		String notifica = "";
		//da sistemare, gestisco i vari casi ,
		//pagamento pendenze o pagamento spontaneo
		if (tipoSpontaneo.equals("PEND")) {
			notifica = build.creaEsitoPagamentoPendenze(model);
		} 
//		else if (tipoSpontaneo.equals("Multa")) {
//			notifica = build.creaEsitoPagamentoSpontaneo(model);
//		}
		logger.debug(" xml notifica = " + notifica );
		return notifica;
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
	 *
	 * @param notPagamento
	 * @return
	 */
	private PagamentoModel creoPagamentoModel(NotifichePagamenti notPagamento, String statoNotificaPagamento) {
		PagamentoModel pagModel = new PagamentoModel();

		//chiave, mi serve per update dello stato su notPagamenti
		pagModel.setIdNotifica(notPagamento.getIdNotifica());
		//nel flusso di notifica diretto verso l'ente inserisco
		//l'id della condizione di pagamento
		pagModel.setIdPagamento(notPagamento.getIdPagamentoEnte());
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
		String timeIso = "+01:00";
		if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
			//ora legale attiva
			timeIso = "+02:00";
		} 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
		pagModel.setDataOraPagamento(format.format(notPagamento.getTsDecorrenza()));
		pagModel.setDataScadenzaPagamento(format.format(notPagamento.getDtScadenza()));
		pagModel.setImporto(notPagamento.getImTotale().toString());
		pagModel.setEsito(notPagamento.getStatoPagamento());
		pagModel.setIdPagante(notPagamento.getCoPagante());
		pagModel.setTipoCanalePagamento("IRIS");
		pagModel.setMezzoPagamento(notPagamento.getTiposervizio());
		pagModel.setIdTransazione(notPagamento.getMsgid());
		// versione corrente riga notifiche_pagamenti
		pagModel.setVersionNotifichePagamento(notPagamento.getVersion());
		
		//#1394
		//la data viene valorizzata diversamente in funzione dello 
		//stato della notifica		
		if (StatoEnum.NOTIFICHE_ESEGUITO.equalsIgnoreCase(statoNotificaPagamento)) {
			pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));	
		} else if (StatoEnum.NOTIFICHE_REGOLATO.equalsIgnoreCase(statoNotificaPagamento)) {
			
			if (notPagamento.getDataAccreditoContoTecnico()==null) {
				pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));					
			} else {
				pagModel.setDataOraTransazione(format.format(notPagamento.getDataAccreditoContoTecnico()));
			}
						
		} if (StatoEnum.NOTIFICHE_INCASSO.equalsIgnoreCase(statoNotificaPagamento)) {
			
			if (notPagamento.getDataAccreditoEnte()==null) {
				pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));
			} else {
				pagModel.setDataOraTransazione(format.format(notPagamento.getDataAccreditoEnte()));
			}						
			
		} else {
			pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));
		}


		//dal 09/11/11 questa non ha più funzionato, rev 5137
		String tsOperazione = notPagamento.getTsOrdine()!=null ? format.format(notPagamento.getTsOrdine()) : null;
		if (tsOperazione!=null) {
			pagModel.setDataOraAutorizzazione(tsOperazione);
		}
		pagModel.setImportoTransato(notPagamento.getTotimportipositivi()+"");
		//tag dettaglio importo transato (non gestisce le commissioni)
		pagModel.setImportoVoce(notPagamento.getTotimportipositivi()+"");

		BigDecimal commissioni = notPagamento.getTotimportipositivi().subtract(notPagamento.getImTotale());
		commissioni.setScale(2);
		pagModel.setImportoVoceCommissioni(commissioni.toPlainString());

		pagModel.setTipoDebito(notPagamento.getTiDebito());
		pagModel.setIdPendenza(notPagamento.getIdPendenza());
		String idPendEnte = notPagamento.getIdPendenzaente()!=null ? notPagamento.getIdPendenzaente().trim() : " ";
		pagModel.setIdPendenzaEnte(idPendEnte);
		
		String deCausale = notPagamento.getDeCausale()!=null ? notPagamento.getDeCausale().trim() : " ";
		pagModel.setDeCausale(fixupXmlWithEscape(deCausale));
		
		boolean flagCausale = true;
		if (notPagamento.getDeCausale().equals("NO-CAUSALE")) {
			flagCausale = false;
		}
		pagModel.setFlagCausale(flagCausale );

		return pagModel;
	}
	public String fixupXmlWithEscape(String s) {
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
