package it.tasgroup.idp.rs.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 */
@XmlRootElement
public class OpzionePagamentoCondizione implements Serializable {
    private String idCondizione;
    private boolean flagOpposizione730;

    /**
     * id  della condizione di pagamento
     * @return
     */
    public String getIdCondizione() {
        return idCondizione;
    }

    public void setIdCondizione(String idCondizione) {
        this.idCondizione = idCondizione;
    }

    /**
     * flag opposizione 730 true/false
     * @return
     */
    public boolean isFlagOpposizione730() {
        return flagOpposizione730;
    }

    public void setFlagOpposizione730(boolean flagOpposizione730) {
        this.flagOpposizione730 = flagOpposizione730;
    }
}
