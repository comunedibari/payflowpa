
package it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoVoce.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoVoce">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ImportoTransato"/>
 *     &lt;enumeration value="ImportoAutorizzato"/>
 *     &lt;enumeration value="ImportoCommissioni"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoVoce")
@XmlEnum
public enum TipoVoce {

    @XmlEnumValue("ImportoTransato")
    IMPORTO_TRANSATO("ImportoTransato"),
    @XmlEnumValue("ImportoAutorizzato")
    IMPORTO_AUTORIZZATO("ImportoAutorizzato"),
    @XmlEnumValue("ImportoCommissioni")
    IMPORTO_COMMISSIONI("ImportoCommissioni");
    private final String value;

    TipoVoce(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoVoce fromValue(String v) {
        for (TipoVoce c: TipoVoce.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
