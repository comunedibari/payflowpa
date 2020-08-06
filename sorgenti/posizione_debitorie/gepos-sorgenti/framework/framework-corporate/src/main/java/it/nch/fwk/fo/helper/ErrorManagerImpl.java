/*
 * Creato il 2-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.helper;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoInterface;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.util.Tracer;

import java.util.HashMap;
import java.util.StringTokenizer;


/**
 * @author EE10057
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class ErrorManagerImpl implements ErrorManager {

	private ErrorMapper em;
	private HashMap severityMap;
	
	public ErrorManagerImpl()
	{
		em=new ErrorMapper();
		initializeSeverityMap();
	}
	
	/**
	 * 
	 */
	private void initializeSeverityMap() {
		severityMap=new HashMap();
		severityMap.put("error",new Integer(DTOInfoInterface.SEVERITY_ERROR));
		severityMap.put("warning",new Integer(DTOInfoInterface.SEVERITY_WARNING));
		severityMap.put("info",new Integer(DTOInfoInterface.SEVERITY_INFO));
		severityMap.put("generic",new Integer(DTOInfoInterface.SEVERITY_GENERIC));
	}

	public ErrorManagerImpl(ErrorMapper em)
	{
		this.em=em;
	}
	
	
	public DTO menageException(DTO dto, RuntimeException re) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		int severity=((Integer)severityMap.get("error")).intValue();
		DTOInfo dtoInfo=new DTOInfo("framework.backend.error",re.getMessage(),severity);
		dtoInfo.setMessage(re.getMessage());
		dto.setInfoList(new DTOInfoList(dtoInfo));
		return dto;
	}
	
	public DTO menageException(DTO dto, RuntimeException re, String customMessage) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		int severity=((Integer)severityMap.get("error")).intValue();
		DTOInfo dtoInfo=new DTOInfo("framework.backend.error",re.getMessage(),severity);
		if (customMessage==null){ dtoInfo.setMessage(re.getMessage());}
			else{dtoInfo.setMessage(customMessage);}		
		dto.setInfoList(new DTOInfoList(dtoInfo));
		return dto;
	}
	
	public DTOCollection menageException(DTOCollection dtoCollection, RuntimeException re) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		dtoCollection.setInfoList(new DTOInfoList(new DTOInfo(((Integer)severityMap.get("error")).intValue())));
		return dtoCollection;
	}
	
	public DTOCollection menageException(DTOCollection dtoCollection, RuntimeException re, String customMessage) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		int severity=((Integer)severityMap.get("error")).intValue();
		DTOInfo dtoInfo=new DTOInfo("framework.backend.error",re.getMessage(),severity);
		if (customMessage==null){ dtoInfo.setMessage(re.getMessage());}
			else{dtoInfo.setMessage(customMessage);}
		dtoCollection.setInfoList(new DTOInfoList(dtoInfo));
		return dtoCollection;
	}
	
	public DTO menageException(DTO dto, Exception e,int severity,String code) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		dto.setInfoList(new DTOInfoList(new DTOInfo(code,e.getMessage(),severity)));
		return dto;
	}
	
	public DTOCollection menageException(DTOCollection dtoCollection, Exception e,int severity,String code) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		dtoCollection.setInfoList(new DTOInfoList(new DTOInfo(code,e.getMessage(),severity)));
		return dtoCollection;
	}
	
	public DTO menageException(DTO dto, Exception e,String code) {
		int severity;
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		severity = extractSeverityFromCode(code);
		
		dto.setInfoList(new DTOInfoList(new DTOInfo(code,e.getMessage(),severity)));
		return dto;
	}
	
	public DTOCollection menageException(DTOCollection dtoCollection, Exception e,String code) {
		Tracer.info(this.getClass().toString(),"manageException","inizio elaborazione",null);
		dtoCollection.setInfoList(new DTOInfoList(new DTOInfo(code,e.getMessage(),extractSeverityFromCode(code))));
		return dtoCollection;
	}
	
	/**
	 * @param code
	 * @return
	 */
	private int extractSeverityFromCode(String code) {
		int severity;
		StringTokenizer codeTokens=new StringTokenizer(code,".");
		Integer sev = (Integer)severityMap.get(codeTokens.nextElement());
		if(sev!=null && sev.intValue()>0)
			severity=sev.intValue();
		else 
			severity=((Integer)severityMap.get("generic")).intValue();
		return severity;
	}

	

	/**
	 * @author EE10057
	 * 
	 * Metodo di utilità per sollevate un'eccezione runtime generica
	 */
	public void throwRuntime(String msg) {
		throw new RuntimeException(msg);
	}

	
	public DTO menageException(DTO dto, String code) {
//		 TODO Stub di metodo generato automaticamente
		throw new UnsupportedOperationException();
	}

	
	public DTOCollection menageException(DTOCollection dtoCollection, String code) {
		// TODO Stub di metodo generato automaticamente
		throw new UnsupportedOperationException();
	}
}
