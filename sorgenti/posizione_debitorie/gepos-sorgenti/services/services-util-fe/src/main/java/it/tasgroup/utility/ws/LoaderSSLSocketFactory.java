package it.tasgroup.utility.ws;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Hashtable;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.axis.components.net.SecureSocketFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class LoaderSSLSocketFactory  extends JSSESocketFactory implements SecureSocketFactory {  

	
	private static Logger LOGGER = LogManager.getLogger(ConfigurationPropertyLoader.class);
	
    /**
     * Constructor SSLSocketFactory
     * 
     * @param attributes
     */
    public LoaderSSLSocketFactory(Hashtable attributes) {
        super(attributes);
        LOGGER.info("LoaderSSLSocketFactory INSTANTIATED!");
    }

    @Override
    protected void initFactory() throws IOException {

        try {
            SSLContext context = getContext();
            sslFactory = context.getSocketFactory();
            LOGGER.info("LoaderSSLSocketFactory INITIALIZED!");
            
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw (IOException) e;
            }
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Gets a custom SSL Context.
     * 
     * Initialise a SSLContext
     * 
     * @return SSLContext
     * @throws WebServiceClientConfigException
     * @throws Exception
     */
    protected SSLContext getContext() throws Exception {
        try {
            
        	ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("ws-client.properties");
        	
        	String trustStorePath=cpl.getProperty("ws.clients.truststore.path");
        	String trustStorePwd=cpl.getProperty("ws.clients.truststore.pwd");
        	
        	if (trustStorePath==null || trustStorePath.trim().equals("")) {
        		LOGGER.error("missing property: ws.clients.truststore.path ");
        		throw new Exception("missing property: ws.clients.truststore.path ");
        	}

        	if (trustStorePwd==null || trustStorePwd.trim().equals("")) {
        		LOGGER.error("missing property: ws.clients.truststore.pwd ");
        		throw new Exception("missing property: ws.clients.truststore.pwd ");
        	}


           	String keyStorePath=cpl.getProperty("ws.clients.keystore.path");
        	String keyStorePwd=cpl.getProperty("ws.clients.keystore.pwd");
        	String keyStoreStorePwd=cpl.getProperty("ws.clients.keystore.storepwd");
        	String keyStoreType=cpl.getProperty("ws.clients.keystore.type");
        	

        	boolean loadKeyStoreToo = keyStorePath!=null && !keyStorePath.trim().equals("") && 
        			                  keyStorePwd!=null && !keyStorePwd.trim().equals("") && 
        			                  keyStoreStorePwd!=null && !keyStoreStorePwd.trim().equals("") &&
        			                  keyStoreType!=null && !keyStoreType.trim().equals("");
        	
        	if (!loadKeyStoreToo) {
        		LOGGER.warn("missing property: ws.clients.keystore.pwd or ws.clients.keystore.path or ws.clients.keystore.storepwd or ws.clients.keystore.type. keystore not loaded. Check ws-client.properties");
        	}
        	
            // load your key store as a stream and initialize a KeyStore
        	LOGGER.info("AXIS: Opening truststore = "+trustStorePath);
        	
            InputStream trustStream = new FileInputStream(trustStorePath);  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());    
           
            // if your store is password protected then declare it (it can be null however)
            char[] trustPassword = trustStorePwd.toCharArray();

            // load the stream to the store
            trustStore.load(trustStream, trustPassword);

            // initialize a trust manager factory with the trusted store
            TrustManagerFactory trustFactory = 
              TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());    
            trustFactory.init(trustStore);

            // get the trust managers from the factory
            TrustManager[] trustManagers = trustFactory.getTrustManagers();
 
            KeyManager[] keyManagers = null;
            
            if (loadKeyStoreToo) {
  
                // load your key store as a stream and initialize a KeyStore
            	LOGGER.info("AXIS: Opening KeyStore = "+keyStorePath);
      
                InputStream keyStream = new FileInputStream(keyStorePath);  
                char[] keyStorePassword = keyStoreStorePwd.toCharArray();
                char[] keyPassword = keyStorePwd.toCharArray();
                

            	KeyStore keyStore = KeyStore.getInstance(keyStoreType.trim());
            	keyStore.load(keyStream, keyStorePassword);
            	
                KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                
                keyFactory.init(keyStore, keyPassword);
                
                keyManagers = keyFactory.getKeyManagers();
                
            } 
             
            // initialize an ssl context to use these managers and set as default
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagers, trustManagers, null);
            //SSLContext.setDefault(sslContext);
            
            LOGGER.info("SSLContext Created!");
            
            return sslContext;
            
            
        } catch (Exception e) {
        	LOGGER.error(e);
            throw new Exception("Error creating context for SSLSocket!", e);
        }
    }
    

}
