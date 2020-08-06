/*
 * Creato il 9-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.util.text;

/**
 * @author EE10801
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CodiceFiscale {
	
	
	public static boolean isValid(String cf){
		
		boolean valid = false;
//		 conversione della stringa in caratteri maiuscoli
	      cf = cf.toUpperCase();
	      /*
	       * eliminazione degli eventuali spazi
	       */
	      cf = cf.trim();
	      /*
	       * verifica della lunghezza del codice fiscale
	       */
	      if (cf.length() == 16){


	        
	         // conversione della stringa da esaminare ad una matrice di caratteri
	         char[] caratteriCF = cf.toCharArray();
	         int valore = 0;
	         for (int i = 0; i < caratteriCF.length - 1; i++){
	            /*
	             * somma delle posizioni pari in base ai valori 
	             * corrispondenti contenuti nell'array ValoriPari
	             * (tranne l'ultimo carattere che è quello di controllo)
	             */
	            if ((i+1) % 2 == 0){
	               for (int j = 0; j < caratteri.length; j++){

	                  if (caratteriCF[i] == caratteri[j]){
	                     valore += valoriPari[j];
	                  }
	               }
	               /*
	                * somma delle posizioni dispari in base ai valori 
	                * corrispondenti contenuti nell'array ValoriDispari
	                */
	            }else{
	               for (int j = 0; j < caratteri.length; j++){
	                  if (caratteriCF[i] == caratteri[j]){
	                     valore += valoriDispari[j];
	                  }
	               }
	            }
	         }
	         /*
	          * ottenimento del resto della divisione per 26 e 
	          * valutazione del carattere di controllo (ultimo carattere)
	          */
	         valore %= 26;
	         for (int i = 0; i < 26; i++){
	            /*
	             * verifica che il valore dell'ultimo carattere corrisponda
	             * al valore ottenuto attraverso l'algoritmo di somma precedente
	             */
	            if (caratteriCF[caratteriCF.length - 1] == caratteri[i]){
	               if (valore == i)
	                  valid = true;
	            }
	         }

	      }
	      return valid;
	   }
	
    /*
     *  matrice con i caratteri dell'alfabeto
     */
	private static char[] caratteri = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0',
            '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	 /*
     * creazione della matrice con i valori attribuiti ai caratteri 
     * dispari, corrispondenti alla matrice di caratteri
     */
	private static int[] valoriDispari = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4,
                      18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22,
                      25, 24, 23, 1, 0, 5, 7, 9, 13, 15, 17, 19,
                      21};
    /*
     * creazione della matrice con i valori attribuiti ai caratteri 
     * pari, corrispondenti alla matrice di caratteri
     */
	private static int[] valoriPari = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
										14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
										0, 	1, 2, 3, 4, 5, 6, 7, 8, 9};
	
}
