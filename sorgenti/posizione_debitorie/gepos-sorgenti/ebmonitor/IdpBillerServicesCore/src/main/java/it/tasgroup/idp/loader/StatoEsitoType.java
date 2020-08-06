
package it.tasgroup.idp.loader;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statoEsitoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statoEsitoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DA_ELABORARE"/>
 *     &lt;enumeration value="VALIDATO"/>
 *     &lt;enumeration value="VALIDATO_KO"/>
 *     &lt;enumeration value="ELABORATO_OK"/>
 *     &lt;enumeration value="ELABORATO_KO"/>
 *     &lt;enumeration value="ELABORATO_OK_PARZIALE"/>
 *     &lt;enumeration value="SCONOSCIUTO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statoEsitoType")
@XmlEnum
public enum StatoEsitoType {

    DA_ELABORARE,
    VALIDATO,
    VALIDATO_KO,
    ELABORATO_OK,
    ELABORATO_KO,
    ELABORATO_OK_PARZIALE,
    SCONOSCIUTO;

    public String value() {
        return name();
    }

    public static StatoEsitoType fromValue(String v) {
        return valueOf(v);
    }

}
