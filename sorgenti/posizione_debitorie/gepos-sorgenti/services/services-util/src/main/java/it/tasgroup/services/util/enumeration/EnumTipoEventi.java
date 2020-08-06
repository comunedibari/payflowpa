package it.tasgroup.services.util.enumeration;

public enum EnumTipoEventi {
	
	INVIOQUIETANZA("01"), 
	AVVISOSCADENZAPENDENZA("02"), 
	AVVISORIACCREDITOENTE("03"), 
	AVVISORICEZIONERIMBORSO("04"), 
	SEGNALAZIONERIACCREDITOFALLITO("05"),
	SEGNALAZIONEPAGAMENTOSPONTANEO("06"),
	AVVISO_RICEZIONE_RT_NDP("07"),
	AVVISATURA_PUSH_INSERT("101"),
	AVVISATURA_PUSH_DELETE("102"),
	AVVISATURA_PUSH_UPDATE("103"),
	AVVISO_DI_PAGAMENTO_ESEGUITO("110");

	private String eventoCode;

	private EnumTipoEventi(String s) {
		eventoCode = s;
	}

	public String getEventoCode() {
		return eventoCode;
	}

	public int getEventoCodeInt() {
		return new Integer(eventoCode).intValue();
	}
}
