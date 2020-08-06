package it.nch.is.fo;



/*
 * Creato il 28-mar-2006
 */

/**
 * @author EE10057
 */
public class BackEndConstant extends CommonConstant{

	/********** Key values for the configuration property file **************/
	public final static String BIR="bonifico.bir";
	public final static String FLOW_MAX_AMOUNT="flow.maxAmount";
	public final static String DISPOSITION_MAX_NUMBER="disposition.maxNumber";
	public static final String BON_DOM_EXP_MAX_AMOUNT = "bonificoDomExp.maxAmount";
	public static final String BON_DOM_MAX_AMOUNT = "bonificoDom.maxAmount";

	public final static String QLISTA_CABBYUSERANDABI = "profilo.listaCabByUserAndAbi";

	//ref. to the field ESITO of IMPORTAZIONE
	public final static String UPLOAD_STATUS_OK="0";
	public final static String UPLOAD_STATUS_ERR="-1";

	//ref. to the field DESCRIZIONEESITO of IMPORTAZIONE
	public final static String UPLOAD_DES="IN CORSO";

	public final static String DEFAULT_CURRENCY="EUR";

	// Flag to enable the functionality in the user menu
	public final static String ABILITAZIONE_GLOBALE = "S";

	public final static String REND_PAYM_FORM_ACCEPT_STATUS="AC";
	public final static String REND_PAYM_FORM_REJECT_STATUS="RIF";

	public final static String BENEFICIARY_CAB="03200";
	public final static String BENEFICIARY_ABI="07601";
	public final static String BENEFICIARY_ACCOUNT="0DOMICILIATI";
	public final static String RIDFILES_ALIGNMENT_DEBTOR_ABI="07601";

	//RidFilesAlignment Causale Constant
	public final static String AAR_CAUSALE_90211="90211";
	public final static String AAR_CAUSALE_90218="90218";
	public final static String AAR_CAUSALE_90219="90219";
	public final static String AAR_CAUSALE_90310="90310";
	public final static String AAR_CAUSALE_90440="90440";
	public final static String AAR_CAUSALE_90450="90450";
	public final static String AAR_CAUSALE_90520="90520";
	public final static String AAR_CAUSALE_90530="90530";
	public final static String NUMMBER_MAX_LOGON_FAILED="max.login.failed";

	public final static String FLOW_STATUS_ACQUISITO_DA_POSTE_CODE="FC";
	public final static String FLOW_STATUS_ACQUISITO_DA_POSTE_DESC="Flusso acquisito";
	public final static String FLOW_STATUS_SCARTATO_DA_POSTE_CODE="FS";
	public final static String RENDICONTAZIONI_TIPO_SERVIZIO_ASSEGNI="UVE";

	public final static String ALL_BENEFICIARY_TYPE="ALL";

	//
	// Gestione cambio password
	//

//	public final static String ENABLE_CHANGE_PWD_MANAGEMENT = "enable.change.pwd.management";

	public final static String ADMIN_PASSWORD_DURATION_DAYS = "ac.password.duration.days";
	public final static String GENERAL_PASSWORD_DURATION_DAYS = "op.password.duration.days";

	public final static String ADMIN_MAX_LOGON_FAILED = "ac.max.logon.failed";
	public final static String GENERAL_MAX_LOGON_FAILED = "op.max.logon.failed";

	public final static String ADMIN_LOCK_DURATION_MINUTES = "ac.lock.duration.minutes";
	public final static String GENERAL_LOCK_DURATION_MINUTES = "op.lock.duration.minutes";

	public final static String ADMIN_RESET_LOGON_FAILED_NUMBER_MINUTES = "ac.reset.logon.failed.minutes";
	public final static String GENERAL_RESET_LOGON_FAILED_NUMBER_MINUTES = "op.reset.logon.failed.minutes";

	public final static String ADMIN_EXPIRATION_ADVANCE_NOTICE_DAYS = "ac.expiration.advance.notice.days";
	public final static String GENERAL_EXPIRATION_ADVANCE_NOTICE_DAYS = "op.expiration.advance.notice.days";


	public final static String ADMIN_PASSWORD_HISTORY_SIZE = "ac.pwd.history.size";
	public final static String GENERAL_PASSWORD_HISTORY_SIZE = "op.pwd.history.size";

