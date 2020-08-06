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
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * Estensione di BaseForm per contenere i dati di un operatore provenienti da
 * form di ricerca.
 * 
 * @author TODO
 * 
 */
public class OperatoriFormImpl extends BaseForm implements OperatoriForm {

	private String operatoreIForm;
	private String usernameIForm;
	private String corporateIForm;
	private String enteIForm;
	private String lockedIForm;
	private String mobileIForm;
	private String signerCodeIForm;
	private String descriptionIForm;
//	private String emailIForm;
	private String flagOperatorTypeIForm;
	private String flagTributiAmmessi;
	private String nameIForm;
	private String numFailedlogonIForm;
	private String passwordIForm;
	private String plainPasswordIForm;
	private String lastLogonIForm;
	private String expirationDateIForm;
	private String lockDateIForm;
	private String failedLogonDateIForm;
	private IntestatariCommon intestatariobjIForm;
	private Set<IntestatariCommon> intestatariIForm = new HashSet<IntestatariCommon>();
	private String codiceFiscaleIForm;
	
	private String tributiAmmessi;
	
	private Set<TributiOperatoriCommon> tributiAmmessiCollection = new HashSet<TributiOperatoriCommon>();
	private Set<TributiOperatoriCommon> tributiDisponibiliList = new HashSet<TributiOperatoriCommon>();
	
	private String surnameIForm;
	private String isEnte;
	private String flagCurrentOperator;
	private Set<IntestatarioperatoriCommon> intestatarioperatori = new HashSet<IntestatarioperatoriCommon>();
	
	

	// TYPE SOLO PER LA FORM
	private String dataUltimaConnessioneIForm;
	private String pageFrom;

	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;

	public OperatoriFormImpl() {

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		intestatariobjIForm = (IntestatariCommon) bf.getBean("IntestatariForm");
	}

	public String getIsEnte() {
		return isEnte;
	}

	public void setIsEnte(String isEnte) {
		this.isEnte = isEnte;
	}

	public void setNativePojo(Object nativePojo) {
		((BaseForm) this.intestatariobjIForm).setNativePojo(((OperatoriCommon) nativePojo).getIntestatariobjIForm());
		this.nativePojo = nativePojo;
	}

	public String getOperatoreIForm() {
		return this.operatoreIForm;
	}

	public void setOperatoreIForm(String operatoreIForm) {
		this.operatoreIForm = operatoreIForm;
	}

	public String getUsernameIForm() {
		return this.usernameIForm;
	}

	public void setUsernameIForm(String usernameIForm) {
		this.usernameIForm = usernameIForm;
	}

	public String getCorporateIForm() {
		return this.corporateIForm;
	}

	public void setCorporateIForm(String corporateIForm) {
		this.corporateIForm = corporateIForm;
	}

	public String getLockedIForm() {
		return this.lockedIForm;
	}

	public void setLockedIForm(String lockedIForm) {
		this.lockedIForm = lockedIForm;
	}

	public String getMobileIForm() {
		return this.mobileIForm;
	}

	public void setMobileIForm(String mobileIForm) {
		this.mobileIForm = mobileIForm;
	}

	public String getSignerCodeIForm() {
		return this.signerCodeIForm;
	}

	public void setSignerCodeIForm(String signerCodeIForm) {
		this.signerCodeIForm = signerCodeIForm;
	}

	public String getDescriptionIForm() {
		return this.descriptionIForm;
	}

	public void setDescriptionIForm(String descriptionIForm) {
		this.descriptionIForm = descriptionIForm;
	}

//	public String getEmailIForm() {
//		return this.emailIForm;
//	}
//
//	public void setEmailIForm(String emailIForm) {
//		this.emailIForm = emailIForm;
//	}
	
	@Override
	public String getFlagTributiAmmessi() {
		return this.flagTributiAmmessi;
	}
	
	@Override
	public void setFlagTributiAmmessi(String flagTributiAmmessi) {
		this.flagTributiAmmessi = flagTributiAmmessi;
	}

