package it.tasgroup.idp.rs.model.pagamenti;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * tipo
 */
@XmlRootElement
public enum EnumTipoNotifica implements MessageDescription{

	R("R","Regolamento con FR",""),
	I("I","Incasso Contabile","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoNotifica(String chiave, String descrizione, String chiaveBundle) {
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

	public static EnumTipoNotifica getByKey(String chiave) {
		EnumTipoNotifica desiredItem = null; // Default
        for (EnumTipoNotifica item : EnumTipoNotifica.values()) {
            if (item.getChiave().equals(chiave)) {
                desiredItem = item;
                break;
            }
        }
        return desiredItem;
    }

}
