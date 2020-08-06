/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.client.utility;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Igor Tamiazzo
 *
 */
public class Utils {
	
	public static XMLGregorianCalendar getDateXML(Date data) throws DatatypeConfigurationException {
		
		GregorianCalendar dateXml = new GregorianCalendar();
		dateXml.setTime(data);
		XMLGregorianCalendar dateFromXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateXml);
		dateFromXMLGregorianCalendar.setFractionalSecond(null);
		dateFromXMLGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return dateFromXMLGregorianCalendar;
		
	}
	
	/*
	 * Converts XMLGregorianCalendar to java.util.Date in Java
	 */
	public static Date toDate(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
	}


}
