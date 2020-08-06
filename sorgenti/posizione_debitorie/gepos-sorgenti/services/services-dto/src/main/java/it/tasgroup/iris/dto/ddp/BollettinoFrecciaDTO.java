/**
 * 
 */
package it.tasgroup.iris.dto.ddp;

import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.io.Serializable;

/**
 * @author pazzik
 *
 */
public class BollettinoFrecciaDTO extends DettaglioDDPBancarioDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final EnumTipoDDP tipo = EnumTipoDDP.FRECCIA;
	
	private String csia;
	private String ind;
	private String citta;
	private String cap;
	private String prov;
	private String mail;
	
	
	public String getCsia() {
		return csia;
	}
	public void setCsia(String csia) {
		this.csia = csia;
	}
	
	public String getInd() {
		return ind;
	}
	public void setInd(String ind) {
		this.ind = ind;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	
	@Override
	public EnumTipoDDP getTipo() {

		return tipo;
	}
	@Override
	public String getFormattedID() {
		return null;
	}

	
	
}
