/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Remote(ObjectCommandExecutor.class)
public class InserimentoPagamentoNotifica implements ObjectCommandExecutor {

	private enum EnumTipoModalitaPagamentoForNotifica {
		
		DLGRID("Delega RID"),
		RVCRID("Revoca RID"),
		RIDOLN("RID On-Line"),	
		CRCARD("Carta di Credito"),
		BNOBT("Bonifico On-Line"),
		ATMBT("Pagamento ATM"),
		HBKBT("Home Banking BT"),	
		BNPPRD("Bonifico Bancario"),
		BLFFRC("Bollettino Freccia"),
		CEUPAY("Canale EUPAY"), 
		CTSERV("Canale T-Serve"),
		CBILL("CBILL"),
		MYBANK("MyBank"),
		BBT("Bonifico Bancario di Tesoreria"),
		BPT("Bollettino Postale"),
		PSP("Pagamento presso PSP"),
	    TTMSST("Circuito Punto SI"),
	    FESPPI("Bollettino Postale on line"),
		UFFPST("Ufficio Postale"),
		PSTSPA("Sportello Amico"),
		AD("Addebito Diretto"),
		CP("Carta di Pagamento"),
		//Nuove modalita
		IBK ("Internet Banking"),
		PAGTEL ("Pagamento telematico generico"),
		TOTEM ("Pagamento da totem riscuotitore generico"),
		CBILUS ("Pagamento CBILL stesso istituto");		
		
		
		String descrizione;

		private EnumTipoModalitaPagamentoForNotifica(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getDescrizione() {
			return descrizione;
		}
		
	}	

	
	
	/*** Resources (CMT) ***/
	@Resource
	private EJBContext ctx;
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta) 
	private EntityManager manager;	

