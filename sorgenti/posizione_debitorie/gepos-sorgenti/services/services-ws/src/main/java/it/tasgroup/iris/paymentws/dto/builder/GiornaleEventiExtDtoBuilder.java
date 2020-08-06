package it.tasgroup.iris.paymentws.dto.builder;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.PaaAttivaRPT;
import gov.telematici.pagamenti.ws.PaaAttivaRPTRisposta;
import gov.telematici.pagamenti.ws.PaaInviaRT;
import gov.telematici.pagamenti.ws.PaaInviaRTRisposta;
import gov.telematici.pagamenti.ws.PaaVerificaRPT;
import gov.telematici.pagamenti.ws.PaaVerificaRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.services.util.enumeration.EnumCategoriaEvento;
import it.tasgroup.services.util.enumeration.EnumComponente;
import it.tasgroup.services.util.enumeration.EnumSottoTipoEvento;
import it.tasgroup.services.util.enumeration.EnumTipoEventiNDP;
import it.tasgroup.services.util.enumeration.EnumTipoVersamento;

import java.sql.Timestamp;


public class GiornaleEventiExtDtoBuilder {
	
	public static GiornaleEventiExtDTO createGiornaleEvDTO(Object a,Object b) {
    	GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
    	g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    	
    	if (a instanceof PaaAttivaRPT) {
    		PaaAttivaRPT ncc = (PaaAttivaRPT)a;
    		IntestazionePPT intppt = (IntestazionePPT)b;     		
    		g.setIdDominio(intppt.getIdentificativoDominio());
    		g.setIdUnivocoVersamento(intppt.getIdentificativoUnivocoVersamento());
    		g.setCodiceContestoPagamento(intppt.getCodiceContestoPagamento());
    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
    		g.setComponente(EnumComponente.PdD);
    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
    		g.setTipoEvento(EnumTipoEventiNDP.paaAttivaRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
    		g.setIdFruitore("NodoDeiPagamentiSPC");
    		g.setIdErogatore(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setIdStazioneIntermediarioPA(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setIdPSP(ncc.getIdentificativoPSP()!=null?ncc.getIdentificativoPSP():"n/a");
    		g.setCanalePagamento(null);//TODO??
    		g.setParametriSpecificiInterfaccia(null);
    		g.setEsito(null);
    		g.setIdEGov(null);
    		g.setStoreDocumentoNDP(false);
    		g.setOpInserimento("IDP");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		
    		return g;
    	} 
    	if (a instanceof PaaVerificaRPT){
    		PaaVerificaRPT ncc = (PaaVerificaRPT)a;
    		IntestazionePPT intppt = (IntestazionePPT)b; 
    		g.setIdDominio(intppt.getIdentificativoDominio());
    		g.setIdUnivocoVersamento(intppt.getIdentificativoUnivocoVersamento());
    		g.setCodiceContestoPagamento(intppt.getCodiceContestoPagamento());
    		g.setTipoVersamento(EnumTipoVersamento.PO);
    		g.setComponente(EnumComponente.PdD);
    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
    		g.setTipoEvento(EnumTipoEventiNDP.paaVerificaRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
    		g.setIdFruitore("NodoDeiPagamentiSPC");
    		g.setIdErogatore(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setIdStazioneIntermediarioPA(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setIdPSP(ncc.getIdentificativoPSP()!=null?ncc.getIdentificativoPSP():"n/a");
    		g.setCanalePagamento(null);//TODO??
    		g.setParametriSpecificiInterfaccia(null);
    		g.setEsito(null);
    		g.setIdEGov(null);
    		g.setStoreDocumentoNDP(false);
    		g.setOpInserimento("IDP");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		
    		return g;
    		
    	} 
    	if (a instanceof PaaInviaRT) {
    		PaaInviaRT ncc = (PaaInviaRT)a;
    		gov.telematici.pagamenti.ws.ppthead.IntestazionePPT intppt = (gov.telematici.pagamenti.ws.ppthead.IntestazionePPT)b; 
    		g.setIdDominio(intppt.getIdentificativoDominio());
    		g.setIdUnivocoVersamento(intppt.getIdentificativoUnivocoVersamento());
    		g.setCodiceContestoPagamento(intppt.getCodiceContestoPagamento());
    		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
    		g.setComponente(EnumComponente.PdD);
    		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
    		g.setTipoEvento(EnumTipoEventiNDP.paaInviaRT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
    		g.setIdFruitore("NodoDeiPagamentiSPC");
    		g.setIdErogatore(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setIdStazioneIntermediarioPA(intppt.getIdentificativoStazioneIntermediarioPA());
    		g.setIdPSP("n/a"); // TODO da estrarre da RT
    		g.setCanalePagamento(null);//TODO??
    		g.setParametriSpecificiInterfaccia(null);
    		g.setEsito(null);
    		g.setIdEGov(null);
    		//
    		g.setStoreDocumentoNDP(true);
    		g.setTentativo(1);
    		g.setTipo("RT");
    		g.setDocumento(ncc.getRt());
    		g.setDimensione(ncc.getRt().length);
    		g.setOpInserimento("IDP");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		return g;
    		
    	} 
    	if (a instanceof PaaAttivaRPTRisposta) {
    		PaaAttivaRPTRisposta ncc = (PaaAttivaRPTRisposta)a;
    		g = (GiornaleEventiExtDTO)b;
    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		g.setTipoEvento(EnumTipoEventiNDP.paaAttivaRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		g.setEsito(ncc.getPaaAttivaRPTRisposta().getEsito());
    		g.setStoreDocumentoNDP(false);
    		if (ncc.getPaaAttivaRPTRisposta().getFault()!=null){
    			g.setFaultId(ncc.getPaaAttivaRPTRisposta().getFault().getId());
    			g.setFaultSerial(ncc.getPaaAttivaRPTRisposta().getFault().getSerial()==null?0:ncc.getPaaAttivaRPTRisposta().getFault().getSerial());
    			g.setFaultString(ncc.getPaaAttivaRPTRisposta().getFault().getFaultString());
    			g.setFaultCode(ncc.getPaaAttivaRPTRisposta().getFault().getFaultCode());
    			g.setFaultDescription(ncc.getPaaAttivaRPTRisposta().getFault().getDescription());    			
    		}
    		g.setOpInserimento("IDP");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    		
    		    		
    		return g;
    		
    	}
    	if (a instanceof PaaVerificaRPTRisposta) {
    		PaaVerificaRPTRisposta ncc = (PaaVerificaRPTRisposta)a;
    		g = (GiornaleEventiExtDTO)b;
    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		g.setTipoEvento(EnumTipoEventiNDP.paaVerificaRPT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		g.setEsito(ncc.getPaaVerificaRPTRisposta().getEsito());    		
    		g.setStoreDocumentoNDP(false);   
    		if (ncc.getPaaVerificaRPTRisposta().getFault()!=null){
    			g.setFaultId(ncc.getPaaVerificaRPTRisposta().getFault().getId());
    			g.setFaultSerial(ncc.getPaaVerificaRPTRisposta().getFault().getSerial()==null?0:ncc.getPaaVerificaRPTRisposta().getFault().getSerial());
    			g.setFaultString(ncc.getPaaVerificaRPTRisposta().getFault().getFaultString());
    			g.setFaultCode(ncc.getPaaVerificaRPTRisposta().getFault().getFaultCode());
    			g.setFaultDescription(ncc.getPaaVerificaRPTRisposta().getFault().getDescription());    			
    		}
    		g.setOpInserimento("IDP");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));

    		return g;
    	}
        if (a instanceof PaaInviaRTRisposta) {
        	PaaInviaRTRisposta ncc = (PaaInviaRTRisposta)a;
    		g = (GiornaleEventiExtDTO)b;
    		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    		g.setTipoEvento(EnumTipoEventiNDP.paaInviaRT);
    		g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
    		g.setEsito(ncc.getPaaInviaRTRisposta().getEsito());
    		g.setStoreDocumentoNDP(false);  
    		if (ncc.getPaaInviaRTRisposta().getFault()!=null){
    			g.setFaultId(ncc.getPaaInviaRTRisposta().getFault().getId());
    			g.setFaultSerial(ncc.getPaaInviaRTRisposta().getFault().getSerial()==null?0:ncc.getPaaInviaRTRisposta().getFault().getSerial());
    			g.setFaultString(ncc.getPaaInviaRTRisposta().getFault().getFaultString());
    			g.setFaultCode(ncc.getPaaInviaRTRisposta().getFault().getFaultCode());
    			g.setFaultDescription(ncc.getPaaInviaRTRisposta().getFault().getDescription());    			
    		}
    		g.setOpInserimento("IDP");
    		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));

    		return g;
        }
    	return null;
	}
	public static GiornaleEventiExtDTO createGiornaleEvRichiestaRevocaDTO(String identificativoDominio, 
			                                                              String identificativoUnivocoVersamento,
			                                                              String codiceContestoPagamento, 
			                                                              byte[] rr) {
		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
    	g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    	g.setIdDominio(identificativoDominio);
		g.setIdUnivocoVersamento(identificativoUnivocoVersamento);
		g.setCodiceContestoPagamento(codiceContestoPagamento);
		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
		g.setComponente(EnumComponente.PdD);
		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
		g.setTipoEvento(EnumTipoEventiNDP.paaInviaRichiestaRevoca);
		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
		g.setIdFruitore("NodoDeiPagamentiSPC");
		g.setIdErogatore("n/a");
		g.setIdStazioneIntermediarioPA("n/a");
		g.setIdPSP("n/a"); 
		g.setCanalePagamento(null);
		g.setParametriSpecificiInterfaccia(null);
		g.setEsito(null);
		g.setIdEGov(null);
		//
		g.setStoreDocumentoNDP(true);
		g.setTentativo(1);
		g.setTipo("RR");
		g.setDocumento(rr);
		g.setDimensione(rr.length);
		g.setOpInserimento("IDP");
		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    	return g;
		
	}
	
	public static GiornaleEventiExtDTO createGiornaleEvRichiestaRevocaDTO(GiornaleEventiExtDTO g, String esito, FaultBean fault) {
	  g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
	  g.setTipoEvento(EnumTipoEventiNDP.paaInviaRichiestaRevoca);
	  g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
	  g.setEsito(esito);
	  g.setStoreDocumentoNDP(false);
	  if (fault!=null){
		  g.setFaultId(fault.getId());
		  g.setFaultSerial(fault.getSerial()==null?0:fault.getSerial());
		  g.setFaultString(fault.getFaultString());
		  g.setFaultCode(fault.getFaultCode());
		  g.setFaultDescription(fault.getDescription());
	  }
	  g.setOpInserimento("IDP");
	  g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
	  return g;
	
	}
	
}
