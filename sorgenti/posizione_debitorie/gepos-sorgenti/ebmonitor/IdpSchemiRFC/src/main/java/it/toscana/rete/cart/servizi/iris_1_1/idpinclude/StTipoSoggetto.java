
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stTipoSoggetto.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stTipoSoggetto">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="G"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stTipoSoggetto")
@XmlEnum
public enum StTipoSoggetto {

    F,
    G;

    public String value() {
        return name();
    }

    public static StTipoSoggetto fromValue(String v) {
        return valueOf(v);
    }

}
