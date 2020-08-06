/**
 *
 */
package it.regioneveneto.mygov.payment.mypivot.client;

import it.veneto.regione.pagamenti.RichiestaFlussiDovuti;
import it.veneto.regione.pagamenti.RichiestaFlussiSPC;
import it.veneto.regione.pagamenti.pa.TipoIdSPC;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author lavoro
 *
 */
public class TakeData {


	public static void main(String[] args) throws URISyntaxException {


		try {
			RichiestaFlussiDovuti richiestaFlussiDovuti = new RichiestaFlussiDovuti("http://paygov.collaudo.regione.veneto.it/pa/services/PagamentiTelematiciFlussiSPC");

			it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlussoRisposta risposta = richiestaFlussiDovuti.chiediStatoExportFlusso("", "", "");
			risposta.getDownloadUrl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		List<TipoIdSPC> lista = chiediElencoFlussiSPC();
//		chiediFlussoSPC(lista);
//
//		String codIpaEnte = "C_D530";
//		String password = "1234";
//		String identificativoPsp = "BCITITMM";
//		String tipoFlusso = "R";
//
//		String identificativoFlusso = "TR0001_20140407-12:41:27.0717-4DC1";
//
//		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");
//		ft.parse("2014-04-07T12:41:27");
//		Date dataOraFlusso = ft.getCalendar().getTime();
//
//		javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean> fault = new javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean>();
//		javax.xml.ws.Holder<java.lang.String> stato = new javax.xml.ws.Holder<java.lang.String>();
//		javax.xml.ws.Holder<java.lang.String> downloadUrl = new javax.xml.ws.Holder<java.lang.String>();
//
//		String endpointUrl = "http://paygov.collaudo.regione.veneto.it/pa/services/PagamentiTelematiciFlussiSPC";
//		RichiestaFlussiSPC richiestaFlussiSPC = new RichiestaFlussiSPC(endpointUrl);
//		richiestaFlussiSPC.chiediFlussoSPC(password, codIpaEnte, identificativoPsp, tipoFlusso, identificativoFlusso, dataOraFlusso, fault, stato, downloadUrl);
//
//		System.out.println(
//				stato.value
//				);
//
//		System.out.println(
//				downloadUrl.value
//				);
//		FetchFile a;
//		try {
//			a = new FetchFile("https://paygov.collaudo.regione.veneto.it/mybox/rest/download.html?authorizationToken=f7a0ac2a-4bd2-4845-b643-4d852b85744b&fileName=BCITITMM_TR0001_20140407-12:41:27.0717-4DC1_20140407124127.xml", "d:/prova.xml");
//
//			a.retriveFile();
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		 String data = "https://paygov.collaudo.regione.veneto.it/mybox/rest/download.html?authorizationToken=1e604259-1ea7-4e2d-adc8-cc9026074742&fileName=BCITITMM_TR0001_20140407-12:41:27.0717-4DC1_20140407124127.xml";
//		  System.out.println(new File(new URI(data).getRawPath()).getName());
//		  getQueryMap(new URI(data).getQuery());
	}

	public static Map<String, String> getQueryMap(String query)
	{
	    String[] params = query.split("&");
	    Map<String, String> map = new HashMap<String, String>();
	    for (String param : params)
	    {
	        String name = param.split("=")[0];
	        String value = param.split("=")[1];
	        System.out.println("NAME: " + name + " VALUE:" + value);
	        map.put(name, value);
	    }
	    return map;
	}

	public static void chiediFlussoSPC(List<TipoIdSPC> lista) {

		String codIpaEnte = "C_D530";
		String password = "1234";
		String identificativoPsp = "BCITITMM";
		String tipoFlusso = "R";

		String identificativoFlusso = "TR0001_20140407-12:41:27.0717-4DC1";

		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'hh:mm:ss");
		try {
			ft.parse("2014-04-07T12:41:27");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date dataOraFlusso = ft.getCalendar().getTime();

//		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
//		Date dateFrom = ft.parse("2015-01-01");
//		Date dateTo = ft.parse("2015-01-02");

		String endpointUrl = "http://paygov.collaudo.regione.veneto.it/pa/services/PagamentiTelematiciFlussiSPC";
		//String endpointUrl = "http://paygov.collaudo.regione.veneto.it/pa/services/PagamentiTelematiciDovutiPagati";

//        javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean> fault = new javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean>();
//        javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.TipoElencoFlussiSPC> elencoFlussiSPC = new javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.TipoElencoFlussiSPC>();

//        RichiestaFlussiDovuti richiestaFlussiDovuti = new RichiestaFlussiDovuti(endpointUrl);
		RichiestaFlussiSPC richiestaFlussiSPC = new RichiestaFlussiSPC(endpointUrl);

//		it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoRisposta d = richiestaFlussiDovuti.

		javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean> fault = new javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean>();

		javax.xml.ws.Holder<java.lang.String> stato = new javax.xml.ws.Holder<java.lang.String>();

		javax.xml.ws.Holder<java.lang.String> downloadUrl = new javax.xml.ws.Holder<java.lang.String>();

		for (TipoIdSPC tipoIdSPC : lista) {
			try {
				Date data = tipoIdSPC.getDataOraFlusso().toGregorianCalendar().getTime();

				GregorianCalendar datOraFlussoXml = new GregorianCalendar();
				datOraFlussoXml.setTime(data);

				XMLGregorianCalendar a = DatatypeFactory.newInstance().newXMLGregorianCalendar(datOraFlussoXml);
				a.setFractionalSecond(null);
				a.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

//				richiestaFlussiSPC.chiediFlussoSPC1(password, codIpaEnte, identificativoPsp, tipoFlusso, tipoIdSPC.getIdentificativoFlusso(), a, fault, stato, downloadUrl);

				System.out.println(
						stato.value
						);

				System.out.println(
						downloadUrl.value
						);

			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}







	}

	public static List<TipoIdSPC> chiediElencoFlussiSPC() {

		String codIpaEnte = "C_D530";
		String password = "1234";
		String identificativoPsp = "BCITITMM";
		String tipoFlusso = "R";



		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		Date dateFrom = new Date();
		try {
			dateFrom = ft.parse("2014-01-01");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date dateTo = new Date();
		try {
			dateTo = ft.parse("2015-01-02");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String endpointUrl = "http://paygov.collaudo.regione.veneto.it/pa/services/PagamentiTelematiciFlussiSPC";

		RichiestaFlussiSPC richiestaFlussiSPC = new RichiestaFlussiSPC(endpointUrl);



		javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean> fault = new javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean>();
        javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.TipoElencoFlussiSPC> elencoFlussiSPC = new javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.TipoElencoFlussiSPC>();

		javax.xml.ws.Holder<java.lang.String> stato = new javax.xml.ws.Holder<java.lang.String>();

		javax.xml.ws.Holder<java.lang.String> downloadUrl = new javax.xml.ws.Holder<java.lang.String>();

		try {
			richiestaFlussiSPC.chiediElencoFlussiSPC(codIpaEnte, password, identificativoPsp, tipoFlusso, dateFrom, dateTo, fault, elencoFlussiSPC);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (TipoIdSPC tipoIdSPC : elencoFlussiSPC.value.getIdSPC()) {
			System.out.println(
					tipoIdSPC.getIdentificativoFlusso()
					);

			System.out.println(
					tipoIdSPC.getDataOraFlusso()
					);
		}

		return elencoFlussiSPC.value.getIdSPC();



	}


}
