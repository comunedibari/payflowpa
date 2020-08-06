
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoCanaleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoCanaleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="E-MAIL"/>
 *     &lt;enumeration value="BACHECA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoCanaleType")
@XmlEnum
public enum TipoCanaleType {

    @XmlEnumValue("E-MAIL")
    E_MAIL("E-MAIL"),
    BACHECA("BACHECA");
    private final String value;

    TipoCanaleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoCanaleType fromValue(String v) {
        for (TipoCanaleType c: TipoCanaleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
