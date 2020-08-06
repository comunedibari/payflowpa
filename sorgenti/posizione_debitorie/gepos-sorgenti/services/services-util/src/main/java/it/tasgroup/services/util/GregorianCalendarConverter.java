/**
 * 
 */
package it.tasgroup.services.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author pazzik
 *
 */
public class GregorianCalendarConverter {
		
	private static final Logger LOGGER = LogManager.getLogger(GregorianCalendarConverter.class.getName());

	
	public static XMLGregorianCalendar convertDate(Date date, boolean showTime){
		
		XMLGregorianCalendar xmlDate = null;
		
		try{
			
			 if (date!=null){
				 
				GregorianCalendar gregorianDtScad = new GregorianCalendar();
				
				gregorianDtScad.setTime(date);
		
				xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianDtScad);			
				
				if (!showTime){
					
					xmlDate.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, null);
					
					xmlDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);	
				}
				
			 }
			 
		} catch (DatatypeConfigurationException e) {
			
			LOGGER.error("GregorianCalendarConverter - convertDate: ", e);
			
		}
				
		return xmlDate;
	}

}
