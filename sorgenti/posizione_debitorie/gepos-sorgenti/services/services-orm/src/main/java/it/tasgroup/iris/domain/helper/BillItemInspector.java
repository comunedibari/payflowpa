/**
 * 
 */
package it.tasgroup.iris.domain.helper;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.IVocePagamento;
import it.tasgroup.services.util.enumeration.EnumStatoRiga;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.sql.Timestamp;
import java.util.*;

import javax.persistence.Transient;

/**
 * 
 * Classe di ausilio per indagare o decorare le proprietà della CondizionePagamento.
 * 
 * @author pazzik
 *
 */
public class BillItemInspector {
	
	private static final String HIDDEN_ROW_STATUS = EnumStatoRiga.HIDDEN.getChiave();
	
	private static final String VALID_ROW_STATUS = EnumStatoRiga.VALID.getChiave();
	
	
	/**
	 * Restituisce l'unico pagamento valido di tutti quelli associati ad una 
	 * CondizionePagamento.
	 * 
	 * @param cond
	 * @return l'unico pagamento valido di tutti quelli associati ad una 
	 * CondizionePagamento.
	 */
	@Transient
	public static Pagamenti getUniqueValidPayment(CondizionePagamento cond){
		
		Pagamenti validPayment = null;
		
		Set<Pagamenti> pagamenti = cond.getPagamenti();
		
		for(Pagamenti pagamento: pagamenti){
			
			if(pagamento.isValidPayment()){
				
				if(validPayment == null)
					validPayment = pagamento;
				else 
					throw new IllegalStateException("Solo un pagamento puo' essere valido per ogni condizione di pagamento");
			}
		}
		
		return validPayment;
	}
	
	/**
	 * Recupera l'ultimo pagamento oppure il pagamento eseguito
	 * 
	 * @return l'ultimo pagamento oppure il pagamento eseguito
	 */
	@Transient
	public static Pagamenti getMainPayment(CondizionePagamento cond){
		
		Pagamenti mainPayment = null;
		
		Timestamp maxTimestamp = null;
		
		Set<Pagamenti> pagamenti = cond.getPagamenti();
		
		for (Pagamenti pagamento : pagamenti) {
			
			// appena si trova un pagamento eseguito si esce dal ciclo
			if (pagamento.getStPagamento().equals(StatiPagamentiIris.ESEGUITO.getPagaMapping())) {
				mainPayment = pagamento;
				break;
			}
			
			// altrimenti si prende l'ultimo pagamento inserito
			if (maxTimestamp == null || pagamento.getTsInserimento().compareTo(maxTimestamp) > 0) {
				maxTimestamp = pagamento.getTsInserimento();
				mainPayment = pagamento;								
			}
			
		}
		
		return mainPayment;
		
	}
	
	@Transient
	public static List<Pagamenti> getPagamentiEseguiti (CondizionePagamento cond){
		
		Set<Pagamenti> pagamenti = cond.getPagamenti();
		
		List<Pagamenti> pagamentiEseguiti =  new ArrayList<Pagamenti>();
		
		for (Pagamenti pagamento : pagamenti) {
			
			if (pagamento.getStPagamento().equals(StatiPagamentiIris.ESEGUITO.getPagaMapping())) {
				pagamentiEseguiti.add(pagamento);
			}
			
		
			
		}
		
		return pagamentiEseguiti;
		
	}
	
	/**
	 * Ritorna l'ultimo pagamento eseguito per questa CondizionePagamento. 
	 * 
	 * @return  l'ultimo pagamento eseguito per questa CondizionePagamento
	 */
	@Transient
	public static Pagamenti getLastExecutedPayment(CondizionePagamento cond) {
		
		Pagamenti lastExecutedPayment = null;
		
		Timestamp maxTimestamp = null;
		
		Set<Pagamenti> pagamenti = cond.getPagamenti();
		
		for (Pagamenti pagamento : pagamenti) {
			
			if(pagamento.isExecutedPayment()) {
				
				if (maxTimestamp == null || pagamento.getTsInserimento().compareTo(maxTimestamp) > 0) {
					maxTimestamp = pagamento.getTsInserimento();
					lastExecutedPayment = pagamento;
				
				}
			}
		}
		return lastExecutedPayment;
	}

