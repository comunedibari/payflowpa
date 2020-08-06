
package it.gov.digitpa.schemas._2011.pagamenti;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stTipoAllegatoRicevuta.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stTipoAllegatoRicevuta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ES"/>
 *     &lt;enumeration value="BD"/>
 *     &lt;length value="2"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stTipoAllegatoRicevuta")
@XmlEnum
public enum StTipoAllegatoRicevuta {

    ES,
    BD;

    public String value() {
        return name();
    }

    public static StTipoAllegatoRicevuta fromValue(String v) {
        return valueOf(v);
    }

}
