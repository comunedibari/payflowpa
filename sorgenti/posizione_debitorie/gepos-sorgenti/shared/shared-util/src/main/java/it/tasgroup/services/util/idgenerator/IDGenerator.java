package it.tasgroup.services.util.idgenerator;

import org.apache.commons.lang.StringUtils;


/**
 * Classe che genera gli ID.
 * 
 * @author PazziK
 */
public final class IDGenerator {
	
	private static volatile long counter = 0;
	
	private static final int minTrustedDigits = 14;
	
	public static int TRANSACTION_ID_LENGTH = 15;
	
	/**
	 * Genera un ID univoco di lunghezza fissa il cui numero di cifre � impostato dal parametro di ingresso desiredLength (per cui sono ammessi solo i valori 14, 15 e 16).
	 * Non aggiunge il CRC.
	 * 
	 * Se si sceglie di generare un ID a 16 cifre � garantita l'univocit� per 317 anni, con 15 cifre � garantita l'univocit� per 31 anni, 
	 * mentre se si sceglie un ID a 14 cifre il codice generato sar� univoco solo per 3 anni.
	 * 
	 * @param desiredLength numero di cifre dell'ID univoco da generare (non comprende il CRC)
	 * @return un ID univoco di lunghezza fissa il cui numero di cifre � impostato dal parametro di ingresso desiredLength senza CRC
	 */
	public static final synchronized String generateID(int desiredLength)	{	
		
		if (desiredLength < minTrustedDigits)
			throw new IllegalStateException("impossibile garantire univocita' per ID con meno di "+minTrustedDigits+" cifre");
		
		String currentTime = String.valueOf(System.currentTimeMillis());
		
		String key = currentTime.substring(2 + minTrustedDigits - desiredLength) + fillLeftWithDigits(3, counter++ % 1000,'0');
		
		return  key; 		 
		
	}	
	
	public static String Generate_TRANSACTION_ID(){
		
		String key = IDGenerator.generateID(TRANSACTION_ID_LENGTH);
		
		String keyWithCRC = key + IDGenerator.calculateCRC(key);
		
		return keyWithCRC;
	}
	
	public static String Generate_PAYMENT_ID(){
		
		String key = IDGenerator.generateID(minTrustedDigits);
		
		String keyWithCRC = key + IDGenerator.calculateCRC(key);
		
		return keyWithCRC;
	}
	
	public static String fillLeftWithDigits(long desideredLength, long initialValue, char filler){
		//System.out.println(counter);
		String valueString = String.valueOf(initialValue);
		
		StringBuffer sb = new StringBuffer();
		
		for (int i=1; sb.length()+valueString.length()<desideredLength; i++)
			sb.append(filler);
		
		sb.append(valueString);
		//System.out.println(sb.toString());
		return sb.toString();		
	}
	
	/**
	 * Calcola il CRC di una stringa di sole cifre in base all'algoritmo Eu-Pay.
	 * 
	 * Secondo le specifiche Eu-Pay, il check-digit di una stringa costituita di sole cifre,
	 * si calcola sommando le cifre pari (pos. 2,4,6,… SP), sommando le cifre dispari (pos 1,3,5,… SD), calcolando S = SP + SD * 3. 
	 * Il CRC è il più piccolo numero che sommato a S consente di ottenere un numero S1 multiplo di 10 (S1 MOD 10 = 0).
	 *
	 * @param digitsToCheck la stringa di sole cifre di cui calcolare il check digit secondo l'algoritmo Eu-Pay.
	 * @return il Check Digit della stringa di sole cifre ricevuta in ingresso.
	 */
	public static String calculateCRC(String digitsToCheck) {
		
		int sumOdd = 0;
		
		int sumEven = 0;
		
		for(int i = digitsToCheck.length()-1; i >= 0; i--){
			
			char c = digitsToCheck.charAt(i);
			
			Integer num = Integer.parseInt(Character.toString(c));
			
			if(isOdd(digitsToCheck.length() - i))
				sumOdd += num;
			else 
				sumEven += num;
		}
		
		int sumTot = sumEven + (sumOdd * 3);
		
		int crcInt;
		
		for(crcInt = 0; ((sumTot + crcInt) % 10) != 0; crcInt++);
		
		return String.valueOf(crcInt);
	}
	
	private static boolean isOdd(int i) {
		
		return (i % 2) != 0;
		
	}
	
	private static int calculateCode128CCheckSum(String digitsToCheck){
		
		int numDigits = digitsToCheck.length();
		
		int sum = 105;
		
		int cursorPosition = 0;
		
		for (int i=0; cursorPosition < numDigits; i++){
			
			System.out.println(digitsToCheck.substring(cursorPosition,cursorPosition+2));	
			
			int c = Integer.parseInt(digitsToCheck.substring(cursorPosition,cursorPosition+2));
			
			System.out.println("C = " + c + " I = "+(i+1));
			
			sum+=c*(i+1);
			
			System.out.println("SUM = " + sum);
			
			cursorPosition += 2;
			
		}
		System.out.println("REMAINDER = " + sum%103);
		
		return sum % 103;
		
	}
	
	private static int calculateCode128ABCheckSum(String digitsToCheck){
		
		int numDigits = digitsToCheck.length();
		
		int sum = 105;
		
		for (int i=0; i < numDigits; i++){
			
			System.out.println(digitsToCheck.substring(i,i+1));	
			
			int c = Integer.parseInt(digitsToCheck.substring(i,i+1));
			
			System.out.println("C = " + c + " I = "+(i+1));
			
			sum+=c*(i+1);
			
			System.out.println("SUM = " + sum);
			
		}
		
		System.out.println("REMAINDER = " + sum%103);
		
		return sum % 103;
		
	}
	
	public static void main(String[] args){
		
		System.out.println(Generate_PAYMENT_ID());
		
		System.out.println(Generate_TRANSACTION_ID());
		
	}

	public static boolean isValidCRC(String id) {
		
		int idlength = id.length();
		
		if (StringUtils.isEmpty(id))
			return false;
		
		String crc = id.substring(idlength-1, idlength);
		
		String idCRCLess = id.substring(0, idlength-1);
		
		String calculatedCRC = calculateCRC(idCRCLess);
		
		return calculatedCRC.equals(crc);
	
	}
	
	public static void checkCRC(String id) {
		
		if (!IDGenerator.isValidCRC(id))
			throw new  IllegalStateException("Bad access");
		
	}
	
	public static void checkCRC(String[] ddpIds) throws SecurityException {
		
		for(String id :ddpIds)
			checkCRC(id);
		
	}
}
