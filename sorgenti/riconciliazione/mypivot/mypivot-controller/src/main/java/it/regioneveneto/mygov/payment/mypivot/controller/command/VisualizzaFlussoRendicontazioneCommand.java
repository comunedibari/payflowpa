package it.regioneveneto.mygov.payment.mypivot.controller.command;

public class VisualizzaFlussoRendicontazioneCommand {

	private Boolean dataRegolamentoCheck;
	private String dataRegolamentoDa;
	private String dataRegolamentoA;
	private String iuf;
	private String identificativoUnivocoRegolamento;
	private int page;
	private int pageSize;

	public Boolean getDataRegolamentoCheck() {
		return dataRegolamentoCheck;
	}

	public void setDataRegolamentoCheck(Boolean dataRegolamentoCheck) {
		this.dataRegolamentoCheck = dataRegolamentoCheck;
	}

	public String getDataRegolamentoDa() {
		return dataRegolamentoDa;
	}

	public void setDataRegolamentoDa(String dataRegolamentoDa) {
		this.dataRegolamentoDa = dataRegolamentoDa;
	}

	public String getDataRegolamentoA() {
		return dataRegolamentoA;
	}

	public void setDataRegolamentoA(String dataRegolamentoA) {
		this.dataRegolamentoA = dataRegolamentoA;
	}

	public String getIuf() {
		return iuf;
	}

	public void setIuf(String iuf) {
		this.iuf = iuf;
	}

	public String getIdentificativoUnivocoRegolamento() {
		return identificativoUnivocoRegolamento;
	}

	public void setIdentificativoUnivocoRegolamento(String identificativoUnivocoRegolamento) {
		this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}