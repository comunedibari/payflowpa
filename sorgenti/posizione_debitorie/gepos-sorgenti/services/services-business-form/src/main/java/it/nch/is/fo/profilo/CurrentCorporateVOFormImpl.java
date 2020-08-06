/**
*
* Classe generata da utilizzare per il solo front end.
*
*/

package it.nch.is.fo.profilo;

import it.nch.is.fo.profilo.CurrentCorporateVOCommon;
import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.*;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.util.Tracer;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;


public class CurrentCorporateVOFormImpl extends BaseForm implements CurrentCorporateVOForm {

    private String corporateIForm;
    private String nameIForm;
    private String siaCodeIForm;
    private String siaCbiCodeIForm;
    private String abiCodeIForm;
    private String cabCodeIForm;
    private String raplCodeIForm;
    private String laplCodeIForm;
    private String tipointestatarioIForm;
    private String fiscalCodeIForm;
    private String addressIForm;
    private String cityIForm;
    private String abiaccentratoreIForm;
    private String capCodeIForm;
    private String provinceIForm;
    private String denominazioneIForm;
    private String flagResidenceIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public CurrentCorporateVOFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public String getCorporateIForm() {
 		return this.corporateIForm;
 	}

    public void setCorporateIForm(String corporateIForm){
 		this.corporateIForm=corporateIForm;
    }

    public String getNameIForm() {
 		return this.nameIForm;
 	}

    public void setNameIForm(String nameIForm){
 		this.nameIForm=nameIForm;
    }

    public String getSiaCodeIForm() {
 		return this.siaCodeIForm;
 	}

    public void setSiaCodeIForm(String siaCodeIForm){
 		this.siaCodeIForm=siaCodeIForm;
    }

    public String getAbiCodeIForm() {
 		return this.abiCodeIForm;
 	}

    public void setAbiCodeIForm(String abiCodeIForm){
 		this.abiCodeIForm=abiCodeIForm;
    }

    public String getCabCodeIForm() {
 		return this.cabCodeIForm;
 	}

    public void setCabCodeIForm(String cabCodeIForm){
 		this.cabCodeIForm=cabCodeIForm;
    }

    public String getRaplCodeIForm() {
 		return this.raplCodeIForm;
 	}

    public void setRaplCodeIForm(String raplCodeIForm){
 		this.raplCodeIForm=raplCodeIForm;
    }

    public String getLaplCodeIForm() {
 		return this.laplCodeIForm;
 	}

    public void setLaplCodeIForm(String laplCodeIForm){
 		this.laplCodeIForm=laplCodeIForm;
    }

    public String getTipointestatarioIForm() {
 		return this.tipointestatarioIForm;
 	}

    public void setTipointestatarioIForm(String tipointestatarioIForm){
 		this.tipointestatarioIForm=tipointestatarioIForm;
    }

    public String getFiscalCodeIForm() {
 		return this.fiscalCodeIForm;
 	}

    public void setFiscalCodeIForm(String fiscalCodeIForm){
 		this.fiscalCodeIForm=fiscalCodeIForm;
    }

    public String getAddressIForm() {
 		return this.addressIForm;
 	}

    public void setAddressIForm(String addressIForm){
 		this.addressIForm=addressIForm;
    }

    public String getCityIForm() {
 		return this.cityIForm;
 	}

    public void setCityIForm(String cityIForm){
 		this.cityIForm=cityIForm;
    }

    public String getAbiaccentratoreIForm() {
 		return this.abiaccentratoreIForm;
 	}

    public void setAbiaccentratoreIForm(String abiaccentratoreIForm){
 		this.abiaccentratoreIForm=abiaccentratoreIForm;
    }

    public String getCapCodeIForm() {
 		return this.capCodeIForm;
 	}

    public void setCapCodeIForm(String capCodeIForm){
 		this.capCodeIForm=capCodeIForm;
    }

    public String getProvinceIForm() {
 		return this.provinceIForm;
 	}

    public void setProvinceIForm(String provinceIForm){
 		this.provinceIForm=provinceIForm;
    }

    public String getDenominazioneIForm() {
 		return this.denominazioneIForm;
 	}

    public void setDenominazioneIForm(String denominazioneIForm){
 		this.denominazioneIForm=denominazioneIForm;
    }

    public String getFlagResidenceIForm() {
 		return this.flagResidenceIForm;
 	}

