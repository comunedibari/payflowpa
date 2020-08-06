package it.tasgroup.idp.proxysiope.gdc;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/*
 *  Impossibile usare java.net.HttpURLConnection: https://stackoverflow.com/questions/25163131/httpurlconnection-invalid-http-method-patch
 *  Usiamo la libreria di apache: http://hc.apache.org/httpcomponents-client-ga/
 *  
 *  SSL: https://hc.apache.org/httpclient-3.x/sslguide.html
 */

public class Client {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	public byte[] send(String url) throws Exception {
		
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalArgumentException("'url' non valorizzata");
		}
		
		byte[] ret = null;
		
		SSLContext sslContext = SSLContexts.createSystemDefault();
		SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslConnectionFactory).register("http", new PlainConnectionSocketFactory()).build();
		BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(registry);
		HttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).setSSLSocketFactory(sslConnectionFactory).setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();
		
		HttpGet request = new HttpGet(url);
		request.setHeader("Connection", "Keep-Alive");
        HttpResponse response = httpclient.execute(request);
        logResponse(url, response);

        ret = EntityUtils.toByteArray(response.getEntity());
		
		return ret;
		
	}

	private void logResponse(String url, HttpResponse response) {
		logger.info("Siope - URL: " + url + " - Status: " + response.getStatusLine().getStatusCode() + " [" + response.getStatusLine().getReasonPhrase() + "]");
	}
	
}
