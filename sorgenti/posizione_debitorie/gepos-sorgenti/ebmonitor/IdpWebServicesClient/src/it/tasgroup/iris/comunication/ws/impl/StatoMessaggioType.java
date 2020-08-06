
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoMessaggioType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoMessaggioType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVIATO"/>
 *     &lt;enumeration value="ERRORE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoMessaggioType")
@XmlEnum
public enum StatoMessaggioType {

    INVIATO,
    ERRORE;

    public String value() {
        return name();
    }

    public static StatoMessaggioType fromValue(String v) {
        return valueOf(v);
    }

}
