
package it.tasgroup.idp.notification;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esitoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="esitoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="KO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "esitoType")
@XmlEnum
public enum EsitoType {

    OK,
    KO;

    public String value() {
        return name();
    }

    public static EsitoType fromValue(String v) {
        return valueOf(v);
    }

}
