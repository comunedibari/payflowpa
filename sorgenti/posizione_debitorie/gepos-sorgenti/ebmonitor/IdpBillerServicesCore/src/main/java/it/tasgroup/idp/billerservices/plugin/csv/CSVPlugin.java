package it.tasgroup.idp.billerservices.plugin.csv;

import static it.tasgroup.idp.billerservices.api.impl.BusinessComparatorHelper.areEquals;
import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestorePendenze;
import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumTipoAllineamento;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.EsitoValidazione;
import it.tasgroup.idp.billerservices.plugin.csv.CSVPluginParser.BasicRowInfo;
import it.tasgroup.idp.billerservices.plugin.csv.CSVRowPagamento.Debitore;
import it.tasgroup.idp.billerservices.plugin.csv.CSVRowPagamento.Stato;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.VociPagamento;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

public class CSVPlugin implements ILoaderPlugin {
	
	private InputStream rawdata;
	BufferedReader reader;
	private CSVPluginParser parser = getParser(getConfig());
	protected CSVBasicConfig config = getConfig();
	
	private CSVRowPagamento lastParsed = null;
	private long posizione=0;
	
	
	public CSVPlugin(InputStream rawdata)  {		
		this.rawdata=rawdata;
		this.rawdata.mark(1);
	}
	
	protected CSVBasicConfig getConfig() {
		return new CSVBasicConfig(';', '"', ';');
	};

	protected CSVPluginParser getParser(CSVBasicConfig config) {
		return new CSVPluginParserV3(config);
	};
	
