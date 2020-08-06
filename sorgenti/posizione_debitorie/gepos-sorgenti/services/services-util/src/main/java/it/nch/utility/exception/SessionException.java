package it.nch.utility.exception;



/**
 * Classe SessionException che gestisce le eccezioni scatenate dalla lettura dei dati sulla sessione
 */
public class SessionException extends Exception  {
	
   /**
	 * Costruttore della classe SessionException che riceve come parametro il messaggio d'errore, il codice e l'eccezione che ha scatenato questa
	 * non fa altro che chiamare l'analogo costruttore della superclasse IBISException
     * @param prev eccezione che ha preceduto e scatenato questa. Questo campo è null se l'eccezione precedente è di sistema 
     * @param cod codice dell'errore
     * @param msg messaggio d'errore
     * @author Roberto Gaudenzi 
	 */
	public SessionException(Exception prev, String cod, String msg) {		
	}
}
