package it.tasgroup.iris.facade.ejb.authorization;

import it.nch.is.fo.profilo.CurrentCorporateVOCommon;
import it.nch.is.fo.profilo.Enti;
import it.nch.profile.IProfileManager;
import it.nch.utility.exception.WrongProfileException;
import it.tasgroup.services.util.enumeration.EnumCategoriaIntestatario;

public class VisibilityChecker {
	
	/**
	 * Questo metodo verifica se i dati dell'ente passato come 
	 * parametro sono visibili dall'operatore in base al suo profilo.
	 * 
	 * @param profiloOperatore
	 * @param ente
	 */
	public static void checkVisibilitaDatiEnte(IProfileManager profiloOperatore, Enti ente) {
		
		String tipoOperatore = profiloOperatore.getCategoria();
		
		if (EnumCategoriaIntestatario.EN.toString().equals(tipoOperatore)) {

			//Se Operatore ente: Id dell'intestatario deve essere uguale a quello in sessione
			
			if (ente.getIntestatarioobj().getCorporateIForm().equals(profiloOperatore.getAzienda())) {
				return; // Controllo superato.
			} else {
				throw new RuntimeException("Wrong profile");			
			}
		}
		
		
		if (EnumCategoriaIntestatario.BO.toString().equals(tipoOperatore))
		{
			
			// Se backoffice ha piena visibilità
			
			return; // Controllo superato.
		}
		
		
	}
	
	
	/**
	 * Questo metodo controlla che i parametri di filtraggio (che poi vengono passati ad una  
	 * query) indirizzino dati visibili
	 * 
	 * @param profiloOperatore
	 * @param identificatoreEnte può essere: codice fiscale ente, codice ente, id ente oppure intestatario ente.
	 * @param gruppo
	 */
	
	
	/**
	 * Metodo che verifica ch profilo sia un profilo Backoffice (BO)e il
	 * 
	 * @param profiloOperatore
	 */
	public static void checkProfiloBackoffice(IProfileManager profiloOperatore) {

		String tipoOperatore = profiloOperatore.getCategoria();
		
		if (EnumCategoriaIntestatario.BO.toString().equals(tipoOperatore))
		{			
			return; // Controllo superato.
		} 
		
		// Caso di tipo operatore non ammesso 
		throw new WrongProfileException();	

	}
	
}
