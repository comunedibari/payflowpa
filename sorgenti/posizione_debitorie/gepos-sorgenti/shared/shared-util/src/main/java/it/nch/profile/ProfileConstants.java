/*
 * Created on Jul 19, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.profile;

/**
 * @author MaranesiL
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProfileConstants {
	
	// da campo FUNZIONIABILITATE della tabella JLTOPER
	public static final String FLAG_ACCESS = "access";
	// Chiavi che rappresentano le singole abilitazioni, 
	// vengono utilizzati anche come nomi dei tag xml figli di: 
	// TagFlagProfilazioneEstesa e
	// abilitazioni - funzione
	public static final String FLAG_INSERT = "insert";
	public static final String FLAG_DELETE = "delete";
	public static final String FLAG_EDIT = "edit";
	public static final String FLAG_MASSIVEEDIT = "massiveedit";
	public static final String FLAG_DUPLICATE = "duplicate";
	public static final String FLAG_VIEWFLUX = "viewflux";
	public static final String FLAG_CREATEFLUX = "createflux";
	public static final String FLAG_DELETEFLUX = "deleteflux";
	public static final String FLAG_DUPLICATEFLUX = "duplicateflux";
	public static final String FLAG_SIGNFLUX = "signflux";
	public static final String FLAG_SENDFLUX = "sendflux";
	public static final String FLAG_VIEWDISPO = "viewdispo";
	
/*	public static final String FIELD_FLAGINSERT = "FLAGINSERT";
	public static final String FIELD_FLAGDELETE = "FLAGDELETE";
	public static final String FIELD_FLAGEDIT = "FLAGEDIT";
	public static final String FIELD_FLAGMASSIVEEDIT = "FLAGMASSIVEEDIT";
	public static final String FIELD_FLAGDUPLICATE = "FLAGDUPLICATE";
	public static final String FIELD_FLAGVIEWFLUX = "FLAGVIEWFLUX";
	public static final String FIELD_FLAGCREATEFLUX = "FLAGCREATEFLUX";
	public static final String FIELD_FLAGDELETEFLUX = "FLAGDELETEFLUX";
	public static final String FIELD_FLAGDUPLICATEFLUX = "FLAGDUPLICATEFLUX";
	public static final String FIELD_FLAGSIGNFLUX = "FLAGSIGNFLUX";
	public static final String FIELD_FLAGSENDFLUX = "FLAGSENDFLUX";
	public static final String FIELD_FLAGVIEWDISPO = "FLAGVIEWDISPO"; */
	
	public static final int NUM_ABILITAZIONI_PROFILAZIONE_ESTESA = 12;
		
	
	// ----- COSTANTI PER CREARE L'XML -----
    // Nome del nodo usato nella creazione del nodo da Query
	public static final String XML_NODE_ABILITAZIONI = "abilitazioni";
	
	// Nome del nodo usato nella creazione del nodo da FlagsAbilitazioniBean
	public static final String XML_NODE_FUNZIONE = "funzione";
	
	// Attributi dei nodi usati nella creazione del nodo da FlagsAbilitazioniBean
	public static final String XML_ATTR_SERVIZIO = "servizio";
	public static final String XML_ATTR_ABILITATO = "abilitato";
	
	
	/*
	 * Traduce il nome del servizio che compare nella gestione flussi:
	 * Es. BONIFICO, EFFETTOPAGATO, nel relativo codice a tre lettere del 
	 * servizio utilizzato per la profilazione. 
	 *  
	 */
	public static String getCodiceServizio(String codiceServizio){
		
		String codeToReturn = null;
	
		String code = null;
		if (codiceServizio!=null) {
			code=codiceServizio.toUpperCase();
		}
		
		if("ASSEGNO".equals(code)){
			
			codeToReturn = "ASC";
		}else if("BONIFICO".equals(code)){
			
			codeToReturn = "BON";
		}else if("GIROCONTO".equals(code)){
			
			codeToReturn = "GIR";
		}else if("STIPENDIO".equals(code)){
			
			codeToReturn = "STI";
		}else if("EFFETTOPAGATO".equals(code) || 
                 "EFFETTONONPAGATO".equals(code)){
			
			codeToReturn = "PEF";
		}else if("PAGAMENTOESTERO".equals(code)){
			
			codeToReturn = "PAE";
		}else if("MAV".equals(code)){
			
		    codeToReturn = "MAV";
	    }else if("RIBA".equals(code)){
			
			codeToReturn = "RIB";
		}else if("RID".equals(code)){
			
		    codeToReturn = "RID";
	    }else if("ALLINEAMENTOARCHIVIRID".equals(code)){
			
			codeToReturn = "AAR";
		}else if("TRASFERIMENTORID".equals(code)){
			
			codeToReturn = "TDR";
		}else if("F24".equals(code)){
			
		    codeToReturn = "F24";
	    }else if("P24".equals(code)){
			
		    codeToReturn = "P24";
	    }else if("R24".equals(code)) {
			codeToReturn = "R24";
	    } else if("ISTRUZIONI".equals(code)){
			codeToReturn = "AVB";
		 } else if("BOLLETTINOBANCARIO".equals(code)){
			 codeToReturn = "BOL";
		 } else if("FLUSSOLIBERO".equals(code)){
			 codeToReturn = "FSL";
		 }	
		
		return codeToReturn;
	}
}
