package it.tasgroup.idp.notification;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2015-09-11T14:51:37.901+02:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "Notification", 
                  wsdlLocation = "file:/home/user/projects/paytas-be/ebmonitor/IdpBillerServicesCore/src/main/resources/wsdl/Notification.wsdl",
                  targetNamespace = "http://idp.tasgroup.it/Notification/") 
public class Notification extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://idp.tasgroup.it/Notification/", "Notification");
    public final static QName NotificationPort = new QName("http://idp.tasgroup.it/Notification/", "notificationPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/user/projects/paytas-be/ebmonitor/IdpBillerServicesCore/src/main/resources/wsdl/Notification.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Notification.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/home/user/projects/paytas-be/ebmonitor/IdpBillerServicesCore/src/main/resources/wsdl/Notification.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Notification(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Notification(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Notification() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns NotificationService
     */
    @WebEndpoint(name = "notificationPort")
    public NotificationService getNotificationPort() {
        return super.getPort(NotificationPort, NotificationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NotificationService
     */
    @WebEndpoint(name = "notificationPort")
    public NotificationService getNotificationPort(WebServiceFeature... features) {
        return super.getPort(NotificationPort, NotificationService.class, features);
    }

}
