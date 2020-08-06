package it.tasgroup.ge.opentoscana;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import it.tasgroup.idp.util.IrisProperties;


public class TokenRetriever {

	protected static final Log logger = LogFactory.getLog(TokenRetriever.class);
	
	private static String tokenLocation, clientId, clientSecret, scope, grantType;
	
	private static String tokenCache = null;
	private static int expires = 0;
	private static Date tsRemoteGet = null;

	// inizializzazione
	static {

		clientSecret = System.getProperty("RT_OAUTH2_CLIENT_SECRET_OPENTOSCANA");
		if (clientSecret == null) {
			clientSecret = IrisProperties.getProperty("rt.oauth2.client.secret.opentoscana");
		}
		tokenLocation = IrisProperties.getProperty("rt.oauth2.token.location.opentoscana");
		clientId = IrisProperties.getProperty("rt.oauth2.client.id.opentoscana");
		scope = IrisProperties.getProperty("rt.oauth2.scope.opentoscana");
		grantType = IrisProperties.getProperty("rt.oauth2.grant.type.opentoscana");

		logger.info("Init TokenRetriever Open Toscana");
		logger.info("clientSecret (RT_OAUTH2_CLIENT_SECRET_OPENTOSCANA / rt.oauth2.client.secret.opentoscana): " + (clientSecret == null ? "null" : "OK"));
		logger.info("rt.oauth2.token.location.opentoscana = " + tokenLocation);
		logger.info("rt.oauth2.client.id.opentoscana = " + clientId);
		logger.info("rt.oauth2.scope.opentoscana = " + scope);
		logger.info("rt.oauth2.grant.type.opentoscana = " + grantType);

	}
	
	public static synchronized String getToken() throws Exception {

//		if (isExpired()) { // TODO sembra che la cache non funzioni, quindi per adesso la eliminiamo
			getTokenRemote();
//		}
		logger.info("token: [" + tokenCache + "]"); 
		return tokenCache;
	}
	
	private static boolean isExpired() {
		
		boolean ret = true;
		if (expires != 0 && tsRemoteGet != null) {
			return tsRemoteGet.getTime() + expires * 1000 > new Date().getTime();
		}
		return ret;
	}
	
    private static void getTokenRemote() throws Exception {

    	tokenCache = null;
    	expires = 0;
    	tsRemoteGet = new Date();
    	
    	Client client = new Client();
    	byte[] authorizationData = ("scope=" + URLEncoder.encode(scope, "UTF-8") + "&client_secret=" + clientSecret + "&grant_type=" + grantType + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")).getBytes("UTF-8");
    	byte[] response = client.send(tokenLocation, Client.Method.POST, Client.Content.FORM, null, authorizationData);
    	JSONObject object = new JSONObject(new String(response, Charset.forName("UTF-8")));
        	
        tokenCache = (String)object.get("access_token");
        expires = object.opt("expires_in") == null ? (object.opt("expires") == null ? 0 : (Integer)object.opt("expires")) : (Integer)object.opt("expires_in");

    }
    
}
