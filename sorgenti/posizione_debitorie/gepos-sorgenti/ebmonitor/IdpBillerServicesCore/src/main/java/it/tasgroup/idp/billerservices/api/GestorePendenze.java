package it.tasgroup.idp.billerservices.api;

import it.tasgroup.idp.billerservices.api.impl.PendenzeComparator;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.enti.TributiEntiPK;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMap;
import it.tasgroup.idp.domain.iuv.IUVPosizEnteMapPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.VociPagamento;
import it.tasgroup.idp.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.iris2.pagamenti.Pagamenti;

import it.tasgroup.idp.avvisi.digitali.AvvisiDigitaliManager;
import it.tasgroup.idp.avvisi.digitali.exceptions.AvvisiDigitaliException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;

//TODO: gestire gli allegati.

public class GestorePendenze {
	
	private static final Log logger = LogFactory.getLog(GestorePendenze.class);

	public static enum EnumTipoAllineamento {
		INSERT,   
		DELETE,    
		REPLACE,
		UPDATE_STATUS,  // aggiornamento degli stati e basta
		SMART_REPLACE   // skip, replace o insert
	}
	
	
	public static enum  EnumEsitoAllineamentoPendenza {
		ERROR,
		INSERTED,
		REPLACED,
		UPDATED_STATUS,
		SKIPPED,
		DELETED
	}
	
	
	/**
	 * Validazione di una  pendenza. 
	 * throw eccezione di validazione in caso di problemi.
	 * 
	 * @param p
	 * @throws ValidationException
	 */
	public static void validaPendenza(Pendenze p, boolean checkDuplicazione, EntityManager em) throws ValidationException {

		//TODO: completare diagnosi.
		
		String idPendenzaEnte = p.getIdPendenzaente();
		String idPagamentoEnte = null;
		
		// Obbligatori
		checkNotNull("Identificativo Pendenza/Debito", p.getIdPendenzaente(), idPendenzaEnte, idPagamentoEnte);
		checkNotNull("Identificativo Creditore", p.getIdEnte(), idPendenzaEnte, idPagamentoEnte);
		checkNotNull("Identificativo Debito", p.getCdTrbEnte(), idPendenzaEnte, idPagamentoEnte);
		
		
		boolean cbillCompliant="true".equalsIgnoreCase(IrisProperties.getProperty("loader.cbill.compliant"));
		
		// Se si vuole essere CBILL compliant allora Identificativo debito deve essere al massimo di 18 cifre
		if (cbillCompliant) {
			if (p.getIdPendenzaente().length()>18) {
				throw new ValidationException(EnumReturnValues.ID_DEBITO_INCOMPATIBILE_CBILL, "L'identificativo del debito deve essere al massimo di 18 caratteri alfanumerici", null, idPendenzaEnte, idPagamentoEnte);
			}
		}
		
		
		// Una pendenza deve avere almeno una condizione
		checkNotEmpty("Condizione di pagamento", p.getCondizioniPagamento(), idPendenzaEnte, null);
		
		if (checkDuplicazione) {
			Pendenze existing = findExistingPendenzaByExample(p,em);
			if (existing!=null) {
				throw new ValidationException(EnumReturnValues.PENDENZA_DUPLICATA, "Il debito risulta gia' presente nella base dati", null, idPendenzaEnte, idPagamentoEnte);
			}
		}
		
		
		for (CondizioniPagamento c:p.getCondizioniPagamento() ) {
			 
			// Validazione della singola condizione
			
			idPagamentoEnte=c.getIdPagamento();
			
			// Date
			checkNotNull("Data Inizio Validita",c.getDtIniziovalidita(),idPendenzaEnte, idPagamentoEnte);
			checkNotNull("Data Fine Validita",c.getDtFinevalidita(),idPendenzaEnte, idPagamentoEnte);
			checkNotNull("Data Scadenza",c.getDtScadenza(),idPendenzaEnte, idPagamentoEnte);

			Date dtInizioValidita = DateUtils.truncate(c.getDtIniziovalidita(), Calendar.DATE);
			Date dtFineValidita = DateUtils.truncate(c.getDtFinevalidita(), Calendar.DATE);
			Date dtScadenza = DateUtils.truncate(c.getDtScadenza(), Calendar.DATE);
			
			if (dtInizioValidita.compareTo(dtFineValidita)>0) {				
				throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA, "La data inizio validita' deve essere antecedente o uguale alla data  fine validita'",null,idPendenzaEnte,idPagamentoEnte);
			} 
			
			if (dtScadenza.compareTo(dtFineValidita)>0 ) {
				throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA, "La data scadenza del pagamento deve essere antecedente o uguale alla data fine validita'",null,idPendenzaEnte,idPagamentoEnte);
			}
						
			if (c.getVociPagamento()!=null && !c.getVociPagamento().isEmpty()) {	

				// Il totale delle voci deve corrispondere al totale della condizione
				
				BigDecimal totVoci= new BigDecimal(0);
				for (VociPagamento v:c.getVociPagamento()) {
					totVoci=totVoci.add(v.getImVoce());
				}
				
				if (c.getImTotale().compareTo(totVoci)!=0) {
					throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA, "Il totale di una condizione di pagamento ["+c.getImTotale() +"] deve essere uguale alla somma degli importi delle voci [" + totVoci + "] che lo dettagliano ");
				}
				
			}
			
			// Duplicazione 
			// 
			// ATTENZIONE: qui la condizione e stata caricata con il valore specificato dall'ente (il campo idPagamento contiene idPagamentoEnte)
			// i controlli vanno quindi effettuati considerando anche il caso in cui lo IUV sia generato da piattaforma paytas-pa e non dall ente.
			CondizioniPagamento existing = findExistingCondizioneByExample(c, em);
						
			if (existing!=null && checkDuplicazione) {
				throw new ValidationException(EnumReturnValues.CONDIZIONE_PAGAMENTO_DUPLICATA, "Il pagamento risulta gia' presente nella base dati", null, idPendenzaEnte, idPagamentoEnte);
			}
			
			// Condizione pagamento esistente, associata per� ad altro debito.
			if (existing!=null && !existing.getPendenze().getIdPendenzaente().equals(idPendenzaEnte)) {
				throw new ValidationException(EnumReturnValues.CONDIZIONE_PAGAMENTO_DUPLICATA, "Il pagamento risulta gia' presente nella base dati, associato ad un diverso debito", null, idPendenzaEnte, idPagamentoEnte);
			}
			
			
			
		}
		
		// Una pendenza deve avere almeno un destinatario
		checkNotEmpty("Destinatario/Debitore", p.getDestinatariPendenze(),idPendenzaEnte,idPagamentoEnte);
				
		// Codici fiscali semanticamente corretti

	}

	

	/**
	 * Versione pendenze mono condizione. Ad uso dei caricamenti CSV
	 * 
	 * @param pendenza
	 * @param smartUpdate
	 * @param compareOnlyMainFields
	 * @param isVariazione
	 * @param em
	 * @throws LoaderException
	 */
