package it.tasgroup.idp.extensions.plugin.multa;

import it.tasgroup.idp.plugin.api.BackEndPluginAdapter;
import it.tasgroup.idp.plugin.api.DettaglioStrutturatoModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultaBackEndPlugin extends BackEndPluginAdapter {

	private static final String DATA_VERBALE_PATTERN = "dd-MM-yyyy";
	private static final String SERIE_VERBALE = "SERIE_VERBALE";
	private static final String DATA_VERBALE = "DATA_VERBALE";
	private static final String NUMERO_VERBALE = "NUMERO_VERBALE";
	private static final String TARGA_VEICOLO = "TARGA_VEICOLO";

	@Override
	public String validaCausale(HashMap<String, String> causaleStrutturata) {
		
		//TODO: controllare le lunghezze.
		   
		String targa=causaleStrutturata.get(TARGA_VEICOLO);
		if (targa==null) {
			return "Causale errata: manca il campo "+TARGA_VEICOLO;
		}
		if (targa.length()>20) {
			return "Causale errata: il campo "+ TARGA_VEICOLO +" eccede la massima dimensione prevista";
		}
		
		//controlo presenza spazi
		Pattern whitespace = Pattern.compile("\u0020");
		Matcher matcher = whitespace.matcher(targa);
		if (matcher.find()) {
			return "Causale errata: il campo "+ TARGA_VEICOLO +" non puo' contenere caratteri di tipo spazio";
		}		
		

		
		String numeroVerbale=causaleStrutturata.get(NUMERO_VERBALE);
		if (numeroVerbale==null) {
			return "Causale errata: manca il campo "+NUMERO_VERBALE;
		}
		if (numeroVerbale.length()>20) {
			return "Causale errata: il campo "+ NUMERO_VERBALE +" eccede la massima dimensione prevista";
		}
		//controlo presenza spazi
		Matcher matcher2 = whitespace.matcher(numeroVerbale);
		if (matcher2.find()) {
			return "Causale errata: il campo "+ NUMERO_VERBALE +" non puo' contenere caratteri di tipo spazio";
		}				

		
		String dataVerbale=causaleStrutturata.get(DATA_VERBALE);
		if (dataVerbale==null) {
			return "Causale errata: manca il campo "+DATA_VERBALE;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_VERBALE_PATTERN);
			try {
				Date d = sdf.parse(dataVerbale);
			} catch (ParseException e) {
				return "Causale errata: il campo "+DATA_VERBALE+" deve essere nel formato '"+DATA_VERBALE_PATTERN+"'";
			}
			
		}
		
		String serieVerbale = causaleStrutturata.get(SERIE_VERBALE);
		if (serieVerbale!=null && serieVerbale.length()>2) {
			return "Causale errata: il campo "+ SERIE_VERBALE +" eccede la massima dimensione prevista";
		}
		//controlo presenza spazi (da eseguire solo se esiste il campo, dato che opzionale
		if (serieVerbale!=null) {	
			Matcher matcher3 = whitespace.matcher(serieVerbale);
			if (matcher3.find()) {
				return "Causale errata: il campo "+ SERIE_VERBALE +" non puo' contenere caratteri di tipo spazio";
			}		
		}
		
		
				
		// Se arrivo qui è andato tutto bene e ritorno null.
		return null;
	}

	@Override
	public DettaglioStrutturatoModel getDettaglioStrutturato(
			HashMap<String, String>  causaleStrutturata) {
		
		String targa=causaleStrutturata.get(TARGA_VEICOLO);		
		String numeroVerbale=causaleStrutturata.get(NUMERO_VERBALE);
		String dataVerbale=causaleStrutturata.get(DATA_VERBALE);
		SimpleDateFormat sdf = new SimpleDateFormat(DATA_VERBALE_PATTERN);
		
		Date d = null;
		try {
				d = sdf.parse(dataVerbale);
		} catch (ParseException e) {
			throw new RuntimeException("Non dovrebbe mai succedere.. se la pendenza ha passato la validazione prima..",e); //Se sei qui... allora sei del gatto.
		}
		
		String serieVerbale = causaleStrutturata.get(SERIE_VERBALE);
		
		MultaModel bm = new MultaModel();
		bm.setTarga(targa);
		bm.setNumeroVerbale(numeroVerbale);
		Timestamp ts = new Timestamp(d.getTime());
		bm.setDataVerbale(ts);
		bm.setSerieVerbale(serieVerbale);
		return bm;
	}

}
