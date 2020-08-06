package it.tasgroup.idp.gateway.enums;

public enum EnumCanalePagamento implements KeyEnums {
	
	WEB(1L), 
	BANCA(3L), 
	POSTE(6L), 
	PSP(14L);

	private Long key;

	EnumCanalePagamento(Long key) {
		this.key = key;
	}

	@Override
	public Long getKey() {
		return this.key;
	}

}
