 /**
  *
  */
package it.tasgroup.iris.dto.anagrafica;

import java.io.Serializable;

/**
 * @author PazziK
 *
 */
public class IndirizzoDTO implements Serializable{
    
    private String ragioneSociale;
    private String via;
    private String numeroCivico;
    private String cap;
    private String citta;
    private String provincia;
    private String nazione;
    private String piva;
    private String codiceFiscale;
    private String email;
    
  //usato per visualizzare il barcode del Bollettino AGID
    private String gln;
    
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }
    
    public String getVia() {
        return via;
    }
    
    public void setVia(String via) {
        this.via = via;
    }
    
    public String getNumeroCivico() {
        return numeroCivico;
    }
    
    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }
    
    public String getCap() {
        return cap;
    }
    
    public void setCap(String cap) {
        this.cap = cap;
    }
    
    public String getCitta() {
        return citta;
    }
    
    public void setCitta(String citta) {
        this.citta = citta;
    }
    
    public String getProvincia() {
        return provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    public String getPiva() {
        return piva;
    }
    
    public void setPiva(String piva) {
        this.piva = piva;
    }
    
    
    public String toString() {
        
        StringBuffer sb = new StringBuffer();
        sb.append("\n"+this.getClass()+"\n");
        sb.append("[");
        sb.append("ragioneSociale="+this.getRagioneSociale());
        sb.append(", via="+this.getVia());
        sb.append(", numeroCivico="+this.getNumeroCivico());
        sb.append(", cap="+this.getCap());
        sb.append(", citta="+this.getCitta());
        sb.append(", provincia="+this.getProvincia());
        sb.append(", PIVA="+this.getPiva());
        sb.append(", CF="+this.getCodiceFiscale());
        sb.append("]\n");
        return sb.toString();
    }
    
    public String getCittaFormattato(){
    	return this.cap + " " +this.getCitta() + " " + this.getProvincia();
    }
    
	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGln() {
		return gln;
	}

	public void setGln(String gln) {
		this.gln = gln;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
    
}
