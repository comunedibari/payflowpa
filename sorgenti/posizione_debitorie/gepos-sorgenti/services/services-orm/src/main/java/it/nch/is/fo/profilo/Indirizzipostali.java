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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
@Entity
@Table(name="INDIRIZZIPOSTALI")
public class Indirizzipostali extends PojoImpl implements IndirizzipostaliCommon, IndirizzipostaliPojo {

	/*** Persistent Properties ***/
    private Integer postalCode;
    private String capCode;
    private String postBox;
    private String fiscalCode;
    private String city;
    private String faxNumber;
    private String flagResidence;
    private String address;
    private String country;
    private String vatCode;
    private String province;
    private String phoneNumber;
    private String mobilePhone;
//    private String email;
    private String numeroCivico;
    
    /*** Transient Properties ***/
	private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;

    private transient String confirmEmailIForm;
    
    @Id
    @Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="indirizzi_postali_pk_gen")	
	@SequenceGenerator(
		    name="indirizzi_postali_pk_gen",
		    sequenceName="INDIRIZZIPOSTALI_ID_SEQ",
		    allocationSize=1
		)	    
    public Integer getPostalCode() {
 		return this.postalCode;
 	} 
    public void setPostalCode(Integer postalCode){
 		this.postalCode=postalCode;
    } 

    @Column(name="CAP")
    public String getCapCode() {
 		return this.capCode;
 	} 
    public void setCapCode(String capCode){
 		this.capCode=capCode;
    } 
    
    @Column(name="CASELLAPOSTALE")
    public String getPostBox() {
 		return this.postBox;
 	} 
    public void setPostBox(String postBox){
 		this.postBox=postBox;
    } 

    @Column(name="CODICEFISCALE")
    public String getFiscalCode() {
 		return this.fiscalCode;
 	} 
    public void setFiscalCode(String fiscalCode){
 		this.fiscalCode=fiscalCode;
    } 

    @Column(name="COMUNE")
    public String getCity() {
 		return this.city;
 	} 
    public void setCity(String city){
 		this.city=city;
    } 

    @Column(name="FAX")
    public String getFaxNumber() {
 		return this.faxNumber;
 	} 
    public void setFaxNumber(String faxNumber){
 		this.faxNumber=faxNumber;
    } 

    @Column(name="FLAGRESIDENTE")
    public String getFlagResidence() {
 		return this.flagResidence;
 	} 
    public void setFlagResidence(String flagResidence){
 		this.flagResidence=flagResidence;
    } 

    @Column(name="INDIRIZZO")
    public String getAddress() {
 		return this.address;
 	} 
    public void setAddress(String address){
 		this.address=address;
    } 

    @Column(name="NAZIONE")
    public String getCountry() {
 		return this.country;
 	} 
    public void setCountry(String country){
 		this.country=country;
    } 

    @Column(name="PARTITAIVA")
    public String getVatCode() {
 		return this.vatCode;
 	} 
    public void setVatCode(String vatCode){
 		this.vatCode=vatCode;
    } 

    @Column(name="PROVINCIA")
    public String getProvince() {
 		return this.province;
 	} 
    public void setProvince(String province){
 		this.province=province;
    } 

    @Column(name="TELEFONO")
    public String getPhoneNumber() {
 		return this.phoneNumber;
 	} 
    public void setPhoneNumber(String phoneNumber){
 		this.phoneNumber=phoneNumber;
    } 

    @Column(name="TELEFONOCELLULARE")
    public String getMobilePhone() {
 		return this.mobilePhone;
 	} 
    public void setMobilePhone(String mobilePhone){
 		this.mobilePhone=mobilePhone;
    } 
    
//    @Column(name="EMAIL")
//    public String getEmail() {
// 		return this.email;
// 	} 
//    public void setEmail(String email){
// 		this.email=email;
//    } 

    @Column(name="NUMEROCIVICO")
    public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	@Override
	@Transient
    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("postalCode="+this.getPostalCode());
       System.out.println("capCode="+this.getCapCode());
       System.out.println("postBox="+this.getPostBox());
       System.out.println("fiscalCode="+this.getFiscalCode());
       System.out.println("city="+this.getCity());
       System.out.println("faxNumber="+this.getFaxNumber());
       System.out.println("flagResidence="+this.getFlagResidence());
       System.out.println("address="+this.getAddress());
       System.out.println("country="+this.getCountry());
       System.out.println("vatCode="+this.getVatCode());
       System.out.println("province="+this.getProvince());
       System.out.println("phoneNumber="+this.getPhoneNumber());
       System.out.println("mobilePhone="+this.getMobilePhone());
