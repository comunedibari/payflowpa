package it.tasgroup.iris.paymentws.ccp.impl;


import gov.telematici.pagamenti.ws.PaaAttivaRPT;
import gov.telematici.pagamenti.ws.PaaAttivaRPTRisposta;
import gov.telematici.pagamenti.ws.PaaVerificaRPT;
import gov.telematici.pagamenti.ws.PaaVerificaRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematiciccp.PagamentiTelematiciCCP;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneLocal;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeLocal;
import it.tasgroup.iris.paymentws.dto.builder.GiornaleEventiExtDtoBuilder;
import it.tasgroup.iris.paymentws.dto.builder.PSPDTOBuilder;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebService(serviceName = "PagamentiTelematiciCCPservice", endpointInterface = "it.gov.spcoop.nodopagamentispc.servizi.pagamentitelematiciccp.PagamentiTelematiciCCP", targetNamespace = "http://NodoPagamentiSPC.spcoop.gov.it/servizi/PagamentiTelematiciCCP")
public class PagamentiTelematiciCCPImpl implements PagamentiTelematiciCCP {
	
	@EJB(name = "AutorizzazioneBusiness")
	private AutorizzazioneLocal autorizzazioneBean;
	
	@EJB(name = "GiornaleEventiBusiness")
	private GiornaleEventiBusinessLocal giornaleEventiBean;
	
	@EJB(name = "DistintePagamentoFacade")
	private DistintePagamentoFacadeLocal distPagFacade;
	
	private static final Logger LOG = LogManager.getLogger(PagamentiTelematiciCCPImpl.class.getName());
		
	public PaaAttivaRPTRisposta paaAttivaRPT(PaaAttivaRPT bodyrichiesta, IntestazionePPT header) {
		
		LOG.info("PagamentiTelematiciCCPImpl - paaAttivaRPT, richiesta: "+ bodyrichiesta);
		
		if (LOG.isInfoEnabled()) {
			String x = getStringValue(bodyrichiesta);
			LOG.info("*****paaAttivaRPT body ****\n "+ x + "\n*********************");
			x = getStringValue(header);
			LOG.info("*****paaAttivaRPT header ****\n "+ x + "\n*********************");
		}
		
		GiornaleEventiExtDTO g = GiornaleEventiExtDtoBuilder.createGiornaleEvDTO(bodyrichiesta, header);
		
		giornaleEventiBean.save(g);
			
		RichiestaAutorizzazioneDTO autorizzazioneDto = PSPDTOBuilder.fillRichiestaAutorizzazioneDTO(header, bodyrichiesta);

		EsitoOperazionePagamentoDTO esitoOpDto = autorizzazioneBean.attivaPagamentoPSP(autorizzazioneDto);
		
		PaaAttivaRPTRisposta datiRisposta = PSPDTOBuilder.fillPaaAttivaRPTRisposta(esitoOpDto);
		
		header = PSPDTOBuilder.fillTestataRispostaDTO(header);
		
		g = GiornaleEventiExtDtoBuilder.createGiornaleEvDTO(datiRisposta, g);
		
		giornaleEventiBean.save(g);
		
		// **** BEGIN GESTIONE INVIO ASINCRONO nodoInviaRPT *****************
		if (esitoOpDto != null && esitoOpDto.getIdFlusso() != null) {
			final DistintaPosizioneDebitoriaDettaglioVO dettDistinta = distPagFacade.getDistintaById(esitoOpDto.getIdFlusso());
			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("gateway-ws-client.properties");
			String delayStr = props.getProperty("iris.gateway.updatepaymentstatus.delay");
			final long delay = Long.parseLong(delayStr);
			final Timer timer = new Timer();
			if (dettDistinta != null
					&& dettDistinta.getStato().equals("IN CORSO")) {
				final String idDistinta = esitoOpDto.getIdFlusso()+"";
				timer.schedule(new TimerTask() {
					@Override
					public void run() { 
						try { 
							ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("gateway-ws-client.properties");
							String urlBase = props.getProperty("iris.gateway.updatepaymentstatus.url");
							String urlUpdatePaymentStatus = urlBase + "?idDistinta=" + idDistinta;
							String updatePaymentStatusResponse = sendGet(urlUpdatePaymentStatus);
							LOG.debug("Result = "+ updatePaymentStatusResponse);
						} catch (Throwable e) {
							LOG.error("ERRORE NEL PROCESSO ASINCRONO", e);
						} finally {
							timer.cancel();
						}
					}
					private String sendGet(String urlToCall) throws Exception {
						LOG.debug("Sending 'GET' request to URL : " + urlToCall);
						URL urlObj = new URL(urlToCall);
						HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
						int responseCode = con.getResponseCode();
						LOG.debug("Response Code : " + responseCode);
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
						LOG.debug("Response:");
						LOG.debug(response.toString());
						return response.toString();
				}
				}, delay);  //300 millisecondi 
			}
		}
		// ***** END GESTIONE INVIO ASINCRONO nodoInviaRPT *****************
		
		if (LOG.isInfoEnabled()) {
			String x = getStringValue(datiRisposta);
			LOG.info("*****paaAttivaRPT****\n "+ x + "\n*********************");
		}
		LOG.info("PagamentiTelematiciCCPImpl - paaAttivaRPT - END ");	
		return datiRisposta;
			
			
	}

