/*
 * Creato il 25-gen-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.menu;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class PathRepository {

	private String applicationCode;
	
	private String areaCode;
	
	private String serviceCode;
	
	private String functionCode;
	
	private NodeDecoder nodeDecoder;
	
	public PathRepository(NodeDecoder nodeDecoder){
		this.nodeDecoder = nodeDecoder;
	}
	
	
	public void setApplicationCode(String applicationCode){
		this.applicationCode = applicationCode;
	}
	
	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}
	
	public void setServiceCode(String serviceCode){
		this.serviceCode = serviceCode;
	}
	
	public void setFunctionCode(String functionCode){
		this.functionCode = functionCode;
	}
	
	public String getApplicationCode(){
		return applicationCode;
	}
	
	public String getAreaCode(){
		return areaCode;
	}
	
	public String getServiceCode(){
		return serviceCode;
	}

	public String getFunctionCode(){
		return functionCode;
	}
	
	public void delApplicationCode(){
		applicationCode = null;
		delAreaCode();
	}
	
	public void delAreaCode(){
		areaCode = null;
		delServiceCode();
	}
	
	public void delServiceCode(){
		serviceCode = null;
		delFunctionCode();
	}
	
	public void delFunctionCode(){
		functionCode = null;
	}
	
	public void reset(){
		delApplicationCode();
	}

	public String getPathCode(){
		return applicationCode + "-" + areaCode + "-" +serviceCode  + "-" + functionCode;
	}
	
	public String getApplicationLabel(){
		return nodeDecoder.getLabel(applicationCode);
	}
	
	public String getAreaLabel(){
		return nodeDecoder.getLabel(applicationCode+"-"+areaCode);
	}
	
	public String getServiceLabel(){
		return nodeDecoder.getLabel(applicationCode+"-"+areaCode+"-"+serviceCode);
	}

	public String getFunctionLabel(){
		return nodeDecoder.getLabel(applicationCode+"-"+areaCode+"-"+serviceCode+"-"+functionCode);
	}
	
	public String getApplicationUrl(){
		return nodeDecoder.getUrl(applicationCode+"-"+areaCode+"-"+serviceCode+"-"+applicationCode);
	}
	
	public String getAreaUrl(){
		return nodeDecoder.getUrl(applicationCode+"-"+areaCode);
	}
	
	public String getServiceUrl(){
		return nodeDecoder.getUrl(applicationCode+"-"+areaCode+"-"+serviceCode);
	}

	public String getFunctionUrl(){
		return nodeDecoder.getUrl(applicationCode+"-"+areaCode+"-"+serviceCode+"-"+functionCode);
	}
}