//	public static EnumEsitoAllineamentoPendenza allineaPendenzaMonoCondizioneALL(CondizioniPagamento condizione, DatiPiazzaturaFlusso datiPiazzaturaFlusso, boolean smartUpdate, boolean compareOnlyMainFields, boolean isVariazione, boolean isPagatoIdp, EntityManager em) throws LoaderException {
//		
//		EnumEsitoAllineamentoPendenza returnValue;
//
//
//		PendenzeCartPK pk = new PendenzeCartPK();
//		pk.setE2emsgid(datiPiazzaturaFlusso.e2eMsgId);
//		pk.setSenderid(datiPiazzaturaFlusso.senderId);
//		pk.setSendersys(datiPiazzaturaFlusso.senderSys);
//		
//		PendenzeCart trasmissione = em.find(PendenzeCart.class, pk);
//		
//		// Preparo esito da persistere
//		EsitiPendenza esito = new EsitiPendenza();
//		esito.setPendenzeCart(trasmissione);
//		esito.setIdEsitoPendenza(GeneratoreIdUnivoci.GetCurrent().generaId("RND"));
//		esito.setIdPendenza(condizione.getPendenze().getIdPendenza());
//		esito.setIdPendenzaEnte(condizione.getPendenze().getIdPendenzaente());
//		esito.setStato("COMPLETO");
//
//		
//		if (trasmissione==null) {
//			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO,"Trasmissione non trovata. ");
//		}
//		
//		
//		try {
//		
//			returnValue = allineaPendenzaMonoCondizione(condizione, smartUpdate, compareOnlyMainFields, isVariazione, isPagatoIdp, em);
//			
//			esito.setEsitoPendenza("OK");
//			esito.setDescrizioneEsito(returnValue.toString());
//			em.persist(esito);
//			return returnValue;
//			
//		} catch (LoaderException e) {
//			esito.setEsitoPendenza("KO");
//			esito.setDescrizioneEsito(e.getDescription());
//			ErroriEsitiPendenza errore = new ErroriEsitiPendenza();
//			errore.setCodice(e.getErrorCode().toString());
//			errore.setDescrizioneErrore(e.getDescription());
//			errore.setIdErrore(GeneratoreIdUnivoci.GetCurrent().generaId("RND"));
//			errore.setIdEsitoPendenza(esito);
//			errore.setIdPendenza(esito.getIdPendenza());
//			errore.setIdPendenzaEnte(esito.getIdPendenzaEnte());
//			em.persist(esito);
//			em.persist(errore);
//			return EnumEsitoAllineamentoPendenza.ERROR;
//		} 
//		
//		
//	
//	}
	
	public static EnumEsitoAllineamentoPendenza  allineaPendenza(Pendenze pendenza,EnumTipoAllineamento tipoAllineamento, boolean compareMainFields, EntityManager em,IUVGeneratorLocal iuvGenerator) throws LoaderException {
		
		EnumEsitoAllineamentoPendenza result=null;
		
		TributiEntiPK tePk = new TributiEntiPK();
		tePk.setIdEnte(pendenza.getIdEnte());
		tePk.setCdTrbEnte(pendenza.getCdTrbEnte());
		TributiEnti te = em.find(TributiEnti.class, tePk);
		
		Pendenze oldPendenza = findExistingPendenzaByExample(pendenza, em); //
		PendenzeComparator pcomparator = new PendenzeComparator(compareMainFields);
//		CondizioniComparator ccomparator = new CondizioniComparator();
		

		switch (tipoAllineamento) {
			case DELETE: {
				if (oldPendenza==null) {
					throw new LoaderException(EnumReturnValues.PENDENZA_NON_TROVATA,"Pendenza (debito) da cancellare non presente");
				}
				deletePendenza(oldPendenza,te,em);
				return EnumEsitoAllineamentoPendenza.DELETED;
			}	
			case INSERT: {	
				// TODO: in caso di pendenze uguali potrei skippare.. aiuterebbe? 
				if (oldPendenza!=null) {
					throw new LoaderException(EnumReturnValues.PENDENZA_DUPLICATA,"Trovata Pendenza (debito) con stesso identificatore id="+pendenza.getIdPendenzaente());
				}
				patchPendenzaWithIUV(pendenza,te,em,iuvGenerator); // riscrittura dello IUV
				em.persist(pendenza); //
                                // TODO invocazione alimentazione a PRENOTA_AVVISI_DIGITALI per ogni condizione associata alla pendenza 
				try {
				  AvvisiDigitaliManager.getInstance().insertPendenza(pendenza, em);
				} catch (Exception ae) {
					logger.error("GestorePendenze::allineaPendenza()  error in  AvvisiDigitaliManager.getInstance().insertPendenza()",ae);
				}
				return EnumEsitoAllineamentoPendenza.INSERTED;
			}	
			case REPLACE: {
				
				if (oldPendenza==null) {
					throw new LoaderException(EnumReturnValues.PENDENZA_NON_TROVATA,"Pendenza (debito) da sostituire non presente");
				}
				invalidatePendenza(oldPendenza, true /*recursive*/); 
				em.flush();
				// devo patchare la pendenza ricevuta con lo IUV presente nella pendenza originaria				
				patchPendenzaExistingWithIUV(pendenza,te,iuvGenerator, em);
				em.persist(pendenza);
				try {
					  AvvisiDigitaliManager.getInstance().updatePendenza(pendenza, em);
				} catch (Exception ae) {
					logger.error("GestorePendenze::allineaPendenza()  error in  AvvisiDigitaliManager.getInstance().updatePendenza()",ae);
				}
				// NB: attenzione nel caso in cui si hanno piu condizioni nella stessa pendenza
				return EnumEsitoAllineamentoPendenza.REPLACED;
			}	
			case SMART_REPLACE:	{	
				if (oldPendenza==null) {
					patchPendenzaWithIUV(pendenza,te,em,iuvGenerator); // riscrittura dello IUV
					em.persist(pendenza);
                                        // TODO invocazione alimentazione a PRENOTA_AVVISI_DIGITALI per ogni condizione associata alla pendenza 
					try {
					  AvvisiDigitaliManager.getInstance().insertPendenza(pendenza, em);
					} catch (Exception ae) {
						logger.error("GestorePendenze::allineaPendenza()  error in  AvvisiDigitaliManager.getInstance().insertPendenza()",ae);
					}
					return EnumEsitoAllineamentoPendenza.INSERTED;
				} 
				
				if (pcomparator.compare(oldPendenza,pendenza, true /*includeStatus*/, true /*recursive*/,em,te)) { // TODO: guardare il compare delle condizioni 
					                                                                                         // e vedere se controllare anche id_pagamento
					// Se sono proprio uguali, anche nello stato, in tutto e per tutto, non faccio niente
					return EnumEsitoAllineamentoPendenza.SKIPPED;
				}	
				
				// Se sono diverse si verifica la possibilita di effettuare una replace
				boolean  hasPagamentiAssociati = hasPagamentiAssociati(oldPendenza,true /*includeInCorso*/,em);
				
				if (hasPagamentiAssociati) {
					throw new LoaderException(EnumReturnValues.PENDENZA_NON_MODIFICABILE, "La pendenza (debito) ha pagamenti associati, non puo' essere modificata");
				}
				
					
				invalidatePendenza(oldPendenza, true /*recursive*/);
				em.flush();
				
				patchPendenzaExistingWithIUV(pendenza,te,iuvGenerator, em);
				em.persist(pendenza);
				// TODO invocazione alimentazione a PRENOTA_AVVISI_DIGITALI per ogni condizione associata alla pendenza
				try {
					  AvvisiDigitaliManager.getInstance().updatePendenza(pendenza, em);
				} catch (Exception ae) {
					logger.error("GestorePendenze::allineaPendenza()  error in  AvvisiDigitaliManager.getInstance().updatePendenza()",ae);
				}										
				return EnumEsitoAllineamentoPendenza.REPLACED;
				
			}					
			case UPDATE_STATUS: {
				// NB: aggiorno la pendenza e le condizioni gia memorizzate su db 
				// quindi non devo applicare la patch su iuv - idPagamento
				if (oldPendenza==null) {
					throw new LoaderException(EnumReturnValues.PENDENZA_NON_TROVATA,"Pendenza (debito) da aggiornare non presente");
				}
				
				if (!pcomparator.compare(oldPendenza, pendenza, false /*includeStatus*/, true /*recursive*/,em,te)) {
					throw new LoaderException(EnumReturnValues.PENDENZA_NON_MODIFICABILE,"La Pendenza (debito) esistente � diversa da quella proposta come variazione, per cambiare lo stato occorre che i dati della pendenza target corrispondano a quelli della pendenza da aggiornare.");
				}
				
				boolean hasPagamentiAssociati = hasPagamentiAssociati(oldPendenza,false,em);   
				boolean hasPagamentiAssociatiIncludeInCorso = hasPagamentiAssociati(oldPendenza,true,em);  
							
				if (!hasPagamentiAssociati)  {
					if (hasPagamentiAssociatiIncludeInCorso) {
						throw new LoaderException(EnumReturnValues.PENDENZA_NON_MODIFICABILE,"Non si puo' aggiornare lo stato di una pendenza/debito in quanto ci sono pagamenti associati ancora in corso");
					} else {
						throw new LoaderException(EnumReturnValues.PENDENZA_NON_MODIFICABILE,"Non si puo' aggiornare lo stato di una pendenza/debito che non ha pagamenti associati sulla piattaforma");
					}					
				}
				
				// Aggiornamento dello stato della pendenza
				oldPendenza.setStPendenza(pendenza.getStPendenza());

				// Creo mappa condizioni della pendenzaOld per ricerca per idPagamento
				HashMap<String, CondizioniPagamento> oldPendenzaCondizioniMap = new HashMap<String, CondizioniPagamento>();
				for (CondizioniPagamento c:oldPendenza.getCondizioniPagamento())  {
					String idPagamento =PendenzeComparator.getIdPagamento(c.getIdPagamento(),  te,  em);
					oldPendenzaCondizioniMap.put(idPagamento, c);
				}
				
				// Ciclo sulle condizioni.
				for (CondizioniPagamento c:pendenza.getCondizioniPagamento()) {
					CondizioniPagamento oldCondizione = oldPendenzaCondizioniMap.get(c.getIdPagamento());  //TODO ????? NON HO CAPITO
					if (oldCondizione==null) {
						throw new LoaderException(EnumReturnValues.ERRORE_GENERICO,"Errore inatteso in Update Status"); // Non deve succedere, deve essere andato in errore prima 
					}
			
					oldCondizione.setDeCanalepag(c.getDeCanalepag());
					oldCondizione.setDeMezzopagamento(c.getDeMezzopagamento());
					oldCondizione.setDeNotepagamento(c.getDeNotepagamento());
					oldCondizione.setImPagamento(c.getImPagamento());
					oldCondizione.setDtPagamento(c.getDtPagamento());
					oldCondizione.setStPagamento(c.getStPagamento());
					if (c.getStPagamento().equals("P") || c.getStPagamento().equals("X")){
					   // invocazione alimentazione a PRENOTA_AVVISI_DIGITALI se la condizione e stata annullata o pagata
						try {
					      AvvisiDigitaliManager.getInstance().deleteCondizione(c, em);
						} catch (Exception ae) {
							logger.error("GestorePendenze::allineaPendenza()  error in  AvvisiDigitaliManager.getInstance().deleteCondizione()",ae);
					    }
					}
				}
				
				return EnumEsitoAllineamentoPendenza.UPDATED_STATUS;
			}	
		}	

		return result;
		
	}

	
	
	public static boolean hasPagamentiAssociati (Pendenze p, boolean includeInCorso, EntityManager em) {
		boolean result=false;
		for (CondizioniPagamento c:p.getCondizioniPagamento()) {
			if (hasPagamentiAssociati(c, includeInCorso, em)) {
				result=true;
				break;
			}
		}
		return result;
	}
	
	public static boolean hasPagamentiAssociati (CondizioniPagamento c, boolean includeInCorso, EntityManager em) {
		boolean result=false;
		
		Query q = em.createNamedQuery("findPagamentoByIdCondizioneAndListaStati");
		q.setParameter( "idCondizione"  , c.getIdCondizione());
		
		List<String> listaStati = new ArrayList<String>();
		listaStati.add("ES");
		if (includeInCorso) {
			listaStati.add("IC");
			listaStati.add("EF");
		}
		q.setParameter( "listaStati" , listaStati );
		
		List<Pagamenti> found = q.getResultList();
		
		if (found!=null && found.size()>0) {
			result=true;
		}
		
		return result;
	}
	

	