    public void setFlagResidenceIForm(String flagResidenceIForm){
 		this.flagResidenceIForm=flagResidenceIForm;
    }

/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("corporateIForm="+this.getCorporateIForm());
       System.out.println("nameIForm="+this.getNameIForm());
       System.out.println("siaCodeIForm="+this.getSiaCodeIForm());
       System.out.println("siaCbiCodeIForm="+this.getSiaCbiCodeIForm());
       System.out.println("abiCodeIForm="+this.getAbiCodeIForm());
       System.out.println("cabCodeIForm="+this.getCabCodeIForm());
       System.out.println("raplCodeIForm="+this.getRaplCodeIForm());
       System.out.println("laplCodeIForm="+this.getLaplCodeIForm());
       System.out.println("tipointestatarioIForm="+this.getTipointestatarioIForm());
       System.out.println("fiscalCodeIForm="+this.getFiscalCodeIForm());
       System.out.println("addressIForm="+this.getAddressIForm());
       System.out.println("cityIForm="+this.getCityIForm());
       System.out.println("abiaccentratoreIForm="+this.getAbiaccentratoreIForm());
       System.out.println("capCodeIForm="+this.getCapCodeIForm());
       System.out.println("provinceIForm="+this.getProvinceIForm());
       System.out.println("denominazioneIForm="+this.getDenominazioneIForm());
       System.out.println("flagResidenceIForm="+this.getFlagResidenceIForm());
       System.out.println("importoMaxFlussoIForm="+this.getImportoMaxFlussoIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         CurrentCorporateVOForm _form = this;
         CurrentCorporateVOCommon _pojo=(CurrentCorporateVOCommon)this.nativePojo;

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
         	_pojo=(CurrentCorporateVOCommon) bf.getBean("CurrentCorporateVO");

         }


         _pojo.setCorporateIForm(_form.getCorporateIForm());
         _pojo.setNameIForm(_form.getNameIForm());
         _pojo.setSiaCodeIForm(_form.getSiaCodeIForm());
         _pojo.setSiaCbiCodeIForm(_form.getSiaCbiCodeIForm());
         _pojo.setAbiCodeIForm(_form.getAbiCodeIForm());
         _pojo.setCabCodeIForm(_form.getCabCodeIForm());
         _pojo.setRaplCodeIForm(_form.getRaplCodeIForm());
         _pojo.setLaplCodeIForm(_form.getLaplCodeIForm());
         _pojo.setTipointestatarioIForm(_form.getTipointestatarioIForm());
         _pojo.setFiscalCodeIForm(_form.getFiscalCodeIForm());
         _pojo.setAddressIForm(_form.getAddressIForm());
         _pojo.setCityIForm(_form.getCityIForm());
         _pojo.setAbiaccentratoreIForm(_form.getAbiaccentratoreIForm());
         _pojo.setCapCodeIForm(_form.getCapCodeIForm());
         _pojo.setProvinceIForm(_form.getProvinceIForm());
         _pojo.setDenominazioneIForm(_form.getDenominazioneIForm());
         _pojo.setFlagResidenceIForm(_form.getFlagResidenceIForm());
         _pojo.setImportoMaxFlussoIForm(_form.getImportoMaxFlussoIForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		this.corporateIForm="";
	   		this.nameIForm="";
	   		this.siaCodeIForm="";
	   		this.siaCbiCodeIForm="";
	   		this.abiCodeIForm="";
	   		this.cabCodeIForm="";
	   		this.raplCodeIForm="";
	   		this.laplCodeIForm="";
	   		this.tipointestatarioIForm="";
	   		this.fiscalCodeIForm="";
	   		this.addressIForm="";
	   		this.cityIForm="";
	   		this.abiaccentratoreIForm="";
	   		this.capCodeIForm="";
	   		this.provinceIForm="";
	   		this.denominazioneIForm="";
	   		this.flagResidenceIForm="";

	   		// DATI FORM NON COMMON

	  }



	@Override
	public String getImportoMaxFlussoIForm() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setImportoMaxFlussoIForm(String importoMaxFlussoIForm) {
		// TODO Auto-generated method stub

	}



	@Override
	public String getCategoria() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setCategoria(String categoria) {
		// TODO Auto-generated method stub

	}


	@Override
	public String getIdEnte() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setIdEnte(String idEnte) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getCdEnte() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setCdEnte(String cdEnte) {
		// TODO Auto-generated method stub

	}



	public String getSiaCbiCodeIForm() {
 		return this.siaCbiCodeIForm;
 	}

    public void setSiaCbiCodeIForm(String siaCbiCodeIForm){
 		this.siaCbiCodeIForm=siaCbiCodeIForm;
    }



}
