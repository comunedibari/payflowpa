/**
 * 
 */
package it.nch.idp.posizionedebitoria;

import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.nch.utility.enumeration.Categoria;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pazzik
 *
 */
public class PosizioneDebitoriaActionHelper {
	
	public static boolean isCittadino(HttpServletRequest request){
		String opr = (String) request.getSession().getAttribute(PosizioneDebitoriaSessionConstant.operatore);
		return Categoria.valueOf(opr).isCittadino();
	}
	
	public static boolean isModalitaPosizioneDebitoria(HttpServletRequest request) {
		String mod = (String) request.getSession().getAttribute(PosizioneDebitoriaSessionConstant.modalitaServizio);

		return (mod == null || mod.equalsIgnoreCase(PosizioneDebitoriaConstants.MODALITA_POSIZIONE_DEBITORIA) ? true : false);
	}

	public static boolean isModalitaPosizioneCreditoria(HttpServletRequest request) {
		String mod = (String) request.getSession().getAttribute(PosizioneDebitoriaSessionConstant.modalitaServizio);

		if (mod == null)
			return false;

		return mod.equalsIgnoreCase(PosizioneDebitoriaConstants.MODALITA_POSIZIONE_CREDITORIA);
	}

	public static boolean isModalitaAmministrazione(HttpServletRequest request) {
		String mod = (String) request.getSession().getAttribute(PosizioneDebitoriaSessionConstant.modalitaServizio);

		if (mod == null)
			return false;

		return mod.equalsIgnoreCase(PosizioneDebitoriaConstants.MODALITA_AMMINISTRAZIONE);
	}

}
