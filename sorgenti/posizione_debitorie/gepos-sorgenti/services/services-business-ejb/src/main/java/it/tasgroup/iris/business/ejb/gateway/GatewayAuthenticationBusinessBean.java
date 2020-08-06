package it.tasgroup.iris.business.ejb.gateway;

import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.IrisContextImpl;
import it.nch.is.fo.profilo.CurrentCorporateVOPojo;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.Operatori;
import it.tasgroup.iris.business.ejb.authentication.dto.builder.CurrentCorporateVoBuilder;
import it.tasgroup.iris.business.ejb.client.gateway.GatewayAuthenticationBusinessLocal;
import it.tasgroup.iris.business.ejb.client.gateway.GatewayAuthenticationBusinessRemote;
import it.tasgroup.iris.domain.CarrelloGateway;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.Sessione;
import it.tasgroup.iris.domain.SessioneGateway;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.PendenzaDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.dto.exception.GatewayAuthenticationException;
import it.tasgroup.iris.dto.gateway.PaymentAuthenticationDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CfgIrisGatewayClientDAO;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.OperatoriDAO;
import it.tasgroup.iris.persistence.dao.interfaces.SessioneDAO;
import it.tasgroup.iris.persistence.dao.interfaces.SessioneGatewayDAO;
import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "GatewayAuthenticationBusiness")
public class GatewayAuthenticationBusinessBean implements GatewayAuthenticationBusinessLocal, GatewayAuthenticationBusinessRemote {

	@EJB(name = "CondizioniPagamentoDaoService")
	private CondizioniPagamentoDao condPagDao;

	@EJB(name = "SessioneGatewayDAO")
	private SessioneGatewayDAO gatewaySessionDao;

	@EJB(name = "CfgIrisGatewayClientDAO")
	private CfgIrisGatewayClientDAO cfgIrisGatewayClientDao;

	@EJB(name = "SessioneDAO")
	private SessioneDAO sessioneDao;

	@EJB(name = "OperatoriDAO")
	private OperatoriDAO operatoriDao;

	@EJB(name = "IntestatariDaoService")
	private IntestatariDAO intestatariDao;

	/*
	 * A0000001 Identificativo non presente in IRIS A0000002 Pendenza già pagata
	 * (stato = eseguito) A0000003 Pendenza non pagabile A0000004 Pendenza
	 * scaduta A0000005 Pendenza con pagamento in corso A0000006 Pendenza con
	 * pagamento disposto / non eseguito (stato = pagato sbf) A0000007 Pendenza
	 * già pagata su canale ATM A0000008 Identificativo associato ad un
	 * documento annullato B0000001 Multa non presente in IRIS B0000002 Multa
	 * non pagabile B0000003 Multa scaduta W0000001 Dati della pendenza diversi
	 * da quelli noti al contribuente; chiedergli conferma prima di proseguire
	 * con il pagamento W0000002 Multa già pagata In questo caso si devono
	 * ritornare anche i dati necessari per la ristampa della ricevuta !
	 */

	private static final Logger LOGGER = LogManager.getLogger(GatewayAuthenticationBusinessBean.class);

	@Override
	public SessioneGateway authenticateForPayment(PaymentAuthenticationDTO dto) throws GatewayAuthenticationException{

		String token = dto.getToken();
		SessioneGateway sessione = gatewaySessionDao.retrieveGatewaySession(token);
		BigDecimal totalAmount = dto.getAmountToCheck();
		if (sessione.isToRedirect()) {
			totalAmount = sessione.getImTotale();
		}  
		// OTF
		if(dto.getRedirectOnly() != null && (("Y".equals(dto.getRedirectOnly()) || "true".equals(dto.getRedirectOnly())))) {
			sessione.setRedirectOnly(dto.getRedirectOnly());
		}

		// controlli sulla sessione:

		// 1- che esista su DB;
		// 2- che non sia spirata;
		// 3- che non sia usata;
		// 3- che corrisponda allo stesso sessionId e applicationId (TO DO PAZZIK);
		// 4- che abbia condizioni associate da pagare.
		
		if (sessione == null)
			throw new GatewayAuthenticationException(token, totalAmount.toString());

		Timestamp now = new Timestamp(System.currentTimeMillis());

		// durata 15 minuti
		Long sessionDuration = 9000000000000000000L;
		if ((sessione.getTsInserimento().getTime() + sessionDuration) < now.getTime())
			throw new GatewayAuthenticationException(token, totalAmount.toString());
		if (sessione.getCarrelloGateways().isEmpty())
			throw new GatewayAuthenticationException(token, totalAmount.toString());
		if (sessione.isUsed())
			throw new GatewayAuthenticationException(token, totalAmount.toString());
		//if(!sessione.getImTotale().equals(totalAmount))
			//throw new GatewayAuthenticationException(token, totalAmount.toString());

		
		sessione.setUsata(1);
		SessioneGateway updatedSession = gatewaySessionDao.updateGatewaySession(sessione);
		return updatedSession;
	}

