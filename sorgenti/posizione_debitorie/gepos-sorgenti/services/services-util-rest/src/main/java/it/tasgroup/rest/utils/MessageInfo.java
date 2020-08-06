package it.tasgroup.rest.utils;
import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * <p>Ritornato in caso di errori, warning, informazioni in alternativa alla risposta attesa del servizio invocato.</p>
 * <p>Si associa sempre ai seguenti codici HTTP :</p>
 * <code>4xx Client Error </code><br>
 * <code>5xx Server Error </code><br>
 *
 * Esempio :
 *
 * <code><pre>
 *     {
 *          "message": "Parametri Obbligatori : [codiceFiscaleDebitore, idPagamentoEnte]",
 *          "data": null,
 *          "severity": "ERROR",
 *          "errorCode": "WRONG_PARAMETERS",
 *          "errorDescription": "Il servizio è stato invocato con parametri errati e/o mancanti",
 *          "debugInfo": null
 *      }
 *      </pre>
 * </code>
 *
 */
@XmlRootElement
public class MessageInfo implements Serializable {
    private String message;

    private String errorCode;

    private String errorDescription;

    private String data;

    private EnumSeverityLevel severity;

    private String debugInfo;

    public MessageInfo(String message) {
        this.message = message;
    }

    public MessageInfo(EnumBusinessErrorCodes errorCode) {
        this.errorCode = errorCode.getChiave();
        this.errorDescription = errorCode.getDescrizione();
    }

    public MessageInfo(String message, String debugInfo) {
        this.message = message;
        this.debugInfo = debugInfo;
    }

    public MessageInfo(String message, String errorCode, String errorDescription, String data, String debugInfo) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.data = data;
        this.debugInfo = debugInfo;
    }

    public MessageInfo() {
    }

    /**
     * Codice applicativo dell'errore gestito (presente solo nel caso di "errore gestito")
     */
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Messaggio esplicativo specifico.
     * Nel caso di errore gestito (errorCode != null) se presente può contestualizzare ulteriormente l'errorDescription.
     * Nel caso di errore non gestito se presente contiene informazioni tecniche sul problema.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Informazioni per uso interno di debug, presenti solamente se l'eccezione non è gestita, si riferiscono ad "internals" dell'applicativo server-side
     * possono essere disabilitate in produzione.
     */
    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    /**
     * Eventuali dati aggiuntivi associati al messaggio (non necessariamente di "errore")
     */
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * Descrizione associata all'errorCode (presente solo se l'errore è associato a un errorCode e quindi è "gestito")
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * FATAL : Errore Grave
     * ERROR : Errore
     * WARN  : Avvertenza,
     * INFO  : Informazione,
     * USERINFO : Informazione Utente
     */
    public EnumSeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(EnumSeverityLevel severity) {
        this.severity = severity;
    }
}
