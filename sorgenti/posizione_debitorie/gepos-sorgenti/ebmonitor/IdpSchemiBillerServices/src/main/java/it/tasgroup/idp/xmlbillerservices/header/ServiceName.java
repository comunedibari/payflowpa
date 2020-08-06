
package it.tasgroup.idp.xmlbillerservices.header;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceName.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceName">
 *   &lt;restriction base="{http://idp.tasgroup.it/xmlbillerservices/Common}Max60Text">
 *     &lt;enumeration value="IdpAllineamentoPendenze"/>
 *     &lt;enumeration value="IdpInformativaPagamento"/>
 *     &lt;enumeration value="IdpRendicontazioneEnti"/>
 *     &lt;enumeration value="IdpConfigurazioneEnte"/>
 *     &lt;enumeration value="IdpAutorizzazioneDiPagamento"/>
 *     &lt;enumeration value="IdpEstrattoContoDebitorio"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ServiceName")
@XmlEnum
public enum ServiceName {

    @XmlEnumValue("IdpAllineamentoPendenze")
    IDP_ALLINEAMENTO_PENDENZE("IdpAllineamentoPendenze"),
    @XmlEnumValue("IdpInformativaPagamento")
    IDP_INFORMATIVA_PAGAMENTO("IdpInformativaPagamento"),
    @XmlEnumValue("IdpRendicontazioneEnti")
    IDP_RENDICONTAZIONE_ENTI("IdpRendicontazioneEnti"),
    @XmlEnumValue("IdpConfigurazioneEnte")
    IDP_CONFIGURAZIONE_ENTE("IdpConfigurazioneEnte"),
    @XmlEnumValue("IdpAutorizzazioneDiPagamento")
    IDP_AUTORIZZAZIONE_DI_PAGAMENTO("IdpAutorizzazioneDiPagamento"),
    @XmlEnumValue("IdpEstrattoContoDebitorio")
    IDP_ESTRATTO_CONTO_DEBITORIO("IdpEstrattoContoDebitorio");
    private final String value;

    ServiceName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceName fromValue(String v) {
        for (ServiceName c: ServiceName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