	/**
	 * @param cond
	 */
	@Transient
	public static void preLoadPagamenti(CondizionePagamento cond) {
		
		Set<Pagamenti> pagamenti = cond.getPagamenti();
		if (pagamenti != null) {
			for (Pagamenti pagamento : pagamenti) 
				pagamento.getFlussoDistinta().getCfgGatewayPagamento().getApplicationId();   //Tutti dati obbligatoriamente presenti nella catena dei get.
			
		}
	}

	/**
	 * Solo per effettuare il cast di una List<VocePagamento> ad una List<IVocePagamento>
	 * 
	 * Necessario per centralizzare il metodo concatVociPagamento tra i due framework attualmente in uso.
	 * 
	 * @param cond
	 * @return
	 */
	@Transient
	public static List<IVocePagamento> getVoci(CondizionePagamento cond){
		
		List<IVocePagamento> voci = new ArrayList<IVocePagamento>();
		
		for(IVocePagamento voce : cond.getVociPagamento()){
			
			voci.add(voce);
			
		}
		
		return voci;
	}
	
	/**
	 * @param cond
	 * @return
	 */
	@Transient
	public static Map<String, List<DocumentoDiPagamento>> getDDPValidoEAnnullati(CondizionePagamento cond){
		
		Map<String, List<DocumentoDiPagamento>> result = new HashMap<String, List<DocumentoDiPagamento>>();
		
		List<DocumentoDiPagamento> nullDDPList = new ArrayList<DocumentoDiPagamento>();
		
		List<DocumentoDiPagamento> validDDPList = new ArrayList<DocumentoDiPagamento>();
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
	    String controllaEsistenzaBollettini = cpl.getProperty("checkExists.bollettino.associato.enabled");
	    
		// Cerco se tra i documenti associati ce n'� almeno uno NON annullato  
		for(CondizioneDocumento c: cond.getCondizioniDocumento()) {
			if (controllaEsistenzaBollettini.equalsIgnoreCase("true")) {
				if (c.getTsAnnullamento()==null)
					validDDPList.add(c.getDocumento());
				else 
					nullDDPList.add(c.getDocumento());
			}
		}
		
		Collections.sort(nullDDPList, new DocumentoDiPagamento.TsEmissioneComparatorDesc());
		
		result.put("VALID_DDP", validDDPList);
		
		result.put("NULL_DDP", nullDDPList);
		
		return result;
		
	}
	
	/**
	 * @param cond
	 * @return
	 */
	@Transient
	public static Pendenza makeVisible(CondizionePagamento cond) {

		Pendenza pend = cond.getPendenza();

		if (isHidden(cond)) {
			
			cond.setStRiga(VALID_ROW_STATUS);
			cond.setTsAnnullamento(null);

			if (pend.getStRiga().equals(HIDDEN_ROW_STATUS)) {
				pend.setStRiga(VALID_ROW_STATUS);
				pend.setTsAnnullamento(null);
			}
			
			Set<DestinatariPendenza> destinatari = pend.getDestinatari();
			for (DestinatariPendenza dest : destinatari) {
				if (dest.getStRiga().equals(HIDDEN_ROW_STATUS)) {
					dest.setStRiga(VALID_ROW_STATUS);
				}
			}
		}

		return pend;
	}
	
	/**
	 * @param cond
	 * @return
	 */
	@Transient
	public static Pendenza makeHidden(CondizionePagamento cond){
			
		cond.setStRiga(HIDDEN_ROW_STATUS);
		
		Pendenza pend = cond.getPendenza();		
		
		pend.setStRiga(HIDDEN_ROW_STATUS);
	
		Set<DestinatariPendenza> destinatari = pend.getDestinatari();
	
		for(DestinatariPendenza dest : destinatari)
					
							dest.setStRiga(HIDDEN_ROW_STATUS);
		
		return pend;
	}
	
	/**
	 * @param cond
	 * @return
	 */
	@Transient
	public static boolean isHidden(CondizionePagamento cond){
		return cond.getStRiga().equals(HIDDEN_ROW_STATUS);
	}
	

}
