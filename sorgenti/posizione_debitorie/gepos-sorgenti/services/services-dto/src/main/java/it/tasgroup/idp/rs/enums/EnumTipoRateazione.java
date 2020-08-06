package it.tasgroup.idp.rs.enums;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoRateazione implements MessageDescription {

    /**
     * Pagamento singolo, in unica soluzione
     */
    PAGAMENTO_SINGOLO("S", "Pagamento Singolo", ""),
    /**
     * Rata di un debito rateizzato
     */
    RATA("R", "Rata", "");

    private String chiave;
    private String descrizione;
    private String chiaveBundle;

    EnumTipoRateazione(String chiave, String descrizione, String chiaveBundle) {
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
