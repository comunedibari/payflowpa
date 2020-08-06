package it.tasgroup.idp.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;

public class BuildCSVBasicV2Notification extends AbstractBuildNotification {

	private static char EOL = '\n'; // smart proxy usa solo il line feed
	private static char FIELD_SEPARATOR = '|';
	private static char COMPLEX_FIELD_SEPARATOR = ';';
	private static char QUOTE = '"';

	private static String MSG_HEADER = "Plugin notifiche CSVBasicV2";
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Override
	protected PagamentoModel creoPagamentoModel(NotifichePagamenti notificaPagamento, String statoNotificaPagamento, EntityManager em) {
		
		PagamentoModel pagamentoModel = new PagamentoModel();

		
		pagamentoModel.setIdNotifica(notificaPagamento.getIdNotifica());
		pagamentoModel.setIdPagamento(notificaPagamento.getIdPagamento());
		pagamentoModel.setIdPendenza(notificaPagamento.getIdPendenza());
		
		pagamentoModel.setCdEnte(notificaPagamento.getCdEnte());
		pagamentoModel.setCdTrbEnte(notificaPagamento.getCdTrbEnte());
		
		pagamentoModel.setEsito(notificaPagamento.getStatoPagamento());
		pagamentoModel.setIdPagante(notificaPagamento.getCoPagante());
		pagamentoModel.setTipoCanalePagamento("IRIS");
		pagamentoModel.setMezzoPagamento(notificaPagamento.getTiposervizio());
		pagamentoModel.setIdTransazione(notificaPagamento.getMsgid());
		// versione corrente riga notifiche_pagamenti
		pagamentoModel.setVersionNotifichePagamento(notificaPagamento.getVersion());
		
		return pagamentoModel;
	}
	