	@Override
	public String getFlagOperatorTypeIForm() {
		return this.flagOperatorTypeIForm;
	}
	
	@Override
	public void setFlagOperatorTypeIForm(String flagOperatorTypeIForm) {
		this.flagOperatorTypeIForm = flagOperatorTypeIForm;
	}

	public String getNameIForm() {
		return this.nameIForm;
	}

	public void setNameIForm(String nameIForm) {
		this.nameIForm = nameIForm;
	}

	public String getNumFailedlogonIForm() {
		return this.numFailedlogonIForm;
	}

	public void setNumFailedlogonIForm(String numFailedlogonIForm) {
		this.numFailedlogonIForm = numFailedlogonIForm;
	}

	public String getPasswordIForm() {
		return this.passwordIForm;
	}

	public void setPasswordIForm(String passwordIForm) {
		this.passwordIForm = passwordIForm;
	}

	public String getPlainPasswordIForm() {
		return this.plainPasswordIForm;
	}

	public void setPlainPasswordIForm(String plainPasswordIForm) {
		this.plainPasswordIForm = plainPasswordIForm;
	}

	public String getLastLogonIForm() {
		return this.lastLogonIForm;
	}

	public void setLastLogonIForm(String lastLogonIForm) {
		this.lastLogonIForm = lastLogonIForm;
	}

	public String getExpirationDateIForm() {
		return this.expirationDateIForm;
	}

	public void setExpirationDateIForm(String expirationDateIForm) {
		this.expirationDateIForm = expirationDateIForm;
	}

	public String getLockDateIForm() {
		return this.lockDateIForm;
	}

	public void setLockDateIForm(String lockDateIForm) {
		this.lockDateIForm = lockDateIForm;
	}

	public String getFailedLogonDateIForm() {
		return this.failedLogonDateIForm;
	}

	public void setFailedLogonDateIForm(String failedLogonDateIForm) {
		this.failedLogonDateIForm = failedLogonDateIForm;
	}

	public IntestatariCommon getIntestatariobjIForm() {
		return this.intestatariobjIForm;
	}

	public void setIntestatariobjIForm(IntestatariCommon intestatariobjIForm) {
		this.intestatariobjIForm = intestatariobjIForm;
	}

	@Override
	public String getSurnameIForm() {
		return this.surnameIForm;
	}

	@Override
	public void setSurnameIForm(String surnameIForm) {
		this.surnameIForm = surnameIForm;

	}

	/**
	 * 
	 * METODI SOLO FORM
	 * 
	 */
	public String getDataUltimaConnessioneIForm() {
		return this.dataUltimaConnessioneIForm;
	}

	public void setDataUltimaConnessioneIForm(String dataUltimaConnessioneIForm) {
		this.dataUltimaConnessioneIForm = dataUltimaConnessioneIForm;
	}

	@Override
	public Set<IntestatariCommon> getIntestatariIForm() {
		return intestatariIForm;
	}

	@Override
	public void setIntestatariIForm(Set<IntestatariCommon> intestatariIForm) {
		this.intestatariIForm = intestatariIForm;

	}

	@Override
	public String getCodiceFiscaleIForm() {
		return codiceFiscaleIForm;
	}

	@Override
	public void setCodiceFiscaleIForm(String codiceFiscaleIForm) {
		this.codiceFiscaleIForm = codiceFiscaleIForm;
	}

	@Override
	public Set<IntestatarioperatoriCommon> getIntestatarioperatoriIForm() {
		return this.intestatarioperatori;
	}

	@Override
	public void setIntestatarioperatoriIForm(Set<IntestatarioperatoriCommon> intestatarioperatori) {
		this.intestatarioperatori = intestatarioperatori;
	}

