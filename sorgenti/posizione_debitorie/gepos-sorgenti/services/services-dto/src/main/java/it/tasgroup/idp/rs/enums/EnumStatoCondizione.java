package it.tasgroup.idp.rs.enums;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement public enum EnumStatoCondizione implements MessageDescription {
    DA_PAGARE("DA_PAGARE", "DA PAGARE", ""), PAGATA("PAGATA", "PAGATA", ""), ANNULLATO_ENTE("ANNULLATO_ENTE", "ANNULATO ENTE", ""), RIMBORSATA("RIMBORSATA", "RIMBORSATA", "");
    
    private final String chiave;
    private final String descrizione;
    private final String chiaveBundle;
    
    EnumStatoCondizione(String chiave, String descrizione, String chiaveBundle) {
        this.chiave = chiave;
        this.descrizione = descrizione;
        this.chiaveBundle = chiaveBundle;
    }
    
    @Override public String getChiave() {
        return this.chiave;
    }
    
    @Override public String getDescrizione() {
        return this.descrizione;
    }
    
    @Override public String getChiaveBundle() {
        return this.chiaveBundle;
    }
}