//	public static EnumEsitoAllineamentoPendenza  allineaPendenzaMonoCondizione(CondizioniPagamento condizione, boolean smartUpdate, boolean compareOnlyMainFields, boolean isVariazione, boolean isPagatoIdp, EntityManager em) throws LoaderException {
//
//		CondizioniPagamento oldCondizione  = findExistingCondizioneByExample(condizione, em);
//		
//		Pendenze pendenza = condizione.getPendenze();
//		
//		Pendenze oldPendenza = findExistingPendenzaByExample(pendenza, em);
//		PendenzeComparator pcomparator = new PendenzeComparator(compareOnlyMainFields);
//		CondizioniComparator ccomparator = new CondizioniComparator();
//
//
//		
//		// -----------------------------------------------------
//		// Se la condizione non esiste inserisco la nuova (End)
//		// -----------------------------------------------------		
//		if (oldPendenza==null) {
//			em.persist(pendenza);
//			return EnumEsitoAllineamentoPendenza.INSERTED ;
//		}
//	
//		// ---------------------------------------------------------
//		// Non posso allineare pendenze multicondizione (Exception)
//		// ---------------------------------------------------------				
//		if (oldPendenza.getCondizioniPagamento().size()>1) {
//			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO,"Impossibile allineare una pendenza multicondizione");
//		}
//
//		// -------------------------------------------------------------------------
//		// Se la pendenza esiste gi� 
//		// Proseguo solo se la logica � uno smartUpdate oppure se
//		// esplicitamente viene detto che si intende fare una variazione (Exception)
//		// --------------------------------------------------------------------------
//		if (!smartUpdate && !isVariazione) {
//			throw new LoaderException(EnumReturnValues.PENDENZA_DUPLICATA,"Debito/Pendenza gi� esistente nel database");
//		}		
//
//		// -------------------------------------------------------------------
//		// Verifica se la condizione � modificabile o meno.
//		// -------------------------------------------------------------------
//		// TODO: query che ricerca eventuali pagamenti IC, ES, EF....
//		boolean  hasPagamentiAssociati = false;
//		boolean  isPagata = false;
//
//		// -------------------------------------------------------------------
//		// Pagamenti in corso, non aggiornabile (Exception)
//		// -------------------------------------------------------------------
//		
//		if (hasPagamentiAssociati && !isPagata) {
//			throw new LoaderException(EnumReturnValues.CONDIZIONE_NON_MODIFICABILE, "La condizione di pagamento ha un pagamento associato in corso, non e' modificabile");
//		}
//
//		// ------------------------------------------------------------------------------------
//		// Se � pagata  aggiorniamo solo se � stato comunicata una "chiusura" (PagatoIdp=true)
//		// In tal caso  aggiorno lo stato di posizione e pendenza
//		// ------------------------------------------------------------------------------------
//
//		if (isPagata) {
//			
//			if ((condizione.getStPagamento().equals("P") || condizione.getStPagamento().equals("E"))&& isPagatoIdp) {
//				if ((pcomparator.compare(oldPendenza,pendenza,false,false))) {
//					// Aggiorno old pendenza e condizione.
//					oldPendenza.setStPendenza("C");
//					oldCondizione.setDeCanalepag(condizione.getDeCanalepag());
//					oldCondizione.setDeMezzopagamento(condizione.getDeMezzopagamento());
//					oldCondizione.setDeNotepagamento(condizione.getDeNotepagamento());
//					oldCondizione.setImPagamento(condizione.getImPagamento());
//					oldCondizione.setDtPagamento(condizione.getDtPagamento());
//					oldCondizione.setStPagamento(condizione.getStPagamento());
//					return EnumEsitoAllineamentoPendenza.UPDATED_STATUS;
//				} else {
//					throw new LoaderException(EnumReturnValues.PENDENZA_NON_MODIFICABILE,"I dati del debito della posizionee che si intende aggiornare non corrispondono ai dati del debito presenti nel database");
//				}
//			} else {
//				throw new LoaderException(EnumReturnValues.CONDIZIONE_NON_MODIFICABILE,"La condizione risulta pagata nella piattaforma e non e' modificabile");
//			}
//			
//		}
//		
//		// ---------------------------------------------------------------------------------------------
//		// Se la vecchia pendenza non � pagata procediamo con una replace, se i dati non sono cambiati
//		// ---------------------------------------------------------------------------------------------
//		if (!pcomparator.compare(pendenza,oldPendenza,false /*includeStatus*/, false /*recursive*/) || !(ccomparator.compare(condizione,oldCondizione,false /*includeStatus*/))) {
//			invalidatePendenza(oldPendenza, true);
//			em.persist(pendenza);
//			return EnumEsitoAllineamentoPendenza.REPLACED;
//		} else {
//			return EnumEsitoAllineamentoPendenza.SKIPPED;
//		}
//		
//	}

