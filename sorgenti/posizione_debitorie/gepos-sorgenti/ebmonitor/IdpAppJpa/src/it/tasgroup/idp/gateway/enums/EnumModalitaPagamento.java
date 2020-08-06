package it.tasgroup.idp.gateway.enums;

public enum EnumModalitaPagamento implements KeyEnums {
	
	MYBANK_NDP(23L), 
	ADDEBITO_DIRETTO(3L), 
	ATTIVATO_PRESSO_PSP(16L), 
	BOLLETTINO_POSTALE(15L), 
	BONIFICO_BANCARIO_TESORERIA(14L), 
	CARTA_PAGAMENTO(4L),
	BANCOMAT_PAY(101L);

	private Long key;

	EnumModalitaPagamento(Long key) {
		this.key = key;
	}

	@Override
	public Long getKey() {
		return this.key;
	}

}
