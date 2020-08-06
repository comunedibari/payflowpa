package it.nch.pagamenti;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import javax.servlet.http.HttpServletRequest;


public abstract class Negozio {	
	private static final String CARD_SIMULATOR = "_simulatore.cardSimulator";
	private static final String MYBANK_SIMULATOR = "_simulatore.myBankSimulator";
	private static final String SIMULATOR_LANGUAGE = "_simulatore.language";
	
	private static final String BOL_SIMULATOR = "_simulatore.bolSimulator";
	private static final String SIMULATORBOL_LANGUAGE = "_simulatoreBol.language";
	
	public abstract String getOpEsito();
	public abstract String getOpStatoOp();
	public abstract String getOpAutorizzazione();	
	public abstract String getOpData();
	public abstract String getOpTokenGuidOtp();
	public abstract String getOpFirma();
	public abstract String getOpTimeOut();
	public abstract String getOpUrlPagamento();
	public abstract String getNmsg();
	
	public static boolean initialize(HttpServletRequest request) {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		boolean simulatoreCarta = Boolean.valueOf(cpl.getProperty("simulatoreCarta")).booleanValue();
		boolean simulatoreMyBank = Boolean.valueOf(cpl.getProperty("simulatoreMyBank")).booleanValue();
		String simulatoreLanguage = cpl.getProperty("simulatore.language");
		
		request.getSession().setAttribute(SIMULATOR_LANGUAGE, simulatoreLanguage == null ? "it" : simulatoreLanguage);	
		
		if (simulatoreCarta) {
			request.getSession().setAttribute(CARD_SIMULATOR, "active");	
		} 
		
		if (simulatoreMyBank) {
			request.getSession().setAttribute(MYBANK_SIMULATOR, "active");
		}		
		
		
		// Initialize simulatore bonifico online con GATEWAY ON
		boolean silmulatoreBol = Boolean.valueOf(cpl.getProperty("simulatoreBol")).booleanValue();
		String simulatoreBolLanguage = cpl.getProperty("simulatoreBol.language");
		
		if (silmulatoreBol) {
			request.getSession().setAttribute(BOL_SIMULATOR, "active");
			
		}	
		
		request.getSession().setAttribute(SIMULATORBOL_LANGUAGE, simulatoreLanguage == null ? "it" : simulatoreLanguage);	
		//
		
		return simulatoreCarta;
	}
	
	public static Negozio getNegozio(HttpServletRequest request) {		
		initialize(request);		
		boolean demo = request.getSession().getAttribute(CARD_SIMULATOR) != null;
		return demo ? new NegozioSimulato(request)  : new NegozioEffettivo(request);
	}

	public static boolean isCardSimulatorActive(HttpServletRequest request) {
		initialize(request);		
		return request.getSession().getAttribute(CARD_SIMULATOR) != null;
	}
	public static boolean isMyBankSimulatorActive(HttpServletRequest request) {
		initialize(request);		
		return request.getSession().getAttribute(MYBANK_SIMULATOR) != null;
	}

	public static String getLanguage(HttpServletRequest request) {
		initialize(request);	
		String lang = (String) request.getSession().getAttribute(SIMULATOR_LANGUAGE);
		return lang == null ? "it" : lang;
	}
	
	
	// Simulatore bonifico online con GATEWAY ON
	
	public static Negozio getNegozio_BolSimulator(HttpServletRequest request) {		
		initialize(request);		
		return new NegozioEffettivo(request);
	}
	
	public static boolean isBolSimulatorActive(HttpServletRequest request) {
		initialize(request);		
		return request.getSession().getAttribute(BOL_SIMULATOR) != null;
	}
	
	public static String getLanguageBol(HttpServletRequest request) {
		initialize(request);	
		String lang = (String) request.getSession().getAttribute(SIMULATORBOL_LANGUAGE);
		return lang == null ? "it" : lang;
	}
	
	//

}
