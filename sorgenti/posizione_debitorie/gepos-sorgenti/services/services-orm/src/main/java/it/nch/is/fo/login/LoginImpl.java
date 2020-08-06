/**
*
* Classe generata
*
*/

package it.nch.is.fo.login;


import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class LoginImpl extends PojoImpl implements LoginCommon, LoginPojo {

    private String corporateCode;
    private String username;
    private String password;
    private String criptedPassword;
    private String corporateState;
    private String toBeActivated;
    private String redirectUrl;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;


    public String getCorporateCode() {
 		return this.corporateCode;
 	}

    public void setCorporateCode(String corporateCode){
 		this.corporateCode=corporateCode;
    }

    public String getUsername() {
 		return this.username;
 	}

    public void setUsername(String username){
 		this.username=username;
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

    public String getToBeActivated() {
 		return this.toBeActivated;
 	}

    public void setToBeActivated(String toBeActivated){
 		this.toBeActivated=toBeActivated;
    }

    public String getRedirectUrl() {
 		return this.redirectUrl;
 	}

    public void setRedirectUrl(String redirectUrl){
 		this.redirectUrl=redirectUrl;
    }




    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("corporateCode="+this.getCorporateCode());
       System.out.println("username="+this.getUsername());
       System.out.println("password="+this.getPassword());
       System.out.println("criptedPassword="+this.getCriptedPassword());
       System.out.println("corporateState="+this.getCorporateState());
       System.out.println("toBeActivated="+this.getToBeActivated());
       System.out.println("redirectUrl="+this.getRedirectUrl());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    public String getCorporateCodeIForm() {
 		return this.corporateCode;
 	}

    public void setCorporateCodeIForm(String corporateCodeIForm){
 		this.corporateCode=corporateCodeIForm;
    }

    public String getUsernameIForm() {
 		return this.username;
 	}

    public void setUsernameIForm(String usernameIForm){
 		this.username=usernameIForm;
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

    public String getToBeActivatedIForm() {
 		return this.toBeActivated;
 	}

    public void setToBeActivatedIForm(String toBeActivatedIForm){
 		this.toBeActivated=toBeActivatedIForm;
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
         LoginForm _form =(LoginForm) bf.getBean("LoginForm");

          _form.setNativePojo(this);

         LoginCommon _pojo = this;

         _form.setCorporateCodeIForm(_pojo.getCorporateCodeIForm());
         _form.setUsernameIForm(_pojo.getUsernameIForm());
         _form.setPasswordIForm(_pojo.getPasswordIForm());
         _form.setCriptedPasswordIForm(_pojo.getCriptedPasswordIForm());
         _form.setCorporateStateIForm(_pojo.getCorporateStateIForm());
         _form.setToBeActivatedIForm(_pojo.getToBeActivatedIForm());
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



         LoginImpl _pojoCurrent = this;
         LoginImpl _pojo = new LoginImpl();

         _pojo.setCorporateCode(_pojoCurrent.getCorporateCode());
         _pojo.setUsername(_pojoCurrent.getUsername());
         _pojo.setPassword(_pojoCurrent.getPassword());
         _pojo.setCriptedPassword(_pojoCurrent.getCriptedPassword());
         _pojo.setCorporateState(_pojoCurrent.getCorporateState());
         _pojo.setToBeActivated(_pojoCurrent.getToBeActivated());
         _pojo.setRedirectUrl(_pojoCurrent.getRedirectUrl());
         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }

	  public String toString() {
		  StringBuffer descrizione = new StringBuffer("****LoginImpl****\n");
		  descrizione.append("corporateCode: ").append(getCorporateCode()).append("\n");
		  descrizione.append("username: ").append(getUsername()).append("\n");
		  descrizione.append("password: ").append(getPassword()).append("\n");
		  descrizione.append("criptedPassword: ").append(getCriptedPassword()).append("\n");
		  descrizione.append("corporateState: ").append(getCorporateState()).append("\n");
		  descrizione.append("toBeActivated: ").append(getToBeActivated()).append("\n");
		  descrizione.append("redirectUrl: ").append(getRedirectUrl()).append("\n");
		  descrizione.append("***************\n");
		  return descrizione.toString();
	   }


}
