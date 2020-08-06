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
import it.nch.utility.enumeration.Categoria;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

@NamedQueries ({
	@NamedQuery(
		name="it.nch.menu.filtered.application",
				query=" SELECT app FROM Applicazioni app LEFT JOIN app.aree ar " +
						" WHERE app.enabled=:abilitazioneGlobale AND ar.enabled=:abilitazioneGlobale " +
						" ORDER BY ar.areaCode ASC, app.applicationCode ASC"),
	@NamedQuery(
		name="getCodApplicazioneByCategoria",
		query="select app.applicationCode from Applicazioni app where app.categoria = :categoria")
		
})

@SuppressWarnings(value="serial")
@Entity
@Table(name = "APPLICAZIONI")
public class Applicazioni extends PojoImpl implements ApplicazioniCommon, ApplicazioniPojo {

	/*** Persistent Properties ***/
	private String applicationCode;
	private Date updateDate;
	private String updateUser;
	private String description;
	private String enabled;
	private String categoria;
	
	/*** Persistent Collections ***/
	private Set<Area> aree;
	
	/*** Transient Properties ***/
	private Categoria category;
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;

	@Id
	@Column(name="codapplicazione")
	public String getApplicationCode() {
		return this.applicationCode;
	}
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	@Column(name = "datamodifica")
	public Date getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "utentemod")
	public String getUpdateUser() {
		return this.updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "descrizione")
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "ABL_GLOBAL")
	public String getEnabled() {
		return this.enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "CATEGORIA")
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
		setCategory(categoria);
	}
	
	
	@OneToMany(targetEntity = Area.class, mappedBy = "applicazioneobj")
	public Set<Area> getAree() {
		return this.aree;
	}
	public void setAree(Set aree) {
		this.aree = aree;
	}

	@Override
	@Transient
	public Categoria getCategory() {
		return this.category;
	}

	@Override
	@Transient
	public void setCategory(String categoryString) {
		this.categoria = categoryString;
		this.category = Categoria.valueOf(categoryString);
	}

	@Override
	@Transient
	public void show() {
		System.out.println("Class=" + this.getClass());
		System.out.println("applicationCode=" + this.getApplicationCode());
		System.out.println("updateDate=" + this.getUpdateDate());
		System.out.println("updateUser=" + this.getUpdateUser());
		System.out.println("description=" + this.getDescription());
		System.out.println("enabled=" + this.getEnabled());
		System.out.println("aree=" + this.getAree());
	}

	/**
	 * 
	 * Method Per gestione Tipi non nativi
	 * 
	 **/
	@Transient
	@Override
	public String getEnabledIForm() {
		return this.enabled;
	}
	@Override
	public void setEnabledIForm(String enabledIForm) {
		this.enabled = enabledIForm;
	}

	@Transient
	@Override
	public String getApplicationCodeIForm() {
		return this.applicationCode;
	}
	@Override
	public void setApplicationCodeIForm(String applicationCodeIForm) {
		this.applicationCode = applicationCodeIForm;
	}

	@Transient
	@Override
	public String getDescriptionIForm() {
		return this.description;
	}
	@Override
	public void setDescriptionIForm(String descriptionIForm) {
		this.description = descriptionIForm;
	}

	@Transient
	@Override
	public Collection getAreeIForm() {
		return (this.aree);
	}
	@Override
	public void setAreeIForm(Set areeIForm) {
		this.aree = areeIForm;
	}

	@Transient
	@Override
	public String getUpdateDateIForm() {
		return Traslator.dateToString(this.updateDate);
	}
	@Override
	public void setUpdateDateIForm(String updateDateIForm) {
		this.updateDate = Traslator.stringToDate(updateDateIForm);
	}

	@Override
	@Transient
	public String getUpdateUserIForm() {
		return this.updateUser;
	}
	@Override
	public void setUpdateUserIForm(String updateUserIForm) {
		this.updateUser = updateUserIForm;
	}

	/**
	 * 
	 * COPY Method Pojo Vs Form
	 * 
	 **/
	@Transient
	@Override
	public CommonBusinessObject copy() {

		bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		ApplicazioniForm _form = (ApplicazioniForm) bf.getBean("ApplicazioniForm");

		_form.setNativePojo(this);

		ApplicazioniCommon _pojo = this;

		_form.setApplicationCodeIForm(_pojo.getApplicationCodeIForm());
		_form.setUpdateDateIForm(_pojo.getUpdateDateIForm());
		_form.setUpdateUserIForm(_pojo.getUpdateUserIForm());
		_form.setDescriptionIForm(_pojo.getDescriptionIForm());
		_form.setEnabledIForm(_pojo.getEnabledIForm());

		/*
		 * 
		 * Code copy AreeIForm attribute
		 */

		//Collection destAreeIForm = new Vector();
		Set destAreeIForm = new HashSet();
		if (_pojo.getAreeIForm() != null) {
			Iterator srcAreeIForm = _pojo.getAreeIForm().iterator();
			while (srcAreeIForm.hasNext()) {
				CommonBusinessObject _innerPojo = (CommonBusinessObject) srcAreeIForm.next();
				PojoImpl source = (PojoImpl) _innerPojo;
				source.setFlagDoNotCopy(true); // SADPA - This patch was made to
												// get rid of infinite
												// recursions in case of
												// bidirectional links
												// (one-to-many-many-to-one)
				destAreeIForm.add(_innerPojo.copy());
			}
			_form.setAreeIForm(destAreeIForm);
		}

		return _form;
	}

	@Transient
	@Override
	public DTO incapsulateBO() {
		return new DTOImpl((CommonBusinessObject) this);
	}

	/**
	 * 
	 * Metodo Clone richiesto!!!
	 * 
	 **/
	@Override
	public Object clone() {

		Applicazioni _pojoCurrent = this;
		Applicazioni _pojo = new Applicazioni();

		_pojo.setApplicationCode(_pojoCurrent.getApplicationCode());
		_pojo.setUpdateDate(_pojoCurrent.getUpdateDate());
		_pojo.setUpdateUser(_pojoCurrent.getUpdateUser());
		_pojo.setDescription(_pojoCurrent.getDescription());
		_pojo.setEnabled(_pojoCurrent.getEnabled());
		/**
		 * 
		 * Trovata Collection [aree] NON applico clone annidato!
		 * 
		 */

		_pojo.setId(_pojoCurrent.getId());

		return _pojo;
	}

	/*** Overriding of methods: equals() and hashCode() to ensure identity by value ***/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((applicationCode == null) ? 0 : applicationCode.hashCode());
		result = prime * result + ((aree == null) ? 0 : aree.hashCode());
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
		Applicazioni other = (Applicazioni) obj;
		if (applicationCode == null) {
			if (other.applicationCode != null)
				return false;
		} else if (!applicationCode.equals(other.applicationCode))
			return false;
		if (aree == null) {
			if (other.aree != null)
				return false;
		} else if (!aree.equals(other.aree))
			return false;
		return true;
	}

	/*** Overriding of method: toString() ***/
	@Override
	public String toString() {
		return "Applicazioni [applicationCode=" + applicationCode
				+ ", updateDate=" + updateDate + ", updateUser=" + updateUser
				+ ", description=" + description + ", category=" + category
				+ ", enabled=" + enabled + ", aree=" + aree + "]";
	}
	
}
