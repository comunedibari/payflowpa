/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.FunzionioperatoriVOCommon;
import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.*;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.util.Tracer;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;


public class FunzionioperatoriVOFormImpl extends BaseForm implements FunzionioperatoriVOForm {

    private String functionCodeIForm;
    private String descriptionIForm;
    private String operatoreIForm;
    private String corporateIForm;
    private String serviceCodeIForm;
    private String tipoperatoreIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public FunzionioperatoriVOFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public String getFunctionCodeIForm() {
 		return this.functionCodeIForm;
 	} 

    public void setFunctionCodeIForm(String functionCodeIForm){
 		this.functionCodeIForm=functionCodeIForm;
    } 

    public String getDescriptionIForm() {
 		return this.descriptionIForm;
 	} 

    public void setDescriptionIForm(String descriptionIForm){
 		this.descriptionIForm=descriptionIForm;
    } 

    public String getOperatoreIForm() {
 		return this.operatoreIForm;
 	} 

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatoreIForm=operatoreIForm;
    } 

    public String getCorporateIForm() {
 		return this.corporateIForm;
 	} 

    public void setCorporateIForm(String corporateIForm){
 		this.corporateIForm=corporateIForm;
    } 

    public String getServiceCodeIForm() {
 		return this.serviceCodeIForm;
 	} 

    public void setServiceCodeIForm(String serviceCodeIForm){
 		this.serviceCodeIForm=serviceCodeIForm;
    } 

    public String getTipoperatoreIForm() {
 		return this.tipoperatoreIForm;
 	} 

    public void setTipoperatoreIForm(String tipoperatoreIForm){
 		this.tipoperatoreIForm=tipoperatoreIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("functionCodeIForm="+this.getFunctionCodeIForm());
       System.out.println("descriptionIForm="+this.getDescriptionIForm());
       System.out.println("operatoreIForm="+this.getOperatoreIForm());
       System.out.println("corporateIForm="+this.getCorporateIForm());
       System.out.println("serviceCodeIForm="+this.getServiceCodeIForm());
       System.out.println("tipoperatoreIForm="+this.getTipoperatoreIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         FunzionioperatoriVOForm _form = this;
         FunzionioperatoriVOCommon _pojo=(FunzionioperatoriVOCommon)this.nativePojo;

         if (_pojo == null){ 

         	if (Tracer.isDebugEnabled(this.getClass().getName())){ 
				Tracer.debug(this.getClass().getName(),"","",null);
				Tracer.debug(this.getClass().getName(),"copy()","---------------------------------------------------------------------",null);
				Tracer.debug(this.getClass().getName(),"copy()","Attenzione nativeObject dentro FORM vuoto provvedo ad istanziare POJO",null);
				Tracer.debug(this.getClass().getName(),"copy()","---------------------------------------------------------------------",null);
				Tracer.debug(this.getClass().getName(),"","",null);
         	}

			bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
			bfr = bfl.useBeanFactory("it.nch.orm");
			bf=bfr.getFactory();
         	_pojo=(FunzionioperatoriVOCommon) bf.getBean("FunzionioperatoriVO");

         }


         _pojo.setFunctionCodeIForm(_form.getFunctionCodeIForm());
         _pojo.setDescriptionIForm(_form.getDescriptionIForm());
         _pojo.setOperatoreIForm(_form.getOperatoreIForm());
         _pojo.setCorporateIForm(_form.getCorporateIForm());
         _pojo.setServiceCodeIForm(_form.getServiceCodeIForm());
         _pojo.setTipoperatoreIForm(_form.getTipoperatoreIForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.functionCodeIForm="";
	   		this.descriptionIForm="";
	   		this.operatoreIForm="";
	   		this.corporateIForm="";
	   		this.serviceCodeIForm="";
	   		this.tipoperatoreIForm="";

	   		// DATI FORM NON COMMON

	  }



}
