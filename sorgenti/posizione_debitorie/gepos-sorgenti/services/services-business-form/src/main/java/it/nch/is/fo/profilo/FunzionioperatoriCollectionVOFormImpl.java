/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.FunzionioperatoriCollectionVOCommon;
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

public class FunzionioperatoriCollectionVOFormImpl extends BaseForm implements FunzionioperatoriCollectionVOForm {

    private String operatoreIForm;
    private String corporateIForm;
    private Collection funzionioperatoriIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public FunzionioperatoriCollectionVOFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
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

    public Collection getFunzionioperatoriIForm() {
 		return this.funzionioperatoriIForm;
 	} 

    public void setFunzionioperatoriIForm(Collection funzionioperatoriIForm){
 		this.funzionioperatoriIForm=funzionioperatoriIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("operatoreIForm="+this.getOperatoreIForm());
       System.out.println("corporateIForm="+this.getCorporateIForm());
       System.out.println("funzionioperatoriIForm="+this.getFunzionioperatoriIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         FunzionioperatoriCollectionVOForm _form = this;
         FunzionioperatoriCollectionVOCommon _pojo=(FunzionioperatoriCollectionVOCommon)this.nativePojo;

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
         	_pojo=(FunzionioperatoriCollectionVOCommon) bf.getBean("FunzionioperatoriCollectionVO");

         }


         _pojo.setOperatoreIForm(_form.getOperatoreIForm());
         _pojo.setCorporateIForm(_form.getCorporateIForm());


         /*
          *
          *  Code copy FunzionioperatoriIForm attribute
          *
          */ 

         /*
          *
          *  Code copy FunzionioperatoriIForm attribute
          *
          */ 

         if (_form.getFunzionioperatoriIForm()!=null){
 		  		Collection destFunzionioperatoriIForm = new HashSet();
 		  		Iterator srcFunzionioperatoriIForm = _form.getFunzionioperatoriIForm().iterator();
 		  		while (srcFunzionioperatoriIForm.hasNext()){
	 				CommonBusinessObject _innerForm =  (CommonBusinessObject)srcFunzionioperatoriIForm.next();
	 				destFunzionioperatoriIForm.add(_innerForm.copy());
		  		}
		      	_pojo.setFunzionioperatoriIForm(destFunzionioperatoriIForm);
		  }


         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.operatoreIForm="";
	   		this.corporateIForm="";

	   		// DATI FORM NON COMMON

	  }



}
