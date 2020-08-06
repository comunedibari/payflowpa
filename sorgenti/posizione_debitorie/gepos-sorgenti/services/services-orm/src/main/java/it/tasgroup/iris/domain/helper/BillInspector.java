package it.tasgroup.iris.domain.helper;

import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * Classe di ausilio per indagare o decorare le proprietà della Pendenza.
 * 
 * 
 * 
 * @author pazzik
 *
 */
public class BillInspector {
	
	/** 
	 * Funzione di utilità. Calcola l'importo pagato di 
	 * una pendenza.
	 * @param p
	 * @return
	 */
	public static  BigDecimal calcolaImportoPagato(Pendenza p) {
		
		BigDecimal result=BigDecimal.ZERO;
		
		for (CondizionePagamento c:p.getCondizioni()) {
			if (c.getTsAnnullamento() == null) {
				
				if (c.getStPagamento().equals("P"))
					
					result=result.add(c.getImTotale());
					
				else {
					
					for (Pagamenti pp:c.getPagamenti()) {
						
						if (pp.getStPagamento().equals("ES") || pp.getStPagamento().equals("EF"))
							result=result.add(pp.getImPagato());
						
					}
				}
			}	
		}
		
		return result;
	}
	
	/** 
	 * Funzione di utilità. Calcola le condizioni non pagabili di  
	 * una pendenza.
	 * @param p
	 * @return
	 */	
	public  static Integer calcolaCondizioniNonPagabili(Pendenza p) {
		Integer result=0;
		
		for (CondizionePagamento c:p.getCondizioni()) {
			if (c.getTsAnnullamento()==null) {
				if (c.getStPagamento().equals("X")) {
					result=result+1;
				}
			}	
		}
				
		return result;
	}

	/** 
	 * Funzione di utilità. Calcola le condizioni pagabili di 
	 * una pendenza.
	 * @param p
	 * @return
	 */
	public static Integer calcolaCondizioniPagabili(Pendenza p) {
		Integer result=0;
		
		for (CondizionePagamento c:p.getCondizioni()) {
			if (c.getTsAnnullamento()==null) {
				if (!c.getStPagamento().equals("X")) {
					result=result+1;
				}
			}	
		}
				
		return result;
	}
	
	
	/*
	 * Funzione di utilità: calcola la "data scadenza" della pendenza (come
	 * minima data scadenza delle condizioni)
	 *  
	 */
	public static Date calcolaDataScadenza(Pendenza p) {
		Date result = null;
		for (CondizionePagamento c:p.getCondizioni()) {
			if (c.getTsAnnullamento()==null || "E".equals(c.getStPagamento())) { 
				if (result==null || c.getDtScadenza().before(result)) {
					result=c.getDtScadenza();
				}
			}	
		}

		return result;
	}
	

	/*
	 * Funzione di utilità: calcola la "data fine validità" della pendenza (come
	 * max della data di fine validità delle condizioni )
	 *  
	 */
	public static Date calcolaDataFineValidita(Pendenza p) {
		Date result = null;
		for (CondizionePagamento c:p.getCondizioni()) {
			if (c.getTsAnnullamento()==null || "E".equals(c.getStPagamento())) {
				if (result==null || c.getDtFinevalidita().after(result)) {
					result=c.getDtFinevalidita();
				}
			}	
		}

		return result;
		
	}
	
	
	public static String getDebitori(Pendenza p, String separator) {
		
		StringBuffer buf = new StringBuffer();
		
		Set<DestinatariPendenza> destinatari = p.getDestinatari();
		
		int counter = 1;
		
		for (DestinatariPendenza destinatario : destinatari) {
			
			if (!destinatario.getTiDestinatario().equals(DestinatariPendenza.TIPO_DEST_DELEGATO)) {
				buf.append(destinatario.getCoDestinatario());
			}
			
			if (counter != destinatari.size()) {
				buf.append(separator);
			}
			
			counter++;
		}
		
		return buf.toString();
	}
	
	
	public static String getDebitoriRich(Pendenza p, String separator) {
		
		StringBuffer buf = new StringBuffer();
		
		Set<DestinatariPendenza> destinatari = p.getDestinatari();
		
		int counter = 1;
		
		for (DestinatariPendenza destinatario: destinatari){ 	
			
			if (!destinatario.getTiDestinatario().equals(DestinatariPendenza.TIPO_DEST_DELEGATO))
				
				buf.append(destinatario.getCoDestinatario());
			
			if(!StringUtils.isEmpty(destinatario.getDeDestinatario()))
				buf.append("=").append(destinatario.getDeDestinatario());
			
				if (counter != destinatari.size())
					buf.append(separator);
			
			counter++;
		}
		
		return buf.toString();
	}
	
	
	public static boolean checkAutorizzazioneAlPagamento(Pendenza p, String codiceDebitore) {
		
		Set<DestinatariPendenza> destinatari = p.getDestinatari();
		
		for (DestinatariPendenza destinatario: destinatari){ 	
			
			if (destinatario.getCoDestinatario().equals(codiceDebitore))
				
				return true;
			
		}
		
		return false;
		
	}

	
	public static DestinatariPendenza getDestinatario(Pendenza p) {
		
		Set<DestinatariPendenza> destinatari = p.getDestinatari();
		
		for (DestinatariPendenza destinatario: destinatari){ 	
			
			if (!destinatario.getTiDestinatario().equals("DE"))
				
				return destinatario;
			
		}
		
		return null;
	}

}
