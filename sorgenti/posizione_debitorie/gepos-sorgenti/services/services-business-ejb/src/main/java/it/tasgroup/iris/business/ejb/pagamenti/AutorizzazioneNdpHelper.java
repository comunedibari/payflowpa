package it.tasgroup.iris.business.ejb.pagamenti;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.nch.is.fo.stati.pagamenti.data.AutorizzaPagamentoResponse;
import it.nch.is.fo.stati.pagamenti.data.FaultType;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

public class AutorizzazioneNdpHelper {
	private static final Logger logger = LogManager.getLogger(AutorizzazioneNdpHelper.class);
	
	public static AutorizzaPagamentoResponse chiamaAutorizzazionePagamentoWs(String url, String codiceContesto, String identificativoDominio, String identificativoPagamento, String tipoOperazione, String importo, String urlServlet) {
		HttpClient httpclient = null;
		HttpPost httpPost = null;
		AutorizzaPagamentoResponse  respObj  = null;
		
			   
		try {
			if (urlServlet != null && !urlServlet.equals("")){
				httpPost = new HttpPost(urlServlet);
			}
			else{
				//httpPost = new HttpPost("http://localhost:8080/IdpBillerNdpServicesClient/PagamentiNdpService");
				// lanciare eccezione
				throw new Exception("Non è stato possibile chiamare il Ws Autorizzazione Pagamento!! URL non valorizzata sul tributo");
			}

			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("TipoOperazione", tipoOperazione));
			nvps.add(new BasicNameValuePair("IdentificativoUnicoVersamento", identificativoPagamento));
			nvps.add(new BasicNameValuePair("IdentificativoDominio", identificativoDominio));
			nvps.add(new BasicNameValuePair("CodiceContestoPagamento", codiceContesto));
			nvps.add(new BasicNameValuePair("UrlServizio", url));
			if (importo !=null)
				nvps.add(new BasicNameValuePair("ImportoPagamento", importo));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			httpclient = HttpClients.createDefault();
	
			HttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			
			respObj = buildResponseObj( instream );

		}catch (Exception e){
			logger.error("Errore durante la chiamata al webservice di Autorizzazione Pagamento", e);
			respObj = buildFaultResponseObj(e);
		}
		return respObj;
	}


	
	private static Element getMessage(InputStream xml) throws Exception  {
		try{
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			Document doc = builder.parse( xml );
			
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList allNodes = doc.getElementsByTagName("soap:Body");
			Element eElement = null;
			
		    for (int i = 0; i < allNodes.getLength(); i++) {
		        Node node = allNodes.item(i);
		        
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		        	eElement = (Element) node.getFirstChild();
		        }
		    }

		    return eElement;
			
		} catch(Exception e) {
			throw new Exception("Errore di validazione sintattica: " + e.getMessage(), e);
		}
	}


	private static String getChildElement(Element eElement, String localName ) {
		if (eElement.getElementsByTagName(localName)!=null && eElement.getElementsByTagName(localName).item(0) !=null)
			return eElement.getElementsByTagName(localName).item(0).getTextContent();
		return null;
	}
	
	private static AutorizzaPagamentoResponse buildResponseObj(InputStream instream ) throws Exception{

		Element body = getMessage(instream);
		if (body==null)
			throw new Exception("Impossibile recuperare il Body della Risposta");
		
		AutorizzaPagamentoResponse responseObj = new AutorizzaPagamentoResponse();

		FaultType fault = null;
	
		NodeList listFault = body.getElementsByTagName("Fault");
		Element eElement = null;
		String faultCode = null;
		String faultDescription = null;
	    
		String tmpElem = null;
		if (listFault!=null && listFault.getLength()>0){
			for (int i = 0; i < listFault.getLength(); i++) {
		        Node node = listFault.item(i);
		        
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		        	eElement = (Element) node.getFirstChild();
		        	if (eElement !=null){
		        		Node nodo1 = eElement.getFirstChild();
		        		if (nodo1 !=null){
		        			faultCode = nodo1.getNodeValue();
		        			//System.out.println("fault code : " + faultCode);
		        		}
		        		Node nodo2 = eElement.getNextSibling();
		        		if (nodo2 !=null){
		        			Node node3 = eElement.getNextSibling().getNextSibling();
		        			if (node3 !=null){
		        				if (node3.getFirstChild()!=null){
		        					faultDescription  = node3.getFirstChild().getNodeValue();
		        					//System.out.println("fault description : " + faultDescription);
		        				}
		        			}
		        		}
		        		
		        	}
					fault = new FaultType();
					fault.setFaultCode(faultCode);
					fault.setFaultDescription(faultDescription);
					responseObj.setFault(fault);
		        }
			}
		}else{
			
			
			try {
				tmpElem  = getChildElement(body, "IdentificativoUnicoVersamento");
				if (tmpElem !=null)
					responseObj.setCodiceIdentificativoUnivocoDebitore(tmpElem);

			} catch (Exception e) {
				throw new Exception("Impossibile recuperare il valore di IdentificativoUnicoVersamento", e);
			}
			try {
				responseObj.setIdMittente(getChildElement(body, "IdentificativoDominio"));

			} catch (Exception e) {
				throw new Exception("Impossibile recuperare il valore di IdentificativoDominio", e);
			}

			try {
				String importoPagamentoStr=getChildElement(body, "ImportoPagamento");
				responseObj.setImportoPagamento(new BigDecimal(importoPagamentoStr));

			} catch (Exception e) {
				throw new Exception("Impossibile recuperare il valore di ImportoPagamento", e);
			}

			try {
				responseObj.setCausaleVersamento(getChildElement(body, "CausaleVersamento"));

			} catch (Exception e) {
				throw new Exception("Impossibile recuperare il valore di CausaleVersamento", e);
			}

			tmpElem = getChildElement(body, "AnagraficaDebitore");
			if (tmpElem !=null)
				responseObj.setAnagraficaDebitore(tmpElem);


			tmpElem = getChildElement(body, "AnnoRiferimento");
			if (tmpElem !=null)
				responseObj.setAnnoRiferimento(tmpElem);

			tmpElem = getChildElement(body, "DescrizioneMittente");
			if (tmpElem !=null)
				responseObj.setDescrizioneMittente(tmpElem);

			tmpElem = getChildElement(body, "IdDebito");
			if (tmpElem !=null)
				responseObj.setIdDebito(tmpElem);

			tmpElem = getChildElement(body, "IdRiscossore");
			if (tmpElem !=null)
				responseObj.setIdRiscossore(tmpElem);

			tmpElem = getChildElement(body, "TipoDebito");
			if (tmpElem !=null)
				responseObj.setTipoDebito(tmpElem);

			tmpElem = getChildElement(body, "TipoIdentificativoUnivocoDebitore");
			if (tmpElem !=null)
				responseObj.setTipoIdentificativoUnivocoDebitore(tmpElem);

		}	


	

	return responseObj;

	}
	

	
	private static AutorizzaPagamentoResponse buildFaultResponseObj(Exception e) {
		AutorizzaPagamentoResponse responseObj = new AutorizzaPagamentoResponse();

		FaultType fault = null;
		
		fault = new FaultType();
		
		fault.setFaultCode(EnumBusinessReturnCodes.KO_GENERICO.getChiave());
		fault.setFaultDescription(e.getMessage());
		responseObj.setFault(fault);
		return responseObj;
	}
	
	public static String convertStreamToString(InputStream is) throws IOException {
		
		if (is != null) {
	
			Writer writer = new StringWriter();
			
			char[] buffer = new char[1024];
			
			try {
			
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			int n;
			
			while ((n = reader.read(buffer)) != -1) {
			
			writer.write(buffer, 0, n);
			
			}
			
			} finally {
			
			is.close();
			
			}
		
			return writer.toString();
		
		} else {       
		
			return "";
		
		}
		
		}
}


