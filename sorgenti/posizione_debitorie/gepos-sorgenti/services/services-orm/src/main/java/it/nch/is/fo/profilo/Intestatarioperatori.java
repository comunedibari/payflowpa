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
import it.nch.is.fo.tributi.TributiOperatore;
import it.nch.utility.enumeration.TipoOperatore;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
@SuppressWarnings("serial")
@Entity
@Table(name="INTEST_OPER")
public class Intestatarioperatori extends PojoImpl implements IntestatarioperatoriCommon, IntestatarioperatoriPojo {

	/*** PK Reference***/
	private IntestatarioperatoriId opeId;
	
	/*** Persistent properties***/
	private String tipoOperatore;
	private Integer locked;

	/*** Persistent References ***/
	private Operatori operatore;
    private Intestatari intestatario;
	
	/*** Transient properties***/
	private TipoOperatore tipoOperatoreEnum;	
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;
	
	/*** Persistent Collections ***/
	private Set<TributiOperatoriCommon> tributiOperatore = new LinkedHashSet<TributiOperatoriCommon>();
	
	public Intestatarioperatori(){
		this.opeId = new IntestatarioperatoriId();
	}	
	
	public Intestatarioperatori(IntestatarioperatoriId opeId){
		this.opeId = opeId;
	}

	@Id
	public IntestatarioperatoriId getOpeId() {
		return opeId;
	}
	public void setOpeId(IntestatarioperatoriId opeId) {
		this.opeId = opeId;
	}

	@Column(name="TP_OPERATORE")
	public String getTipoOperatore() {
		return this.tipoOperatore;
	}
	public void setTipoOperatore(String tipoOperatore) {
		this.tipoOperatore = tipoOperatore;
		if (tipoOperatore == null) {
			throw new RuntimeException("Data exception: il tipo operatore non puo\' essere null");
		} else {
			setTipoOperatoreEnum(tipoOperatore);
		}

	}

	@Column(name="BLOCCATO")
	public Integer getLocked() {
		return this.locked;
	}
	public void setLocked(Integer lock) {
		this.locked = lock;
	}
	
    /*** Unidirectional ManyToOne Association to Intestatari ***/
	@ManyToOne(targetEntity=Intestatari.class)
	@JoinColumn(name="INTESTATARIO",insertable=false,updatable=false)
	public Intestatari getIntestatario() {
		return this.intestatario;
	}
	public void setIntestatario(Intestatari intestatario) {
		this.intestatario = (Intestatari) intestatario;
	}

	@Transient
	public IntestatariCommon getIntestatariobj() {
		return (IntestatariCommon) this.intestatario;
	}
	public void setIntestatariobj(IntestatariCommon intestatario) {
		this.intestatario = (Intestatari) intestatario;
	}
	
    @OneToMany(targetEntity=TributiOperatore.class,mappedBy="intestatarioperatori",fetch = FetchType.EAGER)
    public Set<TributiOperatoriCommon> getTributiOperatori() {
    	return this.tributiOperatore;
 	} 
    public void setTributiOperatori(Set<TributiOperatoriCommon> tributiOperatori){
 		this.tributiOperatore= tributiOperatori;
	}
    
	@Override
	@Transient
	public Set<TributiOperatoriCommon> getTributiOperatoreForm() {
		return this.tributiOperatore;
	}

	@Override
	public void setTributiOperatoreForm(Set<TributiOperatoriCommon> tributiOperatore) {
		this.tributiOperatore = tributiOperatore;
	}

	/*** Bidirectional many-to-one association to Operatori - inverse side***/
    @ManyToOne(targetEntity=Operatori.class)
	@JoinColumn(name="OPERATORE",insertable=false,updatable=false)
	public Operatori getOperatore() {
		return operatore;
	}
	public void setOperatore(Operatori operatore) {
		this.operatore = operatore;
	}

	@Transient
	public OperatoriCommon getOperatoreobj() {
		return operatore;
	}
	public void setOperatoreobj(OperatoriCommon operatore) {
		this.operatore = (Operatori) operatore;
	}
	
	
	@Transient
	public TipoOperatore getTipoOperatoreEnum() {
		return tipoOperatoreEnum;
	}
	public void setTipoOperatoreEnum(String tipoOperatoreString) {
		this.tipoOperatoreEnum = TipoOperatore.valueOf(tipoOperatoreString);
	}
	
	@Transient
	public boolean isGenericOperator() {
		return getTipoOperatoreEnum().equals(TipoOperatore.OP);
	}

	@Transient
	public boolean isAdminitrator() {
		return getTipoOperatoreEnum().equals(TipoOperatore.AC);
	}

	
	@Override
	@Transient
	public void show() {
		System.out.println("Class=" + this.getClass());
		System.out.println("intestatario=" + this.getIntestatariobj().getCorporateIForm());
		System.out.println("operatore=" + this.getOperatoreobj().getOperatoreIForm());
		System.out.println("intestatariobj=" + this.getIntestatariobj());
	}