	public DatiPiazzaturaFlusso piazzaturaFlusso () throws ValidationException {
		
		this.reader = new BufferedReader(new InputStreamReader(rawdata));
		
		String lastIdDebito="";
		
		try {
			validateHeader();
			//estraggo la prima riga, e da li cerco di capire senderId e senderSys
			String row;
			row=this.reader.readLine();
			BasicRowInfo pRow = null; 
					
			while (row!=null) {
				if (row.trim().length()!=0) {
					pRow = parser.getBasicRowInfo(row,1);
					lastIdDebito=pRow.idDebito;
					break;
				}	
				row=this.reader.readLine();
			};
			
		    if (pRow==null) {
		    	throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Il file non contiene nessun record valido");
		    }
			
		    DatiPiazzaturaFlusso returnValue=new DatiPiazzaturaFlusso();

		    returnValue.senderId=pRow.creditore;
		    returnValue.tipoDebito=pRow.tipoDebito;
		    returnValue.tipoFile=getTipoFile();
		    
		    // Si contano le righe non nulle, si controlla che siano tutte parsabili,
		    // Si controlla l'omogeneita' per creditore e tipo debito.
		    int numeroRighe = 1;
		    int numeroPendenze=1;
		    
		    
		    row=this.reader.readLine();

			while (row!=null) {
				if (row.trim().length()!=0) {
					numeroRighe=numeroRighe+1;
					pRow = parser.getBasicRowInfo(row,numeroRighe);
					if (!pRow.creditore.equals(returnValue.senderId)) {
						throw new ValidationException(EnumReturnValues.SENDER_ID_NON_OMOGENEO,"Il campo 'CREDITORE' deve essere omogeneo in tutto il file");
					}
					if (!pRow.tipoDebito.equals(returnValue.tipoDebito)) {
						throw new ValidationException(EnumReturnValues.TIPO_DEBITO_NON_OMOGENEO,"Il campo 'TIPO_DEBITO' deve essere omogeneo in tutto il file");
					}
					if (!lastIdDebito.equals(pRow.idDebito)) {
						numeroPendenze=numeroPendenze+1;
						lastIdDebito=pRow.idDebito;
					}
				}	
				row=this.reader.readLine();
			};

		    returnValue.numeroPosizioni=numeroPendenze;
		    
		    return returnValue;
		    
		} catch (IOException e) {
			throw new ValidationException(EnumReturnValues.ERRORE_GENERICO,e.getMessage(),e);
		} catch (ValidationException ve) {
			throw ve;
		} catch (Exception e) {
			throw new ValidationException(EnumReturnValues.ERRORE_GENERICO,e.getMessage(),e);
		}
		
	}

	
	
	
	@Override
	public EsitoValidazione validazioneFlusso(DatiPiazzaturaFlusso datiPiazzaturaFlusso, int numeroMassimoErrori, EntityManager em) {

		this.reader = new BufferedReader(new InputStreamReader(rawdata));
		boolean multiCondizioniEnabled=true;
		
		// ---------------------------------
		// Step 1: Diagnosi del flusso
		// ---------------------------------
	
		List<ValidationException> errorList = new ArrayList<ValidationException>();
		EsitoValidazione esito  = new EsitoValidazione();
		
		try {
			validateHeader();
		} catch (ValidationException e) {
			errorList.add(e);
			buildEsitoValidazione(errorList);
			return esito;			
		} catch (IOException e) {
			ValidationException ev = new ValidationException(EnumReturnValues.ERRORE_GENERICO, "Errore generico sulla lettura del file",e);
			errorList.add(ev);
			return buildEsitoValidazione(errorList);			
		}
		
		String row;
		Long posizione=0L;  
					
		// Set per controllo duplicati all'interno dello stesso flusso.
		// Non possono esistere nello stesso file due idPagamento uguali.
		// Non possono esistere nello stesso file due  idDebito uguali (non contigui).
		Set<String> idDebitoSet = new LinkedHashSet<String>();
		Set<String> idPagamentoSet = new LinkedHashSet<String>();
		
		// Dati per accorpamento, per rottura di idDebito. Tutte le righe consecutive con stesso idDebito sono considerate rate dello stesso debito
		String lastIdDebito = "";
		List<CSVRowPagamento> condizioniPendenza = new ArrayList<CSVRowPagamento>();
		CSVRowPagamento firstRowPendenza = null;
		
		boolean unmarshalError = true;
		
		try {
			// Leggo riga per riga, e valido la lista dei pagamenti.
			row=this.reader.readLine();
			
			while (row!=null) {
								
				
				if (row.trim().length()!=0) {
					
					posizione=posizione+1;
					
					try {

						unmarshalError=true;
						CSVRowPagamento pRow = parser.unmarshall(row, posizione);  //Ogni riga unmarshall viene validata a se
						unmarshalError=false;
						
						// Se non sono abilitate pendenze multicondizione
						// Esco immediatamente se trovo due righe con stesso id Debito.
						if (!multiCondizioniEnabled) {
							if (idDebitoSet.contains(pRow.getIdDebito())) {
								throw new ValidationException(EnumReturnValues.PENDENZA_DUPLICATA,"ID_DEBITO duplicato all'interno del file ["+pRow.getIdDebito()+"] ",posizione);
							}
						}
						
						
						// Altrimenti vado per "rottura di id Debito"
						if(!lastIdDebito.equals(pRow.getIdDebito())) {

							// check duplicazione
							if (idDebitoSet.contains(pRow.getIdDebito())) {
								throw new ValidationException(EnumReturnValues.PENDENZA_DUPLICATA,"ID_DEBITO duplicato all'interno del file ["+pRow.getIdDebito()+"]. Attenzione, per creare posizioni con stesso ID_DEBITO e piu' rate, occorre disporre le righe con stesso ID_DEBITO contigue nel CSV.",posizione);
							}
							idDebitoSet.add(pRow.getIdDebito());
							
							if (idPagamentoSet.contains(pRow.getIdCondizione())) {
								throw new ValidationException(EnumReturnValues.CONDIZIONE_PAGAMENTO_DUPLICATA,"ID_PAGAMENTO duplicato all'interno del file ["+pRow.getIdCondizione()+"]",posizione);
							}
							idPagamentoSet.add(pRow.getIdCondizione());

							
							// La nuova riga e' una nuova pendenza.
							// Chiudo la precedente e la valido (se non sono nel primo giro).
							if (firstRowPendenza!=null) {
								
								try {
								
									buildAndCheckPendenza(condizioniPendenza,posizione-1,datiPiazzaturaFlusso,em);
									
								} catch (ValidationException e) {
									if (errorList.size()<numeroMassimoErrori) {
									    errorList.add(e);
									} 									
								}
							}	
														
							condizioniPendenza = new ArrayList<CSVRowPagamento>(); 
							condizioniPendenza.add(pRow);
							
							lastIdDebito=pRow.getIdDebito();	
							firstRowPendenza=pRow;
						
						} else {
							// La nuova riga e' da considerare appartenente alla stessa pendenza con stesso id_debito iniziata nelle righe precedenti 
							// quindi la aggiungo alla lista precedentemente istanziata. I dati debito devono essere congruenti

							// Check duplicazione
							if (idPagamentoSet.contains(pRow.getIdCondizione())) {
								throw new ValidationException(EnumReturnValues.CONDIZIONE_PAGAMENTO_DUPLICATA,"ID_PAGAMENTO duplicato all'interno del file ["+pRow.getIdCondizione()+"]",posizione);
							}
							idPagamentoSet.add(pRow.getIdCondizione());
							
							
							if (!datiDebitoCongruenti(firstRowPendenza,pRow)) {
								throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Trovate due righe con stesso idDebito ["+pRow.getIdDebito()+"] con dati debito non omogenei. Impossibile convertire queste righe in unica posizione multi-rata. Per farlo occorre che i seguenti campi siano omogenei: "+getCampiOmogeneitaDebito(),posizione);
							}
							
							condizioniPendenza.add(pRow);
						}
						
					} catch (ValidationException e) {
						// Colleziono gli errori
						if (errorList.size()<numeroMassimoErrori) {
						    errorList.add(e);
						}       
					}
				}	
				row=this.reader.readLine();
			};
			
			
			// Alla fine del ciclo devo chiudere e validare l'ultima pendenza rimasta appesa (a meno che non sia uscito dal ciclo per unmarshall error)
			
			try  {
				if (!unmarshalError) {
					buildAndCheckPendenza(condizioniPendenza,posizione,datiPiazzaturaFlusso,em);
				}
			} catch (ValidationException e) {
				// Colleziono gli errori
				if (errorList.size()<numeroMassimoErrori) {
					errorList.add(e);
				}       
			}
			
						
			
		}  catch (IOException ioe) {
			ValidationException e = new ValidationException(EnumReturnValues.ERRORE_GENERICO, "Errore generico sulla lettura del file",ioe);
			errorList.add(e);
		}
		
		
		
		if (errorList.size()>0) {
			if (errorList.size()==numeroMassimoErrori) {
				ValidationException eLast= new ValidationException(EnumReturnValues.RAGGIUNTO_MAX_NUM_ERRORI_DIAGNOSI,"Raggiunto il numero massimo di errori di diagnosi");
			}
		}
		return buildEsitoValidazione(errorList);
	}


