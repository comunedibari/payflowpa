/**
 * 
 */
package it.tasgroup.iris.dto.anagrafica;


import it.tasgroup.iris.dto.confpagamenti.CfgEntiLogoDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class EnteDTO implements Serializable {
	
	private String codice;
	
	private String id;
	
	private String codiceCart;
    
	private String descrizione;
	
	private String denominazione;
	
	private String gln;
	
	private IntestatarioDTO intestatario = new IntestatarioDTO();
	
	private List<TributoEnteDTO> tributi = new ArrayList<TributoEnteDTO>();
	
	private CfgEntiLogoDTO logo;
	
	private String codiceFiscale;
	
	private String indirizzo;
	
	private String numeroCivico;
	
	private String cap;
	
	private String citta;
	
	private String provincia;
	
	private String telefono;
	
	private String flNdp;
	private String flNdpModello3;
	private String flBancaTesoriera;
	private String flBlf;
	
	private String auxDigit;
	private String codiceStazionePA;
	private String ndpCodSegr;
	
	private String autorizzStampaDDP;
	
	private String infoHomeBO;
	
	private String siaIForm;
	private String siaCbiIForm;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public List<TributoEnteDTO> getTributi() {
		return tributi;
	}
	public void setTributi(List<TributoEnteDTO> tributi) {
		this.tributi = tributi;
	}
	
	/**
	 * @return the denominazione
	 */
	public String getDenominazione() {
		return denominazione;
	}
	
	/**
	 * @param denominazione the denominazione to set
	 */
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public String getCodiceCart() {
		return codiceCart;
	}
	public void setCodiceCart(String codiceCart) {
		this.codiceCart = codiceCart;
	}
	public IntestatarioDTO getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(IntestatarioDTO intestatario) {
		this.intestatario = intestatario;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGln() {
		return gln;
	}
	
	public void setGln(String gln) {
		this.gln = gln;
	}
	
	public CfgEntiLogoDTO getLogo() {
		return logo;
	}
	
	public void setLogo(CfgEntiLogoDTO logo) {
		this.logo = logo;
	}

	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public IndirizzoDTO getIndirizzoDTO() {
		return intestatario.getIndirizzo();
	}
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	public String getFlNdp() {
		return flNdp;
	}
	public void setFlNdp(String flNdp) {
		this.flNdp = flNdp;
	}
	public String getFlNdpModello3() {
		return flNdpModello3;
	}
	public void setFlNdpModello3(String flNdpModello3) {
		this.flNdpModello3 = flNdpModello3;
	}
	public String getFlBancaTesoriera() {
		return flBancaTesoriera;
	}
	public void setFlBancaTesoriera(String flBancaTesoriera) {
		this.flBancaTesoriera = flBancaTesoriera;
	}
	public String getFlBlf() {
		return flBlf;
	}
	public void setFlBlf(String flBlf) {
		this.flBlf = flBlf;
	}
	public String getAuxDigit() {
		return auxDigit;
	}
	public void setAuxDigit(String auxDigit) {
		this.auxDigit = auxDigit;
	}
	public String getCodiceStazionePA() {
		return codiceStazionePA;
	}
	public void setCodiceStazionePA(String codiceStazionePA) {
		this.codiceStazionePA = codiceStazionePA;
	}
	public String getSiaIForm() {
		return siaIForm;
	}
	public void setSiaIForm(String siaIForm) {
		this.siaIForm = siaIForm;
	}
	public String getSiaCbiIForm() {
		return siaCbiIForm;
	}
	public void setSiaCbiIForm(String siaCbiIForm) {
		this.siaCbiIForm = siaCbiIForm;
	}
	public String getSiaFormatted() {
		return (siaIForm == null || siaIForm.isEmpty()) ? "N.D." : siaIForm;
	}
	public String getSiaCbiFormatted() {
		return (siaCbiIForm == null || siaCbiIForm.isEmpty()) ? "N.D." : siaCbiIForm;
	}

	public String getInfoHomeBO() {
		return infoHomeBO;
	}
	public void setInfoHomeBO(String infoHomeBO) {
		this.infoHomeBO = infoHomeBO;
	}
	public String getAutorizzStampaDDP() {
		return autorizzStampaDDP;
	}
	public void setAutorizzStampaDDP(String autorizzStampaDDP) {
		this.autorizzStampaDDP = autorizzStampaDDP;
	}
	
	public String getNdpCodSegr() {
		return ndpCodSegr;
	}

	public void setNdpCodSegr(String ndpCodSegr) {
		this.ndpCodSegr = ndpCodSegr;
	}

	
}
