/*
 * Created on 3-giu-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.idp.posizionedebitoria;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;


/**
 * @author Simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PosizioneDebitoriaHelper {

	public static final String PAGATO = "pagato";
	public static final String DAPAGARE = "dapagare";
	public static final String INCORSO = "incorso";
	public static final String PAGATOSALVOBUONFINE = "pagatosalvobuonfine";
	public static final String ERRORE = "errore";
	public static final String ANNULLATO = "annullato";
	public static final String NONPAGABILE = "nonpagabile";
	public static final String INCARRELLO="incarrello";
	public static final String RIMBORSATO="rimborsato";
	public static final String RIMBORSATO_ENTE="rimborsato_ente";
	public static final String IRREGOLARE="irregolare";

	public static final String STATO_PAGAMENTO_DAPAGARE = "";
	public static final String STATO_PAGAMENTO_STORNATO = StatiPagamentiIris.STORNATO.getPagaMapping();
	public static final String STATO_PAGAMENTO_NON_ESEGUITO = StatiPagamentiIris.NON_ESEGUITO.getPagaMapping();
	public static final String STATO_PAGAMENTO_ESEGUITO = StatiPagamentiIris.ESEGUITO.getPagaMapping();
	public static final String STATO_PAGAMENTO_INCORSO = StatiPagamentiIris.IN_CORSO.getPagaMapping();//"C";
	public static final String STATO_PAGAMENTO_PAGATOSALVOBUONFINE = StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping();
	public static final String STATO_PAGAMENTO_ERRORE = StatiPagamentiIris.IN_ERRORE.getPagaMapping();//"E";
	public static final String STATO_PAGAMENTO_ANNULLATO = StatiPagamentiIris.ANNULLATO.getPagaMapping();//"A";
	
	public static final String STATO_PAGAMENTO_INCARRELLO = StatiPagamentiIris.IN_CARRELLO.getPagaMapping();//"I";
	public static final String STATO_PAGAMENTO_ANNULLATO_DA_OPERATORE = StatiPagamentiIris.ANNULLATO_DA_OPERATORE.getPagaMapping();//"I";


	/**
	 * Verifica se la condizione è pagabile in base allo stato della condizione e
	 * allo stato del pagamento. In particolare una condizione risulta pagabile
	 * solo se il suo stato calcolato è DAPAGARE o ANNULLATO cioè questo è lo stato dei suoi pagamenti.
	 *
	 * @param statoCondizione stato della condizione di pagamento
	 * @param statoPagamento stato del pagamento
	 * @return true se la condizione è pagabile, false altrimenti.
	 */
	public static boolean isCondizionePagabile(String statoCondizione, String statoPagamento)
	{
	  String tmp_risp = calcolaStatoCondizionePagamento(statoCondizione, statoPagamento);

	  if(PAGATO.equalsIgnoreCase(tmp_risp))	  
		  return false;

	  if(IRREGOLARE.equalsIgnoreCase(tmp_risp))	  
		  return false;
	  
	  if(DAPAGARE.equalsIgnoreCase(tmp_risp))
		  return true;
	   
	  if(INCORSO.equalsIgnoreCase(tmp_risp))
		  return false;
	  
	  if(PAGATOSALVOBUONFINE.equalsIgnoreCase(tmp_risp))
		  return false;
	  
	  if(ERRORE.equalsIgnoreCase(tmp_risp))
		  return true;
	  
	  if(ANNULLATO.equalsIgnoreCase(tmp_risp))
		  return true;
	  
	  if (INCARRELLO.equalsIgnoreCase(tmp_risp))
		  return false;
	  
	  if (NONPAGABILE.equalsIgnoreCase(tmp_risp))
		  return false;

	  return false;
	}
	

	/**
	 * Verifica se lo stato della condizione è pagabile in base allo
	 * stato della 
	 * 
	 * @param statoCondizione stato della condizione di pagamento
	 * @param statoPagamento stato del pagamento
	 * @return true se la condizione è pagabile, false altrimenti.
	 */

	public static boolean isCondizionePagabileCompresoNonPagabile(String statoCondizione, String statoPagamento)
	{
	  String tmp_risp = calcolaStatoCondizionePagamento(statoCondizione, statoPagamento);

	  if(PAGATO.equalsIgnoreCase(tmp_risp))	  
		  return false;

	  if(IRREGOLARE.equalsIgnoreCase(tmp_risp))	  
		  return false;
	  
	  if(DAPAGARE.equalsIgnoreCase(tmp_risp))	  
		  return true;
	  
	  if(INCORSO.equalsIgnoreCase(tmp_risp))
		  return false;
	  
	  if(PAGATOSALVOBUONFINE.equalsIgnoreCase(tmp_risp))
		  return false;
	  
	  if(ERRORE.equalsIgnoreCase(tmp_risp))
		  return true;
	  
	  if(ANNULLATO.equalsIgnoreCase(tmp_risp))
		  return true;
	  
	  if (INCARRELLO.equalsIgnoreCase(tmp_risp))
		  return false;

	  if (NONPAGABILE.equalsIgnoreCase(tmp_risp))
		  return true;

	  return false;
	}
	/**
	 * Restituisce una stringa che rappresenta la descrizione dello stato del pagamento
	 * calcolata in base allo stato della condizione di pagamento e allo stato del pagamento.
	 *
	 * @param statoCondizione stato della condizione di pagamento
	 * @param statoPagamento stato del pagamento
	 * @return una stringa che rappresenta la descrizione dello stato del pagamento
	 * calcolata in base allo stato della condizione di pagamento e allo stato del pagamento.
	 */
	public static String calcolaStatoCondizionePagamento(String statoCondizione, String statoPagamento)
	{
	  if("R".equalsIgnoreCase(statoCondizione))
			  return RIMBORSATO_ENTE;
	  if("E".equalsIgnoreCase(statoCondizione))
			  return IRREGOLARE;
	  if("P".equalsIgnoreCase(statoCondizione))
		  return PAGATO;
	  if("X".equalsIgnoreCase(statoCondizione))
		  return NONPAGABILE;
	  if("N".equalsIgnoreCase(statoCondizione))
	  {

		  if(statoPagamento == null || statoPagamento.trim().equals(STATO_PAGAMENTO_DAPAGARE) || STATO_PAGAMENTO_NON_ESEGUITO.equals(statoPagamento.trim()))
			  return DAPAGARE;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_INCORSO))
			  return INCORSO;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_PAGATOSALVOBUONFINE))
			  return PAGATOSALVOBUONFINE;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_ERRORE))
			  return ERRORE;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_ANNULLATO))
			  return ANNULLATO;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_INCARRELLO))
			  return INCARRELLO;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_ESEGUITO))
			  return PAGATO;
		  
		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_ANNULLATO_DA_OPERATORE))
			  return DAPAGARE;

		  if(statoPagamento.trim().equals(STATO_PAGAMENTO_STORNATO))
			  return RIMBORSATO;

	  }
	  return "";
	}

}
