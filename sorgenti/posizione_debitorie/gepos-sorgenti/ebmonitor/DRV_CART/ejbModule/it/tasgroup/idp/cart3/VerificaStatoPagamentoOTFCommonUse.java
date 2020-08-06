package it.tasgroup.idp.cart3;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.IVerificaStatoPagamento.VerificaPagamentoModelException;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.esiti.ErroreEsitoPendenza;
import it.tasgroup.idp.notifiche.PagamentoModelTyped;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;
import it.tasgroup.idp.util.ErrorDecoder;
import it.tasgroup.idp.util.JAXBContextProvider;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpEsitoVerifica;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InformazioniPagamentoType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoPagamentoType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.VerificaStatoPagamentoDettagliato;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.ContentType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.MIMETypeCode;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.VerificaStatoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.CanalePagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.DettaglioImportoTransato;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.IdPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.MezzoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.Pagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.Pagante;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.RiferimentoDebito;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.RiferimentoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.TipoNotifica;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.TipoVoce;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.Transazione;
import it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento.Voce;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamenti;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamentiEsito;


public class VerificaStatoPagamentoOTFCommonUse extends CommonOTFWs {
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	

	public IdpVerificaStatoPagamentiEsito idpVerificaStatoPagamenti(IdpVerificaStatoPagamenti idpVerificaStatoPagamento) {

		// --------------------------
		//Log Messaggio di input		
		// --------------------------
		//moved inside handlers
				
		logger.info("Inside Verifica Stato Pagamento OTF");

		// --------------------------
		// Recupero dati di input
		// --------------------------
		IdpHeader header = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getIdpHeader();
		String versioneMessaggio = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getVersione();
		String cdEnte = header.getE2E().getSender().getE2ESndrId();
		String sil = header.getE2E().getSender().getE2ESndrSys();

		logger.info("Richiesta proveniente da ente = " + cdEnte);
		logger.info("Richiesta proveniente da sil = " + sil);
		

		// --------------------------
		// Elaborazione
		// --------------------------

		List<IdPagamento>listaIdPagamento = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getIdpBody().getIdPagamento();
		
		Boolean inserisciEsitoEsteso = idpVerificaStatoPagamento.getIdpVerificaStatoPagamento().getIdpBody().isRichiestaInformazioniPagamento(); 
		
		inserisciEsitoEsteso = inserisciEsitoEsteso!=null && inserisciEsitoEsteso;
		
		
		
		IdpEsitoVerifica esitoV = new IdpEsitoVerifica();
		IdpEsitoVerifica.IdpBody body = new IdpEsitoVerifica.IdpBody();
		List<InformazioniPagamentoType> esitiEstesi = new ArrayList<InformazioniPagamentoType>();
		List<StatoPagamentoType> esitiSintetici = new ArrayList<StatoPagamentoType>();

		try {
		
				for (IdPagamento idPaga : listaIdPagamento) {
				
					String tipoPend = idPaga.getTipoPendenza();
					String id = idPaga.getValue();		
					logger.info("VERIFICA QUESTO ID = " + id + " ,tipoPend = " + tipoPend);		
					
					VerificaStatoPagamentoInput input = new VerificaStatoPagamentoInput();
					input.setIdPagamento(id);
					input.setTipoPendenza(tipoPend);
					input.setCdEnte(cdEnte);
					input.setSil(sil);
					
					VerificaPagamentoModel pmodel = getVerificaStatoPagamentoBean().verificaPagamentoModel(input, inserisciEsitoEsteso);
					if (pmodel.isRefreshData()) {
						logger.info("Richiesto aggiornamento stato posizione (in corso) -----------------");
						logger.info("Ricalcolo lo stato  -----------------");
						pmodel = getVerificaStatoPagamentoBean().verificaPagamentoModel(input, inserisciEsitoEsteso);
					}
					
								
					logger.info("Esito Verifica -----------------");
					logger.info(pmodel.getStatoPagamento());
					logger.info(pmodel.getPagamento());
					logger.info(pmodel.getTipoPendenza());
					logger.info(pmodel.getDescrizioneStato());
					logger.info("Fine Esito Verifica -----------------");
				
					if (inserisciEsitoEsteso) {
						InformazioniPagamentoType  itype =  buildInformazioniPagamentoType(pmodel);				//qui inserisco il valore dell'allegato
						esitiEstesi.add(itype);
					} else {
						StatoPagamentoType spType = buildStatoPagamentoType(pmodel);
						esitiSintetici.add(spType);
					}
											
				}
				
				if (inserisciEsitoEsteso) {
					body.getInformazioniPagamento().addAll(esitiEstesi);
				} else {
					body.getStatoPagamento().addAll(esitiSintetici);
				}					
						
		} 
		catch (VerificaPagamentoModelException e) {
			
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
						
			// ERRORE inatteso
			Esito esito = new Esito();
			esito.setCodice("errors.db.transactionRollback");
			esito.setDescrizione(e.getMessage());
											
			body.setEsito(esito);
						
		}
			

		// ----------------------------
		// Costruzione output
		// ----------------------------
		
		esitoV.setIdpBody(body);
		esitoV.setIdpHeader(header);

		// inverto mittente con ricevente
		buildHeaderForResponse(header);

		logger.info("esito inviato all ente = " + header.getE2E().getReceiver().getE2ERcvrId());
		logger.info("esito inviato al sil = " + header.getE2E().getReceiver().getE2ERcvrSys());
		esitoV.setIdpHeader(header);
		esitoV.setVersione(versioneMessaggio);

		logger.info("Outside Verifica Stato Pagamento OTF");
		
		//salvo messaggio		
		if (logger.isInfoEnabled()) {
			JAXBContext jaxbContext;
			try {
				// jaxbContext = JAXBContext.newInstance("it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento:it.toscana.rete.cart.servizi.iris_1_1.idpesito:it.toscana.rete.cart.servizi.iris_1_1.idpheader:it.toscana.rete.cart.servizi.iris_1_1.idpinclude");
				jaxbContext = JAXBContextProvider.getInstance()
					.getJAXBContext (
						new String [] {
							  "it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento"
							, "it.toscana.rete.cart.servizi.iris_1_1.idpesito"
							, "it.toscana.rete.cart.servizi.iris_1_1.idpheader"
							, "it.toscana.rete.cart.servizi.iris_1_1.idpinclude"
						}
					);

				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
							
				ByteArrayOutputStream output = new ByteArrayOutputStream();
						
				//marshall
				marshaller.marshal(esitoV, output);
				String outputXml = new String(output.toByteArray());
				
				logger.info("VerificaStato, messaggio inviato = \n " + outputXml);
				
			} catch (JAXBException e) {
				logger.info("VerificaStato, impossibile salvare il messaggio da inviare = \n " + e.getMessage());
			}		
		}
		
		IdpVerificaStatoPagamentiEsito esito  = new IdpVerificaStatoPagamentiEsito();
		esito.setIdpEsitoVerifica(esitoV);
		
		return esito;		
	  }
	
	
	/**
	 * Costruzione dell'esito sintetico
	 * @param model
	 * @return
	 */
	private StatoPagamentoType buildStatoPagamentoType(VerificaPagamentoModel model) {
		StatoPagamentoType spType = new StatoPagamentoType();
		spType.setIdPagamento(model.getIdPagamento());
		spType.setTipoPendenza(model.getTipoPendenza());
			
		switch (model.getStatoPagamento()) {
			case POSIZIONE_PAGATA:
				spType.setValue(VerificaStatoPagamento.PAGAMENTO_ESEGUITO);
				break;
			case POSIZIONE_NON_PAGATA:
				spType.setValue(VerificaStatoPagamento.PAGAMENTO_NON_ESEGUITO);
				break;
			case POSIZIONE_NON_PRESENTE:
				spType.setValue(VerificaStatoPagamento.POSIZIONE_NON_PRESENTE);
				break;				
			case POSIZIONE_NON_PAGABILE:
				spType.setValue(VerificaStatoPagamento.POSIZIONE_NON_PAGABILE);
				break;				
			case POSIZIONE_CON_PAG_IN_CORSO:
				spType.setValue(VerificaStatoPagamento.POSIZIONE_NON_PRESENTE);
				break;							
			case POSIZIONE_CON_DOC_EMESSO:
				spType.setValue(VerificaStatoPagamento.POSIZIONE_NON_PRESENTE);
				break;				
			case POSIZIONE_PAGATA_SBF:
				spType.setValue(VerificaStatoPagamento.POSIZIONE_NON_PRESENTE);
				break;				
							
		}
		
		return spType;
	}
	
