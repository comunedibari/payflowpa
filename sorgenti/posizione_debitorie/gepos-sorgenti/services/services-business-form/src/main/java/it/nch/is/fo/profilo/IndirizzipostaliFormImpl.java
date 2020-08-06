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

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;


public class IndirizzipostaliFormImpl extends BaseForm implements IndirizzipostaliForm {

    private Integer postalCodeIForm;
    private String capCodeIForm;
    private String postBoxIForm;
    private String fiscalCodeIForm;
    private String cityIForm;
    private String faxNumberIForm;
    private String flagResidenceIForm;
    private String addressIForm;
    private String countryIForm;
    private String vatCodeIForm;
    private String provinceIForm;
    private String phoneNumberIForm;
    private String mobilePhoneIForm;
//    private String emailIForm;
    private String confirmEmailIForm;
    private String numeroCivicoIForm;


// TYPE SOLO PER LA FORM


 private transient BeanFactoryLocator bfl;
 private transient BeanFactoryReference bfr;
 private transient BeanFactory bf;


	public IndirizzipostaliFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf=bfr.getFactory();
	}



	public void setNativePojo(Object nativePojo) {
    	this.nativePojo = nativePojo;
	}


    public Integer getPostalCodeIForm() {
 		return this.postalCodeIForm;
 	} 

    public void setPostalCodeIForm(Integer postalCodeIForm){
 		this.postalCodeIForm=postalCodeIForm;
    } 

    public String getCapCodeIForm() {
 		return this.capCodeIForm;
 	} 

    public void setCapCodeIForm(String capCodeIForm){
 		this.capCodeIForm=capCodeIForm;
    } 

    public String getPostBoxIForm() {
 		return this.postBoxIForm;
 	} 

    public void setPostBoxIForm(String postBoxIForm){
 		this.postBoxIForm=postBoxIForm;
    } 

    public String getFiscalCodeIForm() {
 		return this.fiscalCodeIForm;
 	} 

    public void setFiscalCodeIForm(String fiscalCodeIForm){
 		this.fiscalCodeIForm=fiscalCodeIForm;
    } 

    public String getCityIForm() {
 		return this.cityIForm;
 	} 

    public void setCityIForm(String cityIForm){
 		this.cityIForm=cityIForm;
    } 

    public String getFaxNumberIForm() {
 		return this.faxNumberIForm;
 	} 

    public void setFaxNumberIForm(String faxNumberIForm){
 		this.faxNumberIForm=faxNumberIForm;
    } 

    public String getFlagResidenceIForm() {
 		return this.flagResidenceIForm;
 	} 

    public void setFlagResidenceIForm(String flagResidenceIForm){
 		this.flagResidenceIForm=flagResidenceIForm;
    } 

    public String getAddressIForm() {
 		return this.addressIForm;
 	} 

    public void setAddressIForm(String addressIForm){
 		this.addressIForm=addressIForm;
    } 

    public String getCountryIForm() {
 		return this.countryIForm;
 	} 

    public void setCountryIForm(String countryIForm){
 		this.countryIForm=countryIForm;
    } 

    public String getVatCodeIForm() {
 		return this.vatCodeIForm;
 	} 

    public void setVatCodeIForm(String vatCodeIForm){
 		this.vatCodeIForm=vatCodeIForm;
    } 

    public String getProvinceIForm() {
 		return this.provinceIForm;
 	} 

    public void setProvinceIForm(String provinceIForm){
 		this.provinceIForm=provinceIForm;
    } 

    public String getPhoneNumberIForm() {
 		return this.phoneNumberIForm;
 	} 

    public void setPhoneNumberIForm(String phoneNumberIForm){
 		this.phoneNumberIForm=phoneNumberIForm;
    } 

    public String getMobilePhoneIForm() {
 		return this.mobilePhoneIForm;
 	} 

    public void setMobilePhoneIForm(String mobilePhoneIForm){
 		this.mobilePhoneIForm=mobilePhoneIForm;
    }
    
