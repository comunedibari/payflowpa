/**
 * PagamentiTelematiciRPT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT;

public interface PagamentiTelematiciRPT extends java.rmi.Remote {
    public gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta nodoChiediStatoRPT(gov.telematici.pagamenti.ws.NodoChiediStatoRPT bodyrichiesta) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta nodoChiediListaPendentiRPT(gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPT bodyrichiesta) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoInviaRPTRisposta nodoInviaRPT(gov.telematici.pagamenti.ws.NodoInviaRPT bodyrichiesta, gov.telematici.pagamenti.ws.ppthead.IntestazionePPT header) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta nodoInviaCarrelloRPT(gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT bodyrichiesta, gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT header) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta nodoChiediCopiaRT(gov.telematici.pagamenti.ws.NodoChiediCopiaRT bodyrichiesta) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta nodoChiediInformativaPSP(gov.telematici.pagamenti.ws.NodoChiediInformativaPSP bodyrichiesta) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta nodoPAChiediInformativaPA(gov.telematici.pagamenti.ws.NodoPAChiediInformativaPA bodyrichiesta) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta nodoChiediElencoQuadraturePA(gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePA bodyrichiesta) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta nodoChiediQuadraturaPA(gov.telematici.pagamenti.ws.NodoChiediQuadraturaPA parameters) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta nodoChiediElencoFlussiRendicontazione(gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione parameters) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta nodoChiediFlussoRendicontazione(gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione parameters) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta nodoInviaRichiestaStorno(gov.telematici.pagamenti.ws.NodoInviaRichiestaStorno parameters) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta nodoInviaRispostaRevoca(gov.telematici.pagamenti.ws.NodoInviaRispostaRevoca parameters) throws java.rmi.RemoteException;
    public gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta nodoChiediSceltaWISP(gov.telematici.pagamenti.ws.NodoChiediSceltaWISP bodyrichiesta) throws java.rmi.RemoteException;
}
