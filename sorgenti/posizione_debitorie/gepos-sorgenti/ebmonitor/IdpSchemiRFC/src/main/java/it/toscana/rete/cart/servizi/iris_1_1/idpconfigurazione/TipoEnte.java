
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoEnte.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoEnte">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Comune"/>
 *     &lt;enumeration value="Provincia"/>
 *     &lt;enumeration value="Regione"/>
 *     &lt;enumeration value="ASL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoEnte")
@XmlEnum
public enum TipoEnte {

    @XmlEnumValue("Comune")
    COMUNE("Comune"),
    @XmlEnumValue("Provincia")
    PROVINCIA("Provincia"),
    @XmlEnumValue("Regione")
    REGIONE("Regione"),
    ASL("ASL");
    private final String value;

    TipoEnte(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoEnte fromValue(String v) {
        for (TipoEnte c: TipoEnte.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
