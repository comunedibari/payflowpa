package it.nch.is.fo.profilo;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.tributi.ITributoEnte;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class TributiOperatoriFormImpl  extends BaseForm implements TributiOperatoriForm {
	
	private IntestatarioperatoriCommon intestatarioperatoriobjIForm;
	
	private String intestatarioIForm;
    private String operatoreIForm;
    private String tipoOperatoreIForm;
    
    private String idEnte;
    private String cdTrbEntePk;
    
    private String deTrb;
    
	private String lockedIForm;
	
    // TYPE SOLO PER LA FORM
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;
	
	public TributiOperatoriFormImpl(){
		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		
		intestatarioperatoriobjIForm=(IntestatarioperatoriCommon)bf.getBean("IntestatarioperatoriForm");
	}
	
	
	public void setNativePojo(Object nativePojo) {
		if (((TributiOperatoriCommon)nativePojo).getIntestatarioperatoriobjIForm() != null) {
			((BaseForm)this.intestatarioperatoriobjIForm).setNativePojo(((TributiOperatoriCommon)nativePojo).getIntestatarioperatoriobjIForm());
			this.cdTrbEntePk = ((TributiOperatoriCommon)nativePojo).getCdTrbEntePk();
	    	this.deTrb = ((TributiOperatoriCommon)nativePojo).getTributoenteobj().getDeTrb();
	    	this.idEnte = ((TributiOperatoriCommon)nativePojo).getIdEntePk();
	    	this.intestatarioIForm = ((TributiOperatoriCommon)nativePojo).getIntestatarioPk();
	    	this.operatoreIForm = ((TributiOperatoriCommon)nativePojo).getOperatorePk();
	    	this.tipoOperatoreIForm = ((TributiOperatoriCommon)nativePojo).getTipoOperatore();
		}
    	this.nativePojo = nativePojo;
	}


	@Override
	public IntestatarioperatoriCommon getIntestatarioperatoriobjIForm() {
		return intestatarioperatoriobjIForm;
	}


	@Override
	public void setIntestatarioperatoriobjIForm(
			IntestatarioperatoriCommon intestatarioperatoribjIForm) {
		this.intestatarioperatoriobjIForm = intestatarioperatoribjIForm;
	}
	
	public String getIntestatarioIForm() {
 		return this.intestatarioIForm;
 	} 

    public void setIntestatarioIForm(String intestatarioIForm){
 		this.intestatarioIForm=intestatarioIForm;
    } 

    public String getOperatoreIForm() {
 		return this.operatoreIForm;
 	} 

    public void setOperatoreIForm(String operatoreIForm){
 		this.operatoreIForm=operatoreIForm;
    } 


	@Override
	public void setLockedIForm(String locked) {
		this.lockedIForm = locked;
	}


	@Override
	public String getLockedIForm() {
		return this.lockedIForm;
	}


	@Override
	public CommonBusinessObject copy() {
        TributiOperatoriForm _form = this;
        TributiOperatoriCommon _pojo=(TributiOperatoriCommon)this.nativePojo;

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
        	_pojo=(TributiOperatoriCommon) bf.getBean("TributiOperatori");
        }
        // Oggetto innestato copio in modo ricorso relativamente
        // alla definizione delle dipendenze del properties Common
 		if (_form.getIntestatarioperatoriobjIForm() != null)
            _pojo.setIntestatarioperatoriobjIForm((IntestatarioperatoriCommon)_form.getIntestatarioperatoriobjIForm().copy());
 		
 		_pojo.setLockedIForm(_form.getLockedIForm());

        return _pojo;

	}


	@Override
	public void show() {
		System.out.println("Class="+this.getClass());
		System.out.println("intestatarioIForm="+this.getIntestatarioIForm());
		System.out.println("operatoreIForm="+this.getOperatoreIForm());
		System.out.println("idEnte="+this.getIdEnte());
		System.out.println("CdTrbEntePk="+this.getCdTrbEnte());
		System.out.println("deTrb="+this.getDeTrb());
		System.out.println("TipoOperatore="+this.getTipoOperatore());
	}


	@Override
	public DTO incapsulateBO() {
		return new DTOImpl(this);
	}


	@Override
	public void reset() {
		this.intestatarioIForm="";
   		this.operatoreIForm="";
   		this.idEnte="";
   		this.cdTrbEntePk="";
   		this.deTrb="";
   		this.tipoOperatoreIForm="";
	}


	@Override
	public int compareTo(TributiOperatoriCommon o) {
		int comp = this.getIntestatarioperatoriobjIForm().compareTo(o.getIntestatarioperatoriobjIForm());
		return comp;

	}



	@Override
	public IntestatarioperatoriCommon getIntestatarioperatori() {
		return this.intestatarioperatoriobjIForm;
	}


	@Override
	public void setIntestatarioperatori(IntestatarioperatoriCommon intestatarioperatoriCommon) {
		this.intestatarioperatoriobjIForm = intestatarioperatoriCommon;
	}


	@Override
	public ITributoEnte getTributoenteobj() {
		return ((TributiOperatoriCommon)this.getNativePojo()).getTributoenteobj();
	}


	@Override
	public void setTributoenteobj(ITributoEnte tributoenteobj) {
		if (tributoenteobj != null) {
			this.cdTrbEntePk = tributoenteobj.getCdTrbEnte();
			this.idEnte = tributoenteobj.getIdEnte();
		} else {
			this.cdTrbEntePk = null;
			this.idEnte = null;
		}
	}

	@Override
	public String getCdTrbEnte() {
		if(cdTrbEntePk != null)
			return cdTrbEntePk;
		else
			return ((TributiOperatoriCommon)this.getNativePojo()).getTributoenteobj().getCdTrbEnte();
	}


	@Override
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEntePk = cdTrbEnte;
	}


	@Override
	public String getDeTrb() {
		return deTrb;
	}


	@Override
	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}
	
	@Override
	public String getIdEnte() {
		return this.idEnte;
	}


	@Override
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	
	@Override
	public String getCdTrbEntePk() {
		return cdTrbEntePk;
	}


	@Override
	public void setCdTrbEntePk(String cdTrbEntePk) {
		this.cdTrbEntePk = cdTrbEntePk;
	}


	@Override
	public String getIdEntePk() {
		return idEnte;
	}


	@Override
	public void setIdEntePk(String idEntePk) {
		this.idEnte = idEntePk;
	}


	@Override
	public String getIntestatarioPk() {
		return intestatarioIForm;
	}


	@Override
	public void setIntestatarioPk(String intestatarioPk) {
		this.intestatarioIForm = intestatarioPk;
	}


	@Override
	public String getOperatorePk() {
		return operatoreIForm;
	}


	@Override
	public void setOperatorePk(String operatorePk) {
		this.operatoreIForm = operatorePk;
	}

	@Override
	public String getTipoOperatore() {
		return tipoOperatoreIForm;
	}


	@Override
	public void setTipoOperatore(String tipoOperatore) {
		this.tipoOperatoreIForm = tipoOperatore;
	}

}