	public final static String ADMIN_PWD_MIN_LENGTH = "ac.pwd.min.length";
	public final static String GENERAL_PWD_MIN_LENGTH = "op.pwd.min.length";

	public final static String ADMIN_PWD_MIN_NOALPHA_CHARS = "ac.pwd.min.noalpha.chars";
	public final static String GENERAL_PWD_MIN_NOALPHA_CHARS = "op.pwd.min.noalpha.chars";

	public static String getPwdMinLength(String flagOperatorType) {
		String pwdMinLength = "8";
		if (flagOperatorType != null) {
			try {
				if (flagOperatorType.equals(BackEndConstant.TYPE_ADMIN_OPERATOR)) {
					pwdMinLength = AppConfiguration.getStringParameter(BackEndConstant.ADMIN_PWD_MIN_LENGTH);
				} else {
					pwdMinLength = AppConfiguration.getStringParameter(BackEndConstant.GENERAL_PWD_MIN_LENGTH);
				}
			} catch (Exception e) {
				//Tracer.debug("ChangePwdAction", "loadPwdParameter", "non riesco a caricare la configurazione per la password", e);
			}
		}
		return pwdMinLength;
	}

	public static String getPwdCharNumerici(String flagOperatorType) {
		
		String numerici = "8";
		
		if (flagOperatorType != null) {
			
			try {
				
				if (flagOperatorType.equals(BackEndConstant.TYPE_ADMIN_OPERATOR)) {
					
					numerici = AppConfiguration.getStringParameter(BackEndConstant.ADMIN_PWD_MIN_NOALPHA_CHARS);
				
				} else {
					
					numerici = AppConfiguration.getStringParameter(BackEndConstant.GENERAL_PWD_MIN_NOALPHA_CHARS);
				
				}
				
			} catch (Exception e) {
				
				//Tracer.debug("ChangePwdAction", "loadPwdParameter", "non riesco a caricare la configurazione per la password", e);
			
			}
		}
		return numerici;
	}

	public static String getPwdScadenza(String flagOperatorType) {
		String scadenza = "30";
		if (flagOperatorType != null) {
			try {
				if (flagOperatorType.equals(BackEndConstant.TYPE_ADMIN_OPERATOR)) {
					scadenza = AppConfiguration.getStringParameter(BackEndConstant.ADMIN_PASSWORD_DURATION_DAYS);
				} else {
					scadenza = AppConfiguration.getStringParameter(BackEndConstant.GENERAL_PASSWORD_DURATION_DAYS);
				}
			} catch (Exception e) {
				//Tracer.debug("ChangePwdAction", "loadPwdParameter", "non riesco a caricare la configurazione per la password", e);
			}
		}
		return scadenza;
	}

	public static String getPwdAvvisoScadenza(String flagOperatorType) {
		String avviso = "7";
		if (flagOperatorType != null) {
			try {
				if (flagOperatorType.equals(BackEndConstant.TYPE_ADMIN_OPERATOR)) {
					avviso = AppConfiguration.getStringParameter(BackEndConstant.ADMIN_EXPIRATION_ADVANCE_NOTICE_DAYS);
				} else {
					avviso = AppConfiguration.getStringParameter(BackEndConstant.GENERAL_EXPIRATION_ADVANCE_NOTICE_DAYS);
				}
			} catch (Exception e) {
				//Tracer.debug("ChangePwdAction", "loadPwdParameter", "non riesco a caricare la configurazione per la password", e);
			}
		}
		return avviso;
	}

	public static String getPwdBloccoPassword(String flagOperatorType) {
		String blocco = "30";
		if (flagOperatorType != null) {
			try {
				if (flagOperatorType.equals(BackEndConstant.TYPE_ADMIN_OPERATOR)) {
					blocco = AppConfiguration.getStringParameter(BackEndConstant.ADMIN_LOCK_DURATION_MINUTES);
				} else {
					blocco = AppConfiguration.getStringParameter(BackEndConstant.GENERAL_LOCK_DURATION_MINUTES);
				}
			} catch (Exception e) {
				//Tracer.debug("ChangePwdAction", "loadPwdParameter", "non riesco a caricare la configurazione per la password", e);
			}
		}
		if(blocco.equalsIgnoreCase("0")){
			blocco = "permanente dopo 3 tentativi";
		}
		else{
			blocco += " sec.";
		}
		return blocco;
	}


}
