
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.toscana.rete.cart.servizi.iris_1_1.idpesito package. 
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

    private final static QName _IdpEsito_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", "IdpEsito");
    private final static QName _IdpMultiEsitoOTF_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", "IdpMultiEsitoOTF");
    private final static QName _IdpEsitoOTF_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", "IdpEsitoOTF");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.toscana.rete.cart.servizi.iris_1_1.idpesito
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InfoMessaggio }
     * 
     */
    public InfoMessaggio createInfoMessaggio() {
        return new InfoMessaggio();
    }

    /**
     * Create an instance of {@link InfoDettaglio }
     * 
     */
    public InfoDettaglio createInfoDettaglio() {
        return new InfoDettaglio();
    }

    /**
     * Create an instance of {@link IdpOTFType }
     * 
     */
    public IdpOTFType createIdpOTFType() {
        return new IdpOTFType();
    }

    /**
     * Create an instance of {@link IdpBodyType }
     * 
     */
    public IdpBodyType createIdpBodyType() {
        return new IdpBodyType();
    }

    /**
     * Create an instance of {@link Dettaglio }
     * 
     */
    public Dettaglio createDettaglio() {
        return new Dettaglio();
    }

    /**
     * Create an instance of {@link Esiti }
     * 
     */
    public Esiti createEsiti() {
        return new Esiti();
    }

    /**
     * Create an instance of {@link IdpEsitoOTF }
     * 
     */
    public IdpEsitoOTF createIdpEsitoOTF() {
        return new IdpEsitoOTF();
    }

    /**
     * Create an instance of {@link IdpMultiEsitoOTFElement }
     * 
     */
    public IdpMultiEsitoOTFElement createIdpMultiEsitoOTFElement() {
        return new IdpMultiEsitoOTFElement();
    }

    /**
     * Create an instance of {@link Esito }
     * 
     */
    public Esito createEsito() {
        return new Esito();
    }

    /**
     * Create an instance of {@link IdpEsito }
     * 
     */
    public IdpEsito createIdpEsito() {
        return new IdpEsito();
    }

    /**
     * Create an instance of {@link IdpMultiEsitoOTF }
     * 
     */
    public IdpMultiEsitoOTF createIdpMultiEsitoOTF() {
        return new IdpMultiEsitoOTF();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpEsito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", name = "IdpEsito")
    public JAXBElement<IdpEsito> createIdpEsito(IdpEsito value) {
        return new JAXBElement<IdpEsito>(_IdpEsito_QNAME, IdpEsito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpMultiEsitoOTF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", name = "IdpMultiEsitoOTF")
    public JAXBElement<IdpMultiEsitoOTF> createIdpMultiEsitoOTF(IdpMultiEsitoOTF value) {
        return new JAXBElement<IdpMultiEsitoOTF>(_IdpMultiEsitoOTF_QNAME, IdpMultiEsitoOTF.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpEsitoOTF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito", name = "IdpEsitoOTF")
    public JAXBElement<IdpEsitoOTF> createIdpEsitoOTF(IdpEsitoOTF value) {
        return new JAXBElement<IdpEsitoOTF>(_IdpEsitoOTF_QNAME, IdpEsitoOTF.class, null, value);
    }

}
