/*
 * Creato il 13-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.cross;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.helper.ErrorManager;
import it.nch.fwk.fo.util.Tracer;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class AbstractDAO {
	
	protected ErrorManager em;

	public void setErrorManager(ErrorManager em) {
		this.em = em;
	}

	protected void processException(DTO dto, String code) {
		Tracer.info("AbstractjavaBean","method:processException","",null);
		em.menageException(dto, code);
	}
	
	protected void processException(DTOCollection dtoCollection, String code) {
		Tracer.info("AbstractjavaBean","method:processException","",null);
		em.menageException(dtoCollection,code);
	}
	
	protected void processException(DTO dto, Exception e,String code) {
		Tracer.info("AbstractjavaBean","method:processException","",null);
		em.menageException(dto,e, code);
	}
	
	protected void processException(DTOCollection dtoCollection,Exception e, String code) {
		Tracer.info("AbstractjavaBean","method:processException","",null);
		em.menageException(dtoCollection,e, code);
	}

}
