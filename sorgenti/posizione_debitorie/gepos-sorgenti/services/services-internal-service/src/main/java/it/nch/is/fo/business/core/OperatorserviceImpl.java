package it.nch.is.fo.business.core;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.core.StatelessSessionManager;
import it.nch.fwk.fo.cross.AbstractJavaBean;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.AppConfiguration;
import it.nch.is.fo.BackEndConstant;
import it.nch.is.fo.business.core.dao.OperatorserviceDaoDB;
import it.nch.is.fo.core.interfaces.OperatorserviceInternalService;
import it.nch.is.fo.core.interfaces.OperatorserviceServiceLocal;
import it.nch.is.fo.profilo.ChangePwdVOImpl;
import it.nch.is.fo.profilo.CurrentCorporateVOImpl;
import it.nch.is.fo.profilo.FunzionioperatoriCollectionVOImpl;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.Intestatarioperatori;
import it.nch.is.fo.profilo.IntestatarioperatoriCommon;
import it.nch.is.fo.profilo.IntestatarioperatoriId;
import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.profilo.TributiOperatoriCommon;
import it.nch.is.fo.tributi.TributiOperatore;
import it.nch.is.fo.util.crypto.CryptUtil;
import it.nch.is.fo.uuidgen.UUIDGenerator20;
import it.nch.utility.enumeration.TipoOperatore;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
public class OperatorserviceImpl extends AbstractJavaBean implements
		OperatorserviceServiceLocal, OperatorserviceInternalService {

	OperatorserviceDaoDB daoDB = null;

	public OperatorserviceImpl(OperatorserviceDaoDB daoDB) {

		this.daoDB = daoDB;

	}

	/**
	 * Il metodo controlla se esiste un operatore con quella username nella corporate impostata
	 */
	public DTO checkOperatorByUsernameAndCorporate(FrontEndContext fec,DTO dto) {
		try{
			if(daoDB.checkOperatorByUsernameAndCorporate((BackEndContext)fec, dto)){
				return dto;
			}
			else{
				Operatori operatore = (Operatori) dto.getBusinessObject();
				operatore.setUsernameIForm(null);
				return dto;
			}
		}catch(Exception e){
			Tracer.error(this.getClass().getName(), "checkOperatorByUsernameAndCorporate", e.getMessage(), e);
			return this.processException(dto, e,BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}



	/**
	 * 
	 */
	public DTO getOperatorByCodiceFiscaleInternal(FrontEndContext fec, DTO dto) {
		try {
			OperatoriPojo oper = daoDB.getOperatorByCodiceFiscaleInternal((BackEndContext) fec, dto);
			DTO dtoOperator = new DTOImpl((CommonBusinessObject)oper);
			
//			if (oper != null){
//				HibernateBeanReplicator r = new Hibernate3BeanReplicator();
//				OperatoriCommon opCom = r.copy(oper,OperatoriCommon.class);
//				dtoOperator.setBusinessObject(opCom);
//			}
			
			return dtoOperator;
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getOperatorByCodiceFiscale", e.getMessage(), e);
			return this.processException(dto, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}



	public DTO insertOrUpdateOperatorSkipControl(FrontEndContext fec, DTO dto) {
		return insertAndUpdateOperator(fec, dto, true);
	}

	public DTO insertAndUpdateOperator(FrontEndContext fec, DTO dto) {
		return insertAndUpdateOperator(fec, dto, false);
	}
	
	public DTO updateOperator(FrontEndContext fec, DTO dto) {
		return updateOperator(fec, dto, false);
	}


	/***************************************************************************
	 * Tested ok by Goutam on 1st Sep 2006 insertAndUpdate Operator
	 * mino - aggiunto controllo password e crypting prima del salvataggio
	 */
	private DTO insertAndUpdateOperator(FrontEndContext fec, DTO dto, boolean isAnActivation) {

		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "insertAndUpdateOperator", "BEGIN - isAnActivation: " + isAnActivation);

			Operatori operatori = (Operatori) dto.getBusinessObject();

			if (operatori == null) {
				Tracer.error(this.getClass().getName(), "insertAndUpdateOperator", "Operator is null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

			if (operatori.getIntestatariobj()== null) {
				Tracer.error(this.getClass().getName(), "insertAndUpdateOperator", "Corporate Should not be null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

			boolean isNewOperator = false;
			
			DTO opeDTO = null;
		
			if (operatori.getOperatore() != null) {
				opeDTO = getOperatorByCode(fec, dto);
			}
			Operatori opeToSave = null;
			boolean isNewTipoOperatore = false;
			// caso di nuovo operatore
			if (opeDTO == null || (opeDTO.getInfoList() != null && opeDTO.getInfoList().size() > 0)) {
				opeToSave = operatori;
				
				//creazione id operatore
				opeToSave.setOperatoreIForm(UUIDGenerator20.generate());
				opeToSave.setDescription(operatori.getDescription());
				//setta la nuova relazione nella intest_oper
				IntestatarioperatoriId intOpeId = new IntestatarioperatoriId();
				intOpeId.setIntestatario(opeToSave.getIntestatariobj().getCorporateIForm());
				intOpeId.setOperatore(opeToSave.getOperatoreIForm());
				Intestatarioperatori intOpe = new Intestatarioperatori(intOpeId);
				intOpe.setLockedIForm("0");
				//TODO in caso di creazione di un operatore devo recuperare il tipo dall'input?
				intOpe.setTipoOperatore(TipoOperatore.OP.toString());
				HashSet<IntestatarioperatoriCommon> intestatariOperatori = new HashSet();
				intestatariOperatori.add(intOpe);
				opeToSave.setIntestatarioperatori(intestatariOperatori);

				isNewOperator = true;
			}
			else{ //caso di aggiornamento
				opeToSave = (Operatori)opeDTO.getBusinessObject();
				
				//intestarioperatori non pu� essere null!!!!!
				if (opeToSave.getIntestatarioperatoriIForm() == null)
					return this.processException(dto, BackEndConstant.OPERATOR_WITHOUT_INTESTOPER_LINK);

				//valorizzo i dati da modificare
				//opeToSave.setLockedIForm(operatori.getLockedIForm());
				//opeToSave.setFlagOperatorTypeIForm(operatori.getFlagOperatorTypeIForm());
				
				opeToSave.setDescription(operatori.getDescription());
				//valorizzo la collection di intestatarioperatori settando il flag operatore in base al corporate che ricevo in input
				Collection intOperatori = opeToSave.getIntestatarioperatori();
				for (Iterator iterator = intOperatori.iterator(); iterator.hasNext();) {
					IntestatarioperatoriCommon intOpe = (IntestatarioperatoriCommon) iterator.next();
					if (intOpe.getIntestatariobjIForm().getCorporateIForm().equals(operatori.getCorporateIForm())){
						isNewTipoOperatore = !intOpe.getTipoOperatoreIForm().equals(operatori.getFlagOperatorTypeIForm());
						intOpe.setTipoOperatoreIForm(operatori.getFlagOperatorTypeIForm());
						intOpe.setLockedIForm(operatori.getLockedIForm());
						
					}
				}
				
			}
			ArrayList<String> tributiOperatore = new ArrayList<String>();
			Collection <TributiOperatore> tos = new HashSet<TributiOperatore>();
			if(operatori.getFlagTributiAmmessi()!=null) {
				if(operatori.getFlagTributiAmmessi().equals("Y")) {
					Set<TributiOperatoriCommon> tocs = operatori.getTributiAmmessiCollection();
					
					
					for(TributiOperatoriCommon toc: tocs) {
						TributiOperatore to = new TributiOperatore();
						to.setCdTrbEntePk(toc.getCdTrbEntePk());
						to.setIdEntePk(toc.getIdEntePk());
						to.setIntestatarioPk(toc.getIntestatarioPk());
						to.setOperatorePk(toc.getOperatorePk());
						to.setTipoOperatore(toc.getTipoOperatore());
						to.setOpInserimento(fec.getName());
						to.setTsInserimento(new Date());
						tos.add(to);
						if(!isNewTipoOperatore)
							tributiOperatore.add(toc.getCdTrbEntePk());
					}
				}
			}
			String tipoOperatore = operatori.getFlagOperatorTypeIForm();
			String intestatario = operatori.getCorporateIForm();
			String operatore =  operatori.getOperatore();
			String idEnte = operatori.getEnteIForm();
			this.daoDB.deleteTributiOperatoriAndInsertNew((BackEndContext) fec, tos,  tipoOperatore, intestatario, operatore, idEnte, tributiOperatore);
			
			
//			if (operatori.getOperatore() == null || operatori.getOperatore().equals("")) {
//				operatori.setOperatore(null);
//				operatori.setLastLogon(null);
//				isNewOperator = true;
//			}

//			if (this.verifyAndSetOperatorData(fec, dto, isAnActivation)) {
//				operatori = this.daoDB.insertOrUpdate((BackEndContext) fec, operatori);
//				//
//				// la password � cambiata --> la salvo nello storico
//				// (dopo aver inserito l'operatore altrimenti l'id � null)
//				//
//				Password password = new Password();
//				password.setChangeDate(new Date());
//				password.setOperator_id(operatori.getOperatore());
//				password.setPassword(operatori.getPassword());
//
//				daoDB.insertOrUpdatePassword((BackEndContext) fec, password);
//
//			} else {
//				// la password non � cambiata
//				// verifico se per motivi applicativi ...
//				if (dto.getInfoList() != null && dto.getInfoList().size() > 0) {
//					operatori.setIntestatariobj(new Intestatari()); // solo per non avere nullpointeexc
//					return dto;
//				}
//				// no problem. password invariata
//				operatori = this.daoDB.insertOrUpdate((BackEndContext) fec, operatori);
//			}
			

			// eliminato il controllo della password
			Operatori operatoriRet = null;
			operatoriRet = this.daoDB.insertOrUpdate((BackEndContext) fec, opeToSave);			


			// insert intestatari-operatori in caso di nuovo operatore
//			if (isNewOperator) {
//				
//				//operatoriRet = daoDB.insertIntestatariOperatori((BackEndContext) fec, opeToSave, TipoOperatore.OP.toString());
//
//				// insert operator enabled default functions
//				Funzionioperatori funzionioperatori = new Funzionioperatori();
//				funzionioperatori.setFunctionCode("XA3");
//
//
//				Collection funzionioperatoriCollection = new ArrayList();
//				funzionioperatoriCollection.add(funzionioperatori);
//
//				String corporate = operatoriRet.getCorporate();
//				String operator = operatoriRet.getOperatore();
//
//				boolean isOperatorFnInserted = this.daoDB.deleteOperatorFunctionsAndInsertNewFunctions((BackEndContext) fec, funzionioperatoriCollection,
//						corporate, operator);
//
//				if (!isOperatorFnInserted) {
//					this.daoDB.delete((BackEndContext) fec, operatoriRet);
//					//throw new RuntimeException("Failure in inserting data in FunzioniOperatori table for a new Operator");
//					return this.processException(dto, BackEndConstant.OPERATOR_ERROR_INSERT_FUNCTIONS);
//				}
//			}

			return operatoriRet.incapsulateBO();

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertAndUpdateOperator", e.getMessage(), e);
			return this.processException(dto, e, BackEndConstant.INSERT_ERROR_MESSAGE);
		}

	}
	
	/**
	 * Assegna un cittadino ad un intestatario creando un nuovo operatore
	 * @param fec
	 * @param dto
	 * @param isAnActivation
	 * @return
	 */
	public DTO assignNewOperator(FrontEndContext fec, DTO dto){
		
		Operatori operatori;
		
		try {

			operatori = (Operatori) dto.getBusinessObject();

			if (operatori == null) {
				Tracer.error(this.getClass().getName(), "assignNewOperator", "Operator is null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

			if (operatori.getCorporateIForm()== null) {
				Tracer.error(this.getClass().getName(), "assignNewOperator", "Corporate Should not be null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}
			
			DTO opeDTO = null;
			
			if (operatori.getOperatore() != null) {
				opeDTO = getOperatorByCode(fec, dto);
			}
			
			Operatori opeToSave = (Operatori)opeDTO.getBusinessObject();
			
			
			opeToSave.setDescription(operatori.getDescription());
			
			Operatori operatoriRet = null;
			operatoriRet = this.daoDB.insertOrUpdate((BackEndContext) fec, opeToSave);		
		
			//setta la nuova relazione nella intest_oper
			IntestatarioperatoriId intOpeId = new IntestatarioperatoriId();
			intOpeId.setIntestatario(operatori.getCorporateIForm());
			intOpeId.setOperatore(operatori.getOperatore());
			Intestatarioperatori intOpe = new Intestatarioperatori(intOpeId);
			intOpe.setTipoOperatore(operatori.getFlagOperatorTypeIForm());
			
			HashSet<IntestatarioperatoriCommon> intestatariOperatori = new HashSet();
			intestatariOperatori.add(intOpe);
			operatori.setIntestatarioperatori(intestatariOperatori);

			daoDB.insertIntestatariOperatori((BackEndContext) fec, intOpe,intOpe.getTipoOperatore());			

			

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "assignNewOperator", e.getMessage(), e);
			return this.processException(dto, e, BackEndConstant.INSERT_ERROR_MESSAGE);
		}
		return operatori.incapsulateBO();
	}

	/**
	 * 
	 * @param fec
	 * @param dto
	 * @return
	 */
	public DTO deAssignNewOperator(FrontEndContext fec, DTO dto){
		try {

			Operatori operatori = (Operatori) dto.getBusinessObject();

			if (operatori == null) {
				Tracer.error(this.getClass().getName(), "deAssignNewOperator", "Operator is null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

			if (operatori.getCorporateIForm()== null) {
				Tracer.error(this.getClass().getName(), "deAssignNewOperator", "Corporate Should not be null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}
			
			// prima di cancellare 
			this.daoDB.deleteIntestatariOperatori((BackEndContext) fec, operatori.getOperatoreIForm(), operatori.getCorporateIForm());			

			return operatori.incapsulateBO();

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "deAssignNewOperator", e.getMessage(), e);
			return this.processException(dto, e, BackEndConstant.DELETE_ERROR_MESSAGE);
		}
	}

		
	private DTO updateOperator(FrontEndContext fec, DTO dto, boolean isAnActivation) {

		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "updateOperator", "BEGIN - isAnActivation: " + isAnActivation);

			Operatori operatori = (Operatori) dto.getBusinessObject();

			if (operatori == null) {
				Tracer.error(this.getClass().getName(), "updateOperator", "Operator is null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

			if (operatori.getOperatore() == null || operatori.getPassword() == null) {
				Tracer.error(this.getClass().getName(), "updateOperator", "Operatore and password Should not be null!");
				return this.processException(dto, BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

//			boolean isNewOperator = false;
//			// in case of a new Operator
//			if (operatori.getOperatore() == null || operatori.getOperatore().equals("")) {
//				operatori.setOperatore(null);
//				operatori.setLastLogon(null);
//				isNewOperator = true;
//			}

			this.daoDB.updateOperator((BackEndContext) fec, operatori);
			return operatori.incapsulateBO();

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "insertAndUpdateOperator", e.getMessage(), e);
			return this.processException(dto, e, BackEndConstant.INSERT_ERROR_MESSAGE);
		}

	}

//	private boolean verifyAndSetOperatorData(FrontEndContext fec, DTO dto, boolean skipControl) {
//
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "verifyAndSetOperatorData", "");
//
//			Operatori oper = (Operatori) dto.getBusinessObject();
//			String plainPassword = oper.getPlainPassword();
//			String encryptedPwd = null;
//
//			if ( (plainPassword == null || plainPassword.length() == 0) ) {
//				//
//				// la password in chiaro � vuota
//				// non voglio cambiare la password ma faccio update degli altri campi
//				//
//				String savedPassword = oper.getPassword();
//				if (savedPassword == null || savedPassword.length() == 0) {
//					// la password proveniente da DB � vuota e anche quella in chiaro ... qualcosa non va ...
//					Exception e = new Exception("Password and PlainPassword are both empty.");
//					dto = this.processException(dto, e, "error.operator.changePwd.wrongParameter");
//				}
//				return false;
//
//			} else {
//				//
//				// la password in chiaro non � vuota
//				// prima di fare update dell'operatore faccio i controlli
//				//
//				encryptedPwd = CryptUtil.isPwdCryptingEnabled() ? CryptUtil.encryptPwd(plainPassword) : plainPassword;
//
//				if (!skipControl) {
//					//
//					// carico i parametri di configurazione
//					//
//					int pwdMinLength;
//					int pwdMinNoAlphaChars;
//					int pwdHistorySize;
//					if (fec.isAdminitratorLoggedIn()) {
//						pwdMinLength = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PWD_MIN_LENGTH);
//						pwdMinNoAlphaChars = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PWD_MIN_NOALPHA_CHARS);
//						pwdHistorySize = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PASSWORD_HISTORY_SIZE);
//					} else {
//						pwdMinLength = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PWD_MIN_LENGTH);
//						pwdMinNoAlphaChars = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PWD_MIN_NOALPHA_CHARS);
//						pwdHistorySize = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PASSWORD_HISTORY_SIZE);
//					}
//					//
//					// controllo lunghezza
//					//
//					if (plainPassword.length() < pwdMinLength) {
//						Exception e = new Exception("Wrong input parameter");
//						dto = this.processException(dto, e, "error.operator.changePwd.wrongParameter");
//						return false;
//					}
//					//
//					// controllo robustezza (caratteri non alfanumerici)
//					//
//					Pattern p = Pattern.compile("\\W");  // caratteri non alfanumerici
//				    Matcher m = p.matcher(plainPassword);
//					int noAlphaCharsNumber = 0;
//				    boolean b = false;
//				    while(b = m.find()) {
//				    	noAlphaCharsNumber++;
//				    }
//					if (Tracer.isDebugEnabled(getClass().getName())) {
//						Tracer.debug(this.getClass().getName(), "verifyAndSetOperatorData", "la password contiene " + noAlphaCharsNumber + "caratteri non alfanumerici");
//					}
//					if (noAlphaCharsNumber < pwdMinNoAlphaChars) {
//						Exception e = new Exception("Password does not contain enough no-alpha characters");
//						dto = this.processException(dto, e, "error.operator.changePwd.notSecure");
//						return false;
//					}
//
//					//
//					// non posso ripetere le ultime 'pwdHistorySize' password inserite
//					//
//					List pwdList = daoDB.listHistoryPassword((BackEndContext) fec, oper, fec.isAdminitratorLoggedIn());
//
//					String historyPwd;
//					for (int index = 0; index < pwdHistorySize && index < pwdList.size(); index++ ) {
//						historyPwd = (String) pwdList.get(index);
//						if (historyPwd.equals(encryptedPwd)) {
//							Exception e = new Exception("This password was already used in the last " + pwdHistorySize + " times.");
//							dto = this.processException(dto, e, "error.operator.changePwd.alreadyUsed");
//							return false;
//						}
//					}
//				}
//
//				//
//				// ho superato tutti i controlli posso settare la password e la nuova data di scadenza e salvare
//				// in questo caso la data scadenza = oggi
//				//
//				oper.setExpirationDate(getUpdateExpDate(0));
//				oper.setPassword(encryptedPwd);
//
//			}
//			dto.setBusinessObject(oper);
//			return true;
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "insertOrUpdateOperatorAndPassword", e.getMessage(), e);
//			dto = this.processException(dto, e, "error.operator.changePwd.failed");
//			return false;
//		}
//	}

	private Date getUpdateExpDate(int pwdDurationDays) {
		// expiration date = data odierna + ngiorni durata
		Calendar newExpDate = Calendar.getInstance();
		newExpDate.set(Calendar.HOUR, 0);
		newExpDate.set(Calendar.MINUTE, 0);
		newExpDate.set(Calendar.SECOND, 0);
		newExpDate.set(Calendar.MILLISECOND, 0);
		Tracer.debug(this.getClass().getName(), "insertOrUpdateOperatorAndPassword", "today: " + newExpDate.getTime());
		newExpDate.add(Calendar.DATE, pwdDurationDays);
		Date expirationDate = newExpDate.getTime();
		Tracer.debug(this.getClass().getName(), "insertOrUpdateOperatorAndPassword", "new expiration date: " + expirationDate);
		return newExpDate.getTime();

	}

	/***************************************************************************
	 *
	 * @param fec
	 * @param dto
	 * @return
	 */

	public DTO insertAndUpdateOperatorFunctions(FrontEndContext fec, DTO dto) {
		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(),
					"insertAndUpdateOperatorFunctions", "", "");

			FunzionioperatoriCollectionVOImpl funzionioperatoriCollectionVO = (FunzionioperatoriCollectionVOImpl) dto
					.getBusinessObject();

			String corporate = funzionioperatoriCollectionVO.getCorporate();
			String operator = funzionioperatoriCollectionVO.getOperatore();

			if ((corporate != null && corporate.trim().length() > 0)
					&& (operator != null && operator.trim().length() > 0)) {

				Collection funzionioperatori = funzionioperatoriCollectionVO
						.getFunzionioperatori();

				if (funzionioperatori == null || funzionioperatori.size() == 0) {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(),
							"insertAndUpdateOperatorFunctions", "funzionioperatori is null call deleteOperatorFunctions", "");
					daoDB.deleteOperatorFunctions((BackEndContext) fec,
							corporate, operator);
				} else {
					if (Tracer.isDebugEnabled(getClass().getName()))
						Tracer.debug(this.getClass().getName(),
							"insertAndUpdateOperatorFunctions", "funzionioperatori is not null call deleteOperatorFunctionsAndInsertNewFunctions", "");
					daoDB.deleteOperatorFunctionsAndInsertNewFunctions(
							(BackEndContext) fec, funzionioperatori, corporate,
							operator);
				}
				// return the operator object if operation successful
				Operatori obj = new Operatori();
				obj.setOperatore(operator);
				return daoDB.load((BackEndContext) fec, obj).incapsulateBO();
			} else {
				Tracer.error(this.getClass().getName(),
						"insertAndUpdateOperatorFunctions",
						"Insufiicient Param", "");
				return this.processException(dto,
						BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(),
					"insertAndUpdateOperatorFunctions", e.getMessage(), e);
			return this.processException(dto, e,
					BackEndConstant.INSERT_ERROR_MESSAGE);
		}

	}//



	/***************************************************************************
	 * Tested ok by Goutam on 1st Sep 2006 retun all the functions(irrespective
	 * of any right for that operator) with a mark whether the operator have
	 * right in that function or not.If Funzionioperatori.operatore identify the
	 * mark
	 *
	 * @param fec
	 * @param dto
	 * @return
	 */
//	public DTOCollection listOperatorFunctionsByOperatorAndCorporate(
//			FrontEndContext fec, DTO dto) {
//
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(),
//					"listOperatorFunctionsByOperatorAndCorporate", "");
//
//			Operatori operatori = (Operatori) dto.getBusinessObject();
//			if (operatori.getOperatore().trim().length() > 0
//					&& operatori.getCorporate().trim().length() > 0) {
//				return daoDB.listOperatorFunctionsByOperatorAndCorporate(
//						(BackEndContext) fec, dto);
//			} else {
//				Tracer.error(this.getClass().getName(),
//						"listOperatorFunctionsByOperatorAndCorporate",
//						"Insufficient Param!");
//				return this.processException(new DTOCollectionImpl(),
//						BackEndConstant.INSUFFICIENT_INPUT_PARAM);
//			}
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(),
//					"listOperatorFunctionsByOperatorAndCorporate", e
//							.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e,
//					BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//
//	}//

//	/***************************************************************************
//	 * Tested ok by Goutam on 1st Sep 2006
//	 */
//	public DTOCollection listSignersByCorporateCode(FrontEndContext fec, DTO dto) {
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(),
//					"listSignersByCorporateCode", "", "");
//
//			Intestatari intestatari = (Intestatari) dto.getBusinessObject();
//
//			if (intestatari != null
//					&& intestatari.getCorporate().trim().length() > 0) {
//				return daoDB.listSignersByCorporateCode((BackEndContext) fec,
//						intestatari.getCorporate());
//			} else {
//				Tracer.error(this.getClass().getName(),
//						"listSignersByCorporateCode", "Insufficient Param", "");
//				return this.processException(new DTOCollectionImpl(),
//						BackEndConstant.INSUFFICIENT_INPUT_PARAM);
//			}
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(),
//					"listSignersByCorporateCode", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e,
//					BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//
//	}//
//	//

	public DTOCollection listOperatorsByCriteria(FrontEndContext fec, DTO dto) {
		DTOCollection coll = new DTOCollectionImpl(null, false);
		try {
			coll = daoDB.listOperatorsByCriteria((BackEndContext)fec, dto);
			return coll;
		} catch (Exception e) {
			processException(coll, e,BackEndConstant.SEARCH_ERROR_MESSAGE);
			return coll;
		}
	}

	/***************************************************************************
	 * Tested ok by Goutam on 1st Sep 2006 Corporate code is optional for this
	 * search.So that it will return operatore lsit irrespective of any
	 * corporate
	 */
/*	public DTOCollection listOperatorsByCriteria(FrontEndContext fec, DTO dto) {

		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "listOperatorsByCriteria",
					"", "");

			StatelessSessionManager sm = ((BackEndContext) fec)
					.getStatelessSessionManager();

			Operatori operatori = (Operatori) dto.getBusinessObject();

			// if (operatori.getCorporate()!=null &&
			// operatori.getCorporate().trim().length()>0){

			Criteria criteria = sm.createHibernateCriteria(Operatori.class);

			// This is mandatory condition
			criteria.add(Restrictions.eq("flagOperatorType",
					BackEndConstant.TYPE_GENERAL_OPERATOR));
			// ////////////////

			if (operatori.getCorporate() != null
					&& operatori.getCorporate().trim().length() > 0) {

				// bug 1670 aggiunto toUpperCase
				criteria.add(Restrictions.eq("corporate", operatori.getCorporate().toUpperCase()));
			}

			if (operatori.getUsername() != null
					&& operatori.getUsername().trim().length() > 0) {

				//criteria.add(Restrictions.eq("username", operatori
					//	.getUsername()));
				criteria.add(Restrictions.sqlRestriction("upper({alias}.username)=?", operatori.getUsername().toUpperCase(),new StringType()));

			}

			if (operatori.getName() != null
					&& operatori.getName().trim().length() > 0) {

				criteria.add(Restrictions.eq("name", operatori.getName()));
			}

			if (operatori.getDescription() != null
					&& operatori.getDescription().trim().length() > 0) {

				criteria.add(Restrictions.eq("description", operatori
						.getDescription()));
			}
			if (operatori.getSignerCode() != null
					&& operatori.getSignerCode().trim().length() > 0) {

				criteria.add(Restrictions.eq("signerCode", operatori
						.getSignerCode()));
			}

			DTOCollection collDto = null;
			if (dto.getPagingCriteria()!=null){

				collDto = getPagedQueryByCriteria(sm, dto.getPagingCriteria(), criteria); //, paramsQueryCount
			}else
				 collDto = new DTOCollectionImpl(criteria.list(), true);

			return collDto;
			//Collection result = criteria.list();

			//return new DTOCollectionImpl(result, true);

			// }else {
			// Tracer.error(this.getClass().getName(),
			// "listOperatorsByCriteria",
			// "invalid Param");
			// return this.processException(new
			// DTOCollectionImpl(),BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			// }

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listOperatorsByCriteria",
					e.getMessage(), e);
			return this.processException(new DTOCollectionImpl(), e,
					BackEndConstant.SEARCH_ERROR_MESSAGE);
		}

	}//
*/
	/***************************************************************************
	 * Tested ok by Goutam on 1st Sep 2006 load Corporate by PK
	 */
	public DTO getOperatorByCode(FrontEndContext fec, DTO dto) {
		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer
					.debug(this.getClass().getName(), "getOperatorByCode", "",
							"");
			Operatori operatori = (Operatori) dto.getBusinessObject();
			return daoDB.load((BackEndContext) fec, operatori).incapsulateBO();
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getOperatorByCode", e
					.getMessage(), e);
			return this.processException(dto, e,
					BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}//

	/***************************************************************************
	 * Delete a Operator and returns operator list for a corporate ,if Success
	 * Tested ok by Goutam on 1st Sep 2006
	 */
	public DTOCollection deleteOperator(FrontEndContext fec, DTO dto) {
		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "deleteOperator", "", "");
			Operatori operatori = (Operatori) dto.getBusinessObject();

			daoDB.delete((BackEndContext) fec, operatori);
			operatori = new Operatori();

			return listOperatorsByCriteriaForACorporate(fec, operatori
					.incapsulateBO());

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "deleteOperator", e
					.getMessage(), e);
			return this.processException(new DTOCollectionImpl(), e,
					BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}//


	/***************************************************************************
	 * Tested ok by Goutam on 1st Sep 2006 This will call the
	 * listOperatorsByCriteria method but set the corporate code from the
	 * context object.So for this method corporate is mandatory
	 */
	public DTOCollection listOperatorsByCriteriaForACorporate(
			FrontEndContext fec, DTO dto) {
		if (Tracer.isDebugEnabled(getClass().getName()))
			Tracer.debug(this.getClass().getName(),
				"listOperatorsByCriteriaForACorporate", "", "");
		try {

			StatelessSessionManager sm = ((BackEndContext) fec)
					.getStatelessSessionManager();
			Operatori operatori = (Operatori) dto.getBusinessObject();
			if (operatori.getCorporate() == null
					|| operatori.getCorporate().trim().length() == 0) {

				DTO aziendaDTO = fec.getAziendaCorrente();
				CurrentCorporateVOImpl aziendaVO = (CurrentCorporateVOImpl) aziendaDTO
						.getPojo();

				operatori.setCorporate(aziendaVO.getCorporate());
			}
			return listOperatorsByCriteria(fec, operatori.incapsulateBO());
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(),
					"listOperatorsByCriteriaForACorporate", e.getMessage(), e);
			return this.processException(new DTOCollectionImpl(), e,
					BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}// /

//	public DTO test(FrontEndContext fec, DTO dto) {
//
//		return null;
//	}

	/***************************************************************************
	 * Tested ok by Goutam on 1st Sep 2006 returns the Admin Operator for a
	 * corporate
	 */

	public DTO getAdminOperator(FrontEndContext fec, DTO dto) {
		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "getAdminOperator", "");

			Intestatari intestatari = (Intestatari) dto.getBusinessObject();
			Collection col = this.daoDB.getAdminOperator((BackEndContext) fec,
					intestatari.getCorporate());
			if (col.size() > 1) {
				return this.processException(dto,
						BackEndConstant.SEARCH_ERROR_MESSAGE);
			}
			Operatori operatori = new Operatori();
			Iterator it = col.iterator();
			while (it.hasNext()) {
				operatori = (Operatori) it.next();
			}
			return operatori.incapsulateBO();
		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "getAdminOperator", e
					.getMessage(), e);
			return this.processException(dto, e,
					BackEndConstant.SEARCH_ERROR_MESSAGE);
		}

	}// /


	/* (non-Javadoc)
	 * @see it.nch.is.fo.core.interfaces.OperatorserviceServiceLocal#changePwd(it.nch.fwk.fo.interfaces.FrontEndContext, it.nch.fwk.fo.dto.DTO)
	 */
	public DTO changePwd(FrontEndContext fec, DTO dto) {

		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "changePwdNewPolicy", "check credential");

			ChangePwdVOImpl pwd = (ChangePwdVOImpl) dto.getBusinessObject();
			String oldPwd = pwd.getOldPwd();
			String newPwd1 = pwd.getNewPwd1();
			String newPwd2 = pwd.getNewPwd2();

			// Check not allowed situation:
			if (pwd.getOperatore() == null || pwd.getOperatore().equals("")) 
				
				throw new Exception("Missing operatore in ChangePwdVOImpl Obj");
			
			
			Operatori oper = new Operatori();
			oper.setOperatore(pwd.getOperatore());

			oper = daoDB.load((BackEndContext) fec, oper);
			//
			// carico i parametri di configurazione
			//
			int pwdMinLength;
			int pwdMinNoAlphaChars;
			int pwdDurationDays;
			int pwdHistorySize;
			if (fec.isAdminitratorLoggedIn()) {
				pwdMinLength = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PWD_MIN_LENGTH);
				pwdMinNoAlphaChars = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PWD_MIN_NOALPHA_CHARS);
				pwdDurationDays = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PASSWORD_DURATION_DAYS);
				pwdHistorySize = AppConfiguration.getIntParameter(BackEndConstant.ADMIN_PASSWORD_HISTORY_SIZE);
			} else {
				pwdMinLength = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PWD_MIN_LENGTH);
				pwdMinNoAlphaChars = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PWD_MIN_NOALPHA_CHARS);
				pwdDurationDays = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PASSWORD_DURATION_DAYS);
				pwdHistorySize = AppConfiguration.getIntParameter(BackEndConstant.GENERAL_PASSWORD_HISTORY_SIZE);
			}
			//
			// controllo lunghezza
			//
			if (oldPwd == null || oldPwd.equals("")
					|| newPwd1 == null || newPwd1.length() < pwdMinLength
					|| newPwd2 == null || newPwd2.length() < pwdMinLength) {
				Exception e = new Exception("Wrong input parameter in password fields");
				return this.processException(dto, e, "error.operator.changePwd.wrongParameter");
			}
			//
			// controllo campo conferma
			//
			if (!newPwd1.equals(newPwd2)) {
				Exception e = new Exception("New password are NOT confirmed correcly");
				return this.processException(dto, e, "error.operator.changePwd.wrongConfirm");
			}
			//
			// controllo robustezza (caratteri non alfanumerici)
			//
//			Pattern p = Pattern.compile("\\W");  // caratteri non alfanumerici
			Pattern p = Pattern.compile("[^a-zA-Z0-9]"); // caratteri non alfanumerici
		    Matcher m = p.matcher(newPwd1);
			int noAlphaCharsNumber = 0;
		    boolean b = false;
		    while(b = m.find()) {
		    	noAlphaCharsNumber++;
		    }
			if (Tracer.isDebugEnabled(getClass().getName())) {
				Tracer.debug(this.getClass().getName(), "changePwdNewPolicy", "la password contiene " + noAlphaCharsNumber + "caratteri non alfanumerici");
			}
			if (noAlphaCharsNumber < pwdMinNoAlphaChars) {
				Exception e = new Exception("Password does not contain enough no-alpha characters");
				return this.processException(dto, e, "error.operator.changePwd.notSecure");
			}
			//
			// controllo vecchia password
			//
			boolean isCriptingEnabled = CryptUtil.isPwdCryptingEnabled();

			String encryptedOldPwd = null;

			if (isCriptingEnabled) {
				Tracer.debug("OperatorserviceImpl", "changePwdNewPolicy", "cripto la password con il vecchio algoritmo");
				encryptedOldPwd = CryptUtil.encryptPwd(oldPwd);
			} else {
				Tracer.debug("OperatorserviceImpl", "changePwdNewPolicy", "cripting password DISABILITATO");
				encryptedOldPwd = oldPwd;
			}

			if (!oper.getPassword().equals(encryptedOldPwd)) {
				Tracer.warn("OperatorserviceImpl", "changePwdNewPolicy", "Utente consolidato, facendo il cambio password l'utente ha messo una vecchia password sbagliata");
				return this.processException(dto, new Exception("Wrong old pwd!"), "error.operator.changePwd.wrongOldPwd");
			} else {
				Tracer.debug("OperatorserviceImpl", "changePwdNewPolicy", "Utente consolidato, facendo il cambio password l'utente ha fatto tutto giusto!");
			}


			//
			// non posso ripetere le ultime 'pwdHistorySize' password inserite
			//
			String encryptedPwd = isCriptingEnabled ? CryptUtil.encryptPwd(newPwd1) : newPwd1;
			
			//List<String> pwdList = daoDB.listHistoryPassword((BackEndContext) fec, oper, fec.isAdminitratorLoggedIn());
			

//			String historyPwd;
//			for (int index = 0; index < pwdHistorySize && index < pwdList.size(); index++ ) {
//				historyPwd = (String) pwdList.get(index);
//				Tracer.debug("OperatorserviceImpl", "changePwdNewPolicy", "Comparing (encrypted) history password # " + index + " (" + historyPwd + ") with (encrypted) new one (" + encryptedPwd + ")");
//				if (historyPwd.equals(encryptedPwd)) {
//					Exception e = new Exception("This password was already used in the last " + pwdHistorySize + " times.");
//					return this.processException(dto, e, "error.operator.changePwd.alreadyUsed");
//				}
//			}

			//
			// ho superato tutti i controlli posso settare la password e la nuova data di scadenza e salvare
			//
			oper.setExpirationDate(getUpdateExpDate(pwdDurationDays));

			// nuova password
			oper.setPassword(encryptedPwd);

			oper = daoDB.insertOrUpdate((BackEndContext) fec, oper);  // hibernate
//			daoDB.updateOperatorPassword((BackEndContext) fec, oper);  // instruzione sql diretta

			dto.setBusinessObject(oper);
			return dto;

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "changePwd", e.getMessage(), e);
			return this.processException(dto, e, "error.operator.changePwd.failed");
		}
	}

	public DTOCollection listFunctionsByCorporate(FrontEndContext fec, DTO dto) {
		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(),
					"listOperatorFunctionsByOperatorAndCorporate", "");

			Operatori operatori = (Operatori) dto.getBusinessObject();
			if (operatori.getOperatore().trim().length() > 0
					&& operatori.getCorporate().trim().length() > 0) {
				return daoDB
						.listFunctionsByCorporate((BackEndContext) fec, dto, fec.getTipoIntestatarioperatore());
			} else {
				Tracer.error(this.getClass().getName(),
						"listOperatorFunctionsByOperatorAndCorporate",
						"Insufficient Param!");
				return this.processException(new DTOCollectionImpl(),
						BackEndConstant.INSUFFICIENT_INPUT_PARAM);
			}

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(),
					"listOperatorFunctionsByOperatorAndCorporate", e
							.getMessage(), e);
			return this.processException(new DTOCollectionImpl(), e,
					BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}