	public void show() {
		System.out.println("Class=" + this.getClass());
		System.out.println("operatoreIForm=" + this.getOperatoreIForm());
		System.out.println("usernameIForm=" + this.getUsernameIForm());
		System.out.println("corporateIForm=" + this.getCorporateIForm());
		System.out.println("lockedIForm=" + this.getLockedIForm());
		System.out.println("mobileIForm=" + this.getMobileIForm());
		System.out.println("signerCodeIForm=" + this.getSignerCodeIForm());
		System.out.println("descriptionIForm=" + this.getDescriptionIForm());
//		System.out.println("emailIForm=" + this.getEmailIForm());
		System.out.println("flagOperatorTypeIForm=" + this.getFlagOperatorTypeIForm());
		System.out.println("nameIForm=" + this.getNameIForm());
		System.out.println("numFailedlogonIForm=" + this.getNumFailedlogonIForm());
		System.out.println("passwordIForm=" + this.getPasswordIForm());
		System.out.println("plainPasswordIForm=" + this.getPlainPasswordIForm());
		System.out.println("lastLogonIForm=" + this.getLastLogonIForm());
		System.out.println("expirationDateIForm=" + this.getExpirationDateIForm());
		System.out.println("lockDateIForm=" + this.getLockDateIForm());
		System.out.println("failedLogonDateIForm=" + this.getFailedLogonDateIForm());
		System.out.println("intestatariobjIForm=" + this.getIntestatariobjIForm());
		System.out.println("dataUltimaConnessioneIForm=" + this.getDataUltimaConnessioneIForm());
		System.out.println("codiceFiscaleIForm=" + this.getCodiceFiscaleIForm());
		System.out.println("surnameIForm=" + this.getSurnameIForm());
	}

	/**
	 * 
	 *COPY/MERGE Method Form Vs Pojo
	 * 
	 **/

	public CommonBusinessObject copy() {

		OperatoriForm _form = this;
		OperatoriCommon _pojo = (OperatoriCommon) this.nativePojo;

		if (_pojo == null) {

			if (Tracer.isDebugEnabled(this.getClass().getName())) {
				Tracer.debug(this.getClass().getName(), "", "", null);
				Tracer.debug(this.getClass().getName(), "copy()",
						"---------------------------------------------------------------------", null);
				Tracer.debug(this.getClass().getName(), "copy()",
						"Attenzione nativeObject dentro FORM vuoto provvedo ad istanziare POJO", null);
				Tracer.debug(this.getClass().getName(), "copy()",
						"---------------------------------------------------------------------", null);
				Tracer.debug(this.getClass().getName(), "", "", null);
			}

			bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
			bfr = bfl.useBeanFactory("it.nch.orm");
			bf = bfr.getFactory();
			_pojo = (OperatoriCommon) bf.getBean("Operatori");

		}

		_pojo.setOperatoreIForm(_form.getOperatoreIForm());
		_pojo.setUsernameIForm(_form.getUsernameIForm());
		_pojo.setCorporateIForm(_form.getCorporateIForm());
		_pojo.setEnteIForm(_form.getEnteIForm());
		_pojo.setLockedIForm(_form.getLockedIForm());
		_pojo.setMobileIForm(_form.getMobileIForm());
		_pojo.setSignerCodeIForm(_form.getSignerCodeIForm());
		_pojo.setDescriptionIForm(_form.getDescriptionIForm());
//		_pojo.setEmailIForm(_form.getEmailIForm());
		_pojo.setNameIForm(_form.getNameIForm());
		_pojo.setNumFailedlogonIForm(_form.getNumFailedlogonIForm());
		_pojo.setPasswordIForm(_form.getPasswordIForm());
		_pojo.setPlainPasswordIForm(_form.getPlainPasswordIForm());
		_pojo.setLastLogonIForm(_form.getLastLogonIForm());
		_pojo.setExpirationDateIForm(_form.getExpirationDateIForm());
		_pojo.setLockDateIForm(_form.getLockDateIForm());
		_pojo.setFailedLogonDateIForm(_form.getFailedLogonDateIForm());
		
		_pojo.setTributiAmmessiCollection(_form.getTributiAmmessiCollection());
		_pojo.setFlagTributiAmmessi(_form.getFlagTributiAmmessi());
		// Oggetto innestato copio in modo ricorso relativamente
		// alla definizione delle dipendenze del properties Common
		
		if (_form.getIntestatariobjIForm() != null)
			_pojo.setIntestatariobjIForm((IntestatariCommon) _form.getIntestatariobjIForm().copy());

		//copia degli intestatari associati
		if (_form.getIntestatariIForm() != null) {
			Set<IntestatariCommon> intestatariCopy = new HashSet<IntestatariCommon>();
			for (Iterator<IntestatariCommon> intestatariIterator = _form.getIntestatariIForm().iterator(); intestatariIterator
					.hasNext();) {
				intestatariCopy.add((IntestatariCommon) intestatariIterator.next().copy());
			}
			_pojo.setIntestatariIForm(intestatariCopy);
		}
		
		//copia degli intestatari Operatori associati
		if (_form.getIntestatarioperatoriIForm() != null) {
			Set<IntestatarioperatoriCommon> intestatariOperatoriCopy = new HashSet<IntestatarioperatoriCommon>();
			for (Iterator<IntestatarioperatoriCommon> intestatariOperatoriIterator = _form.getIntestatarioperatoriIForm().iterator(); intestatariOperatoriIterator.hasNext();) {
				intestatariOperatoriCopy.add((IntestatarioperatoriCommon) intestatariOperatoriIterator.next().copy());
			}
			_pojo.setIntestatarioperatoriIForm(intestatariOperatoriCopy);
			
		}
		
		_pojo.setCodiceFiscaleIForm(_form.getCodiceFiscaleIForm());
		_pojo.setSurnameIForm(_form.getSurnameIForm());
		_pojo.setFlagOperatorTypeIForm(_form.getFlagOperatorTypeIForm());

		return _pojo;
	}

