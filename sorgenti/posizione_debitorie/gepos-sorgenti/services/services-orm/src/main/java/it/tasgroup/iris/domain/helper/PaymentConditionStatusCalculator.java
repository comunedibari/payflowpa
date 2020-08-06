package it.tasgroup.iris.domain.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.services.util.enumeration.EnumTipoCondPagamento;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

/**
 * Classe di utilit� che centralizza il calcolo dello "stato pagamento effettivo della condizione di pagamento",
 * ovvero lo stato calcolato in base ai dati della condizione, dei pagamenti e documenti associati.
 * Lo stato calcolato della condizione � rappresentato dal tipo EnumStatoPagamentoCondizione che solo questa classe deve gestire.
 * 
 * @author RepettiS
 *
 */
public class PaymentConditionStatusCalculator {
	
	
	/**
	 * Calcola lo stato pagamento complessivo di una condizione di pagamento e lo setta
	 * nel relativo campo statoPagamentoCondizione dell'oggetto condizione passato come parametro. 
	 * @param condizione (con statoPagamentoCondizione calcolato ed aggiornato)
	 * @return
	 */
	public static EnumStatoPagamentoCondizione calculateStatus(CondizionePagamento condizione) {
		
		condizione.setIdDistintaInCorso(null);
		
			if (isRimborsata(condizione))
				return EnumStatoPagamentoCondizione.NON_PAGABILE_RIMBORSATA;
		
			if (condizione.isPagataIrregolarmente())
				return EnumStatoPagamentoCondizione.NON_PAGABILE_PAGAMENTO_IRREGOLARE;
		
			if (isPagata(condizione))
				return EnumStatoPagamentoCondizione.PAGATA;	
			
			if (isInCorso(condizione))
				return EnumStatoPagamentoCondizione.IN_CORSO;
	
			if (condizione.getPendenza().isChiusa())
				return EnumStatoPagamentoCondizione.NON_PAGABILE_PENDENZA_CHIUSA;
			
			if (!isPagabilePerTermini(condizione))
				 return EnumStatoPagamentoCondizione.NON_PAGABILE_TERMINI;	
			
			if (hasDocumentoAssociato(condizione))
				return EnumStatoPagamentoCondizione.NON_PAGABILE_ASSOCIATA_DOCUMENTO;							

			if (condizione.getTiPagamento().equals(EnumTipoCondPagamento.SINGOLO.getChiave())) {
				
					if (hasStartedPagamentoRateale(condizione.getPendenza())) 
							return EnumStatoPagamentoCondizione.NON_PAGABILE_RATEIZZAZIONE;
					
					//se � cartella allora non eseguo questo controllo
					boolean isCartellaPagamento = new Integer(1).equals(condizione.getPendenza().getCartellaPagamento());
					if (!isCartellaPagamento) {
						if (hasStartedPagamentoSingolo(condizione.getPendenza())) 
							return EnumStatoPagamentoCondizione.NON_PAGABILE_RATEIZZAZIONE;						
					}
					
			}
			
			if (condizione.getTiPagamento().equals(EnumTipoCondPagamento.RATEIZZATO.getChiave())) {
					if (hasStartedPagamentoSingolo(condizione.getPendenza())) 
							return EnumStatoPagamentoCondizione.NON_PAGABILE_RATEIZZAZIONE;
					
			}	

				
			

		// In ogni altro caso � "DA PAGARE"
		
		return EnumStatoPagamentoCondizione.DA_PAGARE;
		
		
	}


	/**
	 * Verifica se una pendenza ha un pagamento rateale avviato
	 * Ovvero se esiste una condizione "rata" con associato un pagamento eseguito, in corso oppure
	 * associata ad un documento emesso valido.
	 * @param p
	 * @return
	 */
	private static boolean hasStartedPagamentoRateale(Pendenza p) {
		
		for (CondizionePagamento condizione:p.getCondizioni()) {
			
			if (condizione.getTiPagamento().equals(EnumTipoCondPagamento.RATEIZZATO.getChiave())) {
				
				if (isPagata(condizione) || isInCorso(condizione) || hasDocumentoAssociato(condizione)) {
					
					return true;
					
				}
				
			}
			
		}
		
		return false;
	}

