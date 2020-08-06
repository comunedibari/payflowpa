package it.tasgroup.idp.ndp.utils;

import java.sql.Timestamp;

import gov.telematici.pagamenti.ws.FaultBean;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.NdpAttivaVerificaRisposta;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.services.util.enumeration.EnumCategoriaEvento;
import it.tasgroup.services.util.enumeration.EnumComponente;
import it.tasgroup.services.util.enumeration.EnumSottoTipoEvento;
import it.tasgroup.services.util.enumeration.EnumTipoEventiNDP;
import it.tasgroup.services.util.enumeration.EnumTipoVersamento;


public class GiornaleEventiNdpDtoBuilder {
	

	public static GiornaleEventiExtDTO createGiornaleEvRichiestaVerificaDTO( String identificativoDominio,
                                                                             String identificativoUnivocoVersamento,
                                                                             String codiceContestoPagamento) {
		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
    	g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
    	g.setIdDominio(identificativoDominio);
		g.setIdUnivocoVersamento(identificativoUnivocoVersamento);
		g.setCodiceContestoPagamento(codiceContestoPagamento);
		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
		g.setComponente(EnumComponente.PdD);
		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
		g.setTipoEvento(EnumTipoEventiNDP.paaVerificaRPT);
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
		g.setStoreDocumentoNDP(false);
		g.setTentativo(1);		
		g.setOpInserimento("IDP");
		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
    	return g;
		
	}
	public static GiornaleEventiExtDTO createGiornaleEvRispostaVerificaAttivaDTO(String idDominio, String identificativoUnivocoVersamento, NdpAttivaVerificaRisposta risposta,
            boolean verifica
            ) {
            
		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
        g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
        g.setIdDominio(idDominio);
        g.setIdUnivocoVersamento(identificativoUnivocoVersamento);//???
        g.setCodiceContestoPagamento(NdpRestApiConst.CODICE_CONTESTO_PAGAMENTO);
        g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
        g.setComponente(EnumComponente.PdD);
        g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
        if (verifica) {
          g.setTipoEvento(EnumTipoEventiNDP.paaVerificaRPT);
        } else {
        	g.setTipoEvento(EnumTipoEventiNDP.paaAttivaRPT);
        }
        if (risposta.getEsito().equals("KO")){			
			g.setFaultString(risposta.getFaultString());
			g.setFaultCode(risposta.getFaultCode());
			g.setFaultDescription(risposta.getFaultDescription());    			
		}
        g.setSottoTipoEvento(EnumSottoTipoEvento.rsp);
        g.setIdFruitore("NodoDeiPagamentiSPC");
        g.setIdErogatore("n/a");
        g.setIdStazioneIntermediarioPA("n/a");
        g.setIdPSP("n/a"); 
        g.setCanalePagamento(null);
        g.setParametriSpecificiInterfaccia(null);
        g.setEsito(null);
        g.setIdEGov(null);
        //
        g.setStoreDocumentoNDP(false);
        g.setTentativo(1);		
        g.setOpInserimento("IDP");
        g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
        return g;

    }

	public static GiornaleEventiExtDTO createGiornaleEvRichiestaAttivaDTO( String identificativoDominio,
            String identificativoUnivocoVersamento,
            String codiceContestoPagamento) {

		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
        g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
        g.setIdDominio(identificativoDominio);
        g.setIdUnivocoVersamento(identificativoUnivocoVersamento);
        g.setCodiceContestoPagamento(codiceContestoPagamento);
        g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
        g.setComponente(EnumComponente.PdD);
        g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
        g.setTipoEvento(EnumTipoEventiNDP.paaAttivaRPT);
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
        g.setStoreDocumentoNDP(false);
        g.setTentativo(1);		
        g.setOpInserimento("IDP");
        g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
        return g;

   }
	
	public static GiornaleEventiExtDTO createGiornaleEvRichiestaRicevutaDTO( String identificativoDominio,
            String identificativoUnivocoVersamento,
            String codiceContestoPagamento,byte[] rt) {

		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
		
		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));
		g.setIdDominio(identificativoDominio);
		g.setIdUnivocoVersamento(identificativoUnivocoVersamento);
		g.setCodiceContestoPagamento(codiceContestoPagamento);
		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
		g.setComponente(EnumComponente.PdD);
		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
		g.setTipoEvento(EnumTipoEventiNDP.paaInviaRT);
		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
		g.setIdFruitore("NodoDeiPagamentiSPC");
		g.setIdErogatore(NdpRestApiConst.ID_EROGATORE_PA);
		g.setIdStazioneIntermediarioPA(NdpRestApiConst.STAZ_INTERM_PA);
		g.setIdPSP("n/a"); // TODO da estrarre da RT
		g.setCanalePagamento(null);//TODO??
		g.setParametriSpecificiInterfaccia(null);
		g.setEsito(null);
		g.setIdEGov(null);
		//
		g.setStoreDocumentoNDP(true);
		g.setTentativo(1);
		g.setTipo("RT");
		g.setDocumento(rt);
		g.setDimensione(rt.length);
		g.setOpInserimento("IDP");
		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));
        return g;

   }
	
	public static GiornaleEventiExtDTO createGiornaleEvRispostaRTDTO( String identificativoDominio,
            String identificativoUnivocoVersamento,
            String codiceContestoPagamento,EsitoNDP risposta) {

		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));		
		g.setIdDominio(identificativoDominio);
		g.setIdUnivocoVersamento(identificativoUnivocoVersamento);
		g.setCodiceContestoPagamento(codiceContestoPagamento);
		g.setTipoVersamento(EnumTipoVersamento.CP);//TODO ???
		g.setComponente(EnumComponente.PdD);
		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
		g.setTipoEvento(EnumTipoEventiNDP.paaInviaRT);
		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
		g.setIdFruitore("NodoDeiPagamentiSPC");
		g.setIdErogatore(NdpRestApiConst.ID_EROGATORE_PA);
		g.setIdStazioneIntermediarioPA(NdpRestApiConst.STAZ_INTERM_PA);
		g.setIdPSP("n/a"); // TODO da estrarre da RT
		g.setCanalePagamento(null);//TODO??
		g.setParametriSpecificiInterfaccia(null);
		g.setEsito(null);
		g.setIdEGov(null);
		//
		g.setStoreDocumentoNDP(false);
		if (risposta.getEsito().equals("KO")){			
			g.setFaultString(risposta.getFaultString());
			g.setFaultCode(risposta.getFaultCode());
			g.setFaultDescription(risposta.getFaultDescription());    			
		}
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
