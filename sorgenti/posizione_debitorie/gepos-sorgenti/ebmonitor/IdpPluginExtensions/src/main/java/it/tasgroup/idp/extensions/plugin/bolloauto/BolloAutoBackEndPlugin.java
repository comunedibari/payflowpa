package it.tasgroup.idp.extensions.plugin.bolloauto;

import it.tasgroup.idp.plugin.api.BackEndPluginAdapter;
import it.tasgroup.idp.plugin.api.DettaglioStrutturatoModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BolloAutoBackEndPlugin extends BackEndPluginAdapter {
	
	
	private static final String TIPO_VEICOLO_AUTOVEICOLO = "1";
	private static final String TIPO_VEICOLO_AUTOVEICOLO_STRING = "autoveicolo";
	private static final String TIPO_VEICOLO_MOTOVEICOLO = "2";
	private static final String TIPO_VEICOLO_MOTOVEICOLO_STRING = "motoveicolo";	
	private static final String TIPO_VEICOLO_RIMORCHIO = "3";
	private static final String TIPO_VEICOLO_RIMORCHIO_STRING = "rimorchio";
	private static final String TIPO_VEICOLO = "TIPO_VEICOLO";
	private static final String TARGA = "TARGA";
	private static final String DECORRENZA = "DECORRENZA";
	private static final String MESIVALIDITA = "MESIVALIDITA";
	private static final String PAGFRAZIONATO = "PAG_FRAZIONATO";
	
	
	
	@Override
	public String validaCausale(HashMap<String, String> causaleStrutturata) {
		
		String targa=causaleStrutturata.get(TARGA);
		if (targa==null) {
			return "Causale errata: manca il campo"+ TARGA;
		}
		if (targa.length()>20) {
			return "Causale errata: il campo "+ TARGA +" eccede la massima dimensione prevista";
		}
		
		Pattern whitespace = Pattern.compile("\u0020");
		Matcher matcher = whitespace.matcher(targa);
//		String targaSlim = "";
//		if (matcher.find()) {   
//			targaSlim = matcher.replaceAll("");
//		} 		
		if (matcher.find()) {
			return "Causale errata: il campo "+ TARGA +" non puo' contenere caratteri di tipo spazio";
		}
		
		String tipoVeicolo=causaleStrutturata.get(TIPO_VEICOLO);
		if (tipoVeicolo==null) {
			return "Causale errata: manca il campo "+ TIPO_VEICOLO;
		}
		//if (tipoVeicolo.length()>1) {
		//	return "Causale errata: il campo "+ TIPO_VEICOLO +" eccede la massima dimensione prevista";
		//}

		
		if (!(TIPO_VEICOLO_AUTOVEICOLO.equals(tipoVeicolo) || TIPO_VEICOLO_MOTOVEICOLO.equals(tipoVeicolo) || TIPO_VEICOLO_RIMORCHIO.equals(tipoVeicolo) ||
				TIPO_VEICOLO_AUTOVEICOLO_STRING.equalsIgnoreCase(tipoVeicolo) || TIPO_VEICOLO_MOTOVEICOLO_STRING.equalsIgnoreCase(tipoVeicolo) || TIPO_VEICOLO_RIMORCHIO_STRING.equalsIgnoreCase(tipoVeicolo)	
			)) {
			return "Causale errata: "+TIPO_VEICOLO +" deve valere '"+TIPO_VEICOLO_AUTOVEICOLO+"' o 'autoveicolo' per autoveicolo, '"+TIPO_VEICOLO_MOTOVEICOLO+"' o 'motoveicolo' per motoveicolo, '"+TIPO_VEICOLO_RIMORCHIO+"' o 'rimorchio' per rimorchio";
		}
		
		//**** controlli aggiuntivi per la validazione dei campi facoltativi DECORRENZA e MESIVALIDITA
		String mesiValidita = causaleStrutturata.get(MESIVALIDITA);
		try {
			if (mesiValidita!=null && Integer.parseInt(mesiValidita) <= 0 && Integer.parseInt(mesiValidita) >= 13 ) {
				return "Causale errata: "+MESIVALIDITA +" deve essere compreso tra 1 e 12";
			}			
		} catch (NumberFormatException nfe) {
			return "Causale errata: "+MESIVALIDITA +" deve essere un intero";
		}
		
		String decorrenza = causaleStrutturata.get(DECORRENZA);	
		if (decorrenza != null) {
			SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
			try {
			  df.parse(decorrenza);
			} catch (ParseException p) {
				return "Causale errata: "+DECORRENZA +" deve essere nel formato 'MM/yyyy' ";
			}
			
		}
		
		// Se arrivo qui è andato tutto bene e ritorno null.
		return null;
	}

	@Override
	public DettaglioStrutturatoModel getDettaglioStrutturato(
			HashMap<String, String>  causaleStrutturata) {
		
		String targa=causaleStrutturata.get(TARGA);
		String tipoVeicolo=encodeTipoVeicolo(causaleStrutturata.get(TIPO_VEICOLO));
		String decorrenza = causaleStrutturata.get(DECORRENZA);	 
		String mesiValidita = causaleStrutturata.get(MESIVALIDITA);
		String pagFrazionato = causaleStrutturata.get(PAGFRAZIONATO) == null || "".equals(causaleStrutturata.get(PAGFRAZIONATO)) ? "N" : causaleStrutturata.get(PAGFRAZIONATO);

		BolloAutoModel bm = new BolloAutoModel();
		bm.setTarga(targa);
		bm.setTipoVeicolo(tipoVeicolo);
		bm.setPagFrazionato(pagFrazionato);
		
		if (mesiValidita!=null) {
			bm.setIntervalloValidita(Integer.parseInt(mesiValidita));
		} else {
			bm.setIntervalloValidita(12);
		}
		if (decorrenza!=null) {
			SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
			try {
			  bm.setDataDecorrenza(df.parse(decorrenza));
			} catch (ParseException p) {}
		}
		return bm;
	}
	
	
	private String encodeTipoVeicolo(String tipoVeicolo) {
		if (TIPO_VEICOLO_AUTOVEICOLO.equals(tipoVeicolo) || TIPO_VEICOLO_MOTOVEICOLO.equals(tipoVeicolo) || TIPO_VEICOLO_RIMORCHIO.equals(tipoVeicolo)) {
			return tipoVeicolo;
		} else if (TIPO_VEICOLO_AUTOVEICOLO_STRING.equalsIgnoreCase(tipoVeicolo)) {
			return TIPO_VEICOLO_AUTOVEICOLO;
		} else if (TIPO_VEICOLO_MOTOVEICOLO_STRING.equalsIgnoreCase(tipoVeicolo)) {
			return TIPO_VEICOLO_MOTOVEICOLO;
		} else if (TIPO_VEICOLO_RIMORCHIO_STRING.equalsIgnoreCase(tipoVeicolo)) {
			return TIPO_VEICOLO_RIMORCHIO;
		} else {
			return tipoVeicolo;  // non deve mai capitare.
		}
	}
}
