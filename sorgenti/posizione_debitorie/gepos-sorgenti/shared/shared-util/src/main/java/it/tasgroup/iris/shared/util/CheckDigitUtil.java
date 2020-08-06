package it.tasgroup.iris.shared.util;

import java.math.BigInteger;

public class CheckDigitUtil {
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static boolean checkDigitUtil(String fieldToValidate, int numberCheckDigits, int moduleValue) {
		
		
		 int numberDigitToCheck= fieldToValidate.length()-numberCheckDigits;
		
		 if (fieldToValidate == null) /* avoid NPE */ return false;
		 if (numberDigitToCheck < 1) /* nothing to check */ return false;
		 if (fieldToValidate.length()< numberDigitToCheck) return false;
         StringBuffer buffer = new StringBuffer();
         for (int j=0; j<numberDigitToCheck; j++) {
                    char c = Character.toUpperCase(fieldToValidate.charAt(j));
                    int pos = ALPHABET.indexOf(c);
                    if (pos == -1) /* not found: invalid reference provided */ return false;
                    buffer.append(pos);
         }
         StringBuffer checkD=  new StringBuffer();
         for (int j=numberDigitToCheck; j < fieldToValidate.length(); j++) {
        	 char c = Character.toUpperCase(fieldToValidate.charAt(j));
             int pos = ALPHABET.indexOf(c);
             if (pos == -1) /* not found: invalid reference provided */ return false;
             checkD.append(pos);
         }

         BigInteger bufferToBigInteger = new BigInteger(buffer.toString());
         int remainder = bufferToBigInteger.remainder(BigInteger.valueOf(moduleValue)).intValue();
         int value = moduleValue + 1 - remainder;
         String stringValue=remainder < 10 ? "0"+remainder : String.valueOf(remainder);
         
		return stringValue.equalsIgnoreCase(checkD.toString());
	}

}