	public EsitoOperazionePagamentoDTO checkPaymentGatewayShoppingCart(RichiestaAutorizzazioneDTO autorizzazioneDto) {

	// TODO PAZZIK VERIFICARE CHE QUESTI CONTROLLI SULLA PAGABILITA DELLE CONDIZIONI
	// SIANO VALIDI PER QUALUNQUE CANALE PERCHE IRIS GATEWAY E' MULTICANALE

		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();

		dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);

		// controllo se mi viene passato il carrello
		if (autorizzazioneDto.getPendenze() != null && autorizzazioneDto.getPendenze().size() > 0) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Servizio autorizzazione pagamento - richiesta autorizzazione per il carrello: ");
			}

			dtoOut = checkHeader(dtoOut,autorizzazioneDto);
			if (EnumBusinessReturnCodes.getByKey(dtoOut.getCodice()).getSeverityLevel().equals(EnumSeverityLevel.ERROR)) {
				return dtoOut;
			}

			List<CondizionePagamento> listaCondizioni = estraiListaCondizioni(autorizzazioneDto);

			// Controllo che le pendenze/condizioni passate siano coerenti
			dtoOut = checkPaymentConditions(dtoOut,autorizzazioneDto,listaCondizioni);
			if (EnumBusinessReturnCodes.getByKey(dtoOut.getCodice()).getSeverityLevel().equals(EnumSeverityLevel.ERROR)) {
				return dtoOut;
			}
			//
			dtoOut = isCondizioniPagabili(dtoOut,listaCondizioni);
			if (EnumBusinessReturnCodes.getByKey(dtoOut.getCodice()).getSeverityLevel().equals(EnumSeverityLevel.ERROR)) {
				// pendenza non pagabile
				// non tutte le condizioni sono pagabili
				LOGGER.error("Servizio autorizzazione pagamento - esito richiesta autorizzazione");
				return dtoOut;
			}

			dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);

			return dtoOut;
		} // FINE CARRELLO

		// se non esiste nemmeno una pendenza nella request non consento l'accesso al Gateway
		dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);

		return dtoOut;
	}

	private EsitoOperazionePagamentoDTO checkPaymentConditions(EsitoOperazionePagamentoDTO dtoOut,RichiestaAutorizzazioneDTO dto, List<CondizionePagamento> listaCondizioni) {


		if (listaCondizioni.size() == 0) {

			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);

			return dtoOut;

		}
		
		// Non controllo il flag730, perche' viene impostato su GW

		// CONTROLLO CONGRUENZA IMPORTI SINGOLI

		BigDecimal sum = new BigDecimal(0);

		List<PendenzaDTO> listPendenze = dto.getPendenze();

		PendenzaDTO pend = listPendenze.get(0);

		List<BigDecimal> amountsFromRequest = pend.getImporti();

		List<String> itemIdsFromRequest = pend.getCondizioni();

		for (int i=0; i< amountsFromRequest.size(); i++) {

			CondizionePagamento condizione = listaCondizioni.get(i);

			BigDecimal currAmount = amountsFromRequest.get(i);

			String idCondizione = itemIdsFromRequest.get(i);

			BigDecimal importoCondizione = condizione.getImTotale();

			if(idCondizione.equals(condizione.getIdCondizione())){

				if (!(currAmount.compareTo(condizione.getImTotale())==0)) {

					if (LOGGER.isInfoEnabled())
						LOGGER.info("condizione in request non trovata su DB");

					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000019);

					return dtoOut;
				}

			}

			sum = sum.add(importoCondizione).setScale(2);

		}

		// CONTROLLO CONGRUENZA IMPORTO TOTALE

		if (!sum.equals(dto.getTotalAmount())) {

			if (LOGGER.isInfoEnabled())
				LOGGER.info("Richiesta contenente importi non coerenti con quelli presenti su DB");

			dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000020);

			return dtoOut;
		}

		return dtoOut;
	}

	private EsitoOperazionePagamentoDTO checkHeader(EsitoOperazionePagamentoDTO dtoOut,RichiestaAutorizzazioneDTO autorizzazioneDto) {
		try {

			// /////////////////////
			// Verifica chiamante
			// /////////////////////
			// /////////////////////
			// sistema
			// /////////////////////
			String applicationId = autorizzazioneDto.getTestata().getSenderSil();
			String systemId = autorizzazioneDto.getTestata().getSenderSys();
			CfgIrisGatewayClient gtwClient = cfgIrisGatewayClientDao.retrieveGatewayClient(applicationId, systemId);
			if (gtwClient == null){
				if (LOGGER.isInfoEnabled())
					LOGGER.info("cfgIrisGatewayClient non trovata : applicationId=" +applicationId +"systemId= "+systemId);
				dtoOut.setReturnCode(EnumBusinessReturnCodes.E0000001);
				return dtoOut;

			}

		} catch (Exception e) {
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
		}
		return dtoOut;
	}


	private List<CondizionePagamento> estraiListaCondizioni(RichiestaAutorizzazioneDTO dto) {

		List<CondizionePagamento> condOut = new ArrayList<CondizionePagamento>();

		List<PendenzaDTO> lista = dto.getPendenze();

		for (PendenzaDTO pendenzaDTO : lista) {

			List<String> listaIdCond = pendenzaDTO.getCondizioni();

			List<CondizionePagamento> condizioniPendenza = condPagDao.getCondizioniByIdList(listaIdCond);

			condOut.addAll(condizioniPendenza);

		}

		return condOut;
	}

	public List<CondizionePagamento> getCondizioniByIdList(List<String> condizionePagamentoIdList) {
		return condPagDao.getCondizioniByIdList(condizionePagamentoIdList);
	}


	// TODO: creare servizio di controllo pagabilita condizioni

	/**
	 * @param dtoOut
	 * @param listaCondizioni
	 * @return
	 */
	private EsitoOperazionePagamentoDTO isCondizioniPagabili(EsitoOperazionePagamentoDTO dtoOut,List<CondizionePagamento> listaCondizioni) {

		for (CondizionePagamento condizione : listaCondizioni) {

			boolean isCondizionePagabile = false;

			EnumStatoPagamentoCondizione statoCondizioneCalcolato = null;

			try {

					if (condizione != null){

							condizione.updateStatoPagamentoCalcolato();

							statoCondizioneCalcolato = condizione.getStatoPagamentoCalcolato();

							isCondizionePagabile = statoCondizioneCalcolato.equals(EnumStatoPagamentoCondizione.DA_PAGARE);

							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("verifico la condizione: " + condizione.getIdCondizione() + "stato condizione: " + statoCondizioneCalcolato);
							}
					}

				} catch (Exception e) {
					Tracer.error(this.getClass().getName(), "isCondizionePagabile", e.getMessage(), e);
					e.printStackTrace();
					new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
				}

			// se ne trovo anche una non pagabile ritorno false
			if (!isCondizionePagabile) {
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000003);
				dtoOut.setDescrizione(EnumBusinessReturnCodes.A0000003.getDescrizione()+ ": IdCondizione = "+ condizione.getIdCondizione() +", Stato = "+statoCondizioneCalcolato.getDescrizione());
				return dtoOut;
			}
		}

		return dtoOut;

	}

	@Override
	public SessioneGateway saveGatewaySession(SessioneGateway sessioneGateway) throws GatewayAuthenticationException {

		Set<CarrelloGateway> cart = sessioneGateway.getCarrelloGateways();

		for(CarrelloGateway cartItem : cart){

			CondizionePagamento condizionePagamento = condPagDao.getSingleCondizioneById(cartItem.getId().getIdCondizione());

			cartItem.setCondizionePagamento(condizionePagamento);
		}

		CfgIrisGatewayClient gtwClient = cfgIrisGatewayClientDao.retrieveGatewayClient(sessioneGateway.getCfgIrisGatewayClient().getApplicationId(), sessioneGateway.getCfgIrisGatewayClient().getSystemId());

		sessioneGateway.setCfgIrisGatewayClient(gtwClient);
		Sessione irisSession = sessioneDao.retrieveSessionById(sessioneGateway.getSessionId());
		if (irisSession == null && gtwClient.isAuthenticationRequired())
			throw new GatewayAuthenticationException(sessioneGateway.getToken(), sessioneGateway.getImTotale().toString());
		if (irisSession != null) {
			sessioneGateway.setIntestatario(irisSession.getAzienda());
			sessioneGateway.setOperatore(irisSession.getUsername());
		} else {
			sessioneGateway.setIntestatario("ANONYMOUS");
			sessioneGateway.setOperatore("ANONYMOUS");
		}
		sessioneGateway.setUsata(0);
		SessioneGateway createdSession = gatewaySessionDao.saveGatewaySession(sessioneGateway);
		return createdSession;

	}
	
	@Override
	public FrontEndContext populateFEC(SessioneGateway sessioneGateway) throws GatewayAuthenticationException, ServiceLocatorException, RemoteException {

		FrontEndContext fec = new IrisContextImpl();

		try {

			fec.setName(sessioneGateway.getIntestatario());

			// TODO PAZZIK ELIMINARE IL VECCHIO FWK?

//			DTO dtoIn = new DTOImpl();
//
//			LoginRTPojo login = new LoginRTImpl();
//
//			login.setCodiceFiscale(sessioneGateway.getOperatore());
//			login.setPassword("GATEWAY");
//
//			dtoIn.setPojo(login);
//
//			OperatorserviceDaoDB dao = new OperatorserviceDaoDB();
//			OperatoriPojo oper = dao.getOperatorByCodiceFiscale((BackEndContext)fec, dtoIn, false);


			Operatori oper = operatoriDao.getOperatoreByCodiceFiscale(sessioneGateway.getOperatore());

			//
			// Setto nell'operatore l'INTESTATARIO-OPERATORE legato all'intestatario corrente
			//
// Forse questo non serve più?
//			for (IntestatarioperatoriCommon intOper : oper.getIntestatarioperatori()) {
//				if (intOper.getIntestatariobjIForm().getCorporateIForm().equals(sessioneGateway.getIntestatario())){
//					oper.setIntestatarioperatoriobj(intOper);
//					break;
//				}
//			}



			DTO dtoOut = new DTOImpl();
			dtoOut.setPojo(oper);

			fec.setOperatore(dtoOut);
			fec.setUsername(oper.getOperatore());

			// TODO PAZZIK VERIFICARE, se entro da IRIS SSO, SSOLogin va settato true?
			fec.setSSOLogin(false);

			Intestatari intestatario = intestatariDao.getIntestatarioByCorporateAndOperatore(sessioneGateway.getIntestatario(), oper.getOperatore());
			CurrentCorporateVOPojo corporate = CurrentCorporateVoBuilder.fromIntestatari(intestatario);


			DTO corporateDTO = new DTOImpl();
			corporateDTO.setPojo(corporate);
// da portare a nuovo
			((IrisContextImpl)fec).setAziendaCorrente(corporateDTO);

		} catch (Exception e) {
			e.printStackTrace();
			throw new GatewayAuthenticationException(sessioneGateway.getToken(), sessioneGateway.getImTotale().toString());
		}

		return fec;
	}

	@Override
	public SessioneGateway getSessioneGateway(String token) throws GatewayAuthenticationException{
		return gatewaySessionDao.retrieveGatewaySession(token);
	}

}


