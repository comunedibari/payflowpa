/**
 *
 * Classe generata
 *
 */

package it.nch.is.fo.profilo;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * Pojo che contiene le informazioni di un operatore.
 * 
 * 
 */
@Entity
@Table(name = "OPERATORI")
@NamedQueries({
		@NamedQuery(name = "getOperatoreByUsername", 
			query = "select op from Operatori op where op.username = :username"),
		@NamedQuery(name = "getOperatoreByOperatoreAndCorporate", 
			query = "select op from Operatori op inner join op.intestatarioperatori io  where io.operatore.operatore = :operatore and io.intestatario.corporate = :corporate"),
		@NamedQuery(name = "getOperatoreByCodiceFiscale", 
			query = "select op from Operatori op inner join op.intestatarioperatori iop inner join iop.intestatario intest where op.codiceFiscale = :codiceFiscale"),
			@NamedQuery(name = "resetPwdOperatore", 
			query = "update Operatori op set op.password =:password, op.numFailedlogon=0, op.failedLogonDate=NULL, op.locked=0, op.lockDate=NULL where op.operatore =:operatore ")
		
})
public class Operatori extends PojoImpl implements OperatoriCommon, OperatoriPojo {
	private static final long serialVersionUID = 1L;

	/*** Persistent Properties ***/
	private String operatore;
	private String username;
	private String corporate;
	private String ente;
	private Integer locked;
	private String mobile;
	private String signerCode;
	private String description;
//	private String email;
	private String name;
	private Integer numFailedlogon;
	private String password;
	private String plainPassword;
	private Timestamp lastLogon;
	private Date expirationDate;
	private Timestamp lockDate;
	private Timestamp failedLogonDate;
	private String codiceFiscale;
	private String surname;
	private String flagOperatorType;
	private String flagTributiAmmessi;

	/*** Transient Properties ***/
	private transient Collection<OperatoriPojo> deleganti;
	private transient Set<TributiOperatoriCommon> tributioperatori;

	/*** Persistent References ***/
	/**
	 * Riferimento all'intestatarioperatori con cui l'utente � correntemente
	 * loggato. Non dovrebbe stare qui, ma solo nel FrontEndContext. Sistemato
	 * qui solo per non snaturare l'attuale procedura di recupero delle
	 * informazioni sull'operatore e l'intestatario.
	 */
	private Intestatarioperatori intestatarioperatoriobj;

	/**
	 * Riferimento all'intestatario che contiene i dati anagrfici di questo
	 * operatore.
	 */
	private Intestatari intestatariobj;

	/*** Persistent Collections ***/
	/**
	 * Elenco di intestatari a cui questo operatore � associato in quanto li pu�
	 * gestire.
	 */
	private Set<IntestatariCommon> intestatari = new LinkedHashSet<IntestatariCommon>();

	/**
	 * Elenco di associazioni tra questo operatore e gli intestatari che pu�
	 * gestire.
	 */
	private Set<IntestatarioperatoriCommon> intestatarioperatori = new LinkedHashSet<IntestatarioperatoriCommon>();

	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;

