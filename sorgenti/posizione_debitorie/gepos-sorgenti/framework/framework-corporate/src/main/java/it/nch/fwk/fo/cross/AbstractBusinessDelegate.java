/*
 * Creato il 20-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.cross;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.helper.ErrorManager;
import it.nch.fwk.fo.helper.ErrorManagerImpl;
import it.nch.fwk.fo.util.Tracer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author EE10057
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class AbstractBusinessDelegate {

	ErrorManager em = new ErrorManagerImpl();

	protected DTO processException(DTO dto, Exception e, String code) {
		Tracer.info(this.getClass().toString(), "processException",	"Inizio processo Eccezione", null);
		em.menageException(dto, e, code);
		return dto;
	}

	protected DTOCollection processException(DTOCollection dtoCollection,Exception e, String code) {
		Tracer.info(this.getClass().toString(), "processException",	"Inizio processo Eccezione", null);
		em.menageException(dtoCollection, e, code);
		return dtoCollection;
	}

	protected DTOCollection dto2Collection(DTO dto) {
		Collection c = new ArrayList();
		c.add(dto);
		return new DTOCollectionImpl(c);

	}

	
}
