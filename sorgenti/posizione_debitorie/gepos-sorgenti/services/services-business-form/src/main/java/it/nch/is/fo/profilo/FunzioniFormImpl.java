/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class FunzioniFormImpl extends BaseForm implements FunzioniForm {

    private String functionCodeIForm;
    private String descriptionIForm;
    private String enabledIForm;
    private String accessedIForm;
    private String priorityIForm;
    private String operatorTypeIForm;
    private ServiziCommon serviziobjIForm;
    private Collection funzionioperatoriIForm;
    private Collection rapportiEnabledIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public FunzioniFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
		serviziobjIForm=(ServiziCommon)bf.getBean("ServiziForm");
	}



	public void setNativePojo(Object nativePojo) {
		((BaseForm)this.serviziobjIForm).setNativePojo(((FunzioniCommon)nativePojo).getServiziobjIForm());
    	this.nativePojo = nativePojo;
	}


    public String getFunctionCodeIForm() {
 		return this.functionCodeIForm;
 	} 

    public void setFunctionCodeIForm(String functionCodeIForm){
 		this.functionCodeIForm=functionCodeIForm;
    } 

    public String getDescriptionIForm() {
 		return this.descriptionIForm;
 	} 

    public void setDescriptionIForm(String descriptionIForm){
 		this.descriptionIForm=descriptionIForm;
    } 

    public String getEnabledIForm() {
 		return this.enabledIForm;
 	} 

    public void setEnabledIForm(String enabledIForm){
 		this.enabledIForm=enabledIForm;
    } 

    public String getAccessedIForm() {
 		return this.accessedIForm;
 	} 

    public void setAccessedIForm(String accessedIForm){
 		this.accessedIForm=accessedIForm;
    } 

    public String getPriorityIForm() {
 		return this.priorityIForm;
 	} 

    public void setPriorityIForm(String priorityIForm){
 		this.priorityIForm=priorityIForm;
    } 

    public String getOperatorTypeIForm() {
 		return this.operatorTypeIForm;
 	} 

    public void setOperatorTypeIForm(String operatorTypeIForm){
 		this.operatorTypeIForm=operatorTypeIForm;
    } 

    public ServiziCommon getServiziobjIForm() {
 		return this.serviziobjIForm;
 	} 

    public void setServiziobjIForm(ServiziCommon serviziobjIForm){
 		this.serviziobjIForm=serviziobjIForm;
    } 

    public Collection getFunzionioperatoriIForm() {
 		return this.funzionioperatoriIForm;
 	} 

    public void setFunzionioperatoriIForm(Collection funzionioperatoriIForm){
 		this.funzionioperatoriIForm=funzionioperatoriIForm;
    } 

    public Collection getRapportiEnabledIForm() {
 		return this.rapportiEnabledIForm;
 	} 

    public void setRapportiEnabledIForm(Collection rapportiEnabledIForm){
 		this.rapportiEnabledIForm=rapportiEnabledIForm;
    } 

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("functionCodeIForm="+this.getFunctionCodeIForm());
       System.out.println("descriptionIForm="+this.getDescriptionIForm());
       System.out.println("enabledIForm="+this.getEnabledIForm());
       System.out.println("accessedIForm="+this.getAccessedIForm());
       System.out.println("priorityIForm="+this.getPriorityIForm());
       System.out.println("operatorTypeIForm="+this.getOperatorTypeIForm());
       System.out.println("serviziobjIForm="+this.getServiziobjIForm());
       System.out.println("funzionioperatoriIForm="+this.getFunzionioperatoriIForm());
       System.out.println("rapportiEnabledIForm="+this.getRapportiEnabledIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         FunzioniForm _form = this;
         FunzioniCommon _pojo=(FunzioniCommon)this.nativePojo;

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
         	_pojo=(FunzioniCommon) bf.getBean("Funzioni");

         }


         _pojo.setFunctionCodeIForm(_form.getFunctionCodeIForm());
         _pojo.setDescriptionIForm(_form.getDescriptionIForm());
         _pojo.setEnabledIForm(_form.getEnabledIForm());
         _pojo.setAccessedIForm(_form.getAccessedIForm());
         _pojo.setPriorityIForm(_form.getPriorityIForm());
         _pojo.setOperatorTypeIForm(_form.getOperatorTypeIForm());
         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_form.getServiziobjIForm()!=null)
             _pojo.setServiziobjIForm((ServiziCommon)_form.getServiziobjIForm().copy());


         /*
          *
          *  Code copy FunzionioperatoriIForm attribute
          *
          */ 

         /*
          *
          *  Code copy FunzionioperatoriIForm attribute
          *
          */ 

         if (_form.getFunzionioperatoriIForm()!=null){
 		  		Collection destFunzionioperatoriIForm = new HashSet();
 		  		Iterator srcFunzionioperatoriIForm = _form.getFunzionioperatoriIForm().iterator();
 		  		while (srcFunzionioperatoriIForm.hasNext()){
	 				CommonBusinessObject _innerForm =  (CommonBusinessObject)srcFunzionioperatoriIForm.next();
	 				destFunzionioperatoriIForm.add(_innerForm.copy());
		  		}
		      	_pojo.setFunzionioperatoriIForm(destFunzionioperatoriIForm);
		  }



         /*
          *
          *  Code copy RapportiEnabledIForm attribute
          *
          */ 


         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.functionCodeIForm="";
	   		this.descriptionIForm="";
	   		this.enabledIForm="";
	   		this.accessedIForm="";
	   		this.priorityIForm="";
	   		this.operatorTypeIForm="";

	   		// DATI FORM NON COMMON

	  }



}