	@Override
	protected String buildXmlNotifica(String tipoSpontaneo, NotifichePagamentoModel notificaPagamentoModel, EntityManager em) throws Exception {
		
		// Attenzione: ridefinisco l'e2eMsgId appena deciso dal chiamante per metterne uno 'simil' long come richiesto per retrocompatibilita' con lo smartProxy
		String e2eMsgId = GeneratoreIdUnivoci.longLike();
		logger.info(MSG_HEADER + ": riassegnazione di NOTIFICHE_PAGAMENTI.E2EMSGID (e quindi NOTIFICHE_CART.E2EMSGID) in [" + e2eMsgId + "] e non [" + notificaPagamentoModel.getE2emsgid() + "] per retrocompatibilita' con un id 'simil' long come voluto dallo SmartProxy");
		notificaPagamentoModel.setE2emsgid(e2eMsgId);
		
		String STATO_PAGAMENTO = "PAGATO";
		String PAGATO_IDP = "Si"; // nello smart proxy puo' valere "Si" o "No"

		StringBuffer csv = new StringBuffer("CREDITORE|TIPO_DEBITO|DEBITORI|ID_DEBITO|ID_PAGAMENTO|DATA_SCADENZA|DATA_INIZIO_VALIDITA|DATA_FINE_VALIDITA|CAUSALE_PAGAMENTO|STATO_PAGAMENTO|IMPORTO_PAGAMENTO|VOCI_PAGAMENTO|ANNO_RIFERIMENTO|NOTE_DEBITO|CAUSALE_DEBITO|IBAN_RIACCREDITO|IMPORTO_PAGATO|DATA_VALUTA_ACCREDITO|CANALE_PAGAMENTO|DATA_PAGAMENTO|NOTE_PAGAMENTO|PAGATO_IDP").append(EOL);
		
		for (PagamentoModel pagamentoModel : notificaPagamentoModel.getListaPagamenti()) {

			/*
			 *  recupero di dati del pagamentoModel non messi a disposizione a monte 
			 */
			
			// debitori (da concatenare)
			
			String sqlDebitori = "select co_destinatario from jltdepd jltdepd where jltdepd.id_pendenza = :idPendenza";
	
			Query queryDebitori = em.createNativeQuery(sqlDebitori);
			queryDebitori.setParameter("idPendenza", pagamentoModel.getIdPendenza());
			List<String> listaDestinatari = queryDebitori.getResultList();
			StringBuffer sbDebitori = new StringBuffer();
			for (String destinatario : listaDestinatari) {
				sbDebitori.append(destinatario + COMPLEX_FIELD_SEPARATOR);
			}
			String debitori = sbDebitori.substring(0, sbDebitori.length() - 1);
			
			// voci (da concatenare)

			String sqlVociPagamento = "select de_voce, im_voce from pagamenti join jltvopd on jltvopd.id_condizione = pagamenti.id_condizione where pagamenti.id = :idPagamento";
			
			Query queryVociPagamento = em.createNativeQuery(sqlVociPagamento);
			queryVociPagamento.setParameter("idPagamento", pagamentoModel.getIdPagamento());
			List<Object[]> listaVociPagamento = queryVociPagamento.getResultList();
			StringBuffer sbVociPagamento = new StringBuffer();
			String vociPagamento = null;
			if (!listaVociPagamento.isEmpty()) {
				for (Object[] voce : listaVociPagamento) {
					String descrizione = (String)voce[0]; // nota: dato obbligatorio, non puo' quindi valere null TODO e' un tinytext, quindi ? devo limitarlo ?
					String importo = fmt((BigDecimal)voce[1]); // nota: dato obbligatorio, non puo' quindi valere null
					sbVociPagamento.append(descrizione + "=" + importo + COMPLEX_FIELD_SEPARATOR);
				}
				vociPagamento = sbVociPagamento.substring(0, sbVociPagamento.length() - 1);
			}

			// altri dati
			
			String sql = "select " + 
					"pagamenti.id_pendenzaente, pagamenti.im_pagato, pagamenti.data_accredito_ente, pagamenti.ts_decorrenza, " +
					"jltcopd.id_pagamento, jltcopd.dt_scadenza, jltcopd.dt_iniziovalidita, jltcopd.dt_finevalidita, jltcopd.causale_pagamento, " +
					"jltpend.im_totale, jltpend.anno_riferimento, jltpend.note, jltpend.de_causale " +
					"from pagamenti " +
					"join jltcopd on jltcopd.id_condizione = pagamenti.id_condizione " +
					"join jltpend jltpend on jltpend.id_pendenza = pagamenti.id_pendenza " +
					"where pagamenti.id = :idPagamento";
		
			Query query = em.createNativeQuery(sql);
			query.setParameter("idPagamento", pagamentoModel.getIdPagamento());
			Object[] datiPagamenti = (Object[]) query.getSingleResult() ;
		
			pagamentoModel.setIdPendenzaEnte((String)datiPagamenti[0]);
			pagamentoModel.setImporto(fmt((BigDecimal)datiPagamenti[1]));
			String dataValutaAccredito = fmt((Date)datiPagamenti[2], "yyyy-MM-dd");
			pagamentoModel.setDataOraPagamento(fmt((Timestamp)datiPagamenti[3], "yyyy-MM-dd'T'HH:mm:ss"));
			pagamentoModel.setIdPagamento((String)datiPagamenti[4]);
			pagamentoModel.setDataScadenzaPagamento(fmt((Date)datiPagamenti[5], "yyyy-MM-dd'T'HH:mm:ss"));
			String dataInizioValidita = fmt((Date)datiPagamenti[6], "yyyy-MM-dd'T'HH:mm:ss");
			String dataFineValidita = fmt((Date)datiPagamenti[7], "yyyy-MM-dd'T'HH:mm:ss");
			String causalePagamento = (String)datiPagamenti[8];
			pagamentoModel.setImportoTransato(fmt((BigDecimal)datiPagamenti[9]));
			String annoRiferimento = String.valueOf((Integer)datiPagamenti[10]);
			pagamentoModel.setNote((String)datiPagamenti[11]);
			pagamentoModel.setDeCausale((String)datiPagamenti[12]);

			pagamentoModel.setDescrizioneCanalePagamento(""); // TODO facendo un test sullo smart proxy sembra sia vuoto 

			/*
			 *  creazione di una riga di dati CSV
			 *  cfr smartproxy: CSVBasicParser.marshall()
			 */
			
			csv.append(fmt(pagamentoModel.getCdEnte())).append(FIELD_SEPARATOR); // CREDITORE - notifiche_pagamenti.cd_ente - la classe astratta padre ha reso tutti i pagamentoModel omogenei per cdEnte
			csv.append(fmt(pagamentoModel.getCdTrbEnte())).append(FIELD_SEPARATOR); // TIPO_DEBITO - notifiche_pagamenti.cd_trb_ente - la classe astratta padre ha reso tutti i pagamentoModel omogenei per cdTrbEnte
			csv.append(fmt(debitori)).append(FIELD_SEPARATOR); // DEBITORI - jltdepd.co_destinatario
			csv.append(fmt(pagamentoModel.getIdPendenzaEnte())).append(FIELD_SEPARATOR); // ID_DEBITO - pagamenti.id_pendenzaente
			csv.append(fmt(pagamentoModel.getIdPagamento())).append(FIELD_SEPARATOR); // ID_PAGAMENTO - jltcopd.id_pagamento
			csv.append(fmt(pagamentoModel.getDataScadenzaPagamento())).append(FIELD_SEPARATOR); // DATA_SCADENZA - jltcopd.dt_scadenza
			csv.append(fmt(dataInizioValidita)).append(FIELD_SEPARATOR); // DATA_INIZIO_VALIDITA - jltcopd.dt_iniziovalidita
			csv.append(fmt(dataFineValidita)).append(FIELD_SEPARATOR); // DATA_FINE_VALIDITA - jltcopd.dt_finevalidita
			csv.append(fmt(causalePagamento)).append(FIELD_SEPARATOR); // CAUSALE_PAGAMENTO - jltcopd.causale_pagamento
			csv.append(STATO_PAGAMENTO).append(FIELD_SEPARATOR); // STATO_PAGAMENTO 
			csv.append(fmt(pagamentoModel.getImportoTransato())).append(FIELD_SEPARATOR); // IMPORTO_PAGAMENTO - jltpend.im_totale
			csv.append(fmt(vociPagamento)).append(FIELD_SEPARATOR); // VOCI_PAGAMENTO - jltvopd_de_voce_im_voce - concatenazione de_voce=im_voce ;
			csv.append(fmt(annoRiferimento)).append(FIELD_SEPARATOR); // ANNO_RIFERIMENTO - jltpend_anno_riferimento
			csv.append(fmt(pagamentoModel.getNote())).append(FIELD_SEPARATOR); // NOTE_DEBITO - jltpend.note
			csv.append(fmt(pagamentoModel.getDeCausale())).append(FIELD_SEPARATOR); // CAUSALE_DEBITO - jltpend.de_causale
			csv.append("").append(FIELD_SEPARATOR); // IBAN_RIACCREDITO
			csv.append(fmt(pagamentoModel.getImporto())).append(FIELD_SEPARATOR); // IMPORTO_PAGATO - pagamenti.im_pagato
			csv.append(fmt(dataValutaAccredito)).append(FIELD_SEPARATOR); // DATA_VALUTA_ACCREDITO - pagamenti.data_accredito_ente
			csv.append(fmt(pagamentoModel.getDescrizioneCanalePagamento())).append(FIELD_SEPARATOR); // CANALE_PAGAMENTO - cfg_modalita_pagamento.descrizione
			csv.append(fmt(pagamentoModel.getDataOraPagamento())).append(FIELD_SEPARATOR); // DATA_PAGAMENTO - pagamenti.ts_decorrenza
			csv.append("").append(FIELD_SEPARATOR); // NOTE_PAGAMENTO
			csv.append(PAGATO_IDP); // PAGATO_IDP // TODO facendo un test sullo smart proxy sembra sia vuoto
			csv.append(EOL);
		
		}
		
		return csv.toString();
	}

	private String fmt(Timestamp ts, String formato) {
		String ret = null;
		if (ts != null) {
			Date data = new Date(ts.getTime());
			ret = fmt(data, formato);
		}
		return ret;
	}
	private String fmt(Date data, String formato) {
		String ret = null;
		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			ret = sdf.format(data);
		}
		return ret;
	}
	private String fmt(BigDecimal importo) {
		String ret = null;
		if (importo != null) {
			ret = importo.movePointRight(3).toString();
		}
		return ret;
	}
	private String fmt(String dato) {
		String ret = "";
		if (dato != null) {
			if (dato.contains(FIELD_SEPARATOR + "")) {
				ret = QUOTE + dato + QUOTE;
			} else {
				ret = dato;
			}
		}
		return ret;
	}

}
