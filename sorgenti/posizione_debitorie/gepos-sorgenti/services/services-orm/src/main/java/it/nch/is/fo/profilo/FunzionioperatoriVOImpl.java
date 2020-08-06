/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.is.fo.profilo.FunzionioperatoriVOForm;
import it.nch.fwk.fo.dto.*;
import it.nch.fwk.fo.dto.business.PojoImpl;

public class FunzionioperatoriVOImpl extends PojoImpl implements FunzionioperatoriVOCommon, FunzionioperatoriVOPojo {

    private String functionCode;
    private String description;
    private String operatore;
    private String corporate;
    private String serviceCode;
    private String tipoperatore;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

	public FunzionioperatoriVOImpl(String functionCode,String description,String operatore,String corporate,String serviceCode,String tipoperatore){
		super();
		this.functionCode=functionCode;
		this.description=description;
		this.operatore=operatore;
		this.corporate=corporate;
		this.serviceCode=serviceCode;
		this.tipoperatore=tipoperatore;
	}

	public FunzionioperatoriVOImpl( ){
		
	}

    public String getFunctionCode() {
 		return this.functionCode;
 	} 

    public void setFunctionCode(String functionCode){
 		this.functionCode=functionCode;
    } 

    public String getDescription() {
 		return this.description;
 	} 

    public void setDescription(String description){
 		this.description=description;
    } 

    public String getOperatore() {
 		return this.operatore;
 	} 

    public void setOperatore(String operatore){
 		this.operatore=operatore;
    } 

    public String getCorporate() {
 		return this.corporate;
 	} 

    public void setCorporate(String corporate){
 		this.corporate=corporate;
    } 

    public String getServiceCode() {
 		return this.serviceCode;
 	} 

    public void setServiceCode(String serviceCode){
 		this.serviceCode=serviceCode;
    } 

    public String getTipoperatore() {
 		return this.tipoperatore;
 	} 

    public void setTipoperatore(String tipoperatore){
 		this.tipoperatore=tipoperatore;
    } 




    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("functionCode="+this.getFunctionCode());
       System.out.println("description="+this.getDescription());
       System.out.println("operatore="+this.getOperatore());
       System.out.println("corporate="+this.getCorporate());
       System.out.println("serviceCode="+this.getServiceCode());
       System.out.println("tipoperatore="+this.getTipoperatore());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    public String getFunctionCodeIForm() {
 		return this.functionCode;
 	} 

    public void setFunctionCodeIForm(String functionCodeIForm){
 		this.functionCode=functionCodeIForm;
    } 

    public String getTipoperatoreIForm() {
 		return this.tipoperatore;
 	} 

    public void setTipoperatoreIForm(String tipoperatoreIForm){
 		this.tipoperatore=tipoperatoreIForm;
    } 

    public String getCorporateIForm() {
 		return this.corporate;
 	} 

    public void setCorporateIForm(String corporateIForm){
 		this.corporate=corporateIForm;
    } 

    public String getOperatoreIForm() {
 		return this.operatore;
 	} 

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatore=operatoreIForm;
    } 

    public String getDescriptionIForm() {
 		return this.description;
 	} 

    public void setDescriptionIForm(String descriptionIForm){
 		this.description=descriptionIForm;
    } 

    public String getServiceCodeIForm() {
 		return this.serviceCode;
 	} 

    public void setServiceCodeIForm(String serviceCodeIForm){
 		this.serviceCode=serviceCodeIForm;
    } 

    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/

      public CommonBusinessObject copy(){


         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf=bfr.getFactory();
         FunzionioperatoriVOForm _form =(FunzionioperatoriVOForm) bf.getBean("FunzionioperatoriVOForm");

          _form.setNativePojo(this);

         FunzionioperatoriVOCommon _pojo = this;

         _form.setFunctionCodeIForm(_pojo.getFunctionCodeIForm());
         _form.setDescriptionIForm(_pojo.getDescriptionIForm());
         _form.setOperatoreIForm(_pojo.getOperatoreIForm());
         _form.setCorporateIForm(_pojo.getCorporateIForm());
         _form.setServiceCodeIForm(_pojo.getServiceCodeIForm());
         _form.setTipoperatoreIForm(_pojo.getTipoperatoreIForm());

         return _form;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }



}
