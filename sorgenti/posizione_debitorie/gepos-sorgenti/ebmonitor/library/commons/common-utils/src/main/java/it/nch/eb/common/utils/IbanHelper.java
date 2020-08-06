package it.nch.eb.common.utils;

/**
 * 
 * @author BattagliL
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IbanHelper {
	
	public static void main2(String[] args) {
		
		IbanHelper iban = new IbanHelper("");			
		// TODO Auto-generated method stub
		String res = iban.ibanCheck();
		System.out.println("iban = " + res);
		
	}	

	private String iban = null;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * 
	 * @param iban
	 */
	public IbanHelper(String iban) {
		super();
		this.iban = iban;
	}
	
	
	
	public IbanHelper() {
	}

	/**
	 * 
	 * @return
	 */
	public String ibanCheck() {
		
		synchronized (iban) {
					
			String iban = this.getIban().trim();
			String ok = "OK"; 
			
			int L;// string length
			String S; // varchar2(34); -- IBAN swapped
			int C; // pls_integer; -- code of current char
			int K; // pls_integer; -- code normalyzed to range 0..35
			int R = 0;// pls_integer := 0; -- remainder of division by 97
			//-- Check length
			L = iban.length();//length(B);
			if (iban == null) {
				return "KO";
			} else if ((5>L || L>34))  {
				return "KO";
			}
				
			//-- Swap the first four characters with the rest
			S = iban.substring(5,L-4) + iban.substring(1,4);
			
	        S = iban.substring(4) + iban.substring(0, 4); 
	        for (int i = 0; i < S.length(); i++ ) 
	        { 
	                C = S.charAt(i); 
	                if (48 <= C && C <= 57) 
	                { 
	                	if (i == S.length()-4 || i == S.length()-3) { 
//	                			Tracer.debug(getClass().getName(), "", "Posizioni 1 e 2 non possono contenere cifre"); 
	                			return "KO"; 
	                	} 
	                    K = C - 48; 
	                } 
	                else if (65 <= C && C <= 90) 
	                { 
	                	if (i == S.length()-2 || i == S.length()-1) { 
//	                			Tracer.debug(getClass().getName(), "", "Posizioni 3 e 4 non possono contenere lettere"); 
	                			return "KO"; 
	           			} 
	                	K = C - 55; 
	                } 
	                else { 
//	                	Tracer.debug(getClass().getName(), "", "Sono ammesse solo cifre e lettere maiuscole"); 
	                	return "KO"; 
	               	} 
	                if (K > 9) 
	                        R = (100 * R + K) % 97; 
	                else   
	                        R = (10 * R + K) % 97; 
	        } 
	        if (R != 1) { 
//	        	Tracer.debug(getClass().getName(), "", "Il codice di controllo e' errato"); 
	        	return "KO"; 
	       	} 				
			return ok;
		}
		
	}

	/**
	 * 
	 * @return
	 */
	public String ibanCheck(String value) {
		this.setIban(value);
		return ibanCheck();			
	}
	
	
	/**
	 * Controlla la coerenza dell'IBAN con il BBAN composto da abi, cab, numerocc, cin
	 * @return true o false
	 * Se uno dei parametri passati e' null (o l'iban e' null) ritorna false
	 * Ha senso solo per IBAN radicati su IT o SM. Negli altri casi il risultato non e' affidabile.
	 */
	public boolean ibanCheckWithBBAN(String abi, String cab, String numerocc, String cin ) {
		boolean returnValue=false; 

		if (abi!=null && cab!=null && numerocc!=null & cin!=null && this.iban!=null) {
			returnValue=abi.equals(this.getAbi()) &&
						cab.equals(this.getCab()) &&
						numerocc.equals(this.getNumeroCC()) &&
						cin.equals(this.getCin());
		}

		return returnValue;
	}

	/**
	 * Controlla la coerenza dell'IBAN con il BBAN composto da abi, cab, numerocc (Non controlla il CIN)
	 * @return true o false
	 * Se uno dei parametri passati e' null (o l'iban e' null) ritorna false.
	 * Ha senso solo per IBAN radicati su IT o SM. Negli altri casi il risultato non e' affidabile.
	 */
	public boolean ibanCheckWithBBAN(String abi, String cab, String numerocc) {
		boolean returnValue=false; 
					
		if (abi!=null && cab!=null && numerocc!=null & (this.iban!=null && this.iban.length()>=27)) {
			
			returnValue=abi.equals(this.getAbi()) &&
						cab.equals(this.getCab()) &&
						numerocc.equals(this.getNumeroCC());
		}
		
		return returnValue;
	}
	
	
	/**
	 * Estrae il codicePaese dall'IBAN
	 * @return String
	 */
	public String getPaese() {
		if (this.iban!=null)  {
			return this.iban.substring(0,2);
		} else {
			return null;
		}			
	}

	/**
	 * Estrae il checkdigit dall'IBAN
	 * @return String
	 */		
	public String getCheckDigit() {
		if (this.iban!=null)  {
			return this.iban.substring(2,4);
		} else {
			return null;
		}			
	}

	/**
	 * Estrae il cin dall'IBAN
	 * @return String
	 */
	public String getCin() {
		if (this.iban!=null)  {
			return this.iban.substring(4,5);
		} else {
			return null;
		}			
	}

	/**
	 * Estrae l'Abi dall'IBAN
	 * @return String
	 */				
	public String getAbi() {
		if (this.iban!=null)  {
			return this.iban.substring(5,10);
		} else {
			return null;
		}
	}

	/**
	 * Estrae il CAB dall'IBAN
	 * @return String
	 */		
	public String getCab() {
		if (this.iban!=null)  {
			return this.iban.substring(10,15);
		} else {
			return null;
		}			
	}

	/**
	 * Estrae il nimerocc dall'IBAN
	 * @return String
	 */		
	public String getNumeroCC() {
		if (this.iban!=null)  {
			return this.iban.substring(15,27);
		} else {
			return null;
		}			
	}
	
	// https://github.com/arturmkrtchyan/iban4j/blob/master/src/main/java/org/iban4j/IbanUtil.java
	public static int calculateCheckDigit(String iban) {
		String reformattedIban = iban.substring(4) + iban.substring(0, 2) + "00";
		long total = 0;
		for (int i = 0; i < reformattedIban.length(); i++) {
			int numericValue = Character.getNumericValue(reformattedIban.charAt(i));
			total = (numericValue > 9 ? total * 100 : total * 10) + numericValue;
			if (total > 999999999) {
                total = (total % 97);
            }
		}
		return 98 - (int) (total % 97);
	}
	
	public static boolean checkCheckdigit(String iban) {
		return Integer.parseInt(iban.substring(2, 4)) == calculateCheckDigit(iban);
	}
	
	public static void main(String[] args) {
		String iban = "IT00G0760102800000026730507";
		int checkDigit = IbanHelper.calculateCheckDigit(iban);
		System.out.println(String.format("%02d", checkDigit));
		System.out.println(checkCheckdigit(iban));
	}	
	
}
