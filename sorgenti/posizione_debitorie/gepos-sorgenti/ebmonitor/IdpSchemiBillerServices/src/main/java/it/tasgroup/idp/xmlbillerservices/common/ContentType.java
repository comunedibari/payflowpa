
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Documento"/>
 *     &lt;enumeration value="Ricevuta"/>
 *     &lt;enumeration value="Quietanza"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContentType")
@XmlEnum
public enum ContentType {

    @XmlEnumValue("Documento")
    DOCUMENTO("Documento"),
    @XmlEnumValue("Ricevuta")
    RICEVUTA("Ricevuta"),
    @XmlEnumValue("Quietanza")
    QUIETANZA("Quietanza");
    private final String value;

    ContentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContentType fromValue(String v) {
        for (ContentType c: ContentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
