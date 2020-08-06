package it.tasgroup.iris.business.ejb.authentication;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoInterface;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.AppConfiguration;
import it.nch.is.fo.BackEndConstant;
import it.nch.is.fo.IrisContextImpl;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.login.LoginPojo;
import it.nch.is.fo.login.rt.LoginRTPojo;
import it.nch.is.fo.profilo.Applicazioni;
import it.nch.is.fo.profilo.CurrentCorporateVOPojo;
import it.nch.is.fo.profilo.Indirizzipostali;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.nch.is.fo.profilo.Intestatarioperatori;
import it.nch.is.fo.profilo.IntestatarioperatoriCommon;
import it.nch.is.fo.profilo.IntestatarioperatoriId;
import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.profilo.OperatoriForm;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.util.crypto.CryptUtil;
import it.nch.is.fo.uuidgen.UUIDGenerator20;
import it.nch.utility.enumeration.Categoria;
import it.nch.utility.enumeration.TipoOperatore;
import it.tasgroup.iris.business.ejb.authentication.dto.builder.ApplicazioniMenuBuilder;
import it.tasgroup.iris.business.ejb.authentication.dto.builder.CurrentCorporateVoBuilder;
import it.tasgroup.iris.business.ejb.client.authentication.AuthenticationBusinessLocal;
import it.tasgroup.iris.business.ejb.client.authentication.AuthenticationBusinessRemote;
import it.tasgroup.iris.domain.Sessione;
import it.tasgroup.iris.dto.FunzioniPropDTO;
import it.tasgroup.iris.dto.autentication.SessionDTO;
import it.tasgroup.iris.dto.exception.AuthenticationException;
import it.tasgroup.iris.dto.menu.ApplicazioniMenu;
import it.tasgroup.iris.persistence.dao.interfaces.ApplicazioniDao;
import it.tasgroup.iris.persistence.dao.interfaces.ClassiAbilitazioniDao;
import it.tasgroup.iris.persistence.dao.interfaces.FunzioniintestatariDao;
import it.tasgroup.iris.persistence.dao.interfaces.FunzionioperatoriDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.OperatoriDAO;
import it.tasgroup.iris.persistence.dao.interfaces.SessioneDAO;

import java.sql.DatabaseMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
@Stateless(name = "AuthenticationBusinessBean")
public class AuthenticationBusinessBean implements AuthenticationBusinessLocal, AuthenticationBusinessRemote {

	private static final long serialVersionUID = 1L;

	@EJB(name = "SessioneDAO")
	private SessioneDAO sessioneDao;

	@EJB(name = "ApplicazioniDao")
	private ApplicazioniDao applicazioniDao;

	@EJB(name = "ClassiAbilitazioniDao")
	private ClassiAbilitazioniDao classiAbilitazioniDao;

	@EJB(name = "FunzionioperatoriDao")
	private FunzionioperatoriDao funzionioperatoriDao;

	@EJB(name = "FunzioniintestatariDao")
	private FunzioniintestatariDao funzioniintestatariDao;

	@EJB(name = "OperatoriDAO")
	private OperatoriDAO operatoriDao;

	@EJB(name = "IntestatariDaoService")
	private IntestatariDAO intestatariDao;

	private static final Logger LOGGER = LogManager.getLogger(AuthenticationBusinessBean.class);

	@Override
	public void disconnectSession(String sessionId) {
		sessioneDao.deleteSessionById(sessionId);
	}

