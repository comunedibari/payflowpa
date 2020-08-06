
package it.tasgroup.idp.autorizzazionepagamento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoRichiestaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoRichiestaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="V"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoRichiestaType")
@XmlEnum
public enum TipoRichiestaType {

    A,
    V;

    public String value() {
        return name();
    }

    public static TipoRichiestaType fromValue(String v) {
        return valueOf(v);
    }

}
