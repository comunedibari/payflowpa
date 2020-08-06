/*
 * Creato il 20-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.servizi;

import it.nch.ebweb.generate.backend.service.Azione;
import it.nch.ebweb.generate.backend.service.Service;
import it.nch.ebweb.generate.core.Generic;

import java.util.ArrayList;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class Prova extends Generic{
	
	
	public Service getService(){
		
		ArrayList arrayList = new ArrayList();
		addAzione(arrayList, 1, 1, 0, "save");
		addAzione(arrayList, 1, 1, 0, "load");
		addAzione(arrayList, 1, 1, 0, "update");
		addAzione(arrayList, 1, 2, 1, "list");
		
		Azione[] azioni = new Azione[arrayList.size()];
		for (int iw = 0; iw < arrayList.size(); iw++) {
			azioni[iw] = (Azione)arrayList.get(iw);
		}
				
		Service service = new Service();
		service.setName("Prova");
		service.setAzioni(azioni);
		service.setCategoria(1);				
		service.setDaoType(0);
		service.setTypeReference(0);
		service.setGenerateTestMethod(true);
		
		service.setCrossService(false);
		service.setCrossServiceUsed(null);
		//service.setCrossServiceUsed("Daticbi,TestPaginazione");
		

		return service;
	}

}

