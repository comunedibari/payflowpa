package it.tasgroup.idp.gateway.enums;

public enum EnumTipoCommissione implements KeyEnums {
	
	IMPORTO(1L), 
	PERCENTUALE(2L), 
	GESTIONE_ESTERNA(3L); 

	private Long key;

	EnumTipoCommissione(Long key) {
		this.key = key;
	}

	@Override
	public Long getKey() {
		return this.key;
	}

}
