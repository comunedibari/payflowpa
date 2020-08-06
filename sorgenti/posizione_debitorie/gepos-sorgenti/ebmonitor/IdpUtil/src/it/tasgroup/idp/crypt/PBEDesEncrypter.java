package it.tasgroup.idp.crypt;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class PBEDesEncrypter {

	Cipher ecipher;
	Cipher dcipher;

	// 8-byte Salt
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3,
			(byte) 0x03 };
	
	final byte[] NOLINESEPARATOR = {  };

	// Iteration count
	int iterationCount = 19;

	public PBEDesEncrypter(String passPhrase) throws Exception {
		// Create the key
		PBEKeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		//SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA1AndDESede");
		SecretKey key = factory.generateSecret(keySpec);

		// Prepare the parameter to the ciphers
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

		ecipher = Cipher.getInstance(factory.getAlgorithm());
		dcipher = Cipher.getInstance(factory.getAlgorithm());
		// Create the ciphers
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	}

	public String encrypt(String str) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");
		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);
		// Encode bytes to base64 to get a string
		return new String(new Base64(128,NOLINESEPARATOR,true).encodeAsString(enc));  //UrlSafe, no newlines
	}

	public String decrypt(String str) throws Exception {
		// Decode base64 to get bytes
		byte[] dec = new Base64(true).decode(str.getBytes());   //Decrypt non sa 
		// Decrypt
		byte[] utf8 = dcipher.doFinal(dec);
		// Decode using utf-8
		return new String(utf8, "UTF8");
	}

	public static void main(String args[]) {
		try {
			PBEDesEncrypter encrypter = new PBEDesEncrypter("password");
			
			String stringToEncrypt = "pegi";

			String encrypted = encrypter.encrypt(stringToEncrypt);
			System.out.println("Encrypted: " + encrypted);
			String decrypted = encrypter.decrypt(encrypted);
			System.out.println("Decrypted: " + decrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
