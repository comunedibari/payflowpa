/**
*
* Classe generata
*
*/

package it.nch.is.fo.login;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;

import java.util.Calendar;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;


public class LoginFormImpl extends BaseForm implements LoginForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5197401778762650217L;
	private String corporateCodeIForm;
    private String usernameIForm;
    private String passwordIForm;
    private String criptedPasswordIForm;
    private String corporateStateIForm;
    private String toBeActivatedIForm;
    private String redirectUrlIForm;


// TYPE SOLO PER LA FORM
    private String languageIForm;

// operativita' ente
    private String annoRiferimento;


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public LoginFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public String getCorporateCodeIForm() {
 		return this.corporateCodeIForm;
 	} 

    public void setCorporateCodeIForm(String corporateCodeIForm){
 		this.corporateCodeIForm=corporateCodeIForm;
    } 

    public String getUsernameIForm() {
 		return this.usernameIForm;
 	} 

    public void setUsernameIForm(String usernameIForm){
 		this.usernameIForm=usernameIForm;
    } 

    public String getPasswordIForm() {
 		return this.passwordIForm;
 	} 

    public void setPasswordIForm(String passwordIForm){
 		this.passwordIForm=passwordIForm;
    } 

    public String getCriptedPasswordIForm() {
 		return this.criptedPasswordIForm;
 	} 

    public void setCriptedPasswordIForm(String criptedPasswordIForm){
 		this.criptedPasswordIForm=criptedPasswordIForm;
    } 

    public String getCorporateStateIForm() {
 		return this.corporateStateIForm;
 	} 

    public void setCorporateStateIForm(String corporateStateIForm){
 		this.corporateStateIForm=corporateStateIForm;
    } 

    public String getToBeActivatedIForm() {
 		return this.toBeActivatedIForm;
 	} 

    public void setToBeActivatedIForm(String toBeActivatedIForm){
 		this.toBeActivatedIForm=toBeActivatedIForm;
    } 

    public String getRedirectUrlIForm() {
 		return this.redirectUrlIForm;
 	} 

    public void setRedirectUrlIForm(String redirectUrlIForm){
 		this.redirectUrlIForm=redirectUrlIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */
    public String getLanguageIForm() {
 		return this.languageIForm;
 	} 

    public void setLanguageIForm(String languageIForm){
 		this.languageIForm=languageIForm;
    } 



    public String getAnnoRiferimento() {
		return annoRiferimento;
	}



	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}



	public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("corporateCodeIForm="+this.getCorporateCodeIForm());
       System.out.println("usernameIForm="+this.getUsernameIForm());
       System.out.println("passwordIForm="+this.getPasswordIForm());
       System.out.println("criptedPasswordIForm="+this.getCriptedPasswordIForm());
       System.out.println("corporateStateIForm="+this.getCorporateStateIForm());
       System.out.println("toBeActivatedIForm="+this.getToBeActivatedIForm());
       System.out.println("redirectUrlIForm="+this.getRedirectUrlIForm());
       System.out.println("languageIForm="+this.getLanguageIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         LoginForm _form = this;
         LoginCommon _pojo=(LoginCommon)this.nativePojo;

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
         	_pojo=(LoginCommon) bf.getBean("Login");

         }


         _pojo.setCorporateCodeIForm(_form.getCorporateCodeIForm());
         _pojo.setUsernameIForm(_form.getUsernameIForm());
         _pojo.setPasswordIForm(_form.getPasswordIForm());
         _pojo.setCriptedPasswordIForm(_form.getCriptedPasswordIForm());
         _pojo.setCorporateStateIForm(_form.getCorporateStateIForm());
         _pojo.setToBeActivatedIForm(_form.getToBeActivatedIForm());
         _pojo.setRedirectUrlIForm(_form.getRedirectUrlIForm());
         
         _pojo.setRedirectUrlIForm(_form.getRedirectUrlIForm());

         return _pojo;
	  }

	  public DTO<?, ?, ?> incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.corporateCodeIForm="";
	   		this.usernameIForm="";
	   		this.passwordIForm="";
	   		this.criptedPasswordIForm="";
	   		this.corporateStateIForm="";
	   		this.toBeActivatedIForm="";
	   		this.redirectUrlIForm="";
	   		
	   		this.annoRiferimento = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

	   		// DATI FORM NON COMMON

	   		this.languageIForm="";
	  }



}
