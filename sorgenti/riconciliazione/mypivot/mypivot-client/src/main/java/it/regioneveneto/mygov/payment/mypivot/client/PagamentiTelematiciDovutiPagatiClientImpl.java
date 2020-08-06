package it.regioneveneto.mygov.payment.mypivot.client;

import java.util.List;

import javax.activation.DataHandler;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.veneto.regione.pagamenti.ente.FaultBean;
import it.veneto.regione.pagamenti.ente.PaaSILAutorizzaImportFlusso;
import it.veneto.regione.pagamenti.ente.PaaSILAutorizzaImportFlussoRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILAvvisoPendente;
import it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlusso;
import it.veneto.regione.pagamenti.ente.PaaSILChiediStatoExportFlussoRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILChiediStatoImportFlusso;
import it.veneto.regione.pagamenti.ente.PaaSILChiediStatoImportFlussoRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILImportaDovuto;
import it.veneto.regione.pagamenti.ente.PaaSILImportaDovutoRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILInviaDovuti;
import it.veneto.regione.pagamenti.ente.PaaSILInviaDovutiRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlusso;
import it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoIncrementaleConRicevuta;
import it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILPrenotaExportFlussoRisposta;
import it.veneto.regione.pagamenti.ente.PaaSILVerificaAvviso;
import it.veneto.regione.pagamenti.ente.PaaSILVerificaAvvisoRisposta;
import it.veneto.regione.pagamenti.ente.pagamentitelematicidovutipagati.PagamentiTelematiciDovutiPagati;
import it.veneto.regione.pagamenti.ente.pagamentitelematicidovutipagati.PagamentiTelematiciDovutiPagatiService;
import it.veneto.regione.pagamenti.ente.ppthead.IntestazionePPT;

public class PagamentiTelematiciDovutiPagatiClientImpl implements PagamentiTelematiciDovutiPagatiClient {

	private static final Log log = LogFactory.getLog(PagamentiTelematiciDovutiPagatiClient.class);
	private String pagamentiTelematiciDovutiPagatiEndPointUrl;

	public void setPagamentiTelematiciDovutiPagatiEndPointUrl(String pagamentiTelematiciDovutiPagatiEndPointUrl) {
		this.pagamentiTelematiciDovutiPagatiEndPointUrl = pagamentiTelematiciDovutiPagatiEndPointUrl;
	}

	@Override
	public PaaSILChiediStatoExportFlussoRisposta paaSILChiediStatoExportFlusso(
			PaaSILChiediStatoExportFlusso bodyrichiesta, IntestazionePPT header) {
		throw new RuntimeException("method not implemented");

	}

	@Override
	public PaaSILImportaDovutoRisposta paaSILImportaDovuto(PaaSILImportaDovuto bodyrichiesta, IntestazionePPT header) {
		
		PagamentiTelematiciDovutiPagatiService ss = null;
		try {
			ss = new PagamentiTelematiciDovutiPagatiService();
		} catch (Exception murle) {
			log.error("Failed to initialize PA client", murle);
			throw new RuntimeException("Failed to initialize PA client", murle);
		}
		PagamentiTelematiciDovutiPagati port = ss.getPagamentiTelematiciDovutiPagatiPort();
		((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,	this.pagamentiTelematiciDovutiPagatiEndPointUrl);
		PaaSILImportaDovutoRisposta paaSILImportaDovutoRisposta = null;
		try {
			paaSILImportaDovutoRisposta = port.paaSILImportaDovuto(bodyrichiesta, header);
		} catch (Exception ex) {
			log.error("Error invoking PA ", ex);
			throw new RuntimeException("Error invoking PA ", ex);
		}
		return paaSILImportaDovutoRisposta;
	}

	@Override
	public PaaSILAutorizzaImportFlussoRisposta paaSILAutorizzaImportFlusso(PaaSILAutorizzaImportFlusso bodyrichiesta,
			IntestazionePPT header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaaSILPrenotaExportFlussoRisposta paaSILPrenotaExportFlusso(PaaSILPrenotaExportFlusso bodyrichiesta,
			IntestazionePPT header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaaSILVerificaAvvisoRisposta paaSILVerificaAvviso(PaaSILVerificaAvviso bodyrichiesta,
			IntestazionePPT header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paaSILChiediAvvisiPendenti(String password, String codIpaEnte, XMLGregorianCalendar dateFrom,
			XMLGregorianCalendar dateTo, Holder<FaultBean> fault,
			Holder<List<PaaSILAvvisoPendente>> paaSILAvvisoPendente) {
		// TODO Auto-generated method stub

	}

	@Override
	public PaaSILInviaDovutiRisposta paaSILInviaDovuti(PaaSILInviaDovuti bodyrichiesta, IntestazionePPT header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta paaSILPrenotaExportFlussoIncrementaleConRicevuta(
			PaaSILPrenotaExportFlussoIncrementaleConRicevuta bodyrichiesta, IntestazionePPT header) {

		PagamentiTelematiciDovutiPagatiService ss = null;
		try {
			ss = new PagamentiTelematiciDovutiPagatiService();
		} catch (Exception murle) {
			log.error("Failed to initialize PA client", murle);
			throw new RuntimeException("Failed to initialize PA client", murle);
		}
		PagamentiTelematiciDovutiPagati port = ss.getPagamentiTelematiciDovutiPagatiPort();
		((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,	this.pagamentiTelematiciDovutiPagatiEndPointUrl);
		PaaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta paaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta = null;

		try {
			paaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta = port.paaSILPrenotaExportFlussoIncrementaleConRicevuta(bodyrichiesta, header);
		} catch (Exception ex) {
			log.error("Error invoking PA ", ex);
			throw new RuntimeException("Error invoking PA ", ex);
		}
		return paaSILPrenotaExportFlussoIncrementaleConRicevutaRisposta;
	}

	@Override
	public void paaSILChiediPagatiConRicevuta(String codIpaEnte, String password, String idSession,
			Holder<FaultBean> fault, Holder<DataHandler> pagati, Holder<String> tipoFirma, Holder<DataHandler> rt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paaSILChiediPagati(String codIpaEnte, String password, String idSession, Holder<FaultBean> fault,
			Holder<DataHandler> pagati) {
		// TODO Auto-generated method stub

	}

	@Override
	public PaaSILChiediStatoImportFlussoRisposta paaSILChiediStatoImportFlusso(
			PaaSILChiediStatoImportFlusso bodyrichiesta, IntestazionePPT header) {
		// TODO Auto-generated method stub
		return null;
	}

}
