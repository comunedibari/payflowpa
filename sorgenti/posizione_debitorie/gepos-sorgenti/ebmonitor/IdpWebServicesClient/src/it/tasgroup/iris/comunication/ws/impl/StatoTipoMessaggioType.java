
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoTipoMessaggioType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoTipoMessaggioType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ATTIVO"/>
 *     &lt;enumeration value="DISATTIVO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoTipoMessaggioType")
@XmlEnum
public enum StatoTipoMessaggioType {

    ATTIVO,
    DISATTIVO;

    public String value() {
        return name();
    }

    public static StatoTipoMessaggioType fromValue(String v) {
        return valueOf(v);
    }

}
