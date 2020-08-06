package it.tasgroup.idp.billerservices.util;

import java.io.StringWriter;

import it.tasgroup.idp.billerservices.jobs.status.AllineamentoPendenzeJobStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Classe di utilità. Converte un bean in xml (tipicamente a scopo di 
 * debug sui log).
 * 
 * @author user
 *
 */
public class ObjectToXml {
	/**
	 * L'oggetto passato come parametro deve essere annotato come
	 * @XmlRootElement
	 * 
	 * @param jaxbAnnotatedObject
	 * @return la serializzazione xml del bean
	 */
	public static String toXml(Object jaxbAnnotatedObject) {

		JAXBContext context;
		try {		
			StringWriter sw = new StringWriter();
			context = JAXBContext.newInstance(jaxbAnnotatedObject.getClass());
	        Marshaller m = context.createMarshaller();
	        //for pretty-print XML in JAXB
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        // Write to System.out for debugging
	        m.marshal(jaxbAnnotatedObject, sw);
	        return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
