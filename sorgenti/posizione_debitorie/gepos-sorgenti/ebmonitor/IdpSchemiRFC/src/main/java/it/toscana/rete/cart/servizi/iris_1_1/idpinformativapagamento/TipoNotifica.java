
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoNotifica.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoNotifica">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ESEGUITO"/>
 *     &lt;enumeration value="REGOLATO"/>
 *     &lt;enumeration value="INCASSO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoNotifica")
@XmlEnum
public enum TipoNotifica {

    ESEGUITO,
    REGOLATO,
    INCASSO;

    public String value() {
        return name();
    }

    public static TipoNotifica fromValue(String v) {
        return valueOf(v);
    }

}
