
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoOperazione.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoOperazione">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Insert"/>
 *     &lt;enumeration value="UpdateStatus"/>
 *     &lt;enumeration value="Replace"/>
 *     &lt;enumeration value="Delete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoOperazione")
@XmlEnum
public enum TipoOperazione {

    @XmlEnumValue("Insert")
    INSERT("Insert"),
    @XmlEnumValue("UpdateStatus")
    UPDATE_STATUS("UpdateStatus"),
    @XmlEnumValue("Replace")
    REPLACE("Replace"),
    @XmlEnumValue("Delete")
    DELETE("Delete");
    private final String value;

    TipoOperazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoOperazione fromValue(String v) {
        for (TipoOperazione c: TipoOperazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
