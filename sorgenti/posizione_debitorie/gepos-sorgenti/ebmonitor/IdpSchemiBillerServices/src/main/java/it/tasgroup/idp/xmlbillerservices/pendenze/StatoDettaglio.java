
package it.tasgroup.idp.xmlbillerservices.pendenze;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoDettaglio.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoDettaglio">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="WARN"/>
 *     &lt;enumeration value="KO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoDettaglio")
@XmlEnum
public enum StatoDettaglio {

    OK,
    WARN,
    KO;

    public String value() {
        return name();
    }

    public static StatoDettaglio fromValue(String v) {
        return valueOf(v);
    }

}
