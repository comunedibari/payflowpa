package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumExportSTDFormat implements MessageDescription{

	CSV_BASIC_1 			("CSV_BASIC_1", "std", "CSV Standard", "export.csv.standard",1000,"################"),
	CSV_BASIC_3 			("CSV_BASIC_3", "std3", "CSV Standard3", "export.csv.standard3",1, "##,##0.00");
	
	
	private String chiave;
	private String sigla;
	private String descrizione;
	private String chiaveBundle;
	private Integer amountMultiplier;
	private String amountPattern;
	private String datePattern;
	private String timestampPattern;


	private EnumExportSTDFormat(String chiave, String sigla, String descrizione, String chiaveBundle, Integer amountMultiplier, String amountPattern) {
		
		this.chiave = chiave;
		
		this.sigla = sigla;
		
		this.descrizione = descrizione;
		
		this.chiaveBundle = chiaveBundle;
		
		this.amountMultiplier = amountMultiplier;
		
		this.amountPattern = amountPattern;

	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getAmountMultiplier() {
		return amountMultiplier;
	}

	public String getAmountPattern() {
		return amountPattern;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public String getTimestampPattern() {
		return timestampPattern;
	}
	
}
