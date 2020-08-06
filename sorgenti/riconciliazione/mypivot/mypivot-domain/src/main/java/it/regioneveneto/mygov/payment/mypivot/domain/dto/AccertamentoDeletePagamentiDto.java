package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import java.util.List;

public class AccertamentoDeletePagamentiDto {
	
	
	private String accertamentoId;
	private String codIpa;
	
	List<AccertamentoFlussoExportDto> flussiExportDTOList;
	
	
	public AccertamentoDeletePagamentiDto() {
//		super();
	}
	
	
	
	public String getAccertamentoId() {
		return accertamentoId;
	}
	public void setAccertamentoId(String accertamentoId) {
		this.accertamentoId = accertamentoId;
	}
	
	public String getCodIpa() {
		return codIpa;
	}
	public void setCodIpa(String codIpa) {
		this.codIpa = codIpa;
	}


	public List<AccertamentoFlussoExportDto> getFlussiExportDTOList() {
		return flussiExportDTOList;
	}

	public void setFlussiExportDTOList(List<AccertamentoFlussoExportDto> flussiExportDTOList) {
		this.flussiExportDTOList = flussiExportDTOList;
	}
	

}