
package it.tasgroup.idp.xmlbillerservices.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MIMETypeCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MIMETypeCode">
 *   &lt;restriction base="{http://idp.tasgroup.it/xmlbillerservices/Common}Max4Text">
 *     &lt;enumeration value="GIF_"/>
 *     &lt;enumeration value="HTML"/>
 *     &lt;enumeration value="JPEG"/>
 *     &lt;enumeration value="LNK_"/>
 *     &lt;enumeration value="MSWD"/>
 *     &lt;enumeration value="MSEX"/>
 *     &lt;enumeration value="MSPP"/>
 *     &lt;enumeration value="PDF_"/>
 *     &lt;enumeration value="PNG_"/>
 *     &lt;enumeration value="TEXT"/>
 *     &lt;enumeration value="XML_"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MIMETypeCode")
@XmlEnum
public enum MIMETypeCode {

    @XmlEnumValue("GIF_")
    GIF("GIF_"),
    HTML("HTML"),
    JPEG("JPEG"),
    @XmlEnumValue("LNK_")
    LNK("LNK_"),
    MSWD("MSWD"),
    MSEX("MSEX"),
    MSPP("MSPP"),
    @XmlEnumValue("PDF_")
    PDF("PDF_"),
    @XmlEnumValue("PNG_")
    PNG("PNG_"),
    TEXT("TEXT"),
    @XmlEnumValue("XML_")
    XML("XML_");
    private final String value;

    MIMETypeCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MIMETypeCode fromValue(String v) {
        for (MIMETypeCode c: MIMETypeCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
