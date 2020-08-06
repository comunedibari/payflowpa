package it.nch.utility.iban;

/**
 * 
 * @author BattagliL
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IbanHelper {

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
				return "null";
			} else if ((5>L || L>34))  {
				return "iban non compreso tra 5 e 34";
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
	                			return "1-2"; 
	                	} 
	                    K = C - 48; 
	                } 
	                else if (65 <= C && C <= 90) 
	                { 
	                	if (i == S.length()-2 || i == S.length()-1) { 
//	                			Tracer.debug(getClass().getName(), "", "Posizioni 3 e 4 non possono contenere lettere"); 
	                			return "3-4"; 
	           			} 
	                	K = C - 55; 
	                } 
	                else { 
//	                	Tracer.debug(getClass().getName(), "", "Sono ammesse solo cifre e lettere maiuscole"); 
	                	return "maiuscole"; 
	               	} 
	                if (K > 9) 
	                        R = (100 * R + K) % 97; 
	                else 
	                        R = (10 * R + K) % 97; 
	        } 
	        if (R != 1) { 
//	        	Tracer.debug(getClass().getName(), "", "Il codice di controllo è errato"); 
	        	return "controllo errato"; 
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
		 * Se uno dei parametri passati è null (o l'iban è null) ritorna false
		 * Ha senso solo per IBAN radicati su IT o SM. Negli altri casi il risultato non è affidabile.
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
		 * Se uno dei parametri passati è null (o l'iban è null) ritorna false.
		 * Ha senso solo per IBAN radicati su IT o SM. Negli altri casi il risultato non è affidabile.
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
}
