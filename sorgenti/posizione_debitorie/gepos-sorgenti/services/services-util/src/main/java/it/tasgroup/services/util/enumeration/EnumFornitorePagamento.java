package it.tasgroup.services.util.enumeration;

public enum EnumFornitorePagamento {

	BT(1), PRATO(2), CBILL(3), NDPSPC(4), RT(5), POSTE(7);

	private int id;

	private EnumFornitorePagamento(int id) {
		this.id = id;
	}

	public static EnumFornitorePagamento getById(int id) {
		EnumFornitorePagamento ret = null; // Default
		for (EnumFornitorePagamento item : EnumFornitorePagamento.values()) {
			if (item.getId() == id) {
				ret = item;
				break;
			}
		}
		return ret;
	}

	public int getId() {
		return id;
	}

}
