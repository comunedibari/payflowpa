package it.nch.is.fo.util;

public class EsaConverter {

	public static String toHexString(String string) {
		return string != null ? toHexString(string.getBytes()) : null;
	}
	
	public static String toHexString(byte[] bytes) {
		String content = "";
		for (int i = 0; i < bytes.length; i++) {
			int bt = bytes[i];
			if (bt < 0)
				bt = bt + 256;
			String tmp = Integer.toHexString(bt);
			if (tmp.length() == 1)
				content = content + "0";
			content = content + tmp;
		}
		return content.toUpperCase();
	}

	public static String toHexStringWithPatch(byte[] bytes) {
		String content = "";
		for (int i = 0; i < bytes.length; i++) {
			int bt = bytes[i];
			if (bt < 0)
				bt = bt + 256;
			String tmp = Integer.toHexString(bt);
			if (tmp.length() == 1)
				content = content + "0";
			int comparedWith80 = tmp.compareTo("80");
			int comparedWith9F = tmp.compareTo("9F");
			if (comparedWith80 >= 0 && comparedWith9F <= 0 ) {
				tmp = "3F";
			}
			content = content + tmp;
		}
		return content.toUpperCase();
	}
	/**
	 * Trasfroma la stringa in input (supposta composta da coppie di caratteri
	 * esadecimali) in una nuova stringa in cui tutto i caratteri compresi 
	 * da 80 a 9F vengono sostituiti da 3F
	 * 
	 * @param fromHex
	 * @return
	 */
	public static String hexTohexWithPatch(String fromHex) {
		String toHex = "";
		
		String[] pieces = hexStringToStringArray(fromHex);
		for (int i = 0; i < pieces.length; i++) {
			String piece = pieces[i];
			
			if (between(piece, "80", "9F")) {
				toHex += "3F";
			} else {
				toHex += piece;
			}
		}		
		
		return toHex;
	}
	
	/**
	 * Confronto byte a byte le due stringhe in input, che si assumono della 
	 * stessa lunghezza ed espresse in formato esadecimale.
	 * Nel caso il byte corrente (dall'una o dall'altra parte) sia compreso
	 * nell'intervallo [80, 9F] il confronto viene saltato (cioè come se fosse OK), 
	 * negli altri casi eseguo il confronto normale tra stringhe.
	 * Al primo confronto fallito esco con false.
	 * 
	 * @param hex1
	 * @param hex2
	 * @param ignoreExtraBytes 
	 * @return
	 */
	public static boolean compareHexToHexWithPatch(String hex1, String hex2, boolean ignoreExtraBytes) {
	
		String[] bytes1 = hexStringToStringArray(hex1);
		String[] bytes2 = hexStringToStringArray(hex2);
		
		int numBytes;
		if (bytes1.length != bytes2.length) {
			//
			//	Le due stringhe esadecimali hanno diversa lunghezza: che fare?
			//
			if (!ignoreExtraBytes) {
				//
				//	Se ignoreExtraBytes == false, il confronto deve essere 'stretto' e quindi fallisce
				//
				return false;
			} else {
				//
				//	Se ignoreExtraBytes == true, faccio il confronto solo sui primi byte
				//
				numBytes = Math.min(bytes1.length, bytes2.length);
			}
		} else {
			//
			//	Le due stringhe esadecimali hanno la stessa lunghezza: è un buon inizio!
			//
			numBytes = bytes1.length;
		}
		
		for (int i = 0; i < numBytes; i++) {
			String byte1 = bytes1[i];
			String byte2 = bytes2[i];
			
			boolean ignoreByte1 = between(byte1, "80", "9F");
			boolean ignoreByte2 = between(byte2, "80", "9F");
			
			if (ignoreByte1) {
				System.out.println("Ignoring " + byte1 + " from 1st string");
			}
			if (ignoreByte2) {
				System.out.println("Ignoring " + byte2 + " from 2nd string");
			}
			
			if (ignoreByte1 || ignoreByte2) {
				continue;
			} else {
				if (!byte1.equalsIgnoreCase(byte2)) {
					return false;
				}
			}
		}		
		
		return true;
	}
	
	public static boolean between(String piece, String fromByte, String toByte) {
		int comparedWithFrom = piece.compareTo(fromByte);
		int comparedWithTo = piece.compareTo(toByte);
		if (comparedWithFrom >= 0 && comparedWithTo <= 0 ) {
			return true;
		} else {
			return false;
		}
	}

	public static String hexStringToString(String hex) {
		return hex != null && hex.length() > 0 ? new String(hexStringToByteArray(hex)) : null;
	}

	public static byte[] hexStringToByteArray(String hex) {
		if (hex == null || hex.length() == 0)
			return null;
		try {
			int sz = hex.length() / 2;
			byte[] retValue = new byte[sz];
			int index;
			String byteString;
			for (int n = 0; n < sz; n++) {
				index = n * 2;
				byteString = hex.substring(index, index + 2).toLowerCase();
				retValue[n] = (byte) Integer.parseInt(byteString, 16);
			}
			return retValue;
		} catch (Exception e) {
			throw new RuntimeException("Error converting hexString to byte array", e);
		}
	}
	
	public static String[] hexStringToStringArray(String hex) {
		if (hex == null || hex.length() == 0)
			return null;
		try {
			int sz = hex.length() / 2;
			String[] retValue = new String[sz];
			int index;
			String byteString;
			for (int n = 0; n < sz; n++) {
				index = n * 2;
				byteString = hex.substring(index, index + 2).toUpperCase();
				retValue[n] = byteString;
			}
			return retValue;
		} catch (Exception e) {
			throw new RuntimeException("Error converting hexString to String array", e);
		}
	}
	
	public static void main (String[] args) {
		String source = null;
		//source = "123€";
		//source = "'^abc$%&/";
		source = "v£úø’Õ!ê";
		source = toHexString(source);
		String target = hexTohexWithPatch(source);
		
		System.out.println(source);
		System.out.println(target);
	}

}