//	public DTO getOperatorKeyByUsernameAndCorporate(FrontEndContext fec, DTO dto) {
//	try{
//		String operatorekey = daoDB.getOperatorKeyByUsernameAndCorporate((BackEndContext)fec, dto);
//		Operatori operatore = (Operatori) dto.getBusinessObject();
//		operatore.setOperatoreIForm(operatorekey);
//		return dto;
//
//
//			}catch(Exception e){
//				Tracer.error(this.getClass().getName(), "getOperatorKeyByUsernameAndCorporate", e.getMessage(), e);
//				return this.processException(new DTOImpl(), e,
//						BackEndConstant.SEARCH_ERROR_MESSAGE);
//			}
//	}
//
// FATTO
//	public DTOCollection listEnabledFunctions(FrontEndContext fec, DTO dto) {
//		try {
//
//				return daoDB.newListEnabledFunctions((BackEndContext) fec, dto);
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledFunctions", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}
//
	// FATTO
//	public DTOCollection listEnabledFunctionsWithoutPwdControl(FrontEndContext fec, DTO dto) {
//		try {
//
//				return daoDB.listEnabledFunctions((BackEndContext) fec, dto);
//			}
//		 catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledFunctionsWithoutPwdControl", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}
//
//	public DTOCollection listFunctionsByBackOfficeOperator(FrontEndContext fec, DTO dto) {
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listFunctionsByBackOfficeOperator", "");
//
//			return daoDB.listFunctionsByBackOfficeOperator((BackEndContext) fec, dto);
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listFunctionsByBackOfficeOperator", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}
//
//	public DTOCollection listEnabledFunctionsButtonByBackOfficeOperator(FrontEndContext fec, DTO dto) {
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledFunctionsButtonByBackOfficeOperator", "");
//
//			return daoDB.listEnabledFunctionsButtonByBackOfficeOperator((BackEndContext)fec, dto);
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledFunctionsButtonByBackOfficeOperator", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}
//	public DTOCollection listEnableSubholdings(FrontEndContext fec, DTO dto) {
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "listEnabledSubholdigs", "");
//
//			return daoDB.listEnabledSubholdigs((BackEndContext)fec, dto);
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "listEnabledSubholdigs", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}
//
//	public DTOCollection enableSubholdings(FrontEndContext fec, DTOCollection dto ) {
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "EnableSubholdings", "");
//			Collection toEnable=dto.getCollection();
//			ArrayList List= new ArrayList(toEnable);
//			Iterator itera=List.iterator();
//			while (itera.hasNext())
//			{
//				DTOImpl DTO=(DTOImpl)(itera.next());
//				Intestatarioperatori obj=(Intestatarioperatori)DTO.getBusinessObject();
//				daoDB.insertIntestatariOperatoriSubholding((BackEndContext)fec,obj.getOperatoreobj().getOperatoreIForm(),obj.getIntestatariobjIForm().getCorporateIForm());
//				daoDB.insertFunzioniServiziSubholding((BackEndContext)fec,obj.getOperatoreobj().getOperatoreIForm(),obj.getIntestatariobjIForm().getCorporateIForm());
//				daoDB.insertRapportiSubholding((BackEndContext)fec,obj.getOperatoreobj().getOperatoreIForm(),obj.getIntestatariobjIForm().getCorporateIForm());
//
//			}
//			return new DTOCollectionImpl();
//
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "EnableSubholdings", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}

