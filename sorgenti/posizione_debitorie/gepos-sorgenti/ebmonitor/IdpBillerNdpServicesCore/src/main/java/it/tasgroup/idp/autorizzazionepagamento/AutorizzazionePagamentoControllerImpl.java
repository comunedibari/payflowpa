package it.tasgroup.idp.autorizzazionepagamento;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.domain.bonifici.Intestatari;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.generazioneiuv.ValidationGenerazioneIUVException;
import it.tasgroup.idp.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.idp.util.EnumTipiErrori;
import it.tasgroup.idp.util.GeneratoreIDPIdUnivoci;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.JAXBContextProvider;
import it.tasgroup.idp.util.ServiceLocalMapper;

@Stateless
//@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AutorizzazionePagamentoControllerImpl implements AutorizzazionePagamentoController {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@EJB(name="IUVGenerator")
	private IUVGeneratorLocal iuvGenerator;

	@Override
	public void attivaPagamento(AttivaPagamentoRequestType request, AttivaPagamentoResponseType response) throws Exception{
		AttivaPagamentoResponseBodyType responseBody = response.getBody();
		try{
			
			// cerco l'intestatario
			
			Intestatari inte = getIntestatarioByIdentificativoFiscale(responseBody.getIdentificativoDominio(),em);
			// cerco l'ente
			Enti ente = getEnteByCodEnte(inte.getIntestatario(), em);
			// cerco il tributo
			TributiEnti tributo = GestoreVerificheAnagrafiche.getTributoEnteByIdEnteCodTributo(ente.getIdEnte(), responseBody.getTipoDebito(), em);
			// inserisco la pendenza
			Pendenze pendenza = inserisciPendenza( responseBody, ente, tributo);
			
			// inserimento della request nella PENDENZE_CART
			
			// inserimento della response nella "ESITI_CART".
			
			traceCall(request, response,  pendenza, ente.getCdEnte(),tributo.getId().getCdTrbEnte()) ;
			
			
		}catch (Exception e){
			e.printStackTrace();
			logger.error("Errore generico durante attiva pagamento", e);
			FaultType fault = new FaultType();
			fault.setFaultCode("ERRORE GENERICO");
			fault.setFaultDescription("Errore generico");
			fault.setFaultString("ERRORE GENERICO: " + e.getMessage());
			response.setFault(fault);
			throw e;
		}
		
	}
	
	
	private Pendenze inserisciPendenza(AttivaPagamentoResponseBodyType responseBody,Enti ente,TributiEnti tributo) throws Exception{
		Timestamp tsDecorrenza = new Timestamp(System.currentTimeMillis());  
		
		Pendenze pp = new Pendenze();
		pp.setAnnoRiferimento(Integer.parseInt(responseBody.getAnnoRiferimento()));
		pp.setCdTrbEnte(responseBody.getTipoDebito());
		pp.setCoAbi(null);
		// DESCRIZIONE TRIBUTO IN DE_CAUSALE
		pp.setDeCausale(responseBody.getCausaleVersamento());
		pp.setDeMittente(responseBody.getDescrizioneMittente());
		pp.setFlModPagamento("S"); //Default: poi sovrascritto sotto in  base alle condizioni di pagamento
		pp.setIdEnte(ente.getIdEnte());
		pp.setIdMittente(responseBody.getIdMittente());
		pp.setIdPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());
		pp.setIdPendenzaente(responseBody.getIdDebito());
		pp.setIdTributo(tributo.getJlttrpa().getIdTributo());
		pp.setImTotale(responseBody.getImportoPagamento());
		// id riscossore e riferimento riscossore -- coriscossore per id pedenzeriferimento
		pp.setCoRiscossore(responseBody.getIdRiscossore());
		
		pp.setDvRiferimento("EUR");
		pp.setOpAnnullamento(null);
		pp.setPrVersione(0);
		pp.setStRiga("V");

		pp.setStPendenza("A");
		pp.setTsCreazioneente(new Timestamp(System.currentTimeMillis()) );
		pp.setTsDecorrenza(tsDecorrenza);
		pp.setTsEmissioneente(new Timestamp(System.currentTimeMillis()));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataPrescrizione=null;
		try {
			dataPrescrizione = sf.parse("2100-01-01");
		} catch (ParseException e) {
			logger.error("Errore che non si dovrebbe mai presentare",e);
		}
		
		pp.setTsPrescrizione(new Timestamp(dataPrescrizione.getTime()));
		
		pp.setIdTributoStrutturato(null);
		
		pp.setIdSystem(tributo.getIdSystem());
		
		// Condizioni Pagamento
		Set<CondizioniPagamento> condizioni=new LinkedHashSet<CondizioniPagamento>();
		
			
		String tipoRateazionePagamento=null;
		
		pp.setFlModPagamento("S");
		tipoRateazionePagamento="S";
		
		CondizioniPagamento c = new CondizioniPagamento();
		
		c.setCdTrbEnte(tributo.getId().getCdTrbEnte());
		c.setIdEnte(tributo.getId().getIdEnte());
		c.setImTotale(responseBody.getImportoPagamento());
		c.setOpAnnullamento(null);
		c.setPrVersione(0);
	
		
		c.setStRiga("V");
		c.setTsAnnullamento(null);
		
	
		c.setTsDecorrenza(tsDecorrenza);
		c.setIdCondizione(GeneratoreIdUnivoci.GetCurrent().generaId());
		
		c.setIdPagamento(responseBody.getIdentificativoUnivocoVersamento());
		
	
		
		c.setStPagamento("N");
		
		c.setTiPagamento(tipoRateazionePagamento);
		
		c.setDtScadenza(tsDecorrenza);
		c.setDtFinevalidita(tsDecorrenza);
		c.setDtIniziovalidita(tsDecorrenza);
		c.setCausalePagamento(responseBody.getCausaleVersamento());
		
		c.completeForInsert();
		c.setPendenze(pp);
		condizioni.add(c);
		pp.setCondizioniPagamento(condizioni);
		
		DestinatariPendenze dp = new DestinatariPendenze();
		
		dp.setPrVersione(0);
		
		dp.setStRiga("V");
		
		dp.setTsDecorrenza(tsDecorrenza);
		dp.setIdDestinatario(GeneratoreIdUnivoci.GetCurrent().generaId());
		 
		dp.setCoDestinatario(responseBody.getCodiceIdentificativoUnivocoDebitore());
		dp.setDeDestinatario(responseBody.getAnagraficaDebitore());
		
		//#3141 qui possiamo inserire solo il `TIPO_SOGGETTO_DEST` e `ANAGRAFICA_SOGGETTO_DEST`
		// gli altri dati non sono disponibili
		dp.setAnagraficaDestinatario(responseBody.getAnagraficaDebitore());
		
		// persona fisica CI e AL persona giuridica
		IdentificativoUnivocoDebitoreType tipoIdentificativoUnivocoDebitore = responseBody.getTipoIdentificativoUnivocoDebitore();
		if (tipoIdentificativoUnivocoDebitore!=null){
			dp.setTipoSoggettoDestinatario(tipoIdentificativoUnivocoDebitore.value());
			if (tipoIdentificativoUnivocoDebitore.value().equals("F"))
				dp.setTiDestinatario("CI");
			else
				dp.setTiDestinatario("AL");
		}else{
			// se non è valorizzato ???
			dp.setTiDestinatario("CI");
		}
		
		dp.completeForInsert();
		dp.setPendenze(pp);
		Set<DestinatariPendenze> destinatari  = new LinkedHashSet<DestinatariPendenze>();
		destinatari.add(dp);
		
		pp.setDestinatariPendenze(destinatari);
		
		pp.completeForInsert();
		
		em.persist(pp); //
		em.flush();
		return pp;
	}
	
	private void traceCall(AttivaPagamentoRequestType request, AttivaPagamentoResponseType response, Pendenze pendenza, String senderId, String codiceTributo) {
		
		//Marshalling input and output
		ByteArrayOutputStream inputRequestBytes = marshall(request,true);
		ByteArrayOutputStream outputRequestBytes = marshall(response,false);

		// identificativi prato senderid ente.cdente e sendersys è il ,sil del tributo
		String trtSenderId=new String(senderId);
		String trtSenderSys=pendenza.getIdTributo();
		
		// definire una nuova classe di generatori univoci
		String trtE2EmsgId=GeneratoreIDPIdUnivoci.GetCurrent().generaId();
		
		PendenzeCartPK pcpk=new PendenzeCartPK();
		pcpk.setE2emsgid(trtE2EmsgId);
		pcpk.setSenderid(trtSenderId);
		pcpk.setSendersys(trtSenderSys);
		
		PendenzeCartMessage pc = new PendenzeCartMessage();
		pc.setPk(pcpk);
		pc.setReceiverid("RTIRIS");
		pc.setReceiversys("SIL_IRIS_ITR");
		String tmpMsg = new String(outputRequestBytes.toByteArray());
		System.out.println("messaggio: " + tmpMsg);
		pc.setMessaggioXml(inputRequestBytes.toByteArray());
		pc.setTimestampRicezione(new Timestamp(System.currentTimeMillis()));
		if (response.getFault()==null) {
			pc.setStato("RISPOSTA OK WS");
		} else {
			pc.setStato("RISPOSTA KO WS");
		}
		pc.setPrVersione(0);
		
		//Calcolo il numero delle pendenze.
		int numPend = 1;
		
		
		pc.setNumPendenze(numPend);  
		pc.setTipoMessaggio("ALL_PEND");
		pc.setTipoOperazione("Insert");
		// CDTRBENTE
		pc.setTipoTributo(codiceTributo);
		pc.setNumPendDeleted(0);
		pc.setTrtSenderId(trtSenderId);
		pc.setTrtSenderSys(trtSenderSys);
		pc.setOpInserimento("IDP");
		pc.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		
		em.persist(pc);
		em.flush();
		
		PendenzeCart pcr=em.find(PendenzeCart.class, pcpk);
		
		EsitiCartPK ecpk = new EsitiCartPK();
		ecpk.setE2emsgid(trtE2EmsgId);
		ecpk.setSenderid(trtSenderId);
		ecpk.setSendersys(trtSenderSys);
		
		EsitiCart ec = new EsitiCart();
		ec.setPk(ecpk);
		ec.setStato("SPEDITO WS");
		ec.setPrVersione(0);
		ec.setTrtSenderId(trtSenderId);
		ec.setTrtSenderSys(trtSenderSys);
		ec.setEsitoXml(outputRequestBytes.toByteArray());
		ec.setTimestampInvio(new Timestamp(System.currentTimeMillis()));
		ec.setPendenzeCart(pcr);
		ec.setOpInserimento("IDP");
		ec.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		
		em.persist(pc);
		
		// Caso OK{
		if (response.getFault()==null  && pendenza!=null) { 
			
				EsitiPendenza esitoPendenza = new EsitiPendenza();
				esitoPendenza.setIdEsitoPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());
				esitoPendenza.setEsitoPendenza("OK");
				esitoPendenza.setDescrizioneEsito("");
				esitoPendenza.setStato("RISPOSTA INVIATA WS");
				esitoPendenza.setIdPendenza(pendenza.getIdPendenza());
				esitoPendenza.setIdPendenzaEnte(pendenza.getIdPendenzaente());
				esitoPendenza.setPrVersione(0);
				esitoPendenza.setPendenzeCart(pcr);
				esitoPendenza.setTrtSenderId(pc.getTrtSenderId());
				esitoPendenza.setTrtSenderSys(pc.getTrtSenderSys());
				esitoPendenza.setOpInserimento("WS_OTF_MX");
				esitoPendenza.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				em.persist(esitoPendenza);
			
		}
				
		// Caso KO. Inserisco solo un generico KO, altrimenti da DB non si evince che c'ï¿½ stato un errore.
		if (response.getFault()!=null && response.getFault().getFaultCode()!=null) { 
			
				EsitiPendenza esitoPendenza = new EsitiPendenza();
				esitoPendenza.setIdEsitoPendenza(GeneratoreIdUnivoci.GetCurrent().generaId());
				esitoPendenza.setEsitoPendenza("KO");
				esitoPendenza.setDescrizioneEsito("");
				esitoPendenza.setStato("RISPOSTA INVIATA WS");
				esitoPendenza.setIdPendenza(null);
				esitoPendenza.setIdPendenzaEnte("-");
				esitoPendenza.setPrVersione(0);
				esitoPendenza.setPendenzeCart(pcr);
				esitoPendenza.setTrtSenderId(pc.getTrtSenderId());
				esitoPendenza.setTrtSenderSys(pc.getTrtSenderSys());
				esitoPendenza.setOpInserimento("WS_OTF_MX");
				esitoPendenza.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				
				
				Set<ErroriEsitiPendenza> erroriEsitiPendenzaCollection = new HashSet<ErroriEsitiPendenza>();
				ErroriEsitiPendenza erroriEsitiPendenza = new ErroriEsitiPendenza();
				erroriEsitiPendenza.setPrVersione(0);
				erroriEsitiPendenza.setOpInserimento("WS_OTF_MX");
				erroriEsitiPendenza.setTsInserimento(new Timestamp(System.currentTimeMillis()));
				erroriEsitiPendenza.setIdEsitoPendenza(esitoPendenza);
				erroriEsitiPendenza.setIdPendenza(null);
				erroriEsitiPendenza.setIdPendenzaEnte("-"); 
				erroriEsitiPendenza.setIdErrore(GeneratoreIdUnivoci.GetCurrent().generaId());
				
				// Come codice e descrizione, inserisco il primo che trovo.
				erroriEsitiPendenza.setCodice(response.getFault().getFaultCode());
				erroriEsitiPendenza.setDescrizioneErrore(response.getFault().getFaultDescription());
				erroriEsitiPendenzaCollection.add(erroriEsitiPendenza);
				
				
				esitoPendenza.setErroriEsitiPendenzaCollection(erroriEsitiPendenzaCollection);
				em.persist(esitoPendenza);
				em.persist(erroriEsitiPendenza);
			
		}
		
		

		em.persist(ec);
	}
	
	
	  
	ByteArrayOutputStream marshall(Object root, boolean isARequest) {
		JAXBContext jaxbContext;
		
		ByteArrayOutputStream marshalled = new  ByteArrayOutputStream();
		
		try {
			// jaxbContext = JAXBContext.newInstance("it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento:it.tasgroup.idp.xmlbillerservices.common:it.tasgroup.idp.xmlbillerservices.header:it.tasgroup.idp.xmlbillerservices.pendenze");
			/*
			jaxbContext = JAXBContextProvider.getInstance()
				.getJAXBContext (
					new String [] {
						  "it.tasgroup.idp.autorizzazionepagamento"
						
					}
				);
			
			*/
		
			
			if (!isARequest){
				jaxbContext = JAXBContext.newInstance(AttivaPagamentoResponseType.class);
			
				QName qName = new QName("it.tasgroup.idp.autorizzazionepagamento", "root");
				JAXBElement<AttivaPagamentoResponseType> rootElement = new JAXBElement<AttivaPagamentoResponseType>(qName, AttivaPagamentoResponseType.class, (AttivaPagamentoResponseType)root);
				//marshall
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
				marshaller.marshal(rootElement, marshalled);
			}else{
				jaxbContext = JAXBContext.newInstance(AttivaPagamentoRequestType.class);
				
				QName qName = new QName("it.tasgroup.idp.autorizzazionepagamento", "root");
				JAXBElement<AttivaPagamentoRequestType> rootElement = new JAXBElement<AttivaPagamentoRequestType>(qName, AttivaPagamentoRequestType.class, (AttivaPagamentoRequestType)root);
				//marshall
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
				marshaller.marshal(rootElement, marshalled);
			}
			  
			    
			
			
										
			
				
			return marshalled;
			
			
		} catch (JAXBException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
			
	}
	
	
	private static Intestatari getIntestatarioByIdentificativoFiscale(String identificativo, EntityManager em)throws ValidationGenerazioneIUVException{
		Query q =em.createNamedQuery("IntestatarioByLapl");
		q.setParameter("lapl", identificativo);
		List<Intestatari> l =q.getResultList();
		if (l==null || l.size()==0) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.SENDER_ID_INESISTENTE);
		} 
		Intestatari inte= l.get(0);
		return inte;
	}
	
	public static Enti getEnteByCodEnte(String codEnte, EntityManager em) throws ValidationGenerazioneIUVException {
		String qEnti = " from Enti where intestatario=:cdEnte and stato='A'";
		Query q = em.createQuery(qEnti);
		q.setParameter("cdEnte", codEnte);
		List<Enti> enti=q.getResultList();
		if (enti==null || enti.size()==0) {
			throw new ValidationGenerazioneIUVException(EnumTipiErrori.SENDER_ID_INESISTENTE);
		} 
		Enti ente= enti.get(0);
		return ente;
	}


}