	/**
	 * 
	 * Method Per gestione Tipi non nativi
	 * 
	 **/
	@Override
	@Transient
	public IntestatariCommon getIntestatariobjIForm() {
		return this.intestatario;
	}
	@Override
	public void setIntestatariobjIForm(IntestatariCommon intestatariobjIForm) {
		this.intestatario = (Intestatari) intestatariobjIForm;
	}

	@Override
	@Transient
	public String getTipoOperatoreIForm() {
		return this.tipoOperatore.toString();
	}
	@Override
	public void setTipoOperatoreIForm(String tipoOperatoreIForm) {
		this.setTipoOperatore(tipoOperatoreIForm);
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



	/**
	 * 
	 * COPY Method Pojo Vs Form
	 * 
	 **/
	@Override
	@Transient
	public CommonBusinessObject copy() {

		bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		IntestatarioperatoriForm _form = (IntestatarioperatoriForm) bf.getBean("IntestatarioperatoriForm");

		_form.setNativePojo(this);

		IntestatarioperatoriCommon _pojo = this;
		
		((PojoImpl)_pojo).addToCopyList(_pojo.getClass());

		_form.setTipoOperatoreIForm(_pojo.getTipoOperatoreIForm());

		if (_pojo.getIntestatariobjIForm() != null && !isInCopyList(_pojo.getIntestatariobjIForm().getClass())){
			((PojoImpl) _pojo.getIntestatariobjIForm()).setCopyList(getCopyList());
			_form.setIntestatariobjIForm((IntestatariCommon) _pojo.getIntestatariobjIForm().copy());
		}

		if (_pojo.getOperatoriobjIForm() != null && !isInCopyList(_pojo.getOperatoriobjIForm().getClass())){
			((PojoImpl) _pojo.getOperatoriobjIForm()).setCopyList(getCopyList());
			_form.setOperatoriobjIForm((OperatoriCommon) _pojo.getOperatoriobjIForm().copy());
		}
		
	  Set destTributiOperatoriIForm = new LinkedHashSet();
	  if (_pojo.getTributiOperatoreForm() != null){
		  Iterator inteTribOperIterator = _pojo.getTributiOperatoreForm().iterator();
		  while(inteTribOperIterator.hasNext()){
			  TributiOperatoriCommon _innerPojo = (TributiOperatoriCommon) inteTribOperIterator.next();
			  if (!isInCopyList(_innerPojo.getClass())){
				  ((PojoImpl) _innerPojo).setCopyList(getCopyList());
				  destTributiOperatoriIForm.add(_innerPojo.copy());
			  }
		  }
		  _form.setTributiOperatoreForm(destTributiOperatoriIForm);
	  }


		_form.setLockedIForm(_pojo.getLockedIForm());

		return _form;
	}

	@Override
	@Transient
	public DTO incapsulateBO() {
		return new DTOImpl((CommonBusinessObject) this);
	}

	/**
	 * 
	 * Metodo Clone richiesto
	 * 
	 **/
	@Override
	public Object clone() {

		Intestatarioperatori _pojoCurrent = this;
		Intestatarioperatori _pojo = new Intestatarioperatori();
		
		_pojo.setOperatoreobj(_pojoCurrent.getOperatoreobj());
		_pojo.setIntestatariobj(_pojoCurrent.getIntestatariobj());
		_pojo.setLocked(_pojoCurrent.getLocked());

		return _pojo;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opeId == null) ? 0 : opeId.hashCode());
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
		Intestatarioperatori other = (Intestatarioperatori) obj;
		if (opeId == null) {
			if (other.opeId != null)
				return false;
		} else if (!opeId.equals(other.opeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer descrizione = new StringBuffer("Operatore ").append(getOpeId().getOperatore()).append("\n");
		descrizione.append("Intestatario ").append(getOpeId().getIntestatario()).append("\n");
		descrizione.append("TipoOperatore ").append(getTipoOperatore()).append("\n");
		descrizione.append("Locked ").append(getLocked()).append("\n");
		return descrizione.toString();
	}

	@Override
	@Transient
	public OperatoriCommon getOperatoriobj() {
		return this.operatore;
	}

	@Override
	public void setOperatoriobj(OperatoriCommon operatoriobj) {
		this.operatore = (Operatori)operatoriobj;
	}

	@Override
	@Transient
	public OperatoriCommon getOperatoriobjIForm() {
		return this.operatore;
	}

	@Override
	public void setOperatoriobjIForm(OperatoriCommon intestatariobjIForm) {
		this.operatore = (Operatori)intestatariobjIForm;
	}


	
	


}
