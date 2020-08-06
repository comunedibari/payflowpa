package it.tasgroup.idp.billerservices.ws;

import it.tasgroup.idp.billerservices.controller.loader.LoaderController;
import it.tasgroup.idp.loader.AllineamentoPendenzeRequest;
import it.tasgroup.idp.loader.AllineamentoPendenzeResponse;
import it.tasgroup.idp.loader.CountTrasmissioniRequest;
import it.tasgroup.idp.loader.CountTrasmissioniResponse;
import it.tasgroup.idp.loader.GetEsitoRequest;
import it.tasgroup.idp.loader.GetEsitoResponse;
import it.tasgroup.idp.loader.GetFileTrasmissioneRequest;
import it.tasgroup.idp.loader.GetFileTrasmissioneResponse;
import it.tasgroup.idp.loader.GetStatoRequest;
import it.tasgroup.idp.loader.GetStatoResponse;
import it.tasgroup.idp.loader.ListaTrasmissioniRequest;
import it.tasgroup.idp.loader.ListaTrasmissioniResponse;
import it.tasgroup.idp.loader.LoaderService;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(serviceName = "loaderService", endpointInterface = "it.tasgroup.idp.loader.LoaderService", targetNamespace = "http://idp.tasgroup.it/Loader/", portName = "loaderPort")
public class LoaderServiceImpl implements LoaderService {
	
	@EJB(beanName = "LoaderControllerImpl")
	LoaderController controller;
	
	@Override
	public GetStatoResponse getStato(GetStatoRequest parameters) {
		return controller.getStato(parameters);
	}

	@Override
	public AllineamentoPendenzeResponse allineamentoPendenze(
			AllineamentoPendenzeRequest parameters) {
	
		return controller.allineamentoPendenze(parameters);
	}

	@Override
	public GetEsitoResponse getEsito(GetEsitoRequest parameters) {
		
			return controller.getEsito(parameters);
			
	}

	@Override
	public ListaTrasmissioniResponse listaTrasmissioni(
			ListaTrasmissioniRequest parameters) {

		return controller.listaTrasmissioni(parameters);
	}

	@Override
	public GetFileTrasmissioneResponse getFileTrasmissione(
			GetFileTrasmissioneRequest parameters) {
		
		return controller.getFileTrasmissione(parameters);
	}

	@Override
	public CountTrasmissioniResponse countTrasmissioni(
			CountTrasmissioniRequest parameters) {

		return controller.countTrasmissioni(parameters);
	}


	
	
		
}