	@Override
	public void insertOrUpdateSession(SessionDTO sessionDto) {

		Sessione sessione = sessioneDao.retrieveSessionById(sessionDto.getSessionId());
		boolean alreadyPresent = true;
		if (sessione == null) {
			alreadyPresent = false;
			sessione = new Sessione();
		}

		sessione.setSessionid(sessionDto.getSessionId());
		sessione.setAzienda(sessionDto.getCorporate());
		sessione.setCanale(sessionDto.getCorporateBankingChannel());
		sessione.setLingua(sessionDto.getOfficialLanguage());
		sessione.setUsername(sessionDto.getUserName());
		if (sessionDto.getLastTransactionTime() != null)
			sessione.setUltimaoperazione(new Double(sessionDto.getLastTransactionTime().getTime()));
		if (sessionDto.getConnectionTime() != null)
			sessione.setCollegamento(new Double(sessionDto.getConnectionTime().getTime()));
		sessione.setIndirizzoip(sessionDto.getIpAddress());
		sessione.setCertificato(sessionDto.getCertificato());
		sessione.setMac(sessionDto.getMac());
		sessione.setPassword(sessionDto.getPassword());
		sessione.setStato(sessionDto.getStato());
		sessione.setAbi(sessionDto.getAbi());
		sessione.setIndirizzo(sessionDto.getIndirizzo());
		sessione.setComune(sessionDto.getComune());
		sessione.setProvincia(sessionDto.getProvincia());
		sessione.setCap(sessionDto.getCap());
		sessione.setCodicefiscale(sessionDto.getCodiceFiscale());
		sessione.setLapl(sessionDto.getLapl());
		sessione.setSia(sessionDto.getSia());
		sessione.setTipointestatario(sessionDto.getTipoIntestatario());
		sessione.setAbiazienda(sessionDto.getAbiAzienda());
		sessione.setCab(sessionDto.getCab());
		sessione.setAbiaccentratore(sessionDto.getAbiAccentratore());
		sessione.setRagionesocialeazienda(sessionDto.getRagioneSocialeAzienda());
		sessione.setTiposicurezza(sessionDto.getTipoSicurezza());
		sessione.setImportomax(sessionDto.getImportoMax());
		sessione.setAziendasel(sessionDto.getSelCorporate());

		if (alreadyPresent)
			sessioneDao.updateSession(sessione);
		else
			sessioneDao.insertSession(sessione);
	}

	@Override
	public void updateCorporateInSession(String sessionId, String azienda) {
		Sessione sessione = sessioneDao.retrieveSessionById(sessionId);
		sessione.setAzienda(azienda);
		sessioneDao.updateSession(sessione);
	}

	@Override
	public String getDbInfo() {
		StringBuilder dbInfo = new StringBuilder();
		DatabaseMetaData metadata;
		try {

			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/jdbc/iris");
			metadata = ds.getConnection().getMetaData();
			dbInfo.append(metadata.getDatabaseProductName());
			dbInfo.append(" v.");
			dbInfo.append(metadata.getDatabaseMajorVersion());
			dbInfo.append(".");
			dbInfo.append(metadata.getDatabaseMinorVersion());

		} catch (Exception e) {
			LOGGER.error("errore in getDbInfo", e);
			dbInfo.append(e.getMessage());
		}
		return dbInfo.toString();
	}

	@Override
	public ApplicazioniMenu estraiMenuPerCache(String codApplicazione) {

		Applicazioni applicazioni = applicazioniDao.estraiDatiMenu(codApplicazione);
		return ApplicazioniMenuBuilder.buidApplicazioniMenu(applicazioni);

	}

	@Override
	public FrontEndContext getCurrentCorporateForLogin(FrontEndContext fec, String corporate, String operatore) {

		try {

			Intestatari intestatario = intestatariDao.getIntestatarioByCorporateAndOperatore(corporate, operatore);
			CurrentCorporateVOPojo corporatePojo = CurrentCorporateVoBuilder.fromIntestatari(intestatario);
			DTO corporateDTO = new DTOImpl();
			corporateDTO.setPojo(corporatePojo);

			((IrisContextImpl) fec).setAziendaCorrente(corporateDTO);

			return fec;
		} catch (Exception e) {
			LOGGER.error("errore in getCurrentCorporateForLogin", e);
			throw new AuthenticationException("errore in getCurrentCorporateForLogin", e);
		}
	}


	@Override
	public List<String> listEnabledFunctions(String corporate, String operator) {
		return funzionioperatoriDao.listEnabledFunctions(corporate, operator);
	}


