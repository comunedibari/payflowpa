package it.tasgroup.idp.handlers;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SoapHandlers implements javax.xml.ws.handler.soap.SOAPHandler<SOAPMessageContext> {
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	} 
	
	
	/**
	 * 
	 * @param smc
	 */
	 private void logToSystemOut(SOAPMessageContext smc) {  
         Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);  
 
         if (outboundProperty.booleanValue()) {  
             logger.info("Outgoing message:");  
         } else {  
             logger.info("Incoming message:");  
         }  

     }



	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		logger.info("handleFault:"+arg0.getMessage());
		return false;
	}



	@Override
	public boolean handleMessage(SOAPMessageContext arg0) {
		SOAPMessage src = arg0.getMessage();
		//logger.info("MimeHeaders " + src.getMimeHeaders().toString() );
				
		Boolean outboundProperty = (Boolean)arg0.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty.booleanValue()) {
        	logger.info("Outbound message:");
        } else {
        	logger.info("Inbound message:");
        }

		
		try {
			SOAPBody body = src.getSOAPBody();			
			Node nodo = (Node) body.getFirstChild();			
			//logger.info("NextSibling " + nodo.getNextSibling() );
			String requestType = "";
			if (nodo!=null && nodo.getNextSibling()!=null) {
				requestType = nodo.getNextSibling().getLocalName();
				logger.info(" Request(NextSibling) " + requestType );				
			} else {
				requestType = nodo.getLocalName();
				logger.info(" Request(LocalName) " + requestType );
			} 
			
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return true;
	}


	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}





}
