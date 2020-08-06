/**
 * 
 */
package it.tasgroup.iris.servlet.ddp;

import it.nch.fwk.fo.util.Tracer;
import it.tasgroup.iris.dto.ddp.BollettinoFrecciaDTO;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author pazzik
 *
 */
public class BollettinoFrecciaQueryStringBuilder {
	
	private static final String EXOLAB_INCAS_URL_KEY = "iris.bollettino.freccia.url";
	
	private static final String exolab_incas_url = getProperty(EXOLAB_INCAS_URL_KEY);

	
	/**
	 * @param ddp
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String populateQueryStringWithParameters(DocumentoDiPagamentoDTO ddp) throws UnsupportedEncodingException{
		//String queryStringWithParameters= exolab_incas_address+"?csia=59470&iban=IT22T0103002818000008600053&deb=Maria%20Rossini&ind=Via%20Roma%2015&citta=Napoli&cap=00100&prov=RM&idboll=1234567891234567&motivo=seconda%20rata%202012&desc=Pagamento%20IMU&scadenza=01/12/2012&importo=200";
		
		BollettinoFrecciaDTO bfDTO = (BollettinoFrecciaDTO) ddp.getSpecificDetail();
		
		List<CondizioneDDPDTO> carrello = ddp.getCarrello();
		
		CondizioneDDPDTO condizione = carrello.get(0);
		
		StringBuffer buf = new StringBuffer(exolab_incas_url);
		buf.append("?csia="+bfDTO.getCsia());
		buf.append("&iban="+bfDTO.getCoordinateBancarie());
		buf.append("&deb="+encodeURLUTF8(bfDTO.getNomeOrdinante()));
		String indirizzo = bfDTO.getInd();
		
		int minlength = 1;
		
		if (indirizzo==null)
				indirizzo = "";
		else {
			
			int addressLength = indirizzo.length();
			if (addressLength > 0)
				minlength = Math.min(addressLength, 90);
		}
		
		buf.append("&ind="+encodeURLUTF8(indirizzo.substring(0,minlength)));
		buf.append("&citta="+encodeURLUTF8(bfDTO.getCitta()));
		buf.append("&cap="+bfDTO.getCap());
		buf.append("&prov="+bfDTO.getProv());
		buf.append("&idboll="+bfDTO.getId());
		buf.append("&motivo="+encodeURLUTF8(condizione.getTributo()));
		buf.append("&desc="+encodeURLUTF8(condizione.getDescrizioneFreccia()));
		buf.append("&scadenza="+getExolabDateFormat(condizione.getScadenza()));
		
		// FORZO IL LOCALE IT
		// PER USARE LA VIRGOLA COME SEPARATORE DEI DECIMALI
		// COME IMPOSTO DA SPECIFICHE EXOLAB INCAS

		NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN);
		
		String formattedAmount = nf.format(ddp.getImporto().add(ddp.getImportoCommissioni()));

		buf.append("&importo="+ formattedAmount);
		
		
		buf.append("&mail="+bfDTO.getMail());
		
		// SOLO PER FINI DI TEST
		// buf.append("&mail="+"katia.pazzi@tasgroup.it");
		
		return buf.toString();
	}
	
	/**
	 * @param toEncode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeURLUTF8(String toEncode) throws UnsupportedEncodingException{
		
		return toEncode!=null?URLEncoder.encode(toEncode,"UTF-8"):"";
		
	}
	
	/**
	 * Legge le properties da iris-fe.properties
	 *
	 * @param name il nome della property
	 * @return il valore della property di nome name su iris-fe.properties
	 */
	private static String getProperty(String name) {

		ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");

		String value = cpl.getProperty(name);
		
		if (Tracer.isDebugEnabled(BollettinoFrecciaServlet.class.getName()))
			Tracer.debug("LoginAction", "getProperty", name + " = " + value);
		
		return value;
	}
	
	/**
	 * @param date
	 * @return
	 */
	private String getExolabDateFormat(java.util.Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

}
