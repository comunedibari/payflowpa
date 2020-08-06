package it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni;

import java.util.List;

public class StoricoSegnalazioniDto {

	private List<SegnalazioneDto> segnalazioni;
	private int currPage; 
	private int totPage; 
	private int maxElmPerPage;
	private int elemDa; 
	private int elemA; 
	private boolean next; 
	private boolean prev; 

	public StoricoSegnalazioniDto() {
		super();
	}

	public static StoricoSegnalazioniDto getInstanceByCurrPageAndTotPageAndsegnalazioni(int currPage, int totPage,
			int maxElmPerPage, List<SegnalazioneDto> segnalazioni) {
		StoricoSegnalazioniDto dto = new StoricoSegnalazioniDto();
		dto.setSegnalazioni(segnalazioni);
		dto.setCurrPage(currPage);
		dto.setTotPage(totPage);
		dto.setMaxElmPerPage(maxElmPerPage);
		
		int elemDa = maxElmPerPage * (currPage - 1) + 1;
		int elemA = (maxElmPerPage * (currPage - 1)) + segnalazioni.size();
		dto.setElemDa(elemDa);
		dto.setElemA(elemA);

		boolean hasNext = currPage < totPage;
		boolean hasPrev = currPage > 1;
		dto.setNext(hasNext);
		dto.setPrev(hasPrev);
		return dto;
	}

	public List<SegnalazioneDto> getSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(List<SegnalazioneDto> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotPage() {
		return totPage;
	}

	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}

	public int getElemDa() {
		return elemDa;
	}

	public void setElemDa(int elemDa) {
		this.elemDa = elemDa;
	}

	public int getElemA() {
		return elemA;
	}

	public void setElemA(int elemA) {
		this.elemA = elemA;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public int getMaxElmPerPage() {
		return maxElmPerPage;
	}

	public void setMaxElmPerPage(int maxElmPerPage) {
		this.maxElmPerPage = maxElmPerPage;
	}
}