	public PaaVerificaRPTRisposta paaVerificaRPT(PaaVerificaRPT bodyrichiesta, IntestazionePPT header) {
		
		LOG.info("PagamentiTelematiciCCPImpl - paaVerificaRPT, richiesta: "+ bodyrichiesta);

		if (LOG.isInfoEnabled()) {
			String x = getStringValue(bodyrichiesta);
			LOG.info("*****paaVerificaRPT body ****\n "+ x + "\n*********************");
			x = getStringValue(header);
			LOG.info("*****paaVerificaRPT header ****\n "+ x + "\n*********************");
		}
		
		GiornaleEventiExtDTO g = GiornaleEventiExtDtoBuilder.createGiornaleEvDTO(bodyrichiesta, header);
		
		giornaleEventiBean.save(g);
		
		RichiestaAutorizzazioneDTO autorizzazioneDto = PSPDTOBuilder.fillRichiestaAutorizzazioneDTO(header, bodyrichiesta);

		EsitoOperazionePagamentoDTO esitoOpDto = autorizzazioneBean.verificaPagamentoPSP(autorizzazioneDto);
		
		PaaVerificaRPTRisposta datiRisposta = PSPDTOBuilder.fillPaaVerificaRPTRisposta(esitoOpDto);
		
		header = PSPDTOBuilder.fillTestataRispostaDTO(header);
		
		LOG.info("PagamentiTelematiciCCPImpl - paaVerificaRPT, risposta: "+ datiRisposta);
		
		g = GiornaleEventiExtDtoBuilder.createGiornaleEvDTO(datiRisposta, g);
		
		giornaleEventiBean.save(g);
		
		if (LOG.isInfoEnabled()) {
			String x = getStringValue(datiRisposta);
			LOG.info("*****paaVerificaRPT****\n "+ x + "\n*********************");
		}
		return datiRisposta;
			
			
	}
	
	private String getStringValue(Object o) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			JAXBContext jaxbContext = null;
			Marshaller jaxbMarshaller = null;//jaxbContext.createMarshaller();
			if (o instanceof PaaAttivaRPT) {
				jaxbContext = JAXBContext.newInstance( PaaAttivaRPT.class );
				jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			    jaxbMarshaller.marshal(new JAXBElement<PaaAttivaRPT>(new QName("uri","local"), PaaAttivaRPT.class, (PaaAttivaRPT)o), os);
			}else if (o instanceof IntestazionePPT) {
				jaxbContext = JAXBContext.newInstance( IntestazionePPT.class );
				jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			    jaxbMarshaller.marshal(new JAXBElement<IntestazionePPT>(new QName("uri","local"), IntestazionePPT.class, (IntestazionePPT)o), os);
			}else 
				if (o instanceof PaaAttivaRPTRisposta){
					jaxbContext = JAXBContext.newInstance( PaaAttivaRPTRisposta.class );
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			        jaxbMarshaller.marshal(new JAXBElement<PaaAttivaRPTRisposta>(new QName("uri","local"), PaaAttivaRPTRisposta.class, (PaaAttivaRPTRisposta)o), os);
				} else if (o instanceof PaaVerificaRPT){
					jaxbContext = JAXBContext.newInstance( PaaVerificaRPT.class );
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			        jaxbMarshaller.marshal(new JAXBElement<PaaVerificaRPT>(new QName("uri","local"), PaaVerificaRPT.class, (PaaVerificaRPT)o), os);
				} else if (o instanceof PaaVerificaRPTRisposta){
					jaxbContext = JAXBContext.newInstance( PaaVerificaRPTRisposta.class );
					jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			        jaxbMarshaller.marshal(new JAXBElement<PaaVerificaRPTRisposta>(new QName("uri","local"), PaaVerificaRPTRisposta.class, (PaaVerificaRPTRisposta)o), os);
				}
			
		   return os.toString();
		} catch (Throwable t) {
			return null;
		}
	}
}