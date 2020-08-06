package it.tasgroup.idp.cart.core.model;

public enum TipoMessaggio {

	ALLINEAMENTO_PENDENZE("ap"), INFORMATIVA_PAGAMENTO("ip");

	private final String value;

	TipoMessaggio(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static TipoMessaggio fromValue(String v) {
		for (TipoMessaggio c: TipoMessaggio.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
