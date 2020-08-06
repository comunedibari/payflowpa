
package it.toscana.rete.cart.servizi.iris_1_1.idpesito;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificaStatoPagamentoDettagliato.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VerificaStatoPagamentoDettagliato">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="POSIZIONE_NON_PRESENTE"/>
 *     &lt;enumeration value="POSIZIONE_NON_PAGATA"/>
 *     &lt;enumeration value="POSIZIONE_NON_PAGABILE"/>
 *     &lt;enumeration value="POSIZIONE_PAGATA"/>
 *     &lt;enumeration value="POSIZIONE_PAGATA_SBF"/>
 *     &lt;enumeration value="POSIZIONE_CON_PAG_IN_CORSO"/>
 *     &lt;enumeration value="POSIZIONE_CON_DOC_EMESSO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VerificaStatoPagamentoDettagliato")
@XmlEnum
public enum VerificaStatoPagamentoDettagliato {

    POSIZIONE_NON_PRESENTE,
    POSIZIONE_NON_PAGATA,
    POSIZIONE_NON_PAGABILE,
    POSIZIONE_PAGATA,
    POSIZIONE_PAGATA_SBF,
    POSIZIONE_CON_PAG_IN_CORSO,
    POSIZIONE_CON_DOC_EMESSO;

    public String value() {
        return name();
    }

    public static VerificaStatoPagamentoDettagliato fromValue(String v) {
        return valueOf(v);
    }

}
