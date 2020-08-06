package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class ErrorTypeDto {

	private String desc;
	private String cod;
	
	public ErrorTypeDto(String cod, String desc){
		this.cod = cod;
		this.desc= desc;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	
}
