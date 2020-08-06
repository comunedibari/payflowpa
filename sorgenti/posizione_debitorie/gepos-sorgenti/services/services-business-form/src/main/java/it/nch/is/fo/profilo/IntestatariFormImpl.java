/**
 *
 * Classe generata
 *
 */

package it.nch.is.fo.profilo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.utility.enumeration.Categoria;

public class IntestatariFormImpl extends BaseForm implements IntestatariForm {

	private String corporateIForm;
	private String abiIForm;
	private String abiaccentratoreIForm;
	private String cabIForm;
	private String chiavessbIForm;
	private String codicepostelIForm;
	private String codicesoftwareIForm;
	private String denominazioneIForm;
	private String gruppoIForm;
	private String laplIForm;
	private String ragionesocialeIForm;
	private String raplIForm;
	private String rczIForm;
	private String siaIForm;
	private String siaCbiIForm;
	private String statoIForm;
	private String tipointestatarioIForm;
	private String tiposicurezzaIForm;
	private String uffpostaleIForm;
	private String codiceCucIForm;
	private String nonresidenteIForm;
	private String categoriaIForm;
	private String flagComunicazioniIForm;
	private String flagEnrollmentAvvisaturaIForm;
	/**
	 * Contenitore temporaneo per l'enum che rappresenta la Categoria.
	 */
	private Categoria categoriaEnumIForm;
	private String sottoCategoriaIForm;
	private IndirizzipostaliCommon indirizzipostaliobjIForm;
	private EntiCommon entiobjIForm;
	private String importoMaxFlussoIForm;
	private String nameIForm;
	private String surnameIForm;
	private String operatorCodeIForm;

	// TYPE SOLO PER LA FORM
	private String adminPasswordIForm;
	private Collection operatoriIForm;
	private Collection intestatariOperatoriIForm;


	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;


