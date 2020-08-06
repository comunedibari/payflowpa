/*
 * Created on 21-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.ebweb.generate.backend.service;

import java.util.StringTokenizer;

/**
 * @author FelicettiA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Service {
	
	/**
	 * Service name
	 */
	String name;
	
	/**
	 * Service Category
	 * 0 CORE
	 * 1 CBI
	 * 2 CBI2
	 * 3 RETAIL
	 * 4 CUSTOM
	 * 5 AUTH
	 * 6 HELPDESK
	 * 
	 * 
	 **/
	int categoria;
	
	Azione[] azioni;
	boolean generateTestMethod;
	
	
	
	/**
	 * Service type:
	 * 0 Remote EJB reference
	 * 1 Local EJB reference
	 * 2 Local Service without EJB reference
	 * 
	 **/
	int typeReference;
	
	/**
	 * Persistency Type
	 * 0 DB
	 * 1 NetWork
	 * 2 Both
	 * 
	 **/
	int daoType;
	
	/**
	 * States if the Service is used by other services 
	 */
	boolean isCrossService=false;
	
	/**
	 * list of other services used by this service
	 * for example ("Actionworker","Profilo") 
	 */
	String crossServiceUsed[]=null;
	
	
	/**
	 * States if the Service is a framework internal service (for ex. Actionworker or Profile) 
	 */
	boolean isFrameworkInternalService=false;
	
	/**
	 * root package for the project (for example it.nch.ebweb)
	 */
	String rootPackage;
	
	/** Returns the list of methods (azioni) defined for the service
	 * @return Azione[]
	 * @see Azione
	 */
	public Azione[] getAzioni() {
		return azioni;
	}
	/** set the list of methods for the Service
	 * @param Azione[] The azioni to set
	 * @see Azione
	 */
	public void setAzioni(Azione[] azioni) {
		this.azioni = azioni;
	}
	/**
	 * Service Category
	 * @return int service category<br>
	 * Service Category (Used for packaging):<br>
	 * 0 CORE<br>
	 * 1 CBI<br>
	 * 2 CBI2<br>
	 * 3 RETAIL<br>
	 * 4 CUSTOM<br>
	 * 5 AUTH<br>
	 * 6 HELPDESK<br>
	 */
	public int getCategoria() {
		return categoria;
	}
	/**
	 * Service Category
	 * @param int categoria, the category to set<br>
	 * Service Category (Used for packaging):<br>
	 * 0 CORE<br>
	 * 1 CBI<br>
	 * 2 CBI2<br>
	 * 3 RETAIL<br>
	 * 4 CUSTOM<br>
	 * 5 AUTH<br>
	 * 6 HELPDESK<br>
	 */
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	/**
	 * Persistency Type
	 * @return int, the persistency Type<br>
	 * Persistency Type:<br>
	 * 0 DB<br>
	 * 1 NetWork<br>
	 * 2 Both<br> 
	 */
	public int getDaoType() {
		return daoType;
	}
	/**
	 * Persistency type
	 * @param daoType The persistency type to set<br>
	 * Persistency Type:<br>
	 * 0 DB<br>
	 * 1 NetWork<br>
	 * 2 Both<br>
	 */
	public void setDaoType(int daoType) {
		this.daoType = daoType;
	}
	/**
	 * States if a test method should be created for the service
	 * @return true --> a test method should be created for the service
	 */
	public boolean isGenerateTestMethod() {
		return generateTestMethod;
	}
	/** 
	 * true --> generate a test method for this service
	 * @param generateTestMethod 
	 */
	public void setGenerateTestMethod(boolean generateTestMethod) {
		this.generateTestMethod = generateTestMethod;
	}
	/**
	 * Service name
	 * @return Returns the Service name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Service name
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Service type
	 * @return Returns the Service Type<br>
	 * Service types:<br>
	 * 0 Remote EJB reference<br>
	 * 1 Local EJB reference<br>
	 * 2 Local Service without EJB reference<br>
	 */
	public int getTypeReference() {
		return typeReference;
	}
	/**
	 * Service type
	 * @param typeReference, The service type.<br>
	 * Service types:<br>
	 * 0 Remote EJB reference<br>
	 * 1 Local EJB reference<br>
	 * 2 Local Service without EJB reference<br>
	 */
	public void setTypeReference(int typeReference) {
		this.typeReference = typeReference;
	}
	/**
	 * States if the service can be called internally from other services 
	 * (an internal interface is created)
	 * @return Returns true if the service can be called from other services
	 */
	public boolean isCrossService() {
		return isCrossService;
	}
	/**
	 * 	Set if the service can be called internally from other services 
	 * (an internal interface is created)
	 * @param isCrossService
	 * true --> the service can be used internally by other services 
	 */
	public void setCrossService(boolean isCrossService) {
		this.isCrossService = isCrossService;
	}
	/**
	 * List of internal services used by this service
	 * @return array of string names of services used
	 */
	public String[] getCrossServiceUsed() {
		return crossServiceUsed;
	}
	/**
	 * The list of Internal Services Used from this service, for example ("Actionworker","Profilo")
	 * @param crossServiceUsed The list of Internal Services Used from this service
	 */
	public void setCrossServiceUsed(String crossServiceUsed) {
		
		if (crossServiceUsed!=null){
			
			StringTokenizer names = new StringTokenizer(crossServiceUsed,",");
			int tot = names.countTokens();
			//System.out.println("Numero di servizi cross usati="+tot);
			this.crossServiceUsed = new String[tot];
			
			int i=0;
			while(names.hasMoreTokens()){
				this.crossServiceUsed[i] = names.nextToken().trim(); // ci voleva molto a metterci il trim() ??
				System.out.println("Servizio cross ="+this.crossServiceUsed[i]);
				i++;
			}
		}else{
			this.crossServiceUsed = new String[0];
		}
		
	}
	/**
	 * states if the service is a framework internal service
	 * (cross projects, for example Actionworker or Profiling)
	 * @return true --> the service is a framework internal service
	 */
	public boolean isFrameworkInternalService() {
		return isFrameworkInternalService;
	}
	/**
	 * set if the service must be a framework internal service 
	 * (to be used only in the standard product view)
	 * @param isFrameworkInternalService
	 */
	public void setFrameworkInternalService(boolean isFrameworkInternalService) {
		this.isFrameworkInternalService = isFrameworkInternalService;
	}

	/**
	 * Returns the base package for the project
	 * @return the service root package
	 */
	public String getRootPackage() {
		return rootPackage;
	}
	/**
	 * Base package for the project, must be set for each service (for example it.nch.ebweb) 
	 * @param rootPackage
	 */
	public void setRootPackage(String rootPackage) {
		this.rootPackage = rootPackage;
	}
}
