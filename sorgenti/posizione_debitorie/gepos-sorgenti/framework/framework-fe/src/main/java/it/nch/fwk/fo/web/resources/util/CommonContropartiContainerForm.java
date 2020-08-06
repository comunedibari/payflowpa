/*
 * Creato il 16-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.resources.util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author EE10800
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CommonContropartiContainerForm extends CommonContainerForm 
{
	private static final long serialVersionUID = 1L;
	
	private Collection contropartiColl = new ArrayList();
	//
	//lista di tutte le coordinate bancarie legate ad una unica controparte
	private Collection  listaAltreCoordinateBancarie = null;   
	//
	//parametri di ricerca per le controparti
	private String ragioneSocialeContro;
	private String categoriaContro;
	private String gruppoContro;
	
	
	//parametri di ritorno nelle disposizioni
	private String ragioneSociale;
	private String codiceFiscale;
	private String indirizzo;
	private String provincia;
	private String comune;
	private String cap;
	private String codiceCliente;
	private String codiceSia;
	private String idContro;
	
	
	
	public String getCategoriaContro() {
		return categoriaContro;
	}
	public void setCategoriaContro(String categoriaContro) {
		this.categoriaContro = categoriaContro;
	}
	
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	
	public String getRagioneSocialeContro() {
		return ragioneSocialeContro;
	}
	public void setRagioneSocialeContro(String ragioneSocialeContro) {
		this.ragioneSocialeContro = ragioneSocialeContro;
	}

	
	public Collection getContropartiColl() {
		return contropartiColl;
	}
	public void setContropartiColl(Collection contropartiColl) {
		this.contropartiColl = contropartiColl;
	}
	
	
	public String getGruppoContro() {
		return gruppoContro;
	}
	public void setGruppoContro(String gruppoContro) {
		this.gruppoContro = gruppoContro;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCodiceCliente() {
		return codiceCliente;
	}
	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}
	public String getCodiceSia() {
		return codiceSia;
	}
	public void setCodiceSia(String codiceSia) {
		this.codiceSia = codiceSia;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIdContro() {
		return idContro;
	}
	public void setIdContro(String idContro) {
		this.idContro = idContro;
	}
	
	public Collection getListaAltreCoordinateBancarie() {
		return listaAltreCoordinateBancarie;
	}
	public void setListaAltreCoordinateBancarie(
			Collection listaAltreCoordinateBancarie) {
		this.listaAltreCoordinateBancarie = listaAltreCoordinateBancarie;
	}
	
	
}
