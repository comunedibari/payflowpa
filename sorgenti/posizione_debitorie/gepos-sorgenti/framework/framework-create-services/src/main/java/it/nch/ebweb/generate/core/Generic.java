/*
 * Creato il 20-feb-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.core;

import it.nch.ebweb.generate.backend.service.Azione;
import it.nch.ebweb.generate.backend.service.Service;

import java.util.ArrayList;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class Generic {
	
	
	abstract public Service getService();
	
	protected void addAzione(ArrayList array, int paramType, int returnType, int tyeInternal, String nome) {
		addAzione( array, 0, paramType,  returnType,  tyeInternal,  nome);
	}
	
	protected void addAzione(ArrayList array, int paramType, int returnType, int tyeInternal, String nome, String actionType) {
		addAzione( array, 0, paramType,  returnType,  tyeInternal,  nome,  actionType);
		
	}
	
	protected void addAzione(ArrayList array, int retryNum,int paramType, int returnType, int tyeInternal, String nome) {
		Azione azione = new Azione();
		azione.setNome(nome);
		azione.setParamType(paramType);
		azione.setReturnType(returnType);
		azione.setInternalService(tyeInternal);
		azione.setRetryNum(retryNum);
		array.add(azione);
	}
	
	protected void addAzione(ArrayList array, int retryNum,int paramType, int returnType, int tyeInternal, String nome, String actionType) {
		Azione azione = new Azione();
		azione.setNome(nome);
		azione.setParamType(paramType);
		azione.setReturnType(returnType);
		azione.setInternalService(tyeInternal);
		azione.setServiceType(actionType);
		azione.setRetryNum(retryNum);
		
		array.add(azione);
		
	}

}
