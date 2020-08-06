package it.nch.fwk.fo.exceptions;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.dto.DTOInfoSeverity;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

public class ManageFrontEndException {
	
	
	static public DTO getBusinessDTOByError(String code,int severity) {
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		DTO dtoResult= new DTOImpl();
		DTOInfo dtoInfo=new DTOInfo(code,retrieveInfoByCode(code),severity);
		dtoResult.setInfoList(new DTOInfoList(dtoInfo));
		return dtoResult;
	}

	static public DTOInfo getBusinessDTOInfoByError(String code,int severity) {
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		DTOInfo dtoInfo=new DTOInfo(code,retrieveInfoByCode(code),severity);
		return dtoInfo;
	}

	static public DTOCollection getBusinessDTOCollectionByError(String code,int severity) {
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");
		if( !DTOInfoSeverity.isValidSeverity(severity))
			throw new IllegalArgumentException("Severity param is not correct");

		DTOCollection dtoResult= new DTOCollectionImpl();
		DTOInfo dtoInfo=new DTOInfo(code,retrieveInfoByCode(code),severity);
		dtoResult.setInfoList(new DTOInfoList(dtoInfo));
		return dtoResult;
	}

	static public String retrieveInfoByCode(String code){
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("Code param is mandatory");

		ApplicationContext context = new ClassPathXmlApplicationContext("it/nch/client/message/clientMessage.xml");
		ResourceBundleMessageSource resourceBundleMessageSource = 
		                   (ResourceBundleMessageSource) context.getBean("message");
		//Introdurre il recupero del locale dell'utente
		String message = resourceBundleMessageSource.getMessage(code,null,new Locale ("it", "IT","tuscany"));
		return message;
	}
	
}
