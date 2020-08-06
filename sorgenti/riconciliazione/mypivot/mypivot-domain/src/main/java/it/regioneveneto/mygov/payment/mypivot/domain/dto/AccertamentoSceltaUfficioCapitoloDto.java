package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import java.util.List;

public class AccertamentoSceltaUfficioCapitoloDto {
	
	
	private String accertamentoId;
	private String codIpa;
	private String codAccertamento;
	
	List<AccertamentoFlussoExportDto> flussiExportDTOList;
	
	List<AccertamentoUfficioCapitoloDto> ufficiTOList;
	
	public AccertamentoSceltaUfficioCapitoloDto() {
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

	public List<AccertamentoUfficioCapitoloDto> getUfficiTOList() {
		return ufficiTOList;
	}

	public void setUfficiTOList(List<AccertamentoUfficioCapitoloDto> ufficiTOList) {
		this.ufficiTOList = ufficiTOList;
	}

	public List<AccertamentoFlussoExportDto> getFlussiExportDTOList() {
		return flussiExportDTOList;
	}

	public void setFlussiExportDTOList(List<AccertamentoFlussoExportDto> flussiExportDTOList) {
		this.flussiExportDTOList = flussiExportDTOList;
	}
	

	public String getCodAccertamento() {
		return codAccertamento;
	}

	public void setCodAccertamento(String codAccertamento) {
		this.codAccertamento = codAccertamento;
	}
}