/**
 *
 */
package it.tasgroup.iris.shared.util.enumeration;


/**
 * @author PazziK
 *
 */
public enum EnumBusinessErrorCodes implements EnumErrorMessage{

	APPEXC_FRECCIA_KO("APPEXCFRECCIAKO","Servizio Bollettino Freccia irraggiungibile","it.tasgroup.iris.exception.bollFrecciaKO", EnumSeverityLevel.USERINFO),
	APPEXC_FRECCIA_INDIRIZZO_NOTFOUND("APPEXCFRECCIAINDNF","Servizio Bollettino Freccia non utilizzabile","it.tasgroup.iris.exception.bollFrecciaIndNotFound", EnumSeverityLevel.USERINFO),
	APPEXC_DDP_BON_UNIFORM("APPEXCDDPBONUNIFORM","Bonifico con codice predefinito non utilizzabile","it.tasgroup.iris.exception.ddpBonNonUniforme", EnumSeverityLevel.USERINFO),
	APPEXC_DDP_PAGAMENTO_NON_ESEGUITO("APPEXCDDPPAGNOTEXEC","Impossibile scaricare la ricevuta di un pagamento non eseguito.","it.tasgroup.iris.exception.ddpPagNonEseguito", EnumSeverityLevel.USERINFO),
	APPEXC_DOUBLE_DDP("APPEXCDOUBLEDDDP","Esiste già un documento di pagamento non annullato per le medesime condizioni","it.tasgroup.iris.exception.duplicatedDDP", EnumSeverityLevel.USERINFO),
	APPEXC_INVALID_DDP("APPEXCINVALIDDDP","Si e' verificato un errore nel processo di pagamento. Si prega di riprovare piu' tardi.","it.tasgroup.iris.exception.invalidDDP", EnumSeverityLevel.USERINFO),
	APPEXC_MOLTDDP("APPEXCMOLTDDP","Violazione del vincolo di molteplicita dei DDP","it.tasgroup.iris.exception.molteplicitaDDP", EnumSeverityLevel.USERINFO),
	APPEXC_SECURITY("APPEXCSECURITY","Violazione di sicurezza interna","it.tasgroup.iris.exception.security", EnumSeverityLevel.ERROR),
	APPEXC_MOLTGTW("APPEXCMOLTGTW","Violata la univocita dei gateway di pagamento","it.tasgroup.iris.exception.molteplicitaGateway", EnumSeverityLevel.ERROR),
	APPEXC_NOGTW("APPEXCNOGTW","Nessuna configurazione gateway trovata","it.tasgroup.iris.exception.noGatewayConf", EnumSeverityLevel.ERROR),
	APPEXC_NODDPGTW("APPEXCNODDPGTW","Gateway non abilitato ai DDP","it.tasgroup.iris.exception.noDDPGatewayConf", EnumSeverityLevel.ERROR),
	APPEXC_NOBILLER("APPEXCNOBILLER","Creditore inesistente o non abilitato","it.tasgroup.iris.exception.noBiller", EnumSeverityLevel.ERROR),
	APPEXC_NOBILL("APPEXCNOBILL","Pendenza inesistente o non abilitata al pagamento","it.tasgroup.iris.exception.noBill", EnumSeverityLevel.ERROR),
	GTW_AUTH_KO("GTWAUTHKO","Autenticazione non riuscita","it.tasgroup.iris.gtw.exception.AutenticazioneKO", EnumSeverityLevel.ERROR),
	APPEXC_BAD_REGISTRY("APPEXCBADREGISTRY","Anagrafica incompleta","it.tasgroup.iris.exception.badRegistry", EnumSeverityLevel.USERINFO),
	APPEXC_SOSPETTO_GR("APPEXCSOSPETTOGR","Sospetto Gia' Riversato","it.tasgroup.iris.exception.sospettoGR", EnumSeverityLevel.USERINFO),
	APPEXC_FILE_SIZE("APPEXCFILESIZE","Superato il massimo numero di righe consentito","it.tasgroup.iris.exception.file.size", EnumSeverityLevel.USERINFO),
	APPEXC_FILE_UPLOAD("APPEXCFILEUPLOAD","File Upload non riuscito","it.tasgroup.iris.exception.file.upload", EnumSeverityLevel.USERINFO),
	APPEXC_SMART_PROXY("APPEXCSMARTPROXY","Aggiornamento Esito non riuscito","it.tasgroup.iris.exception.smart.proxy", EnumSeverityLevel.USERINFO),
	APPEXC_INVALID_CSV("APPEXCINVALIDCSV","CSV non valido","it.tasgroup.iris.exception.invalid.csv", EnumSeverityLevel.USERINFO),
	APPEXC_MALFORMED_CSV("APPEXCMALFORMEDCSV","CSV mal formato","it.tasgroup.iris.exception.malformed.csv", EnumSeverityLevel.USERINFO),
	APPEXC_MISSING_CONFIGURATION("APPEXCMISSINGCONFIGURATION","Configurazioni Assenti","it.tasgroup.iris.exception.missing.config", EnumSeverityLevel.USERINFO),
	APPEXC_IUV_NOT_UNIQUE("APPEXCIUVNOTUNIQUE","IUV non univoco","it.tasgroup.iris.exception.iuv.not.unique", EnumSeverityLevel.USERINFO),
	APPEXC_UNIQUE_COLUMN_VALUE_CSV("APPEXCUNIQUECOLUMNVALUECSV","Fallito controllo di univocita' sui valori del CSV","it.tasgroup.iris.exception.notunique.csv", EnumSeverityLevel.USERINFO),

	APPEXC_WRONG_PARAMETERS("WRONG_PARAMETERS","Il servizio e' stato invocato con parametri errati e/o mancanti","it.tasgroup.iris.exception.wrongparams", EnumSeverityLevel.ERROR),
	APPEXC_IN_PROGRESS("PAYMENT_IN_PROGRESS","La condizione specificata non e' pagabile perche' IN CORSO","it.tasgroup.iris.exception.payment.inprogress", EnumSeverityLevel.WARN),
	APPEXC_NODE_GENERIC_ERROR("NODE_ERROR","Si e' riscontrato un problema nel colloquio col nodo dei pagamenti","it.tasgroup.iris.exception.payment.node.generic", EnumSeverityLevel.ERROR);


	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private EnumSeverityLevel severityLevel;

	EnumBusinessErrorCodes(String chiave, String descrizione, String chiaveBundle, EnumSeverityLevel severityLevel){

		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.severityLevel = severityLevel;

	}


	@Override
	public String getChiave() {

		return this.chiave;
	}

	@Override
	public String getDescrizione() {

		return this.descrizione;
	}

	@Override
	public String getChiaveBundle() {

		return this.chiaveBundle;
	}


	@Override
	public EnumSeverityLevel getSeverityLevel() {

		return this.severityLevel;
	}


	@Override
	public void setSeverityLevel(EnumSeverityLevel severityLevel) {

		this.severityLevel = severityLevel;

	}

}
