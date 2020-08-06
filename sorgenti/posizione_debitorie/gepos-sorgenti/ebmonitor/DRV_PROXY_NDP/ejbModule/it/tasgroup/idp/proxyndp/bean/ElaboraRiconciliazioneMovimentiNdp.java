package it.tasgroup.idp.proxyndp.bean;

import it.tasgroup.ge.bean.GestoreEventiInterface;
import it.tasgroup.ge.enums.EnumTipiEventi;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.gateway.EsitiNdp;
import it.tasgroup.idp.util.DataBag;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris2.enums.EnumFlagIncasso;
import it.tasgroup.iris2.pagamenti.DistintePagamento;
import it.tasgroup.iris2.pagamenti.MovimentiAccredito;
import it.tasgroup.iris2.pagamenti.Rendicontazioni;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unchecked")
@Stateless
@Local(IElaboraRiconciliazioneMovimentiNdp.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ElaboraRiconciliazioneMovimentiNdp implements IElaboraRiconciliazioneMovimentiNdp {
	private static final String DB_USER = "RiconciliazioneMovimentiNdpTimer";
	
	public static final String TIPO_ACCREDITO = MovimentiAccredito.InfoRiconciliazione.TIPO.name();
	public static final String CHECK_DIGIT = MovimentiAccredito.InfoRiconciliazione.CHECK_DIGIT.name();
	public static final String REFERENCE = MovimentiAccredito.InfoRiconciliazione.REFERENCE.name();
	public static final String IMPORTO = MovimentiAccredito.InfoRiconciliazione.IMPORTO.name();
	
	public static final String ACCREDITO_RFS = MovimentiAccredito.InfoRiconciliazioneTipo.RFS.name();
	public static final String ACCREDITO_RFB = MovimentiAccredito.InfoRiconciliazioneTipo.RFB.name();
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager em;
	
	@EJB(beanName = "GestoreEventiBean")
	private GestoreEventiInterface gestoreEventiProxy;

	private final Log logger = LogFactory.getLog(this.getClass());
	
	// TODO #30 Formattazione centralizzata stringhe
	
	/*
	 * Sezione pubblica - Logica di business
	 */

	public void setMaxExecutionOccurredOnMovimentiAccredito (MovimentiAccredito movimentiAccredito) {
		movimentoDiAccreditoNonRiconciliatoPerSuperamentoNMaxElaborazioni(movimentiAccredito);
	}
	
	public void doRiconciliazioneAccreditoSingolo (MovimentiAccredito movimentiAccredito, EnumFlagIncasso flagIncasso) {
		Long id = movimentiAccredito.getId();
		BigDecimal importo = movimentiAccredito.getImporto();
		DataBag infoRiconciliazione = DataBag.fromString(movimentiAccredito.getInfoRiconciliazione());
		String tipoAccredito = infoRiconciliazione.get(TIPO_ACCREDITO);
		boolean isValueOfImportoDichiaratoOk = true;
		if (ACCREDITO_RFS.equalsIgnoreCase(tipoAccredito)) {
			String checkDigit = infoRiconciliazione.get(CHECK_DIGIT);
			String reference = infoRiconciliazione.get(REFERENCE);
			String importoDichiarato = infoRiconciliazione.get(IMPORTO);
			if (checkDigit != null && reference != null) {
				// Controllo check digit
				String computedCheckDigit = checkDigit(reference);
				if (computedCheckDigit == null /* && checkDigit != null */ || !computedCheckDigit.equals(checkDigit)) {
					// Mancata corrispondenza nei check digit
					logger.info("Mancata corrispondenza tra valore impostato e valore calcolato di check digit nel movimento di accredito con id %1. Valore impostato: '%2'. Valore calcolato '%3'".replace("%1", String.valueOf(id)).replace("%2", checkDigit).replace("%3", computedCheckDigit));
					movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_IUV_RF_NON_VALIDO);
					// Questa anomalia interrompe la procedura di riconciliazione
					return;
				}
			}
			if (importoDichiarato != null) {
				// Verifica congruenza importo dichiarato
				isValueOfImportoDichiaratoOk = isImportoDichiaratoEqualToImportoRendicontato(id, importoDichiarato, importo);
				// Se anche l'importo dichiarato non corrispondesse all'importo rendicontato si procede nella riconciliazione
			}
			riconciliazioneAccreditoSingolo(movimentiAccredito, isValueOfImportoDichiaratoOk,flagIncasso);
			
		} else if (ACCREDITO_RFB.equalsIgnoreCase(tipoAccredito)) {
			String importoDichiarato = infoRiconciliazione.get(IMPORTO);
			if (importoDichiarato != null) {
				// Verifica congruenza importo dichiarato
				isValueOfImportoDichiaratoOk = isImportoDichiaratoEqualToImportoRendicontato(id, importoDichiarato, importo);
				// Se anche l'importo dichiarato non corrispondesse all'importo rendicontato si procede nella riconciliazione
			}
			riconciliazioneAccreditoSingolo(movimentiAccredito, isValueOfImportoDichiaratoOk,flagIncasso);
			
		} else {
			logger.info("Informazioni di riconciliazione con tipo non riconosciuto per il movimento di accredito con id %1".replace("%1", String.valueOf(id)));
			incrementoElaborazioniSuMovimentoDiAccredito(movimentiAccredito);
		}
	}
	
	public void doRiconciliazioneAccreditoCumulativo (MovimentiAccredito movimentiAccredito, EnumFlagIncasso flagIncasso) {
		Long id = movimentiAccredito.getId();
		String trn = movimentiAccredito.getTrn();
		String idRiconciliazione = movimentiAccredito.getIdRiconciliazione();
		BigDecimal importo = movimentiAccredito.getImporto();
		
		// Individuazione rendicontazione
		Rendicontazioni rendicontazioni = null;
	
			// non uso più il TRN
			logger.info("TRN non presente per il movimento di accredito con id %1".replace("%1", String.valueOf(id)));
			try {
				// #3785 visto che l'id flusso delle rendicontazioni è sempe maiuscolo
				// è sufficiente cercare con l'idRiconciliazione della movimenti accredito in maiuscolo
				// senza fare la ricerca case-insensitive che è più costosa
				rendicontazioni = (Rendicontazioni) em.createNamedQuery("findRendicontazioniByIdFlusso")
					.setParameter("idFlusso", idRiconciliazione.toUpperCase())
					.getSingleResult();
			} catch (NoResultException e) {
				// Nessun record trovato: usciamo dalla procedura confidando che alla prossima esecuzione del timer sul DB sia stato inserito il flusso cercato
				logger.info("Non e' stata trovata alcuna rendicontazione con idFlusso = %1 associata al movimento di accredito con id %2".replace("%1", idRiconciliazione).replace("%2", String.valueOf(id)));
				movimentoDiAccreditoConRiconciliazioneInCorso(movimentiAccredito);
				return;
			}

		// Rendicontazione individuata
		// Quadratura importi di rendicontazione
		BigDecimal importoRendicontazione = rendicontazioni.getImporto();
		if (importo == null) {
			logger.info("Importo non valorizzato nel movimento di accredito con id %1".replace("%1", String.valueOf(id)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ERRORE_GENERICO_DETTAGLI_NEL_LOG);
			return;
		}
		if (importoRendicontazione == null) {
			logger.info("Importo di rendicontazione non valorizzato nella rendicontazione con id %1 associata al movimento di accredito con id %2".replace("%1", String.valueOf(rendicontazioni.getId())).replace("%2", String.valueOf(id)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ERRORE_GENERICO_DETTAGLI_NEL_LOG);
			return;
		}
		switch(importo.compareTo(importoRendicontazione)) {
		case 1:
			// Gli importi squadrano: importo > importoRendicontazione
			logger.info("Importo del movimento di accredito maggiore dell'importo di rendicontazione nel movimento di accredito con id %1. Importo di accredito %2. Importo di rendicontazione %3".replace("%1", String.valueOf(id)).replace("%2", importo.toPlainString()).replace("%3", importoRendicontazione.toPlainString()));
			rendicontazioneNonRiconciliata(rendicontazioni, movimentiAccredito);
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_IMPORTO_MOV_MAGGIORE_IMPORTO_REND);
			return;
		case -1:
			// Gli importo squadrano: importo < importoRendicontazione
			logger.info("Importo del movimento di accredito minore dell'importo di rendicontazione nel movimento di accredito con id %1. Importo di accredito %2. Importo di rendicontazione %3".replace("%1", String.valueOf(id)).replace("%2", importo.toPlainString()).replace("%3", importoRendicontazione.toPlainString()));
			rendicontazioneNonRiconciliata(rendicontazioni, movimentiAccredito);
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_IMPORTO_MOV_MINORE_IMPORTO_REND);
			return;
		}
		// Gli importi quadrano
		logger.info("Importo del movimento di accredto uguale all'importo di rendicontazione nel movimento di accredito con id %1".replace("%1", String.valueOf(id)));

		
		// Individuazione intestatario
		String sia = movimentiAccredito.getSia();
		if (sia == null) {
			// Non e' possibile riconciliare se sia e' non specificato
			logger.info("SIA non specificato per il movimento di accredito con id %1".replace("%1", String.valueOf(id)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_SIA_NON_TROVATO);
			return;
		}
		Intestatari intestatario = null;
		try {
			intestatario = (Intestatari) em.createNamedQuery("IntestatarioBySiaCBI")
				.setParameter("siaCBI", sia)
				.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nessuna intestatario trovata per SIA_CBI %1 presente nel movimento di accredito con id %2".replace("%1", sia).replace("%2", String.valueOf(id)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_INTESTATARIO_NON_TROVATO);
			return;
		} catch (NonUniqueResultException e) {
			logger.info("Intestatario NON UNICO per SIA_CBI %1 presente nel movimento di accredito con id %2".replace("%1", sia).replace("%2", String.valueOf(id)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_INTESTATARIO_NON_TROVATO);
			return;
		}
		
		elaboraFlussoFR(rendicontazioni,intestatario,movimentiAccredito,flagIncasso.getChiave());
				
	}
	
	@Override
	public void doRiconciliazioneFlussoFR(Rendicontazioni rendicontazioni) {
			
		Rendicontazioni attachedRendicontazioni = em.find(Rendicontazioni.class,rendicontazioni.getId());
		
		String ricevente  = attachedRendicontazioni.getCasellarioInfo().getRicevemteVero();
		
		Intestatari intestatario = null;
		try {
		   intestatario = (Intestatari)em.createNamedQuery("IntestatarioByLapl").setParameter("lapl", ricevente).getSingleResult();
		} catch (Throwable t) {
			logger.error("ElaboraRiconciliazioneMovimentiNdp::doRiconciliazioneFlussoFR error getting Intestatari with lapl = "+ricevente);
			logger.error("ElaboraRiconciliazioneMovimentiNdp::doRiconciliazioneFlussoFR skip elaboration and go ahead!!!");
			return;
		}
		elaboraFlussoFR(attachedRendicontazioni, intestatario, null, "2");	
	}


	
	
	/** 
	 * Questo metodo centralizza l'elaborazione di un flusso FR.
	 * Se viene passato anche il movimentoAccredito relativo, tutta l'elaborazione viene effettuata in riferimento
	 * a tale movimento di acccredito. In caso contrario la riconciliazione avviene considerando solo il flusso FR a se stante.
	 * 
	 * @param rendicontazioni
	 * @param intestatario
	 * @param movimentiAccredito
	 */
	private void elaboraFlussoFR(Rendicontazioni rendicontazioni, Intestatari intestatario, MovimentiAccredito movimentiAccredito, String flagIncasso) {

		Long idMovimentoAccredito=null;
		if (movimentiAccredito!=null) {
			idMovimentoAccredito=movimentiAccredito.getId();
		}
		
		// Individuazione lista IUV
		List<EsitiNdp> esitiNdp = em.createNamedQuery("findEsitiNdpByRendicontazioniAndFlagRiconciliazione")
			.setParameter("rendicontazioni", rendicontazioni)
			.setParameter("flagRiconciliazione", (short) 0)
			.getResultList();

// Spostato controllo in fase di ricerca del flusso FR		
//		if (esitiNdp == null) {
//			logger.info("Non sono stati trovati esiti da riconciliare corrispondenti alla rendicontazione con id %1 che fa riferimento al movimento di accredito con id %2".replace("%1", String.valueOf(rendicontazioni.getId())).replace("%2", String.valueOf(id)));
//			incrementoElaborazioniSuMovimentoDiAccredito(movimentiAccredito);
//			return;
//		}
		
		for (EsitiNdp esitoNdp : esitiNdp) {
			// Individuazione distinta corrispondente all'esito
			String iuv = esitoNdp.getIdRiconciliazione(); 
			// TODO iuv null
			DistintePagamento distinta = null;
			try {
				distinta = (DistintePagamento) em.createNamedQuery("DistinteEseguiteByIuvAndIdFiscCreditore")
						.setParameter("iuv", iuv)
						.setParameter("idFiscCreditore", intestatario.getLapl())
						.getSingleResult();
			} catch (NoResultException e) {
				// Non e' stato trovato alcun record
				if (idMovimentoAccredito!=null) {
					logger.info("Nessuna distinta trovata per IUV %1 e idFiscCreditore %2 presente nell'esito con id %3 che fa riferimento al movimento di accredito con id %4"
						.replace("%1", iuv).replace("%2", intestatario.getLapl()).replace("%3", String.valueOf(esitoNdp.getId())).replace("%4", String.valueOf(idMovimentoAccredito) ));
				} else {
					logger.info("Nessuna distinta trovata per IUV %1 e idFiscCreditore %2 presente nell'esito con id %3"
							.replace("%1", iuv).replace("%2", intestatario.getLapl()).replace("%3", String.valueOf(esitoNdp.getId())));
					
				}
				if (esitoNdp.getEsitoPagamento().equals("ESEGUITO_NO_RPT")) {
					esitoNonRiconciliato(esitoNdp, Anomalia.E_DISTINTA_NON_TROVATA, rendicontazioni, movimentiAccredito);
				} else {
				    esitoNonRiconciliato(esitoNdp, Anomalia.E_DISTINTA_NON_TROVATA, rendicontazioni, movimentiAccredito);
				}
				continue;
			} catch (NonUniqueResultException e) {
				if (idMovimentoAccredito!=null) {
					logger.info("Distinta NON UNICA per IUV %1 e idFiscCreditore %2 presente nell'esito con id %3 che fa riferimento al movimento di accredito con id %4"
							.replace("%1", iuv).replace("%2", intestatario.getLapl()).replace("%3", String.valueOf(esitoNdp.getId())).replace("%4", String.valueOf(idMovimentoAccredito) ));
				} else {
					logger.info("Distinta NON UNICA per IUV %1 e idFiscCreditore %2 presente nell'esito con id %3 "
							.replace("%1", iuv).replace("%2", intestatario.getLapl()).replace("%3", String.valueOf(esitoNdp.getId())));					
				}
				esitoNonRiconciliato(esitoNdp, Anomalia.E_DISTINTA_NON_TROVATA, rendicontazioni, movimentiAccredito);
				continue;
			}
			
			// E' stata trovata una distinta. Quadratura degli importi
			switch (esitoNdp.getImporto().compareTo(distinta.getImporto())) {
			case 0:
				// Gli importi quadrano
				if (idMovimentoAccredito!=null) {
					logger.info("Importo dell'esito uguale all'importo della distinta nell'esito con id %1 che corrisponde alla distinta con id %2 che fa riferimento al movimento di accredito con id %3".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(distinta.getId()).replace("%3", String.valueOf(idMovimentoAccredito))));
				} else {
					logger.info("Importo dell'esito uguale all'importo della distinta nell'esito con id %1 che corrisponde alla distinta con id %2 ".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(distinta.getId())));					
				}
				pagamentiEseguiti(movimentiAccredito, distinta, flagIncasso, esitoNdp);

				// eMail, solo nel caso in cui stiamo riconciliando con conto tecnico (riconciliazione completa)
				if (movimentiAccredito!=null) {
					logger.info("Notifica evento 'invio quietanza' per distinta con id %1".replace("%1", String.valueOf(distinta.getId())));
					CommunicationEvent ce = new CommunicationEvent(EnumTipiEventi.INVIOQUIETANZA.getEventoCode(), distinta.getId().toString(), null);
					gestoreEventiProxy.eventNotify(ce);
				}
				
				esitoRiconciliato(esitoNdp, rendicontazioni, movimentiAccredito);
				break;
			case 1:
				// Gli importi squadrano: importo esito > importo distinta
				if (idMovimentoAccredito!=null) {
					logger.info("Importo dell'esito maggiore dell'importo della distinta nell'esito con id %1 che corrisponde alla distinta con id %2 che fa riferimento al movimento di accredito con id %3. Importo dell'esito: %4. Importo della distinta %5".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(distinta.getId()).replace("%3", String.valueOf(idMovimentoAccredito))).replace("%4", esitoNdp.getImporto().toPlainString()).replace("%5", distinta.getImporto().toPlainString()));
				} else {
					logger.info("Importo dell'esito maggiore dell'importo della distinta nell'esito con id %1 che corrisponde alla distinta con id %2. Importo dell'esito: %4. Importo della distinta %5".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(distinta.getId())).replace("%4", esitoNdp.getImporto().toPlainString()).replace("%5", distinta.getImporto().toPlainString()));					
				}
				esitoNonRiconciliato(esitoNdp, Anomalia.E_IMPORTO_ESI_MAGGIORE_IMPORTO_DIST, rendicontazioni, movimentiAccredito);
				break;
			case -1:
				// Gli importi squadrano: importo esito < importo distinta
				if (idMovimentoAccredito!=null) {
					logger.info("Importo dell'esito minore dell'importo della distinta nell'esito con id %1 che corrisponde alla distinta con id %2 che fa riferimento al movimento di accredito con id %3. Importo dell'esito: %4. Importo della distinta %5".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(distinta.getId()).replace("%3", String.valueOf(idMovimentoAccredito))).replace("%4", esitoNdp.getImporto().toPlainString()).replace("%5", distinta.getImporto().toPlainString()));
				} else {
					logger.info("Importo dell'esito minore dell'importo della distinta nell'esito con id %1 che corrisponde alla distinta con id %2. Importo dell'esito: %4. Importo della distinta %5".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(distinta.getId())).replace("%4", esitoNdp.getImporto().toPlainString()).replace("%5", distinta.getImporto().toPlainString()));
				}
				esitoNonRiconciliato(esitoNdp, Anomalia.E_IMPORTO_ESI_MINORE_IMPORTO_DIST, rendicontazioni, movimentiAccredito);
			}
		}
		// Aggiornamento stato flusso e rendicontazione
		List<Object[]> statoEsitiList = em.createQuery("SELECT e.flagRiconciliazione, COUNT(*) FROM EsitiNdp e WHERE e.rendicontazioni = :rendicontazioni GROUP BY e.flagRiconciliazione ORDER BY 1")
			.setParameter("rendicontazioni", rendicontazioni)
			.getResultList();
		int nEsiti, nEsitiDaRiconciliare, nEsitiRiconciliati, nEsitiNonRiconciliati; nEsiti = nEsitiDaRiconciliare = nEsitiRiconciliati = nEsitiNonRiconciliati = 0;
		for (Object[] row : statoEsitiList) {
			short flagRiconciliazione = (Short) row[0];
			long n = (Long) row[1];
			switch (flagRiconciliazione) {
			case 0:
				nEsitiDaRiconciliare = (int)n;
				break;
			case 1:
				nEsitiRiconciliati = (int)n;
				break;
			case 2:
				nEsitiNonRiconciliati = (int)n;
				break;
			}
			nEsiti += (int)n;
		}
		if (idMovimentoAccredito!=null) {
			logger.info("Tot n. %1 esiti collegati a rendicontazione con id %2 che corrisponde a movimento di accredito con id %3. Esiti da riconciliare: %4. Esiti riconciliati: %5. Esiti parzialmente riconciliati %6".replace("%1", String.valueOf(nEsiti)).replace("%2", String.valueOf(rendicontazioni.getId())).replace("%3", String.valueOf(idMovimentoAccredito)).replace("%4", String.valueOf(nEsitiDaRiconciliare)).replace("%5", String.valueOf(nEsitiRiconciliati)).replace("%6", String.valueOf(nEsitiNonRiconciliati)));
		} else {
			logger.info("Tot n. %1 esiti collegati a rendicontazione con id %2 . Esiti da riconciliare: %4. Esiti riconciliati: %5. Esiti parzialmente riconciliati %6".replace("%1", String.valueOf(nEsiti)).replace("%2", String.valueOf(rendicontazioni.getId())).replace("%4", String.valueOf(nEsitiDaRiconciliare)).replace("%5", String.valueOf(nEsitiRiconciliati)).replace("%6", String.valueOf(nEsitiNonRiconciliati)));			
		}
		if (nEsitiRiconciliati == nEsiti) {
			// Tutti gli esiti sono riconciliati
			rendicontazioneRiconciliata(rendicontazioni, movimentiAccredito);
			if (movimentiAccredito!=null) {
				movimentoDiAccreditoRiconciliato(movimentiAccredito);
			}	
		} else if (nEsitiRiconciliati > 0 && nEsitiNonRiconciliati > 0) {
			// Esiti in parte riconciliati in parte non riconciliati
			rendicontazioneParzialmenteRiconciliata(rendicontazioni, movimentiAccredito);
			if (movimentiAccredito!=null) {
				movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ANOMALIE_SU_ESITI);
			}	
		} else if (nEsitiRiconciliati > 0) {
			// Ci sono esiti riconciliati, ma non sono tutti
			rendicontazioneParzialmenteRiconciliata(rendicontazioni, movimentiAccredito);
			if (movimentiAccredito!=null) {
				movimentoDiAccreditoConRiconciliazioneInCorso(movimentiAccredito);
			}	
		} else {
			// Ci sono record in stato non riconciliato
			rendicontazioneNonRiconciliata(rendicontazioni, movimentiAccredito);
			if (movimentiAccredito!=null) {
				movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ANOMALIE_SU_ESITI);
			}	
		}
	}
	
	
	
	
	public static enum Anomalia {
		  E_SUPERAMENTO_LIMITE_ELABORAZIONI
		, E_IUV_RF_NON_VALIDO
		, W_CAUSALE_IMPORTO_INCONGRUENTE
		, E_IMPORTO_MOV_MAGGIORE_IMPORTO_DIST
		, E_IMPORTO_MOV_MINORE_IMPORTO_DIST
		, E_ANOMALIE_SU_ESITI
		, E_IMPORTO_MOV_MAGGIORE_IMPORTO_REND
		, E_IMPORTO_MOV_MINORE_IMPORTO_REND
		, E_IMPORTO_ESI_MAGGIORE_IMPORTO_DIST
		, E_IMPORTO_ESI_MINORE_IMPORTO_DIST
		, E_IUV_NON_TROVATO
		, E_SIA_NON_TROVATO
		, E_DISTINTA_NON_TROVATA
		, E_INTESTATARIO_NON_TROVATO
		, E_CHIAVI_FR_INCONGRUENTI
		, E_ERRORE_GENERICO_DETTAGLI_NEL_LOG
		, E_PAGAMENTO_NOT_FOUND
		
		, E_DESTINATARO_PENDENZE_NON_TROVATO
		, E_TRIBUTO_ENTE_NON_TROVATO
		, E_CONDIZIONE_PAGAMENTO_NON_TROVATA
		, E_PENDENZA_NON_TROVATA
		, E_ESITI_NDP_NON_TROVATI
		, E_RENDICONTAZIONE_NON_TROVATA
		, E_ENTE_NON_TROVATO
		, E_IMPORTI_INCONGRUENTI
		
		
		
	}
	
	
	/*
	 * Sezione privata 1 - Macro operazioni usate nei workflow
	 */
	
	private boolean isImportoDichiaratoEqualToImportoRendicontato (Long idMovimento, String importoDichiaratoInCausale, BigDecimal importoRendicontato){
		// Controllo importo
		BigDecimal importoDichiaratoInCausaleComeNumero = parseBigDecimal(importoDichiaratoInCausale, '.');
		if (importoDichiaratoInCausaleComeNumero != null) {
			// E' stato specificato un importo
			if (importoDichiaratoInCausaleComeNumero.compareTo(importoRendicontato) != 0) {
				logger.info("Mancata corrispondenza tra valore di importo presente nel testo della causale e valore effettivo rendicontato nel movimento di accredito con id %1. Valore presente in causale: %2. Valore effettivo: %3.".replace("%1", String.valueOf(idMovimento)).replace("%2", importoDichiaratoInCausaleComeNumero.toPlainString()).replace("%3", importoRendicontato.toPlainString()));
				return false;
			}
		}
		return true;
	}
	
	private void riconciliazioneAccreditoSingolo (MovimentiAccredito movimentiAccredito, boolean isImportoDichiaratoEqualToImportoRendicontato, EnumFlagIncasso flagIncasso) {
		Long idMovimento = movimentiAccredito.getId();
		BigDecimal importoMovimento = movimentiAccredito.getImporto();
		String iuv = movimentiAccredito.getIdRiconciliazione();
		if (iuv == null) {
			// Non e' possibile riconciliare se iuv e' non specificato
			logger.info("IUV non specificato per il movimento di accredito con id %1".replace("%1", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_IUV_NON_TROVATO);
			return;
		}
		String sia = movimentiAccredito.getSia();
		if (sia == null) {
			// Non e' possibile riconciliare se sia e' non specificato
			logger.info("SIA non specificato per il movimento di accredito con id %1".replace("%1", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_SIA_NON_TROVATO);
			return;
		}
		Intestatari intestatario = null;
		try {
			intestatario = (Intestatari) em.createNamedQuery("IntestatarioBySiaCBI")
				.setParameter("siaCBI", sia)
				.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nessuna intestatario trovata per SIA_CBI %1 presente nel movimento di accredito con id %2".replace("%1", sia).replace("%2", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ERRORE_GENERICO_DETTAGLI_NEL_LOG);
			return;
		} catch (NonUniqueResultException e) {
			logger.info("Intestatario NON UNICO per SIA_CBI %1 presente nel movimento di accredito con id %2".replace("%1", sia).replace("%2", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ERRORE_GENERICO_DETTAGLI_NEL_LOG);
			return;
		}
		
		DistintePagamento distinta = null;
		try {
			distinta = (DistintePagamento) em.createNamedQuery("DistinteEseguiteByIuvAndIdFiscCreditore")
				.setParameter("iuv", iuv)
				.setParameter("idFiscCreditore", intestatario.getLapl())
				.getSingleResult();
		} catch (NoResultException e) {
			// Non e' stato trovato alcun record
			logger.info("Nessuna distinta trovata per IUV %1 e idFiscCreditore %2 presente nel movimento di accredito con id %3"
					.replace("%1", iuv).replace("%2", intestatario.getLapl()).replace("%3", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_DISTINTA_NON_TROVATA);
			return;
		} catch (NonUniqueResultException e) {
			logger.info("Distinta NON UNICA per IUV %1 e idFiscCreditore %2 presente nel movimento di accredito con id %3"
					.replace("%1", iuv).replace("%2", intestatario.getLapl()).replace("%3", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_DISTINTA_NON_TROVATA);
			return;
		}
		
		// Lo IUV corrisponde ad una distinta
		Long idDistinta = distinta.getId();
		BigDecimal importoDistinta = distinta.getImporto();
		logger.info("Lo IUV %1 del movimento di accredito con id %2 corrisponde alla distinta con id %3".replace("%1", iuv).replace("%2", String.valueOf(idMovimento)).replace("%3", String.valueOf(idDistinta)));
		// Quadratura importi
		if (importoMovimento == null) {
			logger.info("Importo di rendicontazione non valorizzato nel movimento di accredito con id %1".replace("%1", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ERRORE_GENERICO_DETTAGLI_NEL_LOG);
			return;
		}
		if (importoDistinta == null) {
			logger.info("Importo distinta non valorizzato nella distinta con id %1 associata al movimento di accredito con id %2".replace("%1", String.valueOf(idDistinta)).replace("%2", String.valueOf(idMovimento)));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_ERRORE_GENERICO_DETTAGLI_NEL_LOG);
			return;
		}
		switch (importoMovimento.compareTo(importoDistinta)) {
		case 0:
			// Gli importi quadrano
			logger.info("Importo di rendicontazione uguale a importo distinta per movimento di accredito con id %1".replace("%1", String.valueOf(idMovimento)));
			// Aggiornamento flag incasso per pagamenti corrispondenti alla distinta in esame
			pagamentiEseguiti(movimentiAccredito, distinta,flagIncasso.getChiave(),null);
			// eMail
			logger.info("Notifica evento 'invio quietanza' per distinta con id %1".replace("%1", String.valueOf(distinta.getId())));
			CommunicationEvent ce = new CommunicationEvent(EnumTipiEventi.INVIOQUIETANZA.getEventoCode(), distinta.getId().toString(), null);
			gestoreEventiProxy.eventNotify(ce);
			if (isImportoDichiaratoEqualToImportoRendicontato) {
				movimentoDiAccreditoRiconciliato(movimentiAccredito);
			}
			else {
				// Nel caso di importo dichiarato non corrispondente a importo rendicontato, e riconciliazione avvenuta, rimane una segnalazione di anomalia non bloccante
				movimentoDiAccreditoRiconciliato(movimentiAccredito, Anomalia.W_CAUSALE_IMPORTO_INCONGRUENTE);
			}
			break;
		case 1:
			// Gli importi squadrano: importoMovimento > importoDistinta
			logger.info("Importo di rendicontazione maggiore di importo distinta per il movimento di accredito con id %1. Importo rendicontazione: %2. Importo distinta: %3".replace("%1", String.valueOf(idMovimento)).replace("%2", importoMovimento.toPlainString()).replace("%3", importoDistinta.toPlainString()));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_IMPORTO_MOV_MAGGIORE_IMPORTO_DIST);
			break;
		case -1:
			// gli importi squadrano: importoMovimento < importoDistinta
			logger.info("Importo di rendicontazione minore di importo distinta per il movimento di accredito con id %1. Importo rendicontazione: %2. Importo distinta: %3".replace("%1", String.valueOf(idMovimento)).replace("%2", importoMovimento.toPlainString()).replace("%3", importoDistinta.toPlainString()));
			movimentoDiAccreditoNonRiconciliato(movimentiAccredito, Anomalia.E_IMPORTO_MOV_MINORE_IMPORTO_DIST);
		}
	}
	
	
	/*
	 * Sezione privata 2 - Operazioni su MovimentiAccredito
	 */
	
	private void movimentoDiAccreditoRiconciliato(MovimentiAccredito movimentiAccredito) {
		movimentiAccredito.setFlagRiconciliazione((short) 1);
		movimentiAccredito.setNumElaborazioni(safeIncrementShort(movimentiAccredito.getNumElaborazioni()));
		movimentiAccredito.setTsAggiornamento(now());
		movimentiAccredito.setOpAggiornamento(DB_USER);
		em.merge(movimentiAccredito);
		logger.info("Movimento di accredito con id %1 riconciliato".replace("%1", String.valueOf(movimentiAccredito.getId())));
	}
	
	private void movimentoDiAccreditoRiconciliato(MovimentiAccredito movimentiAccredito, Anomalia anomalia) {
		movimentiAccredito.setCodAnomalia(anomalia.name());
		movimentoDiAccreditoRiconciliato(movimentiAccredito);
	}
	
	private void movimentoDiAccreditoNonRiconciliato(MovimentiAccredito movimentiAccredito, Anomalia anomalia) {
		movimentiAccredito.setFlagRiconciliazione((short) 2);
		movimentiAccredito.setCodAnomalia(anomalia.name());
		movimentiAccredito.setNumElaborazioni(safeIncrementShort(movimentiAccredito.getNumElaborazioni()));
		movimentiAccredito.setTsAggiornamento(now());
		movimentiAccredito.setOpAggiornamento(DB_USER);
		em.merge(movimentiAccredito);
		logger.info("Movimento di accredito con id %1 non riconciliato".replace("%1", String.valueOf(movimentiAccredito.getId())));
	}
	
	private void movimentoDiAccreditoNonRiconciliatoPerSuperamentoNMaxElaborazioni(MovimentiAccredito movimentiAccredito) {
		movimentiAccredito.setFlagRiconciliazione((short) 2);
		movimentiAccredito.setCodAnomalia(Anomalia.E_SUPERAMENTO_LIMITE_ELABORAZIONI.name());
		movimentiAccredito.setTsAggiornamento(now());
		movimentiAccredito.setOpAggiornamento(DB_USER);
		em.merge(movimentiAccredito);
		logger.info("Movimento di accredito con id %1 non riconciliato causa superamento numero massimo di tentativi di riconciliazione".replace("%1", String.valueOf(movimentiAccredito.getId())));
	}
	
	private void movimentoDiAccreditoConRiconciliazioneInCorso(MovimentiAccredito movimentiAccredito) {
		movimentiAccredito.setFlagRiconciliazione((short) 3);
		movimentiAccredito.setNumElaborazioni(safeIncrementShort(movimentiAccredito.getNumElaborazioni()));
		movimentiAccredito.setTsAggiornamento(now());
		movimentiAccredito.setOpAggiornamento(DB_USER);
		em.merge(movimentiAccredito);
		logger.info("Movimento di accredito con id %1 con riconciliazione in corso".replace("%1", String.valueOf(movimentiAccredito.getId())));
	}
	
	private void incrementoElaborazioniSuMovimentoDiAccredito(MovimentiAccredito movimentiAccredito) {
		movimentiAccredito.setNumElaborazioni(safeIncrementShort(movimentiAccredito.getNumElaborazioni()));
		movimentiAccredito.setTsAggiornamento(now());
		movimentiAccredito.setOpAggiornamento(DB_USER);
		em.merge(movimentiAccredito);
		logger.info("Incremento numero elavorazioni su movimento di accredito con id %1".replace("%1", String.valueOf(movimentiAccredito.getId())));
	}
	
	
	/*
	 * Sezione privata 3 - Operazioni su Rendicontazione
	 */
	
	private void rendicontazioneNonRiconciliata(Rendicontazioni rendicontazioni, MovimentiAccredito movimentiAccredito) {
		rendicontazioni.setFlagElaborazione((short) 1);
		rendicontazioni.setStato("NON RICONCILIATO");
		rendicontazioni.setTsAggiornamento(now());
		rendicontazioni.setOpAggiornamento(DB_USER);
		em.persist(rendicontazioni);
		if (movimentiAccredito!=null) {
			logger.info("La rendicontazione con id %1, corrispondente al movimento di accredito con id %2, e' non riconciliata".replace("%1", String.valueOf(rendicontazioni.getId())).replace("%2", String.valueOf(movimentiAccredito.getId())));
		} else {
			logger.info("La rendicontazione con id %1 e' non riconciliata".replace("%1", String.valueOf(rendicontazioni.getId())));
		}
	}
	
	private void rendicontazioneParzialmenteRiconciliata(Rendicontazioni rendicontazioni, MovimentiAccredito movimentiAccredito) {
		rendicontazioni.setFlagElaborazione((short) 1);
		rendicontazioni.setStato("PARZ RICONCILIATO");
		rendicontazioni.setTsAggiornamento(now());
		rendicontazioni.setOpAggiornamento(DB_USER);
		em.persist(rendicontazioni);
		if (movimentiAccredito!=null) {
			logger.info("La rendicontazione con id %1, corrispondente al movimento di accredito con id %2, e' parzialmente riconciliata".replace("%1", String.valueOf(rendicontazioni.getId())).replace("%2", String.valueOf(movimentiAccredito.getId())));
		} else {
			logger.info("La rendicontazione con id %1 parzialmente riconciliata".replace("%1", String.valueOf(rendicontazioni.getId())));
		}
	}
	
	private void rendicontazioneRiconciliata(Rendicontazioni rendicontazioni, MovimentiAccredito movimentiAccredito) {
		rendicontazioni.setFlagElaborazione((short) 1);
		rendicontazioni.setStato("RICONCILIATO");
		rendicontazioni.setTsAggiornamento(now());
		rendicontazioni.setOpAggiornamento(DB_USER);
		em.persist(rendicontazioni);
		if (movimentiAccredito!=null) {
			logger.info("La rendicontazione con id %1, corrispondente al movimento di accredito con id %2, e' stata riconciliata".replace("%1", String.valueOf(rendicontazioni.getId())).replace("%2", String.valueOf(movimentiAccredito.getId())));
		} else {
			logger.info("La rendicontazione con id %1  e' stata riconciliata".replace("%1", String.valueOf(rendicontazioni.getId())));
		}
	}

	
	/*
	 * Sezione privata 4 - Operazioni su EsitiNdp
	 */
	
	private void esitoNonRiconciliato(EsitiNdp esitoNdp, Anomalia anomalia, Rendicontazioni rendicontazioni, MovimentiAccredito movimentiAccredito) {
		esitoNdp.setFlagRiconciliazione((short) 2);
		esitoNdp.setCodAnomalia(anomalia.name());
		esitoNdp.setTsAggiornamento(now());
		esitoNdp.setOpAggiornamento(DB_USER);
		em.persist(esitoNdp);
		if (movimentiAccredito!=null) {
			logger.info("L'esito con id %1 che fa riferimento alla rendicontazione con id %2 corrispondente al movimento di accredito con id %3 e' non riconciliato".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(rendicontazioni.getId())).replace("%3", String.valueOf(movimentiAccredito.getId())));
		} else {
			logger.info("L'esito con id %1 che fa riferimento alla rendicontazione con id %2 e' non riconciliato".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(rendicontazioni.getId())));
		}
	}
	
	private void esitoRiconciliato(EsitiNdp esitoNdp, Rendicontazioni rendicontazioni, MovimentiAccredito movimentiAccredito) {
		esitoNdp.setFlagRiconciliazione((short) 1);
		esitoNdp.setTsAggiornamento(now());
		esitoNdp.setOpAggiornamento(DB_USER);
		em.persist(esitoNdp);
		if (movimentiAccredito!=null) {
			logger.info("L'esito con id %1 che fa riferimento alla rendicontazione con id %2 corrispondente al movimento di accredito con id %3 e' stato riconciliato".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(rendicontazioni.getId())).replace("%3", String.valueOf(movimentiAccredito.getId())));
		} else {
			logger.info("L'esito con id %1 che fa riferimento alla rendicontazione con id %2  e' stato riconciliato".replace("%1", String.valueOf(esitoNdp.getId())).replace("%2", String.valueOf(rendicontazioni.getId())));			
		}
	}
	
	
	/*
	 * Sezione privata 5 - Operazioni su Pagamento
	 */
	
	private int pagamentiEseguiti(MovimentiAccredito movimentiAccredito, DistintePagamento distinta, String flagIncasso, EsitiNdp esitoNdp ) {
		
		int recordsAffected = -1;
		
		if (esitoNdp==null) {
		
			// Riconciliazione diretta da Movimenti Accredito 
		
			if (flagIncasso.equals(EnumFlagIncasso.ACCREDITATO_SU_CONTO_TECNICO.getChiave())) {

				// se l'accredito ï¿½ su conto tecnico.. non si aggiornano i dati incasso sul pagamento..
				// che sono relativi all'incasso finale (in tal caso dovrebbero essere popolati dall'esito bonifico riaccredito
				recordsAffected = em.createNamedQuery("updFlagIncassoByDistinta")
					.setParameter("distintePagamento", distinta)
					.setParameter("flagIncasso", flagIncasso)
					.setParameter("tsAggiornamento", now())
					.setParameter("opAggiornamento", DB_USER)						
					.executeUpdate();
			
			}  else {

				recordsAffected = em.createNamedQuery("updDatiIncassoByDistinta")
						.setParameter("distintePagamento", distinta)
					    .setParameter("dataAccreditoEnte",movimentiAccredito.getDataContabile())
					    .setParameter("dataRegolamento",movimentiAccredito.getDataContabile())
					    .setParameter("identificativoFlusso",null)
					    .setParameter("trn",movimentiAccredito.getTrn())
						.setParameter("bicBancaRiversamento",null)
					    .setParameter("dataEsecuzione",null)
					    .setParameter("codRendicontazioneIncasso",null)
					    .setParameter("mittenteRendicontazioneIncasso",movimentiAccredito.getIdBancaOrdinante())
					    .setParameter("totaleTransazioneIncasso",movimentiAccredito.getImporto())
						.setParameter("flagIncasso", flagIncasso)
						.setParameter("tsAggiornamento", now())
						.setParameter("opAggiornamento", DB_USER)						
						.executeUpdate();
				
			}
			
		}	else {
			
			Rendicontazioni rendicontazioni = esitoNdp.getRendicontazioni();
			String mittenteRendicontazioni = (String)em.createNamedQuery("getMittenteRendicontazioni").setParameter("idRendicontazioni", rendicontazioni.getId()).getSingleResult();
			
			if (flagIncasso.equals(EnumFlagIncasso.ACCREDITATO_SU_CONTO_TECNICO.getChiave())) {
				
				// se l'accredito ï¿½ su conto tecnico.. non si aggiornano i dati incasso sul pagamento..
				// che sono relativi all'incasso finale (in tal caso dovrebbero essere popolati dall'esito bonifico riaccredito
				recordsAffected = em.createNamedQuery("updFlagIncassoByDistinta")
					.setParameter("distintePagamento", distinta)
					.setParameter("flagIncasso", flagIncasso)
					.setParameter("tsAggiornamento", now())
					.setParameter("opAggiornamento", DB_USER)						
					.executeUpdate();

			} else {
			
				recordsAffected = em.createNamedQuery("updDatiIncassoByDistinta")
						.setParameter("distintePagamento", distinta)
					    .setParameter("dataAccreditoEnte",rendicontazioni.getDataRegolamento())
					    .setParameter("dataRegolamento",rendicontazioni.getDataRegolamento())
					    .setParameter("identificativoFlusso",rendicontazioni.getIdFlusso())
					    .setParameter("trn",rendicontazioni.getIdRegolamento())
					    .setParameter("bicBancaRiversamento",rendicontazioni.getBicBancaRiversamento())
					    .setParameter("dataEsecuzione",esitoNdp.getDataPagamento())
					    .setParameter("codRendicontazioneIncasso","FR")
					    .setParameter("mittenteRendicontazioneIncasso",mittenteRendicontazioni)
					    .setParameter("totaleTransazioneIncasso",rendicontazioni.getImporto())
						.setParameter("flagIncasso", flagIncasso)
						.setParameter("tsAggiornamento", now())
						.setParameter("opAggiornamento", DB_USER)						
						.executeUpdate();
			
			}
			
		}
		
	
		if (movimentiAccredito!=null) {
			logger.info("Aggiornato il flag incasso per %1 pagamenti corrispondenti al movimento di accredito con id %2".replace("%1", String.valueOf(recordsAffected)).replace("%2", String.valueOf(movimentiAccredito.getId())));
		} else {
			logger.info("Aggiornato il flag incasso per %1 pagamenti".replace("%1", String.valueOf(recordsAffected)));
		}
		
		return recordsAffected;
	}
	
	
	/*
	 *	Sezione privata 6 - Utilitï¿½ 
	 */
	
	// Adesso
	private Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	// Acquisizione BigDecimal da stringa
	private BigDecimal parseBigDecimal(String formattedDigits, char decimalSeparator) {
		if (formattedDigits == null) return null;
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
		formatSymbols.setDecimalSeparator(decimalSeparator);
		DecimalFormat importiFormat = new DecimalFormat("############,##",formatSymbols);
		importiFormat.setParseBigDecimal(true);
		try {
			return (BigDecimal) importiFormat.parse(formattedDigits);
		} catch (ParseException e) {
			return null;
		}
	}
	
	// Incrementa uno short rimanendo al massimo ammesso dal tipo quando viene raggiunto
	private short safeIncrementShort(short s) {
		return (short) (s == Short.MAX_VALUE ? s : s+1);
	}
	
	// Controllo check digits
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String checkDigit(final String reference) {
		if (reference == null) /* avoid NPE */ return null;
		StringBuffer buffer = new StringBuffer();
		for (int j=0; j<reference.length(); j++) {
			char c = Character.toUpperCase(reference.charAt(j));
			int pos = ALPHABET.indexOf(c);
			if (pos == -1) /* not found: invalid reference provided */ return null;
			buffer.append(pos);
		}
		buffer.append("271500");

		BigInteger bufferToBigInteger = new BigInteger(buffer.toString());
		int remainder = bufferToBigInteger.remainder(BigInteger.valueOf(97)).intValue();
		int value = 98 - remainder;
		return value < 10 ? "0"+value : String.valueOf(value);
	}


}
