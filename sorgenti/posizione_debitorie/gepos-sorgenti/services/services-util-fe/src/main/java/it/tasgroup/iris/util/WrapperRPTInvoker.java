package it.tasgroup.iris.util;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.axis.MessageContext;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.axis.server.AxisServer;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.xml.sax.helpers.AttributesImpl;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRT;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta;
import gov.telematici.pagamenti.ws.NodoChiediSceltaWISP;
import gov.telematici.pagamenti.ws.NodoChiediSceltaWISPRisposta;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPT;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta;
import gov.telematici.pagamenti.ws.NodoInviaCarrelloRPT;
import gov.telematici.pagamenti.ws.NodoInviaCarrelloRPTRisposta;
import gov.telematici.pagamenti.ws.NodoInviaRPT;
import gov.telematici.pagamenti.ws.NodoInviaRPTRisposta;
import gov.telematici.pagamenti.ws.TipoElementoListaRPT;
import gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.gov.digitpa.schemas.x2011.pagamenti.RPTDocument;
import it.gov.digitpa.schemas.x2011.pagamenti.RTDocument;
import it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTProxy;
import it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTbindingStub;
import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.payment.helper.FornitorePagamentoNodoSPC;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.services.util.enumeration.EnumCategoriaEvento;
import it.tasgroup.services.util.enumeration.EnumComponente;
import it.tasgroup.services.util.enumeration.EnumSottoTipoEvento;
import it.tasgroup.services.util.enumeration.EnumTipoEventiNDP;
import it.tasgroup.services.util.enumeration.EnumTipoVersamento;

public class WrapperRPTInvoker {

	private static  ConfigurationPropertyLoader conf;

	private static final Logger logger = Logger.getLogger(WrapperRPTInvoker.class);
	
	private static final Boolean IS_URL_REDIRECT_AUTH 	= getConf().getBooleanProperty("nodopagamentispc.urlRedirectAndAuthenticate");
	private static final String  URL_NODO_PAGAMENTI 	= getConf().getProperty("nodopagamentispc.services.url");
	private static final String  AUTH_USR 				= getConf().getProperty("nodopagamentispc.autenthication.user");
	private static final String  AUTH_PWD 				= getConf().getProperty("nodopagamentispc.autenthication.password");


	private  GiornaleEventiBusinessLocal giornaleEventiBean;

	public  NodoChiediCopiaRTRisposta nodoChiediCopiaRT(NodoChiediCopiaRT richiestaCopiaRT) throws RemoteException {

		logger.info("nodoChiediCopiaRT BEGIN");
		if (logger.isInfoEnabled()) {
			String x = getStringValue(richiestaCopiaRT);
			logger.info("*****nodoChiediCopiaRT****\n "+ x + "\n*********************");
		}
		//----
		String url = IS_URL_REDIRECT_AUTH ? URL_NODO_PAGAMENTI + "nodoChiediCopiaRT" : URL_NODO_PAGAMENTI;
		
		PagamentiTelematiciRPTProxy proxy = getPagamentiTelematiciRPTProxy(url);
		//----
		// inserimento richiesta su db
		GiornaleEventiExtDTO g = createGiornaleEvDTO(richiestaCopiaRT, null);
		getGiornaleEventiBusinessLocal().save(g);

		NodoChiediCopiaRTRisposta resp=proxy.nodoChiediCopiaRT(richiestaCopiaRT);
		// inserimento risposta su db
		g =  createGiornaleEvDTO(resp,g);
		getGiornaleEventiBusinessLocal().save(g);
		if (logger.isInfoEnabled()) {
			String x = getStringValue(resp);
			logger.info("*****nodoChiediCopiaRT****\n "+ x + "\n*********************");
		}
		logger.info("nodoChiediCopiaRT END");
		return resp;
	}
	
	private PagamentiTelematiciRPTProxy getPagamentiTelematiciRPTProxy(String url) {
		PagamentiTelematiciRPTProxy proxy = new PagamentiTelematiciRPTProxy(url);
		if (IS_URL_REDIRECT_AUTH) {
			if (AUTH_USR != null && !AUTH_USR.isEmpty()) {
		      ((PagamentiTelematiciRPTbindingStub) proxy.getPagamentiTelematiciRPT()).setUsername(AUTH_USR);
		      ((PagamentiTelematiciRPTbindingStub) proxy.getPagamentiTelematiciRPT()).setPassword(AUTH_PWD);
		    }
		}
		return proxy;
	}