	public DTO incapsulateBO() {
		return new DTOImpl(this);
	}

	// Metodo di RESET

	public void reset() {
		this.operatoreIForm = "";
		this.usernameIForm = "";
		this.corporateIForm = "";
		this.lockedIForm = "";
		this.mobileIForm = "";
		this.signerCodeIForm = "";
		this.descriptionIForm = "";
//		this.emailIForm = "";
		this.flagOperatorTypeIForm = "";
		this.nameIForm = "";
		this.numFailedlogonIForm = "";
		this.passwordIForm = "";
		this.plainPasswordIForm = "";
		this.lastLogonIForm = "";
		this.expirationDateIForm = "";
		this.lockDateIForm = "";
		this.failedLogonDateIForm = "";
		this.codiceFiscaleIForm = "";
		this.surnameIForm = "";

		// DATI FORM NON COMMON

		this.dataUltimaConnessioneIForm = "";
	}

	public String getPageFrom() {
		return pageFrom;
	}

	public void setPageFrom(String pageFrom) {
		this.pageFrom = pageFrom;
	}

	public void setFlagCurrentOperator(String flagCurrentOperator) {
		this.flagCurrentOperator = flagCurrentOperator;
	}

	public String getFlagCurrentOperator() {
		return flagCurrentOperator;
	}
	
	public void setTributiAmmessi(String tributiAmmessi) {
		this.tributiAmmessi = tributiAmmessi;
	}

	public String getTributiAmmessi() {
		return tributiAmmessi;
	}
	
	public Set<TributiOperatoriCommon> getTributiDisponibiliList() {
		return tributiDisponibiliList;
	}

	public void setTributiDisponibiliList(Set<TributiOperatoriCommon> tributiDisponibiliList) {
		this.tributiDisponibiliList = tributiDisponibiliList;
	}

	@Override
	public void setTributiAmmessiCollection(Set<TributiOperatoriCommon> tributiOperatori) {
		this.tributiAmmessiCollection = tributiOperatori;
	}

	@Override
	public Set<TributiOperatoriCommon> getTributiAmmessiCollection() {
		return tributiAmmessiCollection;
	}

	public String getEnteIForm() {
		return enteIForm;
	}

	public void setEnteIForm(String enteIForm) {
		this.enteIForm = enteIForm;
	}
	
	
}