	public IntestatariFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		indirizzipostaliobjIForm=(IndirizzipostaliCommon)bf.getBean("IndirizzipostaliForm");
		entiobjIForm=(EntiCommon)bf.getBean("EntiForm");
	}


	public void setNativePojo(Object nativePojo) {
		IntestatariCommon intestatariCommonNativePojo = (IntestatariCommon)nativePojo;
		if (intestatariCommonNativePojo.getIndirizzipostaliobjIForm() != null)
			((BaseForm)this.indirizzipostaliobjIForm).setNativePojo(intestatariCommonNativePojo.getIndirizzipostaliobjIForm());
		if (intestatariCommonNativePojo.getEntiobjIForm()!=null)
			((BaseForm)this.entiobjIForm).setNativePojo(intestatariCommonNativePojo.getEntiobjIForm());
		this.nativePojo = nativePojo;
	}


	public String getCorporateIForm() {
		return this.corporateIForm;
	}

	public void setCorporateIForm(String corporateIForm){
		this.corporateIForm=corporateIForm;
	}

	public String getAbiIForm() {
		return this.abiIForm;
	}

	public void setAbiIForm(String abiIForm){
		this.abiIForm=abiIForm;
	}

	public String getAbiaccentratoreIForm() {
		return this.abiaccentratoreIForm;
	}

	public void setAbiaccentratoreIForm(String abiaccentratoreIForm){
		this.abiaccentratoreIForm=abiaccentratoreIForm;
	}

	public String getCabIForm() {
		return this.cabIForm;
	}

	public void setCabIForm(String cabIForm){
		this.cabIForm=cabIForm;
	}

	public String getChiavessbIForm() {
		return this.chiavessbIForm;
	}

	public void setChiavessbIForm(String chiavessbIForm){
		this.chiavessbIForm=chiavessbIForm;
	}

	public String getCodicepostelIForm() {
		return this.codicepostelIForm;
	}

	public void setCodicepostelIForm(String codicepostelIForm){
		this.codicepostelIForm=codicepostelIForm;
	}

	public String getCodicesoftwareIForm() {
		return this.codicesoftwareIForm;
	}

	public void setCodicesoftwareIForm(String codicesoftwareIForm){
		this.codicesoftwareIForm=codicesoftwareIForm;
	}

	public String getDenominazioneIForm() {
		return this.denominazioneIForm;
	}

	public void setDenominazioneIForm(String denominazioneIForm){
		this.denominazioneIForm=denominazioneIForm;
	}

	public String getGruppoIForm() {
		return this.gruppoIForm;
	}

	public void setGruppoIForm(String gruppoIForm){
		this.gruppoIForm=gruppoIForm;
	}

	public String getLaplIForm() {
		return this.laplIForm;
	}

	public void setLaplIForm(String laplIForm){
		this.laplIForm=laplIForm;
	}

	public String getRagionesocialeIForm() {
		return this.ragionesocialeIForm;
	}

	public void setRagionesocialeIForm(String ragionesocialeIForm){
		this.ragionesocialeIForm=ragionesocialeIForm;
	}

	public String getRaplIForm() {
		return this.raplIForm;
	}

	public void setRaplIForm(String raplIForm){
		this.raplIForm=raplIForm;
	}

	public String getRczIForm() {
		return this.rczIForm;
	}

	public void setRczIForm(String rczIForm){
		this.rczIForm=rczIForm;
	}

	public String getSiaIForm() {
		return this.siaIForm;
	}

	public void setSiaIForm(String siaIForm){
		this.siaIForm=siaIForm;
	}

	public String getStatoIForm() {
		return this.statoIForm;
	}

	public void setStatoIForm(String statoIForm){
		this.statoIForm=statoIForm;
	}

	public String getTipointestatarioIForm() {
		return this.tipointestatarioIForm;
	}

	public void setTipointestatarioIForm(String tipointestatarioIForm){
		this.tipointestatarioIForm=tipointestatarioIForm;
	}

	public String getTiposicurezzaIForm() {
		return this.tiposicurezzaIForm;
	}

	public void setTiposicurezzaIForm(String tiposicurezzaIForm){
		this.tiposicurezzaIForm=tiposicurezzaIForm;
	}

	public String getUffpostaleIForm() {
		return this.uffpostaleIForm;
	}

	public void setUffpostaleIForm(String uffpostaleIForm){
		this.uffpostaleIForm=uffpostaleIForm;
	}

	public String getCodiceCucIForm() {
		return this.codiceCucIForm;
	}

	public void setCodiceCucIForm(String codiceCucIForm){
		this.codiceCucIForm=codiceCucIForm;
	}

	public String getNonresidenteIForm() {
		return this.nonresidenteIForm;
	}

	public void setNonresidenteIForm(String nonresidenteIForm){
		this.nonresidenteIForm=nonresidenteIForm;
	}

	public IndirizzipostaliCommon getIndirizzipostaliobjIForm() {
		return this.indirizzipostaliobjIForm;
	}

	public void setIndirizzipostaliobjIForm(IndirizzipostaliCommon indirizzipostaliobjIForm){
		this.indirizzipostaliobjIForm=indirizzipostaliobjIForm;
	}

	public EntiCommon getEntiobjIForm() {
		return this.entiobjIForm;
	}

	public void setEntiobjIForm(EntiCommon entiobjIForm){
		this.entiobjIForm=entiobjIForm;
	}

	public String getCategoriaIForm() {
		return categoriaIForm;
	}



	public void setCategoriaIForm(String categoriaIForm) {
		this.categoriaIForm = categoriaIForm;
		if(categoriaIForm != null && categoriaIForm.length()>0)
			this.categoriaEnumIForm = Categoria.valueOf(categoriaIForm);
	}



	public String getSottoCategoriaIForm() {
		return sottoCategoriaIForm;
	}



	public void setSottoCategoriaIForm(String sottoCategoriaIForm) {
		this.sottoCategoriaIForm = sottoCategoriaIForm;
	}

	public String getNameIForm() {
		return nameIForm;
	}



	public void setNameIForm(String nameIForm) {
		this.nameIForm = nameIForm;
	}



	public String getSurnameIForm() {
		return surnameIForm;
	}



	public void setSurnameIForm(String surnameIForm) {
		this.surnameIForm = surnameIForm;
	}

	/**
	 *
	 * METODI SOLO FORM
	 *
	 */
	 public String getAdminPasswordIForm() {
		return this.adminPasswordIForm;
	}

	public void setAdminPasswordIForm(String adminPasswordIForm){
		this.adminPasswordIForm=adminPasswordIForm;
	}



	public void show() {
		System.out.println("Class="+this.getClass());
		System.out.println("corporateIForm="+this.getCorporateIForm());
		System.out.println("abiIForm="+this.getAbiIForm());
		System.out.println("abiaccentratoreIForm="+this.getAbiaccentratoreIForm());
		System.out.println("cabIForm="+this.getCabIForm());
		System.out.println("chiavessbIForm="+this.getChiavessbIForm());
		System.out.println("codicepostelIForm="+this.getCodicepostelIForm());
		System.out.println("codicesoftwareIForm="+this.getCodicesoftwareIForm());
		System.out.println("denominazioneIForm="+this.getDenominazioneIForm());
		System.out.println("gruppoIForm="+this.getGruppoIForm());
		System.out.println("laplIForm="+this.getLaplIForm());
		System.out.println("ragionesocialeIForm="+this.getRagionesocialeIForm());
		System.out.println("raplIForm="+this.getRaplIForm());
		System.out.println("rczIForm="+this.getRczIForm());
		System.out.println("siaIForm="+this.getSiaIForm());
		System.out.println("siaCbiIForm="+this.getSiaCbiIForm());
		System.out.println("statoIForm="+this.getStatoIForm());
		System.out.println("tipointestatarioIForm="+this.getTipointestatarioIForm());
		System.out.println("tiposicurezzaIForm="+this.getTiposicurezzaIForm());
		System.out.println("uffpostaleIForm="+this.getUffpostaleIForm());
		System.out.println("codiceCucIForm="+this.getCodiceCucIForm());
		System.out.println("nonresidenteIForm="+this.getNonresidenteIForm());
		System.out.println("indirizzipostaliobjIForm="+this.getIndirizzipostaliobjIForm());
		System.out.println("categoriaIForm="+this.getCategoriaIForm());
		System.out.println("flagComunicazioniIForm="+this.getFlagComunicazioniIForm());
		System.out.println("sottoCategoriaIForm="+this.getSottoCategoriaIForm());
		System.out.println("entiObjIForm="+this.getEntiobjIForm());
		System.out.println("adminPasswordIForm="+this.getAdminPasswordIForm());
		System.out.println("nameIForm="+this.getNameIForm());
		System.out.println("surnameIForm="+this.getSurnameIForm());
	}


	/**
	 *
	 *COPY/MERGE Method Form Vs Pojo
	 *
	 **/

	public CommonBusinessObject copy(){

		IntestatariForm _form = this;
		IntestatariCommon _pojo=(IntestatariCommon)this.nativePojo;

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
			_pojo=(IntestatariCommon) bf.getBean("Intestatari");

		}


		_pojo.setCorporateIForm(_form.getCorporateIForm());
		_pojo.setAbiIForm(_form.getAbiIForm());
		_pojo.setAbiaccentratoreIForm(_form.getAbiaccentratoreIForm());
		_pojo.setCabIForm(_form.getCabIForm());
		_pojo.setChiavessbIForm(_form.getChiavessbIForm());
		_pojo.setCodicepostelIForm(_form.getCodicepostelIForm());
		_pojo.setCodicesoftwareIForm(_form.getCodicesoftwareIForm());
		_pojo.setDenominazioneIForm(_form.getDenominazioneIForm());
		_pojo.setGruppoIForm(_form.getGruppoIForm());
		_pojo.setLaplIForm(_form.getLaplIForm());
		if (_form.getRagionesocialeIForm()!=null && _form.getRagionesocialeIForm().length()>0){
			_pojo.setRagionesocialeIForm(_form.getRagionesocialeIForm());
		} else {
			if (_form.getNameIForm()!= null && _form.getNameIForm().length()>0 || _form.getSurnameIForm()!= null && _form.getSurnameIForm().length()>0){
				String ragioneSociale = ((_form.getNameIForm()!= null && _form.getNameIForm().length()>0)?(_form.getNameIForm()+" "):"")+((_form.getSurnameIForm()!= null && _form.getSurnameIForm().length()>0)?_form.getSurnameIForm():"");
				_pojo.setRagionesocialeIForm(ragioneSociale);
			}
		}
		_pojo.setRaplIForm(_form.getRaplIForm());
		_pojo.setRczIForm(_form.getRczIForm());
		_pojo.setSiaIForm(_form.getSiaIForm());
		_pojo.setSiaCbiIForm(_form.getSiaCbiIForm());
		_pojo.setStatoIForm(_form.getStatoIForm());
		_pojo.setTipointestatarioIForm(_form.getTipointestatarioIForm());
		_pojo.setTiposicurezzaIForm(_form.getTiposicurezzaIForm());
		_pojo.setUffpostaleIForm(_form.getUffpostaleIForm());
		_pojo.setCodiceCucIForm(_form.getCodiceCucIForm());
		_pojo.setNonresidenteIForm(_form.getNonresidenteIForm());
		_pojo.setCategoriaIForm(_form.getCategoriaIForm());

		_pojo.setSottoCategoriaIForm(_form.getSottoCategoriaIForm());

		// Oggetto innestato copio in modo ricorso relativamente
		// alla definizione delle dipendenze del properties Common
		if (_form.getIndirizzipostaliobjIForm()!=null)
			_pojo.setIndirizzipostaliobjIForm((IndirizzipostaliCommon)_form.getIndirizzipostaliobjIForm().copy());

		if (_form.getEntiobjIForm()!=null)
			_pojo.setEntiobjIForm((EntiCommon)_form.getEntiobjIForm().copy());

		if (_form.getOperatoriIForm()!=null){
			Set destOperatoriIForm = new HashSet();
			Iterator srcOperatoriIForm = _form.getOperatoriIForm().iterator();
			while (srcOperatoriIForm.hasNext()){
				CommonBusinessObject _innerForm =  (CommonBusinessObject)srcOperatoriIForm.next();
				destOperatoriIForm.add(_innerForm.copy());
			}
			_pojo.setOperatoriIForm(destOperatoriIForm);
		}  		

		_pojo.setFlagComunicazioniIForm(_form.getFlagComunicazioniIForm());
		_pojo.setFlagEnrollmentAvvisaturaIForm(_form.getFlagEnrollmentAvvisaturaIForm());
		return _pojo;
	}

	public DTO incapsulateBO() {
		return new DTOImpl(this);
	}


	// Metodo di RESET

	public void reset() {
		this.corporateIForm="";
		this.abiIForm="";
		this.abiaccentratoreIForm="";
		this.cabIForm="";
		this.chiavessbIForm="";
		this.codicepostelIForm="";
		this.codicesoftwareIForm="";
		this.denominazioneIForm="";
		this.gruppoIForm="";
		this.laplIForm="";
		this.ragionesocialeIForm="";
		this.raplIForm="";
		this.rczIForm="";
		this.siaIForm="";
		this.siaCbiIForm="";
		this.statoIForm="";
		this.tipointestatarioIForm="";
		this.tiposicurezzaIForm="";
		this.uffpostaleIForm="";
		this.codiceCucIForm="";
		this.nonresidenteIForm="";
		this.categoriaIForm="";
		this.sottoCategoriaIForm="";
		this.nameIForm="";
		this.surnameIForm="";

		// DATI FORM NON COMMON

		this.adminPasswordIForm="";
	}


	public String getImportoMaxFlussoIForm() {
		return this.importoMaxFlussoIForm;
	}

	public void setImportoMaxFlussoIForm(String importoMaxFlussoIForm) {
		this.importoMaxFlussoIForm = importoMaxFlussoIForm;
	}



	/**
	 * Restituisce l'enum che rappresenta la categoria.
	 *
	 * @return l'enum che rappresenta la categoria.
	 */
	public Categoria getCategoriaEnumIForm() {
		return categoriaEnumIForm;
	}



	/**
	 * Imposta l'enum che rappresenta la categoria.
	 *
	 * @param categoriaEnumIForm l'enum che rappresenta la categoria.
	 */
	public void setCategoriaEnumIForm(Categoria categoriaEnumIForm) {
		this.categoriaEnumIForm = categoriaEnumIForm;
	}



	@Override
	public Collection getOperatoriIForm() {
		if (this.intestatariOperatoriIForm == null)
			return null;

		Set<OperatoriCommon> opers = new LinkedHashSet<OperatoriCommon>();
		if (this.intestatariOperatoriIForm != null){
			Iterator iter = this.intestatariOperatoriIForm.iterator();
			while (iter.hasNext()){
				IntestatarioperatoriCommon ioc = (IntestatarioperatoriCommon)iter.next();
				opers.add(ioc.getOperatoriobjIForm());
			}
		}

		return opers;

	}
	@Override
	public void setOperatoriIForm(Set operatoriIForm) {
		this.operatoriIForm = operatoriIForm;
	}



	public void setOperatorCodeIForm(String operatorCodeIForm) {
		this.operatorCodeIForm = operatorCodeIForm;
	}



	public String getOperatorCodeIForm() {
		return operatorCodeIForm;
	}



	@Override
	public Collection getIntestatariOperatoriIForm() {
		return this.intestatariOperatoriIForm;
	}



	@Override
	public void setIntestatariOperatoriIForm(Set intestatariOperatoriIForm) {
		this.intestatariOperatoriIForm = intestatariOperatoriIForm;

	}



	public String getFlagComunicazioniIForm() {
		return flagComunicazioniIForm;
	}

	public void setFlagComunicazioniIForm(String flagComunicazioniIForm) {
		this.flagComunicazioniIForm = flagComunicazioniIForm;
	}
	
	public String getFlagEnrollmentAvvisaturaIForm() {
		return flagEnrollmentAvvisaturaIForm;
	}
	public void setFlagEnrollmentAvvisaturaIForm(String flagEnrollmentAvvisaturaIForm) {
		this.flagEnrollmentAvvisaturaIForm = flagEnrollmentAvvisaturaIForm;
	}


	public String getSiaCbiIForm() {
		return siaCbiIForm;
	}


	public void setSiaCbiIForm(String siaCbiIForm) {
		this.siaCbiIForm = siaCbiIForm;
	}
	
	


}
