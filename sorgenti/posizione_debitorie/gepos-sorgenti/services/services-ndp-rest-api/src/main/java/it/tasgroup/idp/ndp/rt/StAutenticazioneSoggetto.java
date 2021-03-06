//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.12 at 04:22:13 PM CET 
//


package it.tasgroup.idp.ndp.rt;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stAutenticazioneSoggetto.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stAutenticazioneSoggetto">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;maxLength value="4"/>
 *     &lt;enumeration value="CNS"/>
 *     &lt;enumeration value="USR"/>
 *     &lt;enumeration value="OTH"/>
 *     &lt;enumeration value="N/A"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stAutenticazioneSoggetto")
@XmlEnum
public enum StAutenticazioneSoggetto {

    CNS("CNS"),
    USR("USR"),
    OTH("OTH"),
    @XmlEnumValue("N/A")
    N_A("N/A");
    private final String value;

    StAutenticazioneSoggetto(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StAutenticazioneSoggetto fromValue(String v) {
        for (StAutenticazioneSoggetto c: StAutenticazioneSoggetto.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
