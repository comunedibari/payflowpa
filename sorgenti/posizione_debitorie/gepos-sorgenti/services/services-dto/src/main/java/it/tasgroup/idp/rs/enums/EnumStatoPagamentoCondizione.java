package it.tasgroup.idp.rs.enums;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Possibili stati di una condizione di pagamento
 */
@XmlRootElement
public enum EnumStatoPagamentoCondizione {

	/**
     * Da pagare, pagabile.
     */
    DA_PAGARE,
    /**
     * Pagata
     */
    PAGATA {
        @Override
        public EnumStatoCondizione asStatoCondizione() {
            return EnumStatoCondizione.PAGATA;
        }
    },
    /**
     * Esiste un tentativo di pagamento precedente, ancora in corso
     */
    IN_CORSO,
    /**
     * Non pagabile per termini di validità  o su indicazione dell'ente
     */
    NON_PAGABILE_TERMINI,
    /**
     * Non pagabile in quanto associata a documento emesso e non ancora pagato.
     */
    NON_PAGABILE_ASSOCIATA_DOCUMENTO,
    /**
     * Non pagabile perché rimborsata
     */
    NON_PAGABILE_RIMBORSATA
    {
        @Override
        public EnumStatoCondizione asStatoCondizione() {
            return EnumStatoCondizione.RIMBORSATA;
        }
    },
    /**
     * Pagamento singolo non pagabile in quanto ha in corso un pagamento rateale o viceversa
     */
    NON_PAGABILE_RATEIZZAZIONE,
    /**
     * Non pagabile, la pendenza è chiusa
     */
    NON_PAGABILE_PENDENZA_CHIUSA,
    /**
     * Non pagabile, la condizione è stata pagata irregolarmente
     */
    NON_PAGABILE_PAGAMENTO_IRREGOLARE,

    /**
     * Non pagabile, la condizione e' stata annullata dall'Ente
     */
    NON_PAGABILE_ENTE {
        @Override
        public EnumStatoCondizione asStatoCondizione() {
            return EnumStatoCondizione.ANNULLATO_ENTE;
        }
    };

    public EnumStatoCondizione asStatoCondizione()  {
        return EnumStatoCondizione.DA_PAGARE; // default mapping
    }
}
