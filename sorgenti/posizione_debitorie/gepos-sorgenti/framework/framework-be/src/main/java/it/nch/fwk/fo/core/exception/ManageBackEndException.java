package it.nch.fwk.fo.core.exception;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ManageBackEndException extends AbstractManageBackEndException {

	
	public String retrieveInfoByCode(String code){
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");

		ApplicationContext context = new ClassPathXmlApplicationContext("it/nch/server/message/serverMessage.xml");
		ResourceBundleMessageSource resourceBundleMessageSource = 
		                   (ResourceBundleMessageSource) context.getBean("message");
		//Introdurre il recupero del locale dell'utente
		String message = resourceBundleMessageSource.getMessage(code,null,new Locale ("it", "IT","tuscany"));
		return message;
	}

	
}
