package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;

public class CasellarioInfoDTO  extends CasellarioDTO implements Serializable{
    
	private static final long serialVersionUID = 1L;

	private String mittente;
    private String ricevente;
    private byte[] flussoCBI;
    private RendicontazioniDTOLight rendicontazioni;
    private String nomeFile;
    
    
    public RendicontazioniDTOLight getRendicontazioni() {
        return rendicontazioni;
    }
    public void setRendicontazioni(RendicontazioniDTOLight rendicontazioni) {
        this.rendicontazioni = rendicontazioni;
    }
    
    public byte[] getFlussoCBI() {
        return flussoCBI;
    }
    public void setFlussoCBI(byte[] flussoCBI) {
        this.flussoCBI = flussoCBI;
    }
    
    public String getMittente() {
        return mittente;
    }
    
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
    
    public String getRicevente() {
        return ricevente;
    }
    
    public void setRicevente(String ricevente) {
        this.ricevente = ricevente;
    }

	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}