package it.nch.is.fo.configurazionePagamento;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.web.util.text.UtilityNumberFormatter;
import it.nch.is.fo.gestionePagamenti.CircuitoPagamentoCommon;
import it.nch.is.fo.gestionePagamenti.CommissionePagamentoCommon;
import it.nch.is.fo.gestionePagamenti.ConfigurazionePagamentiCommon;
import it.nch.is.fo.gestionePagamenti.GatewayCommon;
import it.nch.is.fo.gestionePagamenti.IConfigurazioneForm;
import it.nch.is.fo.gestionePagamenti.ScaglioneCommissioneCommon;
import it.nch.is.fo.gestionePagamenti.StrumentoPagamentiCommon;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

public class ConfigurazioneFormImpl extends BaseForm implements  IConfigurazioneForm{

	private String confPagamento;
	private String maxImporto;
	private String flStato;
	private String gateway;
	private String commissione;
	private String strPagamento;
	private String circuito;
	private String stRiga;
	private Integer prVersione;
	private String opInserimento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
	private Timestamp tsAggiornamento;
	private GatewayCommon gateway_conf;
	private StrumentoPagamentiCommon strumentoPagamento;
	private CommissionePagamentoCommon commissionePagamento;
	private CircuitoPagamentoCommon circuitoPagamento;
	private String tipoCommissione;
	private Collection scaglioniCommissioni;
	private String importoTotaleCommissione;
	private String descrTipoPagamento;
	
	private String [] selectedItems;
	private String isNew;
	
	private String commissioneTable;
	private ScaglioneCommissioneCommon scaglioneNewRow;
	private ScaglioneCommissioneCommon oltreScaglioneRow;
	


	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;
	
	
	public ConfigurazioneFormImpl(){

		bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
		bfr = bfl.useBeanFactory("it.nch.orm");
		bf = bfr.getFactory();
		gateway_conf=(GatewayCommon)bf.getBean("GatewayForm");
		strumentoPagamento=(StrumentoPagamentiCommon)bf.getBean("StrumentoForm");
		commissionePagamento=(CommissionePagamentoCommon)bf.getBean("CommissioneForm");
		scaglioneNewRow=(ScaglioneCommissioneCommon)bf.getBean("ScaglioneCommissioneForm");
		oltreScaglioneRow=(ScaglioneCommissioneCommon)bf.getBean("ScaglioneCommissioneForm");
	}
	
	
	
	public String getImportoTotaleCommissione() {
		return importoTotaleCommissione;
	}



	public void setImportoTotaleCommissione(String importoTotaleCommissione) {
		this.importoTotaleCommissione = importoTotaleCommissione;
	}



	public ScaglioneCommissioneCommon getOltreScaglioneRow() {
		return oltreScaglioneRow;
	}



	public void setOltreScaglioneRow(ScaglioneCommissioneCommon oltreScaglioneRow) {
		this.oltreScaglioneRow = oltreScaglioneRow;
	}



	public ScaglioneCommissioneCommon getScaglioneNewRow() {
		return scaglioneNewRow;
	}


	public void setScaglioneNewRow(ScaglioneCommissioneCommon scaglioneNewRow) {
		this.scaglioneNewRow = scaglioneNewRow;
	}

	
	public Collection getScaglioniCommissioni() {
		return scaglioniCommissioni;
	}


	public void setScaglioniCommissioni(Set scaglioniCommissioni) {
		this.scaglioniCommissioni = scaglioniCommissioni;
	}
	
	public String getCommissioneTable() {
		return commissioneTable;
	}


	public void setCommissioneTable(String commissioneTable) {
		this.commissioneTable = commissioneTable;
	}


	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public CommissionePagamentoCommon getCommissionePagamento() {
		return commissionePagamento;
	}
	public void setCommissionePagamento(CommissionePagamentoCommon commissionePagamento) {
		this.commissionePagamento = commissionePagamento;
	}
	public CircuitoPagamentoCommon getCircuitoPagamento() {
		return circuitoPagamento;
	}
	public void setCircuitoPagamento(CircuitoPagamentoCommon circuitoPagamento) {
		this.circuitoPagamento = circuitoPagamento;
	}
	public StrumentoPagamentiCommon getStrumentoPagamento() {
		return strumentoPagamento;
	}
	public void setStrumentoPagamento(StrumentoPagamentiCommon strumentoPagamento) {
		this.strumentoPagamento = strumentoPagamento;
	}
	public GatewayCommon getGateway_conf() {
		return gateway_conf;
	}
	public void setGateway_conf(GatewayCommon gateway_conf) {
		this.gateway_conf = gateway_conf;
	}
	public String getConfPagamento() {
		return confPagamento;
	}
	public void setConfPagamento(String confPagamento) {
		this.confPagamento = confPagamento;
	}
	public String getMaxImportoForm() {
		return maxImporto;
	}
	public void setMaxImportoForm(String maxImporto) {
		this.maxImporto = maxImporto;
	}
	public String getFlStato() {
		return flStato;
	}
	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getCommissione() {
		return commissione;
	}
	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}
	public String getStrPagamento() {
		return strPagamento;
	}
	public void setStrPagamento(String strPagamento) {
		this.strPagamento = strPagamento;
	}
	public String getCircuito() {
		return circuito;
	}
	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	public Integer getPrVersione() {
		return prVersione;
	}
	public void setPrVersione(Integer prVersione) {
		this.prVersione = prVersione;
	}
	public String getOpInserimento() {
		return opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	public Timestamp getTsInserimento() {
		return tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	public String getOpAggiornamento() {
		return opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}
	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}
	public String[] getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	public BigDecimal getMaxImportoAsNum(){
		return UtilityNumberFormatter.numberAsStringToBigDecimal(maxImporto);
	}
	
	public void setNativePojo(Object nativePojo) {
		ConfigurazionePagamentiCommon confCommonNativePojo = (ConfigurazionePagamentiCommon)nativePojo;
		if (confCommonNativePojo.getGateway_conf() != null)
			((BaseForm)this.gateway_conf).setNativePojo(confCommonNativePojo.getGateway_conf());
		if (confCommonNativePojo.getStrumentoPagamento()!=null)
			((BaseForm)this.strumentoPagamento).setNativePojo(confCommonNativePojo.getStrumentoPagamento());
		if (confCommonNativePojo.getCommissionePagamento()!=null)
			((BaseForm)this.commissionePagamento).setNativePojo(confCommonNativePojo.getCommissionePagamento());
		if (confCommonNativePojo.getScaglioneNewRow()!=null)
			((BaseForm)this.scaglioneNewRow).setNativePojo(confCommonNativePojo.getScaglioneNewRow());
    	this.nativePojo = nativePojo;
	}
	@Override
	public CommonBusinessObject copy() {
		// TODO Auto-generated method stub
		return null;
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


	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		ActionErrors err = super.validate(mapping, request);
		return err;
	}


	@Override
	public BigDecimal getMaxImporto() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setMaxImporto(BigDecimal maxImportoNum) {
		if(maxImportoNum == null)
			maxImporto = "";
		else	
			maxImporto = UtilityNumberFormatter.numberAsBigDecimalToString(maxImportoNum);
		
	}


	public String getTipoCommissione() {
		return tipoCommissione;
	}


	public void setTipoCommissione(String tipoCommissione) {
		this.tipoCommissione = tipoCommissione;
	}



	public String getDescrTipoPagamento() {
		return descrTipoPagamento;
	}



	public void setDescrTipoPagamento(String descrTipoPagamento) {
		this.descrTipoPagamento = descrTipoPagamento;
	}


	
}