	private final Log logger = LogFactory.getLog(this.getClass());


	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
			return manager;		
	}




	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object data) {

		try {
//			tx.begin();
			//POI INVECE SI DOVRA CAMBIARE LO STATO SULLA TABELLA PAGAMENTI
			Object[] object = (Object[]) data;

		

			logger.info(" =========== NOTIFICA PAGAMENTO ================ ");
			NotifichePagamenti notifichePaga = new NotifichePagamenti();
			String idNotificaPag = GeneratoreIdUnivoci.GetCurrent().generaId(null);
			logger.info(" =========== NOTIFICA PAGAMENTO , ID = " + idNotificaPag);
			notifichePaga.setIdNotifica(idNotificaPag);
			//altri campi
			Integer idPaga = new Integer( ((Number) object[0]).intValue() );
			notifichePaga.setIdPagamento( String.valueOf(idPaga.intValue()) );
			//non setto il TsDecorrenza perchè non mi serve nel messaggio RFC
			notifichePaga.setTsDecorrenza((Timestamp) object[1]);
			//setto invece il data scadenza
			//test oracle
			if ((object[2]) instanceof java.sql.Date) {
				notifichePaga.setDtScadenza((java.sql.Date) object[2]);
			} else if ((object[2]) instanceof Timestamp) {
				notifichePaga.setDtScadenza( new java.sql.Date(((Timestamp) object[2]).getTime()) );
			}
						
			
			BigDecimal impTot = (BigDecimal) object[3];
			impTot.setScale(2);
			notifichePaga.setImTotale(impTot);
			notifichePaga.setCoPagante((String) object[6]);  
			
			//qui mettiamo la decode del idgCfgGatewayPaga
			
			//Integer idCfg = (Integer) object[21];   Sostitutito con Modalità bundle key.
			String bundleKeyModalita = (String) object[30];			
			notifichePaga.setTiposervizio(decodeModalitaPagamento(bundleKeyModalita));  //[SR 20/11/2014] Riorganizzata la funzione di decodifica.  
			//Questo dato dovrà essere preso in futuro dalla tabella CFG_MODALITA_PAGAMENTO ma ora troppo rischio.						

			notifichePaga.setMsgid((String) object[8]);
			
			//setto le date... correttamente..
			notifichePaga.setTmbspedizione((Timestamp) object[9]);			
			notifichePaga.setTsOperazione((Timestamp) object[10]);					
			BigDecimal imp = (BigDecimal) object[11];
			notifichePaga.setTotimportipositivi(imp);

			notifichePaga.setTsOrdine((Timestamp) object[22]);
			//		JLTPAGA.TI_DEBITO = "Pendenza" o "Spontaneo"
			//		JLTPAGA.TIPOSPONTANEO = "Multa” in caso di “Spontaneo”
			//notifichePaga.setTiDebito(object[7].toString());
			//FIX ERRORE PROD SETT 2013
			//se TRIBUTIENTI.FL_PREDETERM = “Y” (predeterminato)     à RFC145 RiferimentoDebito@TipoDebito=”Pendenza”
			//se TRIBUTIENTI.FL_PREDETERM = “N” (spontaneo)        à RFC145 RiferimentoDebito@TipoDebito=”Spontaneo”
			
			String flPredeterminato= (object[7]!=null ? object[7].toString() : null);  // In alcuni DB (e.g. MySql) esce Char, in alcuni String.
			
			notifichePaga.setTiDebito("Y".equals(flPredeterminato) ? "Pendenza" : "Spontaneo");
			
			String idPend = ((String) object[12])!=null ? ((String) object[12]) : "null";
			notifichePaga.setIdPendenza(idPend);
			
			//#1394
			if (object[24]!=null) {
				notifichePaga.setDataAccreditoContoTecnico(new java.sql.Date(((Date)object[24]).getTime()));
			}
			if (object[25]!=null) {
				notifichePaga.setDataAccreditoEnte(new java.sql.Date(((Date)object[25]).getTime()));
			}			

			notifichePaga.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			notifichePaga.setOpInserimento("NotificheDbManager");
			
			String decodeStato = "";				
			String faseNotifica = (String) object[5];
			String faseNotificaPerPagamento = "";
			logger.info("Fase Notifica da controllare = " +	faseNotifica );
			String dataNotifica = "";
			//Integer idCfgPagamenti = (Integer) object[21];
			String flagIncasso = object[26]!=null ? object[26].toString() : null;
			String trtId = (String)  object[31];
			String trtSystem = (String)  object[32];
			
			// TODO con il salto delle notifiche non funziona piu!!!	
			// devo forzare lo stato corrente dal chiamante inserendo un valore opportuno in
			// object[5]
			if (faseNotifica == null ) {				
				//in funzione della fase della notifica...
				//scrivo lo statoNotifica corrisponde...
				//notifica fase 1 = notificaPagamento stato 'creato per eseguito'
				notifichePaga.setStatoNotifica(StatoEnum.NOTIFICHE_DA_CREARE);
				faseNotificaPerPagamento = StatoEnum.NOTIFICHE_ESEGUITO;
				decodeStato = StatoEnum.NOTIFICHE_ESEGUITO;
				dataNotifica = " , notificaEseguito = :dataNotifica ";
				

			} else if ("ESEGUITO".equals(faseNotifica)) {				
				//notifica fase 2 = notificaPagamento stato 'creato per regolato'				
				notifichePaga.setStatoNotifica(StatoEnum.NOTIFICHE_DA_CREARE);
				faseNotificaPerPagamento = StatoEnum.NOTIFICHE_REGOLATO;
				decodeStato = StatoEnum.NOTIFICHE_REGOLATO;
				dataNotifica = " , notificaRegolato = :dataNotifica ";	
				
			} else if ("REGOLATO".equals(faseNotifica)) {
				
				//notifica fase 3 = notificaPagamento stato 'creato per incasso'
				notifichePaga.setStatoNotifica(StatoEnum.NOTIFICHE_DA_CREARE);
				faseNotificaPerPagamento = StatoEnum.NOTIFICHE_NOTIFICATO;
				decodeStato = StatoEnum.NOTIFICHE_INCASSO;
				dataNotifica = " , notificaIncasso = :dataNotifica ";
				
			} 			
			notifichePaga.setStatoPagamento(decodeStato);
			
//			da attivare dopo la alter table
			notifichePaga.setIdEnte((String) object[13]);
			notifichePaga.setCdTrbEnte((String) object[14]);
			notifichePaga.setIdSystem(new String((String) object[15]));
			notifichePaga.setCdEnte(new String((String) object[16]));
			String idPendEnte = ((String) object[18])!=null ? ((String) object[18]) : "null";
			notifichePaga.setIdPendenzaente(idPendEnte);
			//valorizzo la causale solo se FL_INIZIATIVE = Y
			//gestisco anche il caso in cui JLTENTR.FL_PREDETERMINATO == null (per errore del censimento)
			//in caso JLTENTR.FL_PREDETERMINATO == null allora DE_CAUSALE = 'NO-CAUSALE'
			if ((flPredeterminato == null || "Y".equals(flPredeterminato)) && !"BOLLO_AUTO".equals(notifichePaga.getCdTrbEnte())) {
				notifichePaga.setDeCausale("NO-CAUSALE");
			} else {
				notifichePaga.setDeCausale(((String) object[23]));
			}
//			notifichePaga.setDeCausale( (flPredeterminato!=null && flPredeterminato.equals("N") && notifiche)  ? ((String) object[23]) : "NO-CAUSALE" );
//			notifichePaga.setDeCausale(new String((String) object[23]));

			String tipospontaneo = (String) object[19]!=null ? (String) object[19] : "PEND";
			//JLTPAGA.TIPOSPONTANEO = Multa in caso di Spontaneo
			notifichePaga.setTipospontaneo(tipospontaneo);

			//salvo anche l'id della condizione di pagamento che è stata pagata
			//serve all'ente per inviare poi l'update status
			notifichePaga.setIdPagamentoEnte(new String((String) object[20]));
			//salvo anche l'eventuale tratta
			notifichePaga.setTrtReceiverId(trtId);
			notifichePaga.setTrtReceiverSys(trtSystem);

			logData(object, imp, faseNotificaPerPagamento);
			//persist
			manager.persist(notifichePaga);

			//aggiornamento stato tabella pagamenti
			Query queryUpdatePaga = manager.createQuery ("Update Pagamenti paga " +
					" set paga.statoNotifica = :statoNotifica  " +
					dataNotifica + 
					" , tsAggiornamento = :tsAggiornamento " +
					" , opAggiornamento = :opAggiornamento " +
					" , version = version + 1 " +
					" WHERE paga.id = :idPagamento");
//			queryUpdatePaga.setParameter("statoNotifica", StatoEnum.NOTIFICHE_PAG_NOTIFICATO);
			queryUpdatePaga.setParameter("statoNotifica", faseNotificaPerPagamento);
			queryUpdatePaga.setParameter("dataNotifica", new Timestamp(System.currentTimeMillis()));
			queryUpdatePaga.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
			queryUpdatePaga.setParameter("opAggiornamento", "RFC145-"+faseNotificaPerPagamento);
			queryUpdatePaga.setParameter("idPagamento", Long.parseLong(notifichePaga.getIdPagamento()) );
			int pagaUpd = queryUpdatePaga.executeUpdate();
			logger.info(" cambio lo stato notifica del pagamento associato alla notificaPagamenti "
						+ "	con idNotifica = " + notifichePaga.getIdNotifica() + " con statoNotifica = " + faseNotificaPerPagamento
						+ " n. record modificati = " + pagaUpd);

			//logger.info(" =========== NOTIFICA PAGAMENTO PRE COMMIT ================ ");
			
			if (pagaUpd!=1) {
//				tx.rollback();
				ctx.setRollbackOnly();
				logger.error(" Notifiche... rollback");
			} else {
//				tx.commit();
				manager.flush();
				logger.info(" Notifiche... commit");
			}
			

		} catch (RuntimeException e) {
//			if (tx!=null && tx.isActive()) tx.rollback();
			e.printStackTrace();
			ctx.setRollbackOnly();  
			logger.error(this.getClass().getSimpleName() + " notifica pagamento NON INSERITA!!! (Runtime Ex = " + e.getMessage() );
			//non voglio più fare il throw perchè ogni pagamento(e notifica) deve seguire la sua strada
//			throw e;
		} catch (Exception e) {
//			if (tx!=null && tx.isActive()) tx.rollback();
			e.printStackTrace();
			ctx.setRollbackOnly();
			logger.error(this.getClass().getSimpleName() + " notifica pagamento NON INSERITA!!! (Exception Ex = " + e.getMessage() );
			//non voglio più fare il throw perchè ogni pagamento(e notifica) deve seguire la sua strada
			//throw new RuntimeException(e);
		} finally {
			logger.info(" notifica pagamento, closing connection ! ");
		} 


		return null;
	}

	
	private String decodeModalitaPagamento(String bundleKey) {
		
		EnumTipoModalitaPagamentoForNotifica mp=  EnumTipoModalitaPagamentoForNotifica.CRCARD; // Default meglio che niente
		
		try {
			 mp = EnumTipoModalitaPagamentoForNotifica.valueOf(bundleKey);
		} catch (Exception e) {
			logger.error("Bundle key modalità pagamento sconosciuta: "+bundleKey,e);
		}
			
		return mp.getDescrizione();
		
	}
	
	/**
	 * 
	 * @param object
	 * @param imp
	 * @param faseNotificaPerPagamento
	 */
	private void logData(Object[] object, BigDecimal imp,
			String faseNotificaPerPagamento) {
		logger.info("trovati pagamento = " +
				" idPAgamento = " + (Number) object[0] +
				" imTotale = " + (BigDecimal) object[3] +
				" totImporti = " + imp.toPlainString() +
				" tipoDebito = " + object[7] +
				" faseNotifica = " + (String) object[5] +
				" faseNotificaPerPagamento = " + faseNotificaPerPagamento + 
				" coPagante = " + (String) object[6] +					
				" ID_PAGAMENTO = " + (String) object[20] +
				" idCfgGatewayPagamento = " + (Number) object[21] +																
//					" distinta = " + (String) object[24] +					
				" idEnte = " + (String) object[13] +
				" cdTrbEnte = " + (String) object[14] +
				" cdEnte = " + (String) object[16] +
				" sil = " + (String) object[15] +
				" idPEndenza = " + (String) object[12] +
				" idPEndenzaEnte = " + (String) object[18] +
				" tsSpedizione = " + (Timestamp) object[9] +
				" tsOperazione = " + (Timestamp) object[10] +
				" tsDecorrenza = " + (Timestamp) object[1] +
				" tsOrdine = " + (Timestamp) object[22] +
				" dtScadenza = " + new java.sql.Date(((Date) object[2]).getTime()) +		
				" msgId = " + (String) object[8] +					
				" ragione sociale = " + (String) object[17] + 
				" causale = " + (String) object[23]);
	}



	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}


}
