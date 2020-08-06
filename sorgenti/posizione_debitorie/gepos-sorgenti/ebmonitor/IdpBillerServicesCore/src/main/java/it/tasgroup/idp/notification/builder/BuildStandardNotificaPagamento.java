package it.tasgroup.idp.notification.builder;

import it.tasgroup.idp.bean.AbstractBuildNotification;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModelTyped;
import it.tasgroup.idp.notifiche.PagamentoModelTypedBuilder;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.idp.xmlbillerservices.header.E2EReceiver;
import it.tasgroup.idp.xmlbillerservices.header.OutboundHeaderE2E;
import it.tasgroup.idp.xmlbillerservices.header.OutboundHeaderTRT;
import it.tasgroup.idp.xmlbillerservices.header.OutboundIdpHeader;
import it.tasgroup.idp.xmlbillerservices.header.TRTReceiver;
import it.tasgroup.idp.xmlbillerservices.header.TRTSender;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.CanalePagamento;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpBody;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.IdpInformativaPagamento;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.Pagamento;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.RiferimentoIncasso;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.RiferimentoSoggetto;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.TipoNotificaType;
import it.tasgroup.idp.xmlbillerservices.informativapagamento.Transazione;
import it.tasgroup.iris2.pagamenti.Pagamenti;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.HeaderTRT;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.management.RuntimeErrorException;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildStandardNotificaPagamento extends AbstractBuildNotification {

	
	private final Log logger = LogFactory.getLog(this.getClass());

	@Override
	protected PagamentoModel creoPagamentoModel(
			NotifichePagamenti notPagamento, String statoNotificaPagamento,
			EntityManager em) {

		Pagamenti pagamento = em.find(Pagamenti.class, Long.parseLong(notPagamento.getIdPagamento()));
		if (pagamento==null) {
			throw new RuntimeErrorException(null,"Pagamento da notificare non trovato. Id_pagamento="+notPagamento.getIdPagamento());
		}

		PagamentoModelTyped pmt = PagamentoModelTypedBuilder.buildPagamentoModel(pagamento, em, notPagamento.getIdNotifica(),notPagamento.getStatoPagamento());
		pmt.setEsito(statoNotificaPagamento);
		
		
		PagamentoModel pagModel = new PagamentoModel();
		pagModel.setPagamentoModelTyped(pmt);
	
		//chiave, mi serve per update dello stato su notPagamenti
		pagModel.setIdNotifica(pmt.getIdNotifica());
		pagModel.setIdPagamento(pmt.getIdPagamento());
		Calendar cl = new GregorianCalendar(TimeZone.getTimeZone("Europe/Rome"));
		String timeIso = "+01:00";
		if (cl.get(Calendar.DST_OFFSET)/(60*60*1000)==1) {
			//ora legale attiva
			timeIso = "+02:00";
		} 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"+timeIso);
		pagModel.setDataOraPagamento(format.format(pmt.getDataOraPagamento()));
		pagModel.setDataScadenzaPagamento(format.format(pmt.getDataScadenzaPagamento()));
		pagModel.setImporto(pmt.getImporto().toString());
		pagModel.setEsito(pmt.getEsito());
		pagModel.setIdPagante(pmt.getIdPagante());
		pagModel.setTipoCanalePagamento(pmt.getTipoCanalePagamento());
		pagModel.setMezzoPagamento(pmt.getMezzoPagamento());
		pagModel.setIdTransazione(pmt.getIdTransazione());
		// versione corrente riga notifiche_pagamenti
		pagModel.setVersionNotifichePagamento(notPagamento.getVersion());

		pagModel.setDataOraTransazione(format.format(pmt.getDataOraTransazione()));
		
		pagModel.setDataOraAutorizzazione(format.format(pmt.getDataOraAutorizzazione()));
		pagModel.setImportoTransato(pmt.getImportoTransato().toString());
		if (!StatoEnum.NOTIFICHE_INCASSO.equalsIgnoreCase(statoNotificaPagamento)) {
		  pagModel.setImportoVoce(pmt.getImportoVoce().toString());
		} else {
			pagModel.setImportoVoce(null); // serve per eliminare il dettaglio pagamento nella notifica
		}
		pagModel.setImportoVoceCommissioni(pmt.getImportoVoceCommissioni().toString());
	
		pagModel.setTipoDebito(pmt.getTipoDebito());
		pagModel.setIdPendenza(pmt.getIdPendenza());
		pagModel.setIdPendenzaEnte(pmt.getIdPendenzaEnte());
		pagModel.setDeCausale(fixupWithEscapes(pmt.getDeCausale()));
		pagModel.setFlagCausale(true );
		
		pagModel.setIdPagante(pmt.getIdPagante());
		pagModel.setIdVersante(pmt.getIdVersante());
		pagModel.setDescrizioneVersante(pmt.getDescrizioneVersante());
		 // inserimento dati rendicontazione in caso di notifica ESEGUITO
		pagModel.setCodiceAutorizzazione(pmt.getCodiceAutorizzazione());
		pagModel.setDescrizioneCanalePagamento(pmt.getDescrizioneCanalePagamento());
		pagModel.setIuv(pmt.getIuv());
		return pagModel;
	}

	@Override
	protected String buildXmlNotifica(String tipoSpontaneo,
			NotifichePagamentoModel model, EntityManager em) throws Exception {

		DatatypeFactory df;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		GregorianCalendar gcNow = new GregorianCalendar(); 	
		gcNow.setTime(new Date());
		XMLGregorianCalendar xgcNow = df.newXMLGregorianCalendar(gcNow);

		
		IdpInformativaPagamento info = new IdpInformativaPagamento();
		
		// ----------------------------
		// Header
		// ----------------------------
		
		OutboundIdpHeader header = new OutboundIdpHeader();
		OutboundHeaderE2E headere2e = new  OutboundHeaderE2E();
			headere2e.setXMLCrtDt(xgcNow);
			headere2e.setE2EMsgId(model.getE2emsgid());
				E2EReceiver e2ereceiver = new E2EReceiver();
				e2ereceiver.setE2ERcvrId(model.getReceiverId());
				e2ereceiver.setE2ERcvrSys(model.getReceiverSys());
				
		headere2e.setReceiver(e2ereceiver);		
		header.setE2E(headere2e);

			OutboundHeaderTRT headerTrt = new  OutboundHeaderTRT();
			headerTrt.setXMLCrtDt(xgcNow);
			headerTrt.setMsgId(model.getE2emsgid());
				TRTReceiver trtreceiver = new TRTReceiver();
				trtreceiver.setReceiverId(model.getTrtReceiverId());
				trtreceiver.setReceiverSys(model.getTrtReceiverSys());
				
		headerTrt.setReceiver(trtreceiver);
		header.setTRT(headerTrt);

		info.setIdpHeader(header);
		
		// ----------------------------
		// Body
		// ----------------------------
		
		IdpBody body = new IdpBody();
		for (PagamentoModel p:model.getListaPagamenti()) {
			Pagamento pxml = buildPagamento(p.getPagamentoModelTyped());
			body.getPagamento().add(pxml);
		}
				
		info.setIdpBody(body);
		
		return marshall(info);
		
	}
	
	
	/*  Marhalling degli oggetti Biller Services */
	private String marshall(IdpInformativaPagamento root) {
		JAXBContext jaxbContext;
		
		ByteArrayOutputStream marshalled = new  ByteArrayOutputStream();
		
		try {
			
	        StringWriter stringWriter = new StringWriter();

	        jaxbContext = JAXBContext.newInstance(IdpInformativaPagamento.class);
	        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	        // format the XML output
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        QName qName = new QName("http://idp.tasgroup.it/xmlbillerservices/InformativaPagamento", "IdpInformativaPagamento");
	        JAXBElement<IdpInformativaPagamento> rootX = new JAXBElement<IdpInformativaPagamento>(qName, IdpInformativaPagamento.class, root);

	        jaxbMarshaller.marshal(rootX, stringWriter);

	        String result = stringWriter.toString();
	        
	        return result;
						
		} catch (JAXBException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
			
	}

	
	/**
	 * Costruzione del Tag Pagamento. 
	 * @param model
	 * @return
	 */
	public Pagamento buildPagamento(PagamentoModelTyped pmodel) {
		
		DatatypeFactory df;
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		GregorianCalendar gc = new GregorianCalendar(); 
		
		Pagamento p = new Pagamento();
		
		gc.setTime(pmodel.getDataOraInizioPagamento());
		XMLGregorianCalendar xgc = df.newXMLGregorianCalendar(gc);
		p.setDataOraPagamento(xgc);

		gc.setTime(pmodel.getDataScadenzaPagamento());
		xgc = df.newXMLGregorianCalendar(gc);
		p.setDataScadenzaPagamento(xgc);
		
		p.setCausaleDebito(pmodel.getDeCausale());
		
		if ("2".equals(pmodel.getFlagIncasso())) {
			p.setTipoNotifica(TipoNotificaType.INCASSO);
		} else if ("1".equals(pmodel.getFlagIncasso())) {
			p.setTipoNotifica(TipoNotificaType.REGOLATO);
		} else {
			p.setTipoNotifica(TipoNotificaType.ESEGUITO);
		}
		
		p.setImporto(pmodel.getImporto());
		RiferimentoSoggetto versante = new RiferimentoSoggetto();
		  versante.setIdFiscale(pmodel.getIdVersante());
		  //versante.setAnagrafica(pmodel.getDescrizioneVersante()); 
		  versante.setEMail(pmodel.getEmailVersante());
		p.setVersante(versante);
		p.setIdDebito(pmodel.getIdPendenzaEnte());
		p.setTipoDebito(pmodel.getTipoDebito());
				
		RiferimentoSoggetto debitore = new RiferimentoSoggetto();
			debitore.setIdFiscale(pmodel.getIdPagante());

		p.setDebitore(debitore);
		
		p.setIdPagamento(pmodel.getIdPagamento());
				
		Transazione transazione = new Transazione();
		 CanalePagamento canalePagamento = new CanalePagamento(); 
		 			
		 canalePagamento.setDescrizione(pmodel.getDescrizioneCanaleTransazionePagamento());
		 canalePagamento.setIdentificativoCanale(pmodel.getIdCanaleTransazionePagamento());
		 canalePagamento.setTipoVersamento(pmodel.getTipoVersamentoTransazionePagamento());
		 canalePagamento.setIdentificativoPSP(pmodel.getIdPspTransazionePagamento());
		 transazione.setCanalePagamento(canalePagamento);
		 		 
		 gc.setTime(pmodel.getDataOraTransazione());
		 xgc = df.newXMLGregorianCalendar(gc);
		 transazione.setDataOraTransazione(xgc);
		 
		 transazione.setImportoCommissioni(pmodel.getImportoCommissioniTransazionePagamento());
		 transazione.setIdentificativoUnivocoRiscossione(pmodel.getIurTransazionePagamento());
		 transazione.setImporto(pmodel.getImportoTransazionePagamento());
		 transazione.setNumeroPagamenti(pmodel.getNumeroPagamentiTransazionePagamento());
		
		if (pmodel.getDataOraTransazionePagamento()!=null) { 
			 gc.setTime(pmodel.getDataOraTransazionePagamento());
			 xgc = df.newXMLGregorianCalendar(gc);
			 transazione.setDataOraTransazione(xgc);
		}	 
		 
		p.setTransazione(transazione);
		p.setIdentificativoUnivocoVersamento(pmodel.getIuv());
		
		
		if ("2".equals(pmodel.getFlagIncasso())) {
			
			RiferimentoIncasso inc= new RiferimentoIncasso();
			
			if (pmodel.getDataEsecuzioneIncasso()!=null) {
				xgc = df.newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd").format(pmodel.getDataEsecuzioneIncasso()));  //Trucco per formattare la data in Date e non DateTime su XML
				inc.setDataRegolamento(xgc);
			}	
			
			inc.setIdentificativoFlusso(pmodel.getIdFlussoIncasso());
			inc.setIdentificativoPSP(pmodel.getIdPspTransazioneIncasso());
			inc.setTotaleTransazione(pmodel.getTotaleTransazioneIncasso());
			inc.setIdentificativoUnivocoRegolamento(pmodel.getTrnIncasso());
			
			p.setRiferimentoIncasso(inc);
		}	
		p.setNotePagamento(pmodel.getNotePagamento());
		p.setRiferimentoDebito(pmodel.getRiferimentoDebito());
				
		return p;
	}
	
	
	
}
