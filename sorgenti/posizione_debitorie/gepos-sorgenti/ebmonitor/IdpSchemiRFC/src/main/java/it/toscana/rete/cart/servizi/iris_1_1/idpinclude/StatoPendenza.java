
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoPendenza.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPendenza">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Aperta"/>
 *     &lt;enumeration value="Chiusa"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoPendenza")
@XmlEnum
public enum StatoPendenza {

    @XmlEnumValue("Aperta")
    APERTA("Aperta"),
    @XmlEnumValue("Chiusa")
    CHIUSA("Chiusa");
    private final String value;

    StatoPendenza(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPendenza fromValue(String v) {
        for (StatoPendenza c: StatoPendenza.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
