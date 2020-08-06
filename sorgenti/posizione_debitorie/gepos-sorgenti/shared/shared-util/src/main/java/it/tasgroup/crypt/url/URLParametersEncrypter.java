package it.tasgroup.crypt.url;

import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

public class URLParametersEncrypter {
	
	private static final String _KEY = "2003061110092010"; //TODO: spostare in una property
	
	private PBEDesEncrypter encrypter;
	
	private String separator;
	

	public URLParametersEncrypter() throws Exception {
		encrypter = new PBEDesEncrypter(_KEY);
		this.separator=";";		
	}

	public URLParametersEncrypter(String separator) throws Exception {
		encrypter = new PBEDesEncrypter(_KEY);
		this.separator=separator;
	}

	
	public String cryptParameters(List<String> parameters) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String param:parameters)
			sb.append(param).append(separator);
		
		String controlCode = calculateControlRedundancyCode(sb.toString());
		sb.append(controlCode);
		//System.out.println("sb="+sb.toString());
		
		String encrypted = encrypter.encrypt(sb.toString());
		return encrypted;
		//return URLEncoder.encode(encrypted,"UTF-8");
		
	}
	
	public List<String> decryptParameters(String encrypted) throws Exception {
		String decrypted = encrypter.decrypt(encrypted);
		// CRC Check
		String crcString = decrypted.substring(decrypted.lastIndexOf(separator)+1);
		decrypted=decrypted.substring(0,decrypted.lastIndexOf(separator)+1);
		
//		System.out.println("crc="+crcString);
//		System.out.println("decrypted="+decrypted);

		String referenceControlCode=calculateControlRedundancyCode(decrypted);
		
		if (!crcString.equals(referenceControlCode))
			throw new Exception("Not a valid encrypted string");
		
		
		return Arrays.asList(decrypted.split(separator));
	}
	
	/**
	 * Calcola un carattere di controllo
	 * per la stringa dei parametri. Usa un CRC32 e lo codifica in base 32
	 * @param aString
	 * @return
	 */
	public static String calculateControlRedundancyCode(String aString) throws Exception {
		CRC32 crc = new CRC32();
		crc.update(aString.getBytes("UTF-8"));
		String controlCode=Long.toString(crc.getValue(),32);
		return controlCode;
	}
	
}
