/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

@Entity
@Table(name="FUNZIONI")
public class Funzioni extends PojoImpl implements FunzioniCommon, FunzioniPojo {

	/*** Persistent Properties ***/
    private String functionCode;
    private String description;
    private String enabled;
    private String accessed;
    private Integer priority;
    private String operatorType;
    
    /*** Persistent References ***/
    private Servizi serviziobj;
    
    /*** Persistent Collections ***/
    private Set<Funzionioperatori> funzionioperatori;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;


    @Id
    @Column(name="CODFUNZIONE")
    public String getFunctionCode() {
 		return this.functionCode;
 	} 

    public void setFunctionCode(String functionCode){
 		this.functionCode=functionCode;
    } 

    @Column(name="DESCRIZIONE")
    public String getDescription() {
 		return this.description;
 	} 

    public void setDescription(String description){
 		this.description=description;
    } 

    @Column(name="ABL_GLOBAL")
    public String getEnabled() {
 		return this.enabled;
 	} 

    @Override
    @Transient
    public void setEnabled(String enabled){
 		this.enabled=enabled;
    } 
    @Override
    @Transient
    public String getAccessed() {
 		return this.accessed;
 	} 
    @Transient
    public void setAccessed(String accessed){
 		this.accessed=accessed;
    } 

    @Column(name="PRIORITY")
    public Integer getPriority() {
 		return this.priority;
 	} 

    public void setPriority(Integer priority){
 		this.priority=priority;
    } 
    @Column(name="TIPOPERATORE")
    public String getOperatorType() {
 		return this.operatorType;
 	} 

    public void setOperatorType(String operatorType){
 		this.operatorType=operatorType;
    } 

    @ManyToOne(targetEntity=Servizi.class)
    @JoinColumn(name="CODSERVIZIO")
    public ServiziCommon getServiziobj() {
 		return (ServiziCommon)this.serviziobj;
 	} 
    public void setServiziobj(ServiziCommon serviziobj){
 		this.serviziobj=(Servizi)serviziobj;
    } 

    @OneToMany(targetEntity=Funzionioperatori.class,mappedBy="funzioniobj")
    public Set<Funzionioperatori> getFunzionioperatori() {
 		return this.funzionioperatori;
 	} 
    public void setFunzionioperatori(Set funzionioperatori){
 		this.funzionioperatori= funzionioperatori;
    } 



    @Override
    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("functionCode="+this.getFunctionCode());
       System.out.println("description="+this.getDescription());
       System.out.println("enabled="+this.getEnabled());
       System.out.println("accessed="+this.getAccessed());
       System.out.println("priority="+this.getPriority());
       System.out.println("operatorType="+this.getOperatorType());
       System.out.println("serviziobj="+this.getServiziobj());
       System.out.println("funzionioperatori="+this.getFunzionioperatori());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    @Override
    @Transient
    public String getAccessedIForm() {
 		return this.accessed;
 	} 
    @Override
    public void setAccessedIForm(String accessedIForm){
 		this.accessed=accessedIForm;
    } 

    @Override
    @Transient
    public String getFunctionCodeIForm() {
 		return this.functionCode;
 	} 
    @Override
    public void setFunctionCodeIForm(String functionCodeIForm){
 		this.functionCode=functionCodeIForm;
    } 
    
    
    @Override
    @Transient
    public String getOperatorTypeIForm() {
 		return this.operatorType;
 	} 
    @Override
    public void setOperatorTypeIForm(String operatorTypeIForm){
 		this.operatorType=operatorTypeIForm;
    } 

    @Override
    @Transient
    public String getEnabledIForm() {
 		return this.enabled;
 	} 
    @Override
    public void setEnabledIForm(String enabledIForm){
 		this.enabled=enabledIForm;
    } 

    @Override
    @Transient
    public ServiziCommon getServiziobjIForm() {
 		return this.serviziobj;
 	} 
    @Override
    public void setServiziobjIForm(ServiziCommon serviziobjIForm){
 		this.serviziobj=(Servizi)serviziobjIForm;
    } 

