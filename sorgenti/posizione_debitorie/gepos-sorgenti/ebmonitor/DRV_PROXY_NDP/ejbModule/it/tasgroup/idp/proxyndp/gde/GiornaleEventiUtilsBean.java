package it.tasgroup.idp.proxyndp.gde;

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazione;
import gov.telematici.pagamenti.ws.NodoChiediElencoFlussiRendicontazioneRisposta;
import gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazione;
import gov.telematici.pagamenti.ws.NodoChiediFlussoRendicontazioneRisposta;
import gov.telematici.pagamenti.ws.NodoChiediInformativaPSP;
import gov.telematici.pagamenti.ws.NodoChiediInformativaPSPRisposta;
import gov.telematici.pagamenti.ws.NodoInviaRispostaRevoca;
import gov.telematici.pagamenti.ws.NodoInviaRispostaRevocaRisposta;
import it.tasgroup.gde.GiornaleEventi;
import it.tasgroup.gde.GiornaleEventiDocumentiNDP;
import it.tasgroup.ge.util.GestoreEventiConstant;
import it.tasgroup.idp.gateway.enums.EnumCategoriaEvento;
import it.tasgroup.idp.gateway.enums.EnumComponente;
import it.tasgroup.idp.gateway.enums.EnumSottoTipoEvento;
import it.tasgroup.idp.gateway.enums.EnumTipoEventiNDP;
import it.tasgroup.idp.gateway.enums.EnumTipoVersamento;
import it.tasgroup.idp.proxyndp.utils.NDPConstants;

@Stateless
public class GiornaleEventiUtilsBean implements IGiornaleEventiUtils {
	 
	/*** Resources ***/
	@Resource
	private EJBContext ctx;
	
	@PersistenceContext(unitName = GestoreEventiConstant.IdpAppJpaXA)
	private EntityManager manager;

