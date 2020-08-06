
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Stato.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Stato">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Attivo"/>
 *     &lt;enumeration value="Non Attivo"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Stato")
@XmlEnum
public enum Stato {

    @XmlEnumValue("Attivo")
    ATTIVO("Attivo"),
    @XmlEnumValue("Non Attivo")
    NON_ATTIVO("Non Attivo");
    private final String value;

    Stato(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Stato fromValue(String v) {
        for (Stato c: Stato.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
