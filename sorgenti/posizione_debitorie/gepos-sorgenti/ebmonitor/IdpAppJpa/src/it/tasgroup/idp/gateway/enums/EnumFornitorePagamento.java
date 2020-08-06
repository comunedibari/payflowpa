package it.tasgroup.idp.gateway.enums;

public enum EnumFornitorePagamento implements KeyEnums {
	
	NODO_PAGAMENTI_SPC(4L);

	private Long key;

	EnumFornitorePagamento(Long key) {
		this.key = key;
	}

	@Override
	public Long getKey() {
		return this.key;
	}

}
