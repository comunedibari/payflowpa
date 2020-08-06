package it.tasgroup.idp.loader;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2015-09-11T14:51:37.620+02:00
 * Generated source version: 2.7.11
 * 
 */
@WebService(targetNamespace = "http://idp.tasgroup.it/Loader/", name = "LoaderService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LoaderService {

    @WebResult(name = "AllineamentoPendenzeResponse", targetNamespace = "http://idp.tasgroup.it/Loader/", partName = "parameters")
    @WebMethod(operationName = "AllineamentoPendenze", action = "http://idp.tasgroup.it/Loader/AllineamentoPendenze")
    public AllineamentoPendenzeResponse allineamentoPendenze(
        @WebParam(partName = "parameters", name = "AllineamentoPendenzeRequest", targetNamespace = "http://idp.tasgroup.it/Loader/")
        AllineamentoPendenzeRequest parameters
    );

    @WebResult(name = "getFileTrasmissioneResponse", targetNamespace = "http://idp.tasgroup.it/Loader/", partName = "parameters")
    @WebMethod(action = "http://idp.tasgroup.it/Loader/getFileTrasmissione")
    public GetFileTrasmissioneResponse getFileTrasmissione(
        @WebParam(partName = "parameters", name = "getFileTrasmissioneRequest", targetNamespace = "http://idp.tasgroup.it/Loader/")
        GetFileTrasmissioneRequest parameters
    );

    @WebResult(name = "getStatoResponse", targetNamespace = "http://idp.tasgroup.it/Loader/", partName = "parameters")
    @WebMethod(action = "http://idp.tasgroup.it/Loader/getStato")
    public GetStatoResponse getStato(
        @WebParam(partName = "parameters", name = "getStatoRequest", targetNamespace = "http://idp.tasgroup.it/Loader/")
        GetStatoRequest parameters
    );

    @WebResult(name = "countTrasmissioniResponse", targetNamespace = "http://idp.tasgroup.it/Loader/", partName = "parameters")
    @WebMethod(action = "http://idp.tasgroup.it/Loader/countTrasmissioni")
    public CountTrasmissioniResponse countTrasmissioni(
        @WebParam(partName = "parameters", name = "countTrasmissioniRequest", targetNamespace = "http://idp.tasgroup.it/Loader/")
        CountTrasmissioniRequest parameters
    );

    @WebResult(name = "listaTrasmissioniResponse", targetNamespace = "http://idp.tasgroup.it/Loader/", partName = "parameters")
    @WebMethod(action = "http://idp.tasgroup.it/Loader/listaTrasmissioni")
    public ListaTrasmissioniResponse listaTrasmissioni(
        @WebParam(partName = "parameters", name = "listaTrasmissioniRequest", targetNamespace = "http://idp.tasgroup.it/Loader/")
        ListaTrasmissioniRequest parameters
    );

    @WebResult(name = "getEsitoResponse", targetNamespace = "http://idp.tasgroup.it/Loader/", partName = "parameters")
    @WebMethod(action = "http://idp.tasgroup.it/Loader/getEsito")
    public GetEsitoResponse getEsito(
        @WebParam(partName = "parameters", name = "getEsitoRequest", targetNamespace = "http://idp.tasgroup.it/Loader/")
        GetEsitoRequest parameters
    );
}
