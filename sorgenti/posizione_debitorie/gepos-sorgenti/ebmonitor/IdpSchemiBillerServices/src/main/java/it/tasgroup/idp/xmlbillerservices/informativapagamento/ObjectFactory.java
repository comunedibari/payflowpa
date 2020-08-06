
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.tasgroup.idp.xmlbillerservices.informativapagamento package. 
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

    private final static QName _IdpEsitoNotifica_QNAME = new QName("http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", "IdpEsitoNotifica");
    private final static QName _IdpInformativaPagamento_QNAME = new QName("http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", "IdpInformativaPagamento");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.tasgroup.idp.xmlbillerservices.informativapagamento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RiferimentoSoggetto }
     * 
     */
    public RiferimentoSoggetto createRiferimentoSoggetto() {
        return new RiferimentoSoggetto();
    }

    /**
     * Create an instance of {@link InformazioniPagamentoType }
     * 
     */
    public InformazioniPagamentoType createInformazioniPagamentoType() {
        return new InformazioniPagamentoType();
    }

    /**
     * Create an instance of {@link it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpBody }
     * 
     */
    public it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpBody createIdpBody() {
        return new it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpBody();
    }

    /**
     * Create an instance of {@link IdPagamento }
     * 
     */
    public IdPagamento createIdPagamento() {
        return new IdPagamento();
    }

    /**
     * Create an instance of {@link Pagamento }
     * 
     */
    public Pagamento createPagamento() {
        return new Pagamento();
    }

    /**
     * Create an instance of {@link IdpEsitoVerifica }
     * 
     */
    public IdpEsitoVerifica createIdpEsitoVerifica() {
        return new IdpEsitoVerifica();
    }

    /**
     * Create an instance of {@link IdpEsitoNotifica }
     * 
     */
    public IdpEsitoNotifica createIdpEsitoNotifica() {
        return new IdpEsitoNotifica();
    }

    /**
     * Create an instance of {@link IdpVerificaStatoPagamento }
     * 
     */
    public IdpVerificaStatoPagamento createIdpVerificaStatoPagamento() {
        return new IdpVerificaStatoPagamento();
    }

    /**
     * Create an instance of {@link IdpInformativaPagamento }
     * 
     */
    public IdpInformativaPagamento createIdpInformativaPagamento() {
        return new IdpInformativaPagamento();
    }

    /**
     * Create an instance of {@link IdpVerificaStatoPagamento.IdpBody }
     * 
     */
    public IdpVerificaStatoPagamento.IdpBody createIdpVerificaStatoPagamentoIdpBody() {
        return new IdpVerificaStatoPagamento.IdpBody();
    }

    /**
     * Create an instance of {@link Transazione }
     * 
     */
    public Transazione createTransazione() {
        return new Transazione();
    }

    /**
     * Create an instance of {@link RiferimentoIncasso }
     * 
     */
    public RiferimentoIncasso createRiferimentoIncasso() {
        return new RiferimentoIncasso();
    }

    /**
     * Create an instance of {@link Esito }
     * 
     */
    public Esito createEsito() {
        return new Esito();
    }

    /**
     * Create an instance of {@link CanalePagamento }
     * 
     */
    public CanalePagamento createCanalePagamento() {
        return new CanalePagamento();
    }

    /**
     * Create an instance of {@link IdpEsitoVerifica.IdpBodyEsitoVerifica }
     * 
     */
    public IdpEsitoVerifica.IdpBodyEsitoVerifica createIdpEsitoVerificaIdpBodyEsitoVerifica() {
        return new IdpEsitoVerifica.IdpBodyEsitoVerifica();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpEsitoNotifica }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", name = "IdpEsitoNotifica")
    public JAXBElement<IdpEsitoNotifica> createIdpEsitoNotifica(IdpEsitoNotifica value) {
        return new JAXBElement<IdpEsitoNotifica>(_IdpEsitoNotifica_QNAME, IdpEsitoNotifica.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpInformativaPagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", name = "IdpInformativaPagamento")
    public JAXBElement<IdpInformativaPagamento> createIdpInformativaPagamento(IdpInformativaPagamento value) {
        return new JAXBElement<IdpInformativaPagamento>(_IdpInformativaPagamento_QNAME, IdpInformativaPagamento.class, null, value);
    }

}
