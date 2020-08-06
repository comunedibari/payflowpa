/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.ChangePwdVOCommon;
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


public class ChangePwdVOFormImpl extends BaseForm implements ChangePwdVOForm {

    private String operatoreIForm;
    private String oldPwdIForm;
    private String newPwd1IForm;
    private String newPwd2IForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public ChangePwdVOFormImpl(){

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

    public String getOldPwdIForm() {
 		return this.oldPwdIForm;
 	} 

    public void setOldPwdIForm(String oldPwdIForm){
 		this.oldPwdIForm=oldPwdIForm;
    } 

    public String getNewPwd1IForm() {
 		return this.newPwd1IForm;
 	} 

    public void setNewPwd1IForm(String newPwd1IForm){
 		this.newPwd1IForm=newPwd1IForm;
    } 

    public String getNewPwd2IForm() {
 		return this.newPwd2IForm;
 	} 

    public void setNewPwd2IForm(String newPwd2IForm){
 		this.newPwd2IForm=newPwd2IForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("operatoreIForm="+this.getOperatoreIForm());
       System.out.println("oldPwdIForm="+this.getOldPwdIForm());
       System.out.println("newPwd1IForm="+this.getNewPwd1IForm());
       System.out.println("newPwd2IForm="+this.getNewPwd2IForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         ChangePwdVOForm _form = this;
         ChangePwdVOCommon _pojo=(ChangePwdVOCommon)this.nativePojo;

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
         	_pojo=(ChangePwdVOCommon) bf.getBean("ChangePwdVO");

         }


         _pojo.setOperatoreIForm(_form.getOperatoreIForm());
         _pojo.setOldPwdIForm(_form.getOldPwdIForm());
         _pojo.setNewPwd1IForm(_form.getNewPwd1IForm());
         _pojo.setNewPwd2IForm(_form.getNewPwd2IForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.operatoreIForm="";
	   		this.oldPwdIForm="";
	   		this.newPwd1IForm="";
	   		this.newPwd2IForm="";

	   		// DATI FORM NON COMMON

	  }



}
