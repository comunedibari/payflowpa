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
public class IntestatarioOperatoreDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6820346063913324585L;

	private String tipoOperatore;
	
	private Integer locked;

	private OperatoreDTO operatore;
	
    private IntestatarioDTO intestatario;
    
	private List<TributiOperatoreDTO> tributiOperatoreList = new ArrayList<TributiOperatoreDTO>();

    
	public String getTipoOperatore() {
		return tipoOperatore;
	}
	public void setTipoOperatore(String tipoOperatore) {
		this.tipoOperatore = tipoOperatore;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public OperatoreDTO getOperatore() {
		return operatore;
	}
	public void setOperatore(OperatoreDTO operatore) {
		this.operatore = operatore;
	}
	public IntestatarioDTO getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(IntestatarioDTO intestatario) {
		this.intestatario = intestatario;
	}
	public void setTributiOperList(List<TributiOperatoreDTO> tributiOperatoreList) {
		this.tributiOperatoreList = tributiOperatoreList;
	}

}
