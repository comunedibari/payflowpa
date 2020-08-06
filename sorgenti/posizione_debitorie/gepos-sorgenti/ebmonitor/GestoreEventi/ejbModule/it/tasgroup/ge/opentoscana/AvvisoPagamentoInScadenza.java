package it.tasgroup.ge.opentoscana;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.enums.EnumTipiEventi;
import it.tasgroup.ge.helpers.GestoreEventiOpenToscanaHelper;
import it.tasgroup.ge.opentoscana.Client.Method;
import it.tasgroup.ge.opentoscana.RTMessage.Mode;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.util.IrisProperties;

public class AvvisoPagamentoInScadenza extends GestoreEventiOpenToscanaHelper {

	public AvvisoPagamentoInScadenza(EntityManager em) {
		super.em = em;
	}

	@Override
	public RTMessage getRTMessage(CommunicationEvent e, CfgEventi cfgEvento) {
		
		String[] datiEvento = e.getObjectId().split("\\|"); // CD_ENTE|ID_PAGAMENTO|CF_DEBITORE
		String cdEnte = datiEvento[0];
		String idPagamento = datiEvento[1];
		String cfDebitore = datiEvento[2];

		Date expirationDelete = null;
		
		String codiceEvento = cfgEvento.getCodiceEvento();
		if (EnumTipiEventi.AVVISATURA_PUSH_INSERT.getEventoCode().equals(codiceEvento)) {
			super.method = Method.POST;
		} else if (EnumTipiEventi.AVVISATURA_PUSH_DELETE.getEventoCode().equals(codiceEvento)) {
			super.method = Method.PATCH;
			super.messageId = super.getMessageId(e.getObjectId(), Arrays.asList(EnumTipiEventi.AVVISATURA_PUSH_INSERT.getEventoCode()));
			if (super.messageId == null) {
				throw new IllegalArgumentException ("Nessun messageId trovato per l'evento 101 (AVVISATURA_PUSH_INSERT) NOTIFICATO con questi DATIEVENTO");
			}
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(new Date());
			gregorianCalendar.add(Calendar.DATE, -1);
			expirationDelete = gregorianCalendar.getTime();
		} else if (EnumTipiEventi.AVVISATURA_PUSH_UPDATE.getEventoCode().equals(codiceEvento)) {
			super.method = Method.PATCH;
			super.messageId = super.getMessageId(e.getObjectId(), Arrays.asList(EnumTipiEventi.AVVISATURA_PUSH_INSERT.getEventoCode()));
			if (super.messageId == null) {
				throw new IllegalArgumentException ("Nessun messageId trovato per l'evento 101 (AVVISATURA_PUSH_INSERT) NOTIFICATO con questi DATIEVENTO");
			}
		}
		
		Query queryEnti = super.em.createNamedQuery("TributiEntiByCdEnte");
		queryEnti.setParameter("cdEnte", cdEnte);
		List<Enti> enti = (List) queryEnti.getResultList(); // ENTI
		
		Query queryCondizioniPagamento = super.em.createNamedQuery("CondizioniPagamentoByIdEnteAndIdPagamentoEnte");
		queryCondizioniPagamento.setParameter("idEnte", enti.get(0).getIdEnte());
		queryCondizioniPagamento.setParameter("idPagamento", idPagamento);
		queryCondizioniPagamento.setParameter("stRiga", Arrays.asList(super.ST_RIGA_VALIDA, super.ST_RIGA_ANNULLATA));
		List<CondizioniPagamento> listaCondizioniPagamento = (List) queryCondizioniPagamento.getResultList(); // JLTCOPD
		
		CondizioniPagamento condizioniPagamento = null;
		// con questo loop dovremmo ottenere il primo ed unico record valido (ST_RIGA == V) per insert e update
		// e l'ultimo record in ordine di inserimento ("order by asc" nella CondizioniPagamentoByIdEnteAndIdPagamentoEnte) per la delete
		for (CondizioniPagamento cp : listaCondizioniPagamento) {
			condizioniPagamento = cp;
			if (super.ST_RIGA_VALIDA.equals(cp.getStRiga())) {
				break;
			}
		}
		
		TributiEntiPK pk = new TributiEntiPK();
		pk.setIdEnte(condizioniPagamento.getIdEnte());
		pk.setCdTrbEnte(condizioniPagamento.getCdTrbEnte());
		TributiEnti tribEnti = super.em.find(TributiEnti.class, pk); // JLTENTR
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String body = IrisProperties.getProperty("rt.messenger.avviso.pagamento.in.scadenza.body");
		String subject = tribEnti.getDeTrb() + ". Termine di pagamento: " + sdf.format(condizioniPagamento.getDtFinevalidita());
		String altBody = body;
		String altSubject = null;
		String template = IrisProperties.getProperty("rt.messenger.avviso.pagamento.in.scadenza.template");
		Mode mode = Mode.BYIDENTITY;
		List<String> recipients = new ArrayList<String>();
		recipients.add("TINIT-" + cfDebitore); // TODO nazione ?
		Date notBefore = null;
		String webviewUrlPagAnonimo = IrisProperties.getProperty("rt.messenger.avviso.pagamento.in.scadenza.webviewurlpaganonimo");
		HashMap<String, String> meta = new HashMap<String, String>();
		meta.put("link", webviewUrlPagAnonimo + e.getObjectId());

		Date expiration = expirationDelete == null ? condizioniPagamento.getDtFinevalidita() : expirationDelete;
		RTMessage rtMessage = new RTMessage(body, subject, altBody, altSubject, template, mode, recipients, expiration, notBefore, meta, codiceEvento);
		
		return rtMessage;
	}
	
}