    public  NodoChiediStatoRPTRisposta nodoChiediStatoRPT(NodoChiediStatoRPT richiestaStatoRPT) throws RemoteException  {
    	logger.info("nodoChiediStatoRPT BEGIN");
		if (logger.isInfoEnabled()) {
			String x = getStringValue(richiestaStatoRPT);
			logger.info("*****nodoChiediStatoRPT****\n "+ x + "\n*********************");
		}
    	String url = IS_URL_REDIRECT_AUTH ? URL_NODO_PAGAMENTI + "nodoChiediStatoRPT" : URL_NODO_PAGAMENTI;

    	PagamentiTelematiciRPTProxy proxy = getPagamentiTelematiciRPTProxy(url);
    	
		// inserimento richiesta su db
		GiornaleEventiExtDTO g = createGiornaleEvDTO(richiestaStatoRPT, null);
		getGiornaleEventiBusinessLocal().save(g);

		NodoChiediStatoRPTRisposta resp=proxy.nodoChiediStatoRPT(richiestaStatoRPT);
		// inserimento risposta su db
		g =  createGiornaleEvDTO(resp,g);
		getGiornaleEventiBusinessLocal().save(g);
		if (logger.isInfoEnabled()) {
			String x = getStringValue(resp);
			logger.info("*****nodoChiediStatoRPT****\n "+ x + "\n*********************");
		}
		logger.info("nodoChiediStatoRPT END");
		return resp;
    }

    public  NodoInviaRPTRisposta nodoInviaRPT(NodoInviaRPT bodyrichiesta, IntestazionePPT header) throws RemoteException {
    	logger.info("nodoChiediStatoRPT BEGIN");
		if (logger.isInfoEnabled()) {
			String x = getStringValue(bodyrichiesta);
			logger.info("*****nodoInviaRPT body ****\n "+ x + "\n*********************");
			x = getStringValue(header);
			logger.info("*****nodoInviaRPT header ****\n "+ x + "\n*********************");
		}

    	String url = IS_URL_REDIRECT_AUTH ? URL_NODO_PAGAMENTI + "nodoInviaRPT" : URL_NODO_PAGAMENTI;
    	PagamentiTelematiciRPTProxy proxy = getPagamentiTelematiciRPTProxy(url);
    	
		// inserimento richiesta su db
		GiornaleEventiExtDTO g = createGiornaleEvDTO(bodyrichiesta, header);
		getGiornaleEventiBusinessLocal().save(g);
		
		NodoInviaRPTRisposta resp=proxy.nodoInviaRPT(bodyrichiesta,header);
		// inserimento risposta su db
		g =  createGiornaleEvDTO(resp,g);
		getGiornaleEventiBusinessLocal().save(g);
		if (logger.isInfoEnabled()) {
			String x = getStringValue(resp);
			logger.info("*****nodoInviaRPT****\n "+ x + "\n*********************");
		}
		logger.info("nodoInviaRPT END");
		return resp;
    }

	public NodoChiediSceltaWISPRisposta nodoChiediSceltaWISP(String identificativoIntermediarioPA,
															 String identificativoStazioneIntermediarioPA,
															 String password,
															 String identificativoDominio,
															 String keyPA,
															 String keyWISP) throws RemoteException {

		logger.info("nodoChiediSceltaWISP BEGIN");

		NodoChiediSceltaWISP nodoChiediSceltaWISP = new NodoChiediSceltaWISP(
				identificativoIntermediarioPA,
				identificativoStazioneIntermediarioPA,
				password,
				identificativoDominio,
				keyPA,
				keyWISP
		);

		
		if (logger.isInfoEnabled()) {
			String x = getStringValue(nodoChiediSceltaWISP);
			logger.info("*****nodoChiediSceltaWISP body ****\n "+ x + "\n*********************");
		}

		String url = IS_URL_REDIRECT_AUTH ? URL_NODO_PAGAMENTI + "nodoChiediSceltaWISP" : URL_NODO_PAGAMENTI;
		final PagamentiTelematiciRPTProxy pagamentiTelematiciRPTProxy = getPagamentiTelematiciRPTProxy(url);
		final NodoChiediSceltaWISPRisposta nodoChiediSceltaWISPRisposta = pagamentiTelematiciRPTProxy.nodoChiediSceltaWISP(nodoChiediSceltaWISP);

		// inserimento risposta su db
		if (logger.isInfoEnabled()) {
			String x = getStringValue(nodoChiediSceltaWISPRisposta);
			logger.info("*****nodoChiediSceltaWISP****\n "+ x + "\n*********************");
		}

		return nodoChiediSceltaWISPRisposta;
	}
	
	

