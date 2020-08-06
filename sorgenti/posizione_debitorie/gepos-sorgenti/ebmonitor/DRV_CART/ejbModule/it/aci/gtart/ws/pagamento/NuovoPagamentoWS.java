package it.aci.gtart.ws.pagamento;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2015-06-16T11:53:15.942+02:00
 * Generated source version: 2.7.11
 * 
 */
@WebService(targetNamespace = "http://pagamento.ws.gtart.aci.it/", name = "NuovoPagamentoWS")
@XmlSeeAlso({ObjectFactory.class})
public interface NuovoPagamentoWS {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "nuovoPagamento", targetNamespace = "http://pagamento.ws.gtart.aci.it/", className = "it.aci.gtart.ws.pagamento.NuovoPagamento")
    @WebMethod(action = "Inserisce un nuovo pagamento")
    @ResponseWrapper(localName = "nuovoPagamentoResponse", targetNamespace = "http://pagamento.ws.gtart.aci.it/", className = "it.aci.gtart.ws.pagamento.NuovoPagamentoResponse")
    public java.lang.Integer nuovoPagamento(
        @WebParam(name = "tipoIntermediario", targetNamespace = "")
        java.lang.String tipoIntermediario,
        @WebParam(name = "IDIntermediario", targetNamespace = "")
        java.lang.String idIntermediario,
        @WebParam(name = "MSGID", targetNamespace = "")
        java.lang.String msgid,
        @WebParam(name = "idBollo", targetNamespace = "")
        java.lang.String idBollo,
        @WebParam(name = "IUV", targetNamespace = "")
        java.lang.String iuv,
        @WebParam(name = "TipoVeicolo", targetNamespace = "")
        java.lang.Integer tipoVeicolo,
        @WebParam(name = "Targa", targetNamespace = "")
        java.lang.String targa,
        @WebParam(name = "decorrenza", targetNamespace = "")
        java.lang.String decorrenza,
        @WebParam(name = "mesiValidita", targetNamespace = "")
        java.lang.Integer mesiValidita,
        @WebParam(name = "dataPagamento", targetNamespace = "")
        javax.xml.datatype.XMLGregorianCalendar dataPagamento,
        @WebParam(name = "tassa", targetNamespace = "")
        java.math.BigDecimal tassa,
        @WebParam(name = "sanzioni", targetNamespace = "")
        java.math.BigDecimal sanzioni,
        @WebParam(name = "interessi", targetNamespace = "")
        java.math.BigDecimal interessi,
        @WebParam(name = "totale", targetNamespace = "")
        java.math.BigDecimal totale,
        @WebParam(name = "RegBeneficiaria", targetNamespace = "")
        java.lang.Integer regBeneficiaria,
        @WebParam(name = "RegVersamento", targetNamespace = "")
        java.lang.Integer regVersamento,
        @WebParam(name = "codiceFiscale", targetNamespace = "")
        java.lang.String codiceFiscale,
        @WebParam(name = "ModPagamento", targetNamespace = "")
        java.lang.Integer modPagamento,
        @WebParam(name = "Note", targetNamespace = "")
        java.lang.String note
    );
}
