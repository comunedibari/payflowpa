
package it.gov.digitpa.schemas._2011.pagamenti;

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
