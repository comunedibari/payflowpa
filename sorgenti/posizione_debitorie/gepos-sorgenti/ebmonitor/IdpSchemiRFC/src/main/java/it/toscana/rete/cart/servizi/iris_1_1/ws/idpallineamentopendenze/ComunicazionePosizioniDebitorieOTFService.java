package it.toscana.rete.cart.servizi.iris_1_1.ws.idpallineamentopendenze;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2019-05-08T20:00:17.606+02:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "ComunicazionePosizioniDebitorieOTFService", 
                  wsdlLocation = "classpath:wsdl/ComunicazionePosizioniDebitorie.wsdl",
                  targetNamespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy") 
public class ComunicazionePosizioniDebitorieOTFService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy", "ComunicazionePosizioniDebitorieOTFService");
    public final static QName ComunicazionePosizioniDebitorieOTFPort = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy", "ComunicazionePosizioniDebitorieOTFPort");
    static {
        URL url = ComunicazionePosizioniDebitorieOTFService.class.getClassLoader().getResource("wsdl/ComunicazionePosizioniDebitorie.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ComunicazionePosizioniDebitorieOTFService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/ComunicazionePosizioniDebitorie.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public ComunicazionePosizioniDebitorieOTFService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ComunicazionePosizioniDebitorieOTFService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ComunicazionePosizioniDebitorieOTFService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns ComunicazionePosizioniDebitorieOTF
     */
    @WebEndpoint(name = "ComunicazionePosizioniDebitorieOTFPort")
    public ComunicazionePosizioniDebitorieOTF getComunicazionePosizioniDebitorieOTFPort() {
        return super.getPort(ComunicazionePosizioniDebitorieOTFPort, ComunicazionePosizioniDebitorieOTF.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ComunicazionePosizioniDebitorieOTF
     */
    @WebEndpoint(name = "ComunicazionePosizioniDebitorieOTFPort")
    public ComunicazionePosizioniDebitorieOTF getComunicazionePosizioniDebitorieOTFPort(WebServiceFeature... features) {
        return super.getPort(ComunicazionePosizioniDebitorieOTFPort, ComunicazionePosizioniDebitorieOTF.class, features);
    }

}