//	public String getEmailIForm() {
//		return this.emailIForm;
//	}
//
//	public void setEmailIForm(String emailIForm) {
//		this.emailIForm = emailIForm;
//		
//	}
	
	public String getConfirmEmailIForm() {
		return this.confirmEmailIForm;
	}

	public void setConfirmEmailIForm(String confirmEmailIForm) {
		this.confirmEmailIForm = confirmEmailIForm;
	}
		
	public String getNumeroCivicoIForm() {
		return this.numeroCivicoIForm;
	}

	public void setNumeroCivicoIForm(String numeroCivicoIForm) {
		this.numeroCivicoIForm = numeroCivicoIForm;
	}


/**
 *
 * METODI SOLO FORM
 *
 */


    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("postalCodeIForm="+this.getPostalCodeIForm());
       System.out.println("capCodeIForm="+this.getCapCodeIForm());
       System.out.println("postBoxIForm="+this.getPostBoxIForm());
       System.out.println("fiscalCodeIForm="+this.getFiscalCodeIForm());
       System.out.println("cityIForm="+this.getCityIForm());
       System.out.println("faxNumberIForm="+this.getFaxNumberIForm());
       System.out.println("flagResidenceIForm="+this.getFlagResidenceIForm());
       System.out.println("addressIForm="+this.getAddressIForm());
       System.out.println("countryIForm="+this.getCountryIForm());
       System.out.println("vatCodeIForm="+this.getVatCodeIForm());
       System.out.println("provinceIForm="+this.getProvinceIForm());
       System.out.println("phoneNumberIForm="+this.getPhoneNumberIForm());
       System.out.println("mobilePhoneIForm="+this.getMobilePhoneIForm());
//       System.out.println("emailIForm="+this.getEmailIForm());
       System.out.println("confirmEmailIForm="+this.getConfirmEmailIForm());
       System.out.println("numeroCivicoIForm="+this.getNumeroCivicoIForm());
    }


    /**
     *
     *COPY/MERGE Method Form Vs Pojo
     *
     **/

      public CommonBusinessObject copy(){

         IndirizzipostaliForm _form = this;
         IndirizzipostaliCommon _pojo=(IndirizzipostaliCommon)this.nativePojo;

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
         	_pojo=(IndirizzipostaliCommon) bf.getBean("Indirizzipostali");

         }


         _pojo.setPostalCodeIForm(_form.getPostalCodeIForm());
         _pojo.setCapCodeIForm(_form.getCapCodeIForm());
         _pojo.setPostBoxIForm(_form.getPostBoxIForm());
         _pojo.setFiscalCodeIForm(_form.getFiscalCodeIForm());
         _pojo.setCityIForm(_form.getCityIForm());
         _pojo.setFaxNumberIForm(_form.getFaxNumberIForm());
         _pojo.setFlagResidenceIForm(_form.getFlagResidenceIForm());
         _pojo.setAddressIForm(_form.getAddressIForm());
         _pojo.setCountryIForm(_form.getCountryIForm());
         _pojo.setVatCodeIForm(_form.getVatCodeIForm());
         _pojo.setProvinceIForm(_form.getProvinceIForm());
         _pojo.setPhoneNumberIForm(_form.getPhoneNumberIForm());
         _pojo.setMobilePhoneIForm(_form.getMobilePhoneIForm());
//         _pojo.setEmailIForm(_form.getEmailIForm());
         _pojo.setConfirmEmailIForm(_form.getConfirmEmailIForm());
         _pojo.setNumeroCivicoIForm(_form.getNumeroCivicoIForm());

         return _pojo;
	  }

	  public DTO incapsulateBO() {
	  	return new DTOImpl(this);
	  }


// Metodo di RESET

	  public void reset() {
	   		//this.postalCodeIForm="";
	   		this.capCodeIForm="";
	   		this.postBoxIForm="";
	   		this.fiscalCodeIForm="";
	   		this.cityIForm="";
	   		this.faxNumberIForm="";
	   		this.flagResidenceIForm="";
	   		this.addressIForm="";
	   		this.countryIForm="";
	   		this.vatCodeIForm="";
	   		this.provinceIForm="";
	   		this.phoneNumberIForm="";
	   		this.mobilePhoneIForm="";
//	   		this.emailIForm="";
	   		this.numeroCivicoIForm="";
	   		this.confirmEmailIForm="";
	   		// DATI FORM NON COMMON

	  }








	


}
