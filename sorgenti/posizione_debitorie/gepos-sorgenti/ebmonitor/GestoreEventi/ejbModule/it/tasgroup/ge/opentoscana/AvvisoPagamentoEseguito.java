package it.tasgroup.ge.opentoscana;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import it.tasgroup.iris2.pagamenti.Pagamenti;

public class AvvisoPagamentoEseguito extends GestoreEventiOpenToscanaHelper {

	public AvvisoPagamentoEseguito(EntityManager em) {
		super.em = em;
	}

	@Override
	public RTMessage getRTMessage(CommunicationEvent e, CfgEventi cfgEvento) {
		
		String codiceEvento = cfgEvento.getCodiceEvento();
		
		String[] datiEvento = e.getObjectId().split("\\|"); // CD_ENTE|ID_PAGAMENTO|CF_DEBITORE
		String cdEnte = datiEvento[0];
		String idPagamento = datiEvento[1];
		String cfDebitore = datiEvento[2];
		
		Query queryEnti = super.em.createNamedQuery("TributiEntiByCdEnte");
		queryEnti.setParameter("cdEnte", cdEnte);
		List<Enti> enti = (List) queryEnti.getResultList(); // ENTI
		
		Query queryCondizioniPagamento = super.em.createNamedQuery("CondizioniPagamentoByIdEnteAndIdPagamentoEnte");
		queryCondizioniPagamento.setParameter("idEnte", enti.get(0).getIdEnte());
		queryCondizioniPagamento.setParameter("idPagamento", idPagamento);
		queryCondizioniPagamento.setParameter("stRiga", Arrays.asList(super.ST_RIGA_VALIDA));
		List<CondizioniPagamento> condizioniPagamento = (List) queryCondizioniPagamento.getResultList(); // JLTCOPD
		
		TributiEntiPK pk = new TributiEntiPK();
		pk.setIdEnte(condizioniPagamento.get(0).getIdEnte());
		pk.setCdTrbEnte(condizioniPagamento.get(0).getCdTrbEnte());
		TributiEnti tribEnti = super.em.find(TributiEnti.class, pk); // JLTENTR
		
		Query queryPag = super.em.createNamedQuery("findPagamentoByIdCondizioneAndStato");
		queryPag.setParameter("idCondizione", condizioniPagamento.get(0).getIdCondizione());
		queryPag.setParameter("eseguito", "ES");
		queryPag.setParameter("eseguitoSbf", "EF");
		List<Pagamenti> pagamenti = (List) queryPag.getResultList();
		Pagamenti pagamento = pagamenti.get(0); // PAGAMENTI

		super.messageId = super.getMessageId(e.getObjectId(), Arrays.asList(EnumTipiEventi.AVVISATURA_PUSH_INSERT.getEventoCode(), EnumTipiEventi.AVVISATURA_PUSH_UPDATE.getEventoCode()));
		
		if (super.messageId == null) {
			super.method = Method.POST;	
		} else {
			super.method = Method.PATCH;	
			codiceEvento = EnumTipiEventi.AVVISATURA_PUSH_UPDATE.getEventoCode();
		}

		String body = IrisProperties.getProperty("rt.messenger.avviso.pagamento.eseguito.body");
		Date expiration = null;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String subject = tribEnti.getDeTrb() + ". Importo pagato: " + df.format(pagamento.getImPagato()) + " €";
		String altBody = body;
		String altSubject = null;
		String template = IrisProperties.getProperty("rt.messenger.avviso.pagamento.eseguito.template");
		Mode mode = Mode.BYIDENTITY;
		List<String> recipients = new ArrayList<String>();
		recipients.add("TINIT-" + cfDebitore); // TODO nazione ?
		Date notBefore = null;
		String webviewUrlPagAnonimo = IrisProperties.getProperty("rt.messenger.avviso.pagamento.eseguito.webviewurlpaganonimo");
		HashMap<String, String> meta = new HashMap<String, String>();
		meta.put("link", webviewUrlPagAnonimo + e.getObjectId());

		RTMessage rtMessage = new RTMessage(body, subject, altBody, altSubject, template, mode, recipients, expiration, notBefore, meta, codiceEvento);
		
		return rtMessage;
	}

}
