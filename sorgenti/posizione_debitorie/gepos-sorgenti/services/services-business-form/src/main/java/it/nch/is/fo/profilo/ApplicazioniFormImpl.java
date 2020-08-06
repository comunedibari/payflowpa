/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.ApplicazioniCommon;
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

import java.util.Collection;
import java.util.Iterator;
import java.util.*;

public class ApplicazioniFormImpl extends BaseForm implements ApplicazioniForm {

    private String applicationCodeIForm;
    private String updateDateIForm;
    private String updateUserIForm;
    private String descriptionIForm;
    private String enabledIForm;
    private Collection areeIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public ApplicazioniFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public String getApplicationCodeIForm() {
 		return this.applicationCodeIForm;
 	} 

    public void setApplicationCodeIForm(String applicationCodeIForm){
 		this.applicationCodeIForm=applicationCodeIForm;
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

    public Collection getAreeIForm() {
 		return this.areeIForm;
 	} 

    public void setAreeIForm(Set areeIForm){
 		this.areeIForm=areeIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("applicationCodeIForm="+this.getApplicationCodeIForm());
       System.out.println("updateDateIForm="+this.getUpdateDateIForm());
       System.out.println("updateUserIForm="+this.getUpdateUserIForm());
       System.out.println("descriptionIForm="+this.getDescriptionIForm());
       System.out.println("enabledIForm="+this.getEnabledIForm());
       System.out.println("areeIForm="+this.getAreeIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         ApplicazioniForm _form = this;
         ApplicazioniCommon _pojo=(ApplicazioniCommon)this.nativePojo;

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
         	_pojo=(ApplicazioniCommon) bf.getBean("Applicazioni");

         }


         _pojo.setApplicationCodeIForm(_form.getApplicationCodeIForm());
         _pojo.setUpdateDateIForm(_form.getUpdateDateIForm());
         _pojo.setUpdateUserIForm(_form.getUpdateUserIForm());
         _pojo.setDescriptionIForm(_form.getDescriptionIForm());
         _pojo.setEnabledIForm(_form.getEnabledIForm());


         /*
          *
          *  Code copy AreeIForm attribute
          *
          */ 

         /*
          *
          *  Code copy AreeIForm attribute
          *
          */ 

         if (_form.getAreeIForm()!=null){
 		  		Set destAreeIForm = new HashSet();
 		  		Iterator srcAreeIForm = _form.getAreeIForm().iterator();
 		  		while (srcAreeIForm.hasNext()){
	 				CommonBusinessObject _innerForm =  (CommonBusinessObject)srcAreeIForm.next();
	 				destAreeIForm.add(_innerForm.copy());
		  		}
		      	_pojo.setAreeIForm(destAreeIForm);
		  }


         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.applicationCodeIForm="";
	   		this.updateDateIForm="";
	   		this.updateUserIForm="";
	   		this.descriptionIForm="";
	   		this.enabledIForm="";

	   		// DATI FORM NON COMMON

	  }



}