//	public static EnumEsitoAllineamentoPendenza  allineaPendenzaMonoCondizione(CondizioniPagamento condizione, boolean smartUpdate, boolean compareOnlyMainFields, boolean isVariazione, boolean isPagatoIdp, EntityManager em) throws LoaderException {
//
//		CondizioniPagamento oldCondizione  = findExistingCondizioneByExample(condizione, em);
//		
//		Pendenze pendenza = condizione.getPendenze();
//		
//		Pendenze oldPendenza = findExistingPendenzaByExample(pendenza, em);
//
//		PendenzeComparator pcomparator = new PendenzeComparator(compareOnlyMainFields);
//		CondizioniComparator ccomparator = new CondizioniComparator();
//		
//		// -----------------------------------------------------
//		// Se la condizione non esiste inserisco la nuova (End)
//		// -----------------------------------------------------		
//		if (oldPendenza==null) {
//			em.persist(pendenza);
//			return EnumEsitoAllineamentoPendenza.INSERTED ;
//		}
//	
//		// ---------------------------------------------------------
//		// Non posso allineare pendenze multicondizione (Exception)
//		// ---------------------------------------------------------				
//		if (oldPendenza.getCondizioniPagamento().size()>1) {
//			throw new LoaderException(EnumReturnValues.ERRORE_GENERICO,"Impossibile allineare una pendenza multicondizione");
//		}
//
//		// -------------------------------------------------------------------------
//		// Se la pendenza esiste gi� 
//		// Proseguo solo se la logica � uno smartUpdate oppure se
//		// esplicitamente viene detto che si intende fare una variazione (Exception)
//		// --------------------------------------------------------------------------
//		if (!smartUpdate && !isVariazione) {
//			throw new LoaderException(EnumReturnValues.PENDENZA_DUPLICATA,"Debito/Pendenza gi� esistente nel database");
//		}		
//
//		// -------------------------------------------------------------------
//		// Verifica se la condizione � modificabile o meno.
//		// -------------------------------------------------------------------
//		// TODO: query che ricerca eventuali pagamenti IC, ES, EF....
//		boolean  hasPagamentiAssociati = false;
//		boolean  isPagata = false;
//
//		// -------------------------------------------------------------------
//		// Pagamenti in corso, non aggiornabile (Exception)
//		// -------------------------------------------------------------------
//		
//		if (hasPagamentiAssociati && !isPagata) {
//			throw new LoaderException(EnumReturnValues.CONDIZIONE_NON_MODIFICABILE, "La condizione di pagamento ha un pagamento associato in corso, non e' modificabile");
//		}
//
//		// ------------------------------------------------------------------------------------
//		// Se � pagata  aggiorniamo solo se � stato comunicata una "chiusura" (PagatoIdp=true)
//		// In tal caso  aggiorno lo stato di posizione e pendenza
//		// ------------------------------------------------------------------------------------
//
//		if (isPagata) {
//			
//			if ((condizione.getStPagamento().equals("P") || condizione.getStPagamento().equals("E"))&& isPagatoIdp) {
//				if ((pcomparator.equals(oldPendenza,pendenza))) {
//					// Aggiorno old pendenza e condizione.
//					oldPendenza.setStPendenza("C");
//					oldCondizione.setDeCanalepag(condizione.getDeCanalepag());
//					oldCondizione.setDeMezzopagamento(condizione.getDeMezzopagamento());
//					oldCondizione.setDeNotepagamento(condizione.getDeNotepagamento());
//					oldCondizione.setImPagamento(condizione.getImPagamento());
//					oldCondizione.setDtPagamento(condizione.getDtPagamento());
//					oldCondizione.setStPagamento(condizione.getStPagamento());
//					return EnumEsitoAllineamentoPendenza.UPDATED_STATUS;
//				} else {
//					throw new LoaderException(EnumReturnValues.PENDENZA_NON_MODIFICABILE,"I dati del debito della posizionee che si intende aggiornare non corrispondono ai dati del debito presenti nel database");
//				}
//			} else {
//				throw new LoaderException(EnumReturnValues.CONDIZIONE_NON_MODIFICABILE,"La condizione risulta pagata nella piattaforma e non e' modificabile");
//			}
//			
//		}
//		
//		// ---------------------------------------------------------------------------------------------
//		// Se la vecchia pendenza non � pagata procediamo con una replace, se i dati non sono cambiati
//		// ---------------------------------------------------------------------------------------------
//		if (!pcomparator.equals(pendenza,oldPendenza) || !(ccomparator.equals(condizione,oldCondizione))) {
//			invalidatePendenza(oldPendenza, true);
//			em.persist(pendenza);
//			return EnumEsitoAllineamentoPendenza.REPLACED;
//		} else {
//			return EnumEsitoAllineamentoPendenza.SKIPPED;
//		}
//		
//	}	
	
	/**
	 *
	 * @param pendenza
	 * @param smartUpdate: se vale true la pendenza in ingresso � considerata essere esattamente la nuova pendenza
	 * 					   se vale false la pendenza in ingresso � considerata essere un contenitore di "Delta"
	 * @param compareOnlyMainFields: se vale true l'algoritmo di allineamento ritiene uguali due pendenze solo quando sono uguali i campi di business
	 *                               
	 * @param em
	 * @throws LoaderException 
	 */