//	public DTOCollection disableSubholdings(FrontEndContext fec, DTOCollection dto ) {
//		try {
//			if (Tracer.isDebugEnabled(getClass().getName()))
//				Tracer.debug(this.getClass().getName(), "DisableSubholdings", "");
//			Collection toDisable=dto.getCollection();
//			ArrayList List= new ArrayList(toDisable);
//			Iterator itera=List.iterator();
//			while (itera.hasNext())
//			{	DTOImpl dtoIntestatario=(DTOImpl)itera.next();
//				Intestatarioperatori obj=(Intestatarioperatori)(dtoIntestatario.getBusinessObject());
//				daoDB.deleteIntestatariOperatori((BackEndContext)fec,obj.getOperatoreobj().getOperatoreIForm(),obj.getIntestatariobjIForm().getCorporateIForm());
//				daoDB.deleteOperatorFunctions((BackEndContext)fec,obj.getIntestatariobjIForm().getCorporateIForm(),obj.getOperatoreobj().getOperatoreIForm());
//			}
//			return new DTOCollectionImpl();
//		} catch (Exception e) {
//			Tracer.error(this.getClass().getName(), "DisableSubholdings", e.getMessage(), e);
//			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//		}
//	}


	public DTOCollection listOperatorsByCorporate(FrontEndContext fec, DTO dto) {
		try {
			if (Tracer.isDebugEnabled(getClass().getName()))
				Tracer.debug(this.getClass().getName(), "listOperatorsByCorporate", "");

				Intestatari intestatario = (Intestatari) dto.getBusinessObject();
				return daoDB.listOperatorsByCorporateCriteria((BackEndContext)fec, intestatario);

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "listOperatorsByCorporate", e.getMessage(), e);
			return this.processException(new DTOCollectionImpl(), e, BackEndConstant.SEARCH_ERROR_MESSAGE);
		}
	}


}// end class
