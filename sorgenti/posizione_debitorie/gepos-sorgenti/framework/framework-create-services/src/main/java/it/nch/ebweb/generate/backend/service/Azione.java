/*
 * Created on 19-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.ebweb.generate.backend.service;

import it.nch.ebweb.generate.core.CreatorCostanst;

/*
 * 
 * 
 * 
 * @author FelicettiA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * Descrizione firma del metodo
 * 
 */

public class Azione {
	
	int retryNum=CreatorCostanst.DEFAULT_RETRY;
		
	/**
	 * Method name
	 */
	String nome;
	
	/**
	 * Return type:
	 * 1 DTO
	 * 2 DTOCollection
	 * 3 Map
	 */
	int returnType;
	
	/**
	 * input Type:
	 * 1 DTO
	 * 2 DTOCollection
	 * 3 Map
	 */
	int paramType;
		
	/**
	 * 
	 * service visibility:
	 *  Servizio x front-end
	 *  servizio interno
	 * 
	 *  0 ESTERNO
	 *  1 ESTERNO ed INTERNO
	 *  2 INTERNO
	 */
	int internalService;
	String serviceType=CreatorCostanst.STANTARD_TYPE;
	/**
	 * Method name
	 * @return Returns the method name
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Method name
	 * @param String, method name
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Method input type
	 * @return Returns the paramType.
	 * Input parameter types allowed:<br>
	 * 1 DTO<br>
	 * 2 DTOCollection<br>
	 */
	public int getParamType() {
		return paramType;
	}
	/**
	 * Method input type
	 * @param paramType The paramType to set.
	 * Input param type allowed:<br>
	 * 1 DTO<br>
	 * 2 DTOCollection<br>
	 */
	public void setParamType(int paramType) {
		this.paramType = paramType;
	}
	
	/**
	 * Method return type
	 * @return int, the method type
	 * Output param type allowed:<br>
	 * 1 DTO<br>
	 * 2 DTOCollection<br>
	 */
	public int getReturnType() {
		return returnType;
	}
	/**
	 * Method return type
	 * @param returnType The returnType to set
	 * Output param type allowed:<br>
	 * 1 DTO<br>
	 * 2 DTOCollection<br>
	 */
	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}
	/**
	 * Method Type
	 * @return Returns the method type
	 *  0 remote<br>
	 *  1 remote or local<br>
	 *  2 local<br>
	 */
	public int getInternalService() {
		return internalService;
	}
	/** A local method can be called internally from other services 
	 * (is published in the internal interface)
	 * @param serviceType The method Type to set<br>
	 *  0 remote<br>
	 *  1 remote or local<br>
	 *  2 local<br>
	 */
	public void setInternalService(int internalService) {
		this.internalService = internalService;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public int getRetryNum() {
		return retryNum;
	}
	public void setRetryNum(int retryNum) {
		this.retryNum = retryNum;
	}
}
