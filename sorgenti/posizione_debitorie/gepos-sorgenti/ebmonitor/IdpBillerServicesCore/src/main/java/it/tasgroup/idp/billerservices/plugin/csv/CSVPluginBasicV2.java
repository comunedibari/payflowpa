package it.tasgroup.idp.billerservices.plugin.csv;

import java.io.InputStream;

public class CSVPluginBasicV2 extends CSVPlugin {

	public CSVPluginBasicV2(InputStream rawdata) {
		super(rawdata);
	}

	@Override
	public String getTipoFile() {
		return "CSV_BASIC_V2";
	}

	// TODO cfr smartproxy@it.toscana.rete.cart.servizi.iris.smartproxy.plugin.csv.basic_v1.CSVBasicInput.getPreambolo()
	@Override
	protected String getPreambolo() {
		return "CREDITORE" + config.getFieldSeparator()
				+ "TIPO_DEBITO" + config.getFieldSeparator()
				+ "DEBITORI" + config.getFieldSeparator()
				+ "ID_DEBITO" + config.getFieldSeparator()
				+ "ID_PAGAMENTO" + config.getFieldSeparator()
				+ "DATA_SCADENZA" + config.getFieldSeparator()
				+ "DATA_INIZIO_VALIDITA" + config.getFieldSeparator()
				+ "DATA_FINE_VALIDITA" + config.getFieldSeparator()
				+ "CAUSALE_PAGAMENTO" + config.getFieldSeparator()
				+ "STATO_PAGAMENTO" + config.getFieldSeparator()
				+ "IMPORTO_PAGAMENTO" + config.getFieldSeparator()
				+ "VOCI_PAGAMENTO" + config.getFieldSeparator()
				+ "ANNO_RIFERIMENTO" + config.getFieldSeparator()
				+ "NOTE_DEBITO" + config.getFieldSeparator()
				+ "CAUSALE_DEBITO" + config.getFieldSeparator()
				+ "IBAN_RIACCREDITO" + config.getFieldSeparator()
				+ "IMPORTO_PAGATO" + config.getFieldSeparator()
				+ "DATA_VALUTA_ACCREDITO" + config.getFieldSeparator()
				+ "CANALE_PAGAMENTO" + config.getFieldSeparator()
				+ "DATA_PAGAMENTO" + config.getFieldSeparator()
				+ "NOTE_PAGAMENTO" + config.getFieldSeparator()
				+ "PAGATO_IDP";
	}

	@Override
	protected CSVBasicConfig getConfig() {
		return new CSVBasicConfig('|', '"', ';');
	}
	
	@Override
	protected CSVPluginParser getParser(CSVBasicConfig config) {
		return new CSVPluginParserV2(config);
	}
	
}
