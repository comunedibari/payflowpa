package it.tasgroup.idp.rs.model.creditore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Testo libero del movimento
 */
@XmlRootElement
public class MovimentoText implements Serializable {

    private String nomeSupporto;

    private String text;

    public MovimentoText() {
    }

    public MovimentoText(String nomeSupporto, String text) {
        this.nomeSupporto = nomeSupporto;
        this.text = text;
    }

    /**
     * Nome supporto
     */
    @XmlElement(required = true)
    public String getNomeSupporto() {
        return nomeSupporto;
    }

    public void setNomeSupporto(String nomeSupporto) {
        this.nomeSupporto = nomeSupporto;
    }


    /*
        Testo libero del movimento
    */
    @XmlElement(required = true)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
