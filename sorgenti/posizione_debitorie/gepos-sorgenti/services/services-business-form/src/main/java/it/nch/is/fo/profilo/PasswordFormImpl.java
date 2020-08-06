/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.PasswordCommon;
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


public class PasswordFormImpl extends BaseForm implements PasswordForm {

    private String pwdIdIForm;
    private String operator_idIForm;
    private String passwordIForm;
    private String changeDateIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public PasswordFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public String getPwdIdIForm() {
 		return this.pwdIdIForm;
 	} 

    public void setPwdIdIForm(String pwdIdIForm){
 		this.pwdIdIForm=pwdIdIForm;
    } 

    public String getOperator_idIForm() {
 		return this.operator_idIForm;
 	} 

    public void setOperator_idIForm(String operator_idIForm){
 		this.operator_idIForm=operator_idIForm;
    } 

    public String getPasswordIForm() {
 		return this.passwordIForm;
 	} 

    public void setPasswordIForm(String passwordIForm){
 		this.passwordIForm=passwordIForm;
    } 

    public String getChangeDateIForm() {
 		return this.changeDateIForm;
 	} 

    public void setChangeDateIForm(String changeDateIForm){
 		this.changeDateIForm=changeDateIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("pwdIdIForm="+this.getPwdIdIForm());
       System.out.println("operator_idIForm="+this.getOperator_idIForm());
       System.out.println("passwordIForm="+this.getPasswordIForm());
       System.out.println("changeDateIForm="+this.getChangeDateIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         PasswordForm _form = this;
         PasswordCommon _pojo=(PasswordCommon)this.nativePojo;

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
         	_pojo=(PasswordCommon) bf.getBean("Password");

         }


         _pojo.setPwdIdIForm(_form.getPwdIdIForm());
         _pojo.setOperator_idIForm(_form.getOperator_idIForm());
         _pojo.setPasswordIForm(_form.getPasswordIForm());
         _pojo.setChangeDateIForm(_form.getChangeDateIForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.pwdIdIForm="";
	   		this.operator_idIForm="";
	   		this.passwordIForm="";
	   		this.changeDateIForm="";

	   		// DATI FORM NON COMMON

	  }



}
