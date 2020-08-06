/**
 * 
 */
package it.tasgroup.iris.dto.anagrafica;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class OperatoreDTO {
	
	private String nome;
	
	private String cognome;
	
	private List<IntestatarioOperatoreDTO> intestOperList = new ArrayList<IntestatarioOperatoreDTO>();
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<IntestatarioOperatoreDTO> getIntestOperList() {
		return intestOperList;
	}

	public void setIntestOperList(List<IntestatarioOperatoreDTO> intestOperList) {
		this.intestOperList = intestOperList;
	}

	
}
