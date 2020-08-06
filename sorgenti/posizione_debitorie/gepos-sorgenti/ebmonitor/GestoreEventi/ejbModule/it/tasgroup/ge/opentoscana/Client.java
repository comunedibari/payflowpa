package it.tasgroup.ge.opentoscana;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 *  Impossibile usare java.net.HttpURLConnection: https://stackoverflow.com/questions/25163131/httpurlconnection-invalid-http-method-patch
 *  Usiamo la libreria di apache: http://hc.apache.org/httpcomponents-client-ga/
 */

public class Client {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	public enum Method {

		POST("POST"), PATCH("PATCH"); // metodi supportati

		private String valore;

		private Method(String valore) {
			this.valore = valore;
		}

		public String getValore() {
			return this.valore;
		}

	}

	public enum Content {

		FORM("application/x-www-form-urlencoded"), JSON("application/json");

		private String valore;

		private Content(String valore) {
			this.valore = valore;
		}

		public String getValore() {
			return this.valore;
		}

	}
	
	public byte[] send(String url, Method method, Content content, HashMap<String, String> header, byte[] body) throws Exception {
		
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalArgumentException("'url' non valorizzata");
		}
		if (method == null) {
			throw new IllegalArgumentException("'method' non valorizzato");
		}
		if (content == null) {
			throw new IllegalArgumentException("'content' non valorizzato");
		}
		
		byte[] ret = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		if (Method.PATCH == method) {
			HttpPatch request = new HttpPatch(url);
			request.setHeader("Content-Type", content.getValore());
			request.setHeader("Connection", "Keep-Alive");
	        if (header != null && !header.isEmpty()) {
	        	for (String key : header.keySet()) {
	        		request.setHeader(key, header.get(key));
	        	}
	        }
	        HttpEntity httpEntity = new ByteArrayEntity(body);
	        request.setEntity(httpEntity);
	        CloseableHttpResponse response = httpclient.execute(request);
	        logResponse(url, method, response);
			try {
				ret = EntityUtils.toByteArray(response.getEntity());
			} finally {
			    response.close();
			}
		} else if (Method.POST == method) {
			HttpPost request = new HttpPost(url);
			request.setHeader("Content-Type", content.getValore());
			request.setHeader("Connection", "Keep-Alive");
	        if (header != null && !header.isEmpty()) {
	        	for (String key : header.keySet()) {
	        		request.setHeader(key, header.get(key));
	        	}
	        }
	        HttpEntity httpEntity = new ByteArrayEntity(body);
	        request.setEntity(httpEntity);
	        CloseableHttpResponse response = httpclient.execute(request);
	        logResponse(url, method, response);
			try {
				ret = EntityUtils.toByteArray(response.getEntity());
			} finally {
			    response.close();
			}
		}
		
		httpclient.close();
		
		return ret;
		
	}

	private void logResponse(String url, Method method, CloseableHttpResponse response) {
		logger.info("OpenToscana - URL: " + url + " - Method: " + method.getValore() + " - Status: " + response.getStatusLine().getStatusCode() + " [" + response.getStatusLine().getReasonPhrase() + "]");
	}
	
}
