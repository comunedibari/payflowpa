
package gov.telematici.pagamenti.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stEffettuazioneScelta.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stEffettuazioneScelta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SI"/>
 *     &lt;enumeration value="NO"/>
 *     &lt;enumeration value="PO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stEffettuazioneScelta")
@XmlEnum
public enum StEffettuazioneScelta {

    SI,
    NO,
    PO;

    public String value() {
        return name();
    }

    public static StEffettuazioneScelta fromValue(String v) {
        return valueOf(v);
    }

}
