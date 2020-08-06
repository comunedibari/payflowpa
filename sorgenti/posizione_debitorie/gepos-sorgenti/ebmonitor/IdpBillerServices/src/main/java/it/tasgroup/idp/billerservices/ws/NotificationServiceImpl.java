package it.tasgroup.idp.billerservices.ws;


import it.tasgroup.idp.billerservices.controller.notification.NotificationController;
import it.tasgroup.idp.notification.AckFileRequest;
import it.tasgroup.idp.notification.AckFileResponse;
import it.tasgroup.idp.notification.CountNotificheRequest;
import it.tasgroup.idp.notification.CountNotificheResponse;
import it.tasgroup.idp.notification.GetFileNotificaRequest;
import it.tasgroup.idp.notification.GetFileNotificaResponse;
import it.tasgroup.idp.notification.ListaNotificheRequest;
import it.tasgroup.idp.notification.ListaNotificheResponse;
import it.tasgroup.idp.notification.NotificationService;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(serviceName = "notificationService", endpointInterface = "it.tasgroup.idp.notification.NotificationService", targetNamespace = "http://idp.tasgroup.it/Notification/", portName = "notificationPort")
public class NotificationServiceImpl implements NotificationService {

	
	@EJB(beanName = "NotificationControllerImpl")
	NotificationController controller;

	@Override
	public CountNotificheResponse countNotifiche(
			CountNotificheRequest parameters) {
		
		return controller.countNotifiche(parameters);
	}

	@Override
	public GetFileNotificaResponse getFileNotifica(
			GetFileNotificaRequest parameters) {
		
		return controller.getFileNotifica(parameters);
	}

	@Override
	public ListaNotificheResponse listaNotifiche(
			ListaNotificheRequest parameters) {
		
		return controller.listaNotifiche(parameters);
	}

	@Override
	public AckFileResponse ackFileNotifica(AckFileRequest parameters) {		
		return controller.ackFileNotifica(parameters);
	}
	
		
}
