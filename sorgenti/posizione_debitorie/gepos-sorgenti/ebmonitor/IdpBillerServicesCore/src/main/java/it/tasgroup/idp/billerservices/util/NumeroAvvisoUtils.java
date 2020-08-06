package it.tasgroup.idp.billerservices.util;

import it.tasgroup.idp.domain.enti.TributiEnti;

public class NumeroAvvisoUtils {

	/**
	 * 
	 * @param tributo
	 * @param IUV
	 * @param formatted
	 * @return
	 */	 	
	public static String calculateNumeroAvviso(TributiEnti tributo, String IUV, boolean formatted) {
		String auxDigit = tributo.getNdpAuxDigit();
		String codStazPa = tributo.getNdpCodStazPa();
		String separator = formatted ? " " : "";

		String numeroAvviso = null;
		if ("0".equals(auxDigit)) {
			numeroAvviso = auxDigit + separator + codStazPa + separator + IUV;
		} else  {
			numeroAvviso = auxDigit + separator + IUV;
		}
		return numeroAvviso;
	}
	
}
