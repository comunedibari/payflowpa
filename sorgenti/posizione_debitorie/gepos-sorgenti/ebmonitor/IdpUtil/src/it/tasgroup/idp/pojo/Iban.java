package it.tasgroup.idp.pojo;

public class Iban {
	
	private String iban;
	
	public Iban(String iban) {
		this.iban = iban;
	}
	public Iban(String codPaese,String checkDigit,String cin,String abi,String cab,String numeroConto) {
		iban = codPaese + checkDigit + cin + abi+ cab + numeroConto;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getCodPaese() {
		return iban.substring(0, 2);
	}

	public String getCheckDigit() {
		return iban.substring(2, 4);
	}
	
	public String getCin() {
		return iban.substring(4, 5);
	}
	
	public String getAbi() {
		return iban.substring(5, 10);
	}
	
	public String getCab() {
		return iban.substring(10, 15);
	}
	
	public String getNumeroConto() {
		return iban.substring(15);
	}
	


}
