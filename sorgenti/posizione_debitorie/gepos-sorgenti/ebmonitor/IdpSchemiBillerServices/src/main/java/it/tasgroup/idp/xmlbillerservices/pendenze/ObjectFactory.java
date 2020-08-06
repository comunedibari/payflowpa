
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.tasgroup.idp.xmlbillerservices.pendenze package. 
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

    private final static QName _IdpMultiEsitoOTF_QNAME = new QName("http://idp.tasgroup.it/xmlbillerservices/Pendenze", "IdpMultiEsitoOTF");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.tasgroup.idp.xmlbillerservices.pendenze
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Pendenza.InfoPagamento }
     * 
     */
    public Pendenza.InfoPagamento createPendenzaInfoPagamento() {
        return new Pendenza.InfoPagamento();
    }

    /**
     * Create an instance of {@link VociPagamento }
     * 
     */
    public VociPagamento createVociPagamento() {
        return new VociPagamento();
    }

    /**
     * Create an instance of {@link Debitore }
     * 
     */
    public Debitore createDebitore() {
        return new Debitore();
    }

    /**
     * Create an instance of {@link Dettaglio }
     * 
     */
    public Dettaglio createDettaglio() {
        return new Dettaglio();
    }

    /**
     * Create an instance of {@link IdpAllineamentoPendenzeMultiOTF }
     * 
     */
    public IdpAllineamentoPendenzeMultiOTF createIdpAllineamentoPendenzeMultiOTF() {
        return new IdpAllineamentoPendenzeMultiOTF();
    }

    /**
     * Create an instance of {@link Pendenza }
     * 
     */
    public Pendenza createPendenza() {
        return new Pendenza();
    }

    /**
     * Create an instance of {@link IdpOTFType }
     * 
     */
    public IdpOTFType createIdpOTFType() {
        return new IdpOTFType();
    }

    /**
     * Create an instance of {@link Mittente }
     * 
     */
    public Mittente createMittente() {
        return new Mittente();
    }

    /**
     * Create an instance of {@link InfoDettaglio }
     * 
     */
    public InfoDettaglio createInfoDettaglio() {
        return new InfoDettaglio();
    }

    /**
     * Create an instance of {@link IdpMultiEsitoOTFElement }
     * 
     */
    public IdpMultiEsitoOTFElement createIdpMultiEsitoOTFElement() {
        return new IdpMultiEsitoOTFElement();
    }

    /**
     * Create an instance of {@link CondizionePagamento }
     * 
     */
    public CondizionePagamento createCondizionePagamento() {
        return new CondizionePagamento();
    }

    /**
     * Create an instance of {@link IdpMultiEsitoOTF }
     * 
     */
    public IdpMultiEsitoOTF createIdpMultiEsitoOTF() {
        return new IdpMultiEsitoOTF();
    }

    /**
     * Create an instance of {@link IdpAllineamentoPendenzeMultiOTFParametriType }
     * 
     */
    public IdpAllineamentoPendenzeMultiOTFParametriType createIdpAllineamentoPendenzeMultiOTFParametriType() {
        return new IdpAllineamentoPendenzeMultiOTFParametriType();
    }

    /**
     * Create an instance of {@link Debitori }
     * 
     */
    public Debitori createDebitori() {
        return new Debitori();
    }

    /**
     * Create an instance of {@link InfoMessaggio }
     * 
     */
    public InfoMessaggio createInfoMessaggio() {
        return new InfoMessaggio();
    }

    /**
     * Create an instance of {@link IdpBody }
     * 
     */
    public IdpBody createIdpBody() {
        return new IdpBody();
    }

    /**
     * Create an instance of {@link VoceImporto }
     * 
     */
    public VoceImporto createVoceImporto() {
        return new VoceImporto();
    }

    /**
     * Create an instance of {@link IdpAllineamentoPendenzeMultiOTFElementType }
     * 
     */
    public IdpAllineamentoPendenzeMultiOTFElementType createIdpAllineamentoPendenzeMultiOTFElementType() {
        return new IdpAllineamentoPendenzeMultiOTFElementType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpMultiEsitoOTF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/xmlbillerservices/Pendenze", name = "IdpMultiEsitoOTF")
    public JAXBElement<IdpMultiEsitoOTF> createIdpMultiEsitoOTF(IdpMultiEsitoOTF value) {
        return new JAXBElement<IdpMultiEsitoOTF>(_IdpMultiEsitoOTF_QNAME, IdpMultiEsitoOTF.class, null, value);
    }

}
