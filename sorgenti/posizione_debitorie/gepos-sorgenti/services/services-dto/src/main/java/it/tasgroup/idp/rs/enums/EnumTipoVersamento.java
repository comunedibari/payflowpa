package it.tasgroup.idp.rs.enums;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Tipo Versamento. Forma tecnica di pagamento messa a disposizione dal PSP
 */
@XmlRootElement
public enum EnumTipoVersamento implements MessageDescription {
    /**
     * Bonifico Bancario Tesoreria
     */
    BBT("BBT","Bonifico Bancario Tesoreria",""),
    /**
     * Bolletino Postale
     */
    BP("BP","Bolletino Postale",""),

    /**
     *  Addebito Diretto
     */
    AD("AD","Addebito Diretto",""),

    /**
     * Carta di Pagamento
     */
    CP("CP","Carta di Pagamento","Carta di Pagamento"),

    /**
     * Pagamento presso il PSP
     */
    PO("PO","Pagamento presso il PSP",""),

    /**
     * Mybank
     */
    OBEP("OBEP","Mybank","");



    private String chiave;
    private String descrizione;
    private String chiaveBundle;

    EnumTipoVersamento(String chiave, String descrizione, String chiaveBundle) {
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
