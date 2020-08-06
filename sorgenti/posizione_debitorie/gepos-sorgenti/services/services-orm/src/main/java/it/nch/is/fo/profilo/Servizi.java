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
@Table(name="SERVIZI")
@SuppressWarnings("rawtypes")
public class Servizi extends PojoImpl implements ServiziCommon, ServiziPojo {

	/*** Persistent Properties ***/
    private String serviceCode;
    private String enabled;
    private String description;
    
    /*** Persistent References ***/
    private Area areaobj;
    
    /*** Persistent Collections ***/
    private Set<Funzioni> funzioni;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

    @Id
    @Column(name="codservizio")
    public String getServiceCode() {
 		return this.serviceCode;
 	} 
    public void setServiceCode(String serviceCode){
 		this.serviceCode=serviceCode;
    } 

    @Column(name="ABL_GLOBAL")
    public String getEnabled() {
 		return this.enabled;
 	} 
    public void setEnabled(String enabled){
 		this.enabled=enabled;
    } 
    
    @Column(name="descrizione")
    public String getDescription() {
 		return this.description;
 	} 
    public void setDescription(String description){
 		this.description=description;
    } 

    @ManyToOne(targetEntity=Area.class)
    @JoinColumn(name="CODAREA", nullable=false)
    public AreePojo getAreaobj() {
 		return this.areaobj;
 	} 
    public void setAreaobj(AreePojo areaobj){
 		this.areaobj=(Area)areaobj;
    } 

    @OneToMany(targetEntity=Funzioni.class, mappedBy="serviziobj")
    public Set<Funzioni> getFunzioni() {
 		return this.funzioni;
 	} 
    public void setFunzioni(Set funzioni){
 		this.funzioni= funzioni;
    } 

    @Override
    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("serviceCode="+this.getServiceCode());
       System.out.println("enabled="+this.getEnabled());
       System.out.println("description="+this.getDescription());
       System.out.println("areaobj="+this.getAreaobj());
       System.out.println("funzioni="+this.getFunzioni());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
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
    public String getDescriptionIForm() {
 		return this.description;
 	} 
    @Override
    public void setDescriptionIForm(String descriptionIForm){
 		this.description=descriptionIForm;
    } 

    @Override
    @Transient
    public String getServiceCodeIForm() {
 		return this.serviceCode;
 	} 
    @Override
    public void setServiceCodeIForm(String serviceCodeIForm){
 		this.serviceCode=serviceCodeIForm;
    } 

    @Override
    @Transient
    public Collection getFunzioniIForm() {
 		return (this.funzioni);
 	} 
    @Override
    public void setFunzioniIForm(Collection funzioniIForm){
 		this.funzioni= new LinkedHashSet<Funzioni>(funzioniIForm);
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
         ServiziForm _form =(ServiziForm) bf.getBean("ServiziForm");

          _form.setNativePojo(this);

         ServiziCommon _pojo = this;

         _form.setServiceCodeIForm(_pojo.getServiceCodeIForm());
         _form.setEnabledIForm(_pojo.getEnabledIForm());
         _form.setDescriptionIForm(_pojo.getDescriptionIForm());

         /*
          *
          *  Code copy FunzioniIForm attribute
          *
          */ 
 		  Collection destFunzioniIForm = new Vector();
          if (_pojo.getFunzioniIForm()!=null){
 		  	  Iterator srcFunzioniIForm = _pojo.getFunzioniIForm().iterator();
 		  	  while (srcFunzioniIForm.hasNext()){
	 			  CommonBusinessObject _innerPojo =  (CommonBusinessObject)srcFunzioniIForm.next();
	 			  PojoImpl source = (PojoImpl)_innerPojo;
	 			  source.setFlagDoNotCopy(true); // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
	 			  destFunzioniIForm.add(_innerPojo.copy());
		  	   }
		      _form.setFunzioniIForm(destFunzioniIForm);
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

         Servizi _pojoCurrent = this;
         Servizi _pojo = new Servizi();

         _pojo.setServiceCode(_pojoCurrent.getServiceCode());
         _pojo.setEnabled(_pojoCurrent.getEnabled());
         _pojo.setDescription(_pojoCurrent.getDescription());
         _pojo.setAreaobj(_pojoCurrent.getAreaobj());
         /**
          *
          *Trovata Collection [funzioni] NON applico clone annidato!
          *
          */

         /**
          *
          *Trovata Collection [servizioperatori] NON applico clone annidato!
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
				+ ((serviceCode == null) ? 0 : serviceCode.hashCode());
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
		Servizi other = (Servizi) obj;
		if (serviceCode == null) {
			if (other.serviceCode != null)
				return false;
		} else if (!serviceCode.equals(other.serviceCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Servizi [serviceCode=" + serviceCode + ", enabled=" + enabled
				+ ", description=" + description + ", areaobj=" + areaobj
				+ ", funzioni=" + funzioni + ", servizioperatori="
				+ "]";
	}

}
