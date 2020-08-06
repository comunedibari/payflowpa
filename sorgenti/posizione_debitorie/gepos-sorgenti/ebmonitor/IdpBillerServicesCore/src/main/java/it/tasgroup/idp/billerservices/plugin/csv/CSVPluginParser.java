package it.tasgroup.idp.billerservices.plugin.csv;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.EsitoCaricamentoPendenza;
import it.tasgroup.idp.util.IrisProperties;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVPluginParser {
	
	private static final String dateFormatString = "yyyy-MM-dd";
	private static final String hourFormatString = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String dateFormatString2 = "dd/MM/yyyy";
	private static final String hourFormatString2 = "dd/MM/yyyy HH:mm:ss";
	
	protected static final String regexpString35 = "[a-zA-Z0-9._]{1,35}"; 
	protected static final String DOUBLEFORMAT = "%.3f%n";
	private SimpleDateFormat dateFormat, dateFormat2;
	protected SimpleDateFormat hourFormat;
	private SimpleDateFormat hourFormat2;
	private CSVBasicConfig config;

	static class BasicRowInfo {
		String creditore;
		String tipoDebito;
		String idDebito;
	}
	
	public CSVPluginParser(CSVBasicConfig config) {
		dateFormat = new SimpleDateFormat(dateFormatString);
		dateFormat.setLenient(false);

		hourFormat = new SimpleDateFormat(hourFormatString);
		hourFormat.setLenient(false);
		
		dateFormat2 = new SimpleDateFormat(dateFormatString2);
		dateFormat2.setLenient(false);

		hourFormat2 = new SimpleDateFormat(hourFormatString2);
		hourFormat2.setLenient(false);

		this.config = config;
		
	}
	
	public char getFieldSeparator(){
		return config.getFieldSeparator();
	}

	public char getQuote(){
		return config.getQuote();
	}

	public String getComplexFieldSeparator(boolean escape){
		if(escape) 
			return Pattern.quote(config.getComplexFieldSeparator() + "");
		else 
			return config.getComplexFieldSeparator() + "";
	}

	public int getRecordLength() {
		return 20;
	}

	/**
	 * Estrae dalla riga solo le info base. Si utilizza in fase di piazzatura solo 
	 * per controllare correttezza formale (parsabilit�) del flusso e omogeneit� rispetto a questi dati,
	 * rimandando all'elaborazione successiva la validazione completa del file. 
	 * 
	 * @param pagamento
	 * @return
	 * @throws ValidationException 
	 */
	public BasicRowInfo getBasicRowInfo(String pagamento, int posizione) throws ValidationException {
		BasicRowInfo result= new BasicRowInfo();
		List<String[]> myEntries = null;
		try{
			CSVReader reader = new CSVReader(new StringReader(pagamento), getFieldSeparator(), getQuote());
			myEntries = reader.readAll();
			reader.close();
		} catch (Exception e) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Errore durante il parsing.");
		}
		
		if (myEntries==null || myEntries.isEmpty()){
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Errore durante il parsing.");
		}
		
		if (myEntries.get(0).length != getRecordLength()) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Errore durante il parsing, la riga nr: "+ posizione+" ha "+myEntries.get(0).length+" campi. Attesi "+ getRecordLength());
		}
		
		result.creditore=myEntries.get(0)[0].trim();
		result.tipoDebito=myEntries.get(0)[1].trim();
		result.idDebito=myEntries.get(0)[3].trim();

		return result;
	}
	
	
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
		pagamentoDTO.setPosizioneRaw(posizione +1);
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
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione  ) +"] viola la sintassi per il campo STATO_PAGAMENTO. Valore ["+fields[9]+"] non compreso tra quelli consentiti",posizione);
		}
		pagamentoDTO.setImportoPagamento(readImporto(posizione, "IMPORTO_PAGAMENTO", fields[10], 1, 13, false));
		pagamentoDTO.setVoci(readVoci(posizione, "VOCI_PAGAMENTO", fields[11]));
		pagamentoDTO.setAnnoRiferimento(readInteger(posizione, "ANNO_RIFERIMENTO", fields[12], 4, 4, false));
		pagamentoDTO.setNoteDebito(readString(posizione, "NOTE_DEBITO", fields[13], 1, 2000, true));
		pagamentoDTO.setCausaleDebito(readString(posizione, "CAUSALE_DEBITO", fields[14], 1, 256, false));

		pagamentoDTO.setTotalePagato(readImporto(posizione, "IMPORTO_PAGATO", fields[15], 1, 13, true));
		pagamentoDTO.setCanalePagamento(readString(posizione, "CANALE_PAGAMENTO", fields[16], 1, 35, true));
		pagamentoDTO.setDataPagamento(readData(posizione, "DATA_PAGAMENTO", fields[17], true));
		pagamentoDTO.setNotePagamento(readString(posizione, "NOTE_PAGAMENTO", fields[18], 1, 256, true));
		pagamentoDTO.setPagatoIDP(readBoolean(posizione, "PAGATO_IDP", fields[19], true));
		
		
		// SE stato=PAGATO allora IMPORTO_PAGATO deve essere uguale ad IMPORTO_PAGAMENTO.
		if(pagamentoDTO.getStato() != null && pagamentoDTO.getStato().equals(CSVRowPagamento.Stato.PAGATO) && pagamentoDTO.getTotalePagato()!=null && !pagamentoDTO.getImportoPagamento().equals(pagamentoDTO.getTotalePagato())) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) +"] in stato PAGATO presenta un valore in IMPORTO_PAGATO [" + String.format(DOUBLEFORMAT, (pagamentoDTO.getTotalePagato())) + "] diverso da IMPORTO_PAGAMENTO [" + String.format(DOUBLEFORMAT, (pagamentoDTO.getImportoPagamento())) + "].",posizione);
		}
		
		// DATA_INIZIO_VALIDITA <= DATA_SCADENZA <= DATA_FINE_VALIDITA
		if(pagamentoDTO.getDataInizioValidita() != null && pagamentoDTO.getDataInizioValidita().compareTo(pagamentoDTO.getDataScadenza()) > 0) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) +"] presenta una DATA_INIZIO_VALIDITA [" + hourFormat.format(pagamentoDTO.getDataInizioValidita()) + "] successiva alla DATA_SCADENZA [" + (pagamentoDTO.getDataScadenza() != null ? hourFormat.format(pagamentoDTO.getDataScadenza()) : "") + "].",posizione);
		}
		
		if(pagamentoDTO.getDataScadenza() != null && pagamentoDTO.getDataScadenza().compareTo(pagamentoDTO.getDataFineValidita()) > 0) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione  ) +"] presenta una DATA_SCADENZA [" + hourFormat.format(pagamentoDTO.getDataScadenza()) + "] successiva alla DATA_FINE_VALIDITA [" + (pagamentoDTO.getDataFineValidita() != null ? hourFormat.format(pagamentoDTO.getDataFineValidita()) : "") + "].",posizione);
		}

		return pagamentoDTO;
	}

	
	public String marshallEsitoValidazione (List<ValidationException> errors ) {
		StringWriter stringWriter = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(stringWriter,getFieldSeparator(),CSVWriter.NO_ESCAPE_CHARACTER);
		String[] headerEsito = new String [] {
			"PROGRESSIVO",
			"ESITO",
			"ID_DEBITO",
			"ID_PAGAMENTO",
			"CODICE_ERRORE",
			"DESCRIZIONE_ERRORE"
		};
        csvWriter.writeNext(headerEsito);		

        if (errors != null && errors.size()>0) {
        	// Marshalling degli errori
	        for (ValidationException e:errors) {
				String[] row = new String [] {
						""+e.getProgressivo(), 							//"PROGRESSIVO",
						"KO", 											//"ESITO",
						e.getIdDebito()!=null?e.getIdDebito():"",  		//"ID_DEBITO",
						e.getIdPagamento()!=null?e.getIdPagamento():"",	//"ID_PAGAMENTO",
						e.getErrorCode().toString(),	                //"CODICE_ERRORE",
						e.getDescription()!=null?e.getDescription():"" 	//"DESCRIZIONE_ERRORE"
				};
				csvWriter.writeNext(row);
			};	
        } else {
        	// Marshall riga con esito OK (che  vale per tutto  il flusso)
				String[] row = new String [] {
					"", 							 //"PROGRESSIVO",
					"OK", 							 //"ESITO",
					"",  							 //"ID_DEBITO",
					"",							     //"ID_PAGAMENTO",
					EnumReturnValues.OK.toString(),  //"CODICE_ERRORE",
					"Elaborato correttamente" 	 //"DESCRIZIONE_ERRORE"
				};
				csvWriter.writeNext(row);
		}
        
        return stringWriter.toString();
	}

	
	public void marshallEsitoCaricamento (List<EsitoCaricamentoPendenza> errors, OutputStream outStream ) {
		
		boolean includeIUV = IrisProperties.getProperty("includes.iuv.esiti","false").equals("true");
		PrintWriter stringWriter = new PrintWriter(outStream);
		CSVWriter csvWriter = new CSVWriter(stringWriter,getFieldSeparator(),CSVWriter.NO_ESCAPE_CHARACTER);
		String[] headerEsito = null;
		if (!includeIUV) {
		    headerEsito = new String [] {"PROGRESSIVO","ESITO","ID_DEBITO","ID_PAGAMENTO","CODICE_ERRORE","DESCRIZIONE_ERRORE"};
		} else {
			 headerEsito = new String [] {"PROGRESSIVO","ESITO","ID_DEBITO","ID_PAGAMENTO","CODICE_ERRORE","DESCRIZIONE_ERRORE","IUV"};
		}
        csvWriter.writeNext(headerEsito);		

        if (errors != null && errors.size()>0) {
        	// Marshalling degli errori
	        for (EsitoCaricamentoPendenza e:errors) {
				String[] row = null;
				if (!includeIUV) {
				    row = new String [] {
						     ""+e.progressivo, 							    //"PROGRESSIVO",
						     e.statoEsito, 									//"ESITO",
						     e.idDebito!=null?e.idDebito:"",  				//"ID_DEBITO",
						     e.idPagamento!=null?e.idPagamento:"",			//"ID_PAGAMENTO",
						     e.codiceErrore!=null?e.codiceErrore:"",	    //"CODICE_ERRORE",
						     e.descrizioneEsito!=null?e.descrizioneEsito:""	//"DESCRIZIONE_ERRORE"
				         };
				} else {
					row = new String [] {
						     ""+e.progressivo, 							    //"PROGRESSIVO",
						     e.statoEsito, 									//"ESITO",
						     e.idDebito!=null?e.idDebito:"",  				//"ID_DEBITO",
						     e.idPagamento!=null?e.idPagamento:"",			//"ID_PAGAMENTO",
						     e.codiceErrore!=null?e.codiceErrore:"",	    //"CODICE_ERRORE",
						     e.descrizioneEsito!=null?e.descrizioneEsito:"",//"DESCRIZIONE_ERRORE",
						     e.iuv!=null?e.iuv:""                           //"IUV"
				         };
				}
				csvWriter.writeNext(row);
			};	
        } else {
        	// Marshall riga con esito OK (che  vale per tutto  il flusso)
				String[] row = null;
				if (!includeIUV) {
				        row = new String [] {
					                 "", 							 //"PROGRESSIVO",
					                 "OK", 							 //"ESITO",
					                 "",  							 //"ID_DEBITO",
					                 "",							     //"ID_PAGAMENTO",
					                 EnumReturnValues.OK.toString(),  //"CODICE_ERRORE",
					                 "Elaborato correttamente" 	 //"DESCRIZIONE_ERRORE"
				                   };
				} else {
					 row = new String [] {
			                 "", 							 //"PROGRESSIVO",
			                 "OK", 							 //"ESITO",
			                 "",  							 //"ID_DEBITO",
			                 "",							     //"ID_PAGAMENTO",
			                 EnumReturnValues.OK.toString(),  //"CODICE_ERRORE",
			                 "Elaborato correttamente", 	 //"DESCRIZIONE_ERRORE"
			                 ""
		                   };
				}
				
				csvWriter.writeNext(row);
		}
        
        stringWriter.flush();
        
        
	}

	
	
	protected void nullifyEmpties(String[] lst) {
		for (int i = 0; i < lst.length; i++) {
		
			if(lst[i] != null) {
				if(lst[i].equals("")) {
					lst[i] = null;
				}
			}
		}
	}

	protected Date readData(Long posizione, String nomeField, String field, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione,nomeField, field);
		} else {
			if(field == null || field.length() ==0) return null;
		}
		
		Date date = null;
		Date date1970 = new Date(0);
		try {
			date =  hourFormat.parse(field);
			if(date.after(date1970))
				return date;
			else
				throw new ParseException("",0);
		} catch (ParseException e) {
			try {
				date = hourFormat.parse(field + "T00:00:00");
				if(date.after(date1970))
					return date;
				else
					throw new ParseException("",0);
			} catch (ParseException e1) {
				try {
					date =  hourFormat2.parse(field);
					if(date.after(date1970))
						return date;
					else
						throw new ParseException("",0);
				} catch (ParseException e2) {
					try {
						date =  hourFormat2.parse(field + " 00:00:00");
						if(date.after(date1970))
							return date;
						else
							throw new ParseException("",0);
					} catch (ParseException e3) {
						throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione  ) +"] viola la sintassi per il campo "+nomeField+". Valore ["+field+"] non rispetta la sintassi ["+hourFormat.toPattern()+"] o ["+dateFormat.toPattern()+"] o ["+hourFormat2.toPattern()+"] o ["+dateFormat2.toPattern()+"]",posizione);
					}
				}
			}
		}
	}

	protected Boolean readBoolean(Long posizione, String nomeField, String field, boolean nillable) throws ValidationException {
		if(nillable && (field == null || field.equals(""))) return null;
		else {
			checkNotNull(posizione,nomeField, field);
			if(field.equalsIgnoreCase("Si")) return true;
			if(field.equalsIgnoreCase("No")) return false;
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) +"] viola la sintassi per il campo "+nomeField+". Valore ["+field+"] non rispetta la sintassi [Si|No]",posizione);
		}
	}

	protected List<CSVRowPagamento.Voce> readVoci(Long posizione, String nomeField, String field) throws ValidationException {
		if(field == null || field.equals("")) return null;
		String[] voci = field.split(getComplexFieldSeparator(true));
		List<CSVRowPagamento.Voce> lst = new ArrayList<CSVRowPagamento.Voce>();
		for(String voceString: voci) {
			CSVRowPagamento.Voce voce = new CSVRowPagamento.Voce();

			String[] voceFields = voceString.split("=");
			if(voceFields.length != 2) {
				throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) +"] viola la sintassi per il campo "+nomeField+". Valore ["+field+"] non rispetta la sintassi [nome=valore;nome=valore;....]",posizione);
			}

			voce.setIdVoce(readString(posizione, "codiceVoce",voceFields[0], 1, 35, false));
			voce.setImporto(readImporto(posizione, "importoVoce", voceFields[1], 1, 13, false));

			lst.add(voce);
		} 
		return lst;

	}
	
	protected List<CSVRowPagamento.Debitore> readDebitori(Long posizione, String nomeField, String field) throws ValidationException {
		if(field == null || field.equals("")) return null;
		String[] debitori = field.split(getComplexFieldSeparator(true));
		List<CSVRowPagamento.Debitore> lst = new ArrayList<CSVRowPagamento.Debitore>();
		for(String debitoreString: debitori) {
			CSVRowPagamento.Debitore debitore = new CSVRowPagamento.Debitore();

			String[] debitoriFields = debitoreString.split("=");
			String cf_piva = debitoriFields[0];
			if (!isCF(cf_piva) && !isPIVA(cf_piva)) {
				throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE, "Il pagamento in posizione [" + (posizione) + "] viola la sintassi per il campo " + nomeField + ". Valore [" + cf_piva + "] non valido come Codice Fiscale o Partita IVA", posizione);
			}
			debitore.setCodiceFiscale(cf_piva);
			
			if(debitoriFields.length == 2) {
				debitore.setAnagrafica(debitoriFields[1]);
			}
			
			if(debitoriFields.length > 2) {
				throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione ) +"] viola la sintassi per il campo "+nomeField+".",posizione);
			}

			lst.add(debitore);
		} 
		return lst;

	}


	protected Double readImporto(Long posizione, String nomeField, String field, int minDigits, int maxDigits, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione, nomeField, field);
		}
		return readDouble(posizione, nomeField, field, minDigits, maxDigits, nullable);
	}

	protected Integer readInteger(Long posizione, String nomeField, String field, int minDigits, int maxDigits, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione, nomeField, field);
		}
		field = readString(posizione, nomeField, field, minDigits, maxDigits, nullable);

		if(field == null || "".equals(field)) return null;
		try {
			return Integer.parseInt(field);
		} catch (Exception e) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione) +"] viola la sintassi per il campo "+nomeField+". Valore ["+field+"] non rispetta la sintassi [0-9]{1,"+maxDigits+"}",posizione);
		}
	}


	protected Double readDouble(Long posizione, String nomeField, String field, int minDigits, int maxDigits, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione, nomeField, field);
		}
		field = readString(posizione, nomeField, field, minDigits, maxDigits, nullable);

		if(field == null || "".equals(field)) return null;
		try {
			NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ITALIAN);
			Number number = formatter.parse(field);
			// Expression language coerces the Number to BigDecimal
			BigDecimal decimal = BigDecimal.valueOf(number.doubleValue());
			return decimal.doubleValue();
		} catch (Exception e) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione) +"] viola la sintassi per il campo "+nomeField+". Valore ["+field+"] non rispetta la sintassi [0-9]{1,"+maxDigits+"}",posizione);
		}
	}

	protected void checkNotNull(Long posizione, String nomeField, String field) throws ValidationException {
		if(field == null || field.equals("")) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione) +"] non presenta valori per il campo obbligatorio "+nomeField+".",posizione);
		}
	}


	protected String readString(Long posizione, String nomeField, String field, String regexp, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione, nomeField, field);
		}

		if(field == null || field.equals("")) {
			return null;
		} else if(!Pattern.matches(regexp, field)) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione) +"] viola la sintassi per il campo "+nomeField+". Valore ["+field+"] non rispetta la sintassi ["+regexp+"]",posizione);
		}

		return field;	
	}

	protected String readString(Long posizione, String nomeField, String field, int minLength, int maxLength, boolean nullable) throws ValidationException {
		if(!nullable) {
			checkNotNull(posizione, nomeField, field);
		}

		if(field == null || field.equals("")) {
			return null;
		} 
		else if(field.length() < minLength || field.length() > maxLength) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il pagamento in posizione [" + (posizione) +"] viola la sintassi per il campo "+nomeField+". Il valore non rispetta i vincoli di lunghezza [min="+minLength+",max="+maxLength+"]",posizione);
		}

		return field;	
	}

	// http://www.icosaedro.it/cf-pi/cf-java.txt
	static boolean isCF(String cf) {
		int i, s, c;
		String cf2;
		int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
		if (cf.length() != 16)
			return false; // La lunghezza del codice fiscale non e' corretta: il codice fiscale dovrebbe essere lungo esattamente 16 caratteri
		cf2 = cf.toUpperCase();
		for (i = 0; i < 16; i++) {
			c = cf2.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z'))
				return false; // Il codice fiscale contiene dei caratteri non validi: i soli caratteri validi sono le lettere e le cifre
		}
		s = 0;
		for (i = 1; i <= 13; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				s = s + c - '0';
			else
				s = s + c - 'A';
		}
		for (i = 0; i <= 14; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				c = c - '0' + 'A';
			s = s + setdisp[c - 'A'];
		}
		if (s % 26 + 'A' != cf2.charAt(15))
			return false; // Il codice fiscale non e' corretto: il codice di controllo non corrisponde
		return true;
	}
	
	// http://www.icosaedro.it/cf-pi/pi-java.txt
	static boolean isPIVA(String pi) {
		int i, c, s;
		if (pi.length() != 11)
			return false; // La lunghezza della partita IVA non e' corretta: la partita IVA dovrebbe essere lunga esattamente 11 caratteri
		for (i = 0; i < 11; i++) {
			if (pi.charAt(i) < '0' || pi.charAt(i) > '9')
				return false; // La partita IVA contiene dei caratteri non ammessi: la partita IVA dovrebbe contenere solo cifre
		}
		s = 0;
		for (i = 0; i <= 9; i += 2)
			s += pi.charAt(i) - '0';
		for (i = 1; i <= 9; i += 2) {
			c = 2 * (pi.charAt(i) - '0');
			if (c > 9)
				c = c - 9;
			s += c;
		}
		if ((10 - s % 10) % 10 != pi.charAt(10) - '0')
			return false; // La partita IVA non e' valida: il codice di controllo non corrisponde
		return true;
	}
	
}