	private void buildAndCheckPendenza(List<CSVRowPagamento> condizioniPendenza, Long posizione, DatiPiazzaturaFlusso datiPiazzaturaFlusso, EntityManager em) throws ValidationException {
		Pendenze p = this.mapToPendenza(condizioniPendenza,posizione, datiPiazzaturaFlusso);

		try {
			
			//Se smartReplace non controllo le duplicazioni. Altrimenti le controllo solo sullo stato DA_PAGARE, ovvero l'inserimento di una nuova posizione da pagare
			boolean checkDuplicazione = (!datiPiazzaturaFlusso.smartReplace) && (condizioniPendenza.get(0).getStato()==Stato.DA_PAGARE);
			
			
			GestorePendenze.validaPendenza(p,checkDuplicazione,em); 
			
			// Valido gli stati. In caso di pendenza multicondizione gli stati delle righe devono 
			// essere congruenti:
			// CANCELLATO se usato, deve essere uniforme
			// DA_PAGARE_VARIAZIONE se usato, deve essere uniforme
			
			Stato lastStato = null;
			for (CSVRowPagamento pRow:condizioniPendenza) {
				Stato newStato=pRow.getStato();
				if (lastStato==null) {
					lastStato=newStato;
					continue;
				}
				if (newStato.equals(Stato.CANCELLATO) && !lastStato.equals(Stato.CANCELLATO)) {
					throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Lo stato 'CANCELLATO' deve essere assegnato a tutti i pagamenti dello stesso debito");
				}
				if (newStato.equals(Stato.DA_PAGARE_VARIAZIONE) && !lastStato.equals(Stato.DA_PAGARE_VARIAZIONE)) {
					throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Lo stato 'DA_PAGARE_VARIAZIONE' deve essere assegnato a tutti i pagamenti dello stesso debito");					
				}
			}
			
			
		} catch (ValidationException ev) {
			// Intercetto l'eccezione per completarla con la posizione, ma la rilancio subito
			ev.setProgressivo((long)posizione);
			throw ev;
		}

	}
	
		
	@Override
	public void moveFirst()  {
		// Reset dello stream e riposizionamento prima della prima posizione da caricare
		try {
			this.rawdata.reset();
			this.reader = new BufferedReader(new InputStreamReader(rawdata));

			validateHeader();  //In realta' qui NON DEVE esserci mai eccezione,in  quanto questo controllo
	        				   //e' gia' stato fatto in fase di piazzatura e validazione.. questo comando skippa l'header
			
			// Cerco la prima riga non nulla
			String row=null;
			while(true) {
			    row=this.reader.readLine();
			    if (row==null) {
			    	return; 
			    }	
			    if (!row.trim().equals("")) {
			    	posizione=1;
			    	// Unmarshalling. Nota: in teoria qui  ci potrebbe essere una Validation exception
			    	// Pero' sarebbe molto strano in quanto il flusso e' stato gia' validato
			    	this.lastParsed=parser.unmarshall(row, posizione);
			    	break;
			    }
			}
			
			
		} catch (Exception e) {
			
			throw new RuntimeException("Errore inatteso ",e);
			
		}

		
	}

