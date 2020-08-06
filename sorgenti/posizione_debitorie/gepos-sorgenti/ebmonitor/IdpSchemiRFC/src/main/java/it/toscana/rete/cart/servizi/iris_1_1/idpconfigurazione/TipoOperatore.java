
package it.toscana.rete.cart.servizi.iris_1_1.idpconfigurazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoOperatore.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoOperatore">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Amministratore"/>
 *     &lt;enumeration value="Operatore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoOperatore")
@XmlEnum
public enum TipoOperatore {

    @XmlEnumValue("Amministratore")
    AMMINISTRATORE("Amministratore"),
    @XmlEnumValue("Operatore")
    OPERATORE("Operatore");
    private final String value;

    TipoOperatore(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoOperatore fromValue(String v) {
        for (TipoOperatore c: TipoOperatore.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
