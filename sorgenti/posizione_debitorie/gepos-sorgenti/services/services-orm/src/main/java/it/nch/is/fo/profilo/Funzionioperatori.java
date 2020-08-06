/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
@Entity
@Table(name="FUNZIONIOPERATORI")
@NamedQueries({
	@NamedQuery(name="getFunzioniOperatori", 
			query="select fop from Funzionioperatori fop where fop.funOpId.operatore = :operatore and fop.funOpId.corporate = :corporate")
})
public class Funzionioperatori extends PojoImpl implements FunzionioperatoriCommon, FunzionioperatoriPojo {

	private static final long serialVersionUID = 1L;

	/*** PK Reference ***/
	private FunzionioperatoriId funOpId;
    
	/*** Persistent Reference ***/
    private Funzioni funzioniobj;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

    public Funzionioperatori(){
    	this.funOpId = new FunzionioperatoriId();
    }
    
    public Funzionioperatori(FunzionioperatoriId funOpId){
    	this.funOpId = funOpId;
    }

    @Id
	public FunzionioperatoriId getFunOpId() {
		return funOpId;
	}
	public void setFunOpId(FunzionioperatoriId funOpId) {
		this.funOpId = funOpId;
	}
	
	@Transient
    public String getFunctionCode() {
 		return this.funOpId.getFunctionCode();
 	} 
	
	@Transient
    public void setFunctionCode(String functionCode){
 		this.funOpId.setFunctionCode(functionCode);
    } 
    
    @Transient
    public String getCorporate() {
 		return this.funOpId.getCorporate();
 	}
    
	@Transient
    public void setCorporate(String corporate){
 		this.funOpId.setCorporate(corporate);
    } 

	@Transient
    public String getOperatore() {
 		return this.funOpId.getOperatore();
 	} 

	@Transient
    public void setOperatore(String operatore){
 		this.funOpId.setOperatore(operatore);
    } 
    
    @ManyToOne(targetEntity=Funzioni.class, fetch=FetchType.LAZY)
    @JoinColumn(name="CODFUNZIONE",insertable=false,updatable=false)
    public FunzioniCommon getFunzioniobj() {
 		return (FunzioniCommon)this.funzioniobj;
 	} 
    public void setFunzioniobj(FunzioniCommon funzioniobj){
 		this.funzioniobj=(Funzioni)funzioniobj;
    } 

    @Override
    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("functionCode="+this.getFunctionCode());
       System.out.println("corporate="+this.getCorporate());
       System.out.println("operatore="+this.getOperatore());
       System.out.println("funzioniobj="+this.getFunzioniobj());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    @Override
    @Transient
    public String getFunctionCodeIForm() {
 		return this.funOpId.getFunctionCode();
 	} 
    @Override
    public void setFunctionCodeIForm(String functionCodeIForm){
 		this.funOpId.setFunctionCode(functionCodeIForm);
    } 

    @Override
    @Transient
    public String getCorporateIForm() {
 		return this.funOpId.getCorporate();
 	} 
    @Override
    public void setCorporateIForm(String corporateIForm){
 		this.funOpId.setCorporate(corporateIForm);;
    } 

    @Override
    @Transient
    public String getOperatoreIForm() {
 		return this.getOperatore();
 	} 
    @Override
    public void setOperatoreIForm(String operatoreIForm){
 		this.funOpId.setOperatore(operatoreIForm);
    } 

    @Override
    @Transient
    public FunzioniCommon getFunzioniobjIForm() {
 		return this.funzioniobj;
 	} 
    @Override
    public void setFunzioniobjIForm(FunzioniCommon funzioniobjIForm){
 		this.funzioniobj=(Funzioni)funzioniobjIForm;
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
         FunzionioperatoriForm _form =(FunzionioperatoriForm) bf.getBean("FunzionioperatoriForm");

          _form.setNativePojo(this);

         FunzionioperatoriCommon _pojo = this;

         _form.setFunctionCodeIForm(_pojo.getFunctionCodeIForm());
         _form.setCorporateIForm(_pojo.getCorporateIForm());
         _form.setOperatoreIForm(_pojo.getOperatoreIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_pojo.getFunzioniobjIForm()!=null && !getFlagDoNotCopy()) // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
        	 _form.setFunzioniobjIForm((FunzioniCommon)_pojo.getFunzioniobjIForm().copy());

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

         Funzionioperatori _pojoCurrent = this;
         Funzionioperatori _pojo = new Funzionioperatori();

         _pojo.setFunctionCode(_pojoCurrent.getFunctionCode());
         _pojo.setCorporate(_pojoCurrent.getCorporate());
         _pojo.setOperatore(_pojoCurrent.getOperatore());
         _pojo.setFunzioniobj(_pojoCurrent.getFunzioniobj());
         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funOpId == null) ? 0 : funOpId.hashCode());
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
		Funzionioperatori other = (Funzionioperatori) obj;
		if (funOpId == null) {
			if (other.funOpId != null)
				return false;
		} else if (!funOpId.equals(other.funOpId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funzionioperatori [funOpId=" + funOpId + ", funzioniobj="
				+ funzioniobj + "]";
	}


}
