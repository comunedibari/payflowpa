package routines;

import it.veneto.regione.schemas.x2012.pagamenti.ente.BilancioDocument;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;


public class ValidaBilancio {

public static String validaBilancio(String bilancioString) {
        boolean validXml = false;
        try {
            List<XmlError> errors = new ArrayList<XmlError>();
            XmlOptions xmlOptions = new XmlOptions();
            // Set the namespace
            xmlOptions.setLoadSubstituteNamespaces(Collections.singletonMap("", "http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/")); 
            BilancioDocument bilancioDocument = BilancioDocument.Factory.parse(bilancioString, xmlOptions);
            xmlOptions.setErrorListener(errors);
            validXml = bilancioDocument.validate(xmlOptions);
            if (!validXml)
                return null;
            else return bilancioDocument.toString();
        } catch (XmlException e) {
            return null;
        }
    }

public static String calcolaBilancioString(String codCapitolo, String codUfficio, String codAccertamento,
		Double importo) {
	
	String bilancioString = null;
	StringBuffer sb = new StringBuffer();
	sb.append("<bilancio><capitolo>");
	
	// capitolo
	sb.append("<codCapitolo>");
	sb.append(codCapitolo);
	sb.append("</codCapitolo>");

	// ufficio
	sb.append("<codUfficio>");
	sb.append(codUfficio);
	sb.append("</codUfficio>");
	
	//accertamento
	sb.append("<accertamento>");
	
	sb.append("<codAccertamento>");
	sb.append(codAccertamento);
	sb.append("</codAccertamento>");
	
	// importo
	sb.append("<importo>");
	sb.append(new BigDecimal(importo).setScale(2, RoundingMode.HALF_DOWN).toString());
	sb.append("</importo>");
	
	sb.append("</accertamento>");
	
	sb.append("</capitolo></bilancio>");
	
	bilancioString = sb.toString();
	
	boolean validXml = false;
    try {
        List<XmlError> errors = new ArrayList<XmlError>();
        XmlOptions xmlOptions = new XmlOptions();
        // Set the namespace
        xmlOptions.setLoadSubstituteNamespaces(Collections.singletonMap("", "http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/")); 
        BilancioDocument bilancioDocument = BilancioDocument.Factory.parse(bilancioString, xmlOptions);
        xmlOptions.setErrorListener(errors);
        validXml = bilancioDocument.validate(xmlOptions);
        if (validXml)
            return bilancioString;
        else return null;
    } catch (XmlException e) {
        return null;
    }
	
}

public static void main(String[] args) {
	String a = "<bilancio><capitolo><codCapitolo>CAP3</codCapitolo><codUfficio>UFF7</codUfficio><accertamento><importo>0.13</importo></accertamento><accertamento><codAccertamento>ACC4</codAccertamento><importo>1.00</importo></accertamento></capitolo></bilancio>";
	System.out.println(validaBilancio(a));
	//System.out.println(calcolaBilancioString("COD_CAP","COD_UFF","COD_ACC", new Double(5)));
}

}
