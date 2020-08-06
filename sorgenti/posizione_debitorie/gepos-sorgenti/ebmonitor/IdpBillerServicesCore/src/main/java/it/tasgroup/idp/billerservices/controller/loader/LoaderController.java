package it.tasgroup.idp.billerservices.controller.loader;


import javax.ejb.Local;

import it.tasgroup.idp.loader.CountTrasmissioniRequest;
import it.tasgroup.idp.loader.CountTrasmissioniResponse;
import it.tasgroup.idp.loader.ListaTrasmissioniRequest;
import it.tasgroup.idp.loader.ListaTrasmissioniResponse;
import it.tasgroup.idp.loader.LoaderService;

/**
 * Classe EJB che implementa la logica di business del 
 * web service "Loader".
 * @author user
 *
 */
@Local
public interface LoaderController extends LoaderService {
	
	public ListaTrasmissioniResponse listaTrasmissioniSenzaLimitazioneData(ListaTrasmissioniRequest parameters);

	public CountTrasmissioniResponse countTrasmissioniSenzaLimitazioneData(CountTrasmissioniRequest parameters);

}
