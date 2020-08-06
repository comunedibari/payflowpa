
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlagNoSì.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FlagNoSì">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="No"/>
 *     &lt;enumeration value="Sì"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FlagNoS\u00ec")
@XmlEnum
public enum FlagNoSì {

    @XmlEnumValue("No")
    NO("No"),
    @XmlEnumValue("S\u00ec")
    SÌ("S\u00ec");
    private final String value;

    FlagNoSì(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FlagNoSì fromValue(String v) {
        for (FlagNoSì c: FlagNoSì.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
