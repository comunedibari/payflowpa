
package it.aci.gtart.ws.pagamento;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.aci.gtart.ws.pagamento package. 
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

    private final static QName _NuovoPagamentoResponse_QNAME = new QName("http://pagamento.ws.gtart.aci.it/", "nuovoPagamentoResponse");
    private final static QName _NuovoPagamento_QNAME = new QName("http://pagamento.ws.gtart.aci.it/", "nuovoPagamento");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.aci.gtart.ws.pagamento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NuovoPagamentoResponse }
     * 
     */
    public NuovoPagamentoResponse createNuovoPagamentoResponse() {
        return new NuovoPagamentoResponse();
    }

    /**
     * Create an instance of {@link NuovoPagamento }
     * 
     */
    public NuovoPagamento createNuovoPagamento() {
        return new NuovoPagamento();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NuovoPagamentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pagamento.ws.gtart.aci.it/", name = "nuovoPagamentoResponse")
    public JAXBElement<NuovoPagamentoResponse> createNuovoPagamentoResponse(NuovoPagamentoResponse value) {
        return new JAXBElement<NuovoPagamentoResponse>(_NuovoPagamentoResponse_QNAME, NuovoPagamentoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NuovoPagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pagamento.ws.gtart.aci.it/", name = "nuovoPagamento")
    public JAXBElement<NuovoPagamento> createNuovoPagamento(NuovoPagamento value) {
        return new JAXBElement<NuovoPagamento>(_NuovoPagamento_QNAME, NuovoPagamento.class, null, value);
    }

}
