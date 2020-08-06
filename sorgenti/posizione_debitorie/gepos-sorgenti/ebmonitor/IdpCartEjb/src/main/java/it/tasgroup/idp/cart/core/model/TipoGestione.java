package it.tasgroup.idp.cart.core.model;

public enum TipoGestione {

	INBOUND("inbound"), OUTBOUND ("outbound");

	private final String value;

	TipoGestione(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static TipoGestione fromValue(String v) {
		for (TipoGestione c: TipoGestione.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