	@Override
	public DatiAllineamento elaboraNextPendenza(DatiPiazzaturaFlusso datiPiazzaturaFlusso, Long progressivo) throws ValidationException, IOException {

		String row;
		DatiAllineamento result = new DatiAllineamento();
		
		// Quando si entra in questo metodo nella variabile lastParsed e' presente 
		// sempre l'ultima condizione parsata (ma non processata, del giro precedente)
		// A questo punto si valutano  le righe successive fintanto non ne troviamo una che
		// differisce per idDebito da lastParsed.
		// Tutte le condizioni consecutive che sono congruenti sono considerate condizioni dello stesso
		// debito.
		// La prima che differisce diventa "lastParsed" e viene caricata al giro successivo.
		
		if (this.lastParsed==null) {
			return null;
		}
		
		List<CSVRowPagamento> pRows = new ArrayList<CSVRowPagamento>();
		pRows.add(this.lastParsed);
		// Parsing del flusso fino alla prossima riga non vuota
		
		while (true) {
		    row=this.reader.readLine();
		    if (row==null) {
		    	this.lastParsed=null;
		    	break;
		    }	
		    if (!row.trim().equals("")) {
		    	
		    	result = new DatiAllineamento();
		    	
		    	// Unmarshalling. Nota: in teoria qui  ci potrebbe essere una Validation exception
		    	// Pero' sarebbe molto strano in quanto il flusso e' stato gia' validato
		    	CSVRowPagamento pRow = parser.unmarshall(row, 1L);
		    
		    	
		    	if (pRow.getIdDebito().equals(lastParsed.getIdDebito())) {

		    		// Fintanto trovo righe con stesso idDebito le considero "rate" della stessa pendenza
		    		
		    		// Nota: Non ripeto i controlli di congruenza in quanto gia' fatti in validazione.
		    		
		    		pRows.add(pRow);
		    		
		    		
		    	} else {
		    		
		    		// Appena trovo una riga che ha un diverso idDebito dalla precedente, chiudo la 
		    		// pendenza, e mi salvo lastParsed per il giro successivo
		    		
		    		this.lastParsed=pRow;
		    		break;
		    		
		    		
		    	}
		    }
		}

		// Creo la pendenza da allineare.		
    	Pendenze p = mapToPendenza(pRows, progressivo, datiPiazzaturaFlusso);
    	result.pendenzaDaAllineare = p;
    	
    	// Settaggio del tipo allineamento richiesto 
    	// in base allo stato presente nel CSV
    	
    	// Ciclo sulle condizioni estratte per la pendenza in corso per calcolare uno  
    	// "stato richiesto complessivo per la pendenza"
    	// La logica e' questa:
    	// In caso di stato condizioni omogeneo, lo stato complessivo e' quello stato.
    	// In caso di stato condizioni disomogeneo, lo stato complessivo viene valutato secondo questa
    	// priorita': 
    	
    	result.tipoAllineamento = null; 
    	
    	for (CSVRowPagamento pRow:pRows) {
    		EnumTipoAllineamento ta = tipoAllineamentoPerRiga(pRow, datiPiazzaturaFlusso.smartReplace);
    		
    		// Se siamo al primo giro tengo il primo risultato
    		if (result.tipoAllineamento==null) {
    			result.tipoAllineamento=ta;
    			continue;
    		}
    		
    		// Finche' trovo righe omogenee, mantengo il risultato trovato
    		if (ta.equals(result.tipoAllineamento)) {
    			continue;
    		}
    		
    		// Come trovo una difformita' valuto la priorita':
    		
    		if (ta.equals(EnumTipoAllineamento.UPDATE_STATUS)) {
    			// Update Status vince su tutti
    			result.tipoAllineamento=ta;
    			break;
    		}
    		
    		if (ta.equals(EnumTipoAllineamento.SMART_REPLACE)) {
    			result.tipoAllineamento=ta;
    			continue;
    		}
    		
    		if (ta.equals(EnumTipoAllineamento.INSERT)) {
    			if (result.tipoAllineamento.equals(EnumTipoAllineamento.SMART_REPLACE)) {
    				continue;
    			} else {
    				// TODO: Temo si finisca in errore.
    			}
    		}
    		
    		if (ta.equals(EnumTipoAllineamento.DELETE)) {
   				// TODO: Temo si finisca in errore.
    		}

    		if (ta.equals(EnumTipoAllineamento.REPLACE)) {
				// TODO: Temo si finisca in errore.
		    }
		
    	}
    			
		return result;
	}



