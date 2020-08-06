
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Divisa.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Divisa">
 *   &lt;restriction base="{http://idp.tasgroup.it/xmlbillerservices/Common}Max3Text">
 *     &lt;enumeration value="EUR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Divisa")
@XmlEnum
public enum Divisa {

    EUR;

    public String value() {
        return name();
    }

    public static Divisa fromValue(String v) {
        return valueOf(v);
    }

}
