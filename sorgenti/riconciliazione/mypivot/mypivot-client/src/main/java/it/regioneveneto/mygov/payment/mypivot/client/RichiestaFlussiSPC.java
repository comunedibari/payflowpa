/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.client;

import it.veneto.regione.pagamenti.pa.pagamentitelematiciflussispc.PagamentiTelematiciFlussiSPC;
import it.veneto.regione.pagamenti.pa.pagamentitelematiciflussispc.PagamentiTelematiciFlussiSPCservice;
import it.regioneveneto.mygov.payment.mypivot.client.utility.*;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;

/**
 * @author Igor Tamiazzo
 * 
 */
public class RichiestaFlussiSPC {

	private PagamentiTelematiciFlussiSPC port;

	public RichiestaFlussiSPC(String endpointUrl) {
		PagamentiTelematiciFlussiSPCservice ss = new PagamentiTelematiciFlussiSPCservice();
		port = ss.getPagamentiTelematiciFlussiSPCPort();
		((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}

	/**
	 * @param codIpaEnte
	 * @param password
	 * @param identificativoPsp
	 * @param tipoFlusso
	 * @param dateFrom
	 * @param dateTo
	 * @param fault
	 * @param elencoFlussiSPC
	 * @throws DatatypeConfigurationException
	 */
	public void chiediElencoFlussiSPC(String codIpaEnte, String password, String identificativoPsp, String tipoFlusso, Date dateFrom, Date dateTo,
			javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean> fault,
			javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.TipoElencoFlussiSPC> elencoFlussiSPC) throws DatatypeConfigurationException {

		port.paaSILChiediElencoFlussiSPC(password, codIpaEnte, identificativoPsp, tipoFlusso, Utils.getDateXML(dateFrom), Utils.getDateXML(dateTo), fault,
				elencoFlussiSPC);

	}

	/**
	 * @param password
	 * @param codIpaEnte
	 * @param identificativoPsp
	 * @param tipoFlusso
	 * @param identificativoFlusso
	 * @param dataOraFlusso
	 * @param fault
	 * @param stato
	 * @param downloadUrl
	 * @throws DatatypeConfigurationException
	 */
	public void chiediFlussoSPC(String password, String codIpaEnte, String identificativoPsp, String tipoFlusso, String identificativoFlusso,
			Date dataOraFlusso, javax.xml.ws.Holder<it.veneto.regione.pagamenti.pa.FaultBean> fault, javax.xml.ws.Holder<java.lang.String> stato,
			javax.xml.ws.Holder<java.lang.String> downloadUrl) throws DatatypeConfigurationException {

		port.paaSILChiediFlussoSPC(password, codIpaEnte, identificativoPsp, tipoFlusso, identificativoFlusso, Utils.getDateXML(dataOraFlusso), fault, stato,
				downloadUrl);

	}
}
