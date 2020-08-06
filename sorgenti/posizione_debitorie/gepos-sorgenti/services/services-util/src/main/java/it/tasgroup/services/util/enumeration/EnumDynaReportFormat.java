package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumDynaReportFormat implements MessageDescription{

	CSV_STANDARD 			("CSV_STD", "std", "CSV Standard", "export.csv.standard",1000),
	CSV_STANDARD3 			("CSV_STD3", "std3", "CSV Standard3", "export.csv.standard3", 1),
	CSV_CUSTOM 				("CSV_CUSTOM", "csv",  "CSV Custom", "export.csv.custom",1),
	PDF						("PDF", "pdf", "PDF", "export.pdf",1),
	PDF_ZIP					("PDF_ZIP", "pdf", "PDF", "export.pdf",1),
	XML					    ("XML", "xml", "XML", "export.xml",1);
	
	private String chiave;
	private String sigla;
	private String descrizione;
	private String chiaveBundle;
	private Integer amountMultiplier;


	private EnumDynaReportFormat(String chiave, String sigla, String descrizione, String chiaveBundle, Integer amountMultiplier) {
		
		this.chiave = chiave;
		
		this.sigla = sigla;
		
		this.descrizione = descrizione;
		
		this.chiaveBundle = chiaveBundle;
		
		this.amountMultiplier = amountMultiplier;

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
	
}
