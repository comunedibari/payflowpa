package it.veneto.regione.pagamenti.pivot.ente.pagamentitelematicipagatiriconciliati;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2020-05-18T11:23:09.548+02:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "PagamentiTelematiciPagatiRiconciliatiService", 
                  wsdlLocation = "classpath:it/regioneveneto/mygov/payment/mypivot/server/mypivot-per-ente.wsdl",
                  targetNamespace = "http://www.regione.veneto.it/pagamenti/pivot/ente/PagamentiTelematiciPagatiRiconciliati") 
public class PagamentiTelematiciPagatiRiconciliatiService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.regione.veneto.it/pagamenti/pivot/ente/PagamentiTelematiciPagatiRiconciliati", "PagamentiTelematiciPagatiRiconciliatiService");
    public final static QName PagamentiTelematiciPagatiRiconciliatiPort = new QName("http://www.regione.veneto.it/pagamenti/pivot/ente/PagamentiTelematiciPagatiRiconciliati", "PagamentiTelematiciPagatiRiconciliatiPort");
    static {
        URL url = PagamentiTelematiciPagatiRiconciliatiService.class.getClassLoader().getResource("it/regioneveneto/mygov/payment/mypivot/server/mypivot-per-ente.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(PagamentiTelematiciPagatiRiconciliatiService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:it/regioneveneto/mygov/payment/mypivot/server/mypivot-per-ente.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public PagamentiTelematiciPagatiRiconciliatiService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PagamentiTelematiciPagatiRiconciliatiService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PagamentiTelematiciPagatiRiconciliatiService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public PagamentiTelematiciPagatiRiconciliatiService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public PagamentiTelematiciPagatiRiconciliatiService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public PagamentiTelematiciPagatiRiconciliatiService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns PagamentiTelematiciPagatiRiconciliati
     */
    @WebEndpoint(name = "PagamentiTelematiciPagatiRiconciliatiPort")
    public PagamentiTelematiciPagatiRiconciliati getPagamentiTelematiciPagatiRiconciliatiPort() {
        return super.getPort(PagamentiTelematiciPagatiRiconciliatiPort, PagamentiTelematiciPagatiRiconciliati.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PagamentiTelematiciPagatiRiconciliati
     */
    @WebEndpoint(name = "PagamentiTelematiciPagatiRiconciliatiPort")
    public PagamentiTelematiciPagatiRiconciliati getPagamentiTelematiciPagatiRiconciliatiPort(WebServiceFeature... features) {
        return super.getPort(PagamentiTelematiciPagatiRiconciliatiPort, PagamentiTelematiciPagatiRiconciliati.class, features);
    }

}