	@Id
	public String getOperatore() {
		return this.operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Transient
	public String getCorporate() {
		return this.intestatariobj.getCorporate();
	}

	public void setCorporate(String corporate) {
		this.intestatariobj.setCorporate(corporate);
	}

	@Column(name = "BLOCCATO")
	public Integer getLocked() {
		return this.locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	@Column(name = "CELLULARE")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "CODICEFIRMATARIO")
	public String getSignerCode() {
		return this.signerCode;
	}

	public void setSignerCode(String signerCode) {
		this.signerCode = signerCode;
	}

	@Column(name = "DESCRIZIONE")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public String getEmail() {
//		return this.email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	@Column(name = "NOME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NULL_COLL_FALL")
	public Integer getNumFailedlogon() {
		return this.numFailedlogon;
	}

	public void setNumFailedlogon(Integer numFailedlogon) {
		this.numFailedlogon = numFailedlogon;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ULTIMOCOLLEGAMENTO")
	public Timestamp getLastLogon() {
		return this.lastLogon;
	}

	public void setLastLogon(Timestamp lastLogon) {
		this.lastLogon = lastLogon;
	}

	@Column(name = "DATASCADENZA")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name = "DATABLOCCO")
	public Timestamp getLockDate() {
		return this.lockDate;
	}

	public void setLockDate(Timestamp lockDate) {
		this.lockDate = lockDate;
	}

	@Column(name = "DATA_COLL_FALL")
	public Timestamp getFailedLogonDate() {
		return this.failedLogonDate;
	}

	public void setFailedLogonDate(Timestamp failedLogonDate) {
		this.failedLogonDate = failedLogonDate;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@Column(name = "COGNOME")
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@OneToOne(targetEntity = Intestatari.class, optional = false)
	@JoinColumn(name = "INTESTATARIO")
	public IntestatariCommon getIntestatariobj() {
		return (IntestatariCommon) this.intestatariobj;
	}

	public void setIntestatariobj(IntestatariCommon intestatariobj) {
		this.intestatariobj = (Intestatari) intestatariobj;
	}

	// @ManyToMany(mappedBy="operatori",targetEntity=Intestatari.class)
	// public Set<IntestatariCommon> getIntestatari() {
	// return this.intestatari;
	// }
	//
	// public void setIntestatari(Set<IntestatariCommon> intestatari) {
	// this.intestatari = intestatari;
	// }

	@Transient
	public Set<IntestatariCommon> getIntestatari() {
		if (this.intestatarioperatori == null)
			return null;

		Set<IntestatariCommon> intests = new LinkedHashSet<IntestatariCommon>();
		for (IntestatarioperatoriCommon intoper : this.getIntestatarioperatori()) {
			intests.add((IntestatariCommon) intoper.getIntestatariobjIForm());
		}
		return intests;
	}

	public void setIntestatari(Set<IntestatariCommon> intestatari) {
		this.intestatari = intestatari;
	}

	@OneToMany(targetEntity = Intestatarioperatori.class, fetch = FetchType.EAGER, mappedBy = "operatore", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<IntestatarioperatoriCommon> getIntestatarioperatori() {
		return this.intestatarioperatori;
	}

	public void setIntestatarioperatori(Set<IntestatarioperatoriCommon> intestatarioperatori) {
		this.intestatarioperatori = intestatarioperatori;
	}

	@Transient
	public String getPlainPassword() {
		return this.plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/**
	 * Restituisce l'intestatario con cui l'operatore si � loggato nella
	 * sessione corrente.
	 * 
	 * @return l'intestatario con cui l'operatore si � loggato nella sessione
	 *         corrente.
	 */
	@Transient
	public Intestatarioperatori getIntestatarioperatoriCorrente() {
		return (Intestatarioperatori) getIntestatarioperatoriobj();
	}

	/**
	 * @see it.nch.is.fo.profilo.OperatoriPojo#getIntestatarioperatoriobj()
	 */
	@Override
	@Transient
	public IntestatarioperatoriCommon getIntestatarioperatoriobj() {
		return intestatarioperatoriobj;
	}

	/**
	 * @see it.nch.is.fo.profilo.OperatoriPojo#setIntestatarioperatoriobj(it.nch.is.fo.profilo.IntestatarioperatoriCommon)
	 */
	public void setIntestatarioperatoriobj(IntestatarioperatoriCommon intestatarioperatoriobj) {
		this.intestatarioperatoriobj = (Intestatarioperatori) intestatarioperatoriobj;
	}

	@Override
	@Transient
	public Collection<OperatoriPojo> getDeleganti() {
		
		return this.deleganti;
	}

	@Override
	@Transient
	public void setDeleganti(Collection<OperatoriPojo> deleganti) {
		this.deleganti = deleganti;
	}

	@Override
	@Transient
	public void show() {
		System.out.println("Class=" + this.getClass());
		System.out.println("operatore=" + this.getOperatore());
		System.out.println("username=" + this.getUsername());
		System.out.println("corporate=" + this.getCorporate());
		System.out.println("locked=" + this.getLocked());
		System.out.println("mobile=" + this.getMobile());
		System.out.println("signerCode=" + this.getSignerCode());
		System.out.println("description=" + this.getDescription());
//		System.out.println("email=" + this.getEmail());
		System.out.println("name=" + this.getName());
		System.out.println("numFailedlogon=" + this.getNumFailedlogon());
		System.out.println("password=" + this.getPassword());
		System.out.println("plainPassword=" + this.getPlainPassword());
		System.out.println("lastLogon=" + this.getLastLogon());
		System.out.println("expirationDate=" + this.getExpirationDate());
		System.out.println("lockDate=" + this.getLockDate());
		System.out.println("failedLogonDate=" + this.getFailedLogonDate());
		System.out.println("intestatariobj=" + this.getIntestatariobj());
		System.out.println("intestatari=" + this.getIntestatari());
		System.out.println("intestatariOperatori=" + this.getIntestatarioperatori());
		System.out.println("codiceFiscale=" + this.getCodiceFiscale());
		System.out.println("surname=" + this.getSurname());
	}

	/**
	 * 
	 * Method Per gestione Tipi non nativi
	 * 
	 **/
	@Override
	@Transient
	public IntestatariCommon getIntestatariobjIForm() {
		return this.intestatariobj;
	}

	@Override
	public void setIntestatariobjIForm(IntestatariCommon intestatariobjIForm) {
		this.intestatariobj = (Intestatari) intestatariobjIForm;
	}

	@Override
	@Transient
	public String getExpirationDateIForm() {
		return Traslator.dateToString(this.expirationDate);
	}

	@Override
	public void setExpirationDateIForm(String expirationDateIForm) {
		this.expirationDate = Traslator.stringToDate(expirationDateIForm);
	}

	@Override
	@Transient
	public String getNumFailedlogonIForm() {
		return Traslator.integerToString(this.numFailedlogon);
	}

	@Override
	public void setNumFailedlogonIForm(String numFailedlogonIForm) {
		this.numFailedlogon = Traslator.stringToInteger(numFailedlogonIForm);
	}

	@Override
	@Transient
	public String getLockDateIForm() {
		return Traslator.timestampToString(this.lockDate);
	}

	@Override
	public void setLockDateIForm(String lockDateIForm) {
		this.lockDate = Traslator.stringToTimestamp(lockDateIForm);
	}

	@Override
	@Transient
	public String getPasswordIForm() {
		return this.password;
	}

	@Override
	public void setPasswordIForm(String passwordIForm) {
		this.password = passwordIForm;
	}

	@Override
	@Transient
	public String getUsernameIForm() {
		return this.username;
	}

	@Override
	public void setUsernameIForm(String usernameIForm) {
		this.username = usernameIForm;
	}

	@Override
	@Transient
	public String getCorporateIForm() {
		return this.corporate;
	}

	@Override
	public void setCorporateIForm(String corporateIForm) {
		this.corporate = corporateIForm;
	}

	@Override
	@Transient
	public String getEnteIForm() {
		return this.ente;
	}

	@Override
	public void setEnteIForm(String ente) {
		this.ente = ente;
	}

	@Override
	@Transient
	public String getOperatoreIForm() {
		return this.operatore;
	}

	@Override
	public void setOperatoreIForm(String operatoreIForm) {
		this.operatore = operatoreIForm;
	}

//	@Override
//	@Transient
//	public String getEmailIForm() {
//		return this.email;
//	}
//
//	@Override
//	public void setEmailIForm(String emailIForm) {
//		this.email = emailIForm;
//	}

	@Override
	@Transient
	public String getSignerCodeIForm() {
		return this.signerCode;
	}

	@Override
	public void setSignerCodeIForm(String signerCodeIForm) {
		this.signerCode = signerCodeIForm;
	}

	@Override
	@Transient
	public String getDescriptionIForm() {
		return this.description;
	}

	@Override
	public void setDescriptionIForm(String descriptionIForm) {
		this.description = descriptionIForm;
	}

	@Override
	@Transient
	public String getFailedLogonDateIForm() {
		return Traslator.timestampToString(this.failedLogonDate);
	}

	@Override
	public void setFailedLogonDateIForm(String failedLogonDateIForm) {
		this.failedLogonDate = Traslator.stringToTimestamp(failedLogonDateIForm);
	}

	@Override
	@Transient
	public String getNameIForm() {
		return this.name;
	}

	@Override
	public void setNameIForm(String nameIForm) {
		this.name = nameIForm;
	}

	@Override
	@Transient
	public String getPlainPasswordIForm() {
		return this.plainPassword;
	}

	@Override
	public void setPlainPasswordIForm(String plainPasswordIForm) {
		this.plainPassword = plainPasswordIForm;
	}

	@Override
	@Transient
	public String getLastLogonIForm() {
		return Traslator.timestampToString(this.lastLogon);
	}

	@Override
	public void setLastLogonIForm(String lastLogonIForm) {
		this.lastLogon = Traslator.stringToTimestamp(lastLogonIForm);
	}

	@Override
	@Transient
	public String getLockedIForm() {
		return Traslator.integerToString(this.locked);
	}

	@Override
	public void setLockedIForm(String lockedIForm) {
		this.locked = Traslator.stringToInteger(lockedIForm);
	}

	@Override
	@Transient
	public String getMobileIForm() {
		return this.mobile;
	}

	@Override
	public void setMobileIForm(String mobileIForm) {
		this.mobile = mobileIForm;
	}

	@Override
	@Transient
	public String getCodiceFiscaleIForm() {
		return this.codiceFiscale;
	}

	@Override
	public void setCodiceFiscaleIForm(String codiceFiscaleIForm) {
		this.codiceFiscale = codiceFiscaleIForm;
	}

	@Override
	@Transient
	public Set<IntestatariCommon> getIntestatariIForm() {
		return this.getIntestatari();
	}

	@Override
	public void setIntestatariIForm(Set<IntestatariCommon> intestatariIForm) {
		this.intestatari = intestatariIForm;
	}

	@Override
	@Transient
	public String getSurnameIForm() {
		return this.surname;
	}

	@Override
	public void setSurnameIForm(String surnameIForm) {
		this.surname = surnameIForm;
	}

	@Override
	@Transient
	public Set<IntestatarioperatoriCommon> getIntestatarioperatoriIForm() {
		return this.intestatarioperatori;
	}

	@Override
	public void setIntestatarioperatoriIForm(Set<IntestatarioperatoriCommon> intestatarioperatori) {
		this.intestatarioperatori = intestatarioperatori;
	}

	@Override
	@Transient
	public String getFlagOperatorTypeIForm() {
		return this.flagOperatorType;
	}

	@Override
	public void setFlagOperatorTypeIForm(String flagOperatorTypeIForm) {
		this.flagOperatorType = flagOperatorTypeIForm;
	}

	@Override
	@Transient
	public String getFlagTributiAmmessi() {
		return this.flagTributiAmmessi;
	}

	@Override
	public void setFlagTributiAmmessi(String flagTributiAmmessi) {
		this.flagTributiAmmessi = flagTributiAmmessi;
	}

	@Override
	@Transient
	public void setTributiAmmessiCollection(Set<TributiOperatoriCommon> tributiOperatori) {
		this.tributioperatori = tributiOperatori;
	}


	@Override
	@Transient
	public Set<TributiOperatoriCommon> getTributiAmmessiCollection() {
		return this.tributioperatori;
	}
	/**
	 * Copia in un OperatoriForm il contenuto di questo Pojo, settando anche
	 * questo pojo come . nativePojo nel Form. La copia avviene chiamando solo i
	 * metodi IForm del pojo.
	 * 
	 * COPY Method Pojo Vs Form
	 * 
	 * @see it.nch.fwk.fo.common.CommonBusinessObject#copy()
	 */
	@Override
	@Transient
	public CommonBusinessObject copy() {

		bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		OperatoriForm _form = (OperatoriForm) bf.getBean("OperatoriForm");

		_form.setNativePojo(this);

		OperatoriCommon _pojo = this;
		
		((PojoImpl)_pojo).addToCopyList(_pojo.getClass());

		_form.setOperatoreIForm(_pojo.getOperatoreIForm());
		_form.setUsernameIForm(_pojo.getUsernameIForm());
		_form.setCorporateIForm(_pojo.getCorporateIForm());
		_form.setLockedIForm(_pojo.getLockedIForm());
		_form.setMobileIForm(_pojo.getMobileIForm());
		_form.setSignerCodeIForm(_pojo.getSignerCodeIForm());
		_form.setDescriptionIForm(_pojo.getDescriptionIForm());
//		_form.setEmailIForm(_pojo.getEmailIForm());
		_form.setNameIForm(_pojo.getNameIForm());
		_form.setNumFailedlogonIForm(_pojo.getNumFailedlogonIForm());
		_form.setPasswordIForm(_pojo.getPasswordIForm());
		_form.setPlainPasswordIForm(_pojo.getPlainPasswordIForm());
		_form.setLastLogonIForm(_pojo.getLastLogonIForm());
		_form.setExpirationDateIForm(_pojo.getExpirationDateIForm());
		_form.setLockDateIForm(_pojo.getLockDateIForm());
		_form.setFailedLogonDateIForm(_pojo.getFailedLogonDateIForm());
		// Oggetto innestato copio in modo ricorso relativamente
		// alla definizione delle dipendenze del properties Common

		
		  Set destIntestatariOperatoriIForm = new LinkedHashSet();
		  if (_pojo.getIntestatarioperatoriIForm()!=null){
			  Iterator inteOperIterator = _pojo.getIntestatarioperatoriIForm().iterator();
			  while(inteOperIterator.hasNext()){
				  IntestatarioperatoriCommon _innerPojo = (IntestatarioperatoriCommon) inteOperIterator.next();
				  if (!isInCopyList(_innerPojo.getClass())){
					  ((PojoImpl) _innerPojo).setCopyList(getCopyList());
					  destIntestatariOperatoriIForm.add(_innerPojo.copy());
				  }
			  }
			  _form.setIntestatarioperatoriIForm(destIntestatariOperatoriIForm);
		  }

		if (_pojo.getIntestatariobjIForm() != null 
				&& !getFlagDoNotCopy() 
				&& !isInCopyList(_pojo.getIntestatariobjIForm().getClass())){
			
			((PojoImpl)_pojo.getIntestatariobjIForm()).setCopyList(this.getCopyList());
			_form.setIntestatariobjIForm((IntestatariCommon) _pojo.getIntestatariobjIForm().copy());
		}
		
//		if (_pojo.getIntestatariIForm() != null && !getFlagDoNotCopy()) {
//			Set<IntestatariCommon> intestatariCopy = new HashSet<IntestatariCommon>();
//
//			for (Iterator<IntestatariCommon> intestatariIterator = _pojo.getIntestatariIForm().iterator(); intestatariIterator.hasNext();) {
//				IntestatariCommon item = intestatariIterator.next();
//				((PojoImpl) item).setFlagDoNotCopy(true);
//				intestatariCopy.add((IntestatariCommon) item.copy());
//			}
//			_form.setIntestatariIForm(intestatariCopy);
//		}


		_form.setCodiceFiscaleIForm(_pojo.getCodiceFiscaleIForm());
		_form.setSurnameIForm(_pojo.getSurnameIForm());
		return _form;
	}

	@Override
	@Transient
	public DTO<?, Operatori, ?> incapsulateBO() {
		return new DTOImpl((CommonBusinessObject) this);
	}

	/**
	 * 
	 * Metodo Clone richiesto
	 * 
	 **/
	@Override
	public Object clone() {

		Operatori _pojoCurrent = this;
		Operatori _pojo = new Operatori();

		_pojo.setOperatore(_pojoCurrent.getOperatore());
		_pojo.setUsername(_pojoCurrent.getUsername());
		_pojo.setCorporate(_pojoCurrent.getCorporate());
		_pojo.setLocked(_pojoCurrent.getLocked());
		_pojo.setMobile(_pojoCurrent.getMobile());
		_pojo.setSignerCode(_pojoCurrent.getSignerCode());
		_pojo.setDescription(_pojoCurrent.getDescription());
//		_pojo.setEmail(_pojoCurrent.getEmail());
		// _pojo.setFlagOperatorType(_pojoCurrent.getFlagOperatorType());
		_pojo.setName(_pojoCurrent.getName());
		_pojo.setNumFailedlogon(_pojoCurrent.getNumFailedlogon());
		_pojo.setPassword(_pojoCurrent.getPassword());
		_pojo.setPlainPassword(_pojoCurrent.getPlainPassword());
		_pojo.setLastLogon(_pojoCurrent.getLastLogon());
		_pojo.setExpirationDate(_pojoCurrent.getExpirationDate());
		_pojo.setLockDate(_pojoCurrent.getLockDate());
		_pojo.setFailedLogonDate(_pojoCurrent.getFailedLogonDate());
		_pojo.setIntestatariobj(_pojoCurrent.getIntestatariobj());
		_pojo.setSurname(_pojoCurrent.getSurname());
		_pojo.setCodiceFiscale(_pojoCurrent.getCodiceFiscale());
		_pojo.setIntestatari(_pojoCurrent.getIntestatari());
		/**
		 * 
		 * Trovata Collection [intestatariOperatori] NON applico clone annidato!
		 * 
		 */

		_pojo.setId(_pojoCurrent.getId());

		return _pojo;
	}

	@Override
	@Transient
	public int compareTo(OperatoriPojo o) {
		int intForm = this.getIntestatariobj().getCorporateIForm().compareTo(o.getIntestatariobj().getCorporateIForm());
		return intForm;
	}

	@Override
	@Transient
	public String toString() {
		return getOperatore();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operatore == null) ? 0 : operatore.hashCode());
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
		Operatori other = (Operatori) obj;
		if (operatore == null) {
			if (other.operatore != null)
				return false;
		} else if (!operatore.equals(other.operatore))
			return false;
		return true;
	}


}
