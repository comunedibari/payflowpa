package it.tasgroup.iris.nodopagamentispc.util;

import gov.telematici.pagamenti.ws.*;
import gov.telematici.pagamenti.ws.ppthead.IntestazioneCarrelloPPT;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPT;
import it.gov.spcoop.NodoPagamentiSPC.servizi.PagamentiTelematiciRPT.PagamentiTelematiciRPTProxy;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;


public class RPTUtil {

	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");

	public static PagamentiTelematiciRPT getPagamentiTelematiciRPTPort() {
		String url = conf.getProperty("nodopagamentispc.services.url");
		return new PagamentiTelematiciRPTProxy(url);
	}

	public static IntestazionePPT buildIntestazioneRichiesta(String idUnivocoPagamento, String codiceContestoPagamento, String idFiscaleCreditore) {

		IntestazionePPT header = new IntestazionePPT();
		header.setCodiceContestoPagamento(codiceContestoPagamento);
		header.setIdentificativoDominio(idFiscaleCreditore);
		header.setIdentificativoIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
		header.setIdentificativoStazioneIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
		header.setIdentificativoUnivocoVersamento(idUnivocoPagamento);

		return header;
	}

	public static NodoInviaRPT buildBodyInviaRPT(String idPSP, String idIntermediarioPSP, String applicationId, byte[] rpt) {

		String idCanale;
		if(applicationId.lastIndexOf("-") > -1) {
			idCanale = applicationId.substring(0, applicationId.lastIndexOf("-"));
		} else {
			idCanale = applicationId;
		}

		NodoInviaRPT bodyrichiesta = new NodoInviaRPT();
		bodyrichiesta.setPassword(conf.getProperty("nodopagamentispc.password"));
		bodyrichiesta.setIdentificativoPSP(idPSP);
		bodyrichiesta.setIdentificativoIntermediarioPSP(idIntermediarioPSP); // SUB_SYSTEM_ID
		bodyrichiesta.setIdentificativoCanale(idCanale); // APPLICATION_ID (ripulito della modalita versamento finale)
		bodyrichiesta.setTipoFirma("");
		bodyrichiesta.setRpt(rpt);

		return bodyrichiesta;
	}

    public static IntestazioneCarrelloPPT buildIntestazioneRichiestaCarrello(String identificativoCarrello) {

    	IntestazioneCarrelloPPT header = new IntestazioneCarrelloPPT();
		header.setIdentificativoCarrello(identificativoCarrello);
		header.setIdentificativoIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
		header.setIdentificativoStazioneIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));

		return header;
	}

    public static NodoInviaCarrelloRPT buildBodyNodoInviaCarrelloRPT(String idPSP,
                                                                     String idIntermediarioPSP,
                                                                     String applicationId,
                                                                     List<String> codiceContestoPagamento,
                                                                     List<String> idFiscaleList,
                                                                     List<String> iuvList,
                                                                     List<String> rptList) {

    	NodoInviaCarrelloRPT bodyrichiesta = new NodoInviaCarrelloRPT();

    	String idCanale;
		if(applicationId.lastIndexOf("-") > -1) {
			idCanale = applicationId.substring(0, applicationId.lastIndexOf("-"));
		} else {
			idCanale = applicationId;
		}
    	bodyrichiesta.setPassword(conf.getProperty("nodopagamentispc.password"));
    	bodyrichiesta.setIdentificativoPSP(idPSP);
		bodyrichiesta.setIdentificativoIntermediarioPSP(idIntermediarioPSP); // SUB_SYSTEM_ID
		bodyrichiesta.setIdentificativoCanale(idCanale); // APPLICATION_ID (ripulito della modalita versamento finale)

		TipoElementoListaRPT[] rptHelp = new TipoElementoListaRPT[rptList.size()];
    	//**
        Iterator<String> rptListIter = rptList.iterator();
        Iterator<String> iuvListIter = iuvList.iterator();
        Iterator<String> idFiscaleIter = idFiscaleList.iterator();
        Iterator<String> codContestoPagIter = codiceContestoPagamento.iterator();
         int i=0;
        while (rptListIter.hasNext()) {

           String rpt                      = rptListIter.next();
           String iuv                      = iuvListIter.next();
           String idFiscaleCreditore       = idFiscaleIter.next();
           String codContestoPagamentoPSP  = codContestoPagIter.next();

           TipoElementoListaRPT elemListaRPT = new TipoElementoListaRPT();
           elemListaRPT.setIdentificativoDominio(idFiscaleCreditore);
           elemListaRPT.setCodiceContestoPagamento(codContestoPagamentoPSP);
           elemListaRPT.setTipoFirma("");
           elemListaRPT.setIdentificativoUnivocoVersamento(iuv);
           elemListaRPT.setRpt(rpt.getBytes());
           rptHelp[i]=elemListaRPT;
           i++;
        }

        bodyrichiesta.setListaRPT(new TipoListaRPT(rptHelp));


    	return bodyrichiesta;
    }


}
