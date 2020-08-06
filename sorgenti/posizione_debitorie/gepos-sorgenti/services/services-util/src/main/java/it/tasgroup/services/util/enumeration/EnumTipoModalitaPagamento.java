package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoModalitaPagamento implements MessageDescription{
	
	DELEGARID("1","DELEGA RID","DLGRID"),
	REVOCARID("2","REVOCA RID","RVCRID"),
	RIDONLINE("3","RID ON-LINE","RIDOLN"),	
	CARTADICREDITO("4","CARTA DI CREDITO","CRCARD"),
	BONIFICOONLINE("5","BONIFICO ON-LINE BT","BNOBT"),
	DDPATMBT("6","PAGAMENTO DA ATM GRUPPO BT","ATMBT"),
	BONIFICOHBBT("7","BONIFICO DA HOME BANKING BT","HBKBT"),	
	BONIFICOCODPRED("8","BONIFICO CON CODICE  PREDEFINITO","BNPPRD"),
	BOLLETTINOFRECCIA("9","BOLLETTINO FRECCIA","BLFRCC"),
	EUPAY("10","PAGAMENTO SUL CIRCUITO EU-PAY","CEUPAY"),
	TSERVE("11","PAGAMENTO SUL CIRCUITO T-SERVE","CTSERV"),
	CBILL("12","PAGAMENTO DA CIRCUITO CBILL","CBILL"),
	MYBANK("13","MYBANK","MYBANK"),

	BBT("14","BONIFICO BANCARIO DI TESORERIA","BBT"),
	BPT("15","BOLLETTINO POSTALE","BPT"),
	PSP("16","PAGAMENTO PRESSO PSP PAGOPA","PSP"),
    TTMSST("17","PAGAMENTO DA CIRCUITO PUNTOSI","TTMSST"),
    FESPPI("18","BOLLETTINO POSTALE ON LINE","FESPPI"),
	PSTSPA("19","SPORTELLO AMICO","PSTSPA"),
	AD("20","ADDEBITO DIRETTO","AD"),
	CP("21","CARTA DI PAGAMENTO","CP"),
	TTMPGI("22","PAGAMENTO DA PUNTI GIALLI","TTMPGI"),
	OBEP("23","BONIFICO MYBANK VIA NODO DEI PAGAMENTI","OBEP"),
	IBK("24","Internet Banking",	"IBK"),
	PAGTEL("97","Pagamento telematico generico","PAGTEL"),
	TOTEM("98","Pagamento da totem riscuotitore generico",	"TOTEM"),
	UFFPST("99","UFFICIO POSTALE","UFFPST"),
	PAGO_PA("100","PAGAMENTO ONLINE PAGO PA","PAGOPA");


	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoModalitaPagamento(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}
	
	public static EnumTipoModalitaPagamento getByChiave(String chiave) {
		             
		for (EnumTipoModalitaPagamento item : EnumTipoModalitaPagamento.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				return item;                     
				               
				}             
			} 
		
		return null;
	}
	
    public static EnumTipoModalitaPagamento getByChiaveBundle(String chiaveBundle) {
        
		for (EnumTipoModalitaPagamento item : EnumTipoModalitaPagamento.values()) {                 
			if (item.getChiaveBundle().equals(chiaveBundle)) {                     
				return item;                     
				               
				}             
			} 
		
		return null;
	}
    
	public static EnumTipoModalitaPagamento getByDescrizione(String descrizione) {
        
		for (EnumTipoModalitaPagamento item : EnumTipoModalitaPagamento.values()) {                 
			if (item.getDescrizione().equalsIgnoreCase(descrizione)) {                     
				return item;                     
				               
				}             
			} 
		
		return null;
	}


}
