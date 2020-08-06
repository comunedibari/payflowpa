/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.FunzionioperatoriCommon;
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

import it.nch.is.fo.profilo.FunzioniCommon;

public class FunzionioperatoriFormImpl extends BaseForm implements FunzionioperatoriForm {

    private String functionCodeIForm;
    private String corporateIForm;
    private String operatoreIForm;
    private FunzioniCommon funzioniobjIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public FunzionioperatoriFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
		funzioniobjIForm=(FunzioniCommon)bf.getBean("FunzioniForm");
	}



	public void setNativePojo(Object nativePojo) {
		((BaseForm)this.funzioniobjIForm).setNativePojo(((FunzionioperatoriCommon)nativePojo).getFunzioniobjIForm());
    	this.nativePojo = nativePojo;
	}


    public String getFunctionCodeIForm() {
 		return this.functionCodeIForm;
 	}

    public void setFunctionCodeIForm(String functionCodeIForm){
 		this.functionCodeIForm=functionCodeIForm;
    }

    public String getCorporateIForm() {
 		return this.corporateIForm;
 	}

    public void setCorporateIForm(String corporateIForm){
 		this.corporateIForm=corporateIForm;
    }

    public String getOperatoreIForm() {
 		return this.operatoreIForm;
 	}

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatoreIForm=operatoreIForm;
    }

    public FunzioniCommon getFunzioniobjIForm() {
 		return this.funzioniobjIForm;
 	}

    public void setFunzioniobjIForm(FunzioniCommon funzioniobjIForm){
 		this.funzioniobjIForm=funzioniobjIForm;
    }

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("functionCodeIForm="+this.getFunctionCodeIForm());
       System.out.println("corporateIForm="+this.getCorporateIForm());
       System.out.println("operatoreIForm="+this.getOperatoreIForm());
       System.out.println("funzioniobjIForm="+this.getFunzioniobjIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         FunzionioperatoriForm _form = this;
         FunzionioperatoriCommon _pojo=(FunzionioperatoriCommon)this.nativePojo;

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
         	_pojo=(FunzionioperatoriCommon) bf.getBean("Funzionioperatori");

         }


         _pojo.setFunctionCodeIForm(_form.getFunctionCodeIForm());
         _pojo.setCorporateIForm(_form.getCorporateIForm());
         _pojo.setOperatoreIForm(_form.getOperatoreIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_form.getFunzioniobjIForm()!=null)
             _pojo.setFunzioniobjIForm((FunzioniCommon)_form.getFunzioniobjIForm().copy());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.functionCodeIForm="";
	   		this.corporateIForm="";
	   		this.operatoreIForm="";

	   		// DATI FORM NON COMMON

	  }

	  public String toString(){
		  return "Funzione Operatore: "+getFunctionCodeIForm();
	  }



}
