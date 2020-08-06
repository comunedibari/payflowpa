/**
*
* Classe generata
*
*/

package it.nch.is.fo.enti;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class TipologiaEntiFormImpl extends BaseForm implements TipologiaEntiForm {
	
    private String tipoIForm;
    private String descrizioneIForm;

// TYPE SOLO PER LA FORM
    private String adminPasswordIForm;


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public TipologiaEntiFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();		
	}



	public void setNativePojo(Object nativePojo) {		
    	this.nativePojo = nativePojo;
	}


    public String getTipoIForm() {
 		return this.tipoIForm;
 	} 

    public void setTipoIForm(String tipoIForm){
 		this.tipoIForm=tipoIForm;
    } 
    
    public String getDescrizioneIForm() {
 		return this.descrizioneIForm;
 	} 

    public void setDescrizioneIForm(String descrizioneIForm){
 		this.descrizioneIForm=descrizioneIForm;
    } 
    
    public String getAdminPasswordIForm() {
 		return this.adminPasswordIForm;
 	} 

    public void setAdminPasswordIForm(String adminPasswordIForm){
 		this.adminPasswordIForm=adminPasswordIForm;
    } 



    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("tipoIForm="+this.getTipoIForm());
       System.out.println("descrizioneIForm="+this.getDescrizioneIForm());
       System.out.println("adminPasswordIForm="+this.getAdminPasswordIForm());       
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         TipologiaEntiForm _form = this;
         TipologiaEntiCommon _pojo=(TipologiaEntiCommon)this.nativePojo;

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
         	_pojo=(TipologiaEntiCommon) bf.getBean("TipologiaEnti");

         }

         _pojo.setTipoIForm(_form.getTipoIForm());
         _pojo.setDescrizioneIForm(_form.getDescrizioneIForm());
         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.tipoIForm="";
	   		this.descrizioneIForm="";

	   		// DATI FORM NON COMMON

	   		this.adminPasswordIForm="";
	  }



}
