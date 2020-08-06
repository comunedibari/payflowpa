package it.nch.is.fo.configurazionePagamento;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.util.text.UtilityNumberFormatter;
import it.nch.is.fo.gestionePagamenti.CircuitoPagamentoCommon;
import it.nch.is.fo.gestionePagamenti.CommissionePagamentoCommon;
import it.nch.is.fo.gestionePagamenti.ConfigurazionePagamentiCommon;
import it.nch.is.fo.gestionePagamenti.ICircuitoPagamento;
import it.nch.is.fo.gestionePagamenti.ICommissionePagamento;
import it.nch.is.fo.gestionePagamenti.IConfigurazioneForm;
import it.nch.is.fo.gestionePagamenti.GatewayCommon;
import it.nch.is.fo.gestionePagamenti.IGatewayForm;
import it.nch.is.fo.gestionePagamenti.IScaglioneCommissioneForm;
import it.nch.is.fo.gestionePagamenti.IStrumentoPagamento;
import it.nch.is.fo.gestionePagamenti.ScaglioneCommissioneCommon;
import it.nch.is.fo.gestionePagamenti.StrumentoPagamentiCommon;
import it.nch.is.fo.profilo.EntiCommon;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.IntestatariCommon;
import it.nch.is.fo.profilo.IntestatariForm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class ScaglioneCommissioneFormImpl extends BaseForm implements  IScaglioneCommissioneForm{

	private String commissione;
	private String confPagamento;
	private String codScaglione;
	private String daScaglioneIForm;
	private String scaglioneAIForm;
	private String commissioneDescr;
	
	private String isNew;
	
	private String commissioneTable;
	
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;
	
	
	public ScaglioneCommissioneFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
	}
	

	
	public String getCommissione() {
		return commissione;
	}



	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}



	public String getConfPagamento() {
		return confPagamento;
	}



	public void setConfPagamento(String confPagamento) {
		this.confPagamento = confPagamento;
	}



	public String getCodScaglione() {
		return codScaglione;
	}



	public void setCodScaglione(String codScaglione) {
		this.codScaglione = codScaglione;
	}



	public String getDaScaglioneIForm() {
		return daScaglioneIForm;
	}



	public void setDaScaglioneIForm(String daScaglioneIForm) {
		this.daScaglioneIForm = daScaglioneIForm;
	}

	@Override
	public String getScaglioneAIForm() {
		return scaglioneAIForm;
	}



	@Override
	public void setScaglioneAIForm(String scaglioneAIForm) {
		this.scaglioneAIForm = scaglioneAIForm;
		
	}


	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		ActionErrors err = super.validate(mapping, request);
		return err;
	}



	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public CommonBusinessObject copy() {

		IScaglioneCommissioneForm _form = this;
        ScaglioneCommissioneCommon _pojo=(ScaglioneCommissioneCommon)this.nativePojo;

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
        	_pojo=(ScaglioneCommissioneCommon) bf.getBean("ScaglioneForm");

        }


        _pojo.setCommissione(_form.getCommissione());
        _pojo.setCodScaglione(_form.getCodScaglione());
        _pojo.setConfPagamento(_form.getConfPagamento());
        _pojo.setScaglioneAIForm(_form.getScaglioneAIForm());
        _pojo.setDaScaglioneIForm(_form.getDaScaglioneIForm());
        _pojo.setCommissioneDescr(_form.getCommissioneDescr());
   
 		
        return _pojo;
	  }



	@Override
	public DTO incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}



	public String getCommissioneDescr() {
		return commissioneDescr;
	}



	public void setCommissioneDescr(String commissioneDescr) {
		this.commissioneDescr = commissioneDescr;
	}









	
}
