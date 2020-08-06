package it.regioneveneto.mygov.payment.mypivot.controller.command;

public class VisualizzaFlussoTesoreriaCommand {

	private Boolean dataContabileCheck;
	private String dataContabileDa;
	private String dataContabileA;
	private Boolean dataValutaCheck;
	private String dataValutaDa;
	private String dataValutaA;
	private String deAnnoBolletta;
	private String codBolletta;
	private String importo;
	private String conto;
	private String codOr1;
	private String iuv;
	private String iuf;
	private int page;
	private int pageSize;

	public Boolean getDataContabileCheck() {
		return dataContabileCheck;
	}

	public void setDataContabileCheck(Boolean dataContabileCheck) {
		this.dataContabileCheck = dataContabileCheck;
	}

	public String getDataContabileDa() {
		return dataContabileDa;
	}

	public void setDataContabileDa(String dataContabileDa) {
		this.dataContabileDa = dataContabileDa;
	}

	public String getDataContabileA() {
		return dataContabileA;
	}

	public void setDataContabileA(String dataContabileA) {
		this.dataContabileA = dataContabileA;
	}

	public Boolean getDataValutaCheck() {
		return dataValutaCheck;
	}

	public void setDataValutaCheck(Boolean dataValutaCheck) {
		this.dataValutaCheck = dataValutaCheck;
	}

	public String getDataValutaDa() {
		return dataValutaDa;
	}

	public void setDataValutaDa(String dataValutaDa) {
		this.dataValutaDa = dataValutaDa;
	}

	public String getDataValutaA() {
		return dataValutaA;
	}

	public void setDataValutaA(String dataValutaA) {
		this.dataValutaA = dataValutaA;
	}

	public String getDeAnnoBolletta() {
		return deAnnoBolletta;
	}

	public void setDeAnnoBolletta(String deAnnoBolletta) {
		this.deAnnoBolletta = deAnnoBolletta;
	}

	public String getCodBolletta() {
		return codBolletta;
	}

	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getConto() {
		return conto;
	}

	public void setConto(String conto) {
		this.conto = conto;
	}

	public String getCodOr1() {
		return codOr1;
	}

	public void setCodOr1(String codOr1) {
		this.codOr1 = codOr1;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getIuf() {
		return iuf;
	}

	public void setIuf(String iuf) {
		this.iuf = iuf;
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