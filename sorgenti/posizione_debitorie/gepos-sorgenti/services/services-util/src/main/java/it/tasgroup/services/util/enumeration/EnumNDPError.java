/**
 * 
 */
package it.tasgroup.services.util.enumeration;

/**
 * @author pazzik
 *
 */
public enum EnumNDPError {
	
	PAA_SEMANTICA,
	PAA_ID_DOMINIO_ERRATO,
	PAA_PAGAMENTO_SCONOSCIUTO,
	PAA_PAGAMENTO_DUPLICATO,
	PAA_PAGAMENTO_IN_CORSO,
	PAA_PAGAMENTO_ANNULLATO,
	PAA_PAGAMENTO_SCADUTO,
	PAA_ATTIVA_RPT_IMPORTO_NON_VALIDO,
	PPT_PSP_SCONOSCIUTO;
	
	public static EnumNDPError getFromIrisReturnCode(EnumBusinessReturnCodes irisReturnCode){
		
		switch (irisReturnCode){
		
			case E0000001:
			case E0000002: 
			case E0000003:
			case E0000004:
			case E0000005: return PAA_SEMANTICA;
			case E0000007: return PPT_PSP_SCONOSCIUTO;
			case A0000028: return PAA_ID_DOMINIO_ERRATO;
			
			case A0000001: return PAA_PAGAMENTO_SCONOSCIUTO;
			
			case A0000007: 
			case A0000021: return PAA_PAGAMENTO_DUPLICATO;
			
			case A0000011: return PAA_PAGAMENTO_ANNULLATO;
			
			case A0000005: return PAA_PAGAMENTO_IN_CORSO;
			
			case A0000004: return PAA_PAGAMENTO_SCADUTO;
			
			case A0000017: return PAA_PAGAMENTO_IN_CORSO;
			
			case A0000022: return PAA_PAGAMENTO_DUPLICATO;
			
			case A0000020: return PAA_ATTIVA_RPT_IMPORTO_NON_VALIDO;
			
			case A0000029: return PAA_SEMANTICA;
			
			default: return PAA_SEMANTICA;
		}
		
	}
	
	public static String getFaultStringFromIrisRetCode(EnumBusinessReturnCodes irisReturnCode){		
			
			switch (irisReturnCode){
			
				case E0000001:
				case E0000002: 
				case E0000003:
				case E0000004:
				case E0000005: return "Errore semantico.";
				
				case E0000007: return "PSP sconosciuto";
				case A0000028: return "La PAA non corrisponde al Dominio indicato.";
				
				case A0000001: return "Pagamento in attesa risulta sconosciuto all Ente Creditore.";
				
				case A0000007: 
				case A0000021: return "Pagamento in attesa risulta concluso all Ente Creditore.";
				
				case A0000011: return "Pagamento in attesa risulta annullato all Ente Creditore.";
				
				case A0000005: return "Pagamento in attesa risulta in corso all Ente Creditore.";
				
				case A0000004: return "Pagamento in attesa risulta scaduto all Ente Creditore.";
				
				case A0000017: return "Pagamento in attesa risulta in corso all Ente Creditore.";
				
				case A0000022: return "Pagamento in attesa risulta concluso all Ente Creditore.";
				
				case A0000020: return "L importo del pagamento in attesa non e congruente con il dato indicato dal PSP.";
				
				case A0000029: return "Soggetto pagatore non congruente con il dato indicato dal PSP.";
			
				default: return "Errore semantico.";
			}
	}
}
