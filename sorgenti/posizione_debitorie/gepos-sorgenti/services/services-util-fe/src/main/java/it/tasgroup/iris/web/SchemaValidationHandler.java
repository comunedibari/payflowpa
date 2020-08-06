package it.tasgroup.iris.web;

import it.tasgroup.iris.util.XMLUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public abstract class SchemaValidationHandler implements LogicalHandler<LogicalMessageContext>{
	
	/** 
	 * 
	 * In caso di REQUEST, viene scritta sul file di log e poi validata rispetto all'XSD.
	 *
	 * In caso di RESPONSE si scrive il suo contenuto sul file di log.
	 * 
	 */
	public boolean handleMessage(LogicalMessageContext context) {
		
		

		try {
			
			Boolean isOutBound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

			LogicalMessage lm = context.getMessage();
			
			Source payload = lm.getPayload();
			StreamResult res = new StreamResult(new StringWriter());
			String message = "";
			
			Transformer trans;
			trans = TransformerFactory.newInstance().newTransformer();
			trans.transform(payload, res);
			message = res.getWriter().toString();
			
			//Convert in UTF-8. Commentato per timore di regressioni.
			//message = new String(message.getBytes(),Charset.forName("UTF-8"));
			
			getLogger().info((isOutBound?"OUTPUT: ":"INPUT: ")+" - "+message.replaceAll("[\r\n]", ""));

			getLogger().info((isOutBound?"OUTPUT SIZE: ":"INPUT SIZE: ")+" - "+message.length());

			if (isOutBound)
				return true;

			// Validate
			XMLUtils.validate(message, getSchemaURL());
		} catch (TransformerConfigurationException e) {
			// When Source payload Transformer object could not be obtained.
			getLogger().error("Errore durante la validazione della richiesta", e);
			throw new WebServiceException(e);
		} catch (TransformerFactoryConfigurationError e) {
			// When Source payload Transformer object could not be obtained.
			getLogger().error("Errore durante la validazione della richiesta", e);
			throw new WebServiceException(e);
		} catch (TransformerException e) {
			// When Source payload could not be transformed to String.
			getLogger().error(e);
			throw new WebServiceException(e);
		} catch (MalformedURLException e) {
			// When URL to schema is invalid.
			getLogger().error("Errore durante la validazione della richiesta", e);
			throw new WebServiceException(e);
		} catch (ParserConfigurationException e) {
			// When parser needed for validation could not be obtained.
			getLogger().error("Errore durante la validazione della richiesta", e);
			throw new WebServiceException(e);
		} catch (IOException e) {
			// When something is wrong with IO.
			getLogger().error(e);
			throw new WebServiceException(e);
		} catch (SAXException e) {
			// When XSD-schema validation fails.
			getLogger().error("Errore durante la validazione della richiesta", e);
			throw new WebServiceException(e);
		} catch(Exception e){			
			// Whenever this method fails.
			getLogger().error("Errore durante la validazione della richiesta", e);
			throw new WebServiceException(e);			
		}

		return true;
	}	

	/** 
	 * 
	 * In caso di FAULT si scrive il Fault Message sul file di log.
	 * 
	 */
	@Override
	public boolean handleFault(LogicalMessageContext context){
		
		LogicalMessage lm = context.getMessage();
		
		Source payload = lm.getPayload();
        
	    StreamResult res = new StreamResult(new StringWriter());
	    
		String message = "";
		  
		try {		    	 
				Transformer trans;
				
				trans = TransformerFactory.newInstance().newTransformer();
				
				trans.transform(payload, res);
				
				message = res.getWriter().toString();
				
				//Convert in UTF-8. Commentato per timore di regressioni.
				//message = new String(message.getBytes(),Charset.forName("UTF-8"));
				
				getLogger().info("FAULT: - "+message.replaceAll("[\r\n]", ""));

			} catch (TransformerConfigurationException e) {
				// When Source payload Transformer object could not be obtained.
				getLogger().error(e);
				throw new WebServiceException(e);
			} catch (TransformerFactoryConfigurationError e) {
				// When Source payload Transformer object could not be obtained.
				getLogger().error(e);
				throw new WebServiceException(e);
			} catch (TransformerException e) {
				// When Source payload could not be transformed to String.
				getLogger().error(e);
				throw new WebServiceException(e);
			}

		return true;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub

	}
	
	public abstract String getSchemaURL();
	
	public abstract Logger getLogger();

}
