package it.regioneveneto.mygov.payment.mypivot.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.14
 * 2016-04-04T14:39:04.930+02:00
 * Generated source version: 2.7.14
 * 
 */
public interface PagamentiTelematiciDovutiPagatiClient {

    public it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlussoRisposta paaSILChiediStatoExportFlusso(
        it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlusso bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public it.veneto.regione.pagamenti.ente.PaaSILImportaDovutoRisposta paaSILImportaDovuto(
        it.veneto.regione.pagamenti.ente.PaaSILImportaDovuto bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public it.veneto.regione.pagamenti.ente.PaaSILAutorizzaImportFlussoRisposta paaSILAutorizzaImportFlusso(
        it.veneto.regione.pagamenti.ente.PaaSILAutorizzaImportFlusso bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoRisposta paaSILPrenotaExportFlusso(
        it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlusso bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public it.veneto.regione.pagamenti.ente.PaaSILVerificaAvvisoRisposta paaSILVerificaAvviso(
        it.veneto.regione.pagamenti.ente.PaaSILVerificaAvviso bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public void paaSILChiediAvvisiPendenti(
        java.lang.String password,
        java.lang.String codIpaEnte,
        javax.xml.datatype.XMLGregorianCalendar dateFrom,
        javax.xml.datatype.XMLGregorianCalendar dateTo,
        javax.xml.ws.Holder<it.veneto.regione.pagamenti.ente.FaultBean> fault,
        javax.xml.ws.Holder<java.util.List<it.veneto.regione.pagamenti.ente.PaaSILAvvisoPendente>> paaSILAvvisoPendente
    );

    public it.veneto.regione.pagamenti.ente.PaaSILInviaDovutiRisposta paaSILInviaDovuti(
        it.veneto.regione.pagamenti.ente.PaaSILInviaDovuti bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta paaSILPrenotaExportFlussoIncrementaleConRicevuta(
         it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoIncrementaleConRicevuta bodyrichiesta,
        it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );

    public void paaSILChiediPagatiConRicevuta(
        java.lang.String codIpaEnte,
       java.lang.String password,
        java.lang.String idSession,
        javax.xml.ws.Holder<it.veneto.regione.pagamenti.ente.FaultBean> fault,
        javax.xml.ws.Holder<javax.activation.DataHandler> pagati,
        javax.xml.ws.Holder<java.lang.String> tipoFirma,
        javax.xml.ws.Holder<javax.activation.DataHandler> rt
    );

     public void paaSILChiediPagati(
        java.lang.String codIpaEnte,
        java.lang.String password,
        java.lang.String idSession,
        javax.xml.ws.Holder<it.veneto.regione.pagamenti.ente.FaultBean> fault,
        javax.xml.ws.Holder<javax.activation.DataHandler> pagati
    );

    public it.veneto.regione.pagamenti.ente.PaaSILChiediStatoImportFlussoRisposta paaSILChiediStatoImportFlusso(
        it.veneto.regione.pagamenti.ente.PaaSILChiediStatoImportFlusso bodyrichiesta,
         it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT header
    );
}
