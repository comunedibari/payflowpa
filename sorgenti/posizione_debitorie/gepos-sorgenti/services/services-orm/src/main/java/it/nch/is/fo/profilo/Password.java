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
import it.nch.is.fo.profilo.PasswordForm;
import it.nch.fwk.fo.dto.*;
import it.nch.fwk.fo.dto.business.PojoImpl;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings(value="serial")
@Entity
@Table(name="STORICOPASSWORD")
public class Password extends PojoImpl implements PasswordCommon, PasswordPojo {

	/*** Persistent Properties ***/
    private String pwdId;
    private String operator_id;
    private String password;
    private Date changeDate;

    /*** Transient Properties***/
    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    public String getPwdId() {
 		return this.pwdId;
 	} 
    public void setPwdId(String pwdId){
 		this.pwdId=pwdId;
    } 

    @Column(name="ID_OPERATORE")
    public String getOperator_id() {
 		return this.operator_id;
 	} 
    public void setOperator_id(String operator_id){
 		this.operator_id=operator_id;
    } 

    @Column(name="PASSWORD")
    public String getPassword() {
 		return this.password;
 	} 
    public void setPassword(String password){
 		this.password=password;
    } 

    @Column(name="DATACAMBIO")
    public Date getChangeDate() {
 		return this.changeDate;
 	} 
    public void setChangeDate(Date changeDate){
 		this.changeDate=changeDate;
    } 

    @Override
    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("pwdId="+this.getPwdId());
       System.out.println("operator_id="+this.getOperator_id());
       System.out.println("password="+this.getPassword());
       System.out.println("changeDate="+this.getChangeDate());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    @Override
    @Transient
    public String getChangeDateIForm() {
 		return Traslator.dateToString(this.changeDate);
 	} 

    @Override
    @Transient
    public void setChangeDateIForm(String changeDateIForm){
 		this.changeDate=Traslator.stringToDate(changeDateIForm);
    } 

    @Override
    @Transient
    public String getOperator_idIForm() {
 		return this.operator_id;
 	} 

    @Override
    @Transient
    public void setOperator_idIForm(String operator_idIForm){
 		this.operator_id=operator_idIForm;
    } 

    @Override
    @Transient
    public String getPwdIdIForm() {
 		return this.pwdId;
 	} 

    @Override
    @Transient
   public void setPwdIdIForm(String pwdIdIForm){
 		this.pwdId=pwdIdIForm;
    } 

    @Override
    @Transient
    public String getPasswordIForm() {
 		return this.password;
 	} 

    @Override
    @Transient
    public void setPasswordIForm(String passwordIForm){
 		this.password=passwordIForm;
    } 

    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/
    @Override
    @Transient
    public CommonBusinessObject copy(){

         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf=bfr.getFactory();
         PasswordForm _form =(PasswordForm) bf.getBean("PasswordForm");

          _form.setNativePojo(this);

         PasswordCommon _pojo = this;

         _form.setPwdIdIForm(_pojo.getPwdIdIForm());
         _form.setOperator_idIForm(_pojo.getOperator_idIForm());
         _form.setPasswordIForm(_pojo.getPasswordIForm());
         _form.setChangeDateIForm(_pojo.getChangeDateIForm());

         return _form;
	  }

    @Override
    @Transient
	public DTO incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	}

    /**
     *
     * Metodo Clone richiesto!!!
     *
     **/
    @Override
    public Object clone() {

         Password _pojoCurrent = this;
         Password _pojo = new Password();

         _pojo.setPwdId(_pojoCurrent.getPwdId());
         _pojo.setOperator_id(_pojoCurrent.getOperator_id());
         _pojo.setPassword(_pojoCurrent.getPassword());
         _pojo.setChangeDate(_pojoCurrent.getChangeDate());
         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pwdId == null) ? 0 : pwdId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Password other = (Password) obj;
		if (pwdId == null) {
			if (other.pwdId != null)
				return false;
		} else if (!pwdId.equals(other.pwdId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Password [pwdId=" + pwdId + ", operator_id=" + operator_id
				+ ", password=" + password + ", changeDate=" + changeDate + "]";
	}

	
}
