
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoMessaggio.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoMessaggio">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Elaborato Correttamente"/>
 *     &lt;enumeration value="Elaborato con Errori"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoMessaggio")
@XmlEnum
public enum StatoMessaggio {

    @XmlEnumValue("Elaborato Correttamente")
    ELABORATO_CORRETTAMENTE("Elaborato Correttamente"),
    @XmlEnumValue("Elaborato con Errori")
    ELABORATO_CON_ERRORI("Elaborato con Errori");
    private final String value;

    StatoMessaggio(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoMessaggio fromValue(String v) {
        for (StatoMessaggio c: StatoMessaggio.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
