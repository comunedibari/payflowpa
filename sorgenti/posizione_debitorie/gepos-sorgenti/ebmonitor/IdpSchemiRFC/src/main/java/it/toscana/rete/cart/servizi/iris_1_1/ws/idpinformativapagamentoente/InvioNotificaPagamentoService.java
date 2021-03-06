package it.toscana.rete.cart.servizi.iris_1_1.ws.idpinformativapagamentoente;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2019-05-08T20:00:10.054+02:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "InvioNotificaPagamentoService", 
                  wsdlLocation = "classpath:wsdl/InvioNotificaPagamento_Ente.wsdl",
                  targetNamespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy") 
public class InvioNotificaPagamentoService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy", "InvioNotificaPagamentoService");
    public final static QName InvioNotificaPagamento = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/proxy", "InvioNotificaPagamento");
    static {
        URL url = InvioNotificaPagamentoService.class.getClassLoader().getResource("wsdl/InvioNotificaPagamento_Ente.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(InvioNotificaPagamentoService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/InvioNotificaPagamento_Ente.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public InvioNotificaPagamentoService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public InvioNotificaPagamentoService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public InvioNotificaPagamentoService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns InvioNotificaPagamento
     */
    @WebEndpoint(name = "InvioNotificaPagamento")
    public InvioNotificaPagamento getInvioNotificaPagamento() {
        return super.getPort(InvioNotificaPagamento, InvioNotificaPagamento.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns InvioNotificaPagamento
     */
    @WebEndpoint(name = "InvioNotificaPagamento")
    public InvioNotificaPagamento getInvioNotificaPagamento(WebServiceFeature... features) {
        return super.getPort(InvioNotificaPagamento, InvioNotificaPagamento.class, features);
    }

}
