package it.tasgroup.idp.rs.enums;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EnumStatoPagamento implements MessageDescription {
	/**
     * Pagamento in corso
     */
    IN_CORSO ("IC", "In Corso", ""),
    /**
     * Pagamento eseguito, in attesa di ricevuta telematica (di fatto ancora in corso).
     */
    ESEGUITO_SBF ("EF", "In Corso", ""),
    /**
     * Pagamento eseguito.
     */
    ESEGUITO ("ES", "In Corso", ""),
    /**
     * Pagamento non eseguito dal PSP, per problemi legati alla transazione finanziaria (e.g. fondi insufficienti nella carta etc..)
     */
    NON_ESEGUITO ("NE", "In Corso", ""),
    /**
     * Pagamento in errore per problemi tecnici.
     */
    IN_ERRORE ("IE", "In Corso", ""),
    /**
     * Pagamento annullato dal sistema (e.g. per timeout)
     */
    ANNULLATO ("AN", "In Corso", ""),
    /**
     * Pagamento annullato dall'utente
     */
    ANNULLATO_OPERATORE ("AO", "In Corso", ""),

    /**
     * Pagamento stornato
     */
    STORNATO ("IC", "In Corso", ""),;

    private String chiave;
    private String descrizione;
    private String chiaveBundle;

    EnumStatoPagamento(String chiave, String descrizione, String chiaveBundle) {
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