    @Override
    @Transient
    public Collection getFunzionioperatoriIForm() {
 		return (this.funzionioperatori);
 	} 
    @Override
    public void setFunzionioperatoriIForm(Collection funzionioperatoriIForm){
 		this.funzionioperatori= new LinkedHashSet<Funzionioperatori>(funzionioperatoriIForm);
    } 

    @Override
    @Transient
    public String getPriorityIForm() {
 		return Traslator.integerToString(this.priority);
 	} 
    @Override
    public void setPriorityIForm(String priorityIForm){
 		this.priority=Traslator.stringToInteger(priorityIForm);
    } 

    @Override
    @Transient
    public String getDescriptionIForm() {
 		return this.description;
 	} 
    @Override
    public void setDescriptionIForm(String descriptionIForm){
 		this.description=descriptionIForm;
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
         FunzioniForm _form =(FunzioniForm) bf.getBean("FunzioniForm");

          _form.setNativePojo(this);

         FunzioniCommon _pojo = this;

         _form.setFunctionCodeIForm(_pojo.getFunctionCodeIForm());
         _form.setDescriptionIForm(_pojo.getDescriptionIForm());
         _form.setEnabledIForm(_pojo.getEnabledIForm());
         _form.setAccessedIForm(_pojo.getAccessedIForm());
         _form.setPriorityIForm(_pojo.getPriorityIForm());
         _form.setOperatorTypeIForm(_pojo.getOperatorTypeIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_pojo.getServiziobjIForm()!=null && !getFlagDoNotCopy()) // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
        	 _form.setServiziobjIForm((ServiziCommon)_pojo.getServiziobjIForm().copy());

         /*
          *
          *  Code copy FunzionioperatoriIForm attribute
          *
          */ 
 		  Collection destFunzionioperatoriIForm = new Vector();
          if (_pojo.getFunzionioperatoriIForm()!=null){
 		  	  Iterator srcFunzionioperatoriIForm = _pojo.getFunzionioperatoriIForm().iterator();
 		  	  while (srcFunzionioperatoriIForm.hasNext()){
	 			  CommonBusinessObject _innerPojo =  (CommonBusinessObject)srcFunzionioperatoriIForm.next();
	 			  PojoImpl source = (PojoImpl)_innerPojo;
	 			  source.setFlagDoNotCopy(true); // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
	 			  destFunzionioperatoriIForm.add(_innerPojo.copy());
		  	   }
		      _form.setFunzionioperatoriIForm(destFunzionioperatoriIForm);
		  }

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

         Funzioni _pojoCurrent = this;
         Funzioni _pojo = new Funzioni();

         _pojo.setFunctionCode(_pojoCurrent.getFunctionCode());
         _pojo.setDescription(_pojoCurrent.getDescription());
         _pojo.setEnabled(_pojoCurrent.getEnabled());
         _pojo.setAccessed(_pojoCurrent.getAccessed());
         _pojo.setPriority(_pojoCurrent.getPriority());
         _pojo.setOperatorType(_pojoCurrent.getOperatorType());
         _pojo.setServiziobj(_pojoCurrent.getServiziobj());
         /**
          *
          *Trovata Collection [funzionioperatori] NON applico clone annidato!
          *
          */

         /**
          *
          *Trovata Collection [rapportiEnabled] NON applico clone annidato!
          *
          */

         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((functionCode == null) ? 0 : functionCode.hashCode());
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
		Funzioni other = (Funzioni) obj;
		if (functionCode == null) {
			if (other.functionCode != null)
				return false;
		} else if (!functionCode.equals(other.functionCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funzioni [functionCode=" + functionCode + ", description="
				+ description + ", enabled=" + enabled + ", accessed="
				+ accessed + ", priority=" + priority + ", operatorType="
				+ operatorType + ", serviziobj=" + serviziobj
				+ ", funzionioperatori=" + funzionioperatori 
				+ "]";
	}


}
