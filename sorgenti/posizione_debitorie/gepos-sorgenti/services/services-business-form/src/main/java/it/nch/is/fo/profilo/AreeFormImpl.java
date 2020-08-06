

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.AreeCommon;
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

import it.nch.is.fo.profilo.ApplicazioniCommon;
import java.util.Collection;
import java.util.Iterator;
import java.util.*;

public class AreeFormImpl extends BaseForm implements AreeForm {

    private String areaCodeIForm;
    private String updateDateIForm;
    private String updateUserIForm;
    private String descriptionIForm;
    private String enabledIForm;
    private ApplicazioniCommon applicazioneobjIForm;
    private Collection serviziIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public AreeFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
		applicazioneobjIForm=(ApplicazioniCommon)bf.getBean("ApplicazioniForm");
	}



	public void setNativePojo(Object nativePojo) {
		((BaseForm)this.applicazioneobjIForm).setNativePojo(((AreeCommon)nativePojo).getApplicazioneobjIForm());
    	this.nativePojo = nativePojo;
	}


    public String getAreaCodeIForm() {
 		return this.areaCodeIForm;
 	} 

    public void setAreaCodeIForm(String areaCodeIForm){
 		this.areaCodeIForm=areaCodeIForm;
    } 

    public String getUpdateDateIForm() {
 		return this.updateDateIForm;
 	} 

    public void setUpdateDateIForm(String updateDateIForm){
 		this.updateDateIForm=updateDateIForm;
    } 

    public String getUpdateUserIForm() {
 		return this.updateUserIForm;
 	} 

    public void setUpdateUserIForm(String updateUserIForm){
 		this.updateUserIForm=updateUserIForm;
    } 

    public String getDescriptionIForm() {
 		return this.descriptionIForm;
 	} 

    public void setDescriptionIForm(String descriptionIForm){
 		this.descriptionIForm=descriptionIForm;
    } 

    public String getEnabledIForm() {
 		return this.enabledIForm;
 	} 

    public void setEnabledIForm(String enabledIForm){
 		this.enabledIForm=enabledIForm;
    } 

    public ApplicazioniCommon getApplicazioneobjIForm() {
 		return this.applicazioneobjIForm;
 	} 

    public void setApplicazioneobjIForm(ApplicazioniCommon applicazioneobjIForm){
 		this.applicazioneobjIForm=applicazioneobjIForm;
    } 

    public Collection getServiziIForm() {
 		return this.serviziIForm;
 	} 

    public void setServiziIForm(Collection serviziIForm){
 		this.serviziIForm=serviziIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("areaCodeIForm="+this.getAreaCodeIForm());
       System.out.println("updateDateIForm="+this.getUpdateDateIForm());
       System.out.println("updateUserIForm="+this.getUpdateUserIForm());
       System.out.println("descriptionIForm="+this.getDescriptionIForm());
       System.out.println("enabledIForm="+this.getEnabledIForm());
       System.out.println("applicazioneobjIForm="+this.getApplicazioneobjIForm());
       System.out.println("serviziIForm="+this.getServiziIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         AreeForm _form = this;
         AreeCommon _pojo=(AreeCommon)this.nativePojo;

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
         	_pojo=(AreeCommon) bf.getBean("Aree");

         }


         _pojo.setAreaCodeIForm(_form.getAreaCodeIForm());
         _pojo.setUpdateDateIForm(_form.getUpdateDateIForm());
         _pojo.setUpdateUserIForm(_form.getUpdateUserIForm());
         _pojo.setDescriptionIForm(_form.getDescriptionIForm());
         _pojo.setEnabledIForm(_form.getEnabledIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_form.getApplicazioneobjIForm()!=null)
             _pojo.setApplicazioneobjIForm((ApplicazioniCommon)_form.getApplicazioneobjIForm().copy());


         /*
          *
          *  Code copy ServiziIForm attribute
          *
          */ 

         /*
          *
          *  Code copy ServiziIForm attribute
          *
          */ 

         if (_form.getServiziIForm()!=null){
 		  		Collection destServiziIForm = new HashSet();
 		  		Iterator srcServiziIForm = _form.getServiziIForm().iterator();
 		  		while (srcServiziIForm.hasNext()){
	 				CommonBusinessObject _innerForm =  (CommonBusinessObject)srcServiziIForm.next();
	 				destServiziIForm.add(_innerForm.copy());
		  		}
		      	_pojo.setServiziIForm(destServiziIForm);
		  }


         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.areaCodeIForm="";
	   		this.updateDateIForm="";
	   		this.updateUserIForm="";
	   		this.descriptionIForm="";
	   		this.enabledIForm="";

	   		// DATI FORM NON COMMON

	  }



}
