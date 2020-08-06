package it.nch.idp.backoffice.monitoraggioflussi;

import java.util.Calendar;

import org.apache.struts.validator.ValidatorActionForm;

public class RicercaOperativitaEnteForm extends ValidatorActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1419526556917626820L;
	private String idEnte;
	private String annoRiferimento;
	
	public RicercaOperativitaEnteForm() {
		annoRiferimento = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)); 
	}

    public String getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(String idEnte) {
        this.idEnte = idEnte;
    }

 	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	
}