	/**
	 * Costruzione dell'esito dettagliato
	 * @param model
	 * @return
	 */
	private InformazioniPagamentoType buildInformazioniPagamentoType(VerificaPagamentoModel model) {
		InformazioniPagamentoType  i = new InformazioniPagamentoType();
		i.setStato(VerificaStatoPagamentoDettagliato.valueOf(model.getStatoPagamento().toString()));
		i.setIdPagamento(model.getIdPagamento());
		i.setDescrizioneStato(model.getDescrizioneStato());
		i.setTipoPendenza(model.getTipoPendenza());
		
	
		if (model.getPagamento()!=null) {
			i.setPagamento(buildPagamento(model.getPagamento()));
		}	
		
		return i;
	}
	
	/**
	 * 
	 * @param pmodel
	 * @return
	 */
	private Pagamento buildPagamento(PagamentoModelTyped pmodel) {
		
		DatatypeFactory df;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		GregorianCalendar gc = new GregorianCalendar(); 
		//XMLGregorianCalendar xgc = df.newXMLGregorianCalendar(gc);
		
		
		Pagamento p = new Pagamento();
		
		p.setAllegato(pmodel.getAllegato());
		
		gc.setTime(pmodel.getDataOraPagamento());
		XMLGregorianCalendar xgc = df.newXMLGregorianCalendar(gc);
		p.setDataOraPagamento(xgc);

		gc.setTime(pmodel.getDataScadenzaPagamento());
		xgc = df.newXMLGregorianCalendar(gc);
		p.setDataScadenzaPagamento(xgc);
		
		p.setDescrizioneCausale(pmodel.getDeCausale());
		p.setEsito(TipoNotifica.valueOf(pmodel.getEsito()));
		//p.setFlagQuietanzaCartacea(value);
		//p.setIdentificativoUnivocoVersamento(value);
		
		p.setImporto(pmodel.getImporto());
		//p.setNote(value);
		
		Pagante pagante = new Pagante();
		pagante.setTipo(pmodel.getTipoVersante());
		pagante.setIdPagante(pmodel.getIdVersante());
		pagante.setDescrizione(pmodel.getDescrizioneVersante());
		p.setPagante(pagante);
		
		RiferimentoDebito riferimentoDebito = new RiferimentoDebito();
		riferimentoDebito.setPendenza(pmodel.getIdPendenzaEnte());
		riferimentoDebito.setTipoDebito(pmodel.getTipoDebito());
		p.setRiferimentoDebito(riferimentoDebito);
		
		p.setRiferimentoDebitore(pmodel.getIdPagante());
		
		RiferimentoPagamento riferimentoPagamento = new RiferimentoPagamento();
		riferimentoPagamento.setIdPagamento(pmodel.getIdPagamento());
		riferimentoPagamento.setTipoPagamento("S".equals(pmodel.getTipoPagamento()) ? TipoPagamento.PAGAMENTO_UNICO : TipoPagamento.PAGAMENTO_A_RATE);
		p.setRiferimentoPagamento(riferimentoPagamento);
		
		Transazione transazione = new Transazione();
		 CanalePagamento canalePagamento = new CanalePagamento(); 
		 canalePagamento.setDescrizione(pmodel.getDescrizioneCanalePagamento());
		 canalePagamento.setTipo(pmodel.getTipoCanalePagamento());
		 transazione.setCanalePagamento(canalePagamento);
		 
		 //transazione.setCodiceAutorizzazione(value);
		 gc.setTime(pmodel.getDataOraAutorizzazione());
		 xgc = df.newXMLGregorianCalendar(gc);
		 transazione.setDataOraAutorizzazione(xgc);
		 
		 gc.setTime(pmodel.getDataOraTransazione());
		 xgc = df.newXMLGregorianCalendar(gc);
		 transazione.setDataOraTransazione(xgc);
		 
		 //TODO #2653
		 transazione.setCodiceAutorizzazione(pmodel.getCodiceAutorizzazione());
		 
		 //transazione.setDescrizione(value);
		 //transazione.setDettaglioCanalePagamento(value);
		 
		 DettaglioImportoTransato  dettaglioImportoTransato  = new DettaglioImportoTransato();
		 List<Voce> voci = dettaglioImportoTransato.getVoce();
		 	Voce v = new Voce();
		 	v.setDescrizione(TipoVoce.IMPORTO_AUTORIZZATO.toString());
		 	v.setImporto(pmodel.getImportoVoce());
		 	v.setTipo(TipoVoce.IMPORTO_AUTORIZZATO);
		 	voci.add(v);
		 
		 	Voce v1 =  new Voce();
		 	v1.setDescrizione(TipoVoce.IMPORTO_COMMISSIONI.toString());
		 	v1.setImporto(pmodel.getImportoVoceCommissioni());
		 	v1.setTipo(TipoVoce.IMPORTO_COMMISSIONI);
		 	voci.add(v1);
		
		 transazione.setDettaglioImportoTransato(dettaglioImportoTransato);
		 transazione.setIdTransazione(pmodel.getIdTransazione());
		 transazione.setImportoTransato(pmodel.getImportoTransato());
		 
		 MezzoPagamento mezzoPagamento = new MezzoPagamento();
		 //mezzoPagamento.setDescrizione(pmodel.getMezzoPagamento());
		 mezzoPagamento.setTipo(pmodel.getMezzoPagamento());
		 transazione.setMezzoPagamento(mezzoPagamento);
		 
		 //transazione.setTipoSicurezza(value);
		
		p.setTransazione(transazione);
		p.setIdentificativoUnivocoVersamento(pmodel.getIuv());
		
		return p;
	}

}
