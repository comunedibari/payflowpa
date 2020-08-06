package it.nch.is.fo.util.plugin;

import static it.tasgroup.iris.domain.helper.PersistenceUtils.materialize;
import it.nch.idp.posizionedebitoria.constants.PosizioneDebitoriaConstants;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnReceiptHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;

import java.util.Locale;
import java.util.Map;

public class PluginReceiptUtil {

	public static boolean exists_plugin(String cdPlugin){
		return AddOnManagerFactory.exists(cdPlugin);
	}
	
	public static <T extends TributoStrutturato> Map<String, String> getDetails( T tributo, String idEnte, String cdTrbEnte, Locale locale) {
		if (tributo != null) {
			AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, tributo.getTipoTributo()); 
			AddOnReceiptHelper<T> receiptHelper = manager.getReceiptHelper(); 
			Map<String, String> receiptDetailsMap = receiptHelper.extractReceiptDetails(materialize(tributo), locale); 
			return receiptDetailsMap;
		}
		return null;
	}
	
	
	public static String getCausaleFormatted(String codTributo, String causale){

		String causaleFormatted = "";
		String tipo = "";
		String targa= "";

		if (codTributo.equals(PosizioneDebitoriaConstants.COD_TRIBUTO_BOLLO_AUTO)) {

			String[] causalesplit = causale.split(";");

			for (String string : causalesplit) {
				if (string.contains("TIPO")) {
					tipo = string.split("=")[1].equals("1")? "Autoveicolo targa: ": string.split("=")[1]+ " targa: ";
				}
				if (string.contains("TARGA")) {
					targa = string.split("=")[1];
				}

			}
			causaleFormatted += tipo + targa;
			
		} else if (codTributo.equals(PosizioneDebitoriaConstants.COD_TRIBUTO_MULTA)) {
			
			String[] causalesplit = causale.split(";");

			for (String string : causalesplit) {
				if (string.contains("Targa")) {
					causaleFormatted += string + "\n";
				} else if (string.contains("Data Verbale")) {
					causaleFormatted += "N." + string + "\n";
				} else if (string.contains("Verbale")) {
					causaleFormatted += string + "\n";
				}

			}
		} else {
			causaleFormatted = causale;
		}
		
		return causaleFormatted;
	}
	
//	public static <T extends TributoStrutturato> String getCausale(String properties, String codTributo){
//		if(codTributo != null && !codTributo.equals("")){
//			AddOnManager<T> manager = AddOnManagerFactory.getAddOnManager(codTributo); 
//			AddOnReceiptHelper<T> receiptHelper = manager.getReceiptHelper();
//			String viewCausale = receiptHelper.getCausale(properties);
//			return viewCausale;
//		}
//		return null;
//	}


}
