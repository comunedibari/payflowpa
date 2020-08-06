/*
 * Created on Sep 4, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.erbweb.business;

import it.nch.fwk.fo.cross.AbstractJavaBean;
import it.nch.fwk.fo.dto.DTO;
import it.nch.profile.IProfileManager;

/**
 * Il ServiceImpl rappresenta un esecutore perciò espone un solo
 * metodo execute che gli permette di eseguire ogni altro metodo distinguendo in base
 * al parametro method in ingresso.
 * In questo modo le interfacce e i deployment descriptor del ServiceBean non devono più registrare ogni singolo
 * metodo ma è sufficiente che espongano execute.
 *
 * @author Vergolanis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public abstract class ServiceImpl extends AbstractJavaBean {

	/**
	 * Esegue il metodo indicato dal parametro method.
	 *
	 * @param method ilnome del metodo da eseguire
	 * @param profilo l'IProfileManager
	 * @param dto il DTO con i parametri di ingresso.
	 * @return il DTO valorizzato con i parametri di ritorno.
	 */
	public DTO execute(String method, IProfileManager profilo, DTO dto) {
		DTO result = runService(method, profilo, dto);

		return result;
	}

	protected abstract DTO runService(String method, IProfileManager profilo, DTO dto);

}