	/**
	 * Verifica se una pendenza ha un pagamento singolo avviato
	 * Ovvero se esiste una condizione "rata" con associato un pagamento eseguito, in corso oppure
	 * associata ad un documento emesso valido.
	 * @param p
	 * @return
	 */
	private static boolean hasStartedPagamentoSingolo(Pendenza p) {
		for (CondizionePagamento condizione:p.getCondizioni()) {
			if (condizione.getTiPagamento().equals(EnumTipoCondPagamento.SINGOLO.getChiave())) {
				if (isPagata(condizione) || isInCorso(condizione) || hasDocumentoAssociato(condizione)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Verifica se una condizione di pagamento 
	 * e' pagabile (guardando solo i termini indicati nella posizione debitoria dall'ente).
	 * @param p
	 * @return
	 */
	private static boolean isPagabilePerTermini(CondizionePagamento condizione) {
		
		// Non � pagabile per definizione (RFC127)
		if("X".equalsIgnoreCase(condizione.getStPagamento()))
			return false;

		// Non rientra nel range di validita' 
		boolean ratapagabile = checkValidita(condizione.getDtFinevalidita(), condizione.getDtIniziovalidita());
		
		if (!ratapagabile) 
			return false;
		
		return true;
	}
	
	/**
	 * Verifica se una condizione � pagata (o pagata sbf)
	 * @param condizionePagamento
	 * @return
	 */
	public static boolean isPagata(CondizionePagamento condizionePagamento) {
			
			// Segnalata pagata dall'ente
			if (condizionePagamento.getStPagamento().equals("P"))
				return true;

			Set<Pagamenti> p = condizionePagamento.getPagamenti();
			String statoPagamento = null;
			for (Pagamenti pagamenti:p) {
				statoPagamento = pagamenti.getStPagamento();
				if (statoPagamento!=null) {
					if (statoPagamento.equals(StatiPagamentiIris.ESEGUITO.getPagaMapping()) || 
						statoPagamento.equals(StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping()) ) {
						return true;
					}				
				}
			}
			
			return false;
	}

	
	/**
	 * Verifica se una condizione ha un pagamento in corso associato
	 * @param condizionePagamento
	 * @return
     */
	private static boolean isInCorso(CondizionePagamento condizionePagamento) {
		
		// Segnalata pagata dall'ente
		if (!condizionePagamento.getStPagamento().equals("N") )
			return false;

		Set<Pagamenti> p = condizionePagamento.getPagamenti();
		String statoPagamento = null;
		for (Pagamenti pagamenti:p) {
			statoPagamento = pagamenti.getStPagamento();
			if (statoPagamento!=null) {
				if (statoPagamento.equals(StatiPagamentiIris.IN_CORSO.getPagaMapping())) {
					condizionePagamento.setIdDistintaInCorso(pagamenti.getFlussoDistinta().getId().toString());
					return true;
				}				
			}
		}
		return false;
	}

	/**
	 * Verifica se una condizione ha un pagamento in corso rimborsato
	 * @param condizionePagamento
	 * @return
     */
	private static boolean isRimborsata(CondizionePagamento condizionePagamento) {
		
		
		if (condizionePagamento.getStPagamento().equals("R") )
			return true;
		
		// Segnalata pagata dall'ente
		if (!condizionePagamento.getStPagamento().equals("N") )
			return false;

		Set<Pagamenti> p = condizionePagamento.getPagamenti();
		String statoPagamento = null;
		for (Pagamenti pagamenti:p) {
			statoPagamento = pagamenti.getStPagamento();
			if (statoPagamento!=null) {
				if (statoPagamento.equals(StatiPagamentiIris.STORNATO.getPagaMapping())) {
					return true;
				}				
			}
		}
		return false;
	}	
	
	
	
	/**
	 * Verifica se una condizione di pagamento ha un documento associato non annullato
	 * @param condizione
	 * @return
	 */
	private static boolean hasDocumentoAssociato(CondizionePagamento condizione) {
		
		if (condizione.getCondizioniDocumento()!=null && condizione.getCondizioniDocumento().size()>0) {
			// Cerco se tra i documenti associati ce n'� almeno uno NON annullato  
			
			for(CondizioneDocumento c:condizione.getCondizioniDocumento()) {
				
				if (c.getTsAnnullamento()==null) {
					ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
				    String controllaEsistenzaBollettini = cpl.getProperty("checkExists.bollettino.associato.enabled");
				    if (controllaEsistenzaBollettini.equalsIgnoreCase("true")) {
				    	return true;
				    }
					
				}
			}
		} 
		
		return false; 
	}
	

	/**
	 * Verifica le date di inizio e fine validit�
	 * e che "oggi" cada in quest'intervallo.
	 * N.B. data fine e data inizio comprese
	 * @param dataFine
	 * @param dataInizio
	 * @return
	 */
	public static boolean checkValidita(Date dataFine, Date dataInizio) {

		// ref #915 sposto in avanti di 24 ore la data fine letta dal db
		// in modo che il giorno di scadenza sia compreso nell'intervallo di validit� 
		Date dataScadenza;
		
		if (dataFine == null) 
			dataScadenza = null;
		else
			dataScadenza = new Date(dataFine.getTime() + 1000 * 3600 * 24);
	
		Date today = new Date();

		// entrambi != null
		if (dataScadenza != null && dataInizio != null) 
			if (dataScadenza.after(today) && dataInizio.before(today)) 
				return true;
			
		// se data fine null
		if (dataScadenza == null && dataInizio != null) 
			if (dataInizio.before(today)) 
				return true;

		// se data inizio null
		if (dataInizio == null && dataScadenza != null)
			if (dataScadenza.after(today))
				return true;

		return false;
	}
	
	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	    
	    Date inizio = cal.getTime();
    	System.out.println("INIZIO: " + inizio);
	    
	    cal.set( Calendar.DATE, 11 );

	    Date fine = cal.getTime();
    	System.out.println("FINE:   " + fine);
	    
	    if (PaymentConditionStatusCalculator.checkValidita(fine, inizio)) {
	    	System.out.println("OGGI E' VALIDO");
	    } else {
	    	System.out.println("OGGI E' FUORI DAI TERMINI DI VALIDITA'");
	    }
	    
	}
	
}