//	public static void allineaPendenzaMultiCondizione(Pendenze pendenza, boolean smartUpdate, boolean compareOnlyMainFields, boolean isVariazione, EntityManager em) throws LoaderException {
//	
//		// Recupero la pendenza
//		Pendenze old=findExistingPendenzaByExample(pendenza, em); 
//		
//		// Se non esiste 
//		if (old==null) {
//			if ("A".equals(pendenza.getStRiga())) {
//				// Se viene richiesto di cancellare la pendenza e non la trovo, vado in errore.
//				throw new LoaderException(EnumReturnValues.PENDENZA_NON_TROVATA,"Richiesta cancellazione di pendenza [idDebito="+pendenza.getIdPendenzaente()+"], non presente nel sistema");
//			} else {
//				// In tutti gli altri casi la inserisco.
//				em.persist(pendenza);
//			}	
//			return;
//		}
//		
//		// Pendenza gi� esistente.
//		if (!smartUpdate && !isVariazione) {
//			throw new LoaderException(EnumReturnValues.PENDENZA_DUPLICATA,"Debito/Pendenza gi� esistente nel database");
//		}
//		
//		
//		//  Variabili utili per l'elaborazione successiva:
//		Pendenze newPendenza=pendenza;
//		Set<CondizioniPagamento> newCondizioni=pendenza.getCondizioniPagamento();
//		
//
//		// Per prima cosa mi chiedo se la pendenza � modificabile o meno.
//		// TODO: query che ricerca eventuali pagamenti IC, ES, EF....
//		boolean  isPendenzaModificabile = true;
//
//		
//		// Verifico se devo "Cancellare" la pendenza.
//		if ("A".equals(pendenza.getStRiga())) {
//			if (isPendenzaModificabile) {
//				deletePendenza(old);
//				return; 
//			} else {
//				throw new LoaderException(EnumReturnValues.PENDENZA_NON_CANCELLABILE,"Pendenza non cancellabile in quanto ha pagamenti associati");
//			}
//		}
//		
//		// Controllo se i dati di testata sono uguali.
//		boolean datiPendenzaUguali = new PendenzeComparator(compareOnlyMainFields).equals(old, pendenza);
//		
//		// Se sono uguali continuo a lavorare con la pendenza vecchia, altrimenti utilizzo la nuova.
//		Pendenze target;  //La nuova pendenza che vado a comporre
//		if (datiPendenzaUguali) {
//			target = old; 
//		} else {
//			invalidatePendenza(old,false);  //Invalido (al primo livello, destinatari compresi) la pendenza
//			em.flush();
//			
//			target = pendenza;
//			
//			target.setCondizioniPagamento(old.getCondizioniPagamento());  // Sposto le condizioni dalla old alla target
//			for (CondizioniPagamento c:old.getCondizioniPagamento()) {
//				c.setPendenze(target);
//				if (c.getVociPagamento()!=null) {
//					for (VociPagamento v:c.getVociPagamento()) {
//						c.setPendenze(target);
//					}
//				}
//			}
//			old.setCondizioniPagamento(new LinkedHashSet<CondizioniPagamento>());
//						
//		}
//
//		if (!datiPendenzaUguali) {
//			em.persist(target);
//		}  
//		
//		// Analisi delle condizioni di pagamento
//		// In teoria se non ci sono pagamenti annullati quello che sto facendo � troppo complicato (basterebbe una replace totale..)
//		HashMap<String, CondizioniPagamento> oldCondizioniMap = new HashMap<String, CondizioniPagamento>();
//		for (CondizioniPagamento c:target.getCondizioniPagamento()) {
//			oldCondizioniMap.put(c.getIdPagamento(), c);
//		}
//		
//		for (CondizioniPagamento c1:newCondizioni) {
//			CondizioniPagamento c2 = oldCondizioniMap.get(c1.getIdPagamento());
//			
//			if (c2==null) {
//				// Condizione nuova da inserire
//				target.getCondizioniPagamento().add(c1);
//				c1.setPendenze(target);
//				em.persist(c1);
//				continue;
//			}
//			
//			boolean condizioniUguali = (new CondizioniComparator()).equals(c1, c2);
//			if (condizioniUguali) {
//				// non si fa niente
//				oldCondizioniMap.remove(c1.getIdPagamento());
//				continue;
//			}
//			
//			invalidateCondizione(c2);
//			em.flush();
//			target.getCondizioniPagamento().add(c1);
//			c1.setPendenze(target);
//			em.persist(c1);
//			oldCondizioniMap.remove(c1.getIdPagamento());
//			
//		}
//		
//		// nella Map restano condizioni vecchie non pi� usate, le cancello:
//		for (CondizioniPagamento c:oldCondizioniMap.values()) {
//			deleteCondizione(c);
//		}
//			
//	}
	
	
	
