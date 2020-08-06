/**
 * 
 */
package it.tasgroup.iris.domain.helper;

import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.tasgroup.iris.domain.CfgCommissionePagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.services.util.enumeration.EnumTipoCommissionePagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Set;

/**
 * @author pazzik
 *
 */
public class CommissioniCalculator {
	
	/**
	 * 
	 * @param pagamenti
	 * @return
	 */
	public static BigDecimal getTotalePagamenti(Collection<CondizionePagamento> condizioni){
		
		BigDecimal totale = new BigDecimal("0.00");		 
		
		for (CondizionePagamento condizione : condizioni) 
			totale = totale.add(condizione.getImTotale().setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
		
		return totale;
	}
	
	/**
	 * 
	 * @param pagamenti
	 * @return
	 */
	public static BigDecimal getTotalePagamentiDoc(Collection<CondizioneDocumento> condizioni){
		BigDecimal totale = new BigDecimal("0.00");
		
		for (CondizioneDocumento condizioneDoc : condizioni) 
			totale = totale.add(condizioneDoc.getCondizionePagamento().getImTotale().setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
		
		return totale;
	}
	
	public static BigDecimal calculateTotaleCommissioni(DocumentoDiPagamento documentoDiPagamento){
		
		BigDecimal importoTotalePagamenti = getTotalePagamentiDoc(documentoDiPagamento.getCondizioni());
		BigDecimal totaleCommissioni = calcolaTotaleCommissioni(documentoDiPagamento.getCfgCommissioniPagamenti(), importoTotalePagamenti);
		
		return totaleCommissioni;
		
	}
	
	public static BigDecimal calculateTotalAmountConCommissioni(DocumentoDiPagamento documentoDiPagamento){
		
		BigDecimal importoTotalePagamenti = getTotalePagamentiDoc(documentoDiPagamento.getCondizioni());
//		BigDecimal totaleCommissioni = calcolaTotaleCommissioni(documentoDiPagamento.getCfgCommissioniPagamenti(), importoTotalePagamenti);
//		BigDecimal totalAmountConCommissioni = importoTotalePagamenti.add(totaleCommissioni);
		return importoTotalePagamenti;
		
	}
	
	/**
	 * 
	 * @param configurazione
	 * @param importoTotalePagamenti
	 * @param service
	 * @param fec
	 * @return
	 * @throws ServiceLocatorException
	 */
	public static BigDecimal calcolaTotaleCommissioni(Set<CfgCommissionePagamento> commissioni, BigDecimal importoTotalePagamenti) {

		BigDecimal importoCalcolato = new BigDecimal("0.00");

		if(commissioni != null){
    		for (CfgCommissionePagamento commissione : commissioni) {
    			importoCalcolato = importoCalcolato.add(addCommissione(commissione, importoTotalePagamenti)).setScale(2, RoundingMode.HALF_UP);
    		}
		}

		return importoCalcolato;
	}

	/**
	 * 
	 * @param commissione
	 * @param importoTotalePagamenti
	 * @return
	 */
	private static BigDecimal addCommissione(CfgCommissionePagamento commissione, BigDecimal importoTotalePagamenti) {

		BigDecimal valoreCommissione = commissione.getValore();
		BigDecimal importoA = commissione.getImportoA() != null ? commissione.getImportoA() : BigDecimal.ZERO;
		BigDecimal importoDa = commissione.getImportoDa() != null ? commissione.getImportoDa() : BigDecimal.ZERO;

		BigDecimal imponibile = importoTotalePagamenti.setScale(2, RoundingMode.HALF_UP);
		BigDecimal valoreCalcolato = new BigDecimal("0.00");

		/*
		 * Esempio commissione singola importo 100 da 0 a 0 : imponibile 0
		 * 
		 * Esempio commissioni scaglioni con importo 100 da 0 a 10 - v 2:
		 * imponibile 10 da 11 a 34 - v 3: imponibile 22 da 35 a 0 - v 5:
		 * imponibile 65
		 * 
		 * Il valore importoA = 0 uguale ad infinito
		 */
		if (importoDa.compareTo(importoTotalePagamenti) < 0) {

			if (importoA.compareTo(BigDecimal.ZERO) > 0 && importoTotalePagamenti.compareTo(importoA) > 0) {
				imponibile = importoA.subtract(importoDa).setScale(2, RoundingMode.HALF_UP);
			} else {
				imponibile = importoTotalePagamenti.subtract(importoDa).setScale(2, RoundingMode.HALF_UP);
			}

			if (commissione.getCfgTipoCommissione().getId() == Long.parseLong(EnumTipoCommissionePagamento.PERCENTUALE.getChiave())) {
				valoreCalcolato = valoreCalcolato.add(getImportoPercentuale(imponibile, valoreCommissione));
			} else {
				valoreCalcolato = valoreCalcolato.add(valoreCommissione);
			}

			commissione.setImportoCalcolato(valoreCalcolato);
		}

		return valoreCalcolato;
	}

	/**
	 * 
	 * @param importoTotalePagamenti
	 * @param valore
	 * @return
	 */
	private static BigDecimal getImportoPercentuale(BigDecimal imponibile, BigDecimal valore) {
		BigDecimal percent = new BigDecimal("100");
		valore = valore.divide(percent);
		return imponibile.multiply(valore).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

}
