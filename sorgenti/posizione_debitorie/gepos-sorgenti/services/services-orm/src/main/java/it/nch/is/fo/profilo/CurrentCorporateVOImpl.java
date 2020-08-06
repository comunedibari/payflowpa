/**
*
* Classe generata
*
*/

package it.nch.is.fo.profilo;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.is.fo.profilo.CurrentCorporateVOForm;
import it.nch.fwk.fo.dto.*;
import it.nch.fwk.fo.dto.business.PojoImpl;

/**
 * Pojo contenente le informazioni anagrafiche di un intestatario, cioè informazioni provenienti
 * dal join della tabella degli intestatari con la tabella degli indirizzi postali.
 *
 * @author TODO
 *
 */
public class CurrentCorporateVOImpl extends PojoImpl implements CurrentCorporateVOCommon, CurrentCorporateVOPojo {

    private String corporate;
    private String name;
    private String siaCode;
    private String siaCbiCode;
    private String abiCode;
    private String cabCode;
    private String raplCode;
    private String laplCode;
    private String tipointestatario;
    private String fiscalCode;
    private String partitaIva;
    private String address;
    private String city;
    private String abiaccentratore;
    private String capCode;
    private String province;
    private String denominazione;
    private String flagResidence;
    private String categoria;
    private String sottoCategoria;
    private Double importoMaxFlusso;
	
	// Solo nel caso di operatore ente
	private String idEnte;
	private String cdEnte;

    private transient BeanFactoryLocator bfl;
    private transient BeanFactoryReference bfr;
    private transient BeanFactory bf;
	


	public CurrentCorporateVOImpl(String corporate,String name,String siaCode,String siaCbiCode,String abiCode,String cabCode,String raplCode,String laplCode,String tipointestatario,String fiscalCode,String partitaIva,String address,String city,String abiaccentratore,String capCode,String province,String denominazione,String flagResidence, String categoria, String sottoCategoria){
		super();
		this.corporate=corporate;
		this.name=name;
		this.siaCode=siaCode;
		this.siaCbiCode=siaCbiCode;
		this.abiCode=abiCode;
		this.cabCode=cabCode;
		this.raplCode=raplCode;
		this.laplCode=laplCode;
		this.tipointestatario=tipointestatario;
		this.fiscalCode=fiscalCode;
		this.partitaIva=partitaIva;
		this.address=address;
		this.city=city;
		this.abiaccentratore=abiaccentratore;
		this.capCode=capCode;
		this.province=province;
		this.denominazione=denominazione;
		this.flagResidence=flagResidence;
		this.categoria = categoria;
		this.sottoCategoria = sottoCategoria;
	}

	public CurrentCorporateVOImpl( ){

	}

    public String getCorporate() {
 		return this.corporate;
 	}

    public void setCorporate(String corporate){
 		this.corporate=corporate;
    }

    public String getName() {
 		return this.name;
 	}

    public void setName(String name){
 		this.name=name;
    }

    public String getSiaCode() {
 		return this.siaCode;
 	}

    public void setSiaCode(String siaCode){
 		this.siaCode=siaCode;
    }

    public String getAbiCode() {
 		return this.abiCode;
 	}

    public void setAbiCode(String abiCode){
 		this.abiCode=abiCode;
    }

    public String getCabCode() {
 		return this.cabCode;
 	}

    public void setCabCode(String cabCode){
 		this.cabCode=cabCode;
    }

    public String getRaplCode() {
 		return this.raplCode;
 	}

    public void setRaplCode(String raplCode){
 		this.raplCode=raplCode;
    }

    public String getLaplCode() {
 		return this.laplCode;
 	}

    public void setLaplCode(String laplCode){
 		this.laplCode=laplCode;
    }

    public String getTipointestatario() {
 		return this.tipointestatario;
 	}

    public void setTipointestatario(String tipointestatario){
 		this.tipointestatario=tipointestatario;
    }

    public String getFiscalCode() {
 		return this.fiscalCode;
 	}

    public void setFiscalCode(String fiscalCode){
 		this.fiscalCode=fiscalCode;
    }

    public String getPartitaIva() {
 		return this.partitaIva;
 	}

    public void setPartitaIva(String partitaIva){
 		this.partitaIva=partitaIva;
    }

    public String getAddress() {
 		return this.address;
 	}

    public void setAddress(String address){
 		this.address=address;
    }

    public String getCity() {
 		return this.city;
 	}

    public void setCity(String city){
 		this.city=city;
    }

    public String getAbiaccentratore() {
 		return this.abiaccentratore;
 	}

    public void setAbiaccentratore(String abiaccentratore){
 		this.abiaccentratore=abiaccentratore;
    }

    public String getCapCode() {
 		return this.capCode;
 	}

    public void setCapCode(String capCode){
 		this.capCode=capCode;
    }

    public String getProvince() {
 		return this.province;
 	}

    public void setProvince(String province){
 		this.province=province;
    }

    public String getDenominazione() {
 		return this.denominazione;
 	}

    public void setDenominazione(String denominazione){
 		this.denominazione=denominazione;
    }

