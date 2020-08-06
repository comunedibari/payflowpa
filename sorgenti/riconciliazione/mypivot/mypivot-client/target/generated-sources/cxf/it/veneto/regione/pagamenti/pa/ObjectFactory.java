
package it.veneto.regione.pagamenti.pa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.veneto.regione.pagamenti.pa package. 
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

    private final static QName _PaaSILChiediElencoFlussiSPC_QNAME = new QName("http://www.regione.veneto.it/pagamenti/pa/", "paaSILChiediElencoFlussiSPC");
    private final static QName _PaaSILChiediElencoFlussiSPCRisposta_QNAME = new QName("http://www.regione.veneto.it/pagamenti/pa/", "paaSILChiediElencoFlussiSPCRisposta");
    private final static QName _PaaSILChiediFlussoSPC_QNAME = new QName("http://www.regione.veneto.it/pagamenti/pa/", "paaSILChiediFlussoSPC");
    private final static QName _PaaSILChiediFlussoSPCRisposta_QNAME = new QName("http://www.regione.veneto.it/pagamenti/pa/", "paaSILChiediFlussoSPCRisposta");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.veneto.regione.pagamenti.pa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PaaSILChiediElencoFlussiSPC }
     * 
     */
    public PaaSILChiediElencoFlussiSPC createPaaSILChiediElencoFlussiSPC() {
        return new PaaSILChiediElencoFlussiSPC();
    }

    /**
     * Create an instance of {@link PaaSILChiediElencoFlussiSPCRisposta }
     * 
     */
    public PaaSILChiediElencoFlussiSPCRisposta createPaaSILChiediElencoFlussiSPCRisposta() {
        return new PaaSILChiediElencoFlussiSPCRisposta();
    }

    /**
     * Create an instance of {@link PaaSILChiediFlussoSPC }
     * 
     */
    public PaaSILChiediFlussoSPC createPaaSILChiediFlussoSPC() {
        return new PaaSILChiediFlussoSPC();
    }

    /**
     * Create an instance of {@link PaaSILChiediFlussoSPCRisposta }
     * 
     */
    public PaaSILChiediFlussoSPCRisposta createPaaSILChiediFlussoSPCRisposta() {
        return new PaaSILChiediFlussoSPCRisposta();
    }

    /**
     * Create an instance of {@link Risposta }
     * 
     */
    public Risposta createRisposta() {
        return new Risposta();
    }

    /**
     * Create an instance of {@link FaultBean }
     * 
     */
    public FaultBean createFaultBean() {
        return new FaultBean();
    }

    /**
     * Create an instance of {@link TipoElencoFlussiSPC }
     * 
     */
    public TipoElencoFlussiSPC createTipoElencoFlussiSPC() {
        return new TipoElencoFlussiSPC();
    }

    /**
     * Create an instance of {@link TipoIdSPC }
     * 
     */
    public TipoIdSPC createTipoIdSPC() {
        return new TipoIdSPC();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaaSILChiediElencoFlussiSPC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.regione.veneto.it/pagamenti/pa/", name = "paaSILChiediElencoFlussiSPC")
    public JAXBElement<PaaSILChiediElencoFlussiSPC> createPaaSILChiediElencoFlussiSPC(PaaSILChiediElencoFlussiSPC value) {
        return new JAXBElement<PaaSILChiediElencoFlussiSPC>(_PaaSILChiediElencoFlussiSPC_QNAME, PaaSILChiediElencoFlussiSPC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaaSILChiediElencoFlussiSPCRisposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.regione.veneto.it/pagamenti/pa/", name = "paaSILChiediElencoFlussiSPCRisposta")
    public JAXBElement<PaaSILChiediElencoFlussiSPCRisposta> createPaaSILChiediElencoFlussiSPCRisposta(PaaSILChiediElencoFlussiSPCRisposta value) {
        return new JAXBElement<PaaSILChiediElencoFlussiSPCRisposta>(_PaaSILChiediElencoFlussiSPCRisposta_QNAME, PaaSILChiediElencoFlussiSPCRisposta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaaSILChiediFlussoSPC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.regione.veneto.it/pagamenti/pa/", name = "paaSILChiediFlussoSPC")
    public JAXBElement<PaaSILChiediFlussoSPC> createPaaSILChiediFlussoSPC(PaaSILChiediFlussoSPC value) {
        return new JAXBElement<PaaSILChiediFlussoSPC>(_PaaSILChiediFlussoSPC_QNAME, PaaSILChiediFlussoSPC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaaSILChiediFlussoSPCRisposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.regione.veneto.it/pagamenti/pa/", name = "paaSILChiediFlussoSPCRisposta")
    public JAXBElement<PaaSILChiediFlussoSPCRisposta> createPaaSILChiediFlussoSPCRisposta(PaaSILChiediFlussoSPCRisposta value) {
        return new JAXBElement<PaaSILChiediFlussoSPCRisposta>(_PaaSILChiediFlussoSPCRisposta_QNAME, PaaSILChiediFlussoSPCRisposta.class, null, value);
    }

}
