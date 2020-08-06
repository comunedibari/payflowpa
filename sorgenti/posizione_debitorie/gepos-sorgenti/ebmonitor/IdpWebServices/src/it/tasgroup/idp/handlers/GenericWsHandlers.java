package it.tasgroup.idp.handlers;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.util.JAXBContextProvider;

public class GenericWsHandlers implements LogicalHandler<LogicalMessageContext> {
	
	private final Log logger = LogFactory.getLog(this.getClass());	

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub
	} 
	
	
	@Override
	public boolean handleFault(LogicalMessageContext arg0) {
		logger.info("handleFault:"+arg0.getMessage());
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext arg0) {
		
		Boolean outbound;
		outbound = (Boolean)arg0.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
//		logger.info("direction:" + outbound);
		Boolean outboundProperty = (Boolean)arg0.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		String direction = "";
        if (outboundProperty.booleanValue()) {
        	logger.info("Outbound message:");
        	direction = "OUTBOUND";
        } else {
        	logger.info("Inbound message:");
        	direction = "INBOUND";
        }		
		
		Source src = arg0.getMessage().getPayload();
		
		JAXBContext jaxbContextCbill = null;
		JAXBContext jaxbContext127Otf = null;
		JAXBContext jaxbContext145Otf = null;
		try {
//			logger.info(" trying to read CBill message");
			// jaxbContextCbill = JAXBContext.newInstance("it.tasgroup.psp.cbill");
			jaxbContextCbill = JAXBContextProvider.getInstance()
				.getJAXBContext (
					new String [] {
						  "it.tasgroup.psp.cbill"
					}
				);
			
//			logger.info(" trying to read Rfc127 message");
			// jaxbContext127Otf = JAXBContext.newInstance("it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze:it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze:it.toscana.rete.cart.servizi.iris_1_1.idpheader:it.toscana.rete.cart.servizi.iris_1_1.idpinclude");
			jaxbContext127Otf = JAXBContextProvider.getInstance()
				.getJAXBContext (
					new String [] {
						  "it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpheader"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpinclude"
					}
				);
//			logger.info(" trying to read Rfc145 message");
			// jaxbContext145Otf = JAXBContext.newInstance("it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento:it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento:it.toscana.rete.cart.servizi.iris_1_1.idpesito:it.toscana.rete.cart.servizi.iris_1_1.idpheader:it.toscana.rete.cart.servizi.iris_1_1.idpinclude");
			jaxbContext145Otf = JAXBContextProvider.getInstance()
				.getJAXBContext (
					new String [] {
						  "it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpinformativapagamento"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpesito"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpheader"
						, "it.toscana.rete.cart.servizi.iris_1_1.idpinclude"				}
				);
			
		} catch (JAXBException e) {
			logger.info("context not found:" + e.getMessage());
		}
		Object obj = null;
		
		//trying to read the payload and the request type
		DOMSource sourcePayload = (DOMSource) arg0.getMessage().getPayload();
	    String tipoRichiesta = sourcePayload.getNode().getLocalName();
	    //cbill=BillDataRequest
	    //rfc127=IdpAllineamentoPendenzeEnteOTF
	    //rfc127multi=IdpAllineamentoPendenzeMultiOTF
	    //rfc145=IdpVerificaStatoPagamenti
	    //EsitoRfc127=IdpAllineamentoPendenzeEnteOTF.Esito
	    logger.info("Tipo Richiesta:" + tipoRichiesta);
	    
		
		//adesso loggami questo oggetto
		Marshaller marshaller;
		ByteArrayOutputStream output = null;
		
		boolean guessed = true;
		boolean guessed127 = true;
		
		//cbill
		if ("RendicontazionePSP.Input".equals(tipoRichiesta)) {
			logger.info("found cbill request");
			output = checkCBill(arg0, jaxbContextCbill, output, direction);	
		}
		if ("RendicontazionePSP.Esito".equals(tipoRichiesta)) {
			logger.info("found cbill response");
			output = checkCBill(arg0, jaxbContextCbill, output, direction);	
		}						
		
		//127
		if ("IdpAllineamentoPendenzeEnteOTF".equals(tipoRichiesta)) {
			logger.info("found rfc127 request");
			checkRfc127(arg0, jaxbContext127Otf, output, direction);				
		}
		//127esito
		if ("IdpAllineamentoPendenzeEnteOTF.Esito".equals(tipoRichiesta)) {
			logger.info("found rfc127 response");
			checkRfc127(arg0, jaxbContext127Otf, output, direction);				
		}		
		
		//145
		if ("IdpVerificaStatoPagamenti".equals(tipoRichiesta)) {
			logger.info("found rfc145 request");
			checkRfc145(arg0, jaxbContext145Otf, output, direction);									
		}
		if ("IdpVerificaStatoPagamenti.Esito".equals(tipoRichiesta)) {
			logger.info("found rfc145 response");
			checkRfc145(arg0, jaxbContext145Otf, output, direction);									
		}		
		

		
		return true;
	}


	/**
	 * 
	 * @param arg0
	 * @param jaxbContext145Otf
	 * @param output
	 * @param direction 
	 */
	private void checkRfc145(LogicalMessageContext arg0,
			JAXBContext jaxbContext145Otf, ByteArrayOutputStream output, String direction) {
		Object obj;
		Marshaller marshaller;
		//allora vediamo se � un 145Otf piuttosto che 127otf oppure Cbill
		try {					
			//getting object from payload
			obj = arg0.getMessage().getPayload(jaxbContext145Otf);
			
			//byte array
			output = new ByteArrayOutputStream();
			
			marshaller = jaxbContext145Otf.createMarshaller();	
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
			//marshall
			marshaller.marshal(obj, output);	
			
			//recupero message
			String st = new String(output.toByteArray());
			
			//finally I can log a message
			logger.info("VerificaStato, messaggio " + direction + " = \n " + st);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("this is not a 145 message, Jaxb Error = " + e.getMessage());
		}	catch (RuntimeException e) {
			// TODO: handle exception
			logger.info("this is not a 145 message , Runtime Error =" + e.getMessage());	
		}
	}


	/**
	 * 
	 * @param arg0
	 * @param jaxbContext127Otf
	 * @param output
	 * @param direction 
	 */
	private void checkRfc127(LogicalMessageContext arg0,
			JAXBContext jaxbContext127Otf, ByteArrayOutputStream output, String direction) {
		Object obj;
		Marshaller marshaller;
		//allora vediamo se � un 127otf piuttosto che un Cbill
		try {					
			
			//byte array
			output = new ByteArrayOutputStream();
			
			//getting object from payload
			obj = arg0.getMessage().getPayload(jaxbContext127Otf);
			
			marshaller = jaxbContext127Otf.createMarshaller();	
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
			//marshall
			marshaller.marshal(obj, output);	
			
			//recupero message
			String st = new String(output.toByteArray());
			
			//finally I can log a message
			logger.info("PosizioneDebitoria, messaggio " + direction + " = \n " + st);
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("this is not a 127 message, Jaxb Error = " + e.getMessage());
			//guessed127 = false;
		}	catch (RuntimeException e) {
			// TODO: handle exception
			logger.info("this is not a 127 message , Runtime Error =" + e.getMessage());	
		}
	}


	/**
	 * 
	 * @param arg0
	 * @param jaxbContextCbill
	 * @param output
	 * @param direction 
	 * @return
	 */
	private ByteArrayOutputStream checkCBill(LogicalMessageContext arg0,
			JAXBContext jaxbContextCbill, ByteArrayOutputStream output, String direction) {
		Object obj;
		Marshaller marshaller;
		try {
			marshaller = jaxbContextCbill.createMarshaller();			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));			
			//byte array
			output = new ByteArrayOutputStream();
			
			//getting object from payload
			obj = arg0.getMessage().getPayload(jaxbContextCbill);

			//marshall
			marshaller.marshal(obj, output);				
			//recupero message
			String st = new String(output.toByteArray());
			
			//finally I can log a message
			logger.info("UploadRendicontazione (CBill), messaggio " + direction + " = \n" + st);	
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.info("this is not a CBILL message, Jaxb Error = " + e.getMessage());	
			//guessed = false;
		} catch (RuntimeException e) {
			// TODO: handle exception
			logger.info("this is not a CBILL message , Runtime Error =" + e.getMessage());	
		}
		return output;
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

}
