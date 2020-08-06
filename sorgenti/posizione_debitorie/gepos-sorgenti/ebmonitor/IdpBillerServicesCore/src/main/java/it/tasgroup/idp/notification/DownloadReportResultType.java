
package it.tasgroup.idp.notification;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for downloadReportResultType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="downloadReportResultType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IN_CORSO"/>
 *     &lt;enumeration value="DISPONIBILE"/>
 *     &lt;enumeration value="IN_ERRORE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "downloadReportResultType")
@XmlEnum
public enum DownloadReportResultType {

    IN_CORSO,
    DISPONIBILE,
    IN_ERRORE;

    public String value() {
        return name();
    }

    public static DownloadReportResultType fromValue(String v) {
        return valueOf(v);
    }

}
