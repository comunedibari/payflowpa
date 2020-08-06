/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.client;

import it.veneto.regione.pagamenti.ente.pagamentitelematicidovutipagati.PagamentiTelematiciDovutiPagati;
import it.veneto.regione.pagamenti.ente.pagamentitelematicidovutipagati.PagamentiTelematiciDovutiPagatiService;
import it.regioneveneto.mygov.payment.mypivot.client.utility.*;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;

/**
 * @author Igor Tamiazzo
 * 
 */
public class RichiestaFlussiDovuti {

	private PagamentiTelematiciDovutiPagati port;

	public RichiestaFlussiDovuti(String endpointUrl) {
		PagamentiTelematiciDovutiPagatiService ss = new PagamentiTelematiciDovutiPagatiService();
		port = ss.getPagamentiTelematiciDovutiPagatiPort();
		((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
	}

	public it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlussoRisposta chiediStatoExportFlusso(String codIpaEnte, String password,
			String requestToken) {

		it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT _paaSILChiediStatoExportFlusso_header = new it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT();
		_paaSILChiediStatoExportFlusso_header.setCodIpaEnte(codIpaEnte);

		it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlusso _paaSILChiediStatoExportFlusso_bodyrichiesta = new it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlusso();
		_paaSILChiediStatoExportFlusso_bodyrichiesta.setPassword(password);
		_paaSILChiediStatoExportFlusso_bodyrichiesta.setRequestToken(requestToken);

		return port.paaSILChiediStatoExportFlusso(_paaSILChiediStatoExportFlusso_bodyrichiesta, _paaSILChiediStatoExportFlusso_header);

	}

	public it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoRisposta prenotaExportFlusso(String codIpaEnte, String password, String identificativoTipoDovuto, Date dateFrom, Date dateTo) throws DatatypeConfigurationException {

		it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT _paaSILPrenotaExportFlusso_header = new it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT();
		_paaSILPrenotaExportFlusso_header.setCodIpaEnte(codIpaEnte);
		
		it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlusso _paaSILPrenotaExportFlusso_bodyrichiesta = new it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlusso();
		_paaSILPrenotaExportFlusso_bodyrichiesta.setPassword(password);
		_paaSILPrenotaExportFlusso_bodyrichiesta.setIdentificativoTipoDovuto(identificativoTipoDovuto);		
		_paaSILPrenotaExportFlusso_bodyrichiesta.setDateFrom(Utils.getDateXML(dateFrom));
		_paaSILPrenotaExportFlusso_bodyrichiesta.setDateTo(Utils.getDateXML(dateTo));
		
		return port.paaSILPrenotaExportFlusso(_paaSILPrenotaExportFlusso_bodyrichiesta, _paaSILPrenotaExportFlusso_header);

	}

}
