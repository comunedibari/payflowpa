
package it.tasgroup.idp.xmlbillerservices.informativapagamento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoNotificaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoNotificaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ESEGUITO"/>
 *     &lt;enumeration value="REGOLATO"/>
 *     &lt;enumeration value="INCASSO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoNotificaType")
@XmlEnum
public enum TipoNotificaType {

    ESEGUITO,
    REGOLATO,
    INCASSO;

    public String value() {
        return name();
    }

    public static TipoNotificaType fromValue(String v) {
        return valueOf(v);
    }

}
