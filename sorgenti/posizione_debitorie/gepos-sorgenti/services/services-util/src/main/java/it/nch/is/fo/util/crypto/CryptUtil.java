package it.nch.is.fo.util.crypto;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

public class CryptUtil {

	public static void main(String[] args) {
//		String plainPassword = "12345678";
//		String cryptPassword = encryptFlowPwd(plainPassword);
//		System.out.println("password in chiaro: (" + plainPassword.length() + ") " + plainPassword);
//		System.out.println("password criptata:  (" + cryptPassword.length() + ") " + cryptPassword);
	}

	public static boolean isPwdCryptingEnabled() {
		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("fe-be.properties");
		return cpl.getBooleanProperty("iris.cripted.password.enabled");
	}

//	public static boolean isFlowPwdCryptingEnabled() {
//		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("fe-be.properties");
//		return cpl.getBooleanProperty("iris.cripted.flow.password.enabled");
//	}

	public static String encryptPwd(String plainPassword) {
		// return CriptDes.cifraNativoAsHex(plainPassword, plainPassword, "ISO-8859-1");
		try {
			return CryptSHA1.SHA1(plainPassword);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//	public static String encryptFlowPwd(String plainPassword) {
//		//return CriptDes.cifraNativoAsHex(plainPassword, plainPassword, "ISO-8859-1");
//		return CriptDes.pseudoTrasparenza(elaboraFirma2(plainPassword));
//	}

//	private  static String elaboraFirma2(String firma)	{
////		try	{
//			/* concateno il reverse: */
//			firma += new StringBuffer(firma).reverse().toString();
//			
//			/* trasformo in esadecimale */
//			int hex1, hex2;
//			byte[] buff=new byte[8];
//			for (int i=0; i<16; i+=2)	{
//				hex1 = new Integer(firma.substring(i,i+1)).intValue() * 16;
//				hex2 = new Integer(firma.substring(i+1,i+2)).intValue();
//				buff[i/2]=new Integer(hex1+hex2).byteValue();		
//		 	}
//				
//			/* cifro con des: */
////			System.out.println("DES*** "+Des2.cifraNativo(buff,buff));
////			System.out.println("DES*** "+new String(buff,"ISO-8859-1"));
//			return CriptDes.cifraNativo(buff,buff);	
////		}
////		catch(Exception e)	{
////			throw new FirmaServiceException(e,"SLB_FIRMA_NON_CORRETTA","");	
////		}
//	}

}
