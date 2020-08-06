package it.tasgroup.idp.gateway.enums;

public enum EnumDocumentoPagamento implements KeyEnums {
	
	BOLLETTINO_BONIFICO(1L, "BLBON"),
	BOLLETINO_ATM(2L, "BLATM"),
	BOLLETTINO_FRECCIA(3L, "BLFRC"),
	BOLLETINO_EUPAY(4L, "BLEUP"),
	BOLLETTINO_NDP(5L, "BLNDP"),
	BOLLETTINO_ASL(6L, "BLASL");

	private Long key;
	private String bundleKey;

	EnumDocumentoPagamento(Long key, String bundleKey) {
		this.key = key;
		this.bundleKey = bundleKey;
	}

	@Override
	public Long getKey() {
		return this.key;
	}

	public String getBundleKey() {
		return this.bundleKey;
	}

}
