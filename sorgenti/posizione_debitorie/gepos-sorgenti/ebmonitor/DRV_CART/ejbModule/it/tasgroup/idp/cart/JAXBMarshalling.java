package it.tasgroup.idp.cart;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class JAXBMarshalling {
	
	
	
	public static String getXMLString(JAXBElement<?> document) throws Exception {
		
		StringWriter writer = new StringWriter();
		String xmlString = null;

		Class<?> clazz = document.getValue().getClass();
		ValidationEventCollector validationCollector = new ValidationEventCollector();
		try {
			JAXBContext context = JAXBContext.newInstance(clazz.getPackage().getName());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setEventHandler(validationCollector);
			m.marshal(document, writer);
			xmlString = writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			for (ValidationEvent event : validationCollector.getEvents()) {
				System.out.println("\nEVENT");
				System.out.println("SEVERITY:  " + event.getSeverity());
				System.out.println("MESSAGE:  " + event.getMessage());
				System.out.println("LINKED EXCEPTION:  " + event.getLinkedException());
				System.out.println("LOCATOR");
				System.out.println("    LINE NUMBER:  " + event.getLocator().getLineNumber());
				System.out.println("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
				System.out.println("    OFFSET:  " + event.getLocator().getOffset());
				System.out.println("    OBJECT:  " + event.getLocator().getObject());
				System.out.println("    NODE:  " + event.getLocator().getNode());
				System.out.println("    URL:  " + event.getLocator().getURL());
			}
		}
		return xmlString;
	}
	

	public static String getXMLString(String context, Object objectJAXB) throws JAXBException, SAXException {
		StringWriter xml = new StringWriter();
		String xmlString = new String();
		ValidationEventCollector validationCollector = new ValidationEventCollector();
		try {
			
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			URL fileXsdUrl = Thread.currentThread().getContextClassLoader().getResource("xsd/PagInf_RPT_RT_6_0_1.xsd");

			Schema schema = sf.newSchema(fileXsdUrl);

			JAXBContext jaxbLocalContext = JAXBContext.newInstance(context);
			Marshaller marshaller = jaxbLocalContext.createMarshaller();
			marshaller.setSchema(schema);
			marshaller.setEventHandler(validationCollector);
			marshaller.marshal(objectJAXB, xml);
			xmlString = xml.toString();

			for (ValidationEvent event : validationCollector.getEvents()) {
				System.out.println("\nEVENT");
				System.out.println("SEVERITY:  " + event.getSeverity());
				System.out.println("MESSAGE:  " + event.getMessage());
				System.out.println("LINKED EXCEPTION:  " + event.getLinkedException());
				System.out.println("LOCATOR");
				System.out.println("    LINE NUMBER:  " + event.getLocator().getLineNumber());
				System.out.println("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
				System.out.println("    OFFSET:  " + event.getLocator().getOffset());
				System.out.println("    OBJECT:  " + event.getLocator().getObject());
				System.out.println("    NODE:  " + event.getLocator().getNode());
				System.out.println("    URL:  " + event.getLocator().getURL());
			}

		} catch (JAXBException e) {
			e.printStackTrace();

			for (ValidationEvent event : validationCollector.getEvents()) {
				System.out.println("\nEVENT");
				System.out.println("SEVERITY:  " + event.getSeverity());
				System.out.println("MESSAGE:  " + event.getMessage());
				System.out.println("LINKED EXCEPTION:  " + event.getLinkedException());
				System.out.println("LOCATOR");
				System.out.println("    LINE NUMBER:  " + event.getLocator().getLineNumber());
				System.out.println("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
				System.out.println("    OFFSET:  " + event.getLocator().getOffset());
				System.out.println("    OBJECT:  " + event.getLocator().getObject());
				System.out.println("    NODE:  " + event.getLocator().getNode());
				System.out.println("    URL:  " + event.getLocator().getURL());
			}
			throw e;
		} catch (SAXException e) {
			e.printStackTrace();
			throw e;
		}
		return xmlString;
	}

	public static byte[] getXMLByteArray(String context, Object objectJAXB) throws JAXBException, SAXException {
		return getXMLString(context, objectJAXB).getBytes(); // TODO: CHARSET???
	}

	public static Object getObject(String context, String xmlStringObj) throws JAXBException {
		Unmarshaller unmarshaller = null;
		Object objectJAXB = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(context);
			unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setEventHandler(new ValidationEventHandler() {

				@Override
				public boolean handleEvent(ValidationEvent event) {
					System.out.println(event.getMessage());				   
					return false;
				}});
			StringBuffer xmlString = new StringBuffer(xmlStringObj);
			objectJAXB = unmarshaller.unmarshal(new StringReader(xmlString.toString()));
		} catch (JAXBException e) {
			e.printStackTrace();
			throw e;
		}
		return objectJAXB;
	}

	public static Object getObject(String context, byte[] xmlByteArrayObj) throws JAXBException {
		return getObject(context, new String(xmlByteArrayObj)); // TODO: CHARSET???
	}
	 
	public static void main(String[] args) {
/*		
	File f = new File("");
        JAXBElement<IdpInformativaPagamento> idpJaxb = (JAXBElement<IdpInformativaPagamento>) JAXBMarshalling.getObject("it.tasgroup.idp.ws.otf.rfc145:it.tasgroup.idp.ws.otf.common", temp);
   	     IdpInformativaPagamento rt = idpJaxb.getValue();
        int c=1; 
        */
	}
	
}

