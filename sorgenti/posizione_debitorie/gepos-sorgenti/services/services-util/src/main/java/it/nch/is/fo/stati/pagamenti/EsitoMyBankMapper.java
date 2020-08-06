package it.nch.is.fo.stati.pagamenti;

import java.util.HashMap;
import java.util.Map;


    
public class EsitoMyBankMapper {
	private static EsitoMyBankMapper obj = null;
	
	//HashMap is not synchronized!!
	private static final Map<String, StatiPagamentiIris> mapper = new HashMap<String, StatiPagamentiIris>();
	private boolean loaded = false;
	private Loader loader = null;

	private EsitoMyBankMapper() {};
	/*
	 * TODO rendere la classe singleton attraverso spring, in questo modo è possibile rendere il Map
	 * non static ma anche definire il loader da caricare.
	 */ 
	public static EsitoMyBankMapper getInstance() {
        if(obj == null)
        	obj = new EsitoMyBankMapper();
        
        return obj;
	}
	
	private void load() {
		if(!loaded) {
			if(loader == null)
				loader = new InMemoryLoader();
			loader.load();
			loaded = true;
		}
	}
	
	/**
	 * 
	 * @param stato stato ricevuto da MyBank  trasformato in {@link Integer}
	 * @return lo stato iris
	 * @throws Exception se non è stato trovato la decodifica
	 */
	public StatiPagamentiIris getEsitoIris(String stato) throws Exception {
		load();
		StatiPagamentiIris stI = mapper.get(stato);
		
		if(stI == null) {
			throw new Exception("Stato non trovato per la chiave:" + stato);
		}
		return stI;
	}
	
	
	interface Loader {
		void load();
	}

	class InMemoryLoader implements Loader {
		
		public void load() {
			mapper.put(StatiMyBank.AUTHORISED.getStato(), StatiPagamentiIris.ESEGUITO);
			mapper.put(StatiMyBank.AUTHORISINGPARTYABORTED.getStato(), StatiPagamentiIris.ANNULLATO);
			mapper.put(StatiMyBank.ERROR.getStato(), StatiPagamentiIris.IN_ERRORE);
			mapper.put(StatiMyBank.PENDING.getStato(), StatiPagamentiIris.IN_CORSO);
			mapper.put(StatiMyBank.TIMEOUT.getStato(), StatiPagamentiIris.IN_ERRORE);
	
		}
	}

}
