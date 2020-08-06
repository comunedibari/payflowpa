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

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class IntestatarioperatoriFormImpl extends BaseForm implements IntestatarioperatoriForm {

    private String intestatarioIForm;
    private String operatoreIForm;
    private IntestatariCommon intestatariobjIForm;
    private OperatoriCommon operatoriobjIForm;
    private String tipoOperatore;
    private String lockedIForm;
    
    private Set<TributiOperatoriCommon> tributiOperatori = new HashSet<TributiOperatoriCommon>();



    // TYPE SOLO PER LA FORM
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;


	public IntestatarioperatoriFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
		
		intestatariobjIForm=(IntestatariCommon)bf.getBean("IntestatariForm");
		
		operatoriobjIForm=(OperatoriCommon)bf.getBean("OperatoriForm");
	}



	public void setNativePojo(Object nativePojo) {
		if (((IntestatarioperatoriCommon)nativePojo).getIntestatariobjIForm() != null) 
		((BaseForm)this.intestatariobjIForm).setNativePojo(((IntestatarioperatoriCommon)nativePojo).getIntestatariobjIForm());
		
		if (((IntestatarioperatoriCommon)nativePojo).getOperatoriobjIForm() != null) 
			((BaseForm)this.operatoriobjIForm).setNativePojo(((IntestatarioperatoriCommon)nativePojo).getOperatoriobjIForm());
		
    	this.nativePojo = nativePojo;
	}


    public String getIntestatarioIForm() {
 		return this.intestatarioIForm;
 	} 

    public void setIntestatarioIForm(String intestatarioIForm){
 		this.intestatarioIForm=intestatarioIForm;
    } 

    public String getOperatoreIForm() {
 		return this.operatoreIForm;
 	} 

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatoreIForm=operatoreIForm;
    } 

    public IntestatariCommon getIntestatariobjIForm() {
 		return this.intestatariobjIForm;
 	} 

    public void setIntestatariobjIForm(IntestatariCommon intestatariobjIForm){
 		this.intestatariobjIForm=intestatariobjIForm;
    } 

	@Override
	public String getTipoOperatoreIForm() {
		return this.tipoOperatore;
	}


	@Override
	public void setTipoOperatoreIForm(String tipoOperatoreIForm) {
		this.tipoOperatore = tipoOperatoreIForm;
	}


	@Override
	public String getLockedIForm() {
		return this.lockedIForm;
	}

	@Override
	public void setLockedIForm(String locked) {
		this.lockedIForm = locked;
	}

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("intestatarioIForm="+this.getIntestatarioIForm());
       System.out.println("operatoreIForm="+this.getOperatoreIForm());
       System.out.println("intestatariobjIForm="+this.getIntestatariobjIForm());
       System.out.println("operatoriobjIForm="+this.getOperatoriobjIForm());
       System.out.println("lockedIForm="+this.getLockedIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         IntestatarioperatoriForm _form = this;
         IntestatarioperatoriCommon _pojo=(IntestatarioperatoriCommon)this.nativePojo;

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
         	_pojo=(IntestatarioperatoriCommon) bf.getBean("Intestatarioperatori");

         }


         // Oggetto innestato copio in modo ricorso relativamente
         // alla definizione delle dipendenze del properties Common
  		  if (_form.getIntestatariobjIForm()!=null)
             _pojo.setIntestatariobjIForm((IntestatariCommon)_form.getIntestatariobjIForm().copy());
  		  
  		  if (_form.getOperatoriobjIForm()!=null)
              _pojo.setOperatoriobjIForm((OperatoriCommon)_form.getOperatoriobjIForm().copy());
  		  
  		  
  		  _pojo.setLockedIForm(_form.getLockedIForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.intestatarioIForm="";
	   		this.operatoreIForm="";

	   		// DATI FORM NON COMMON

	  }



	@Override
	public int compareTo(IntestatarioperatoriCommon o) {
//		int intForm = this.getIntestatariobjIForm().getCorporateIForm().compareTo(o.getIntestatariobjIForm().getCorporateIForm());
//		return intForm;
		int comp = this.getIntestatariobjIForm().getCategoriaIForm().compareTo(o.getIntestatariobjIForm().getCategoriaIForm());
		if(comp == 0) {
			comp = this.getIntestatariobjIForm().getRagionesocialeIForm().compareTo(o.getIntestatariobjIForm().getRagionesocialeIForm());
		}
		return comp;
	}



	@Override
	public OperatoriCommon getOperatoriobjIForm() {
		return this.operatoriobjIForm;
	}



	@Override
	public void setOperatoriobjIForm(OperatoriCommon intestatariobjIForm) {
		this.operatoriobjIForm = intestatariobjIForm;
	}



	public String getTipoOperatore() {
		return tipoOperatore;
	}



	public void setTipoOperatore(String tipoOperatore) {
		this.tipoOperatore = tipoOperatore;
	}



	@Override
	public Set<TributiOperatoriCommon> getTributiOperatoreForm() {
		return this.tributiOperatori;
	}



	@Override
	public void setTributiOperatoreForm(Set<TributiOperatoriCommon> tributiOperatori) {
		this.tributiOperatori = tributiOperatori;
	}




}