    public NodoInviaCarrelloRPTRisposta nodoInviaCarrelloRPT(NodoInviaCarrelloRPT bodyrichiesta, IntestazioneCarrelloPPT header) throws RemoteException {

    	logger.info("nodoInviaCarrelloRPT BEGIN");
		if (logger.isInfoEnabled()) {
			String x = getStringValue(bodyrichiesta);
			logger.info("*****nodoInviaCarrelloRPT body ****\n "+ x + "\n*********************");
			x = getStringValue(header);
			logger.info("*****nodoInviaCarrelloRPT header ****\n "+ x + "\n*********************");
		}
    	String url = IS_URL_REDIRECT_AUTH ? URL_NODO_PAGAMENTI + "nodoInviaCarrelloRPT" : URL_NODO_PAGAMENTI;
		PagamentiTelematiciRPTProxy proxy = getPagamentiTelematiciRPTProxy(url);
		// inserimento richiesta su db
		GiornaleEventiExtDTO[] g = createGiornaleEvDTOs(bodyrichiesta, header);

		NodoInviaCarrelloRPTRisposta resp=null;
		try {
			resp=proxy.nodoInviaCarrelloRPT(bodyrichiesta, header);
		} catch (RemoteException re) {
			for (GiornaleEventiExtDTO gee : g) {
				gee.setFaultId(header.getIdentificativoIntermediarioPA());
				gee.setFaultCode("PAA_SYSTEM_ERROR");
				gee.setFaultString("Errore generico");
				gee.setFaultDescription("Nodo dei Pagamenti non raggiungibile");
				gee.setEsito("KO");
			}
			getGiornaleEventiBusinessLocal().save(g);
			throw re;
		}
		
		getGiornaleEventiBusinessLocal().save(g);
		
		
		// inserimento risposta su db
		if (logger.isInfoEnabled()) {
			String x = getStringValue(resp);
			logger.info("*****nodoInviaCarrelloRPT****\n "+ x + "\n*********************");
		}
		g =  createGiornaleEvDTOs(resp,g);
		getGiornaleEventiBusinessLocal().save(g);

		logger.info("nodoInviaCarrelloRPT END");
		return resp;
    }
    private  GiornaleEventiExtDTO createGiornaleEvDTO(Object a,Object b) {
    	GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
    	g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));

    	if (a instanceof NodoChiediCopiaRT) {
    		NodoChiediCopiaRT ncc = (NodoChiediCopiaRT)a;
    		g.setIdDominio(ncc.getIdentificativoDominio());
    		g.setIdUnivocoVersamento(ncc.getIdentificativoUnivocoVersamento());
    		g.setCodiceContestoPagamento(ncc.getCodiceContestoPagamento());
    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
    		g.setComponente(EnumComponente.PA);
    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediCopiaRT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
    		g.setIdFruitore(getConf().getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
    		g.setIdErogatore("NodoDeiPagamentiSPC");
    		g.setIdStazioneIntermediarioPA(ncc.getIdentificativoStazioneIntermediarioPA());
    		g.setIdPSP("n/a");
    		g.setCanalePagamento(null);//TODO??
    		g.setParametriSpecificiInterfaccia(null);
    		g.setEsito(null);
    		g.setIdEGov(null);
    		g.setStoreDocumentoNDP(false);
    		g.setOpInserimento("IRIS");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		return g;
    	}
    	if (a instanceof NodoChiediStatoRPT){
    		NodoChiediStatoRPT ncc = (NodoChiediStatoRPT)a;
    		g.setIdDominio(ncc.getIdentificativoDominio());
    		g.setIdUnivocoVersamento(ncc.getIdentificativoUnivocoVersamento());
    		g.setCodiceContestoPagamento(ncc.getCodiceContestoPagamento());
    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
    		g.setComponente(EnumComponente.PA);
    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediStatoRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
    		g.setIdFruitore(getConf().getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
    		g.setIdErogatore("NodoDeiPagamentiSPC");
    		g.setIdStazioneIntermediarioPA(ncc.getIdentificativoStazioneIntermediarioPA());
    		g.setIdPSP("n/a");
    		g.setCanalePagamento(null);//TODO??
    		g.setParametriSpecificiInterfaccia(null);
    		g.setEsito(null);
    		g.setIdEGov(null);
    		g.setStoreDocumentoNDP(false);
    		g.setOpInserimento("IRIS");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		return g;

    	}
    	if (a instanceof NodoInviaRPT) {
    		NodoInviaRPT ncc = (NodoInviaRPT)a;
    		IntestazionePPT intppt = (IntestazionePPT)b;
    		g.setIdDominio(intppt.getIdentificativoDominio());
    		g.setIdUnivocoVersamento(intppt.getIdentificativoUnivocoVersamento());
    		g.setCodiceContestoPagamento(intppt.getCodiceContestoPagamento());
    		g.setComponente(EnumComponente.PA);
    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
    		g.setTipoEvento(EnumTipoEventiNDP.nodoInviaRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
    		g.setIdFruitore(getConf().getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
    		g.setIdErogatore("NodoDeiPagamentiSPC");
    		g.setIdPSP(ncc.getIdentificativoPSP());
    		g.setIdStazioneIntermediarioPA(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setCanalePagamento(null);//TODO??
    		g.setParametriSpecificiInterfaccia(null);
    		g.setEsito(null);
    		g.setIdEGov(null);
    		//
    		g.setStoreDocumentoNDP(true);
    		g.setTentativo(1);
    		g.setTipo("RP");
    		g.setDocumento(ncc.getRpt());
    		try {
    		   RPTDocument d =getRPTDocument(new String(ncc.getRpt()));
    		   g.setTipoVersamento(EnumTipoVersamento.valueOf(d.getRPT().getDatiVersamento().getTipoVersamento().toString()));

    		} catch (Exception e) {

    		}
    		g.setDimensione(ncc.getRpt().length);
    		g.setOpInserimento("IRIS");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		return g;

    	}
    	if (a instanceof NodoChiediCopiaRTRisposta) {
    		NodoChiediCopiaRTRisposta ncc = (NodoChiediCopiaRTRisposta)a;
    		g = (GiornaleEventiExtDTO)b;
    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediCopiaRT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		g.setEsito("OK");
    		g.setStoreDocumentoNDP(true);
    		g.setTentativo(1);
    		g.setTipo("RT");
    		g.setDocumento(ncc.getRt());
    		g.setDimensione(ncc.getRt().length);
    		g.setOpInserimento("IRIS");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		if (ncc.getFault()!=null){
    			g.setFaultId(ncc.getFault().getId());
    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
    			g.setFaultString(ncc.getFault().getFaultString());
    			g.setFaultCode(ncc.getFault().getFaultCode());

    			if (ncc.getFault().getDescription()!=null) {
     			   String descr = null;
     			   if (ncc.getFault().getDescription().length()> 250) {
     				   descr = ncc.getFault().getDescription().substring(0, 249);
     			   } else {
     				   descr = ncc.getFault().getDescription();
     			   }
     			   g.setFaultDescription(descr);
     			}
    		}
    		else {
    			g.setEsito("OK");
    		}
    		return g;

    	}
    	if (a instanceof NodoChiediStatoRPTRisposta) {
    		NodoChiediStatoRPTRisposta ncc = (NodoChiediStatoRPTRisposta)a;
    		g = (GiornaleEventiExtDTO)b;
    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediStatoRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		if (ncc.getEsito()!=null) {
    		   g.setEsito(ncc.getEsito().getStato());
    		} else {
    			g.setEsito("KO");
    		}
    		g.setStoreDocumentoNDP(false);
    		g.setOpInserimento("IRIS");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		if (ncc.getFault()!=null){
    			g.setFaultId(ncc.getFault().getId());
    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
    			g.setFaultString(ncc.getFault().getFaultString());
    			g.setFaultCode(ncc.getFault().getFaultCode());
    			if (ncc.getFault().getDescription()!=null) {
      			   String descr = null;
      			   if (ncc.getFault().getDescription().length()> 250) {
      				   descr = ncc.getFault().getDescription().substring(0, 249);
      			   } else {
      				   descr = ncc.getFault().getDescription();
      			   }
      			   g.setFaultDescription(descr);
      			}
    		}
    		return g;
    	}
        if (a instanceof NodoInviaRPTRisposta) {
        	NodoInviaRPTRisposta ncc = (NodoInviaRPTRisposta)a;
    		g = (GiornaleEventiExtDTO)b;
    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		g.setTipoEvento(EnumTipoEventiNDP.nodoInviaRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		g.setEsito(ncc.getEsito());
    		g.setStoreDocumentoNDP(false);
    		g.setOpInserimento("IRIS");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		if (ncc.getFault()!=null){
    			g.setFaultId(ncc.getFault().getId());
    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
    			g.setFaultString(ncc.getFault().getFaultString());
    			g.setFaultCode(ncc.getFault().getFaultCode());
    			if (ncc.getFault().getDescription()!=null) {
    			   String descr = null;
    			   if (ncc.getFault().getDescription().length()> 250) {
    				   descr = ncc.getFault().getDescription().substring(0, 249);
    			   } else {
    				   descr = ncc.getFault().getDescription();
    			   }
    			   g.setFaultDescription(descr);
    			}
    		}
    		return g;
        }
    	return null;
    }

    private  GiornaleEventiExtDTO[] createGiornaleEvDTOs(Object a,Object b) {

    	if (a instanceof NodoInviaCarrelloRPT) {
    		NodoInviaCarrelloRPT ncc = (NodoInviaCarrelloRPT)a;
    		int l= ncc.getListaRPT().getElementoListaRPT().length;
    		GiornaleEventiExtDTO[] out = new GiornaleEventiExtDTO[l];


    		IntestazioneCarrelloPPT intppt = (IntestazioneCarrelloPPT)b;
    		for (int i=0;i< l; i++) {
    			GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
    			TipoElementoListaRPT t = (ncc.getListaRPT().getElementoListaRPT(i));

    			g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    			g.setIdDominio(t.getIdentificativoDominio()); //
        		g.setIdUnivocoVersamento(t.getIdentificativoUnivocoVersamento()); //
        		g.setCodiceContestoPagamento(t.getCodiceContestoPagamento());    //
        		g.setComponente(EnumComponente.PA);
        		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
        		g.setTipoEvento(EnumTipoEventiNDP.nodoInviaCarrelloRPT);
        		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
        		g.setIdFruitore(getConf().getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
        		g.setIdErogatore("NodoDeiPagamentiSPC");
        		g.setIdPSP(ncc.getIdentificativoPSP());
        		g.setIdStazioneIntermediarioPA(intppt.getIdentificativoStazioneIntermediarioPA());
        		g.setCanalePagamento(null);//TODO??
        		g.setParametriSpecificiInterfaccia(null);
        		g.setEsito(null);
        		g.setIdEGov(null);
        		//
        		g.setStoreDocumentoNDP(true);
        		g.setTentativo(1);
        		g.setTipo("RP");
        		g.setDocumento(t.getRpt());

        		try {
         		   RPTDocument d =getRPTDocument(new String(t.getRpt()));
         		   g.setTipoVersamento(EnumTipoVersamento.valueOf(d.getRPT().getDatiVersamento().getTipoVersamento().toString()));

         		} catch (Exception e) {

         		}
         		g.setDimensione(t.getRpt().length);
         		g.setOpInserimento("IRIS");
         		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));

         		out[i] = g;
    		}

    		return out;

    	}
    	if (a instanceof NodoInviaCarrelloRPTRisposta) {
        	NodoInviaCarrelloRPTRisposta ncc = (NodoInviaCarrelloRPTRisposta)a;


    		GiornaleEventiExtDTO[] out = (GiornaleEventiExtDTO[])b;

    		if (ncc.getListaErroriRPT() == null || ncc.getListaErroriRPT().getFault().length == 0) {
    			
    			GiornaleEventiExtDTO g = out[0];
    		    g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		    g.setTipoEvento(EnumTipoEventiNDP.nodoInviaCarrelloRPT);
    		    g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		    g.setEsito(ncc.getEsitoComplessivoOperazione());
    		    g.setStoreDocumentoNDP(false);
    		    g.setOpInserimento("IRIS");
    		    g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		    if (ncc.getFault()!=null){
    			   g.setFaultId(ncc.getFault().getId());
    			   g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
    			   g.setFaultString(ncc.getFault().getFaultString());
    			   g.setFaultCode(ncc.getFault().getFaultCode());
    			   if (ncc.getFault().getDescription()!=null) {
         			   String descr = null;
         			   if (ncc.getFault().getDescription().length()> 250) {
         				   descr = ncc.getFault().getDescription().substring(0, 249);
         			   } else {
         				   descr = ncc.getFault().getDescription();
         			   }
         			   g.setFaultDescription(descr);
         			}
    		    }
    		    out = new GiornaleEventiExtDTO[1];
    		    out[0] = g;
    		} else {
    			FaultBean[] listaErroriRPT = ncc.getListaErroriRPT().getFault();
    			for (int i = 0; i < listaErroriRPT.length; i++) {
        			GiornaleEventiExtDTO giornaleEvExtDto = new GiornaleEventiExtDTO();
        			//
        			giornaleEvExtDto.setIdDominio(out[0].getIdDominio()); //
            		giornaleEvExtDto.setIdUnivocoVersamento(out[0].getIdUnivocoVersamento()); //
            		giornaleEvExtDto.setCodiceContestoPagamento(out[0].getCodiceContestoPagamento());    //
            		giornaleEvExtDto.setComponente(EnumComponente.PA);
            		giornaleEvExtDto.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
            		giornaleEvExtDto.setIdFruitore(getConf().getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
            		giornaleEvExtDto.setIdErogatore("NodoDeiPagamentiSPC");
            		giornaleEvExtDto.setIdPSP(out[0].getIdPSP());
            		giornaleEvExtDto.setIdStazioneIntermediarioPA(out[0].getIdStazioneIntermediarioPA());
            		giornaleEvExtDto.setTipoVersamento(out[0].getTipoVersamento());
        			//
        			giornaleEvExtDto.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
        		    giornaleEvExtDto.setTipoEvento(EnumTipoEventiNDP.nodoInviaCarrelloRPT);
        		    giornaleEvExtDto.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
        		    giornaleEvExtDto.setEsito(ncc.getEsitoComplessivoOperazione());
        		    giornaleEvExtDto.setStoreDocumentoNDP(false);
        		    giornaleEvExtDto.setOpInserimento("IRIS");
        		    giornaleEvExtDto.setTsInserimento(new Timestamp(System.currentTimeMillis()));

        		    FaultBean faultBean = ncc.getListaErroriRPT().getFault(i);
        		    if (faultBean == null)
        		    	faultBean = ncc.getFault();
        		    giornaleEvExtDto.setFaultId(faultBean.getId());
    			    giornaleEvExtDto.setFaultSerial(faultBean.getSerial() == null ? 0 : faultBean.getSerial());
    			    giornaleEvExtDto.setFaultString(faultBean.getFaultString());
    			    giornaleEvExtDto.setFaultCode(faultBean.getFaultCode());
    			    if (faultBean.getDescription() != null)
    			    	giornaleEvExtDto.setFaultDescription(truncateDesc(faultBean.getDescription(), 249));
    			    giornaleEvExtDto.setOriginalFaultCode(faultBean.getOriginalFaultCode());
    			    giornaleEvExtDto.setOriginalFaultString(faultBean.getOriginalFaultString());
    			    if (faultBean.getOriginalDescription() != null)
    			    	giornaleEvExtDto.setOriginalDescription(truncateDesc(faultBean.getOriginalDescription(), 249));

             		out[i] = giornaleEvExtDto;
        		}
    		}
    		return out;
        }
    	return null;
    }
    
    private String truncateDesc(String value, int length) {
    	if (value != null && value.length() > length)
    		value = value.substring(0, length);
    	return value;
    }
    
    private static ConfigurationPropertyLoader getConf() {
    	if (conf==null) {
    		conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
    	}
    	return conf;

    }
    private GiornaleEventiBusinessLocal getGiornaleEventiBusinessLocal(){
    	if (giornaleEventiBean==null) {
    	  try {
    	     giornaleEventiBean= (GiornaleEventiBusinessLocal) ServiceLocator.getSLSBProxy("giornaleEventiBusinessBean");
    	  } catch (Throwable t){
    		  t.printStackTrace();
    	  }
        }
        return giornaleEventiBean;
    }

    private RTDocument getRTDocument(String xmlString) throws Exception {
       Collection validationErrors = new ArrayList();
	   XmlOptions validationOptions = new XmlOptions();
	   validationOptions.setErrorListener(validationErrors);

	   RTDocument xmlObj = RTDocument.Factory.parse(xmlString);

	   if (!xmlObj.validate(validationOptions)) {
	    	StringBuffer errors = new StringBuffer(
		    		"Errori di validazione xml RT: \n");
		    Iterator iter = validationErrors.iterator();
		    while (iter.hasNext()) {
		    	errors.append(iter.next()).append("\n");
		    }
		    Tracer.error(FornitorePagamentoNodoSPC.class.getName(),
				"parseXmlRT", errors.toString());
		    throw new Exception(errors.toString());
	   }
	   return xmlObj;
    }

    private RPTDocument getRPTDocument(String xmlString) throws Exception {
        Collection validationErrors = new ArrayList();
 	   XmlOptions validationOptions = new XmlOptions();
 	   validationOptions.setErrorListener(validationErrors);

 	   RPTDocument xmlObj = RPTDocument.Factory.parse(xmlString);

 	   if (!xmlObj.validate(validationOptions)) {
 	    	StringBuffer errors = new StringBuffer(
 		    		"Errori di validazione xml RPT: \n");
 		    Iterator iter = validationErrors.iterator();
 		    while (iter.hasNext()) {
 		    	errors.append(iter.next()).append("\n");
 		    }
 		    Tracer.error(FornitorePagamentoNodoSPC.class.getName(),
 				"parseXmlRPT", errors.toString());
 		    throw new Exception(errors.toString());
 	   }
 	   return xmlObj;
     }

     private String getStringValue(Object o) {
    	  try {
 				String r= serializeAxisObject(o,true,true);
 				return r;
 			} catch (Throwable t) {
 				return null;
 			}

     }

     private  String serializeAxisObject(final Object obj, final boolean removeNamespaces,
 			final boolean prettyPrint) throws Exception
 			{
 		final StringWriter outStr = new StringWriter();
 		final TypeDesc typeDesc = getAxisTypeDesc(obj);
 		QName qname = typeDesc.getXmlType();
 		String lname = qname.getLocalPart();
 		if (lname.startsWith(">") && lname.length() > 1)
 			lname = lname.substring(1);

 		qname = removeNamespaces ? new QName(lname)
 		: new QName(qname.getNamespaceURI(), lname);
 		final AxisServer server = new AxisServer();
 		final BeanSerializer ser = new BeanSerializer(obj.getClass(), qname, typeDesc);
 		final SerializationContext ctx = new SerializationContext(outStr, new MessageContext(server));
 		ctx.setSendDecl(false);
 		ctx.setDoMultiRefs(false);
 		ctx.setPretty(prettyPrint);
 		try
 		{
 			ser.serialize(qname, new AttributesImpl(), obj, ctx);
 		}
 		catch (final Exception e)
 		{
 			throw new Exception("Unable to serialize object "
 					+ obj.getClass().getName(), e);
 		}

 		String xml = outStr.toString();

 		if (removeNamespaces)
 		{
 			// remove any namespace attributes
 			xml = xml.replaceAll(" xmlns[:=].*?\".*?\"", "")
 					.replaceAll(" xsi:type=\".*?\"", "");
 		}

 		return(xml);
 	}
 	/**
      * Return the Axis TypeDesc object for the passed Axis JavaBean.
      *
      * @param obj The Axis JavaBean object.
      *
      * @return The Axis TypeDesc for the JavaBean.
      * @throws AxisObjectException If the passed object is not an Axis JavaBean.
      */
     private  TypeDesc getAxisTypeDesc(final Object obj) throws Exception
     {
         final Class objClass = obj.getClass();
         try
         {
             final Method methodGetTypeDesc = objClass.getMethod("getTypeDesc", new Class[] {});
             final TypeDesc typeDesc = (TypeDesc) methodGetTypeDesc.invoke(obj, new Object[] {});
             return(typeDesc);
         }
         catch (final Exception e)
         {
             throw new Exception("Unable to get Axis TypeDesc for "
 		+ objClass.getName(), e);
         }
     }

}
