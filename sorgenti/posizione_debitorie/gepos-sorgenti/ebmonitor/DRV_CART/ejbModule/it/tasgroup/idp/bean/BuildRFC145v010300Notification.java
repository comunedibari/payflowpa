package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.notifiche.NotifichePagamentoBuilder.EnumVersioniNotificaRFC145;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.util.StatoEnum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.persistence.EntityManager;

public class BuildRFC145v010300Notification extends AbstractBuildRFC145Notification {

	@Override
	protected PagamentoModel creoPagamentoModel(NotifichePagamenti notPagamento, String statoNotificaPagamento, EntityManager em) {
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
		
		if (notPagamento.getCdTrbEnte().equals("BOLLO_AUTO") && notPagamento.getIdSystem().equals("SIL_RTOSCANA_ITR")) {
			String note = notPagamento.getDeCausale()!=null ? notPagamento.getDeCausale().trim() : " ";
			pagModel.setNote(fixupWithEscapes(note));
		}
		
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
	
		return pagModel;
	}

	@Override
	protected EnumVersioniNotificaRFC145 getVersione() {
		return EnumVersioniNotificaRFC145.v010300;
	}
	

}
