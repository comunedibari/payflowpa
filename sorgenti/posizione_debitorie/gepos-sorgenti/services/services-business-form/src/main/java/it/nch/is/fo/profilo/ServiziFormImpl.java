/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.ServiziCommon;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.*;

public class ServiziFormImpl extends BaseForm implements ServiziForm {

    private String serviceCodeIForm;
    private String enabledIForm;
    private String descriptionIForm;
    private Collection funzioniIForm;
    private Collection servizioperatoriIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public ServiziFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public String getServiceCodeIForm() {
 		return this.serviceCodeIForm;
 	} 

    public void setServiceCodeIForm(String serviceCodeIForm){
 		this.serviceCodeIForm=serviceCodeIForm;
    } 

    public String getEnabledIForm() {
 		return this.enabledIForm;
 	} 

    public void setEnabledIForm(String enabledIForm){
 		this.enabledIForm=enabledIForm;
    } 

    public String getDescriptionIForm() {
 		return this.descriptionIForm;
 	} 

    public void setDescriptionIForm(String descriptionIForm){
 		this.descriptionIForm=descriptionIForm;
    } 

    public Collection getFunzioniIForm() {
 		return this.funzioniIForm;
 	} 

    public void setFunzioniIForm(Collection funzioniIForm){
 		this.funzioniIForm=funzioniIForm;
    } 

    public Collection getServizioperatoriIForm() {
 		return this.servizioperatoriIForm;
 	} 

    public void setServizioperatoriIForm(Collection servizioperatoriIForm){
 		this.servizioperatoriIForm=servizioperatoriIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("serviceCodeIForm="+this.getServiceCodeIForm());
       System.out.println("enabledIForm="+this.getEnabledIForm());
       System.out.println("descriptionIForm="+this.getDescriptionIForm());
       System.out.println("funzioniIForm="+this.getFunzioniIForm());
       System.out.println("servizioperatoriIForm="+this.getServizioperatoriIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         ServiziForm _form = this;
         ServiziCommon _pojo=(ServiziCommon)this.nativePojo;

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
         	_pojo=(ServiziCommon) bf.getBean("Servizi");

         }


         _pojo.setServiceCodeIForm(_form.getServiceCodeIForm());
         _pojo.setEnabledIForm(_form.getEnabledIForm());
         _pojo.setDescriptionIForm(_form.getDescriptionIForm());


         /*
          *
          *  Code copy FunzioniIForm attribute
          *
          */ 

         /*
          *
          *  Code copy FunzioniIForm attribute
          *
          */ 

         if (_form.getFunzioniIForm()!=null){
 		  		Collection destFunzioniIForm = new HashSet();
 		  		Iterator srcFunzioniIForm = _form.getFunzioniIForm().iterator();
 		  		while (srcFunzioniIForm.hasNext()){
	 				CommonBusinessObject _innerForm =  (CommonBusinessObject)srcFunzioniIForm.next();
	 				destFunzioniIForm.add(_innerForm.copy());
		  		}
		      	_pojo.setFunzioniIForm(destFunzioniIForm);
		  }


         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.serviceCodeIForm="";
	   		this.enabledIForm="";
	   		this.descriptionIForm="";

	   		// DATI FORM NON COMMON

	  }



}
