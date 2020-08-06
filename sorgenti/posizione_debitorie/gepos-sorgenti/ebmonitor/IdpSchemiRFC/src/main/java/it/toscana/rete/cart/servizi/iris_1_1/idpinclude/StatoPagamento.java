
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoPagamento.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPagamento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Non Pagato"/>
 *     &lt;enumeration value="Pagato"/>
 *     &lt;enumeration value="Non Pagabile"/>
 *     &lt;enumeration value="Pagamento Irregolare"/>
 *     &lt;enumeration value="Pagamento Rimborsato"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoPagamento")
@XmlEnum
public enum StatoPagamento {

    @XmlEnumValue("Non Pagato")
    NON_PAGATO("Non Pagato"),
    @XmlEnumValue("Pagato")
    PAGATO("Pagato"),
    @XmlEnumValue("Non Pagabile")
    NON_PAGABILE("Non Pagabile"),

    /**
     * Pagamento di un importo diverso dal dovuto
     * 
     */
    @XmlEnumValue("Pagamento Irregolare")
    PAGAMENTO_IRREGOLARE("Pagamento Irregolare"),
    @XmlEnumValue("Pagamento Rimborsato")
    PAGAMENTO_RIMBORSATO("Pagamento Rimborsato");
    private final String value;

    StatoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPagamento fromValue(String v) {
        for (StatoPagamento c: StatoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
