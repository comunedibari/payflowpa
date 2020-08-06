package it.tasgroup.idp.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.esiti.ErroreEsitoPendenza;

public class ErrorDecoder {

    private ResourceBundle  i18nbundle;
    private static String propFileName = "error";
    
    private final Log logger = LogFactory.getLog(this.getClass());
      
    /**
     * 
     * @param qe
     * @return
     */
    public ErroreEsitoPendenza decode(ErroriEsitiPendenza qe) { 

    	ErroreEsitoPendenza errorEP = new ErroreEsitoPendenza();
    	String fileName = null;
    	String codDecoded = null;
    	String dex = "";
        try {
        	
//        	Locale localDef = Locale.getDefault();
//        	logger.debug( " LOCALE DEFAULT :" + "country:" + localDef.getCountry() + "\n"
//					+ "disp country:" + localDef.getDisplayCountry() + "\n"
//					+ "disp language:" + localDef.getDisplayLanguage() + "\n"
//					+ "disp name:" + localDef.getDisplayName() + "\n"
//					+ "variant:" + localDef.getDisplayVariant() + "\n"
//					+ "iso3 country:" + localDef.getISO3Country() + "\n"
//					+ "iso3 lang:" + localDef.getISO3Language() + "\n"
//					+ "lang:" + localDef.getLanguage() + "\n"
//					+ "variant:" + localDef.getVariant() + "\n"
//					+ "iso country:" + localDef.getISOCountries() + "\n"
//					+ "iso lang:" + localDef.getISOLanguages() + "\n"        								
//					);
        	
        	fileName = propFileName;
        	logger.info("fileName = " + fileName);
        	i18nbundle = ResourceBundle.getBundle(fileName);

        	if(logger.isDebugEnabled()) {
            	Locale local = i18nbundle.getLocale();
        		logger.debug( " LOCALE :" + "country:" + local.getCountry() + "\n"
        								+ "disp country:" + local.getDisplayCountry() + "\n"
        								+ "disp language:" + local.getDisplayLanguage() + "\n"
        								+ "disp name:" + local.getDisplayName() + "\n"
        								+ "variant:" + local.getDisplayVariant() + "\n"
        								+ "iso3 country:" + local.getISO3Country() + "\n"
        								+ "iso3 lang:" + local.getISO3Language() + "\n"
        								+ "lang:" + local.getLanguage() + "\n"
        								+ "variant:" + local.getVariant() + "\n"
        								+ "iso country:" + local.getISOCountries() + "\n"
        								+ "iso lang:" + local.getISOLanguages() + "\n"        								
        								);
        	}
        	
        	codDecoded = i18nbundle.getString(qe.getCodice().trim());
            dex = i18nbundle.getString(codDecoded);
                        
        } catch (MissingResourceException e) {
          logger.info("err - don't know your language" + fileName);
          logger.debug(e);
          e.printStackTrace();
        } catch (NullPointerException e) {
          logger.info("err - didn't find your key " + qe.getCodice());
        }
//        String codDecoded = i18nbundle.getString(qe.getCodice().trim());
//        String dex = i18nbundle.getString(codDecoded);
          
        errorEP.setCodice(codDecoded);
        errorEP.setDescrizione(dex);
    	
    	return errorEP;
    	
    }
       
	
}