//	public static void savePendenza(Pendenze pendenza, boolean beSmart, EntityManager em) {
//		
//		// Per prima cosa recupero l'eventuale pendenza valida con stessa chiave semantica
//		Pendenze existing = findExistingPendenzaByExample(pendenza, em);
//		
//		if (existing==null) {
//			//TODO: in caso di soppressione dei check di duplicazione in diagnosi.. 
//			//qui occorre fare il controllo.. e intercettare le chiavi duplicate, prima di inserire
//			em.persist(pendenza);
//			return;
//		}
//		
//		// --------------------------------
//		// Caso (existing!=null)
//		// --------------------------------		
//		Set<CondizioniPagamento> existingConds = existing.getCondizioniPagamento();
//		Set<CondizioniPagamento> newConds = existing.getCondizioniPagamento();	
//		HashMap<String,CondizioniPagamento>  existingCondsMap = new HashMap<String, CondizioniPagamento>();
//		boolean hasPagamentiAssociati=false;
//		for (CondizioniPagamento c:existingConds) {
//			existingCondsMap.put(c.getIdPagamento(),c);
//			if (hasPagamentoAssociato(c,em)) {
//				hasPagamentiAssociati=true;  // ne basta uno per metterci nei guai.
//			}
//			
//		}
//		
//		if (!hasPagamentiAssociati) {
//			invalidatePendenza(existing,true);
//			em.flush();
//			em.persist(pendenza);
//		}
//		
//		// Io vorrei fare sempre una "replace", sostituendo quello che c'era prima con quello che arriva 
//		// in ingresso. L'unico problema che vedo � "sempre" se una condizione di pagamento � stata 
//		// pagata sulla piattaforma. In quel caso ci sono dei problemi tecnici (id fisici di collegamento) 
//		// che ci impediscono di eliminarla. Di fatto quella posizione va "salvaguardata" in qualche modo.
//		// non parrebbe troppo giusto completamente cambiarla.. ma forse � la soluzione pi� ovvia
//
//		//Condizione con pagamento 
//		
//		
//	}
	
	private static void checkNotNull(String nomeCampo, String campo, String idPendenzaEnte, String idPagamentoEnte) throws ValidationException {
		if(campo == null || nomeCampo.equals("")) {
			throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"Campo obbligatorio assente: '"+nomeCampo+"'",null,idPendenzaEnte,idPagamentoEnte);
		}
	}

	private static void checkNotNull(String nomeCampo, Date campo, String idPendenzaEnte, String idPagamentoEnte) throws ValidationException {
		if(campo == null ) {
			throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"Campo obbligatorio assente: '"+nomeCampo+"'",null,idPendenzaEnte,idPagamentoEnte);
		}
	}

	
	private static void checkNotEmpty(String nomeCampo, Set set, String idPendenzaEnte, String idPagamentoEnte) throws ValidationException {
		if(set == null || set.isEmpty()) {
			throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"Elemento obbligatorio assente: deve esistere almeno un'occorrenza di '"+nomeCampo+"'",null,idPendenzaEnte,idPagamentoEnte);
		}
	}
	
	
	private static Pendenze findExistingPendenzaByExample(Pendenze p, EntityManager em) {
		String queryFindExisting = "from Pendenze where idPendenzaente=:idPendenzaente and idEnte=:idEnte and cdTrbEnte=:cdTrbEnte and stRiga='V' and tsAnnullamento is null";
		Query q = em.createQuery(queryFindExisting);
		q.setParameter("idPendenzaente", p.getIdPendenzaente());
		q.setParameter("idEnte", p.getIdEnte());
		q.setParameter("cdTrbEnte", p.getCdTrbEnte());
		List<Pendenze> existing = (List<Pendenze>) q.getResultList();
		if (existing.size()>0) {
			// Per costruzione (dagli indici) deve esisterne una solo al pi�
			return existing.get(0);
		}  else {
			return null;
		}	
	}
	
	private static CondizioniPagamento findExistingCondizioneByExample(CondizioniPagamento c, EntityManager em) {
		
		// controllo se 
		TributiEntiPK tePk = new TributiEntiPK();
		tePk.setIdEnte(c.getIdEnte());
		tePk.setCdTrbEnte(c.getCdTrbEnte());
		TributiEnti te = em.find(TributiEnti.class, tePk);
		String idPagamento = c.getIdPagamento();
		if (!te.getFlNdpIuvGenerato().equals("0")) {
		    Query q1=em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIdPagamento");
		    q1.setParameter("idPagamento", c.getIdPagamento());
		    q1.setParameter("idEnte", c.getIdEnte());
		    List<IUVPosizEnteMap> lIuvMapList = ( List<IUVPosizEnteMap>) q1.getResultList();
		    if (lIuvMapList.size()>0) {		    	
		    	idPagamento=lIuvMapList.get(0).getId().getIuv();
		    } else {
		    	return null;
		    }
		}
		String queryFindExisting = "from CondizioniPagamento where idPagamento=:idPagamento and idEnte=:idEnte and stRiga='V' and tsAnnullamento is null";
		Query q = em.createQuery(queryFindExisting);
		q.setParameter("idPagamento",idPagamento);
		q.setParameter("idEnte", c.getIdEnte());

		List<CondizioniPagamento> existing = (List<CondizioniPagamento>) q.getResultList();
		if (existing.size()>0) {
			// Per costruzione (dagli indici) deve esisterne una solo al piu
			return existing.get(0);
		}  else {
			return null;
		}
		
	}

