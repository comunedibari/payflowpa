package it.nch.is.fo.tributi;

import java.util.Date;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.common.Traslator;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.business.PojoImpl;
import it.nch.is.fo.profilo.Intestatarioperatori;
import it.nch.is.fo.profilo.IntestatarioperatoriCommon;
import it.nch.is.fo.profilo.TributiOperatoriCommon;
import it.nch.is.fo.profilo.TributiOperatoriForm;
import it.nch.is.fo.profilo.TributiOperatoriPojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

@Entity
@Table(name="TRIBUTI_OPER")
@NamedQueries({

})


public class TributiOperatore extends PojoImpl implements TributiOperatoriCommon, TributiOperatoriPojo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3346881828900360L;
	
	/*** Persistent References ***/
	private Intestatarioperatori intestatarioperatori;
	private TributoEnte tributoente;
	
	/*** PK Reference ***/
	private TributioperId tribOperId;
	
	private String opInserimento;
	private Date tsInserimento;
	private String opAggiornamento;
	private Date tsAggiornamento;

	
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;
	private Integer locked;

	
	@Id
	public TributioperId getTributioperId() {
		return this.tribOperId;
	}
	public void setTributioperId(TributioperId tribOperId) {
		this.tribOperId = tribOperId;
	}
	
	@Column(name="CD_TRB_ENTE",insertable=false,updatable=false)
	public String getCdTrbEnte() {
		return tribOperId.getCdTrbEntePk();
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.tribOperId.setCdTrbEntePk(cdTrbEnte);
	}
	
	@Column(name="ID_ENTE", insertable=false, updatable=false)
	public String getIdEnte() {
		return this.tribOperId.getIdEntePk();
	}
	public void setIdEnte(String idEnte) {
		this.tribOperId.setIdEntePk(idEnte);
	}
	
	
	@Column(name="INTESTATARIO",insertable=false,updatable=false)
	public String getIdIntestatario() {
		return this.tribOperId.getIntestatario();
	}
	public void setIdIntestatario(String intestatario) {
		this.tribOperId.setIntestatario(intestatario);
	}
	
	@Column(name="OPERATORE",insertable=false,updatable=false)
	public String getIdOperatore() {
		return this.tribOperId.getOperatore();
	}
	public void setIdOperatore(String operatore) {
		this.tribOperId.setOperatore(operatore);
	}
	
	@ManyToOne(targetEntity=Intestatarioperatori.class)
	@JoinColumns({
        @JoinColumn(name="INTESTATARIO", referencedColumnName="INTESTATARIO",insertable =  false, updatable = false),
        @JoinColumn(name="OPERATORE", referencedColumnName="OPERATORE",insertable =  false, updatable = false)
    })
	
	public Intestatarioperatori getIntestatarioperatori() {
		return intestatarioperatori;
	}
	public void setIntestatarioperatori(IntestatarioperatoriCommon intestatarioperatori) {
		this.intestatarioperatori = (Intestatarioperatori)intestatarioperatori;
	}
	
	@ManyToOne(targetEntity=TributoEnte.class)
	@JoinColumns({
        @JoinColumn(name="ID_ENTE", referencedColumnName="ID_ENTE",insertable =  false, updatable = false),
        @JoinColumn(name="CD_TRB_ENTE", referencedColumnName="CD_TRB_ENTE",insertable =  false, updatable = false)
    })
	
	public TributoEnte getTributoente() {
		return tributoente;
	}
	public void setTributoente(TributoEnte tributoente) {
		this.tributoente = tributoente;
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
	
	@Transient
	public IntestatarioperatoriCommon getIntestatarioperatoriobj() {
		return intestatarioperatori;
	}
	public void setIntestatarioperatoriobj(IntestatarioperatoriCommon intestatarioperatori) {
		this.intestatarioperatori = (Intestatarioperatori) intestatarioperatori;
	}


	
	/**
	 * Copia in un TributiOperatoreForm il contenuto di questo Pojo, settando anche
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
		TributiOperatoriForm _form = (TributiOperatoriForm) bf.getBean("TributiOperatoriForm");
		_form.setNativePojo(this);
		TributiOperatoriCommon _pojo = this;
		((PojoImpl)_pojo).addToCopyList(_pojo.getClass());
		if (_pojo.getIntestatarioperatoriobjIForm() != null && !isInCopyList(_pojo.getIntestatarioperatoriobjIForm().getClass())){
			((PojoImpl) _pojo.getIntestatarioperatoriobjIForm()).setCopyList(getCopyList());
			_form.setIntestatarioperatoriobjIForm((IntestatarioperatoriCommon) _pojo.getIntestatarioperatoriobjIForm().copy());
		}
		
		_form.setLockedIForm(_pojo.getLockedIForm());
		return _form;
	}
	
	@Override
	@Transient
	public void show() {
		System.out.println("Class=" + this.getClass());
		System.out.println("intestatarioOperatore=" + this.getIntestatarioperatoriobj().getOperatoriobjIForm());
	}
	
	@Override
	@Transient
	public DTO incapsulateBO() {
		return new DTOImpl((CommonBusinessObject) this);
	}
	
	
	
	@Override
	@Transient
	public IntestatarioperatoriCommon getIntestatarioperatoriobjIForm() {
		return this.intestatarioperatori;
	}
	@Override
	public void setIntestatarioperatoriobjIForm(IntestatarioperatoriCommon intestatarioperatoribjIForm) {
		this.intestatarioperatori = (Intestatarioperatori)intestatarioperatoribjIForm;
		
	}
	@Override
	public int compareTo(TributiOperatoriCommon o) {
		int comp = this.getIntestatarioperatoriobjIForm().compareTo(o.getIntestatarioperatoriobjIForm());
		return comp;
	}
	@Override
	@Transient
	public ITributoEnte getTributoenteobj() {
		return this.tributoente;
	}
	@Override
	public void setTributoenteobj(ITributoEnte tributoenteobj) {
		this.tributoente = (TributoEnte)tributoenteobj;
	}
	@Override
	@Transient
	public String getCdTrbEntePk() {
		if(tribOperId != null)
			return tribOperId.getCdTrbEntePk();
		else
			return null;
	}
	@Override
	@Transient
	public void setCdTrbEntePk(String cdTrbEntePk) {
		if(this.tribOperId == null) {
			tribOperId = new TributioperId();
		}
		tribOperId.setCdTrbEntePk(cdTrbEntePk);
	}
	@Override
	@Transient
	public String getIdEntePk() {
		if(tribOperId != null)
			return tribOperId.getIdEntePk();
		else
			return null;
	}
	@Override
	@Transient
	public void setIdEntePk(String idEntePk) {
		if(this.tribOperId == null) {
			tribOperId = new TributioperId();
		}
		tribOperId.setIdEntePk(idEntePk);
		
	}
	@Override
	@Transient
	public String getIntestatarioPk() {
		if(tribOperId != null)
			return tribOperId.getIntestatario();
		else
			return null;
	}
	@Override
	@Transient
	public void setIntestatarioPk(String intestatarioPk) {
		if(this.tribOperId == null) {
			tribOperId = new TributioperId();
		}
		tribOperId.setIntestatario(intestatarioPk);
		
	}
	@Override
	@Transient
	public String getOperatorePk() {
		if(tribOperId != null)
			return tribOperId.getOperatore();
		else
			return null;

	}
	@Override
	@Transient
	public void setTipoOperatore(String tipoOperatore) {
		if(this.tribOperId == null) {
			tribOperId = new TributioperId();
		}
		tribOperId.setTipoOperatore(tipoOperatore);
	}
	
	@Override
	@Transient
	public String getTipoOperatore() {
		if(tribOperId != null)
			return tribOperId.getTipoOperatore();
		else
			return null;

	}
	@Override
	@Transient
	public void setOperatorePk(String operatorePk) {
		if(this.tribOperId == null) {
			tribOperId = new TributioperId();
		}
		tribOperId.setOperatore(operatorePk);
	}

	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(name="TS_INSERIMENTO")
	public Date getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Date tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Date getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Date tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}



}
