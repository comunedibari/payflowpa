package it.tasgroup.iris.persistence.dao.interfaces;

import javax.ejb.Local;

import it.nch.is.fo.profilo.Intestatari;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface IntestatariDAO extends Dao<Intestatari>{
	
	public Intestatari retrieveIntestatarioById(String id);

	/**
	 * ex metodo aziendaCorrenteForLogin
	 * @param corporate
	 * @param operatore
	 * @return
	 */
	public Intestatari getIntestatarioByCorporateAndOperatore(String corporate, String operatore);
	
	public Intestatari getIntestatarioByLapl(String lapl, boolean addressRequired);
	
	public Intestatari getIntestatarioBySessionId(String sessionId);

	public String getLAPLEnteByCdAziendaSanitaria(String cdAziendaSanitaria);

	public Intestatari updateCorporate(Intestatari intestatario);

	public Intestatari find(String corporate);
	
	public boolean checkIdFiscaleEnte(String idFiscaleEnte);    
	
	public Intestatari createIntestatario(Intestatari intestatario);
	
}
