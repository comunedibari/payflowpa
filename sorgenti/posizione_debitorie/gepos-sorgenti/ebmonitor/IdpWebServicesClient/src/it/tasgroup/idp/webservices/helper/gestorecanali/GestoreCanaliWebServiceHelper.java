package it.tasgroup.idp.webservices.helper.gestorecanali;

import it.tasgroup.idp.pojo.UtenteIris;

import java.util.List;

public interface GestoreCanaliWebServiceHelper {

	public void sendMessage(List<UtenteIris> destinatari, String subject, String content, String messageType) throws Exception;

}