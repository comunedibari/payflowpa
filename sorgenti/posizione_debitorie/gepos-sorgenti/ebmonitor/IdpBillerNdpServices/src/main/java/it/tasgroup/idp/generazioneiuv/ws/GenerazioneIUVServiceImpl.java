package it.tasgroup.idp.generazioneiuv.ws;

import javax.ejb.EJB;
import javax.jws.WebService;

import it.tasgroup.idp.generazioneiuv.GeneraIUVRequest;
import it.tasgroup.idp.generazioneiuv.GeneraIUVResponseType;
import it.tasgroup.idp.generazioneiuv.GeneraLottoIUVRequest;
import it.tasgroup.idp.generazioneiuv.GeneraLottoIUVResponseType;
import it.tasgroup.idp.generazioneiuv.GenerazioneIUV;
import it.tasgroup.idp.generazioneiuv.GenerazioneIUVController;



@WebService(serviceName = "GenerazioneIUVService",   endpointInterface = "it.tasgroup.idp.generazioneiuv.GenerazioneIUV",
targetNamespace = "http://idp.tasgroup.it/GenerazioneIUV/",portName = "GenerazioneIUVSOAP")

public class GenerazioneIUVServiceImpl implements GenerazioneIUV {

	@EJB(beanName="GenerazioneIUVControllerImpl")
	GenerazioneIUVController controller;

	@Override
	public GeneraLottoIUVResponseType generaLottoIUV(GeneraLottoIUVRequest parameters) {
		
		return controller.generaLottoIUV(parameters);
		
		
	}

	@Override
	public GeneraIUVResponseType generaIUV(GeneraIUVRequest parameters) {
		
		return controller.generaIUV(parameters);
		
		
	}
	
	


}
