

package it.nch.is.fo.login.rt;


import java.util.List;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;
import it.nch.is.fo.profilo.OperatoriForm;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class LoginRTImpl extends PojoImpl implements LoginRTCommon, LoginRTPojo {

    private String codiceFiscale;    
	private String password;
    private String criptedPassword;
    private String corporateState;
    private String redirectUrl;
    
    private String nome;
    private String cognome;
//    private String email;
    private List<OperatoriForm> deleganti;
    
    
    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;
   
    public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
    public String getPassword() {
 		return this.password;
 	} 

    public void setPassword(String password){
 		this.password=password;
    } 

    public String getCriptedPassword() {
 		return this.criptedPassword;
 	} 

    public void setCriptedPassword(String criptedPassword){
 		this.criptedPassword=criptedPassword;
    } 

    public String getCorporateState() {
 		return this.corporateState;
 	} 

    public void setCorporateState(String corporateState){
 		this.corporateState=corporateState;
    } 

    

    public String getRedirectUrl() {
 		return this.redirectUrl;
 	} 

    public void setRedirectUrl(String redirectUrl){
 		this.redirectUrl=redirectUrl;
    } 

    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("codiceFiscale="+this.getCodiceFiscale());       
       System.out.println("password="+this.getPassword());
       System.out.println("criptedPassword="+this.getCriptedPassword());
       System.out.println("corporateState="+this.getCorporateState());       
       System.out.println("redirectUrl="+this.getRedirectUrl());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    public String getCodiceFiscaleIForm() {
 		return this.codiceFiscale;
 	} 

    public void setCodiceFiscaleIForm(String codiceFiscaleIForm){
 		this.codiceFiscale=codiceFiscaleIForm;
    } 
    
    public String getCriptedPasswordIForm() {
 		return this.criptedPassword;
 	} 

    public void setCriptedPasswordIForm(String criptedPasswordIForm){
 		this.criptedPassword=criptedPasswordIForm;
    } 

    public String getCorporateStateIForm() {
 		return this.corporateState;
 	} 

    public void setCorporateStateIForm(String corporateStateIForm){
 		this.corporateState=corporateStateIForm;
    } 

    public String getPasswordIForm() {
 		return this.password;
 	} 

    public void setPasswordIForm(String passwordIForm){
 		this.password=passwordIForm;
    } 

    public String getRedirectUrlIForm() {
 		return this.redirectUrl;
 	} 

    public void setRedirectUrlIForm(String redirectUrlIForm){
 		this.redirectUrl=redirectUrlIForm;
    } 

    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/

      public CommonBusinessObject copy(){


         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf=bfr.getFactory();
         LoginRTForm _form =(LoginRTForm) bf.getBean("LoginRTForm");

          _form.setNativePojo(this);

         LoginRTCommon _pojo = this;

         _form.setCodiceFiscaleIForm(_pojo.getCodiceFiscaleIForm());         
         _form.setPasswordIForm(_pojo.getPasswordIForm());
         _form.setCriptedPasswordIForm(_pojo.getCriptedPasswordIForm());
         _form.setRedirectUrlIForm(_pojo.getRedirectUrlIForm());

         return _form;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }

    /**
     *
     * Metodo Clone richiesto!!!
     *
     **/
      public Object clone() {



         LoginRTImpl _pojoCurrent = this;
         LoginRTImpl _pojo = new LoginRTImpl();

         _pojo.setCodiceFiscale(_pojoCurrent.getCodiceFiscale());         
         _pojo.setPassword(_pojoCurrent.getPassword());
         _pojo.setCriptedPassword(_pojoCurrent.getCriptedPassword());                 
         _pojo.setRedirectUrl(_pojoCurrent.getRedirectUrl());
         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	@Override
	public List<OperatoriForm> getDeleganti() {
		return deleganti;
	}

	@Override
	public void setDeleganti(List<OperatoriForm> deleganti) {
		this.deleganti = deleganti;
	}




}
