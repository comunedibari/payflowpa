package it.nch.is.fo.stati.pagamenti;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * TODO rendere la classe singleton attraverso spring, in questo modo è possibile rendere il Map
 * non static ma anche definire il loader da caricare.
 * 
 * @author TamboriniP
 *
 */
    
public class EsitiNegozioPPGMapper {
	//Meglio renderlo singleton attraverso spring!
	private static EsitiNegozioPPGMapper obj = null;
	
	//HashMap is not synchronized!!
	private static final Map<Integer, StatiPagamentiIris> mapper = new HashMap<Integer, StatiPagamentiIris>();
	private boolean loaded = false;
	private Loader loader = null;
    
	/* 
	 * esiti tx Negozio (op_esito). Servono ?
	 */
	private static final String ESITO_0 = "0";
	private static final String ESITO_1 = "1";
	private static final String ESITO_2 = "2";
	
	private EsitiNegozioPPGMapper() {};
	/*
	 * TODO rendere la classe singleton attraverso spring, in questo modo è possibile rendere il Map
	 * non static ma anche definire il loader da caricare.
	 */ 
	public static EsitiNegozioPPGMapper getInstance() {
        if(obj == null)
        	obj = new EsitiNegozioPPGMapper();
        
        return obj;
	}
	
	private void load() {
		if(!loaded) {
			//FIXME Insert Factory for mapping load
			if(loader == null)
				loader = new InMemoryLoader();
			loader.load();
			loaded = true;
		}
	}
	/**
	 * 
	 * @param stato ricevuto dal negozio (parametro op_stato_op)
	 * @return lo stato iris
	 * @throws Exception se non è stato trovato la decodifica
	 */
	public String getEsito(String stato) throws Exception{
		String esito = null;
		try {
			Integer statoInt = Integer.getInteger(stato);
			
			esito = getEsitoIris(statoInt).name();
			
		}catch(Exception e) {
			//go back with exception
			//TODO log;
			throw e;
		}
		return esito;
	}
	/**
	 * 
	 * @param stato stato ricevuto dal negozio (op_stato_op) trasformato in {@link Integer}
	 * @return lo stato iris
	 * @throws Exception se non è stato trovato la decodifica
	 */
	public StatiPagamentiIris getEsitoIris(Integer stato) throws Exception {
		load();
		StatiPagamentiIris stI = mapper.get(stato);
		
		if(stI == null) {
			throw new Exception("Stato non trovato per la chiave:" + stato);
		}
		return stI;
	}
	
	public StatiPagamentiIris getEsitoIris(String stato) throws Exception {
		StatiPagamentiIris esito = null;
		try {
			Integer statoInt = Integer.valueOf(stato);
			
			esito = getEsitoIris(statoInt);
			
		}catch(Exception e) {
			//go back with exception
			//TODO log;
			throw e;
		}
		return esito;
	}
	
	interface Loader {
		void load();
	}

	class InMemoryLoader implements Loader {
		
		public void load() {
			mapper.put(StatiNegozioPPG.ESITO_STATO_0.getStato(), StatiPagamentiIris.ANNULLATO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_1.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_2.getStato(), StatiPagamentiIris.IN_CORSO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_3.getStato(), StatiPagamentiIris.IN_CORSO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_5.getStato(), StatiPagamentiIris.ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_6.getStato(), StatiPagamentiIris.ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_7.getStato(), StatiPagamentiIris.ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_8.getStato(), StatiPagamentiIris.ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_9.getStato(), StatiPagamentiIris.ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_10.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_11.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_12.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_13.getStato(), StatiPagamentiIris.ANNULLATO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_14.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_15.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_16.getStato(), StatiPagamentiIris.NON_ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_17.getStato(), StatiPagamentiIris.NON_ESEGUITO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_18.getStato(), StatiPagamentiIris.IN_CORSO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_19.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_20.getStato(), StatiPagamentiIris.IN_CORSO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_21.getStato(), StatiPagamentiIris.ANNULLATO_DA_OPERATORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_30.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_31.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_32.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_33.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_34.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_39.getStato(), StatiPagamentiIris.ANNULLATO);
			mapper.put(StatiNegozioPPG.ESITO_STATO_40.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiNegozioPPG.ESITO_STATO_50.getStato(), StatiPagamentiIris.IN_ERRORE);
		}
	}

}