    public String getFlagResidence() {
 		return this.flagResidence;
 	}

    public void setFlagResidence(String flagResidence){
 		this.flagResidence=flagResidence;
    }

    public String getCategoria(){
 		return this.categoria;
    }

    public void setCategoria(String categoria){
 		this.categoria=categoria;
    }

    public String getSottoCategoria(){
 		return this.sottoCategoria;
    }

    public void setSottoCategoria(String sottoCategoria){
 		this.sottoCategoria=sottoCategoria;
    }




    public void show() {
       System.out.println("Class="+this.getClass());
       System.out.println("corporate="+this.getCorporate());
       System.out.println("name="+this.getName());
       System.out.println("siaCode="+this.getSiaCode());
       System.out.println("siaCbiCode="+this.getSiaCbiCode());
       System.out.println("abiCode="+this.getAbiCode());
       System.out.println("cabCode="+this.getCabCode());
       System.out.println("raplCode="+this.getRaplCode());
       System.out.println("laplCode="+this.getLaplCode());
       System.out.println("tipointestatario="+this.getTipointestatario());
       System.out.println("fiscalCode="+this.getFiscalCode());
       System.out.println("partitaIva="+this.getPartitaIva());
       System.out.println("address="+this.getAddress());
       System.out.println("city="+this.getCity());
       System.out.println("abiaccentratore="+this.getAbiaccentratore());
       System.out.println("capCode="+this.getCapCode());
       System.out.println("province="+this.getProvince());
       System.out.println("denominazione="+this.getDenominazione());
       System.out.println("flagResidence="+this.getFlagResidence());
       System.out.println("categoria="+this.getCategoria());
       System.out.println("sottoCategoria="+this.getSottoCategoria());
    }


    /**
     *
     * Method Per gestione Tipi non nativi
     *
     **/
    public String getCabCodeIForm() {
 		return this.cabCode;
 	}

    public void setCabCodeIForm(String cabCodeIForm){
 		this.cabCode=cabCodeIForm;
    }

    public String getLaplCodeIForm() {
 		return this.laplCode;
 	}

    public void setLaplCodeIForm(String laplCodeIForm){
 		this.laplCode=laplCodeIForm;
    }

    public String getTipointestatarioIForm() {
 		return this.tipointestatario;
 	}

    public void setTipointestatarioIForm(String tipointestatarioIForm){
 		this.tipointestatario=tipointestatarioIForm;
    }

    public String getFiscalCodeIForm() {
 		return this.fiscalCode;
 	}

    public void setFiscalCodeIForm(String fiscalCodeIForm){
 		this.fiscalCode=fiscalCodeIForm;
    }

    public String getSiaCodeIForm() {
 		return this.siaCode;
 	}

    public void setSiaCodeIForm(String siaCodeIForm){
 		this.siaCode=siaCodeIForm;
    }
    
    public String getSiaCbiCodeIForm() {
 		return this.siaCbiCode;
 	}

    public void setSiaCbiCodeIForm(String siaCbiCodeIForm){
 		this.siaCbiCode=siaCbiCodeIForm;
    }

    public String getAbiaccentratoreIForm() {
 		return this.abiaccentratore;
 	}

    public void setAbiaccentratoreIForm(String abiaccentratoreIForm){
 		this.abiaccentratore=abiaccentratoreIForm;
    }

    public String getDenominazioneIForm() {
 		return this.denominazione;
 	}

    public void setDenominazioneIForm(String denominazioneIForm){
 		this.denominazione=denominazioneIForm;
    }

    public String getCityIForm() {
 		return this.city;
 	}

    public void setCityIForm(String cityIForm){
 		this.city=cityIForm;
    }

    public String getAbiCodeIForm() {
 		return this.abiCode;
 	}

    public void setAbiCodeIForm(String abiCodeIForm){
 		this.abiCode=abiCodeIForm;
    }

    public String getCorporateIForm() {
 		return this.corporate;
 	}

    public void setCorporateIForm(String corporateIForm){
 		this.corporate=corporateIForm;
    }

    public String getAddressIForm() {
 		return this.address;
 	}

    public void setAddressIForm(String addressIForm){
 		this.address=addressIForm;
    }

    public String getNameIForm() {
 		return this.name;
 	}

    public void setNameIForm(String nameIForm){
 		this.name=nameIForm;
    }

    public String getProvinceIForm() {
 		return this.province;
 	}

    public void setProvinceIForm(String provinceIForm){
 		this.province=provinceIForm;
    }

    public String getRaplCodeIForm() {
 		return this.raplCode;
 	}

    public void setRaplCodeIForm(String raplCodeIForm){
 		this.raplCode=raplCodeIForm;
    }

    public String getCapCodeIForm() {
 		return this.capCode;
 	}

    public void setCapCodeIForm(String capCodeIForm){
 		this.capCode=capCodeIForm;
    }

    public String getFlagResidenceIForm() {
 		return this.flagResidence;
 	}

    public void setFlagResidenceIForm(String flagResidenceIForm){
 		this.flagResidence=flagResidenceIForm;
    }

    /**
     *
     *COPY Method Pojo Vs Form
     *
     **/

