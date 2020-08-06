
package it.tasgroup.iris.comunication.ws.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.tasgroup.iris.comunication.ws.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateTipoMessaggioResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "updateTipoMessaggioResponse");
    private final static QName _ElencoLogMessaggiResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "elencoLogMessaggiResponse");
    private final static QName _GetCanaliConfigurazione_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "getCanaliConfigurazione");
    private final static QName _GetTipoMessaggio_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "getTipoMessaggio");
    private final static QName _UpdateTipoMessaggio_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "updateTipoMessaggio");
    private final static QName _GetCanaliConfigurazioneResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "getCanaliConfigurazioneResponse");
    private final static QName _GetCanaliComunicazioneResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "getCanaliComunicazioneResponse");
    private final static QName _SubscribeCanali_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "subscribeCanali");
    private final static QName _UpdateCanaliComunicazione_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "updateCanaliComunicazione");
    private final static QName _GetCanaliComunicazione_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "getCanaliComunicazione");
    private final static QName _SendMessageAgain_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "sendMessageAgain");
    private final static QName _ValidaConfigurazioneUtenteCanale_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "validaConfigurazioneUtenteCanale");
    private final static QName _SendMessageAgainResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "sendMessageAgainResponse");
    private final static QName _UnsubscribeCanali_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "unsubscribeCanali");
    private final static QName _ValidaConfigurazioneCanale_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "validaConfigurazioneCanale");
    private final static QName _ValidaConfigurazioneUtenteCanaleResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "validaConfigurazioneUtenteCanaleResponse");
    private final static QName _UnsubscribeCanaliResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "unsubscribeCanaliResponse");
    private final static QName _ValidaConfigurazioneCanaleResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "validaConfigurazioneCanaleResponse");
    private final static QName _UpdateCanaliComunicazioneResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "updateCanaliComunicazioneResponse");
    private final static QName _SendMessageResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "sendMessageResponse");
    private final static QName _SendMessage_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "sendMessage");
    private final static QName _GetTipoMessaggioResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "getTipoMessaggioResponse");
    private final static QName _ElencoLogMessaggi_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "elencoLogMessaggi");
    private final static QName _SubscribeCanaliResponse_QNAME = new QName("http://impl.ws.comunication.iris.tasgroup.it/", "subscribeCanaliResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.tasgroup.iris.comunication.ws.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TipoMessaggioType }
     * 
     */
    public TipoMessaggioType createTipoMessaggioType() {
        return new TipoMessaggioType();
    }

    /**
     * Create an instance of {@link SendMessageAgain }
     * 
     */
    public SendMessageAgain createSendMessageAgain() {
        return new SendMessageAgain();
    }

    /**
     * Create an instance of {@link MessaggioLogicoType }
     * 
     */
    public MessaggioLogicoType createMessaggioLogicoType() {
        return new MessaggioLogicoType();
    }

    /**
     * Create an instance of {@link GetCanaliConfigurazioneResponse }
     * 
     */
    public GetCanaliConfigurazioneResponse createGetCanaliConfigurazioneResponse() {
        return new GetCanaliConfigurazioneResponse();
    }

    /**
     * Create an instance of {@link GetCanaliConfigurazione }
     * 
     */
    public GetCanaliConfigurazione createGetCanaliConfigurazione() {
        return new GetCanaliConfigurazione();
    }

    /**
     * Create an instance of {@link SearchLogMessaggiType }
     * 
     */
    public SearchLogMessaggiType createSearchLogMessaggiType() {
        return new SearchLogMessaggiType();
    }

    /**
     * Create an instance of {@link SubscribeCanali }
     * 
     */
    public SubscribeCanali createSubscribeCanali() {
        return new SubscribeCanali();
    }

    /**
     * Create an instance of {@link ElencoLogMessaggiResponse }
     * 
     */
    public ElencoLogMessaggiResponse createElencoLogMessaggiResponse() {
        return new ElencoLogMessaggiResponse();
    }

    /**
     * Create an instance of {@link UpdateCanaliComunicazioneResponse }
     * 
     */
    public UpdateCanaliComunicazioneResponse createUpdateCanaliComunicazioneResponse() {
        return new UpdateCanaliComunicazioneResponse();
    }

    /**
     * Create an instance of {@link ValidoType }
     * 
     */
    public ValidoType createValidoType() {
        return new ValidoType();
    }

    /**
     * Create an instance of {@link GetCanaliComunicazione }
     * 
     */
    public GetCanaliComunicazione createGetCanaliComunicazione() {
        return new GetCanaliComunicazione();
    }

    /**
     * Create an instance of {@link SubscribeCanaliResponse }
     * 
     */
    public SubscribeCanaliResponse createSubscribeCanaliResponse() {
        return new SubscribeCanaliResponse();
    }

    /**
     * Create an instance of {@link LogMessaggioType }
     * 
     */
    public LogMessaggioType createLogMessaggioType() {
        return new LogMessaggioType();
    }

    /**
     * Create an instance of {@link UpdateTipoMessaggio }
     * 
     */
    public UpdateTipoMessaggio createUpdateTipoMessaggio() {
        return new UpdateTipoMessaggio();
    }

    /**
     * Create an instance of {@link ParametroCanaleType }
     * 
     */
    public ParametroCanaleType createParametroCanaleType() {
        return new ParametroCanaleType();
    }

    /**
     * Create an instance of {@link ElencoLogMessaggi }
     * 
     */
    public ElencoLogMessaggi createElencoLogMessaggi() {
        return new ElencoLogMessaggi();
    }

    /**
     * Create an instance of {@link CanaleType }
     * 
     */
    public CanaleType createCanaleType() {
        return new CanaleType();
    }

    /**
     * Create an instance of {@link ValidaConfigurazioneUtenteCanaleResponse }
     * 
     */
    public ValidaConfigurazioneUtenteCanaleResponse createValidaConfigurazioneUtenteCanaleResponse() {
        return new ValidaConfigurazioneUtenteCanaleResponse();
    }

    /**
     * Create an instance of {@link ValidaConfigurazioneUtenteCanale }
     * 
     */
    public ValidaConfigurazioneUtenteCanale createValidaConfigurazioneUtenteCanale() {
        return new ValidaConfigurazioneUtenteCanale();
    }

    /**
     * Create an instance of {@link GetTipoMessaggioResponse }
     * 
     */
    public GetTipoMessaggioResponse createGetTipoMessaggioResponse() {
        return new GetTipoMessaggioResponse();
    }

    /**
     * Create an instance of {@link MessaggioType }
     * 
     */
    public MessaggioType createMessaggioType() {
        return new MessaggioType();
    }

    /**
     * Create an instance of {@link GetTipoMessaggio }
     * 
     */
    public GetTipoMessaggio createGetTipoMessaggio() {
        return new GetTipoMessaggio();
    }

    /**
     * Create an instance of {@link UpdateTipoMessaggioResponse }
     * 
     */
    public UpdateTipoMessaggioResponse createUpdateTipoMessaggioResponse() {
        return new UpdateTipoMessaggioResponse();
    }

    /**
     * Create an instance of {@link UtenteType }
     * 
     */
    public UtenteType createUtenteType() {
        return new UtenteType();
    }

    /**
     * Create an instance of {@link SendMessage }
     * 
     */
    public SendMessage createSendMessage() {
        return new SendMessage();
    }

    /**
     * Create an instance of {@link SendMessageAgainResponse }
     * 
     */
    public SendMessageAgainResponse createSendMessageAgainResponse() {
        return new SendMessageAgainResponse();
    }

    /**
     * Create an instance of {@link ValidaConfigurazioneCanaleResponse }
     * 
     */
    public ValidaConfigurazioneCanaleResponse createValidaConfigurazioneCanaleResponse() {
        return new ValidaConfigurazioneCanaleResponse();
    }

    /**
     * Create an instance of {@link ValidaConfigurazioneCanale }
     * 
     */
    public ValidaConfigurazioneCanale createValidaConfigurazioneCanale() {
        return new ValidaConfigurazioneCanale();
    }

    /**
     * Create an instance of {@link UtenteCanaleType }
     * 
     */
    public UtenteCanaleType createUtenteCanaleType() {
        return new UtenteCanaleType();
    }

    /**
     * Create an instance of {@link UpdateCanaliComunicazione }
     * 
     */
    public UpdateCanaliComunicazione createUpdateCanaliComunicazione() {
        return new UpdateCanaliComunicazione();
    }

    /**
     * Create an instance of {@link GetCanaliComunicazioneResponse }
     * 
     */
    public GetCanaliComunicazioneResponse createGetCanaliComunicazioneResponse() {
        return new GetCanaliComunicazioneResponse();
    }

    /**
     * Create an instance of {@link SendMessageResponse }
     * 
     */
    public SendMessageResponse createSendMessageResponse() {
        return new SendMessageResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTipoMessaggioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "updateTipoMessaggioResponse")
    public JAXBElement<UpdateTipoMessaggioResponse> createUpdateTipoMessaggioResponse(UpdateTipoMessaggioResponse value) {
        return new JAXBElement<UpdateTipoMessaggioResponse>(_UpdateTipoMessaggioResponse_QNAME, UpdateTipoMessaggioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoLogMessaggiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "elencoLogMessaggiResponse")
    public JAXBElement<ElencoLogMessaggiResponse> createElencoLogMessaggiResponse(ElencoLogMessaggiResponse value) {
        return new JAXBElement<ElencoLogMessaggiResponse>(_ElencoLogMessaggiResponse_QNAME, ElencoLogMessaggiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCanaliConfigurazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "getCanaliConfigurazione")
    public JAXBElement<GetCanaliConfigurazione> createGetCanaliConfigurazione(GetCanaliConfigurazione value) {
        return new JAXBElement<GetCanaliConfigurazione>(_GetCanaliConfigurazione_QNAME, GetCanaliConfigurazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTipoMessaggio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "getTipoMessaggio")
    public JAXBElement<GetTipoMessaggio> createGetTipoMessaggio(GetTipoMessaggio value) {
        return new JAXBElement<GetTipoMessaggio>(_GetTipoMessaggio_QNAME, GetTipoMessaggio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTipoMessaggio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "updateTipoMessaggio")
    public JAXBElement<UpdateTipoMessaggio> createUpdateTipoMessaggio(UpdateTipoMessaggio value) {
        return new JAXBElement<UpdateTipoMessaggio>(_UpdateTipoMessaggio_QNAME, UpdateTipoMessaggio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCanaliConfigurazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "getCanaliConfigurazioneResponse")
    public JAXBElement<GetCanaliConfigurazioneResponse> createGetCanaliConfigurazioneResponse(GetCanaliConfigurazioneResponse value) {
        return new JAXBElement<GetCanaliConfigurazioneResponse>(_GetCanaliConfigurazioneResponse_QNAME, GetCanaliConfigurazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCanaliComunicazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "getCanaliComunicazioneResponse")
    public JAXBElement<GetCanaliComunicazioneResponse> createGetCanaliComunicazioneResponse(GetCanaliComunicazioneResponse value) {
        return new JAXBElement<GetCanaliComunicazioneResponse>(_GetCanaliComunicazioneResponse_QNAME, GetCanaliComunicazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeCanali }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "subscribeCanali")
    public JAXBElement<SubscribeCanali> createSubscribeCanali(SubscribeCanali value) {
        return new JAXBElement<SubscribeCanali>(_SubscribeCanali_QNAME, SubscribeCanali.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCanaliComunicazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "updateCanaliComunicazione")
    public JAXBElement<UpdateCanaliComunicazione> createUpdateCanaliComunicazione(UpdateCanaliComunicazione value) {
        return new JAXBElement<UpdateCanaliComunicazione>(_UpdateCanaliComunicazione_QNAME, UpdateCanaliComunicazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCanaliComunicazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "getCanaliComunicazione")
    public JAXBElement<GetCanaliComunicazione> createGetCanaliComunicazione(GetCanaliComunicazione value) {
        return new JAXBElement<GetCanaliComunicazione>(_GetCanaliComunicazione_QNAME, GetCanaliComunicazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageAgain }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "sendMessageAgain")
    public JAXBElement<SendMessageAgain> createSendMessageAgain(SendMessageAgain value) {
        return new JAXBElement<SendMessageAgain>(_SendMessageAgain_QNAME, SendMessageAgain.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidaConfigurazioneUtenteCanale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "validaConfigurazioneUtenteCanale")
    public JAXBElement<ValidaConfigurazioneUtenteCanale> createValidaConfigurazioneUtenteCanale(ValidaConfigurazioneUtenteCanale value) {
        return new JAXBElement<ValidaConfigurazioneUtenteCanale>(_ValidaConfigurazioneUtenteCanale_QNAME, ValidaConfigurazioneUtenteCanale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageAgainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "sendMessageAgainResponse")
    public JAXBElement<SendMessageAgainResponse> createSendMessageAgainResponse(SendMessageAgainResponse value) {
        return new JAXBElement<SendMessageAgainResponse>(_SendMessageAgainResponse_QNAME, SendMessageAgainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeCanali }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "unsubscribeCanali")
    public JAXBElement<SubscribeCanali> createUnsubscribeCanali(SubscribeCanali value) {
        return new JAXBElement<SubscribeCanali>(_UnsubscribeCanali_QNAME, SubscribeCanali.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidaConfigurazioneCanale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "validaConfigurazioneCanale")
    public JAXBElement<ValidaConfigurazioneCanale> createValidaConfigurazioneCanale(ValidaConfigurazioneCanale value) {
        return new JAXBElement<ValidaConfigurazioneCanale>(_ValidaConfigurazioneCanale_QNAME, ValidaConfigurazioneCanale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidaConfigurazioneUtenteCanaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "validaConfigurazioneUtenteCanaleResponse")
    public JAXBElement<ValidaConfigurazioneUtenteCanaleResponse> createValidaConfigurazioneUtenteCanaleResponse(ValidaConfigurazioneUtenteCanaleResponse value) {
        return new JAXBElement<ValidaConfigurazioneUtenteCanaleResponse>(_ValidaConfigurazioneUtenteCanaleResponse_QNAME, ValidaConfigurazioneUtenteCanaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeCanaliResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "unsubscribeCanaliResponse")
    public JAXBElement<SubscribeCanaliResponse> createUnsubscribeCanaliResponse(SubscribeCanaliResponse value) {
        return new JAXBElement<SubscribeCanaliResponse>(_UnsubscribeCanaliResponse_QNAME, SubscribeCanaliResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidaConfigurazioneCanaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "validaConfigurazioneCanaleResponse")
    public JAXBElement<ValidaConfigurazioneCanaleResponse> createValidaConfigurazioneCanaleResponse(ValidaConfigurazioneCanaleResponse value) {
        return new JAXBElement<ValidaConfigurazioneCanaleResponse>(_ValidaConfigurazioneCanaleResponse_QNAME, ValidaConfigurazioneCanaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCanaliComunicazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "updateCanaliComunicazioneResponse")
    public JAXBElement<UpdateCanaliComunicazioneResponse> createUpdateCanaliComunicazioneResponse(UpdateCanaliComunicazioneResponse value) {
        return new JAXBElement<UpdateCanaliComunicazioneResponse>(_UpdateCanaliComunicazioneResponse_QNAME, UpdateCanaliComunicazioneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "sendMessageResponse")
    public JAXBElement<SendMessageResponse> createSendMessageResponse(SendMessageResponse value) {
        return new JAXBElement<SendMessageResponse>(_SendMessageResponse_QNAME, SendMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "sendMessage")
    public JAXBElement<SendMessage> createSendMessage(SendMessage value) {
        return new JAXBElement<SendMessage>(_SendMessage_QNAME, SendMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTipoMessaggioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "getTipoMessaggioResponse")
    public JAXBElement<GetTipoMessaggioResponse> createGetTipoMessaggioResponse(GetTipoMessaggioResponse value) {
        return new JAXBElement<GetTipoMessaggioResponse>(_GetTipoMessaggioResponse_QNAME, GetTipoMessaggioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ElencoLogMessaggi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "elencoLogMessaggi")
    public JAXBElement<ElencoLogMessaggi> createElencoLogMessaggi(ElencoLogMessaggi value) {
        return new JAXBElement<ElencoLogMessaggi>(_ElencoLogMessaggi_QNAME, ElencoLogMessaggi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeCanaliResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.ws.comunication.iris.tasgroup.it/", name = "subscribeCanaliResponse")
    public JAXBElement<SubscribeCanaliResponse> createSubscribeCanaliResponse(SubscribeCanaliResponse value) {
        return new JAXBElement<SubscribeCanaliResponse>(_SubscribeCanaliResponse_QNAME, SubscribeCanaliResponse.class, null, value);
    }

}