//	private static boolean hasPagamentoAssociato(CondizioniPagamento c, EntityManager em) {
//		String queryFindPagamenti= "from Pagamenti where idCondizione=:idCondizione and stPagamento in ('ES','IC','EF')";
//		Query q = em.createQuery(queryFindPagamenti);
//		q.setParameter("idCondizione", c.getIdCondizione());
//		List<Pagamenti> pags = q.getResultList();
//		if (pags.size()>0) {
//			return true;
//		} else {
//			return false;
//		}
//		
//	}
	
	private static void invalidatePendenza(Pendenze p, boolean recursive) {
		//
		// NB: nel caso di IUV generato da piattaforma non vado ad annullare ed inserire il contenuto 
		// della tabella IUVPosizEnteMap perche comunque riutilizzo lo IUV associato al idPagamentoEnte  
		// (funziona solo se numero condizioni rimane invariato)
		Timestamp now= new Timestamp(System.currentTimeMillis());
		p.setStRiga("A");
		p.setTsAnnullamento(now);
		
		for (DestinatariPendenze d:p.getDestinatariPendenze()) {
			d.setStRiga("A");
		}
		
		if (!recursive) return;
		// TODO va gestita la invalidazione della riga nella tabella degli IUV
		for (CondizioniPagamento c : p.getCondizioniPagamento()) {
			c.setStRiga("A");
			c.setTsAnnullamento(now);
		}
	}

	
