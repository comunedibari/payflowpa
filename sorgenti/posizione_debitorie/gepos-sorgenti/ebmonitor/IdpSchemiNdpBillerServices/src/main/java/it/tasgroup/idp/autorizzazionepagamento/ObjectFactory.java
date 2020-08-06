
package it.tasgroup.idp.autorizzazionepagamento;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.tasgroup.idp.autorizzazionepagamento package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AttivaPagamentoRequestType_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "AttivaPagamentoRequestType");
    private final static QName _VerificaPagamentoResponseType_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "VerificaPagamentoResponseType");
    private final static QName _AttivaPagamentoResponseType_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "AttivaPagamentoResponseType");
    private final static QName _VerificaPagamentoResponse_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "VerificaPagamentoResponse");
    private final static QName _AttivaPagamentoRequest_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "AttivaPagamentoRequest");
    private final static QName _AttivaPagamentoResponse_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "AttivaPagamentoResponse");
    private final static QName _VerificaPagamentoRequest_QNAME = new QName("http://idp.tasgroup.it/AutorizzazionePagamento/", "VerificaPagamentoRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.tasgroup.idp.autorizzazionepagamento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AttivaPagamentoResponseType }
     * 
     */
    public AttivaPagamentoResponseType createAttivaPagamentoResponseType() {
        return new AttivaPagamentoResponseType();
    }

    /**
     * Create an instance of {@link VerificaPagamentoResponseType }
     * 
     */
    public VerificaPagamentoResponseType createVerificaPagamentoResponseType() {
        return new VerificaPagamentoResponseType();
    }

    /**
     * Create an instance of {@link AttivaPagamentoRequestType }
     * 
     */
    public AttivaPagamentoRequestType createAttivaPagamentoRequestType() {
        return new AttivaPagamentoRequestType();
    }

    /**
     * Create an instance of {@link AttivaPagamentoResponseBodyType }
     * 
     */
    public AttivaPagamentoResponseBodyType createAttivaPagamentoResponseBodyType() {
        return new AttivaPagamentoResponseBodyType();
    }

    /**
     * Create an instance of {@link RequestBase }
     * 
     */
    public RequestBase createRequestBase() {
        return new RequestBase();
    }

    /**
     * Create an instance of {@link VerificaPagamentoResponseBodyType }
     * 
     */
    public VerificaPagamentoResponseBodyType createVerificaPagamentoResponseBodyType() {
        return new VerificaPagamentoResponseBodyType();
    }

    /**
     * Create an instance of {@link ResponseBase }
     * 
     */
    public ResponseBase createResponseBase() {
        return new ResponseBase();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttivaPagamentoRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "AttivaPagamentoRequestType")
    public JAXBElement<AttivaPagamentoRequestType> createAttivaPagamentoRequestType(AttivaPagamentoRequestType value) {
        return new JAXBElement<AttivaPagamentoRequestType>(_AttivaPagamentoRequestType_QNAME, AttivaPagamentoRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificaPagamentoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "VerificaPagamentoResponseType")
    public JAXBElement<VerificaPagamentoResponseType> createVerificaPagamentoResponseType(VerificaPagamentoResponseType value) {
        return new JAXBElement<VerificaPagamentoResponseType>(_VerificaPagamentoResponseType_QNAME, VerificaPagamentoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttivaPagamentoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "AttivaPagamentoResponseType")
    public JAXBElement<AttivaPagamentoResponseType> createAttivaPagamentoResponseType(AttivaPagamentoResponseType value) {
        return new JAXBElement<AttivaPagamentoResponseType>(_AttivaPagamentoResponseType_QNAME, AttivaPagamentoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificaPagamentoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "VerificaPagamentoResponse")
    public JAXBElement<VerificaPagamentoResponseType> createVerificaPagamentoResponse(VerificaPagamentoResponseType value) {
        return new JAXBElement<VerificaPagamentoResponseType>(_VerificaPagamentoResponse_QNAME, VerificaPagamentoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttivaPagamentoRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "AttivaPagamentoRequest")
    public JAXBElement<AttivaPagamentoRequestType> createAttivaPagamentoRequest(AttivaPagamentoRequestType value) {
        return new JAXBElement<AttivaPagamentoRequestType>(_AttivaPagamentoRequest_QNAME, AttivaPagamentoRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttivaPagamentoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "AttivaPagamentoResponse")
    public JAXBElement<AttivaPagamentoResponseType> createAttivaPagamentoResponse(AttivaPagamentoResponseType value) {
        return new JAXBElement<AttivaPagamentoResponseType>(_AttivaPagamentoResponse_QNAME, AttivaPagamentoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestBase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/AutorizzazionePagamento/", name = "VerificaPagamentoRequest")
    public JAXBElement<RequestBase> createVerificaPagamentoRequest(RequestBase value) {
        return new JAXBElement<RequestBase>(_VerificaPagamentoRequest_QNAME, RequestBase.class, null, value);
    }

}
