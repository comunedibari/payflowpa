/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.is.fo.profilo.ChangePwdVOForm;
import it.nch.fwk.fo.dto.*;
import it.nch.fwk.fo.dto.business.PojoImpl;

public class ChangePwdVOImpl extends PojoImpl implements ChangePwdVOCommon, ChangePwdVOPojo {

    private String operatore;
    private String oldPwd;
    private String newPwd1;
    private String newPwd2;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;


    public String getOperatore() {
 		return this.operatore;
 	} 

    public void setOperatore(String operatore){
 		this.operatore=operatore;
    } 

    public String getOldPwd() {
 		return this.oldPwd;
 	} 

    public void setOldPwd(String oldPwd){
 		this.oldPwd=oldPwd;
    } 

    public String getNewPwd1() {
 		return this.newPwd1;
 	} 

    public void setNewPwd1(String newPwd1){
 		this.newPwd1=newPwd1;
    } 

    public String getNewPwd2() {
 		return this.newPwd2;
 	} 

    public void setNewPwd2(String newPwd2){
 		this.newPwd2=newPwd2;
    } 




    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("operatore="+this.getOperatore());
       System.out.println("oldPwd="+this.getOldPwd());
       System.out.println("newPwd1="+this.getNewPwd1());
       System.out.println("newPwd2="+this.getNewPwd2());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    public String getOldPwdIForm() {
 		return this.oldPwd;
 	} 

    public void setOldPwdIForm(String oldPwdIForm){
 		this.oldPwd=oldPwdIForm;
    } 

    public String getOperatoreIForm() {
 		return this.operatore;
 	} 

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatore=operatoreIForm;
    } 

    public String getNewPwd2IForm() {
 		return this.newPwd2;
 	} 

    public void setNewPwd2IForm(String newPwd2IForm){
 		this.newPwd2=newPwd2IForm;
    } 

    public String getNewPwd1IForm() {
 		return this.newPwd1;
 	} 

    public void setNewPwd1IForm(String newPwd1IForm){
 		this.newPwd1=newPwd1IForm;
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
         ChangePwdVOForm _form =(ChangePwdVOForm) bf.getBean("ChangePwdVOForm");

          _form.setNativePojo(this);

         ChangePwdVOCommon _pojo = this;

         _form.setOperatoreIForm(_pojo.getOperatoreIForm());
         _form.setOldPwdIForm(_pojo.getOldPwdIForm());
         _form.setNewPwd1IForm(_pojo.getNewPwd1IForm());
         _form.setNewPwd2IForm(_pojo.getNewPwd2IForm());

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



         ChangePwdVOImpl _pojoCurrent = this;
         ChangePwdVOImpl _pojo = new ChangePwdVOImpl();

         _pojo.setOperatore(_pojoCurrent.getOperatore());
         _pojo.setOldPwd(_pojoCurrent.getOldPwd());
         _pojo.setNewPwd1(_pojoCurrent.getNewPwd1());
         _pojo.setNewPwd2(_pojoCurrent.getNewPwd2());
         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }


}
