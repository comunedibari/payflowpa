
package it.tasgroup.idp.autorizzazionepagamento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificativoUnivocoDebitoreType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IdentificativoUnivocoDebitoreType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="ANONIMO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IdentificativoUnivocoDebitoreType")
@XmlEnum
public enum IdentificativoUnivocoDebitoreType {

    F,
    G,
    ANONIMO;

    public String value() {
        return name();
    }

    public static IdentificativoUnivocoDebitoreType fromValue(String v) {
        return valueOf(v);
    }

}