//       System.out.println("email="+this.getEmail());
       System.out.println("numeroCivico="+this.getNumeroCivico());
    }

    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
	@Override
	@Transient
    public String getFiscalCodeIForm() {
 		return this.fiscalCode;
 	} 
	@Override
    public void setFiscalCodeIForm(String fiscalCodeIForm){
 		this.fiscalCode=fiscalCodeIForm;
    } 

	@Override
	@Transient
    public String getVatCodeIForm() {
 		return this.vatCode;
 	} 
	@Override
    public void setVatCodeIForm(String vatCodeIForm){
 		this.vatCode=vatCodeIForm;
    } 

	@Override
	@Transient
    public String getPostBoxIForm() {
 		return this.postBox;
 	} 
	@Override
    public void setPostBoxIForm(String postBoxIForm){
 		this.postBox=postBoxIForm;
    } 

	@Override
	@Transient
    public String getCityIForm() {
 		return this.city;
 	} 
	@Override
    public void setCityIForm(String cityIForm){
 		this.city=cityIForm;
    } 

	@Override
	@Transient
    public String getCountryIForm() {
 		return this.country;
 	} 
	@Override
    public void setCountryIForm(String countryIForm){
 		this.country=countryIForm;
    } 

	@Override
	@Transient
    public Integer getPostalCodeIForm() {
 		return this.postalCode;
 	} 
	@Override
    public void setPostalCodeIForm(Integer postalCodeIForm){
 		this.postalCode=postalCodeIForm;
    } 

	@Override
	@Transient
    public String getFaxNumberIForm() {
 		return this.faxNumber;
 	} 
	@Override
    public void setFaxNumberIForm(String faxNumberIForm){
 		this.faxNumber=faxNumberIForm;
    } 

	@Override
	@Transient
    public String getPhoneNumberIForm() {
 		return this.phoneNumber;
 	} 
	@Override
    public void setPhoneNumberIForm(String phoneNumberIForm){
 		this.phoneNumber=phoneNumberIForm;
    } 

	@Override
	@Transient
    public String getMobilePhoneIForm() {
 		return this.mobilePhone;
 	} 
	@Override
    public void setMobilePhoneIForm(String mobilePhoneIForm){
 		this.mobilePhone=mobilePhoneIForm;
    } 

	@Override
	@Transient
    public String getAddressIForm() {
 		return this.address;
 	} 
	@Override
    public void setAddressIForm(String addressIForm){
 		this.address=addressIForm;
    } 

	@Override
	@Transient
    public String getProvinceIForm() {
 		return this.province;
 	} 
	@Override
    public void setProvinceIForm(String provinceIForm){
 		this.province=provinceIForm;
    } 

	@Override
	@Transient
    public String getCapCodeIForm() {
 		return this.capCode;
 	} 
	@Override
    public void setCapCodeIForm(String capCodeIForm){
 		this.capCode=capCodeIForm;
    } 

	@Override
	@Transient
    public String getFlagResidenceIForm() {
 		return this.flagResidence;
 	} 
	@Override
    public void setFlagResidenceIForm(String flagResidenceIForm){
 		this.flagResidence=flagResidenceIForm;
    } 

//	@Override
//	@Transient
//    public String getEmailIForm() {
// 		return this.email;
// 	} 
//	@Override
//    public void setEmailIForm(String emailIForm){
// 		this.email=emailIForm;
//    } 
    
	@Override
	@Transient
    public String getNumeroCivicoIForm() {
 		return this.numeroCivico;
 	} 
	@Override
    public void setNumeroCivicoIForm(String numeroCivicoIForm){
 		this.numeroCivico=numeroCivicoIForm;
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
         IndirizzipostaliForm _form =(IndirizzipostaliForm) bf.getBean("IndirizzipostaliForm");

          _form.setNativePojo(this);

         IndirizzipostaliCommon _pojo = this;

         _form.setPostalCodeIForm(_pojo.getPostalCodeIForm());
         _form.setCapCodeIForm(_pojo.getCapCodeIForm());
         _form.setPostBoxIForm(_pojo.getPostBoxIForm());
         _form.setFiscalCodeIForm(_pojo.getFiscalCodeIForm());
         _form.setCityIForm(_pojo.getCityIForm());
         _form.setFaxNumberIForm(_pojo.getFaxNumberIForm());
         _form.setFlagResidenceIForm(_pojo.getFlagResidenceIForm());
         _form.setAddressIForm(_pojo.getAddressIForm());
         _form.setCountryIForm(_pojo.getCountryIForm());
         _form.setVatCodeIForm(_pojo.getVatCodeIForm());
         _form.setProvinceIForm(_pojo.getProvinceIForm());
         _form.setPhoneNumberIForm(_pojo.getPhoneNumberIForm());
         _form.setMobilePhoneIForm(_pojo.getMobilePhoneIForm());
//         _form.setEmailIForm(_pojo.getEmailIForm());
         _form.setNumeroCivicoIForm(_pojo.getNumeroCivicoIForm());

         return _form;
	  }
    
    @Override
    @Transient
	 public DTO incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }
    
    
    @Override
    @Transient
	public String getConfirmEmailIForm() {
		return this.confirmEmailIForm;
	}
	@Override
	public void setConfirmEmailIForm(String confirmEmailIForm) {
		this.confirmEmailIForm=confirmEmailIForm;
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
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
		Indirizzipostali other = (Indirizzipostali) obj;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Indirizzipostali [postalCode=" + postalCode + ", capCode="
				+ capCode + ", postBox=" + postBox + ", fiscalCode="
				+ fiscalCode + ", city=" + city + ", faxNumber=" + faxNumber
				+ ", flagResidence=" + flagResidence + ", address=" + address
				+ ", country=" + country + ", vatCode=" + vatCode
				+ ", province=" + province + ", phoneNumber=" + phoneNumber
				+ ", mobilePhone=" + mobilePhone //+ ", email=" + email
				+ ", numeroCivico=" + numeroCivico + "]";
	}
	
	
	private Integer version;
	
	@Version
	@Column(name="VERSION")
	public Integer getVersion() {
		
		return version;
		
	}
	
	public void setVersion(Integer version) {
		
		this.version = version;
		
	}	


}
