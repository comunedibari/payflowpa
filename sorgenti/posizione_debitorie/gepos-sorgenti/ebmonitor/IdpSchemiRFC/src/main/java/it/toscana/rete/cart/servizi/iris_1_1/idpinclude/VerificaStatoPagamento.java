
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificaStatoPagamento.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VerificaStatoPagamento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Posizione non presente"/>
 *     &lt;enumeration value="Posizione non pagabile"/>
 *     &lt;enumeration value="Pagamento non eseguito"/>
 *     &lt;enumeration value="Pagamento eseguito"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VerificaStatoPagamento")
@XmlEnum
public enum VerificaStatoPagamento {


    /**
     * L'identificativo fornito non riferisce una posizione debitoria registrata su IRIS
     * 
     */
    @XmlEnumValue("Posizione non presente")
    POSIZIONE_NON_PRESENTE("Posizione non presente"),

    /**
     * L'identificativo fornito riferisce una posizione debitoria non pagabile su IRIS
     * 
     */
    @XmlEnumValue("Posizione non pagabile")
    POSIZIONE_NON_PAGABILE("Posizione non pagabile"),
    @XmlEnumValue("Pagamento non eseguito")
    PAGAMENTO_NON_ESEGUITO("Pagamento non eseguito"),
    @XmlEnumValue("Pagamento eseguito")
    PAGAMENTO_ESEGUITO("Pagamento eseguito");
    private final String value;

    VerificaStatoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerificaStatoPagamento fromValue(String v) {
        for (VerificaStatoPagamento c: VerificaStatoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
