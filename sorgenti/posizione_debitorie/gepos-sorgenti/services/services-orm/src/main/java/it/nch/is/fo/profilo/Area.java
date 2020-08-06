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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

@SuppressWarnings(value="serial")
@Entity
@Table(name="AREA")
public class Area extends PojoImpl implements AreeCommon, AreePojo {
	
	/*** Persistent Properties ***/
    private String areaCode;
    private Date updateDate;
    private String updateUser;
    private String description;
    private String enabled;
    /*** Persistent Reference - ManyToOne Association***/
    private Applicazioni applicazioneobj;
    /*** Persistent Collection - OneToMany Association***/
    private Set<Servizi> servizi;

    /*** Transient Properties ***/
    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

    @Id
    @Column(name="CODAREA")
    public String getAreaCode() {
 		return this.areaCode;
 	} 
    public void setAreaCode(String areaCode){
 		this.areaCode=areaCode;
    } 

    @Column(name="datamodifica")
    public Date getUpdateDate() {
 		return this.updateDate;
 	} 
    public void setUpdateDate(Date updateDate){
 		this.updateDate=updateDate;
    } 

    @Column(name="utentemod")
    public String getUpdateUser() {
 		return this.updateUser;
 	} 
    public void setUpdateUser(String updateUser){
 		this.updateUser=updateUser;
    } 

    @Column(name="descrizione")
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
    public void setEnabled(String enabled){
 		this.enabled=enabled;
    } 

    @ManyToOne(targetEntity=Applicazioni.class)
    @JoinColumn(name="APPLICAZIONE")
    public ApplicazioniCommon getApplicazioneobj() {
 		return (ApplicazioniCommon)this.applicazioneobj;
 		//return this.applicazioneobj;
 	} 
    public void setApplicazioneobj(ApplicazioniCommon applicazioneobj){
 		this.applicazioneobj=(Applicazioni)applicazioneobj;
    } 

    @OneToMany(targetEntity=Servizi.class, mappedBy="areaobj")
    public Set<Servizi> getServizi() {
 		return this.servizi;
 	} 
    public void setServizi(Set servizi){
 		this.servizi= servizi;
    } 

    @Override
    @Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("areaCode="+this.getAreaCode());
       System.out.println("updateDate="+this.getUpdateDate());
       System.out.println("updateUser="+this.getUpdateUser());
       System.out.println("description="+this.getDescription());
       System.out.println("enabled="+this.getEnabled());
       System.out.println("applicazioneobj="+this.getApplicazioneobj());
       System.out.println("servizi="+this.getServizi());
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
    @Transient    
    public void setEnabledIForm(String enabledIForm){
 		this.enabled=enabledIForm;
    } 

    
    @Override
    @Transient    
    public Collection getServiziIForm() {
 		return (this.servizi);
 	} 
    @Override
    @Transient    
    public void setServiziIForm(Collection serviziIForm){
 		this.servizi=new LinkedHashSet<Servizi>(serviziIForm);
    } 

    @Override
    @Transient    
    public String getAreaCodeIForm() {
 		return this.areaCode;
 	} 
    @Override
    @Transient    
   public void setAreaCodeIForm(String areaCodeIForm){
 		this.areaCode=areaCodeIForm;
    } 

    @Override
    @Transient    
    public String getDescriptionIForm() {
 		return this.description;
 	} 
    @Override
    @Transient    
    public void setDescriptionIForm(String descriptionIForm){
 		this.description=descriptionIForm;
    } 

    @Override
    @Transient    
    public ApplicazioniCommon getApplicazioneobjIForm() {
 		return this.applicazioneobj;
 	} 
    public void setApplicazioneobjIForm(ApplicazioniCommon applicazioneobjIForm){
 		this.applicazioneobj=(Applicazioni)applicazioneobjIForm;
    } 

    @Override
    @Transient    
    public String getUpdateDateIForm() {
 		return Traslator.dateToString(this.updateDate);
 	} 
    @Override
    @Transient    
   public void setUpdateDateIForm(String updateDateIForm){
 		this.updateDate=Traslator.stringToDate(updateDateIForm);
    } 

    @Override
    @Transient    
    public String getUpdateUserIForm() {
 		return this.updateUser;
 	} 
    @Override
    @Transient    
   public void setUpdateUserIForm(String updateUserIForm){
 		this.updateUser=updateUserIForm;
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
         AreeForm _form =(AreeForm) bf.getBean("AreeForm");

          _form.setNativePojo(this);

         AreeCommon _pojo = this;

         _form.setAreaCodeIForm(_pojo.getAreaCodeIForm());
         _form.setUpdateDateIForm(_pojo.getUpdateDateIForm());
         _form.setUpdateUserIForm(_pojo.getUpdateUserIForm());
         _form.setDescriptionIForm(_pojo.getDescriptionIForm());
         _form.setEnabledIForm(_pojo.getEnabledIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_pojo.getApplicazioneobjIForm()!=null && !getFlagDoNotCopy()) // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
        	 _form.setApplicazioneobjIForm((ApplicazioniCommon)_pojo.getApplicazioneobjIForm().copy());


         /*
          *
          *  Code copy ServiziIForm attribute
          *
          */ 

 		  Collection destServiziIForm = new Vector();
          if (_pojo.getServiziIForm()!=null){
 		  	  Iterator srcServiziIForm = _pojo.getServiziIForm().iterator();
 		  	  while (srcServiziIForm.hasNext()){
	 			  CommonBusinessObject _innerPojo =  (CommonBusinessObject)srcServiziIForm.next();
	 			  PojoImpl source = (PojoImpl)_innerPojo;
	 			  source.setFlagDoNotCopy(true); // SADPA - This patch was made to get rid of infinite recursions in case of bidirectional links (one-to-many-many-to-one)
	 			  destServiziIForm.add(_innerPojo.copy());
		  	   }
		      _form.setServiziIForm(destServiziIForm);
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

         Area _pojoCurrent = this;
         Area _pojo = new Area();

         _pojo.setAreaCode(_pojoCurrent.getAreaCode());
         _pojo.setUpdateDate(_pojoCurrent.getUpdateDate());
         _pojo.setUpdateUser(_pojoCurrent.getUpdateUser());
         _pojo.setDescription(_pojoCurrent.getDescription());
         _pojo.setEnabled(_pojoCurrent.getEnabled());
         _pojo.setApplicazioneobj(_pojoCurrent.getApplicazioneobj());
         /**
          *
          *Trovata Collection [servizi] NON applico clone annidato!
          *
          */

         _pojo.setId(_pojoCurrent.getId());

         return _pojo;
	  }
    
	/*** Overriding of methods: equals() and hashCode() to ensure identity by value ***/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaCode == null) ? 0 : areaCode.hashCode());
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
		Area other = (Area) obj;
		if (areaCode == null) {
			if (other.areaCode != null)
				return false;
		} else if (!areaCode.equals(other.areaCode))
			return false;
		return true;
	}
	
	/*** Overriding of method: toString() ***/
	@Override
	public String toString() {
		return "Area [areaCode=" + areaCode + ", updateDate=" + updateDate
				+ ", updateUser=" + updateUser + ", description=" + description
				+ ", enabled=" + enabled + ", applicazioneobj="
				+ applicazioneobj + ", servizi=" + servizi + "]";
	}

    

}
