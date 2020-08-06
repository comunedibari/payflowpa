
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoDestinatario.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoDestinatario">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Cittadino"/>
 *     &lt;enumeration value="Delegato"/>
 *     &lt;enumeration value="Altro"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoDestinatario")
@XmlEnum
public enum TipoDestinatario {

    @XmlEnumValue("Cittadino")
    CITTADINO("Cittadino"),

    /**
     * Soggetto delegato al pagamento della posizione debitoria
     * 
     */
    @XmlEnumValue("Delegato")
    DELEGATO("Delegato"),
    @XmlEnumValue("Altro")
    ALTRO("Altro");
    private final String value;

    TipoDestinatario(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoDestinatario fromValue(String v) {
        for (TipoDestinatario c: TipoDestinatario.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
