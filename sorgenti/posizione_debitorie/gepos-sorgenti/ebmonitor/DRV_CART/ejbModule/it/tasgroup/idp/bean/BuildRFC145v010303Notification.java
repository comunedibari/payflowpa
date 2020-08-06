package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder.EnumVersioniNotificaRFC145;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModelTyped;
import it.tasgroup.idp.notifiche.PagamentoModelTypedBuilder;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.iris2.pagamenti.Pagamenti;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.management.RuntimeErrorException;
import javax.persistence.EntityManager;

public class BuildRFC145v010303Notification extends AbstractBuildRFC145Notification {

	@Override
	protected PagamentoModel creoPagamentoModel(NotifichePagamenti notPagamento, String statoNotificaPagamento, EntityManager em) {

		Pagamenti pagamento = em.find(Pagamenti.class, Long.parseLong(notPagamento.getIdPagamento()));
		if (pagamento==null) {
			throw new RuntimeErrorException(null,"Pagamento da notificare non trovato. Id_pagamento="+notPagamento.getIdPagamento());
		}
		PagamentoModelTypedBuilder pmtBuilder = new PagamentoModelTypedBuilder();
		PagamentoModelTyped pmt = pmtBuilder.buildPagamentoModel(pagamento, em, notPagamento.getIdNotifica(),notPagamento.getStatoPagamento());
		pmt.setEsito(statoNotificaPagamento);
		
		
		PagamentoModel pagModel = new PagamentoModel();
	
		//chiave, mi serve per update dello stato su notPagamenti
		pagModel.setIdNotifica(pmt.getIdNotifica());
		pagModel.setIdPagamento(pmt.getIdPagamento());
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
		String timeIso = "+01:00";
		if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
			//ora legale attiva
			timeIso = "+02:00";
		} 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
		pagModel.setDataOraPagamento(format.format(pmt.getDataOraPagamento()));
		pagModel.setDataScadenzaPagamento(format.format(pmt.getDataScadenzaPagamento()));
		pagModel.setImporto(pmt.getImporto().toString());
		pagModel.setEsito(pmt.getEsito());
		pagModel.setIdPagante(pmt.getIdPagante());
		pagModel.setTipoCanalePagamento(pmt.getTipoCanalePagamento());
		pagModel.setMezzoPagamento(pmt.getMezzoPagamento());
		pagModel.setIdTransazione(pmt.getIdTransazione());
		// versione corrente riga notifiche_pagamenti
		pagModel.setVersionNotifichePagamento(notPagamento.getVersion());
//		//#1394
//		//la data viene valorizzata diversamente in funzione dello 
//		//stato della notifica. 	
//		if (StatoEnum.NOTIFICHE_ESEGUITO.equalsIgnoreCase(statoNotificaPagamento)) {
//			pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));	
//		} else if (StatoEnum.NOTIFICHE_REGOLATO.equalsIgnoreCase(statoNotificaPagamento)) {
//			if (notPagamento.getDataAccreditoContoTecnico()==null) {
//				pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));					
//			} else {
//				pagModel.setDataOraTransazione(format.format(notPagamento.getDataAccreditoContoTecnico()));
//			}
//		} if (StatoEnum.NOTIFICHE_INCASSO.equalsIgnoreCase(statoNotificaPagamento)) {	
//			if (notPagamento.getDataAccreditoEnte()==null) {
//				pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));
//			} else {
//				pagModel.setDataOraTransazione(format.format(notPagamento.getDataAccreditoEnte()));
//			}						
//		} else {
//			pagModel.setDataOraTransazione(format.format(notPagamento.getTmbspedizione()));
//		}

		pagModel.setDataOraTransazione(format.format(pmt.getDataOraTransazione()));
		//#2653
		pagModel.setCodiceAutorizzazione(pmt.getCodiceAutorizzazione());
		
		pagModel.setDataOraAutorizzazione(format.format(pmt.getDataOraAutorizzazione()));
		pagModel.setImportoTransato(pmt.getImportoTransato().toString());
		if (!StatoEnum.NOTIFICHE_INCASSO.equalsIgnoreCase(statoNotificaPagamento)) {
		  pagModel.setImportoVoce(pmt.getImportoVoce().toString());
		} else {
			pagModel.setImportoVoce(null); // serve per eliminare il dettaglio pagamento nella notifica
		}
		pagModel.setImportoVoceCommissioni(pmt.getImportoVoceCommissioni().toString());
	
		pagModel.setTipoDebito(pmt.getTipoDebito());
		pagModel.setIdPendenza(pmt.getIdPendenza());
		pagModel.setIdPendenzaEnte(pmt.getIdPendenzaEnte());
		pagModel.setDeCausale(fixupWithEscapes(pmt.getDeCausale()));
		pagModel.setFlagCausale(true );
		
		pagModel.setIdPagante(pmt.getIdPagante());
		pagModel.setIdVersante(pmt.getIdVersante());
		pagModel.setDescrizioneVersante(pmt.getDescrizioneVersante());
		 // inserimento dati rendicontazione in caso di notifica ESEGUITO
		pagModel.setCodiceAutorizzazione(pmt.getCodiceAutorizzazione());
		pagModel.setDescrizioneCanalePagamento(pmt.getDescrizioneCanalePagamento());
		pagModel.setIuv(pmt.getIuv());
		return pagModel;
	}

	@Override
	protected EnumVersioniNotificaRFC145 getVersione() {
		return EnumVersioniNotificaRFC145.v010303;
	}
	

}