	private EnumTipoAllineamento tipoAllineamentoPerRiga(CSVRowPagamento pRow, boolean isSmartReplaceActive) {
		
		EnumTipoAllineamento result=null;
		
		switch (pRow.getStato()) {
		case CANCELLATO:
			result = EnumTipoAllineamento.DELETE;
			break;
		case DA_PAGARE:
	    	if(isSmartReplaceActive==true) {
	    		result = EnumTipoAllineamento.SMART_REPLACE;
	    	} else {
	    		result = EnumTipoAllineamento.INSERT;
	    	}
	    	break;
		case DA_PAGARE_VARIAZIONE:
			result = EnumTipoAllineamento.REPLACE;
			break;
		case NON_PAGABILE:
			result = EnumTipoAllineamento.SMART_REPLACE;
			break;
		case PAGATO:
			if (pRow.isPagatoIDP()) {
				result = EnumTipoAllineamento.UPDATE_STATUS;
			} else {
				result = EnumTipoAllineamento.SMART_REPLACE;
			}
			break;
		case PAGATO_IRREGOLARE:	
			if (pRow.isPagatoIDP()) {
				result = EnumTipoAllineamento.UPDATE_STATUS;
			} else {
				result = EnumTipoAllineamento.SMART_REPLACE;
			}
			break;
		}	
		return result;
	}

	private void validateHeader() throws ValidationException, IOException {

		// Salto tutte le righe vuote all'inizio
		String header=this.reader.readLine();
		while (header!=null && header.trim().length()==0){
			header=this.reader.readLine();
		}
		
		// Una riga di intestazione ci deve essere
		if(header == null ) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Nessuna intestazione presente");
		}
		
		// La valido, rispetto al template di cui al metodo getPreambolo()
		String[] preamboloFileds = getPreambolo().split("[" + config.getFieldSeparator() + "]", -1); // mettiamo il separatore nella regex come character set (cioe' tra parentesi quadre) per non dovere "escapare" caratteri particolari, come il pipe
		String[] fields = header.split("[" + config.getFieldSeparator() + "]", -1); // vedi nota sopra
		
		for(int i = 0; i < preamboloFileds.length && i < fields.length ; i++) {
			if(fields[i].compareToIgnoreCase(preamboloFileds[i]) != 0) throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Intestazione del CVS errata. Atteso in posizione [" + (i+1) +"] il campo [" + preamboloFileds[i] + "], trovato il campo [" + fields[i] + "].");
		}
		
