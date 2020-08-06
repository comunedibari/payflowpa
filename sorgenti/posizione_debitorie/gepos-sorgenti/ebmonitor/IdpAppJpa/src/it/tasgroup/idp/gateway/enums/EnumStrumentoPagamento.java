package it.tasgroup.idp.gateway.enums;

public enum EnumStrumentoPagamento implements KeyEnums {

	ADDEBITO_DIRETTO(3L), 
	BONIFICO(2L), 
	CARTA_PAGAMENTO(6L), 
	DOCUMENTO_PAGAMENTO(5L);

	private Long key;

	EnumStrumentoPagamento(Long key) {
		this.key = key;
	}

	@Override
	public Long getKey() {
		return this.key;
	}

}