	private  GiornaleEventiExtDTO createGiornaleEvDTO(Object a,Object b) {
		
		    NDPConstants ndpC = new NDPConstants();
		    
	    	GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
	    	g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
	    	
	    	if (a instanceof NodoChiediFlussoRendicontazione) {
	    		NodoChiediFlussoRendicontazione ncc = (NodoChiediFlussoRendicontazione)a;
	    		g.setIdDominio(ncc.getIdentificativoDominio());
	    		g.setIdUnivocoVersamento("n/a");
	    		g.setCodiceContestoPagamento("n/a");
	    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
	    		g.setComponente(EnumComponente.PA);
	    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediFlussoRendicontazione);
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
	    		try {
		    		  g.setIdFruitore(ndpC.getCodiceStazionePA());
		    	} catch (Throwable t){}          
	    		g.setIdErogatore("NodoDeiPagamentiSPC");
	    		g.setIdStazioneIntermediarioPA(ncc.getIdentificativoStazioneIntermediarioPA());
	    		g.setIdPSP(ncc.getIdentificativoPSP()!=null?ncc.getIdentificativoPSP():"n/a");
	    		g.setCanalePagamento(null);
	    		g.setParametriSpecificiInterfaccia(ncc.getIdentificativoFlusso());// inserisco qui identificativo flusso
	    		g.setEsito(null);
	    		g.setIdEGov(null);
	    		g.setStoreDocumentoNDP(false);
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		return g;
	    	} 
	    	if (a instanceof NodoChiediInformativaPSP){
	    		NodoChiediInformativaPSP ncc = (NodoChiediInformativaPSP)a;
	    		g.setIdDominio(ncc.getIdentificativoDominio());
	    		g.setIdUnivocoVersamento("n/a");
	    		g.setCodiceContestoPagamento("n/a");
	    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
	    		g.setComponente(EnumComponente.PA);
	    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediInformativaPSP);
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
	    		try {
	    		  g.setIdFruitore(ndpC.getCodiceStazionePA());
	    		} catch (Throwable t){}
	    		g.setIdErogatore("NodoDeiPagamentiSPC");
	    		g.setIdStazioneIntermediarioPA(ncc.getIdentificativoStazioneIntermediarioPA());
	    		g.setIdPSP(ncc.getIdentificativoPSP()!=null?ncc.getIdentificativoPSP():"n/a");
	    		g.setCanalePagamento(null);//TODO??
	    		g.setParametriSpecificiInterfaccia(null);
	    		g.setEsito(null);
	    		g.setIdEGov(null);
	    		g.setStoreDocumentoNDP(false);
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		return g;
	    		
	    	} 
	    	if (a instanceof NodoChiediElencoFlussiRendicontazione) {
	    		NodoChiediElencoFlussiRendicontazione ncc = (NodoChiediElencoFlussiRendicontazione)a;
	    		
	    		g.setIdDominio(ncc.getIdentificativoDominio());
	    		g.setIdUnivocoVersamento("n/a");
	    		g.setCodiceContestoPagamento("n/a");    		
	    		g.setComponente(EnumComponente.PA);
	    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
	    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediElencoFlussiRendicontazione);
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
	    		try {
		    		g.setIdFruitore(ndpC.getCodiceStazionePA());
		    	} catch (Throwable t){}
	    		g.setIdErogatore("NodoDeiPagamentiSPC");
	    		g.setIdPSP(ncc.getIdentificativoPSP()!=null?ncc.getIdentificativoPSP():"n/a");
	    		g.setIdStazioneIntermediarioPA(ncc.getIdentificativoStazioneIntermediarioPA());
	    		g.setCanalePagamento(null);//TODO??
	    		g.setParametriSpecificiInterfaccia(null);
	    		g.setEsito(null);
	    		g.setIdEGov(null);	    		
	    		g.setStoreDocumentoNDP(false);
	    		g.setTentativo(1);	    		
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		
	    		return g;
	    		
	    	} 
	    	if (a instanceof NodoChiediFlussoRendicontazioneRisposta) {
	    		NodoChiediFlussoRendicontazioneRisposta ncc = (NodoChiediFlussoRendicontazioneRisposta)a;
	    		g = (GiornaleEventiExtDTO)b;
	    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediFlussoRendicontazione);
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
	    		g.setEsito("OK");
	    		g.setStoreDocumentoNDP(false);
	    		g.setTentativo(1);	    		
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));    		
	    		if (ncc.getFault()!=null){
	    			g.setFaultId(ncc.getFault().getId());
	    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
	    			g.setFaultString(ncc.getFault().getFaultString());
	    			g.setFaultCode(ncc.getFault().getFaultCode());
	    			g.setFaultDescription(ncc.getFault().getDescription());
	    			g.setEsito("KO");
	    		}
	    		else {
	    			g.setEsito("OK");
	    		}
	    		return g;
	    		
	    	}
	    	if (a instanceof NodoChiediInformativaPSPRisposta) {
	    		NodoChiediInformativaPSPRisposta ncc = (NodoChiediInformativaPSPRisposta)a;
	    		g = (GiornaleEventiExtDTO)b;
	    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediInformativaPSP);
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);	    		
	    		g.setStoreDocumentoNDP(false);   
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		if (ncc.getFault()!=null){
	    			g.setFaultId(ncc.getFault().getId());
	    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
	    			g.setFaultString(ncc.getFault().getFaultString());
	    			g.setFaultCode(ncc.getFault().getFaultCode());
	    			g.setFaultDescription(ncc.getFault().getDescription());    
	    			g.setEsito("KO");
	    		}else {
	    			g.setEsito("OK");
	    		}
	    		
	    		return g;
	    	}
	        if (a instanceof NodoChiediElencoFlussiRendicontazioneRisposta) {
	        	NodoChiediElencoFlussiRendicontazioneRisposta ncc = (NodoChiediElencoFlussiRendicontazioneRisposta)a;
	    		g = (GiornaleEventiExtDTO)b;
	    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoChiediElencoFlussiRendicontazione); 
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);	    		
	    		g.setStoreDocumentoNDP(false);    	
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		if (ncc.getFault()!=null){
	    			g.setFaultId(ncc.getFault().getId());
	    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
	    			g.setFaultString(ncc.getFault().getFaultString());
	    			g.setFaultCode(ncc.getFault().getFaultCode());
	    			g.setFaultDescription(ncc.getFault().getDescription());    	
	    			g.setEsito("KO");
	    		
	            }else {
    			    g.setEsito("OK");
    		    }
	    		return g;
	        }
	        if (a instanceof NodoInviaRispostaRevoca) {
            	NodoInviaRispostaRevoca ncr = (NodoInviaRispostaRevoca)a;
            	
            	g.setIdDominio(ncr.getIdentificativoDominio());
	    		g.setIdUnivocoVersamento(ncr.getIdentificativoUnivocoVersamento());
	    		g.setCodiceContestoPagamento(ncr.getCodiceContestoPagamento());    		
	    		g.setComponente(EnumComponente.PA);
	    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
	    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoInviaRispostaRevoca);
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
	    		try {
		    		g.setIdFruitore(ndpC.getCodiceStazionePA());
		    	} catch (Throwable t){}
	    		g.setIdErogatore("NodoDeiPagamentiSPC");
	    		g.setIdPSP("n/a");
	    		g.setIdStazioneIntermediarioPA(ncr.getIdentificativoStazioneIntermediarioPA());
	    		g.setCanalePagamento(null);//TODO??
	    		g.setParametriSpecificiInterfaccia(null);
	    		g.setEsito(null);
	    		g.setIdEGov(null);	    		
	    		g.setStoreDocumentoNDP(true);
	    		g.setTipo("ER");
	    		g.setTentativo(1);	    		
	    		g.setOpInserimento("IRIS");
	    		g.setDocumento(ncr.getEr());
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		
	    		return g;
	    		
            	
	        }
            if (a instanceof NodoInviaRispostaRevocaRisposta) {
            	NodoInviaRispostaRevocaRisposta ncc = (NodoInviaRispostaRevocaRisposta)a;
            	
            	g = (GiornaleEventiExtDTO)b;
	    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
	    		g.setTipoEvento(EnumTipoEventiNDP.nodoInviaRispostaRevoca); 
	    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);	    		
	    		g.setStoreDocumentoNDP(false);    	
	    		g.setOpInserimento("IRIS");
	    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	    		if (ncc.getFault()!=null){
	    			g.setFaultId(ncc.getFault().getId());
	    			g.setFaultSerial(ncc.getFault().getSerial()==null?0:ncc.getFault().getSerial());
	    			g.setFaultString(ncc.getFault().getFaultString());
	    			g.setFaultCode(ncc.getFault().getFaultCode());
	    			g.setFaultDescription(ncc.getFault().getDescription());    	
	    			g.setEsito("KO");
	    		
	            }else {
    			    g.setEsito("OK");
    		    }
	    		return g;
	    		
            	
	        }
	    	return null;
	    }
	    
    
	    
		private void save(GiornaleEventiExtDTO g) {
			
			GiornaleEventi ge = new GiornaleEventi();
			ge.setCanalePagamento(g.getCanalePagamento());
			ge.setCategoriaEvento(g.getCategoriaEvento());
			ge.setCodiceContestoPagamento(g.getCodiceContestoPagamento());
			ge.setComponente(g.getComponente());
			ge.setDataOraEvento(g.getDataOraEvento());
			ge.setEsito(g.getEsito());
			ge.setIdDominio(g.getIdDominio()!=null?g.getIdDominio():"n/a");
			ge.setIdErogatore(g.getIdErogatore());
			ge.setIdFruitore(g.getIdFruitore());
			ge.setIdPSP(g.getIdPSP());
			ge.setIdStazioneIntermediarioPA(g.getIdStazioneIntermediarioPA());
			ge.setIdUnivocoVersamento(g.getIdUnivocoVersamento());
			ge.setParametriSpecificiInterfaccia(g.getParametriSpecificiInterfaccia());
			ge.setSottoTipoEvento(g.getSottoTipoEvento());
			ge.setTipoEvento(g.getTipoEvento());
			ge.setTipoVersamento(g.getTipoVersamento());
			//
			ge.setOpAggiornamento(g.getOpAggiornamento());
			ge.setOpInserimento(g.getOpInserimento());
			ge.setTsAggiornamento(g.getTsAggiornamento());
			ge.setTsInserimento(g.getTsInserimento());
			//
			ge.setFaultCode(g.getFaultCode());
			ge.setFaultString(g.getFaultString());
			ge.setFaultId(g.getFaultId()); 
			ge.setFaultDescription(g.getFaultDescription());
			ge.setFaultSerial(g.getFaultSerial());
			try {
				manager.persist(ge);
			} catch (Throwable t){
				throw new RuntimeException();
			}
            if (g.isStoreDocumentoNDP()) {
				
				GiornaleEventiDocumentiNDP gdo = new GiornaleEventiDocumentiNDP();
				gdo.setIdDominio(g.getIdDominio());
				gdo.setCodiceContestoPagamento(g.getCodiceContestoPagamento());
				gdo.setIdUnivocoVersamento(g.getIdUnivocoVersamento());
					
				gdo.setDimensione(g.getDimensione());
				gdo.setDocumento(g.getDocumento());
				gdo.setDimensione(g.getDocumento().length);
				gdo.setTentativo(g.getTentativo());
				gdo.setTipo(g.getTipo());
				gdo.setOpAggiornamento(g.getOpAggiornamento());
				gdo.setOpInserimento(g.getOpInserimento());
				gdo.setTsAggiornamento(g.getTsAggiornamento());
				gdo.setTsInserimento(g.getTsInserimento());
				gdo.setVersion(0);
				try {
					manager.persist(gdo);
				} catch (Throwable t) {
					throw new RuntimeException();
				}
			} 
		}
		/*
		 * 
		 */
		@Override
		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
		public GiornaleEventiExtDTO saveGDE(Object a, Object b) {
			GiornaleEventiExtDTO gg =createGiornaleEvDTO( a, b);
			save(gg);
			return gg;
		}
		
}
