package it.tasgroup.idp.billerservices.controller.verificapagamento;

import it.tasgroup.idp.bean.IVerificaStatoPagamento;
import it.tasgroup.idp.bean.IVerificaStatoPagamento.VerificaPagamentoModelException;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.esiti.ErroreEsitoPendenza;
import it.tasgroup.idp.notification.builder.BuildStandardNotificaPagamento;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;
import it.tasgroup.idp.util.ErrorDecoder;
import it.tasgroup.idp.xmlbillerservices.header.InboundIdpHeader;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.Esito;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdPagamento;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpEsitoVerifica;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpEsitoVerifica.IdpBodyEsitoVerifica;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.InformazioniPagamentoType;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.VerificaStatoPagamentoDettagliato;
import it.tasgroup.idp.xmlbillerservices.ws.verificapagamento.VerificaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.verificapagamento.VerificaPagamentoResponse;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class VerificaPagamentoControllerImpl implements
		VerificaPagamentoController {

	
	@EJB(beanName = "VerificaStatoPagamento")
	private IVerificaStatoPagamento verificaStatoPagamentoBean;
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	
	@Override
	public VerificaPagamentoResponse verificaPagamento(VerificaPagamento idpVerificaStatoPagamento) {
		
		// --------------------------
		//Log Messaggio di input		
		// --------------------------
				
		logger.info("Inside Verifica Stato Pagamento OTF");

		// --------------------------
		// Recupero dati di input
		// --------------------------
		InboundIdpHeader header = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getIdpHeader();
		String versioneMessaggio = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getVersione();
		String cdEnte = header.getE2E().getSender().getE2ESndrId();
		String sil = header.getE2E().getSender().getE2ESndrSys();

		logger.info("Richiesta proveniente da ente = " + cdEnte);
		logger.info("Richiesta proveniente da sil = " + sil);
		

		// --------------------------
		// Elaborazione
		// --------------------------

		List<IdPagamento>listaIdPagamento = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getIdpBody().getIdPagamento();
		
		Boolean inserisciEsitoEsteso = true; 
		
		inserisciEsitoEsteso = inserisciEsitoEsteso!=null && inserisciEsitoEsteso;
		
		IdpEsitoVerifica esitoV = new IdpEsitoVerifica();
		IdpBodyEsitoVerifica body = new IdpBodyEsitoVerifica();
		List<InformazioniPagamentoType> esitiEstesi = new ArrayList<InformazioniPagamentoType>();

		try {
		
				for (IdPagamento idPaga : listaIdPagamento) {
				
					String tipoPend = idPaga.getTipoDebito();
					String id = idPaga.getValue();		
					logger.info("VERIFICA QUESTO ID = " + id + " ,tipoPend = " + tipoPend);		
					
					VerificaStatoPagamentoInput input = new VerificaStatoPagamentoInput();
					input.setIdPagamento(id);
					input.setTipoPendenza(tipoPend);
					input.setCdEnte(cdEnte);
					input.setSil(sil);
					
					VerificaPagamentoModel pmodel = verificaStatoPagamentoBean.verificaPagamentoModel(input, inserisciEsitoEsteso);
					if (pmodel.isRefreshData()) {
						logger.info("Richiesto aggiornamento stato posizione (in corso) -----------------");
						logger.info("Ricalcolo lo stato  -----------------");
						pmodel = verificaStatoPagamentoBean.verificaPagamentoModel(input, inserisciEsitoEsteso);
					}
					
								
					logger.info("Esito Verifica -----------------");
					logger.info(pmodel.getStatoPagamento());
					logger.info(pmodel.getPagamento());
					logger.info(pmodel.getTipoPendenza());
					logger.info(pmodel.getDescrizioneStato());
					logger.info("Fine Esito Verifica -----------------");
				

					InformazioniPagamentoType  itype =  buildInformazioniPagamentoType(pmodel);				
					esitiEstesi.add(itype);
											
				}
				
				body.getInformazioniPagamento().addAll(esitiEstesi);
						
		} 
		catch (VerificaPagamentoModelException e) {
			
			logger.error("Errore Verifica Stato Pagamento",e);
			
			//decodifico errore con il multilanguage
			ErrorDecoder errorDecoder = new ErrorDecoder();
			ErroriEsitiPendenza esitoInsert = new ErroriEsitiPendenza();
			esitoInsert.setCodice(e.getMessage());
			ErroreEsitoPendenza erroreEP = errorDecoder.decode(esitoInsert);	
			
			// ERRORE
			Esito esito = new Esito();
			esito.setCodice(erroreEP.getCodice());
			esito.setDescrizione(erroreEP.getDescrizione());
			body.setEsito(esito);
			
		}
		
		catch (Exception e ) {
						
			logger.error("Errore Verifica Stato Pagamento",e);
			
			// ERRORE inatteso
			Esito esito = new Esito();
			esito.setCodice("errors.db.transactionRollback");
			esito.setDescrizione(e.getMessage());
											
			body.setEsito(esito);
						
		}
			

		// ----------------------------
		// Costruzione output
		// ----------------------------
		
		esitoV.setIdpBodyEsitoVerifica(body);

		logger.info("Outside Verifica Stato Pagamento OTF");
		
//		//salvo messaggio		
//		if (logger.isInfoEnabled()) {
//			JAXBContext jaxbContext;
//			try {
//				jaxbContext = JAXBContext.newInstance("it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento:it.toscana.rete.cart.servizi.iris_1_1.idpesito:it.toscana.rete.cart.servizi.iris_1_1.idpheader:it.toscana.rete.cart.servizi.iris_1_1.idpinclude");
//				Marshaller marshaller = jaxbContext.createMarshaller();
//				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
//							
//				ByteArrayOutputStream output = new ByteArrayOutputStream();
//						
//				//marshall
//				marshaller.marshal(esitoV, output);
//				String outputXml = new String(output.toByteArray());
//				
//				logger.info("VerificaStato, messaggio inviato = \n " + outputXml);
//				
//			} catch (JAXBException e) {
//				logger.info("VerificaStato, impossibile salvare il messaggio da inviare = \n " + e.getMessage());
//			}		
//		}
		
		VerificaPagamentoResponse esito  = new VerificaPagamentoResponse();
		esito.setIdpEsitoVerifica(esitoV);
		
		return esito;
	}
	
	
	
	/**
	 * Costruzione dell'esito in output
	 * @param model
	 * @return
	 */
	private InformazioniPagamentoType buildInformazioniPagamentoType(VerificaPagamentoModel model) {
		InformazioniPagamentoType  i = new InformazioniPagamentoType();
		i.setStato(VerificaStatoPagamentoDettagliato.valueOf(model.getStatoPagamento().toString()));
		i.setIdPagamento(model.getIdPagamento());
		i.setDescrizioneStato(model.getDescrizioneStato());
		if (i.getDescrizioneStato()==null) {
			i.setDescrizioneStato("-");
		}
		i.setTipoDebito(model.getTipoPendenza());
		
		if (model.getPagamento()!=null) {
			i.setPagamento(new BuildStandardNotificaPagamento().buildPagamento(model.getPagamento()));
		}	
		
		return i;
	}
	
//	/**
//	 * Costruzione del Tag Pagamento
//	 * @param model
//	 * @return
//	 */
//	private Pagamento buildPagamento(PagamentoModelTyped pmodel) {
//		
//		DatatypeFactory df;
//		try {
//			df = DatatypeFactory.newInstance();
//		} catch (DatatypeConfigurationException e) {
//			throw new RuntimeException(e);
//		}
//		GregorianCalendar gc = new GregorianCalendar(); 
//		
//		Pagamento p = new Pagamento();
//		
//		gc.setTime(pmodel.getDataOraPagamento());
//		XMLGregorianCalendar xgc = df.newXMLGregorianCalendar(gc);
//		p.setDataOraPagamento(xgc);
//
//		gc.setTime(pmodel.getDataScadenzaPagamento());
//		xgc = df.newXMLGregorianCalendar(gc);
//		p.setDataScadenzaPagamento(xgc);
//		
//		p.setCausaleDebito(pmodel.getDeCausale());
//		p.setTipoNotifica(TipoNotificaType.valueOf(pmodel.getEsito()));
//		
//		p.setImporto(pmodel.getImporto());
//		RiferimentoSoggetto versante = new RiferimentoSoggetto();
//   		  versante.setTipo(pmodel.getTipoVersante());
//		  versante.setIdFiscale(pmodel.getIdVersante());
//		  versante.setDescrizione(pmodel.getDescrizioneVersante()); //TODO: la mail!!
//		p.setVersante(versante);
//		p.setIdDebito(pmodel.getIdPendenzaEnte());
//		p.setTipoDebito(pmodel.getTipoDebito());
//				
//		RiferimentoSoggetto debitore = new RiferimentoSoggetto();
//			debitore.setIdFiscale(pmodel.getIdPagante());
//
//		p.setDebitore(debitore);
//		
//		p.setIdPagamento(pmodel.getIdPagamento());
//				
//		Transazione transazione = new Transazione();
//		 CanalePagamento canalePagamento = new CanalePagamento(); 
//		 canalePagamento.setDescrizione(pmodel.getDescrizionePsp());
//		 canalePagamento.setIdentificativoCanale(pmodel.getIdCanale());
//		 canalePagamento.setTipoVersamento(pmodel.getTipoVersamento());
//		 canalePagamento.setIdentificativoPSP(pmodel.getIdPsp());
//		 transazione.setCanalePagamento(canalePagamento);
//		 		 
//		 gc.setTime(pmodel.getDataOraTransazione());
//		 xgc = df.newXMLGregorianCalendar(gc);
//		 transazione.setDataOraTransazione(xgc);
//		 
//
//		 transazione.setImportoCommissioni(pmodel.getImportoVoceCommissioni());
//		 transazione.setIdentificativoUnivocoRiscossione(pmodel.getIdTransazione());
//		 transazione.setImporto(pmodel.getImportoTransato());
//		 
//		 
//		p.setTransazione(transazione);
//		p.setIdentificativoUnivocoVersamento(pmodel.getIuv());
//		
//// TODO: Capire se si vuole veramente fare: RiferimentoIncasso riferimentoIncasso = new RiferimentoIncasso();
//				
//		return p;
//	}
	

}
