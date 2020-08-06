package it.toscana.rete.cart.servizi.iris_1_1.ws.idpconfigurazione;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2020-02-14T17:50:13.790+01:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "ConfigurazioneIDPService", 
                  wsdlLocation = "classpath:wsdl/ConfigurazioneIDP.wsdl",
                  targetNamespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy") 
public class ConfigurazioneIDPService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy", "ConfigurazioneIDPService");
    public final static QName ConfigurazioneIDPPort = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy", "ConfigurazioneIDPPort");
    static {
        URL url = ConfigurazioneIDPService.class.getClassLoader().getResource("wsdl/ConfigurazioneIDP.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ConfigurazioneIDPService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/ConfigurazioneIDP.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public ConfigurazioneIDPService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ConfigurazioneIDPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConfigurazioneIDPService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns ConfigurazioneIDP
     */
    @WebEndpoint(name = "ConfigurazioneIDPPort")
    public ConfigurazioneIDP getConfigurazioneIDPPort() {
        return super.getPort(ConfigurazioneIDPPort, ConfigurazioneIDP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConfigurazioneIDP
     */
    @WebEndpoint(name = "ConfigurazioneIDPPort")
    public ConfigurazioneIDP getConfigurazioneIDPPort(WebServiceFeature... features) {
        return super.getPort(ConfigurazioneIDPPort, ConfigurazioneIDP.class, features);
    }

}