	@Override
	public FrontEndContext login(FrontEndContext fec, DTO dto) {

		LoginPojo login = (LoginPojo) dto.getPojo();
		String upperCorporate = login.getCorporateCode().toUpperCase();
		String upperUsername = login.getUsername();

		System.out.println("=============================================================================================== login: username " + upperUsername);
		System.out.println("=============================================================================================== login: corporate " + upperCorporate);

		
		if (Tracer.isInfoEnabled(getClass().getName()))
			Tracer.info(this.getClass().getName(), "login", "BEGIN");

		// Get the operator by Username and corporate ..
		Operatori operator = operatoriDao.getOperatoreByOperatoreAndCorporate(upperUsername, upperCorporate);
		// Now failed login check

		DTOInfo dtoi = new DTOInfo(DTOInfoInterface.SEVERITY_ERROR);

		// operator null check
		if (operator == null) {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "Operator is null");

			dtoi.setCode("error.login.operator.null");
			dto.setInfoList(new DTOInfoList(dtoi));
			fec.setInfo(new DTOInfoList(dtoi));
			return fec;

		} else {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "Operator is not null");
		}
		//
		// user locked check
		//
		if (CommonConstant.BLOCKED_OPERATOR.equals(operator.getLocked())) {

			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " isLocked");
			//
			// first check that this time user have been locked i.e number of
			// failed login exceed
			//
			try {
				int maxLoginFailed = fec.isAdminitratorLoggedIn() ? AppConfiguration.getIntParameter(BackEndConstant.ADMIN_MAX_LOGON_FAILED) : AppConfiguration
						.getIntParameter(BackEndConstant.GENERAL_MAX_LOGON_FAILED);

				if (Tracer.isDebugEnabled(getClass().getName())) {
					Tracer.debug(this.getClass().getName(), "login", "Max failed logons allowed = " + maxLoginFailed);
					Tracer.debug(this.getClass().getName(), "login", operator.getUsername() + "'s num of failed logons = " + operator.getNumFailedlogon());
				}

				if (operator.getNumFailedlogon() != null && operator.getNumFailedlogon().intValue() == maxLoginFailed) {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " has exceeded num of max failed logons allowed");
					dtoi.setCode("error.login.nooffailedLogin.exceed");
				} else {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " is locked");
					dtoi.setCode("error.login.user.locked");
				}
			} catch (Exception e) {
				Tracer.error(getClass().getName(), "errors.login.system", "AppConfiguration Not Initialised");
				dtoi.setCode("errors.login.system");
			}
			dto.setInfoList(new DTOInfoList(dtoi));
			fec.setInfo(new DTOInfoList(dtoi));
			return fec;

		} else {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "User is not locked");

		}

		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " has inserted a correct password");

		fec.setName(operator.getUsername());
		fec.setUsername(operator.getOperatore());
		DTO dtoOper = new DTOImpl();
		dtoOper.setPojo(operator);
		fec.setOperatore(dtoOper);

		//
		// gestione cambio password
		//
		if (!fec.isSSOLogin()) {
			//
			// controllo scadenza password
			//
			if (isPasswordExpired(operator)) {
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(), "login",
							"The password of User " + operator.getUsername() + " is expired on: " + operator.getExpirationDate());
				dtoi.setCode("site.iris.login.password.expired.msg");
				dto.setInfoList(new DTOInfoList(dtoi));
				fec.setInfo(new DTOInfoList(dtoi));

			} else {
				//
				// la password non è scaduta verifico se sono nel periodo in cui
				// mostrare il preavviso di scadenza
				//
				if (showExpirationNotice(fec, operator)) {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(), "login",
								"The password of User " + operator.getUsername() + " will expire on: " + operator.getExpirationDate());
					dtoi.setCode("site.iris.login.password.expired.advance.notice.msg");
					dto.setInfoList(new DTOInfoList(dtoi));
					fec.setInfo(new DTOInfoList(dtoi));

				}
			}
		}

		return fec;
	}


	@Override
	public FrontEndContext loginRT(FrontEndContext fec, DTO dto) {

		if (Tracer.isInfoEnabled(getClass().getName()))
			Tracer.info(this.getClass().getName(), "loginRT", "BEGIN");

		// Get the operator by codiceFiscale
		LoginRTPojo login = (LoginRTPojo) dto.getPojo();
		String upperCodiceFiscale = login.getCodiceFiscale().toUpperCase();
	 			
		
		Operatori operator = operatoriDao.getOperatoreByCodiceFiscale(upperCodiceFiscale);
		if (operator != null) {
			//
			// Esegue il controllo delle credenziali per l'operatore in ingresso
			//
			try {
				checkLogin(login.getPassword(), operator, fec.isAdminitratorLoggedIn(), fec.isSSOLogin());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AuthenticationException("errore in loginRT durante il checkLogin", e);

			}
		}

		//
		// Now failed login check
		//
		DTOInfo dtoi = new DTOInfo(DTOInfoInterface.SEVERITY_ERROR);

		//
		// in caso di login con Single Sign On è necessario creare oppure aggiornare
		// intestatario e operatore
		//
		if (fec.isSSOLogin()) {

			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "Insert new operator");

			operator = this.saveCittadino(login, operator);
			
			if (operator == null) {
				if (Tracer.isDebugEnabled(getClass().getName()))
					Tracer.debug(this.getClass().getName(), "login", "SSO save/update citizen failed");
			}
		}

		//
		// operator null check
		//
		if (operator == null) {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "Operator is null");

			dtoi.setCode("error.login.operator.null");
			dto.setInfoList(new DTOInfoList(dtoi));
			fec.setInfo(new DTOInfoList(dtoi));
			return fec;
		} else if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "login", "Operator is not null");

		//
		// user locked check
		//
		if (CommonConstant.BLOCKED_OPERATOR.equals(operator.getLocked())) {

			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " isLocked");
			// first check that this time user have been locked i.e number of
			// failed login exceed
			try {
				int maxLoginFailed = fec.isAdminitratorLoggedIn() ? AppConfiguration.getIntParameter(BackEndConstant.ADMIN_MAX_LOGON_FAILED) : AppConfiguration
						.getIntParameter(BackEndConstant.GENERAL_MAX_LOGON_FAILED);

				if (Tracer.isDebugEnabled(getClass().getName())) {
					Tracer.debug(this.getClass().getName(), "login", "Max failed logons allowed = " + maxLoginFailed);
					Tracer.debug(this.getClass().getName(), "login", operator.getUsername() + "'s num of failed logons = " + operator.getNumFailedlogon());
				}

				if (operator.getNumFailedlogon() != null && operator.getNumFailedlogon().intValue() == maxLoginFailed) {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " has exceeded num of max failed logons allowed");
					dtoi.setCode("error.login.nooffailedLogin.exceed");
				} else {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " is locked");
					dtoi.setCode("error.login.user.locked");
				}
			} catch (Exception e) {
				Tracer.error(getClass().getName(), "errors.login.system", "AppConfiguration Not Initialised");
				dtoi.setCode("errors.login.system");
			}
			dto.setInfoList(new DTOInfoList(dtoi));
			fec.setInfo(new DTOInfoList(dtoi));
			return fec;
		} else {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "User is not locked");
		}

		if (fec.isSSOLogin() || checkPwdRT(operator, dto)) {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " has inserted a correct password");
			fec.setName(operator.getUsername());
			fec.setUsername(operator.getOperatore());
			DTO dtoOper = new DTOImpl();
			dtoOper.setPojo(operator);

			// inserisce l'operatore nel constesto di frontend
			fec.setOperatore(dtoOper);

			// setta l'operatore in caso di delega
			fec.setOperatoreOriginale(dtoOper);

			//
			// gestione cambio password
			//
			if (!fec.isSSOLogin()) {
				//
				// controllo scadenza password
				//
				if (isPasswordExpired(operator)) {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(), "login",
								"The password of User " + operator.getUsername() + " is expired on: " + operator.getExpirationDate());
					dtoi.setCode("site.iris.login.password.expired.msg");
					dto.setInfoList(new DTOInfoList(dtoi));
					fec.setInfo(new DTOInfoList(dtoi));

				} else {
					//
					// la password non è scaduta verifico se sono nel periodo in
					// cui mostrare il preavviso di scadenza
					//
					if (showExpirationNotice(fec, operator)) {
						if (Tracer.isDebugEnabled(getClass().getName()))
							Tracer.debug(this.getClass().getName(), "login",
									"The password of User " + operator.getUsername() + " will expire on: " + operator.getExpirationDate());
						dtoi.setCode("site.iris.login.password.expired.advance.notice.msg");
						dto.setInfoList(new DTOInfoList(dtoi));
						fec.setInfo(new DTOInfoList(dtoi));

					}
				}
			}

		} else {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "login", "User " + operator.getUsername() + " has inserted a wrong password");
			dtoi.setCode("error.login.authentication.failed");
			dto.setInfoList(new DTOInfoList(dtoi));
			fec.setInfo(new DTOInfoList(dtoi));
		}

		return fec;
	}

	private boolean isPasswordExpired(OperatoriPojo operator) {
		return operator.getExpirationDate() == null || operator.getExpirationDate().before(new Date());
	}

	private boolean showExpirationNotice(FrontEndContext fec, OperatoriPojo operator) {
		int days = 7; // valore di default
		String propName = fec.isAdminitratorLoggedIn() ? BackEndConstant.ADMIN_EXPIRATION_ADVANCE_NOTICE_DAYS
				: BackEndConstant.GENERAL_EXPIRATION_ADVANCE_NOTICE_DAYS;
		try {
			days = AppConfiguration.getIntParameter(propName);
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "showExpirationNotice", "Property " + propName + " NOT FOUND. using default 7days");
		}
		Date advanceNoticeDate = new Date(operator.getExpirationDate().getTime() - days * 24 * 3600 * 1000);
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(), "isCheckExpirationDateEnabled", "advanceNoticeDate: " + advanceNoticeDate);
		return advanceNoticeDate.before(new Date());
	}

	private static boolean checkPwd(String operatorPassword, String loginPassword) {
		if (CryptUtil.isPwdCryptingEnabled()) {
			Tracer.debug(AuthenticationBusinessBean.class.getName(), "checkPwd", "cripting password ABILITATO");
			String cryptedPwd = CryptUtil.encryptPwd(loginPassword);
			return cryptedPwd.equals(operatorPassword);

		} else {
			Tracer.debug(AuthenticationBusinessBean.class.getName(), "checkPwd", "cripting password DISABILITATO");
			return loginPassword.equals(operatorPassword);
		}
	}

	private static boolean checkPwdRT(OperatoriPojo operator, DTO loginDTO) {
		LoginRTPojo loginP = (LoginRTPojo) loginDTO.getPojo();
		return checkPwd(operator.getPassword(), loginP.getPassword());
	}

	private void checkLogin(String password, Operatori oper, boolean isAdministratorLoggedIn, boolean isSSOLogin) throws Exception {

		if ((password != null) && (oper.getPassword() != null) || isSSOLogin) {

			//
			// carico i parametri di configurazione
			//
			Date now = new Date();
			long nowTime = now.getTime();
			int maxLoginFailed;
			int lockDurationMinutes;
			int resetLogonFailedMinutes;
			boolean isOperatore = false;
//			boolean isAmministratore = false;

			if (isAdministratorLoggedIn) {
//				isAmministratore = true;
				maxLoginFailed = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_MAX_LOGON_FAILED);
				lockDurationMinutes = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_LOCK_DURATION_MINUTES);
				resetLogonFailedMinutes = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_RESET_LOGON_FAILED_NUMBER_MINUTES);
			} else {
				isOperatore = true;
				maxLoginFailed = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_MAX_LOGON_FAILED);
				lockDurationMinutes = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_LOCK_DURATION_MINUTES);
				resetLogonFailedMinutes = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_RESET_LOGON_FAILED_NUMBER_MINUTES);
			}

			boolean isAlreadyLocked = BackEndConstant.BLOCKED_OPERATOR.equals(oper.getLocked());
			int failedLogonNumber = oper.getNumFailedlogon() != null ? oper.getNumFailedlogon().intValue() : 0;

			//
			// Se sono già loccato e non sono un amministratore esco subito
			// (non resetto né incremento il conteggio dei login falliti)
			//
			if (isOperatore && isAlreadyLocked) {
				Tracer.error(this.getClass().getName(), "checkLogin", "Operator (OP) is already locked, exiting. Username = " + oper.getUsername()
						+ ", # of failures = " + failedLogonNumber);
				return;
			}

			//
			// verifico se resettare il conteggio dei logon falliti
			//
			if (!isAlreadyLocked && oper.getFailedLogonDate() != null && nowTime - oper.getFailedLogonDate().getTime() > resetLogonFailedMinutes * 60 * 1000) {
				oper.setFailedLogonDate(null);
				oper.setNumFailedlogon(new Integer(0));
				failedLogonNumber = 0;
			}

			//
			// verifico se resettare il blocco dell'account
			//
			if (lockDurationMinutes > 0) {
				//
				// Sono probabilmente un amministratore
				//
				if (oper.getLockDate() != null && nowTime - oper.getLockDate().getTime() > lockDurationMinutes * 60 * 1000) {
					oper.setLockDate(null);
					oper.setLocked(BackEndConstant.VALID_OPERATOR);
					oper.setFailedLogonDate(null);
					oper.setNumFailedlogon(new Integer(0));
					failedLogonNumber = 0;
				}
			}

			//
			// controllo password
			//

			// se la password non è valida per l'operatore in ingresso
			if (!isSSOLogin && !checkPwd(oper.getPassword(), password)) {
				//
				// modalita login "classico" e password check KO
				//
				failedLogonNumber++;
				oper.setNumFailedlogon(new Integer(failedLogonNumber));
				oper.setFailedLogonDate(new Timestamp(now.getTime()));
				if (failedLogonNumber >= maxLoginFailed) {
					oper.setLocked(BackEndConstant.BLOCKED_OPERATOR);
					oper.setLockDate(new Timestamp(now.getTime()));
					Tracer.error(this.getClass().getName(), "checkLogin",
							"Operator has inserted a wrong password too many times. Username = " + oper.getUsername() + ", # of failures = "
									+ failedLogonNumber + " (more or equal than max allowed = " + maxLoginFailed + ")");
				} else {
					Tracer.error(this.getClass().getName(), "checkLogin", "Operator has inserted a wrong password. Username = " + oper.getUsername()
							+ ", # of failures = " + failedLogonNumber + " (less than max allowed = " + maxLoginFailed + ")");
				}

			} else {
				//
				// modalità login SSO oppure
				// password check OK - now update Operatore with the last login
				// datetime for valid operator
				//
				oper.setFailedLogonDate(null);
				oper.setNumFailedlogon(new Integer(0));
				oper.setLastLogon(new Timestamp(now.getTime()));
			}

			operatoriDao.save(oper);

		} else {
			throw new Exception("login password or operator password is null");
		}

	}

	private Operatori saveCittadino(LoginRTPojo login, Operatori operator) {
		Operatori retOpe = null;

		try {
			String upperCodiceFiscale = login.getCodiceFiscale().toUpperCase();

			// gestione cittadino
			if (operator == null) {
				retOpe = creaCittadino(upperCodiceFiscale, login.getNome(), login.getCognome());
			} else {
				retOpe = aggiornaCittadino(operator);
			}

			//
			// gestione deleganti
			//
			List<OperatoriForm> deleg = login.getDeleganti();
			// TODO: eliminare il pojo, usare Operatori
			List<OperatoriPojo> listaDeleganti = new ArrayList<OperatoriPojo>();

			Operatori out;
			if (deleg != null) {
				for (Iterator iterator = deleg.iterator(); iterator.hasNext();) {
					OperatoriForm delegante = (OperatoriForm) iterator.next();
					// verifico l'esistenza del delegante
					Operatori operatorDelegante = operatoriDao.getOperatoreByCodiceFiscale(delegante.getCodiceFiscaleIForm());
					// se non esiste inserisco il cittadino delegante
					if (operatorDelegante == null) {
						out = creaCittadino(delegante.getCodiceFiscaleIForm().toUpperCase(), delegante.getNameIForm(), delegante.getSurnameIForm());
					} else {
						out = aggiornaCittadino(operatorDelegante);
					}
					listaDeleganti.add(out);
				}
			}
			retOpe.setDeleganti(listaDeleganti);

		} catch (Exception e) {
			e.printStackTrace();
			// return this.processException(new DTOImpl(), e,
			// "error.current.corporate");
			LOGGER.error("errore in getCurrentCorporateForLogin", e);
			throw new AuthenticationException("errore in saveCittadino", e);
		}

		return retOpe;
	}

	private Operatori aggiornaCittadino(Operatori operator) throws Exception  {
		Intestatari intes = (Intestatari) operator.getIntestatariobj();
		intestatariDao.updateCorporate(intes);

		operator.setFlagOperatorTypeIForm(TipoOperatore.OP.toString());
		// TODO: return operatorService.insertOrUpdateOperatorSkipControl(fec, in);
		// vedi Operatorserviceimpl.insertAndUpdateOperator
		
		// spostare la chiamata su operatoriDao.insertAndUpdateOperator(operator);
		// oppure su operatorBusiness.insertAndUpadateOperator
		return operatoriDao.save(operator); 
	}

	private Operatori creaCittadino(String upperCodiceFiscale, String nome, String cognome) throws Exception {

		Intestatari intes = fillIntestatario(Categoria.CI.toString(), UUIDGenerator20.generate(), upperCodiceFiscale, nome + " " + cognome);
		intes = intestatariDao.create(intes);
		// verificare se serve chiamare anche updateCorporate
		// N.B. updateCorporate fa delle modifiche all'ente (che in questo caso è null) e chiama SPXAligner 
		intes = intestatariDao.updateCorporate(intes); 

		Operatori operator = fillOperatore(intes, upperCodiceFiscale, nome, cognome);
		// creazione id operatore
		operator.setOperatore(UUIDGenerator20.generate());

		
		// setta la nuova relazione nella intest_oper
		IntestatarioperatoriId intOpeId = new IntestatarioperatoriId();
		intOpeId.setIntestatario(operator.getIntestatariobj().getCorporateIForm());
		intOpeId.setOperatore(operator.getOperatore());
		
		Intestatarioperatori intOpe = new Intestatarioperatori(intOpeId);
		intOpe.setLockedIForm("0");
		//TODO in caso di creazione di un operatore devo recuperare il tipo dall'input?
		intOpe.setTipoOperatore(TipoOperatore.OP.toString());
		intOpe.setIntestatario(intes);
		
		Set<IntestatarioperatoriCommon> intestatariOperatori = new HashSet<IntestatarioperatoriCommon>();
		intestatariOperatori.add(intOpe);
		
		operator.setIntestatarioperatori(intestatariOperatori);
		
		// spostare la chiamata su operatoriDao.insertAndUpdateOperator(operator);
		// oppure su operatorBusiness.insertAndUpadateOperator
		return operatoriDao.create(operator);
	}

	private Intestatari fillIntestatario(String categoria, String corpId, String codiceFiscale, String ragioneSociale) {
		Intestatari intes = new Intestatari();
		intes.setCategoria(categoria);
		intes.setCorporate(corpId);
		intes.setLapl(codiceFiscale);
		intes.setRagionesociale(ragioneSociale);
		intes.setIndirizzipostaliobj(fillIndirizzo(codiceFiscale));
		return intes;
	}

	private IndirizzipostaliCommon fillIndirizzo(String codiceFiscale) {
		IndirizzipostaliCommon indirizzo = new Indirizzipostali();
		indirizzo.setFiscalCodeIForm(codiceFiscale);
		return indirizzo;
	}

	private Operatori fillOperatore(Intestatari intes, String codiceFiscale, String nome, String cognome) {
		Operatori oper = new Operatori();
		oper.setIntestatariobj(intes);
		// oper.setCorporate(corpId);
		oper.setNumFailedlogon(new Integer(0));
		oper.setLocked(new Integer(0));
		oper.setPlainPassword(CommonConstant.DEFAULT_PASSWORD);
		oper.setUsername(codiceFiscale);
		oper.setCodiceFiscale(codiceFiscale);
		oper.setName(nome);
		oper.setSurname(cognome);
		Calendar cal = Calendar.getInstance();
		cal.set(CommonConstant.LONG_EXPIRING_DATE_YYYY, CommonConstant.LONG_EXPIRING_DATE_MM, CommonConstant.LONG_EXPIRING_DATE_DD);
		oper.setExpirationDate(cal.getTime());
		return oper;
	}

	@Override
	public String getCodApplicazioneByCategoria(String categoria) {
		return applicazioniDao.getCodApplicazioneByCategoria(categoria);
	}

	@Override
	public IntestatariCommon getAziendaByCode(String corporate) {
		
		Intestatari intestatario = intestatariDao.find(corporate);
		
		// TODO risolvere questo attacco dei cloni
		return (IntestatariCommon)intestatario.clone();
	}

	@Override
	public void synchTemplate(String operatore, String corporate, String codApplicazione) {
		
		funzionioperatoriDao.cleanFunzionioperatori(corporate, operatore);
		funzioniintestatariDao.cleanFunzioniintestatari(corporate);
		funzioniintestatariDao.synchFunzioniintestatari(corporate, codApplicazione);
		funzionioperatoriDao.synchFunzionioperatori(corporate, operatore, codApplicazione);
		
	}

	@Override
	public List<Applicazioni> getListaApplicazioni() {
		return applicazioniDao.getListaApplicazioni();
	}

	@Override
	public List<String> getListaClassiByApplicazione(String applicazione) {
		return classiAbilitazioniDao.getListaClassiByApplicazione(applicazione);
	}

	@Override
	public List<FunzioniPropDTO> getListaFunzioniByApplicazioneClasse(String applicazione, String classe) {
		return applicazioniDao.getFunzioniByApplicazioneClasse(applicazione, classe);
	}

	@Override
	public void abilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione, String user) {
		classiAbilitazioniDao.abilitaClasseByApplicazioneFunzione(classe, applicazione, funzione, user);
		
	}

	@Override
	public void disabilitaClasseByApplicazioneFunzione(String classe, String applicazione, String funzione) {
		classiAbilitazioniDao.disabilitaClasseByApplicazioneFunzione(classe, applicazione, funzione);
	}
	
	
}
