
package it.veneto.regione.schemas._2012.pagamenti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.veneto.regione.schemas._2012.pagamenti package. 
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

    private final static QName _RP_QNAME = new QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/", "RP");
    private final static QName _Esito_QNAME = new QName("http://www.regione.veneto.it/schemas/2012/Pagamenti/", "Esito");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.veneto.regione.schemas._2012.pagamenti
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CtRichiestaPagamento }
     * 
     */
    public CtRichiestaPagamento createCtRichiestaPagamento() {
        return new CtRichiestaPagamento();
    }

    /**
     * Create an instance of {@link CtEsito }
     * 
     */
    public CtEsito createCtEsito() {
        return new CtEsito();
    }

    /**
     * Create an instance of {@link CtDominio }
     * 
     */
    public CtDominio createCtDominio() {
        return new CtDominio();
    }

    /**
     * Create an instance of {@link CtIdentificativoUnivoco }
     * 
     */
    public CtIdentificativoUnivoco createCtIdentificativoUnivoco() {
        return new CtIdentificativoUnivoco();
    }

    /**
     * Create an instance of {@link CtIdentificativoUnivocoPersonaFG }
     * 
     */
    public CtIdentificativoUnivocoPersonaFG createCtIdentificativoUnivocoPersonaFG() {
        return new CtIdentificativoUnivocoPersonaFG();
    }

    /**
     * Create an instance of {@link CtIdentificativoUnivocoPersonaG }
     * 
     */
    public CtIdentificativoUnivocoPersonaG createCtIdentificativoUnivocoPersonaG() {
        return new CtIdentificativoUnivocoPersonaG();
    }

    /**
     * Create an instance of {@link CtSoggettoVersante }
     * 
     */
    public CtSoggettoVersante createCtSoggettoVersante() {
        return new CtSoggettoVersante();
    }

    /**
     * Create an instance of {@link CtSoggettoPagatore }
     * 
     */
    public CtSoggettoPagatore createCtSoggettoPagatore() {
        return new CtSoggettoPagatore();
    }

    /**
     * Create an instance of {@link CtEnteBeneficiario }
     * 
     */
    public CtEnteBeneficiario createCtEnteBeneficiario() {
        return new CtEnteBeneficiario();
    }

    /**
     * Create an instance of {@link CtIstitutoAttestante }
     * 
     */
    public CtIstitutoAttestante createCtIstitutoAttestante() {
        return new CtIstitutoAttestante();
    }

    /**
     * Create an instance of {@link CtDatiSingoloVersamentoRP }
     * 
     */
    public CtDatiSingoloVersamentoRP createCtDatiSingoloVersamentoRP() {
        return new CtDatiSingoloVersamentoRP();
    }

    /**
     * Create an instance of {@link CtDatiVersamentoRP }
     * 
     */
    public CtDatiVersamentoRP createCtDatiVersamentoRP() {
        return new CtDatiVersamentoRP();
    }

    /**
     * Create an instance of {@link CtDatiSingoloPagamentoEsito }
     * 
     */
    public CtDatiSingoloPagamentoEsito createCtDatiSingoloPagamentoEsito() {
        return new CtDatiSingoloPagamentoEsito();
    }

    /**
     * Create an instance of {@link CtDatiVersamentoEsito }
     * 
     */
    public CtDatiVersamentoEsito createCtDatiVersamentoEsito() {
        return new CtDatiVersamentoEsito();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtRichiestaPagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.regione.veneto.it/schemas/2012/Pagamenti/", name = "RP")
    public JAXBElement<CtRichiestaPagamento> createRP(CtRichiestaPagamento value) {
        return new JAXBElement<CtRichiestaPagamento>(_RP_QNAME, CtRichiestaPagamento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CtEsito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.regione.veneto.it/schemas/2012/Pagamenti/", name = "Esito")
    public JAXBElement<CtEsito> createEsito(CtEsito value) {
        return new JAXBElement<CtEsito>(_Esito_QNAME, CtEsito.class, null, value);
    }

}
