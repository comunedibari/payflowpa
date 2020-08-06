package it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT;

import java.rmi.RemoteException;

import gov.telematici.pagamenti.ws.NodoChiediCopiaRT;
import gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione;
import gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePA;
import gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione;
import gov.telematici.pagamenti.ws.NodoChiediInformativaPSP;
import gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPT;
import gov.telematici.pagamenti.ws.NodoChiediQuadraturaPA;
import gov.telematici.pagamenti.ws.NodoChiediSceltaWISP;
import gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPT;
import gov.telematici.pagamenti.ws.NodoInviaRichiestaStorno;
import gov.telematici.pagamenti.ws.NodoInviaRispostaRevoca;
import gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta;
import gov.telematici.pagamenti.ws.NodoPAChiediInformativaPA;
import gov.telematici.pagamenti.ws.NodoPAChiediInformativaPARisposta;

public class PagamentiTelematiciRPTProxy implements it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT {
  private String _endpoint = null;
  private it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT pagamentiTelematiciRPT = null;
  
  public PagamentiTelematiciRPTProxy() {
    _initPagamentiTelematiciRPTProxy();
  }
  
  public PagamentiTelematiciRPTProxy(String endpoint) {
    _endpoint = endpoint;
    _initPagamentiTelematiciRPTProxy();
  }
  
  private void _initPagamentiTelematiciRPTProxy() {
    try {
      pagamentiTelematiciRPT = (new it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTserviceLocator()).getPagamentiTelematiciRPTPort();
      if (pagamentiTelematiciRPT != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pagamentiTelematiciRPT)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pagamentiTelematiciRPT)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pagamentiTelematiciRPT != null)
      ((javax.xml.rpc.Stub)pagamentiTelematiciRPT)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT getPagamentiTelematiciRPT() {
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT;
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta nodoChiediStatoRPT(NodoChiediStatoRPT richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediStatoRPT(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediListaPendentiRPTRisposta nodoChiediListaPendentiRPT(NodoChiediListaPendentiRPT richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediListaPendentiRPT(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoInviaRPTRisposta nodoInviaRPT(gov.telematici.pagamenti.ws.NodoInviaRPT bodyrichiesta, gov.telematici.pagamenti.ws.ppthead.IntestazionePPT header) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoInviaRPT(bodyrichiesta, header);
  }
  
  public gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta nodoInviaCarrelloRPT(gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT bodyrichiesta, gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT header) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoInviaCarrelloRPT(bodyrichiesta, header);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta nodoChiediCopiaRT(NodoChiediCopiaRT richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediCopiaRT(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta nodoChiediInformativaPSP(NodoChiediInformativaPSP richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediInformativaPSP(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediElencoQuadraturePARisposta nodoChiediElencoQuadraturePA(NodoChiediElencoQuadraturePA richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediElencoQuadraturePA(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediQuadraturaPARisposta nodoChiediQuadraturaPA(NodoChiediQuadraturaPA richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediQuadraturaPA(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta nodoChiediElencoFlussiRendicontazione(NodoChiediElencoFlussiRendicontazione richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediElencoFlussiRendicontazione(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta nodoChiediFlussoRendicontazione(NodoChiediFlussoRendicontazione richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediFlussoRendicontazione(richiesta);
  }
  
  public gov.telematici.pagamenti.ws.NodoInviaRichiestaStornoRisposta nodoInviaRichiestaStorno(NodoInviaRichiestaStorno richiesta) throws java.rmi.RemoteException{
    if (pagamentiTelematiciRPT == null)
      _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoInviaRichiestaStorno(richiesta);
  }
  
  @Override
  public NodoChiediSceltaWISPRisposta nodoChiediSceltaWISP(NodoChiediSceltaWISP richiesta) throws RemoteException {
	if (pagamentiTelematiciRPT == null)
	  _initPagamentiTelematiciRPTProxy();
    return pagamentiTelematiciRPT.nodoChiediSceltaWISP(richiesta);
  }

   @Override
   public NodoInviaRispostaRevocaRisposta nodoInviaRispostaRevoca(NodoInviaRispostaRevoca richiesta)
		throws RemoteException {
	  
	   if (pagamentiTelematiciRPT == null)
			  _initPagamentiTelematiciRPTProxy();
	   return pagamentiTelematiciRPT.nodoInviaRispostaRevoca(richiesta);
  }

	@Override
	public NodoPAChiediInformativaPARisposta nodoPAChiediInformativaPA(NodoPAChiediInformativaPA bodyrichiesta)
			throws RemoteException {

		   if (pagamentiTelematiciRPT == null)
				  _initPagamentiTelematiciRPTProxy();
		   return pagamentiTelematiciRPT.nodoPAChiediInformativaPA(bodyrichiesta);

		
	}
  
}