package it.tasgroup.ge.util;

import it.tasgroup.ge.pojo.CommunicationEventDetail;
import it.tasgroup.ge.pojo.MessaggioLogico;
import it.tasgroup.idp.util.IrisProperties;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EmailUtil {
	
	/*** Loggers ***/
	private final Log logger = LogFactory.getLog(this.getClass());
	
	
	private static String propFileName = "template-email";
	private static final String GESTORE_EVENTI_LOCALE_TO_PROPERTY = "GESTORE_EVENTI_LOCALE";
	private static final String GESTORE_EVENTI_LOCALE_DEFAULT = "it_IT";
	/**
	 * Metodo che crea l'oggetto e il testo dell'email recuperando il template da un file di properties
	 * @param eventData contiene la lista dei valori da sostituire nel template del testo
	 * @param templateName contiene nome del template del messaggio
	 * @return il messaggio logico con oggetto e il testo valorizzato
	 * @throws Exception Eccezione sollevata nel caso in cui si verifichino errori nel recupero del template o nella sostituzione dei placeholder
	 */
	public MessaggioLogico createEmail(CommunicationEventDetail eventData, String templateName)throws Exception{
		MessaggioLogico email = new MessaggioLogico();
		
		String locale = IrisProperties.getProperty(GESTORE_EVENTI_LOCALE_TO_PROPERTY, GESTORE_EVENTI_LOCALE_DEFAULT, false);
		//String language = "IT";
		if (eventData.getLocale()!=null && !eventData.getLocale().equals("")){
			locale = eventData.getLocale();
		}
		String templateSubjectName = templateName + ".subjectmail";
		String templateMessageName = templateName + ".bodymail";
		String template = getTemplate(templateSubjectName,locale);
		String oggetto = null;
		String msgText = null;
		if (template != null){
			oggetto = createText(eventData,template);
		}
		else{
			throw new Exception("Non è stato possibile recuperare il template " + templateName);
		}
		template = getTemplate(templateMessageName,locale);
		if (template != null){
			msgText = createText(eventData,template);
		}else{
			throw new Exception("Non è stato possibile recuperare il template " + templateName);
		}
		
		email.setSubject(oggetto);
		email.setContent(msgText);

		
		logger.trace("EmailDeliveryService.createEmail - END");
		return email;
	}

	

	/**
	 * Metodo che crea il testo dell'email a partire dal template del testo, 
	 * e sostituendo i placeholder con i valori passati in input
	 * @param eventData contienelista dei valori da sostituire
	 * @param text contiene template del messaggio
	 * @return Il testo della mail
	 * @throws Exception Eccezione sollevata nel caso in cui si verifichino nella sostituzione dei placeholder
	*/
	private String createText(CommunicationEventDetail eventData, String text) throws Exception {
		//Collection placeHolder = eventData.getPlaceHolderMessageKeys();
		String nameField = null;
		 Field[] fields = eventData.getClass().getDeclaredFields();
		 for (int i = 0; i < fields.length; i++)
	     {
	            //System.out.println("Field found: " +
	            //    fields[i].getName());
	            Object fieldValue = (Object) fields[i].get(eventData);
	            if (fieldValue !=null){
	            	nameField = "#"+fields[i].getName().toUpperCase()+"#";
	            	//System.out.println(nameField);
	            	text = text.replaceAll(nameField, fieldValue.toString());
	            	text.trim();
	            }
	         // eliminazione eventuali a capo
				//text = text.replaceAll("\r\n|\r|\n", " ");
	     }
	
		//text = text.replaceAll("\r\n", "<BR/>");
		//text = text.replaceAll("\\n", "<BR/>");
		return text;
		
	}
	
	/**
	 * Metodo che recupera il template del messaggio a seconda della lingua impostata
	 * @param templateName contiene nome del template
	 * @param language contiene la lingua 
	 * @return Il template della mail
	*/
	private String getTemplate(String templateName, String locale){
		String template = null;
		String fileName = null;
		ResourceBundle  bundle = null;
		String country = null;
		String language = null;
		try {
		  String[] array=locale.trim().split("_");
		  language=array[0];
		  if (array.length>1) {
		     country=array[1];
		  }   
		 
		} catch (Throwable t){
			country="IT";
			language="it";
		}
		try {
			Locale currentLocale = null;
			if (country!=null) {
				currentLocale = new Locale(language,country);
			}else { 
				currentLocale = new Locale(language);
			}
			if (bundle == null){
				try{
					fileName = propFileName;
					logger.info("Tentativo di caricamento bundle " + fileName + " ...");
					//System.out.println("FILENAME: " + fileName);
					bundle = ResourceBundle.getBundle(fileName,currentLocale);
					
					logger.info("Bundle caricato con successo!");
				}catch(MissingResourceException mre){					
					logger.error("Il bundle " + fileName + " non è presente. locale = "+ currentLocale,mre);
					throw mre;
				}
			}
        	String filename = "";
        	try {
        		filename = bundle.getString("filename");
        	} catch (MissingResourceException m){}
        	
        	
        	template = bundle.getString(templateName);
        	logger.debug("template: filename = "+filename+"  nome=" + templateName + "testo=" + template);
                        
        } catch (MissingResourceException e) {
          
          logger.error("err - don't know your language" + fileName);
          logger.error(e);
          e.printStackTrace();
        } catch (NullPointerException e) {
          logger.error("err - didn't find your key " + templateName);
          e.printStackTrace();
        }
		return template;
	}
}
