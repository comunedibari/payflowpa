package it.tasgroup.iris.paymentws.ccp.validator;

import it.tasgroup.iris.util.XMLUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * @author pazzik
 *
 */
public class CCPSchemaValidator implements javax.xml.ws.handler.soap.SOAPHandler<SOAPMessageContext> {
	
private static final String SCHEMA_URL = "/PaPerNodoPagamentoPsp.wsdl";
	
	private static final Logger LOGGER = LogManager.getLogger(CCPSchemaValidator.class);

	@Override
	public boolean handleMessage(SOAPMessageContext mc) {
	      
		     Boolean isOutBound = (Boolean) mc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		      
		     SOAPMessage sm = mc.getMessage();
		
		     try {		    	 
				    SOAPBody sb = sm.getSOAPBody();
			        
			        // clono il SOAPBody perchè il metodo extractContentAsDocument()
					// lo rende inutilizzabile privandolo del FirstChild.
					SOAPBody sb1 = (SOAPBody) sb.cloneNode(true);
					
					Document doc = sb1.extractContentAsDocument();
					
					String message = XMLUtils.parseNodeToString(doc);
					
					//Convert in UTF-8. Commentato per timore di regressioni.
					//message = new String(message.getBytes(),Charset.forName("UTF-8"));
					
					getLogger().info((isOutBound?"OUTPUT: ":"INPUT: ")+" - "+message.replaceAll("[\r\n]", ""));            
					
					if (isOutBound)
						return true;
					
					// Validate
					XMLUtils.validate(message, getSchemaURL());
				           
			} catch (SOAPException e) {
				 
				// When SOAP message is invalid.
				getLogger().error(e);
			    throw new ProtocolException(e);
			    
			} catch (MalformedURLException e) {
			 	
				// When URL to schema is invalid.
				getLogger().error(e);
				throw new WebServiceException(e);
				
			} catch (ParserConfigurationException e) {
				
				// When parser needed for validation could not be obtained.
				getLogger().error(e);
				throw new WebServiceException(e);
				
			} catch (IOException e) {
				
				// When something is wrong with IO.
				getLogger().error(e);
				throw new WebServiceException(e);
				
			} catch (SAXException e) {
			
				// When XSD-schema validation fails.
				getLogger().error(e);
				throw new WebServiceException(e);
			
			}
      
      return true;
   }
   
   @Override
   public Set<QName> getHeaders() {
      return null;
   }

   @Override
   public void close(MessageContext mc) {
   }

   @Override
   public boolean handleFault(SOAPMessageContext mc) {
	   
	  SOAPMessage sm = mc.getMessage();
	  
	  try {		    	 
		    SOAPBody sb = sm.getSOAPBody();
	        
	        // clono il SOAPBody perchè il metodo extractContentAsDocument()
			// lo rende inutilizzabile privandolo del FirstChild.
			SOAPBody sb1 = (SOAPBody) sb.cloneNode(true);
			
			Document doc = sb1.extractContentAsDocument();
			
			String message = XMLUtils.parseNodeToString(doc);
	  
			getLogger().info("FAULT: - "+message.replaceAll("[\r\n]", "")); 
			
		  
	  } catch (SOAPException e) {
		  
		  	// When SOAP message is invalid.
			getLogger().error(e);
			
		    throw new ProtocolException(e);
	  }
	  
      return true;
   }
   
   public String getSchemaURL(){
	   
	   return SCHEMA_URL;
	   
   }
   
   public Logger getLogger(){
	   
	   return LOGGER;
   }

}
