/**
 * 
 */
package it.tasgroup.iris.dto.anagrafica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author pazzik
 *
 */
public class IntestatarioDTO implements Serializable{

	private String idFiscale;
	
	private String email;
	
	private String ragioneSociale;
		
	private IndirizzoDTO indirizzo = new IndirizzoDTO();
	
	private List<IntestatarioOperatoreDTO> intestOperList = new ArrayList<IntestatarioOperatoreDTO>();
	

	public IndirizzoDTO getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(IndirizzoDTO indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdFiscale() {
		return idFiscale;
	}

	public void setIdFiscale(String idFiscale) {
		this.idFiscale = idFiscale;
	}
	
	public boolean isPersonaFisica(){
		
		// se l'ID Fiscale è un Codice Fiscale
		// TODO PAZZIK AGGIUNGERE IN QUESTA SEDE IL CONTROLLO DI VALIDITA' DEL CF?
		if (idFiscale!=null) {
			return idFiscale.length() == 16;
		} else {
			return false;
		}
	}
	
	public boolean isPersonaGiuridica(){
		
		// si suppone di operare solo con PIVA italiane
		if (idFiscale!=null) {
			return idFiscale.length() == 11;
		} else {
			return false;
		}
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public List<IntestatarioOperatoreDTO> getIntestOperList() {
		return intestOperList;
	}

	public void setIntestOperList(List<IntestatarioOperatoreDTO> intestOperList) {
		this.intestOperList = intestOperList;
	}

}
