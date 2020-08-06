
package it.gov.digitpa.schemas._2011.pagamenti;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stTipoVersamento.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stTipoVersamento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BBT"/>
 *     &lt;enumeration value="BP"/>
 *     &lt;enumeration value="AD"/>
 *     &lt;enumeration value="CP"/>
 *     &lt;enumeration value="PO"/>
 *     &lt;enumeration value="OBEP"/>
 *     &lt;enumeration value="OTH"/>
 *     &lt;enumeration value="JIF"/>
 *     &lt;maxLength value="4"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stTipoVersamento")
@XmlEnum
public enum StTipoVersamento {

    BBT,
    BP,
    AD,
    CP,
    PO,
    OBEP,
    OTH,
    JIF;

    public String value() {
        return name();
    }

    public static StTipoVersamento fromValue(String v) {
        return valueOf(v);
    }

}
