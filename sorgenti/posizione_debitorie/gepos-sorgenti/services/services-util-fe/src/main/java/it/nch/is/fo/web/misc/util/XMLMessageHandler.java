package it.nch.is.fo.web.misc.util;

import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.util.cbiengine.CBIEngineError;
import it.nch.is.fo.util.cbiengine.MessageParser;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.util.ArrayList;
import java.util.Properties;

public class XMLMessageHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String decodeXMLMessage(String originalErrorDescription) {
		String decodedErrorDescription = null;

		MessageParser mp = new MessageParser();
		ArrayList errors = mp.parseErrors(originalErrorDescription);
		CBIEngineError REBAAuthError = null;
		if (errors != null && errors.size() == 1) {
			CBIEngineError first = (CBIEngineError)errors.get(0);
			if (first.id.equalsIgnoreCase("ERR_SPEDIZIONE_FLUSSO_AUTH")) {
				REBAAuthError = first;
			}
		}
		
		if (REBAAuthError != null ) {
			String esitoMir = REBAAuthError.getValueByKey("MESSAGE");
			Tracer.debug("XMLMessageHandler", "decodeXMLMessage", "Handling REBA exception (esitiMir.properties): " + esitoMir);
			decodedErrorDescription = decodeHostErrorDescription(esitoMir);
		} else {
			Tracer.debug("XMLMessageHandler", "decodeXMLMessage", "Handling NON-REBA exception (cbiengine.properties)");
			Properties resource = ConfigurationPropertyLoader.getProperties("cbiengine.properties");
			decodedErrorDescription = mp.format(errors, resource, true);
		}
		
		if (REBAAuthError == null && (decodedErrorDescription==null||decodedErrorDescription.equals(""))) {
			Tracer.debug("XMLMessageHandler", "decodeXMLMessage", "Handling non-XML exception (esitiMir.properties)");
			decodedErrorDescription = decodeHostErrorDescription(originalErrorDescription);
		}

		Tracer.debug("XMLMessageHandler", "decodeXMLMessage", "Decoded error description = " + decodedErrorDescription);
		return decodedErrorDescription;
	}

	private static String decodeHostErrorDescription(String errorCode) {
		String out = errorCode; // Setting decoded equal to input in case of no match on property file
		if (errorCode != null && !errorCode.equals("")) {
			ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("messages/services/esitiMir.properties");
			String errorDes = cpl.getProperty(errorCode);
			if (errorDes != null) {
				out = "Esito: " + errorCode + " - " + errorDes;
			}
			Tracer.debug("XMLMessageHandler", "decodeHostErrorDescription", errorCode + " = " + errorDes);
		} else {
			Tracer.debug("XMLMessageHandler", "decodeHostErrorDescription", "Input error code was null");
		}
		return out;
	}
}
