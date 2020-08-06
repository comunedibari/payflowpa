package it.nch.is.fo;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * Constants shared between Backend and Frontend
 * @author EE10057
 *
 */
public class CommonConstant {
    /** Nome dell'applicazione IRIS per la parte di BackOffice */
    public final static String APPLICAZIONE_BACKOFFICE = "BackOffice";

    /** Indica il tipo di operatore per le funzioni dei menu */
    public final static String TIPOPERATORE_BACKOFFICE_FUNZIONI = "BK_OP";

    /** Indica il tipo di operatore per le funzioni dei pulsanti*/
    public final static String TIPOPERATORE_BACKOFFICE_FUNZIONIPULSANTI = "BK_BU";
    public final static String DEFAULT_FUNCTION_RAPPORTI_EXCLUDED_RANGE = "";
    public final static String BACKOFFICE_FUNCTION_RAPPORTI_EXCLUDED_RANGE = "";
    public final static String DEFAULT_AREA_RAPPORTI_EXCLUDED_RANGE = "";
    public final static String DEFAULT_FUNCTION_FUNZIONI_EXCLUDED_RANGE = "";
    public final static String DEFAULT_BACKOFFICE_FUNCTION_EXCLUDED_RANGE = "";

    //ref. field BLOCCATO of OPERATORI
    public final static Integer VALID_OPERATOR = new Integer(0);
    public final static Integer BLOCKED_OPERATOR = new Integer(-1);

    //ref. field FLAGABILITATO of OPERATORI
    public final static String TYPE_ADMIN_OPERATOR = "AC";
    public final static String TYPE_GENERAL_OPERATOR = "OP";
    public final static String DEFAULT_PASSWORD = "";
    public final static int LONG_EXPIRING_DATE_YYYY = 2500;
    public final static int LONG_EXPIRING_DATE_MM = 4;
    public final static int LONG_EXPIRING_DATE_DD = 10;

    //Export format
    public final static String EXPORT_XLS = "Excel";
    public final static String EXPORT_TXT = "STD";

    /********** Err message as mapped in the resource file **************/
    public final static String SEARCH_ERROR_MESSAGE = "error.search.message";
    public final static String INSERT_ERROR_MESSAGE = "error.insert.message";
    public final static String DELETE_ERROR_MESSAGE = "error.delete.message";
    public final static String UPDATE_ERROR_MESSAGE = "error.update.message";
    public final static String GENERIC_ERROR_MESSAGE = "error.iris.generic";
    public final static String RUNTIME_ERROR_MESSAGE = "error.global.runtime";
    public final static String UPLOAD_ERROR_MESSAGE = "error.upload.message";
    public final static String DOWNLOAD_ERROR_MESSAGE = "error.download.message";
    public final static String FLOW_AMOUNT_ERROR = "error.flow.maxAmtLimitExceed";
    public final static String FLOW_WRONG_PAYMDATE = "error.flow.insert.paymentdate";
    public final static String DISP_ABI_ERROR = "error.disposition.abiCheckFailed";
    public final static String DISP_WRONG_BICCODE_ERROR = "error.bonificiestori.disposition.wrongBicCode";
    public final static String DISPOSITION_AMOUNT_ERROR = "error.disposition.maxAmtLimitExceed";
    public final static String SAME_DISTICT_DISP_WARN = "site.iris.Contodiriferimento.second.Body.bodyLayoutContent.contodiRiferimentoPageContent.alreadyPresent";
    public final static String INSUFFICIENT_INPUT_PARAM = "error.search.insuff.inputparam";

    //errori per aggiornamento operatori
    public final static String OPERATOR_WITHOUT_INTESTOPER_LINK = "error.operatori.consistenza.operatore";
    public final static String OPERATOR_ERROR_INSERT_FUNCTIONS = "error.operatori.inserimento.funzioni.operatore";
    public final static String INCASSI_RID = "RID";
    public final static String RIDFILES_ALIGNMENT = "AAR";
    public final static String DIRECT_DEBIT = "UMP";
    public final static String RID_CASUALE_VALUE = "50000";
    public final static String CURRENCY = "EUR";
    public final static String RT_PARTITA_IVA = "01386030488";
    public final static String RT_RAG_SOCIALE = "REGIONE TOSCANA";
    public final static String RT_SIA = "59470";
    public final static String PROPERTY_FILE_PATH = "tas.properties.file.configuration";
    public static final String CITTADINO_CORPORATE = "CI";
    public static final String AZIENDA_CORPORATE = "AZ";
    public static final String ENTE_CORPORATE = "EN";
    public static final String AZIENDA_BACKOFFICE = "ADMINCORPORATE";
    public static final String MENUNAME = "MENUNAME";
    public static final String LOGONAME = "LOGONAME";
    public static final String PAGENAME = "PAGENAME";
    public static final String BREADCRUMB = "BREADCRUMB";
    public static final String SOLO_RENDICONTI = "soloRendiconti";
    final static public String PAYMENT_MODULE = "pagamento";
    final static public String DDP_FOLDER_NAME = "ddp";
    final static public String RECEIPT_FOLDER_NAME = "ricevuta";
    final static public String DDP_REPORT_NAME = "DDP_Bone_";
    final static public String DDP_REPORT_NDP_NAME = "DDP_Bone_NDP_";
    final static public String RICEVUTA_REPORT_NAME = "DDP_Ricevuta_";
    final static public String RICEVUTA_REPORT_NDP_NAME = "DDP_Ricevuta_NDP_";
    final static public String DDP_FILE_NAME_PREFIX = "DDP_";
    final static public String RICEVUTA_FILE_NAME_PREFIX = "RICEVUTA_PAGAMENTO_";
    public static final String LISTA_AVVISI_POSDEB = "listaAvvisiDebitore";
    public static final String LISTA_AVVISI_POSCRED = "listaAvvisiCreditore";
    public static final String LISTA_AVVISI_AMMINISTRAZIONE = "listaAvvisiAmministrazione";
    public static final String RICERCA_AVVISI_AMMINISTRAZIONE = "ricercaAvvisiAmministrazione";
    public static final String RICERCA_AVVISI_POSDEB = "ricercaAvvisiDebitore";
    public static final String RICERCA_AVVISI_POSCRED = "ricercaAvvisiCreditore";
}
