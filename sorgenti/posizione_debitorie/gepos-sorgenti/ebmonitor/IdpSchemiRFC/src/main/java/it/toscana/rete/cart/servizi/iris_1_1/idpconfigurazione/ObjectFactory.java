
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione package. 
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

    private final static QName _IdpConfigurazioneEnte_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione", "IdpConfigurazioneEnte");
    private final static QName _IdpConfigurazioneTributi_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione", "IdpConfigurazioneTributi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DatiAnagraficiEnte }
     * 
     */
    public DatiAnagraficiEnte createDatiAnagraficiEnte() {
        return new DatiAnagraficiEnte();
    }

    /**
     * Create an instance of {@link IdpConfigurazioneTributi }
     * 
     */
    public IdpConfigurazioneTributi createIdpConfigurazioneTributi() {
        return new IdpConfigurazioneTributi();
    }

    /**
     * Create an instance of {@link IdpBodyTributi }
     * 
     */
    public IdpBodyTributi createIdpBodyTributi() {
        return new IdpBodyTributi();
    }

    /**
     * Create an instance of {@link SILType }
     * 
     */
    public SILType createSILType() {
        return new SILType();
    }

    /**
     * Create an instance of {@link ParametriGenerazioneIUVTributoType }
     * 
     */
    public ParametriGenerazioneIUVTributoType createParametriGenerazioneIUVTributoType() {
        return new ParametriGenerazioneIUVTributoType();
    }

    /**
     * Create an instance of {@link ParametriGenerazioneIUVEnteType }
     * 
     */
    public ParametriGenerazioneIUVEnteType createParametriGenerazioneIUVEnteType() {
        return new ParametriGenerazioneIUVEnteType();
    }

    /**
     * Create an instance of {@link IdpConfigurazioneEnte }
     * 
     */
    public IdpConfigurazioneEnte createIdpConfigurazioneEnte() {
        return new IdpConfigurazioneEnte();
    }

    /**
     * Create an instance of {@link ConfigurazioneEnte }
     * 
     */
    public ConfigurazioneEnte createConfigurazioneEnte() {
        return new ConfigurazioneEnte();
    }

    /**
     * Create an instance of {@link ConfigurazioneTributi }
     * 
     */
    public ConfigurazioneTributi createConfigurazioneTributi() {
        return new ConfigurazioneTributi();
    }

    /**
     * Create an instance of {@link IdpBodyEnte }
     * 
     */
    public IdpBodyEnte createIdpBodyEnte() {
        return new IdpBodyEnte();
    }

    /**
     * Create an instance of {@link Indirizzo }
     * 
     */
    public Indirizzo createIndirizzo() {
        return new Indirizzo();
    }

    /**
     * Create an instance of {@link Tributo }
     * 
     */
    public Tributo createTributo() {
        return new Tributo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpConfigurazioneEnte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione", name = "IdpConfigurazioneEnte")
    public JAXBElement<IdpConfigurazioneEnte> createIdpConfigurazioneEnte(IdpConfigurazioneEnte value) {
        return new JAXBElement<IdpConfigurazioneEnte>(_IdpConfigurazioneEnte_QNAME, IdpConfigurazioneEnte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpConfigurazioneTributi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpConfigurazione", name = "IdpConfigurazioneTributi")
    public JAXBElement<IdpConfigurazioneTributi> createIdpConfigurazioneTributi(IdpConfigurazioneTributi value) {
        return new JAXBElement<IdpConfigurazioneTributi>(_IdpConfigurazioneTributi_QNAME, IdpConfigurazioneTributi.class, null, value);
    }

}