		if(fields.length < preamboloFileds.length) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Intestazione del CVS errata. Atteso in posizione [" + (fields.length) +"] il campo [" + preamboloFileds[fields.length] + "].");
		}
		
		if(fields.length > preamboloFileds.length) {
			throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"Intestazione del CVS errata. Non sono previsti campi oltre la posizione [" + (fields.length) +"].");
		}
		
	}
	
	@Override
	public String getTipoFile() {
		return "CSV_BASIC_V3";
	}
	
	protected String getPreambolo(){
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
				+ "IMPORTO_PAGATO" + config.getFieldSeparator()
				+ "CANALE_PAGAMENTO" + config.getFieldSeparator()
				+ "DATA_PAGAMENTO" + config.getFieldSeparator()
				+ "NOTE_PAGAMENTO" + config.getFieldSeparator()
				+ "PAGATO_IDP";
	}
	
	private String getCampiOmogeneitaDebito() {
		return "CREDITORE, TIPO_DEBITO, NOTE_DEBITO, CAUSALE_DEBITO, ANNO_RIFERIMENTO, DEBITORI";
	}


	/**
	 * Trasforma un array di condizioni di pagamento in una pendenza.
	 * Il  metodo e' predisposto per la gestione delle rateizzazioni
	 * 
	 * @param condizioniPendenza
	 * @return
	 */
	private Pendenze mapToPendenza(List<CSVRowPagamento> condizioniPendenza, long posizione, DatiPiazzaturaFlusso datiPiazzaturaFlusso) throws ValidationException {
		
		Pendenze pendenza = new Pendenze();
		
		if (condizioniPendenza==null || condizioniPendenza.isEmpty()) {
			throw new ValidationException(EnumReturnValues.ERRORE_GENERICO,"Impossibile convertire in pendenza la riga "+(posizione),(posizione));
		}
		
		String modalitaPagamento;
		String tipoPagamentoCondizione;
		BigDecimal importoTotale=BigDecimal.ZERO;
		
		
		if (condizioniPendenza.size()>1) {
			// Piu' condizioni: Assumo il comportamento "pagamento a rate"
			modalitaPagamento = "R";
			tipoPagamentoCondizione = "R";
		} else {
			// Una condizione: assumo il comportamento posizione unica.
			modalitaPagamento = "S";
			tipoPagamentoCondizione = "S";
		}
		
		pendenza.setIdPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());
		
		CSVRowPagamento p = condizioniPendenza.get(0);   // Prima condizione, la uso come template per i dati comuni.
		
		// Dati fissi
		pendenza.setDvRiferimento("EUR");
		pendenza.setCoRiscossore(null);
		pendenza.setIdMittente(datiPiazzaturaFlusso.senderId);
		pendenza.setFlModPagamento(modalitaPagamento);
		pendenza.setIdTributoStrutturato(null);  //Per ora non gestito
		pendenza.setOpInserimento("Loader Plugin CSV");
		pendenza.setRiferimento(null);
		pendenza.setTsCreazioneente(new Timestamp(System.currentTimeMillis()));
		pendenza.setTsEmissioneente(new Timestamp(System.currentTimeMillis()));
		pendenza.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date mai;
		try {
			mai = sdf.parse("2999-01-01");
			pendenza.setTsPrescrizione(new Timestamp(mai.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore parsing date");
		}
		
				
		// Dati  omogeneita'
		pendenza.setAnnoRiferimento(p.getAnnoRiferimento());
		pendenza.setCdTrbEnte(p.getTipoDebito());
		pendenza.setDeCausale(p.getCausaleDebito());
		pendenza.setIdPendenzaente(p.getIdDebito());
		pendenza.setNote(p.getNoteDebito());
		
		// Questi dati dovrebbero uscire dalla "Piazzatura Flusso"
		// decodificando p.getCreditore() e p.getTipoDebito()..gia' fatto in precedenza
		pendenza.setDeMittente(datiPiazzaturaFlusso.descrizioneEnte);
		pendenza.setCoAbi(datiPiazzaturaFlusso.abiEnte);
		pendenza.setIdEnte(datiPiazzaturaFlusso.idEnte);
		pendenza.setIdSystem(datiPiazzaturaFlusso.senderSys);
		pendenza.setIdTributo(datiPiazzaturaFlusso.idTributo);

				
		Set<DestinatariPendenze> destinatari = new LinkedHashSet<DestinatariPendenze>();
		
		// Assegnazione dei debitori
		// --------------------------
		if (p.getDebitori()!=null) {
			for(CSVRowPagamento.Debitore debitore:p.getDebitori()) {
				DestinatariPendenze dp = new DestinatariPendenze();
				 dp.setIdDestinatario(GeneratoreIdUnivoci.GetCurrent().generaId());
				 dp.setPendenze(pendenza);
				 dp.setCoDestinatario(debitore.getCodiceFiscale());
				 dp.setDeDestinatario(debitore.getAnagrafica());
				 dp.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
				 dp.setTiDestinatario("CI"); 
				 dp.setStRiga("V");
				destinatari.add(dp);
			}
			pendenza.setDestinatariPendenze(destinatari);
		}	
		
		
		// Assegnazione delle condizioni di pagamento  
		// ---------------------------------------------
		Set<CondizioniPagamento> condizioni = new LinkedHashSet<CondizioniPagamento>();
		
		for (CSVRowPagamento cond : condizioniPendenza) {
			
				CondizioniPagamento  cp = new CondizioniPagamento();
				cp.setIdCondizione(GeneratoreIdUnivoci.GetCurrent().generaId("RND"));
				cp.setPendenze(pendenza);
				
				cp.setCausalePagamento(cond.getCausalePagamento());
				cp.setCdTrbEnte(cond.getTipoDebito());
				cp.setDeCanalepag(cond.getCanalePagamento());
				cp.setDeNotepagamento(cond.getNotePagamento());
				cp.setDtFinevalidita(cond.getDataFineValidita());
				cp.setDtIniziovalidita(cond.getDataInizioValidita());
				if (cond.getDataPagamento()!=null) {
					cp.setDtPagamento(new Timestamp(cond.getDataPagamento().getTime()));
				} else {
					cp.setDtPagamento(null);
				}
				cp.setDtScadenza(cond.getDataScadenza());
				
				
		
				cp.setIdEnte(datiPiazzaturaFlusso.idEnte);
				if (cond.getIbanBeneficiario()!=null) {
					cp.setIbanBeneficiario(cond.getIbanBeneficiario());
					cp.setRagioneSocialeBeneficiario(datiPiazzaturaFlusso.descrizioneEnte);
				}
				
				cp.setIdPagamento(cond.getIdCondizione());
				
				if (cond.getImportoPagamento()!=null) {
					BigDecimal imp = new BigDecimal(cond.getImportoPagamento());
					cp.setImTotale(imp.setScale(2, RoundingMode.HALF_DOWN));	
				} else {
					cp.setImTotale(null);
				}
		
				if (cond.getTotalePagato()!=null) {
					BigDecimal imp = new BigDecimal(cond.getTotalePagato());
					cp.setImPagamento(imp.setScale(2,RoundingMode.HALF_DOWN));	
				} else {
					cp.setImPagamento(null);
				}
				if (CSVRowPagamento.Stato.CANCELLATO.equals(cond.getStato())) {
					cp.setStRiga("C");
				} else {
					cp.setStRiga("V");
				}	
				cp.setTiPagamento(tipoPagamentoCondizione);
				cp.setDeMezzopagamento(null);
				cp.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
				
				if (CSVRowPagamento.Stato.PAGATO.equals(cond.getStato())) {
					cp.setStPagamento("P");	
				} else if (CSVRowPagamento.Stato.NON_PAGABILE.equals(cond.getStato())) {
					cp.setStPagamento("X");	
				} else if (CSVRowPagamento.Stato.PAGATO_IRREGOLARE.equals(cond.getStato())) {
					cp.setStPagamento("E");	
				} else {
					cp.setStPagamento("N");
				}
				
				
				if (cond.getVoci()!=null && !cond.getVoci().isEmpty()) {
					Set<VociPagamento> voci= new LinkedHashSet<VociPagamento>();
					
					for (CSVRowPagamento.Voce v:cond.getVoci()) {
						VociPagamento vp = new VociPagamento();
						vp.setIdVoce(GeneratoreIdUnivoci.GetCurrent().generaId());
						vp.setCondizioniPagamento(cp);
						
						vp.setCoVoce(v.getIdVoce());
						vp.setDeVoce(v.getIdVoce());
						vp.setTiVoce(v.getIdVoce());
						vp.setIdPendenza(pendenza.getIdPendenza());
						vp.setStRiga("V");
						vp.setTsDecorrenza(new Timestamp(System.currentTimeMillis()));
						
						if (v.getImporto()!=null) {
							BigDecimal imp = new BigDecimal(v.getImporto());
							vp.setImVoce(imp.setScale(2,RoundingMode.HALF_DOWN));	
						} else {
							vp.setImVoce(null);
						}
						
						voci.add(vp);
					}
		
					cp.setVociPagamento(voci);
					
				}
				
				condizioni.add(cp);
				importoTotale = importoTotale.add(cp.getImTotale());
		
		}
		
		pendenza.setCondizioniPagamento(condizioni);
		
		
		// Importo totale pendenza= somma importi condizioni 
		pendenza.setImTotale(importoTotale);
		
		
		// Calcolo lo stato della pendenza in base allo stato delle sue condizioni
		
		String statoPendenza="C";  //Chiusa se tutte le sue condizioni sono PAGATO, NON_PAGABILE. 
		String statoRigaPendenza="A"; // Se tutte le condizioni sono in stato "CANCELLATO" passo la pendenza all'allineamento come cancellata.
		for (CSVRowPagamento cond : condizioniPendenza) {
			if (CSVRowPagamento.Stato.CANCELLATO.equals(cond.getStato())) {
				continue;
			} else {
				statoRigaPendenza="V";
			}
			if (CSVRowPagamento.Stato.PAGATO_IRREGOLARE.equals(cond.getStato()) || 
				CSVRowPagamento.Stato.PAGATO_IRREGOLARE.equals(cond.getStato()) || 
				CSVRowPagamento.Stato.DA_PAGARE.equals(cond.getStato()) ||
				CSVRowPagamento.Stato.DA_PAGARE_VARIAZIONE.equals(cond.getStato())) {
				statoPendenza="A";
				break;
			}
		}
		pendenza.setStRiga(statoRigaPendenza);
		pendenza.setStPendenza(statoPendenza);		
			
		return pendenza;
	}
	
	
	private EsitoValidazione buildEsitoValidazione(List<ValidationException> errorsList) {
		EsitoValidazione esito  = new EsitoValidazione();
		if (errorsList==null || errorsList.isEmpty()) {
			esito.ok=true;
		} else {
			esito.ok=false;
		}
		esito.errori=errorsList;
		esito.msgEsito=parser.marshallEsitoValidazione(errorsList);
		return esito;
	}
	
	/**
	 * Controlla che due righe del CSV siano congruenti riguardo ai dati riguardanti il debito
	 * @param p1
	 * @param p2
	 */
	private boolean datiDebitoCongruenti(CSVRowPagamento p1, CSVRowPagamento p2) {
		if (!areEquals(p1.getIdDebito(),p2.getIdDebito())) return false;
		if (!areEquals(p1.getAnnoRiferimento(),p2.getAnnoRiferimento())) return false;
		if (!areEquals(p1.getTipoDebito(),p2.getTipoDebito())) return false;
		if (!areEquals(p1.getCausaleDebito(),p2.getCausaleDebito())) return false;
		if (!areEquals(p1.getNoteDebito(),p2.getNoteDebito())) return false;
		if (!areEquals(p1.getCreditore(),p2.getCreditore())) return false;
		
		if (p1.getDebitori()==null && p2.getDebitori()!=null) return false;
		if (p1.getDebitori()!=null && p2.getDebitori()==null) return false;
		if (p1.getDebitori().size()!= p2.getDebitori().size()) return false;
		
		// Creo mappa transitoria per trovare rapidamente un debitore, per codice fiscale
		HashMap<String, Debitore> p2DestinatariMap = new HashMap<String, Debitore>();
		for (Debitore d:p2.getDebitori())  {
			p2DestinatariMap.put(d.getCodiceFiscale(), d);
		}
		
		// Ciclo sui debitori di p1 e cerco corrispondenza con quelli di p2, mon appena non la trovo esco.		
		for (Debitore d1:p1.getDebitori()) {
			Debitore d2 = p2DestinatariMap.get(d1.getCodiceFiscale());
			if (d2==null) return false;
			if (!areEquals(d1.getAnagrafica(),d2.getAnagrafica())) return false;
		}
				
		return true;
	}


	@Override
	public void marshallEsitoOnStream(
			List<EsitoCaricamentoPendenza> esitiPendenze, OutputStream os) {
		parser.marshallEsitoCaricamento(esitiPendenze,os);
		
	}	
}
