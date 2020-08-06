package it.tasgroup.idp.rs.enums;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modello di processo di pagamento
 */
@XmlRootElement
public enum EnumModelloPagamento implements MessageDescription {
    /**
     * Pagamento immediato (valore AgID=0).
     */
    IMMEDIATO ("0","Immediato","Immediato"),

    /**
     * Pagamento immediato con supporto carrello multibeneficiario (valore AgID=1).
     */
    IMMEDIATO_CARRELLO("1","Immediato Carrello", "immediatoCarrello"),

    /**
     * Pagamento differito (valore AgID=2)
     */
    DIFFERITO ( "2", "Differito", "differito"),

    /**
     * Pagamento ad Iniziativa PSP (valore AgID=4)
     */
    INIZIATIVA_PSP ( "4", "Iniziatia PSP", "iniziativaPsp");


    private String chiave;
    private String descrizione;
    private String chiaveBundle;

    EnumModelloPagamento(String chiave, String descrizione, String chiaveBundle) {
        this.chiave = chiave;
        this.descrizione = descrizione;
        this.chiaveBundle = chiaveBundle;
    }

    @Override
    public String getChiave() {
        return this.chiave;
    }

    @Override
    public String getDescrizione() {
        return this.descrizione;
    }

    @Override
    public String getChiaveBundle() {
        return this.chiaveBundle;
    }
}