      public CommonBusinessObject copy(){


         bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
         bfr = bfl.useBeanFactory("it.nch.orm");
         bf=bfr.getFactory();
         CurrentCorporateVOForm _form =(CurrentCorporateVOForm) bf.getBean("CurrentCorporateVOForm");

          _form.setNativePojo(this);

         CurrentCorporateVOCommon _pojo = this;

         _form.setCorporateIForm(_pojo.getCorporateIForm());
         _form.setNameIForm(_pojo.getNameIForm());
         _form.setSiaCodeIForm(_pojo.getSiaCodeIForm());
         _form.setSiaCbiCodeIForm(_pojo.getSiaCbiCodeIForm());
         _form.setAbiCodeIForm(_pojo.getAbiCodeIForm());
         _form.setCabCodeIForm(_pojo.getCabCodeIForm());
         _form.setRaplCodeIForm(_pojo.getRaplCodeIForm());
         _form.setLaplCodeIForm(_pojo.getLaplCodeIForm());
         _form.setTipointestatarioIForm(_pojo.getTipointestatarioIForm());
         _form.setFiscalCodeIForm(_pojo.getFiscalCodeIForm());
         _form.setAddressIForm(_pojo.getAddressIForm());
         _form.setCityIForm(_pojo.getCityIForm());
         _form.setAbiaccentratoreIForm(_pojo.getAbiaccentratoreIForm());
         _form.setCapCodeIForm(_pojo.getCapCodeIForm());
         _form.setProvinceIForm(_pojo.getProvinceIForm());
         _form.setDenominazioneIForm(_pojo.getDenominazioneIForm());
         _form.setFlagResidenceIForm(_pojo.getFlagResidenceIForm());
         _form.setImportoMaxFlussoIForm(_pojo.getImportoMaxFlussoIForm());

         return _form;
	  }

	  public DTO<?, CurrentCorporateVOImpl, ?> incapsulateBO() {
	  	return new DTOImpl((CommonBusinessObject)this);
	  }

    /**
     *
     * Metodo Clone richiesto!!!
     *
     **/
      public Object clone() {



         CurrentCorporateVOImpl _pojoCurrent = this;
         CurrentCorporateVOImpl _pojo = new CurrentCorporateVOImpl();

         _pojo.setCorporate(_pojoCurrent.getCorporate());
         _pojo.setName(_pojoCurrent.getName());
         _pojo.setSiaCode(_pojoCurrent.getSiaCode());
         _pojo.setSiaCbiCode(_pojoCurrent.getSiaCbiCode());
         _pojo.setAbiCode(_pojoCurrent.getAbiCode());
         _pojo.setCabCode(_pojoCurrent.getCabCode());
         _pojo.setRaplCode(_pojoCurrent.getRaplCode());
         _pojo.setLaplCode(_pojoCurrent.getLaplCode());
         _pojo.setTipointestatario(_pojoCurrent.getTipointestatario());
         _pojo.setFiscalCode(_pojoCurrent.getFiscalCode());
         _pojo.setPartitaIva(_pojoCurrent.getPartitaIva());
         _pojo.setAddress(_pojoCurrent.getAddress());
         _pojo.setCity(_pojoCurrent.getCity());
         _pojo.setAbiaccentratore(_pojoCurrent.getAbiaccentratore());
         _pojo.setCapCode(_pojoCurrent.getCapCode());
         _pojo.setProvince(_pojoCurrent.getProvince());
         _pojo.setDenominazione(_pojoCurrent.getDenominazione());
         _pojo.setFlagResidence(_pojoCurrent.getFlagResidence());
         _pojo.setCategoria(_pojoCurrent.getCategoria());
         _pojo.setSottoCategoria(_pojoCurrent.getSottoCategoria());
         _pojo.setId(_pojoCurrent.getId());
         _pojo.setImportoMaxFlusso(_pojoCurrent.getImportoMaxFlusso());
         _pojo.setCdEnte(_pojoCurrent.getCdEnte());
         _pojo.setIdEnte(_pojoCurrent.getIdEnte());

         return _pojo;
	  }

	public Double getImportoMaxFlusso() {
		return this.importoMaxFlusso;
	}

	public void setImportoMaxFlusso(Double importoMaxFlusso) {
		this.importoMaxFlusso = importoMaxFlusso;
	}

	@Override
	public String getImportoMaxFlussoIForm() {
		return Traslator.doubleToString(this.importoMaxFlusso);
	}

	@Override
	public void setImportoMaxFlussoIForm(String importoMaxFlussoIForm) {
		this.importoMaxFlusso = Traslator.stringToDouble(importoMaxFlussoIForm);
	}

	//TODO: da rimuovere come la dipendenza da Pojo
	
	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getCdEnte() {
		return cdEnte;
	}

	public void setCdEnte(String cdEnte) {
		this.cdEnte = cdEnte;
	}

	public String getSiaCbiCode() {
 		return this.siaCbiCode;
 	}

    public void setSiaCbiCode(String siaCbiCode){
 		this.siaCbiCode=siaCbiCode;
    }


}
