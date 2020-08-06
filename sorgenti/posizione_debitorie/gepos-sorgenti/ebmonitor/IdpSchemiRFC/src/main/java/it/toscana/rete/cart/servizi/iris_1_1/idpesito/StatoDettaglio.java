
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoDettaglio.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoDettaglio">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Scartato"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoDettaglio")
@XmlEnum
public enum StatoDettaglio {

    @XmlEnumValue("Scartato")
    SCARTATO("Scartato");
    private final String value;

    StatoDettaglio(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoDettaglio fromValue(String v) {
        for (StatoDettaglio c: StatoDettaglio.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
