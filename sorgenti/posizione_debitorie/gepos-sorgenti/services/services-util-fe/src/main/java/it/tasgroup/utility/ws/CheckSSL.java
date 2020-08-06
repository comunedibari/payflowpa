package it.tasgroup.utility.ws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class CheckSSL {
	
	private static final Logger LOG = LogManager.getLogger(CheckSSL.class);
	
	public static final String OK = "OK";

	public static void logSSLInfo(HashMap serviceInfo, Map http_headers) {
		
		String SenderIDInRequest="-";
		String SenderSILRequest="-";
		String CN="-";
		String DN="-";
		String Issuer="-";
		String IP="-";
		
		LOG.info("======================================================");	
		LOG.info("log SSL info BEGIN");
		LOG.info("======================================================");	
		LOG.info("------------------------------------------------------");	
		LOG.info("Service Info:");
		LOG.info("------------------------------------------------------");	
		Iterator<Map.Entry<String, String>> info = serviceInfo.entrySet().iterator();
		while (info.hasNext()) {
		    Map.Entry<String, String> entry = info.next();
		    LOG.info(entry.getKey() + " = " + entry.getValue());
		    if (entry.getKey().equals("SenderId"))
		    	SenderIDInRequest= entry.getValue();
		    if (entry.getKey().equals("SenderSIL"))
		    	SenderSILRequest= entry.getValue(); 
		}
	
		LOG.info("------------------------------------------------------");
		LOG.info("SSL Info:");
		LOG.info("------------------------------------------------------"); 
		
		 
		 if (((List) http_headers.get("x-ssl-client-cn")) != null && ((List) http_headers.get("x-ssl-client-cn")).get(0) != null)
			 CN = ((List) http_headers.get("x-ssl-client-cn")).get(0).toString();
		 LOG.info("CN: " +CN);
		 
		 if (((List) http_headers.get("x-ssl-client-dn")) != null && ((List) http_headers.get("x-ssl-client-dn")).get(0) != null)
			 DN = ((List) http_headers.get("x-ssl-client-dn")).get(0).toString();
		 LOG.info("DN: " +DN);
		 
		 if (((List) http_headers.get("x-ssl-issuer")) != null && ((List) http_headers.get("x-ssl-issuer")).get(0) != null)
			 Issuer =((List) http_headers.get("x-ssl-issuer")).get(0).toString();
		 LOG.info("Issuer: " +Issuer);
	
		 if (((List) http_headers.get("x-forwarded-for")) != null && ((List) http_headers.get("x-forwarded-for")).get(0) != null)
			  IP =((List) http_headers.get("x-forwarded-for")).get(0).toString();
		 LOG.info("IP: " +IP);
			 
		 
		 LOG.info("------------------------------------------------------");
		 LOG.info("Info for enforcement:");
		 LOG.info("------------------------------------------------------"); 

		 LOG.info("[SenderIDInRequest="+SenderIDInRequest+";SenderSILInRequest="+SenderSILRequest+";CN="+CN+";IP="+IP+"]");
		 
		 LOG.info("======================================================");	
		 LOG.info("log SSL info END");
		 LOG.info("======================================================");	
		
	}
	
	public static void logSslInfo(SSLInfo sslInfo) {
		LOG.info("SSL Info: " + sslInfo.toString());
	}
	public static void logSslInfo(SSLInfo sslInfo, String esitoControlli) {
		if (OK.equals(esitoControlli)) {
			LOG.info("SSL Info: " + sslInfo.toString() + " - Ctrl: " + esitoControlli);
		} else {
			LOG.error("SSL Info: " + sslInfo.toString() + " - Ctrl: " + esitoControlli);
		}
	}
	
	public static class SSLInfo {
		
		private HashMap<String, String> serviceInfo;
		private String senderIDInRequest, senderSILRequest, cn, dn, issuer, ip;

		public SSLInfo(HashMap<String, String> serviceInfo, Map http_headers) {
			this.serviceInfo = serviceInfo;
			if (((List) http_headers.get("x-ssl-client-cn")) != null && ((List) http_headers.get("x-ssl-client-cn")).get(0) != null) {
				cn = ((List) http_headers.get("x-ssl-client-cn")).get(0).toString();
			}
			if (((List) http_headers.get("x-ssl-client-dn")) != null && ((List) http_headers.get("x-ssl-client-dn")).get(0) != null) {
				dn = ((List) http_headers.get("x-ssl-client-dn")).get(0).toString();
			}
			if (((List) http_headers.get("x-ssl-issuer")) != null && ((List) http_headers.get("x-ssl-issuer")).get(0) != null) {
				issuer = ((List) http_headers.get("x-ssl-issuer")).get(0).toString();
			}
			if (((List) http_headers.get("x-forwarded-for")) != null && ((List) http_headers.get("x-forwarded-for")).get(0) != null) {
				ip = ((List) http_headers.get("x-forwarded-for")).get(0).toString();
			}
		}
		
		@Override
		public String toString() {
			StringBuffer ret = new StringBuffer("ServiceInfo ");
			for (String key : serviceInfo.keySet()) {
				ret.append(key + "[" + serviceInfo.get(key) + "] ");
			}
			ret.append("- CN [" + cn + "] DN [" + dn + "] Issuer [" + issuer + "] IP [" + ip + "]");
			return ret.toString();
		}

		public String getSenderId() {
			return serviceInfo.get("SenderId");
		}

		public String getSenderSil() {
			return serviceInfo.get("SenderSIL");
		}

		public String getSenderIDInRequest() {
			return senderIDInRequest;
		}

		public String getSenderSILRequest() {
			return senderSILRequest;
		}

		public String getCn() {
			return cn;
		}

		public String getDn() {
			return dn;
		}

		public String getIssuer() {
			return issuer;
		}

		public String getIp() {
			return ip;
		}
		
	}
}
