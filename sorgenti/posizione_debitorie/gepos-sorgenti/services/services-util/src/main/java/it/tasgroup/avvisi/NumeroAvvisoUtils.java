package it.tasgroup.avvisi;

public class NumeroAvvisoUtils {

	public static final int NUM_AVVISO_LEN_17 = 17;
	public static final int NUM_AVVISO_LEN_15 = 15;
	
	/**
	 * 
	 * @param tributo
	 * @param IUV
	 * @param formatted
	 * @return
	 */	 	
	public static String calculateNumeroAvviso(String auxDigit, String codStazPa, String iuv, boolean formatted) {

		String separator = formatted ? " " : "";
		
		String numeroAvviso = null;
		if ("0".equals(auxDigit)) {
			numeroAvviso = auxDigit + separator + codStazPa + separator + iuv;
		} else  {
			numeroAvviso = auxDigit + separator + iuv;
		}
		return numeroAvviso;
	}
	

	
}

