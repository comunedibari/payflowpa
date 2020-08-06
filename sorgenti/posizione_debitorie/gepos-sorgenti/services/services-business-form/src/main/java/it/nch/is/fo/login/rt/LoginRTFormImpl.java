package it.nch.is.fo.login.rt;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;


public class LoginRTFormImpl extends BaseForm implements LoginRTForm {

    private String codiceFiscaleIForm;    
	private String passwordIForm;
    private String criptedPasswordIForm;
    private String redirectUrlIForm;


// TYPE SOLO PER LA FORM
    private String languageIForm;


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public LoginRTFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		try{
		bfr = bfl.useBeanFactory("it.nch.orm");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Error(e.getCause());
		}
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}

	
	public String getCodiceFiscaleIForm() {
		return codiceFiscaleIForm;
	}



	public void setCodiceFiscaleIForm(String codiceFiscaleIForm) {
		this.codiceFiscaleIForm = codiceFiscaleIForm;
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



    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("codiceFiscaleIForm="+this.getCodiceFiscaleIForm());
       System.out.println("passwordIForm="+this.getPasswordIForm());
       System.out.println("criptedPasswordIForm="+this.getCriptedPasswordIForm());       
       System.out.println("redirectUrlIForm="+this.getRedirectUrlIForm());
       System.out.println("languageIForm="+this.getLanguageIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         LoginRTForm _form = this;
         LoginRTCommon _pojo=(LoginRTCommon)this.nativePojo;

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
         	_pojo=(LoginRTCommon) bf.getBean("Login");

         }


         _pojo.setCodiceFiscaleIForm(_form.getCodiceFiscaleIForm());         
         _pojo.setPasswordIForm(_form.getPasswordIForm());
         _pojo.setCriptedPasswordIForm(_form.getCriptedPasswordIForm());
         _pojo.setRedirectUrlIForm(_form.getRedirectUrlIForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.codiceFiscaleIForm="";	   		
	   		this.passwordIForm="";
	   		this.criptedPasswordIForm="";	   		
	   		this.redirectUrlIForm="";

	   		// DATI FORM NON COMMON

	   		this.languageIForm="";
	  }



}
