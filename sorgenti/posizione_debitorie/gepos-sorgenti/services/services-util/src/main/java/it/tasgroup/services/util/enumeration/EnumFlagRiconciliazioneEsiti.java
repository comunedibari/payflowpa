package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;
import java.util.ArrayList;
import java.util.List;

public enum EnumFlagRiconciliazioneEsiti implements MessageDescription{
    
    Fm1("-1","Scartato",""),
    F0("0","Da riconciliare",""),
    F1("1","Riconciliato",""),
    F2("2","Non Riconciliato",""),
    F3("3","Riconciliato (non eseguito)",""),
    F4("4","Riconciliato (importo minore del dovuto)","");
    
    private String chiave;
    private String descrizione;
    private String chiaveBundle;
    
    private EnumFlagRiconciliazioneEsiti(String chiave, String descrizione, String chiaveBundle) {
        this.chiave = chiave;
        this.descrizione = descrizione;
        this.chiaveBundle = chiaveBundle;
    }
    
    public String getChiave() {
        return chiave;
    }
    
    public void setChiave(String chiave) {
        this.chiave = chiave;
    }
    
    public String getDescrizione() {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public String getChiaveBundle() {
        return chiaveBundle;
    }
    
    public void setChiaveBundle(String chiaveBundle) {
        this.chiaveBundle = chiaveBundle;
    }
    
    public static List<String> getChiaveEsitAnomali(){
        List<String> flags = new ArrayList<String>();
        flags.add(EnumFlagRiconciliazioneEsiti.F0.chiave);
        flags.add(EnumFlagRiconciliazioneEsiti.F2.chiave);
        flags.add(EnumFlagRiconciliazioneEsiti.F3.chiave);
        flags.add(EnumFlagRiconciliazioneEsiti.F4.chiave);
        return flags;
        
    }
    
    public static EnumFlagRiconciliazioneEsiti getEnumFlagRiconciliazioneEsitiByChiave(Integer chiave) {
        if (chiave == null)
            return null;
        String key = chiave.toString();
        
        EnumFlagRiconciliazioneEsiti desiredValue = null;
        
        for (EnumFlagRiconciliazioneEsiti item : EnumFlagRiconciliazioneEsiti.values()) {
            if (item.getChiave().equals(key)) {
                desiredValue = item;
                break;
            }
        }
        
        return desiredValue;
    }
}
