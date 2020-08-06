

package it.tasgroup.idp.anagrafica;


import it.tasgroup.idp.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@NamedQueries({ 
	@NamedQuery(name = "IndirizzoByCodFiscale", query = "select indirizzipostali "
			+ "from Indirizzipostali Indirizzipostali where Indirizzipostali.fiscalCode = :codiceFiscale ")
})

@Entity
@Table(name="INDIRIZZIPOSTALI")
public class Indirizzipostali extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
    private String email;
    private String numeroCivico;
    private Integer version;
	
    
    
    
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
    
     @Column(name="EMAIL")
    public String getEmail() {
 		return this.email;
 	} 
    public void setEmail(String email){
 		this.email=email;
    } 

    @Column(name="NUMEROCIVICO")
    public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
    
	@Version
	@Column(name="VERSION")
	public Integer getVersion() {
		
		return version;
		
	}
	
	public void setVersion(Integer version) {
		
		this.version = version;
		
	}	
	
    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
	
	
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
		return "Indirizzipostali [postalCode=" + postalCode + ", \ncapCode="
				+ capCode + ", \npostBox=" + postBox + ", \nfiscalCode="
				+ fiscalCode + ", \ncity=" + city + ", \nfaxNumber=" + faxNumber
				+ ", \nflagResidence=" + flagResidence + ", \naddress=" + address
				+ ", \ncountry=" + country + ", \nvatCode=" + vatCode
				+ ", \nprovince=" + province + ", \nphoneNumber=" + phoneNumber
				+ ", \nmobilePhone=" + mobilePhone //+ ", email=" + email
				+ ", \nnumeroCivico=" + numeroCivico + "]";
	}
	
	
	
	


}