//	private static void invalidateCondizione(CondizioniPagamento c) {
//		Timestamp now= new Timestamp(System.currentTimeMillis());
//		c.setStRiga("A");
//		c.setTsAnnullamento(now);
//		
//		for (VociPagamento v:c.getVociPagamento()) {
//			v.setStRiga("A");
//		}
//		
//	}

	
	private static void deletePendenza(Pendenze p,TributiEnti te,EntityManager em) {
		Timestamp now= new Timestamp(System.currentTimeMillis());
		p.setStRiga("A");
		p.setTsAnnullamento(now);
		for (CondizioniPagamento c : p.getCondizioniPagamento()) {
			deleteCondizione(c,te,em);
		}
		for (DestinatariPendenze d:p.getDestinatariPendenze()) {
			d.setStRiga("A");
		}
	}

	private static void deleteCondizione(CondizioniPagamento c,TributiEnti te,EntityManager em) {
		Timestamp now= new Timestamp(System.currentTimeMillis());
		// TODO va gestita anche la eventuale invalidazione dello IUV
		if (!te.getFlNdpIuvGenerato().equals("0")) {
			// la tabella condizione contiene nel campo idPagamento lo IUV 
			// devo invalidare il contenuto della tabella IUVPosizEnteMap
			Query q= em.createNamedQuery("AnnullaIUVPosizEnteMapByIdEnteAndIuv");
			q.setParameter("iuv",c.getIdPagamento());
			q.setParameter("idEnte", c.getIdEnte());
			q.setParameter("tsAggiornamento",new Timestamp(System.currentTimeMillis()));
			q.setParameter("opAggiornamento", "LOADER");
			int numRighe= q.executeUpdate();
			
		}
		c.setStRiga("A");
		c.setTsAnnullamento(now);
		// inserisco qui alimentazione riga PRENOTA_AVVISI_DIGITALI 
		try {
			AvvisiDigitaliManager.getInstance().deleteCondizione(c, em);
		} catch (Exception e) {
			logger.error("GestorePendenze::allineaPendenza()  error in  AvvisiDigitaliManager.getInstance().deleteCondizione()",e);
		}
		if (c.getVociPagamento()!=null) {
			for (VociPagamento v:c.getVociPagamento()) {
				v.setStRiga("A");
			}
		}
	}
    
	private static void patchPendenzaWithIUV(Pendenze p,TributiEnti te,EntityManager em, IUVGeneratorLocal iuvGenerator){
				
		if (!te.getFlNdpIuvGenerato().equals("0")) {
			int numOfCondizioni =p.getCondizioniPagamento().size();
			int numIuvDaGenerare = 0;
			HashMap<String,String> m = new HashMap<String,String>();
			for (CondizioniPagamento c: p.getCondizioniPagamento()) {
				if (c.getStRiga().equals("H")){
					// se hidden verifico se esiste un 
					Query q = em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIdPagamento");
					q.setParameter("idEnte", p.getIdEnte());
					q.setParameter("idPagamento", c.getIdPagamento());
					List<IUVPosizEnteMap> iuvmap = q.getResultList();
					if (iuvmap.size()>0) {
						c.setIdPagamento(iuvmap.get(0).getId().getIuv());
						// esiste gia una riga attiva di legame sulla iuvmap
					} else {
						numIuvDaGenerare++;
						m.put(c.getIdPagamento(), c.getIdPagamento());
					}
				} else {
					numIuvDaGenerare++;
					m.put(c.getIdPagamento(), c.getIdPagamento());
				}
			}
			//
			List<String> iuvList=iuvGenerator.IUVGeneratorByTributoEnte(te, numIuvDaGenerare);
			
			Iterator<String> iterIuv  = iuvList.iterator();
			Iterator<CondizioniPagamento> iterCondPag = p.getCondizioniPagamento().iterator();
			
			while (iterCondPag.hasNext()) {
				CondizioniPagamento condPag = iterCondPag.next();
				if (m.containsKey(condPag.getIdPagamento())){
					String iuv = iterIuv.next();
					String idPagamento = condPag.getIdPagamento();
					
					//
					condPag.setIdPagamento(iuv);
					// 
					IUVPosizEnteMap iuvMap = new IUVPosizEnteMap();
					IUVPosizEnteMapPK iuvPK = new IUVPosizEnteMapPK(p.getIdEnte(), iuv);
					iuvMap.setId(iuvPK);
					iuvMap.setCdTrbEnte(p.getCdTrbEnte());
					iuvMap.setIdPagamento(idPagamento);					
					iuvMap.setStRiga("V");
					iuvMap.setOpInserimento("LOADER");				
					iuvMap.setTsInserimento(new Timestamp(System.currentTimeMillis()));
					
					em.persist(iuvMap);
				}
			}
		}
		
	}
	
	private static void patchPendenzaExistingWithIUV(Pendenze p,TributiEnti te,IUVGeneratorLocal iuvGenerator, EntityManager em){
		
		if (!te.getFlNdpIuvGenerato().equals("0")) {			
			// patch-o ognuna delle CondizioniPagamento inserendo al posto dell idPagamento 
			// lo IUV generato presente nella tabella IUVPosizEnteMap
			Iterator<CondizioniPagamento> iterCondPag = p.getCondizioniPagamento().iterator();
			while (iterCondPag.hasNext()) {
				
				CondizioniPagamento condPag = iterCondPag.next();
				
				Query q1=em.createNamedQuery("IUVPosizEnteMapByIdEnteAndIdPagamento");
				q1.setParameter("idPagamento", condPag.getIdPagamento());
				q1.setParameter("idEnte", condPag.getIdEnte());
				List<IUVPosizEnteMap> lIuvMapList = ( List<IUVPosizEnteMap>) q1.getResultList();
				if (lIuvMapList.size()>0) {		    	
					condPag.setIdPagamento(lIuvMapList.get(0).getId().getIuv());
				} else {
				    // se non trovo nulla ... lo genero!!! 					
					List<String> iuvList=iuvGenerator.IUVGeneratorByTributoEnte(te, 1);
					String iuv = iuvList.get(0);
					
					IUVPosizEnteMap iuvMap = new IUVPosizEnteMap();
					IUVPosizEnteMapPK iuvPK = new IUVPosizEnteMapPK(p.getIdEnte(), iuv);
					iuvMap.setId(iuvPK);
					iuvMap.setCdTrbEnte(p.getCdTrbEnte());
					iuvMap.setIdPagamento(condPag.getIdPagamento());
					iuvMap.setStRiga("V");
					iuvMap.setOpInserimento("LOADER");				
					iuvMap.setTsInserimento(new Timestamp(System.currentTimeMillis()));
					
					condPag.setIdPagamento(iuv);
					em.persist(iuvMap);
				}
				//
								
				
			}
		}
		
	}
}
