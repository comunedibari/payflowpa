package it.tasgroup.iris.dto.tavolooperativo;

import it.tasgroup.iris.dto.ddp.ChiaveValoreDTO;

import java.io.Serializable;
import java.util.List;

public class InformazioniCART_DTO implements Serializable{

	private static final long serialVersionUID = -4295620198690244812L;
	
	private DatiInoltro_DTO datiInoltro;
	
	private List<ChiaveValoreDTO> couples;
	
	private List<DatiCART_DTO> datiCartList;
	
	public DatiInoltro_DTO getDatiInoltro() {
		
		return datiInoltro;
		
	}
	
	public void setDatiInoltro(DatiInoltro_DTO datiInolto) {
		
		this.datiInoltro = datiInolto;
		
	}
	
	public List<DatiCART_DTO> getDatiCartList() {
		
		return datiCartList;
		
	}
	
	public void setDatiCartList(List<DatiCART_DTO> datiCartList) {
		
		this.datiCartList = datiCartList;
		
	}

	public List<ChiaveValoreDTO> getCouples() {
		return couples;
	}

	public void setCouples(List<ChiaveValoreDTO> couples) {
		this.couples = couples;
	}
	
}
