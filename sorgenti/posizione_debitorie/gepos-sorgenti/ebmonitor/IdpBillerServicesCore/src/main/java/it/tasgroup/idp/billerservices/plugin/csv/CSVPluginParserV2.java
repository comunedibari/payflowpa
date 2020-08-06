package it.tasgroup.idp.billerservices.plugin.csv;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.EsitoCaricamentoPendenza;
import it.tasgroup.idp.util.IrisProperties;

public class CSVPluginParserV2 extends CSVPluginParser {

	public CSVPluginParserV2(CSVBasicConfig config) {
		super(config);
	}

	@Override
	public int getRecordLength() {
		return 22;
	}

	@Override
	public CSVRowPagamento unmarshall(String pagamento, long posizione) throws ValidationException {

		if(pagamento == null) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"La stringa da leggere non puo' essere nulla");
		}
		
		List<String[]> myEntries = null;
		
		try{
			CSVReader reader = new CSVReader(new StringReader(pagamento), getFieldSeparator(), getQuote());
			myEntries = reader.readAll();
			reader.close();
		} catch (Exception e) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Errore durante il parsing.");
		}
		
		String[] fields = myEntries.get(0);
		nullifyEmpties(fields);

		if(fields.length != getRecordLength()) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) +"] non e' nel formato corretto: attesi ["+ getRecordLength() +"] campi separati da ["+getFieldSeparator()+"], trovati ["+fields.length+"]",posizione);
		}

		CSVRowPagamento pagamentoDTO = new CSVRowPagamento();
		pagamentoDTO.setPosizioneRaw(posizione + 1);
		pagamentoDTO.setCreditore(readString(posizione, "CREDITORE", fields[0], regexpString35, false));
		pagamentoDTO.setTipoDebito(readString(posizione, "TIPO_DEBITO", fields[1], 1, 35, false));
		pagamentoDTO.setDebitori(readDebitori(posizione, "DEBITORI", fields[2]));
		pagamentoDTO.setIdDebito(readString(posizione, "ID_DEBITO", fields[3], regexpString35, false));
		pagamentoDTO.setIdCondizione(readString(posizione, "ID_PAGAMENTO", fields[4], regexpString35, false));
		pagamentoDTO.setDataScadenza(readData(posizione, "DATA_SCADENZA", fields[5], false));
		pagamentoDTO.setDataInizioValidita(readData(posizione, "DATA_INIZIO_VALIDITA", fields[6], false));
		pagamentoDTO.setDataFineValidita(readData(posizione, "DATA_FINE_VALIDITA", fields[7], false));
		pagamentoDTO.setCausalePagamento(readString(posizione, "CAUSALE_PAGAMENTO", fields[8], 1, 256, true));

		try { 
			pagamentoDTO.setStato(CSVRowPagamento.Stato.valueOf(fields[9]));
		} catch (Exception iae) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione  ) + "] viola la sintassi per il campo STATO_PAGAMENTO. Valore ["+fields[9]+"] non compreso tra quelli consentiti",posizione);
		}
		pagamentoDTO.setImportoPagamento(readImporto(posizione, "IMPORTO_PAGAMENTO", fields[10], 1, 13, false));
		pagamentoDTO.setVoci(readVoci(posizione, "VOCI_PAGAMENTO", fields[11]));
		pagamentoDTO.setAnnoRiferimento(readInteger(posizione, "ANNO_RIFERIMENTO", fields[12], 4, 4, false));
		pagamentoDTO.setNoteDebito(readString(posizione, "NOTE_DEBITO", fields[13], 1, 2000, true));
		pagamentoDTO.setCausaleDebito(readString(posizione, "CAUSALE_DEBITO", fields[14], 1, 256, false));
		pagamentoDTO.setIbanRiaccredito(readString(posizione, "IBAN_RIACCREDITO", fields[15], 1, 27, true)); // su jltcopd.iban_beneficiario 27, nel tracciato 30 e secondo l'iban 34: mah...
		pagamentoDTO.setTotalePagato(readImporto(posizione, "IMPORTO_PAGATO", fields[16], 1, 13, true));
		pagamentoDTO.setDataValutaAccredito(readData(posizione, "DATA_VALUTA_ACCREDITO", fields[17], true));
		pagamentoDTO.setCanalePagamento(readString(posizione, "CANALE_PAGAMENTO", fields[18], 1, 35, true));
		pagamentoDTO.setDataPagamento(readData(posizione, "DATA_PAGAMENTO", fields[19], true));
		pagamentoDTO.setNotePagamento(readString(posizione, "NOTE_PAGAMENTO", fields[20], 1, 256, true));
		pagamentoDTO.setPagatoIDP(readBoolean(posizione, "PAGATO_IDP", fields[21], true));
		
		
		// SE stato=PAGATO allora IMPORTO_PAGATO deve essere uguale ad IMPORTO_PAGAMENTO.
		if(pagamentoDTO.getStato() != null && pagamentoDTO.getStato().equals(CSVRowPagamento.Stato.PAGATO) && pagamentoDTO.getTotalePagato()!=null && !pagamentoDTO.getImportoPagamento().equals(pagamentoDTO.getTotalePagato())) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) + "] in stato PAGATO presenta un valore in IMPORTO_PAGATO [" + String.format(DOUBLEFORMAT, (pagamentoDTO.getTotalePagato())) + "] diverso da IMPORTO_PAGAMENTO [" + String.format(DOUBLEFORMAT, (pagamentoDTO.getImportoPagamento())) + "].", posizione);
		}
		
		// DATA_INIZIO_VALIDITA <= DATA_SCADENZA <= DATA_FINE_VALIDITA
		if(pagamentoDTO.getDataInizioValidita() != null && pagamentoDTO.getDataInizioValidita().compareTo(pagamentoDTO.getDataScadenza()) > 0) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) + "] presenta una DATA_INIZIO_VALIDITA [" + hourFormat.format(pagamentoDTO.getDataInizioValidita()) + "] successiva alla DATA_SCADENZA [" + (pagamentoDTO.getDataScadenza() != null ? hourFormat.format(pagamentoDTO.getDataScadenza()) : "") + "].", posizione);
		}
		
		if(pagamentoDTO.getDataScadenza() != null && pagamentoDTO.getDataScadenza().compareTo(pagamentoDTO.getDataFineValidita()) > 0) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione  ) + "] presenta una DATA_SCADENZA [" + hourFormat.format(pagamentoDTO.getDataScadenza()) + "] successiva alla DATA_FINE_VALIDITA [" + (pagamentoDTO.getDataFineValidita() != null ? hourFormat.format(pagamentoDTO.getDataFineValidita()) : "") + "].", posizione);
		}

		return pagamentoDTO;
	}

	@Override
	public String marshallEsitoValidazione(List<ValidationException> errors) {
		StringWriter stringWriter = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(stringWriter, getFieldSeparator(), CSVWriter.NO_ESCAPE_CHARACTER);
		String[] headerEsito = new String[] { "ESITO", "ID_DEBITO", "CODICE_ERRORE", "DESCRIZIONE_ERRORE" };
		csvWriter.writeNext(headerEsito);

		if (errors != null && errors.size() > 0) {
			for (ValidationException e : errors) {
				String[] row = new String[] { 
						"KO", 													// ESITO
						e.getIdDebito() != null ? e.getIdDebito() : "", 		// ID_DEBITO
						e.getErrorCode().toString(), 							// CODICE_ERRORE
						e.getDescription() != null ? e.getDescription() : "" 	// DESCRIZIONE_ERRORE
				};
				csvWriter.writeNext(row);
			};
		} else {
			// Marshall riga con esito OK (che vale per tutto il flusso)
			String[] row = new String[] { 
					"OK", 								// ESITO
					"", 								// ID_DEBITO
					EnumReturnValues.OK.toString(), 	// CODICE_ERRORE
					"Elaborato correttamente" 			// DESCRIZIONE_ERRORE
			};
			csvWriter.writeNext(row);
		}

		return stringWriter.toString();
	}

	public void marshallEsitoCaricamento(List<EsitoCaricamentoPendenza> errors, OutputStream outStream) {

		// TODO ctrl  smartproxy@CSVBasicPlugin.serializzaEsito()
		
		boolean includeIUV = IrisProperties.getProperty("includes.iuv.esiti", "false").equals("true");
		PrintWriter stringWriter = new PrintWriter(outStream);
		CSVWriter csvWriter = new CSVWriter(stringWriter, getFieldSeparator(), CSVWriter.NO_ESCAPE_CHARACTER);
		String[] headerEsito = null;
		headerEsito = new String[] {"ESITO", "ID_DEBITO", "CODICE_ERRORE", "DESCRIZIONE_ERRORE"};
		csvWriter.writeNext(headerEsito);

		boolean hasKO = false;
		if (errors != null && errors.size() > 0) {
			for (EsitoCaricamentoPendenza e : errors) {
				if (e.statoEsito.equals("KO")) { // evitiamo gli OK ma anche i WARN (SKIPPED)
					hasKO = true;
					String[] row = null;
					row = new String[] { 
							e.statoEsito, 											// ESITO
							e.idDebito != null ? e.idDebito : "", 					// ID_DEBITO
							e.codiceErrore != null ? e.codiceErrore : "", 			// CODICE_ERRORE
							e.descrizioneEsito != null ? e.descrizioneEsito : "" 	// DESCRIZIONE_ERRORE
					};
					csvWriter.writeNext(row);
				}
			}
		}
		if (errors == null || errors.size() == 0 || !hasKO) {
			String[] row = null;
			row = new String[] { 
					"OK", 								// ESITO
					"", 								// ID_DEBITO
					EnumReturnValues.OK.toString(), 	// CODICE_ERRORE
					"Elaborato correttamente" 			// DESCRIZIONE_ERRORE
			};
			csvWriter.writeNext(row);
		}

		stringWriter.flush();

	}
        
	protected Double readImporto(Long posizione, String nomeField, String field, int minDigits, int maxDigits, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione, nomeField, field);
		}
		field = readString(posizione, nomeField, field, minDigits, maxDigits, nullable);
		if(field == null || "".equals(field)) return null;
		BigDecimal importo = null;
		try {
			importo = new BigDecimal(field.trim()).movePointLeft(3);
		} catch (Exception e) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE, "Il pagamento in posizione [" + (posizione) + "] ha una valorizzazione invalida per il campo " + nomeField + ".", posizione);
		}
		return importo.doubleValue();
	}
	
}
