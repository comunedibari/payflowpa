
package it.toscana.rete.cart.servizi.iris_1_1.idpinclude;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoPagamento.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoPagamento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Pagamento a Rate"/>
 *     &lt;enumeration value="Pagamento Unico"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoPagamento")
@XmlEnum
public enum TipoPagamento {

    @XmlEnumValue("Pagamento a Rate")
    PAGAMENTO_A_RATE("Pagamento a Rate"),
    @XmlEnumValue("Pagamento Unico")
    PAGAMENTO_UNICO("Pagamento Unico");
    private final String value;

    TipoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoPagamento fromValue(String v) {
        for (TipoPagamento c: TipoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
