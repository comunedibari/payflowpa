/**
 * ComunicationPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.tasgroup.iris.comunication.ws.impl;

public interface ComunicationPortType extends java.rmi.Remote {
    public it.tasgroup.iris.comunication.ws.impl.UtenteCanaleType[] getCanaliComunicazione(it.tasgroup.iris.comunication.ws.impl.UtenteType utente) throws java.rmi.RemoteException;
    public void sendMessageAgain(it.tasgroup.iris.comunication.ws.impl.MessaggioType[] messaggio) throws java.rmi.RemoteException;
    public void subscribeCanali(it.tasgroup.iris.comunication.ws.impl.UtenteType subscriber, it.tasgroup.iris.comunication.ws.impl.TipoCanaleType tipoCanaleResponse, it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType parametroCanale) throws java.rmi.RemoteException;
    public it.tasgroup.iris.comunication.ws.impl.CanaleType[] getCanaliConfigurazione() throws java.rmi.RemoteException;
    public void updateTipoMessaggio(it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType tipoMessaggio) throws java.rmi.RemoteException;
    public it.tasgroup.iris.comunication.ws.impl.TipoMessaggioType[] getTipoMessaggio() throws java.rmi.RemoteException;
    public it.tasgroup.iris.comunication.ws.impl.ValidoType validaConfigurazioneCanale(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) throws java.rmi.RemoteException;
    public it.tasgroup.iris.comunication.ws.impl.LogMessaggioType[] elencoLogMessaggi(it.tasgroup.iris.comunication.ws.impl.SearchLogMessaggiType searchLogMessaggi) throws java.rmi.RemoteException;
    public void unsubscribeCanali(it.tasgroup.iris.comunication.ws.impl.UtenteType subscriber, it.tasgroup.iris.comunication.ws.impl.TipoCanaleType tipoCanaleResponse, it.tasgroup.iris.comunication.ws.impl.ParametroCanaleType parametroCanale) throws java.rmi.RemoteException;
    public void updateCanaliComunicazione(it.tasgroup.iris.comunication.ws.impl.CanaleType canale) throws java.rmi.RemoteException;
    public void sendMessage(it.tasgroup.iris.comunication.ws.impl.MessaggioLogicoType messaggioLogico, it.tasgroup.iris.comunication.ws.impl.UtenteType[] utenti) throws java.rmi.RemoteException;
}
